<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.element.add.page' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">

<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
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
	src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
$(function(){	
$("#planClassifyDiv").css("display","none");
$("#elementType").change(function(){
	$("#originalId").text("");
	var elementT=$("#elementType").val();
	if(elementT==0){
		$("#planClassifyDiv").css("display","none");
		$("#planClassifyDiv").css("display","none");
		$.post("<%=request.getContextPath() %>/discont/findDiscont",
				function(data){
				var discontlist=eval(data);
				for ( var i = 0; i < discontlist.length; i++) {
					$("#originalId").append("<option value='"+discontlist[i].discontId+"'>"+discontlist[i].discontName+"</option>");
				}
		},"json");		
	}else{
		$("#planClassifyDiv").css("display","block");
	};
});
$("#planClassify").change(function(){
	$("#originalId").text("");
	var planClassify=$("#planClassify").val();
	$("#planClassifyDiv").css("display","block");
	var operatorsId = $("#operatorsId").val();
	var activeWay=$("#activeWay").val();
	var goodsType=$("#goodsType").val();
	$.post("<%=request.getContextPath() %>/operatorPlan/findOperatorPlan",{"operators":operatorsId,"enableTag":activeWay,"planType":goodsType,"planClassify":planClassify},				
			function(data){
			var operatorPlanList = eval(data);
			for ( var i = 0; i < operatorPlanList.length; i++) {
				$("#originalId").append("<option value='"+operatorPlanList[i].planId+"'>"+operatorPlanList[i].planName+"</option>");
			}
	},"json");
	});

});

	function check(){  
		  var elementTypeValue=window.document.getElementById("elementType").value;  
		  var planClassifyValue=window.document.getElementById("planClassify").value;  
		  var originalIdValue=window.document.getElementById("originalId").value;  
		  var goodsIndexValue=window.document.getElementById("goodsIndex").value; 
		  var startDateValue=window.document.getElementById("startDate").value;  
		  var endDateValue=window.document.getElementById("endDate").value;  	  
			if (elementTypeValue == "") {     	
			    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Select.the.element.type' />");
			    return false;  
			}else if(elementTypeValue == "apn1" || elementTypeValue == "apn2"){
				if(planClassifyValue==""){
					layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.package.type' />");
				    return false;  
				}
			}
			if (originalIdValue == "") {     	
			    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.selected.package.name.cannot.be.empty' />");
			    return false;  
			}
			if(goodsIndexValue == ""){
			    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.position.of.the.goods.cannot.be.empty' />");
			    return false;
			}
			
			if(startDateValue == ''){
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
		$("#planClassifyDiv").css("display","none");
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
	        url:"${ctx}/goodsElement/editGoodsElement",
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
	var data=new Array();
	var t;
	function add(){
		var flag = check();
		if(flag == false){
			return ;
		}
		 var json={
	         "name":       $('#a').val(),
	         "elementTypeText":   $('#elementType option:selected').text(),
	         "elementType":   $('#elementType option:selected').val(),
	         "planClassifyText":   $('#planClassify option:selected').text(),
	         "planClassify":   $('#planClassify option:selected').val(),
	         "originalText":    $('#originalId option:selected').text(),
	         "originalId":    $('#originalId option:selected').val(),
	         "goodsIndex":    $('#goodsIndex').val(),
	         "startDate":     $('#startDate').val(),
	         "endDate":     $('#endDate').val(),
	         "goodsId":  $('#goodsId').val(),
	         "packageTypeText":      $('input:radio:checked').text(),
	         "packageType":      $('input:radio:checked').val()
	         }
		data[data.length]=json;
		init();
	}
	
	function init(){
		$("#example").empty();
		$("#example").append("<thead><tr class='zpTable' style='height:30px;line-height:30px;'><th><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Element.type' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='package.type' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Selected.package.name' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Goods.position' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Effective.start.time' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.time' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Access.method' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th></tr></thead>")
		
		t= $('#example').DataTable({
		    data: data,
		    destroy:true,
		    bLengthChange : false,
	        bFilter : false,
	        bSort : false,
	        searching: false,
	        paging:false,
	        bInfo:false,
	        columns: [
	            { data: 'name' },
	            { data: 'elementTypeText' },
	            { data: 'planClassifyText' },
	            { data: 'originalText' },
	            { data: 'goodsIndex' },
	            { data: 'startDate' },
	            { data: 'endDate' },
	            { data: 'packageTypeText' },
	            {
	            	"targets" : 7,
	            	"data" : null,
	            	"render" : function(data,type,row,full) {
	            	var html='<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('+ full.row + ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></a> '
	            	return html;
	            	}
	            }
	        ]
	    });
	}

	function del(index){
		data.splice(index, 1);
		init();
	}
	function saveAll(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$.ajax({
	        type:"POST",
	        url:"${ctx}/goodsElement/inserts",
	        data:{elements:JSON.stringify(data)},
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
		<h1><fmt:message bundle='${pageScope.bundle}'  key='Product.element.add.page' /></h1>
		<form method="post" class="form form-horizontal" id="form1"
			name="form-member-add">



			<div class="col-6 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="hidden" id="goodsId" class="input-text"
						value="${goods.goodsId }" name="goodsId"> <input
						type="text" class="input-text" value="${goods.goodsName }" id="a">
					<input type="hidden" value="${goods.operatorsId }" id="operatorsId" />
					<input type="hidden" value="${goods.goodsType }" id="goodsType" />
					<input type="hidden" value="${goods.activeWay }" id="activeWay" />
				</div>
			</div>
			<div class="col-6 mt20" id="b">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Element.type' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<select name="elementType" id="elementType" class="input-text">
						<option value="0">==<fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.package' />==</option>
						<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='Discount.package' /></option>
						<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Original.package' /></option>
					</select>
				</div>
			</div>
			<div class="col-12 mt20" id="planClassifyDiv">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='package.type' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<select name="planClassify" id="planClassify" class="input-text">
						<option value="0">==<fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.package' />==</option>
						<option value="apn1">apn1</option>
						<option value="apn2">apn2</option>
						<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='voice.package' /></option>
						<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='SMS.message.package' /></option>
					</select>
				</div>
			</div>
			<div class="col-6 mt20" id="c">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Selected.package.name' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<select name="originalId" id="originalId" class="input-text">
					</select>
				</div>
			</div>
			<div class="col-6 mt20" id="d">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Position.of.product' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="goodsIndex"
						id="goodsIndex" />
				</div>
			</div>
			<div class="col-6 mt20" id="e">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Come.into.effect.date' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="startDate"
						id="startDate" onclick="WdatePicker()" readonly="readonly" />
				</div>
			</div>

			<div class="col-6 mt20" id="f">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.Date' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="endDate" id="endDate"
						onclick="WdatePicker()" readonly="readonly" />
				</div>
			</div>

			<div class="col-12 mt20" id="g">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Access.method' />:</span>
				</label>
				<div class="formControls zpInput oh">
					<div class="col-6" style="margin-top: 10px;">
						<input type="radio" name="packageType" value="1" checked="checked"
							class="fl" style="margin-top: 3px; margin-left: 10px;" />
						&nbsp;&nbsp;&nbsp;&nbsp;<span class="font12 fl"
							style="line-height: 18px;"><fmt:message bundle='${pageScope.bundle}'  key='According.to.index.postponed' /></span>
					</div>
					<div class="col-6" style="margin-top: 10px;">
						<input type="radio" name="packageType" value="2" class="fl"
							style="margin-top: 3px;" /> &nbsp;&nbsp;&nbsp;&nbsp;<span
							class="font12 fl" style="line-height: 18px;"><fmt:message bundle='${pageScope.bundle}'  key='Consistent.with.goods.start.time' /></span>
					</div>
				</div>
			</div>

			<div class="row cl">
				<!-- 	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
	      </div> -->

				<div class="zpButton">
					<input class="btn btn-primary" type="button"onclick="save();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;">
					<input class="btn btn-primary radius" type="button" onclick="saveAll();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Batch.operation' />&nbsp;&nbsp;"> 
					<input class="btn btn-primary radius" type="reset" onclick="clear();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='undo' />&nbsp;&nbsp;">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='add' />&nbsp;&nbsp;" onclick="add();">
				</div>
			</div>
			<div style="height: 30px;"></div>
			<table id="example">
				<!-- <thead>
                    <tr class="zpTable">
                        <th>商品名称</th>
                        <th>元素类型</th>
                        <th>所选套餐名称</th>
                        <th>商品位置</th>
                        <th>生效开始时间</th>
                        <th>生效截止时间</th>
                        <th>接入方式</th>
                        <th>操作</th>
                    </tr>
                </thead> -->
			</table>
		</form>
	</div>
</body>
</html>

