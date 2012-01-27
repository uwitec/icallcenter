package com.callcenter.domain.entity;

public class Driver{
	private String SSNO;
	private String name;
	private int gender;
	private int level;
	public String getSSNO() {
		return SSNO;
	}
	public void setSSNO(String sSNO) {
		SSNO = sSNO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
}
