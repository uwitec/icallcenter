package com.callcenter.infrastructure;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class EhcacheCacheServiceImpl implements CacheService {

	private Cache cache;
	
	@Override
	public Object get(Object key) {
		Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
	}

	@Override
	public void put(Object key, Object value) {
		 cache.put(new Element(key, value));
	}

	@Override
	public void put(Object key, Object value, int second) {
		Element element = new Element(key,value);
        element.setTimeToLive(second);
        cache.put(element);
	}

	@Override
	public void remove(Object key) {
		 cache.remove(key);
	}

	@Override
	public List<Object> getKeys() {
		return cache.getKeys();
	}

}
