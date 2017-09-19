<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Add.role' /></title>
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
	$(document).ready(
			function() {
				$(".permission-list>dt input:checkbox").click(
						function() {
							$(this).closest("dl").find("dt input:checkbox")
									.prop("checked", $(this).prop("checked"));
						});
				$(".permission-list2>dt input:checkbox").click(
						function() {
							//获取子节点选中个数
							var l = $(this).parent().parent().parent().find(
									"input:checked").length;
							if ($(this).prop("checked")) {
								$(this).parents(".permission-list").find("dt")
										.first().find("input:checkbox").prop(
												"checked", true);
							} else {
								if (l == 0) {
									//$(this).closest("dl").find("dt input:checkbox").prop("checked",false);
									$(this).parents(".permission-list").find(
											"dt").first()
											.find("input:checkbox").prop(
													"checked", false);
								}
							}

						});
			});
</script>
<script type="text/javascript">
	var flag = true;
	function update() {
		debugger;
		var parent = document.getElementsByName('user-Character-0');
		var obj = document.getElementsByName('user-Character-1-0');
		var navigationIds = "";
		for (i = 0; i < parent.length; i++) {
			if (parent[i].checked == true) {
				navigationIds += parent[i].value + ",";
			}
		}
		for (i = 0; i < obj.length; i++) {
			if (obj[i].checked == true) {
				navigationIds += obj[i].value + ",";
			}
		}
		$("#navigationIds").val(navigationIds);
		/* $("#form-member-update").submit(); */
		saveRole();
	}
	function check(){
		debugger;
		var roleName=$("#roleName").val();
		if(roleName==null ||roleName=='' ){
			layer.msg('<fmt:message bundle="${pageScope.bundle}"  key="Role.name.is.required" />');
			$("#roleNameMsg").css("color", "red");
			flag=false;
		}else{
			$.ajax({
				url:"${ctx}/role/checkRoleName",
				data:{"roleName":roleName},
				type:"POST",
				dataType:"json",
				success:function(result){
					if(!result.success){
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Role.names.cannot.be.repeated' />");
						$("#roleNameMsg").css("color", "red");
						flag=false;
					}else{
						return true;
						$("#roleNameMsg").removeAttr("style");
					}
				}
			});
		}
	}
	//校验优先级合法性
	function checkPriority(){
		var priority = $("#priority").val();
		if (priority==null && priority==''){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Role.priority.is.required' />");
			$("#priorityMsg").css("color", "red");
			flag=false;
		}
		// 1-3位的正整数正则表达式
		var re = /^[1-9]\d{0,2}$/;
		if (!re.test(priority)){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Role.priority.is.number' />");
			$("#priorityMsg").css("color", "red");
			flag=false;
		}
	}
	function saveRole(){
		check();
		checkPriority();
		if(flag==false){
			return;
		}
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$.ajax({
	        type:"POST",
	        url:"${ctx}/role/roleUpdate",
	        data:$("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
				console.log("result:"+data);
			  	debugger;
			  	window.parent.location.reload();
			  	parent.layer.close(index);
			},
			error : function(error) {
			}
		});
	}
	function closeLayer(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post"
				class="form form-horizontal" id="form-member-update">
				<input type="hidden" id="navigationIds" name="navigationIds">
				<input type="hidden" value="${roledto.roleId }" id="roleId"
					name="roleId">
				<div class="row cl">
					<label class="form-label col-2 widthSmall" ><span
						class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Role.name' />:</label>
					<div class="formControls col-8">
						<input type="text" class="input-text" value="${roledto.roleName }"
							placeholder="" id="roleName" name="roleName" onchange="check();">
							<lable id="roleNameMsg" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Role.names.cannot.be.empty.and.cannot.be.repeated' /></lable>
					</div>
				</div>
				<div class="row cl font12">
					<label class="form-label col-2 widthSmall"><span
						class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Role.priority' />:</label>
					<div class="formControls col-8" >
						<input type="text" class="input-text" value="${roledto.priority}" placeholder=""
							id="priority" name="priority" onchange="checkPriority();" >
							<lable id="priorityMsg" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Role.priority.is.numberAndReq' /></lable>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-2 widthSmall" ><fmt:message bundle='${pageScope.bundle}'  key='Role.description' />:</label>
					<div class="formControls col-8" >
						<input type="text" class="input-text"
							value="${roledto.description }" placeholder="" id="description"
							name="description">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-2 widthSmall" ><fmt:message bundle='${pageScope.bundle}'  key='Allocation.menu' />:</label>
					<div class="formControls col-10">
						<c:forEach var="i" items="${vBeans}">
							<dl class="permission-list">
								<dt>
									<label> <input type="checkbox" value="${i.id}"
										name="user-Character-0"
										<c:if test="${i.check==true }">checked="checked"</c:if>
										id="user-Character-1"> ${i.name}
									</label>
								</dt>
								<dl class="cl permission-list2" style="padding-left: 10px">
									<c:forEach items="${i.viewTreeBeans}" var="j">
										<dt>
											<label class=""> <input type="checkbox"
												value="${j.id}" name="user-Character-1-0"
												<c:if test="${j.check==true }">checked="checked"</c:if>
												id="user-Character-1-0"> ${j.name }
											</label>
										</dt>
									</c:forEach>
								</dl>
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
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;"
							onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>