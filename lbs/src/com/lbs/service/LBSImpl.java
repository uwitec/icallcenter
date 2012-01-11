package com.lbs.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.caucho.hessian.server.HessianServlet;
import com.lbs.client.GPSEntity;
import com.lbs.client.LBS;

public class LBSImpl extends HessianServlet implements LBS {

	@Override
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		try {
			MarsWgs.instance().init();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public GPSEntity rectify(double x, double y) {
		return MarsWgs.instance().rectify(x, y);
	}

}
