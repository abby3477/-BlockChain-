<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--这是top-->
<!--<div style="background-color:#FCFCFC; margin-top:0px;">-->
<div id="headerNav" style="background-color:#0663c1; margin-top:0px;"> <!--#e78170;#DE4949-->
<div class="header">
  <div class="span1">
    <div class="logo"><img src="images/bonus3.png" alt="积分兑换平台"/></div>
      
  </div>
  <div class="span2" >
    <div class="mainNav">
      <ul>
        <li><a href="index.jsp" style="color:#efefef;">主页</a></li>
        <li><a href="reference.jsp" style="color:#efefef;" >参考价</a></li>
        <li><a href="/bonusPointsExchange/QueryLatestOrder" style="color:#efefef;">最新发布</a></li>
        <li><a href="/bonusPointsExchange/actionServlet?actionCode=bindShop&methodCode=find_bindedShops" style="color:#efefef;">发起交易</a></li>
    <%
	String isUserLogin = (String)session.getAttribute("userName"); 
	String isShopLogin = (String)session.getAttribute("shopName"); 
	if(isUserLogin == null && isShopLogin == null) {
	%>
	<li><a href="login.jsp" style="color:#efefef; font-size:24px;">登录</a> </li>
<% } else if(isUserLogin != null && isShopLogin == null){%>
	 <li  style="font-size:24px;text-height: 26px;">
	<a href="/bonusPointsExchange/actionServlet?actionCode=user&methodCode=query_user_info" style="color:#efefef;"><%=session.getAttribute("userName") %></a><span style="color:#efefef;"></li><li></span><a href="/bonusPointsExchange/actionServlet?actionCode=log&methodCode=logout&logType=user" style="color:#efefef;"><span style="font-size:22px;">注销</span></a></li>	
	 <% } else if(isShopLogin != null && isUserLogin == null){%>
	 <li style="font-size:24px; text-height: 26px;">
	<a href="/bonusPointsExchange/QueryShopInfoServlet" style="color:#efefef;"><%=session.getAttribute("shopName") %></a><span style="color:#efefef;"></li><li></span><a href="/bonusPointsExchange/actionServlet?actionCode=log&methodCode=logout&logType=shop" style="color:#efefef;"><span style="font-size:22px;">注销</span></a></li>	
<% }%>

     </ul>
    </div>
  </div>
</div>
<hr  style="background-color:#EBEBEB; height:1px;"/>
</div>