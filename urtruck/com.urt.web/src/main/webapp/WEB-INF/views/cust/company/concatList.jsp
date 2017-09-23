<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>

<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Contact.manager' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
		function add_concat(title,url,w,h){
			layer_show(title,url,w,h);
		}
		//更新
		function toUpdate(id){
			debugger;
			if(id!=""){
				url='${ctx}/cust/toUpdateConcat/'+id+"?custId="+$("#custId").val();
				layer_show('<fmt:message bundle="${pageScope.bundle}"  key="edit.contacts" />',url,'800','550');
			}
			
		}
		function delConcat(id){
			if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.delete.this.rule' />")){
				window.location.href='${ctx}/cust/delConcat/'+id+"?custId="+$("#custId").val();
			}
			return;
		}
	</script>

</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<form role="form">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Contact.person' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.customer.name' />" id="contactName" name="contactName">
						<input type="hidden" id="custId" value="${requestScope.custId}" />
					</div>
				</div>
			</div>
			<script type="text/javascript">
				$(document).ready(function(){
				    $('#tb_concat').dataTable(concatTableObj);
				});
			</script>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Search' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
		</form>
		<div class="fr mtb20">
			<a href="javascript:;" onclick="add_concat('<fmt:message bundle='${pageScope.bundle}'  key='Add.a.Contact' />','${ctx}/cust/createConcat/${requestScope.custId}','800','550')" class="btn btn-primary radius">
			<span class="human"></span>
					<fmt:message bundle='${pageScope.bundle}'  key='Add.a.Contact' /></a>
		</div>
		<div class="mt-20">
			<table id="tb_concat" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<!-- <th><input type="checkbox"></th> -->
						<th><fmt:message bundle='${pageScope.bundle}'  key='name' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='telephone.number' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='fax' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='email' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='postcode' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='mailing.address' /></th>
						<!-- <th>家庭地址</th>
						<th>证件类型</th>
						<th>证件号码</th>
						<th>单位名称</th>
						<th>单位地址</th> -->
						<th><fmt:message bundle='${pageScope.bundle}'  key='department' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='position' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Best.contact.time' /></th>
						<!-- <th>备注</th> -->
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	var custId="";
	debugger;
	custId=$("#custId").val();
	var concatTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath %>cust/contactList?custId="+custId,    /* 跳转url */
			"iDisplayLength": 5,  /* 展示条数 */
			           "columnDefs": [
			                       /*  {
			                           "sClass": "text-center",
			                           "data": "contactId",
			                           "render": function (data, type, full, meta) {
			                               return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
			                           },
			                           "bSortable": false
			                       },  */
			                       {"targets": [0], "data": "contactName" },
			                       {"targets": [1], "data": "contactPhone" },
			                       {"targets": [2], "data": "contactFax" },
			                       {"targets": [3], "data": "contactEmail" },
			                       {"targets": [4], "data": "contactPostCode" },                 
			                       {"targets": [5], "data": "contactPostAddr" },                 
			                      /*  {"targets": [6], "data": "contactHomeAddr" },                 
			                       {"targets": [7], "data": "contactPsptTypeCode" },                 
			                       {"targets": [8], "data": "contactPsptId" },                 
			                       {"targets": [9], "data": "contactWorkName" },                 
			                       {"targets": [10], "data": "contactWorkAddr" },     */             
			                       {"targets": [6], "data": "contactDepart" },                 
			                       {"targets": [7], "data": "contactDuty" },                 
			                       {"targets": [8], "data": "bestContactTime" },                 
			                     /*   {"targets": [14], "data": "remark" }, */
			                       {"targets": [9],"data": null,
			  			        	 "mRender": function ( data, type, full ) {
			  			        		 //<a title=\"删除\" href="javascript:;" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>
			  			                	return  '<a title=\"编辑\" href="javaScript:toUpdate('+data.contactId+');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a> '
			  			                	 +'<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javascript:delConcat('+data.contactId+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i>'
			  			                 }
			  			         },
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
	
		
		function sreach(){
			debugger;
			$('#tb_concat').dataTable().fnClearTable(false);
			var oSettings = $('#tb_concat').dataTable().fnSettings();
		    oSettings.aoServerParams.push({
		        "fn": function (aoData) {
		            aoData.push(
		            		{
				            "name" :  "contactName",
				            "value": $("#contactName").val()
				            }/* , {
					            "name" :  "custId",
					            "value": $("#custId").val()
					            } */
				           
		            		);}
		    });
		    $('#tb_concat').dataTable().fnDraw();
		}
	
	</script>
</body>
</html>
