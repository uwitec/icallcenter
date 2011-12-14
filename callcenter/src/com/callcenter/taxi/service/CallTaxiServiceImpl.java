package com.callcenter.taxi.service;

import javax.servlet.http.HttpServletRequest;

import com.callcenter.taxi.client.CallTaxiService;
import com.caucho.hessian.server.HessianServlet;
import com.caucho.services.server.ServiceContext;

public class CallTaxiServiceImpl extends HessianServlet implements CallTaxiService{

	@Override
	public int echo(String userID) {
		
		HttpServletRequest req = (HttpServletRequest) ServiceContext.getContextRequest();
		if(req.getSession().getAttribute(userID) == null){
			req.getSession().setAttribute(userID, userID);
		}else{
			return 0;
		}
		return userID.hashCode();
	}

}
