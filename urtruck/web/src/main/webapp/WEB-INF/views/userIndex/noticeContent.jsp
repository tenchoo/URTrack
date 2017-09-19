<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<html lang="en" class="overHid">
<head>
    <meta charset="UTF-8">
    <title><fmt:message bundle='${pageScope.bundle}'  key='Notification.content.page' /></title>
    <script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/newStyle.css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
    <%-- <script type="text/javascript" src="${ctx}/static/ui3/js/data_list.js"> --%></script>	
</head>
<body class="add_bg">
	<input id="noticeId" type="hidden" value="${noticeId}">
    <div class="main clearfix">
        <div class="clear"></div>
        <div class="container">
            <!-- 加载内容放这里 -->
            <div class="box minHeight01 clearfix">
                <h3 id="noticetitle"></h3>
                <p class="grey01 pdT03" id="timeAndNumber"></p>
                <article id="noticeContent">
                </article>
            </div>

        </div>
    </div>
    <script>
    $(function(){
    	selectContent();
    })
    	function selectContent(){
    	var noticeId = $("#noticeId").val();
    		$.ajax({
    			type:"post",
    			url:"${ctx}/custNotice/selectNoticeContent",
    			data:{noticeId:noticeId},
    			success:function(result){
    				$("#noticetitle").html(result.NOTICE_TITLE);
    				$("#noticeContent").html(result.NOTICE_CONTENT);
    				$("#timeAndNumber").html("<fmt:message bundle='${pageScope.bundle}'  key='Release.time' />:"+result.STARTTIME+"&nbsp"+"<fmt:message bundle='${pageScope.bundle}'  key='Click' />:"+result.CLICK_NUMBER+"<fmt:message bundle='${pageScope.bundle}'  key='each' />");
    				
    			}
    		})
    	}
    </script>
</body>
</html>