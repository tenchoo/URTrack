<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.element.add.page' /></title>
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />

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
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
$(function(){	
$("#elementType").change(function(){
	$("#originalId").text("");
	$("#elementType").text("");
	var elementT=$("#elementType").val();
	if(elementT==0){
		$("#elementType").
		$.post("<%=request.getContextPath() %>/discont/findDiscont",
				function(data){
				var discontlist=eval(data);
				for ( var i = 0; i < discontlist.length; i++) {
					$("#originalId").append("<option value='"+discontlist[i].discontId+"'>"+discontlist[i].discontName+"</option>");
				}
		},"json");		
	}else{
		$.post("<%=request.getContextPath() %>/operatorPlan/findOperatorPlan",
				function(data){
				var operatorPlanList = eval(data);
				for ( var i = 0; i < operatorPlanList.length; i++) {
					$("#originalId").append("<option value='"+operatorPlanList[i].planId+"'>"+operatorPlanList[i].planName+"</option>");
				}
		},"json");	
	};
});

});

</script>
</head>
<body>
<form action="<%=request.getContextPath()%>/goodsElement/editGoodsElement" method="post" class="form form-horizontal" id="form1" name="form-member-add" >
	  <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Commodity.name' />:</label>
	      <div class="formControls col-2" style="width: 80%">
	        <input type="hidden"  class="input-text" value="${goods.goodsId }" id="roleName" name="goodsId" >
	        <input type="text"  class="input-text" value="${goods.goodsName }"  >
	      </div>
      </div>
	  <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Element.type' />:</label>
	      <div class="formControls col-2" style="width: 80%">
	      	<input type="hidden" name=""/>
	        	<select name="elementType" id="elementType"  class="input-text" >
 					<option >===<fmt:message bundle='${pageScope.bundle}'  key='Select.the.element.type' />===</option>
<%--  						<c:if test="${goodsElement.elementType == 0 }">		
						<option selected="selected">优惠套餐包</option>
						</c:if>
						<c:if test="${goodsElement.elementType == 1 }">		
						<option selected="selected">原始套餐包</option>
						</c:if> --%>
 					<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='Discount.package' /></option>
					<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Original.package' /></option>
				</select>
	      </div>
      </div>
	  <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Selected.package.name' />:</label>
	      <div class="formControls col-2" style="width: 80%">
	        	<select name="originalId" id="originalId"   class="input-text" >
				</select>
	      </div>
      </div>      
      <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Position.of.commodity' />:</label>
	      <div class="formControls col-2" style="width: 80%">
	        	<input type="text" class="input-text" name="goodsIndex"  value="${goodsElement.goodsIndex }"/>
	      </div>
      </div>
            <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Come.into.effect.date' />:</label>
	      <div class="formControls col-2" style="width: 80%">	      
	        	<input type="text" class="input-text" name="startDate" onclick="WdatePicker()" readonly="readonly" value="<fmt:formatDate  value="${goodsElement.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	      </div>
      </div>
      
            <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.Date' />:</label>
	      <div class="formControls col-2" style="width: 80%">
	        	<input type="text" class="input-text" name="endDate" onclick="WdatePicker()" readonly="readonly" value="<fmt:formatDate value="${goodsElement.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
	      </div>
      </div>
      
            <div class="row cl">
	      <label class="form-label col-2" style="width: 10%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Access.method' />:</label>
	      <div class="formControls col-2" style="width: 80%">	       
				<input type="radio" name="packageType" value="1" ${goodsElement.packageType=="1"?"checked='checked'":""}/><fmt:message bundle='${pageScope.bundle}'  key='According.to.index.postponed' />
				<input type="radio" name="packageType" value="2" ${goodsElement.packageType=="2"?"checked='checked'":""}/><fmt:message bundle='${pageScope.bundle}'  key='Consistent.with.goods.start.time' />
	      </div>
      </div>
      
	  <div class="row cl">
	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
	      </div>
      </div>
</form>
</body>
</html>


