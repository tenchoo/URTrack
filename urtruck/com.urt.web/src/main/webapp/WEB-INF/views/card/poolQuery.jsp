<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>上传卡信息</title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript">

/* var */
	var dataTableObjPool = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>poolController/poolInfo"
		             ,    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */
		"columnDefs": [ 
			         {"targets": [0],"data": "poolId"} ,
			         {"targets": [1],"data": "poolDesc"} ,
			         {
							"targets" : [ 2 ],
							"data" : "updateTime",
							"mRender" : function(data, type, full) {
								if (full.updateTime != null) {
									var result = new Date(full.updateTime)
											.format("yyyy-MM-dd");
									return result;
								} else {
									return "";
								}
							}
						}],
	
			"sScrollX" : "100%",
			"sScrollXInner" : "100%",
			"bScrollCollapse" : true,
			"bPaginate" : true,
			"bLengthChange" : false,
			"bFilter" : false,
			"bDestroy" : true,
			"bSort" : false,
			"bInfo" : true,
			"bAutoWidth" : true,
			"aaSorting" : [ [ 0, "desc" ] ],
			"bStateSave" : false,
			"sPaginationType" : "full_numbers",
			"oLanguage" : {
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "对不起，查询不到任何相关数据",
				"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				"sInfoEmpty": "当前显示 0 到 0 条，共 0  条记录",
				"sInfoFiltered" : "",
				"sProcessing" : "正在加载中...",
				"sZeroRecords" : "对不起，查询不到相关数据！",
				"sEmptyTable" : "表中无数据存在！",
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


/* var */
	var dataTableObjPoolMember = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>poolController/poolMenberInfo"
			             ,    /* 跳转url */
			"iDisplayLength": 5,  /* 展示条数 */
			"columnDefs": [ 
				         {"targets": [0],"data": "poolId"} ,
				         {"targets": [1],"data": "iccid"} ,
				         {"targets": [2],"data": "msisdn"} ,
				         {
								"targets" : [ 3 ],
								"data" : "updateTime",
								"mRender" : function(data, type, full) {
									if (full.updateTime != null) {
										var result = new Date(full.updateTime)
												.format("yyyy-MM-dd");
										return result;
									} else {
										return "";
									}
								}
							}],
		
				"sScrollX" : "100%",
				"sScrollXInner" : "100%",
				"bScrollCollapse" : true,
				"bPaginate" : true,
				"bLengthChange" : false,
				"bFilter" : false,
				"bDestroy" : true,
				"bSort" : false,
				"bInfo" : true,
				"bAutoWidth" : true,
				"aaSorting" : [ [ 0, "desc" ] ],
				"bStateSave" : false,
				"sPaginationType" : "full_numbers",
				"oLanguage" : {
					"sLengthMenu" : "每页显示 _MENU_ 条记录",
					"sZeroRecords" : "对不起，查询不到任何相关数据",
					"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
					"sInfoEmpty": "当前显示 0 到 0 条，共 0  条记录",
					"sInfoFiltered" : "",
					"sProcessing" : "正在加载中...",
					"sZeroRecords" : "对不起，查询不到相关数据！",
					"sEmptyTable" : "表中无数据存在！",
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
	
	/* var */
	var dataTableObjUseInfo = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>poolController/poolUseInfo", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "poolId"
				},
				{
					"targets" : [ 1 ],
					"data" : "usedVolume"
				},
				{
					"targets" : [ 2 ],
					"data" : "remainingVolume"
				},
				{
					"targets" : [ 3 ],
					"data" : "totalVolume"
				},
				{
					"targets" : [ 4 ],
					"data" : "updateTime",
					"mRender" : function(data, type, full) {
						if (full.updateTime != null) {
							var result = new Date(full.updateTime)
									.format("yyyy-MM-dd");
							return result;
						} else {
							return "";
						}
					}
				}],

		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : true,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bDestroy" : true,
		"bSort" : false,
		"bInfo" : true,
		"bAutoWidth" : true,
		"aaSorting" : [ [ 0, "desc" ] ],
		"bStateSave" : false,
		"sPaginationType" : "full_numbers",
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ 条记录",
			"sZeroRecords" : "对不起，查询不到任何相关数据",
			"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			"sInfoEmpty": "当前显示 0 到 0 条，共 0  条记录",
			"sInfoFiltered" : "",
			"sProcessing" : "正在加载中...",
			"sZeroRecords" : "对不起，查询不到相关数据！",
			"sEmptyTable" : "表中无数据存在！",
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
	/* var */

	$(document).ready(function() {
		/* var str="201708";
		var arr=str.split("");
		alert(arr[0]);
		alert(arr[1]);
		alert(arr[2]);
		alert(arr[3]);
		alert(arr[4]);
		alert(arr[5]);
		var result=arr[0]+arr[1]+arr[2]+arr[3]+"-"+arr[4]+arr[5]+"-"+"01";
		alert(result); */

		$('#pool').dataTable(dataTableObjPool);
		$('#poolmember').dataTable(dataTableObjPoolMember);
		$('#pooluseinfo').dataTable(dataTableObjUseInfo);

	});

	function pool() {
		$('#pool').dataTable().fnClearTable(false);
		var oSettings = $('#pool').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "poolId",
					"value" : $("#poolId1").val()
				}

				);
			}
		});
		$('#pool').dataTable().fnDraw();
	}

	function poolMember() {
		$('#poolmember').dataTable().fnClearTable(false);
		var oSettings = $('#poolmember').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "poolId",
					"value" : $("#poolId2").val()
				}, {
					"name" : "iccid",
					"value" : $("#iccid").val()
				}, {
					"name" : "msisdn",
					"value" : $("#msisdn").val()
				}

				);
			}
		});
		$('#poolmember').dataTable().fnDraw();
	}

	/* var */
	function poolUseInfo() {
		$('#pooluseinfo').dataTable().fnClearTable(false);
		var oSettings = $('#pooluseinfo').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "poolId",
					"value" : $("#poolId3").val()
				}/* , {
					"name" : "queryMonth",
					"value" : $("#updateTime").val()
					} */ 
				 
				);
			}
		});
		$('#pooluseinfo').dataTable().fnDraw();
	}
