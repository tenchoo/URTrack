<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Recharge.details' /></title>
<base href="<%=basePath%>" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>
<link href="static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="static/h5/js/wow.min.js"></script>
<link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
<script>
	new WOW().init();
</script>
<script>
	new WOW().init();
</script>
<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}
</style>

<script src="js/main.js"></script>

<script src="js/date_min_v1.js"></script>
<div id="__calendarPanel" style="position: absolute; visibility: hidden; display: none; z-index: 9999; background-color: transparent; border: none; width: 240px; height: 276px;">
	<iframe name="__calendarIframe" id="__calendarIframe" width="100%"
		height="100%" scrolling="no" frameborder="0" style="margin: 0px;"></iframe>
</div>

<script src="js/user_account.js"></script>
<script type="text/javascript">
	function nextPage(num) {
		var curPage = document.getElementById('curPage').value;
		if (num == "-1") {
			curPage = Number(curPage) - 1;
			if (curPage < 1) {
				curPage = 1;
			}
		}
		if (num == "+1") {
			curPage = Number(curPage) + 1;
		}
		document.getElementById('curPage').value = curPage;
		document.getElementById('form1').submit();
	}

	function subForm() {
		document.getElementById('form1').submit();
	}
</script>

</head>
<body>
	<div style="font-size: 3.3vw;">
		<div
			style="background-color: #fa9148; padding-bottom: 0.5rem; padding-top: 0.5rem;;width:100vm;padding-left:0;padding-right:0"
			class="row">
				<div style="padding: 0.5rem 0.55rem; margin: 0.44rem 3rem"
					class="col-xs-1 col-xs-offset-1">
				<a href="javascript:history.go(-1)">
						<img src="static/h5/images/goback1.png" class="img-responsive"
							alt="Responsive image">
				</a>
				</div>

			<div style="font-size: 4.2vw; color: #FFFFFF; margin: 1rem 15vw"
				class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Recharge.details' /></div>
		</div>
		<div style="width:100vm;padding-left:0;padding-right:0"class="row">
			<div
				style="font-size: 3.8vw; margin-top: 2rem; margin-bottom: 3rem; padding-right: 0;padding-left:3rem"
				class="col-xs-8 col-xs-offset-1 ">${chargedate}<fmt:message bundle='${pageScope.bundle}'  key='Recharge.details.are.as.follows' />:</div>

		</div>

		<div style="width: 100%;" class="row">

			<form>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Order.numbers' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">${orderid}
						</div>
					</div>


				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Recharge.account' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">${username}</div>
					</div>

				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 ">ICCID
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">
							${iccid}</div>
					</div>


				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='data.plan' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">${flowsize}</div>
					</div>

				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Recharge.type' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 "><fmt:message bundle='${pageScope.bundle}'  key='Regular.recharge' />
						</div>
					</div>


				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Recharge.amount' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">${payamount}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></div>
					</div>

				</div>
				<div
					style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
					class="col-xs-11 col-xs-offset-1 ">
					<div class="row">
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Payment.method' />
						</div>
						<div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">${paytype}</div>
					</div>

				</div>

			</form>
		</div>
		</div>
</body>
</html>