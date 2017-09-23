<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></title>
<link href="${ctx}/static/toWeb/css/dateRange.css" rel="stylesheet" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery.cycle.all.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/dateRange.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

</head>

<body>

    <div class="header bg02">
        <div class="centerBox">
            <a href="javascript:void(0)" class="logo"></a>
            <dl class="nav">
                <dt class="current"><a href="${ctx}/customerQuery/loginSuccessIndex"><fmt:message bundle='${pageScope.bundle}'  key='home page' /></a></dt>
                <dt class="navPro"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></a></dt>
                <dt class="navFlow current"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='phone.data.service' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.connection.platform' /></a></dt>
                <dt><a href="${ctx}/customerQuery/toBrandIntroduction"><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></a></dt>
                <dt class="account_1">
                    <span class="photo"><img src="${pageContext.request.contextPath}/static/toWeb/images/photo.png" width="42" height="42" /></span>
                    <span class="acc">${userName }</span>
                </dt>
                <dt class="account">
                	<a href="${ctx}/customerQuery/toExit" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='quit' /></a>
                </dt>
                 <dd class="pro hide">
                    <div class="proInner">
                        <ul class="proSlide">
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" class="arrowR"></a>
                </dd>      
           		<dd class="flow hide">
                	<a class="" href=""><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></a>
                	<a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></a>
                	<a href="${ctx}/customerQuery/toRecharge"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a>
                </dd> 
            </dl>
        </div>
    </div>
    
    <div class="flowBanner">
        <div class="inner">
              <span class="text01"><fmt:message bundle='${pageScope.bundle}'  key='Device.number' /></span>
             		<select id="iccid" name="iccid" class="input-text" style="width:200px;">
             			<c:if test="${!empty iccid}">
					        <option value="${iccid }">${iccid}</option>
		                </c:if>
             		</select>
                	<input type="hidden" id="custId" value="${custId }">
                	<input type="hidden" id="iccidHidden" value="${iccid }">
        	<dl class="dl">
            	<dt>
                	<div class="text02"><fmt:message bundle='${pageScope.bundle}'  key='current.balance' /></div>
                    <div class="text03"><span class="sub" id="dataRemaining">${dataRemaining }</span>M</div>
                </dt>
                <dd>
                	<div class="text04"><span id="userName">${userName }</span></div>
                    <div class="text05"><fmt:message bundle='${pageScope.bundle}'  key='<fmt:message bundle='${pageScope.bundle}'  key='' />' />~</div>
                    <div class="text06"><fmt:message bundle='${pageScope.bundle}'  key='telecom.plan.in.use' /><span id="ratePlanName">${ratePlanName }</span>,<span id="expirationDate">${expirationDate }</span>到期<br />
                    <!-- 尚未使用流量包剩余1024M，上一个流量包到期后自动生效 -->
                    </div>
                                        
                </dd>
            </dl>
        	<div class="text07"><fmt:message bundle='${pageScope.bundle}'  key='deadline' />  <span id="timestamp">${timestamp }</span></div>
            <div class="btnBox">
            	<!-- <a href="javascript:;" class="btn">充 值</a> -->
                <!-- <a href="javascript:;" class="btn">刷 新</a> -->
                <a href="javascript:void(0)" class="btn" id="refresh"><fmt:message bundle='${pageScope.bundle}'  key='refresh' /></a>
            </div>
        </div>
    </div>
    
    <div class="centerBox">
    	
        <ul class="flowTabs">
        	<li class="selected">
            	<span><fmt:message bundle='${pageScope.bundle}'  key='Recharge.records' /></span>
                <b></b>
            </li>
        	<li >
            	<span><fmt:message bundle='${pageScope.bundle}'  key='expendse.records' /></span>
            </li>
        </ul>
        
        <dl class="flowInfo">
        	<dd>
            	<div class="search">
                	<span><fmt:message bundle='${pageScope.bundle}'  key='starting and ending dates' />：</span>
                    <input id="date1" type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.starting.and.ending.dates ' />"/>
                    <a href="javascript:void(0)" class="btn" id="search"><fmt:message bundle='${pageScope.bundle}'  key='Search' /></a>
                </div>
                <input type='hidden' class='btn'  value='$(currentPage)' id='curPage' />
                <input type="hidden" id="chargeMsg" value="${chargeMsg }">
                <table class="flowTable" id="table">
                    <tr >
                    	<th width="20%"><fmt:message bundle='${pageScope.bundle}'  key='Order.numbers' /></th>
                    	<th width="20%"><fmt:message bundle='${pageScope.bundle}'  key='data.plan' /></th>
                    	<th width="20%"><fmt:message bundle='${pageScope.bundle}'  key='purchase.time' /></th>
                    	<th width="20%"><fmt:message bundle='${pageScope.bundle}'  key='Payment.amount' /></th>
                    	<th width="20%"><fmt:message bundle='${pageScope.bundle}'  key='Payment.method' /></th>
                    </tr>
                    
                </table>
            </dd>
        	<dd style="display:none;">
            	<div class="search">
                	<span><fmt:message bundle='${pageScope.bundle}'  key='starting and ending dates in one months' />：</span>
                    <input id="date2" type="text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.starting.and.ending.dates ' />" />
                    <a href="javascript:void(0)" class="btn" id="search1"><fmt:message bundle='${pageScope.bundle}'  key='Search' /></a>
                </div>
		        <div class="cl pd-5">
					<div id="histogram1"
						style="height: 500px; width: 90%; border: 0px solid #ccc; padding: 10px;">
					</div>
				</div>
            </dd>
        </dl>
    
    </div>

	<div class="footer">
    	<div class="logoBox">
        	<div class="footLogo"></div>
            <div class="share">
            	<a href="javascript:;" class="sina"><fmt:message bundle='${pageScope.bundle}'  key='click.for.attention' /></a>
                <span class="qrcode01"><fmt:message bundle='${pageScope.bundle}'  key='WeChat.Official.Accounts' /></span>
                <!--<span class="qrcode02">APP下载</span>-->
            </div>
        </div>
        <div class="copyright">©2017 Lenovo Connect all right reserved</div>
    </div>
    
    <div id="div"></div>
    
 
 <script type="text/javascript">
	if ($("#chargeMsg").val()!="" && $("#chargeMsg").val()!=null) {
		alert($("#chargeMsg").val());
	} 
	//流量刷新
	$("#refresh").click(function(){
		var iccid = $("#iccid").val();
		if(iccid == ""){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />！');
			return false;
		}
		if(iccid.length>20){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.correct.device.number' />！');
			return false;
		}
		$.ajax({
			url:"${ctx}/customerQuery/doQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccid,			
			},
			success:function(data){
				if(data.msg == 0){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.correct.device.number' />");
					$("#dataRemaining").html("");
					$("#userName").html(data.userName);
					$("#ratePlanName").html("");
					$("#timestamp").html("");
					$("#expirationDate").html("");	
				}else{
					$("#dataRemaining").html(data.dataRemaining);
					$("#userName").html(data.userName);
					$("#ratePlanName").html(data.ratePlanName);
					$("#timestamp").html(data.timestamp);
					$("#expirationDate").html(data.expirationDate);	
				}
			}
		});
	});
	
	
	
	//定义变量
	var startDate,endDate,curPage=1,type=1;
	//选择日期后变量赋值
	var dateRange1 = new pickerDateRange('date1', {
		isTodayValid : true,
		startDate : '',
		endDate : '',
		needCompare : false,
		defaultText : ' 至 ',
		autoSubmit : true,
		inputTrigger : 'input_trigger1',
		theme : 'ta',
		success : function(obj) {
				startDate=obj.startDate;
			 	endDate=obj.endDate;
			
		}
	});
	var dateRange2 = new pickerDateRange('date2', {
		isTodayValid : true,
		startDate : '',
		endDate : '',
		needCompare : false,
		defaultText : ' 至 ',
		autoSubmit : true,
		inputTrigger : 'input_trigger1',
		theme : 'ta',
		success : function(obj) {
			startDate=obj.startDate.substr(0,7);
		 	endDate=obj.endDate.substr(0,7);
		}
	});
	//查询充值记录和消费记录
	$("#search").click(function(){
		if(!startDate){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='please.enter.starting.time' />！');
			return;
		}
		if(!endDate){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='please.enter.ending.time' />！');
			return;
		}
		if($('.flowTabs').children().eq(0).hasClass('selected')){
			type=1;
			//添加是否选择起始截止时间判断
			searchByPage(curPage,startDate,endDate,type);
		}/* else{
			type=2;
			queryBy();
		} */
		
		

	});
	
	$("#search1").click(function(){
		if(!startDate){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='please.enter.starting.time' />！');
			return;
		}
		if(!endDate){
			alert('<fmt:message bundle='${pageScope.bundle}'  key='please.enter.ending.time' />！');
			return;
		}
		if($('.flowTabs').children().eq(1).hasClass('selected')){
			type=2;
			//添加是否选择起始截止时间判断
			queryBy();
		}
		
	});
	
	//分页查询
	function toPage(state){
		$("#table").html('');
		if(state==-1){
			curPage--;
		}else if(state==-2){
			curPage++;
		}
		else if(state==1){
			curPage=1;
		}
		else{
			curPage=state;
		}
		searchByPage(curPage,startDate,endDate,tpye);
		}
	
	function searchByPage(page,startDate,endDate,tpye){
		$.ajax({ 
			url:"${ctx}/customerQuery/chargeSearch",
			type:"post",
			dataType: "json",
			data:{
				"iccid":$("#iccid").val(),
				"currentPage":page,
				"startTime":startDate,
				"endTime":endDate,
				"type":type
			},
			success:function(data){
				if (data.msg == 0){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='no.recharge.records.in.this.period' />");
				} else{
					var list = eval(data.tradeFeeSubDtoList);
					var htmlEl='',attr='',attr2='',
					payType='<fmt:message bundle='${pageScope.bundle}'  key='Alipay' />';
					if(curPage==1){
						attr2='';
						attr="disabled='disabled'";
					}else{
						attr='';
						attr2="disabled='disabled'";
					}
					for ( var i = 0; i < list.length; i++) {
						if(list[i].payType==2){
							payType='<fmt:message bundle='${pageScope.bundle}'  key='Wechat' />';
						}
						htmlEl+="<tr>"+
	            		"<td>"+list[i].tradeId+"</td>"+
	            		"<td>"+list[i].goodsDto.goodsName+"</td>"+
	            		"<td>"+list[i].acceptDate+"</td>"+
	            		"<td>"+list[i].fee+"</td>"+
	            		"<td>"+payType+
	            		"<a href='javaScript:detail("+list[i].tradeId+");' class='btn_info' ></a>"+
	            		"</td>"+
	            	"</tr>"+
	                "<tr>";
					}
					htmlEl+="<th colspan='5'>"+
								"<input type='button' class='btn'  value='<fmt:message bundle='${pageScope.bundle}'  key='The.first.page' />' "+attr+ " onclick='toPage(1)'>"+
								"<input type='button' class='btn'  value='<fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' />' "+attr+" onclick='toPage(-1)'>"+
								"<input type='button' class='btn'  value='<fmt:message bundle='${pageScope.bundle}'  key='The.next.page' />' "+attr2+" onclick='toPage(-2)'>"+
								"<input type='button' class='btn'  value='<fmt:message bundle='${pageScope.bundle}'  key='The.last.page' />' "+attr2+" onclick='toPage("+data.totalPage+")'>"+
							"</th>"+
							"</tr>"
					$("#table").append(htmlEl);	

				}
			}
		});
	}
	
	function queryBy() {
		$.ajax({
			url : "${ctx}/customerQuery/recordSearch",
			type : "post",
			dataType : "json",
			data : {
				"iccid" : $("#iccid").val(),
				"start" : startDate,
				"type" : type,
			},
			success : function(data) {
				if(data.msg == 0){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='data.usage.conditions.in.this.month' />");
				}else{
					doHistogram1(data.xList, data.yList);
				}
				
			}
		});
	}

			function doHistogram1(xStr,yStr1) {
			var histogram1 = echarts.init(document.getElementById('histogram1'));
			histogram1.setOption({
				    title : {
				        text: '<fmt:message bundle='${pageScope.bundle}'  key='Statistical.chart.of.Monthly.consumer.record ' />',
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['<fmt:message bundle='${pageScope.bundle}'  key='data.value' />']
				    },
				    toolbox: {
				        show : true,
				        feature : {
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
				            data : xStr,
				            axisLabel : {
				                formatter: '{value}日'
				            },
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            axisLabel : {
				                formatter: '{value}'
				            },
				        }
				    ],
				    series : [
				        {
				            name:'<fmt:message bundle='${pageScope.bundle}'  key='data.value' />：',
				            type:'bar',
				            data:yStr1,
				        },
				    ]
				});
		}
	
	
	
	
	//查看充值详情
	function detail(tradeId){
		
		var iccid = $("#iccid").val();
		$('.btn_info').click(function(){
			var self = $(this);
			$.ajax({
				url:"${ctx}/customerQuery/chargeDetailSearch",
				type:"post",
				dataType: "json",
				data:{
					"iccid":iccid,			
					"tradeId":tradeId,			
				},
				success:function(data){
					$("#div").append(
						    "<dl class='dialog'>"+
					    	"<dt>"+
					        	"<fmt:message bundle='${pageScope.bundle}'  key='Order.details' />"+
					            " <a href='javascript:;' class='close'></a>"+
					        "</dt>"+
					        "<dd>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='Order numbers' />："+data.tradeFeeSubDto.tradeId+"</div>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='Recharge account' />："+data.userName+"</div>"+
				        	"<div class='text01'>ICCID："+data.iccid+"</div>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='data plan' />："+data.goodsName+"</div>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='Recharge type' />："+data.tradeFeeSubDto.calculateTag+"</div>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='Recharge amount' />："+data.tradeFeeSubDto.fee+"</div>"+
				        	"<div class='text01'><fmt:message bundle='${pageScope.bundle}'  key='Payment method' />："+data.tradeFeeSubDto.payType+"</div>"+
						    "</dd>"+
						    "</dl>"										
								);					
					self.addClass('selected');					
					//$('.dialog').show(); 
					$('#div .close').click(function(){
						$('.btn_info').removeClass('selected');
						$(this).parents('.dialog').hide();
					});
				}
				
			});

		});


	};
			
    $(function(){    

        $('.flowTabs li').click(function(){
			var self = $(this);
			self.addClass('selected').siblings().removeClass('selected');
			$('.flowInfo > dd').eq(self.index()).show().siblings().hide();
		});
		
		$('.btn_info').click(function(){
			var self = $(this);
			self.addClass('selected');
			$('.dialog').show();
		});
		
		var iccidHidden = $("#iccidHidden").val();
		if(iccidHidden==""){
			$.ajax({
				url:"${ctx}/customerQuery/lookRecharge",
				data: {"custId":$("#custId").val()},
				success:function(result){
					var select=$("#iccid").select2({
						width : 250,  
						placeholder: '<fmt:message bundle='${pageScope.bundle}'  key='Device.number' />iccid',
						tags: "true",
						allowClear: true,
						data:result
					});
					$("#iccid").change(function() {
						$.ajax({
							url:"${ctx}/customerQuery/doQuery",
							data: {"iccid":$("#iccid").val()},
							success:function(data){
								if(data.msg == 0){
									alert("<fmt:message bundle='${pageScope.bundle}'  key='Please enter the correct device number' />");
									$("#dataRemaining").html("");
									$("#iccid").html("");
									$("#userName").html(data.userName);
									$("#ratePlanName").html("");
									$("#timestamp").html("");
									$("#expirationDate").html("");	
								} else {
									$("#dataRemaining").html(data.dataRemaining);
									$("#iccid").html(data.iccid);
									$("#userName").html(data.userName);
									$("#ratePlanName").html(data.ratePlanName);
									$("#timestamp").html(data.timestamp);
									$("#expirationDate").html(data.expirationDate);	
								}
							}
						})
					})
				}
			});
		} else {

		}
		
    });
    </script> 
</body>
</html>
