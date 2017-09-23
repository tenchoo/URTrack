<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/lib/pager-taglib.jar" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Batch.order' /></title>
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script><!-- 三级联动 -->
	<script type="text/javascript">
	$.ajax({
		url:"${ctx}/traffic/getChannelCust",
		//url:"${ctx}/cust/getAgentList",
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
/* 				$.ajax({
					type : "post",
					url : "${ctx}/ss/getAttrs",
					data: {"custId":$("#custId").val()},
					success:function(result){
						console.log(result);
						 $("#lable1").text(result.attr1+":");
						$("#lable2").text(result.attr2+":");
					}
				}); */
				$.ajax({
					url:"${ctx}/ss/getTypeList",
					data:{"custId":$("#custId").val()},
					success:function(result){						
						var select=$("#type").select2({
							width : 200,  
							placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Type" />',
							tags: "true",
							allowClear: true,
							data:result
						});
						$("#type").change(function() {
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
//查询运营商
$(function(){
	$.post("<%=request.getContextPath()%>/operators/findOperators",function(data){
		var operatorsList=eval(data);
		for ( var i = 0; i < operatorsList.length; i++) {
			$("#operatorsId").append("<option value='"+operatorsList[i].operatorsId+"'><span class='font12'>"+operatorsList[i].operatorsName+"</span></option>");
		}
	},"json");

});
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>/laouser/list",    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */	
 					 "columnDefs": [ 
 							         {"targets": [0],"data": null,
 							        	 "mRender": function ( data, type, full ) {
 							                	return "<input type='checkbox' value='"+data.iccid+"' class='box' name='box' />" ;
 							                 }
 							         } ,
							         {"targets": [1],"data": "userId"} ,
 							         {"targets": [2],"data": "channelCustName"} ,
 							         {"targets": [3],"data": "custName"} ,
 							         {"targets": [4],"data": "operatorsName"} ,
 							         {"targets": [5],"data": "iccid"} ,
 							         {"targets": [6],"data": "serviceName"} ,	
			         
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
		  "fnServerParams": function ( aoData ) {
		      aoData.push( {
		            "name" :  "custId",
		            "value": $("#custId").val()},
            		{"name" :  "operatorsId",
			        "value": $("#operatorsId").val()},
			        {"name" :  "value1",
			        "value": $("#type").val()},
			        {"name" :  "value2",
			        "value": $("#version").val()},
			        {"name" :  "iccid",
			        "value": $("#iccid").val()});
		    },
	    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
	}
  	$(document).ready(function(){
		//$('#example').dataTable(dataTableObj);
	}); 
	//模糊查询
	function sreach(){
		var flag = check();
		if(flag == false){
			return ;
		}
	/* 	$('#example').dataTable(dataTableObj);
		$('#example').dataTable().fnClearTable(false);		
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "custId",
			            "value": $("#custId").val()},
	            		{"name" :  "operatorsId",
				        "value": $("#operatorsId").val()},
				        {"name" :  "value1",
				        "value": $("#type").val()},
				        {"name" :  "value2",
				        "value": $("#version").val()},
				        {"name" :  "iccid",
				        "value": $("#iccid").val()}
	 	            	 );}
	    });
	    $('#example').dataTable().fnDraw(); */
		$('#example').dataTable().fnDestroy();
		$('#example').dataTable(dataTableObj);
	}

	
	function check(){  
	 	  var custIdValue=window.document.getElementById("custId").value;  
		  var operatorsIdValue=window.document.getElementById("operatorsId").value;  
		  var value1Value=window.document.getElementById("type").value; 
		  var value2Value=window.document.getElementById("version").value;  
	  if (custIdValue == "-1") {     	
	      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Customer.cannot.be.empty' />!");
	      return false;  
	  }

	  if (operatorsIdValue == "") {     	
	      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />!");
	      return false;  
	  }
	  if(value1Value == ""|| value1Value == "-1"){
	      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />");
	      return false;
	  }
	  if(value2Value == "" || value2Value == "-1"){
	     layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />");
	     return false;
	  }
	 
	  return true;  
	} 
 </script>
</head>
<body>
	
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
	<div class="mt-20">
	<form role="form" >
      	<div class="oh row">				
			<div class="col-12 font12">
				
				<lable class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Customer' />:</lable><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
				<lable id="lable1" class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Level.1.label' />:</lable><select id="type" name="type" class="input-text" style="width:200px;"></select>
				<lable id="lable2" class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Level.2.label' />:</lable><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>
				<input type="text" id="attribute1" name="attribute1" style="display:none">
				<input type="text" id="attribute2" name="attribute2" style="display:none"><br/>
				<label class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Operator' />：</label><select name="operatorsId" id="operatorsId" style="width:200px" class="input-text"></select>   									
				<label class="kehu">iccid：</label><input type="text" name="iccid" id="iccid" style="width:200px" class="input-text"/>				
				
			</div>
	  </div>
      	
      	
      	
      	
      	
      	<div class="pd-10" style="text-align:center;">
		   	<button id="search" type="button" class="btn btn-primary radius" onclick="sreach();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
	   	</div>
	</form>
	<form action="teacher_deleteAll.action" method="post" id="formBatch">
	<div class="mt-20">			  
		<button type="button" id="batchOrder" style="margin-bottom:10px;" class="btn btn-primary radius"><fmt:message bundle='${pageScope.bundle}'  key='Next.step' /></button>

		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">					
					<th><input type="checkbox" id="selectAll"/></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='User.identification' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Channel.customer' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Use.customer' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Affiliated.operators' /></th>
					<th>iccid</th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='User.service.status' /></th>
			</tr>
		</thead>
	</table>
	</div>
	</form>
	</div>
</div>
<script type="text/javascript">

//点击全选
  $().ready(function(){		
	$("#selectAll").click(function(){
		$(".box").prop("checked",$(this).prop("checked"));	
	});
	

});

/*批量选购*/
 $("#batchOrder").bind("click",function(){
	var boxIds = new Array();
   	var custId = $("#custId").val();
	var operatorsId = $("#operatorsId").val();
	var value1 = $("#attribute1").val();
	var value2 = $("#attribute2").val(); 
	if($("input[name='box']:checked").size() != 0){
		$("input[name='box']:checked").each(function(){
			var iccid = $(this).val();
			boxIds.push(iccid.toString());
		});
		
		$.ajax({
		    type: "GET",
		    async: false,
		    dataType:"json",
		    url: "${ctx}/laouser/batchorder",
		    data:{
		    	"boxIds":boxIds,
 		    	"custId":custId,
		    	"operatorsId":operatorsId,
		    	"value1":value1,
		    	"value2":value2 
		    },
		    success:function(msg){
		    	if(msg.success == true){
		    		window.location.href = ("${ctx}/laouser/order");
		    	}else{
		    		alert("<fmt:message bundle='${pageScope.bundle}'  key='Shopping.failure' /> ！");
		    	}
		    }
		});
	}else{
		alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.card.for.the.purchase' />！")
	}
});

</script>
</body>
</html>

