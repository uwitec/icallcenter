package com.lbs.test;

import com.lbs.client.GPSEntity;
import com.lbs.client.LBSProxy;

public class LBSTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LBSProxy.setServerURL("http://dinkysun.kf0309.3g.qq.com/lbs/lbs");
		GPSEntity g = LBSProxy.CLIENT.rectify(100.7, 20.5);
		System.out.println(g.getX() + "|" + g.getY());
	}

}
