package com.bit.bonusPointsExchange.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.bit.bonusPointsExchange.bean.ShopPointChange;
import com.bit.bonusPointsExchange.bean.UserAmountAweek;
import com.bit.bonusPointsExchange.manager.IntelligentAnalysisManger;
import com.bit.bonusPointsExchange.utils.TimeUtils;


public class QueryShopPointInOutNumServ extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String shopName = (String)session.getAttribute("shopName");
		//查询最近七天的用户量变化
		IntelligentAnalysisManger analysisManger = new IntelligentAnalysisManger(); 
		List<ShopPointChange> list = analysisManger.queryShopPointInOut(shopName);
		//调用JSONArray.fromObject方法把array中的对象转化为JSON格式的数组
        JSONArray json=JSONArray.fromObject(list);
        //System.out.println(json.toString());
        //返回给前段页面
        out.println(json);  
        out.flush();  
        out.close();   
	}

}
