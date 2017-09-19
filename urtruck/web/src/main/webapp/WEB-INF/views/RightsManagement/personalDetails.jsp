<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Personal.information' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript">

function updateSysUserPassword(userId){
	var url = "${ctx}/ssUser/toUpdatePassword/"+userId ;
	layer_show('<fmt:message bundle="${pageScope.bundle}"  key="change.password" />',url,'500','300');
}
</script>
</head>
<body>
<form role="form" class="form form-horizontal">
    <div class="row cl">
      <input type="hidden" class="input-text" value="${ssUserDto.userId}" placeholder="" id="userId" name="userId">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />:</label>
      <div class="formControls col-5">
        <span title="" data-href="">${ssUserDto.loginName}</span>
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Nickname' />：</label>
      <div class="formControls col-5">
        <span title="" data-href="">${ssUserDto.nickname}</span>
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl" id="pasDivId">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Password' />：</label>
      <div class="formControls col-5">
        <input class="input-text" type="password" id="password" name="password" value="${ssUserDto.password}" disabled="disabled"><a title="修改密码" href="javaScript:updateSysUserPassword(${ssUserDto.userId});" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe60c;</i></a>
        
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='mobliephone.number' />:</label>
      <div class="formControls col-5">
        <span title="" data-href="">${ssUserDto.phone}</span>
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Email' />:</label>
      <div class="formControls col-5">
        <span title="" data-href="">${ssUserDto.email}</span>
      </div>
      <div class="col-4"> </div>
	</div>
    <div class="row cl">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Role.name' />:</label>
      <div class="formControls col-5">
      	<c:forEach items="${ssUserDto.roles}" var="role">
            <span title="" data-href="">${role.roleName}</span>
        </c:forEach>
      </div>
	</div>
  </form>

</body>

</html>