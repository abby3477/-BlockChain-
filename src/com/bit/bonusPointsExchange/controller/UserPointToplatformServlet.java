package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.bean.Transfer;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;

public class UserPointToplatformServlet extends HttpServlet {

	//���ڴ����û�ѡ��ת�ƻ��ֵ�ƽ̨
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�и�ǰ�ᣬ�����û����̼�ע����û������û���ƽ̨ע����û�����ͬ����ΪĿǰ�޷�֪���̼ҵ����ݿ�
		//�����������Ҫ�û������̼ҵ����ƺ����루�������
		//���ת�ƻ������ݿ���ز���
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		BindShopManager bindShopManger = new BindShopManager();
		//ͨ��session��ȡƽ̨�û���
		//���ﻹӦ�û�ȡ�û����̼ҵ��û������˺ţ������жϣ���������������������ƽ̨�û������̼��û�����ͬ����Ȼ�ⲻ��ѧF��
		String userName =(String)request.getSession().getAttribute("userName");	
		int points = Integer.parseInt(request.getParameter("points"));//��ȡ�û���������
		String userNameAtShop = (String)request.getParameter("userName");//��ȡ�û����̼�ע����û���
		//System.out.println(userNameAtShop);
		//��ȡ�û���Ҫת�ƵĻ�������,����ת������
		String transfer_points = (String)request.getParameter("transfer_points");
		int wantTransfer_points = Integer.parseInt(transfer_points);
		//��ȡ�û�ѡ����̼ң��û�ѡ����̼ұ���������ƽ̨���й�ע�ᣨ�������
		String shopName = request.getParameter("shop");		
		//�������ݿ�
		UserPointToplatfromManger dbManger = new UserPointToplatfromManger();
		if(points == 0 || wantTransfer_points == 0 || wantTransfer_points > points) {
			request.setAttribute("pointTranRes", "N"); 
			request.setAttribute("shopPoints", points);
		}
		else {
			Point point = bindShopManger.findBindedShop(userName, shopName);
			String str="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'transfer','args':['"+point.getPointID()+"','1','"+wantTransfer_points+"']}},'id': 3}";
			HttpUtils httputils = new HttpUtils();
			String conRes = httputils.getHttpConnection();
			if(conRes.equals("�����������ӳɹ�")){
				GetJsonStr result = httputils.postJsonToBlockChain(str);
				if(result.getResult().getStatus().equals("OK")){
				
					//ִ����ز���
					//1.ƽ̨���ݿ������ӻ���
					boolean res1 = dbManger.updatePointsPlatform(userName, shopName, wantTransfer_points);
					//��ѯpointID
					int pointID = dbManger.queryPointID(userName, shopName);
					//2.�̼����ݿ��м��ٻ���
					boolean res2 = dbManger.updatePointsShop(userNameAtShop, shopName, wantTransfer_points);
					//��transfer���м�¼��ʽ���
					String transferTime = TimeUtils.getNowTime();//ת��ʱ��
					Transfer transfer = new Transfer(pointID, 0, wantTransfer_points,transferTime,shopName);
					int res3 = dbManger.insertTransfer(transfer);
					
					if (res1 && res2 && (0 != res3)) {//�������߼�������ģ�����������ݿ����ʧ��һ������Ҫ�����ݿ�ع���û�и��µ�״̬����ʱ�����
						int userPoints1 =  dbManger.ownPointsAtPlatform(userName, shopName);//�û���ƽ̨�Ļ���
						int shopPoints1 = dbManger.ownPoints(userNameAtShop, shopName);//�û����̼ҵĻ���
						String userPoints = String.valueOf(userPoints1);
						String shopPoints = String.valueOf(shopPoints1);
						request.setAttribute("userPoints", userPoints);//������ʾ
						request.setAttribute("shopPoints", shopPoints);//������ʾ
						request.setAttribute("pointTranRes", "Y"); 
					}
					else {
						request.setAttribute("shopPoints", point);
						request.setAttribute("pointTranRes", "N"); 
						
					}
				}else{
					request.setAttribute("shopPoints", point);
					request.setAttribute("pointTranRes", "N"); 
				}
			}else{						
				request.setAttribute("shopPoints", point);
				request.setAttribute("pointTranRes", "����blockchainʧ�ܣ���������");
			}
			
		}
		
		//��ѯ�û��󶨵��̼���Ϣ����ʾ��select��
		
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "3");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
	}
}
