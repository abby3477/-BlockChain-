package com.bit.bonusPointsExchange.bean;
//�û������ʾ�Ѱ���Ϣ����ѯ������Ϣ�����Ϣ
public class ShowBindInfo {
	private String shopName;//�̼�����
	private String imgURL;//�̼�ͷ���ַ
	private String platformPoints;//�û�������ƽ̨�Ļ���
	public String getShopName() {
		return shopName;
	}
	public ShowBindInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getPlatformPoints() {
		return platformPoints;
	}
	public void setPlatformPoints(String platformPoints) {
		this.platformPoints = platformPoints;
	}
	public ShowBindInfo(String shopName, String imgURL, String platformPoints) {
		super();
		this.shopName = shopName;
		this.imgURL = imgURL;
		this.platformPoints = platformPoints;
	}
	
	
}
