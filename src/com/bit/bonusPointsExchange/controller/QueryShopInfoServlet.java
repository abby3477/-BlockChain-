package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;

public class QueryShopInfoServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		
		HttpSession session = request.getSession();
		String shopName = (String)session.getAttribute("shopName");
		
		LoginShopManger loginShopManger = new LoginShopManger();
		Shop shop = loginShopManger.getShopInfo(shopName);
		
		request.setAttribute("email", shop.getEmail());//���ݸ�personal_shopҳ�������ʾ
		request.setAttribute("imageURL", shop.getImgUrl());//���ݸ�personal_shopҳ�������ʾ
		request.setAttribute("telephone", shop.getTelephone());//���ݸ�personal_shopҳ�������ʾ
		request.setAttribute("shopDec", shop.getShopDec());//���ݸ�personal_shopҳ�������ʾ
		request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
	}

}
