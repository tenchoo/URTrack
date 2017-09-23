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
<title><fmt:message bundle='${pageScope.bundle}'  key='Alarm.rule.list' /></title>


<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
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
	function del(id){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Sure.To.Delete.This.Rule?' />")){
			$.ajax({
		        type:"POST",
		        url:"${ctx}/alarm/del?id="+id,
				cache : false,
				dataType:"json",
				success : function(data) {
					debugger
					if(data.success){
						layer.msg(data.msg,{icon:1,time:1000});
						sreach();
					}else{
						layer.msg(data.msg,{icon:1,time:1000});
					}
				}
			});
		}
		return;
	}
	function add_rule(title,url,w,h){
		layer_show(title,url,w,h);
	}
	//更新
	function toUpdate(id){
		if(id!=""){
			url='${ctx}/alarm/toUpdate/'+id;
			layer_show("<fmt:message bundle='${pageScope.bundle}'  key='Edit.Alarm.Rule' />",url,'800','550');
		}
		
	}
	function startUsing(id){
		$.ajax({
			 type:"POST",
	        url:"${ctx}/alarm/startUsing?id="+id,
			cache : false,
			dataType:"json",
			success : function(data) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Start.Successfully' />");
				sreach();
			}
		});
	}
	function blockUp(id){
		$.ajax({
			type:"POST",
	        url:"${ctx}/alarm/blockUp?id="+id,
			cache : false,
			dataType:"json",
			success : function(data) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Deactivate.Successfully' />");
				sreach();
			}
		});
	}
		 
		
	function toDetail(id){
		if(id!=""){
			url='${ctx}/alarm/toDetail/'+id;
			layer_show("<fmt:message bundle='${pageScope.bundle}'  key='Check.Alarm.Rule' />",url,'800','550');
		}
		
	}
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>alarm/queryList", /* 跳转url */
		"iDisplayLength" : 10, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "RULE_NAME"
				},
				{
					"targets" : [ 1 ],
					"data" : "RULE_TYPE_NAME"
				},
				{
					"targets" : [ 2 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var date = DateFormat.tolongdata(data.RECV_TIME);
						return date;
					}
				},
				{
					"targets" : [ 3 ],

					"data" : "RECV_NAME"
				},
				{
					"targets" : [ 4 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var date = DateFormat.tolongdata(data.UPD_TIME);
						return date;
					}
				},
				{
					"targets" : [ 5 ],
					"data" : "U_NAME"
				},
				{
					"targets" : [ 6 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						debugger;
						var str = "";
						if (data.VALID_TAG == 1) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Already.Started' />"
						} else {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Deactivate' />"
						}
						return str;
					}
				},
				{
					"targets" : [ 7 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var status='';
						if(data.VALID_TAG==1){
							status='<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Deactivate' />\" href="javaScript:blockUp('
							+ data.RULE_ID
							+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle='${pageScope.bundle}'  key='Deactivate' /></i></a>';
						}else if(data.VALID_TAG==0){
							status='<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='start' />\" href="javaScript:startUsing('
								+ data.RULE_ID
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle='${pageScope.bundle}'  key='start' /></i></a>';
						}else{
							layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Unknown.State' />");
						}
						return '<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Delete' />\" href="javaScript:del('
								+ data.RULE_ID
								+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle='${pageScope.bundle}'  key='Delete' /></i></a>'
								+ '<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Edit' />\" href="javaScript:toUpdate('
								+ data.RULE_ID
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle='${pageScope.bundle}'  key='Edit' /></a> '
								+ '<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Check' />\" href="javaScript:toDetail('
								+ data.RULE_ID
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle='${pageScope.bundle}'  key='Check' /></a>'+status;
								
					}
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

	$(document).ready(function() {
		$('#example').dataTable(dataTableObj);
	});

	function sreach() {
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "ruleName",
					"value" : $("#ruleName").val()
				}

				);
			}
		});
		$('#example').dataTable().fnDraw();
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
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Rule.Name' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.Add.Rule.Name' />" id="ruleName" name="ruleName">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Research' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Clear.Out' />&nbsp;&nbsp;">
			</div>
		</form>

		<div class="fr mtb20">
			<a href="javascript:;"
				onclick="add_rule('<fmt:message bundle='${pageScope.bundle}'  key='Add.Alarm.Rule' />','${ctx}/alarm/createRule','800','550')"
				class="btn btn-primary radius"> <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='Add.Alarm.Rule' />
			</a>
		</div>
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Name' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Type' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Creating.Time' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Creating.Person' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Updating.Time' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Updating.Person' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Condition' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>
