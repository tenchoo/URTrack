<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>   
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Role.management' /></title>
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
	<!-- css -->
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
	<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
	
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
</head>
<body>
     <div>
         <div class="seconSec"><h1><fmt:message bundle='${pageScope.bundle}'  key='Customer.Basic.Information' /></h1></div><br>
	     <%-- <div class=""><img src="<%=basePath %>static/images/customerinfo/customer.png" style="float: left;width: 40px"> <span class="l" style="margin-top: 10px">客户基本信息</span> </div> --%>
	     <div style="padding-left:90px;">
		     <table class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0" cellspacing="0" class="tb" style="width: 93%;">
			  <tr class="zpTable">
			    <th><fmt:message bundle='${pageScope.bundle}'  key='Customer.Name' /></th>
			    <th><fmt:message bundle='${pageScope.bundle}'  key='Customer.Condition' /></th>
			    <th><fmt:message bundle='${pageScope.bundle}'  key='Certificate.Type' /></th>
			    <th><fmt:message bundle='${pageScope.bundle}'  key='Certificate.Number' /></th>
			  </tr>
			  <tr>
			    <td style="text-align:center;"><font size="1">${custName}</font></td>
			    <td style="text-align:center;">
			         <c:if test="${custState eq 0}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Potential.Customer' /></font>	</c:if>
			         <c:if test="${custState eq 1}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Customer.In.Use' /></font>	</c:if>
			         <c:if test="${custState eq 2}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Cutomer.Logged.Off' /></font>	</c:if>
			    </td>
			    <td style="text-align:center;">
			         <c:if test="${psptTypeCode eq 1}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Business.License' /></font>	</c:if>
				     <c:if test="${psptTypeCode eq 2}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Legal.Person.Certificate' /></font>	</c:if>
				     <c:if test="${psptTypeCode eq 3}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Organization.Code.Certificate' /></font>	</c:if>
				     <c:if test="${psptTypeCode eq 4}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Introduction.Letter' /></font>	</c:if>
				     <c:if test="${psptTypeCode eq 5}"><font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Note' /></font>	</c:if>
	            </td>
			    <td style="text-align:center;"><font size="1">${psptId}</font></td>
			  </tr>
			</table>
	     </div>
	    <%--  <label for="name" class="font12 fl">客户名称<fmt:message bundle='${pageScope.bundle}'  key='All' /> ：</label><lavel id="custName" class="font12 fl">${custName}</lavel><br><br>
	     <label for="name" class="font12 fl">证件类型<fmt:message bundle='${pageScope.bundle}'  key='All' /> ：</label><lavel id="certType" class="font12 fl">
	     <c:if test="${psptTypeCode eq 1}">营业执照<fmt:message bundle='${pageScope.bundle}'  key='All' />	</c:if>
	     <c:if test="${psptTypeCode eq 2}">法人证书<fmt:message bundle='${pageScope.bundle}'  key='All' />	</c:if>
	     <c:if test="${psptTypeCode eq 3}">组织机构代码证<fmt:message bundle='${pageScope.bundle}'  key='All' />	</c:if>
	     <c:if test="${psptTypeCode eq 4}">介绍信<fmt:message bundle='${pageScope.bundle}'  key='All' />	</c:if>
	     <c:if test="${psptTypeCode eq 5}">照会<fmt:message bundle='${pageScope.bundle}'  key='All' />	</c:if>
	     &nbsp;&nbsp;&nbsp;&nbsp;</lavel>
	     <label for="name" class="font12 fl">证件号码<fmt:message bundle='${pageScope.bundle}'  key='All' /> ：</label><lavel id="certNum" class="font12 fl">${psptId}</lavel> --%>
     </div>
     <%-- <div>
	     <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">账户基本信息</span> </div>
	     <label for="name" class="font12 fl">账户余额（元）：</label><lavel id="certNum" class="font12 fl">${deposiMoney}</lavel>
     </div> --%>
     <div>
         <div class="seconSec"><h1><fmt:message bundle='${pageScope.bundle}'  key='Card.Basic.Information' /></h1></div><br>
	     <%-- <div class="cl pd-5 bg-1 bk-gray mt-20"> <img src="<%=basePath %>static/images/customerinfo/info.png" style="float: left;width: 40px"><span class="l" style="margin-top: 10px"><fmt:message bundle='${pageScope.bundle}'  key='Card.Basic.Information' /></span> </div> --%>
         <div id="cardInfoObj"  style="height:500px;width:100%;padding:10px;"></div>
     </div>
     <div>
         <div class="seconSec"><h1><fmt:message bundle='${pageScope.bundle}'  key='Classification.Statistics.Of.Ordering.Products.In.This.Month' /></h1></div><br>
	     <%-- <div class="cl pd-5 bg-1 bk-gray mt-20"> <img src="<%=basePath %>static/images/customerinfo/info.png" style="float: left;width: 40px"><span class="l" style="margin-top: 10px"><fmt:message bundle='${pageScope.bundle}'  key='Classification.Statistics.Of.Ordering.Products.In.This.Month' /></span> </div> --%>
         <div id="goodsCount"  style="height:500px;width:100%;padding:10px;"></div>
     </div>
     <div>
         <div class="seconSec"><h1><fmt:message bundle='${pageScope.bundle}'  key='Data.Classification' /></h1></div><br>
	     <%-- <div class="cl pd-5 bg-1 bk-gray mt-20"> <img src="<%=basePath %>static/images/customerinfo/info.png" style="float: left;width: 40px"><span class="l" style="margin-top: 10px"><fmt:message bundle='${pageScope.bundle}'  key='Data.Classification' /></span> </div> --%>
	     <div class="cl pd-5">
	          <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Data.Use.Condition.In.Recent.15.Days(UnitG)' /></font></h5>
              <div id="flowCount" style="height:500px;width:50%;padding:10px;float:left;"></div>
              <div style="height:500px;width:40%;padding:10px;float:left;">
                   <div>
                       <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='Monthly.Traffic.Usage.Rankings.Top5' /></span>
                       <div style="height:400px;width:50%;padding:10px;float:left;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='This.Month.Sees.The.Most.Data.Traffic' /></span>
                            <table class="table table-border table-bordered table-hover table-bg table-sort " height="360px" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:40%;text-align:center;" height="50px"><fmt:message bundle='${pageScope.bundle}'  key='Card.Number' /></th>
						       <th style="text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='Data(M)' /></th>
						     </tr>
						     <c:forEach items="${maxFlows}" var="map">
                             <tr>
						       <td><label class="font12 fl">${map.ICCID}</label></td>
						       <td style="text-align:center;"><font size="1">${map.USECOUNT/1024}</font> </td>
						     </tr>
                             </c:forEach>
						    </table>
                       </div>
                       <div style="height:400px;width:50%;padding:10px;float:right;">
                           <span class="btn btn-primary radius" style="width:100%;text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='This.Month.Sees.The.Least.Data.Traffic' /></span>
                           <table class="table table-border table-bordered table-hover table-bg table-sort " height="360" border="2" cellpadding="0" cellspacing="0" class="tb">
						     <tr class="zpTable">
						       <th style="width:40%;text-align:center;" height="50px"><fmt:message bundle='${pageScope.bundle}'  key='Card.Number' /> </th>
						       <th style="text-align:center;"><fmt:message bundle='${pageScope.bundle}'  key='Data(M)' /></th>
						     </tr>
						     <c:forEach items="${minFlows}" var="map">
	                             <tr>
							       <td><label class="font12 fl">${map.ICCID}</label> </td>
							       <td style="text-align:center;"><font size="1">${map.USECOUNT/1024}</font> </td>
							     </tr>
                             </c:forEach>
						    </table>
                       </div>
                   </div>
              </div>
	     </div>
	     <div class="cl pd-5">
	          <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size="1"><fmt:message bundle='${pageScope.bundle}'  key='Last.month.traffic.usage.map' /></font></h5>
              <div id="flowSpread" style="height:500px;width:100%;padding:10px;"></div>
	     </div>
     </div>
     <script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
     <script>
	    operatChart1();
	    operatChart2();
	    operatChart3();
	    operatChart4();
	    
	    //1卡基本信息
	 	function operatChart1(){
				$.ajax({
	                type: "post",
	                url: "<%=basePath %>cust/customerIndexEcharts",
	                dataType: "json",
	                success: function (data) {
	                	var operators=data.operatorList;
	                	var states=data.statesList;
	                	var sumMap = data.sums;
	                	var series=[];
	                	var operatorList=[];
	                	var operatorIdList=[];
	                	var stateList=[];
	                	for(var k=0;k<operators.length;k++){
	                		var operatorsElement=operators[k].split("_");
	                		operatorList.push(operatorsElement[0]);
	                		operatorIdList.push(operatorsElement[1]);
	                	}
	                	for(var i=0;i<states.length;i++){
	                		var startsElement=states[i].split("_");
	                		stateList.push(startsElement[0]);
	                		var sum=[];
	                		for(var j=0;j<operatorIdList.length;j++){
	                			var key=startsElement[1]+"_"+operatorIdList[j];
	                			if(sumMap[key]){
	                			    sum.push(sumMap[key]);
	                			}else{
	                				sum.push(0);
	                			}
	                		}
	                		var serieOne={
	                	            name:'<fmt:message bundle='${pageScope.bundle}'  key='Being.Used' />',
	                	            type:'bar',
	                	            stack: '<fmt:message bundle='${pageScope.bundle}'  key='Whole.Amount' />',
	                	            barWidth : 60,
	                	            itemStyle : { normal: {label : {show: true, position: 'insideRight'}}},
	                	            data:[220, 182, 191]
	                	        }
	                		serieOne.name=startsElement[0];
	                		serieOne.data=sum;
	                		series.push(serieOne);
	                	}
	                	cardInfo(operatorList,stateList,series);
	                },
	        });
		}
        function cardInfo(operateors,states,series){
            var cardInfoObj = echarts.init(document.getElementById('cardInfoObj'));
            cardInfoObj.setOption({//卡基本信息
        	    tooltip : {
        	        trigger: 'axis',
        	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
        	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        	        }
        	    },
        	    legend: {
        	        data:states
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
       	            	type : 'category',
           	            data : operateors
        	        }
        	    ],
        	    yAxis : [
        	        {
        	        	type : 'value'
        	        }
        	    ],
        	    series : series
        	});
        }
       //2 本月订购产品分类统计
       function operatChart2(){
				$.ajax({
	                type: "post",
	                url: "<%=basePath %>cust/selectCategoryCount",
	                dataType: "json",
	                success: function (data) {
	                	var operateors=data.operatorList;
	                	var goods=data.goodsList;
	                	var sumMap = data.sums;
	                	var series=[];
	                	var operateorList=[];
	                	var operatiorIdList=[]
	                	var goodsNameList=[];
	                	for(var k=0;k<operateors.length;k++){
	                		var operateorElement=operateors[k].split("_");
	                		operateorList.push(operateorElement[0]);
	                		operatiorIdList.push(operateorElement[1]);
	                	}
	                	for(var i=0;i<goods.length;i++){
	                		var sum=[];
	                		var goodsElement=goods[i].split("_");
	                		goodsNameList.push(goodsElement[0]);
	                		for(var j=0;j<operatiorIdList.length;j++){
	                			var key=goodsElement[1]+"_"+operatiorIdList[j];
	                			if(sumMap[key]){
	                			    sum.push(sumMap[key]);
	                			}else{
	                				sum.push(0);
	                			}
	                		}
	                		var serieOne={
	                	            name:'1G',
	                	            type:'bar',
	                	            barWidth : 30,
	                	            data:[1000, 200, 100]
	                	        }
	                		serieOne.name=goodsElement[0];
	                		serieOne.data=sum;
	                		series.push(serieOne);
	                	}
	                	categoryInfo(operateorList,goodsNameList,series);
	                },
	        });
		}
     function categoryInfo(operateors,goods,series){
    	   var goodsCount = echarts.init(document.getElementById('goodsCount'));
           goodsCount.setOption({//本月订购产品分类统计
   	    tooltip : {
   	        trigger: 'axis',
   	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
   	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
   	        }
   	    },
   	    legend: {
   	        data:goods
   	    },
   	    toolbox: {
   	        show : true,
   	        feature : {
   	            mark : {show: true},
   	            dataView : {show: true, readOnly: false},
   	            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
   	            restore : {show: true},
   	            saveAsImage : {show: true}
   	        }
   	    },
   	    calculable : true,
   	    
   	    xAxis : [
   	        {
   	            type : 'value',
   	        }
   	    ],
   	    yAxis : [
   	        {
   	            type : 'category',
   	            data : operateors
   	        }
   	    ],
   	    series : series
   	});
       }
        
        //3  15天流量情况
	 	function operatChart3(){
				$.ajax({
	                type: "post",
	                url: "<%=basePath %>traffic/selectMonthFlowCount",
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
        	var flowCount = echarts.init(document.getElementById('flowCount'));	                    
            flowCount.setOption({
//         	    title : {
//         	        text: '<fmt:message bundle='${pageScope.bundle}'  key='Data.Use.Condition.In.Recent.15.Days(UnitG)' />',
//         	    },
        	    tooltip : {
        	        trigger: 'axis'
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
        	            data : dayList
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'<fmt:message bundle='${pageScope.bundle}'  key='Data' />',
        	            type:'bar',
        	            data:flowData,
        	        },
        	    ]
        	});
        }
        
        
        //4 上月流量使用分布图
	 	function operatChart4(){
				$.ajax({
	                type: "post",
	                url: "<%=basePath %>traffic/selectPrefixFlow",
	                dataType: "json",
	                success: function (data) {
	                	var xFlow=data.xFlow;
	                	var yRate=data.yRate;
	                	flowRate(xFlow,yRate);
	                },
	        });
		}
      function flowRate(xFlow,yRate){
        var flowSpread = echarts.init(document.getElementById('flowSpread'));
        flowSpread.setOption({
//     	    title : {
//     	        text: '上月流量使用分布图（单位：MB）',
//     	    },
    	    tooltip : {
    	        trigger: 'axis'
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
    	            data : xFlow
    	        }
    	    ],
    	    yAxis : [
    	        {
    	        	type : 'value',
    	        }
    	    ],
    	    series : [
    	        {
    	            name:"<fmt:message bundle='${pageScope.bundle}'  key='Number.Of.People' />",
    	            type:'bar',
    	            barWidth : 60,
    	            data:yRate,
    	        },
    	    ]
    	});
      }
    </script>
</body>
</html>
 