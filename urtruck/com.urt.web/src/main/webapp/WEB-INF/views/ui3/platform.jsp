<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.Data.View' /></title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/h5/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/newStyle.css">
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/loginNew.js"></script>
<script type="text/javascript">
function toCmp(){
	debugger;
	window.location.href="${ctx}/login/toIndex";
}
function toDmp(){
	window.location.href="https://219.142.224.173:3000/emm/login";
}
</script>
</head>
<body class="loginBody">
    <div class="start01"><img src="${ctx}/static/ui3/images/start01.png"></div>
    <div class="start02"><img src="${ctx}/static/ui3/images/start02.png"></div>
    <div class="start03"><img src="${ctx}/static/ui3/images/start03.png"></div>
    <div class="start04"><img src="${ctx}/static/ui3/images/start04.png"></div>
    <div class="platBox clearfix">
        <div class="cmpBox" onclick="toCmp();">
            <div class="cmpImg">
                <img src="${ctx}/static/ui3/images/plat_cmp.png" height="195" width="361">
            </div>
            <div class="cmpName" >
                CMP<br>
              	 <fmt:message bundle='${pageScope.bundle}'  key='Connection.Manager' />
            </div>
        </div>
        <div class="dmpBox" onclick="toDmp();">
            <div class="dmpImg">
                <img src="${ctx}/static/ui3/images/plat_DMP.png" height="162" width="360">
            </div>
            <div class="dmpName">
                DMP<br>
                <fmt:message bundle='${pageScope.bundle}'  key='Device.Manager' />
            </div>
        </div>
    </div>
</body>
</html>