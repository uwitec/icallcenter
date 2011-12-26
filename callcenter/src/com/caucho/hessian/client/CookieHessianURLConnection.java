package com.caucho.hessian.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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
		List<String> _cookies = conn.getHeaderFields().get("Set-Cookie");
		if (_cookies != null) cookies = _cookies;
	}
	
	public void addRequestHeaders() {
		if (cookies != null) {
			for (String cookieString : cookies) { 
				addHeader("Cookie", cookieString);
			}
		}
	}
}
