<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<title><fmt:message bundle='${pageScope.bundle}'  key='XIAODONG.payment' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		var _inow = 0;
		$('.wx').bind('touchstart',function(){
			_inow ++ ;
			console.log(_inow)
			if(_inow % 2 == 1){
				$(this).find('img').attr('src','static/newH5/images/wx_icon3.png');
				$(".login_btn").attr("disabled","disabled");
                $(".login_btn").removeClass("active_login");
			}else{
				$(this).find('img').attr('src','static/newH5/images/wx_icon2.png');
				$(".login_btn").removeAttr("disabled");
                $(".login_btn").addClass("active_login");
			}
			
		});
		/**
		支付倒计时
	**/	
		//CountDown();
		function CountDown(){
			var x = 30,
	            interval;
	        window.onload = function() {
	            var d = new Date("1111/1/1,0:" + x + ":0");
	            interval = setInterval(function() {
	                var m = d.getMinutes();
	                var s = d.getSeconds();
	                m = m < 10 ? "0" + m : m;
	                s = s < 10 ? "0" + s : s;
	                $('.zf_time p span').text( m + "分" + s +"秒");
	                if (m == 0 && s == 0) {
	                    clearInterval(interval);
	                    $(".login_btn").attr("disabled","disabled");
	                    $(".login_btn").removeClass("active_login");
	                    return;
	                }
	                d.setSeconds(s - 1);
	            }, 1000);
	        }
        }
	})			
	</script>
</head>
<body style="background:#ededed;">
<div class="wrapper">
	<div class="zf_time">
		<p class="text-center"><fmt:message bundle='${pageScope.bundle}'  key='Please.complete.the.payment.as.soon.as.possible' />！</p>
	</div>
	<div class="zf_info">
		<h4><fmt:message bundle='${pageScope.bundle}'  key='The.order.is.submitted.successfully' />！</h4>
		<p><fmt:message bundle='${pageScope.bundle}'  key='Device.number' />：${iccid}</p>
		<p><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />：${goodsName}</p>
		<p><fmt:message bundle='${pageScope.bundle}'  key='Payment' />：￥${product.releasePrice}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></p>
	</div>
	
	<form action="glaH5AppPay/torealPay" method="post" id="chargeForm">
     <input  type="hidden" name="iccid" value="${iccid}">
     <input  type="hidden" name="goodsReleaseId" value="${goodsReleaseId}">
     <input type="hidden" name="payWay" value="1" >
	<div class="zf_info">
		<p class="row" style="padding-bottom:0">
			<span class="col-xs-10 zf_s">
				<strong><fmt:message bundle='${pageScope.bundle}'  key='paying.through.Wechat' /></strong><fmt:message bundle='${pageScope.bundle}'  key='The.use.of.WeChat.payment,safe.and.convenient' />
			</span>
			<span class="col-xs-2 wx">
			<img src="static/newH5/images/wx_icon2.png" class="img-responsive zf_r pull-right" width="22" height="22">
			</span>
		</p>
	</div>
	</form>
	
	<div class="nav navbar-fixed-bottom" style="border-bottom:none;">
		<div class="wrap_btn">
			<button class="login_btn active_login" onclick="$('#chargeForm').submit()"><fmt:message bundle='${pageScope.bundle}'  key='payment.sure' /></button>
		</div>
	</div>
	
</div>
</body>
</html>