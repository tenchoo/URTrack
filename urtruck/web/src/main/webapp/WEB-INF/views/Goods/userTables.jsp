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
	<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<title><fmt:message bundle='${pageScope.bundle}'  key='data.card.list' /></title>
</head>
<body>
<div class="pd-20">
	<div class="mt-20">
		<button type="button" id="batchOrder" style="margin-bottom:10px;"><fmt:message bundle='${pageScope.bundle}'  key='Next.step' /></button>
		<input id="custId" type="hidden" value="${custId}">
		<input id="operatorsId" type="hidden" value="${operatorsId}">
		<input id="value1" type="hidden" value="${value1}">
		<input id="value2" type="hidden" value="${value2}">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th><input type="checkbox" name="" value=""></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='User.identification' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Channel.customer' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Use.customer' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Affiliated.operators' /></th>
					<th>iccid</th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='User.service.status' /></th>
				</tr>
			</thead>
			<tbody >
			    <c:forEach var="s" items="${laoUsers }">
					<tr class="text-c">
						<td><input type="checkbox" value="" name="box"></td>
						<td>${s.userId}</td>
						<td>${s.channelCustName}</td>
						<td>${s.custName}</td>
						<td>${s.operatorsName}</td>
						<td>${s.iccid}</td>
						<td>${s.stateCode}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="${ctx}/static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
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
		  {"orderable":false,"aTargets":[0,3]}// 制定列不参与排序
		],
		language: {
	        "sProcessing": "处理中...",
	        "sLengthMenu": "显示 _MENU_ 项结果",
	        "sZeroRecords": "没有匹配结果",
	        "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	        "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	        "sInfoPostFix": "",
	        "sSearch": "搜索:",
	        "sUrl": "",
	        "sEmptyTable": "表中数据为空",
	        "sLoadingRecords": "载入中...",
	        "sInfoThousands": ",",
	        "oPaginate": {
	            "sFirst": "首页",
	            "sPrevious": "上页",
	            "sNext": "下页",
	            "sLast": "末页"
	        },
	        "oAria": {
	            "sSortAscending": ": 以升序排列此列",
	            "sSortDescending": ": 以降序排列此列"
	        }
	    }
	});
	$.ajax({
		type: "GET",
	    async: false,
	    dataType:"json",
	    url: "${ctx}/operators/findOperators",
	    success:function(msg){
	    	$("#operatorsId").append('<option value="' + msg[0].operatorsId + '">' + msg[0].operatorsName + '</option>');
	    }
	});
	
	/*批量选购*/
	$("#batchOrder").bind("click",function(){
		var boxIds = new Array();
		var custId = $("#custId").val();
		var operatorsId = $("#operatorsId").val();
		var value1 = $("#value1").val();
		var value2 = $("#value2").val();
		
		alert("custId    "+custId);
		alert("operatorsId    "+operatorsId);
		alert("value1    "+value1);
		alert("value2    "+value2);
		if($("input[name='box']:checked").size() != 0){
			$("input[name='box']:checked").each(function(){
				var iccid = $(this).parent().nextAll().eq(4).text();
				boxIds.push(iccid.toString());
			});
			$.ajax({
			    type: "GET",
			    async: false,
			    dataType:"json",
			    url: "${ctx}/laouser/batchorder",
			    data:{
			    	"boxIds":boxIds,
			    	"custId":custId,
			    	"operatorsId":operatorsId,
			    	"value1":value1,
			    	"value2":value2
			    },
			    success:function(msg){
			    	if(msg.success == true){
			    		window.location.href = ("${ctx}/laouser/order");
			    	}else{
			    		alert("<fmt:message bundle='${pageScope.bundle}'  key='Shopping.failure' /> ！");
			    	}
			    }
			});
		}else{
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.card.for.the.purchase' />！")
		}
	});
</script>
</body>
</html>