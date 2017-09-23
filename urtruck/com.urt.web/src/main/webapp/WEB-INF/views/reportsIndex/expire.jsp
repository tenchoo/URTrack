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
<title><fmt:message bundle='${pageScope.bundle}'  key='List.of.project.groups' /></title>

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
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
			var dataTableObj = {
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"sAjaxSource" : "<%=basePath%>expire/expireList", /* 跳转url */
				"iDisplayLength" : 10, /* 展示条数 */
				"columnDefs" : [
						{
							"targets" : [ 0 ],
							"data" : "OPERATORS_NAME"
						},
						{
							"targets" : [ 1 ],
							"data" : "GOODS_NAME"
						},
						{
							"targets" : [ 2 ],
							"data" : "EXPIRE_NUM"
						}, 
						{
							"targets" : [ 3 ],
							"data" : "END_DATE"
						}
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
		        $('#example').dataTable().fnDraw();
		    }
			$(document).ready(function() {
				$('#example').dataTable(dataTableObj);
			});
</script>
</head>
<body>
				<div class="pd-20">
					<div class="mt-20">
						<table id="example"
							class="table table-border table-bordered table-hover table-bg table-sort "
							cellpadding="0">
							<thead>
								<tr class="zpTable">
									<th><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
									<th><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
									<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.at.Expiration.date' /></th>
								 	<th><fmt:message bundle='${pageScope.bundle}'  key='Expiration.date' /></th>	
								</tr>
							</thead>
						</table>
					</div>
				</div>
</body>
</html>
