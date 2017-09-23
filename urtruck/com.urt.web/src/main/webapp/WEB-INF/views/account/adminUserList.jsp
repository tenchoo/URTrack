<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='user.management' /></title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th><fmt:message bundle='${pageScope.bundle}'  key='Login.name' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='Nickname' /></th><th><fmt:message bundle='${pageScope.bundle}'  key='registration.date' /><th><fmt:message bundle='${pageScope.bundle}'  key='administration' /></th></tr></thead>
		<tbody>
		<c:forEach items="${users}" var="user">
			<tr>
				<td><a href="${ctx}/admin/user/update/${user.id}">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>
					<fmt:formatDate value="${user.registerDate}" pattern="yyyy年MM月dd日  HH时mm分ss秒" />
				</td>
				<td><a href="${ctx}/admin/user/delete/${user.id}"><fmt:message bundle='${pageScope.bundle}'  key='Delete' /></a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
