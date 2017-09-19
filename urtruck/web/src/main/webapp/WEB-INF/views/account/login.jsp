<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='landing.page' /></title>
</head>

<body>
	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
	<%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button><fmt:message bundle='${pageScope.bundle}'  key='Login.failed,please.try.again' />
		</div>
	<%
	}
	%>
		<div class="control-group">
			<label for="username" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />:</label>
			<div class="controls">
				<input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='Password' />:</label>
			<div class="controls">
				<input type="password" id="password" name="password" class="input-medium required"/>
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> <fmt:message bundle='${pageScope.bundle}'  key='Keep.me.signed.in' /></label>
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='sign.in' />"/> <a class="btn" href="${ctx}/register"><fmt:message bundle='${pageScope.bundle}'  key='registration' /></a>
			 	<span class="help-block">(<fmt:message bundle='${pageScope.bundle}'  key='administrator' />: <b>admin/admin</b>, <fmt:message bundle='${pageScope.bundle}'  key='commen.user' />: <b>user/user</b>)</span>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
		});
	</script>
</body>
</html>
