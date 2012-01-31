package com.callcenter.domain.entity;

public class MovingObject {
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
	
	public void updatePosition(double longitude, double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public boolean isInTheArea(Area area){
		return false;
	}
}
