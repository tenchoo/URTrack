<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title>卡划拨</title>
	<base href="<%=basePath%>" />
	<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
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

	<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	
</head>
<body>
	<div class="pd-20 font12">
	
	<input type="text" style="width:200px" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Batch.number' />">
	<button id="search2" type="button" class="btn btn-primary radius" onclick="queryByBatchId();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
	<button type="button" class="btn btn-primary radius" onclick="layer_show('卡划拨','${ctx}/iccidassignbatch/assignCard','800','550')"><i class="Hui-iconfont">&#xe665;</i>划拨</button>
	<button type="button" class="btn btn-primary radius" onclick="layer_show('卡批量划拨','${ctx}/iccidassignbatch/assignCardUpload','800','550')"><i class="Hui-iconfont">&#xe665;</i>批量划拨</button>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Batch.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.size' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='card.cost' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='start' />ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='end' />ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Total.quantity' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
			</tr>
		</thead>
	</table>
	</div>
</div>
<script type="text/javascript">
		$(function(){
		var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath %>iccidassignbatch/get"
			             ,    /* 跳转url */
			"iDisplayLength": 5,  /* 展示条数 */
			"columnDefs": [ 
				         {"targets": [0],"data": "batchId"} ,
				         {"targets": [1],"data": "simType"} ,
				         {"targets": [2],"data": "simSize"} ,
				         {"targets": [3],"data": "simFee"} ,
				         {"targets": [4],"data": "iccidStart"} ,
				         {"targets": [5],"data": "iccidEnd"} ,
						 {
							"targets" : [ 6 ],
							"data" : "recvTime",
							"mRender" : function(data, type, full) {
								if (full.recvTime != null) {
									var updateTime = new Date(full.recvTime)
											.format("yyyy-MM-dd");
									return updateTime;
								} else {
									return "";
								}
							}
						}, {
							"targets" : [ 7 ],
							"data" : "sumNum"
						},
						{
							"targets" : [ 8 ],
							"data" : "batchId",
							"mRender" : function(data, type, full) {
								return '<a title=\"卡基本信息\" href="javaScript:toBasicCard('
                                + full.batchId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">卡基本信息</a> '
                                /* +'<a title=\"SIM卡、号信息\" href="javaScript:toSIMCard('
                                + full.batchId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">SIM卡、号信息</a> ' */
                                +'<a title=\"录入信息\" href="javaScript:toOneQuery('
                                + full.batchId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">录入信息</a> '
                                +'<a title=\"录入明细\" href="javaScript:toQuery('
                                + full.batchId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">录入明细</a> '
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
				"bDestroy" : true,
				"bSort" : false,
				"bInfo" : true,
				"bAutoWidth" : true,
				"aaSorting" : [ [ 0, "desc" ] ],
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
		$('#example').dataTable(dataTableObj);
		$("#operators").change(function() {
			$('#cardstatus').empty();
			$.ajax({
				url:"<%=basePath%>userNewService/getCardstatusList",
				data:{"operators":$("#operators").val()},
				success:function(result){
					var select=$("#cardstatus").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Card.type" />',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
		});
	    
	});
		
		function queryByStartIccid(){
			$('#example').dataTable().fnClearTable(false);
			var oSettings = $('#example').dataTable().fnSettings();
			oSettings.aoServerParams.push({
				"fn" : function(aoData) {
					aoData.push({
						"name" : "iccidStart",
						"value" : $("#iccidStartByQuery").val()
					});
				}
			});
			$('#example').dataTable().fnDraw();
		}
		
		function toQuery(batchId){
			if(batchId != ""){
				window.location.href='<%=basePath %>iccidassignbatch/queryDetail?batchId='+batchId;
			}
		}
		function toOneQuery(batchId){
			if(batchId != ""){
				window.location.href='<%=basePath %>iccidassignbatch/queryOneDetail?batchId='+batchId;
			}
		}
		function toBasicCard(batchId){
			if(batchId != ""){
				layer_show('卡基本信息','${ctx}/iccidassignbatch/basicCard?batchId='+batchId,'800','550');
			}
		}
		function toSIMCard(batchId){
			if(batchId != ""){
				layer_show('SIM卡、号信息','${ctx}/iccidassignbatch/SIMCard?batchId='+batchId,'800','550');
			}
		}
</script>
</body>
</html>
