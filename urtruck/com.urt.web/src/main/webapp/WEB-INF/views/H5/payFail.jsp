<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Recharge.failed' /></title>
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

<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}
</style>




<style>
h5 {
	font-size: 3.7vw
}
</style>

</head>
<body>
	<div style="background-color: #f4f3f9; font-size: 3.8vw;">
		<div style="background-color: #ffffff; border-bottom: 1px solid #dfe4e1; margin: 0; overflow: hidden;" 
			class="row">
			<div style="padding: 1rem 0;margin: 0 1rem;" class="col-xs-1 col-xs-offset-1">
				<a href="<%=basePath%>h5/index">
					<img style="" src="static/h5/images/goback.png"
						class="img-responsive" alt="Responsive image">
				</a>
			</div>
			
			<div style="margin: 1.25rem 3.8rem;font-size:4.2vw"
				class="col-xs-4 col-xs-offset-4"><fmt:message bundle='${pageScope.bundle}'  key='Recharge.failed' /></div>
		</div>
		<div style="margin-bottom: 2vw;width: 100vm ;margin-left:0;margin-right:0" class="row">
			<img src="static/h5/images/single.jpg" class="img-responsive">
		</div>
		<div
			style="border-bottom: 1px solid #dfe4e1; background-color: #ffffff; margin-bottom: 2vw; padding-bottom: 5vw;">
			<div
				style="padding-top: 6vw; padding-bottom: 6vw; background-color: #Ffffff; text-align: left;width: 100vm ;margin-left:0;margin-right:0"
				class="row">
				<div style="" class="col-xs-3 col-xs-offset-2 ">
					<img style="padding: 1.5vw 2vw; text-align: right"
						src="static/h5/images/chargefail.PNG" class="img-responsive"
						alt="Responsive image">
				</div>
				<div style="text-align: left" class="col-xs-7 ">

					<div style="margin: 5vw; text-align: left" class="row">
						<p style="font-size: 5vw; color: #353433;">&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Payment.failed' />！</p>
					</div>
					<div style="margin: 0vw;" class="row"></div>
				</div>

			</div>

			<div style="width: 100vw" class="row">
				<div class="col-xs-5 col-xs-offset-2">
					<button
						style="background-color: #fa9148; color: #ffffff; border-radius: 10px; text-align: center;"
						type="button" class="btn " onclick="window.location.href='paymentService/topay'"><fmt:message bundle='${pageScope.bundle}'  key='Recharge.again' /></button>
				</div>
				<div class="col-xs-5">
					<button
						style="border-radius: 10px; text-align: center; background-color: #fa9148; color: #ffffff;"
						type="button" class="btn "  onclick="window.location.href='h5/index'"><fmt:message bundle='${pageScope.bundle}'  key='Home' /></button>
				</div>
			</div>



		</div>
</body>
</html>