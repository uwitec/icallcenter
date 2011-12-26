package com.caucho.hessian.client;

import java.net.URL;

public class MyHessianProxy extends HessianProxy {

	public  MyHessianProxy(URL url, HessianProxyFactory factory, Class<?> type) {
		super(url, factory, type);
		// TODO Auto-generated constructor stub
	}
	
	/** Add cookie list to request headers */
	@Override
	protected void addRequestHeaders(HessianConnection conn){
		 super.addRequestHeaders(conn);
		 if(conn instanceof CookieHessianURLConnection) {   
			 CookieHessianURLConnection connection = (CookieHessianURLConnection)conn;
			 connection.addRequestHeaders();
		 }
	}
}
