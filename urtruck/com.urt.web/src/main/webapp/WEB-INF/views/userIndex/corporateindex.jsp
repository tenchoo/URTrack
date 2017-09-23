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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.user.home.page' /></title>
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
			<div class="model_q whole">
				<h3 class="clearfix"><span class="Situation"><fmt:message bundle='${pageScope.bundle}'  key='overall.situation' /></span><i class="more hide"><fmt:message bundle='${pageScope.bundle}'  key='more' />>></i></h3>
				<div class="disk">
					<div id="echartsPie" style="width:300px;height:280px"></div>
					<span class="disk_title"><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></span>
				<div id="nameAndSum">
					<!-- <p><i></i>移动：4034</p>
					<p><i></i>联通：4034</p>
					<p><i></i>电信：4034</p> -->
				</div>
				</div>
				<div class="disk_line">
					<div id="echartsPie2" style="width:440px;height:280px;"></div>
				</div>
			</div>
			<div class="model_q survey">
				<h3><span><fmt:message bundle='${pageScope.bundle}'  key='general.information' /></span><em><fmt:message bundle='${pageScope.bundle}'  key='Custom.view' /></em></h3>
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
			<div class="model_q whole">
				<h3 class="clearfix"><span class="Situation"><fmt:message bundle='${pageScope.bundle}'  key='Situation.of.this.month' /></span><i class="more hide"><fmt:message bundle='${pageScope.bundle}'  key='more' />>></i></h3>
				<div class="disk">
					<div id="echartsPie3" style="width:300px;height:280px"></div>
					<span class="disk_title disk_title2"><fmt:message bundle='${pageScope.bundle}'  key='This.month' /><br/><fmt:message bundle='${pageScope.bundle}'  key='Product.order' /></span>
				</div>
				<div class="disk_line mgT02">
					<div id="echartsPie4" style="width:440px;height:300px"></div>
				</div>
			</div>
			<div class="model_q survey survey_list">
				<img src="${ctx}/static/ui3/images/ul_icon.png">
				<h3><span><fmt:message bundle='${pageScope.bundle}'  key='Important.notice' /></span><em style="background:none" onclick="noticeMore()"><fmt:message bundle='${pageScope.bundle}'  key='for.more.information' /></em></h3>
				<ul id="noticeId">
				</ul>
			</div>
	    </div>
	</div>
