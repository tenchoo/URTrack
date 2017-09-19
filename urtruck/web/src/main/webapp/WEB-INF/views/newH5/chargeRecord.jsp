<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Trading.order' /></title>
<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/newH5/js/search_data.js"></script>
	<script src="static/h5/js/layer/layer.js"></script>
</head>
<body>
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		<div class="search row">
			<label class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='User.equipment' /></label>
			<input type="tel"  class="col-xs-9" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />" id="iccid" autocomplete="off" value="${fn:substring(search,11,19)}">
			<form action="glaH5AppQuery/queryChargeRecord" method="post" id="cform">
				<input type="text" hidden name="iccid">
			</form>
			<span><img src="static/newH5/images/search.png" class="img-responsive" onclick="query()" id="img"></span>
			<div class="data_list" id="append">
				<ul>
					<c:if test="${not empty glaIccidList}">
						<c:forEach items="${glaIccidList}" var="item">
							<li data-value="${item}">•••${fn:substring(item,11,19)}</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
		<hr style="margin:14px -15px 0">
		<div class="jy_list" style="margin:0 -8px 0 -8px;padding-top:0;">
			<ul>
				<c:if test="${not empty tradeFeeList}">
						<c:forEach items="${tradeFeeList}" var="item">
							<li class="pack" onclick="window.location.href='glaH5AppQuery/queryChargeRecordDetail?tradeId=${item.tradeId}&iccid=${iccid}'">
								<span class="pull-left">${item.goodsDto.goodsName} <fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${item.goodsDto.releaseCycle}<c:if test="${item.goodsDto.unit ==0}"><fmt:message bundle='${pageScope.bundle}'  key='month' /></c:if><c:if test="${item.goodsDto.unit ==1}"><fmt:message bundle='${pageScope.bundle}'  key='Day' /></c:if><br/><em><fmt:message bundle='${pageScope.bundle}'  key='Device.number' />：•••${fn:substring(search,11,19)}</em></span>
								<span class="pull-right"><strong><span>¥</span> ${item.fee}</br></strong><em><fmt:formatDate value="${item.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss" /></em></span>
							</li>
						</c:forEach>
				</c:if>
				
				<c:if test="${empty tradeFeeList }">
					<div class="search_data_list">
					<p class="text-center"><img src="static/newH5/images/history.png" width="32%">
						<span><fmt:message bundle='${pageScope.bundle}'  key='No.consumption.record' /></span>
					</p>
					</div>
				</c:if>
			</ul>
		</div>
	</div>
	<script type="text/javascript">
	/* $(function() {
		<c:if test="${not empty glaIccidList}">
			<c:if test="${empty search}">
				$('.data_list ul').find('li:first').trigger("click");
				$('#img').trigger("click");
			</c:if>
		</c:if>	
	}); */
	function query(){
		var iccid = $("#iccid").val();
		if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
		$('input[name="iccid"]').val(iccid);
		if(iccid != ''){
			$('#cform').submit()
		}else{
			layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.device.number' />！");
		}
		
	}
	</script>
</body>
</html>