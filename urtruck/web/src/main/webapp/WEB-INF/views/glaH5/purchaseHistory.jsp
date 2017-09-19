<%@ page language="java" contentType="text/html; charset=Utf-8"
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
<title><fmt:message bundle='${pageScope.bundle}'  key='expendse.records' /></title>
<base href="<%=basePath%>" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
<link rel="stylesheet" href="static/h5/css/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
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
<script type="text/javascript" src="static/js/echarts-all.js"></script>
<link href="static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />

<style>
* {
	font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif,
		"宋体";
}

h5 {
	font-size: 3.8vw
}

button {
	font-size: 4vw
}
</style>
<!-- <script src="static/h5/js/main.js"></script> -->

<script src="static/h5/js/date_min_v1.js"></script>
<div id="__calendarPanel"
	style="position: absolute; visibility: hidden; display: none; z-index: 9999; background-color: transparent; border: none; width: 240px; height: 276px;">
	<iframe name="__calendarIframe" id="__calendarIframe" width="100%"
		height="100%" scrolling="no" frameborder="0" style="margin: 0px;"></iframe>
</div>

<!-- <script src="static/h5/js/user_account.js"></script> -->


</head>
<body>
	<div style="font-size: 3.8vw;overflow: hidden;">
		<div style="background-color: #fa9148; border-bottom: 1px solid #dfe4e1; margin: 0; overflow: hidden;" class="row">

			<div style="padding: 1rem 0;margin: 0 1rem;" class="col-xs-1 col-xs-offset-1">
				<a href="javascript:history.go(-1)">
				<img src="static/h5/images/goback1.png" class="img-responsive"
					alt="Responsive image">
					</a>
			</div>
	
			<div style="color: #ffffff;margin: 1.25rem 3.8rem;font-size:4.2vw;" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Consumer.inquiries' /></div>
		</div>
			<div style="width: 96vw; background-color: #ffffff; margin: 1rem auto; padding-top: 1rem"
				class="row">
				<div id="page1">
					<p
						style="font-size: 3.6vw; color: #8c8c8c; margin-bottom: 1rem; margin-left: 1rem"><fmt:message bundle='${pageScope.bundle}'  key='The.consumer.record.is.as.follows' />:</p>
					<div style="text-align: center">

						<c:if test="${empty list}">
			           		     <fmt:message bundle='${pageScope.bundle}'  key='Your.Lenovo.account.did.not.queried.any.consumer.records' />
                    	</c:if>

						<c:if test="${not empty list}">
						<c:set var="totalPages" value="${list[0].totalPages}"/> 
							<c:forEach items="${list}" var="item">
								<div
									style="border-bottom: 1px solid #dfe4e1; margin: 0rem 2rem 0rem 0rem; padding: auto 0 auto auto;"
									class="col-xs-12">
										<div style="padding: 1.5rem 0 0.2rem 0; margin: 0.8rem auto 0 0" class="col-xs-2">
											<img src="static/h5/images/consumption3.png"
												style="height: 4rem">
										</div>
									<div
										style="padding: 1.5rem 0 0.2rem 0; margin: 0 auto 0rem 0rem"
										class="col-xs-10 ">
										<div style="margin: 1rem auto" class="row">
											<div
												style="padding-right: 0; margin-right: 0; text-align: right"
												class="col-xs-7">
												<h5>${item.sessionStartTime}</h5>
											</div>
											<div style="padding-right: 0; text-align: center"
												class="col-xs-5">
												<h5 style="padding-right: 0;"><fmt:message bundle='${pageScope.bundle}'  key='use' />${item.dataVolume}KB</h5>
											</div>

										</div>
										<div style="color: #8c8c8c; margin: 1rem auto" class="row">
											<div style="padding-right: 0; text-align: right"
												class="col-xs-7">
												<h5><fmt:message bundle='${pageScope.bundle}'  key='Continued' />${item.duration}</h5>
											</div>
											<div style="padding-right: 0; text-align: center"
												class="col-xs-5">
												<h5><%-- ${item.zone} --%><fmt:message bundle='${pageScope.bundle}'  key='data.plan' /></h5>
											</div>
										</div>
									</div>
								</div>
							</c:forEach>
						
						<div style="margin-bottom: 3rem;width:100vm;padding-left:0;padding-right:0" class="row">
							<div style="margin-top: 2rem; text-align: center"
								class="col-xs-4">
								<a href="<%=basePath%>glaH5AppQuery/queryPurchaseHistory?curPage=${curPage-1 < 1? curPage:(curPage-1)}"><fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /></a>
							</div>
							<div style="margin-top: 2rem; text-align: center" class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='Total' />&nbsp;&nbsp;${totalPages}&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='page' /></div>
							<div style="margin-top: 2rem; text-align: center"
								class="col-xs-4">
								<a href="<%=basePath%>glaH5AppQuery/queryPurchaseHistory?curPage=${curPage+1 > totalPages? curPage:(curPage+1)}"><fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /></a>
							</div>																 
						</div>
						</c:if>
						<c:if test="${not empty tradelist}">
							<div id="showList"style="height: 500px; width: 99%; border: 0px solid #ccc; padding: 10px;float: left"></div>
						</c:if>
						<script>
						// 柱状图
						function doHistogram(titleText,histogram,yMeaning,xStr,yStr) {
							var myChart = echarts.init(document.getElementById(histogram));
					        // 指定图表的配置项和数据
					        var option1 = {
					        	    title : {
					        	        text: '月流量使用情况<fmt:message bundle='${pageScope.bundle}'  key='' />',
					        	        subtext: '不是自然月<fmt:message bundle='${pageScope.bundle}'  key='' />'
					        	    },
					        	    tooltip : {
					        	        trigger: 'axis'
					        	    },
					        	    legend: {
					        	        data:['流量<fmt:message bundle='${pageScope.bundle}'  key='' />（MB）']
					        	    },
					        	    toolbox: {
					        	        show : true,
					        	        feature : {
					        	            mark : {show: true},
					        	            dataView : {show: true, readOnly: false},
					        	            magicType : {show: true, type: ['line', 'bar']},
					        	            restore : {show: true},
					        	            saveAsImage : {show: true}
					        	        }
					        	    },
					        	    calculable : true,
					        	    xAxis : [
					        	        {
					        	            type : 'category',
					        	            data : xStr
					        	        }
					        	    ],
					        	    yAxis : [
					        	        {
					        	            type : 'value'
					        	        }
					        	    ],
					        	    series : [
					        	        {
					        	            name:'流量<fmt:message bundle='${pageScope.bundle}'  key='' />',
					        	            type:'bar',
					        	            data: yStr,
					        	            markPoint : {
					        	                data : [
					        	                    {type : 'max', name: '最大值<fmt:message bundle='${pageScope.bundle}'  key='' />'},
					        	                    {type : 'min', name: '最小值<fmt:message bundle='${pageScope.bundle}'  key='' />'}
					        	                ]
					        	            },
					        	            markLine : {
					        	                data : [
					        	                    {type : 'average', name: '平均值<fmt:message bundle='${pageScope.bundle}'  key='' />'}
					        	                ]
					        	            }
					        	        }
					        	    ]
					        	};

					        // 使用刚指定的配置项和数据显示图表。
					        myChart.setOption(option1);
						}
						$(function(){
							var yStr = ${flowList};
							var xStr3 = ${dateList};
							//xStr3= ['2017-01-26', '2017-01-27', '2017-01-28', '2017-01-29', '2017-01-30', '2017-01-31', '2017-02-01', '2017-02-02', '2017-02-03', '2017-02-04', '2017-02-05', '2017-02-06', '2017-02-07', '2017-02-08', '2017-02-09', '2017-02-10', '2017-02-11', '2017-02-12', '2017-02-13', '2017-02-14', '2017-02-15', '2017-02-16', '2017-02-17', '2017-02-18', '2017-02-19', '2017-02-20']
							doHistogram("月消费流量<fmt:message bundle='${pageScope.bundle}'  key='' />","showList","流量<fmt:message bundle='${pageScope.bundle}'  key='' />(MB)",xStr3,yStr);
						})
						
						</script>
					</div>
				</div>
			</div>
</body>
</html>