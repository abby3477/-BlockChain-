package com.bit.bonusPointsExchange.bean;
//һ�����ڵ��û���
public class UserAmountAweek {
	private int num;//�û���
	private String time;//��Ӧ��ʱ��
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
