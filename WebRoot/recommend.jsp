<%@page import="com.bit.bonusPointsExchange.bean.Shop"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@page import="com.bit.bonusPointsExchange.bean.Order"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	Vector<Vector<Integer>> recOrderIdList = (Vector<Vector<Integer>>)request.getAttribute("recOrderIdList");//智能推荐的所有可能路径的所有orderId
//	String shopName = (String)request.getAttribute("shopName");
//	String wantedShop = (String)request.getAttribute("wantedShop");
	List<Integer> recPoints =(List<Integer>)request.getAttribute("recPoints");
	List<Integer> recWantedPoints =(List<Integer>)request.getAttribute("recWantedPoints");	
	Shop recShop = (Shop)request.getAttribute("recShop");
	Shop recWantShop = (Shop)request.getAttribute("recWantShop");
%>
<%
	String finishOrderRes = (String)request.getAttribute("finishOrderRes"); //兑换积分的返回信息
	if(finishOrderRes=="您未绑定目标商家！"){
%>
  <script type="text/javascript" language="javascript">
    alert("您未绑定目标商家！请绑定目标商家！"); 
	location.href="/bonusPointsExchange/QueryBindedShopNameServlet?index="+5;  
  </script> 
<%}else if(finishOrderRes=="您未绑定商家！") {%>
 <script type="text/javascript" language="javascript">
    alert("您未绑定商家！请绑定商家！");     
   location.href="/bonusPointsExchange/QueryBindedShopNameServlet?index="+5;                      
  </script> 
<%}else if(finishOrderRes=="您在商家的积分不够！"){ %>
 <script type="text/javascript" language="javascript">
    alert("您在商家的积分不够！请转移积分到平台！");          
    location.href="/bonusPointsExchange/QueryBindedShopNameServlet?index="+3;    
  </script>   
<%}else if(finishOrderRes=="积分兑换成功！"){ %>
 <script type="text/javascript" language="javascript">
    alert("积分兑换成功!");                            
    location.href="/bonusPointsExchange/actionServlet?actionCode=order&methodCode=findAllOrder&selectSort=null";
  </script>     
<%}else if(finishOrderRes=="积分兑换失败！"){ %>
 <script type="text/javascript" language="javascript">
    alert("积分兑换失败！");                            
    location.href="/bonusPointsExchange/actionServlet?actionCode=order&methodCode=findAllOrder&selectSort=null";
  </script>    
 <%}else if(finishOrderRes=="连接blockchain失败，请检查网络！"){ %>
 <script type="text/javascript" language="javascript">
    alert("连接blockchain失败，请检查网络！");            
    location.href="/bonusPointsExchange/actionServlet?actionCode=order&methodCode=findAllOrder&selectSort=null";
  </script>   
<%} else if(finishOrderRes=="需要绑定中间商家"){
		String recBindShops = (String)request.getAttribute("recBindShops");
		System.out.println("jsp"+recBindShops);%>
 <script type="text/javascript" language="javascript">
 var recBindShops1= "<%=recBindShops%>";
    alert("推荐您绑定"+recBindShops1+"才能完成此交易！");            
    location.href="/bonusPointsExchange/QueryBindedShopNameServlet?index="+5;
  </script>   
<%} %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>智能推荐</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/footer.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/order.css">
<link rel="Shortcut Icon" href="images/bonus.jpg">
</head>

<body>
<!--这是top-->
	<%@ include file="header.jsp" %>
<!--这是main_page-->
<div class="exchangeOrder">
    <p class="title">系统推荐订单<span class="title1">&nbsp;&nbsp;&nbsp;RECOMMEND ORDER</span><span class="title1 right"><a href="order.jsp">返回订单中心</a>&nbsp;&nbsp;&nbsp;&nbsp;</span></p>
  <p style=" font-size:24px; border:#0663c1 1px solid;"><span style="color:red;" >提示：</span>智能推荐涉及到多方交易,您需要绑定交易链中涉及到的相关商家。</p>
  <div id="order-list" class="clearfix"> 
    <!---- 事例1------>
    <div class="order-info clearfix">
    <!-- 每次需要获得两个订单的详细信息-->
     <% if(null!=recOrderIdList){
     if(recOrderIdList.size()>0) {
             //System.out.println(latestOrderInfoList.size());
            for(int i = 0; i < recOrderIdList.size(); i++) {
           	 Vector<Integer> recOrderIds = (Vector<Integer>)recOrderIdList.get(i);
           	 
      %>
      <!-- 以上需要修改 -->
    <form action= "/bonusPointsExchange/actionServlet" method="post" onsubmit="return checkShop();">
      <ul class="clearfix">
        <li class="shop-logo"><img src="images/shopLogo/<%=recShop.getImgUrl()%>"/></li>
        <li class="exchangeOrder-info">
        <!-- table 中是用户最终交易需要的积分-->
          <table>
            <tr>
              <td>商家：<%=recShop.getShopName() %></td>
            </tr>
            <tr>
              <td>积分数量：<%=recPoints.get(i) %></td>
            </tr>
            <tr>
             <td>订单交易方：<%=recOrderIds.size()+1 %>方</td>
            </tr> 
          </table>
        </li>  
        <li><img src="images/2.png"/></li>
        <li>&nbsp;&nbsp;</li>
        <li class="shop-logo" rowspan="3"><img src="images/shopLogo/<%=recWantShop.getImgUrl()%>"/></li>
        <li class="exchangeOrder-info">
        <!-- table 中是用户最终交易获得的积分-->
          <table>
            <tr>
              <td>目标商家：<%=recWantShop.getShopName() %></td>
            </tr>
            <tr>
              <td>目标积分数量：<%=recWantedPoints.get(i) %></td>
            </tr>
            <tr>
              <td></td>
            </tr> 
          </table>
        </li>          
         <li class="operate">
          <input name="exchange" type="submit" class="submitBtn"  id="exchange" value="交易">
        </li>
    
        <input type="hidden" name="recOrderIds" value="<%=recOrderIds%>"/>
        <input type="hidden" name="actionCode" value="order"/>
      	<input type="hidden" name="methodCode" value="finsh_order_muliti"/>     	
      </ul>
     </form>
    <%} %>
  <%} else {%>
       <br/><br/><br/><p align="center" color="red">  搜索结果为0！</p>
 <%}} %>
    </div>
  </div>
  
</div>
<!--这是bottom-->
	<%@ include file="footer.jsp" %>
</body>

<script type="text/javascript">
function checkShop() {
	var shop = document.getElementById("session").value;
	if (shop != "null") {
		alert("商家类型账号不能交易，请更换用户类型账号登录！");
		return false;
	}
}

function getJson()
</script>
-->
</html>
