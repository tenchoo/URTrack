<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Real.name.authentication.approval' /></title>
<!-- 	<style>
	.pd-20 {
    padding: 0 !important;
}
.pd-10 {
     padding: 0px !important; 
}
	</style> -->
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
		//通过
		function approved(id){
			$.ajax({
				url:"${ctx}/realnameVerify/approved",
				data:{"id":id},
				success:function(result){
					if(result.success){
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='operation.successfully' />");
						sreach();
					}else{
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='operation.failed' />");
					}
				}
			});
		}
		//不通过
		function unapproved(id){
			$.ajax({
				url:"${ctx}/realnameVerify/unapproved",
				data:{"id":id},
				success:function(result){
					if(result.success){
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='operation.successfully' />");
						sreach();
					}else{
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='operation.failed' />");
					}
				}
			});
			
		}
	</script>
	<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "${ctx}/realnameVerify/list",    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */
		"columnDefs": [ 
		 {"targets": [0],"data": "realname",defaultContent:""} ,
         {"targets": [1],"data": "idnum",defaultContent:""} ,
         {"targets": [2],"data": null,defaultContent:"",
        	 "mRender": function(data,type,full){
				var idtype = full.idtype;
					if(idtype=="1"){
						return "<fmt:message bundle='${pageScope.bundle}'  key='ID.card' />";
					}else if(idtype=="2"){
						return "<fmt:message bundle='${pageScope.bundle}'  key='Household.Register' />";
					}else if(idtype=="3") {
						return "<fmt:message bundle='${pageScope.bundle}'  key='Military.id.card' />";
					}else if(idtype=="4") {
						return "<fmt:message bundle='${pageScope.bundle}'  key='the.resident.identity.cards.of.people.s.armed.policemen' />";
					}else if(idtype=="5") {
						return "<fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.HongKong.and Macao.to.Chinese.mainland' />";
					}else if(idtype=="6") {
						return "<fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.Taiwan.to.Chinese.mainland' />";
					}else if(idtype=="7") {
						return "<fmt:message bundle='${pageScope.bundle}'  key='passport' />";
					}
				}	 
         } ,
         {"targets": [3],"data": null, "mRender": function ( data, type, full ) {
       		return '<img style="width:120px;hieght:120px;" src='+data.handpicurl+'>' ;
       		}
 		} ,
         {"targets": [4],"data":null, "mRender": function ( data, type, full ) {
      		return '<img style="width:120px;hieght:120px;" src='+data.frontpicurl+'>' ;
      		}
		} ,
         {"targets": [5],"data": null, "mRender": function ( data, type, full ) {
         		return '<img style="width:120px;hieght:120px;" src='+data.backpicurl+'>' ;
         	}
 		} ,
         {"targets": [6],"data": null ,
        	 "mRender": function ( data, type, full ) {
                	return '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Success" />\" href="javaScript:approved('+data.id+');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a>'
                	+'&nbsp;&nbsp;&nbsp;&nbsp;<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="fail" />\" href="javaScript:unapproved('+data.id+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>' ;
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
		$('#verifyTable').dataTable(dataTableObj);
	});
	
	function sreach(){
		$('#verifyTable').dataTable().fnClearTable(false);
		var oSettings = $('#verifyTable').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "realname",
			            "value": $("#realname").val()}
	 	            /* 	{"name" :  "description",
		           		 "value": $("#description").val()} */
	 	            	 );} 
	    }); 
	    $('#verifyTable').dataTable().fnDraw();
	}
	</script>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20 f">
	<form role="form font12" style="margin-top: 20px;" >
	    <!-- <div class="pd-20" style="float:left;margin:0 20px">
			<label for="name" class="font12 fl">姓名：</label>
	      	<input style = "width:300px" type="text" class="input-text" style="width:250px" value="" placeholder="请输入名称" id="realname" name="realname"">
      	</div> -->
      	<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='name' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.a.valid.name' />" id="realname" name="realname">
					</div>
				</div>
			</div>
	   	<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Search' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
	</form>
	<div class="mt-20">
		<table id="verifyTable" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th width="60"><fmt:message bundle='${pageScope.bundle}'  key='name' /></th>
				<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Document.number' /></th>
				<th width="60"><fmt:message bundle='${pageScope.bundle}'  key='ID.Type' /></th>
				<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='hold.photos' /></th>
				<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='front.photo' /></th>
				<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='backside.photo' /></th>
				<th width="60"><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
			</tr>
		</thead>
	</table>
	</div>
</div>
</body>
</html>
