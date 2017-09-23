<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="page" uri="/WEB-INF/lib/pager-taglib.jar" %>
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
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<title><fmt:message bundle='${pageScope.bundle}'  key='List.of.goods' /></title>
</head>
<body>
<div class="pd-20">

	<div class="mt-20" style="margin-top:50px;">
		<button type="button" id="batchOrder" style="margin-bottom:10px;" class="btn btn-primary radius"><fmt:message bundle='${pageScope.bundle}'  key='submit.orders' /></button>
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th><input type="checkbox" name="" value=""></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.name' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.pictures' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.main.provider' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Price.(yuan)' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Charges.cycle' /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="s" items="${laoGoodsDtos }">
					<tr class="zpTable">
						<td><input type="checkbox" value="${s.goodsId},${s.goodsReleaseId}" name="box"></td>
						<td>${s.goodsName}</td>
						<td><img src="${s.goodsPic}" style="width:50px;height:25px;"/></td>
						<td>${s.operatorsName}</td>
						<td>${s.goodsPrices}</td>
						<td>${s.releaseCycle}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
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
		  {"orderable":false,"aTargets":[0,2]}// 制定列不参与排序
		]
	});
	$("#batchOrder").bind("click",function(){
		
		var goodsId = $("input[name='box']:checked").attr("value")
		$.ajax({
			type: "GET",
		    async: false,
		    dataType:"json",
		    url: "${ctx}/laouser/orderSubmit?goodsId=" + goodsId,
		    success:function(msg){
		    	/* if(msg.success == true){
		    		alert('订购成功');
		    	}else{
		    		alert('订购失败');
		    	} */
		    	alert("<fmt:message bundle='${pageScope.bundle}'  key='ordered.sucessfully' />"+msg.success+",<fmt:message bundle='${pageScope.bundle}'  key='ordered.failed' />"+msg.fail);
		    },
		    error:function(){
		    	alert("<fmt:message bundle='${pageScope.bundle}'  key='ordered.failed' />");
		    }
		});
	});

</script> 
</body>
</html>