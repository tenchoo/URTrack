<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Cluster.network.statement.export' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
	$(function (){
		$("#msgdaoru").hide();
		var retmsg = $("#msgId").val();
		if(retmsg != ""){
			alert(retmsg);
		}
	});
</script>
</head>
<body>
	<div class="pd-20">
	<form role="form" action="${ctx}/grpNetExpBill/grpNetBillType" method="post" class="form form-horizontal" enctype="multipart/form-data" id="expform">
	    <div class="oh row">
	        <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Export.file.type' />：</label>
			<select name="tradeType" class="input-text" style="width:250px">
                <option value="0"><fmt:message bundle='${pageScope.bundle}'  key='Cluster.network.user.detail.statement' /></option>
                <option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Cluster.network.user.number.summary.statement' /></option>
                <option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Cluster.network.user.cost.summary.statement' /></option>
            </select>
         </div>   
         <div class="oh row">
             <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />：</label>
             <input class="input-text"  style="width:250px" type="text" name="cycleId" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M-1}'})" readonly="readonly">
        </div>    
        <div class="oh row">
	        <div class="col-9 col-offset-3">
	            <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='export' />&nbsp;&nbsp;">
            </div>
        </div>
	</form>
	</div>
<%-- 	<form role="form" action="${ctx}/grpNetExpBill/dealUserInfo" method="post" class="form form-horizontal" enctype="multipart/form-data" id="addform">  
        <div class="oh row">
	        <label class="form-label col-2" style="width:250px">管理集群网用户点击此处：</label>
            <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;新&nbsp增&nbsp;&nbsp;">
        </div>
	</form> --%>
	<div align="center">
	<form action="${ctx}/batchFtpImport/colonySendMessage" method="post">
		<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Last.month.s.statement' /></span>  <input class="btn btn-primary radius" type="submit"  value="&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='send.text.message' />">
	</form>
	</div>
	<input id="msgId" type="hidden" value="${msg}"/>
</body>

</html>