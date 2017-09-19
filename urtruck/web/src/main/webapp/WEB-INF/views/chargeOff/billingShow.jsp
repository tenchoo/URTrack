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
<title><fmt:message bundle='${pageScope.bundle}'  key='presentation.of.statement.of.operator.account.results' /></title>
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
<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="static/js/H-ui.js"></script> 
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="static/js/jquery.form.js"></script> 
<script type="text/javascript" src="static/js/jquery.validate.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>

<link rel="stylesheet" href="static/h5/css/selectize.bootstrap3.css">
<script type="text/javascript" src="static/h5/js/selectize.js"></script>

<script type="text/javascript">
function send(){ 
	var operators = $("#operatorsId").find("option:selected").val();
	/* if(operators =='' || operators == -1){
		 layer.msg("请选择运营商");
		 return false;
	} */
	
	var cycleId = $('#cycleId').val();
	if(cycleId == ""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />！");
		return false;
	}
	$('#example').dataTable().fnClearTable();   //将数据清除  
	var oSettings =  $('#example').dataTable().fnSettings();
    oSettings.aoServerParams.push({
        "fn": function (aoData) {
            aoData.push(
            		{"name" :  "operatorsId", "value": $("#operatorsId").find("option:selected").val()},
            		{"name" :  "cycleId", "value": $('#cycleId').val()}
	 	    );}
    });
    $('#example').dataTable().fnDraw();
}

function filterGlobal () {
    $('#example2').DataTable().search($('#global_filter').val()).draw();
}
 $(function(){
 	$('#operatorsId').selectize();
 	$('#example').dataTable(dataTableObj);
 	$('#example2').dataTable(dataTableObj2);
 	$('input.global_filter').on( 'keyup click', function () {
         filterGlobal();
     } ); 
}); 
 
 var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>chargeOff/getBillsResult",    /* 跳转url */
			"iDisplayLength": 10,  /* 展示条数 */
	 					 "columnDefs": [ 
				         {"targets": [0],"data": "operatorsName"} ,
				         {"targets": [1],"data": "sumNum"} ,
				         {
			                  "targets": [2],
			                  "data": null,
			                  "mRender": function (data, type, full) {
			                	  return "<a href='javascript:query("+full.operatorsId+","+full.cycleId+",1)' >"+full.succNum+"</a>" ;//'<fmt:formatDate value='${full.inDate}' pattern="yyyy-MM-dd HH:mm:ss" />';
			             }},
			             {
			                  "targets": [3],
			                  "data": null,
			                  "mRender": function (data, type, full) {
			                	  return "<a href='javascript:query("+full.operatorsId+","+full.cycleId+",2)' >"+full.failNum+"</a>" ;//'<fmt:formatDate value='${full.inDate}' pattern="yyyy-MM-dd HH:mm:ss" />';
			             }},
			             {
			                  "targets": [4],
			                  "data": null,
			                  "mRender": function (data, type, full) {
			                	  return "<a href='javascript:query("+full.operatorsId+","+full.cycleId+",0)' >"+full.unChargeNum+"</a>" ;//'<fmt:formatDate value='${full.inDate}' pattern="yyyy-MM-dd HH:mm:ss" />';
			             }},
				         {"targets": [5],"data": "totalCost"} ,
				         {"targets": [6],"data": "glaTotalCost"} ,
				         {"targets": [7],"data": "operateTime"} 
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
	 <%-- var option = {
			    url : "<%=basePath %>chargeOff/exportBilling",
			    type: "post",
			    async: false,
			    success : function(data){
			    	layer.msg("导出成功！");
			   }, 
			   error:function(){
				   layer.msg("导出失败！");
				}
		 }; --%>
	 if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.export' />？")){
		 $("#cform").submit();
	 }
 }
 function query(opId,cId,flag){
		$("#billShow").show();
		$("#opId").val(opId);
		$("#cId").val(cId);
		$("#flag").val(flag);
		$('#example2').dataTable().fnClearTable();   //将数据清除  
		var oSettings =  $('#example2').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{"name" :  "operatorsId", "value": $("#opId").val()},
	            		{"name" :  "cycleId", "value": $("#cId").val()},
	            		{"name" :  "balanceTag", "value": $("#flag").val()}
		 	    );}
	    });
	    $('#example2').dataTable().fnDraw();
	}
