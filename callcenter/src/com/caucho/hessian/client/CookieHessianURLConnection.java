package com.caucho.hessian.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**  
*   
*   
* @author liliang  
* @descirption 为维持session而扩展的支持cookie的连接  
*/
public class CookieHessianURLConnection extends HessianURLConnection {

	/**  
	 * @description 共享cookie  
	*/  
	private static List<String> cookies = null;   

	CookieHessianURLConnection(URL url, URLConnection conn) {
		super(url, conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void parseResponseHeaders(HttpURLConnection conn) throws IOException{
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
	
	public void addRequestHeaders() {
		if (cookies != null) {
			for (String cookieString : cookies) { 
				addHeader("Cookie", cookieString);
			}
		}
	}
}
