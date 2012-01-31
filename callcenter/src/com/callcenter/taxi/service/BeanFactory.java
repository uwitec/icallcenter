package com.callcenter.taxi.service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import com.callcenter.domain.repository.MovingObjectRepository;
import com.callcenter.domain.repository.MovingObjectRepositoryImpl;
import com.callcenter.infrastructure.CacheService;
import com.callcenter.infrastructure.EhcacheCacheServiceImpl;

public class BeanFactory {

	private static final String path = "/ehcache.xml";
	
	private static BeanFactory theInstance = new BeanFactory();
	
	private Map<String, Object> beanInstances  = new HashMap<String, Object>();
	
	private BeanFactory(){}
	
	public static BeanFactory instance(){
		return theInstance;
	}
	
	
	private MovingObjectRepository movingObjectRepository;
	
	private CacheService movingObjectCache;
	
	public void init(){
		initCacheService();
		
		initRepository();
	}

	private void initRepository() {
		movingObjectRepository = new MovingObjectRepositoryImpl();
		beanInstances.put("movingObjectRepository", movingObjectRepository);
		((MovingObjectRepositoryImpl)movingObjectRepository).setMovingObjectCache(movingObjectCache);
	}
	
	private void initCacheService(){
		URL url = getClass().getResource(path);
		CacheManager manager = CacheManager.create(url);  
		movingObjectCache = new EhcacheCacheServiceImpl(manager,"movingObjectCache");
		beanInstances.put("movingObjectCache", movingObjectCache);
	}
	
	public void destroy() {
		CacheManager.create().shutdown();
	}
	
	public Object getBean(String beanName){
		return beanInstances.get(beanName);
	}
}
