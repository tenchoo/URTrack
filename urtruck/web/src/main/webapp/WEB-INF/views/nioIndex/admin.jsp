<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.Data.View' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui3/css/style.css">
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/unit/echarts.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/ui3/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body style="background: transparent;">
	<input type="hidden" id="totalNum" value="${count}" />
	<div class="output">
		<p>
			连接数：<span class="tosize"><img
				src="${ctx}/static/ui3/images/num_1.png"><img
				src="${ctx}/static/ui3/images/num_2.png"><img
				src="${ctx}/static/ui3/images/num_3.png"><img
				src="${ctx}/static/ui3/images/num_4.png"><img
				src="${ctx}/static/ui3/images/num_5.png"><img
				src="${ctx}/static/ui3/images/num_6.png"><img
				src="${ctx}/static/ui3/images/num_7.png"><img
				src="${ctx}/static/ui3/images/num_8.png"><img
				src="${ctx}/static/ui3/images/num_9.png"><img
				src="${ctx}/static/ui3/images/num_0.png"></span> 位
		</p>
	</div>
	<div class="region_data">
		<div class="data">
			<h3>运营商</h3>
			<div class="data_s">
				<p>
					移动<span id="y">39230</span>
				</p>
				<p>
					联通<span id="l">39230</span>
				</p>
				<p>
					电信<span id="d">39230</span>
				</p>
			</div>
			<h3>卡状态</h3>
			<div class="data_s">
				<p>
					待激活<span id="w">39230</span>
				</p>
				<p>
					在网<span id="z">39230</span>
				</p>
				<p>
					停机<span id="t">39230</span>
				</p>
			</div>
		</div>
	</div>
	<div class="region_view" id="main">环形图</div>
	<div class="clear"></div>
	<div class="search_data">
		<div class="data_title">
			<ul class="clearfix">
				<li><label>时间</label> <input id="sdate"
					class="Wdate startTime mgL02" name="startTime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true,maxDate:'#F{$dp.$D(\'edate\')}',onpicked:pickeTimeFn})" />
					<b class="timeLine">至</b> <input id="edate" class="Wdate endTime"
					name="endTime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true,minDate:'#F{$dp.$D(\'sdate\')}',startDate:'#F{$dp.$D(\'sdate\',{d:+1})}',onpicked:pickeTimeFn})" />
					<button class="btn02" id="confirmBtn">确定</button> <i></i></li>
				<li>连接数<i></i></li>
				<li>语音<i></i></li>
				<li><span>流量</span><i></i></li>
				<li>短信<i></i></li>
			</ul>
		</div>
		<div class="data_main">
			<ul>
				<li class="data_time">
		  					<span class="sTime"></span>--<span class="eTime"></span>
		  				</li>
		  				<li class="data_conn" id="conn">--</li>
		  				<li class="data_earn" id="voice">--</li>
		  				<li class="data_flow" id="fn">--</li>
		  				<li class="data_earnMom" id="sms">--</li>
			</ul>
		</div>

		<script type="text/javascript">
			 function pickeTimeFn(){
		          var _val = this.value,
		              _name = this.name;
		          if(_name =='startTime'){
		              $('.sTime').text(_val);
		          }else if(_name =='endTime'){
		              $('.eTime').text(_val);
		          }
		        //执行统一搜索

		      };
			//时间显示隐藏列表
			switchTab($('.time') , $('.data_core') , 'click');
	
			//控制显示隐藏
			function switchTab(obj,list,oEvent){
				obj.bind(oEvent , function(){
					if(oEvent == 'click'){
						if(list.is(':hidden')){
							list.show();
						}else{
							list.hide();
						}
					}else if(oEvent == 'mouseover'){
						list.show();
					}else if(oEvent == 'mouseout'){
						list.hide();
					}
	
					return false;
				});
	
				$(document).bind(oEvent , function(){
					list.hide();
				})
			} 
			function numCount(count){
			    var strNum=count+'';
			    var arrNum=strNum.split('').reverse().join('').replace(/(\d{3})/g,'$1a').replace(/\,$/,'').split('').reverse();
			    var arrLength=arrNum.length;
			    if(arrLength%4==0){
			    	var str=arrNum.join('').substring(1);
			    	arrNum=str.split('');
			    }
			    var html="";
			    $.each(arrNum,function(index,value){
			        html+='<img class="imgTab" src="../static/ui3/images/num_'+value+'.png">';
			        $('.tosize').html(html);
			    });
			}
			numCount($("#totalNum").val());
			var total = {
				//  y--移动   l--联通   d--电信   w--待激活   z--在网   t--停机 fn:流量
				//测试期
				/* "testSession" : {
					"y" : 1,
					"l" : 1,
					"d" : 1,
					"w" : 1,
					"z" : 1,
					"t" : 1,
					"fn" : 100
				},
				//正式期
				"formalizationPhase" : {
					"y" : 2,
					"l" : 2,
					"d" : 2,
					"w" : 2,
					"z" : 2,
					"t" : 2,
					"fn" : 200
				},
				//休眠期
				"dormancyStage" : {
					"y" : 3,
					"l" : 3,
					"d" : 3,
					"w" : 3,
					"z" : 3,
					"t" : 3,
					"fn" : 300
				},
				//销号期
				"cancellationPeriod" : {
					"y" : 3,
					"l" : 3,
					"d" : 3,
					"w" : 3,
					"z" : 3,
					"t" : 3,
					"fn" : 4000
				}  */
			};
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
					},error(data){
						console.log(data);
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
							color : '#fff'
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
						center : [ '50%', '40%' ],
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
						$("#fn").text(total.testSession.fn);
						$("#voice").text(total.testSession.voice);
						$("#sms").text(total.testSession.sms);
						$("#conn").text(total.testSession.y+total.testSession.l+total.testSession.d);
					} else if (params.name == '正式期') {
						$("#y").text(total.formalizationPhase.y);
						$("#l").text(total.formalizationPhase.l);
						$("#d").text(total.formalizationPhase.d);
						$("#w").text(total.formalizationPhase.w);
						$("#z").text(total.formalizationPhase.z);
						$("#t").text(total.formalizationPhase.t);
						$("#fn").text(total.formalizationPhase.fn);
						$("#voice").text(total.formalizationPhase.voice);
						$("#sms").text(total.formalizationPhase.sms);
						$("#conn").text(total.formalizationPhase.y+total.formalizationPhase.l+total.formalizationPhase.d);
					} else if (params.name == '休眠期') {
						$("#y").text(total.dormancyStage.y);
						$("#l").text(total.dormancyStage.l);
						$("#d").text(total.dormancyStage.d);
						$("#w").text(total.dormancyStage.w);
						$("#z").text(total.dormancyStage.z);
						$("#t").text(total.dormancyStage.t);
						$("#fn").text(total.dormancyStage.fn);
						$("#voice").text(total.dormancyStage.voice);
						$("#sms").text(total.dormancyStage.sms);
						$("#conn").text(total.dormancyStage.y+total.dormancyStage.l+total.dormancyStage.d);
					} else if (params.name == '销号') {
						$("#y").text(total.cancellationPeriod.y);
						$("#l").text(total.cancellationPeriod.l);
						$("#d").text(total.cancellationPeriod.d);
						$("#w").text(total.cancellationPeriod.w);
						$("#z").text(total.cancellationPeriod.z);
						$("#t").text(total.cancellationPeriod.t);
						$("#fn").text(total.cancellationPeriod.fn);
						$("#voice").text(total.cancellationPeriod.voice);
						$("#sms").text(total.cancellationPeriod.sms);
						$("#conn").text(total.cancellationPeriod.y+total.cancellationPeriod.l+total.cancellationPeriod.d);
					}
				});
			}
			
			initData();
			var dataPartition = function(){
				$('.list').find('a').bind('click' , function(ev){
					var timeVal = $(this).text();
					$('.time').find('em').text(timeVal);
					ev.preventDefault()
				})
			}
			$('#confirmBtn').on('click',function(){
				dataPartition();
				initData();
			})
		</script>
	</div>
</body>
</html>