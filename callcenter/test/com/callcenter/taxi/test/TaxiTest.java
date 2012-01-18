package com.callcenter.taxi.test;

import com.callcenter.taxi.client.CallTaxiProxy;

public class TaxiTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CallTaxiProxy.setServerURL("http://dinkysun.kf0309.3g.qq.com/callcenter/taxi");
		for(int i=0; i<100; i++){
		System.out.println("hi:" +CallTaxiProxy.CLIENT.echo("sw"));
		System.out.println("hi:" +CallTaxiProxy.CLIENT.echo("sw"));
		}
	}

}
