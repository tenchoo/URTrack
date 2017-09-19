<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Write.enterprise.client.ontacts' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
	
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
/* $(document).ready(function() {
	$("#form_concat").validate();
}); */
function check(){
	if($("#contactName").val()==null || $("#contactName").val()==""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Contact.name.is.required' />");
		return false;
	}
	if($("#contactPhone").val()==null || $("#contactPhone").val()==""){
		layer.msg("电话为必填项");
		return false;
	}
	if($("#contactDepart").val()==null || $("#contactDepart").val()==""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='department.is.required' />");
		return false;
	}
	if($("#contactDuty").val()==null || $("#contactDuty").val()==""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='position.is.required' />");
		return false;
	}
	debugger;
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$.ajax({
        type:"POST",
        url:"${ctx}/cust/saveConcat",
        data:$("form").serialize(),
		dataType : "json",
		cache : false,
		success : function(data) {
		  	window.parent.location.reload();
		  	parent.layer.close(index);
		},
		error : function(error) {
		}
	});
	return true;
}
function closeLayer(){
	debugger;
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
</script>
</head>
<body>
<div class="">
	<div class="seconSec font12">
	<form method="post"
		class="form form-horizontal" id="form_concat" name="form-member-add" onsubmit="return check()" >
		<input type="hidden" id="contactId" name="contactId"
			value="${concatDto.contactId}"> <input type="hidden"
			id="custId" name="custId" value="${custId}">

		<div class="row cl">
			<h4 class="col-md-offset-1"><fmt:message bundle='${pageScope.bundle}'  key='Add.a.Contact' />:</h4>
		</div>
		<div class="row cl">
			<div class="col-10 col-offset-1">
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='name' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactName}" id="contactName" name="contactName" required>
					</div>
					<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='telephone.number' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactPhone}" id="contactPhone" name="contactPhone" required>
					</div>
					<%--  --%>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='department' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactDepart}" id="contactDepart" name="contactDepart" required>
					</div>
					<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='position' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" id="contactDuty" value="${contactDuty}"
							name="contactDuty" required>
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='fax' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactFax}" name="contactFax" />
					</div>
					<label class="form-label col-3">EMail:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactEmail}" name="contactEmail"
							validate="{email:true, messages:{required:'输入email地址', email:'你输入的不是有效的邮件地址'}}" />
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">

					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='postcode' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactPostCode}" name="contactPostCode" />
					</div>
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='mailing.address' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactPostAddr}" name="contactPostAddr" />
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='home.address' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactHomeAddr}" name="contactHomeAddr" />
					</div>
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Best.contact.time' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.bestContactTime}" name="bestContactTime"
							onclick="WdatePicker()" readonly="readonly" />
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Document.type' />:</label>
					<div class="formControls col-3">
						<select name="contactPsptTypeCode" id="contactPsptTypeCode">
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='ID.card' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Household.Register' /></option>
							<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Military.id.card' /></option>
							<option value="4"><fmt:message bundle='${pageScope.bundle}'  key='the.resident.identity.cards.of.people.s.armed.policemen' /></option>
							<option value="5"><fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.HongKong.and.Macao.to.Chinese.mainland' /></option>
							<option value="6"><fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.Taiwan.to.Chinese.mainland' /></option>
							<option value="7"><fmt:message bundle='${pageScope.bundle}'  key='passport' /></option>
						</select> <input type="hidden" id="idcode"
							value="${concatDto.contactPsptTypeCode}" />
						<script type="text/javascript">
			        		$("#contactPsptTypeCode").val($("#idcode").val());
			        	</script>
					</div>
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Document.number' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactPsptId}" name="contactPsptId" />
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='name.of.organization' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactWorkName}" name="contactWorkName" />
					</div>
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='address.of.organization' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text"
							value="${concatDto.contactWorkAddr}" name="contactWorkAddr" />
					</div>

				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Remark' />:</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" value="${concatDto.remark}"
							name="remark" />
					</div>
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-6 col-offset-5">
				<input class="btn btn-primary radius" type="button"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="check();">
				<!-- <input class="btn btn-primary radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> -->
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;"
					onclick="closeLayer();">
			</div>
		</div>
	</form>
	</div>
	</div>
</body>
</html>
