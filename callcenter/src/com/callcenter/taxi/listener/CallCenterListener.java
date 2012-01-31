package com.callcenter.taxi.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.callcenter.infrastructure.CacheService;
import com.callcenter.taxi.service.BeanFactory;

public class CallCenterListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		String uin = (String)session.getAttribute("UIN");

		CacheService movingObjectCache = (CacheService)BeanFactory.instance().getBean("movingObjectCache");
		movingObjectCache.remove(uin);
	}

}
