package com.callcenter.domain.factory;

import com.callcenter.domain.entity.MovingObject;

public interface MovingObjectFactory {

	/**
	 * 
	 * @param type 1:passenger  2:taxi  3:bus
	 * @return
	 */
	public MovingObject createMovingObject(int type);
}
