package com.callcenter.taxi.test;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cacheName = "sampleCache1";  
		CacheManager manager = new CacheManager("src/ehcache.xml");  
		Cache cache = manager.getCache(cacheName);   
		Element element = new Element("key1", "value1");  
		cache.put(element);   
		cache.put(new Element("key1", "value2"));
		element = cache.get("key1");
		Serializable value = element.getValue();
		System.out.println(value);

	}

}
