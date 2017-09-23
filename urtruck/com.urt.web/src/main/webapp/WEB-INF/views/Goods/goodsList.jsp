<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
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
	
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
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
<script type="text/javascript">
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
		layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Modify.information" />',url,'800','550');
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
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>goods/list", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
				{
					"targets" : [ 0 ],
					"data" : "goodsName"
				},
				{
					"targets" : [ 1 ],
					"data" : "goodsId"
				},
				
				{
					"targets" : [ 2 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var date = "<img class='tabImg' src='"+data.goodsPic+"' width='100%'>";
						return date;
					} 
				},
				{
					
					"targets" : [ 3 ],
					"data" : "operatorsName"
				},
				{
					"targets" : [ 4 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var str = "";
						if (data.goodsType == 0) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Monthly.package' />"
						} else if(data.goodsType == 1) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='overlay.package' />"
						}else if(data.goodsType == 2) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Flexible.data.pool' />"
						}else if(data.goodsType == 3) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='regular.data.pool' />"
						}else if(data.goodsType == 4) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Limited.batch' />"
						}else if(data.goodsType == 5) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Unlimited.monthly.use' />"
						}else if(data.goodsType == 6) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Limited.monthly.use' />"
						}else if(data.goodsType == 7) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='test.product' />"
						}
						return str;
					}
				},
				{
					"targets" : [ 5 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						var str = "";
						if (data.activeWay == 0) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='Effective.immediately' />"
						} else if(data.activeWay == 1) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='in.force.at.next.period ' />"
						}else if(data.activeWay == 2) {
							str = "<fmt:message bundle='${pageScope.bundle}'  key='The.application.comes.into.effect.immediately' />"
						}
						return str;
					}
				},
				{
					"targets" : [ 6 ],
					"data" : "goodsPrices"
				},
				{
					"targets" : [ 7 ],
					"data" : null,
					"mRender" : function(data, type, full) {
						return '<a title=\"<fmt:message bundle="${pageScope.bundle}" key="Add.elements" />\" href="javaScript:toAddElement('
								+ data.goodsId
								+ ');" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Add.elements" /></a>'
								+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Product.details" />\" href="javaScript:showMessage('
								+ data.goodsId
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Product.details" /></a> '
								+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Modify" />\" href="javaScript:toUpdate('
								+ data.goodsId
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Modify" /></a>'
								+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('
								+ data.goodsId
								+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></a>';
								
					}
				}

		],

		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : true,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : true,
		"bAutoWidth" : true,
		"aaSorting" : [ [ 1, "asc" ] ],
		"bStateSave" : false,
		"sPaginationType" : "full_numbers",
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
            "sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='sorry,no.records' />",
            "sInfo" : "<fmt:message bundle='${pageScope.bundle}'  key='Current.view' /> _START_ <fmt:message bundle='${pageScope.bundle}'  key='To' />"+
            			"_END_ <fmt:message bundle='${pageScope.bundle}'  key='Article' />,<fmt:message bundle='${pageScope.bundle}'  key='Total' />"+
            			" _TOTAL_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
            "sInfoEmtpy" : "显示0到0条记录",
            "sInfoFiltered" : "",
            "sProcessing" : "<fmt:message bundle='${pageScope.bundle}'  key='Loading' />...",
            "sSearch" : "<fmt:message bundle='${pageScope.bundle}'  key='Search' />",
            "oPaginate" : {
                "sFirst" : "<fmt:message bundle='${pageScope.bundle}'  key='The.first.page' />",
                "sPrevious" : " <fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /> ",
                "sNext" : " <fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /> ",
                "sLast" : " <fmt:message bundle='${pageScope.bundle}'  key='The.last.page' /> "
            }
		},
		"aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
				[ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
	//设置每页显示记录的下拉菜单
	}

	$(document).ready(function() {
		$('#example').dataTable(dataTableObj);
	});

	function sreach() {
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(aoData) {
				aoData.push({
					"name" : "goodsName",
					"value" : $("#goodsName").val()
				},
				{
					"name" : "goodsType",
					"value" : $("#goodsType").val()
				},
				{
					"name" : "activeWay",
					"value" : $("#activeWay").val()
				}

				);
			}
		});
		$('#example').dataTable().fnDraw();
	}
</script> 
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<form role="form">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />：</label>
					<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.product.name' />" id="goodsName" name="goodsName">
					</div>
				</div>
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Product.type' />：</label>
					<div class="tBox">
						<select id="goodsType" name="goodsType" class="input-text">
							<option value=""><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
							<option value="0">预付单卡</option>
							<option value="1">预付共享灵活</option>
							<option value="2">预付共享固定</option>
							<option value="3">月付单卡</option>
							<option value="4">月付共享灵活</option>
							<option value="5">月付共享固定</option>
							<option value="6">单卡叠加包</option>
							<option value="7">流量池叠加包</option>
						</select>
					</div>
				</div>
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Effective.condition' />：</label>
					<div class="tBox">
						<select id="activeWay" name="activeWay" class="input-text">
							<option value=""><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
							<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='Effective.immediately' /></option>
							<option value="1">次月生效</option>
							<option value="2">到期生效</option>
						</select>
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Search' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
		</form>

		<div class="fr mtb20">
			<a href="javascript:;"
				onclick="goods_add('<fmt:message bundle='${pageScope.bundle}'  key='Add.product' />','${ctx}/goods/toAddGoods','800','550')"
				class="btn btn-primary radius"> <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='Add.product' />
			</a>
		</div>
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Product.ID' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Product.picture' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Product.provider' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Product.type' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Effective.condition' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Standard.price'/></th>				
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>

</body>
</html>
