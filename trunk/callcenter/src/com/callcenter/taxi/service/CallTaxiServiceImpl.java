package com.callcenter.taxi.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.callcenter.taxi.client.CallTaxiService;
import com.callcenter.taxi.client.Passenger;
import com.callcenter.taxi.client.Rectangle;
import com.caucho.hessian.server.HessianServlet;
import com.caucho.services.server.ServiceContext;

public class CallTaxiServiceImpl extends HessianServlet implements CallTaxiService{

	@Override
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
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

	@Override
	public int login(String userID, String password) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int logout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Passenger> queryPassengers(int color, Rectangle rect) {
		List<Passenger> passengers = new ArrayList<Passenger>();
		Passenger passenger =  new Passenger();
		passenger.setPhoneNo("45466");
		passengers.add(passenger);
		return passengers;
	}

	@Override
	public void reportPosition(double longitude, double latitude) {
		// TODO Auto-generated method stub
		
	}

}
