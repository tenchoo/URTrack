<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.element.display.page' /></title>
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
	
<script type="text/javascript" src="<%=request.getContextPath() %>/static/jquery/jquery-1.9.1.min.js"></script>
<style type="text/css">
	.dataTables_wrapper .dataTables_paginate{
		padding-bottom:20px;
	}
</style>
</head>
<body>
<div class="pd-20">
	<div class="mt-20">	
		<form action="<%=request.getContextPath()%>/goodsElement/deleteGoodsElement" method="post" id="form1">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th><fmt:message bundle='${pageScope.bundle}'  key='Name.of.commodity' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Element.type' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Element.name' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Access.method' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Position.of.commodity' /></th>	
					<th><fmt:message bundle='${pageScope.bundle}'  key='Come.into.effect.date' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Effective.end.Date' /></th>	
					<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="element" items="${goodsElementList }">
					<tr class="zpTable">
						<td>${goodsDto.goodsName }</td>
						<c:if test="${element.elementType==0 }">		
						<td><fmt:message bundle='${pageScope.bundle}'  key='Discount.package' /></td>
						</c:if>
						<c:if test="${element.elementType==1 }">
						<td><fmt:message bundle='${pageScope.bundle}'  key='Original.package' /></td>
						</c:if>
						<td>${element.elementName}</td>
						<c:if test="${element.packageType==1 }">		
						<td><fmt:message bundle='${pageScope.bundle}'  key='According.to.index.postponed' /></td>
						</c:if>
						<c:if test="${element.elementType==2 }">
						<td><fmt:message bundle='${pageScope.bundle}'  key='Consistent.with.goods.start.time' /></td>
						</c:if>
						<td>${element.goodsIndex }</td>
						<td><fmt:formatDate  value="${element.startDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${element.endDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>		
						<td>
						<a href="<%=request.getContextPath()%>/goods/toAddGoodsInfo?goodsId=${element.goodsId}"><fmt:message bundle='${pageScope.bundle}'  key='add' /></a>
						<a title="<fmt:message bundle='${pageScope.bundle}'  key='Delete.information' />" href="javaScript:del(${element.elementId});" class="ml-5" ><fmt:message bundle='${pageScope.bundle}'  key='Delete' /></a>

						</td>						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/H-ui.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/static/js/H-ui.admin.js"></script>
<script type="text/javascript">

$.extend( $.fn.dataTable.defaults, {
    searching: false,
    ordering:  false
} );
$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"bPaginate": true,
	"bLengthChange": false,
	"sLengthMenu": 10,
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,3]}// 制定列不参与排序
	]
});

 //删除
/* 	$().ready(function(){		
		$("#selectAll").click(function(){
			$(".elementId").prop("checked",$(this).prop("checked"));	
		});
		$(".del").click(function(){
			if(confirm("确定要删除选中的吗？")){
				$("#form1").submit();
			}
		});
	});
 */
 
	//删除信息
	function del(elementId){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.that.you.want.to.delete.it' />")){
			
				if(elementId!=""){	
					alert(elementId);
					$.ajax({
						
				        type:"POST",
				        url:"${ctx}/goodsElement/deleteGoodsElement",
				        data:{"elementId":elementId},
						dataType : "json",
						success : function(data) {
							layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Delete.successfully' />");
							location.reload();
						},
						error : function(error) {
						}
					});
				}
			  }
			  else{
			   return;
		 }
	}
</script>
</body>
</html>




