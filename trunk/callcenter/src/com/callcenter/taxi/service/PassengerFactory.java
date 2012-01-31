package com.callcenter.taxi.service;

import java.util.ArrayList;
import java.util.List;

import com.callcenter.taxi.client.Passenger;

public class PassengerFactory {

	public static Passenger create(com.callcenter.domain.entity.Passenger p){
		Passenger result = new Passenger();
		result.setUserid(p.getUin());
		return result;
	}
	
	
	public static List<Passenger> create(List<com.callcenter.domain.entity.Passenger> ps){
		List<Passenger> result = new ArrayList<Passenger>();
		for( com.callcenter.domain.entity.Passenger p : ps){
			result.add(create(p));
		}
		return result;
	}
}
