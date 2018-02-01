package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.RegistShopManger;
import com.bit.bonusPointsExchange.utils.Encode;

public class RegistShopServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//��ȡ�û�����д����Ϣ
		//1.��ȡ�̼�����
		String shopName = (String)request.getParameter("shopName");
		//System.out.println(shopName);
		//2.��ȡͼ���ַ�������
		//3.��ȡ���룬����MD5����
		String password = (String)request.getParameter("repassword");
		String MD5password = Encode.MD5Encode(password);//����֮�������
		//System.out.println(MD5password);
		//4.��ȡemail
		
		String email = (String)request.getParameter("email");
		//System.out.println(email);
		//5.��ȡ�̼ұ����š������(����ж��Ƿ���ʵ)
		String number = (String)request.getParameter("number");
		//6.����Shop���󣬲������ݿ�
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setImgUrl("defaultIcon.jpg");//����ͼ���ַ(��һ��Ĭ�ϵ�ͼ��)
		shop.setPassword(MD5password);
		shop.setEmail(email);
		shop.setNumber(number);
		//�������ݿ�
		RegistShopManger registShopManger = new RegistShopManger();
		int res = registShopManger.insertShop(shop);
		if (res != 0) {
			request.setAttribute("registRes", "Y");//ע��ɹ�����ת����¼ҳ�����ע�ᣬͬʱ��ʾ�û�ע��ɹ�
			request.getRequestDispatcher("login_shop.jsp").forward(request, response);
		} else {
			out.print("<script language='JavaScript'>alert('ע��ʧ�ܣ������½���ע�ᣡ');location.href='/bonusPointsExchange/regist_shop.jsp';</script>");
		}
	}
}
