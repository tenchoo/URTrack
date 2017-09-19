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

<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
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
$.ajax({
	url:"traffic/getChannelCust",
	data:{},
	success:function(result){
		var select=$("#custId").select2({
			width : 200,  
			placeholder: '客户',
			tags: "true",
			allowClear: true,
			data:result
		});
		$("#custId").change(function() {
			$('#type').empty();
			$('#version').empty();
			$('#type1').empty();
			$('#version1').empty();
			$('#initproduct').empty();
			$('#testGoodsRlsId').empty();
			$.ajax({
				type : "post",
				url : "${ctx}/ss/getAttrs",
				data: {"custId":$("#custId").val()},
				success:function(result){
					console.log(result);
					$("#attribute1").val(result.attrV1);
					$("#attribute2").val(result.attrV2);
				}
			}); 
			$.ajax({
				url:"${ctx}/ss/getTypeList",
				data:{"custId":$("#custId").val()},
				success:function(result){
					var select=$("#type").select2({
						width : 200,  
						placeholder: '类型',
						tags: "true",
						allowClear: true,
						data:result
					});
				
					$("#type").change(function() {
						$('#version').empty();
						var pid="";
						$.ajax({
							url:"${ctx}/ss/getPidByValue",
							data:{"id":$("#type").val(),"custId":$("#custId").val()},
							async:false,
							success:function(result){
								pid=result;
							}
						});
						$.ajax({
							url:"${ctx}/ss/getVersionList",
							data:{"pid":pid,"custId":$("#custId").val()},
							success:function(result){
								var select=$("#version").select2({
									width : 200,  
									placeholder: '版本',
									tags: "true",
									allowClear: true,
									data:result
								});
							}
						});
					});
				}
			});
			
			//标示
			$.ajax({
				url:"${ctx}userNewService/getType1TSP",
				data:{"custId":$("#custId").val()},
				success:function(result){
					debugger;
					var select=$("#type1").select2({
						width : 200,  
						placeholder: '类型标识',
						tags: "true",
						allowClear: true,
						data:result
					});
					
					$("#type1").change(function(){
						$('#version1').empty();
						$.ajax({
							url:"${ctx}/userNewService/getVersion1",
							data:{"pid":$("#type1").val()},
							success:function(result){
								var select=$("#version1").select2({
									width : 200,  
									placeholder: '版本标识',
									tags: "true",
									allowClear: true,
									data:result
								});
							}
						});
					});
				}
			});
			
		});
		
	}
});
	
	
	
		//验证信息
		function myValidate() {
			var custId = $("#custId").find("option:selected").val();
			if (custId == '' || custId == -1) {
				layer.msg("请选择部门");
				return false;
			}
			var type = $("#type").find("option:selected").val();
			if (type == '' || type == -1) {
				var lable1 = $("#lable1").text();
				layer.msg("请选择" + lable1);
				return false;
			}
			var version = $("#version").find("option:selected").val();
			if (version == '' || version == -1) {
				var lable2 = $("#lable2").text();
				layer.msg("请选择" + lable2);
				return false;
			}
			var type1 = $("#type1").find("option:selected").val();
			if (type1 == '' || type1 == -1) {
				var lable3 = $("#lable3").text();
				layer.msg("请选择" + lable3);
				return false;
			}
			var version1 = $("#version1").find("option:selected").val();
			if (version1 == '' || version1 == -1) {
				var lable4 = $("#lable4").text();
				layer.msg("请选择" + lable4);
				return false;
			}
			var operators = $("#operators").find("option:selected").val();
			if (operators == '' || operators == -1) {
				layer.msg("请选择运营商");
				return false;
			}
			var initproduct = $("#initproduct").find("option:selected").val();
			if (initproduct == '' || initproduct == -1 || !initproduct) {
				layer.msg("请选择正式期产品");
				return false;
			}
			var testGoodsRlsId = $("#testGoodsRlsId").find("option:selected").val();
			if (testGoodsRlsId == '' || testGoodsRlsId == -1 || !testGoodsRlsId) {
				layer.msg("请选择测试期产品");
				return false;
			}
			return true;
		};

		//下载模板
		function msgdownlaod() {
			window.location.href = "${ctx}/download/msgdownlaod.xlsx";
		}
		
		function downlaod() {
			window.location.href = "${ctx}/download/upload.xlsx";
		}

		function cmcc() {
			window.location.href = "${ctx}/download/cmcc.xlsx";
		}
		//验证上传文件
		function validateFile() {			
			var obj = document.getElementById('inputfile');		
			if (obj.value == '') {
				layer.msg("请上传Excel文件！");
				return false;
			}			
			var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];			
			if (stuff != 'xls' && stuff != 'xlsx') {
				layer.msg("请选择正确Excel文件格式");
				return false;
			}
			return true;
		}
		//验证上传文件
		function validateFile2() {
			var obj = document.getElementById('inputfile2');
			if (obj.value == '') {
				layer.msg("请上传Excel文件！");
				return false;
			}
			var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
			if (stuff != 'xls' && stuff != 'xlsx') {
				layer.msg("请选择正确Excel文件格式");
				return false;
			}
			return true;
		}
		//验证input
		function validateInput() {
			var iccidHeader = $("#iccidHeader").val();
			if (iccidHeader == '' || isNaN(iccidHeader)) {
				layer.msg("请正确填写iccid头");
				return false;
			}
			var startIccid = $("#startIccid").val();
			if (startIccid == '' || isNaN(startIccid)) {
				layer.msg("请正确填写iccid开始数");
				return false;
			}
			var endIccid = $("#endIccid").val();
			if (endIccid == '' || isNaN(endIccid)) {
				layer.msg("请正确填写iccid结束数");
				return false;
			}
			return true;
		}
		function upload(){
			/* 自己新加的内容 */
			 var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>userNewService/getIccidInfo"
			             ,    /* 跳转url */
			"iDisplayLength": 5,  /* 展示条数 */
			"columnDefs": [ 
				         {"targets": [0],"data": "batchId"} ,
				         {"targets": [1],"data": "simType"} ,
				         {"targets": [2],"data": "simSize"} ,
				         {"targets": [3],"data": "simFee"} ,
				         {"targets": [4],"data": "iccidStart"} ,
				         {"targets": [5],"data": "iccidEnd"} ,
						 {
							"targets" : [ 6 ],
							"data" : "recvTime",
							"mRender" : function(data, type, full) {
								if (full.recvTime != null) {
									var updateTime = new Date(full.recvTime)
											.format("yyyy-MM-dd");
									return updateTime;
								} else {
									return "";
								}
							}
						}, {
							"targets" : [ 7 ],
							"data" : "sumNum"
						},
						{
							"targets" : [ 8 ],
							"data" : "batchId",
							"mRender" : function(data, type, full) {
								return 

								'<a title=\"录入明细\" href="javaScript:toQuery('
								+ full.batchId
								+ ');" style=\"text-decoration:none\">录入明细</a>'
								;
							}
						}
						],
		
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
			var option = {
				    url : "<%=basePath%>userNewService/batchImportTSP",
				    type: "post",
				    success : function(data){
				    	 //重新构建table  
				    	 if (data != null) {
			    		 	alert(data.msg);
						}
	                     $('#example').dataTable().fnClearTable();   //将数据清除 
				    	 $('#example').dataTable(dataTableObj);
				   }, 
				   error:function(){
						alert('导入失败');
					}
			 };
			$("#cform").attr("enctype","multipart/form-data");
			if(myValidate()&& validateFile()){
			if(window.confirm("您确定要导入？")){
				$("#cform").ajaxSubmit(option);
			}
			}
	}
	
	function importIccid(){
		var option = {
			    url : "<%=basePath%>userNewService/batchImport2TSP",
			    type: "post",
			    success : function(data){
			    	 //重新构建table  
			    	 if (data != null) {
			    		 if(data.msg != null){
			    		 	alert(data.msg);
			    		 }
					 }
                     $('#example').dataTable().fnClearTable();   //将数据清除 
			    	 $('#example').dataTable(dataTableObj);
			   },
			   error:function(){
					alert('导入失败');
				}
		 };
		$("#cform").removeAttr("enctype");
		if(myValidate() && validateInput())
		if(window.confirm("您确定要导入？")){
			$("#cform").ajaxSubmit(option);
		}
	}
	//查询正式期产品的方法
	 function queryGoodsRealease(){
	    	$('#initproduct').empty();
			$.ajax({
				url:"${ctx}/userNewService/getGoodRealsesTSP",
				data:{"value2":$("#version").val(),"value1":$("#type").val(),"custId":$("#custId").val(),"operatorsId":$("#operators").val()},
				success:function(result){
					var select=$("#initproduct").select2({
						width : 200,  
						placeholder: '版本',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
	    }
	
	//查询测试期产品的方法
	 function getTestGoodRealses(){
	    	$('#testGoodsRlsId').empty();
			$.ajax({
				url:"${ctx}/userNewService/getTestGoodRealses",
				data:{"value2":$("#version").val(),"value1":$("#type").val(),"custId":$("#custId").val(),"operatorsId":$("#operators").val()},
				success:function(result){
					var select=$("#testGoodsRlsId").select2({
						width : 200,  
						placeholder: '版本',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
	    }
	function queryGoods(){
		getTestGoodRealses();
		queryGoodsRealease();
	}
	function batchImportNewMsg(){
		var option = {
			    url : "<%=basePath%>userNewService/batchImportNewMsg",
			    type: "post",
			    success : function(data){
			    	if (data == null ||data == "") {
						alert("Excel填入信息不正确！");
					} else {
			    		$("#sumNum").val(data.sumNum);
			    		$("#enterpriseConsignee").val(data.enterpriseConsignee);
			    		$("#consigneePhone").val(data.consigneePhone);
			    		$("#consigneeIdcard").val(data.consigneeIdcard);
			    		$("#deliveryAddress").val(data.deliveryAddress);
			    		$("#remark").val(data.remark);
			    		$("#simType").val(data.simType);
			    		$("#simSize").val(data.simSize);
			    		$("#simFee").val(data.simFee);
			    		$("#iccidStart").val(data.iccidStart);
			    		$("#iccidEnd").val(data.iccidEnd);
			    		$("#numberStart").val(data.numberStart);
			    		$("#numberEnd").val(data.numberEnd);
			    		if (data.deliveryDate != null && data.deliveryDate != "") {
				    		var deliveryDate = new Date(data.deliveryDate).format("yyyy-MM-dd");
				    		$("#delDate").val(deliveryDate);
						}
			    		$("#consignor").val(data.consignor);
			    		$("#logisticsCompany").val(data.logisticsCompany);
			    		$("#logisticsNumber").val(data.logisticsNumber);
			    		$("#pullPerson").val(data.pullPerson);
			    		$("#pullNumber").val(data.pullNumber);
			    		$("#orderFee").val(data.orderFee);
			    		$("#orderDate").val(data.orderDate);
					}
			   }, 
			   error:function(){
					alert('格式内容有误，导入填写失败');
				}
		 };
		$("#cform").attr("enctype","multipart/form-data");
		if(validateFile2()){
			$("#cform").ajaxSubmit(option);
		}
	}
	function zhankai(){
		$('#hidediv').show();
	}
	function shouqi(){
		$('#hidediv').hide();
	}
	function toQuery(batchId){
		if(batchId != ""){
			window.location.href='<%=basePath%>userNewService/queryDetail?batchId='+batchId;
		}
	}
	function toOneQuery(batchId){
		if(batchId != ""){
			window.location.href='<%=basePath%>userNewService/queryOneDetail?batchId='+batchId;
		}
	}
	function toQueryNew(batchId){
		if(batchId != ""){
			window.location.href='<%=basePath%>userNewService/queryDetailNew?batchId='+batchId;
		}
	}
	</script>
</head>
<body>
	<div class="pd-20 font12">
		<div>
			<label> <span class="colorRed smallStar"><font
					size="5">*</font></span>请您先填写批量录入相关资料信息
			</label>
		</div>
		<form role="form" action="/userNewService/batchImport2TSP" method="post"
			id="cform">
				<div class="seconSec ">
					<h1 align="left">卡基本信息</h1>
					<br>
				
				<div class="col-12">													
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>部 门:</label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%; margin-left: 0px;">
						<select id="custId" name=custid class="selectbox">
							<option value="-1" selected="selected">请选择</option>
						</select>
					</div>			
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;padding-left: 150px;">
						<label for="name" class="font12 labelWidth fl"> <span class="colorRed smallStar">*</span>一级分类:</label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%; margin-left: 0px;">
						<select id="type" name="type" class="input-text"
							style="width: 200px;"></select>
					</div>
					</div>
					<div class="col-12">													
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>二级分类:</label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">						
						<select id="version" name="version" class="input-text"
							style="width: 200px;" onkeyup=""></select>
					</div>				
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;padding-left: 150px;">
						<label for="name" class="font12 labelWidth fl"> <span class="colorRed smallStar">*</span>运营商:</label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%; margin-left: 0px;">
						<select id="operators" name="operators" class="selectbox">
								<option value="-1">请选择</option>
								<c:forEach var="operator" items="${operatorList}">
									<option value="${operator.operatorsId}">${operator.operatorsName}</option>
								</c:forEach>
							</select>
					</div>
				</div>	
				<div class="col-12">													
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>卡采购单号:</label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">						
						<input type="text" class="input-text" id="buyOrderNo" name="buyOrderNo" >
					</div>				
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;padding-left: 150px;">
						<label for="name" class="font12 labelWidth fl"> <span class="colorRed smallStar">*</span>测试卡周期:</label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%; margin-left: 0px;">
						<select id="testCycle" name="testCycle" class="selectbox">
							<option value="6" selected="selected">6个月</option>
						</select>
					</div>
				</div>
				<div class="col-12">												
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>测试期产品: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">
						<select id="testGoodsRlsId" name="testGoodsRlsId" class="input-text"></select>
					</div>				
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;padding-left: 150px;">
						<label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>正式期产品: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%; margin-left: 0px;">
						<select id="initproduct" name="initproduct" class="input-text"></select>
					</div>
				</div>
									
				 <div class="col-12">  
					<button style="float:right;" id="search" type="button" class="btn btn-primary radius" onclick="queryGoods();" style="margin:20px;"><i class="Hui-iconfont">&#xe665;</i> 查询产品</button>
			     </div>  
				</div>		
						
				<!-- 自己加的 -->
			<!-- <div class="cl pd-5 bg-1 bk-gray mt-20"> -->
			<div class="col-12 ">
			<br><br>
				 <label>上传方式：</label> <input name="Fruit" type="radio" value=""
					onclick="$('#selectHander').hide(),$('#selectExcel').show();" />Excel文件上传
				<input name="Fruit" type="radio" value=""
					onclick="$('#selectExcel').hide(),$('#selectHander').show();" />手动上传
			</div>
			<div class="cl pd-5 mt-10" id="selectExcel" style="display: none">
				<label for="name" style="float: left; width: 100px;">导入文件：</label>
				<input type="file" class=""
					style="width: 200px; padding-top: 7px; float: left;" id="inputfile"
					name="file">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="upload();">
					<i class="Hui-iconfont">&#xe665;</i> 导入
				</button>
				<button id="search" type="button" class="btn btn-primary radius"
					style="float: right; margin-left: 10px;" onclick="cmcc();">
					<i class="Hui-iconfont">&#xe665;</i>上传Excel模板 下载
				</button>
			</div>
			<div class="cl pd-5 mt-10" id="selectHander" style="display: none">
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl">Iccid号头:</label>
					<div class="tBox">
						<input type="text" name="iccidHeader" id="iccidHeader"
							placeholder=" Iccid号头" style="width: 200px" class="input-text">
					</div>
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl">启始Iccid:</label>
					<div class="tBox">
						<input type="text" name="startIccid" id="startIccid"
							placeholder=" 启始Iccid" style="width: 200px" class="input-text">
					</div>
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl">截至Iccid:</label>
					<div class="tBox">
						<input type="text" name="endIccid" id="endIccid"
							placeholder=" 截至Iccid" style="width: 200px" class="input-text">
					</div>
				</div>
				<div class="col-3 mt20">
					<label for="name" class="font12 labelWidth fl">Iccid号尾:</label>
					<div class="tBox">
						<input type="text" name="iccidTail" id="iccidTail"
							placeholder=" Iccid号尾" style="width: 200px" class="input-text">
					</div>
				</div>
				<div class="text-center mt20">
					<button id="search" type="button" style="margin-top: 20px"
						class="btn btn-primary radius" onclick="importIccid();">
						<i class="Hui-iconfont">&#xe665;</i> 导入
					</button>
				</div>
			</div>
		</form>
		<div class="col-12">												
			<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
			  <label for="name" class="font12 labelWidth fl"><span class="colorRed smallStar">*</span>起始ICCID: </label>
			</div>
			<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">			
				<input type="text" class="input-text" name="iccidStartByQuery" id="iccidStartByQuery">
			</div>				
			<div class="col-md-3 col-lg-3 mt20" style="width: 25%;padding-left: 150px;">
				<button id="search2" type="button" class="btn btn-primary radius"
				onclick="queryByStartIccid();">
				<i class="Hui-iconfont">&#xe665;</i> 查询
			</button>
			</div>
			<br><br>
		</div>
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th>批次号</th>
						<th>起始ICCID</th>
						<th>终止ICCID</th>
						<th>入表日期</th>
						<th>操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
		var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>userNewService/getIccidInfo"
			             ,    /* 跳转url */
			"iDisplayLength": 5,  /* 展示条数 */
			"columnDefs": [ 
				         {"targets": [0],"data": "batchId"} ,
				         {"targets": [1],"data": "iccidStart"} ,
				         {"targets": [2],"data": "iccidEnd"} ,
						 {
							"targets" : [ 3 ],
							"data" : "recvTime",
							"mRender" : function(data, type, full) {
								if (full.recvTime != null) {
									var updateTime = new Date(full.recvTime)
											.format("yyyy-MM-dd");
									return updateTime;
								} else {
									return "";
								}
							}
						},
						{
							"targets" : [ 4 ],
							"data" : "batchId",
							"mRender" : function(data, type, full) {
								return '<a title=\"录入明细\" href="javaScript:toQuery('
								+ full.batchId
								+ ');" style=\"text-decoration:none\">录入明细</a>'
								;
							}
						}
						],
		
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
		$('#example').dataTable(dataTableObj);
		$("#operators").change(function() {
			$('#cardstatus').empty();
			$('#testGoodsRlsId').empty();
			$('#initproduct').empty();
			$.ajax({
				url:"<%=basePath%>userNewService/getCardstatusList",
					data : {
						"operators" : $("#operators").val()
					},
					success : function(result) {
						var select = $("#cardstatus").select2({
							width : 200,
							placeholder : '卡类型',
							tags : "true",
							allowClear : true,
							data : result
						});
					}
				});
			});

		});

		function queryByStartIccid() {
			$('#example').dataTable().fnClearTable(false);
			var oSettings = $('#example').dataTable().fnSettings();
			oSettings.aoServerParams.push({
				"fn" : function(aoData) {
					aoData.push({
						"name" : "iccidStart",
						"value" : $("#iccidStartByQuery").val()
					});
				}
			});
			$('#example').dataTable().fnDraw();
		}
	</script>
</body>
</html>
