/*
 * Copyright (c) 1998-2007 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Emil Ong
 */

package com.callcenter.taxi.client;

import java.net.MalformedURLException;
import java.util.List;

import java.io.*;

import com.caucho.hessian.client.CookieHessianURLConnectionFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.MyHessianProxyFactory;

/**
 * A small wrapper around the WordService client proxy.  This class
 * initializes the proxy and does some minor formatting.
 **/
public class CallTaxiProxy {
  /** An instance of the client used by the JavaFX script. */
  public static CallTaxiProxy CLIENT = 
    new CallTaxiProxy("http://localhost:8080/callcenter/taxi");

  /** The URL of the service. */
  private String _url;

  /** The client proxy for the WordService. */
  private CallTaxiService _service;

  /** A constructor which takes a URL string. */
  private CallTaxiProxy(String url)
  {
    _url = url;
  }

  /** Sets the static client instance's server URL. */
  public static void setServerURL(String server)
  {
    CLIENT = new CallTaxiProxy(server);
  }

  /**
   * Retrieves the WordService client proxy, creating it if necessary.
   **/
  private CallTaxiService getService()
  {
    if (_service == null) {
    	HessianProxyFactory factory = getHessianProxyFactory();
      try {
        _service = (CallTaxiService) factory.create(CallTaxiService.class, _url);
      }
      catch (MalformedURLException e) {
      }
    }

    return _service;
  }

  /**
   * Retrieves the HessianProxyFactory
   * @return
   */
private HessianProxyFactory getHessianProxyFactory() {
	/**
	 * for android api level 9
	 */
	//    	CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
	//      HessianProxyFactory factory = new HessianProxyFactory();
	
	/**
	 *  for android api level 8
	 */
//	      System.setProperty(HessianConnectionFactory.class.getName(), CookieHessianURLConnectionFactory.class.getName());
//	      MyHessianProxyFactory factory = new MyHessianProxyFactory();
	HessianProxyFactory factory = new HessianProxyFactory();
	return factory;
}

 
  public int echo(String userID){
	  return getService().echo( userID );
  }
  
  public List<Passenger> queryPassengers(int color, Rectangle rect){
	  return getService().queryPassengers(color, rect);
  }
}
