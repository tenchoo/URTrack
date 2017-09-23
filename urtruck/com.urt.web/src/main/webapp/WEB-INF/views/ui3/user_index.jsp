<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Personal.home.page' /></title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
</head>
<body style="background:transparent;">
	<div class="user_information">
		<img src="images/head.png" class="head">
		<h3><fmt:message bundle='${pageScope.bundle}'  key='Good.morning' />，<span>188********</span></h3>
		<p><fmt:message bundle='${pageScope.bundle}'  key='account.names' />：m18602203530@163.com <a href=""><img src="images/user_icon.png"></a><a href=""><img src="images/phone.png"></a></p>
	</div>
	<div class="flow_management">
		<ul>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Remaining.data.in.current.data.plan' /></li>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Remaining.data' /></li>
			<li><fmt:message bundle='${pageScope.bundle}'  key='usage.data.in.last.month' /></li>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Promotion.activities' /></li>
		</ul>
		<div class="model model1">
			<div id="indicatorContainer" style="margin:20px 0 22px 0;"></div>
			<script type="text/javascript" src="js/radialIndicator.js"></script>
			<script type="text/javascript">
				$(document).ready(function(){
				    //圆形流量使用————比例图 
				    $('#indicatorContainer').radialIndicator({
				        barColor: '#0194ff', //数值颜色
				        barWidth: 5,  //圆型线的宽度
				        initValue: 70, //所占比例值
				        radius:50, //半径宽度
				        roundCorner : true, //如果设置为true则圆形指示器的刻度bar有圆角
				        percentage: true  //设置为true显示圆形指示器的百分比数值
				    });

				   var radialObj = $('#indicatorContainer').data('radialIndicator');
						//获取圆形图对象
					   radialObj.animate(10); 
						//设置圆形对象的比例值
				})
			</script>
			<p><fmt:message bundle='${pageScope.bundle}'  key='valid.until' />2017-05-30</p>
		</div>
		<div class="model model2">
			<h2>12</h2>
			<h3>40789MB</h3>
			<button class="linear"><fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' /></button>
			<p><fmt:message bundle='${pageScope.bundle}'  key='uses.4GB.data.packages.when.it.use.up.or.expires' /></p>
		</div>
		<div class="model model3">
			<h3>40789MB</h3>
		</div>
		<div class="model model4">
			<h3>10元1G</h3>
			<span><fmt:message bundle='${pageScope.bundle}'  key='view.details' /></span>
		</div>
	</div>
	<div class="monitor">
		<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Data.usage.in.this.month' /></span></h3>
		<p><fmt:message bundle='${pageScope.bundle}'  key='Everyday.data.usage.conditions' /></p>
		<div id="line">
			<canvas id="canvas" height="272px" width="960"></canvas>
			<script type="text/javascript"src="js/unit/Chart.js"></script>
			<script type="text/javascript">
	         var lineChartData = {
	         	//折线图需要为每个数据点设置一标签。这是显示在X轴上
				labels : ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"],
				//数据集（y轴数据范围随数据集合中的data中的最大或最小数据而动态改变的）
				datasets : [
					{
						// 颜色的使用类似于CSS，你也可以使用RGB、HEX或者HSL
				// rgba颜色中最后一个值代表透明度
				// 填充颜色
						fillColor : "rgba(1,148,255,0.1)",
						// 线的颜色
						strokeColor : "rgba(1,148,255,1)",
						// 点的填充颜色
						pointColor : "rgba(255,255,255,1)",
						// 点的边线颜色
						pointStrokeColor : "rgba(1,148,255,1)",
						// 与x轴标示对应的数据
						data : [0,15,82,12,300,2,8,32,65,59,1000,81,56,500,40]
					}
				]
			};
			
			var myLine = new Chart(document.getElementById("canvas").getContext("2d")).Line(lineChartData);
		    </script>
	    </div>
	</div>
</body>
</html>