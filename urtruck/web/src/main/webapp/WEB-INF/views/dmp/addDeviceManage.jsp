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
<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Add.device.group' /></title>
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

<style>
.error {
	color: red;
}

.verticalSpacing {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#form_deviceManage").validate();
	});
	
	 //验证上传文件
		function validateFile(){
			var obj=document.getElementById('inputfile'); 
			if(obj.value==''){ 
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='There.is.no.import.device.for.this.add.time' />");
				return false; 
			} 
			var stuff=obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; 
			if(stuff!='xls' && stuff!='xlsx' ) 
			{ 
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
				return false; 
			} 
			return true;
		}

	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	//通知模板下载
	function notify(){
		 window.location.href = "${ctx}/download/device.xlsx";
	}
	function saveDevice(){
		var index = parent.layer.getFrameIndex(window.name);
		var nameLength = document.getElementById("groupName").value.length;
		var descriptionLength = document.getElementById("groupComment").value.length;
		var file = $("#inputfile").val();
		if(nameLength==0){
			layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='The.device.group.name.cannot.be.empty' />");
		}
// 		else if(file==""){
// 			alert("请添加要导入的设备");
// 		}
		else{
				var option = {
						url : "<%=basePath%>/deviceManage/save",
						type : "post",
						success : function(data) {
							if(data.success){
								layer.alert(data.retMsg,function(){
			                        parent.location.reload();
									var index = parent.layer.getFrameIndex(window.name);
			                        parent.layer.close(index);
								})
							}else{
								  layer.alert(data.retMsg,function(){
			                          layer.closeAll();
			                      })
							}
						}
					};
						$("#form_deviceManage").ajaxSubmit(option);
				}
		}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_deviceManage"
				name="form-member-add" enctype="multipart/form-data">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Device.group.name' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="text" class="input-text" id="groupName" placeholder="(<fmt:message bundle='${pageScope.bundle}'  key='You.can.enter.100.words.at.most' />)" maxlength="100" name="groupName" required>
						</div>
					</div>
				</div>
						<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Device.group.description' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<textarea rows="4px" cols="45px" placeholder="(最多可输入200字)" id="groupComment" name="groupComment" maxlength="200"></textarea>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Import.equipment' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="file" style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
				<div class="fr mtb20">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="notify();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Device.information.import.template.download' />
				</button>
				</div>
						</div>
					</div>
				</div>
		
				<div class="row cl" style="margin-bottom: 20px;">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveDevice();">
						<input class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> <input
							class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
