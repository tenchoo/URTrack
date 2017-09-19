<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Activation.card.information' /></title>
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
	<script type="text/javascript" src="static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="static/js/jquery.form.js"></script> 
	<script type="text/javascript" src="static/select2-4.0.3/dist/js/select2.full.js"></script>
	<script type="text/javascript" src="static/js/jquery.validate.js"></script>
	<script type="text/javascript" src="static/js/messages_zh.js"></script>
	<script type="text/javascript" src="static/js/additional-methods.js"></script>
	<script type="text/javascript" src="static/js/jquery-methods.js"></script>
	<script type="text/javascript" src="static/js/jquery.metadata.js"></script> 
	<script type="text/javascript">
	$.ajax({
		//url:"${ctx}/cust/getAgentList",
		url:"traffic/getChannelCust",
		data:{},
		success:function(result){
			var select=$("#custId").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Customer" />',
				tags: "true",
				allowClear: true,
				data:result
			});
			$("#custId").change(function() {
				$('#type').empty();
				$('#version').empty();
				$.ajax({
					type : "post",
					url : "${ctx}/ss/getAttrs",
					data: {"custId":$("#custId").val()},
					success:function(result){
						debugger;
						console.log(result);
						/* $("#lable1").text(result.attr1+":");
						$("#lable2").text(result.attr2+":"); */
					}
				});
				$.ajax({
					url:"${ctx}/ss/getTypeList",
					data:{"custId":$("#custId").val()},
					success:function(result){
						debugger;
						var select=$("#type").select2({
							width : 200,  
							placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Type" />',
							tags: "true",
							allowClear: true,
							data:result
						});
						$("#type").change(function() {
							debugger;
							$('#version').empty();
							var pid="";
							$.ajax({
								url:"${ctx}/ss/getPidByValue",
								data:{"id":$("#type").val(),"custId":$("#custId").val()},
								async:false,
								success:function(result){
									pid=result;
								}
							});
							$.ajax({
								url:"${ctx}/ss/getVersionList",
								data:{"pid":pid,"custId":$("#custId").val()},
								success:function(result){
									var select=$("#version").select2({
										width : 200,  
										placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
										tags: "true",
										allowClear: true,
										data:result
									});
								}
							});
						});
					}
				});
			});
		}
	});
	$(document).ready(function(){
		//$('#example').dataTable(dataTableObj);
	});
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>userService/queryIccid",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
 					 "columnDefs": [ 
			         {"targets": [0],"data": "iccid"} ,
			         {"targets": [1],"data": "custName"} ,
			         {"targets": [2],"data": "privatekey"} ,
			         {"targets": [3],"data": "goodName"} ,
			         {"targets": [4],"data": "serviceName"} ,
			         {"targets": [5],"data": "operatorsName"} ,
			         /* {"targets": [6],"data": "ctype"} , */
			         {"targets": [6],"data": "att1"} ,
			         {"targets": [7],"data": "val1"},
			         {"targets": [8],"data": "att2"} ,
			         {"targets": [9],"data": "val2"} ,
			         {/* "targets": [13],"data": "ifMaintenance" */
			        	 "targets": [10],
		                  "data": null,
		                  "mRender": function (data, type, full) {
		                	  if(full.ifMaintenance== "1"){
		                          return "<fmt:message bundle='${pageScope.bundle}'  key='maintenance' />"
		                       }else if(full.ifMaintenance == "0"){
		                          return "<fmt:message bundle='${pageScope.bundle}'  key='normal' />"
		                       }
		                  }
			         },
			         {"targets": [11],"data": "msisdn"} ,
			         {
		                  "targets": [12],
		                  "data": null,
		                  "mRender": function (data, type, full) {
		                	  return full.inTime ;//'<fmt:formatDate value='${full.inDate}' pattern="yyyy-MM-dd HH:mm:ss" />';
		                  }
		              },
		              
		              /* {
		                  "targets": [13],
		                  "data": null,
		                  "mRender": function (data, type, full) {
		                	  return full.inTime ;//'<fmt:formatDate value='${full.inDate}' pattern="yyyy-MM-dd HH:mm:ss" />';
		                  }
		              }, */
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
	  "fnServerParams": function ( aoData ) {
	      aoData.push( {"name" :  "custId",
	            "value": $("#custId").val()},
           		{"name" :  "type",
			     "value": $("#type").val()},
           		{"name" :  "version",
			     "value": $("#version").val()},
			    {"name" :  "startIccid",
			     "value": $("#startIccid").val()},
			    {"name" :  "endIccid",
	     	     "value": $("#endIccid").val()} );
	    },
	    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
	}
	
	function sreach(){
		if(myValidate()){
			/* $('#example').dataTable().fnClearTable(false);   //将数据清除  
			var oSettings =  $('#example').dataTable().fnSettings();
		    oSettings.aoServerParams.push({
		        "fn": function (aoData) {
		            aoData.push(
		            		{"name" :  "custId",
				            "value": $("#custId").val()},
			           		{"name" :  "type",
						     "value": $("#type").val()},
			           		{"name" :  "version",
						     "value": $("#version").val()},
						    {"name" :  "startIccid",
						     "value": $("#startIccid").val()},
						    {"name" :  "endIccid",
				     	     "value": $("#endIccid").val()}
		 	            	 );}
		    });
		    $('#example').dataTable().fnDraw(); */
		    
		    $('#example').dataTable().fnDestroy();
			$('#example').dataTable(dataTableObj);
		}
	}
	
	
	
	//验证信息
	function myValidate(){
		var custId = $("#custId").find("option:selected").val();
		var type = $("#type").find("option:selected").val();
		var version = $("#version").find("option:selected").val();
		var startIccid = $("#startIccid").val();
		var endIccid = $("#endIccid").val();
		if((custId =='' || custId == -1) && startIccid == '' && endIccid==''){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.a.parameter' />");
			 return false;
		}
		return true;
	}
	function active() {
		var option = {
			    url : "<%=basePath%>userService/userArchiving",
			    type: "post",
			    success : function(data){
			    	if(data.indexOf("ok") >-1){
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='activation.success' />")
			    	}else if(data.indexOf("activefailed") >-1){
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
			    	}
			    	else if(data.indexOf("orderfailed") >-1){
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='fail.to.order' />");
			    	}
			    	else if(data.indexOf("maintenance") >-1){
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='there.exits.cards' />");
			    	}else if(data.indexOf("operatorServiceFailed") >-1){
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='no.operator.operation' />");
			    	}else{
			    		alert(data);
			    	}
			   },
			   error:function(){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
			}
		 };
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.activate.all' />？")){
				$("#submitForm").ajaxSubmit(option);
		}
	}
