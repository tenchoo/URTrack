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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Activate.new.device' /></title>
	<base href="<%=basePath%>"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/h5/js/idcard.js"></script>
	<script type="text/javascript" src="static/newH5/js/device.js"></script>
	<script type="text/javascript" src="static/h5/js/layer/layer.js"></script>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<form id="form_decice"  method="post" autocomplete="off">
			<div class="device">
				<p class="row">
					<label class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='device.number' /></label>
					<input type="tel" class="col-xs-10" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.19.bit.device.number' />" name="iccid" id="equipment">
				</p>
				<p class="error row">
					<span class="col-xs-10">
					<img src="static/newH5/images/error.png" width="16" height="16">
						<em><fmt:message bundle='${pageScope.bundle}'  key='Device.number.error' /></em>
					</span>
				</p>
				<p class="row clerafix">
					<label class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='activation code' /></label>
					<input type="text" class="col-xs-10" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.activation.code' />" id="activation" name="privatekey">
				</p>
				<p class="error row">
					<span class="col-xs-10">
					<img src="static/newH5/images/error.png" width="16" height="16">
						<em><fmt:message bundle='${pageScope.bundle}'  key='Activation.code.error' /></em>
					</span>
				</p>
				<p class="row">
					<label class="col-xs-2">姓名<fmt:message bundle='${pageScope.bundle}'  key='name' /></label>
					<input type="text" class="col-xs-10" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.name' />" id="nameId" name="username">
				</p>
				<p class="error row">
					<span class="col-xs-10">
					<img src="static/newH5/images/error.png" width="16" height="16">
						<em><fmt:message bundle='${pageScope.bundle}'  key='name.error' /></em>
					</span>
				</p>
				<p class="row">
					<label class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='ID.card' /></label>
					<input type="tel" class="col-xs-10" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.ID.card' />" id="userNameId" name="idcard" >
				</p>
				<p class="error row">
					<span class="col-xs-10">
					<img src="static/newH5/images/error.png" width="16" height="16">
						<em><fmt:message bundle='${pageScope.bundle}'  key='The ID number error' /></em>
					</span>
				</p>
				<p class="row">
					<label class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='Type' /></label>
					<select class="col-xs-10">
						<option><fmt:message bundle='${pageScope.bundle}'  key='tablet.computer' /></option>
						<option><fmt:message bundle='${pageScope.bundle}'  key='mobliephone' /></option>
					</select>
				</p>
			</div>
		</form>
		<p class="xy"><label><img src="static/newH5/images/gray_checked.png" width="20" height="14"><fmt:message bundle='${pageScope.bundle}'  key='I.have.read.and.agreed.to.the' /></label><span onclick="window.location.href='glaH5App/protocol'">《<fmt:message bundle='${pageScope.bundle}'  key='access.agreement' />》</span></p>
		<div class="explain">
			<fmt:message bundle='${pageScope.bundle}'  key='explain' />：<br/>
			<p>1. 设备号及激活码位于设备后盖内，请正确填写；<fmt:message bundle='${pageScope.bundle}'  key='' /></p>
			<p>2. 上网卡一旦激活将不支持退卡操作；<fmt:message bundle='${pageScope.bundle}'  key='' /></p>
			<p>3. 如设备终端存在问题请直接到销售点进行检测处理。<fmt:message bundle='${pageScope.bundle}'  key='' /></p>
			<p>4. 根据国家通信管理局相关政策规定，同时也保障用户自 身的权益，懂的通信上网卡严格按照国家规定执行入网 实名制。请务必正确填写使用人真实的身份证信息。<fmt:message bundle='${pageScope.bundle}'  key='' /></p>
    		<p>5. 对于上网卡无法激活或使用过程中存在异常,可直接致电 懂的通信客服热线4004610041。<fmt:message bundle='${pageScope.bundle}'  key='' /></p>
		</div>
		<div class="nav navbar-fixed-bottom" style="border-bottom:none">
			<div class="wrap_btn">
				<button class="login_btn" checked id="activeBtn"><fmt:message bundle='${pageScope.bundle}'  key='Activate' /></button>
			</div>
		</div>
	</div>
</body>
</html>