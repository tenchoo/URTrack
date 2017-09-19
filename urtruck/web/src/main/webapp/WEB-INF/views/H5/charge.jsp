<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Internet.recharge.through.lenovo.connect' /></title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>

<style>
* {
	font-family: "冬青黑体", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}

input {
	background-color: transparent;
}

select {
	-webkit-appearance: menulist;
	align-items: center;
	white-space: pre;
	-webkit-rtl-ordering: logical;
	background-color: transparent;
	cursor: default;
	border: none;
	font-size: 3.8vw;
}
</style>

<script src="static/h5/js/main.js"></script>

<script src="static/h5/js/wow.min.js"></script>
<link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
<script>
	new WOW().init();
</script>
<script>
	new WOW().init();
</script>
<style>
button {
	font-size: :4vw
}
</style>

</head>
<body>
	<div style="background-color: #f4f3f9; font-size: 3.8vw;">
		<jsp:include page="header.jsp" flush="true"/> 

		<form class="alipay_content" action="paymentService/toComfirm" method="post" id="chargeForm">
			<input type="hidden" name="payName" value="alipay_directPay">
            <input id="alipay_payAmount" type="hidden" name="payAmount" >
            <input id="alipay_flowSize" type="hidden" name="flowSize" >
		<div
			style="background-color: #ffffff; border-radius: 0px; padding: 0vw; margin: 1rem auto;padding-bottom: 0.5rem"
			class="">

			<div style="margin: 0vw 0;" class="row">
				<div class="col-xs-12 ">
					<p style="margin: auto 3vw"></p>
				</div>
				<div style="padding-left: 0.8rem; padding-top: 1rem; padding-bottom:0.2rem;" class="col-xs-12 ">
					<div>
						<label style="margin: auto 1rem"><fmt:message bundle='${pageScope.bundle}'  key='Device.number' /></label> 
						    <c:if test="${empty userInfoList}">
					               	  <fmt:message bundle='${pageScope.bundle}'  key='Your.Lenovo.account.has.not.been.linked' />
		                   </c:if>
						   <c:if test="${not empty userInfoList}">
								<select style="width: 58vw; margin: auto 4vw" id="iccid" name="iccid">
									<c:forEach items="${userInfoList}" var="item">
										<option value="${item.iccid}">${item.iccid}</option>
									</c:forEach>
								</select>
						   </c:if>
					</div>
				</div>

			</div>
			<div class="" style="height:2rem;">
				<p
					style="padding-left: 2rem; padding-top: 0.5rem; margin-bottom: 1rem; font-size: 3.3vw; color: #8c8c8c;"><fmt:message bundle='${pageScope.bundle}'  key='Tip.select.correct.equipment.number.to.avoid.false.charge' /></p>
			</div>
		</div>
		<div style="background-color: #ffffff">
			<div
				style="background-color: #ffffff; margin: 3vw; margin-left: 0; padding: 3vw; padding-left: 2px; padding-bottom: 0; margin-bottom: 0"
				class="row">
				<div style="padding-left: 1.8rem;width:210px;" class="col-xs-6 ">
					<p> <fmt:message bundle='${pageScope.bundle}'  key='data.plan' /><span id="errorMsg" style="padding-left: 3.3rem; padding-top: 0.5rem; margin-bottom: 1rem; font-size: 3.3vw; color: red;"></span>
					</p>
				</div>
			</div>
			<div style="margin: 3vw; padding: 3vw; margin-top: 3px" class="row" id="flows_content">
				<c:if test="${not empty flowConfigList}">
					<c:forEach items="${flowConfigList}" var="flowConfig">
	                    	<c:if test="${flowConfig.flowsize=='1G' }">
		                    	<div class="col-xs-6 ">
									<a href="javascript:void(0);" class="thumbnail" flowsize="${flowConfig.flowsize }" payAmount="${flowConfig.price }" data-id="${flowConfig.price }"> <img src="static/h5/images/bag1G.png"></a>
								</div>
	                        </c:if>
	                        <c:if test="${flowConfig.flowsize=='10G' }">
	                        	<div class="col-xs-6 ">
									<a href="javascript:void(0);" class="thumbnail" flowsize="${flowConfig.flowsize }" payAmount="${flowConfig.price }" data-id="${flowConfig.price }"> <img src="static/h5/images/bag10G.PNG"></a>
								</div>
	                  	   </c:if>
	                       <c:if test="${flowConfig.flowsize=='3个4G' }">
	                       		<div class="col-xs-6 ">
									<a href="javascript:void(0);" class="thumbnail" flowsize="${flowConfig.flowsize }" payAmount="${flowConfig.price }" data-id="${flowConfig.price }"> <img src="static/h5/images/bag444G.png"></a>
								</div>
	                  	   </c:if>
	                 </c:forEach>
                 </c:if>
			</div>
			<nav style="height: 50px" class="navbar navbar-default navbar-fixed-bottom">
			<div style="margin: 0; padding: 0; width: 100%; height: 50px;padding-right:0px !important;"
				class="container">
				<div style="margin: 0vw; width: 100%; height: 50px" class="row">
					<div
						style="color: black; text-align: right; padding-right: 0; margin: 1.5rem auto;width:60px;"
						class="col-xs-2"><fmt:message bundle='${pageScope.bundle}'  key='subtotal' />：</div>
					<div
						style="color: red; text-align: left; padding-left: 0; margin: 1.5rem auto;width:50px;"
						class="col-xs-1" id="showPrice"></div>
					<div style="float:right;border-left: 1px solid #dfe4e1; background-color: #eb641a;height: 50px; margin-bottom: 0vw; text-align: center; padding: auto 0vw; margin-left:-4px"
						class="col-xs-4 col-xs-offset-5 ">
						<a href="javascript:void(0);"  id="submit">
							<h5 style="font-size: 3.8vw; color: #ffffff; margin-left: 0px;line-height: 50px; ">
								<fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' />
							</h5>
						</a>
					</div>
				</div>
			</div>
			</nav>
		</div>
		</form>
	</div>
	<script>
	$(function(){
		 /*单选菜单*/
       $('#flows_content a').click(function(){
    	   $("#alipay_flowSize").val($(this).attr("flowsize"));
    	   $("#alipay_payAmount").val($(this).attr("payAmount"));
    	   $("#showPrice").text($(this).attr('data-id'));
       });
       $('#submit').click(function(){ 
    	   var checked = $("#alipay_payAmount").val();
		   if(checked == ''){
			  $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='please.select.data.plan' />")
			  return;
		   }
		   var iccid = $("#iccid").find("option:selected").text();
		   if(iccid == ''){
			   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.bind.device.number' />")
			   return;
		   }
		   $("#chargeForm").submit();
		   
	})
    	  
		 
	});
	
	</script>
</body>
</html>