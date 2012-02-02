package com.callcenter.taxi.client;

public class Rectangle implements java.io.Serializable{

	private double north;
	private double south;
	private double east;
	private double west;
	public double getNorth() {
		return north;
	}
	public void setNorth(double north) {
		this.north = north;
	}
	public double getSouth() {
		return south;
	}
	public void setSouth(double south) {
		this.south = south;
	}
	public double getEast() {
		return east;
	}
	public void setEast(double east) {
		this.east = east;
	}
	public double getWest() {
		return west;
	}
	public void setWest(double west) {
		this.west = west;
	}
	public Rectangle(double north, double south, double east, double west) {
		super();
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}
	
	
}