</script> 
</head>
<body>
<div class="pd-20 font12">
		<form role="form" id="submitForm">
		<div class="oh">
			<div class="col-12">
				<lable class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Customer' />: </lable><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
				<lable id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' />:</lable><select id="type" name="type" class="input-text" style="width:200px;"></select>
				<lable id="lable2"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />:</lable><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>
				<input type="text" id="attribute1" name="attribute1" style="display:none">
				<input type="text" id="attribute2" name="attribute2" style="display:none">
			</div>
		</div>
		<div class="oh">
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='start' />:</label>
				<div class="tBox">
					<input type="text" name="startIccid" id="startIccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='origin' />Iccid"  style="width:200px" class="input-text">
				</div>	
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='end' />:</label>
				<div class="tBox">
					<input type="text" name="endIccid" id="endIccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='deadline' />Iccid"  style="width:200px" class="input-text">
				</div>	
			</div>
   		</div>
		<div class="text-center mt20">
			<button id="search" type="button" class="btn btn-primary radius" onclick="sreach();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
		</div>	
		</form>
		<div class="fr mtb20">
			<span class="l"><button id="search" type="button" class="btn btn-primary radius" onclick="active();"><i class="Hui-iconfont">&#xe600;</i><fmt:message bundle='${pageScope.bundle}'  key='Activate.card' /></button></span>
		</div>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th >ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Customer' />ID</th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Device.type' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Password' /></th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Card.subdivision.type' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Initial.product' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
				<!-- <th ><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></th> -->
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.1' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.1' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.2' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.2' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' /></th>
				<th >MSISDN</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
			</tr>
		</thead>
		<%-- <tbody>
			<c:forEach var="s" items="${list }">
				<tr class="text-c">
					<td><input type="checkbox" value="" name=""></td>
					<td>${s.iccid}</td>
					<td class="text-l">${s.custid}</td>
					<td>${s.devicetype}</td>
					<td>${s.privatekey}</td>
					<td>${s.cardtype}</td>
					<td>${s.initproduct}</td>
					<td>${s.cardstatus}</td>
					<td>${s.operators}</td>
					<td>${s.ctype}</td>
					<td>${s.attribute1}</td>
					<td>${s.value1}</td>
					<td>${s.attribute2}</td>
					<td>${s.value2}</td>
					<td>${s.ifMaintenance}</td>
				</tr>
			</c:forEach>
		</tbody> --%>
	</table>
	</div>
</div>
</body>
</html>