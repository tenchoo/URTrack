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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Presentation.of.Income.statement' /></title>
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

<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="static/lib/laypage/1.2/laypage.js"></script>  
<script src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script> 
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>

<script type="text/javascript" src="static/js/jquery.form.js"></script> 
<script type="text/javascript" src="static/js/jquery.validate.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>

<link rel="stylesheet" href="static/h5/css/selectize.bootstrap3.css">
<script type="text/javascript" src="static/h5/js/selectize.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<script type="text/javascript">
function send(){ 
	var channelCustId = $("#channelCustId").find("option:selected").val();
	var startDate = $('#startDate').val();
	var endDate = $('#endDate').val();
	
	if( channelCustId== "" || channelCustId == "-1"){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.customers' />！");
		return false;
	}
	if(startDate == ""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.start.time' />！");
		return false;
	}
	if(endDate == ""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.end.time' />！");
		return false;
	}
	if(endDate < startDate){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Select.a.valid.end.time' />！");
		return false;
	}
	
	$('#example').dataTable().fnClearTable();   //将数据清除  
	var oSettings =  $('#example').dataTable().fnSettings();
    oSettings.aoServerParams.push({
        "fn": function (aoData) {
            aoData.push(
            		{"name" :  "channelCustId", "value": $("#channelCustId").find("option:selected").val()},
            		{"name" :  "paymentOp", "value": $("#paymentOp").find("option:selected").val()},
            		{"name" :  "iccid", "value": $('#iccid').val()},
            		{"name" :  "msisdn", "value": $('#msisdn').val()},
            		{"name" :  "startDate", "value": $('#startDate').val()},
            		{"name" :  "endDate", "value": $('#endDate').val()}
	 	    );}
    });
    $('#example').dataTable().fnDraw();
}

 $(function(){
 	$('#paymentOp').selectize({allowEmptyOption: true});
 	$('#channelCustId').selectize();
 	$('#example').dataTable(dataTableObj);
}); 
 
 var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>chargeOff/revenue",    /* 跳转url */
			"iDisplayLength": 10,  /* 展示条数 */
	 					 "columnDefs": [ 
				         {"targets": [0],"data": "custName"} ,
				         {"targets": [1],"data": "iccid"} ,
				         {"targets": [2],"data": "msisdn"} ,
				         {"targets": [3],"data": "operatorName"} ,
				         {"targets": [4],"data": "goodName"} ,
				         {"targets": [5],"data": "status"}, 
				         {"targets": [6],"data": "realFee"}, 
				         {"targets": [7],"data": "staticName"}, 
				         {"targets": [8],"data": "recvTime"}
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
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		
		if( channelCustId== "" || channelCustId == "-1"){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.customers'/>！");
			return false;
		}
		if(startDate == ""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.start.time' />！");
			return false;
		}
		if(endDate == ""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.end.time' />！");
			return false;
		}
		if(endDate < startDate){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Select.a.valid.end.time' />！");
			return false;
		}
	 layer.open({  
         content: '<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.export' />？',  
         btn: ['<fmt:message bundle='${pageScope.bundle}'  key='YES' />', '<fmt:message bundle='${pageScope.bundle}'  key='NO' />'],  
         yes: function(index, layero) {  
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
		<form method="post"  name="sform" id="sform" action="<%=basePath%>chargeOff/exportRevenue">
			<div class="row">
	            <label class="font12 col-2" style="width: 10%"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
				<select id="channelCustId" name="channelCustId" class="col-2 font12" style="width:250px">
					<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
					<c:forEach var="agent" items="${agents}">
						<option value="${agent.custId}">${agent.custName}</option>
				    </c:forEach>
				</select>
		        <label class="font12 col-2" style="width: 10%"><fmt:message bundle='${pageScope.bundle}'  key='payment.type' />：</label>
				<select id="paymentOp" name="paymentOp" class="col-2 font12" style="width:250px">
					<option value=""><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
					<c:forEach var="type" items="${paymentType}">
						<option value="${type.staticId}">${type.staticName}</option>
				    </c:forEach>
	            </select>
			</div>
			<div class="row">
	            <label class="font12 col-2" style="width: 10%">ICCID：</label>
				<input class="input-text" id="iccid" style="width:250px;float:left" type="text">
		        <label class="font12 col-2" style="width: 10%"><fmt:message bundle='${pageScope.bundle}'  key='current.used.number' />：</label>
				<input class="input-text" id="msisdn" style="width:250px" type="text">
			</div>
			<div class="row">
	            <label class="font12 col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Start.time' />：</label>
	            <input class="input-text" id="startDate" style="width:250px;float:left" type="text" name="startDate" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMMdd',minDate:'1970-01-01',maxDate:'%y-{%M}'})" readonly="readonly">
	            <label class="font12 col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='end.time' />：</label>
	            <input class="input-text" id="endDate" style="width:250px" type="text" name="endDate" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMMdd',minDate:'1970-01-01',maxDate:'%y-{%M}'})" readonly="readonly">
	            <input style="margin-right: -25px;" class="btn btn-primary radius"  type="button" onclick="send()" id="button"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Query' />&nbsp;&nbsp;" />
	            <input style="margin-left: 25px;" class="btn btn-primary radius"  type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Export' />&nbsp;&nbsp;"  onclick="exportExcel()" />
	         </div>   
		</form>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
				<th >ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='current.used.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='current.state.of.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='amount.of.money' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='type.of.expense' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
			</tr>
		</thead>
		</table>
	</div>
	</div>
</body>
</html>