package com.callcenter.taxi.client;

public class Passenger extends MovingObjects{
	 private String    userid;
	 private int   age;
	 private String   gender;//0:mail  1:fmail
	 private String   phoneNo;
	 private String    SSNo;//身份证
	 private String    headPicUrl;//头像
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getSSNo() {
		return SSNo;
	}
	public void setSSNo(String sSNo) {
		SSNo = sSNo;
	}
	public String getHeadPicUrl() {
		return headPicUrl;
	}
	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}
}
