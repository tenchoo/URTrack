<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title>企业客户产品展示页</title>	
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
	<script type="text/javascript">
	
	//删除信息
	function del(goodsReleaseId){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.that.you.want.to.delete.it' />")){
				if(goodsReleaseId!=""){							
					$.ajax({
				        type:"POST",
				        url:"${ctx}/custgoods/delCustGoodsRelease",
				        data:{'goodsReleaseId':goodsReleaseId},
						dataType : "json",
						success : function(data) {
							layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Delete.successfully' />");
							location.reload();
						},
						error : function(error) {
						}
					});
				}
			  }
			  else{
			   return;
			  }

	}	
	//产品发布
	  function GoodRelease(goodsReleaseId){
	if(goodsReleaseId!=""){
		var url = '${ctx}/custgoods/GoodsRelease?goodsReleaseId='+goodsReleaseId;
		layer_show('产品发布',url,'800','550');
	}
} 

	//企业产品发布展示页
	  function showGoodRelease(goodsReleaseId){
	if(goodsReleaseId!=""){
		var url = '${ctx}/custgoods/toshowGoodsRelease?goodsReleaseId='+goodsReleaseId;
		layer_show('产品发布展示',url,'800','550');
	}
} 
		
	
	
	
	
	</script>
	<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>custgoods/list",    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */	
 					 "columnDefs": [ 
			         {"targets": [0],"data": "goodsName"} ,
			         {"targets": [1],"data": "goodsReleaseId"} ,
			         {"targets": [2],"data": "goodsId"} ,
			         {"targets": [3],"data": "custName"} ,
			         {"targets": [4],"data": "sse"} ,
			         {"targets": [5],"data": "ssr"} ,
			         {"targets": [6],"data": "releasePrice" } ,
			         {"targets": [7],"data": "releaseCycle" } ,
			         {"targets": [8],"data": "pname" } ,
			         {"targets": [9],"data": "startDateValue" } ,
			         {"targets": [10],"data": "endDateValue" } ,         
			         {"targets": [11],"data": null,
			        	 "mRender": function ( data, type, full ) {
			                	return'<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('
			                	+data.goodsReleaseId+');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></a> '
			                	+'<a title=\"发布\" href="javaScript:GoodRelease('
								+ data.goodsReleaseId
								+ ');" class=\"ml-5\" style=\"text-decoration:none\">发布</i></a> '
			                	
    
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
	
	function sreach(){
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "custName",
			            "value": $("#custName").val()}
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
	<form role="form" >
		<div class="oh row">
	    <div class="col-md-4 col-lg-3 mt20">
			<label for="custName" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
	      	<div class="tBox">
	      	<input type="text" class="input-text" style="width:250px" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.customer.name' />" id="custName" name="custName">
      		</div>
      	</div>
      	</div>
      	<div class="row">
      		<div class="pd-10" style="text-align:center;">
		   	<button id="search" type="button" class="btn btn-primary radius" onclick="sreach();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Find.customers' /></button>
	   		<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
	   	</div>
      	</div>
	</form>
	<%-- 	<div class="fr pd-10" style="float: left;"><span class="l"><a                href="<%=request.getContextPath()%>/goodsRelease/toAddGoodRelease" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>产品发布</a></span> </div>	
<div class="fr pd-10" style="float: left;"><span class="l"><a title="产品发布列表展示" href="javaScript:toAddGoodsRelease();" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>产品发布</a></span></div>		
 --%>			<div class="fr pd-10">
			<a href="javascript:;" onclick="toAddGoodsRelease('产品发布列表展示 ','${ctx}/custgoods/showGoodsRelease','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					产品发布列表展示</a>
		</div>
	   <div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.name' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.code' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Parent.commodity.code'/></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Terminal.type' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Device.subdivision' /></th> 
				<th><fmt:message bundle='${pageScope.bundle}'  key='Sale.Price' /></th> 
				<th><fmt:message bundle='${pageScope.bundle}'  key='Charges.cycle' /></th> 
				<th><fmt:message bundle='${pageScope.bundle}'  key='data.pool' /></th> 
				<th><fmt:message bundle='${pageScope.bundle}'  key='Effective.date' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='expiration.date' /></th>
				<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
				</tr>
		</thead>
	</table>
	</div>
</div>
</body>
</html>
 