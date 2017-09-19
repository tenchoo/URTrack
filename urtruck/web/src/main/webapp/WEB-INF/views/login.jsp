<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<!--tian  -->
	<base href="<%=basePath%>" /> 
	<title><fmt:message bundle='${pageScope.bundle}'  key='Login.and.registration' /></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/newStyle.css">
	<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<!-- <script type="text/javascript" src="js/login.js"></script> -->
	<script type="text/javascript" src="${ctx}/static/ui3/js/loginNew.js"></script>
</head>
<body class="loginBody">
	<!-- 一闪一闪亮晶晶 -->
	<div class="start01"><img src="${ctx}/static/ui3/images/start01.png"></div>
    <div class="start02"><img src="${ctx}/static/ui3/images/start02.png"></div>
    <div class="start03"><img src="${ctx}/static/ui3/images/start03.png"></div>
    <div class="start04"><img src="${ctx}/static/ui3/images/start04.png"></div>
    <div class="start05"><img src="${ctx}/static/ui3/images/start04.png"></div>
    <div class="start06"><img src="${ctx}/static/ui3/images/start04.png"></div>
    <div class="start07"><img src="${ctx}/static/ui3/images/start04.png"></div>
    <div class="start08"><img src="${ctx}/static/ui3/images/start04.png"></div>
	<div class="wrapper">
		<img src="${ctx}/static/ui3/images/login_bg1.jpg" class="bg">
		<!--	<h1 class="logo"><a href="#"><img src="${ctx}/static/ui3/images/logo.png"></a></h1>		-->
			
		<!-- 左侧大动画图 -->
		<div class="starBig">
			<img src="${ctx}/static/ui3/images/starBig.png" alt=""/>
		</div>
		<!-- 右侧小动画图 -->
		<div class="starSmall">
			<img src="${ctx}/static/ui3/images/starSmall.png" alt=""/>
		</div>
		<!-- 右侧文字 -->
		<div class="logoMsg"><img src="${ctx}/static/ui3/images/logoMsg.png"></div>

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
						<li class="loginMenu mgL01 active"><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></a></li>
						<!-- <li class="loginMenu"><a href="#">注册</a></li> -->
						<li class="active qrCode hide"><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='QR.Code.sign.in' /></a></li>
					</ul>
				</div>
				<div class="tab_main">
					<form method="post" id="user_from" class="tabForm" action="${ctx}/login" autocomplete="off">
							<div class="tab_con">
								<ul>
									<li>
										<img src="${ctx}/static/ui3/images/login_icon1.png">
										<input type="text" class="required" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.account.number' />" id="username" name="username">
									</li>
									<li>
										<img src="${ctx}/static/ui3/images/login_icon2.png">
										<input type="password" name="password" class="required" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" id="pwd">
									</li>
									<li class="yzm_wrap">
										<img src="${ctx}/static/ui3/images/login_icon3.png">
										<input type="text" class="required" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.verification.code' />" id="yzm" name="captcha">
										<!-- <span class="yzm">12345</span> -->
										<img id="captcha" class="captcha" src="${ctx}/servlet/captchaCode" onclick="javascript:refreshCaptcha();">
									</li>
								</ul>
								<p class="agreement">
									<span class="xy"><img src="${ctx}/static/ui3/images/ok_icon.png" data-ok="true">
									<fmt:message bundle='${pageScope.bundle}'  key='Keep.me.signed.in' /></span>
									<span class="zc"><fmt:message bundle='${pageScope.bundle}'  key='New.to.Lenovo.Connect' />?</span>
								</p>
								<button type="button" id="btnLogin" class="submit_btn linear"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></button>
							</div>
						</form>
						<!-- <form method="post" id="zc_from" class="tabForm hide" action="http://www.2345.com" autocomplete="off">
							<div class="tab_con" style="margin-top:-23px;">
								<ul >
									<li><img src="images/phone_icon.png"><input type="text" placeholder="请输入手机号" id="phone"></li>
									<li><img src="images/login_icon2.png"><input type="password" placeholder="请输入密码" id="zc_pwd"></li>
									<li><img src="images/login_icon2.png"><input type="password" placeholder="确认密码" id="zc_pwd2"></li>
									<li class="yzm_wrap2"><img src="images/login_icon3.png"><input type="text" placeholder="请输入验证码" id="zc_yzm"></li><span class="yzm2 linear">发送手机验证码</span>
								</ul>
								<p class="agreement agreement2">
									<span class="xy"><img src="images/ok_icon.png" data-ok="true">
									同意联想的</span><a href="">《使用条款》、</a><a href="">《隐私协议》。</a>
								</p>
								<button type="button" id="btnReg" class="submit_btn linear">立即注册</button>
							</div>
						</form> -->
					</div>
				<!-- 右上角二维码与电脑登录标志切换 -->
				<div class="iconImg">
					<span type="button" id="iconQrCode"><img src="${ctx}/static/ui3/images/iconQrCode.png" /></span>
					<span id="iconComp" class="hide"><img src="${ctx}/static/ui3/images/iconComp.png"/></span>
				</div>
				<!-- 二维码登录扫描 -->
				<div class="qrBox hide">
					<p class="lineHbig color-white"><fmt:message bundle='${pageScope.bundle}'  key='Mobile.Scanning.to.Login.Securely' /></p>
					<div class="qrImgBox">
						<!-- tian -->
						<img src="${ctx}/static/ui3/images/qrImg.png" height="228" width="228"  id="QrCodeImg">
					</div>
					<div class="scanBox clearfix">
						<div class="float-left">
							<img src="${ctx}/static/ui3/images/iconScanning.png" height="30" width="35">
						</div>
						<div class="float-right color-white">
							<fmt:message bundle='${pageScope.bundle}'  key='Open.WeChat.to.login.in.scanning' /><br/>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modalBox hide">
			<div class="closeBtn"><img src="${ctx}/static/ui3/images/closeIcon.png" height="32" width="32"></div>
			<div class="modalTitle clearfix">
				<div class="userLogo float-left"><img src="${ctx}/static/ui3/images/head.png"></div>
				<div class="userWelcome float-left fontS16">
					Hi,<span class="wordBlue" id="nickname">王晓明</span>,欢迎通过微信登录GLA账号!
				</div>
			</div>
			<!-- tian -->
			<form action="glaH5App/weixinLogin" method="post" id="cform" autocomplete="off">
			<dl class="modalContent">
				<dt><fmt:message bundle='${pageScope.bundle}'  key='Please.Fill.In.The.User.Name.And.Password.After.Binding.You.Can.Login.Via.WeChat.Scanning.QR.Code' /></dt>
				<dd><label><fmt:message bundle='${pageScope.bundle}'  key='Username' />：</label><input type="text"  placeholder="请输入用户名<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.account.number' />" name="account" id="account"></dd>
				<dd><label><fmt:message bundle='${pageScope.bundle}'  key='Password' />：</label><input type="password"  placeholder="请输入密码<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" name="password" id="password"></dd>
			</dl>
			<div class=""><button class="bindBtn linear" type="button" onclick="submitCform()"><fmt:message bundle='${pageScope.bundle}'  key='Bound' /></button></div>
			</form>
		</div>
<!--		<div class="footer">
			<p>Copyright by 懂的通信 GLA v1.0 | 京ICP备11035381</p>
		</div>	-->
	</div>
	<div class="popup hide"><span>${error}</span></div>
	<script>
		function refreshCaptcha() {
			$("#captcha").attr("src", "servlet/captchaCode?t=" + Math.random());
		}
		function submitCform(){
			var account = $('#account').val();
			var password = $('#password').val();
			if(account != '' && password != ''){
				var option = {
					    url : "<%=basePath %>glaH5App/weixinLogin",
					    type: "post",
					    success : function(data){
					    	var json = (new Function("return " + data))();
					    	if(("success" in json)){
					    		location.href=json.success;
					    	 }else{
					    		 $('.popup span').html(json.error);
						         $('.popup').show();//提示信息显示
					    	 }
					   }, 
					   error:function(){
						   $('.popup span').html("<fmt:message bundle='${pageScope.bundle}'  key='Login.Exception.Please.Later' />...");
				           $('.popup').show();//提示信息显示
						}
				 };
				$('#cform').ajaxSubmit(option);
			}else{
				 $('.popup span').html("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Username.Password' />");
	             $('.popup').show();//提示信息显示
			}
		}
	</script>
</body>
</html>
