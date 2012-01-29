package com.callcenter.taxi.service;

import java.util.HashMap;
import java.util.Map;

import com.callcenter.domain.repository.MovingObjectRepository;
import com.callcenter.domain.repository.MovingObjectRepositoryImpl;
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
	
	public void init(){
		movingObjectCache = new EhcacheCacheServiceImpl("movingObjectCache");
		beanInstances.put("movingObjectCache", movingObjectCache);
		
		movingObjectRepository = new MovingObjectRepositoryImpl();
		beanInstances.put("movingObjectRepository", movingObjectRepository);
		((MovingObjectRepositoryImpl)movingObjectRepository).setMovingObjectCache(movingObjectCache);
	}
	
	public Object getBean(String beanName){
		return beanInstances.get(beanName);
	}
}
