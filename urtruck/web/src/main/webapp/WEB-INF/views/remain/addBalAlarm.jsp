<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Define.the.balance.warning.rule' /></title>
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
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
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>

<style>
.error {
	color: red;
}

.verticalSpacing {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#form_rule").validate();
	});

	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	function saveRule() {
		var custId = ${bCustId};
		var ruleName = $("#ruleName").val();
		var balFee = $("#balFee").val();
		var emaileId = $("#emaileId").val();
		var telphoneId = $("#telphoneId").val();
		var cal = $("#cal").val();
		var dataArr = [];
		if (ruleName == "") {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.rule.name.cannot.be.empty' />！");
			$("#ruleName").focus();
			return;
		}
		if (balFee == "" || balFee < 0) {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.balance.threshold.cannot.be.empty.or.less.than.0' />！");
			$("#balFee").focus();
			return;
		}
		if (emaileId == "" && telphoneId == "") {
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.recipient.of.the.mail.and.the.receiver' />！");
			return;
		}
		//默认优先级为0
		if (cal == "") {
			cal = 0;
		}
		dataArr.push(ruleName);
		dataArr.push(balFee);
		dataArr.push(emaileId);
		dataArr.push(telphoneId);
		dataArr.push(cal);
		dataArr.push(custId);

		var str = dataArr.join(";");

		var index = parent.layer.getFrameIndex(window.name);
		$.ajax({
			type : "POST",
			url : "${ctx}/remain/saveBalRules",
			data : {
				"accData" : str
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				if (data == 0) {
					alert("<fmt:message bundle='${pageScope.bundle}'  key='successfully.added' />!");
					window.parent.location.reload();
					parent.layer.close(index);
				} else {
					alert("<fmt:message bundle='${pageScope.bundle}'  key='The.balance.warning.rule.already.existed' />!");
					window.parent.location.reload();
					parent.layer.close(index);
				}

			},
			error : function(error) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='failed.added' />");
			}
		});
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec clearfix">
			<form method="post" class="form form-horizontal" id="form_rule"
				name="form-member-add">
				<div class="col-12 clearfix" style="margin-bottom: 50px;">
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='rule.name' />: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;">
						<input type="text" class="input-text" id="ruleName"
							name="ruleName" required>
					</div>

					<div class="col-md-3 col-lg-3 mt20"
						style="width: 15%;padding-left: 40px;">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Balance.threshold' />: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin-left: 2px;">
						<input type="text" class="input-text" id="balFee" name="balFee"
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='unit.part' />" required>
					</div>
				</div>
				<div class="col-12 clearfix" style="margin-bottom: 50px;">
					<div class="col-md-3 col-lg-3 mt20">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Mail.recipient' />： </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;">
						<input type="text" class="input-text" id="emaileId" name="emaileId"
						placeholder="<fmt:message bundle='${pageScope.bundle}'  key='multiple.recipients.are.separated.in.english.commas' />" required>
					</div>

					<div class="col-md-3 col-lg-3 mt20"
						style="padding-left: 40px;">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='SMS.receiver' />： </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%;">
						<input type="text" class="input-text" id="telphoneId"
							name="telphoneId" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='multiple.recipients.are.separated.in.english.commas' />" required>
					</div>
				</div>
				<div class="col-12 clearfix" style="margin-bottom: 50px;">
					<div class="col-md-3 col-lg-3 mt20">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Rule.Priority' />： </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin: auto;">
						<input type="text" class="input-text" id="cal" name="cal"
								required>
					</div>
				</div>
				<div class="col-12 clearfix" style="margin-bottom: 50px;">
					<div class="col-md-3 col-lg-3 mt20">
						<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='perform.action' />： </label>
					</div>
					<div class="row cl" id="execute"></div>
					<div class="row cl" style="margin-bottom: 20px;">
						<div class="col-6 col-offset-5">
							<input class="btn btn-primary radius" type="button"
								value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveRule();">
							<input class="btn btn-default radius" type="reset"
								value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> <input
								class="btn btn-primary radius" type="button"
								value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
