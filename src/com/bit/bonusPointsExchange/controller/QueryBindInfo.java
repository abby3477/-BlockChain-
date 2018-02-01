package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.manager.BindShopManager;
//��ѯ�û��Ѿ��󶨵��̼ҵ������Ϣ����6��div������ʾ
public class QueryBindInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//��ȡ�û���ƽ̨��¼����
		String userName =(String)request.getSession().getAttribute("userName");
		//System.out.println(userName);
		//��ѯ���ݿ⣬������ϲ�ѯ
		BindShopManager bindShopManger = new BindShopManager();
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "6");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		
		
	}

}
