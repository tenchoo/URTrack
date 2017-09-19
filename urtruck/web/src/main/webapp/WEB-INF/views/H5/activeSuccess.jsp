<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><fmt:message bundle='${pageScope.bundle}'  key='activation.success' /></title>
    <link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="static/h5/js/jquery-1.12.4.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
    <script src="static/h5/js/bootstrap.min.js"></script>

    <style>
        *{
            font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
        }
    </style>




    <style>h5{font-size: 3.7vw}</style>

</head>
<body><div style=" font-size: 3.8vw;" >
    <div style="background-color: #ffffff; border-bottom: 1px solid #dfe4e1; margin-bottom: 2vw; overflow: hidden;" class="">
		<div style="padding: 1rem 0;margin: 0 1rem;" class="col-xs-1 col-xs-offset-1">
        <a href="javascript:history.go(-1)">
            <img style="" src="static/h5/images/goback.png" class="img-responsive" alt="Responsive image">
        </a>
        </div>
        <div style="margin: 1.25rem 3.8rem;font-size:4.2vw" class="col-xs-4 col-xs-offset-4"><fmt:message bundle='${pageScope.bundle}'  key='activation.success' /></div>
    </div>
    <!--<div style="margin-bottom: 2vw;" class="row"> <img src="images/single.jpg"class="img-responsive"></div>-->
    <div class="">
    <div style="margin-top:3rem; text-align: center" class="col-xs-12 "> <img src="static/h5/images/g.png" style="height: 5rem"></div>
    </div>
        <div style="margin-top: 10rem; text-align: center;margin-bottom: 3rem" class="">

    <p style="font-size:5vw;color: #353433; ">&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='activation.success' />！</p>

</div>
    <div style="margin-top: 3rem; text-align: center" class="">

        <button style="font-size:5vw;height: 40px;width:80vw;color:#ffffff;border-radius: 10px;text-align: center;background-color: #fa9148; " type="button" class="btn " onclick="window.location.href='<%=basePath%>queryService/index' "><fmt:message bundle='${pageScope.bundle}'  key='View.traffic' /></button>

    </div>

</div>

</body>
</html>