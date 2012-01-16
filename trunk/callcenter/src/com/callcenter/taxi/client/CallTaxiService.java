package com.callcenter.taxi.client;

import java.util.List;

public interface CallTaxiService {

	public int echo(String userID);
	public int login(String userID, String password);
	public int logout();
	public List<Passenger>  queryPassengers( int color ,  Rectangle  rect );
}
