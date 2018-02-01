package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;

public class ChangeOrderStaServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		String orderID1 = request.getParameter("orderID");
		int orderID = Integer.parseInt(orderID1);
		//System.out.println(orderID);
		QueryOrderManager manager = new QueryOrderManager();
		//�ı䶩��״̬
		manager.changeOrderStatus(orderID,2);//�˴�Ӧ�õ�����û���ʾ������д��������
		//��ȡ�û���ƽ̨��¼����
		String userName =(String)request.getSession().getAttribute("userName");
		//��ѯ���ݿ��order
		List<Order> list = manager.queryOrderInfo(userName);
		request.setAttribute("orderInfo", list);
		request.setAttribute("index", "2");
		request.getRequestDispatcher("order.jsp").forward(request, response);
		
	}

}
