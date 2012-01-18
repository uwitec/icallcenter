package com.caucho.hessian.client;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class MyHessianProxy extends HessianProxy {

	/**  
	 * @description 共享cookie  
	*/  
	private static List<String> cookies = null;   
	
	public  MyHessianProxy(URL url, HessianProxyFactory factory, Class<?> type) {
		super(factory, url);
	}
	
	/** Add cookie list to request headers */
	@Override
	protected void addRequestHeaders(URLConnection conn) {
		 super.addRequestHeaders(conn);
		 addHeaders(conn);
	}
	
	private void addHeaders(URLConnection conn) {
		if (cookies != null) {
			for (String cookieString : cookies) { 
				addHeader("Cookie", cookieString, conn);
			}
		}
	}
	
	private void addHeader(String key, String value, URLConnection conn){
	    conn.setRequestProperty(key, value);
	}
	
	protected void parseResponseHeaders(URLConnection conn) {
		super.parseResponseHeaders(conn);
		Map<String, List<String>> responseHeaders = conn.getHeaderFields();
		
		// pre-condition check
        if (responseHeaders == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        
        for (String headerKey : responseHeaders.keySet()) {
        	// RFC 2965 3.2.2, key must be 'Set-Cookie2'
            // we also accept 'Set-Cookie' here for backward compatibility
            if (headerKey == null
                || !(headerKey.equalsIgnoreCase("Set-Cookie2")
                     || headerKey.equalsIgnoreCase("Set-Cookie")
                    )
                )
            {
                continue;
            }
            
            List<String> _cookies = responseHeaders.get(headerKey);
    		cookies = _cookies;
        }
		  
	}
}
