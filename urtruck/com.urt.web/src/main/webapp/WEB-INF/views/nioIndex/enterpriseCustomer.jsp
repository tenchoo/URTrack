<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html class="overHid">
<head>
<meta charset="UTF-8">
<title>企业用户首页</title>
<!-- css -->
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui3/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/unit/echarts.min.js"></script>
</head>
<body class="add_bg_data">
	<div class="main">
		<div class="clear"></div>
		<div class="container">
			<!-- 加载内容放这里 -->
			<div class="model_q whole">
				<h3 class="clearfix">
					<span class="Situation">整体情况</span><i class="more hide">更多>></i>
				</h3>
				<div class="disk">
					<!-- <div id="echartsPie" style="width: 300px; height: 280px"></div>
					<span class="disk_title">运营商</span>
					<div id="nameAndSum"></div> -->
					<div class="region_view" id="main" style="width: 300px; height: 280px"><span class="disk_title">生命周期</div>
				</div>
				<div class="disk_line">
					<!-- <div id="echartsPie2" style="width: 440px; height: 280px;"></div> -->
					<div style="width: 440px; height: 280px;">
						<div class="data">
							<h3>运营商</h3>
							<div class="data_s">
								<p style="margin-bottom:0px;">
									移动<span id="y">39230</span>
								</p>
								<p style="margin-bottom:0px;">
									联通<span id="l">39230</span>
								</p>
								<p style="margin-bottom:0px;">
									电信<span id="d">39230</span>
								</p>
							</div>
							<h3>卡状态</h3>
							<div class="data_s">
								<p style="margin-bottom:0px;">
									待激活<span id="w">39230</span>
								</p>
								<p style="margin-bottom:0px;">
									在网<span id="z">39230</span>
								</p>
								<p style="margin-bottom:0px;">
									停机<span id="t">39230</span>
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="model_q survey">
				<h3>
					<span>信息概况</span><em>自定义视图</em>
				</h3>
				<div class="m_l">
					<span><i></i><em>总连接数</em></span>
					<p id="cardsAllId"></p>
				</div>
				<div class="m_l">
					<span><i></i><em>环比</em></span>
					<p id="rateId"></p>
				</div>
				<div class="m_l">
					<span><i></i><em>本月新增</em></span>
					<p id="newCardsId"></p>
				</div>
			</div>
			<div class="model_q whole">
				<h3 class="clearfix">
					<span class="Situation">本月情况</span><i class="more hide">更多>></i>
				</h3>
				<div class="disk">
					<div id="echartsPie3" style="width: 300px; height: 280px"></div>
					<span class="disk_title disk_title2">本月<br />产品订购
					</span>
				</div>
				<div class="disk_line mgT02">
					<div id="echartsPie4" style="width: 440px; height: 300px"></div>
				</div>
			</div>
			<div class="model_q survey survey_list">
				<img src="${ctx}/static/ui3/images/ul_icon.png">
				<h3>
					<span>重要通知</span><em style="background: none"
						onclick="noticeMore()">查看更多</em>
				</h3>
				<ul id="noticeId">
				</ul>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${ctx}/static/ui3/js/unit/echarts.min.js"></script>
	<script>
	var total = {};
    $(function(){
    	initData();
    	//operatChart();
    	//stateCard();
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
 	<%-- function operatChart(){
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
}	--%>
 	
    //1卡基本状态
 	<%-- function stateCard(){
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
	} --%>
    /* function operatChart2(operateors,states,series){
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
        	    },
        	   
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
    } */
    /* function operatChart1(data1,data2){
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
					            name:'运营商',
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
					            name:'运营商',
					            type:'pie',
					            radius: ['25%', '55%'],

					            data:data2
					        }
					    ]
        	};
			var myDoughnut = echarts.init(document.getElementById("echartsPie"))
				myDoughnut.setOption(option);
    }
     */
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
    	        text: '近15天流量使用统计',
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
		            name: "流量统计",
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
				dataType : "json",
				success : function(data) {
					operatChart4(data.data1, data.data2);
				}

			});
		}
		function operatChart4(data1, data2) {
			var option = {
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b}: {c} ({d}%)"
				},
				color : [ '#4bceff', '#009e96', '#0194ff', "#87cefa",
						"#da70d6", "#32cd32", "#6495ed", "#ff69b4", "#ba55d3",
						"#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347",
						"#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666",
						"#3cb371", "#b8860b", "#30e0e0" ],
				legend : {
					orient : 'horizontal',
					x : 'center',
					y : 'bottom',
					itemWidth : 10,
					itemHeight : 10,
					data : data1
				},
				series : [ {
					name : '本月产品订购',
					type : 'pie',
					selectedMode : 'single',
					radius : [ 0, '30%' ],

					label : {
						normal : {
							position : 'inner'
						}
					},
					labelLine : {
						normal : {
							show : false
						}
					}
				}, {
					name : '本月产品订购',
					type : 'pie',
					radius : [ '35%', '65%' ],

					data : data2
				} ]
			};

			var myDoughnut = echarts.init(document
					.getElementById("echartsPie3"))
			myDoughnut.setOption(option);
		}
		function initData (){
			$.ajax({
				url:"${ctx}/nioIndex/queryDetailByNio",
				data: {"startDate":$("#sdate").val(),"endDate":$("#edate").val()},
				dataType:"json",
				type:"POST",
				async:false,
				success:function(data){
					debugger;
					console.log("data="+data);
					total=data;
					console.log("total="+total)
				}
			});
			var myChart = echarts.init(document.getElementById('main'));
			option = {
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : [ '测试期', '正式期', '休眠期', '销号' ],
					textStyle : {
						color : '#000'
					}
				},
				toolbox : {
					show : false,
					feature : {
						mark : {
							show : true
						},
						dataView : {
							show : true,
							readOnly : false
						},
						magicType : {
							show : true,
							type : [ 'pie', 'funnel' ],
							option : {
								funnel : {
									x : '25%',
									width : '50%',
									funnelAlign : 'center',
									max : 1548
								}
							}
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				calculable : true,
				series : [ {
					name : '生命周期',
					type : 'pie',
					center : [ '70%', '40%' ],
					radius : [ '25%', '50%' ],
					itemStyle : {
						normal : {
							label : {
								show : false
							},
							labelLine : {
								show : false
							}
						},
						emphasis : {
							label : {
								show : true,
								position : 'center',
								textStyle : {
									fontSize : '15',
									fontWeight : 'bold'
								}
							}
						}
					},
					data : [
							{
								value : total.testSession.y
										+ total.testSession.l
										+ total.testSession.d,
								name : '测试期'
							},
							{
								value : total.formalizationPhase.y
										+ total.formalizationPhase.l
										+ total.formalizationPhase.d,
								name : '正式期'
							},
							{
								value : total.dormancyStage.y
										+ total.dormancyStage.l
										+ total.dormancyStage.d,
								name : '休眠期'
							},
							{
								value : total.cancellationPeriod.y
										+ total.cancellationPeriod.l
										+ total.cancellationPeriod.d,
								name : '销号'
							} ]
				} ]
			};

			myChart.setOption(option);
			$("#y").text(
					total.testSession.y + total.formalizationPhase.y
							+ total.dormancyStage.y
							+ total.cancellationPeriod.y);
			$("#l").text(
					total.testSession.l + total.formalizationPhase.l
							+ total.dormancyStage.l
							+ total.cancellationPeriod.l);
			$("#d").text(
					total.testSession.d + total.formalizationPhase.d
							+ total.dormancyStage.d
							+ total.cancellationPeriod.d);
			$("#w").text(
					total.testSession.w + total.formalizationPhase.w
							+ total.dormancyStage.w
							+ total.cancellationPeriod.w);
			$("#z").text(
					total.testSession.z + total.formalizationPhase.z
							+ total.dormancyStage.z
							+ total.cancellationPeriod.z);
			$("#t").text(
					total.testSession.t + total.formalizationPhase.t
							+ total.dormancyStage.t
							+ total.cancellationPeriod.t);
			myChart.on('click', function(params) {
				console.log(params);
				if (params.name == '测试期') {
					$("#y").text(total.testSession.y);
					$("#l").text(total.testSession.l);
					$("#d").text(total.testSession.d);
					$("#w").text(total.testSession.w);
					$("#z").text(total.testSession.z);
					$("#t").text(total.testSession.t);
				} else if (params.name == '正式期') {
					$("#y").text(total.formalizationPhase.y);
					$("#l").text(total.formalizationPhase.l);
					$("#d").text(total.formalizationPhase.d);
					$("#w").text(total.formalizationPhase.w);
					$("#z").text(total.formalizationPhase.z);
					$("#t").text(total.formalizationPhase.t);
				} else if (params.name == '休眠期') {
					$("#y").text(total.dormancyStage.y);
					$("#l").text(total.dormancyStage.l);
					$("#d").text(total.dormancyStage.d);
					$("#w").text(total.dormancyStage.w);
					$("#z").text(total.dormancyStage.z);
					$("#t").text(total.dormancyStage.t);
				} else if (params.name == '销号') {
					$("#y").text(total.cancellationPeriod.y);
					$("#l").text(total.cancellationPeriod.l);
					$("#d").text(total.cancellationPeriod.d);
					$("#w").text(total.cancellationPeriod.w);
					$("#z").text(total.cancellationPeriod.z);
					$("#t").text(total.cancellationPeriod.t);
				}
			});
		}
	</script>
</body>
</html>

