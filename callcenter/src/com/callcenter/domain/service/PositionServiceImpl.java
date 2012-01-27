package com.callcenter.domain.service;

import com.callcenter.domain.entity.MovingObject;
import com.callcenter.infrastructure.CacheService;

public class PositionServiceImpl implements PositionService {

	private CacheService movingObjectCache;
	
	public void setMovingObjectCache(CacheService movingObjectCache) {
		this.movingObjectCache = movingObjectCache;
	}

	@Override
	public void updatePosition(String uin, double longitude, double latitude) {
		MovingObject mo = (MovingObject) movingObjectCache.get(uin);
		if(mo == null) return;
		
		mo.setLongitude(longitude);
		mo.setLatitude(latitude);
		movingObjectCache.put(uin, mo);
	}

}
