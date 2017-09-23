<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<title>Lenovo Connect GLA</title>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<!--bootstrap-->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<style type="text/css">
	input:-webkit-autofill {
		-webkit-box-shadow: 0 0 0px 1000px #4e5c75 inset;
		-webkit-text-fill-color: #fff;
	}
	.row_space{
		margin-bottom: 20px;
	}
</style>
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
</head>
<body class="">
	<script type="text/javascript">
		$.ajax({
			url : "${ctx}/login/logout",
			type : "post",
			data : {}
		});
	</script>
	<div class="mainbg">
		<!--content starts-->
		<div class="contentTwo">
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
						<form class="form form-horizontal" action="${ctx}/login"
							method="post">
							<div class="inputOne clearfix row_space">
								<div class="inputImg fl">
									<img src="${ctx}/static/ui/images/user.png"
										class="img-responsive">
								</div>
								<div class="inputz">
									<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.login.name' />" class="textOne"
										name="username"  required oninvalid="setCustomValidity(<fmt:message bundle='${pageScope.bundle}'  key='login.name.can.not.be.empty' />)" oninput="setCustomValidity('')">
								</div>
							</div>
							<div class="inputOne clearfix row_space">
								<div class="inputImg fl">
									<img src="${ctx}/static/ui/images/lock.png"
										class="img-responsive">
								</div>
								<div class="inputz">
									<input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.password' />" class="textOne"
										name="password" required oninvalid="setCustomValidity('<fmt:message bundle='${pageScope.bundle}'  key='password.can.not.be.empty' />')" oninput="setCustomValidity('')">
								</div>
							</div>
							<div class="inputOne clearfix row_space">
								<div class="pull-left inputImg shield">
									<img src="${ctx}/static/ui/images/shield.png"
										class="img-responsive">
								</div>
								<div class="inputz">
									<input type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.verification.code' />"
										class="textOne authCode" name="captcha">
								</div>
								<div class="captcha">
									<!--${ctx}/static/ui/images/captcha.png  -->
									<img id="captcha" src="${ctx}/servlet/captchaCode"
										onclick="javascript:refreshCaptcha();"
										style="vertical-align: middle; border-radius: 15px;">
								</div>
							</div>
							<div class="clearfix radOne">
								<label class="pull-left checkz"> <input type="checkbox"
									checked class="chekbx"><span class="chOne fl"></span> <span
									class="labelOne fl"><fmt:message bundle='${pageScope.bundle}'  key='Keep.me.signed.in' /></span>
								</label>
								<h3 class="pull-right links">
									<a onClick="location.href='${ctx}/register';" id="register"><fmt:message bundle='${pageScope.bundle}'  key='New.to.Lenovo.Connect' />？</a>
								</h3>
							</div>
							<button class="buttonOne text-center"><fmt:message bundle='${pageScope.bundle}'  key='Sign.in' /></button>
						</form>
						<div class="lastSection clearfix">
							<lable style="color:red">${error}</lable>
						</div>
						<!-- <div class="lastSection clearfix">
							<h2 class="pull-left linkThree">
								<a href="#">关于联想</a>
							</h2>
							<h3 class="pull-right linkTwo">
								<a href="#">重置</a>
							</h3>
						</div> -->
						<div class="lenovoSec clearfix">
							<span class="fl"><img
								src="${ctx}/static/ui/images/slideborderleft_03.png"
								class="img-responsive"></span> <span class="fr"><img
								src="${ctx}/static/ui/images/sliceborderright.png"
								class="img-responsive"></span>
							<div class="txtpos">
								<h4 class="text-center"><fmt:message bundle='${pageScope.bundle}'  key='Other.platforms.account.login' /></h4>
							</div>
						</div>
						<div class="circleSec">
							<%-- <a href="#" class="col-lg-4 col-md-4 platforms"><img src="${ctx}/static/ui/images/logos.png"/></a> --%>
							<a onclick="location.href='${lenovoUrl}'" class="platforms"><img src="${ctx}/static/ui/images/len.png" /></a>
							<a onclick="#" class="platforms"><img src="${ctx}/static/ui/images/qq.png" /></a>
							<a onclick="#" class="platforms"><img src="${ctx}/static/ui/images/weibo.png" /></a>
							<a onclick="#" class="platforms"><img src="${ctx}/static/ui/images/wenxin.png" /></a>
							<a onclick="#" class="platforms"><img src="${ctx}/static/ui/images/zhifubao.png" /></a>
							<%--  <a href="#" class="col-lg-4 col-md-4 platforms"><img src="${ctx}/static/ui/images/logos.png"/></a> --%>
							<!-- <div onclick="location.href='#';" class="col-lg-4 lenovoText zeroMargin">
	                      	  <h1>Lenovo</h1>
	                      </div>
	                      <div onclick="location.href='#';" class="col-lg-4 lenovoText">
	                      	  <h1>Lenovo</h1>
	                      </div>
	                      <div onclick="location.href='#';" class="col-lg-4 lenovoText">
	                      	  <h1>Lenovo</h1>
	                      </div> -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--content ends-->
		<!--footer starts-->
		<div class="container-fluid padRemov">
			<div class="footer">
				<h1 class="text-center">Copyright <fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' /> by GLA v1.0  | <fmt:message bundle='${pageScope.bundle}'  key='Beijing.ICP.record.11035381' /></h1>
			</div>
		</div>
		<!--footer ends-->
	</div>
	<script src="${ctx}/static/ui/js/jquery v1.12.4.js"></script>
	<script src="${ctx}/static/ui/js/bootstrap.min.js"></script>
	<script src="${ctx}/static/ui/js/ie.js"></script>
	<script src="${ctx}/static/ui/js/respond.min.js"></script>

	<script>
		function refreshCaptcha() {
			$("#captcha").attr("src", "servlet/captchaCode?t=" + Math.random());
		}	
	</script>
</body>
</html>