<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.user.home.page' /></title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/unit/Chart.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
</head>
<body style="background:transparent;">
	<div class="model_q whole" style="margin-top:30px;">
		<h3><span><fmt:message bundle='${pageScope.bundle}'  key='overall.situation ' /></span></h3>
		<div class="disk">
			<!-- <canvas id="canvas" height="172" width="172"></canvas> -->
			<div id="echartsPie" style="width:222px;height:222px"></div>
			<span class="disk_title"><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></span>
			<!-- <div class="d">电信</div>
			<div class="l">联通</div>
			<div class="y">移动</div> -->
			<p><i></i><fmt:message bundle='${pageScope.bundle}'  key='China.Mobile' />：4034</p>
			<p><i></i><fmt:message bundle='${pageScope.bundle}'  key='China.Unicom' />：4034</p>
			<p><i></i><fmt:message bundle='${pageScope.bundle}'  key='China.Telecom' />：4034</p>
			<script type="text/javascript" src="js/unit/echarts.min.js"></script>
			<script>
			var option = {
				    // tooltip: {
				    //     trigger: 'item',
				    //     formatter: "{a} <br/>{b}: {c} ({d}%)"
				    // },
				    color:['#4bceff','#009e96','#0194ff'],
				    legend: {
				        orient: 'vertical',
				        x: 'left'
				    },
				    series: [
				        {
				            name:'<fmt:message bundle="${pageScope.bundle}"  key="Access.source" />',
				            type:'pie',
				            selectedMode: 'single',
				            radius: [0, '30%'],

				            label: {
				                normal: {
				                    position: 'inner'
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            }
				        },
				        {
				            name:'<fmt:message bundle="${pageScope.bundle}"  key="Access.source" />',
				            type:'pie',
				            radius: ['25%', '55%'],

				            data:[
				                {value:335, name:'<fmt:message bundle="${pageScope.bundle}"  key="China.Unicom" />'},
				                {value:310, name:'<fmt:message bundle="${pageScope.bundle}"  key="China.Mobile" />'},
				                {value:234, name:'<fmt:message bundle="${pageScope.bundle}"  key="China.Telecom" />'}
				            ]
				        }
				    ]
				};
				var myDoughnut = echarts.init(document.getElementById("echartsPie"))
					myDoughnut.setOption(option);
			</script>
		</div>
		<div class="disk_line">
			<p><fmt:message bundle='${pageScope.bundle}'  key='SIM.card.basic.state' /></p>
			<canvas id="canvas4" height="135px" width="434px"></canvas>
			<script type="text/javascript">
				var barChartData = {
					labels : ["<fmt:message bundle='${pageScope.bundle}'  key='Activation' />","<fmt:message bundle='${pageScope.bundle}'  key='on.service' />","<fmt:message bundle='${pageScope.bundle}'  key='out.of.service' />"],
					datasets : [
						{
							fillColor : "rgba(86,209,255,1)",
							data : [200,59,90]
						},
						{
							fillColor : "rgba(8,151,255,1)",
							data : [28,48,40]
						},
						{
							fillColor : "rgba(8,161,153,1)",
							data : [96,27,100]
						}
					]
					
				}

				var myLine = new Chart(document.getElementById("canvas4").getContext("2d")).Bar(barChartData);
			</script>
		</div>
	</div>
	<div class="model_q survey" style="margin-top:30px;">
		<h3><span><fmt:message bundle='${pageScope.bundle}'  key='general.information' /></span><em><fmt:message bundle='${pageScope.bundle}'  key='Custom.view' /></em></h3>
		<div class="m_l">
			<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='Total.connections' /></em></span>
			<p>40000000035</p>
		</div>
		<div class="m_l">
			<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='link.relative' /></em></span>
			<p>20%</p>
		</div>
		<div class="m_l">
			<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='New.additions.this.month' /></em></span>
			<p>7400035</p>
		</div>
	</div>
	<div class="article_wrap">
		<div class="model_q whole">
			<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Situation.of.this.month' /></span></h3>
			<div class="disk">
				<div id="echartsPie2" style="width:280px;height:220px"></div>
				<span class="disk_title disk_title2"><fmt:message bundle='${pageScope.bundle}'  key='This.month' /><br/><fmt:message bundle='${pageScope.bundle}'  key='Product.order' /></span>
				<p><i></i><span>1G</span> 1个月到期</p>
				<p><i></i><span>200M</span> 1个月到期</p>
				<p><i></i><span>30M</span> 1个月到期</p>
				<script>
				var option = {
					    // tooltip: {
					    //     trigger: 'item',
					    //     formatter: "{a} <br/>{b}: {c} ({d}%)"
					    // },
					     color:['#4bceff','#009e96','#0194ff'],
					    legend: {
					        orient: 'vertical',
					        x: 'left'
					    },
					    series: [
					        {
					            name:'<fmt:message bundle="${pageScope.bundle}"  key="Access source" />',
					            type:'pie',
					            selectedMode: 'single',
					            radius: [0, '30%'],

					            label: {
					                normal: {
					                    position: 'inner'
					                }
					            },
					            labelLine: {
					                normal: {
					                    show: false
					                }
					            }
					        },
					        {
					            name:'<fmt:message bundle="${pageScope.bundle}"  key="Access source" />',
					            type:'pie',
					            radius: ['25%', '40%'],

					            data:[
					                {value:335, name:'1G 29120'},
					                {value:310, name:'200M 291'},
					                {value:234, name:'30M 20'}
					            ]
					        }
					    ]
					};
					var myDoughnut = echarts.init(document.getElementById("echartsPie2"))
						myDoughnut.setOption(option);
				</script>
			</div>
			<div class="disk_line">
			  	<p><fmt:message bundle='${pageScope.bundle}'  key='Traffic.usage.statistics.in.the.last.15.days' /></p>
				<canvas id="canvas3" height="135px" width="414px"></canvas>
				<script type="text/javascript">
			         var lineChartData = {
			         	//折线图需要为每个数据点设置一标签。这是显示在X轴上
						labels : ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"],
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
								data : [0,15,82,12,300,2,8,32,65,59,500,81,56,500,40]
							}
						]
					};
					
					var myLine = new Chart(document.getElementById("canvas3").getContext("2d")).Line(lineChartData);
			    </script>
		    </div>
		</div>
		<div class="model_q survey survey_list">
			<img src="images/ul_icon.png">
			<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Important.notice' /></span><em style="background:none"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
			<ul>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
				<li>平台维护时间更新<span>017-05-12</span></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>	
</body>
</html>