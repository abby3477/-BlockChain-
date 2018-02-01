package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.PointManager;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;

public class FinishLatestOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		OrderManager om = new OrderManager();
		PointManager pm = new PointManager();
		BindShopManager bsm = new BindShopManager();
		UserPointToplatfromManger uptm =new UserPointToplatfromManger();
		
		int orderID = Integer.parseInt(request.getParameter("orderID"));
		String exchangeUserName = (String) request.getSession().getAttribute("userName");
		Point pointsFromRelease = new Point();//���������ߵĻ���
		Point pointsFromReleaseWanted = new Point();//������������Ҫ�����Ļ���
		Point pointsFromExchange = new Point();//�������ߵĻ���
		Point pointsFromExchangeWanted = new Point();//����������Ҫ�����Ļ���
		
		Order orderInfo = om.findOrderByID(orderID);
		
		boolean isBindWantedShop = bsm.isBindThisShop(exchangeUserName, orderInfo.getShopName());
		boolean isBindShopName = bsm.isBindThisShop(exchangeUserName, orderInfo.getWantedShop());
		
		
		
		try {
			if(!isBindWantedShop){
				//���ݿ��ѯ���¶���				
				request.setAttribute("isBindWantedShop", "false");
				QueryOrderManager manager = new QueryOrderManager();
				List<Order> list = manager.QueryLatestOrder(exchangeUserName);
				request.setAttribute("latestOrderInfo", list);
				request.getRequestDispatcher("exchange.jsp").forward(request, response);
				return;
	
			}
			if(!isBindShopName){
				request.setAttribute("isBindShopName", "false");
				QueryOrderManager manager = new QueryOrderManager();
				List<Order> list = manager.QueryLatestOrder(exchangeUserName);
				request.setAttribute("latestOrderInfo", list);
				request.getRequestDispatcher("exchange.jsp").forward(request, response);
				return;
			}
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int pointsAtPlatform=uptm.ownPointsAtPlatform(exchangeUserName,orderInfo.getWantedShop());
		if(pointsAtPlatform<orderInfo.getWantedPoint()){
			request.setAttribute("isPointEnough", "no");
			request.setAttribute("noPointShop", orderInfo.getWantedShop());
			try {
				QueryOrderManager manager = new QueryOrderManager();
				List<Order> list = manager.QueryLatestOrder(exchangeUserName);
				request.setAttribute("latestOrderInfo", list);
				request.getRequestDispatcher("exchange.jsp").forward(request, response);
				return;
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		Point point1 = bsm.findBindedShop(exchangeUserName, orderInfo.getWantedShop());//���׷��������̼��еĻ���
		Point point2 = bsm.findBindedShop(exchangeUserName, orderInfo.getShopName());//���׷������������̼��еĻ���
		Point point3 = bsm.findBindedShop(orderInfo.getUserName(), orderInfo.getShopName());//�������������̼��еĻ���
		Point point4 = bsm.findBindedShop(orderInfo.getUserName(), orderInfo.getWantedShop());//�����������������̼��еĻ���                                             
	
		String str1 ="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'invoke','args':['"+String.valueOf(point1.getPointID())+"','"+String.valueOf(point2.getPointID())+"','"+String.valueOf(orderInfo.getWantedPoint())+"','"+String.valueOf(orderInfo.getPoint())+"']}},'id': 3}";
		String str2 ="{'jsonrpc': '2.0','method': 'invoke','params': {'type': 1,'chaincodeID':{'name':'6ef62a4eb59238a25fedcb50cc873f90f9d3fe0053888620f9011e25947fa85c9d411ac7193572732cf11987f2f8423d9a77d18332cf6f7dc4c2fa4821136099'},'ctorMsg': {'function':'invoke','args':['"+String.valueOf(point3.getPointID())+"','"+String.valueOf(point4.getPointID())+"','"+String.valueOf(orderInfo.getPoint())+"','"+String.valueOf(orderInfo.getWantedPoint())+"']}},'id': 3}";
		HttpUtils httputils = new HttpUtils();
		String conRes = httputils.getHttpConnection();
		if(conRes.equals("�����������ӳɹ�")){
			GetJsonStr result1 = httputils.postJsonToBlockChain(str1);
			if(result1.getResult().getStatus().equals("OK")){
				HttpUtils httputils2 = new HttpUtils();
				httputils2.getHttpConnection();
				GetJsonStr result2 = httputils2.postJsonToBlockChain(str2);
				if(result2.getResult().getStatus().equals("OK")){
	
				/*���������ߵĻ���*/
				pointsFromRelease.setUserName(orderInfo.getUserName());
				pointsFromRelease.setShopName(orderInfo.getShopName());
				pointsFromRelease.setPlatformPoint(pm.findPointByUserName(pointsFromRelease)-orderInfo.getPoint());
				
				/*/������������Ҫ�����Ļ���*/
				pointsFromReleaseWanted.setUserName(orderInfo.getUserName());
				pointsFromReleaseWanted.setShopName(orderInfo.getWantedShop());
				pointsFromReleaseWanted.setPlatformPoint(pm.findPointByUserName(pointsFromReleaseWanted)+orderInfo.getWantedPoint());
				
				/*�������ߵĻ���*/
				pointsFromExchange.setUserName(exchangeUserName);
				pointsFromExchange.setShopName(orderInfo.getWantedShop());
				pointsFromExchange.setPlatformPoint(pm.findPointByUserName(pointsFromExchange)-orderInfo.getWantedPoint());
				
				/*����������Ҫ�����Ļ���*/
				pointsFromExchangeWanted.setUserName(exchangeUserName);
				pointsFromExchangeWanted.setShopName(orderInfo.getShopName());
				pointsFromExchangeWanted.setPlatformPoint(pm.findPointByUserName(pointsFromExchangeWanted)+orderInfo.getPoint());
				
				int pointRes1 = pm.updatePoint(pointsFromRelease);
				int pointRes2 = pm.updatePoint(pointsFromReleaseWanted);
				int pointRes3 = pm.updatePoint(pointsFromExchange);
				int pointRes4 = pm.updatePoint(pointsFromExchangeWanted);
				
				String orderDate = TimeUtils.getNowTime();
				int orderStatus = 1;//�������
				
				Order order = new Order();
				order.setOrderID(orderID);
				order.setExchangeUserName(exchangeUserName);
				order.setOrderDate(orderDate);
				order.setOrderStatus(orderStatus);
				int orderRes = om.finshOrder(order);//��ɶ���
				
				if(orderRes>0&&pointRes1>0&&pointRes2>0&&pointRes3>0&&pointRes4>0){
					request.setAttribute("exchangeRes", "true");
				}else
					request.setAttribute("exchangeRes", "false");
			}
			}
		}else{
			request.setAttribute("exchangeRes", "����blockchainʧ�ܣ���������");
		}
				
				
			try {
				QueryOrderManager manager = new QueryOrderManager();
				List<Order> list = manager.QueryLatestOrder(exchangeUserName);
				request.setAttribute("latestOrderInfo", list);
				request.getRequestDispatcher("exchange.jsp").forward(request, response);
				return;
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
