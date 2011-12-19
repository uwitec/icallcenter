package com.callcenter.taxi.client;

public interface CallTaxiService {

	public int echo(String userID);
	public int login(String userID, String password);
	public int logout();
}
