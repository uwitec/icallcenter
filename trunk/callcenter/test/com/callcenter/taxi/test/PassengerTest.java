package com.callcenter.taxi.test;

import java.util.List;

import com.callcenter.taxi.client.CallTaxiProxy;
import com.callcenter.taxi.client.Passenger;
import com.callcenter.taxi.client.Rectangle;

public class PassengerTest {

	public static void main(String[] args){
		CallTaxiProxy.setServerURL("http://dinkysun.kf0309.3g.qq.com/callcenter/taxi");
		List<Passenger> l = CallTaxiProxy.CLIENT.queryPassengers(0, new Rectangle());
		System.out.println(l.size());
		for(Passenger p: l){
			System.out.println(p.getPhoneNo());
		}
	}
}
