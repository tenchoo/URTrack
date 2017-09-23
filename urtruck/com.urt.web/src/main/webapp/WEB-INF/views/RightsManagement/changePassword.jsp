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
<title><fmt:message bundle='${pageScope.bundle}'  key='Add.system.user' /></title>
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
$(document).ready(function(){
	var type = "${submitType}";
	if(type == "update"){
		$("#pasDivId").attr("style","display:none");
		$("#pasDivId2").attr("style","display:none");
		$("#password").attr("disabled",true);
	}
});
</script>	
<script type="text/javascript">

function saveSysUser(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	$.ajax({
        type:"POST",
        url:"${ctx}/ssUser/${submitType}",
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
}
</script>
</head>
<body>
<form role="form" class="form form-horizontal">
    <div class="row cl">
      <label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />:</label>
      <div class="formControls col-5">
        <span title="" data-href="">redMo</span>
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl" id="pasDivId">
      <label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='pld.password' />：</label>
      <div class="formControls col-5">
        <input type="password" id="oldPassword" name="oldPassword" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='pld.password' />" autocomplete="off" value="" class="input-text" datatype="*6-20" nullmsg="<fmt:message bundle='${pageScope.bundle}'  key='password.can.not.be.empty' />">
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl" id="pasDivId">
      <label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='new.password' />：</label>
      <div class="formControls col-5">
        <input type="password" id="newPassword" name="newPassword" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='new.password' />" autocomplete="off" value="" class="input-text" datatype="*6-20" nullmsg="<fmt:message bundle='${pageScope.bundle}'  key='password.can.not.be.empty' />">
      </div>
      <div class="col-4"> </div>
	</div>
    <div class="row cl" id="pasDivId2">
      <label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Re-enter.password' />：</label>
      <div class="formControls col-5">
        <input type="password" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Re-enter.password' />" value="" autocomplete="off" class="input-text Validform_error" errormsg="<fmt:message bundle='${pageScope.bundle}'  key='Entered.passwords.differ' />！" datatype="*" nullmsg="<fmt:message bundle='${pageScope.bundle}'  key='retype.password' />！" recheck="newpassword" id="newpassword2" name="newpassword2">
      </div>
	</div>
    <div class="row cl">
      <div class="col-9 col-offset-3">
        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveSysUser();">
        <input class="btn btn-primary radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
      </div>
    </div>
  </form>

</body>

</html>