package com.caucho.hessian.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

import com.caucho.hessian.io.HessianRemoteObject;

public class MyHessianProxyFactory extends HessianProxyFactory {

	@Override
	  public Object create(Class api, String urlName, ClassLoader loader) throws MalformedURLException{
		if (api == null){
			throw new NullPointerException("api must not be null for HessianProxyFactory.create()");   
		}
		URL url = new URL(urlName);
		
		InvocationHandler handler = null;
		handler = new MyHessianProxy(url, this, api);   

		return Proxy.newProxyInstance(loader, new Class[] { api, HessianRemoteObject.class }, handler);  

	}
}
