<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
	<title><fmt:message bundle='${pageScope.bundle}'  key='user.sign.in' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/newH5/js/login.js"></script>
	<script type="text/javascript" src="static/js/jquery.form.js"></script> 
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="logo.jsp" flush="true"/>
		<form name="login" method="post" id="login_form" autocomplete="off">
			<div class="zc_info">
				<ul>
					<li>
						<div class="form_info row">
							<label class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='Account.number' /></label>
							<input type="text"  id="userName" class="col-xs-9" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.account.number' />" name="account">
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="16" height="16"><span><fmt:message bundle='${pageScope.bundle}'  key='Account.input.error' /></span>
						</div>
					</li>
					<li>
						<div class="form_info row">
							<label  class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='Password' /></label>
							<input type="email"  id="pwd" class="col-xs-9" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" name="password" >
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="16" height="16"><span><fmt:message bundle='${pageScope.bundle}'  key='Incorrect.password.range.or.syntax' /></span>
						</div>
					</li>
					<li>
						<div class="form_info row">
							<label  class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='verify.code' /></label>
							<input type="text" id="yzm" class="col-xs-5" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.verification.code' />" name="captcha" >
							<!-- <button class="yzm pull-right">获取验证码</button> -->
							<!-- <span class="yzm">HSL92</span> -->
							<span class="yzm_w">
								<img id="captcha" onclick="refreshCaptcha()" src="/servlet/captchaCode" style="vertical-align: middle; border-radius: 0px;">
							</span>
						</div>
						<div class="error row">
							<img src="static/newH5/images/error.png" width="16" height="16"><span><fmt:message bundle='${pageScope.bundle}'  key='Incorrect.input.for.verification.code' /></span>
						</div>
					</li>
				</ul>
			</div>
		</form>
		<div class="wrap_btn wrap_btn2">
			<!-- <p class="text-center lenove_btn"><a href="">Lenove ID 登录</a></p> -->
			<button class="login_btn active_login"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></button>
			<p class="ts"><span class="pull-right" onclick="reset()"><fmt:message bundle='${pageScope.bundle}'  key='Forgot.password' /></span></p>
		</div>
		<!--<span class="pull-left registration" onclick="window.location.href='glaH5App/toRegister'">手机快速注册</span>  -->
		<script type="text/javascript">
			function reset(){
				window.location.href='glaH5App/toResetPwd?phone='+$("#userName").val();
			}
		</script>
	</div>
</body>
	
</html>