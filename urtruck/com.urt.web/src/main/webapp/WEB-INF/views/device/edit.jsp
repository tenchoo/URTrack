<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Equipment.modification' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
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
	
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
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
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript">
function tijiao(){
	var index = parent.layer.getFrameIndex(window.name);
	$.ajax({
        type:"POST",
        url:"${ctx}/deviceBindings/doUpdate",
        data:$("form").serialize(),
		dataType : "json",
		cache : false,
		success : function(data) {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Modify.successfully' />！");
		  	window.parent.location.reload();
		  	parent.layer.close(index);
		},
		error : function(error) {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Modify.failed' />！");
		}
	});
}
</script>
<style>
.error {
	color: red;
}

.verticalSpacing {
	margin-top: 10px;
}
</style>

</head>
<body>
	<div class="pd-20" align="center">
		<h4> <fmt:message bundle='${pageScope.bundle}'  key='Device.binding.Modify' /> </h4>
		<br>
		<form  id="form" action="${ctx}/deviceBindings/doUpdate" class="form form-horizontal" method="post">
			<table id="example" border="0" class="table table-border table-bordered table-hover table-bg table-sort " >
				<tr>
					<td >
						<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Binding.number' />：</label><input type="text" style="width: 60%;height: 32px;" readonly="readonly" name="relId" id="relId" value="${dto.relId}">
					</td>
					<td >
						<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key='bind.type' />：&nbsp;&nbsp;&nbsp;</label><input type="text" style="width: 60%;height: 32px;" name="idType" id="idType" value="${dto.idType}">
					</td>
				</tr>
				<tr>
					<td >
						<label class="font12">iccid：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" style="width: 60%;height: 32px;" readonly="readonly" name="iccid" id="iccid" value="${dto.iccid}">
					</td>
					<td >
						<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key=' binding.equipment.numbers' />：</label><input type="text" style="width: 60%;height: 32px;" name="deviceId" id="deviceId" value="${dto.deviceId}">
					</td>
				</tr>
				<tr >
					<td >
						<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Agent' />：</label><input type="text" style="width: 60%;height: 32px;" readonly="readonly" name="operId" id="operId" value="${dto.operId}">
					</td>
					<td></td>
				</tr>
			</table>
			<br>
			<p></p>
			<p>
				<label>
					<button  type="button" onclick="tijiao();" class="btn btn-primary radius" >
							<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='Submit' />
					</button>
				</label>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<label>
					<button  type="reset" class="btn btn-primary radius" >
							<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='Reset' />
					</button>
				</label>
			</p>
		</form>
	</div>
			
</body>
</html>
