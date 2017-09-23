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
<title><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></title>
<base href="<%=basePath%>" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css"
	type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet"
	href="static/h5/css/bootstrap-datetimepicker.min.css" type="text/css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>

<link href="static/h5/css/style.css" rel="stylesheet" type="text/css"
	media="all" />

<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}

h5 {
	font-size: 3.3vw
}

button {
	font-size: 3.8vw
}
</style>

<style>
button {
	font-size: 3.8vw
}
</style>
</head>
<body>
	<div style="font-size: 3.8vw;overflow: hidden;">
		<div  style="background-color: #fa9148; border-bottom: 1px solid #dfe4e1; margin: 0; overflow: hidden;"
			class="row">
			<div style="padding: 1rem 0;margin: 0 1rem;"
				class="col-xs-1 col-xs-offset-1">
				<a href="javascript:history.go(-1)"> <img src="static/h5/images/goback1.png"
					class="img-responsive" alt="Responsive image">
				</a>
			</div>

			<div style="color: #ffffff;margin: 1.25rem 3.8rem;font-size:4.2vw;" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></div>
		</div>

		<div
			style="width: 96vw; background-color: #ffffff; margin: 1rem auto; padding-top: 1rem;;width:100vm;padding-left:0;padding-right:0"
			class="row">
			<div id="page1.1">
				<p
					style="font-size: 3.6vw; color: #8c8c8c; margin-bottom: 1.6rem; margin-left: 1rem">
					<c:if test="${empty startTime}">
               	<fmt:message bundle='${pageScope.bundle}'  key='Recharge.records.are.as.follows' />:
               </c:if>
					<c:if test="${not empty startTime}">
                  ${startTime}-${endTime}<fmt:message bundle='${pageScope.bundle}'  key='Recharge.records.are.as.follows' />:
               </c:if>
				</p>
				<div style="text-align: center">

					<c:if test="${empty list}">
		           		      <fmt:message bundle='${pageScope.bundle}'  key='Your.Lenovo.account.has.not.any.recharge.records' />
                   	</c:if>

					<c:if test="${not empty list}">
						<c:forEach items="${list}" var="item">
							<div
								style="border-bottom: 1px solid #dfe4e1; margin: 0rem 2rem 0rem 0rem; padding: auto 0 auto auto;"
								class="col-xs-12">
								<div style="padding: 1rem 0 0.5rem 0; margin: 0.5rem auto 0 0"
									class="col-xs-2">
									<c:if test="${item.flowsize =='10G'}">
										<img src="static/h5/images/bag10G.PNG" style="height: 5rem">
									</c:if>
									<c:if test="${item.flowsize =='3个4G'}">
										<img src="static/h5/images/bag444G.png" style="height: 5rem">
									</c:if>
									<c:if test="${item.flowsize =='1G'}">
										<img src="static/h5/images/bag1G.png" style="height: 5rem">
									</c:if>
								</div>
								<div style="padding: 2rem 0 0.2rem 0; margin: 0 auto 0rem 0rem"
									class="col-xs-10 ">
									<div style="margin: 0.5rem auto 0 auto" class="row">
										<div	
											style="padding-right: 0; margin-right: 0; text-align: right"
											class="col-xs-5">
											<h5>${item.flowsize}<fmt:message bundle='${pageScope.bundle}'  key='data.plan' /></h5>
										</div>
										<div style="padding-right: 0; text-align: center"
											class="col-xs-5">
											<h5 style="padding-right: 0;">${item.payamount}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></h5>
										</div>
										<div
											style="margin-left: 0rem; padding-right: 0; text-align: center"
											class="col-xs-2">
											<a 
											href="<%=basePath%>queryService/queryChargeRecordDetail?iccid=${item.iccid}&username=${item.username}&orderid=${item.orderid}&flowsize=${item.flowsize}&payamount=${item.payamount}&paytype=${item.paytype}&chargedate=<fmt:formatDate value='${item.chargedate}' pattern='yyyy-MM-dd HH:mm:ss' />">
											<img src="static/h5/images/right.png" style="height: 1.5rem">
											</a>
										</div>
									</div>
									<div style="color: #8c8c8c; margin: 0.5rem auto" class="row">
										<div style="padding-right: 0; text-align: right"
											class="col-xs-5">
											<h5>
												<fmt:formatDate value='${item.chargedate}' pattern='yyyy-MM-dd' />
											</h5>
										</div>
										<div style="padding-right: 0; text-align: center"
											class="col-xs-5">
											<h5><fmt:message bundle='${pageScope.bundle}'  key='First.charge.discount' /></h5>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>


				</div>
			</div>


			<div style="margin-bottom: 3rem;width:100vm;padding-left:0;padding-right:0" class="row">
				<div style="margin-top: 2rem; text-align: center"
					class="col-xs-4">
					<a style="font-size: 3.8vw"
						href="<%=basePath%>queryService/queryChargeRecord?curPage=${curPage-1 < 1? curPage:(curPage-1)}&startTime=${startTime}&endTime=${endTime}"}><fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /></a>
				</div>

				<div style="font-size: 3.8vw; margin-top: 2rem; text-align: center"
					class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='Total' />&nbsp;&nbsp;${totalPage}&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='page' /></div>
				<div style="margin-top: 2rem; text-align: center" class="col-xs-4">
					<a style="font-size: 3.8vw"
						href="<%=basePath%>queryService/queryChargeRecord?curPage=${curPage+1 > totalPage? curPage:(curPage+1)}&startTime=${startTime}&endTime=${endTime}"}>下一页<fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /></a>
				</div>
			</div>
		</div>

	</div>
</body>
</html>