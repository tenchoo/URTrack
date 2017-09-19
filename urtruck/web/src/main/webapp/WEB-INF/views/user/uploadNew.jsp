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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Upload.card.information' /></title>
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
	 
	<script type="text/javascript">
	$.ajax({
		//url:"${ctx}/cust/getAgentList",
		url:"traffic/getChannelCust",
		data:{},
		success:function(result){
			var select=$("#custId").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Customer" />',
				tags: "true",
				allowClear: true,
				data:result
			});
			$("#custId").change(function() {
				$('#type').empty();
				$('#version').empty();
				$('#initproduct').empty();
				$.ajax({
					type : "post",
					url : "${ctx}/ss/getAttrs",
					data: {"custId":$("#custId").val()},
					success:function(result){
						console.log(result);
						$("#attribute1").val(result.attrV1);
						$("#attribute2").val(result.attrV2);
				 	  /* 	$("#lable1").text(result.attr1+":");
						$("#lable2").text(result.attr2+":"); */
					}
				}); 
				$.ajax({
					url:"${ctx}/ss/getTypeList",
					data:{"custId":$("#custId").val()},
					success:function(result){
						var select=$("#type").select2({
							width : 200,  
							placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Type" />',
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
										placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
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
	
	$.ajax({
		url:"userNewService/getType1",
		data:{},
		success:function(result){
			var select=$("#type1").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="type.identification" />',
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
							placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version.identifier" />',
							tags: "true",
							allowClear: true,
							data:result
						});
					}
				});
			});
		}
	});
		//éªè¯ä¿¡æ¯
		function myValidate() {
			var custId = $("#custId").find("option:selected").val();
			if (custId == '' || custId == -1) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.customer' />");
				return false;
			}
			var type = $("#type").find("option:selected").val();
			if (type == '' || type == -1) {
				var lable1 = $("#lable1").text();
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />" + lable1);
				return false;
			}
			var version = $("#version").find("option:selected").val();
			if (version == '' || version == -1) {
				var lable2 = $("#lable2").text();
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />" + lable2);
				return false;
			}
			var operators = $("#operators").find("option:selected").val();
			if (operators == '' || operators == -1) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />");
				return false;
			}
			var cardstatus = $("#cardstatus").find("option:selected").val();
			if (cardstatus == '' || cardstatus == -1) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.card.status' />");
				return false;
			}
			var initproduct = $("#initproduct").find("option:selected").val();
			if (initproduct == '' || initproduct == -1 || !initproduct) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.Product' />");
				return false;
			}
			return true;
		};

		//ä¸è½½æ¨¡æ¿
		function msgdownlaod() {
			window.location.href = "${ctx}/download/msgdownlaod.xlsx";
		}
		
		function downlaod() {
			window.location.href = "${ctx}/download/upload.xlsx";
		}

		function cmcc() {
			window.location.href = "${ctx}/download/cmcc.xlsx";
		}
		//éªè¯ä¸ä¼ æä»¶
		function validateFile() {
			var obj = document.getElementById('inputfile');
			if (obj.value == '') {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />ï¼");
				return false;
			}
			var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
			if (stuff != 'xls' && stuff != 'xlsx') {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
				return false;
			}
			return true;
		}
		//éªè¯ä¸ä¼ æä»¶
		function validateFile2() {
			var obj = document.getElementById('inputfile2');
			if (obj.value == '') {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />ï¼");
				return false;
			}
			var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3];
			if (stuff != 'xls' && stuff != 'xlsx') {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
				return false;
			}
			return true;
		}
		//éªè¯input
		function validateInput() {
			var iccidHeader = $("#iccidHeader").val();
			if (iccidHeader == '' || isNaN(iccidHeader)) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.iccid.correctly' />");
				return false;
			}
			var startIccid = $("#startIccid").val();
			if (startIccid == '' || isNaN(startIccid)) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.start.number.of.iccid.correctly' />");
				return false;
			}
			var endIccid = $("#endIccid").val();
			if (endIccid == '' || isNaN(endIccid)) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.end.number.of.iccid.correctly' />");
				return false;
			}
			return true;
		}
		function upload(){
			var option = {
				    url : "<%=basePath %>userNewService/batchImportNew",
				    type: "post",
				    success : function(data){
				    	 //éæ°æå»ºtable  
				    	 if (data != null) {
			    		 	alert(data.msg);
						}
	                     $('#example').dataTable().fnClearTable();   //å°æ°æ®æ¸é¤ 
				    	 $('#example').dataTable(dataTableObj);
				   }, 
				   error:function(){
						alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
					}
			 };
			$("#cform").attr("enctype","multipart/form-data");
			//if(myValidate()&& validateFile())
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />ï¼")){
				$("#cform").ajaxSubmit(option);
			}
	}
	
	function importIccid(){
		var option = {
			    url : "<%=basePath %>userNewService/batchImport2",
			    type: "post",
			    success : function(data){
			    	 //éæ°æå»ºtable  
			    	 if (data != null) {
			    		 if(data.msg != null){
			    		 	alert(data.msg);
			    		 }
					 }
                     $('#example').dataTable().fnClearTable();   //å°æ°æ®æ¸é¤ 
			    	 $('#example').dataTable(dataTableObj);
			   },
			   error:function(){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
				}
		 };
		$("#cform").removeAttr("enctype");
		if(myValidate() && validateInput())
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />ï¼")){
			$("#cform").ajaxSubmit(option);
		}
	}
	//æ¥è¯¢äº§åçæ¹æ³
	 function queryGoodsRealease(){
	    	$('#initproduct').empty();
			$.ajax({
				url:"${ctx}/userNewService/getGoodRealses",
				data:{"value2":$("#version").val(),"value1":$("#type").val(),"custId":$("#custId").val(),"operatorsId":$("#operators").val()},
				success:function(result){
					var select=$("#initproduct").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
	    }
	function batchImportNewMsg(){
		var option = {
			    url : "<%=basePath %>userNewService/batchImportNewMsg",
			    type: "post",
			    success : function(data){
			    	if (data == null ||data == "") {
						alert("Excel<fmt:message bundle='${pageScope.bundle}'  key='The.information.provided.is.incorrect' />ï¼");
					} else {
			    		$("#sumNum").val(data.sumNum);
			    		$("#enterpriseConsignee").val(data.enterpriseConsignee);
			    		$("#consigneePhone").val(data.consigneePhone);
			    		$("#consigneeIdcard").val(data.consigneeIdcard);
			    /* 		$("#industryCategory").val(data.industryCategory);
			    		$("#industrySegmentation").val(data.industrySegmentation); */
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
					alert("<fmt:message bundle='${pageScope.bundle}'  key='Format.content.is.incorrect, import.failed' />");
				}
		 };
		$("#cform").attr("enctype","multipart/form-data");
		if(validateFile2()){
			$("#cform").ajaxSubmit(option);
		}
	}	
	<!-- function zhankai(){
		$('#hidediv').show();
	}
	function shouqi(){
		$('#hidediv').hide();
	} -->
	
	 
	</script>
</head>
<body>
	<div class="pd-20 font12">
	<div>
		 <font color="red">导入</font>
	  <!-- <button id="zhankai" type="button" class="btn btn-primary radius" 
					onclick="zhankai();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='expand' />
		</button>
		<button id="shouqi" type="button" class="btn btn-primary radius" 
					onclick="shouqi();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='collapsing' />
		</button> -->	
	</div>
	<form role="form" action="/userNewService/batchImport2" method="post"  id="cform">
	<div id="hidediv" style="display:none;">
		<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='basic.information.of.card' /></h1>
			<br>
		</div>
		<div class="oh">
			<div class="col-12">
				<lable class="kehu"><span class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer' />: </lable><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
				<lable id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' />ï¼</lable><select id="type" name="type" class="input-text" style="width:200px;"></select>
				<lable id="lable2"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />ï¼</lable><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>
 				<input type="text" id="attribute1" name="attribute1" style="display:none">
				<input type="text" id="attribute2" name="attribute2" style="display:none">
			</div>
		</div>
		<div class="oh">	
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Operator' />: </label>
				<div class="tBox">
					<select id="operators" name="operators" class="selectbox">
						<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
						<c:forEach var="operator" items="${operatorList}">
						<option value="${operator.operatorsId}">${operator.operatorsName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Card.status' />ï¼</label>
				<div class="tBox">
					<select id="cardstatus" name="cardstatus" class="input-text"></select>
				</div>
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' />ï¼</label>
				<div class="tBox">
				 	<select id="ifMaintenance" name="ifMaintenance" class="input-text">
						<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
						<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
				   	</select>	
				</div>
			</div>
		</div>
		<br>
		<div class="oh">
			<div class="col-12" style="margin-left:30px;">
				<lable id="lable3"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.1.logo' />ï¼</lable><select id="type1" name="type1" class="input-text" style="width:200px;"></select>
				<lable id="lable4"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.2.logo' />ï¼</lable><select id="version1" name="version1" class="input-text" style="width:200px;" onkeyup=""></select>
			</div>
		</div>
		<div class="text-center mt20">
			<div class="col-3 ">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Product' />ï¼</label>
				<div class="tBox" >
					<select id="initproduct" name="initproduct" class="input-text"></select>
				</div>
			</div>

			<button style="margin-right: 100px" id="search" type="button" class="btn btn-primary radius" onclick="queryGoodsRealease();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query.products' /></button>
		</div>
		
	<!-- 	<div class="text-center mt20">
			<label for="name" class="font12 labelWidth fl" style="width: 8%;">åæ¶ç¼´è´¹ï¼</label>
			<input type="checkbox"class="font12 labelWidth fl" name="payment" id="payment" value="1" style="width: 10%;height: 20px;">
			<label for="name" class="font12 labelWidth fl" style="width: 15%;">çæç¨æ·ä¿¡æ¯ï¼</label>
			<input type="checkbox"class="font12 labelWidth fl" name="generateUserInfor" id="generateUserInfor" value="1" style="width: 10%;height: 20px;">
		</div> -->
<br>
<br>
<p></p>
		<div class="text-center mt20">
			<label for="name" class="font12 labelWidth fl" style="width: 18%;"><fmt:message bundle='${pageScope.bundle}'  key='you.to.select.the.import.Excel' />ï¼</label>
			
			 <input type="file" class="" style="width:200px;padding-top: 7px;float:left;" id="inputfile2" name="file2">
			 
			 <label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<button id="search" type="button" class="btn btn-primary radius" onclick="batchImportNewMsg();">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.information' />
				</button>
			</label>
			<button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;"
					onclick="msgdownlaod();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Excel.template.download' />
			</button>
		</div>

 <br>
 <br>
 <br>
 <br>
		<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='Order.information' /></h1>
			<br>
		</div>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Order.numbers' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="sumNum" id="sumNum" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.consignee' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="enterpriseConsignee" id="enterpriseConsignee" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Reciever.Phonenumber' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="consigneePhone" id="consigneePhone" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Reciever.ID.card' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="consigneeIdcard" id="consigneeIdcard" style="width: 15%;height: 30px;">
		</div>
		<br>
		<div class="oh">
<!-- 			<label for="name" class="font12 labelWidth fl" style="width: 10%;">è¡ä¸ç±»å«<fmt:message bundle='${pageScope.bundle}'  key='' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="industryCategory" id="industryCategory" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">è¡ä¸ç»å<fmt:message bundle='${pageScope.bundle}'  key='' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="industrySegmentation" id="industrySegmentation" style="width: 15%;height: 30px;"> -->
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Shipping.Address' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="deliveryAddress" id="deliveryAddress" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='order.date' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="orderDate" id="orderDate" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Other.note.message' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="remark" id="remark" style="width: 15%;height: 30px;">
		</div>
		
 <br>
	 	<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='SIM.card.number.information' /></h1>
			<br>
		</div>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Card.type' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="simType" id="simType" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Card.size' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="simSize" id="simSize" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='card.cost.(Yuan/each)' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="simFee" id="simFee" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='start' /> ICCIDï¼</label>
			<input type="text"class="font12 labelWidth fl" name="iccidStart" id="iccidStart" style="width: 15%;height: 30px;">
		</div>
		<br>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='end' /> ICCIDï¼</label>
			<input type="text"class="font12 labelWidth fl" name="iccidEnd" id="iccidEnd" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Assign.starting.number' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="numberStart" id="numberStart" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Assign.end.number' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="numberEnd" id="numberEnd" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='order.amount' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="orderFee" id="orderFee" style="width: 15%;height: 30px;">
		</div>
<br>	
	 	<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='logistics.information' /></h1>
			<br>
		</div>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='Delivery.date' />ï¼</label>
			<input name="delDate" id="delDate" class="font12 labelWidth fl" style="width: 15%;height: 30px;"
							type="text" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM-dd',minDate:'1970-01-01',maxDate:'#now'})"
							readonly="readonly">
			
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='consignor' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="consignor" id="consignor" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='logistics.company' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="logisticsCompany" id="logisticsCompany" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='logistics.number' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="logisticsNumber" id="logisticsNumber" style="width: 15%;height: 30px;">
		</div>
		<br>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='receiver' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="pullPerson" id="pullPerson" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;"><fmt:message bundle='${pageScope.bundle}'  key='receiver.Phone.number' />ï¼</label>
			<input type="text"class="font12 labelWidth fl" name="pullNumber" id="pullNumber" style="width: 15%;height: 30px;">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<button id="search" type="button" class="btn btn-primary radius" onclick="shouqi();">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Preservation' />
				</button>
			</label>
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<input class="btn btn-primary radius" type="reset" value="<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />"> 
			</label>
		</div>
<br>	
</div>  
       <div class="cl pd-5 mt-10" id="selectExcel" >
			<label for="name" style="float:left;width:100px;"><fmt:message bundle='${pageScope.bundle}'  key='Import.excel.file' /></label>
	      	<input type="file" class="" style="width:200px;padding-top: 7px;float:left;" id="inputfile" name="file">
	        <button id="search" type="button" class="btn btn-primary radius" onclick="upload();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' /></button> 	
	    </div>
	   <!-- <div class="cl pd-5 bg-1 bk-gray mt-20">
			<label><fmt:message bundle='${pageScope.bundle}'  key='Upload.mode' />ï¼</label> 
			<input name="Fruit" type="radio" value=""  onclick="$('#selectHander').hide(),$('#selectExcel').show();"/>Excel<fmt:message bundle='${pageScope.bundle}'  key='File.upload' /> 
			<input name="Fruit" type="radio" value=""  onclick="$('#selectExcel').hide(),$('#selectHander').show();" /><fmt:message bundle='${pageScope.bundle}'  key='Manual.upload' />
		</div>  
		
	    <div class="cl pd-5 mt-10" id="selectExcel" style="display:none">
			<label for="name" style="float:left;width:100px;"><fmt:message bundle='${pageScope.bundle}'  key='Import.excel.file' />ï¼</label>
	      	<input type="file" class="" style="width:200px;padding-top: 7px;float:left;" id="inputfile" name="file">
	        <button id="search" type="button" class="btn btn-primary radius" onclick="upload();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' /></button> 	
	       <button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;"
					onclick="downlaod();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Other.Excel.template.download' />
			</button>  -->
			<button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;"
					onclick="cmcc();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Mobile.upload.Excel.template.download' />
				</button>
      	</div>
		<div class="cl pd-5 mt-10" id="selectHander" style="display:none">
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl">Iccid<fmt:message bundle='${pageScope.bundle}'  key='Head.of.number' />:</label>
				<div class="tBox">
					<input type="text" name="iccidHeader" id="iccidHeader" placeholder=" Iccid<fmt:message bundle='${pageScope.bundle}'  key='Head.of.number' />"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='origin' /> Iccid:</label>
				<div class="tBox">
					<input type="text" name="startIccid" id="startIccid" placeholder=" å¯å§Iccid<fmt:message bundle='${pageScope.bundle}'  key='' />"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='deadline' /> Iccid:</label>
				<div class="tBox">
					<input type="text" name="endIccid" id="endIccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='deadline' /> Iccid"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl">Iccid <fmt:message bundle='${pageScope.bundle}'  key='Tail.of.number' />:</label>
				<div class="tBox">
					<input type="text" name="iccidTail" id="iccidTail" placeholder=" Iccid <fmt:message bundle='${pageScope.bundle}'  key='Tail.of.number' />"  style="width:200px" class="input-text">
				</div>
		</div>	
		<div class="text-center mt20">
			<button id="search" type="button" style="margin-top: 20px" class="btn btn-primary radius" onclick="importIccid();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.files' /></button>
		</div>
		</div>
	</form>
	<div class="mt-20">
	        
	 <!-- <span style="width: 30%;"><fmt:message bundle='${pageScope.bundle}'  key='start' />ICCID: <input type="text" style="width: 20%;height: 30px;" name="iccidStartByQuery" id="iccidStartByQuery"> -->
		</span>
		<!-- <span style="width: 30%;">å¥è¡¨æ¥æ
		<input name="recvTimeByQuery" id="recvTimeByQuery" style="width: 20%;height: 30px;" type="text" 
				onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM-dd',minDate:'1970-01-01',maxDate:'#now'})" 
				readonly="readonly">
		</span> -->
	   <!-- <button id="search2" type="button" class="btn btn-primary radius" onclick="queryByStartIccid();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button> -->
		
	</div>	 
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
			    <th ><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
			    <th>文件名</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Batch.number' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.size' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='card.cost' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='start' />ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='end' />ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Total.quantity' /></th>				
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
		"sAjaxSource" : "<%=basePath %>userNewService/getIccidInfo"
		             ,    /* è·³è½¬url */
		"iDisplayLength": 5,  /* å±ç¤ºæ¡æ° */
		"columnDefs": [
                     {
                    	 "targets" : [ 0 ],
                         "data" : "batchId",
                        "mRender" : function(data, type, full) {
	                     return '<a title=\"卡基本信息\" href="javaScript:toBasicCard('
			             + full.batchId        
			            + ');" class=\"ml-5\" style=\"text-decoration:none\">卡基本信息</a>'
			               + 
			            '<a title=\"SIM卡、号信\" href="javaScript:toSIMCard('
				           + full.batchId
				          + ');" class=\"ml-5\" style=\"text-decoration:none\">SIM卡、号信息</a>'
				             +				        
					     '<a title=\"录入信息\" href="javaScript:toOneQuery('
				           + full.batchId
				          + ');" class=\"ml-5\" style=\"text-decoration:none\">录入信息</a>'
				          +
				          '<a title=\"录入明细\" href="javaScript:toQuery('
				           + full.batchId
				          + ');" class=\"ml-5\" style=\"text-decoration:none\">录入明细</a>'
                      } 
                      },
                     {"targets": [1],"data": "fileName"},
			         {"targets": [2],"data": "batchId"} ,
			         {"targets": [3],"data": "simType"} ,
			         {"targets": [4],"data": "simSize"} ,
			         {"targets": [5],"data": "simFee"} ,
			         {"targets": [6],"data": "iccidStart"} ,
			         {"targets": [7],"data": "iccidEnd"} ,
					 {
						"targets" : [ 8 ],
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
						"targets" : [ 9 ],
						"data" : "sumNum"
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
					"sLengthMenu" : "æ¯é¡µæ¾ç¤º _MENU_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
		            "sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='sorry,no.records' />",
		            "sInfo" : "<fmt:message bundle='${pageScope.bundle}'  key='Current.view' /> _START_ <fmt:message bundle='${pageScope.bundle}'  key='To' />"+
		            			"_END_ <fmt:message bundle='${pageScope.bundle}'  key='Article' />,<fmt:message bundle='${pageScope.bundle}'  key='Total' />"+
		            			" _TOTAL_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
		            "sInfoEmtpy" : "æ¾ç¤º0å°0æ¡è®°å½",
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
						[ "æ¯é¡µ10æ¡", "æ¯é¡µ25æ¡", "æ¯é¡µ50æ¡", "æ¾ç¤ºæææ°æ®", "ä¸æ¾ç¤ºæ°æ®" ] ]
			//è®¾ç½®æ¯é¡µæ¾ç¤ºè®°å½çä¸æèå
			}
		$('#example').dataTable(dataTableObj);
		$("#operators").change(function() {
			$('#cardstatus').empty();
			$.ajax({
				url:"<%=basePath%>userNewService/getCardstatusList",
				data:{"operators":$("#operators").val()},
				success:function(result){
					var select=$("#cardstatus").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Card.type" />',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
		});
	    
	});
		
		function queryByStartIccid(){
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
		function toQuery(batchId){
			if(batchId != ""){
				layer_show('录入明细','${ctx}/iccidassignbatch/queryDetail?batchId='+batchId,'800','550');				
			}
		}
		function toOneQuery(batchId){
			if(batchId != ""){
				layer_show('录入明细','${ctx}/iccidassignbatch/queryOneDetail?batchId='+batchId,'800','550');				
			}
		}
		function toBasicCard(batchId){
			if(batchId != ""){
				layer_show('卡基本信息','${ctx}/iccidassignbatch/basicCard?batchId='+batchId,'800','550');
			}
		}
		function toSIMCard(batchId){
			if(batchId != ""){
				layer_show('SIM卡、号信息','${ctx}/iccidassignbatch/SIMCard?batchId='+batchId,'800','550');
			}
		}
</script>
</body>
</html>