<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript">
$.ajax({
	url:"${ctx}/cust/getAgentList",
	data:{},
	success:function(result){
		var custSelect=$("#custId").select2({
			width : 200,  
			placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Customer" />',
			tags: "true",
			allowClear: true,
			data:result
		});
		var arrayObj = new Array();
		arrayObj[0]=$("#hiddenCustId").val();
		custSelect.val(arrayObj).trigger('change');
		if(arrayObj[0]!=null && arrayObj[0]!=''){
			changeCust();
		}

		$("#custId").change(function() {
			$('#type').empty();
			$('#version').empty();
			$('#poolId').empty();
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
				
					var typeSelect=$("#type").select2({
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
								var versionSelect=$("#version").select2({
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
function changeCust(){
	$('#type').empty();
	$('#poolId').empty();
	$('#version').empty();
	$.ajax({
		type : "post",
		url : "${ctx}/ss/getPoolList",
		data: {"custId":$("#custId").val()},
		success:function(result){
			
			console.log(result);
			var poolSelect=$("#poolId").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="data.pool" />',
				tags: "true",
				allowClear: true,
				data:result
			});	
			arrayObj[0]=$("#hiddenPoolId").val();
			poolSelect.val(arrayObj).trigger('change');
		}
	});
	$.ajax({
		url:"${ctx}/ss/getTypeList",
		data:{"custId":$("#custId").val()},
		success:function(result){
			var typeSelect=$("#type").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Type" />',
				tags: "true",
				allowClear: true,
				data:result
			});
			$.ajax({
				type : "post",
				url : "${ctx}/ss/getAttrs",
				data: {"custId":$("#custId").val()},
				success:function(result){
					console.log(result);
					$("#lable1").text(result.attr1+":");
					$("#lable2").text(result.attr2+":");
				}
			});
			var arrayObj = new Array();
			arrayObj[0]=$("#hiddenTypeId").val();
			typeSelect.val(arrayObj).trigger('change');
			if(arrayObj[0]!=null && arrayObj[0]!=''){
				changeType();
			}
		}
	});
	
}
function changeType(){
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
			var versionSelect=$("#version").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
				tags: "true",
				allowClear: true,
				data:result
			});
			var arrayObj = new Array();
			arrayObj[0]=$("#hiddenVersionId").val();
			versionSelect.val(arrayObj).trigger('change');
		}
	});
}



$(function(){
	$.post("<%=request.getContextPath()%>/goods/findGoods",
			function(data){
			var goodsList=eval(data);
			for ( var i = 0; i < goodsList.length; i++) {
				if('${goodsRelease.goodsId}'==goodsList[i].goodsId){
					$("#goods").append("<option value='"+goodsList[i].goodsId+"'selected='selected'>"+goodsList[i].goodsName+"</option>");
				}else{
					$("#goods").append("<option value='"+goodsList[i].goodsId+"'>"+goodsList[i].goodsName+"</option>");
				}
			}
		},"json");
	
	$.post("<%=request.getContextPath() %>/customer/findCustomer",
			function(data){
			var CustomerList = eval(data);
			for ( var i = 0; i < CustomerList.length; i++) {
				$("#channelGroup").append("<option value='"+CustomerList[i].custId+"'>"+CustomerList[i].custName+"</option>");
			}
	},"json");	
	

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
	
	if (custIdValue == "" || custIdValue == "<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />") {     	
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


//保存
function update(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	var flag = check();
	if(flag == false){
		return ;
	}
	
	$.ajax({
        type:"POST",
        url:"${ctx}/goodsRelease/updateGoodsRelease",
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
</script>
</head>
<body>
<div class="seconSec">
<h1><fmt:message bundle='${pageScope.bundle}'  key='Product.modification' /></h1>
<form  method="post" class="form form-horizontal" id="form" name="form-member-add">

      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
	      	    <span class="colorRed smallStar">*</span>
				<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Commodity.name' />:</span>	
	      </label>
	      <div class="formControls zpInput">
	      		<input type="hidden" id="hiddenCustId" value="${goodsRelease.channelGroupId }"/>
	      		<input type="hidden" id="hiddenTypeId" value="${goodsRelease.groupAttrV1 }"/>
	      		<input type="hidden" id="hiddenVersionId" value="${goodsRelease.groupAttrV2 }"/>
	      		<input type="hidden" id="hiddenPoolId" value="${goodsRelease.poolId }"/>
	        	<select name="goodsId" id="goods"  class="input-text"  >
				</select>
	      </div>
      </div>
      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
	      	    <span class="colorRed smallStar">*</span>
				<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</span>	
	      </label>
	      <div class="formControls zpInput">
				<select id="custId" name="custId" class="form-control select2" style="width:200px;">
				</select>
				
	      </div>
      </div>
      <div class="col-12 mt20">
	      <label class="fl langWidth">
	      	    <!-- <span class="colorRed smallStar">*</span> -->
				<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='data.pool' />:</span>
	      </label>
	      <div class="formControls langMl zpInput">
				<select id="poolId" name="poolId" class="form-control select2" style="width:200px;">
				</select>
	      </div>
      </div>
      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
      	  	    <!-- <span class="colorRed smallStar">*</span> -->
				<span class="font12"><lable id="lable1"></lable></span>
		  </label>
	      <div class="formControls zpInput">
	        	<!-- <input type="text" class="input-text" name="groupAttrN1" /> -->
	        	<select id="type" name="type" class="form-control select2" style="width:200px;">
	        	</select>
	      </div>
      </div>       
      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
      	  	    <span class="colorRed smallStar">*</span>
				<span class="font12"><lable id="lable2"></lable></span>
		 </label>
	      <div class="formControls zpInput">
	        	<!-- <input type="text" class="input-text" name="groupAttrV1" /> -->
	        	<select id="version" name="version" class="form-control select2" style="width:200px;">
	        	</select>
	      </div>
      </div>          
      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
	      	    <span class="colorRed smallStar">*</span>
				<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Effective.start.time' />:</span>	  
		  </label>
	      <div class="formControls zpInput">
	        	<input type="text" class="input-text" name="startDate" id="startDate" onclick="WdatePicker()" readonly="readonly" value="<fmt:formatDate  value="${goodsRelease.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	      		
	      </div>
      </div>      
      <div class="col-6 mt20 zpHeight">
	      <label class="fl labelWidth control-label text-left">
	      	    <span class="colorRed smallStar">*</span>
				<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.time' />:</span>	
		 </label>
	      <div class="formControls zpInput">
	        	<input type="text" class="input-text" name="endDate" id="endDate" onclick="WdatePicker()" readonly="readonly" value="<fmt:formatDate  value="${goodsRelease.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>

	      </div>
      </div>            
	  <div class="col-12">
	       <div class="zpButton">
					<input class="btn btn-primary radius" type="button"
						onclick="update();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;"> 
		  </div>
      </div>
</form>
</div>
</body>
</html>