</script>
</head>
<body>
	<div class="pd-20 font12">

		<form role="form" method="post" id="cform"
			enctype="multipart/form-data">
			<div class="seconSec ">
				<h1 align="left">企业流量池</h1>
				<br>
			</div>
			<div class="oh">
				<div class="col-12">
					<label class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span>选择流量池:</label>&nbsp; <select
						id="poolId1" name="poolId" class="input-text"
						style="width: 200px;">
						<option value="-1">请选择</option>
						<c:forEach var="poolName" items="${poolName}">
							<option value="${poolName.poolId}">${poolName.poolDesc}</option>
						</c:forEach>
					</select>&nbsp; &nbsp; &nbsp;
					<button id="search" type="button" class="btn btn-primary radius"
						onclick="pool();">
						<i class="Hui-iconfont">&#xe665;</i>搜索
					</button>
				</div>
			</div>
			<div class="mt-20">
				<table id="pool"
					class="table table-border table-bordered table-hover table-bg table-sort "
					cellpadding="0">
					<thead>
						<tr class="zpTable">
							<th>流量池编号</th>
							<th>流量池名称</th>
							<th>更新时间</th>
						</tr>
					</thead>
				</table>
				<br> <br>
			</div>
		</form>
<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
		<form role="form" method="post" id="cform"
			enctype="multipart/form-data">
			<div class="seconSec ">
				<h1 align="left">流量池成员</h1>
				<br>
			</div>
			<div class="oh row">
					<div class="col-md-4 col-lg-4 mt20">
						<label class="font12 labelWidth fl"> 
						<span class="colorRed smallStar">*</span>选择流量池: </label>
						<select id="poolId2" name="poolId" class="input-text" style="width: 200px;">
							<option value="-1">请选择</option>
							<c:forEach var="poolName" items="${poolName}">
								<option value="${poolName.poolId}">${poolName.poolDesc}</option>
							</c:forEach>
						</select> 
					</div>
					<div class="col-md-4 col-lg-4 mt20">
						<label class="font12 labelWidth fl">
						<span  class="colorRed smallStar">*</span>ICCID:</label>
						 <input type="text" id="iccid" style="width: 200px; height: 35px; border-radius: 5px;">
					 </div>
					 <div class="col-md-4 col-lg-4 mt20">
						<label class="font12 labelWidth fl"><span
							class="colorRed smallStar">*</span>MSISDN:</label><input
							type="text" id="msisdn" style="width: 200px; height: 35px;">
						<button id="search" type="button" class="btn btn-primary radius" style="margin-left: 10px;"
							onclick="poolMember();">
							<i class="Hui-iconfont">&#xe665;</i>搜索
						</button>
					</div>
			</div>
			<div class="mt-20">
				<table id="poolmember"
					class="table table-border table-bordered table-hover table-bg table-sort "
					cellpadding="0">
					<thead>
						<tr class="zpTable">
							<th>流量池编号</th>
							<th>ICCID</th>
							<th>MSISDN</th>
							<th>更新时间</th>
						</tr>
					</thead>
				</table>
				<br> <br>
			</div>
		</form>
<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />
		<form role="form" method="post" id="cform" action="/poolController/poolUseInfo"
			enctype="multipart/form-data">
			<div class="seconSec ">
				<h1 align="left">流量池流量信息</h1>
				<br>
			</div>
			<div class="oh">
				<div class="col-12">
					<label class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span>选择流量池:</label>&nbsp; <select
						id="poolId3" name="poolId" class="input-text"
						style="width: 200px;">
						<option value="-1">请选择</option>
						<c:forEach var="poolName" items="${poolName}">
							<option value="${poolName.poolId}">${poolName.poolDesc}</option>
						</c:forEach>
					</select> <!-- <label class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span>选择时间:</label>&nbsp;
					 <input id="updateTime" class="input-text"
							type="text" name="updateTime" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM',minDate:'1970-01',maxDate:'#now'})"
							readonly="readonly" style="width: 200px;">&nbsp; -->
					<button id="search" type="button" class="btn btn-primary radius"
						onclick="poolUseInfo();">
						<i class="Hui-iconfont">&#xe665;</i>搜索
					</button>
				</div>
			</div>
			<div class="mt-20">
				<table id="pooluseinfo"
					class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
					<thead>
						<tr class="zpTable">
							<th>流量池编号</th>
							<th>已用流量（MB）</th>
							<th>剩余流量（MB）</th>
							<th>总流量（MB）</th>
							<th>更新时间</th>
						</tr>
					</thead>
				</table>
				<br> <br>
			</div>
		</form>


	</div>

</body>
</html>
