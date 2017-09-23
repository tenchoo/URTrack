<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title>添加企业客户</title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
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
		$("#form_cust").validate();
	});
	function closeLayer() {
		debugger;
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form action="${ctx}/cust/save" method="post"
				class="form form-horizontal" id="form_cust" name="form-member-add">
				<input type="hidden" id="custId" name="custId" value="${customerDto.custId}"> 
				<input type="hidden" id="custStateHidden" value="${customerDto.custState}" /> 
			    <input type="hidden" id="psptTypeValue" value="${customerDto.psptTypeCode}" /> 
			    <input type="hidden" id="sellTypeValue" value="1" />
			    <input type="hidden" id="sexId" name="sexId" value="${custPersonDto.sex}" />
				<input type="hidden" id="ids" value="${ids}" />
				<div class="row cl">
					<h1>客户信息：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3"><span class="c-red" required>*</span>客户名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${customerDto.custName}" id="custName" name="custName"
								readonly="readonly">
						</div>
						<label class="form-label col-3"><span class="c-red">*</span>客户类型：</label>
						<div class="formControls col-3">
							<select id="custState" name="custState">
								<option value="0">潜在客户</option>
								<option value="1">在用客户</option>
								<option value="2">注销客户</option>
							</select>
							<script type="text/javascript">
								$("#custState")
										.val($("#custStateHidden").val());
							</script>
						</div>

					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">证件号码：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${customerDto.psptId}" id="psptId" name="psptId">
						</div>
						<label class="form-label col-3">证件类别：</label>
						<div class="formControls col-3">
							<select id="psptTypeCode" name="psptTypeCode">
							   <option value="1">身份证</option>								
							</select>
							<script type="text/javascript">
								$("#psptTypeCode").val(
										$("#psptTypeValue").val());
							</script>
						</div>
					</div>
				</div>
				<div class="row cl" style="">
					<h1>个人资料：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">性别：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.sex}" id="sexId"
								name="sexId" readonly="readonly">
						</div>
						<label class="form-label col-3">生日：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.birthday}" id="birthdayId"
								name="birthdayId" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">						
						<div style="display: inline;">
							<label class="form-label col-3">销售类型：</label>
							<div class="formControls col-3">
								<select id="sellType" name="sellType" readonly="readonly">
									<option value="0">自营</option>
								</select>
								<script type="text/javascript">
									$("#sellType").val(
											$("#sellTypeValue").val());
								</script>
							</div>

						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">身份证所在地：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.psptAddr}" id="provinceCode"
								name="provinceCode" readonly="readonly">
						</div>						
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">居住地址：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.postAddress}" id="groupAddr"
								name="groupAddr" readonly="readonly">
						</div>
					</div>
				</div>
				<div class="row cl">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>

</html>
