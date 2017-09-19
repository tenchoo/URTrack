<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="reqUrl" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<html>
<head>
	<title>Restful API <fmt:message bundle='${pageScope.bundle}'  key='list' /></title>
</head>

<body>

<h3>Restful API <fmt:message bundle='${pageScope.bundle}'  key='list' /></h3>
<h4><fmt:message bundle='${pageScope.bundle}'  key='Query' /> API</h4>
<ul>
	<li><fmt:message bundle='${pageScope.bundle}'  key='Get.the.task.list' /> ： <a href="${reqUrl}/api/v1/task">${reqUrl}/api/v1/task</a></li>
	<li><fmt:message bundle='${pageScope.bundle}'  key='Get.the.task' />(id=1) ： <a href="${reqUrl}/api/v1/task/1">${reqUrl}/api/v1/task/1</a></li>
</ul>

<h4><fmt:message bundle='${pageScope.bundle}'  key='Modify' /> API</h4>
<ul>
	<li><fmt:message bundle='${pageScope.bundle}'  key='create.task' /> ：${reqUrl}/api/v1/task method=Post, consumes=JSON</li>
	<li><fmt:message bundle='${pageScope.bundle}'  key='Modify.the.task' />(id=1) ：${reqUrl}/api/v1/task/1 method=Put, consumes=JSON</li>
</ul>
</body>
</html>
