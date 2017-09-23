<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>    
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Personal.home.page' /></title>
<link rel="stylesheet" type="text/css" href="static/ui3/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript" src="static/ui3/js/jquery.js"></script>
<script type="text/javascript" src="static/ui3/js/unit/echarts.min.js"></script>
<link rel="stylesheet" href="static/h5/css/selectize.bootstrap3.css">
<script type="text/javascript" src="static/h5/js/selectize.js"></script>

</head>
<body class="add_bg">
	<div class="main">
		<div class="container backNone">
			<!-- 加载内容放这里 -->
			<div class="user_information">
				<img src="static/ui3/images/head.png" class="head">
				<h3>
					<script type="text/javascript">
				    now = new Date(),hour = now.getHours() 
					if(hour < 6){document.write("凌晨好！")} 
					else if (hour < 9){document.write("早上好！")} 
					else if (hour < 12){document.write("上午好！")} 
					else if (hour < 14){document.write("中午好！")} 
					else if (hour < 17){document.write("下午好！")} 
					else if (hour < 19){document.write("傍晚好！")} 
					else if (hour < 22){document.write("晚上好！")} 
					else {document.write("夜里好！")} 
				   </script>
				   <span>${loginName}</span>
				</h3>
				<p class="clearfix">
					<label class="float-left lineHeight02"><fmt:message bundle='${pageScope.bundle}'  key='device.number' />：</label>
					<!-- <input type="text" id="userName" placeholder="请输入设备号" class="userName"> --> 
					<select id="userName" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />" class="userName" style="width:250px;float:left;">
						<option value=""><fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' /></option>
						<c:forEach var="iccid" items="${iccidList}">
							<c:if test="${not empty iccid}">
								<option value="${iccid}">${iccid}</option>
							</c:if>
						</c:forEach>
					</select>
					<script>
						$('#userName').selectize({
							allowEmptyOption: false,
							create: true
						});
					</script>
					<a href="javascript:volid(0);"><img src="static/ui3/images/user_icon.png"></a> 
					<a href="javascript:volid(0);"><img src="static/ui3/images/phone.png"></a>
					<script src="static/h5/js/layer/layer.js"></script>
					<script type="text/javascript">
						$('#userName').change(function(){
							var selectIccid = $('#userName').val();
							var flag = true;
							if(selectIccid.indexOf('898602') > -1 || selectIccid.indexOf('898603') > -1 ){
								$('#header1').html("<fmt:message bundle='${pageScope.bundle}'  key='Current.data.balance' />");
								$('#header2').html("<fmt:message bundle='${pageScope.bundle}'  key='remaining.data.plan' />");
								flag = false;
							}else{
								$('#header1').html("<fmt:message bundle='${pageScope.bundle}'  key='Remaining.data.in.current.data.plan' />");
								$('#header2').html("<fmt:message bundle='${pageScope.bundle}'  key='Remaining.data ' />");
								flag = true;
							}
							var msg = layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Data.loading' />", {
								icon : 16,
								time : 200000, //20秒关闭（如果不配置，默认是3秒）
								area : [ '182px', '66px' ],
								offset : '215px'
							});
							$.ajax({
								type : "post",
								url : "/personal/queryFlow",
								data : {"iccid":$(this).find("option:selected").val()},
								cache : false,
								success : function(data) {
									var json = eval("(" + data + ")");
									
									if(!jQuery.isEmptyObject(json) && !json.hasOwnProperty("error")){
										var radialObj = $('#indicatorContainer').data('radialIndicator');
										if(json.packageRemaining == 0 && json.packageCount == 0){
											radialObj.animate(0);	
										}else{
											var total = parseFloat(json.packageCount)+parseFloat(json.packageRemaining)
											var _inow = Math.floor(json.packageRemaining/total*100); //剩余流量与圆盘的比例
											//获取圆形图对象
										    radialObj.animate(_inow);
										}
										
										if (typeof(json.packageExpirationDate) != "undefined"){
											if(json.packageExpirationDate == '未使用'){
												document.getElementById('packageExpirationDate').innerHTML=(json.packageExpirationDate)+"<fmt:message bundle='${pageScope.bundle}'  key='data.plan' />";
											}else{
												document.getElementById('packageExpirationDate').innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='valid.until ' />"+(json.packageExpirationDate);
											}
										}else{
											document.getElementById('packageExpirationDate').innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='No.data' />";
										}
										if(!flag){
											document.getElementById('packageExpirationDate').innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='At.the.end.of.this.month' />";
										}
										if (typeof(json.lastMonCount) != "undefined"){
											document.getElementById('lastMonCount').innerHTML=Math.ceil(json.lastMonCount)+"MB";
										}else{
											document.getElementById('lastMonCount').innerHTML="0MB";
										}
										
										var box = document.getElementById('packInfo');
										if(flag){
											var packageInfo = json.packageMsg;
											$('#packInfo').find('h2').remove();
											if(!jQuery.isEmptyObject(packageInfo)){
												var pagg = '';
												for(var key in packageInfo){
													var h2 = document.createElement('h2');
													pagg = pagg + key+"·"+packageInfo[key];
													$('#nextPag').html();
													$('#nextPag').html(key);
												}
												h2.innerHTML = pagg;
												box.appendChild(h2)
											}else{
												var h2 = document.createElement('h2');
												h2.innerHTML = "0个0MB";
												box.appendChild(h2);
												$('#nextPag').html("<fmt:message bundle='${pageScope.bundle}'  key='data.plan' />");
											}
										}else{
											$('#packInfo').find('h2').remove();
											var h2 = document.createElement('h2');
											if (typeof(json.dataRemaining) != "undefined"){
												h2.innerHTML = Math.ceil(json.dataRemaining)+"M";
											}else{
												h2.innerHTML = "0M";
											}
											box.appendChild(h2)
											$('#nextPag').html("<fmt:message bundle='${pageScope.bundle}'  key='Monthly.package' />");
										}
										
										var time = json.lables;
										var dege = json.data;
										if(!jQuery.isEmptyObject(time) && !jQuery.isEmptyObject(dege)){
											$('.noData').hide();//暂无数据提示隐藏
								            $('#line').show();//echart区域显示
											doHistogram(time,dege)
										}else{
											$('.noData').show();//暂无数据提示显示
								            $('#line').hide();//echart区域隐藏
											
										}
									}else{
										layer.alert(json.error);
									}
									layer.close(msg);
								},
								error : function(XMLHttpRequest, textStatus, errorThrown) {
									layer.close(msg);
									layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Data.load.timeout' />！！");
								}
							});
						})
					</script>
				</p>
			</div>
			<div class="flow_management">
				<ul>
					<li id="header1"><fmt:message bundle='${pageScope.bundle}'  key='Remaining.data.in.current.data.plan' /></li>
					<li id="header2"><fmt:message bundle='${pageScope.bundle}'  key='Remaining.data' /></li>
					<li><fmt:message bundle='${pageScope.bundle}'  key='usage.data.in.last.month' /></li>
					<li><fmt:message bundle='${pageScope.bundle}'  key='Promotion.activities' /></li>
				</ul>
				<div class="model model1">
					<div id="indicatorContainer" style="margin: 20px 0 22px 0;"></div>
					<script type="text/javascript" src="static/ui3/js/radialIndicator.js"></script>
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
								   radialObj.animate(0);
									//设置圆形对象的比例值
							})
					</script>
					<p id="packageExpirationDate">
					<fmt:message bundle='${pageScope.bundle}'  key='valid.until' /><fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd" />
					</p>
				</div>
				<div class="model model2">
					<div id="packInfo"><h2>0个0MB</h2></div>
					<h3></h3>
					<button class="linear" onclick="window.location.href='laouser/toPersonalOrder'"><fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' /></button>
					<p><fmt:message bundle='${pageScope.bundle}'  key='uses.4GB.data.packages.when.it.use.up.or.expires' /><span id="nextPag">4GB<fmt:message bundle='${pageScope.bundle}'  key='data.plan' /></span></p>
				</div>
				<div class="model model3">
					<h3 id="lastMonCount">0MB</h3>
				</div>
				<div class="model model4">
					<h3>10<fmt:message bundle='${pageScope.bundle}'  key='yuan' />1G</h3>
					<span onclick="window.location.href='laouser/toPersonalOrder'"><fmt:message bundle='${pageScope.bundle}'  key='view.details' /></span>
				</div>
			</div>
			<div class="monitor">
				<h3>
					<span><fmt:message bundle='${pageScope.bundle}'  key='Data.usage.in.this.month' /></span>
				</h3>
				<p><fmt:message bundle='${pageScope.bundle}'  key='Everyday.data.usage.conditions' /></p>
				<div class="noData"><fmt:message bundle='${pageScope.bundle}'  key='No.data' /></div>
				<div id="line">
					<div id="fifteenDay" style="width:960px;height:250px;"></div>
					    <script type="text/javascript">
					    function doHistogram(xStr,yStr) {
					    	// 基于准备好的dom，初始化echarts实例
					        var myChart = echarts.init(document.getElementById('fifteenDay'));
					         // 指定图表的配置项和数据
					        option = {
					        		title : {
					        	        subtext: "<fmt:message bundle='${pageScope.bundle}'  key='Not.natural.month' />"
					        	    },
					        	    tooltip : {
					        	        trigger: 'axis',
					        	        formatter: '{a0}:{c0}MB'
					        	    },
					        	    legend: {
					        	        data:["<fmt:message bundle='${pageScope.bundle}'  key='data.value' />（MB）"]
					        	    },
							    toolbox: {
							        show: false,
							        feature: {
							            mark: {
							                show: true
							            },
							            dataView: {
							                show: true,
							                readOnly: true
							            },
							            magicType: {
							                show: false,
							                type: ["line", "bar"]
							            },
							            restore: {
							                show: true
							            },
							            saveAsImage: {
							                show: true
							            }
							        }
							    },
							    xAxis: [
							        {
							            type: "category",
							            boundaryGap: false,
							            data: xStr,
							            splitArea: {
							                show: false
							            }
							        }
							    ],
							    yAxis: [
							        {
							            type: "value",
							            splitLine: {
							                lineStyle: {
							                    color: "rgb(198, 190, 186)"
							                }
							            },
							            axisTick: {
							                show: true
							            }
							        }
							    ],
							    series: [
							        {
							            name: "<fmt:message bundle='${pageScope.bundle}'  key='Business.data' />",
							            type: "line",
							            data: yStr,
							            smooth: true,
							            itemStyle: {
							                normal: {
							                    areaStyle: {
							                        type: "default"
							                    },
							                    borderWidth: 2
							                }
							            }
							        }
							    ],
							    color: ["#0094ff"]
							}
					        // 使用刚指定的配置项和数据显示图表。
					        myChart.setOption(option);
					    }
					    </script>
				</div>
			</div>
		</div>
	</div>
</body>
</html>