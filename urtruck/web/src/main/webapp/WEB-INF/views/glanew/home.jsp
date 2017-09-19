<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
        <meta name="renderer" content="webkit">
		<meta name="keywords" content="">
        <meta name="description" content="">
		<!--页面需默认用极速核-->
		<!--[if lt IE 9]>
	    <meta http-equiv="refresh" content="0;ie.html" />
	    <![endif]--> 
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.Data.View' /></title>
    	<link rel="stylesheet" href="${ctx}/static/glaNew/css/iconfont.css" />
		<link rel="stylesheet" href="${ctx}/static/glaNew/css/reset.css" />
		<link rel="stylesheet" href="${ctx}/static/glaNew/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/static/glaNew/css/animate.css" />
		<link rel="stylesheet" href="${ctx}/static/glaNew/css/common.css" />
		<!-- 全局js -->
	    <script src="${ctx}/static/glaNew/js/jquery-1.11.3.min.js"></script>
	    <script src="${ctx}/static/glaNew/js/bootstrap.min.js"></script>
	    <script src="${ctx}/static/glaNew/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	    <script src="${ctx}/static/glaNew/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	    <!-- 自定义js -->
	    <script src="${ctx}/static/glaNew/js/default.js"></script>
	    <script src="${ctx}/static/glaNew/js/main.js"></script>
	    <!-- 第三方插件 -->
	    <script src="${ctx}/static/glaNew/js/plugins/pace/pace.min.js"></script>
	    <style type="text/css">
	    	.home-body {overflow:hidden;}
	    </style>
	</head>
	<body class="fixed-sidebar full-height-layout gray-bg home-body">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            
                        </div>
                        <div class="logo-element">
                        	CMP2.0
                        </div>
                    </li>
         			<c:if test="${indexNav != null}">
	         			<li>
	                        <a class="J_menuItem" href="${indexNav.url}">
	                            <i class="fa ${nav1.navPicture}"></i>
	                            <span class="nav-label">${indexNav.navName}</span>
	                        </a>
	                    </li>
         			</c:if>
                    
                   	<c:forEach items="${navList}" var="nav1">
						<li>
							<a href="#">
	                            <i class="fa ${nav1.navPicture}"></i>
	                            <span class="nav-label">${nav1.navName}</span>
	                            <span class="fa arrow"></span>
                       		</a>
                       		<ul class="nav nav-second-level">
                        		<c:forEach items="${nav1.navigationList}" var="nav2">
									 <li>
                                		<a class="J_menuItem" href="${ctx}/${nav2.url}">${nav2.navName}</a>
                            		</li>
								</c:forEach>
							</ul>
                   		 </li>
					</c:forEach>
                </ul> 
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">
            <div class="row border-bottom">
                <nav class="navbar  navbar-static-top" role="navigation" style="margin-bottom: 0">
                    <div class="navbar-header col-sm-1"><a class="navbar-minimalize minimalize-styl-2 btn" href="#"><i class="fa icon-menu"></i> </a>
                        
                    </div>
                    <div class="welcome_text fl">
                    	<p><fmt:message bundle='${pageScope.bundle}'  key='WELCOME.GLA' /></p>
                    </div>
                    <div class="right_account fr">
                    	<span></span><fmt:message bundle='${pageScope.bundle}'  key='Hello' />, ${user.nickname}!&nbsp;
                    	<a href="${ctx}/login/logout">[<fmt:message bundle='${pageScope.bundle}'  key='Safe.exit' />]</a>
                    </div>
                </nav>
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe id="J_iframe" width="100%" height="100%" src="${indexNav.url}" frameborder="0" data-id="index.html" seamless></iframe>
            </div>
        </div>
        <!--右侧部分结束-->
    </div>
</body>

</html>
