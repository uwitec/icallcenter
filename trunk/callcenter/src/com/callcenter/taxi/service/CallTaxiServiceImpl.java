package com.callcenter.taxi.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.callcenter.domain.entity.MovingObject;
import com.callcenter.domain.repository.MovingObjectRepository;
import com.callcenter.domain.service.PositionService;
import com.callcenter.infrastructure.CacheService;
import com.callcenter.taxi.client.CallTaxiService;
import com.callcenter.taxi.client.Passenger;
import com.callcenter.taxi.client.Rectangle;
import com.caucho.hessian.server.HessianServlet;
import com.caucho.services.server.ServiceContext;

public class CallTaxiServiceImpl extends HessianServlet implements CallTaxiService{

	private MovingObjectRepository movingObjectRepository;
	
	private CacheService movingObjectCache;
	
	private PositionService positionService;
	
	@Override
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		BeanFactory.instance().init();
		
		movingObjectRepository = (MovingObjectRepository)BeanFactory.instance().getBean("movingObjectRepository");
		movingObjectCache = (CacheService)BeanFactory.instance().getBean("movingObjectCache");
		positionService = (PositionService)BeanFactory.instance().getBean("positionService");
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
		MovingObject mo = movingObjectRepository.findMovingObject(userID, password);
		if(mo == null) return 1;
		
		movingObjectCache.put(userID, mo);
		setUINIntoSession(userID);
		
		return 0;
	}

	@Override
	public int logout() {
		String uin;
		try {
			uin = getUINFromSession();
		} catch (AuthenticationException e) {
			return 1;
		}
		
		removeUINFromSession();
		movingObjectCache.remove(uin);
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
		String uin;
		try {
			uin = getUINFromSession();
		} catch (AuthenticationException e) {
			return ;
		}
		
		positionService.updatePosition(uin, longitude, latitude);
		
	}

	private void setUINIntoSession(String userID) {
		HttpServletRequest req = (HttpServletRequest) ServiceContext.getContextRequest();
		req.getSession().setAttribute("UIN", userID);
	}

	/**
	 * 
	 * @return
	 * @throws AuthenticationException 用户未登录
	 */
	private String getUINFromSession() throws AuthenticationException{
		HttpServletRequest req = (HttpServletRequest) ServiceContext.getContextRequest();
		String uin = (String)req.getSession().getAttribute("UIN");
		if( uin == null) throw new AuthenticationException();
		return uin;
	}
	
	private void removeUINFromSession(){
		HttpServletRequest req = (HttpServletRequest) ServiceContext.getContextRequest();
		req.getSession().removeAttribute("UIN");
	}

}
