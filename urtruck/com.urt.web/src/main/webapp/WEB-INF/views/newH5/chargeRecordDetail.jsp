<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>       
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message bundle='${pageScope.bundle}'  key='Transaction.details' /></title>
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
		<div class="jy_list">
			<ul>
				<li>${tradeFee.goodsDto.goodsName} <fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${tradeFee.goodsDto.releaseCycle}<c:if test="${tradeFee.goodsDto.unit ==0}"><fmt:message bundle='${pageScope.bundle}'  key='month' /></c:if><c:if test="${tradeFee.goodsDto.unit ==1}"><fmt:message bundle='${pageScope.bundle}'  key='Day' /></c:if><br/><span><fmt:formatDate value="${tradeFee.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss" /></span><strong class="pull-right"><span>¥</span> ${tradeFee.fee}</strong></li>
			</ul>
			<div class="d_list">
				<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Order.number' />：${tradeFee.payOrderId}</p>
				<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Device.number' />：${shebei}</p>
				<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='data.plan' /><span>无</span>：${tradeFee.goodsDto.goodsName}</p>
				<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' /><span>无</span>：${tradeFee.goodsDto.releaseCycle}<c:if test="${tradeFee.goodsDto.unit ==0}">月</c:if><c:if test="${tradeFee.goodsDto.unit ==1}">天</c:if></p>
				<p class="row"><fmt:message bundle='${pageScope.bundle}'  key='Payment.amount' />：¥${tradeFee.fee}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></p>
			</div>
		</div>
	</div>
</body>
</html>