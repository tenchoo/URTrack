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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Role.management' /></title>
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
	<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
	<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
	<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css"
		href="${ctx}/static/ui/css/bootstrap.css" media="all" />
	<link rel="stylesheet" type="text/css"
		href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
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
	<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" :"<%=basePath %>batchQuery/queryFailDataList",
		"fnServerParams": function ( aoData ) {
           aoData.push( {
            "name" :  "batchId",
            "value": $("#batchId").val()} );
           },
		"iDisplayLength": 5,  /* 展示条数 */
 					 "columnDefs": [ 
			         {"targets": [0],"data": "batchId"} ,
			         {"targets": [1],"data": "tradeTypeCode"} ,
			         {"targets": [2],"data": null,"mRender": function ( data, type, full ) {
			        	    var flowStatus=data.flowStatus;
			        	    if(flowStatus=="1"){
			        	    	flowStatus="<fmt:message bundle='${pageScope.bundle}'  key='data.verification' />";
			        	    }else if(flowStatus=="2"){
			        	    	flowStatus="<fmt:message bundle='${pageScope.bundle}'  key='create.order' />";
			        	    }else if(flowStatus=="3"){
			        	    	flowStatus="<fmt:message bundle='${pageScope.bundle}'  key='business.process' />";
			        	    }else if(flowStatus=="4"){
			        	    	flowStatus="<fmt:message bundle='${pageScope.bundle}'  key='Business.process.completion' />";
			        	    }else{
			        	    	flowStatus="<fmt:message bundle='${pageScope.bundle}'  key='data.verification' />";
			        	    }
		                	return flowStatus;
		        	 }} ,
			         {"targets": [3],"data": "msisdn"} ,
			         {"targets": [4],"data": "iccid"} ,
			         {"targets": [5],"data": "recvTime"} ,
			         {"targets": [6],"data": null,"mRender": function ( data, type, full ) {
			        	    var dealTag=data.dealTag;
			        	    if(dealTag=="0"){
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='pending' />";
			        	    }else if(dealTag=="2"){
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='processing.sucessfully' />";
			        	    }else if(dealTag=="3"){
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='processing.failed' />";
			        	    }else{
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='processing' />";
			        	    }
		                	return dealTag;
		        	 }} ,
			         {"targets": [7],"data": "resultInfo"} ,
			         {"targets": [8],"data": "operId"} ,
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
	  function download(batchId){
        var url="<%=basePath %>batchQuery/exportExcel?batchId="+batchId;
        window.location.href=url;
    }
	$(document).ready(function(){
		$('#example').dataTable(dataTableObj);
	});
	function sreach(){
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "batchId",
			            "value": $("#batchId").val()}
	 	            	 );}
	    });
	    $('#example').dataTable().fnDraw();
	}
	</script>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-10" style="text-align:center;">
	       <button type="button" onclick="download('${batchId}')" class="btn btn-primary radius"><fmt:message bundle='${pageScope.bundle}'  key='export.data' /></button>
	   	</div>
	</form>
	<div class="pd-20">
	<input id="batchId" type="hidden" value="${batchId}"/>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th><fmt:message bundle='${pageScope.bundle}'  key='Batch.serial.number' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Business.type' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='data.circulation.state' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Service.numbers' /></th>
				<th>iccid</th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Processing.state' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Result.Message' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
				</tr>
		</thead>
	</table>
	</div>
</div>
</body>
</html>
 