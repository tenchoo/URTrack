<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='send.warning.message' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" /><link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
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
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>


</head>
<body>
	<div class="pd-20">
			<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='rule.name' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='rule.types' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='creater' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='send.time' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='perform.action' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='warning.contents' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Card.details' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">

$(function() {
	$('#example').dataTable(dataTableObj);
});
var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>alarm/queryLogList",
		"iDisplayLength" : 10, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "RULENAME"
				},
				{
					"targets" : [ 1 ],
					"data" : "RULETYPE"
				},
				{
					"targets" : [ 2 ],
					"data" : "CREATOR"
				},
				{
					"targets" : [ 3 ],
					"data" : "SENDTIME"
				},
				{
					"targets" : [ 4 ],
					"data" : "OPERATE",
					"mRender" : function(data, type, full) {
						if("1"==data){
						  return "<fmt:message bundle='${pageScope.bundle}'  key='email' />";
						}else if("2"==data){
							return "<fmt:message bundle='${pageScope.bundle}'  key='message' />";
						}else if("3"==data){
							return "<fmt:message bundle='${pageScope.bundle}'  key='out of service' />";
						}else{
							return "<fmt:message bundle='${pageScope.bundle}'  key='unknown' />";
						}
					}
				},
				{
					"targets" : [ 5 ],
					"data" : "CONTENT",
				},
				{
					"targets" : [ 6 ],
					"data" : "ALMID",
					"mRender" : function(data, type, full) {
						debugger
						var almStr=data.split("%");
						if("6"==almStr[1]){
						     return '<a href="javascript:;" onclick=\"downloadFile('+almStr+ ');\"><fmt:message bundle='${pageScope.bundle}'  key='download' /></a>';
						}else{
							 return "--";
						}
								
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

	function downloadFile(almId,almType){
	    window.location.href = "<%=basePath%>alarm/download?almId="+almId;
	}
</script>
</body>
</html>
