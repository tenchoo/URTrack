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
<title><fmt:message bundle='${pageScope.bundle}'  key='Device.binding' /></title>
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

<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript">

	var htmlList=[];
	var htmlList2=[];
	$.ajax({
		url:"/traffic/getChannelCust",
		data:{},
		success:function(result){
			var list=result;
			    for(var i=0;i<list.length;i++){
			    	var mapObject=list[i];
			    	var str='<option value="'+mapObject.id+'">'+mapObject.text+'</option>';
			    	htmlList.push(str);
			    }
			    $("#channelCustId").html(htmlList.join(" "));
		}
	});
	$.ajax({
		url:"/deviceBindings/getIdType",
		data:{},
		success:function(result){
			var list=result;
			    for(var i=0;i<list.length;i++){
			    	var mapObject=list[i];
			    	var str='<option value="'+mapObject.id+'">'+mapObject.text+'</option>';
			    	htmlList2.push(str);
			    }
			    $("#idType").html(htmlList2.join(" "));
		}
	});
	function doAddOne(){
		var iccid = $("#iccid").val();
		var idType = $("#idType").val();
		var deviceId = $("#deviceId").val();
		if (iccid == "" || idType =="-1" || deviceId == "" ) {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.input.iccid' />！");
			return false;
		}
		$.ajax({
			url:"/deviceBindings/addOneDeviceBindings",
			data:{
				"iccid":iccid,
				"idType":idType,
				"deviceId":deviceId,
			},
			success:function(result){
				alert(result.msg);
				sreach();
			}
		});
	}
	function delOne(relId){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.cancel.this.binding' />?")){
			$.ajax({
				url:"/deviceBindings/delOneDeviceBindings",
				data:{
					"relId":relId,
				},
				success:function(result){
					alert(result.msg);
					sreach();
				}
			});
		}
	}
	function toUpdate(relId){
		if(relId != ""){
			url='${ctx}/deviceBindings/toUpdate/'+relId;
			layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Device binding modification" />',url,'800','550');
		}
	}
	
	function upload(){
		var option = {
			url : "<%=basePath%>/deviceBindings/batchImport",
			type : "post",
			success : function(data) {
				alert(data.retMsg);
			}
		};
		if(validateFile())
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
				$("#sform").ajaxSubmit(option);
			}
	}
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
	function templet(){
		window.location.href = "${ctx}/download/deviceBindings.xlsx";
	}
</script>
</head>
<body>
	<div class="pd-20" align="center">
		<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='Batch.device.binding' /></h1>
			<br>
		</div>
		<form role="form" action="/            "
			method="post" enctype="multipart/form-data" id="sform">
			<div class="cl pd-5 mt-10">
				<label class="font12" style="float:left"><fmt:message bundle='${pageScope.bundle}'  key='Import.files' />:</label>
				<input type="file" class="font12"  style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left"
					onclick="upload();">
					<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='Import.file' />
				</button>
			</div>
		</form>
		<div class="cl pd-5  bk-gray mt-20">
			<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
					onclick="templet();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Download.templates' />
			</button>
			<!-- <label style="float: left;" class="font12">请下载上传文件模板：<a href="##">点击下载模板</a></label> -->
		</div>
		
		<div>
			<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='individual.equipment.operator' /></h1>
			<br>
		</div>
		
		<div class="oh row">
			<div class="oh row">
				<div class="oh row">
		  			<label class="font12" style="float: left;width: 12%;"><fmt:message bundle='${pageScope.bundle}'  key='Channels.customer.name' />：&nbsp;&nbsp;</label>
		  			<select id="channelCustId" name="ChannelCustId" class="input-text" style="float: left;width: 20%;"></select>
		  			<label class="font12" style="float: left; width: 30%;">ICCID：</label>
		  			<input type="text" id="iccid"  style="margin-right:10em;width: 20%;height: 32px;">
    			</div>
    			<br>
    			<div class="oh row">
		  			<label class="fl  font12" style="float: left;width: 12%;"><fmt:message bundle='${pageScope.bundle}'  key='bind.type' />：&nbsp;&nbsp;</label>
		  			<select id="idType" name="idType" class="input-text" style="float: left;width: 20%;"></select>
		  			<label class="font12" style="float: left;width: 30%;"><fmt:message bundle='${pageScope.bundle}'  key='binding.equipment.numbers' />：</label>
					<input type="text"  id="deviceId" style="margin-right:10em; width: 20%;height: 32px;">
    			</div>
    			<br>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius" 
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='Query' />
				</button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="doAddOne" type="button" class="btn btn-primary radius" 
					onclick="doAddOne();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='New' />
				</button>
			</div>
			<div class="mt-20">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='Channels.customer.name' /></th>
						<th class="font12" align="center">ICCID</th>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='bind.type' /></th>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='binding.equipment.numbers' /></th>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
						<th class="font12" align="center"><fmt:message bundle='${pageScope.bundle}'  key='Operation.type' /></th>
					</tr>
				</thead>
			</table>
			</div>
			<input type="hidden" id="doUpdateMsg" value="${doUpdateMsg}">
		</div>
	<script type="text/javascript">

	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>deviceBindings/toSelectByCondition"
					, /* 跳转url */
			"iDisplayLength" : 10, /* 展示条数 */
			"columnDefs" : [
					{
						"targets" : [ 0 ],
						"data" : "custName",
					},
					{
						"targets" : [ 1 ],
						"data" : "iccid",
					},
					{
						"targets" : [ 2 ],
						"data" : "idType",
					},
					{
						"targets" : [ 3 ],
						"data" : "deviceId",
					},
					{
						"targets" : [ 4 ],
						"data" : "updateTime",
						"mRender" : function(data, type, full) {
							if (full.updateTime != null){
			            		var updateTime = new Date(full.updateTime).format("yyyy-MM-dd hh:mm:ss"); 
			                	return updateTime;
			            	} else {
			            		return "";
			            	}
						}
					},
					{
						"targets" : [ 5 ],
						"data" : "operId",
					},
					{
						"targets" : [ 6 ],
						"data" : "relId",
						"mRender" : function(data, type, full) {
						
							return '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:delOne('
									+ full.relId
									+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></i></a>'
									
									+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Modify" />\" href="javaScript:toUpdate('
									+ full.relId
									+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Modify" /></a>'
									;
						}
					}

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

		$(document).ready(function() {
			$('#example').dataTable(dataTableObj);
		});

		function sreach() {
			$('#example').dataTable().fnClearTable(false);
			var oSettings = $('#example').dataTable().fnSettings();
			oSettings.aoServerParams.push({
				"fn" : function(aoData) {
					aoData.push({
						"name" : "channelCustId",
						"value" : $("#channelCustId").val()
					}, {
						"name" : "iccid",
						"value" : $("#iccid").val()
					}, {
						"name" : "idType",
						"value" : $("#idType").val()
					}, {
						"name" : "deviceId",
						"value" : $("#deviceId").val()
					});
				}
			});
			$('#example').dataTable().fnDraw();
		}
		var doUpdateMsg = $('#doUpdateMsg').val();
		if (doUpdateMsg != "") {
			alert(doUpdateMsg);
		}
	</script>
	</div>
</body>
</html>