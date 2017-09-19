<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="wm" uri="/WEB-INF/tlds/watchme.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<!--[if lt IE 7]>	<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>	   <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>	   <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<base href="<%=basePath%>">
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Account.information.inquiry' /></title>
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
	<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>user/toAccountDetail",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
 					 "columnDefs": [ 
			         {"targets": [0],"data": "PAY_NAME","sDefaultContent" : ""} ,
			         {"targets": [1],"data": "SCORE_VALUE","sDefaultContent" : ""} ,
			         {"targets": [2],"data": "CREDIT_CLASS_ID","sDefaultContent" : ""} ,
			         {"targets": [3],"data": "CREDIT_VALUE","sDefaultContent" : ""} ,
			         {"targets": [4],"data": "CONTRACT_NO","sDefaultContent" : ""} ,
			         {"targets": [5],"data": "REMOVE_TAG_NAME","sDefaultContent" : ""} ,
			         {"targets": [6],"data": null,"sDefaultContent" : "",
			        	 "mRender": function ( data, type, full ) {
			        		var date= data.OPEN_DATE;
			        		if(date==null||date==""){
		        		 		date="";
		        		 	}else{
		        		 		date= DateFormat.tolongdata(data.OPEN_DATE);
		        		 	}
		                	return date;
			                 }
			         } ,
			         {"targets": [7],"data": null,"sDefaultContent" : "",
			        	 "mRender": function ( data, type, full ) {
			        		var date= data.REMOVE_DATE;
			        		if(date==null||date==""){
		        		 		date="";
		        		 	}else{
		        		 		date= DateFormat.tolongdata(data.REMOVE_DATE);
		        		 	}
		                	return date;
			                 }
			         } ,
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
	
	$(document).ready(function(){
		$('#example').dataTable(dataTableObj);
	});
	
	function searchList(){
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "payName",
			            "value": $("#payName").val()},
			            {"name" : "removeTag",
		            	 "value": $("#removeTag").val()},
		            	{"name" : "startDate",
			           	"value": $("#startDate").val()},
	 	            	{"name" :  "endDate",
		           		 "value": $("#endDate").val()}
	 	            	 );}
	    });
	    $('#example').dataTable().fnDraw();
	}
	
	function sreah(){
		
		//校验生效时间
		var startDate = $("#startDate").val();
    	var endDate = $("#endDate").val();
    	if(startDate!=""&&endDate!=""){
    		if((new Date(startDate))>(new Date(endDate))){
    			alert("<fmt:message bundle='${pageScope.bundle}'  key='The.final.time.can.t.beyond.the.beginning.time' />！");
    		}else{
    			searchList();
        	}
    	}else{
    		searchList();
    	}
	}
	</script>
</head>



<body>
	<%-- <c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if> --%>
	<div class="pd-20">
	<form action="" method="post" class="form form-horizontal" id="form-article-add">
		<div class="row cl">  
			<span class="l">&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='search.conditions' />:</span>
			<div class="cl pd-5 bg-1 bk-gray"></div>
			<div class="cl pd-5 bg-2 bk-gray">
				<label class="form-label col-2"><span class="c-red"></span><fmt:message bundle='${pageScope.bundle}'  key='account.names' />：</label>
				<div class="formControls col-3">
					<input type="text" class="input-text" value="" placeholder="" id="payName" name="payName" style="width:300px"  placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.group.company.name' />">
				</div>
				<label class="form-label col-2"><span class="c-red"></span><fmt:message bundle='${pageScope.bundle}'  key='Cancellation.mark' />：</label>
				<div class="formControls col-3"> 
					<span class="select-box" style="width:300px" >
						<select id="removeTag" name="removeTag" class="select" >
							<wm:option typeId="CUI_000080" value="" isFull="true" ></wm:option>
						</select>
					</span>
				</div>
			</div>
			<div class="cl pd-5 bg-2 bk-gray">
				<label class="form-label col-2"><span class="c-red"></span><fmt:message bundle='${pageScope.bundle}'  key='Start.time' />：</label>
				<div class="formControls col-3">
					<input type="text" class="input-text Wdate" value="" placeholder="" id="startDate" name="startDate" style="width:300px"  placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.group.company.name' />" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd' })">
				</div>
				<label class="form-label col-2"><span class="c-red"></span><fmt:message bundle='${pageScope.bundle}'  key='end.time' />：</label>
				<div class="formControls col-2">
					<input type="text" class="input-text Wdate" id="endDate" name="endDate" placeholder=""  style="width:300px" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Registration.Mark.of.Business.License' />  " onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd' })">
				</div>
			</div>
			<div class="cl pd-5 bg-2 bk-gray" style=" text-align:center">
				<td align="center" width="80px"><input type="button" value="<fmt:message bundle='${pageScope.bundle}'  key='Query' />" onclick="sreah();" style="cursor:pointer; width:64px; height:25px; border:none;"></td>
			</div>
		</div>
	</form>
	
	<div class="mt-20" id="flushDIV" >
	<table id="example" class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr>
				<th><fmt:message bundle='${pageScope.bundle}'  key='account.names' /></th>
		 		<th><fmt:message bundle='${pageScope.bundle}'  key='Account.score' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='credit.rating' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='credit.line' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='contract.number' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Cancellation.mark' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Activation.Date' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='cancelling.date' /></th>  
			</tr>
		</thead>
	</table>
	</div>
</div>

</body>
</html>
