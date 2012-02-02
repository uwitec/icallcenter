package com.callcenter.taxi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.callcenter.taxi.service.BeanFactory;

public class GlobalListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		BeanFactory.instance().destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		BeanFactory.instance().init();
	}

}
