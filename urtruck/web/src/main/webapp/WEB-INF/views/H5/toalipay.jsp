<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<base href="<%=basePath%>" />
<meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Internet.recharge.through.lenovo.connect' /></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css"
	type="text/css">
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
	<div style="background-color: #f4f3f9; font-size: 3.8vw; overflow:hidden;">
		<div style="background-color: #ffffff; border-bottom: 1px solid #dfe4e1; margin-bottom: 2vw"
			class="row">

				<div style="padding: 0.85rem 0;" class="col-xs-1 col-xs-offset-1">
					<a href="javascript:history.go(-1)">
					<img style="" src="static/h5/images/goback.png"
						class="img-responsive" alt="Responsive image">
					</a>
				</div>
			<div style="margin: 1.45rem 5rem; font-size: 4.2vw"
				class="col-xs-4 col-xs-offset-4"><fmt:message bundle='${pageScope.bundle}'  key='data.recharge' /></div>
		</div>
		<div
			style="background-color: #ffffff; margin-bottom: 2vw; padding-bottom: 5vw;">
			<div
				style="background-color: #Ffffff; border-bottom: 1px solid #dfe4e1; text-align: left"
				class="row">
				<div class="col-xs-4">
					<p style="margin-top: 2rem; margin-left: 1.5rem"><fmt:message bundle='${pageScope.bundle}'  key='Order.information' /></p>
				</div>
				<div class="col-xs-8">
					<p
						style="font-size: 3.4vw; margin-top: 2rem;padding-bottom:20px;">ICCID：${iccid}</p>
				</div>
			</div>
			<div class="row">
				<div
					style="padding-bottom: 0.5rem; background-color: #eef0f0; text-align: center"
					class="col-xs-12">




					<div style="margin: 0 auto 0.5vw 0; padding-left: 1rem; "
						class="col-xs-12">
							<div style="padding: 0 0 0 0; margin: 0.5rem auto 0 0"
								class="col-xs-3">
								<c:if test="${flowSize =='1G' }">
			                    	<img src="static/h5/images/bag1G.png" style="height: 6rem">
		                        </c:if>
		                        <c:if test="${flowSize =='10G' }">
		                        	<img src="static/h5/images/bag10G.PNG" style="height: 6rem">
		                  	   </c:if>
		                       <c:if test="${flowSize=='3个4G' }">
								<img src="static/h5/images/bag444G.png" style="height: 6rem">
		                  	   </c:if>
							</div>
						<div style="padding: 0 0 0.2rem 0; margin-top:30px;"
							class="col-xs-9 ">
							<div style="color: #8c8c8c; margin: 0 auto;margin-left:60px;" class="row">
								<c:if test="${flowSize =='1G' }">
			                    	<div
										style="font-size: 3.2vw; padding-left: 17px; padding-right: 0; margin-right: 0; text-align: left;width:200px"
										class="col-xs-8">
										<h5 style="margin-bottom: 0px; font-size: 3.3vw;">1G流量包&nbsp;&nbsp;&nbsp;&nbsp;30天有效</h5>
									</div>
		                        </c:if>
		                        <c:if test="${flowSize =='10G' }">
		                        	<div
										style="font-size: 3.2vw; padding-left: 17px; padding-right: 0; margin-right: 0; text-align: left;width:200px"
										class="col-xs-8">
										<h5 style="margin-bottom: 0px; font-size: 3.3vw;">10G流量包&nbsp;&nbsp;&nbsp;&nbsp;360天有效</h5>
									</div>
		                  	   </c:if>
		                       <c:if test="${flowSize=='3个4G' }">
		                       		<div
										style="font-size: 3.2vw; padding-left: 17px; padding-right: 0; margin-right: 0; text-align: left;width:200px"
										class="col-xs-8">
										<h5 style="margin-bottom: 0px; font-size: 3.3vw;">3个4G流量包&nbsp;&nbsp;&nbsp;&nbsp;每个30天有效</h5>
									</div>
		                  	   </c:if>
								
								

							</div>
							<!-- <div style="color: #8c8c8c; margin: 0 auto;margin-left:60px" class="row">
								
							</div> -->
							<div style="color: #8c8c8c; margin: 0 auto;margin-left:60px;margin-top:10px;" class="row">
								<div style="font-size: 3.2vw; padding-left: 17px; padding-top: 0; padding-right: 0; text-align: left"
									class="col-xs-6">
									<h5 style="font-size: 3.3vw; margin: 0.5rem auto; margin-top: 0"><fmt:message bundle='${pageScope.bundle}'  key='First.charge.discount' /></h5>
								</div>
								<div style="font-size: 3.2vw; color: red; padding-right: 0; text-align: left"
									class="col-xs-6">
									<h5 style=" font-size: 3.3vw; padding-right: 0;">${payAmount}</h5>
								</div>
							</div>
							
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div style="background-color: #ffffff;">
			<div
				style="margin-top: 1vw; font-size: 3.8vw; border-bottom: 1px solid #dfe4e1; padding-bottom: 1rem;"
				class="row">
				<div style="font-size: 3.8vw; padding-left: 3rem; padding-top: 3rem"
					class="col-xs-5"><fmt:message bundle='${pageScope.bundle}'  key='Payment.method' /></div>
			</div>
			<div style="border-bottom: 1px solid #dfe4e1;" class="row">

				<div style="text-align: right; margin-left: 2rem" class="col-xs-3">
					<img style="padding: 0.6rem 1.3rem; width: 20vw"
						src="static/h5/images/pay.png" class="img-responsive" alt="Responsive image">
				</div>
				<div style="font-size: 3.3vw; padding-left: 0; padding-top: 0.8rem;"
					class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='paying.through.Alipay' /></div>
				<div style="padding-top: 0.6rem; text-align: right" class="col-xs-4">
					<label> <input type="radio" name="optionsRadios"
						id="optionsRadios3" value="option3" checked="checked">

					</label>
				</div>

			</div>
			<!-- <div style="border-bottom: 1px solid #dfe4e1;" class="row">

				<div style="text-align: right; margin-left: 2rem" class="col-xs-3">
					<img style="padding: 0.6rem 1.3rem; width: 20vw;"
						src="static/h5/images/pay2.png" class="img-responsive"
						alt="Responsive image">
				</div>
				<div style="font-size: 3.3vw; padding-left: 0; padding-top: 2rem;"
					class="col-xs-3 ">微信支付</div>
				<div style="padding-top: 1.6rem; text-align: right" class="col-xs-4">
					<label> <input type="radio" name="optionsRadios"
						id="optionsRadios3" value="option3">

					</label>
				</div>

			</div> -->

			<nav style="height: 50px"
				class="navbar navbar-default navbar-fixed-bottom">
			<div style="margin: 0; padding: 0; width: 100%; height: 50px;padding-right:0px !important;"
				class="container">
				<div style="margin: 0vw; width: 100%; height: 50px" class="row">
					<div
						style="color: black; text-align: right; padding-right: 0; margin: 1.5rem auto;width:60px;"
						class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='subtotal' />：</div>
					<div
						style="color: red; text-align: left; padding-left: 0; margin: 1.5rem auto;width:50px;"
						class="col-xs-1">${payAmount}</div>
						
					<form action="paymentService/torealPay" method="post" id="chargeForm">
						<input type="hidden" name="payName" value="alipay_directPay">
			            <input  type="hidden" name="payAmount" value="${payAmount}">
			            <input  type="hidden" name="flowSize" value="${flowSize}">
			            <input  type="hidden" name="iccid" value="${iccid}">
					</form>	
						<div style="float:right;border-left: 1px solid #dfe4e1; background-color: #eb641a; height: 50px; margin-bottom: 0vw; text-align: center; padding: auto 0vw;margin-left:-4px"
							class="col-xs-4 col-xs-offset-5 ">
							<a href="javascript:void(0);"  onclick="$('#chargeForm').submit()" sytle="text-decorationnone;">
							<h5 style="font-size: 3.8vw; color: #ffffff; margin-left: 0;line-height: 50px;">
								<fmt:message bundle='${pageScope.bundle}'  key='Immediate.payment' /></h5>
							</a>
						</div>
				</div>
			</div>
			</nav>
		</div>
	</div>

</body>
</html>