<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="nav navbar-fixed-top row clerafix">
	<div class="nav_wrap">
		<h1 class="pull-left col-xs-8">
		<c:if test="${empty logo}">
			<img src="static/newH5/images/nav_logo.png" class="img-responsive" >
		</c:if>
		<c:if test="${logo ==1}">
			<img src="static/newH5/images/vinci_logo.jpg" class="img-responsive" >
		</c:if>
		<c:if test="${logo ==2}">
            <img src="static/newH5/images/nav_logo.png" class="img-responsive" >
        </c:if>
		</h1>
		<span class="pull-right menu_btn"><img src="static/newH5/images/nav_bar.png" class="img-responsive"></span>
		<div class="menu_wrap">
			<img src="static/newH5/images/sj_icon.png" class="sj">
			<ul class="menu">
				<li onclick="window.location.href='glaH5AppPay/toChargeView'"><img src="static/newH5/images/cz_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></li>
				<li onclick="window.location.href='glaH5AppQuery/index'"><img src="static/newH5/images/search_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></li>
				<!-- <li onclick="window.location.href='glaH5App/helper'"><img src="static/newH5/images/hepple_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='help' /></li>
				<li onclick="window.location.href='glaH5AppActive/index'"><img src="static/newH5/images/jh_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='Activate new device' /></li> 
				<li onclick="window.location.href='glaH5AppQuery/queryRecord'"><img src="static/newH5/images/dd_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='' /></li>-->
				<c:if test="${empty sessionUser}">
					<li onclick="window.location.href='glaH5App/login'"><img src="static/newH5/images/login_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='sign.in' /></li>
				</c:if>
				<c:if test="${not empty sessionUser}">
					<li onclick="window.location.href='glaH5App/loginout'"><img src="static/newH5/images/login_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='Switch.account' /></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>
<script>
      var color = null;
      var image = null;
      $(function(){
            <c:if test="${logo ==2}">
                <c:if test="${not empty webDesign}">
                    color = "${webDesign.color}";
                    image = "${webDesign.image}";
                    alert(color + ":" + image);
                    if(color != null && color != ""){
                        $('.nav').css({'border-bottom' : '1px solid #' + color});
                        $('.menu_wrap').css({'border' : '2px solid #' + color});
                        newCss = '{color:#'+color+';border:2px solid #' + color+'}';
                        $('.active_k').css({'color' : '#' + color});
                        $('.active_k').css({'border' : '2px solid #' + color});
                        $('.active_login').css({'background' : '#' + color});
                        $('.ts').css({'color' : '#' + color});
                    }
                    if(image != null && image != ""){
                        $('h1').find('img').attr('src','/static/webDesign/img/'+image);
                        $('.col-xs-8').css({'line-height':'40px'});
                    }
                </c:if>
            </c:if>
        });
</script>
