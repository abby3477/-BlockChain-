package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.manager.ShopChangePwdManger;
import com.bit.bonusPointsExchange.utils.Encode;

public class ShopChangePwdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			this.doPost(request, response);
	}

	//�����̼��޸�����Ĳ���
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		PrintWriter out = response.getWriter();
		
		//��ȡ�̼����;������Լ�������
		String shopName = request.getParameter("userName");
		//String oldPassword = request.getParameter("oldPassword");//ûʲô�ã��̼����ڵ�¼֮����ܽ������ҳ��
		String reNewPassword = request.getParameter("reNewPassword");
		String newPassword = Encode.MD5Encode(reNewPassword);//MD5����
		//�������ݿ�
		ShopChangePwdManger changePwdManger = new ShopChangePwdManger();
		boolean res = changePwdManger.updateShopPwd(shopName, newPassword);
		if(res) {
			request.setAttribute("shopChangePwdResult", "Y");// �������ԣ���ʾ�޸ĳɹ�����ǰ̨��ȡ������ʾ����
			request.getRequestDispatcher("login_shop.jsp").forward(request, response);
		}
		else {
			request.setAttribute("shopChangePwdResult", "N");// �������ԣ���ʾʧ��
			request.getRequestDispatcher("personal_shop.jsp").forward(request, response);
		}
	}

}
