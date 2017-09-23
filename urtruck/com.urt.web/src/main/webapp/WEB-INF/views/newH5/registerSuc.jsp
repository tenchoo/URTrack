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
	<base href="<%=basePath%>" />
	<title><fmt:message bundle='${pageScope.bundle}'  key='registered.successfully' /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
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
		<jsp:include page="logo.jsp" flush="true"/>
		<div class="r_ok text-center">
			<img src="static/newH5/images/ok.png" class="img-responsive">
		</div>
		<div class="wrap_btn wrap_btn2">
			<h3 class="text-center r_info"><fmt:message bundle='${pageScope.bundle}'  key='registered.successfully' /></h3>
			<button class="login_btn active_login" onclick="window.location.href='/glaH5App/login'"><fmt:message bundle='${pageScope.bundle}'  key='immediately.on' /></button>
		</div>
	</div>
</body>
</html>