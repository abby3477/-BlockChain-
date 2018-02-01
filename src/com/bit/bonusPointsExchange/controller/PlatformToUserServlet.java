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
import com.bit.bonusPointsExchange.manager.PlatformPointToUserManger;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;

public class PlatformToUserServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		BindShopManager bindShopManger = new BindShopManager();
		//ͨ��session��ȡƽ̨�û���
		String userName =(String)request.getSession().getAttribute("userName");	
		//��ȡ�û���ƽ̨��������
		int points = Integer.parseInt(request.getParameter("platformPoints"));
		//��ȡ�û���Ҫת�ƵĻ�������,����ת������
		String transfer_points = (String)request.getParameter("transfer_points2");
		System.out.print("ssssss");
		System.out.print(transfer_points);
		int wantTransfer_points = Integer.parseInt(transfer_points);
		//��ȡ�û����̼�ע����û���
		String userNameAtShop = (String)request.getParameter("userName2");
		//��ȡ�û������̼�
		String shopName = request.getParameter("shop2");
		//�������ݿ�
		PlatformPointToUserManger dbManger = new PlatformPointToUserManger();
		if(points == 0 || wantTransfer_points == 0 || wantTransfer_points > points) {
			request.setAttribute("pointToPshopRes", "N");
			request.setAttribute("userPoints", points);
			}
		else {
			
			Point point = bindShopManger.findBindedShop(userName, shopName);
			String str="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'transfer','args':['"+point.getPointID()+"','0','"+wantTransfer_points+"']}},'id': 3}";
			HttpUtils httputils = new HttpUtils();
			String conRes = httputils.getHttpConnection();
			if(conRes.equals("�����������ӳɹ�")){
				GetJsonStr result = httputils.postJsonToBlockChain(str);
				if(result.getResult().getStatus().equals("OK")){
					//ִ����ز���
					//1.ƽ̨���ݿ��м��ٻ���
					boolean res1 = dbManger.updatePointsPlatform(userName, shopName, wantTransfer_points);
					//2.�̼����ݿ������ӻ���
					boolean res2 = dbManger.updatePointsShop(userNameAtShop, shopName, wantTransfer_points);
					UserPointToplatfromManger pointToplatfromManger = new UserPointToplatfromManger();
					//��ѯpointID
					int pointID = dbManger.queryPointID(userName, shopName);
					//��transfer���м�¼��ʽ���
					String transferTime = TimeUtils.getNowTime();//ת��ʱ��
					Transfer transfer = new Transfer(pointID, 1, wantTransfer_points,transferTime,shopName);
					int res3 = dbManger.insertTransfer(transfer);
					if (res1 && res2 && (0 != res3)) ////�������߼�������ģ�����������ݿ����ʧ��һ������Ҫ�����ݿ�ع���û�и��µ�״̬��Ҫ�ֱ�ȥ�жϣ������ع��ĸ����ݿ�
					{
						int userPoints1 =  pointToplatfromManger.ownPointsAtPlatform(userName, shopName);//�û���ƽ̨�Ļ���
						int shopPoints1 = pointToplatfromManger.ownPoints(userNameAtShop, shopName);//�û����̼ҵĻ���
						String userPoints = String.valueOf(userPoints1);
						String shopPoints = String.valueOf(shopPoints1);
						request.setAttribute("userPoints", userPoints);
						request.setAttribute("shopPoints", shopPoints);
						request.setAttribute("pointToPshopRes", "Y");	
					}
					else {
						request.setAttribute("userPoints", point);
						request.setAttribute("pointTranRes", "N"); 
						
					}
					
				}else{
					request.setAttribute("userPoints", point);
					request.setAttribute("pointTranRes", "N"); 
				}
			}else{						
				request.setAttribute("userPoints", point);
				request.setAttribute("pointTranRes", "����blockchainʧ�ܣ���������");
			}
			
		}
		//��ѯ�û��󶨵��̼���Ϣ����ʾ��select��
		List< ShowBindInfo> list = bindShopManger.bingShopInfo(userName);
		request.setAttribute("bindInfo", list);
		request.setAttribute("index", "4");
		request.getRequestDispatcher("personalv1.0.jsp").forward(request, response);
		
	}

}
