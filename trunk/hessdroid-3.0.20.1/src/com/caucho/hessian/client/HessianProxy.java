/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2004 Caucho Technology, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Caucho Technology (http://www.caucho.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "Hessian", "Resin", and "Caucho" must not be used to
 *    endorse or promote products derived from this software without prior
 *    written permission. For written permission, please contact
 *    info@caucho.com.
 *
 * 5. Products derived from this software may not be called "Resin"
 *    nor may "Resin" appear in their names without prior written
 *    permission of Caucho Technology.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL CAUCHO TECHNOLOGY OR ITS CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Scott Ferguson
 */

package com.caucho.hessian.client;

import java.io.*;
import java.util.*;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.caucho.hessian.io.*;

/**
 * Proxy implementation for Hessian clients.  Applications will generally
 * use HessianProxyFactory to create proxy clients.
 */
public class HessianProxy implements InvocationHandler {
  private HessianProxyFactory _factory;
  private URL _url;
  
  HessianProxy(HessianProxyFactory factory, URL url)
  {
    _factory = factory;
    _url = url;
  }

  /**
   * Returns the proxy's URL.
   */
  public URL getURL()
  {
    return _url;
  }

  /**
   * Handles the object invocation.
   *
   * @param proxy the proxy object to invoke
   * @param method the method to call
   * @param args the arguments to the proxy object
   */
  public Object invoke(Object proxy, Method method, Object []args)
    throws Throwable
  {
    String methodName = method.getName();
    Class []params = method.getParameterTypes();

    // equals and hashCode are special cased
    if (methodName.equals("equals") &&
        params.length == 1 && params[0].equals(Object.class)) {
      Object value = args[0];
      if (value == null || ! Proxy.isProxyClass(value.getClass()))
        return new Boolean(false);

      HessianProxy handler = (HessianProxy) Proxy.getInvocationHandler(value);

      return new Boolean(_url.equals(handler.getURL()));
    }
    else if (methodName.equals("hashCode") && params.length == 0)
      return new Integer(_url.hashCode());
    else if (methodName.equals("getHessianType"))
      return proxy.getClass().getInterfaces()[0].getName();
    else if (methodName.equals("getHessianURL"))
      return _url.toString();
    else if (methodName.equals("toString") && params.length == 0)
      return "[HessianProxy " + _url + "]";

    InputStream is = null;
    URLConnection conn = null;
    HttpURLConnection httpConn = null;
    
    try {
      if (! _factory.isOverloadEnabled()) {
      }
      else if (args != null)
        methodName = methodName + "__" + args.length;
      else
        methodName = methodName + "__0";

      conn = sendRequest(methodName, args);

      if (conn instanceof HttpURLConnection) {
	httpConn = (HttpURLConnection) conn;
        int code = 500;

        try {
          code = httpConn.getResponseCode();
        } catch (Exception e) {
        }

        parseResponseHeaders(conn);
        
        if (code != 200) {
          StringBuffer sb = new StringBuffer();
          int ch;

          try {
            is = httpConn.getInputStream();

            if (is != null) {
              while ((ch = is.read()) >= 0)
                sb.append((char) ch);

              is.close();
            }

            is = httpConn.getErrorStream();
            if (is != null) {
              while ((ch = is.read()) >= 0)
                sb.append((char) ch);
            }
          } catch (FileNotFoundException e) {
            throw new HessianRuntimeException(String.valueOf(e));
          } catch (IOException e) {
	    if (is == null)
	      throw new HessianProtocolException(code + ": " + e, e);
          }

          if (is != null)
            is.close();

          throw new HessianProtocolException(code + ": " + sb.toString());
        }
      }

      is = conn.getInputStream();

      AbstractHessianInput in = _factory.getHessianInput(is);

      return in.readReply(method.getReturnType());
    } catch (HessianProtocolException e) {
      throw new HessianRuntimeException(e);
    } finally {
      try {
	if (is != null)
	  is.close();
      } catch (Throwable e) {
      }
      
      try {
	if (httpConn != null)
	  httpConn.disconnect();
      } catch (Throwable e) {
      }
    }
  }

  /**
   * Method that allows subclasses to parse response headers such as cookies.
   * Default implementation is empty. 
   * @param conn
   */
  protected void parseResponseHeaders(URLConnection conn) {
	  
  }
  
  private URLConnection sendRequest(String methodName, Object []args)
    throws IOException
  {
    URLConnection conn = null;
    
    conn = _factory.openConnection(_url);

    // Used chunked mode when available, i.e. JDK 1.5.
    if (_factory.isChunkedPost() && conn instanceof HttpURLConnection) {
      try {
	HttpURLConnection httpConn = (HttpURLConnection) conn;

	httpConn.setChunkedStreamingMode(8 * 1024);
      } catch (Throwable e) {
      }
    }
    
    addRequestHeaders(conn);
    
    OutputStream os = null;

    try {
      os = conn.getOutputStream();
    } catch (Exception e) {
      throw new HessianRuntimeException(e);
    }

    try {
      AbstractHessianOutput out = _factory.getHessianOutput(os);

      out.call(methodName, args);
      out.flush();

      return conn;
    } catch (IOException e) {
      if (conn instanceof HttpURLConnection)
	((HttpURLConnection) conn).disconnect();

      throw e;
    } catch (RuntimeException e) {
      if (conn instanceof HttpURLConnection)
	((HttpURLConnection) conn).disconnect();

      throw e;
    }
  }
  
  /**
   * Method that allows subclasses to add request headers such as cookies.
   * Default implementation is empty. 
   */
  protected void addRequestHeaders(URLConnection conn) {
	  
  }
}
