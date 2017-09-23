<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
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
<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.home.page' /></title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
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
	<script type="text/javascript">
	
	$(function() {
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByState",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var pieChart = "pieChart1";
				var serName = "<fmt:message bundle='${pageScope.bundle}'  key='The.quantity.of.cards' />";
				doPieChart(pieChart,dateMap.data1,dateMap.data2,serName);
				$("#countStr").html("<fmt:message bundle='${pageScope.bundle}'  key='Number.of.users' />:"+dateMap.countStr);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByOperators",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var pieChart = "pieChart2";
				var serName = "<fmt:message bundle='${pageScope.bundle}'  key='The.quantity.of.cards' />";
				doPieChart(pieChart,dateMap.data1,dateMap.data2,serName);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByTop10Cust",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var titleText1 = "<fmt:message bundle='${pageScope.bundle}'  key='Enterprise.users' />TOP10";
				var histogram1 = "histogram1";
				var yMeaning1 = "<fmt:message bundle='${pageScope.bundle}'  key='The.quantity.of.cards' />";
				doHistogram(titleText1,histogram1,yMeaning1,dateMap.xStr,dateMap.yStr);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByUserCountYear",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var titleText2 = "<fmt:message bundle='${pageScope.bundle}'  key='Consumer.growth.in.the.past.year' />";
				var histogram2 = "histogram2";
				var yMeaning2 = "<fmt:message bundle='${pageScope.bundle}'  key='Number.of.users' />";
				doHistogram(titleText2,histogram2,yMeaning2,dateMap.xStr,dateMap.yStr);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByTrade",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var pieChart = "pieChart3";
				var serName = "<fmt:message bundle='${pageScope.bundle}'  key='Number' />";
				doPieChart(pieChart,dateMap.data1,dateMap.data2,serName);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByFlowCountMonth",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var titleText3 = "<fmt:message bundle='${pageScope.bundle}'  key='Nearly.a.month.of.traffic.usage' />(GB)";
				var histogram3 = "histogram3";
				var yMeaning3 = "<fmt:message bundle='${pageScope.bundle}'  key='Flow.rate' />";
				doHistogram(titleText3,histogram3,yMeaning3,dateMap.xStr,dateMap.yStr);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByComePayment",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var pieChart = "pieChart4";
				var serName = "<fmt:message bundle='${pageScope.bundle}'  key='amount.of.money' />";
				doPieChart(pieChart,dateMap.data1,dateMap.data2,serName);
			}
		});
		$.ajax({
			url : "${ctx}/homePage/doAjaxHomePageByConsume6Month",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var titleText4 = "<fmt:message bundle='${pageScope.bundle}'  key='consumption.amount.of.money' />";
				var histogram4 = "histogram4";
				var yMeaning4 = "<fmt:message bundle='${pageScope.bundle}'  key='consumption.amount.of.money' />";
				doHistogram(titleText4,histogram4,yMeaning4,dateMap.xStr,dateMap.yStr);
			}
		});
/* 		var data1 = ['正常','未激活','停机','系统总用户数：2018万'];
		var data2 = [
         {value:1548, name:'正常'},
         {value:335, name:'未激活'},
         {value:135, name:'停机'}
        ];
		doPieChart(pieChart1,data1,data2);
		
		var pieChart2 = "pieChart2";
		var data3 = ['中国移动','中国电信','中国联通','接入运营商：4家'];
		var data4 = [
         {value:335, name:'中国移动'},
         {value:235, name:'中国电信'},
         {value:1248, name:'中国联通'}
        ];
		doPieChart(pieChart2,data3,data4); */
		
/* 		var titleText1 = "企业用户TOP10(万)";
		var histogram1 = "histogram1";
		var yMeaning1 = "卡数量";
		var xStr1 = ['企业1','企业2','企业3','企业4','企业5','企业6','企业7','企业8','企业9','企业10'];
		var yStr1 = [21, 32, 21, 13, 35, 76, 122, 6, 32, 2];
		doHistogram(titleText1,histogram1,yMeaning1,xStr1,yStr1); */
		
/* 		var titleText2 = "近一年用户增长情况(万)";
		var histogram2 = "histogram2";
		var yMeaning2 = "用户数";
		var xStr2 = ['2016年01月','2016年03月','2016年04月','2016年05月','2016年06月','2016年07月','2016年08月','2016年09月','2016年10月','2016年11月','2016年12月','2017年01月'];
		var yStr2 = [1, 3, 11, 13, 35, 76, 122, 136, 152, 212,288,321];
		doHistogram(titleText2,histogram2,yMeaning2,xStr2,yStr2); */
		
/* 		var pieChart3 = "pieChart3";
		var data5 = ['批量录入','批量激活','产品订购','业务总数量：2300笔	'];
		var data6 = [
         {value:435, name:'批量录入'},
         {value:735, name:'批量激活'},
         {value:1248, name:'产品订购'}
        ];
		doPieChart(pieChart3,data5,data6); */
		
