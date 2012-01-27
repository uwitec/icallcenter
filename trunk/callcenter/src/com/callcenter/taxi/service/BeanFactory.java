package com.callcenter.taxi.service;

import java.util.HashMap;
import java.util.Map;

import com.callcenter.domain.repository.MovingObjectRepository;
import com.callcenter.domain.repository.MovingObjectRepositoryImpl;
import com.callcenter.domain.service.PositionService;
import com.callcenter.domain.service.PositionServiceImpl;
import com.callcenter.infrastructure.CacheService;
import com.callcenter.infrastructure.EhcacheCacheServiceImpl;

public class BeanFactory {

	private static BeanFactory theInstance = new BeanFactory();
	
	private Map<String, Object> beanInstances  = new HashMap<String, Object>();
	
	private BeanFactory(){}
	
	public static BeanFactory instance(){
		return theInstance;
	}
	
	
	private MovingObjectRepository movingObjectRepository;
	
	private CacheService movingObjectCache;
	
	private PositionService positionService;
	
	public void init(){
		movingObjectCache = new EhcacheCacheServiceImpl("movingObjectCache");
		beanInstances.put("movingObjectCache", movingObjectCache);
		
		positionService = new PositionServiceImpl();
		beanInstances.put("positionService", positionService);
		((PositionServiceImpl)positionService).setMovingObjectCache(movingObjectCache);
		
		movingObjectRepository = new MovingObjectRepositoryImpl();
		beanInstances.put("movingObjectRepository", movingObjectRepository);
	}
	
	public Object getBean(String beanName){
		return beanInstances.get(beanName);
	}
}