var dataTableObj2 = {
		/* "searching": true, */
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>chargeOff/getOperatorsBills",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
					 "columnDefs": [ 
			         {"targets": [0],"data": "operatorsName"} ,
			         {"targets": [1],"data": "iccid"} ,
			         {"targets": [2],"data": "msisdn"} ,
			         {"targets": [3],"data": "productName"} ,//productName  operatorsPid
			         {"targets": [4],"data": "fee"} ,
			         {"targets": [5],"data": "glaFee"} ,
			        /*  {"targets": [6],"data": "balanceTag"}  0-未对账；1-已平账；2-未平账*/
			         {"targets": [6],
			         "data": null,
	                  "mRender": function (data, type, full) {
	                	  if(full.balanceTag == 0){
	                		  return "<fmt:message bundle='${pageScope.bundle}'  key='unchecked.account.statement'/>";
	                	  }else if(full.balanceTag == 1){
	                		  return "<fmt:message bundle='${pageScope.bundle}'  key='balanced.account '/>";
	                	  }else{
	                		  return "<fmt:message bundle='${pageScope.bundle}'  key='unbalanced.account '/>";
	                	  }
	                  }
					}
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
	
	
</script>

</head>
<body>
	<div class="pd-20">
		<form method="post"  name="sform" id="sform">
			<div class="row">
		        <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />：</label>
				<select id="operatorsId" name="operatorsId" class="col-2 font12" style="width:250px">
	             	<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
					<c:forEach var="operator" items="${operators}">
						<option value="${operator.operatorsId}">${operator.operatorsName}</option>
				    </c:forEach>
	            </select>
	            
	            <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />：</label>
	            <input class="input-text" id="cycleId" style="width:250px" type="text" name="cycleId" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M}'})" readonly="readonly">
	            
	            <input style="margin-right: -25px;" class="btn btn-primary radius"  type="button" onclick="send()" id="button"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Query' />&nbsp;&nbsp;" />
	         </div>   
	         <!-- <div class="oh row">
	             <label for="mark" class="font12 col-2" style="width: 15%"></span>打标标记：</label>
	             <select id="mark" name="mark" class="demo-default col-2 font12" style="width:250px">
	             	0-未对账；1-已平账；2-未平账 
	             	 <option value="-1">请选择</option>
	            	 <option value="0">未对账</option>
	            	 <option value="2">未平帐</option>
	            	 <option value="1">已平账</option>
	            </select>
	        </div>  -->
		</form>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='reconciliation.numbers' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='balanced.account.numbers' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='unbalanced.account.numbers' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='unchecked.account.statement.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator.account.statement.charges' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='GLA.payment.statement.charges' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
			</tr>
		</thead>
		</table>
	</div>
	
	<br><br><br><br>
	<div class="mt-20" id="billShow" style="display:none">
		<div class="cl pd-5 bg-1 bk-gray mt-20 font12">
			<!-- <label>账期</label> --> 
			<!-- style="margin-left: 65px;" -->
			<input  class="btn btn-primary radius"  type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Export' />&nbsp;&nbsp;"  onclick="exportExcel()" />
			<!-- Global search<input class="global_filter" id="global_filter" type="text"> -->
		</div>
		<form action="chargeOff/exportBilling" method="post" style="display:none" id="cform">
			<input style="display:none" type="text" id="opId" name="opId">
			<input style="display:none" type="text" id="cId" name="cId">
			<input style="display:none" type="text" id="flag" name="flag">
		</form>
		<table id="example2" class="table table-border table-bordered table-hover table-bg table-sort"  cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
				<th >ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='use.telephone.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator.charges' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='GLA.payment.statement.charges ' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Marking.tags' /></th>
			</tr>
		</thead>
		</table>
	</div>
	</div>
</body>
</html>