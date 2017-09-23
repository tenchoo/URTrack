<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Changing.Interfaces' /></title>
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
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		/* $(".permission-list>dt input:checkbox").click(
				function() {
					$(this).closest("dl").find("dt input:checkbox")
							.prop("checked", $(this).prop("checked"));
				}); */
		$(".permission-list2>dt input:checkbox").click(function() {

		});
	});
</script>
<script type="text/javascript">
	var flag = true;
	function update() {
		debugger;
		/* var parent = document.getElementsByName('user-Character-0'); */
		var obj = document.getElementsByName('user-Character-1-0');
		var serverIds = "";
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked == true) {
				serverIds += obj[i].value + ",";
			}
		}
		$("#serverIds").val(serverIds);
		$("#custId").val();
		saveRole();
	}
	function check() {
		debugger;
		var custName = $("#custName").val();
		if (custName == null || custName == '') {
			layer.msg('<fmt:message bundle="${pageScope.bundle}"  key="Customer.name.is.required.fields" />');
			$("#roleNameMsg").css("color", "red");
			flag = false;
			return flag;
		} else {
			flag=true;
			return flag;
		}
		
	}
	function saveRole() {
		check();
		if (flag == false) {
			return;
		}
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$.ajax({
			type : "POST",
			url : "${ctx}/abilityOpen/updateServer",
			data : $("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
				window.parent.location.reload();
			  	parent.layer.close(index);
			},
			error : function(error) {
			}
		});
	}
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
			<form method="post" class="form form-horizontal"
				id="form-member-update">
				<input type="hidden" id="serverIds" name="serverIds"> <input
					type="hidden" value="${customer.custId}" id="custId" name="custId">
				<div class="row cl">
					<label class="fl langWidth"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</span>
					</label>
					<div class="formControls col-2" style="width: 80%">
						<input type="text" readonly="true" class="input-text" value="${customer.custName}"
							placeholder="" id="custName" name="custName" onchange="check();">
					</div>
				</div>
				<div class="row cl">
					<label class="fl langWidth"> <span
						class="colorRed smallStar"></span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='interface.names' />:</span>
					</label>
					<div class="formControls col-10" style="width: 80%">
						<c:forEach var="i" items="${vBeans}">
							<dl class="permission-list">
								<dt>
									<label class=""> <input type="checkbox"
										value="${i.serverId}" name="user-Character-1-0"
										<c:if test="${i.check}">checked="checked"</c:if>
										id="user-Character-1-0"> ${i.serverDesc}
									</label>
								</dt>
							</dl>
						</c:forEach>
					</div>
					<div class="col-2"></div>
				</div>
				<div class="row cl" style="margin-bottom: 20px;">
					<div class="col-9 col-offset-3">
						<input class="btn btn-primary radius" type="button"
							onclick="update()" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;"> <input
							class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>