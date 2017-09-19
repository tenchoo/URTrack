﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
<script type="text/javascript" src="${ctx}/static/ui3/js/data_list.js"></script>

<script type="text/javascript">
   /*  window.onload=function(){
        var ifm=document.getElementById('ifm');
        var ifmBox=document.getElementById('ifmBox');
        var oBox=ifm.contentWindow.document.getElementById('logindDtaList');
        var oBody=document.getElementsByTagName('body')[0];
        if(oBox){
             ifmBox.style.background='none'; 
           // $('.main').css('width','1000px')
       // }else{
       // 	$('.main').css('width','1200px')
      //  }
    }; */
</script>

</head>
<body class="add_bg">
	<div class="wrap_header">
		<div class="header">
			<span class="welcome"></span>
			<div class="user_info">
				<p>
					<span class="user"><fmt:message bundle='${pageScope.bundle}'  key='Hello' />，${user.nickname}！</span>
					<a href="${ctx}/login/logout">[<fmt:message bundle='${pageScope.bundle}'  key='Safe.exit' />]</a>
					<span class="question hide"><fmt:message bundle='${pageScope.bundle}'  key='Frequently.Asked.Questions/FAQ' /></em></span>
				</p>
				<div class="answer_wrap">
					<span class="square fix hide"></span>
					<div class="answer">
						<ul>
							<li><a href="">什么问题1</a></li>
							<li><a href="">什么问题2</a></li>
							<li><a href="">什么问题3</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="main clearfix">
		<div class="logo2">
	<!-- <img src="${ctx}/static/ui3/images/logo2.png" alt=""> -->     
		</div>
		<div class="nav">
			<ul>
				<!-- <li><a href="">首页</a></li>
				<li><a href="">客户中心</a></li>
				<li><a href="">管理中心</a></li>
				<li><a href="">商品中心</a></li>
				<li><a href="">号卡中心</a>
					<div class="answer_wrap">
						<div class="answer">
							<span class="square fix"></span>
							<ul>
								<li><a href="">什么问题1</a></li>
								<li><a href="">什么问题2</a></li>
								<li><a href="">什么问题3</a></li>
							</ul>
						</div>
					</div></li>
				<li><a href="">订购中心</a></li>
				<li><a href="">余额中心</a></li> -->
				<c:forEach items="${navList}" var="nav1">
					<c:if test="${nav1.navName=='首页'}">
						<li><a href="${nav1.url}" target="ifm">${nav1.navName}</a>
					</c:if>
					<c:if test="${nav1.navName!='首页'}">
						<li><a href="#">${nav1.navName}</a>
					</c:if>
					<%-- <div class="answer_wrap">
						<span class="square fix"></span>
						<div class="answer">
							<ul>
								<c:forEach items="${nav1.navigationList}" var="nav2">
									<li><a href="${ctx}/${nav2.url}" target="ifm" >${nav2.navName}</a></li>
								</c:forEach>
							</ul>
						</div>
					</div> --%>
					<dl class="childMenu clearfix">
						<c:forEach items="${nav1.navigationList}" var="nav2">
							<dd><a href="${ctx}/${nav2.url}" target="ifm" >${nav2.navName}</a></dd>
						</c:forEach>
					</dl>
				</c:forEach>
			</ul>
		</div>
		<div class="clear"></div>
		<div class="container" id="ifmBox">
			<!-- 加载内容放这里 -->
			<iframe id="ifm" name="ifm" scrolling="auto"
				frameborder="0" src="${ctx}${url}"> </iframe>
		</div>
	</div>

</body>
</html>
