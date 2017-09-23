 ﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!doctype html>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='registration' /></title>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<!--bootstrap-->
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/skin/default/skin.css" rel="stylesheet"
	type="text/css" id="skin" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet"
	type="text/css" />

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<style type="text/css">
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
lable{
	color:#fff;
}

.hint{
	display:block;
	position: relative;
    top: -26px;
    left: 313px;
    zoom: 1;
    color: #fff;
    width:100%;
    height:18px;
}
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 1000px #4e5c75 inset;
	-webkit-text-fill-color: #fff;
}
</style>
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript">
		/* $(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/register/checkLoginName"
					}
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
					}
				}
			});
		}); */
		var flag=true;
		function checkLoginName(){
			var loginName=$("#loginName").val();
			var msg='';
			var patrn=/^([a-zA-Z0-9]){6,20}$/;   
			if (!patrn.exec(loginName)){
				flag=false;
				$("#loginNameMsg").removeClass("checkSuccess");
				$("#loginNameMsg").css("color","red");
				msg='<fmt:message bundle="${pageScope.bundle}"  key="login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese" />'; 
				$("#loginNameMsg").text(msg);
				return;
			}else{
				$("#loginNameMsg").removeAttr("style");
			}
			$.ajax({
				url:"${ctx}/register/checkLoginName",
				type:"post",
				data:{'loginName':loginName},
				success:function(result){
					console.log("result:"+result);
					if(result=='false'){
						msg='<fmt:message bundle="${pageScope.bundle}"  key="login.name.already.exists" />'; 
						flag=false;
						$("#loginNameMsg").text(msg);
						$("#loginNameMsg").css("color","red");
						$("#loginNameMsg").removeClass("checkSuccess");
					}else{
						/* msg="登录名由6-20个字母数字组合"; */
						$("#loginNameMsg").text("");
						$("#loginNameMsg").addClass("checkSuccess");
						$("#loginNameMsg").removeAttr("style");
						flag=true;
					}
					
				}
			});
			
		}
		function checkEqPw(){
			if($("#plainPassword").val()!=$("#confirmPassword").val()){
				$("#secondPwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Entered.passwords.differ' />");
				$("#secondPwMsg").css("color","red");
				$("#secondPwMsg").removeClass("checkSuccess");
				flag=false;
			}else{
				$("#secondPwMsg").text("");
				$("#secondPwMsg").addClass("checkSuccess"); 
				$("#secondPwMsg").removeAttr("style");
				flag=true;
			}
		}
		function checkPW(){
			var pw=$("#plainPassword").val();
			var patrn=/^([a-zA-Z0-9]){6,19}$/;   
			if (!patrn.exec(pw)){
				flag=false;
				$("#pwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />");
				$("#pwMsg").css("color","red");
				$("#pwMsg").removeClass("checkSuccess");
				return;
			}else{
				flag=true;
				$("#pwMsg").text("");
				$("#pwMsg").addClass("checkSuccess");
				$("#pwMsg").removeAttr("style");
			}
			if($("#confirmPassword").val()!=''){
				checkEqPw();
			}
			
		}
		
		var captchaFlag=true;
		function checkCaptcha(){
			$.ajax({
				url:"${ctx}/login/checkCaptcha",
				type:"post",
				data:{"captcha":$("#captchaReg").val()},
				async:false,
				success:function(result){
					debugger;
					if(result.success){
						captchaFlag=true;
						$("#captchaMsg").text("");
						$("#captchaMsg").addClass("checkSuccess");
						$("#captchaMsg").removeAttr("style");
					
					}else{
						captchaFlag=false;
						$("#captchaMsg").removeClass("checkSuccess");
						$("#captchaMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Verify.code.entered.is.wrong' />");
						$("#captchaMsg").css("color","red");
						refreshCaptcha();
						
					}
				}
			});
		}
		function check(){
			debugger;
			checkLoginName();
			if(flag==false){
				return false;
			}
			checkEqPw();
			if(flag==false){
				return false;
			}
			checkPW();
			if(flag==false){
				return false;
			}
			if(captchaFlag==false){
				return false;
			}
			if(flag==false){
				return false;
			}else{
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='registered.successfully' />！");
				return true;
			}
		}
