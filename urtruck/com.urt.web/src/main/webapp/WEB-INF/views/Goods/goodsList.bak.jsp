<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="page" uri="/WEB-INF/lib/pager-taglib.jar" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.display.page' /></title>
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
<script type="text/javascript">
function toadd() {
	window.location.href="<%=request.getContextPath() %>/goods/toAddGoods";
}
</script>
</head>
<body>
<div class="pd-20">
	<div class="mt-20">
<%-- 		<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="<%=request.getContextPath()%>/goods/toAddGoods" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>产品添加</a></span> 
		<span class="l">&nbsp;&nbsp;&nbsp;&nbsp;</span><span class="l"><a href="<%=request.getContextPath()%>/goodsRelease/toAddGoodRelease" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>产品发布</a></span> 
		</div> --%>
<%-- 
		<div class="fr pd-10">
			<a href="javascript:;" onclick="goodsRealse_add('发布新产品','${ctx}/goodsRelease/toAddGoodRelease','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					产品发布</a>
		</div> --%>
		<div class="fr pd-10">
			<a href="javascript:;" onclick="goods_add('<fmt:message bundle='${pageScope.bundle}'  key='Add.product' />','${ctx}/goods/toAddGoods','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					<fmt:message bundle='${pageScope.bundle}'  key='Product.add' /></a>
		</div>
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Product.picture' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Product.provider' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Product.type' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Effective.condition' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Original.price.(yuan)' /></th>				
					<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="goods" items="${goodslist }">
					<tr class="zpTable">
						<td>${goods.goodsName }</td>
						<td class="tabImg"><img src="${goods.goodsPic }" width="100%"></td>
						<td>${goods.operatorsName }</td>
						<td><c:if test="${goods.goodsType=='0'}"><fmt:message bundle='${pageScope.bundle}'  key='Monthly.package' /></c:if><c:if test="${goods.goodsType=='1'}"><fmt:message bundle='${pageScope.bundle}'  key='overlay.package' /></c:if><c:if test="${goods.goodsType=='2'}"><fmt:message bundle='${pageScope.bundle}'  key='Flexible.data.pool' /></c:if><c:if test="${goods.goodsType=='3'}"><fmt:message bundle='${pageScope.bundle}'  key='regular.data.pool' /></c:if><c:if test="${goods.goodsType=='4'}"><fmt:message bundle='${pageScope.bundle}'  key='Limited.batch' /></c:if></td>
						<td><c:if test="${goods.activeWay=='0'}"><fmt:message bundle='${pageScope.bundle}'  key='Effective.immediately' /></c:if><c:if test="${goods.activeWay=='1'}"><fmt:message bundle='${pageScope.bundle}'  key='in.force.at.next.period ' /></c:if><c:if test="${goods.activeWay=='2'}"><fmt:message bundle='${pageScope.bundle}'  key='The.application.comes.into.effect.immediately' /></c:if></td>
						<td>${goods.goodsPrices }</td>
						<td class="f-14 td-manage">
<%-- 						    <a href="<%=request.getContextPath()%>/goods/toAddGoodsInfo?goodsId=${goods.goodsId}">添加元素</a>
							<a href="<%=request.getContextPath()%>/goodsElement/findGoodsElementByGoodsId?goodsId=${goods.goodsId}">产品详情</a>
							<a href="<%=request.getContextPath()%>/goods/toUpdateGoods?goodsId=${goods.goodsId}">修改</a>
							<a href="<%=request.getContextPath()%>/goods/delGoods?goodsId=${goods.goodsId}">删除</a>		 --%>				    
						    <a title="<fmt:message bundle='${pageScope.bundle}'  key='Add.elements' />" href="javaScript:toAddElement(${goods.goodsId});" class="ml-5" ><fmt:message bundle='${pageScope.bundle}'  key='Add.elements' /></a>		
							<a title="<fmt:message bundle='${pageScope.bundle}'  key='Product.details' />" href="javaScript:showMessage(${goods.goodsId});" class="ml-5" ><fmt:message bundle='${pageScope.bundle}'  key='Product.details' /></a>							
						    <a title="<fmt:message bundle='${pageScope.bundle}'  key='Modify' />" href="javaScript:toUpdate(${goods.goodsId});" class="ml-5" ><fmt:message bundle='${pageScope.bundle}'  key='Modify' /></a>
						    <a title="<fmt:message bundle='${pageScope.bundle}'  key='Delete' />" href="javaScript:del(${goods.goodsId});" class="ml-5" ><fmt:message bundle='${pageScope.bundle}'  key='Delete' /></a>							
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
	"bLengthChange": false,
	"bPaginate": true,
	"bPaginate": true,
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,3]}// 制定列不参与排序
	]
});
//新增产品
function goods_add(title,url,w,h){
	layer_show(title,url,w,h);
}
//发布产品
function goodsRealse_add(title,url,w,h){
	layer_show(title,url,w,h);
}

//修改信息
function toUpdate(goodsId){
	if(goodsId!=""){
		var url = '${ctx}/goods/toUpdateGoods?goodsId='+goodsId;
		layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Modify information" />',url,'800','550');
	}
}
//删除信息
function del(goodsId){
	if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.that.you.want.to.delete.it' />")){
			if(goodsId!=""){	
				
				//var url = '${ctx}/goods/delGoods?goodsId='+goodsId;
				$.ajax({
			        type:"POST",
			        url:"${ctx}/goods/delGoods",
			        data:{'goodsId':goodsId},
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
//产品详情
function showMessage(goodsId){
	if(goodsId!=""){
		var url = '${ctx}/goodsElement/findGoodsElementByGoodsId?goodsId='+goodsId;
		layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Product.details" />',url,'800','550');
	}
}
//添加元素
function toAddElement(goodsId){
	if(goodsId!=""){
		var url = '${ctx}/goods/toAddGoodsInfo?goodsId='+goodsId;
		layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Add.elements" />',url,'800','550');
	}
}
</script> 
</body>
</html>