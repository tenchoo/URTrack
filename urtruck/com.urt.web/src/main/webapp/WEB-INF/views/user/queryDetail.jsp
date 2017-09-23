<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>   
<html>
<head>
	<title></title>
	
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
	
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
</head>
<body>
	<div class="pd-20">
	<h4 align="center"><fmt:message bundle='${pageScope.bundle}'  key='Input.detailed.information.in.batch' /></h4>
	<input id="batchId" type="hidden" value="${batchId}"/>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Batch.number' /></th> -->
				<th >ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Customer' />ID</th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Device.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Password' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.subdivision.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Initial.product' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.1' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.1' /></th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.2' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.2' /></th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' /></th> -->
				<th >MSISDN</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
			</tr>
		</thead>
	</table>
	</div>
</div>
	<script type="text/javascript">
	var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" :"<%=basePath %>userNewService/getDetailIccidInfo",
			"fnServerParams": function ( aoData ) {
	           aoData.push( {
	            "name" :  "batchId",
	            "value": $("#batchId").val()} );
	           },
			"iDisplayLength": 10,  /* 展示条数 */
	 					
				           
			  "sScrollX": "100%",
			  "sScrollXInner": "100%",
			  "bScrollCollapse": true,
			  "bPaginate": true,
			  "bLengthChange": false,
			  "bFilter": false,
			  "bDestroy":true,
			  "bSort": false,
			  "bInfo": true,
			  "bAutoWidth": true,
			  "aaSorting": [[1, "asc"]],
			  "bStateSave": false, 
			  "sPaginationType": "full_numbers",
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
		    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
		}
	var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" :"<%=basePath %>userNewService/getDetailIccidInfo",
			"fnServerParams": function ( aoData ) {
	           aoData.push( {
	            "name" :  "batchId",
	            "value": $("#batchId").val()} );
	           },
			"iDisplayLength": 10,  /* 展示条数 */
			 "columnDefs": [
							 /* {"targets": [0],"data": "batchId"} , */
					         {"targets": [0],"data": "iccid"} ,
					         {"targets": [1],"data": "custid"} ,
					      /*    {"targets": [3],"data": "devicetype"} ,
					         {"targets": [4],"data": "privatekey"} , */
					         {"targets": [2],"data": "cardtype"} ,
					         {"targets": [3],"data": "initproduct"} ,
					         {"targets": [4],"data": "cardstatus"} ,
					         {"targets": [5],"data": "operators"} ,
					         {"targets": [6],"data": "ctype"} ,
					        /*  {"targets": [10],"data": "attribute1"} , */
					         {"targets": [7],"data": "value1"} ,
					        /*  {"targets": [12],"data": "attribute2"} , */
					         {"targets": [8],"data": "value2"} ,
					        /*  {"targets": [14],"data": "ifMaintenance"} , */
					         {"targets": [9],"data": "msisdn"} ,
					         {
									"targets" : [ 10 ],
									"data" : "inDate",
									"mRender" : function(data, type, full) {
										if (full.inDate != null) {
											var updateTime = new Date(full.inDate)
													.format("yyyy-MM-dd");
											return updateTime;
										} else {
											return "";
										}
									}
								},
					         
				           ],
			  "sScrollX": "100%",
			  "sScrollXInner": "100%",
			  "bScrollCollapse": true,
			  "bPaginate": true,
			  "bLengthChange": false,
			  "bFilter": false,
			  "bSort": false,
			  "bInfo": true,
			  "bAutoWidth": true,
			  "aaSorting": [[1, "asc"]],
			  "bStateSave": false, 
			  "sPaginationType": "full_numbers",
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
		    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
		}
	$(function(){
		$('#example').dataTable(dataTableObj);
	});

	</script>
</body>
</html>
 