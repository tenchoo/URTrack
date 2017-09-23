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
	function del(id){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.delete.this.rule?' />")){
			$.ajax({
		        type:"POST",
		        url:"${ctx}/tactical/del?id="+id,
				cache : false,
				dataType:"json",
				success : function(data) {
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
			url='${ctx}/tactical/toUpdate/'+id;
			layer_show("<fmt:message bundle='${pageScope.bundle}'  key='Rule.editing' />",url,'800','550');
		}
		
	}
	function startUsing(id){
		$.ajax({
			 type:"POST",
	        url:"${ctx}/tactical/startUsing?id="+id,
			cache : false,
			dataType:"json",
			success : function(data) {
				if(data.success){
					layer.msg(data.msg,{icon:1,time:1000});
					sreach();
				}else{
					layer.msg(data.msg,{icon:1,time:1000});
				}
			}
		});
	}
	function blockUp(id){
		$.ajax({
			type:"POST",
	        url:"${ctx}/tactical/blockUp?id="+id,
			cache : false,
			dataType:"json",
			success : function(data) {
				if(data.success){
					layer.msg(data.msg,{icon:1,time:1000});
					sreach();
				}else{
					layer.msg(data.msg,{icon:1,time:1000});
				}
			}
		});
	}
		 
		
	function toDetail(id){
		 layer.open({
			  type: 2,
			  area: ['800px', '550px'],
			  title:"<fmt:message bundle='${pageScope.bundle}'  key='Policy.details' />",	
			  fixed: false, //不固定
			  maxmin: true,
			  btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Close' />"],
			  content: '/tactical/schemeDetail/'+id,
			   yes:function(index,layero){
			     layer.close(index);
			  }
			});
	}
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>tactical/queryList", /* 跳转url */
		"iDisplayLength" : 10, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "schemeName"
				},
				{
					"targets" : [ 1 ],
					"data" : "schemeComment"
				},
				{
					"targets" : [ 2 ],
					"data" : "targittype",
					"mRender" : function(data, type, full) {
						var str = "";
						if (data == "1") {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Common.policy' />"
						} else {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='privacy.policy' />"
						}
						return str;
					}
				},
				{
					"targets" : [ 3 ],
					"data" : "delflag",
					"mRender" : function(data, type, full) {
						var str = "";
						if (data == "1") {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='enabled' />"
						} else {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='disabled' />"
						}
						return str;
					}
				},
				{
					"targets" : [ 4 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var status='';
						if(data.delflag=="1"){
							status='<a title=\"停用\" href="javaScript:blockUp('
							+ data.id
							+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"disabled\" /></i></a>';
						}else if(data.delflag=="2"){
							status='<a title=\"启用\" href="javaScript:startUsing('
								+ data.id
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"enabled\" /></i></a>';
						}else{
							layer.msg("<fmt:message bundle=\"${pageScope.bundle}\"  key=\"unknown.state\" />");
						}
						return '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"cancel\" />\" href="javaScript:del('
								+ data.id
								+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"cancel\" /></i></a>'
								+ '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Edit\" />\" href="javaScript:toUpdate('
								+ data.id
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"Edit\" /></a> '
								+ '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"View\" />\" href="javaScript:toDetail('
								+ data.id
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"View\" /></a>'+status;
								
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
       <div class="fr mtb20">
			<a href="javascript:;"
				onclick="add_rule('<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Add.a.policy\" />','${ctx}/tactical/addScheme','800','550')"
				class="btn btn-primary radius">
				<fmt:message bundle='${pageScope.bundle}'  key='Add.a.policy' />
			</a>
		</div>

		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='Policy.name' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Policy.description' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Policy.type' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='status' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
		</div>
	</div>

</body>
</html>
