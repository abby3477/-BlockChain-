<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
//商家需登录之后才能修改xiangguan信息
String oldShopName = (String)request.getSession().getAttribute("shopName");	
if(oldShopName == null) { %>
	<script type="text/javascript" language="javascript">
		alert("您还没有登录！请登录！");    // 弹出错误信息
		window.location.href="/bonusPointsExchange/login_shop.jsp" ;                             
	</script>	
<% }%>


<%
	String shopChangeResult = (String)request.getAttribute("shopChangeResult");  //获取修改商家信息是否成功
	if(shopChangeResult == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("修改信息成功！");                                    // 弹出错误信息
	</script>	
<% } else if(shopChangeResult == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("修改信息失败！");                                    
	</script>	
<% }%>

<%
String shopChangePwdResult = (String)request.getAttribute("shopChangePwdResult");  //获取修改商家密码是否成功
if(shopChangePwdResult == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("修改密码失败！");                                    
	</script>	
<% }%>

<%
	String uploadRes = (String)request.getAttribute("uploadRes");  //获取上传头像是否成功
	if(uploadRes == "Y") {
%>
	<script type="text/javascript" language="javascript">
		alert("上传头像成功！");                                    // 弹出错误信息
	</script>	
<% } else if(uploadRes == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("上传头像失败！");                                    
	</script>	
<% }%>

<%
String uploadTypeErr = (String)request.getAttribute("uploadTypeErr");  //上传图像类型错误
if(uploadTypeErr == "N") {%>
	<script type="text/javascript" language="javascript">
		alert("只能上传jpg、png、gif、bmp类型的图片");                                   
	</script>	
<% }%>

<!doctype html>
<html>
<head>
<style type="text/css">
#mask{ 
	background-color:#ccc;
	opacity:0.5;
	filter: alpha(opacity=50); 
	position:absolute; 
	left:0;
	top:0;
	z-index:1000;
	}
</style>
<meta charset="utf-8">
<title>商家中心</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/main.css">
<link href="<%=basePath%>css/footer.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/personal.css">
<link href="jQueryAssets/jquery.ui.core.min.css" rel="stylesheet" type="text/css">
<link href="jQueryAssets/jquery.ui.theme.min.css" rel="stylesheet" type="text/css">
<link href="jQueryAssets/jquery.ui.accordion.min.css" rel="stylesheet" type="text/css">
<link href="jQueryAssets/jquery.ui.button.min.css" rel="stylesheet" type="text/css">
<script src="jQueryAssets/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="jQueryAssets/jquery-ui-1.9.2.accordion.custom.min.js" type="text/javascript"></script>
<script src="jQueryAssets/jquery-ui-1.9.2.button.custom.min.js" type="text/javascript"></script>
<script src="Echarts/echarts.common.min.js"></script>
<link rel="Shortcut Icon" href="images/bonus.jpg">
</head>
<body>
<!--这是top-->
	<%@ include file="header.jsp" %>
<!--这是main_page-->
<div class="personal">
  <div class="span7">
    <div id="Accordion1" class="nav" style="height:200px;">
      <h3><a href="#">商家信息</a></h3>
      <div class="index">
        <p><a href="javascript:showDiv(1)">修改信息</a></p>
        <p><a href="javascript:showDiv(2)">修改密码</a></p>
      </div>
 <!--
      <h3><a href="#">商业化</a></h3>
      <div class="index">
        <p><a href="javascript:showDiv(3)">添加广告</a></p>
        <p><a href="javascript:showDiv(4)">积分商城</a></p>
      </div>
