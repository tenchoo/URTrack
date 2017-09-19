<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='personalization' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet">
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
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript">
function selectFileId(){
	$.ajax({
		url:"<%=basePath%>business/getFileList",
		data : {},
		success : function(result) {
			var select = $("#fileId").select2({
				width : 200,
				placeholder : '<fmt:message bundle="${pageScope.bundle}"  key="File.name" />',
				tags : "true",
				allowClear : true,
				data : result
			});
			$("#fileId").change(function() {
				$('#typecodeId').empty();
				$.ajax({
					url:"<%=basePath%>business/getTypeCodeByFileId",
					data : {"fileId":$("#fileId").val()},
					success : function(result) {
						var select = $("#typecodeId").select2({
							width : 200,
							/* placeholder : '业务类型',
							tags : "true",
							allowClear : true, */
							data : result
						});
						
					}
				});
				
			});
			
			
			
		}
	});
}


	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>business/listFile", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "FILE_NAME"
				},
				{
					"targets" : [ 1 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var date = DateFormat.tolongdata(data.CREATE_DATE);
						return date;
					} 
				},
				{
					"targets" : [ 2 ],
					"data" : "TRADETYPECODE",
					
				},
				{
					"targets" : [ 3 ],
					"data" : "SUCCESSNUM"
				},
				{
					"targets" : [ 4 ],
					"data" : "FAILNUM"
				},
				{
					"targets" : [ 5 ],
					"data" : "PENDINGNUM"
				},
				{
					"targets" : [ 6 ],
					"data" : "CARDTOTAL"
				},
				{
					"targets" : [ 7 ],
					"data" : null,
					"mRender" : function(data, type, full) {
							
							return   '<select id=\"'+data.ID+'\" name=\"exportId\" class=\"select2\"'+
							   'style=\"width: 100px;\" onchange=\"selectexportfile(\''+data.ID+'\')\">'+
							   "<option value=\"0\"><fmt:message bundle='${pageScope.bundle}'  key='all.export' /></option>"+
							   "<option value=\"1\"><fmt:message bundle='${pageScope.bundle}'  key='pending' /></option>"+
							   "<option value=\"2\"><fmt:message bundle='${pageScope.bundle}'  key='processing.failed' /></option>"+
							   "<option value=\"3\"><fmt:message bundle='${pageScope.bundle}'  key='processing.sucessfully' /></option>"+
							   "</select>"+
							'<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="export" />\" onclick=\"exportFile(\''+data.ID+'\')\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="export" /></i></a>';
							 
					}  
				},
				{
					"targets" : [ 8 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						   return  '<select id=\"009'+data.ID+'\" name=\"selectId\" class=\"select2\" '+
						   'style=\"width: 100px;\" onchange=\"selectChange(\''+data.ID+'\')\">'+
						   "<option value=\"-1\"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>"+
						   "<option value=\"1\"><fmt:message bundle='${pageScope.bundle}'  key='processing.failed' /></option>"+
						   "<option value=\"2\"><fmt:message bundle='${pageScope.bundle}'  key='processing.sucessfully' /></option>"+
						   "</select>"; 
						  /*  
						   '<select id=\"smsId\" name=\"smsId\" class=\"select2\"'+
						   '						style=\"width: 200px;\" onchange=\"smsChange()\">'+
						   '						<option value=\"1\">正在处理</option>'+
						   '						<option value=\"2\">处理完成</option>'+
						   '					</select>'; */
					
					} 
				},

		],

		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : true,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : true,
		"bAutoWidth" : true,
		"aaSorting" : [ [ 1, "asc" ] ],
		"bStateSave" : false,
		"sPaginationType" : "full_numbers",
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
            "sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='sorry,no.records' />",
            "sInfo" : "<fmt:message bundle='${pageScope.bundle}'  key='Current.view' /> _START_ <fmt:message bundle='${pageScope.bundle}'  key='To' />"+
            			"_END_ <fmt:message bundle='${pageScope.bundle}'  key='Article' />,<fmt:message bundle='${pageScope.bundle}'  key='Total' />"+
            			" _TOTAL_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
            "sInfoEmtpy" : "显示0到0条记录",
            "sInfoFiltered" : "",
            "sProcessing" : "<fmt:message bundle='${pageScope.bundle}'  key='Loading' />...",
            "sSearch" : "<fmt:message bundle='${pageScope.bundle}'  key='Search' />",
            "oPaginate" : {
                "sFirst" : "<fmt:message bundle='${pageScope.bundle}'  key='The.first.page' />",
                "sPrevious" : " <fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /> ",
                "sNext" : " <fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /> ",
                "sLast" : " <fmt:message bundle='${pageScope.bundle}'  key='The.last.page' /> "
            }
		},
		"aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
				[ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
	//设置每页显示记录的下拉菜单
	}
	function sreach() {
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "fileName",
					"value" : $("#fileName").val()
				}

				);
			}
		});
		$('#example').dataTable().fnDraw();
	}
	$(document).ready(function() {
		$('#example').dataTable(dataTableObj);
		selectFileId();
	});
	
	function selectexportfile(id) {
		
		 var exportVal = $('#'+id).val();
		 return exportVal;
	}
	function exportFile(id){
		
		  var exportVal=selectexportfile(id);
		  
		  window.location.href = "<%=basePath%>business/exportFile?fileID="+ id + "&exportVal="+exportVal;
	 }

	function selectChange(id) {

		var selectVal = $('#009'+id).val();
		alert(selectVal)
		if (selectVal == -1 ) {
			return false;
		}
		if (confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.update' />？")) {
			alert(selectVal)
			$.ajax({
				type : "post",
				url : "${ctx}/business/update",
				data : {
					"fileId" : id,
					"selectVal" : selectVal
				},
				success : function(data) {
					debugger;
					if (data.success) {
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.successfully' />");
						sreach();
					} else {
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.failed' />");
						sreach();
					}
				}
			});
		}

	}
