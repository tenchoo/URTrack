<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <base href="<%=basePath%>" /> 
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><fmt:message bundle='${pageScope.bundle}'  key='query.record' /></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
    <link rel="stylesheet" href="static/h5/css/bootstrap-datetimepicker.min.css" type="text/css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="static/h5/js/jquery-1.12.4.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
    <script src="static/h5/js/bootstrap.min.js"></script>

    <link href="static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />

    <style>
        *{
            font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
        }
        h5{font-size: 3.8vw}
        button{font-size: 4vw}
        .nav li.live a{color: blue;background: #fff; }
    </style>

    <!-- <script src="static/h5/js/main.js"></script> -->

    <style>button{font-size: 4vw}</style>
    <script src="static/h5/js/mobiscroll_002.js" type="text/javascript"></script>
    <script src="static/h5/js/mobiscroll_004.js" type="text/javascript"></script>
    <link href="static/h5/css/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <link href="static/h5/css/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="static/h5/js/mobiscroll.js" type="text/javascript"></script>
    <script src="static/h5/js/mobiscroll_003.js" type="text/javascript"></script>
    <script src="static/h5/js/mobiscroll_005.js" type="text/javascript"></script>
    <script>
    		$(function(){
    			$("#chargeRecord").click(function(){
    				$('.nav li').removeClass('live');
    				$(this).parent().addClass('live')
    				$("#submitForm").attr("action","glaH5AppQuery/queryChargeRecord");
    				
    				$("#record1").hide();
    				$("#record1").show();
    				$("#record2").hide();
    				$("#record2").show();
    				$("#record3").hide();
    			});
    			$("#purchaseHistory").click(function(){
    				$('.nav li').removeClass('live');
    				$(this).parent().addClass('live')
    				$("#submitForm").attr("action","glaH5AppQuery/queryPurchaseHistory");
    				
					$("#record1").hide();
    				$("#record2").hide();
    				$("#record3").hide();
    				$("#record3").show();
    			});
    		});
    </script>
</head>
<body>
	<div style="font-size: 3.8vw; overflow: hidden;">
		<div style="background-color: #ffffff; border-bottom: 1px solid #dfe4e1; margin: 0; overflow: hidden;" class="row">
			<div style="padding: 1rem 0;margin: 0 1rem;"
				class="col-xs-1 col-xs-offset-1">
				<a href="javascript:history.go(-1)" > <img
					src="static/h5/images/goback.png" class="img-responsive"
					alt="Responsive image">
				</a>
			</div>
			<div style="margin: 1.25rem 3.8rem;font-size:4.2vw" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></div>
		</div>
		<div style="">
			<img class="img-responsive" alt="Responsive image"
				style="width: 100%;" src="static/h5/images/s2.jpg">

		</div>

		<div class="tabbable" id="tabs-720364">
			<ul class="nav nav-tabs">
				<li class="live">
				<a href="javascript:void(0);"  id="chargeRecord">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</li>
				<li>
				<a href="javascript:void(0);"  id="purchaseHistory">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<fmt:message bundle='${pageScope.bundle}'  key='expendse.records' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
				</li>
			</ul>
			<div class="tab-content">
				<div
					style="padding-top: 1rem; padding-bottom: 1rem; background-color: #eef0f0"
					class="tab-pane active" id="panel-216951">
					<div
						style="margin-right: 0; margin-left: 0; background-color: #FFFFFF; width: 100%; padding-bottom: 13rem"
						class="row">
						<form action="glaH5AppQuery/queryChargeRecord" method="post" id="submitForm">
							<div
								style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
								class="col-xs-11 col-xs-offset-1 " id="record1">
								<div class="row">
									<div style="color: #5e5e5e; padding-right: 0"
										class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='start.date' /></div>
									<div style="" class="col-xs-8 ">
										<input style="border: none" value="" class=""
											readonly="readonly" name="startTime" id="startTime" type="text">
									</div>
								</div>
							</div>
							<div
								style="padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
								class="col-xs-11 col-xs-offset-1 " id="record2">
								<div class="row">
									<div style="color: #5e5e5e; padding-right: 0"
										class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='deadline' /></div>
									<div style="" class="col-xs-8 ">
										<input style="border: none" value="" class=""
											readonly="readonly" name="endTime" id="endTime" type="text">
									</div>
								</div>
							</div>
							
							<div
								style="display:none;padding-top: 18px; padding-bottom: 16px; border-bottom: 1px solid #dfe4e1"
								class="col-xs-11 col-xs-offset-1 " id="record3">
								<div class="row">
									<div style="color: #5e5e5e; padding-right: 0"
										class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='Query.month' /></div>
									<div style="" class="col-xs-8 ">
										<input style="border: none" value="" class=""
											readonly="readonly" name="monthTime" id="monthTime" type="text">
									</div>
								</div>
							</div>
						</form>
						<div style="text-align: center;" class="row">

							<button
								style="font-size: 4vw; width: 70vw; margin: 3rem auto; color: #ffffff; border-radius: 15px; text-align: center; background-color: #fa9148;"
								type="button" class="btn" onclick="queryFun()"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
								<script>
									function queryFun(){
										if($('#submitForm').attr('action').indexOf('Purchase') > -1){
											if($("#monthTime").val() != null && $("#monthTime").val() != ''){
												$('#submitForm').submit();
											}else{
												alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.check.the.month.of.inquiry' />");
											}
										}else{
											$('#submitForm').submit();
										}
											
											
									}
								
								</script>
						</div>
					</div>

				</div>

			</div>
		</div>

	</div>
	<script type="text/javascript">
    $(function () {
        var currYear = (new Date()).getFullYear();
        var currMonth = (new Date()).getMonth()+1;
        var opt={};
        opt.date = {preset : 'date'};
        opt.datetime = {preset : 'datetime'};
        opt.time = {preset : 'time'};
        opt.default1 = {
            theme: 'android-ics light', //皮肤样式
            display: 'modal', //显示方式
            mode: 'scroller', //日期选择模式
            dateFormat: 'yyyy-mm-dd',
            lang: 'zh',
            showNow: true,
            nowText: "<fmt:message bundle='${pageScope.bundle}'  key='Today' />",
            startYear: currYear - 10, //开始年份
            endYear: currYear + 10 //结束年份
        };
        
        opt.month = {
       		theme: 'android-ics light', //皮肤样式
            display: 'modal', //显示方式
            mode: 'scroller', //日期选择模式
            dateFormat: 'yyyy-mm',
            lang: 'zh',
            showNow: true,
            nowText: "<fmt:message bundle='${pageScope.bundle}'  key='This.month' />",
            startYear: currYear - 10, //开始年份
            endYear: currYear + 10 //结束年份
        }

        $("#endTime").mobiscroll($.extend(opt['date'], opt['default1']));
        $("#startTime").mobiscroll($.extend(opt['date'], opt['default1']));
        $("#monthTime").mobiscroll($.extend(opt['date'], opt['month']));
        /* var optDateTime = $.extend(opt['datetime'], opt['default']);
        var optTime = $.extend(opt['time'], opt['default']);
        $("#endTime").mobiscroll(optDateTime).datetime(optDateTime);
        $("#startTime").mobiscroll(optTime).time(optTime); */
    });
</script>
</body>
</html>