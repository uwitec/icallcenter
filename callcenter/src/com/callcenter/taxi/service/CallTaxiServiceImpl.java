package com.callcenter.taxi.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.callcenter.domain.entity.Area;
import com.callcenter.domain.entity.LatLonBox;
import com.callcenter.domain.entity.MovingObject;
import com.callcenter.domain.repository.MovingObjectRepository;
import com.callcenter.infrastructure.CacheService;
import com.callcenter.taxi.client.CallTaxiService;
import com.callcenter.taxi.client.Passenger;
import com.callcenter.taxi.client.Rectangle;
import com.caucho.hessian.server.HessianServlet;
import com.caucho.services.server.ServiceContext;

public class CallTaxiServiceImpl extends HessianServlet implements CallTaxiService{

	private MovingObjectRepository movingObjectRepository;
	
	private CacheService movingObjectCache;
	
	@Override
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		BeanFactory.instance().init();
		
		movingObjectRepository = (MovingObjectRepository)BeanFactory.instance().getBean("movingObjectRepository");
		movingObjectCache = (CacheService)BeanFactory.instance().getBean("movingObjectCache");

	}
	
	@Override
	public void destroy() {
		super.destroy();
		BeanFactory.instance().destroy();
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
		String uin;
		try {
			uin = getUINFromSession();
		} catch (AuthenticationException e) {
			return null;
		}
		
		List<com.callcenter.domain.entity.Passenger> passengers = movingObjectRepository.findPassengers(new LatLonBox(rect.getNorth(), rect.getSouth(), rect.getEast(), rect.getWest()));
		return PassengerFactory.create(passengers);
	}

	@Override
	public void reportPosition(double longitude, double latitude) {
		String uin;
		try {
			uin = getUINFromSession();
		} catch (AuthenticationException e) {
			return ;
		}
		
		movingObjectRepository.updatePosition(uin, longitude, latitude);
		
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
