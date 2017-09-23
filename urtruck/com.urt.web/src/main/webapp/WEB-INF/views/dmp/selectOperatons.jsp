<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Alarm.rule.list' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
$(function(){
	var schemeId=$("#schemeId").val();
	var strategyId=$("#strategyId").val();
	if(schemeId!=null&&schemeId!=""){
	 $.ajax({
			url:"${ctx}/tactical/getOperationByStrategy",
			data:{schemeId:schemeId,strategyId:strategyId},
			success:function(result){
			    $.each(result, function(index, value) {
			     var checkbox=document.getElementById(""+value.id);
				 checkbox.checked = true;
				}); 
			}
		  });
	}
})
function getOperation(){
    var operationIds=[];
	$('input[name="operation"]:checked').each(function(){
	    operationIds.push($(this).val());
	});
	return operationIds;
}
</script>
</head>
<body>
<div class="seconSec font12">
           <div class="row cl">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='Operation' />：</h1>
		</div>
		<div class="row cl" id="execute" style="margin-left:86px;">
		 <input type="hidden" class="input-text" id="schemeId" name="schemeId" value="${schemeId}">
		 <input type="hidden" class="input-text" id="strategyId" name="strategyId" value="${strategyId}">
		 <c:forEach items="${operationDtos}" var="operation">
			    <label class="font12"><input type="checkbox" id="${operation.id}" name="operation" value="${operation.id}"/><c:out value="${operation.operationName}"></c:out>&nbsp&nbsp&nbsp&nbsp&nbsp</label> 
		 </c:forEach>
		</div>
</div>
</body>
</html>
