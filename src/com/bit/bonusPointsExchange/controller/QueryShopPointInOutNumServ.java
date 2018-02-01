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
		//��ѯ���������û����仯
		IntelligentAnalysisManger analysisManger = new IntelligentAnalysisManger(); 
		List<ShopPointChange> list = analysisManger.queryShopPointInOut(shopName);
		//����JSONArray.fromObject������array�еĶ���ת��ΪJSON��ʽ������
        JSONArray json=JSONArray.fromObject(list);
        //System.out.println(json.toString());
        //���ظ�ǰ��ҳ��
        out.println(json);  
        out.flush();  
        out.close();   
	}

}
