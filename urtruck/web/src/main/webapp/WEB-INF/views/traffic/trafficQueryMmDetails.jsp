<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_CN" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/reset.css" />
    <link href="${ctx}/static/glaNew/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/glaNew/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/common.css"/>
<title><fmt:message bundle='${pageScope.bundle}' key='Enterprise.customer.monthly.data.usage.query' /></title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<base href="<%=basePath%>" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
</head>
<body style="color: #444; background-color: #edf0ff;" >
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="pad row white-bg borderradius">
      	<!--顶部按钮区-->
      	<div class="row">
      		<h5 class="pb10" style="border-bottom:1px solid #ababab;"><a class="J_menuItem" href="SIM卡查询.html">SIM卡查询</a>>><a style="color: #444;" href="#">详细信息</a></h5>
      	</div>
        <div class="row white-bg mb30 mt10"> 
			<ul class="col-sm-6  item_box col-xs-12">
    			<li class="title">
    				ICCID:
    			</li>
    			<li class="content">
    			${map.ICCID }
    			</li>
    		</ul>
    		
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				IMSI:
    			</li>
    			<li class="content">
    			${map.IMSI }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				服务号码:
    			</li>
    			<li class="content">
    			${map.MSISDN }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				运营商:
    			</li>
    			<li class="content">
    			${map.OPERATORS_NAME }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				终端类型:
    			</li>
    			<li class="content">
    			${map.ZHONGDUAN }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				客户名称:
    			</li>
    			<li class="content">
    			${map.CUSTNAME }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				资费套餐ID:
    			</li>
    			<li class="content">
    			${map.GOODS_ID }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				型号:
    			</li>
    			<li class="content">
    			${map.XINGHAO }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				卡状态:
    			</li>
    			<li class="content">
    			${map.STATIC_NAME }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				已使用量:
    			</li>
    			<li class="content">
    			${map.USE_COUNT }
    			</li>
    		</ul>
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				SIM入库时间:
    			</li>
    			<li class="content">
    			${map.IMTIME }
    			</li>
    		</ul>
    		<!-- <ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				发货时间:
    			</li>
    			<li class="content">
    			</li>
    		</ul> -->
    		<ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				激活时间:
    			</li>
    			<li class="content">
    			${map.FIRST_CALL_TIME }
    			</li>
    		</ul>
    		<!-- <ul class="col-sm-6 item_box col-xs-12">
    			<li class="title">
    				服务ID:
    			</li>
    			<li class="content">
    			</li>
    		</ul> -->
        </div>
        
        <div class="row mt20">
        	<ul id="sim_detail_box" class="cl col-sm-7">
    			<li class="active">日月流量</li>
    			<li>日详单</li>
    			<li>生命周期展示</li>
    			<li>订购信息展示</li>
    		</ul>
        </div>
	  </div>
	</div>
   
 
    <!-- 全局js -->
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>

	<script>
		$(document).ready(function(){
			//tab选项切换
			$('#sim_detail_box li').on('click',function(){
				$(this).addClass('active').siblings('li').removeClass('active');
			});
		});
	</script>
     
</body>
</html>