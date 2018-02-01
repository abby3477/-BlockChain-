package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginManager;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.utils.Encode;
//�����¼����
public class ShopLoginServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();	
		//��ȡ�̼���������
		String shopName = (String)request.getParameter("userName");
		//System.out.println(shopName);
		String password = (String)request.getParameter("password");
		//System.out.println(password);
		//�������ݿ�����洢����MD5���ܺ�����룬MD5�ǲ�����ģ��˴����û������������ܺ������ݿ��е�������бȶԣ�
		//���Ƿ�һ��
		String encodePasswordString = Encode.MD5Encode(password);
		//System.out.println(encodePasswordString);
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setPassword(encodePasswordString);
		//��ѯ���ݿ��û����������Ƿ���ȷ
		LoginShopManger loginShopManger = new LoginShopManger();
		int res = loginShopManger.isValid(shop);
		if (res == 1) {//��Ϣ��ȷ,��ת����ҳ�����̼����Ʊ��浽session��
			System.out.println("success");
			HttpSession session = request.getSession();
			session.setAttribute("shopName",shopName);
			//session.setAttribute("userName",shopName);
			//��ѯ���ݿ��е������Ϣ
			Shop resShop = loginShopManger.getShopInfo(shopName);
			System.out.println(resShop.getImgUrl());
			request.setAttribute("email", resShop.getEmail());//���ݸ�personal_shopҳ�������ʾ
			request.setAttribute("imageURL", resShop.getImgUrl());//���ݸ�personal_shopҳ�������ʾ
			
			request.setAttribute("telephone", resShop.getTelephone());//���ݸ�personal_shopҳ�������ʾ
			request.setAttribute("shopDec", resShop.getShopDec());//���ݸ�personal_shopҳ�������ʾ
	
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else {
			//System.out.println("error");
			out.print("<script language='JavaScript'>alert('��¼ʧ�ܣ������µ�¼��');location.href='/bonusPointsExchange/login_shop.jsp';</script>");
		}
	}

}
