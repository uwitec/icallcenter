package com.callcenter.domain.entity;

public class MovingObject {
	String uin;
	// absolutedSpeed and directions compose speed vector
	private double absolutedSpeed; // km/h
	private double directions; // the angle relevent to the north pole;
	private double longitude;
	private double latitude;
	public double getAbsolutedSpeed() {
		return absolutedSpeed;
	}
	public void setAbsolutedSpeed(double absolutedSpeed) {
		this.absolutedSpeed = absolutedSpeed;
	}
	public double getDirections() {
		return directions;
	}
	public void setDirections(double directions) {
		this.directions = directions;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public String getUin() {
		return uin;
	}
	public void setUin(String uin) {
		this.uin = uin;
	}
	public void updatePosition(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public boolean isInTheArea(Area area){
		if(longitude <area.getX2() && longitude >area.getX1() && latitude < area.getY1() && latitude > area.getY2())
			return true;
		return false;
	}
}
