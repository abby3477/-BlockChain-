package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;

public class BindShopQueryInfoServlet extends HttpServlet {

//�û��ڰ��̼ҵ�ʱ���ѯ�̼������Ϣ
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//1.��ȡ�û������Ҫ�������̼ҵ�����
		String shopName = request.getParameter("search");
		LoginShopManger loginShopManger = new LoginShopManger();
		//��ȡ�̼ҵ���ϸ��Ϣ����ѯshop��
		Shop shop = loginShopManger.getShopInfo(shopName);
		//��������
		request.setAttribute("shopDec", shop.getShopDec());
		request.setAttribute("shopName", shop.getShopName());
		request.setAttribute("imgURL", shop.getImgUrl());
		request.setAttribute("index", "5");//������ʾ�ڼ���div
		//ת����ҳ�������ʾ
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
	}

}