-->
      <h3><a href="#">智能分析</a></h3>
      <div class="index">
        <p><a href="javascript:showDiv(3)">用户量分析</a></p>
        <p><a href="javascript:showDiv(4)">积分变化分析</a></p>
      </div>
    </div>
  </div>
  <div class="span8">
    <div id="div1">
      <p class="title">商家资料 <span class="title1">USER INFOMATION</span></p>
        <table>
         <tr>
            <td>商家图标：</td>
            <td class="mid">            
             <form id="icon" enctype="multipart/form-data" method="post" action="/bonusPointsExchange/UploadIconServlet">
             	<img alt="头像" src="images/shopLogo/<%=request.getAttribute("imageURL") %>" width="80px" height="60px" />                
                 <input style="display: none;" type="file" title="上传头像" name="fileField" id="fileField" onchange="fileSelected();">
                 <input type="submit" id="uploadBtn" hidefocus="true" value="上传头像" />
             </form>                                          
			</td>
          </tr>
         <form onsubmit="return checkForm();" action="/bonusPointsExchange/ShopChangeInfo" method="post">
          <tr>
            <td style="display: none" ><input name="imageURL" type="text" id="imageURL" value="<%=request.getAttribute("imageURL") %>"</td>
            <td>商家名称：</td>
            <td><input name="name" readonly="readonly"  type="text" id="name" value="<%=session.getAttribute("shopName")%>" maxlength="40" style="border:none;"></td>
          </tr>
          <tr>
            <td>邮&nbsp;箱：</td>
            <td><input name="email" type="text" id="email" value="<%=request.getAttribute("email")%>" maxlength="40"></td>
          </tr>
          <tr>
            <td>电&nbsp;话：</td>
            <td><input name="phone" type="text" id="phone" value="<%=request.getAttribute("telephone")%>" maxlength="40"></td>
          </tr>
          <tr>
            <td>简&nbsp;介：</td>
            <td><input name="description" type="textarea" id="description" value="<%=request.getAttribute("shopDec")%>" maxlength="40"></td>
          </tr>
          <tr>
            <td colspan="2"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td   
          ></tr>
          </form>
        </table>
      
    </div>
    <div id="div2">
      <p class="title">修改密码 <span class="title1">SHOP　PASSWORD</span></p>
      <form onsubmit="return changePawcheckFrom();" method="post" action="/bonusPointsExchange/ShopChangePwdServlet">
        <table>
          <tr>
            <td>账&nbsp;号：</td>
            <td><input name="userName" type="text" class="no-border" id="userName" value="<%=session.getAttribute("shopName")%>" maxlength="20" readonly></td>
          </tr>
         <!--<tr>
            <td>旧密码：</td>
            <td><input name="oldPassword" type="password" id="oldPassword" maxlength="20"></td>
          </tr>
          -->
          <tr>
            <td>新密码：</td>
            <td><input name="newPassword" type="password" id="newPassword" maxlength="20"></td>
          </tr>
          <tr>
            <td>再次输入新密码：</td>
            <td><input name="reNewPassword" type="password" id="reNewPassword" maxlength="20"></td>
          </tr>
          <tr>
            <td colspan="2" ><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
      </form>
    </div>
<!--
    <div id="div3">
      <p class="title">添加广告<span class="title1">ADD ADVERTISEMENT</span></p>
      <form>
        <table>
          <tr>
            <td>广告标题：</td>
            <td><input name="adName" type="text" class="adName" id="adName"  maxlength="40"></td>
          </tr>
          <tr>
            <td>广告图片&nbsp;：</td>
            <td class="mid"><input name="selectImage" type="submit" class="submitBtn" id="selectImage" value="选择"></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
      </form>
    </div>
    <div id="div4">
      <p class="title">积分商城<span class="title1">POINTS SHOP</span></p>
      <form>
        <table>
          <tr>
            <td>商城网址:</td>
            <td><input name="url" type="text" class="inputUrl" id="url"></td>
          </tr>
          <tr>
            <td colspan="2" class="mid"><input name="submit" type="submit" class="submitBtn" id="submit" value="提交"></td>
              </td>
          </tr>
        </table>
      </form>
    </div>
