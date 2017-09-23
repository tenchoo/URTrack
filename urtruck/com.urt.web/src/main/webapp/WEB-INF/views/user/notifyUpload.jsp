<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Batch.operation' /></title>
<base href="<%=basePath%>" />
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
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript">
	function upload(){
		var option = {
			    url : "<%=basePath%>userNotify/notifyImport",
			type : "post",
			success : function(data) {
				if(data=="fail"){
					layer.alert('<fmt:message bundle="${pageScope.bundle}"  key="import.failed" />');
				}else if(data=="empty"){
					layer.alert('<fmt:message bundle="${pageScope.bundle}"  key="The.import.file.is.empty" />');
				}else{
					layer.alert('<fmt:message bundle="${pageScope.bundle}"  key="import.successful" />');
				}
			},
			error : function() {
				layer.alert('<fmt:message bundle="${pageScope.bundle}"  key="import.failed" />');
			}
		};
		if(myValidate()&& validateFile())
			layer.confirm('<fmt:message bundle="${pageScope.bundle}"  key="are you sure to import" />？', {
				  btn: ['<fmt:message bundle="${pageScope.bundle}"  key="Determine" />','<fmt:message bundle="${pageScope.bundle}"  key="Cancel" />'] //按钮
				}, function(){
					$("#sform").ajaxSubmit(option);
				}, function(){
					layer.msg('<fmt:message bundle="${pageScope.bundle}"  key="Cancel.successfully" />', {icon: 1});
				});
	}
	//验证信息
	function myValidate(){
		var cardstatus = $("#cardstatus").find("option:selected").val();
		if(cardstatus =='' || cardstatus == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.card.status' />");
			 return false;
		}
		return true;
	};
	
	//验证上传文件
	function validateFile(){
		var obj=document.getElementById('inputfile'); 
		if(obj.value=='') 
		{ 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />！");
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
	
	function notify(){
		 window.location.href = "${ctx}/download/notify.xlsx";
	}
	
</script>
</head>
<body>
	<div class="pd-20 font12">
		<form role="form" action="/userBatchssService/batchImport"
			method="post" enctype="multipart/form-data" id="sform">
			<div class="cl pd-5 mt-10">
				<label style="float:left"><fmt:message bundle='${pageScope.bundle}'  key='Import.files' />:</label>
				<input type="file" class=""  style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left"
					onclick="upload();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' />
				</button>
			</div>
		</form>
		<div class="cl pd-5  bk-gray mt-20">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="notify();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Notification.template.download' />
				</button>
		</div>
		
	</div>
</body>
</html>