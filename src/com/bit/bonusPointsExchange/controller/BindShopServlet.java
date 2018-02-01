package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.json.CtorMsg;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.json.Params;
import com.bit.bonusPointsExchange.json.PostJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.utils.Encode;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BindShopServlet extends HttpServlet {
//ִ�а��̼ҵ���ز���
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		//��ȡ�û���д�������̼ҵ��û���������
		String userName = (String) request.getParameter("userName");//���̼ҵ��û���
		String password = (String) request.getParameter("password");//���̼ҵ�����
		String shopName = (String)request.getParameter("ShopName");//�̼�����
		//String password = Encode.MD5Encode(passwd);//����
		String bindTime = TimeUtils.getNowTime();
		System.out.println(shopName);
		System.out.println(password);
		//System.out.println(shopName);
		//��ѯ�̼����ݿ�userpoint�ȶ�,��ѯ���û�������ѡ���̼��Ƿ�ע��
		//��������1.ѡ����̼ұ���������ƽ̨ע����ܱ���
		//2.�û�����������ƽ̨��¼
		//3.ѡ�����̼Ҹ��û�û�а�
		//4.�û�������ѡ���̼ұ���ע��
		BindShopManager bindShopManger = new BindShopManager();
		int res = bindShopManger.isValid(userName, password,shopName);
		System.out.println(res);
		if(res == 1) {//���ڣ�����point������һ���ʾ�û��İ���Ϣ����󶨵��̼ҵ����ƣ��Լ������ƣ��󶨵��̼��Լ�ת�Ƶ�ƽ̨�Ļ���
			//�ڲ������ݿ�֮ǰӦ���ж��Ƿ��Ѿ����˸��̼�
			String user =(String)request.getSession().getAttribute("userName");	
			boolean re = bindShopManger.isBindThisShop(user, shopName);
			if(!re) {		
				int count = bindShopManger.insertBindInfoToPoint(user, shopName,bindTime);
				if(count == 1) {//����ɹ�����point���в�ѯ���û��󶨵��̼�
					Point point = bindShopManger.findBindedShop(user, shopName);
										
					String str ="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'addAcc','args':['"+String.valueOf(point.getPointID())+"','0']}},'id': 3}";
					System.out.println(str);
					/*
					PostJsonStr jsonStr = new PostJsonStr(); 
					Params params = new Params();
					CtorMsg ctorMsg = new CtorMsg();
					List<String> args = new ArrayList<String>();					
					args.add(String.valueOf(point.getPointID()));
					args.add(String.valueOf(point.getPlatformPoint()));
					ctorMsg.setFunction("addAcc");
					ctorMsg.setArgs(args);
					params.setCtorMsg(ctorMsg);
					jsonStr.setMethod("invoke");
					jsonStr.setParams(params);
					jsonStr.setId(3);
					*/
					
					
					HttpUtils httputils = new HttpUtils();
					String conRes = httputils.getHttpConnection();
					if(conRes.equals("�����������ӳɹ�")){
						GetJsonStr result = httputils.postJsonToBlockChain(str);
						if(result.getResult().getStatus().equals("OK")){
							request.setAttribute("index", "5");//������ʾ�ڼ���div
							//����������ԣ�ת����ҳ�������ʾ
							request.setAttribute("bindRes", "Y");
							request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
							return;
						}else{
							bindShopManger.deleteBindShop(point.getPointID());						
						} 
					}else{						
						bindShopManger.deleteBindShop(point.getPointID());
						request.setAttribute("index", "5");//������ʾ�ڼ���div
						request.setAttribute("bindRes", "����blockchainʧ�ܣ���������");
						request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
						return;
					}
														
					
				}
			}
		}
		//������
		//��ʾ��ʧ�ܣ�ͣ����ԭҳ�棬���½��а�
		request.setAttribute("shopName", shopName);
		request.setAttribute("bindRes", "N");
		request.setAttribute("index", "5");//������ʾ�ڼ���div
		//out.print("<script language='JavaScript'>alert('��ʧ�ܣ������½��а󶨣�');location.href='/bonusPointsExchange/bindShop.jsp';</script>");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		
	}

}