-->
    <div id="div3">
      <p class="title">用户量分析<span class="title1">USER AMOUNT ANALYSIS</span></p>
      <h3 style="text-align: center">一星期用户变化折线图</h3>
      <div id="mainDiv" style="width: 700px;height:400px;"></div>
    </div>
    <div id="div4">
      <p class="title">积分变化分析<span class="title1">POINTS ANALYSIS</span></p>
      <table>
          <td>今日入积分：</td>
          <td><input name="inPoint" type="text" id="inPoint" class="no-border" value="0" maxlength="40" readonly></td>
        </tr>
        <tr>
          <td>今日出积分：</td>
          <td><input name="outPoint" type="text" id="outPoint" class="no-border" value="0" maxlength="40" readonly></td>
        </tr>
      </table>
      <h3 style="text-align: center">积分变化表</h3>
      <div id="mainDiv2" style="width: 700px;height:400px;"></div>
    </div>
  </div>
</div>
<!--这是bottom-->
	<%@ include file="footer.jsp" %>


<script type="text/javascript">
	
</script>

<script type="text/javascript">
function showDiv(index){   
var show=parseInt(index);
for(i=1;i<=4;i++){
	document.getElementById('div'+i).style.display = "none";}
	document.getElementById('div'+index).style.display = "block";
	if(show == 3) {
		loadDataAndShow();
	}
	if(show == 4) {
		loadDataAndShow2();
	}

} 
//积分变化量分析
function loadDataAndShow2(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('mainDiv2'));
	myChart.showLoading({text : '正在努力为您加载中...',});
	// 指定图表的配置项和数据
	var option = {
		  tooltip: {//不显示待解决
        		trigger: 'axis',
        		axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            	}
    		},
    		legend: {
       			 data:['商家每日转入积分(平台到商家)','商家每日转出积分(商家到平台)'],
   		 	},
   		 	
          xAxis : [
              {
              	type:'category',  
       	 		axisLabel:{ interval: 0 }  
              }
          ],
          yAxis : [
              {
                  type : 'value',
              }
          ],
          series : [
              {
              		name:'商家每日转入积分(平台到商家)',
              		type:'bar',
              },
              {
              		name:'商家每日转出积分(商家到平台)',
              		type:'bar',
              }
          ]
      };
	
	 $.ajax({
	       type : "post",
	       async : false, //同步执行
	       url : "/bonusPointsExchange/QueryShopPointInOutNumServ",
	       data : {},
	       dataType : "json", //返回数据形式为json
	       success : function(result) {
	       //alert(result[0].time);
	                  if (result) {
	                         //初始化option.xAxis[0]中的data
	                          option.xAxis[0].data=[];
	                          for(var i = 0;i < result.length;i++){
	                            option.xAxis[0].data.push(result[i].time);
	                          }
	                          //初始化option.series[0]中的data
	                          option.series[0].data=[];
	                          option.series[1].data=[];
	                          for(var i = 0;i < result.length;i++){
	                            option.series[0].data.push(result[i].inNum);//入积分
	                            option.series[1].data.push(result[i].outNum);//出积分
	                          }
				                var d = new Date();
								var nowTime = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
				                //alert(nowTime);
				                result[result.length - 1].inNum;
				                var time = result[result.length - 1].time;
				                var inNum = result[result.length - 1].inNum;
				                var outNum = result[result.length - 1].outNum;
				                if(nowTime == time) {
				                	//alert("aa");
				                	document.getElementById('inPoint').value=inNum;
				                	document.getElementById('outPoint').value=outNum;
	              				}                          
	                   }
	                }
	    });       
	// 使用刚指定的配置项和数据显示图表。
	myChart.hideLoading();//取消loading
	myChart.setOption(option);
}
//用户量分析
function loadDataAndShow(){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('mainDiv'));
	myChart.showLoading({text : '正在努力为您加载中...',});
	// 指定图表的配置项和数据
	var option = {
		  tooltip: {//不显示待解决
        		trigger: 'axis',
    		},
    		legend: {
       			 data:['每日绑定用户量'],
   		 	},
          xAxis : [
              {
              	type:'category',  
              	boundaryGap: false,
       	 		axisLabel:{ interval: 0 }  
              }
          ],
          yAxis : [
              {
                  type : 'value',
                  boundaryGap: false,
              }
          ],
          series : [
              {
              		name:'每日绑定用户量',
              		type:'line',
              }
          ]
      };
	
	 $.ajax({
	       type : "post",
	       async : false, //同步执行
	       url : "/bonusPointsExchange/QueryUserAmountChangeAweekServ",
	       data : {},
	       dataType : "json", //返回数据形式为json
	       success : function(result) {
	       //alert(result[0].time);
	                  if (result) {
	                         //初始化option.xAxis[0]中的data
	                          option.xAxis[0].data=[];
	                          for(var i = 0;i < result.length;i++){
	                            option.xAxis[0].data.push(result[i].time);
	                          }
	                          //初始化option.series[0]中的data
	                          option.series[0].data=[];
	                          for(var i = 0;i < result.length;i++){
	                            option.series[0].data.push(result[i].num);
	                          }
	                   }
	                }
	    });       
	// 使用刚指定的配置项和数据显示图表。
	myChart.hideLoading();//取消loading
	myChart.setOption(option);
}
//修改密码的表单验证
function changePawcheckFrom() {
	
	var oldPassword = document.getElementById("oldPassword").value;
	if (oldPassword == "") {
		alert("旧密码不能为空！");
		return false;
	}
	
	
	var newPassword = document.getElementById("newPassword").value;
	if (newPassword == "") {
		alert("新密码不能为空！");
		return false;
	}
	//密码需在6位以上，只能为数字和字母的组合
	var Reg=/^[0-9A-Za-z]{6,}$/;
  	if(newPassword!=""&&!Reg.test(newPassword)){
     alert("密码只能输入是字母或者数字,长度6位及以上！");
     return false;
	}
	
	var reNewPassword = document.getElementById("reNewPassword").value;
	if (reNewPassword == "") {
		alert("确认密码不能为空！");
		return false;
	}

	if (newPassword != reNewPassword) {
		alert("两次密码必须一样！");
		return false;
	}
	return true;
}



