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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Forgot.password' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/newH5/js/findpassword.js" ></script>
	<link rel="stylesheet" href="static/h5/css/layer.css" id="layui_layer_skinlayercss">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/h5/js/layer.js"></script>
	<script type="text/javascript" src="static/js/jquery.form.js"></script> 
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="logo.jsp" flush="true"/>
		<form action="glaH5App/resetPwd" method="post" id="findpassword" autocomplete="off">
			<div class="zc_info">
				<ul>
					<li>
						<div class="form_info row">
							<label class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='mobliephone.number' /></label>
							<input type="tel" class="col-xs-8 phone" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.account.number' />" id="phone" value="${phone}" name="loginName">
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="18" height="18"><span><fmt:message bundle='${pageScope.bundle}'  key='The.mobile.number.has.been.registered' /></span>
						</div>
					</li>
					<li>
						<div class="form_info row">
							<label  class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='verify.code' /></label>
							<input type="email" class="col-xs-5" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.verification.code' />" id="codeid" name="salt">
							<button class="yzm pull-right" disabled="disabled"><fmt:message bundle='${pageScope.bundle}'  key='Get.validation.code' /></button>
							<!-- <span class="yzm">HSL92</span> -->
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="18" height="18"><span><fmt:message bundle='${pageScope.bundle}'  key='Incorrect.input.for.verification.code' /></span>
						</div>
					</li>
					<li>
						<div class="form_info row">
							<label  class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='enter.password' /></label>
							<input type="email" class="col-xs-8" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='enter.password' />" id="pwdid" name="plainPassword">
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="18" height="18"><span>
							<fmt:message bundle='${pageScope.bundle}'  key=' 6~16.digits,letters.(case.sensitive)' /></span>
						</div>
					</li>
					<li>
						<div class="form_info row">
							<label  class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='Re-enter.password' /></label>
							<input type="email" class="col-xs-8" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='confirm.password' />" id="pwdid2" name="confirmPassword">
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="18" height="18"><span><fmt:message bundle='${pageScope.bundle}'  key='Two.passwords.are.not.the.same' /></span>
						</div>
					</li>
					
				</ul>
			</div>
		</form>
		<div class="wrap_btn wrap_btn2">
			<button class="login_btn"><fmt:message bundle='${pageScope.bundle}'  key='Sure' /></button>
		</div>
		<div class="cue">
			<p><fmt:message bundle='${pageScope.bundle}'  key='To.ensure.business.security,if.the.enterprise' /></p>
		</div>
	</div>
	<div class="popup_mask"></div>
	<div class="popup">
		<h3 class="text-center"><fmt:message bundle='${pageScope.bundle}'  key='Your.password.has.been.changed.successfully' /></h3>
		<p class="text-center"><a href="glaH5App/login" ><fmt:message bundle='${pageScope.bundle}'  key='Log.in.again' /></a></p>
	</div>
</html>