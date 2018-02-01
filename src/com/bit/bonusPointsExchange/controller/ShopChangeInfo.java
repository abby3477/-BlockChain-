package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Shop;
import com.bit.bonusPointsExchange.manager.RegistShopManger;
import com.bit.bonusPointsExchange.manager.ShopChangeInfoManger;

public class ShopChangeInfo extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		//��ȡ�û��ύ�������Ϣ
		String oldShopName = (String)request.getSession().getAttribute("shopName");	
		String shopName = request.getParameter("name");
		String imgURL= request.getParameter("imageURL");//��ȡ�̼�ѡ���ͼ���·��
		String email = request.getParameter("email");
		String telephone = request.getParameter("phone");
		String shopDec = request.getParameter("description");
		
		//System.out.println(shopName);
		//System.out.println(email);
		//System.out.println(telephone);
		//System.out.println(shopDec);
		//System.out.println(imgURL);
		//����ͼ����ϴ�����
		
		//��װ�ɶ���
		Shop shop = new Shop();
		shop.setShopName(shopName);
		shop.setEmail(email);
		shop.setImgUrl(imgURL);
		shop.setShopDec(shopDec);
		shop.setTelephone(telephone);
		//�����̼������Ƿ��Ѿ�����
		//���ò�ѯ��������ѯ�����ݿ��и��̼������Ƿ��Ѿ�����
		RegistShopManger registShopManger = new RegistShopManger();
		boolean res = registShopManger.isShopNameExit(shop);
		if(!res && !oldShopName.equals(shopName)){  //sahngjia���Ѵ���,�Ҳ���ԭ�е��̼�����
			request.setAttribute("shopChangeResult", "N");// �������ԣ���ʾ�޸���Ϣʧ��
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("shopDec", shopDec);
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
			return;
		}
		
		//�������ݿ�
		ShopChangeInfoManger changeInfoManger = new ShopChangeInfoManger();
		res = changeInfoManger.updateShopInfo(shop, oldShopName);
		if(res) {
			request.getSession().setAttribute("shopName",shopName);	//���ݸ��³ɹ�������sessionֵ
			request.setAttribute("imageURL", imgURL);
			request.setAttribute("telephone", telephone);
			request.setAttribute("email", email);
			request.setAttribute("shopDec", shopDec);
			request.setAttribute("shopChangeResult", "Y");// �������ԣ���ʾ�޸���Ϣ�ɹ�����ǰ̨��ȡ������ʾ����
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
			
		}
		else {
			request.setAttribute("shopChangeResult", "N");// �������ԣ���ʾ�޸���Ϣʧ��
			System.out.println(request.getAttribute("shopChangeResult"));
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		}
	}

}
