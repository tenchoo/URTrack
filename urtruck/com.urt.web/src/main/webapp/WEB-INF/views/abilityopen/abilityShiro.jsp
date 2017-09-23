<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Empowerment.Ability' /></title>
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
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
		function server_add(title,url,w,h){
			layer_show(title,url,w,h);
		}
		function toUpdateSsUser(accountId){
			if(accountId!=""){
				var url = '${ctx}/ssUser/update/'+accountId;
				
			}
			
		}
		//更新
		function toUpdate(id){
			
			if(id!=""){
				var url ='${ctx}/abilityOpen/toUpdate/'+id;
				layer_show('<fmt:message bundle="${pageScope.bundle}"  key="editing.interface" />',url,'800','550');
			}
			
		}
	</script>
<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>abilityOpen/custList", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "CUST_NAME"
				},
				{
					"targets" : [ 1 ],
					"data" : "TOTALCOUNT"
				},
				{
					"targets" : [ 2 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						debugger;
						return '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Edit" />\" href="javaScript:toUpdate('
								+ data.CUST_ID
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a> ';
					}
				},

		],

		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : true,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : true,
		"bAutoWidth" : true,
		"aaSorting" : [ [ 1, "asc" ] ],
		"bStateSave" : false,
		"sPaginationType" : "full_numbers",
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
		"aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
				[ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
	//设置每页显示记录的下拉菜单
	}
    
	function sreach() {
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "custName",
					"value" : $("#custName").val()
				}

				);
			}
		});
		$('#example').dataTable().fnDraw();
	}
	$(document).ready(function() {
		$('#example').dataTable(dataTableObj);
	});
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
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.customer.name' />" id="custName" name="custName">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='search.customers' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
		</form>
		<div class="fr mtb20">
			<a href="javascript:;" onclick="server_add('<fmt:message bundle='${pageScope.bundle}'  key='Add.customer.ability' />','${ctx}/abilityOpen/toAdd','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					<fmt:message bundle='${pageScope.bundle}'  key='Add.customer.ability' /></a>
		</div>
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">

				<thead>
					<tr class="zpTable">
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='interface.numbers' /></th>
						<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>

