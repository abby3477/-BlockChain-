package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.LoginShopManger;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
import com.bit.bonusPointsExchange.utils.MinimalistProportionUtils;

public class ReferencePriceServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		//��ȡ�û�������̼�����
		String shopName = request.getParameter("search1");
		String wantedShop = request.getParameter("search2");
		//��ѯ�̼Һ�Ŀ���̼ҵ�ͼ��
		LoginShopManger loginShopManger = new LoginShopManger();
		Shop shop =loginShopManger.getShopInfo(shopName);//�̼�ͼ��
		String ShopImgURL = shop.getImgUrl();
		shop =loginShopManger.getShopInfo(wantedShop);//Ŀ���̼�ͼ��
		String wantedShopImgURL = shop.getImgUrl();
		//��ѯ������ɵĽ���
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findLatestFinishedOrder(shopName, wantedShop);
		if(list.size() != 0) {
			//�������±���
			Order orderInfo = (Order)list.get(0);//�õ����µ�һ�ʽ���
			int point = orderInfo.getPoint();
			int wantedPoint =orderInfo.getWantedPoint();
			//���±���latestRate
			String latestRate = MinimalistProportionUtils.minimalistProportion(point, wantedPoint);
			//��������ʮ�ʽ��׵�ƽ������������ʮ���ж��������
			int size = list.size();
			int poi = 0;//point�ĺ�
			int wanPoi = 0;//wantedPoint�ĺ�
			if(size < 10) {
				for(int i = 0; i < size; i++) {
					orderInfo = (Order)list.get(i);
					poi += orderInfo.getPoint();
					wanPoi +=orderInfo.getWantedPoint();
				}
			} else {
				for(int i = 0; i < 10; i++) {
					orderInfo = (Order)list.get(i);
					poi += orderInfo.getPoint();
					wanPoi +=orderInfo.getWantedPoint();
				}
			}
			//����ƽ������
			String averageRate = MinimalistProportionUtils.minimalistProportion(poi, wanPoi);
			//ҳ����ʾ
			request.setAttribute("latestRate", latestRate);
			request.setAttribute("point", point);
			request.setAttribute("wantedPoint", wantedPoint);
			request.setAttribute("averageRate", averageRate);	
		} else {
			request.setAttribute("newOrder", "N");
		}
		request.setAttribute("ShopImgURL", ShopImgURL);
		request.setAttribute("wantedShopImgURL", wantedShopImgURL);
		request.setAttribute("shopName", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.getRequestDispatcher("reference.jsp").forward(request, response);
	}
}

