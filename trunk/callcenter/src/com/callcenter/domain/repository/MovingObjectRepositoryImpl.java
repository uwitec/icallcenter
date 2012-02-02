package com.callcenter.domain.repository;

import java.util.ArrayList;
import java.util.List;

import com.callcenter.domain.entity.Area;
import com.callcenter.domain.entity.LatLonBox;
import com.callcenter.domain.entity.MovingObject;
import com.callcenter.domain.entity.Passenger;
import com.callcenter.infrastructure.CacheService;

public class MovingObjectRepositoryImpl implements MovingObjectRepository {

	private CacheService movingObjectCache;
	
	public void setMovingObjectCache(CacheService movingObjectCache) {
		this.movingObjectCache = movingObjectCache;
	}
	
	@Override
	public MovingObject findMovingObject(String uin, String password) {
		MovingObject mo = (MovingObject) movingObjectCache.get(uin);
		if(mo != null) return mo;
		
		//get moving object from db
		Passenger p = new Passenger();
		p.setUin(uin);
		mo = p;
		if(mo == null) return null;
		
		//set moving into cache
		movingObjectCache.put(uin, mo);
		return mo;
	}

	@Override
	public void updatePosition(String uin, double longitude, double latitude) {
		MovingObject mo = (MovingObject) movingObjectCache.get(uin);
		if(mo == null) return;
		
		mo.updatePosition(longitude, latitude);
	//	movingObjectCache.put(uin, mo);
		
	}

	@Override
	public MovingObject getCachedMovingObject(String uin) {
		MovingObject mo = (MovingObject) movingObjectCache.get(uin);
		return mo;
	}

	@Override
	public List<Passenger> findPassengers(LatLonBox box) {
		List<Passenger>  result = new ArrayList<Passenger>();
		List<String> uins = movingObjectCache.getKeys();
		for(String uin : uins){
			MovingObject mo = (MovingObject) movingObjectCache.get(uin);
			if( mo instanceof Passenger){
				if( mo.isInTheBox(box))
					result.add((Passenger)mo);
			}
		}
		return result;
	}

}