</script>


</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<form role="form">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='File.name' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.a.file.name' />" id="fileName" name="fileName">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
			
		</form>
		
		<form role="form" action="/business/batchUpdate"
			method="post" enctype="multipart/form-data" id="cform">
			<div class="cl pd-5  bk-gray mt-20">
				<label class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.file.name' />:</label> 
				<select id="fileId" name="fileId" class="input-text select2"style="width: 200px;" >
					<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
				</select>
				 <label class="font12" id="label"><fmt:message bundle='${pageScope.bundle}'  key='Business.type' />:</label> 
				 <select id="typecodeId" name="typecodeId" class="input-text select2" style="width: 200px;">
				</select>
				 <label class="font12" id="label"><fmt:message bundle='${pageScope.bundle}'  key='processed.results' />:</label> 
				 <select id="resultId" name="resultId" class="input-text" style="width: 200px;">
					<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Process.unsuccessfully' /></option>
					<option value="2" selected><fmt:message bundle='${pageScope.bundle}'  key='Process.successfully' /></option>
				</select>
			</div>
			<div class="cl pd-5 mt-10">
				<label style="float:left"><fmt:message bundle='${pageScope.bundle}'  key='Import.files' />:</label>
				<input type="file" class=""  style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left"
					onclick="updateInfo();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
			
		</form>
		<script type="text/javascript">
		
		function updateInfo(){
			var option = {
				    url : "<%=basePath%>business/batchUpdate",
				    type: "post",
				    success : function(data){
				    	 //重新构建table  
				    	 if (data != null) {
			    		 	alert(data.msg);
						}
				    	sreach();
				   }, 
				   error:function(){
						alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
					}
			 };
			$("#cform").attr("enctype","multipart/form-data");
			//$("#cform").removeAttr("enctype");
			if(myValidate()&& validateFile())
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
				$("#cform").ajaxSubmit(option);
			}
		}
		   
		//验证信息
		function myValidate(){
			var fileId = $("#fileId").find("option:selected").val();
			if(fileId =='' || fileId == -1){
				 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.file.name' />");
				 return false;
			}
			var type = $("#typecodeId").find("option:selected").val();
			if(type =='' || type == -1){
				 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='select.the.business.type' />");
				 return false;
			}
			var version = $("#resultId").find("option:selected").val();
			if(version =='' || version == -1){
				
				 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Select.the.processing.result' />");
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
		
		
		
		</script>
			
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">

				<thead>
					<tr class="zpTable">
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='File.name' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='create.time' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Processing.type' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Processing.successful.number' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Processing.failure.number' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Pending.number' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Total.card.number' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Export.details' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Update.all' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>

