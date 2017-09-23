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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Recharge.help' /></title>
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
<div class="wrapper">
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
	</div>	
	<div class="tab_main">
		 <ul class="nav nav-tabs first_leveltree"> 
			 <li class="active"><a href="#1" data-toggle="tab"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.wifi' /></a></li>
			 <li><a href="#2" data-toggle="tab"><fmt:message bundle='${pageScope.bundle}'  key='laptap' /></a></li> 
			 <li><a href="#3" data-toggle="tab"><fmt:message bundle='${pageScope.bundle}'  key='tablet.computer' /></a></li> 
		 </ul>  
		  <div class="tab-content first_levelcontent">    
			  <div class="tab-pane active" id="1">      
			 		<p class="ask"><fmt:message bundle='${pageScope.bundle}'  key='About.telecom.recharge.instructions' />。1</p>  
			 		<p class="answer"><fmt:message bundle='${pageScope.bundle}'  key='Corresponding.service.provider.will.provide.prepaid.recharge' /></p>  
					<hr>
					<p class="ask">关于电信充值说明。1</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。</p>  
					<hr>
					<p class="ask">关于电信充值说明。1</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。</p>  
					<hr>
					<p class="ask">关于电信充值说明。1</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。</p>  
					<hr>
					<p class="ask">关于电信充值说明。1</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。</p>  
					<hr>
					<p class="ask">关于电信充值说明。1</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。</p>  
					<hr>
			  </div>    
			  <div class="tab-pane" id="2">      
			  		<p class="ask">关于电信充值说明。2</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。222222</p>
					<hr> 
					<p class="ask">关于电信充值说明。2</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。222222</p>
					<hr> 
					<p class="ask">关于电信充值说明。2</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。222222</p>
					<hr> 
					<p class="ask">关于电信充值说明。2</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。222222</p>
					<hr> 
					<p class="ask">关于电信充值说明。2</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。222222</p>
					<hr>   
			  </div>  
			   <div class="tab-pane" id="3">      
			  		<p class="ask">关于电信充值说明。3</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。333333</p>
					<hr> 
					<p class="ask">关于电信充值说明。3</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。333333</p>
					<hr> 
					<p class="ask">关于电信充值说明。3</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。333333</p>
					<hr> 
					<p class="ask">关于电信充值说明。3</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。333333</p>
					<hr> 
					<p class="ask">关于电信充值说明。3</p>  
			 		<p class="answer">电信话费充值为卡直充，由相应服务商提供话费充值发票，
中国电信不提供话费充值发票。333333</p>
					<hr> 
			  </div> 
		  </div>
	</div>
</div>
</body>
</html>