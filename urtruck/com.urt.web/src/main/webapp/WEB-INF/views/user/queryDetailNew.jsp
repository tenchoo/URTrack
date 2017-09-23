<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>   
<html>
<head>
	<title></title>
	
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
</head>
<body>
	<div class="pd-20">
	<h4 align="LEFT">批量录入明细信息</h4>
	<input id="batchId" type="hidden" value="${batchId}"/>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th >ICCID</th>
				<th >部门编码</th>
				<th >正式期产品</th>
				<th >测试期产品</th>
				<th >运营商</th>
				<th >一级分类</th>
				<th >二级分类</th>
				<th >MSISDN</th>
				<th >录入时间</th>
			</tr>
		</thead>
	</table>
	</div>
</div>
	<script type="text/javascript">
	var custId='';
	var iccId='';
	var msisdn='';
	function ShowDetail(){
	    iccId='';
		msisdn='';
		var dataIccid = [];
		var dataMsidn = [];
		var dataTableObj = {
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"sAjaxSource" :"<%=basePath %>userNewService/getDetailIccidInfoTSP",
				"fnServerParams": function ( aoData ) {
		           aoData.push( {
		            "name" :  "batchId",
		            "value": $("#batchId").val()} );
		           },
				"iDisplayLength": 10,  /* 展示条数 */
				 "columnDefs": [						        
						         {
									"targets" : [0],
								    "data" : null,
									"mRender" : function(data, type, full) {
									if(full.iccid=="" ||full.iccid==null)
									{
										dataIccid.push(" ");
									}else{
										dataIccid.push(full.iccid);
									}
									return full.iccid;
									}
								},
						         {
									"targets" : [1],
									"data" : null,
									"mRender" : function(data, type, full) {	
										custId=full.custid;
										return full.custid;
									}
								},
						         {"targets": [2],"data": "initproductname"} ,
						         {"targets": [3],"data": "testGoodsRlsIdname"} ,
						         {"targets": [4],"data": "operatorsName"} ,
						         {"targets": [5],"data": "value1"} ,
						         {"targets": [6],"data": "value2"} ,
						         {
								  "targets" : [7],
								  "data" : null,
								  "mRender" : function(data, type, full) {
									if(full.msisdn=="" ||full.msisdn==null)
										{
											dataMsidn.push(" ");
										}else{
											dataMsidn.push(full.msisdn);
										}
										return full.msisdn;
										}
									},
						         {
										"targets" : [ 8 ],
										"data" : "inDate",
										"mRender" : function(data, type, full) {
											if (full.inDate != null) {
												var updateTime = new Date(full.inDate)
														.format("yyyy-MM-dd");
												return updateTime;
											} else {
												return "";
											}
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
				  "oLanguage": {
				      "sLengthMenu": "每页显示 _MENU_ 条记录",
				      "sZeroRecords": "对不起，查询不到任何相关数据",
				      "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				      "sInfoEmtpy": "显示0到0条记录",
				      "sInfoFiltered": "",
				      "sProcessing": "正在加载中...",
				      "sSearch": "搜索",
				      "oPaginate": {
				          "sFirst":    "第一页",
				          "sPrevious": " 上一页 ",
				          "sNext":     " 下一页 ",
				          "sLast":     " 最后一页 "
				      }
				  },
			    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
			}
		iccId=dataIccid.join(";");
		msisdn=dataMsidn.join(";");
		$('#example').dataTable(dataTableObj);
	}
	
	$(function(){
		ShowDetail();
	});
	//单卡状态变更的方法
	 function singleTestState(){
		if($("#iccId").val()=="" && $("#msisdn").val()==""){
			alert("单卡变更iccid与msison不能同时为空！");
		}else{
			  $.ajax({
					type : "POST",
					url : "${ctx}/cardState/singleTestState",
					data : {
						"iccId":$("#iccId").val(),"msisdn":$("#msisdn").val(),"custId":custId
					},
					dataType : "json",
					cache : false,
					success : function(data) {						
							if(data==0){
								alert("测试期开通成功!");		
							}else{
								alert("测试期开通失败！");
							}									
					},
					error : function(error) {
						alert("测试期开通失败！");
					}
				}); 
		}	
	}
	
	//批量状态变更的方法
	 function mutilTestState(){
		if($("#iccId").val()=="" && $("#msisdn").val()==""){
			alert("批量卡变更iccid与msisdn不能同时为空！");
		}else{
			  $.ajax({
					type : "POST",
					url : "${ctx}/cardState/mutilTestState",
					data : {
						"iccId":iccId,"msisdn":msisdn,"custId":custId
					},
					dataType : "json",
					cache : false,
					success : function(data) {						
							if(data==0){
								alert("测试期开通成功!");		
							}else{
								alert("测试期开通失败:"+data+"条！");
							}									
					},
					error : function(error) {
						alert("测试期开通失败！");
					}
				}); 
		}	
	}
	</script>
	 <div class="pd-20">
	 <br>
	 <!-- <button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;" onclick="mutilTestState();"><i class="Hui-iconfont">&#xe665;</i>批量测试期开通</button> -->
	 <input class="btn btn-primary radius" type="button" style="float:right;margin-left:10px;"
								value="&nbsp;&nbsp;批量测试期开通&nbsp;&nbsp;" onclick="mutilTestState();">
	 <br>
        <!-- <h1>单卡测试期开通</h1>   -->     
        <h4 align="LEFT">单卡测试期开通</h4>
		<div class="oh">
			<div class="col-12">												
				<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name">ICCID: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">
						<input type="text" class="input-text" id="iccId"
							name="iccId" >
					</div>				
					<div class="col-md-3 col-lg-3 mt20"
						style="width: 25%;padding-left: 150px;">
						<label for="name">MSISDN: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin-left: 0px;">
						<input type="text" class="input-text" id="msisdn" name="msisdn" >
					</div>
			</div>							
			<!-- <button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;" onclick="singleTestState();"><i class="Hui-iconfont">&#xe665;</i>单卡测试期开通</button> -->
		   <input class="btn btn-primary radius" type="button" style="float:right;margin-left:10px;"
								value="&nbsp;&nbsp;单卡测试期开通&nbsp;&nbsp;" onclick="singleTestState();">
		</div>
		</div>
</body>
</html>
 