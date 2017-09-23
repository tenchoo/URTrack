<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title><sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style type="text/css">
.container1{
	width:100%;
	height:100%;
}
.zpCont{
	width:100%;
	height:100%;
} 
</style>
<sitemesh:head/>
</head>

<body <sitemesh:getProperty property="body.class" writeEntireProperty="true" />>
	
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<sitemesh:body/>
		<!-- <div id="content" class="zpCont">
			
		</div> -->
		<%-- <%@ include file="/WEB-INF/layouts/footer.jsp"%> --%>
	<!-- </div> -->
	<%-- <script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap.min.js" type="text/javascript"></script> --%>
</body>
</html> 
