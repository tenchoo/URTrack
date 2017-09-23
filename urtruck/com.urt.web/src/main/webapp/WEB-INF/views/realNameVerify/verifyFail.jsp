<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="author" content="">
    <meta name="robots" content="all">
    <meta name="Copyright" content="lenovo">
    <title><fmt:message bundle='${pageScope.bundle}'  key='Authentication.failed' /></title>
    <link rel="stylesheet" type="text/css" href="/myaccount/css/global-new.css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
</head>
<body>



<div class="con cf">
    <div class="wrapper oh">
        <ul class="aside" id="aside">
            <li class="nav_aside"><a title="实名认证"  class="aside_li_a" href="/realname/real_name_verify_for_web.xhtml"><fmt:message bundle='${pageScope.bundle}'  key='real.name.verify' /></a></li>
            <!-- <li ><a title="激活新设备"  class="aside_li_a" href="">激活新设备</a></li> -->
            <li ><a title="充值记录"  class="aside_li_a " href="/account/account.xhtml" ><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></a></li>
        </ul>
		 <div class="content">
             <!--认证失败-->
             <div class="error_con">
                 <img src="/myaccount/img/breakBg.png">
                 <h3><fmt:message bundle='${pageScope.bundle}'  key='certificate.information.authentication.failed' />！</h3>
                 <p><fmt:message bundle='${pageScope.bundle}'  key='We.will.verify.your.uploaded.photos.within.5.working.days' /></p>
             </div>
        </div>
	</div>
</div>
</body>
</html>