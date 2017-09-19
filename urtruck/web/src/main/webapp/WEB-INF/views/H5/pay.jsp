<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<form action="${pcwebPayRequestUrl}" name='pay' method="post">

		<c:forEach items="${params}" var="entry">
			<input type="hidden" name="<c:out value="${entry.key}" />" value="<c:out value="${entry.value}"/>">
		</c:forEach>
	</form>
	<script type="text/javascript">
 		document.pay.submit();
	</script>
</body>
</html>