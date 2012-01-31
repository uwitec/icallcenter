package com.callcenter.taxi.test;

import com.callcenter.taxi.client.CallTaxiProxy;

public class LoginTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	CallTaxiProxy.setServerURL("http://dinkysun.kf0309.3g.qq.com/callcenter/taxi");
		System.out.println(CallTaxiProxy.CLIENT.login("sunwei", "sunwei"));
	}

}
