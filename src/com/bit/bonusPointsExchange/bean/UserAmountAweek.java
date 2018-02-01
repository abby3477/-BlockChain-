package com.bit.bonusPointsExchange.bean;
//一个星期的用户量
public class UserAmountAweek {
	private int num;//用户量
	private String time;//对应的时间
	public UserAmountAweek(int num, String time) {
		super();
		this.num = num;
		this.time = time;
	}
	public UserAmountAweek() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
