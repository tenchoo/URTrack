<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Add.customer.ability' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
	
	
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<style>
.error {
	color: red;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<script type="text/javascript">
	function check() {
		var custIdValue = window.document.getElementById("custId").value;
		if (custIdValue == "" || custIdValue == "-1") {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.the.channel.client.to.be.allocated' />!");
			return false;
		}
		return true;
	}
	function save(){
		var obj=document.getElementsByName("user-Character-1-0");
		var serverIds="";
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked == true) {
				serverIds += obj[i].value + ",";
			}
		}
		var falg=check();
		$("#custId").val();
		if (falg==false) {
			return ;
		}
		if (serverIds=="") {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.the.interface.permissions.to.assign' />!");
			return false;
		}
		$("#serverIds").val(serverIds);
		$.ajax({
	        type:"POST",
	        url:"${ctx}/abilityOpen/addCustServer",
	        data:$("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
			  	window.parent.location.reload();
			  	parent.layer.close(index);
			},
			error : function(error) {
			}
		});
		
	}
	function closeLayer() {
		debugger;
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='basic.information' /></h1>
			<form role="form" class="form form-horizontal" id="majorForm">
			       <input type="hidden" name="serverIds" id="serverIds" />
				<div class="col-12 mt20">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="custId" name="custId" class="input-text select2"
							style="width: 200px;">
						</select>
					</div>
				</div>
				<script type="text/javascript">
					$.ajax({
						url : "${ctx}/cust/getAgentList",
						data : {},
						success : function(result) {
							var select = $("#custId").select2({
								width : 200,
								placeholder : '<fmt:message bundle="${pageScope.bundle}"  key="Customer" />',
								tags : "true",
								allowClear : true,
								data : result
							});
						}
					});
				</script>
				<div class="col-12 mt20">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='interface.names' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<c:forEach var="i" items="${vBeans}">
							<dl class="permission-list">
								<dt>
									<label class=""> <input type="checkbox"
										value="${i.serverId}" name="user-Character-1-0"
										id="user-Character-1-0"> ${i.serverDesc}
									</label>
								</dt>
							</dl>
						</c:forEach>
					</div>
				</div>
				<div class="col-12" style="margin-bottom: 20px;">
					<div class="zpButton">
						<input class="btn btn-primary radius" type="button"
							onclick="save();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;"> <input
							class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Reduction' />&nbsp;&nbsp;" onclick="clearErrMsg();">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>

