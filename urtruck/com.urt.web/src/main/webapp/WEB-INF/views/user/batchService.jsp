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
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript">
	function upload(){
		var option = {
			    url : "<%=basePath%>userBatchssService/batchImport",
			type : "post",
			success : function(data) {
				var Data = data;
				for ( var key in Data) {
					$("#example").empty();
					if (key.indexOf('total') > -1) {
						$("#example").append(
								"<tr class='text-c'><td><fmt:message bundle='${pageScope.bundle}'  key='successful.number.in.batch.processing' /></td><td>"
										+ Data[key] + "</td></tr>");
					} else if (key.indexOf('errorMsg') > -1) {
						$("#example").append(
								"<tr class='text-c'><td><fmt:message bundle='${pageScope.bundle}'  key='incorrect.information' /></td><td>"
										+ Data[key] + "</td></tr>");
					}
				}
				$("#showResult").hide();
				$("#showResult").show();
			},
			error : function() {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
			}
		};
		if(myValidate()&& validateFile())
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
				$("#sform").ajaxSubmit(option);
			}
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
	
	function cmcc(){
		 window.location.href = "${ctx}/download/cmcc.xlsx";
	}
	function activation(){
		window.location.href = "${ctx}/download/activation.xlsx";
	}
	function order(){
		 window.location.href = "${ctx}/download/orderProduct.xlsx";
	}
	
	function openOrClose(){
		window.location.href = "${ctx}/download/openorclose.xlsx";
	}
	
	function hideSel(){
		var cardstatus = $("#cardstatus").find("option:selected").val();
		if(cardstatus.indexOf("100") > -1){
			$("#label").hide();
			$("#ifMaintenance").hide();
		}else{
			$("#label").hide();
			$("#label").show();
			$("#ifMaintenance").hide();
			$("#ifMaintenance").show();
		}
	}
</script>
</head>
<body>
	<div class="pd-20 font12">
		<form role="form" action="/userBatchssService/batchImport"
			method="post" enctype="multipart/form-data" id="sform">
			<div class="cl pd-5  bk-gray mt-20">
				<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Card.operation' />:</label> 
				<select id="cardstatus" name="cardstatus" class="input-text" style="width: 200px;" >
					<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
					<option value="100"><fmt:message bundle='${pageScope.bundle}'  key='Activation' /></option>
					<option value="120"><fmt:message bundle='${pageScope.bundle}'  key='Product.order' /></option>
					<option value="130"><fmt:message bundle='${pageScope.bundle}'  key='deactivate' /></option>
					<option value="140"><fmt:message bundle='${pageScope.bundle}'  key='starting.up' /></option>
				</select>
				 <label class="font12" id="label"><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' />:</label> 
				 <select id="ifMaintenance" name="ifMaintenance" class="input-text" style="width: 200px;">
					<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
					<option value="0" selected><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
				</select>
			</div>
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
				<!-- <button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="cmcc();">
					<i class="Hui-iconfont">&#xe665;</i>移动激活模板 下载<fmt:message bundle='${pageScope.bundle}'  key='' />
				</button> -->
				
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="activation();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Active.template.download' />
				</button>
				
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="order();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Order.template.download' />
				</button>
				
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="openOrClose();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='close.down.and.power.on.model.templates.download' />
				</button>
		</div>
		<div class="mt-20" id="showResult" style="display: none">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			</table>
		</div>
	</div>
</body>
</html>