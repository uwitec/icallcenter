package com.callcenter.taxi.client;

import java.util.List;

public interface CallTaxiService {

	public int echo(String userID);
	public int login(String userID, String password);
	public int logout();
	public void reportPosition(double longitude, double latitude);
	public List<Passenger>  queryPassengers( int color ,  Rectangle  rect );
}
