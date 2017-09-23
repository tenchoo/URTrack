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
<title><fmt:message bundle='${pageScope.bundle}'  key='home.page' /></title>
<link href="${ctx}/static/toWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/static/toWeb/css/dateRange.css" rel="stylesheet" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
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

    <div class="header">
        <div class="centerBox">
            <a href="javascript:;" class="logo"></a>
            <dl class="nav">
                <dt class="current"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='home.page' /></a></dt>
                <dt class="navPro"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></a></dt>
                <dt class="navFlow"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='phone.data.service' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.connection.platform' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></a></dt>
                <dt class="account">
                    <a href="javascript:;" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='registration' /></a>
                    &nbsp;/&nbsp;
                    <a href="${ctx}/customerQueryWeb/loginSuccess" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='sign.in' /></a>
                </dt>
                <dd class="pro hide">
                    <div class="proInner">
                        <ul class="proSlide">
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
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
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
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
                	<a class="" href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></a>
                	<a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></a>
                	<a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a>
                </dd>
            </dl>
        </div>
    </div>
    
    <div class="frontBanner">
        <ul>
            <li><img src="${pageContext.request.contextPath}/static/toWeb/images/banner_1.jpg" alt=""></li>
            <li><img src="${pageContext.request.contextPath}/static/toWeb/images/banner_2.jpg" alt=""></li>
            <li><img src="${pageContext.request.contextPath}/static/toWeb/images/banner_1.jpg" alt=""></li>
            <li><img src="${pageContext.request.contextPath}/static/toWeb/images/banner_2.jpg" alt=""></li>
        </ul>
        <div class="frontBannerNav"></div>
    </div>

    <div class="slideBox">
        <dl class="slideElem lianTong">
            <dt class="title">
                <span class="text"><fmt:message bundle='${pageScope.bundle}'  key='Unicom.data.recharge' /></span>
                <span class="arrow">
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
                </span>
            </dt>
            <dd>
                <ul class="lianTongSlide">
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_1.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_2.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_3.png" width="147" height="103" /></span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_1.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_2.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_1_3.png" width="147" height="103" /></span>
                        </a>
                    </li>
                </ul>
            </dd>
        </dl>

        <dl class="slideElem yiDong">
            <dt class="title">
                <span class="text"><fmt:message bundle='${pageScope.bundle}'  key='chinamobile.data.recharge' /></span>
                <span class="arrow">
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
                </span>
            </dt>
            <dd>
                <ul class="yiDongSlide">
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_1.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_2.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_3.png" width="147" height="103" /></span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_1.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_2.png" width="147" height="103" /></span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/card_2_3.png" width="147" height="103" /></span>
                        </a>
                    </li>
                </ul>
            </dd>
        </dl>
    </div>
	
    <div class="starProBg">
        <dl class="starPro">
            <dt class="title"></dt>
            <dd>
                <ul class="starProSlide">
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_1.png" width="210" height="160" /></span>
                            <span class="text01"><b><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /></b></span>
                            <span class="text02"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' />Air12-6Y30WiFi</span>
                            <span class="text03"><fmt:message bundle='${pageScope.bundle}'  key='purchase.a.mobile.phone.to.get.a.free.bag' /></span>
                            <span class="text04">￥ 4231</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_2.png" width="210" height="160" /></span>
                            <span class="text01"><b>Miix</b></span>
                            <span class="text02">拯救者I5window10</span>
                            <span class="text03">下单即送游戏键鼠套装<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 5499</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_3.png" width="210" height="160" /></span>
                            <span class="text01"><b>Yoga Pad</b></span>
                            <span class="text02">ThinkPad 黑将<fmt:message bundle='${pageScope.bundle}'  key='' />S5</span>
                            <span class="text03">黑将临世生为竞技<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 6699</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_4.png" width="210" height="140" /></span>
                            <span class="text01"><b>Tablet3</b></span>
                            <span class="text02">小新<fmt:message bundle='${pageScope.bundle}'  key='' />Air13ProI5window</span>
                            <span class="text03">4G大显存升级固态硬盘<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 5399</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_5.png" width="210" height="160" /></span>
                            <span class="text01"><b>随身MIFI<fmt:message bundle='${pageScope.bundle}'  key='' /></b></span>
                            <span class="text02">ZUKZ2Pro 尊享版<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text03">购机既送新秀丽单件背包<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 2298</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_1.png" width="210" height="160" /></span>
                            <span class="text01"><b>小新<fmt:message bundle='${pageScope.bundle}'  key='' /></b></span>
                            <span class="text02">小新<fmt:message bundle='${pageScope.bundle}'  key='' />Air12-6Y30WiFi</span>
                            <span class="text03">购机既送新秀丽单件背包<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 4231</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="images/pro_2_2.png" width="210" height="160" /></span>
                            <span class="text01"><b>Miix</b></span>
                            <span class="text02">拯救者<fmt:message bundle='${pageScope.bundle}'  key='' />I5window10</span>
                            <span class="text03">下单即送游戏键鼠套装<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 5499</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_3.png" width="210" height="160" /></span>
                            <span class="text01"><b>Yoga Pad</b></span>
                            <span class="text02">ThinkPad 黑将<fmt:message bundle='${pageScope.bundle}'  key='' />S5</span>
                            <span class="text03">黑将临世生为竞技<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 6699</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_4.png" width="210" height="140" /></span>
                            <span class="text01"><b>Tablet3</b></span>
                            <span class="text02">小新<fmt:message bundle='${pageScope.bundle}'  key='' />Air13ProI5window</span>
                            <span class="text03">4G大显存升级固态硬盘<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 5399</span>
                        </a>
                        <a href="javascript:;">
                            <span class="img"><img src="${pageContext.request.contextPath}/static/toWeb/images/pro_2_5.png" width="210" height="160" /></span>
                            <span class="text01"><b>随身MIFI<fmt:message bundle='${pageScope.bundle}'  key='' /></b></span>
                            <span class="text02">ZUKZ2Pro 尊享版<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text03">购机既送新秀丽单件背包<fmt:message bundle='${pageScope.bundle}'  key='' /></span>
                            <span class="text04">￥ 2298</span>
                        </a>
                    </li>
                </ul>
                <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
            </dd>
        </dl>
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

	<script type="text/javascript">
    $(function(){    

        $('.frontBanner ul').cycle({ 
            fx:'fade', 
            speed:1000, 
            timeout:5000, 
            pager:'.frontBannerNav',
			pauseOnPagerHover:1
        });

        $('.lianTongSlide').cycle({ 
            fx:'scrollHorz', 
            speed:1000, 
            timeout:4000,
			prev:'.lianTong .arrowL',
			next:'.lianTong .arrowR'
        });

        $('.yiDongSlide').cycle({ 
            fx:'scrollHorz', 
            speed:1000, 
            timeout:4000,
			prev:'.yiDong .arrowL',
			next:'.yiDong .arrowR'
        });

        $('.starProSlide').cycle({ 
            fx:'scrollHorz', 
            speed:1000, 
            timeout:4000,
			prev:'.starPro .arrowL',
			next:'.starPro .arrowR'
        });


    });
    </script> 
</body>
</html>
