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
	<title>卡划拨</title>
	<base href="<%=basePath%>" />
	<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
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
	<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	
</head>
<body>
	<div class="pd-20 font12">
	<div class="mt-20">
						<input type="text" style="width:200px" class="input-text" placeholder="ICCID" id="paramNum" name="paramNum">
						<input class="btn btn-primary radius" type="button" style="float: right" onclick="sreach();"
							value="&nbsp;&nbsp;高级搜索&nbsp;&nbsp;">	
		<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th>全选
						  <input type="checkbox" name="keeperUserGroup-checkable" class="group-checkable" data-set="#sample_1 .checkboxes" />  
						</th>
						<th>操作</th>
						<th>iccid</th>
						<th>服务号码</th>
						<th>运营商</th>
						<th>终端类型</th>
						<th>客户名称</th>
						<th>型号</th>
						<th>卡状态</th>
					</tr>
				</thead>
			</table>
	<div class="cl pd-5">
		<input type="button" class="btn btn-primary radius" onclick="assign()" value="提交" />
		<button type="button" class="btn btn-primary radius" onclick=""><i class="Hui-iconfont">&#xe665;</i>清除</button>
	</div>
	</div>
</div>
<script type="text/javascript">
var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>month/queryList", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
				{
		                        "targets" : [0 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	 var content = '<label style="margin-left:32px;" class="mt-checkbox mt-checkbox-single mt-checkbox-outline">';  
		                             content += '    <input type="checkbox"  name="test" class="group-checkable"     value="' + data + '" />';  
		                             content += '</label>';  
		                             return content;  
		                        }
		                    },
				{
					"targets" : [ 1 ],
					"data" : ""
				},
				{
					"targets" : [ 2 ],
					"data" : "ICCID"
				},
				{
					"targets" : [ 3 ],
					"data" : "MSISDN"
				},
				{
					"targets" : [ 4 ],
					"data" : "CUSTNAME"
				},
				{
					"targets" : [ 5 ],
					"data" : "DATAADDED"
				},
				{
					"targets" : [ 6 ],
					"data" : "STATICNAMEA"
				},
				{
					"targets" : [ 7 ],
					"data" : "STATICNAMEB"
				},
				{
					"targets" : [ 8 ],
					"data" : "CARDSTATUS"
				},
				
				
				/* {
					"targets" : [ 3 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						debugger;
						return '<a title=\"编辑\" href="javaScript:toUpdate('
								+ data.CUST_ID
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6df;</i></a> ';
					}
				},
 */
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
			"sLengthMenu" : "每页显示 _MENU_ 条记录",
			"sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='we.can.not.find.any.relevant.data' />",
			"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			"sInfoEmtpy" : "显示0到0条记录",
			"sInfoFiltered" : "",
			"sProcessing" : "正在加载中...",
			"sSearch" : "搜索",
			"oPaginate" : {
				"sFirst":    " <fmt:message bundle="${pageScope.bundle}"  key="First.Page" />",
		          "sPrevious": " <fmt:message bundle="${pageScope.bundle}"  key="Previous.Page" />",
		          "sNext":     " <fmt:message bundle="${pageScope.bundle}"  key="Next.Page" /> ",
		          "sLast":     " <fmt:message bundle="${pageScope.bundle}"  key="Last.Page" />"
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
				"name" : "paramNum",
				"value" : $("#paramNum").val()
			}

			);
		}
	});
	$('#example').dataTable().fnDraw();
}


$(document).ready(function(){
	$('#example').dataTable(dataTableObj);
});

function assign(){
	var  iccidStrArry = [];
	$("input[type='checkbox']:checked").each(function(){	  
		iccidStrArry.push($(this).parents('tr').children().eq(2).html());
	});

	if(iccidStrArry == ''){
		alert("请选择要划拨的卡");
		return false;
	}
	$.ajax({
		url:"<%=basePath%>/iccidassignbatch/insertAssignCard",
		type:"post",
		data:{"iccidStrArry":iccidStrArry},
		success:function(result){
			if(result=='1'){
				alert("划拨成功");
			}
				alert("划拨失败");		
		}
	});
}

</script>
</body>
</html>
