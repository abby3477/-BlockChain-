package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
//��ѯ�û����̼Ҵ���ӵ�еĻ���
public class QueryUserPoints extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����û�ѡ���ѯ���̼����ݿ��У�ģ�⣩�û��ж��ٻ���
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//ͨ��session��ȡ�û���
		//String userName =(String)request.getSession().getAttribute("userName");
		//��ȡ�û����̼�ע����û���
		String userNameAtShop = request.getParameter("userNameAtShop");
		userNameAtShop = URLDecoder.decode(userNameAtShop, "UTF-8");
		System.out.println("adadasdasd54555");
		System.out.println(userNameAtShop);
		//��ȡ�û��ύ�������Ϣ
		String shopName = request.getParameter("shop");
		shopName = URLDecoder.decode(shopName, "UTF-8");
		//System.out.println(shopName);
		//BindShopManger bindShopManger = new BindShopManger();
		//��ѯ�û����̼�ӵ�еĻ���
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		int points = dbManger.ownPoints(userNameAtShop, shopName);
		out.print(String.valueOf(points));
		
	}

}
