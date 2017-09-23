
﻿﻿<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<%-- <LINK rel="Bookmark" href="${ctx}/static/images/favicon.ico" >
<LINK rel="Shortcut Icon" href="${ctx}/static/images/favicon.ico" /> --%>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title><fmt:message bundle='${pageScope.bundle}'  key='Background.login' />-<fmt:message bundle='${pageScope.bundle}'  key='Connected.car ' /></title>
<meta name="keywords" content="H-ui.admin v2.3,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v2.3，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<header class="Hui-header cl">
	<ul class="Hui-userbar">
		<li><fmt:message bundle='${pageScope.bundle}'  key='super.administrator' /></li>
		<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A">jiliadmin <i class="Hui-iconfont">&#xe6d5;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='Personal.information' /></a></li>
				<li><a href="#"><fmt:message bundle='${pageScope.bundle}'  key='Change.account' /></a></li>
				<li><a href="${ctx}/login/logout"><fmt:message bundle='${pageScope.bundle}'  key='quit' /></a></li>
			</ul>
		</li>
		<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>
		<li id="Hui-skin" class="dropDown right dropDown_hover"><a href="javascript:;" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
			<ul class="dropDown-menu radius box-shadow">
				<li><a href="javascript:;" data-val="default" title="默认（黑色）"><fmt:message bundle='${pageScope.bundle}'  key='default.black' /></a></li>
				<li><a href="javascript:;" data-val="blue" title="蓝色"><fmt:message bundle='${pageScope.bundle}'  key='blue' /></a></li>
				<li><a href="javascript:;" data-val="green" title="绿色"><fmt:message bundle='${pageScope.bundle}'  key='green' /></a></li>
				<li><a href="javascript:;" data-val="red" title="红色"><fmt:message bundle='${pageScope.bundle}'  key='red' /></a></li>
				<li><a href="javascript:;" data-val="yellow" title="黄色"><fmt:message bundle='${pageScope.bundle}'  key='yellow' /></a></li>
				<li><a href="javascript:;" data-val="orange" title="绿色"><fmt:message bundle='${pageScope.bundle}'  key='orange' /></a></li>
			</ul>
		</li>
	</ul>
	<a aria-hidden="false" class="Hui-nav-toggle" href="#"></a> </header>
<aside class="Hui-aside">
	<input runat="server" id="divScrollValue" type="hidden" value="" />
	<div class="menu_dropdown bk_2">
		<dl id="menu-page">
			<dt><i class="Hui-iconfont">&#xe626;</i><fmt:message bundle='${pageScope.bundle}'  key='Ordering.information' /><i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${ctx}/tradeQuery/toList" href="javascript:void(0)"><fmt:message bundle='${pageScope.bundle}'  key='Ordering.history.list' /></a></li>
					<li><a _href="page-flinks.html" href="javascript:void(0)"><fmt:message bundle='${pageScope.bundle}'  key='Ordering.product.list' /></a></li>
					<li><a _href="${ctx}/tradeQuery/toResList" href="javascript:void(0)"><fmt:message bundle='${pageScope.bundle}'  key='Ordering.resource.list' /></a></li>
					<li><a _href="page-flinks.html" href="javascript:void(0)"><fmt:message bundle='${pageScope.bundle}'  key='Links' /></a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments">
			<dt><i class="Hui-iconfont">&#xe616;</i><fmt:message bundle='${pageScope.bundle}'  key='Integrated.query ' /><i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${ctx}/bill/showBillList" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Bills' /></a></li>
					<li><a _href="${ctx}/paylog/showList" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Payment.history ' /></a></li>
					<li><a _href="${ctx}/accountDeposit/showList" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='account.book.balance.query' /></a></li>
					<li><a _href="${ctx}/settle/showList" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='List.settlement.confirmation.query' /></a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-comments">
			<dt><i class="Hui-iconfont">&#xe616;</i><fmt:message bundle='${pageScope.bundle}'  key='Statement.of.statistics' /><i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a _href="${ctx}/report/dayReport" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Daily.income.statement' /></a></li>
					<li><a _href="${ctx}/bill/receivableBill" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Monthly.income.statement' /></a></li>
				</ul>
			</dd>
		</dl>
	</div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active"><span title="欢迎使用" data-href="welcome.jsp"><fmt:message bundle='${pageScope.bundle}'  key='Welcome' /></span><em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${ctx}/login/welcome"></iframe>
		</div>
	</div>
</section>
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script> 
<script type="text/javascript">
/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
<script type="text/javascript">
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s)})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>