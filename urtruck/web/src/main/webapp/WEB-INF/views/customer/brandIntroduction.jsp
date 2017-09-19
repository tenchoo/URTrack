<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></title>
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

<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery.cycle.all.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/dateRange.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/common.js"></script>
</head>

<body>

    <div class="header bg02">
        <div class="centerBox">
            <a href="javascript:;" class="logo"></a>
            <dl class="nav">
                <dt class=""><a href="${ctx}/customerQuery/loginSuccessIndex"><fmt:message bundle='${pageScope.bundle}'  key='home.page' /></a></dt>
                <dt class="navPro"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></a></dt>
                <dt class="navFlow current"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='phone.data.service' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.connection.platform' /></a></dt>
                <dt><a href="${ctx}/customerQuery/toBrandIntroduction"><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></a></dt>
                <dt class="account">
                	<a href="${ctx}/customerQuery/toExit" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='quit' /></a>
                </dt>
                <dd class="pro hide">
                    <div class="proInner">
                        <ul class="proSlide">
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' />/ PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' />/ PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
                </dd>
                <dd class="flow hide">
                	<a class="" href=""><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></a>
                	<a href="${ctx}/customerQuery/toRateSearch2"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></a>
                	<a href="${ctx}/customerQuery/toRecharge"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a>
                </dd>
            </dl>
        </div>
    </div>
    
    <div class="centerBox">
        
        <div class="pinpai">
        	<div class="title01">Brand <fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></div>
            <img src="${pageContext.request.contextPath}/static/toWeb/images/banner_3.jpg" width="1200" height="624" style="margin-bottom:50px;display:block;" />
            <div class="text01"><fmt:message bundle='${pageScope.bundle}'  key='company.introduction' />：</div>
            <div class="text02"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect.Co., Ltd.is.a.wholly.owned.subsidiary.of.Lenovo.Capital.and.Incubator.Group, which.was.mainly.focused.on.building.a.new.intelligent.internet.platform.and.Promoting.the.development.process.of.the.global.intelligence.network.to.create.a.better.intelligent.life.that.understand.you.by.using.shared.economic.rules.to.connect.global.telecom.operators, devices.and.life.service.resources. Lenovo.Connect.has.set.up.subsidiaries.in.Hongkong, Europe.and.the.United.States, which.served.users.in.China.and.many.countries.and.regions.around.the.world. Lenovo.Connect.is.providing.a.variety.of.products.and.customized.solutions.including.diversified.etwork.connectivity, communication.services, value.added.service.and.ICT.devices.and.corporation.ICT.solutions.to.telecom.operators.and.corporate.customers.in.China.and.globally.' /></div>
            <!--<div class="text01">公司的名称：</div>
            <div class="text02">Lenovo Connect 品牌slogan<br />Be different,Be better 喜欢自由自在</div>-->
            <div class="text01"><fmt:message bundle='${pageScope.bundle}'  key='The.vision.of.company' />：</div>
            <div class="text02"><fmt:message bundle='${pageScope.bundle}'  key='Focused.on.building.a.new.intelligent.internet.platform.and.Promoting.the.development.process.of.the.global.intelligence.network.to.create.a.better.intelligent.life.that.understand.you.by.using.shared.economic.rules.to.connect.global.telecom.operators, devices.and.life.service.resources. ' /></div>
        </div>
    
    </div>

	<div class="footer">
    	<div class="logoBox">
        	<div class="footLogo"></div>
            <div class="share">
            	<a href="javascript:;" class="sina"><fmt:message bundle='${pageScope.bundle}'  key='click.for.attention' /></a>
                <span class="qrcode01"><fmt:message bundle='${pageScope.bundle}'  key='WeChat.Official.Accounts' /></span>
                <!--<span class="qrcode02">APP下载</span>-->
            </div>
        </div>
        <div class="copyright">©2017 Lenovo Connect all right reserved</div>
    </div>

    $(function(){    
		
		$('.radio').click(function(){
			$('.text04 dd span.m').text($(this).find('input').val());
		});

    });
    </script> 
</body>
</html>
