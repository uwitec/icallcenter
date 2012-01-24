package com.callcenter.infrastructure;

import java.util.List;

public interface CacheService {

	public Object get(String key);
	public void put(String key,Object value);
	
	/**
     * 可指定存活时间的缓存接口
     * @param key
     * @param value
     * @param ttlSeconds
     */
	public void put(String key, Object value, int ttlSeconds);
	
	public void remove(String key);
	
	public List<String> getKeys();
}
