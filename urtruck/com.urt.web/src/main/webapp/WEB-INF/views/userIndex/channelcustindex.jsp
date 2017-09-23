<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>   
<html class="overHid">
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Channel.user.home.page' /></title>
	<!-- css -->
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/ui3/js/unit/Chart.js"></script>
</head>
<body class="add_bg_data">
	<div class="main">
		<div class="clear"></div>
		<div class="container">
			<!-- 加载内容放这里 -->
			<div class="model_q whole colum_3">
				<h3><span>SIM<fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></span><em style="background:none"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
				<div class="disk">
					<!-- <canvas id="canvas" height="172" width="172"></canvas> -->
					<div id="echartsPie" style="width:380px;height:288px"></div>
					<!-- <span class="disk_title">运营商</span> -->
				</div>
			</div>
			<div class="model_q survey survey_list colum_3">
				<img src="${ctx}/static/ui3/images/ul_icon.png">
				<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Exception.warning' /></span><!-- <em style="background:none" onclick="amInfo(2);">查看更多</em> --></h3>
				<ul id="almInfoId">
				</ul>
			</div>
			<div class="model_q survey colum_3">
				<h3><span><fmt:message bundle='${pageScope.bundle}'  key='general.information' /></span><!-- <em>自定义视图</em> --></h3>
				<div class="m_l">
					<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='Total.connections' /></em></span>
					<p id="cardsAllId"></p>
				</div>
				<div class="m_l">
					<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='link.relative' /></em></span>
					<p id="rateId"></p>
				</div>
				<div class="m_l">
					<span><i></i><em><fmt:message bundle='${pageScope.bundle}'  key='New.additions.this.month' /></em></span>
					<p id="newCardsId"></p>
				</div>
			</div>
			<div class="model_q whole colum_3">
					<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Situation.of.this.month' /></span><!-- <em style="background:none">查看更多</em> --></h3>
					<div class="disk">
						<div id="echartsPie2" style="width:322px;height:222px"></div>
						<div class="colum_btn" id="operateII">
						</div>
					</div>
			</div>
			<div class="model_q survey survey_list colum_3">
					<img src="${ctx}/static/ui3/images/ul_icon.png">
					<h3><span><fmt:message bundle='${pageScope.bundle}'  key='expiration.reminder' /></span><!-- <em style="background:none" onclick="expireWarnInfo(2);">查看更多</em> --></h3>
					<ul id="expireWarnId">
					</ul>
				</div>
				<div class="model_q survey survey_list colum_3">
					<img src="${ctx}/static/ui3/images/ul_icon.png">
					<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Important.notice' /></span><em style="background:none" onclick="noticeMore()"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
					<ul id="noticeId">
					</ul>
				</div>
	    </div>
	</div>
	<script type="text/javascript" src="${ctx}/static/ui3/js/unit/echarts.min.js"></script>
	<script>
		var dataMap={
		};
		var dataGoodsMap={
		};
		$(function(){
			operatChartGoods();
			operatChart();
			notice("1");
	        infoOutline();
	        amInfo("1")
	        expireWarnInfo("1");
		})
		  //本月产品订购
 	function operatChartGoods(){
		$.ajax({
            type: "post",
            url: "<%=basePath %>corporate/corporateGoodsEcharts",
            dataType: "json",
            success: function (data) {
            	operatChart4(data.data1,data.data2);
            }
            	
    });
}	
    function operatChart4(data1,data2){
        var option = {
        	    tooltip: {
        	        trigger: 'item',
        	        formatter: "{a} <br/>{b}: {c} ({d}%)"
        	    },
        	    color:['#4bceff','#009e96','#0194ff',"#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"],
        	    legend: {
			        orient: 'horizontal',
			        x: 'center',	
			        y:'bottom',
			       	itemWidth:10,  
                    itemHeight:10,
			        data: data1
			    },
			    series: [
					        {
					            name:"<fmt:message bundle='${pageScope.bundle}'  key='Product ordered for this month' />",
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
					            name:"<fmt:message bundle='${pageScope.bundle}'  key='Product ordered for this month' />",
					            type:'pie',
					            radius: ['35%', '65%'],

					            data:data2
					        }
					    ]
        	};

        var myDoughnut = echarts.init(document.getElementById("echartsPie2"))
		myDoughnut.setOption(option);
        }
		  //运营商
	 	function operatChart(){
			$.ajax({
	            type: "post",
	            url: "<%=basePath %>cust/customerIndexEcharts",
	            dataType: "json",
	            success: function (data) {
	         /*    	var list = data.list;
	            	var htmlList =[];
	            	for(var i=0;i<list.length;i++){	
	            		var sum= list[i].SUM;
	            		var operaName = list[i].OPERATORSNAME;
				    	htmlList.push('<p><i></i>'+operaName+':'+sum+'</p>');
	            	}
	            	 $("#nameAndSum").html(htmlList.join(" "));
	            	 htmlList =[]; */
	            	operatChart1(data.data1,data.data2);
	            }
	    });
	}	
		   function operatChart1(data1,data2){
		        var option = {
		        	    tooltip: {
		        	        trigger: 'item',
		        	        formatter: "{a} <br/>{b}: {c} ({d}%)"
		        	    },
		        	    color:['#4bceff','#009e96','#0194ff',"#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"],
			       	    legend: {
					        orient: 'horizontal',
					        x: 'center',
					        y:'bottom',
					        itemWidth:10,  
			                itemHeight:10,  
					        data: data1
					    },
					    series: [
							        {
							            name:"<fmt:message bundle='${pageScope.bundle}'  key='Operator' />",
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
							            name:"<fmt:message bundle='${pageScope.bundle}'  key='Operator' />",
							            type:'pie',
							            radius: ['25%', '55%'],

							            data:data2
							        }
							    ]
		        	};
					var myDoughnut = echarts.init(document.getElementById("echartsPie"))
						myDoughnut.setOption(option);
		    }
		    //查看更多
		    function noticeMore(){
					url='${ctx}/corporate/noticeMore/';
					window.location.href=url
		    }
		function cardInfo(operatorId){
			var operatorMap=dataMap[operatorId];
			var stateNames=operatorMap.stateNames;
			var stateIds=operatorMap.stateIds;
			var sums=operatorMap.sums;
			var echartMap=[];
			for(var i=0;i<stateIds.length;i++){
				var stateValue={};
				stateValue.name=stateNames[i];
				stateValue.value=sums[i];
				echartMap.push(stateValue);
			}
			option = {
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        y: 'bottom',
				        data:stateNames
				    },
				    series: [
				        {
				            name:"<fmt:message bundle='${pageScope.bundle}'  key='Access.source' />",
				            type:'pie',
				            radius: ['20%', '50%'],
				            data:echartMap
				        }
				    ]
				};
				var myDoughnut = echarts.init(document.getElementById("echartsPie"),macarons)
					myDoughnut.setOption(option);
		}
		function expireWarnInfo(eventtype){	
	    	 $.ajax({
	    			url:"${ctx}/expire/expireWarnInfo",
	    			data:{"eventType":eventtype},
	    			success:function(result){
	    				var htmlList=[];
	    			    $.each(result, function(index, value) {
	    			    	htmlList.push('<li>'+value.CUSTNAME+'<span><strong>'+value.COUNT+'</strong/></span></li>');
	    				});
	    			    $("#expireWarnId").html(htmlList.join(" "));
	    			    htmlList=[];
	    			}
	    		  });
	    	}
	    function amInfo(eventtype){	
	    	 $.ajax({
	    			url:"${ctx}/alm/almInfo",
	    			data:{"eventType":eventtype},
	    			success:function(result){
	    				var htmlList=[];
	    			    $.each(result, function(index, value) {
	    			    	htmlList.push('<li>'+value.RULENAME+'<span><strong>'+value.COUNT+'</strong></span></li>');
	    				});
	    			    $("#almInfoId").html(htmlList.join(" "));
	    			    htmlList=[];
	    			}
	    		  });
	    	}
	    function notice(eventtype){
	    	 $.ajax({
	    			url:"${ctx}/custIndex/initNotice",
	    			data:{"eventType":eventtype},
	    			success:function(result){
	    				var htmlList=[];
	    			    $.each(result, function(index, value) {
	    			    	htmlList.push('<li>'+value.NOTICE_TITLE+'<span>'+value.STARTTIME+'</span></li>');
	    				});
	    			    $("#noticeId").html(htmlList.join(" "));
	    			    htmlList=[];
	    			}
	    		  });
	    	}
	    function infoOutline(){
	    	debugger
	      	 $.ajax({
	      			url:"${ctx}/info/infoOutline",
	      			data:{},
	      			success:function(result){
	      				debugger
	      				if(result.rate=="9999%"){
	      				    $("#rateId").html("+∞%");
	      				}else{
	      					$("#rateId").html(result.rate);
	      				}
	      				$("#cardsAllId").html(result.cardCountAll);
	      				$("#newCardsId").html(result.newCardsOfCurrentMonth);
	      			}
	      		  });
	      	}
	</script>
</body>
</html>
