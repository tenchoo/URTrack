<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="Keywords" content=""/>
		<meta name="Description" content=""/>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<title>NIO</title>
		<!--[if lte IE 8]>
		<script src="js/html5shiv.min.js"></script>
		<![endif]-->
		<script src="${ctx}/static/nio/js/PIE_IE678.js"></script>
		<link rel="stylesheet" href="${ctx}/static/nio/css/reset.css" />
		<link rel="stylesheet" href="${ctx}/static/nio/css/iconfont.css" />
		<link rel="stylesheet" href="${ctx}/static/nio/css/common.css" />
		<link rel="stylesheet" href="${ctx}/static/nio/css/index.css" />

	</head>
	<body>
	<section class="main">
		<!--总体分布图开始-->
		<section class="total_distribution ">
			<h2>总体分布图</h2>
			<ul class="clearfix map_nav">
				<li>ES8</li>
				<li>充电桩</li>
			</ul>
			<div class="map_wraper">
				<div id="map" class="map_left">
					
				</div>
				<div id="map_right" class="map_right">
					
				</div>
			</div>
		</section>
		<!--总体分布图结束-->
		
		<!--总连接数开始-->
		<section class="total_connect_and_flux">
			<ul class="clearfix">
				<li class="bing1">
					<div class="inner_left">
						<h2>总连接数:&nbsp;<span class="blue"></span>&nbsp;个</h2>
						<div class="cl">
							<p class="fl active">卡状态</p>
							<p class="fl">卡分类</p>
						</div>
						<div id="card_circle1" style="width: 300px;height: 220px;">
							
						</div>
					</div>
					<div class="inner_right">
						<div class="inner_right_content">
							<h3>本月新增</h3>
							<p class="insertCount blue"></p>
							<i class="iconfont icon-jiaren blue"></i>
						</div>
						<div class="inner_right_content">
							<h3>环比增长</h3>
							<p class="deep_orange mom"><strong class="deep_orange"></strong><em class="deep_orange">%</em></p>
							<i class="iconfont icon-zengchang deep_orange"></i>
						</div>
					</div>
				</li>
				<li class="bing2">
					<div class="inner_left">
						<h2>流量总使用量:&nbsp;<span class="blue"></span>&nbsp;G</h2>
						<div class="cl">
							<p class="fl active">卡状态</p>
							<p class="fl">卡分类</p>
						</div>
						<div id="card_circle2" style="width: 300px;height: 220px;">
							
						</div>
					</div>
					<div class="inner_right">
						<div class="inner_right_content">
							<h3>本月新增</h3>
							<p class="insertCount blue"></p>
							<i class="iconfont icon-jiaren blue"></i>
						</div>
						<div class="inner_right_content">
							<h3>环比增长</h3>
							<p class="deep_orange mom"><strong class="deep_orange"></strong><em class="deep_orange">%</em></p>
							<i class="iconfont icon-zengchang deep_orange"></i>
						</div>
					</div>
				</li>
				<li class="bing3">
					<div class="inner_left">
						<h2>APN1流量使用量:&nbsp;<span class="blue"></span>&nbsp;G</h2>
						<div class="cl">
							<p class="fl active">卡状态</p>
							<p class="fl">卡分类</p>
						</div>
						<div id="card_circle3" style="width: 300px;height: 220px;">
							
						</div>
					</div>
					<div class="inner_right">
						<div class="inner_right_content">
							<h3>本月新增</h3>
							<p class="blue insertCount"></p>
							<i class="iconfont icon-jiaren blue"></i>
						</div>
						<div class="inner_right_content">
							<h3>环比增长</h3>
							<p class="deep_orange mom"><strong class="deep_orange"></strong><em class="deep_orange">%</em></p>
							<i class="iconfont icon-zengchang deep_orange"></i>
						</div>
					</div>
				</li>
				<li class="bing4">
					<div class="inner_left">
						<h2>APN2流量使用量:&nbsp;<span class="blue"></span>&nbsp;G</h2>
						<div class="cl">
							<p class="fl active">卡状态</p>
							<p class="fl ">卡分类</p>
						</div>
						<div id="card_circle4" style="width: 300px;height: 220px;">
							
						</div>
					</div>
					<div class="inner_right">
						<div class="inner_right_content">
							<h3>本月新增</h3>
							<p class="insertCount blue"></p>
							<i class="iconfont icon-jiaren blue"></i>
						</div>
						<div class="inner_right_content">
							<h3>环比增长</h3>
							<p class="deep_orange mom"><strong class="deep_orange"></strong><em class="deep_orange">%</em></p>
							<i class="iconfont icon-zengchang deep_orange"></i>
						</div>
					</div>
				</li>
			</ul>
			
		</section>
		<!--总连接数结束-->
		
		<!--月份变化走势图开始-->
		<section class="month_change">
			<h2>月份变化走势图</h2>
			<i class="iconfont icon-zhuzhuangtu1"></i>
			<ul class="clearfix month_nav">
				<li class="active">连接数</li>
				<li class="haha">流量使用量</li>
				<li>APN1流量</li>
				<li>APN2流量</li>
				<li>充值笔数</li>
				<li>充值金额</li>
				<li>API调用</li>
			</ul>
			<div id="main" style="width: 100%;height: 330px;">
				
			</div>
		</section>
		<!--月份变化走势图结束-->
		<section class="five_box">
			<ul>
				<li class="api_count">
					<h2>API累计调用次数<i class="iconfont icon-gongju"></i></h2>
					<p class="number blue"><strong></strong><em class="blue">万次</em></p>
					<p class="insertCount">本月新增：<strong></strong></p>
					<p class="mom">环比增长：<strong></strong><em style="font-weight: 700;">%</em></p>
					<span>充电桩</span>
					<div class="wraper EP9_wraper blue_wraper">
	
						<div class="inner EP9_inner blue_inner">
							<div class="progress"></div>
						</div>
					</div>
					<span>ES8</span>
					<div class="wraper EP8_wraper blue_wraper">
		
						<div class="inner EP8_inner blue_inner">
							<div class="progress">30%</div>
						</div>
					</div>
				</li>
				<li class="amountSum">
					<h2>用户累计充值金额<i class="iconfont icon-qian"></i></h2>
					<p class="number orange"><strong></strong><em class="orange">万元</em></p>
					<p class="insertCount">本月新增：<strong></strong></p>
					<p class="mom">环比增长：<strong></strong><em style="font-weight: 700;">%</em></p>
					<span>充电桩</span>
					<div class="wraper EP9_wraper orange_wraper">
						<div class="inner EP9_inner orange_inner">
							<div class="progress">30%</div>
						</div>
					</div>
					<span>ES8</span>
					<div class="wraper EP8_wraper orange_wraper">
						<div class="inner EP8_inner orange_inner">
							<div class="progress">30%</div>
						</div>
					</div>
				</li>
				<li class="amountCount">
					<h2>用户累计充值笔数<i class="iconfont icon-iconziti08"></i></h2>
					<p class="number skyblue"><strong></strong><em class="skyblue">万次</em></p>
					<p class="insertCount">本月新增：<strong></strong></p>
					<p class="mom">环比增长：<strong></strong><em style="font-weight: 700;">%</em></p>
					<span>充电桩</span>
					<div class="wraper EP9_wraper skyblue_wrap">
						<div class="inner EP9_inner skyblue_inner">
							<div class="progress">30%</div>
						</div>
					</div>
					<span>ES8</span>
					<div class="wraper EP8_wraper skyblue_wrap">
						<div class="inner EP8_inner skyblue_inner">
							<div class="progress">30%</div>
						</div>
					</div>
				</li>
				<li class="smsUsedCount">
					<h2><strong>短信总使用量</strong><i class="iconfont icon-duanxin" style="margin-left: 70px;"></i></h2>
					<p class="number green"><strong></strong><em class="green">万条</em></p>
					<p class="insertCount">本月新增：<strong></strong> </p>
					<p class="mom">环比增长：<strong></strong><em style="font-weight: 700;">%</em></p>
					<span>充电桩</span>
					<div class="wraper EP9_wraper green_wraper">
						<div class="inner EP9_inner green_inner">
							<div class="progress">30%</div>
						</div>
					</div>
					<span>ES8</span>
					<div class="wraper EP8_wraper green_wraper">
						<div class="inner EP8_inner green_inner">
							<div class="progress">30%</div>
						</div>
					</div>
				</li>
				<li class="voiceUsedCount">
					<h2><strong>语音总使用量</strong><i class="iconfont icon-yuyin" style="margin-left: 70px;"></i></h2>
					<p class="number pink"><strong></strong><em class="pink">万分钟</em></p>
					<p class="insertCount">本月新增：<strong></strong></p>
					<p class="mom">环比增长：<strong></strong><em style="font-weight: 700;">%</em></p>
					<span>充电桩</span>
					<div class="wraper EP9_wraper pink_wraper">
						<div class="inner EP9_inner pink_inner">
							<div class="progress">30%</div>
						</div>
					</div>
					<span>ES8</span>
					<div class="wraper EP8_wraper pink_wraper">
						<div class="inner EP8_inner pink_inner">
							<div class="progress">30%</div>
						</div>
					</div>
				</li>
			</ul>
		</section>
		
		<!--异常告警开始-->
		<section class="alarm">
			<h2>异常告警</h2>
			<i class="iconfont icon-msnui-alarm-triangle"></i>
			<ul>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
				<li>
					<p><span></span><a href=""></a></p>
				</li>
			</ul>
		</section>
		<!--异常告警结束-->	
	</section>	
	<script src="${ctx}/static/nio/json/result.json"></script>
	<script src="${ctx}/static/nio/js/jquery-1.11.3.min.js"></script>
	<script src="${ctx}/static/nio/js/echarts.min.js"></script>
	<script src="https://cdn.hcharts.cn/highmaps/highmaps.js"></script>
	<script src="https://data.jianshukeji.com/geochina/china.js"></script>
	<script src="${ctx}/static/nio/js/chinaMap.js"></script>
	<script src="${ctx}/static/nio/js/zhuzhuangtu.js"></script>
	<script src="${ctx}/static/nio/js/bingzhuangtu.js"></script>
	<script src="${ctx}/static/nio/js/common.js"></script>
	<script src="${ctx}/static/nio/js/index.js"></script>
	<script src="${ctx}/static/nio/js/result.js"></script>
	</body>
</html>