<script type="text/javascript" src="${ctx}/static/ui3/js/unit/echarts.min.js"></script>
	<script>
    $(function(){
    	operatChart();
    	stateCard();
    	operatChart3();
    	operatChartGoods();
    	infoOutline();
    	notice();
    })
    //查看更多
    function noticeMore(){
			url='${ctx}/corporate/noticeMore/';
			window.location.href=url
    }
    //重要通知
     function notice(){
    	 $.ajax({
    			url:"${ctx}/corporate/notice",
    			success:function(result){
    				var htmlList=[];
    			    $.each(result, function(index, value) {
    			    	htmlList.push('<li>'+value.NOTICE_TITLE+'<span><strong>'+value.STARTTIME+'</strong></span></li>');
    				});
    			    $("#noticeId").html(htmlList.join(" "));
    			    htmlList=[];
    			}
    		  });
    	}
    //信息概况
     function infoOutline(){
   	 $.ajax({
   			url:"${ctx}/info/infoOutline",
   			data:{},
   			success:function(result){
   				$("#cardsAllId").html(result.cardCountAll);
   				if(result.rate=="9999%"){
  				    $("#rateId").html("+∞%");
  				}else{
  					$("#rateId").html(result.rate);
  				}
   				$("#newCardsId").html(result.newCardsOfCurrentMonth);
   			}
   		  });
   	}
    //运营商
 	function operatChart(){
		$.ajax({
            type: "post",
            url: "<%=basePath %>corporate/corporateEcharts",
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
 	
    //1卡基本状态
 	function stateCard(){
			$.ajax({
                type: "post",
                url: "<%=basePath %>cust/customerIndexEcharts",
                dataType: "json",
                success: function (data) {
                	var operators=data.operatorList;
                	var states=data.statesList;
                	var sumMap = data.sums;
                	var operatorList=[];
                	var operatorIdList=[];
                	var stateList=[];
                	var stateIdList=[];
                	var columnValue=[];
                		
                		for(var k=0;k<operators.length;k++){
                			var operatorsElement=operators[k].split("_");
                    		operatorList.push(operatorsElement[0]);
                    		operatorIdList.push(operatorsElement[1]);
                		  }
                		for(var j=0;j<states.length;j++){
                			var startsElement=states[j].split("_");
                			stateList.push(startsElement[0]);
                			stateIdList.push(startsElement[1]);
                		}
                		for(var p=0;p<operatorList.length;p++){
                			var sum=[];
                			for(var q=0;q<stateIdList.length;q++){
                				var key=stateIdList[q]+"_"+operatorIdList[p];
                    			if(sumMap[key]){
                    			    sum.push(sumMap[key]);
                    			}else{
                    				sum.push(0);	
                    			}
                			}
                		 columnValue.push(eval('(' + ("{'name':'"+operatorList[p]+"','type':'bar','data':["+sum+"],'label':{'normal':{'show':true,'position':'top'}}}") + ')'));  
                		}
                	operatChart2(operatorList,stateList,columnValue);
                },
        });
	}
    function operatChart2(operateors,states,series){
        var option = {
        	    tooltip : {
        	        trigger: 'axis',
        	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
        	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        	        }
        	    },
        	    legend: {
			        orient: 'horizontal',
			        x: 'center',
			        y:10,
			        itemWidth:10,  
	                itemHeight:10,  
			        data: operateors
			    },
        	    color:['#4bceff','#009e96','#0194ff',"#87cefa", "#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"],
        	   /*  legend: {
        	    	x:'right',
        	        data:operateors
        	    }, */
        	   
        	    grid: {
        	        left: '3%',
        	        right: '4%',
        	        bottom: '3%',
        	        containLabel: true
        	        
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : states
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : series
        	};
    	var myDoughnut = echarts.init(document.getElementById("echartsPie2"))
		myDoughnut.setOption(option);
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
			        y:10,
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
    
    //3  15天流量情况
 	function operatChart3(){
			$.ajax({
                type: "post",
                url: "<%=basePath %>traffic/selectMonthFlowCountMB",
                dataType: "json",
                success: function (data) {
                	var sumMap=data.sumMap;
                	var dayList=data.dayList;
                	var flowData=[];
                	for(var i=0;i<dayList.length;i++){
                		var day=dayList[i];
                		var sum=sumMap[day];
                		if(sum==null||sum==""){
                			sum=0;
                		}
                		flowData.push(sum);
                	}
                	monthFlowCount(dayList,flowData);
                },
        });
	}
    function monthFlowCount(dayList,flowData){
       var option = {
     	    title : {
    	        text: "<fmt:message bundle='${pageScope.bundle}'  key='Traffic.usage.statistics.in.the.last.15.days' />",
    	        x:'center'
     	    },
    	    tooltip : {
    	        trigger: 'axis'
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
		            data: dayList,
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
		            name: "<fmt:message bundle='${pageScope.bundle}'  key='Traffic.statistics' />",
		            type: "line",
		            data: flowData,
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
    	};
       var myDoughnut = echarts.init(document.getElementById("echartsPie4"))
		myDoughnut.setOption(option);
    }
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
					            name:"<fmt:message bundle='${pageScope.bundle}'  key='Product.ordered.for.this.month' />",
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
					            name:"<fmt:message bundle='${pageScope.bundle}'  key='Product.ordered.for.this.month' />",
					            type:'pie',
					            radius: ['35%', '65%'],

					            data:data2
					        }
					    ]
        	};

        var myDoughnut = echarts.init(document.getElementById("echartsPie3"))
		myDoughnut.setOption(option);
        }
    </script>
    </body>
</html>
 
