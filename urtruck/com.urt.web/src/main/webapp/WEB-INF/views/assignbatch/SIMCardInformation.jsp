<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title>卡基本信息</title>
	<base href="<%=basePath%>" />
	<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

	<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<div class="pd-20 font12">
	<form role="form" action="/userNewService/batchImport2" method="post"  id="cform">
	 	<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='SIM.card.number.information' /></h1>
			<br>
		</div>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></label>
			<input type="text"class="font12 labelWidth fl" name="simType" id="simType" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Card.size' /></label>
			<input type="text"class="font12 labelWidth fl" name="simSize" id="simSize" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='card.cost.(Yuan/each)' /></label>
			<input type="text"class="font12 labelWidth fl" name="simFee" id="simFee" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='start' /> ICCID</label>
			<input type="text"class="font12 labelWidth fl" name="iccidStart" id="iccidStart" style="width: 15%;height: 30px;">
		</div>
		<br>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='end' /> ICCID</label>
			<input type="text"class="font12 labelWidth fl" name="iccidEnd" id="iccidEnd" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Assign.starting.number' /></label>
			<input type="text"class="font12 labelWidth fl" name="numberStart" id="numberStart" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Assign.end.number' /></label>
			<input type="text"class="font12 labelWidth fl" name="numberEnd" id="numberEnd" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='order.amount' /></label>
			<input type="text"class="font12 labelWidth fl" name="orderFee" id="orderFee" style="width: 15%;height: 30px;">
		</div>
<br>	
		<br>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<button id="search" type="button" class="btn btn-primary radius" onclick="shouqi();">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Preservation' />
				</button>
			</label>
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<input class="btn btn-primary radius" type="reset" value="<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />"> 
			</label>
		</div>

		
	</form>
	
</div>

</body>
</html>
