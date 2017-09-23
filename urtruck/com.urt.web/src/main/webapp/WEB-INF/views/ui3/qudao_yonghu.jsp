<!DOCTYPE html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Channel.user.home.page' /></title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/unit/Chart.js"></script>
	<script type="text/javascript" src="js/main.js"></script>
</head>
<body style="background:transparent;">
	<div class="model_q whole colum_3" style="margin-top:30px;">
		<h3><span style="width:78px;">SIM<fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></span><em style="background:none"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
		<div class="disk">
			<div id="echartsPie" style="width:322px;height:222px"></div>
			<p><span><i></i><fmt:message bundle='${pageScope.bundle}'  key='normal' /></span><span><i></i><fmt:message bundle='${pageScope.bundle}'  key='Activation' /></span><span><i></i><fmt:message bundle='${pageScope.bundle}'  key='out.of.service' /></span></p>
			<div class="colum_btn">
				<span class="linear"><fmt:message bundle='${pageScope.bundle}'  key='China.Mobile' /></span>
				<span><fmt:message bundle='${pageScope.bundle}'  key='China.Unicom' /></span>
				<span><fmt:message bundle='${pageScope.bundle}'  key='China.Telecom' /></span>
			</div>
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
				                {value:335, name:'待激活 80'},
				                {value:310, name:'正常 12210'},
				                {value:234, name:'停机 29'}
				            ]
				        }
				    ]
				};
				var myDoughnut = echarts.init(document.getElementById("echartsPie"))
					myDoughnut.setOption(option);
			</script>
		</div>
	</div>
	<div class="model_q survey survey_list colum_3" style="margin-top:30px;">
		<img src="images/ul_icon.png">
		<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Exception.warning' /></span><em style="background:none">查看更多<fmt:message bundle='${pageScope.bundle}'  key='' /></em></h3>
		<ul>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Exceeded.package.warning' /><span><strong>1212</strong></span></li>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Exceeded.data.cap.warning' /><span><strong>1212</strong></span></li>
			<li><fmt:message bundle='${pageScope.bundle}'  key='Mobile.machine.card.separation warning' /><span><strong>1212</strong></span></li>
		</ul>
	</div>
	<div class="model_q survey colum_3" style="margin-top:30px;">
		<h3><span><fmt:message bundle='${pageScope.bundle}'  key='general.information' /></span><em><fmt:message bundle='${pageScope.bundle}'  key='Custom.view' /></em></h3>
		<div class="m_l">
			<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='Total.connections' /></em></span>
			<p>40000000035</p>
		</div>
		<div class="m_l">
			<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='Customer.development' /></em></span>
			<p>12839012928</p>
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
		<div class="model_q whole colum_3">
			<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Situation.of.this.month' /></span><em style="background:none">查看更多<fmt:message bundle='${pageScope.bundle}'  key='' /></em></h3>
			<div class="disk">
				<div id="echartsPie2" style="width:322px;height:222px"></div>
				<p style="margin-left:58px;"><span><i></i>700M</span><span><i></i>1000M</span><span><i></i>1G</span><span><i></i>4G</span></p>
				<div class="colum_btn">
					<span class="linear"><fmt:message bundle='${pageScope.bundle}'  key='China.Mobile' /></span>
				<span><fmt:message bundle='${pageScope.bundle}'  key='China.Unicom' /></span>
				<span><fmt:message bundle='${pageScope.bundle}'  key='China.Telecom' /></span>
				</div>
				<script type="text/javascript" src="js/unit/echarts.min.js"></script>
				<script>
				var option = {
					    // tooltip: {
					    //     trigger: 'item',
					    //     formatter: "{a} <br/>{b}: {c} ({d}%)"
					    // },
					    color:['#4bceff','#009e96','#0194ff','#0068b7'],
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
					                {value:335, name:'4G 10'},
					                {value:310, name:'1G 50'},
					                {value:234, name:'100MB 20'},
					                {value:234, name:'70MB 100'}
					            ]
					        }
					    ]
					};
					var myDoughnut = echarts.init(document.getElementById("echartsPie2"))
						myDoughnut.setOption(option);
				</script>
			</div>
		</div>
		<div class="model_q survey survey_list colum_3">
			<img src="images/ul_icon.png">
			<h3><span><fmt:message bundle='${pageScope.bundle}'  key='expiration.reminder' /></span><em style="background:none"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
			<ul>
				<li>客户A<span><strong>270</strong/>张卡即将到期</span></li>
				<li>客户B<span><strong>70</strong/>张卡即将到期</span></li>
				<li>客户C<span><strong>20</strong/>张卡即将到期</span></li>
				<li>客户D<span><strong>5</strong/>张卡即将到期</span></li>
				<li>客户E<span><strong>30</strong/>张卡即将到期</span></li>
				<li>客户F<span><strong>2270</strong/>张卡即将到期</span></li>
				<li>客户G<span><strong>20</strong/>张卡即将到期</span></li>
			</ul>
		</div>
		<div class="model_q survey survey_list colum_3">
			<img src="images/ul_icon.png">
			<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Important.notice' /></span><em style="background:none"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
			<ul>
				<li>系统升级<span>017-05-12</span></li>
				<li>到货通知<span>017-05-12</span></li>
				<li>流量池警告<span>017-05-12</span></li>
				<li>流量池警告<span>017-05-12</span></li>
				<li>流量池警告<span>017-05-12</span></li>
				<li>流量池警告<span>017-05-12</span></li>
				<li>流量池警告<span>017-05-12</span></li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>