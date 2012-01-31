package com.callcenter.taxi.test;

import java.util.List;

import com.callcenter.taxi.client.CallTaxiProxy;
import com.callcenter.taxi.client.Passenger;
import com.callcenter.taxi.client.Rectangle;

public class ReportPositionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CallTaxiProxy.setServerURL("http://dinkysun.kf0309.3g.qq.com/callcenter/taxi");
		System.out.println(CallTaxiProxy.CLIENT.login("sunwei", "sunwei"));
		CallTaxiProxy.CLIENT.reportPosition(12, 7);
		List<Passenger> l = CallTaxiProxy.CLIENT.queryPassengers(0, new Rectangle(1,100,100,1));
		System.out.println(l.size());
		for(Passenger p: l){
			System.out.println(p.getUserid());
		}
	}

}
