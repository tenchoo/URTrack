﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%-- <LINK rel="Bookmark" href="${ctx}/static/images/favicon.ico" >
<LINK rel="Shortcut Icon" href="${ctx}/static/images/favicon.ico" /> --%>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/skin/default/skin.css" rel="stylesheet"
	type="text/css" id="skin" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">	
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>Lenovo Connect GLA</title>
<meta name="keywords"
	content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description"
	content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
	<header class="Hui-header cl topMenu" id="header">
		<%--<ul class="Hui-userbar">
		<li>${user.roles[0].name}</li>
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">${user.nickname} <i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#">个人信息</a></li>
				<li><a href="#">切换账户</a></li>
				<li><a href="${ctx}/login/logout">退出</a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
				<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
				<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色">橙色</a></li>
			</ul>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a>  --%>
		<div class="LeftMenu fl">
			<div class="mainLeft" onClick="location.href='#';">
				<div class="mainlogo">
					<div class="logo">
						<img src="${ctx}/static/ui/images/logo.png">
					</div>
				</div>
			</div>
		</div>
		<div class="rightContent">
			<div class="container-fluid">
				<div class="row navHeight">
					<div class="hedLeft fl">
						<div class="clearfloat">
							<%-- <div class="redli redlis fl" onClick="location.href='${ctx}/login/welcome/index';">首页</div> --%>
							<div class="redli fl active" onClick="location.href='#';">GLA</div>
							<!-- <div class="redli redlia fl" onClick="location.href='#';">
								商品中心<span class="arrow"></span>
								<ul class="hvr">
									<li onClick="location.href='#';">产品配置</li>
									<li onClick="location.href='#';">产品管理</li>
									<li onClick="location.href='#';">产品上架</li>
								</ul>
							</div>
							<div class="redli redlia fl" onClick="location.href='#';">
								客户中心<span class="arrow"></span>
								<ul class="hvr">
									<li onClick="location.href='#';">产品配置</li>
									<li onClick="location.href='#';">产品管理</li>
									<li onClick="location.href='#';">产品上架</li>
								</ul>
							</div> -->
						</div>
					</div>
					<div class="hedRight fr">
						<div class="clearfloat">
						<c:if test="${user.fstStruct != undefined}">
					   	<h1 class="fl"><fmt:message bundle='${pageScope.bundle}'  key='Organizational.structure' />：${user.fstStruct}</h1>
						</c:if>
						<c:if test="${user.secStruct != undefined}">
					   	<h1 class="fl"><fmt:message bundle='${pageScope.bundle}'  key='Secondary.organization.structure' />：${user.secStruct}</h1>
						</c:if>
							<h1 class="fl">${user.nickname}</h1>
							<a href="${ctx}/login/logout" class="exit fr"><fmt:message bundle='${pageScope.bundle}'  key='Log.out' /></a>
						</div>
					</div>
				</div>
			</div>
		</div>

	</header>
	<aside class="Hui-aside LeftMenu fl">
		<input runat="server" id="divScrollValue" type="hidden" value="" />
		<div class="menu_dropdown bk_2 contenThree">
			<div class="part1" id="part1">
				<c:forEach items="${navList}" var="nav1">
					<%-- <dl id="menu-picture">
						<dt>
							<i class="Hui-iconfont"><img
											src="${ctx}/static/ui/images/${nav1.navPicture}"
											class="img-responsive"></i> ${nav1.navName}<i
								class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
						</dt>
						<dd>
							<ul>
								<c:forEach items="${nav1.navigationList}" var="nav2">
									<li><a _href="${ctx}/${nav2.url}" href="javascript:void(0)">${nav2.navName}</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl> --%>
					<dl id="menu-picture" class="leftContent">
						<dt class="clickz">
							<span class="user fl"><img src="${ctx}/static/ui/images/${nav1.navPicture}" class="img-responsive"></span> ${nav1.navName}
							<a class="arroleft arr fr"></a>
							<%-- <i class="Hui-iconfont"><img src="${ctx}/static/ui/images/${nav1.navPicture}" class="img-responsive"></i> 
							${nav1.navName}
							<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i> --%>
						</dt>
						<dd class="subContent">
							<ul>
								<c:forEach items="${nav1.navigationList}" var="nav2">
									<li><a _href="${ctx}/${nav2.url}" href="javascript:void(0)">${nav2.navName}</a></li>
								</c:forEach>
							</ul>
						</dd>
					</dl>
					
				</c:forEach>
			</div>
		</div>
	</aside>
	<!-- <div class="dislpayArrow">
		<a class="pngfix" href="javascript:void(0);"
			onClick="displaynavbar(this)"></a>
	</div> -->
	<section class="Hui-article-box rightContent">
		<div class="container-fluid">
			 <div id="Hui-tabNav" class="Hui-tabNav">
				<div class="Hui-tabNav-wp commonText row"> 
						
					<ul id="min_title_list" class="acrossTab cl">
						<li class="active"><span class="home"></span><span title="我的桌面" data-href="welcome.jsp">${title}</span><em></em></li>
					</ul>
				
				</div> 
				<!--<div class="Hui-tabNav-more btn-group">
					<a id="js-tabNav-prev" class="btn radius btn-default size-S"
						href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a
						id="js-tabNav-next" class="btn radius btn-default size-S"
						href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
				</div>
			</div> -->
			
			<div id="iframe_box" class="Hui-article">
				<div class="show_iframe">
					<div style="display: none" class="loading"></div>
					<iframe class="c-c-iframe" scrolling="yes" frameborder="0" src="${ctx}${url}"></iframe>
				</div>
			</div>
		</div>
	</section>
	<!--footer starts-->
	<!-- <div class="footerBottom" id="footer">
		<h1 class="text-center">Copyright 懂的通信 by GLA v1.0</h1>
	</div> -->
	<!--footer ends-->
	<script type="text/javascript"
		src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script type="text/javascript">
	/* if($("#min_title_list li").class=='active'){
		console.log(111)
		$(this).addClass("newColor")
	} */
	/* $("#min_title_list li").on("click",function(){
		$(this).addClass("newColor")
	}) */
		
	</script>
	<script type="text/javascript">
		/*资讯-添加*/
		function article_add(title, url) {
			var index = layer.open({
				type : 2,
				title : title,
				content : url
			});
			layer.full(index);
		}
		/*图片-添加*/
		function picture_add(title, url) {
			var index = layer.open({
				type : 2,
				title : title,
				content : url
			});
			layer.full(index);
		}
		/*产品-添加*/
		function product_add(title, url) {
			var index = layer.open({
				type : 2,
				title : title,
				content : url
			});
			layer.full(index);
		}
		/*用户-添加*/
		function member_add(title, url, w, h) {
			layer_show(title, url, w, h);
		}
	</script>
	<script type="text/javascript">
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
			var s = document.getElementsByTagName("script")[0];
			s.parentNode.insertBefore(hm, s)
		})();
		var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://"
				: " http://");
		document
				.write(unescape("%3Cscript src='"
						+ _bdhmProtocol
						+ "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
	</script>
	<script type="text/javascript">  

/* $(function(){  
    $("#c-c-iframe").load(function(){         
        $(this).height($(this).contents().find("#content").height()+40);  
       
    });  
      
});  */ 
/* $(".subContent ul li a").on('click',function(){
	$('iframe').addClass('c-c-iframe')
}) */
function windowHeight() {
    var de = document.documentElement;
    return self.innerHeight||(de && de.clientHeight)||document.body.clientHeight;
}
window.onload=window.onresize=function(){
	 var wh=windowHeight();
	 document.getElementById("part1").style.height=document.getElementsByClassName("c-c-iframe")[0].style.height=(wh-document.getElementById("header").offsetHeight-document.getElementById("Hui-tabNav").offsetHeight)+"px"
	}
</script> 

</body>
</html>
