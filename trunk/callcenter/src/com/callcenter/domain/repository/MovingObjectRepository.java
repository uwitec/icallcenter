package com.callcenter.domain.repository;

import java.util.List;

import com.callcenter.domain.entity.Area;
import com.callcenter.domain.entity.LatLonBox;
import com.callcenter.domain.entity.MovingObject;
import com.callcenter.domain.entity.Passenger;

public interface MovingObjectRepository {

	public MovingObject findMovingObject(String uin, String password);
	public void updatePosition(String uin, double longitude, double latitude);
	public MovingObject getCachedMovingObject(String uin);
	public List<Passenger> findPassengers(Area area);
	public List<Passenger> findPassengers(LatLonBox box);
}
