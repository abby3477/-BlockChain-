package com.bit.bonusPointsExchange.bean;

public class ShopPointChange {
	private int inNum;//�������
	private int outNum;//��������
	private String time;//��Ӧ��ʱ��
	public ShopPointChange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShopPointChange(int inNum, int outNum, String time) {
		super();
		this.inNum = inNum;
		this.outNum = outNum;
		this.time = time;
	}
	public int getInNum() {
		return inNum;
	}
	public void setInNum(int inNum) {
		this.inNum = inNum;
	}
	public int getOutNum() {
		return outNum;
	}
	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
