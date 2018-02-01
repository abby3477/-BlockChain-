package com.bit.bonusPointsExchange.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.bonusPointsExchange.bean.Order;
import com.bit.bonusPointsExchange.bean.Point;
import com.bit.bonusPointsExchange.bean.ShowBindInfo;
import com.bit.bonusPointsExchange.json.GetJsonStr;
import com.bit.bonusPointsExchange.manager.BindShopManager;
import com.bit.bonusPointsExchange.manager.OrderManager;
import com.bit.bonusPointsExchange.manager.PointManager;
import com.bit.bonusPointsExchange.manager.QueryOrderManager;
import com.bit.bonusPointsExchange.manager.UserPointToplatfromManger;
import com.bit.bonusPointsExchange.service.OrderService;
import com.bit.bonusPointsExchange.utils.HttpUtils;
import com.bit.bonusPointsExchange.utils.TimeUtils;

/**
 * ����ģ��
 * @author gmx
 *
 */
public class OrderAction extends Action{
	
	private int orderStatus_unfinished_valid = 0;//0����δ�����δ������Ч�ڣ���Ч��
	private int orderStatus_finished = 1;//1�������
	private int orderStatus_cancel_invalid=2;//2�������򳬹���Ч�ڣ���Ч)
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String methodCode = request.getParameter("methodCode");
		String sortMeans = request.getParameter("selectSort");
		if(methodCode.equals("release_order")){//��������
			this.releaseOrder(request,response);
		}else if(methodCode.equals("findAllOrder")){//��ѯ���ж���
			if(sortMeans.equals("��������")){
				this.findAllOrderPriorityPoint(request, response);
			}else if(sortMeans.equals("��������")){
				this.findAllOrderByRate(request,response);
			}else if(sortMeans.equals("ʱЧ����")){
				this.findAllOrderByUntilDate(request,response);
			}else if(sortMeans.equals("null")){
				request.setAttribute("index", 3);
				request.getRequestDispatcher("order.jsp").forward(request, response);
			}
		}else if(methodCode.equals("finsh_order")){//���л��ֶһ�����ɶ���,˫������
			this.finishOrderByTwo(request, response);
		}else if(methodCode.equals("finsh_order_muliti")){//�෽����
			this.finshOrderByMuliti(request, response);
		}
	}
	
	/*��������*/
	public void releaseOrder(HttpServletRequest request, HttpServletResponse response){
		String shopName = request.getParameter("shopName");
		int point = Integer.parseInt(request.getParameter("points"));
		String wantedShop = request.getParameter("wantedShop");
		int wantedPoint = Integer.parseInt(request.getParameter("wantedPoint"));
		String userName = (String) request.getSession().getAttribute("userName");
		String untilDate = request.getParameter("utilDate2");
		try {
			OrderManager om = new OrderManager();
			Order order = new Order();
			order.setShopName(shopName);
			order.setPoint(point);
			order.setWantedShop(wantedShop);
			order.setWantedPoint(wantedPoint);
			order.setUserName(userName);
			order.setOrderStatus(orderStatus_unfinished_valid);//����δ�������Ч
			order.setUntilDate(untilDate);
			
			int result = om.addOrder(order);
			if(result>0){
				BindShopManager bindShopManager = new BindShopManager();
				List<ShowBindInfo> shops = bindShopManager.bingShopInfo(userName);
				request.setAttribute("bindShops", shops);
				request.setAttribute("releaseOrderResult", "Y");
				request.getRequestDispatcher("order.jsp").forward(request, response);
		
			}else{
				request.setAttribute("releaseOrderResult", "N");
				request.getRequestDispatcher("order.jsp").forward(request, response);
			}
			
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*�������Ȳ�ѯ���ж���*/
	public void findAllOrderPriorityPoint(HttpServletRequest request, HttpServletResponse response){//�������ȷ�ʽ�������ж���
		String userName = (String)request.getSession().getAttribute("userName");
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//��������
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//Ŀ���������
		OrderManager om = new OrderManager();
		Order order = new Order();
		order.setShopName(shopName);
		order.setPoint(points);
		order.setWantedShop(wantedShop);
		order.setWantedPoint(wantedPoints);
		List<Order> orders = om.findAllOrderPriorityPoint(userName,order);
		request.setAttribute("orders", orders);
		request.setAttribute("index", "3");
		request.setAttribute("findRes", "true");
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
		try {
			request.getRequestDispatcher("order.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	

	//��������
	public void	 findAllOrderByRate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String userName = (String)request.getSession().getAttribute("userName");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//��������
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//Ŀ���������
		//��ѯ���ݿ⣬���ð����ʲ�ѯ����
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findAllOrderByRate(shopName, wantedShop,userName,points,wantedPoints);
		request.setAttribute("AllOrderByRate", list);
		request.setAttribute("index", "3");
		request.setAttribute("selectID", "2");//���ý�������ʾ�ڼ���select
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
		request.getRequestDispatcher("order.jsp").forward(request, response);
	}
		
	//ʱЧ����
	public void	 findAllOrderByUntilDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String shopName = request.getParameter("shop");
		String wantedShop = request.getParameter("targetShop");
		String userName = (String)request.getSession().getAttribute("userName");
		String point = request.getParameter("point");
		int points= Integer.parseInt(point);//��������
		String wantedPoint = request.getParameter("wantedPoint2");
		int wantedPoints = Integer.parseInt(wantedPoint);//Ŀ���������
		//��ѯ���ݿ⣬���ð�ʱЧ���Ȳ�ѯ����
		QueryOrderManager manager = new QueryOrderManager();
		List<Order> list = manager.findAllOrderByUntilDate(shopName, wantedShop,userName,points,wantedPoints);
		request.setAttribute("AllOrderByUntilDate", list);
		request.setAttribute("index", "3");
		request.setAttribute("selectID", "3");//���ý�������ʾ�ڼ���select
		request.setAttribute("shop", shopName);
		request.setAttribute("wantedShop", wantedShop);
		request.setAttribute("point", point);
		request.setAttribute("wantedPoint", wantedPoint);
		request.getRequestDispatcher("order.jsp").forward(request, response);
	}
	
	/*�һ�����,��ɶ���*/  /*����Ƿ����̼�ע�����˻�*/ /*˫������*/
	public void finishOrderByTwo(HttpServletRequest request, HttpServletResponse response){	
		int orderID = Integer.parseInt(request.getParameter("orderID"));				
		String exchangeUserName = (String) request.getSession().getAttribute("userName");							
//		String shop = (String)request.getParameter("shop");
//		String wantedShop = (String)request.getParameter("targetShop");
//		int point = Integer.parseInt(request.getParameter("point"));
//		int wantedPoint = Integer.parseInt(request.getParameter("wantedPoint2"));
		OrderService os = new OrderService();
		String finishOrderRes=os.finishOrder(exchangeUserName, orderID);
		 System.out.print(finishOrderRes);
		request.setAttribute("index", 3);	
//		request.setAttribute("shop", shop);
//		request.setAttribute("wantedShop", wantedShop);
//		request.setAttribute("point", point);
//		request.setAttribute("wantedPoint", wantedPoint);
		request.setAttribute("finishOrderRes", finishOrderRes);
		
		try {
			request.getRequestDispatcher("order.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void finshOrderByMuliti(HttpServletRequest request, HttpServletResponse response){
		OrderService os = new OrderService();
		String finishOrderRes=null;
		OrderManager om = new OrderManager();
		BindShopManager bsm = new BindShopManager();
		String str = request.getParameter("recOrderIds");
		str=str.substring(1, str.length()-1);
		String recOrderIds[] = str.split(",");	
		String exchangeUserName = (String)request.getSession().getAttribute("userName");
		String recBindShops = "�̼ң�";//�û��������Ƽ���������Ҫ�󶨵��м��̼�
		int recBindShopNum = 0;
		for(int i = 0; i < recOrderIds.length; i++) {//��ѯ�û��Ƿ��ֻ���Ƽ�����ʽ�����е��м��̼�
			int recOrderId=Integer.parseInt(recOrderIds[i].trim());
			Order order = om.findOrderByID(recOrderId);
			boolean isBindRes = bsm.isBindThisShop(exchangeUserName, order.getShopName());
			if(isBindRes==false){
				recBindShops=recBindShops+order.getShopName()+",";
				System.out.print(recBindShops);
				//recBindShops.add(order.getShopName());
				recBindShopNum++;
			}
		}
		if(recBindShopNum!=0){//����û����м��̼�Ϊ�󶨷���ҳ����ʾ��Ϣ
			request.setAttribute("recBindShops",recBindShops);
			request.setAttribute("finishOrderRes", "��Ҫ���м��̼�");
		}else{
		
			for(int i = 0; i < recOrderIds.length; i++) {
				int recOrderId=Integer.parseInt(recOrderIds[i].trim());
				finishOrderRes = os.finishOrder(exchangeUserName, recOrderId);
				if(!finishOrderRes.equals("���ֶһ��ɹ���")){
					break;
				}
					System.out.println(finishOrderRes);
			}
			System.out.println("end"+finishOrderRes);		
			request.setAttribute("finishOrderRes", finishOrderRes);
		}
		try {
			request.getRequestDispatcher("recommend.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
