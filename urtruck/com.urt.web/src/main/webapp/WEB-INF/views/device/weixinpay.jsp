<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='paying.through.Wechat' /></title>

<link href="${ctx}/static/toWeb/css/dateRange.css" rel="stylesheet" />
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

</head>

<body>
    <div class="header bg02">
        <div class="centerBox">
            <a href="javascript:void(0)" class="logo"></a>
        </div>
    </div>
  <!--    <style> 
    .divcss5{ height:80px; width:150px; border:1px solid #00F} 
    </style>  -->
     <div class="header bg03">
        <div class="inner" style="height:130px; width:400px"   >
           <img alt=""  width="30%"   height="40%"   src="${ctx}/static/device/WePayLogo.jpg">
        </div>
    </div>
    <div class="flowBanner">
            <div class="inner">
            <div class="btnBox">
            	<img alt="<fmt:message bundle='${pageScope.bundle}'  key='WeChat.QR.code' />" src="${ctx}/static/device/QRCode.jpg">
            </div>
            </div>
    </div>
</body>
</html>