//修改商家信息的表单验证
function checkForm() {
	
	//商家名不能空
	var shopName = document.getElementById("name").value;
	//alert(shopName);
	if (shopName == "") {
		alert("商家名不能为空！");
		return false;
	}
	//邮箱格式要正确
	var email = document.getElementById("email").value;
	//alert(email);
	var expression = /^[a-zA-Z0-9_-]+(\.([a-zA-Z0-9_-])+)*@[a-zA-Z0-9_-]+[.][a-zA-Z0-9_-]+([.][a-zA-Z0-9_-]+)*$/;
	var objExp = new RegExp(expression);
	if(!objExp.test(email)) {
		alert("邮箱格式不正确！");
		return false;
	}
	//电话号码格式要正确
	/*验证固定电话号码
 	 0\d{2,3}   代表区号   
 	 [0\+]\d{2,3}   代表国际区号
	 \d{7,8} 代表7－8位数字(表示电话号码)
 	正确格式：区号-电话号码-分机号(全写|只写电话号码)
	*/
	var reg=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/; 
	var telephone = document.getElementById("phone").value;
	//alert(telephone);
	if(telephone == ""||!reg.test(telephone)){
 		alert("电话号码格式输入错误！");
 		return false;
	}
	return true;
} 

function openNew(){
	/*//获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	//遮罩层
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	//弹出文件选择对话框
	document.getElementById("fileField").click();
	
	//点击遮罩层，取消遮罩	
	oMask.onclick=function(){
		document.body.removeChild(oMask);
	};
	//关闭文件对话框关闭遮罩,这里不知道怎么获取文件打开对话框关闭的事件，所以取消了遮罩这部分
	document.getElementById("fileField").ondestroy=function(){
		document.body.removeChild(oMask);
	};*/
	
	document.getElementById("fileField").click();
}

window.onload=function(){
		var oBtn=document.getElementById("uploadBtn");
			//点击shangchuan按钮
			oBtn.onclick=function(){
				openNew();
				return false;
		}
}

 function fileSelected() {
  	//var str = document.getElementById("fileField").value;//选择的文件路径
 	document.getElementById("icon").submit();
 }
</script>
</body>
</html>


