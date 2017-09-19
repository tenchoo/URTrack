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
<link href="${ctx}/static/cardcycle/css/style.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/cardcycle/js/jquery1.10.2.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
	<script type="text/javascript">
	
	$(function() {
		$.ajax({
			url : "${ctx}/card/doAjaxHomePageByState",
			type : "post",
			dataType : "json",
			success : function(dateMap) {
				var pieChart = "pieChart1";
				var serName = "<fmt:message bundle='${pageScope.bundle}'  key='The.quantity.of.cards' />";
				doPieChart(pieChart,dateMap.data1,dateMap.data2,serName);
				$("#countStr").html("<fmt:message bundle='${pageScope.bundle}'  key='Number.of.users' />:"+dateMap.countStr);
			}
		});
		
		
	})
	
	// 饼状图
	function doPieChart(pieChart,data1,data2,serName) {
		var pieChart1 = echarts.init(document.getElementById(pieChart));
		pieChart1.setOption({
		    title : {
		        text: '<fmt:message bundle="${pageScope.bundle}"  key="User.status.distribution" />',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    }, 
		    legend: {
		    	  orient : 'horizontal',
		          x : 'center',
		          y : 'bottom',
			      data: data1,
		    },
		    series : [
		        {
		            name: serName,
		            type: 'pie',
		            radius : '50%',
		            center: ['50%', '50%'],
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
	function checkIccid(){
		var iccid = $("#iccid").val();
		if (iccid.length <=0) {
			layer.msg("iccid<fmt:message bundle='${pageScope.bundle}'  key='cannot.be.empty' />");
			return false;
		}
		if(iccid.length<19||iccid.length>20){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.a.valid.iccid' />");
			return false;
		}
		return true;
	}
	function queryBy() {
		$("#dateSlider").html("");
		if (!checkIccid()) {
			return false;
		};
		$.ajax({
			url : "${ctx}/card/queryCycle",
			type : "post",
			dataType : "json",
			data : {
				"iccid" : $("#iccid").val(),
			},
			success : function(data) {
				if(data.length){
					var htmlList=[];
					$.each(data,function(index,item){
						htmlList.push('<li>');
						htmlList.push('<div class="shiji">');
						htmlList.push('<h1>'+item.STATEDATE+'</h1>');
						htmlList.push('<p>'+item.STATENAME+'</p>');
						htmlList.push('</div>');
						htmlList.push('</li>');
					});
					$("#dateSlider").html(htmlList.join(" "));
				}else{
					layer.msg("iccid<fmt:message bundle='${pageScope.bundle}'  key='Not.existed' />");
				}
			}
		});
	}
	</script>
</head>
<body>
	<div class="pd-20" align="center">
			<div id="pieChart1"style="height: 500px; width: 50%; border: 0px solid #ccc; padding: 10px;"></div>
	</div>
    <div class="oh">
		<div class="col-12">
			<lable class="kehu">ICCID: </lable>
			<input type="text" name="iccid" id="iccid" style="width:250px" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.iccid' />">
			<button id="search" type="button" class="btn btn-primary radius" onclick="queryBy();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' />
			</button>
		</div>
	</div>
	<div class="clearfix course_nr">
	<ul class="course_nr2" id="dateSlider">
		
	</ul>
</div>	
</body>
</html>