/* 		var titleText3 = "近一个月流量使用情况(GB)";
		var histogram3 = "histogram3";
		var yMeaning3 = "流量使用量";
		var xStr3 = ['14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','01','02','03','04','05','06','07','08','09','10','11','12'];
		var yStr3 = [121, 103, 151, 134, 135, 176, 136, 152, 212,288,221, 103, 151, 134, 135, 176, 122, 136, 152, 212,288,221, 151, 134, 135, 176, 152, 212,288,221];
		doHistogram(titleText3,histogram3,yMeaning3,xStr3,yStr3); */
		
/* 		var pieChart4 = "pieChart4";
		var data7 = ['缴费','扣费','退费','返销','结算返现'];
		var data8 = [
         {value:435, name:'缴费'},
         {value:735, name:'扣费'},
         {value:128, name:'退费'},
         {value:248, name:'返销'},
         {value:482, name:'结算返现'}
        ];
		doPieChart(pieChart4,data7,data8); */
		
/* 		var titleText4 = "消费金额";
		var histogram4 = "histogram4";
		var yMeaning4 = "消费金额";
		var xStr4 = ['2016年08月','2016年09月','2016年10月','2016年11月','2016年12月','2017年01月'];
		var yStr4 = [2213, 4103, 5151, 3212, 4288, 5221];
		doHistogram(titleText4,histogram4,yMeaning4,xStr4,yStr4); */
	});
	// 饼状图
	function doPieChart(pieChart,data1,data2,serName) {
		var pieChart1 = echarts.init(document.getElementById(pieChart));
		pieChart1.setOption({
		    title : {
		        //text: '标题',
		       // subtext: '纯属虚构',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    }, 
		    legend: {
		        orient: 'vertical',
		        bottom: 0,
		        x : '212',
		        data: data1,
		    },
		    series : [
		        {
		            name: serName,
		            type: 'pie',
		            radius : '50%',
		            center: ['50%', '60%'],
		            data:data2,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		});
	}
	// 柱状图
	function doHistogram(titleText,histogram,yMeaning,xStr,yStr) {
		var myChart = echarts.init(document.getElementById(histogram));
        // 指定图表的配置项和数据
        var option = {
        	color: ['#00BFFF'],
            title: {
                text: titleText
            },
            tooltip: {},
            legend: {
                data:[yMeaning]
            },
            xAxis: {
                data: xStr
            },
            yAxis: {},
            series: [{
                name: yMeaning,
                type: 'bar',
                data: yStr
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	}
	</script>
</head>
<body>
	<div class="pd-20" align="center">
		<!-- <h4 class="font12">运营基本信息</h4> -->
		<div class="cl pd-5">
			<span style="height: 5px; width: 50%; border: 0px solid #ccc; padding: 10px;float: left;font-weight: bold;"><fmt:message bundle='${pageScope.bundle}'  key='User' /></span>
			<span style="height: 5px; width: 50%; border: 0px solid #ccc; padding: 10px;float: right;font-weight: bold;"><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></span>
		</div>
		<div class="cl pd-5">
			<span id ="countStr" style="height: 5px; width: 100%; border: 0px solid #ccc; padding: 10px;float: left;font-size: 29px;color: Red"></span>
		</div>
		<div class="cl pd-5">
			<div id="pieChart1"style="height: 500px; width: 50%; border: 0px solid #ccc; padding: 10px;float: left"></div>
			<div id="pieChart2"style="height: 500px; width: 50%; border: 0px solid #ccc; padding: 10px;float: left"></div>
		</div>
		<div class="cl pd-5">
			<div id="histogram1"style="height: 500px; width: 95%; padding: 10px;"></div>
			<div id="histogram2"style="height: 500px; width: 95%; padding: 10px;"></div>
		</div>
		<h4 align="center"><fmt:message bundle='${pageScope.bundle}'  key='Business.Statistics' /></h4>
		<br>
		<div class="cl pd-5">
			<div style="height: 500px; width: 55%; border: 0px solid #ccc; padding: 10px;float: left">
				<span style="font-weight: bold;"><fmt:message bundle='${pageScope.bundle}'  key='Business.volume.statistics.in.this.month' /></span>
				<div id="pieChart3"style="height: 500px; width: 95%; border: 0px solid #ccc; padding: 10px;float: left"></div>
			</div>
			<div style="height:500px;width:44%;padding:10px;float:left;">
                       <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='This.month.companies.use.data.charts' />TOP5</span>
                       <div style="height:400px;width:50%;padding:10px;float:left;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='The.total.flow.is.the.highest.this.month' /></span>
                            <table class="table table-border table-bordered table-hover table-bg table-sort " height="360px" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:60%;text-align:center;" height="50px"><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.user' /></th>
						       <th style="text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='Usage.amount' />(GB)</th>
						     </tr>
						     <c:forEach items="${useList}" var="map">
                             <tr>
						       <td class="font12" style="text-align:center;">${map.CUST_NAME}</td>
						       <td class="font12" style="text-align:center;"><font size="1">${map.USECOUNT}</font> </td>
						     </tr>
                             </c:forEach>
						    </table>
                       </div>
                       <div style="height:400px;width:50%;padding:10px;float:right;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='The.average.flow.rate.of.single.card' /></span>
                           <table class="table table-border table-bordered table-hover table-bg table-sort " height="360" border="2" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:60%;text-align:center;" height="50px"><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.user' /> </th>
						       <th style="text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='Usage.amount' />（MB）</th>
						     </tr>
						     <c:forEach items="${avgUseList}" var="map">
	                             <tr>
							       <td class="font12" style="text-align:center;">${map.CUST_NAME}</td>
							       <td class="font12" style="text-align:center;"><font size="1">${map.USECOUNT}</font> </td>
							     </tr>
                             </c:forEach>
						    </table>
                       </div>
              </div>
		</div>
		<div class="cl pd-5">
			<div id="histogram3"style="height: 500px; width: 95%; padding: 10px;"></div>
		</div>
<%-- 		<h4>营收统计</h4>
		<br>
		<div class="cl pd-5">
			<div style="height: 500px; width: 55%; border: 0px solid #ccc; padding: 10px;float: left">
				<span style="font-weight: bold;">本月营收统计</span>
				<div id="pieChart4"style="height: 500px; width: 95%; border: 0px solid #ccc; padding: 10px;float: left"></div>
			</div>
			<div style="height:500px;width:44%;padding:10px;float:left;">
                       <span class="btn btn-primary radius" style="width:100%;text-align:center;">本月企业消费排行榜TOP5</span>
                       <div style="height:400px;width:50%;padding:10px;float:left;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;">本月总消费最多</span>
                            <table class="table table-border table-bordered table-hover table-bg table-sort " height="360px" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:60%;text-align:center;" height="50px">企业 </th>
						       <th style="text-align:center;"> 金额(元)</th>
						     </tr>
						     <c:forEach items="${consumeCustMaxList}" var="map">
	                             <tr>
							       <td class="font12" style="text-align:center;">${map.CUST_NAME}</td>
							       <td class="font12" style="text-align:center;"><font size="1">${map.SUMFEE}</font> </td>
							     </tr>
                             </c:forEach>
						    </table>
                       </div>
                       <div style="height:400px;width:50%;padding:10px;float:right;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;">本月单卡平均消费最多</span>
                           <table class="table table-border table-bordered table-hover table-bg table-sort " height="360" border="2" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:60%;text-align:center;" height="50px">企业</th>
						       <th style="text-align:center;"> 金额(元)</th>
						     </tr>
						     <c:forEach items="${consumeCustMaxAvgList}" var="map">
	                             <tr>
							       <td class="font12" style="text-align:center;">${map.CUST_NAME}</td>
							       <td class="font12" style="text-align:center;"><font size="1">${map.AVGFEE}</font> </td>
							     </tr>
                             </c:forEach>
						    </table>
                       </div>
              </div>
		</div>
		<br>
		<div class="cl pd-5">
			<div style="height: 500px; width: 55%; border: 0px solid #ccc; padding: 10px;float: left">
				<span style="font-weight: bold;">近6个月消费情况(单位：元)</span>
				<div id="histogram4"style="height: 500px; width: 99%; border: 0px solid #ccc; padding: 10px;float: left"></div>
			</div>
			<div style="height: 500px; width: 44%; padding: 10px; float: left;">
				<span class="btn btn-primary radius"style="width: 100%; text-align: center;">本月单卡消费排行榜TOP5</span>
				<div style="height: 400px; width: 50%; padding: 10px; float: left;">
					<span class="btn btn-primary radius"style="width: 100%; text-align: center;">本月消费最多</span>
					<table
						class="table table-border table-bordered table-hover table-bg table-sort "
						height="360px" cellpadding="0" cellspacing="0" class="tb">
						<tr class="zpTable">
							<th style="width: 60%; text-align: center;" height="50px">卡号
							</th>
							<th style="text-align: center;">金额(元)</th>
						</tr>
						<c:forEach items="${consumeSingleMaxList}" var="map">
							<tr>
								<td><label class="font12 fl">${map.ICCID}</label></td>
								<td style="text-align: center;"><font size="1">${map.SUMFEE}</font>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div style="height: 400px; width: 50%; padding: 10px; float: right;">
					<span class="btn btn-primary radius"
						style="width: 100%; text-align: center;">本月消费最少</span>
					<table
						class="table table-border table-bordered table-hover table-bg table-sort "
						height="360" border="2" cellpadding="0" cellspacing="0" class="tb">
						<tr class="zpTable">
							<th style="width: 60%; text-align: center;" height="50px">卡号</th>
							<th style="text-align: center;">金额(元)</th>
						</tr>
						<c:forEach items="${consumeSingleMinList}" var="map">
							<tr>
								<td class="font12" style="text-align: center;">${map.CUST_NAME}</td>
								<td class="font12" style="text-align: center;"><font
									size="1">${map.SUMFEE}</font></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div> --%>
	</div>
</body>
</html>