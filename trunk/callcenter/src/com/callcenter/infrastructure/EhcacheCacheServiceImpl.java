package com.callcenter.infrastructure;

import java.net.URL;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheCacheServiceImpl implements CacheService {
	 
	private Cache cache;
	
	public EhcacheCacheServiceImpl(CacheManager manager, String cacheName){
		cache = manager.getCache(cacheName);
	}
	
	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@Override
	public Object get(String key) {
		Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
	}

	@Override
	public void put(String key, Object value) {
		 cache.put(new Element(key, value));
	}

	@Override
	public void put(String key, Object value, int ttlSeconds) {
		Element element = new Element(key,value);
        element.setTimeToLive(ttlSeconds);
        cache.put(element);
	}

	@Override
	public void remove(String key) {
		 cache.remove(key);
	}

	@Override
	public List<String> getKeys() {
		return cache.getKeys();
	}

}
