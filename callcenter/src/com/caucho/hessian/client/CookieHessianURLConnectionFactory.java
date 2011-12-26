package com.caucho.hessian.client;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieHessianURLConnectionFactory extends AbstractHessianConnectionFactory{

	private static final Logger log = Logger.getLogger(CookieHessianURLConnectionFactory.class.getName());   

	@Override
	public HessianConnection open(URL url) throws IOException {
		if (log.isLoggable(Level.FINER)){
			log.finer(this + " open(" + url + ")");   
		}
		
		URLConnection conn = url.openConnection(); 
		long connectTimeout = getHessianProxyFactory().getConnectTimeout();
		
		if (connectTimeout >= 0)   
			 conn.setConnectTimeout((int) connectTimeout);   

		conn.setDoOutput(true);   

		long readTimeout = getHessianProxyFactory().getReadTimeout();   

		
		if (readTimeout > 0) {   
			           try {   
		                conn.setReadTimeout((int) readTimeout);   
		           } catch (Throwable e) {   
	         }   
	     }   

		
		return new CookieHessianURLConnection(url, conn);
	}

}
