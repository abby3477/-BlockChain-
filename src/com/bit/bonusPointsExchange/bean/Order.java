package com.bit.bonusPointsExchange.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * ����
 * @author gmx
 */

public class Order {
	
	private int orderID;//������
	private int point;//���׻���
	private String userName;//���������̼���
	private String shopName;//���������û���
	private String wantedShop;//�����һ��̼���
	private int wantedPoint;//�����һ�����
	private String untilDate;//������Ч��
	private String exchangeUserName;//���ֶһ��û���
	private String orderDate;//�������ʱ��
	private int orderStatus;//����״̬
	private String shopLogo;//�̼�ͼ��
	private String wantedShopLogo;//Ŀ���̼�ͼ��
	

	
	public String getShopLogo() {
		return shopLogo;
	}
	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}
	public String getWantedShopLogo() {
		return wantedShopLogo;
	}
	public void setWantedShopLogo(String wantedShopLogo) {
		this.wantedShopLogo = wantedShopLogo;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public String getWantedShop() {
		return wantedShop;
	}
	public void setWantedShop(String wantedShop) {
		this.wantedShop = wantedShop;
	}
	public int getWantedPoint() {
		return wantedPoint;
	}
	public void setWantedPoint(int wantedPoint) {
		this.wantedPoint = wantedPoint;
	}
	public String getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(String untilDate) {
		this.untilDate = untilDate;
	}
	public String getExchangeUserName() {
		return exchangeUserName;
	}
	public void setExchangeUserName(String exchangeUserName) {
		this.exchangeUserName = exchangeUserName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	

	
	
}

