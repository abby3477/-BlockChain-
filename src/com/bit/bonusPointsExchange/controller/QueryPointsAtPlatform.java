package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;

public class QueryPointsAtPlatform extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����û�ѡ���ѯ��pingtai���ݿ����û��ж��ٻ���
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//ͨ��session��ȡ�û���
		String userName =(String)request.getSession().getAttribute("userName");	
		//��ȡ�û��ύ�������Ϣ
		String shopName = request.getParameter("shop");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		//System.out.println(shopName);
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		int points = dbManger.ownPointsAtPlatform(userName, shopName);//�û�ӵ�еĻ���
		OrderManager om = new OrderManager();
		List<Order> orders = om.findOrderByUserShopName(userName, shopName);//��ѯ�û���ĳ�̼ҷ��������ж���
		int orderPointSUM=0;
		for(int i=0;i<orders.size();i++){
			orderPointSUM+=orders.get(i).getPoint();
		}
		points=points-orderPointSUM;
		out.print(String.valueOf(points));
		
	}

}
