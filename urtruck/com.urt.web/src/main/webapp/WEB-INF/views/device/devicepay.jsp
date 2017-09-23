<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title><fmt:message bundle='${pageScope.bundle}'  key='XIAODONG.payment' /></title>
<link rel="icon" href="//sp.jd.com/payment/1.3.0/css/i/ico.ico"
	type="image/x-icon" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<link rel="stylesheet" type="text/css"
	href="//sp.jd.com/payment/1.3.0/css/main.css">
<link rel="stylesheet" type="text/css"
	href="//sp.jd.com/payment/1.2.0/css/bankList.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/misc/js/lib/md5.js"></script>
<script type="text/javascript"
	src="//sp.jd.com/payment/1.3.0/js/common.js"></script>
<script type="text/javascript">
</script>
<script type="text/javascript"
	src="/misc/js/cashier/prefs_jd.js?verson=201611171"></script>
<script type="text/javascript"
	src="/misc/js_release/cashier.js?verson=201611171"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.qrcode.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#code").qrcode({
		render : "table", //table方式 
		width : 290, //宽度 
		height : 290, //高度 
		text : "${codeUrl}" //任意内容 
	});
});
	
</script>
</head>
<body>

	<!-- p-header -->
	<div class="p-header">
		<div class="w">
			<div id="logo">
				<img width="170" height="28"
					src="${pageContext.request.contextPath}/static/toWeb/images/logo_1.png" alt="懂的 收银台">
			</div>
		</div>
	</div>
	<!-- p-header end -->
	<div class="main">
		<div class="w">
			<div class="order">
				<div class="o-left">
					<h3 class="o-title"><fmt:message bundle='${pageScope.bundle}'  key='The.valid.time.of.the.QR.code.is.only.5.minutes' />：<span id="orderId">${outTradeNo}</span></h3>
					<p class="o-tips"></p>
				</div>
				<div class="o-right">
					<div class="o-price">
						<em><fmt:message bundle='${pageScope.bundle}'  key='Payment' /></em><strong>${orderPrice}</strong><em><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></em>
					</div>
				</div>
				<div class="clr"></div>
				<div class="o-list j_orderList" id="listPayOrderInfo">
					<div class="o-list-info">
						<span class="mr10" id="shdz"></span> <span class="mr10" id="shr"></span>
						<span id="mobile"></span>
					</div>
					<div class="o-list-info">
						<span id="spmc"></span>
					</div>
				</div>
			</div>
			<div class="payment">
				<!-- 微信支付 -->
				<div class="pay-weixin">
					<div class="p-w-hd"><fmt:message bundle='${pageScope.bundle}'  key='paying.through.Wechat' /></div>
					<div class="p-w-bd" style="position: relative">
						<!-- <div class="j_weixinInfo"
							style="position: absolute; top: -36px; left: 130px;">
							距离二维码过期还剩<span class="j_qrCodeCountdown font-bold font-red">45</span>秒，过期后请刷新页面重新获取二维码。
						</div> -->
						<c:if test="${!empty token}">
							<div class="p-w-box">
							     <div class="pw-box-hd" id="code">
							     </div>
								<div class="pw-box-ft">
									 <p><fmt:message bundle='${pageScope.bundle}'  key='Please.use.WeChat.to.sweep.away' /></p>
									<p><fmt:message bundle='${pageScope.bundle}'  key='Payment.through.Scannig.QR.code' /></p> 
									<%-- <img width="200" height="40" src="${pageContext.request.contextPath}/static/device/tishi.png"> --%>
								</div>
							</div>
						</c:if>
						<div class="p-w-sidebar"></div> 
					</div>
				</div>
				<!-- 微信支付 end -->
			</div>
		</div>
	</div>
	<div class="p-footer">
		<div class="pf-wrap w">
			<div class="pf-line">
				<span class="pf-l-copyright">Copyright &copy; 2004-2017
					<fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' />.com <fmt:message bundle='${pageScope.bundle}'  key='All.rights.reserved' /></span> 
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		window.setInterval("huiDiao()",5000);
		function huiDiao(){
			$.ajax({
				url:"${ctx}/customerQuery/queryOrder",
				data: {"orderId": $("#orderId").html(), },
				success:function(result){
					if(result.chargeMsg==1){
						//去掉定时器的方法 
						window.location.href="${ctx}/customerQuery/toRateSearch2?chargeMsg="+"<fmt:message bundle='${pageScope.bundle}'  key='Recharge.successfully' />！";
					}else if(result.chargeMsg==0){
						window.location.href=="${ctx}/customerQuery/toRecharge?chargeMsg="+"<fmt:message bundle='${pageScope.bundle}'  key='Recharge.failed' />！";
					}
				}
			});
			
		}
	</script>
</body>

</html>