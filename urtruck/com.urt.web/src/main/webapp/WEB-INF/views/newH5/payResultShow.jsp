<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Recharge.successfully' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<div class="cg_info">
			<p class="text-algin"><img src="static/newH5/images/cg_icon.png" width="54" height="54"></p>
			<h1><fmt:message bundle='${pageScope.bundle}'  key='Recharge.successfully' /></h1>
			<span><fmt:formatDate value="${star<fmt:message bundle='${pageScope.bundle}'  key='' />tTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
			<h2><fmt:message bundle='${pageScope.bundle}'  key='Thank.you.for.your.using.lenovo.Connect.to.surf.the.Internet' /></h2>
			<h3><fmt:message bundle='${pageScope.bundle}'  key='It.is.expected.that.the.data.will.be.credited.within.24.hours' /></h3>
			<h4><fmt:message bundle='${pageScope.bundle}'  key='If.you.have.any.questions,please.dial.customer.service.number' />：<a href="wtai://wp//mc;4006410041">4006410041</a></h4>
		</div>
		<hr>
		<div class="d_list">
			<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Order.number' />：${tradeId}</p>
			<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Device.number' />：${iccid}</p>
			<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='data.plan' /><span>无</span>：${goodsName}</p>
			<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' /><span>无</span>：${limitData}</p>
			<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Payment.amount' />：¥${payAmount}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></p>
		</div>
		<div class="nav navbar-fixed-bottom" style="border-bottom:none;">
			<div class="wrap_btn">
				<button class="login_btn active_login" onclick="window.location.href='glaH5AppQuery/queryChargeRecord?iccid=${iccid}'"><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></button>
			</div>
		</div>
	</div>
</body>
</html>