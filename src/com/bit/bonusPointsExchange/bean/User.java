package com.bit.bonusPointsExchange.bean;

/**
 * �û�
 * @author gmx
 *
 */
public class User {
	
	private int userId;//���
	private String userName;//�˺�
	private String passwd;//����
	private String email;//����
	private String fullName;//����
	private String phone;//�绰
	private boolean activated;//�Ƿ񼤻�
	private String randomCode;//����루�����˻�������������������ʱʹ�ã�
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean getActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public String getRandomCode() {
		return randomCode;
	}
	public void setRandomCode(String randomCode) {
		this.randomCode = randomCode;
	}
	
	
	
	
}
