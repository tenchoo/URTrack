<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Consumer.record.presentation' /></title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css" />
<base href="<%=basePath%>" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	
<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<script type="text/javascript" src="static/js/jquery.validate.js"></script>

<link rel="stylesheet" href="static/h5/css/selectize.bootstrap3.css">
<script type="text/javascript" src="static/h5/js/selectize.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<script type="text/javascript">
function send(){ 
	var channelCustId = $("#channelCustId").find("option:selected").val();
	var month = $('#month').val();
	
	if(month == ""){
		layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Please.Select.Inquiry.Month" />！");
		return false;
	}
	
	$('#example').dataTable().fnClearTable();   //将数据清除  
	var oSettings =  $('#example').dataTable().fnSettings();
    oSettings.aoServerParams.push({
        "fn": function (aoData) {
            aoData.push(
            		{"name" :  "custId", "value": $("#channelCustId").find("option:selected").val()},
            		{"name" :  "month", "value": $('#month').val()}
	 	    );}
    });
    $('#example').dataTable().fnDraw();
}
function send2(){ 
	var channelCustId = $("#channelCustId").find("option:selected").val();
	var month = $('#month').val();
	
	if(month == ""){
		layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Please.Select.Inquiry.Month" />！");
		return false;
	}
	
	$('#example2').dataTable().fnClearTable();   //将数据清除  
	var oSettings =  $('#example2').dataTable().fnSettings();
    oSettings.aoServerParams.push({
        "fn": function (aoData) {
            aoData.push(
            		{"name" :  "custId", "value": $("#channelCustId").find("option:selected").val()},
            		{"name" :  "month", "value": $('#month').val()}
	 	    );}
    });
    $('#example2').dataTable().fnDraw();
}

 $(function(){
 	$('#channelCustId').selectize();
 	$('#example').dataTable(dataTableObj);
 	$('#example2').dataTable(dataTableObj2);
}); 
 
 var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>chargeQuery/queryChargeHistory",    /* 跳转url */
			"iDisplayLength": 10,  /* 展示条数 */
	 					 "columnDefs": [ 
				         {"targets": [0],"data": "custName"} ,
				         {"targets": [1],"data": "iccid"} ,
				         {"targets": [2],"data": "goodsName"} ,
				         {"targets": [3],"data": "pagNumber"} ,
				         {"targets": [4],"data": "fee"},
				         {"targets": [5],"data": "goodsPrices"}
			           ],
			             
	          "sScrollX": "100%",
	          "bStateSave": true ,
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
 
 var dataTableObj2 = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>chargeQuery/queryChargeHistory",    /* 跳转url */
			"iDisplayLength": 10,  /* 展示条数 */
	 					 "columnDefs": [ 
				         {"targets": [0],"data": "custName"} ,
				         {"targets": [1],"data": "iccid"} ,
				         {"targets": [2],"data": "goodsName"} ,
				         {"targets": [3],"data": "pagNumber"} ,
				         {"targets": [4],"data": "fee"} ,
				         {"targets": [5],"data": "payDateStr"}, 
				         {"targets": [6],"data": "orderResult"}
			           ],
			             
	          "sScrollX": "100%",
	          "bStateSave": true ,
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
 function exportExcel(){
	 var channelCustId = $("#channelCustId").find("option:selected").val();
		var month = $('#month').val();
		
		if(month == ""){
			layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Please.Select.Inquiry.Month" />！");
			return false;
		}
	 layer.open({  
         content: '<fmt:message bundle="${pageScope.bundle}"  key="Sure.To.Export" />?',  
         btn: ['<fmt:message bundle="${pageScope.bundle}"  key="Confirm" />', "<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"],  
         yes: function(index, layero) {  
        	 layer.close(index);
        	 $("#sform").submit(); 
         },  
         cancel: function() {  
        	 layer.close();
         }  
     }); 
 }
	
</script>

</head>
<body>
	<div class="pd-20">
		<form method="post"  name="sform" id="sform" action="<%=basePath%>chargeQuery/exportOrder">
			<div class="row">
				<c:if test="${right eq true}">
		            <label class="font12 col-2" style="width: 10%"><fmt:message bundle="${pageScope.bundle}"  key="Customer.Name" />：</label>
					<select id="channelCustId" name="custId" class="col-2 font12" style="width:250px">
						<option value="-1">Please.Select</option>
						<c:forEach var="agent" items="${agents}">
							<option value="${agent.custId}">${agent.custName}</option>
					    </c:forEach>
					</select>
				</c:if>
		       <label class="font12 col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle="${pageScope.bundle}"  key="Choose.Month" />：</label>
	            <input class="input-text" id="month" style="width:250px" type="text" name="month"  onClick="WdatePicker({dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M+1}',skin:'whyGreen',readOnly:true})">
	            <c:if test="${right eq true}">
		            <input style="margin-right: -25px;" class="btn btn-primary radius"  type="button" onclick="send()" id="button"  value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Inquiry" />&nbsp;&nbsp;" />
	            </c:if>
	            <c:if test="${right eq false}">
		            <input style="margin-right: -25px;" class="btn btn-primary radius"  type="button" onclick="send2()" id="button"  value="&nbsp;&nbsp;inquiry&nbsp;&nbsp;" />
	            </c:if>
	            <input style="margin-left: 25px;" class="btn btn-primary radius"  type="button" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Export" />&nbsp;&nbsp;"  onclick="exportExcel()" />
			</div>
		</form>
	<div class="mt-20">
		<c:if test="${right eq true}">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Customer.Name" /></th>
					<th >ICCID</th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Order.Product" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Number" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Payment.Amount(Yuan)" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Unit.(Yuan)" /></th>
				</tr>
			</thead>
			</table>
		</c:if>
		<c:if test="${right eq false}">
			<table id="example2" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Customer.Name" /></th>
					<th >ICCID</th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Order.Product" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Number" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Payment.Amount(Yuan)" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Order.Time" /></th>
					<th ><fmt:message bundle="${pageScope.bundle}"  key="Order.Result" /></th>
				</tr>
			</thead>
			</table>
		</c:if>
	</div>
	</div>
</body>
</html>