<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
<meta name="viewport"
	content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Balance.inquiry' /> - <fmt:message bundle='${pageScope.bundle}'  key='Go.to.the.website.through.Lenovo.Connect' /></title>
<!-- 新 Bootstrap 核心 CSS 文件<img style="position:absolute;margin-left: 20vw;margin-right: ;top:15rem;width:60vw;height:60vw;z-Index:11; " src="images/back.png" /> -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css"
	type="text/css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css"
	type="text/css">
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
<script src="static/h5/js/jquery-1.12.4.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
<script src="static/h5/js/bootstrap.min.js"></script>
<link href="static/h5/css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script src="static/h5/js/wow.min.js"></script>
<link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
<script>
	new WOW().init();
</script>
<script>
	new WOW().init();
</script>
<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
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
}
</style>

<script src="static/h5/js/url.js"></script>
<script src="static/h5/js/layer/layer.js"></script>
<script src="static/h5/js/flowCount.js"></script>

<script type="text/javascript">
	$(function() {
		var error_box=$(".error-box");
		function request(jsonData) {
			var msg = layer.msg('<fmt:message bundle="${pageScope.bundle}"  key="Data.loading" />', {
				icon : 16,
				time : 20000, //20秒关闭（如果不配置，默认是3秒）
				area : [ '182px', '66px' ],
				offset : '215px'
			});
			$.ajax({
				type : "post",
				url : "/queryService/queryFlow",
				data : jsonData,
				cache : false,
				success : function(data) {
					var jsonObj = eval("(" + data + ")");
					  var statusCode=parseInt(jsonObj.retcode);
                      switch (statusCode) {
                          case 1:
                        	  $.flowShow($, jsonObj);
                        	  break;
                          case 2:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='The.device.has.been.activated' />");
                              break;
                          case -1:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='Parameter.incomplete' />");
                              break;
                          case -2:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='Service.verification.failed.Please.login.again' />");
                              break;
                          case -3:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='Failed.to.obtain.current.plan' />");
                              break;
                          case -4:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='The.device.number.does.not.exist.or.is.incorrect' />");
                              break;
                          case -5:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='Signature.error' />");
                              break;
                          case -6:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='iccid.has.been.bound.by.another.account' />");
                              break;
                          case -7:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='The.device.number.does.not.exist.or.is.incorrect' />");
                              break;
                          case -8:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='The.activation.code.does.not.match.the.device.number' />");
                              break;
                          case -9:
                        	  alert("<fmt:message bundle='${pageScope.bundle}'  key='iccid.Equipment.is.not.belong.to.mifi.equipment' />");
                              break;
                      }
					layer.close(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.close(msg);
					alert("<fmt:message bundle='${pageScope.bundle}'  key='Data.load.timeout' />！！");
				}

			});
		}

		var iccid = $("#iccid");
		var value3 = iccid.val();
		var data3 = {
			'iccid' : value3
		};
		request(data3);
		iccid.change(function() {
			var value = $(this).val();
			var data1 = {
				'iccid' : value
			};
			request(data1);
		})
		
		$("#refish").click(function() {
			var data4 = {
				'iccid' : iccid.val()
			};
			request(data4);
		})
	})
</script>
<style>
h5 {
	font-size: 3.8vw;
}

button {
	font-size: 3.8vw
}

a {
	text-align: left
}
</style>

</head>
<body>
	<div style="overflow:hidden;text-shadow: none; border-radius: 0px;padding: 0vw; margin: 0rem auto; background-image: -moz-linear-gradient(top,#f9d38f 0,#f98333 100%);
    background-image: -webkit-gradient(linear,0 0,0 100%,from(#f9d38f),to(#f98333));
    background-image: -webkit-linear-gradient(top,#f9d38f 0,#f98333 100%);
    background-image: -o-linear-gradient(top,#f9d38f 0,#f98333 100%);
    background-image: linear-gradient(to bottom,#f9d38f 0,#f98333 100%);" >

		<nav style="text-shadow: none; background:rgba(255,00,255,0); border:none;margin-bottom: 0vw" class="navbar navbar-default">
            <div style="text-shadow: none;background:rgba(255,00,255,0);  padding: 0em;border:none;" class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div style=" background:rgba(255,00,255,0);margin: 0em" class="navbar-header ">
                <button type="button" class="navbar-toggle collapsed" style="border-color: #fff;" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar" style="background-color: #fff;"></span>
                    <span class="icon-bar" style="background-color: #fff;"></span>
                    <span class="icon-bar" style="background-color: #fff;"></span>
                </button>
                <a class="navbar-brand" style="padding-top: 0px" href="<%=basePath%>h5/index"><img src="static/h5/images/logo51.png" style="height: 2.2rem;margin-top:0.5rem;"></a>

            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div  style="border-bottom: none;background-color: #FFFFFF; font-family:微软雅黑;" class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
                <ul style="margin: 1.5rem 1rem;text-align: left" class="nav navbar-nav  ">
                    <li style="text-align: left" >
                        <a  style=" padding-top: 1.2rem;padding-bottom:1.3rem;animation-delay: 0.21s; animation-name: fadeInDownBig;border-bottom: 1px  solid #dfe4e1; text-align: left;
                    padding-left: 4rem" class="wow fadeInDownBig animated " href="<%=basePath%>h5/index"><fmt:message bundle='${pageScope.bundle}'  key='home.page' />
                        </a> </li>

                    <li class="dropdown">
                        <a style="padding-top: 1.2rem;padding-bottom:1.3rem;margin: auto 0;animation-delay: 0.16s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem;border-bottom: 1px  solid #dfe4e1;" href="#" class="dropdown-toggle wow fadeInDownBig animated" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message bundle='${pageScope.bundle}'  key='device' /> <span class="caret"></span></a>
                        <ul style="margin: auto 1rem;" class="dropdown-menu">
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>deviceActivation/index"><fmt:message bundle='${pageScope.bundle}'  key='Activation' /></a></li>
                            <li role="separator" class="divider"></li>

                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>h5/productIntroduce"><fmt:message bundle='${pageScope.bundle}'  key='introduction' /></a></li>
                            <li role="separator" class="divider"></li>


                        </ul>
                    </li>
                    <li class="dropdown">
                        <a  style="padding-top: 1.2rem;padding-bottom:1.3rem;margin: auto 0;border-bottom: 1px  solid #dfe4e1;animation-delay: 0.11s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="#" class="wow fadeInDownBig animated dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message bundle='${pageScope.bundle}'  key='data' /> <span class="caret"></span></a>
                        <ul style="margin: auto 1rem;" class="dropdown-menu ">
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>paymentService/topay"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a></li>
                            <li role="separator" class="divider"></li>
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem"  href="<%=basePath%>queryService/index"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></a></li>
                            <li role="separator" class="divider"></li>

                        </ul>
                    </li>


                </ul>

				<ul style="margin: auto 1rem;" class="nav navbar-nav navbar-right ">
					<%
							if (session.getAttribute("lenovoid") != null) {
							%>
                            <li class="dropdown">
		                        <a style="margin: 0.5rem 0;padding-top:0;padding-bottom:2rem;animation-delay:0s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="<%=basePath%>h5/loginout" class="wow fadeInDownBig animated dropdown-toggle" >
		                       		<fmt:message bundle='${pageScope.bundle}'  key='quit' />
		                        </a>
		                    </li>
							<%
								}else {
							%>
							<li class="dropdown">
		                        <a style="margin: 0.5rem 0;padding-top:0;padding-bottom:2rem;animation-delay:0s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="<%=basePath%>deviceActivation/index" class="wow fadeInDownBig animated dropdown-toggle" >
		                       		<fmt:message bundle='${pageScope.bundle}'  key='sign.in' />
		                        </a>
		                    </li>
							<%
								}
					%>
                </ul>
                <%-- <ul style="margin: auto 1rem;" class="nav navbar-nav navbar-right ">

                    <li class="dropdown">
                        <a style="margin: 0.5rem 0;padding-top:0;padding-bottom:2rem;animation-delay:0s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="#" class="wow fadeInDownBig animated dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                       		Hi <%= session.getAttribute("lenovoid") == null? "":session.getAttribute("lenovoid")%><span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a  class="wow fadeIn animated" style="font-size: 3.8vw; animation-delay: 0.01s; animation-name: fadeInDownBig;text-align: left;padding-left: 12rem" href="<%=basePath%>h5/loginout">退出</a></li>

                        </ul>
                    </li>
                </ul> --%>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

		<div style="margin: 5vw 0;" class="row">
			<!--<div class="col-xs-11 "><p style="margin:  auto 3vw"></p></div>-->
			<div class="col-xs-12">
				<div style="color:">
					<label style="margin: auto 1vw"><fmt:message bundle='${pageScope.bundle}'  key='device.number' /></label> 
					<select style="color: #000; float:right;"
						id="iccid" name="iccid">
						<%
							Object[] devices = (Object[]) session.getAttribute("deviceList");
							if (devices != null) {

								for (Object iccid : devices) {
						%>
						<option value='<%=iccid%>'><%=iccid%></option>
						<%
							}
							}
						%>
					</select>
				</div>
			</div>

		</div>

		<div
			style="color: #ffffff; border-radius: 50%; border: #fff 3px solid; width: 44vw; height: 44vw; margin-left: 28vw; margin-bottom: 2rem; margin-top: 3.5rem"
			class="row">
			<div style="text-align: center; margin-left: 8vw; margin-top: 1rem; padding-left: 0px; padding-bottom: 0"
				class="col-xs-9 ">
				<p style="font-size: 3.8vw; margin-top: 1.5rem; margin-left: 0;">
					<fmt:message bundle='${pageScope.bundle}'  key='Residual.trafffic' /></p>

				<p style="font-size: 9vw; margin-top: 0rem; margin-left: 0">
					<span id="capacity">0</span><span style="font-size: 3.8vw;">M</span>
				</p>
				<p style="font-size: 3.8vw; margin-top: 0.5rem">
					<fmt:message bundle='${pageScope.bundle}'  key='data.plan' />：<span id="useing">0</span>%
				</p>
				<!-- <p style="font-size: 3.8vw; margin-top: 0.5rem"><span id="remaining"></span>%</p> -->
			</div>

		</div>
		<div style="color: #fff; margin-top: 4rem; padding-bottom: 1rem;width:100vm;padding-left:0;padding-right:0;"
			class="row">
			<div style="text-align: center; padding-right: 0; padding-left: 20px"
				class="col-xs-4">
				<p style="margin-top: 0rem; font-size: 3.3vw; padding-right: 10px;"><fmt:message bundle='${pageScope.bundle}'  key='in.use' />
				</p>
				<p
					style="margin-top: 0.5rem; font-size: 3.3vw; padding-right: 10px;">
					<span id="dataRemaining">0</span><fmt:message bundle='${pageScope.bundle}'  key='data.plan' />
				</p>
				<p style="margin-top: 0.5rem; font-size: 3.3vw;">
					<span id="expirationDate"></span><fmt:message bundle='${pageScope.bundle}'  key='expire.date' />
				</p>

			</div>
			<div style="text-align: center; padding-right: 0; padding-left: 0"
				class="col-xs-4">
				<button
					style="padding: 3px; border-radius: 10px; font-size: 3.3vw; text-align: center; background-color: transparent; border: 1px solid #fff"
					type="button" class="btn "><fmt:message bundle='${pageScope.bundle}'  key='data.calibration' /></button>
				<p style="margin-top: 1.8rem; font-size: 3.3vw;">
					<span id="timestamp"></span>
				</p>
			</div>
			<div style="padding-left: 0; text-align: center;padding-right: 20px;" class="col-xs-4">
				<p style="margin-top: 0rem; font-size: 3.3vw; padding-right: 10px;"><fmt:message bundle='${pageScope.bundle}'  key='Not.yet.in.use' />
				</p>
				<p
					style="margin-top: 0.5rem; font-size: 3.3vw; padding-right: 10px;">
					<span id="result">0</span>M<fmt:message bundle='${pageScope.bundle}'  key='data.plan' />
				</p>
				<p style="margin-top: 0.5rem; font-size: 3.3vw;"><fmt:message bundle='${pageScope.bundle}'  key='Automatic.activation.after.last.package.run.out' /></p>

			</div>

		</div>

	</div>

	<div
		style="width: 100vw; padding-bottom:10rem; ">
		<div style="margin:0rem;" class="row">

			<div
				style="text-align: center; padding-top: 1.5rem; padding-bottom: 3vw; border-right: 1px solid #dfe4e1"
				class="col-xs-6">
				<a href="<%=basePath%>queryService/queryRecord"> <img class="img-circle" src="static/h5/images/charge.png"
					style="width: 15vw">
				</a>
				<p style="margin-top: 1rem; font-size: 3.8vw;"><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></p>
			</div>
			<div
				style="text-align: center; padding-top: 1.5rem; padding-bottom: 3vw;"
				class="col-xs-6">
				<a href="<%=basePath%>queryService/queryRecord">
					<img class="img-circle" src="static/h5/images/consumption111.PNG" style="width: 15vw">
				</a>
				<p style="margin-top: 1rem; font-size: 3.8vw;"><fmt:message bundle='${pageScope.bundle}'  key='expendse.records' /></p>
			</div>

		</div>
	</div>

	<nav class="navbar navbar-default navbar-fixed-bottom">
	<div style="text-align: center; padding:; width: 100%"
		class="container">
		<button
			style="font-size: 4vw; width: 70vw; margin: 0.8rem auto; color: #ffffff; border-radius: 10px; text-align: center; background-color: #fa9148;"
			type="button" class="btn " onclick="window.location.href='/paymentService/topay'"><fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' /></button>
	</div>
	</nav>
	<div>
</body>
</html>