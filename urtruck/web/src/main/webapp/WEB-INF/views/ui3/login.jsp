<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Login.and.registration' /></title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
</head>
<body style="overflow:hidden;">
	<div class="wrapper">
		<img src="images/login_bg1.jpg" class="bg">
		<h1 class="logo"><a href="#"><img src="images/logo.png"></a></h1>
		<div class="content">
			<!-- <div class="icons">
				<span class="icon icon_1"><img src="images/icon/38-5.png"></span>
				<span class="icon icon_2"><img src="images/icon/38-4.png"></span>
				<span class="icon icon_3"><img src="images/icon/38-3.png"></span>
				<span class="icon icon_4"><img src="images/icon/38-1.png"></span>
				<span class="icon icon_5"><img src="images/icon/38-2.png"></span>
				<span class="icon icon_6"><img src="images/icon/80-6.png"></span>
			</div> -->
			<div class="form_info">
				<div class="tab_hd">
					<ul>
						<li class="active"><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></a></li>
						<li><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='registration' /></a></li>
					</ul>
				</div>
				<div class="tab_main">
					<form method="post" id="user_from" action="http://www.baidu.com" autocomplete="off">
							<div class="tab_con">
								<ul>
									<li><img src="images/login_icon1.png"><input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.account.number' />" id="username"></li>
									<li><img src="images/login_icon2.png"><input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" id="pwd"></li>
									<li class="yzm_wrap"><img src="images/login_icon3.png"><input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.verification.code' />" id="yzm"><span class="yzm">12345</span></li>
								</ul>
								<p class="agreement">
									<span class="xy"><img src="images/ok_icon.png" data-ok="true">
									<fmt:message bundle='${pageScope.bundle}'  key='Keep.me.signed.in' /></span>
									<span class="zc"><fmt:message bundle='${pageScope.bundle}'  key='New.to.Lenovo.Connect' />?</span>
								</p>
								<button type="button" class="submit_btn linear"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></button>
							</div>
						</form>
						<form method="post" id="zc_from" action="http://www.2345.com" autocomplete="off">
							<div class="tab_con"  style="display: none;margin-top:-23px;">
								<ul >
									<li><img src="images/phone_icon.png"><input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.cell.phone.number' />" id="phone"></li>
									<li><img src="images/login_icon2.png"><input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" id="zc_pwd"></li>
									<li><img src="images/login_icon2.png"><input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='confirm.password' />" id="zc_pwd2"></li>
									<li class="yzm_wrap2"><img src="images/login_icon3.png"><input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.verification.code' />" id="zc_yzm"></li><span class="yzm2 linear"><fmt:message bundle='${pageScope.bundle}'  key='send.phone.code' /></span>
								</ul>
								<p class="agreement agreement2">
									<span class="xy"><img src="images/ok_icon.png" data-ok="true">
									<fmt:message bundle='${pageScope.bundle}'  key='agree.lenovo' /></span><a href="">《<fmt:message bundle='${pageScope.bundle}'  key='Conditions.of.Use' />》、</a><a href="">《<fmt:message bundle='${pageScope.bundle}'  key='Privacy.Notice' />》。</a>
								</p>
								<button type="button" class="submit_btn linear"><fmt:message bundle='${pageScope.bundle}'  key='Register' /></button>
							</div>
						</form>	
					</div>
				</div>
		</div>
		<div class="footer">
			<p>Copyright by <fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' /> GLA v1.0 | <fmt:message bundle='${pageScope.bundle}'  key='Beijing ICP record 11035381' /></p>
		</div>
	</div>
</body>
</html>