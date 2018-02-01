package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;

public class QueryValidPointSerlvet extends HttpServlet {
	//��ѯ�û���ƽ̨���ݿ��ж�����Ч�Ļ���,���û���ƽ̨�Ļ��ּ�ȥ�û�������δ��ɶ����Ļ��ֺ�

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ѯ�û���ƽ̨���ݿ��ж�����Ч�Ļ���,���û���ƽ̨�Ļ��ּ�ȥ�û�������δ��ɶ����Ļ��ֺ�
				response.setContentType("text/html;charset=utf-8");
				request.setCharacterEncoding("utf-8"); 
				OrderManager om = new OrderManager();
				PrintWriter out = response.getWriter();
				//ͨ��session��ȡ�û���
				String userName =(String)request.getSession().getAttribute("userName");	
				//��ȡ�û��ύ�������Ϣ
				String shopName = request.getParameter("shop");
				shopName = URLDecoder.decode(shopName, "UTF-8");
				//System.out.println(shopName);
				UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
				int points = dbManger.ownPointsAtPlatform(userName, shopName);//�û���ƽ̨ӵ�еĻ���			
				List<Order> orders = om.findOrderByUserShopName(userName, shopName);
				int orderPointSUM=0;
				for(int i=0;i<orders.size();i++){
					orderPointSUM += orders.get(i).getPoint();
				}
				points =points-orderPointSUM;
				System.out.println(points);
				out.print(String.valueOf(points));
				
				
	
	}

}
