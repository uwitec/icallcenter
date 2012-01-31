package com.callcenter.taxi.test;

import java.io.Serializable;
import java.util.List;

import com.callcenter.taxi.client.Rectangle;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cacheName = "movingObjectCache";  
		CacheManager manager = new CacheManager("src/ehcache.xml");  
		Cache cache = manager.getCache(cacheName);   
		Element element = new Element("key1", "value1");  
		cache.put(element);   
		cache.put(new Element("key1", "value2"));
		element = cache.get("key1");
		Serializable value = element.getValue();
		System.out.println(value);
		
		List<Object> l = cache.getKeys();
		for(Object s  :l){
			System.out.println(s);
		}
		
		Rectangle r = new Rectangle(1,1,1,1);
		r.setX1(1233);
		element = new Element("key11", r);  
		cache.put(element);   

		r.setX1(5454);
		element = cache.get("key11");
		value = element.getValue();
		System.out.println(((Rectangle)value).getX1());
	}

}
