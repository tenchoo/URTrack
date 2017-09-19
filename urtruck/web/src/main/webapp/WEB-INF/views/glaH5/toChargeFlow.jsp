<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Equipment.recharge' /></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css"
	type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<style>
* {
	font-family: "冬青黑体", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}

input {
	background-color: transparent;
}

select {
	-webkit-appearance: menulist;
	align-items: center;
	white-space: pre;
	-webkit-rtl-ordering: logical;
	background-color: transparent;
	cursor: default;
	border: none;
	font-size: 3.8vw;
}
</style>
<script src="static/h5/js/main.js"></script>
<link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
<style>
button {
	font-size: :4vw
}
</style>

</head>
<body>
	<div style="background-color: #f4f3f9; font-size: 3.8vw;">
		<jsp:include page="../H5/header.jsp" flush="true" />

		<form class="alipay_content" method="post" id="chargeForm">
			<input type="hidden" name="payName" value="alipay_directPay">
			<input id="alipay_payAmount" type="hidden" name="payAmount">
			<input id="alipay_goodsName" type="hidden" name="goodsName">
			<input id="alipay_goodsId" type="hidden" name="goodsId">
			<div
				style="background-color: #ffffff; border-radius: 0px; padding: 0vw; margin: 1rem auto; padding-bottom: 1rem"
				class="">

				<div style="margin: 0vw 0;" class="row">
					<div class="col-xs-12 ">
						<p style="margin: auto 3vw"></p>
					</div>
					<div
						style="padding-left: 0.8rem; padding-top: 1.5rem; padding-bottom: 1rem;"
						class="col-xs-12 ">
						<div>
							<label style="margin: auto 1rem">ICCID</label>
							<input style="width: 45vw; margin: auto 4vw" id="iccid" name="iccid">
							<a onclick="showProduct()"><fmt:message bundle='${pageScope.bundle}'  key='Determine' /></a>
						</div>
					</div>

				</div>
				<div class="" style="height: 2rem;">
					<p
						style="padding-left: 2rem; padding-top: 0.5rem; margin-bottom: 1rem; font-size: 3.3vw; color: #8c8c8c;"><fmt:message bundle='${pageScope.bundle}'  key='Tip: enter the ICCID.number.correctly.to.avoid.false.charge' /></p>
				</div>
			</div>
			<div style="background-color: #ffffff">
				<div
					style="background-color: #ffffff; margin: 3vw; margin-left: 0; padding: 3vw; padding-left: 2px; padding-bottom: 0; margin-bottom: 0"
					class="row">
					<div style="padding-left: 1.8rem; width: 210px;" class="col-xs-6 ">
						<p>
							流量包<fmt:message bundle='${pageScope.bundle}'  key='data.plan' /> <span id="errorMsg"
								style="padding-left: 2rem; padding-top: 0.5rem; margin-bottom: 1rem; font-size: 3.3vw; color: red;"></span>
						</p>
					</div>
				</div>
				<div style="margin: 3vw; padding: 3vw; margin-top: 3px" class="row" id="flows_content">
				</div>
				<nav style="height: 50px"
					class="navbar navbar-default navbar-fixed-bottom">
				<div style="margin: 0; padding: 0; width: 100%; height: 50px"
					class="container">
					<div style="margin: 0vw; width: 100%; height: 50px" class="row">
						<div
							style="color: black; text-align: right; padding-right: 0; margin: 1.2rem auto; width: 60px;"
							class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='subtotal' />：</div>
						<div
							style="color: red; text-align: left; padding-left: 0; margin: 1.2rem auto; width: 50px; margin-right: 100px"
							class="col-xs-1" id="showPrice"></div>
						<div
							style="border-left: 1px solid #dfe4e1; background-color: #eb641a; height: 50px; margin-bottom: 0vw; text-align: center; padding: auto 0vw; margin-left: -4px"
							class="col-xs-4 col-xs-offset-5 ">
							<a href="javascript:void(0);"  id="submit">
								<h5
									style="font-size: 3.8vw; color: #ffffff; margin-top: 1.2rem; margin-left: 0px;">
									<fmt:message bundle='${pageScope.bundle}'  key='Immediate.payment' /></h5>
							</a>
						</div>
					</div>
				</div>
				</nav>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	//提交表单
	$('#submit').click(function() {
		payAmount = $("#alipay_payAmount").val();
		goodsId = $("#alipay_goodsId").val();
		goodsName = $("#alipay_goodsName").val();
		if (payAmount == '' || payAmount == null) {
			$("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='please.select.data.plan' />")
			return;
		}
		var iccid = $("#iccid").val();
		if (iccid == '' || iccid == null) {
			$("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.bind.device.number' />")
			return;
		}
		$.ajax({
   			url:"<%=basePath %>glaH5/toRealPay",
   			data:{
   				"iccid":iccid,		
   				"payAmount":payAmount,
   				"goodsId":goodsId,
   				"goodsName":goodsName
   			},
   			success:function(data){
   			  json = eval(data);
   			  appId= json.appId;
   		      timeStamp= json.timeStamp;  
   		      nonceStr= json.nonceStr;
   		      prepay_id= json.prepay_id; 
   		      paySign= json.paySign;
   		      signType = json.signType;
   			  pay();
   			},
		   error:function(){
			   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Payment.failed' />！");
			}
   		});
	})
		/*单选菜单*/
		function selectOne(payAmount, goodsDesc, goodsId) {
			$("#alipay_payAmount").val(payAmount);
			$("#alipay_goodsName").val(goodsDesc);
			$("#alipay_goodsId").val(goodsId);
			$("#showPrice").text(payAmount+"<fmt:message bundle='${pageScope.bundle}'  key='yuan' />");
		};
		/*展示产品*/
		function showProduct(){
			$("#errorMsg").html("")
	   		var iccid = $("#iccid").val();
			if (iccid == '' || iccid == null) {
				$("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.bind.device.number' />")
				return;
			}
	   		$.ajax({
	   			url:"laouser/showGoodsRealease",
	   			data:{
	   				"iccid2":iccid,			
	   			},
	   			success:function(data){
	   				var list = data;
	   				$("#flows_content").html("");
	   				if(list == '' || list == null || list.indexOf("<htm") > -1){
	   					   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='The.device.did.not.bind.the.product.in.advance' />")
	   					   return;
	   				}
	   				for ( var i = 0; i < list.length; i++) {
	   					$("#flows_content").append("<div class='col-xs-6 '><a href=\"javascript:selectOne("+list[i].goodsPrices+", '"+list[i].goodsName+"', "+list[i].goodsId+");\" class='thumbnail' goodDesc='"+list[i].goodsDesc+"' payAmount='"+list[i].goodsPrices+"' > <img src='"+list[i].goodsPic+"'></a><p>"+list[i].goodsName+" "+list[i].operatorsName+" "+list[i].goodsPrices+"元<fmt:message bundle='${pageScope.bundle}'  key='' /></p></div>");
	   				}
	   			},
			   error:function(){
				   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Query.product.failed' />！");
				}
	   		});
	   	} ;
	
	   function onBridgeReady(){ 
		   WeixinJSBridge.invoke( 
	        'getBrandWCPayRequest', { 
	          "appId" : appId,   //公众号名称，由商户传入    
	          "timeStamp": timeStamp,     //时间戳，自1970年以来的秒数    
	          "nonceStr" : nonceStr, //随机串    
	          "package" : prepay_id,    
	          "signType" : signType,     //微信签名方式:    
	          "paySign" : paySign, //微信签名  
	        }, 
	        
	        function(res){ 
	        	//alert(res.err_code+"|||"+res.err_desc+"|||"+res.err_msg); 
	            if(res.err_msg == "get_brand_wcpay_request:ok" ){ 
	           		window.location.href="<%=basePath%>glaH5/payShow?payAmount="+$("#alipay_payAmount").val()+"&iccid="+$("#iccid").val()+"&goodsName="+$("#alipay_goodsName").val();
	          	}else if(res.err_msg = "get_brand_wcpay_request：cancel"){
	          		alert("<fmt:message bundle='${pageScope.bundle}'  key='User.cancelled.during.payment.process' />");
	          	}else if(res.err_msg = "get_brand_wcpay_request：fail"){
	          		alert("<fmt:message bundle='${pageScope.bundle}'  key='Payment.failed' />");
	          	}
	        } 
	      );  
	    } 
	    function pay(){ 
	    	if (typeof WeixinJSBridge == "undefined"){
	    		   if( document.addEventListener ){
	    		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    		   }else if (document.attachEvent){
	    		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	    		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    		   }
	    		}else{
	    		   onBridgeReady();
	    		}
	    } 
  </script>
</body>
</html>