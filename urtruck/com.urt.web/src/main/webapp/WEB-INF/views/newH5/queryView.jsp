<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Balance.inquiry' /></title>
	<base href="<%=basePath%>"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/newH5/js/query.js"></script>
	<script type="text/javascript" src="static/newH5/js/search_data.js"></script>
	<script src="static/h5/js/layer/layer.js"></script>
	<style type="text/css">
	*{margin:0;padding:0;}
	html{font-family:"微软雅黑";}
	h2{
		width:186px;
		margin:0 auto;
		font-size:16px;
		font-weight:normal;
		text-align:center;
		margin-top:10px;
	}
	#box{
		width:290px;
		margin:0 auto;
		/*height:320px;*/
		background:#fff;
		position:relative;
		/*border-bottom:1px solid #ccc;*/
	}
	canvas{
		background:#fff;
		transform: all 1s;
	}
	#box p{
		position:absolute;
		top:107px;
		left:93px;
		color:#fff;
		text-align:center;
		font-size:14px;
	}
	#box p span.total{
		display:block;
		font-size:20px;
		margin-top:6px;
	}
	.total em{
		font-size:12px;
	}
	#box p span.day{
		display:block;
		font-size:18px;
		margin-top:6px;
	}
	</style>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<div class="search row">
			<label class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='User.equipment' /></label>
			<input type="tel" class="col-xs-9" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />" id="iccid">
			<span><img src="static/newH5/images/search.png" class="img-responsive" onclick="seachIccid()"></span>
			<div class="data_list" id="append">
				<ul>
					<c:if test="${not empty glaIccidList}">
						<c:forEach items="${glaIccidList}" var="item">
							<li data-value="${item}">•••${fn:substring(item,11,19)}</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
		<div  id="box" class="data_model r_ok text-center r_ok2">
			<!-- <img src="images/search_data.png" class="img-responsive"> -->
			<!-- <p>当前流量包已使用300M</p> -->
			<canvas width="290" height="290" id="cvs"></canvas>
			<p><fmt:message bundle='${pageScope.bundle}'  key='The.remaining.data.of.this.month' /><span class="total">700M</span><span class="day"><fmt:message bundle='${pageScope.bundle}'  key='remainnig' />12<fmt:message bundle='${pageScope.bundle}'  key='Day' /></span></p>
		</div>
		<div class="record">
			<span onclick="queryClick()">
				<img src="static/newH5/images/xf_icon.png" width="16" height="16"><fmt:message bundle='${pageScope.bundle}'  key='expendse.records' />
			</span>
			<span onclick="charge()">
				<img src="static/newH5/images/c_icon.png" width="16" height="16"><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' />
			</span>
		</div>
		<div class="l_d">
			<p class="clerafix"><span class="pull-left" id="updateTime"><fmt:message bundle='${pageScope.bundle}'  key='Last.update.time' /><br/>2017.03.30 13:30</span>
			<span class="pull-right" id="packet"><fmt:message bundle='${pageScope.bundle}'  key='data.plan' />6<br/><fmt:message bundle='${pageScope.bundle}'  key='the.total.flow' />700M</span></p>
		</div>
		<div class="nav navbar-fixed-bottom" style="margin:0 15px;border-bottom:none;">
			<button class="login_btn active_login" onclick="doCharge()"><fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' /></button>
			</nav>
		</div>
	<script type="text/javascript">
		var whole ;
		function seachIccid(){
			var iccid = $("#iccid").val();
			if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
			console.log("iccid:",iccid);
			var parent = /\d{8}$/ //
			if( parent.test(iccid)){
				$.ajax({
		   			url:"glaH5AppPay/queryIccid",
		   			data:{
		   				"iccid":iccid,			
		   			},
		   			success:function(result){
		   				var data1 = {'iccid' : result};
		   				console.log("iccid result:",result);
		   				whole = result;
		   				if(result.indexOf('898602') > -1 || result.indexOf('898603') > -1 ){
		   					layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='China.Mobile.and.China.telecom.card.are.not.supported.for.the.inquiry' />");
		   				}else{
		   					if(result=="" || result == null){
		   						layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.device.number' />！");
		   					}else{
								request(data1);
		   					}
		   				}
		   			},
		   			error:function(){
		   				layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.device.number' />！");
	                }
				});
			}else{
				layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.device.number' />！");
			}	
		};
		
		function queryClick(){
			var iccid = $("#iccid").val();
			if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
			if(typeof(whole) == 'undefined' && iccid ==''){
				layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.device.number.correctly' />");
			}else{
				if(typeof(whole) == 'undefined'){
					window.location.href='glaH5AppQuery/queryRecord?iccid='+iccid;
				}else{
					window.location.href='glaH5AppQuery/queryRecord?iccid='+whole;
				}
			}
		}
		function charge(){
			var iccid = $("#iccid").val();
			if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
			window.location.href='glaH5AppQuery/queryChargeRecord?iccid='+iccid;
		}
		
		function doCharge(){
			var iccid = $("#iccid").val();
			if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
			window.location.href='/glaH5AppPay/toChargeView?iccid='+iccid;
		}
		
		$(function() {
		dataFlag = false;
		 var init = {
   	   		packageRemaining:0,  //设置当前包剩余流量
			dataRemaining:0,						   //设置剩余总流量
   			packageExpirationDate:"2017-06-04",//设置当前包有效时间
   			packageNum:0,								//设置包个数
   			packageCount:0, //设置当前包总流量
   			x:145,     //圆心得坐标
   			y:145,
   			c:120     //圆的半径
   		}
   		//流量监控图
   		yp(init); 
		<c:if test="${not empty glaIccidList}">
		    var dataList = $('.data_list ul').find('li');
		    if(dataList.length == 1){
				$('.data_list ul').find('li:first').trigger("click");
				seachIccid();
		    }
		</c:if>
		})
	</script>
	</div>
</body>
</html>