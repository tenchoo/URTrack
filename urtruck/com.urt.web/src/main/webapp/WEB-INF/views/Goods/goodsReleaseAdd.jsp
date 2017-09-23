<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.release.page' /></title>

<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
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
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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
<script type="text/javascript">
/* var data = [{ id: 0, text: 'enhancement' }, { id: 1, text: 'bug' }, { id: 2, text: 'duplicate' }, { id: 3, text: 'invalid' }, { id: 4, text: 'wontfix' }];

$("#releaseUnit").select2({
  data: data
}) */
$(function(){
	<%-- $.post("<%=request.getContextPath()%>/goods/findGoods",function(data){
		var goodsList = eval(data);
		for ( var i = 0; i < goodsList.length; i++) {
			$("#goods").append("<option value='"+goodsList[i].goodsId+"'>"+goodsList[i].goodsName+"</option>");
		}
	},"json"); --%>
	var arr1 = new Array(); 
	 $.post("<%=request.getContextPath()%>/goods/findGoods",function(data){
		var goodsList = eval(data);
		for ( var i = 0; i < goodsList.length; i++) {
			/* $("#goods").append("<option value='"+goodsList[i].goodsId+"'>"+goodsList[i].goodsName+"</option>"); */
			var obj={id:goodsList[i].goodsId,text:goodsList[i].goodsName}
			arr1[i]=obj;
		}
		 $("#goods").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Product" />',
				tags: "true",
				allowClear: true,
				data:arr1
		});
	},"json"); 
	/* $.ajax({
		url:"${ctx}/goods/findGoods",
		data:{},
		success:function(result){
			var select=$("#custId").select2({
				width : 200,  
				placeholder: '客户',
				tags: "true",
				allowClear: true,
				data:result
			});
		}
	}); */
	
});
$.ajax({
	url:"${ctx}/cust/getAgentList",
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
			debugger;
			$('#poolId').empty();
			$('#type').empty();
			$('#version').empty();
			/* $.ajax({
				type : "post",
				url : "${ctx}/ss/getAttrs",
				data: {"custId":$("#custId").val()},
				success:function(result){
					
					console.log(result);
			 	$("#lable1").text(result.attr1+":");
					$("#lable2").text(result.attr2+":"); 
					$("#hidden1").val(result.attrV1);
					$("#hidden2").val(result.attrV2);
				}
			}); */
			$.ajax({
				type : "post",
				url : "${ctx}/ss/getPoolList",
				data: {"custId":$("#custId").val()},
				success:function(result){
					
					console.log(result);
					var select=$("#poolId").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="data.pool" />',
						tags: "true",
						allowClear: true,
						data:result
					});				}
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





function check(){  
	  var goodsNameValue=window.document.getElementById("goods").value;  
	  var custIdValue=window.document.getElementById("custId").value;  
	  var startDateValue=window.document.getElementById("startDate").value;  
	  var endDateValue=window.document.getElementById("endDate").value;  
	if (goodsNameValue == "") {     	
	    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.name.of.the.commodity.is.not.empty' />!");
	    return false;  
	}
	
	if (custIdValue == "" || custIdValue == "-1") {     	
	    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.channel.customers.to.publish' />!");
	    return false;  
	}
	if(startDateValue == ""){
	    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.fill.in.the.effective.time' />");
	    return false;
	}
	
	if(endDateValue == ''){
	   layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.fill.in.the.expiration.date' />");
	   return false;
	}
	
	return true;  
} 

//清空
function clear(){
	$("#goodsName").text("");
	$("#pic").text("");
	$("#operators").text("");
	$("#goodsPrices").text("");
}

//保存
function save(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	var flag = check();
	if(flag == false){
		return ;
	}
	
	$.ajax({
        type:"POST",
        url:"${ctx}/goodsRelease/addGoodsRelease",
        data:$("form").serialize(),
		dataType : "json",
		cache : false,
		success : function(data) {
		  	window.parent.location.reload();
		  	parent.layer.close(index);
		},
		error : function(error) {
		}
	});
}
function toAlarm(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	var flag = check();
	if(flag == false){
		return ;
	}
	$.ajax({
        type:"POST",
        url:"${ctx}/goodsRelease/addGoodsRelease",
        data:$("form").serialize(),
		dataType : "json",
		cache : false,
		success : function(data) {
			window.parent.toAddAlarmRule();
			parent.layer.close(index);
			parent.location.reload();
		},
		error : function(error) {
		}
	}); 
	
	
	
}
</script>
</head>
<body>
	<div class="seconSec">
		<h1><fmt:message bundle='${pageScope.bundle}'  key='Release.products' /></h1>
		<form method="post" class="form form-horizontal" id="form"
			name="form-member-add">
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select name="goodsId" id="goods" class="input-text">
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="custId" name="channelGroupId" class="form-control select2"
							style="width: 200px;">
						</select>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Effective.start.time' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<input type="text" class="input-text" name="startDate"
							onclick="WdatePicker()" readonly="readonly" id="startDate" />
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.time' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<input type="text" class="input-text" name="endDate"
							onclick="WdatePicker()" readonly="readonly" id="endDate" />
					</div>
				</div>

			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Metering.period' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="releaseCycle"
						style="width: 200px; float: left;" /> <select id="unit"
						name="unit" class="form-control select2"
						style="width: 60px; float: right; margin-right: 40%;">
						<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='month' /></option>
						<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Day' /></option>
					</select>
				</div>

			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Release.price' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<input type="text" class="input-text" name="releasePrice"
							style="width: 200px;" /><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span>
					</div>

				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='data.pool' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="poolId" name="poolId" class="form-control select2"
							style="width: 200px;">
						</select>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><lable
								id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' />:</lable></span>
					</label>
					<div class="formControls langMl zpInput">
						<!-- <input type="text" class="input-text" name="groupAttrN1" /> -->
						<select id="type" name="groupAttrV1" class="form-control select2"
							style="width: 200px;">
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <!-- <span class="colorRed smallStar">*</span> -->
						<span class="font12"><lable id="lable2"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />:</lable></span>
					</label>
					<div class="formControls langMl zpInput">
						<!-- <input type="text" class="input-text" name="groupAttrV1" /> -->
						<select id="version" name="groupAttrV1" class="form-control select2"
							style="width: 200px;">

						</select>
					</div>
				</div>
			</div>
			<input type="hidden" id="hidden1" name="groupAttrN1"> 
			<input type="hidden" id="hidden2" name="groupAttrN2">
			<!-- 新增开始 -->
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='SMS.function' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="sms" name="sms" class="form-control select2"
							style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Opened' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Close' /></option>
							<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Receive.only' /></option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Voice.function' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="speechSounds" name="speechSounds"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Opened' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Close' /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Activation.grace.period' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="silentPeriod" name="silentPeriod"
							class="form-control select2" style="width: 200px;">
							<option value="1">1月</option>
							<option value="2">2月</option>
							<option value="3">3月</option>
							<option value="4">4月</option>
							<option value="5">5月</option>
							<option value="6">6月</option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Expiration.renew.mode' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="extensionModel" name="extensionModel"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Disable' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Automatic.renewal.of.the.original.tariff.plan' /></option>
							<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Expiration.rescheduling' /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Discount.level' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="discountLevel" name="discountLevel"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='District.Manager' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Sales.Department' /></option>
							<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='General.manager' /></option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Discount.rate' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<input type="text" class="input-text" name="discountRate"
							style="width: 200px;" /><span class="font12">%</span>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Are.all.standard.capital.elements' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="isAllNormPlan" name="isAllNormPlan"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
							<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Is.there.an.IMEI.binding' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="isBindImei" name="isBindImei"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
							<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Private.network' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="isPrivateNetwork" name="isPrivateNetwork"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
							<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12">APN<fmt:message bundle='${pageScope.bundle}'  key='Domain.name' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<input type="text" class="input-text" name="apnRealm"
							style="width: 200px;" />
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Is.the.default.tariff.plan' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<select id="isDefaultPlan" name="isDefaultPlan"
							class="form-control select2" style="width: 200px;">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
							<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
						</select>
					</div>
				</div>
				<div class="col-6">
					<label class="fl langWidth"> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Remark' />:</span>
					</label>
					<div class="formControls langMl zpInput">
						<!-- <input type="text" class="input-text" name="apnRealm" style="width:200px;" /> -->
						<textarea rows="3" cols="7" id="remark" name="remark"
							style="width: 200px;">
	        	</textarea>
					</div>
				</div>
			</div>

			<!-- 新增结束 -->

			<div class="col-12">
				<!-- 	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
	      </div> -->
				<div class="zpButton">
					<input class="btn btn-primary radius" type="button"
						onclick="toAlarm();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Save.and.add.alarms' />&nbsp;&nbsp;">
					<input class="btn btn-primary radius" type="button"
						onclick="save();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Preservation' />&nbsp;&nbsp;"> <input
						class="btn btn-default radius" type="reset"
						value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Reduction' />&nbsp;&nbsp;" onclick="clear();">
				</div>
			</div>
		</form>
	</div>
</body>
</html>