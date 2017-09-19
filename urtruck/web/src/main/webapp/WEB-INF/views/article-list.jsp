<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title><fmt:message bundle='${pageScope.bundle}'  key='Information.list' /></title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i>
<fmt:message bundle='${pageScope.bundle}'  key='home.page' />
<span class="c-gray en">&gt;</span><fmt:message bundle='${pageScope.bundle}'  key='Information.management' />
<span class="c-gray en">&gt;</span><fmt:message bundle='${pageScope.bundle}'  key='Information.list' /> 
<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
<i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-c"> <span class="select-box inline">
		<select name="" class="select">
			<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='All.categories' /></option>
			<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='categories.1' /></option>
			<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='categories.2' /></option>
		</select>
		</span><fmt:message bundle='${pageScope.bundle}'  key='Date.range' />：
		<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'logmax\')||\'%y-%M-%d\'}'})" id="logmin" class="input-text Wdate" style="width:120px;">
		-
		<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'logmin\')}',maxDate:'%y-%M-%d'})" id="logmax" class="input-text Wdate" style="width:120px;">
		<input type="text" name="" id="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Information.name' />" style="width:250px" class="input-text">
		<button name="" id="" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Search.information' /></button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="l"><a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
		<i class="Hui-iconfont">&#xe6e2;</i> 
		<fmt:message bundle='${pageScope.bundle}'  key='Batch.delete' />
		</a> <a class="btn btn-primary radius" onclick="article_add(<fmt:message bundle='${pageScope.bundle}'  key='Add.information' />,'article-add.html')" href="javascript:;">
		<i class="Hui-iconfont">&#xe600;</i> 
		<fmt:message bundle='${pageScope.bundle}'  key='Add.information' />
		</a></span> <span class="r">
		<fmt:message bundle='${pageScope.bundle}'  key='Common.data' />：
		<strong>54</strong><fmt:message bundle='${pageScope.bundle}'  key='Article' /></span> 
	</div>
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover table-sort">
			<thead>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					<th width="80">ID</th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Title' /></th>
					<th width="80"><fmt:message bundle='${pageScope.bundle}'  key='classification' /></th>
					<th width="80"><fmt:message bundle='${pageScope.bundle}'  key='sources' /></th>
					<th width="120"><fmt:message bundle='${pageScope.bundle}'  key='update.time' /></th>
					<th width="75"><fmt:message bundle='${pageScope.bundle}'  key='Browse.times' /></th>
					<th width="60"><fmt:message bundle='${pageScope.bundle}'  key='release.status' /></th>
					<th width="120"><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
				</tr>
			</thead>
			<tbody>
				<tr class="text-c">
					<td><input type="checkbox" value="" name=""></td>
					<td>10001</td>
					<td class="text-l"><u style="cursor:pointer" class="text-primary" onClick="article_edit('查看','article-zhang.html','10001')" title="查看"><fmt:message bundle='${pageScope.bundle}'  key='Information.headlines' /></u></td>
					<td><fmt:message bundle='${pageScope.bundle}'  key='Industry.dynamics' /></td>
					<td>H-ui</td>
					<td>2014-6-11 11:11:42</td>
					<td>21212</td>
					<td class="td-status"><span class="label label-success radius"><fmt:message bundle='${pageScope.bundle}'  key='Already.released' /></span></td>
					<td class="f-14 td-manage"><a style="text-decoration:none" onClick="article_stop(this,'10001')" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a> <a style="text-decoration:none" class="ml-5" onClick="article_edit('资讯编辑','article-add.html','10001')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a> <a style="text-decoration:none" class="ml-5" onClick="article_del(this,'10001')" href="javascript:;" title="<fmt:message bundle='${pageScope.bundle}'  key='Delete' />"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</tr>
				<tr class="text-c">
					<td><input type="checkbox" value="" name=""></td>
					<td>10002</td>
					<td class="text-l"><u style="cursor:pointer" class="text-primary" onClick="article_edit('查看','article-zhang.html','10002')" title="查看"><fmt:message bundle='${pageScope.bundle}'  key='Information.headlines' /></u></td>
					<td><fmt:message bundle='${pageScope.bundle}'  key='Industry.dynamics' /></td>
					<td>H-ui</td>
					<td>2014-6-11 11:11:42</td>
					<td>21212</td>
					<td class="td-status"><span class="label label-success radius"><fmt:message bundle='${pageScope.bundle}'  key='draft' /></span></td>
					<td class="f-14 td-manage"><a style="text-decoration:none" onClick="article_shenhe(this,'10001')" href="javascript:;" title="审核"><fmt:message bundle='${pageScope.bundle}'  key='Check' /></a> <a style="text-decoration:none" class="ml-5" onClick="article_edit('资讯编辑','article-add.html','10001')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a> <a style="text-decoration:none" class="ml-5" onClick="article_del(this,'10001')" href="javascript:;" title="<fmt:message bundle='${pageScope.bundle}'  key='Delete' />"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</tr>
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
$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,8]}// 制定列不参与排序
	]
});

