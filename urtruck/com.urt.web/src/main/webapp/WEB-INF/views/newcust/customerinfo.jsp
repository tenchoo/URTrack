<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_cn" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'
		key='Product.add.page' /></title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />

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
<script type="text/javascript" src="${ctx}/static/jquery/jquery.js"></script>
</head>
<body>
	<div class="seconSec">



		<h1>企业信息</h1>
		<div class="oh">
			<div class="col-12 clearfix">
				<div class="col-md-3 col-lg-3 mt20">
					<label for="name"><fmt:message
							bundle='${pageScope.bundle}' key='Customer.Name' />:</label>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="custName">${custInfo.custName}</div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}' key='Customer.Condition' />
					:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="custState"></div>
				</div>
			</div>
		</div>
		<div class="oh">
			<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}' key='Certificate.Type' />
					:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="psptTypeCode"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}' key='Certificate.Number' />
					:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="psptId">
						<span></span>
					</div>
				</div>
			</div>
		</div>
		<h1>分格设置</h1>
		<form method="post" class="form form-horizontal" id="form" name="form-member-add" enctype="multipart/form-data">
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12">上传logo:</span>
				</label>
				<div class="formControls langMl zpInput">
					<div class="uldImg fl">
						<img id="allImgUrl" src="${ctx}/static/ui/images/pic.png" />
					</div>
					<input type="hidden" name="goodsPic" id="path" />
					<div
						style="float: left; margin-left: 20px; margin-top: 30px; font-size: 12px;">
						<input type="file" onchange="uploadPic()" name="pic" id="pic" />
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12">欢迎语:</span>
				</label>
				<div class="tBox">
						<input type="text" class="input-text" value=""
							placeholder="请输入欢迎语" id="welcomeLanguage" name="welcomeLanguage">
				</div>
			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12">选择样式:</span>
				</label>
				<div class="formControls langMl zpInput">
					<select id="lang">
						<option value="-1" selected="selected">请选择</option>
						<option value="1">淡蓝</option>
						<option value="2">简约</option>
					</select>
				</div>
			</div>

		</form>
		    <div class="row cl">
				<div class="zpButton">
					<input class="btn btn-primary radius" type="button"
						onclick="save();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;"> <input
						class="btn btn-default radius" type="reset"
						value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Reduction' />&nbsp;&nbsp;" onclick="clear();">
				</div>
			</div>
	</div>
	<script type="text/javascript">

//上传图片
function uploadPic(){
	//定义参数
	var options = {
		url : "<%=request.getContextPath()%>/goods/uploadPic",
		dataType : "json",
		type :  "post",
		success : function(data){
			//回调 二个路径  
			//url
			//path
			$("#allImgUrl").attr("src",data.url);
			$("#path").val(data.url);			
		}
	};	
	//jquery.form使用方式
	$("#form").ajaxSubmit(options);
	
}



$(function(){
	$.post("<%=request.getContextPath()%>/operators/findOperators",
					function(data) {
						var operatorsList = eval(data);
						for (var i = 0; i < operatorsList.length; i++) {
							$("#operators").append(
									"<option value='"+operatorsList[i].operatorsId+"'>"
											+ operatorsList[i].operatorsName
											+ "</option>");
						}
					}, "json");
			var goodsTypes = [
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Monthly.package' />",
						id : "0"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='overlay.package' />",
						id : "1"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Flexible.data.pool' />",
						id : "2"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='regular.data.pool' />",
						id : "3"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Limited.batch' />",
						id : "4"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Unlimited.monthly.use' />",
						id : "5"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Limited.monthly.use' />",
						id : "6"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='test.product' />",
						id : "7"
					} ];
			$("#goodsType").select2({
				data : goodsTypes
			});
			var activeWays = [
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='Effective.immediately' />",
						id : "0"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='in.force.at.next.period' />",
						id : "1"
					},
					{
						text : "<fmt:message bundle='${pageScope.bundle}'  key='The.application.comes.into.effect.immediately' />",
						id : "2"
					} ];
			$("#activeWay").select2({
				data : activeWays
			});

		});

		function check() {
			var goodsNameValue = window.document.getElementById("welcomeLanguage").value;
			var activeWayValue = window.document.getElementById("lang").value;
			var picValue =window.document.getElementById("pic").value;
			alert(picValue);
			alert(activeWayValue);
			
			if (picValue == "") {
				layer.msg("请上传图片");
				return false;
			}
			if (goodsNameValue == "") {
				layer.msg("请填写欢迎语");
				return false;
			}

			
			if (activeWayValue == "-1") {
				layer.msg("请选择风格");
				return false;
			}
			
			return true;
		}

		//清空
		function clear() {
			$("#welcomeLanguage").text("");
			$("#pic").text("");
			$("#lang").text("");
		}

		//保存
		function save() {
			var goodsNameValue = window.document.getElementById("welcomeLanguage").value;
			var activeWayValue = window.document.getElementById("lang").value;
			var picValue =window.document.getElementById("pic").value;
			alert(picValue);
			alert(activeWayValue);
			if (picValue == "") {
				layer.msg("请上传图片");
				return false;
			}
			if (goodsNameValue == "") {
				layer.msg("请填写欢迎语");
				return false;
			}
			if (activeWayValue == "-1") {
				layer.msg("请选择风格");
				return false;
			}
            alert(1)
			$.ajax({
				type : "GET",
				url : "${ctx}/customer/addcuststyle?welcomeLanguage="+goodsNameValue+"&lang="+activeWayValue+"&pic="+picValue,
				/* data : $("#form").serialize(),
				dataType : "json",
				cache : false, */
				success : function(data) {
					layer.msg("提交成功");
					clear();
				},
				error : function(error) {
					layer.msg("提交失败");
				}
			});
		}
	</script>
</body>
</html>