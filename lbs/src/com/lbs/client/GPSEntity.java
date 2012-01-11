package com.lbs.client;

public class GPSEntity implements java.io.Serializable{
	
	private double x;//����
	private double y;//γ��
	private double h;//�߶�
	private double s;//�ٶ�
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getS() {
		return s;
	}
	public void setS(double s) {
		this.s = s;
	}
}