/*资讯-添加*/
function article_add(title,url,w,h){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*资讯-编辑*/
function article_edit(title,url,id,w,h){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*资讯-删除*/
function article_del(obj,id){
	layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.delete.it' />",function(index){
		$(obj).parents("tr").remove();
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Deleted' />!",1);
	});
}
/*资讯-审核*/
function article_shenhe(obj,id){
	layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Check.paper' />？", {
		btn: ["<fmt:message bundle='${pageScope.bundle}'  key='passed' />","<fmt:message bundle='${pageScope.bundle}'  key='Not.passed' />"], 
		shade: false
	},
	function(){
		$(obj).parents("tr").find(".td-manage").prepend('<a class="c-primary" onClick="article_start(this,id)" href="javascript:;" title="申请上线"><fmt:message bundle='${pageScope.bundle}'  key='Application.on-line' /></a>');
		$(obj).parents("tr").find(".td-status").html("<span class='label label-success radius'><fmt:message bundle='${pageScope.bundle}'  key='Already.released' /></span>");
		$(obj).remove();
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Already.released' />", {icon:6,time:1000});
	},
	function(){
		$(obj).parents("tr").find(".td-manage").prepend('<a class="c-primary" onClick="article_shenqing(this,id)" href="javascript:;" title="申请上线"><fmt:message bundle='${pageScope.bundle}'  key='Application.on-line' /></a>');
		$(obj).parents("tr").find(".td-status").html("<span class='label label-danger radius'><fmt:message bundle='${pageScope.bundle}'  key='Not.passed' /></span>");
		$(obj).remove();
    	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Not.passed' />", {icon:5,time:1000});
	});	
}
/*资讯-下架*/
function article_stop(obj,id){
	layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.withdraw.products' />？",function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="article_start(this,id)" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
		$(obj).parents("tr").find(".td-status").html("<span class='label label-defaunt radius'><fmt:message bundle='${pageScope.bundle}'  key='Removed' /></span>");
		$(obj).remove();
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Removed' />!",{icon: 5,time:1000});
	});
}

/*资讯-发布*/
function article_start(obj,id){
	layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.publish.it' />？",function(index){
		$(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="article_stop(this,id)" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
		$(obj).parents("tr").find(".td-status").html("<span class='label label-success radius'><fmt:message bundle='${pageScope.bundle}'  key='Already.released' /></span>");
		$(obj).remove();
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Already.released' />!",{icon: 6,time:1000});
	});
}
/*资讯-申请上线*/
function article_shenqing(obj,id){
	$(obj).parents("tr").find(".td-status").html("<span class='label label-default radius'><fmt:message bundle='${pageScope.bundle}'  key='checking' /></span>");
	$(obj).parents("tr").find(".td-manage").html("");
	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.patiently.wait.for.checking' />!", {icon: 1,time:2000});
}

</script> 
</body>
</html>