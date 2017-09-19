<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:if test="${empty logo}">
	<h1 class="logo"><img src="static/newH5/images/logo.png" class="img-responsive"></h1>
</c:if>
<c:if test="${logo ==1}">
	<h1 class="logo"><img src="static/newH5/images/vinci_logo.jpg" class="img-responsive"></h1>
</c:if>
<c:if test="${logo ==2}">
    <h1 class="logo"><img src="static/newH5/images/logo.png" class="img-responsive"></h1>
</c:if>

<script>
$(function(){
    <c:if test="${logo ==2}">
        <c:if test="${not empty webDesign}">
            var color = "${webDesign.color}";
            var image = "${webDesign.image}";
            alert(color + ":" + image)
            if(color != null && color != ""){
                $('.active_login').css({'background' : '#' + color});
                $('.ts').css({'color' : '#' + color});
                $('.yzm').css({'background' : '#' + color});
            }
            if(image != null && image != ""){
                $('h1').find('img').attr('src','/static/webDesign/img/'+image);
                $('h1').find('img').css({'align': 'center'});
            }
        </c:if>
    </c:if>
});
</script>
