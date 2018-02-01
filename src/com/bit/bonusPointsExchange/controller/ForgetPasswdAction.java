package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.bean.User;
import com.bit.bonusPointsExchange.manager.ShopManager;
import com.bit.bonusPointsExchange.manager.UserManager;
import com.bit.bonusPointsExchange.utils.EmailUtils;

/**
 * ��������
 * @author gmx
 *
 */
public class ForgetPasswdAction extends Action{
	
	

	/*
	public void findPasswd(){
		Properties props = new Properties();//��������������Ϣ�����ö���
		props.setProperty("mail.transport.protocol", "smtp");//����������һ̨������
		props.setProperty("mail.smtp.auth", "true");//�����Ƿ���֤
		Session session = Session.getInstance(props);//��ȡsession����
		session.setDebug(true);//���õ���ģʽ
		MimeMessage msg = new MimeMessage(session);//������Ϣ��������Message��ʵ����
		try {
			msg.setFrom(new InternetAddress(""));//���÷�����
			msg.setRecipient(RecipientType.TO, new InternetAddress(""));//�����ռ���
			msg.setRecipient(RecipientType.CC, new InternetAddress("kdyzm@foxmail.com"));//���ó���
	        msg.setRecipient(RecipientType.BCC, new InternetAddress("kdyzm@sina.cn"));//���ð���
	        msg.setSubject("������java���͵��ʼ�")//��������
	        msg.setContent("������java���͵��ʼ�������","text/plain;charset=utf-8");//�����ʼ�����
	        Transport.send(msg);
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)  {
		// TODO Auto-generated method stub
		
		String name = (String)request.getParameter("methodCode");
		if(name.equals("forgetPasswd_user")){
			this.forgetPasswdByUser(request,response);
		}else if(name.equals("forgetPasswd_shop")){
			this.forgetPasswdByShop(request,response);
		}
		
    }  
		
		
	public void forgetPasswdByUser(HttpServletRequest request, HttpServletResponse response) {
		User user = new User();
		UserManager um = new UserManager();
		String userName  = request.getParameter("userName");
		String email = request.getParameter("email");
		user.setUserName(userName);
		user.setEmail(email);
		int result = um.queryUserByNameAndEmail(user);
			
		if(result==0){
			try {
				request.setAttribute("errorMsg", "��������˺Ż����䲻���ڣ�");  
				request.getRequestDispatcher("/retrievePassword_1.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
		}else{
			// ���������������������  
			EmailUtils.sendResetPasswordEmail(user);  
          
			request.setAttribute("sendMailMsg", "�����������ύ�ɹ�����鿴����"+user.getEmail()+"���䡣");         
			try {
				request.getRequestDispatcher("forgetPasswdSuccess.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	}
	public void forgetPasswdByShop(HttpServletRequest request, HttpServletResponse response) {
		Shop shop = new Shop();
		ShopManager sm = new ShopManager();
		String shopName  = request.getParameter("userName");
		String email = request.getParameter("email");
		shop.setShopName(shopName);
		shop.setEmail(email);
		int result = sm.queryShopByNameAndEmail(shop);
	
		if(result==0){
			request.setAttribute("errorMsg", "��������˺Ż����䲻���ڣ�");  
			try {
				request.getRequestDispatcher("/retrievePassword_1.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

       
		}else{
			// ���������������������  
			EmailUtils.sendResetPasswordEmail(shop);  
          
			request.setAttribute("sendMailMsg", "�����������ύ�ɹ�����鿴����"+shop.getEmail()+"���䡣");         
			try {
				request.getRequestDispatcher("forgetPasswdSuccess.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
}
