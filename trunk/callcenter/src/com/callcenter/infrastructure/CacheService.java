package com.callcenter.infrastructure;

import java.util.List;

public interface CacheService {

	public Object get(Object key);
	public void put(Object key,Object value);
	
	/**
     * 可指定存活时间的缓存接口
     * @param key
     * @param value
     * @param second
     */
	public void put(Object key, Object value, int second);
	
	public void remove(Object key);
	
	public List<Object> getKeys();
}
