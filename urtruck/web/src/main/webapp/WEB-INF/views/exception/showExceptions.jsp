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
	<title><fmt:message bundle='${pageScope.bundle}'  key='exception.handling' /></title>
	<base href="<%=basePath%>" />
	<link href="static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
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
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>exceptionService/queryExceptions",    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */	
 					 "columnDefs": [ 
 							         {"targets": [0],"data": null,
 							        	 "mRender": function ( data, type, full ) {
 							                	return "<input type='checkbox' value='"+data.excpId+"' class='box' name='box' />" ;
 							                 }
 							         } ,
							         {"targets": [1],"data": "excpId"} ,
 							         {"targets": [2],"data": "tradeId"} ,
 							         {"targets": [3],"data": "batchId"} ,
 							         {"targets": [4],"data": "batchdetailId"} ,
 							         {"targets": [5],"data": "msisdn"} ,	
 							         {"targets": [6],"data": "iccid"} ,
 							        {"targets": [7],"data": "excpTypeCode"} ,
 							       {"targets": [8],"data": "dealTag"} ,
 							      {"targets": [9],"data": "resultInfo"} ,
 							   	 {"targets": [10],"data": "recvTime"} ,
 							  	 {"targets": [11],"data": "updateTime"} ,
 							     {"targets": [12],"data": "doneTimes"} ,
			         
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
	//模糊查询
	function sreach(){
		<%-- var option = {
			    url : "<%=basePath %>exceptionService/queryExceptions",
			    type: "post",
			    success : function(data){
			    	if(data == ''){alert('导入失败');}
		    	 //重新构建table  
                    $('#example').dataTable().fnClearTable();   //将数据清除 
		    	    $('#example').dataTable(dataTableObj); 
			   },
			   error:function(){
					alert('导入失败');
				}
		 };
 		$("#cform").ajaxSubmit(option); --%>
 		$('#example').dataTable().fnClearTable(false);   //将数据清除  
		var oSettings =  $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{"name" :  "excpId",
				            "value": $("#excpId").val()},
					{"name" :  "dealTag",
				            "value": $("#dealTag").val()},
						{"name" :  "resultInfo",
				            "value": $("#resultInfo").val()},
						{"name" :  "iccid",
				            "value": $("#iccid").val()},
						{"name" :  "msisdn",
				            "value": $("#msisdn").val()},
						{"name" :  "recvTime",
				            "value": $("#recvTime").val()},
						{"name" :  "updateTime",
				            "value": $("#updateTime").val()},
			           		{"name" :  "tradeId",
						     "value": $("#tradeId").val()},
			           		{"name" :  "excpTypeCode",
						     "value": $("#excpTypeCode").val()},
						    {"name" :  "batchId",
						     "value": $("#batchId").val()},
						    {"name" :  "batchdetailId",
				     	     "value": $("#batchdetailId").val()}
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
	<div class="pd-20">
	<div class="mt-20">
	<form role="form" action="/exceptionService/queryExceptions" method="post" id="cform">
      	<div class="oh row">
			<div class="cl pd-5 mt-10">
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='exception.serial.number' />：</label> <input type="text" name="excpId" id="excpId" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Order.serial.number' />：</label><input type="text" name="tradeId" id="tradeId" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Business.type' />：</label> 
					<input type="text" name="excpTypeCode" id="excpTypeCode" style="width:200px" class="input-text"/>
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Batch.serial.number' />：</label> <input type="text" name="batchId" id="batchId" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Batch.detailed.number' />：</label> <input type="text" name="batchdetailId" id="batchdetailId" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl">iccid：</label> <input type="text" name="iccid" id="iccid" style="width:200px" class="input-text"/>				
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Service.numbers' />：</label> <input type="text" name="msisdn" id="msisdn" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' />：</label> <input type="text" name="recvTime" id="recvTime" style="width:200px" class="input-text" onclick="WdatePicker()" readonly="readonly"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='update.time' />：</label> <input type="text" name="updateTime" id="updateTime" style="width:200px" class="input-text" onclick="WdatePicker()" readonly="readonly"/>
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Processing.state' />：</label> <input type="text" name="dealTag" id="dealTag" style="width:200px" class="input-text"/>
					<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='processed.results' />：</label> <input type="text" name="resultInfo" id="resultInfo" style="width:200px" class="input-text"/>
				</div>
			</div>
	  </div>
      	<div class="pd-10" style="text-align:center;">
		   	<button id="search" type="button" class="btn btn-primary radius" onclick="sreach();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
	   	</div>
	</form>
	<form action="teacher_deleteAll.action" method="post" id="formBatch">
	<div class="mt-20">			  
		<button type="button" id="batchOrder" style="margin-bottom:10px;" class="btn btn-primary radius"><fmt:message bundle='${pageScope.bundle}'  key='Relaunch' /></button>

		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">					
					<th><input type="checkbox" id="selectAll"/></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='exception.serial.number' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Order.serial.number' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Batch.serial.number' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Batch.list.number' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Service.numbers' /></th>
					<th>iccid</th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Business.type' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Processing.state' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='processed.results' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='update.time' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Acumulative.processing.times' /></th>
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
	if($("input[name='box']:checked").size() != 0){
		$("input[name='box']:checked").each(function(){
			var excpId = $(this).val();
			boxIds.push(excpId.toString());
		});
		
		$.ajax({
		    dataType:"json",
		    url: "exceptionService/solveExceptions",
		    data:{
		    	"boxIds":boxIds,
		    },
		    success:function(msg){
		    	var Data = msg;
				for ( var key in Data) {
					$("#example").empty();
					if (key.indexOf('total') > -1) {
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Process.successfully' />"+Data[key]);
					} else if (key.indexOf('errorMsg') > -1) {
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Process.unsuccessfully' />："+Data[key])
					}
				}
		    }
		});
	}else{
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.exception.information' />！")
	}
});

</script>
</body>
</html>