</script>
</head>
<body class="">
	<!--content starts-->
	<div class="mainbg" >
	<div class="contentOne">
		<div class="container">
			<div class="row myrow">
				<div class="fl topPad">
					<img src="${ctx}/static/ui/images/lenovo.png"
						class="img-responsive">
				</div>
				<div class="col-lg-1 col-md-1">
					<img src="${ctx}/static/ui/images/middleborder.png"
						class="img-responsive loginLine">
				</div>
				<div class="col-lg-5 col-md-5 leftContent">
					<div class="textImg">
						<img src="${ctx}/static/ui/images/textImg.png"
							class="img-responsive">
					</div>
					<form id="inputForm" action="${ctx}/register" method="post"
						class="form-horizontal" onsubmit="return check();">
						<div class="inputOne clearfix" style="margin-bottom:20px;">
							<div class="inputImg fl">
								<img src="${ctx}/static/ui/images/nicheng.png"
									class="img-responsive">
							</div>
							<div class="inputz">
								<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.nickname' />" class="textOne"
									name="nickname" id="nickname">
							</div>
						</div>
						<div class="inputOne clearfix">
							<div class="inputImg fl">
								<img src="${ctx}/static/ui/images/user.png"
									class="img-responsive">
							</div>
							<div class="inputz">
								<c:if test="${!empty name}">
									<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.login.name' />" class="textOne"
										id="loginName" name="loginName" readonly="readonly" value="${name}">
								</c:if>
								<c:if test="${empty name}">
									<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.login.name' />" class="textOne"
										id="loginName" name="loginName" onchange="checkLoginName();">
								</c:if>
							</div>
						</div>
						<lable id="loginNameMsg" class="font12 hint"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
						<div class="inputOne clearfix" style="margin-bottom: 0px;">
							<div class="inputImg fl">
								<img src="${ctx}/static/ui/images/lock.png"
									class="img-responsive">
							</div>
							<div class="inputz">
								<input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" class="textOne"
									id="plainPassword" name="plainPassword" onchange="checkPW();">
							</div>
						</div>
						<lable id="pwMsg" class="font12 hint"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
						<div class="inputOne clearfix">
							<div class="inputImg fl">
								<img src="${ctx}/static/ui/images/lockopen.png"
									class="img-responsive">
							</div>
							<div class="inputz">
								<input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.confirm.your.password.again' />" class="textOne"
									id="confirmPassword" name="confirmPassword"
									onchange="checkEqPw();">
							</div>
						</div>
						<lable id="secondPwMsg" class="font12 hint"></lable>
						<div class="inputOne clearfix row_space">
							<div class="pull-left inputImg shield">
								<img src="${ctx}/static/ui/images/shield.png"
									class="img-responsive">
							</div>
							<div class="inputz">
								<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.the.verification.code' />"
									class="textOne authCode" name="captchaReg" id="captchaReg" onchange="checkCaptcha();">
							</div>
							<div class="captcha">
								<!--${ctx}/static/ui/images/captcha.png  -->
								<img id="captchaImg" src="${ctx}/servlet/captchaCode"
									onclick="javascript:refreshCaptcha();"
									style="vertical-align: middle; border-radius: 15px;">
							</div>
						</div>
						<lable id="captchaMsg" class="font12 hint">&nbsp;</lable>
						<input type="submit" class="buttonOne text-center" value="<fmt:message bundle='${pageScope.bundle}'  key='registration' />">
					</form>
					<div class="row lastSection clearfix">
						<!-- <h2 class="pull-left linkThree">
							<a href="#">关于联想</a>
						</h2> -->
						<h3 class="pull-right linkTwo">
							<a href="${ctx}/login"><fmt:message bundle='${pageScope.bundle}'  key='Have.an.account,Login.in.now' /></a>
						</h3>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--content ends-->
	<!--footer starts-->
	<div class="container-fluid padRemov">
		<div class="footer">
			<h1 class="text-center">Copyright <fmt:message bundle='${pageScope.bundle}'  key='lenovo.Technology' /> by LAOAPI v1.0</h1>
		</div>
	</div>
	<!--footer ends-->
	<script src="js/jquery v1.12.4.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/ie.js"></script>
	<script src="js/respond.min.js"></script>
	<script type="text/javascript">
	function refreshCaptcha() {
		debugger;
		$("#captchaImg").attr("src", "servlet/captchaCode?t=" + Math.random());
	}
	</script>
	</div>
</body>
</html>