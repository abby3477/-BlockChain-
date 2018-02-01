package com.bit.bonusPointsExchange.bean;

public class Transfer {
	private int pointID;
	private int status;
	private int point;
	private String transferTime;
	private String shopName;
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public Transfer(int pointID, int status, int point,String transferTime,String shopName) {
		super();
		this.pointID = pointID;
		this.status = status;
		this.point = point;
		this.transferTime = transferTime;
		this.shopName = shopName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getPointID() {
		return pointID;
	}
	public void setPointID(int pointID) {
		this.pointID = pointID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	
}
