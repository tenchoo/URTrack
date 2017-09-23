<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>系统用户管理</title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
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
		//更新
		function toUpdateSsUser(accountId){
			if(accountId!=""){
				var url = '${ctx}/ssUser/update/'+accountId;
				layer_show('编辑企业联系人',url,'800','550');
			}
			
		}

		//重置密码
		function resetPassword(accountId){
			layer.confirm('确认要重置该用户密码吗？',function(index){
				$.ajax({
			        type:"POST",
			        url:"${ctx}/ssUser/resetPassword?accountId="+accountId,
					cache : false,
					dataType:"json",
					success : function(data) {
						if(data.flag){
							layer.msg(data.msg,{icon:1,time:1000});
						}else{
							layer.msg(data.msg,{icon:1,time:1000});
						}
					}
				});
			});
		}
		
		/*管理员-增加*/
		function admin_add(title,url,w,h){
			layer_show(title,url,w,h);
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
		<input type="hidden" value="${custId}" id="custId" name="custId">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl">登录名：</label> 
					<div class="tBox">
						<input type="text" class="input-text" value="" placeholder="请输入登录名" id="loginName" name="loginName"> 
					</div>
				</div>
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl">昵称：</label> 
					<div class="tBox">
						<input type="text" class="input-text" value=""	placeholder="请输入昵称" id="nickname" name="nickname">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i> 搜系统用户
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;清空&nbsp;&nbsp;">
			</div>
		</form>
		<div class="fr mtb20">
			<a href="javascript:;" onclick="admin_add('添加企业账号','${ctx}/ssUser/toAdd','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					添加企业账号</a>
		</div>
		<div class="mt-20">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th width="90">登录名</th>
						<th width="90">昵称</th>
						<!-- <th width="130">电子邮箱</th>
						<th width="100">电话</th> -->
						<th width="80">用户状态</th>
						<th width="70">创建日期</th>
						<th width="90">用户角色</th>
						<th width="100">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	var custId="";
	debugger;
	custId=$("#custId").val();
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>ssUser/toSsUserList?custId="
					+ custId, /* 跳转url */
			"iDisplayLength" : 10, /* 展示条数 */
			"columnDefs" : [
					{
						"targets" : [ 0 ],
						"data" : "loginName",
						"sDefaultContent" : ""
					},
					{
						"targets" : [ 1 ],
						"data" : "nickname",
						"sDefaultContent" : ""
					},
					{
						"targets" : [ 2 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var status = full.status;
							if (status == "0")
								return "正常";
							else if (status == "1")
								return "冻结";
						}
					},
					{
						"targets" : [ 3 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var date = DateFormat.tolongdata(data.createDate);
							return date;
						}
					},
					{
						"targets" : [ 4 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var ids = data.roles;
							var idStr = "";
							if (ids != null) {
								for (var i = 0; i < ids.length; i++) //遍历用户角色
								{
									if (i > 0) {
										idStr += ",";
									}
									if (ids[i] != null) {
										idStr += ids[i].roleName;
									}
								}
							}
							return idStr;
						}
					},
					{
						"targets" : [ 5 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							return '<a title=\"编辑\" href="javaScript:toUpdateSsUser('
									+ data.acconutId
									+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe60c;</i></a>';
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
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "对不起，查询不到任何相关数据",
				"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				"sInfoEmpty": "当前显示 0 到 0 条，共 0  条记录",
				"sInfoFiltered" : "",
				"sProcessing" : "正在加载中...",
				"sSearch" : "搜索",
				"oPaginate" : {
					"sFirst" : "第一页",
					"sPrevious" : " 上一页 ",
					"sNext" : " 下一页 ",
					"sLast" : " 最后一页 "
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
						"name" : "loginName",
						"value" : $("#loginName").val()
					}, {
						"name" : "nickname",
						"value" : $("#nickname").val()
					});
				}
			});
			$('#example').dataTable().fnDraw();
		}
	</script>

</body>

</html>
