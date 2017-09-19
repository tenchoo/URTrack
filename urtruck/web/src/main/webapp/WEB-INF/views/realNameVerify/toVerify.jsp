<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=7">
    <meta name="author" content="">
    <meta name="robots" content="all">
    <meta name="Copyright" content="lenovo">
    <title><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' /></title>
    <link rel="stylesheet" type="text/css" href="/myaccount/css/global-new.css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    
</head>

<body>

<div class="con cf">
    <div class="wrapper oh">
        <ul class="aside" id="aside">
            <li class="nav_aside"><a title="实名认证"  class="aside_li_a" href="/realname/real_name_verify_for_web.xhtml"><fmt:message bundle='${pageScope.bundle}'  key='real.name.verify' /></a></li>
            <!-- <li ><a title="充值记录"  class="aside_li_a " href="/account/account.xhtml" >充值记录</a></li> -->
           
        </ul>
     
        
		 <div class="content">
             <!--实名认证             -->
             <div class="real wrap">
                 <c:if test="${dto.verifystatus==1}">
                 	<label onclick="localtion.href='${ctx}/realnameVerify/toVerify'"><fmt:message bundle='${pageScope.bundle}'  key='National.citizen.certification.failed,click.re-certification' /></label>
                 </c:if>
                 <c:if test="${dto.verifystatus==4}">
                 	<label onclick="localtion.href='${ctx}/realnameVerify/toVerify'"><fmt:message bundle='${pageScope.bundle}'  key='Manual.review.does.not.pass,click.re-certification' /></label>
                 </c:if>
                 <c:if test="${dto.verifystatus==2}">
                 	<label><fmt:message bundle='${pageScope.bundle}'  key='National.citizen.certification.pass,wait.for.manual.review' /></label>
                 </c:if>
                 <c:if test="${dto.verifystatus==3}">
                 	<label><fmt:message bundle='${pageScope.bundle}'  key='Manual.review.approval' /></label>
                 </c:if>
             </div>
		</div>
	</div>
</div>

</div>




</body>
</html> 