<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
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
	<title><fmt:message bundle='${pageScope.bundle}'  key='expendse.records' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/h5/js/layer/layer.js"></script>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<div class="search_data row" style="margin-top: 77px;">
			<input type="text" id ="iccid" value="${iccid}" hidden>
			<div class="pull-left col-xs-6">
				<p class="text-center"><fmt:message bundle='${pageScope.bundle}'  key='start.date' /></p>
				<input id="startTime" type="text" readonly="" name="startDate" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.start.date' />" data-lcalendar="2000-01-01,2018-01-29" />
			</div>
			<div class="pull-right col-xs-6">
				<p class="text-center"><fmt:message bundle='${pageScope.bundle}'  key='end.time' /></p>
				<input id="overTime" type="text" readonly="" name="endDate" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.end.time' />" data-lcalendar="2000-01-01,2018-01-29" />
			</div>
		</div>
		<div class="nav" style="margin:0 15px;border-bottom:none;">
			<button class="login_btn"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
		</div>
		<div class="search_data_list">
			<p class="text-center"><img src="static/newH5/images/history.png" width="32%">
				<span><fmt:message bundle='${pageScope.bundle}'  key='No.consumption.record' /></span>
			</p>
		</div>
	</div>	
	<script type="text/javascript" src="static/newH5/js/datePicker.js"></script>
	<script type="text/javascript" src="static/newH5/js/xiaofei_ji_L.js"></script>
	</body>
</html>