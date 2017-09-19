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

<title><fmt:message bundle='${pageScope.bundle}'  key='Edit.project.group.information' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
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
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="example"
				name="form-member-add">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Project.group.name' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="text" class="input-text" id="groupName" name="groupName" required value="${dto.groupName}">
							<input type="hidden" class="input-text" id="manageId" name="manageId" value="${dto.id}" required>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Project.group.description' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
						<textarea rows="4px" cols="45px" id="groupComment" name="groupComment"  required>${dto.groupComment}</textarea>
						</div>
					</div>
				</div>
				<div class="row cl" style="margin-bottom: 20px;">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='submmit' />&nbsp;&nbsp;" onclick="saveManage();">
						<input class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> <input
							class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	
	 $(document).ready(function() {
		$("#form_manage").validate();
		
	}); 

	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	function saveManage(){
		if($("#groupName").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.device.group.name.cannot.be.empty' />");
			return;
		}
		var dto ={
			groupName:$("#groupName").val(),
			id:$("#manageId").val(),
			groupComment:$("#groupComment").val()
		};
		var index = parent.layer.getFrameIndex(window.name);
		$.ajax({
	        type:"POST",
	        url:"${ctx}/deviceManage/update",
	        data:dto,
			dataType : "json",
			cache : false,
			success : function(data) {
				if(data.success){
					layer.alert(data.msg,function(){
                        parent.location.reload();
						var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
					})
				}else{
					  layer.alert(data.msg,function(){
                          layer.closeAll();
                      })
				}
			},
		});
	}

</script>
</body>
</html>
