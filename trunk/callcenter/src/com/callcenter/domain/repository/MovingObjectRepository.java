package com.callcenter.domain.repository;

import com.callcenter.domain.entity.MovingObject;

public interface MovingObjectRepository {

	public MovingObject findMovingObject(String uin, String password);
}
