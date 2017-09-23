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
	<title>卡基本信息</title>
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
	$(function(){
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

		
	});
	
	function assign(){
		$.ajax({
			url:"${ctx}/iccidassignbatch/toAssignCard",
			data:{"batchId":$("#batchId").val(),"id":$("#id").val(),"channelCustId":$("#custId").val(),"attribute1":$("#attribute1").val(),"attribute2":$("#attribute2").val(),"cardstatus":$("#cardstatus").val(),"ifMaintenance":$("#ifMaintenance").val(),"value1":$("#value1").val(),"value2":$("#value2").val(),"initproduct":$("#initproduct").val()},
			success:function(result){
				layer_show("划拨成功");
			}
		});
	}
	</script>
</head>
<body>
	<div class="pd-20 font12">
	<form role="form" action="/userNewService/batchImport2" method="post"  id="cform">
		<input type="hidden" id="batchId" name="batchId" value="${batchId }">
		<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='basic.information.of.card' /></h1>
			<br>
		</div>
		<div class="oh">
			<lable class="bianhao"><span class="colorRed smallStar">*</span>订单编号: </lable><input type="text"class="colorRed smallStar" name="id" id="id" style="width: 15%;height: 30px;">
			<lable class="kehu"><span class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer' />: </lable><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
			<div class="col-12">
				<lable id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' /></lable><select id="attribute1" name="attribute1" class="input-text" style="width:200px;"></select>
				<lable id="lable2"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' /></lable><select id="attribute2" name="attribute2" class="input-text" style="width:200px;" onkeyup=""></select>
			</div>
		</div>
		<div class="oh">	
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span>生命周期</label>
				<div class="tBox">
					<select id="cardstatus" name="cardstatus" class="input-text"></select>
				</div>
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' /></label>
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
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.1.logo' /></lable><select id="value1" name="value1" class="input-text" style="width:200px;"></select>
				<lable id="lable4"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.2.logo' /></lable><select id="value2" name="value2" class="input-text" style="width:200px;" onkeyup=""></select>
			</div>
		</div>
		<div class="text-center mt20">
			<div class="col-3 ">
				<label for="name" class="font12 labelWidth fl"><span
						class="colorRed smallStar">*</span><fmt:message bundle='${pageScope.bundle}'  key='Product' /></label>
				<div class="tBox" >
					<select id="initproduct" name="initproduct" class="input-text"></select>
				</div>
			</div>
		</div>

		<br>
		<br>
		<br>
		<br>
		<div class="oh">
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<button id="search" type="button" class="btn btn-primary radius" onclick="assign()">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Preservation' />
				</button>
			</label>
			<label for="name" class="font12 labelWidth fl" style="width: 10%;">
				<input class="btn btn-primary radius" type="reset" value="<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />"> 
			</label>
		</div>
	</form>
	
</div>

</body>
</html>
