/*
 * Copyright (c) 2001-2004 Caucho Technology, Inc.  All rights reserved.
 *
 * The Apache Software License, Version 1.1
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
 * 4. The names "Burlap", "Resin", and "Caucho" must not be used to
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

package com.caucho.services.jmx;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ObjectInstance;
import javax.management.MBeanInfo;
import javax.management.JMException;
import javax.management.RuntimeMBeanException;

import com.caucho.hessian.jmx.JMXSerializerFactory;

import com.caucho.hessian.client.HessianProxyFactory;

import com.caucho.services.server.GenericService;

/**
 * Client for mbeans.
 */
public class MBeanClient {
  private String _url;
  private RemoteJMX _jmxProxy;
  
  /**
   * Creates the MBeanClient.
   */
  public MBeanClient()
  {
  }
  
  /**
   * Creates the MBeanClient.
   */
  public MBeanClient(String url)
  {
    _url = url;
  }

  /**
   * Sets the proxy
   */
  public void setProxy(RemoteJMX proxy)
  {
    _jmxProxy = proxy;
  }

  /**
   * Returns the mbean info
   */
  public MBeanInfo getMBeanInfo(ObjectName objectName)
    throws Exception
  {
    return getProxy().getMBeanInfo(objectName.getCanonicalName());
  }

  /**
   * Gets an attribute.
   */
  public Object getAttribute(ObjectName objectName, String attrName)
    throws Exception
  {
    return getProxy().getAttribute(objectName.getCanonicalName(), attrName);
  }

  private RemoteJMX getProxy()
  {
    if (_jmxProxy == null) {
      try {
	HessianProxyFactory proxy = new HessianProxyFactory();
	proxy.getSerializerFactory().addFactory(new JMXSerializerFactory());
	_jmxProxy = (RemoteJMX) proxy.create(_url);
      } catch (Exception e) {
	throw new RuntimeException(e);
      }
    }

    return _jmxProxy;
  }
}
