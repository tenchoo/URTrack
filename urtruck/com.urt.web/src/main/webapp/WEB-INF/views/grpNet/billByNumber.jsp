<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><fmt:message bundle='${pageScope.bundle}'  key='Consumer.inquiries' /></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="/static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
	<link rel="stylesheet" href="/static/h5/css/bootstrap-theme.min.css" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="/static/h5/js/jquery-1.12.4.min.js"></script>
    
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
	<script src="/static/h5/js/bootstrap.min.js"></script>

    <link href="/static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />
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

    <style>
        *{
            font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
        }
        h5{font-size: 3.3}
        button{font-size: 4}
    </style>

    <script src="/static/h5/js/main.js"></script>

    <style>button{font-size: 4}</style>
  <script type="text/javascript">
  	var map = {};
    var mapYearMonth;
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	function queryBynumber(){
		var number = $("#number").val();
		if (number == "") {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.fill.in.your.cell.phone.number' />!");
			return false;
		}
		
		if (number != "") {
			//var reg = /^[1-9]\d*$|^0$/;
			var reg = /^\d{11}$/ ;
			if (reg.test(number) == false) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Mobile.phone.number.is.11.Arabic.numerals' />！");
				return false;
			}
		}
		
		$.ajax({
			url : "${ctx}/batchFtpImport/webBillByAjaxNumber",
			type : "post",
			dataType : "json",
			data : {
				"number" : $("#number").val(),
			},
			success : function(data) {
				map = data;
				var mapmonth = data.mapmonth;
				var number = data.number;
				$("#numberBen").empty();
				$("#numberBen").append(number);
				$("#soundsDiscnt").empty();
				$("#messageDiscnt").empty();
				$("#flowDiscnt").empty();
				$("#soundsDiscnt").append(mapmonth.soundsDiscnt);
				$("#messageDiscnt").append(mapmonth.messageDiscnt);
				$("#flowDiscnt").append(mapmonth.flowDiscnt);
				var defaultStart = new Date();
				var month = defaultStart.getMonth();
				month = (month<10 ? "0"+month:month)+"月"; 
				if("00月" == month){
					month = "12月";
				}
				change2(month);
			}
		
		});
	}
	
	function change2(month){
		$("#yearMonthId").empty();
		$("#yearMonthId").append($("#"+month).val());
		month = month.split("月")[0];
		var list = map[month];
		// 每次查询前，清空表格
		$("#example tr:not(:first)").remove();
		for (var i = 0; i < list.length; i++) {
			
			$("#example").append('<tr><td class='+'font12'+'>'+list[i].itemName+'</td><td class='+'font12'+'>'+list[i].feeName+'</td><td class='+'font12'+'>'+list[i].discnt+'</td></tr>');

		}
	}
	
    $(function(){
		var defaultStart=new Date();
		var defaultDate=defaultStart.Format("yyyy/MM/dd");
		$("#nowDate").html(defaultDate);

  });
  </script>

</head>
<body>
	<div class="pd-20" align="center">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20" >
					<label class="font12 fl langWidth" style="padding-top:0px;"><fmt:message bundle='${pageScope.bundle}'  key='mobliephone.number' />:</label>
						<div class="tBox langMl" >
							<input id="number" class='input-text' placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.cell.phone.number' />" type="text" name="number">
						</div>
				</div>
			</div>
				<div class="mt20 clr" style="text-align: center;">
					<button id="search" type="button" class="btn btn-primary radius" onclick="queryBynumber();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
					<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> 
				</div>
			<div class="mt-20">
			<p align="left" class="font12">[<fmt:message bundle='${pageScope.bundle}'  key='deadline' /><span id="nowDate" ></span>]</p>
			<br>
				<table id="table1" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
					<tr>
						<td width="25%" class="font12"><span id = "numberBen"></span>&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='This.month.situation.of.usage' /></td>
						<td width="25%" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Conversation' />: &nbsp;<span id="soundsDiscnt"></span></td>
						<td width="25%" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='message' />: &nbsp;<span id="messageDiscnt"></span></td>
						<td width="25%" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='data' />: &nbsp;<span id="flowDiscnt"></span></td>
					</tr>
				</table>
			</div>
		<br>
		<br>
<div style=" font-size: 1.3;" >
    <div style="width: 100">
        <div class="tabbable" id="tabs-634545">
            <ul class="nav nav-pills">
                <li class="active">
                    <a  id="aNext" onclick="change2('${map.cycleIdM0}');" style="border: solid;padding: 1 2" href="#panel-58792" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;" >&nbsp;&nbsp;<span id = "spanId1"class="font12">${map.cycleIdM0}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li >
                    <a  onclick="change2('${map.cycleIdM1}');" style="border: solid;padding: 1 2"href="#panel-58793" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;" >&nbsp;&nbsp;<span id = "spanId2"class="font12">${map.cycleIdM1}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM2}');" style="border: solid;padding: 1 2"href="#panel-58794" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;">&nbsp;&nbsp;<span id = "spanId3"class="font12">${map.cycleIdM2}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li >
                    <a  onclick="change2('${map.cycleIdM3}');" style="border: solid;padding: 1 2" href="#panel-58795" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;">&nbsp;&nbsp;<span id = "spanId4"class="font12">${map.cycleIdM3}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM4}');" style="border: solid;padding: 1 2"href="#panel-58796" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;">&nbsp;&nbsp;<span id = "spanId5"class="font12">${map.cycleIdM4}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM5}');" style="border: solid;padding: 1 2"href="#panel-58797" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1;">&nbsp;&nbsp;<span id = "spanId6"class="font12">${map.cycleIdM5}</span>&nbsp;&nbsp;</p></a>
                </li>
            </ul>
                    <div style="width: 96;margin: 0rem auto;padding-top: 1rem"  class="row" align="left">
                    	<p style="font-size: 3.8;margin-bottom: 0rem; margin-left: 1rem" class="font12"><span id="yearMonthId" >${map.cycleIdYM0 }</span><fmt:message bundle='${pageScope.bundle}'  key='The.consumer.record.is.as.follows' />:</p>
                    </div>
            <br>
        <div style="width: 96;margin: 0rem auto;padding-top: 0rem"  class="row" >
	        
	        <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th ><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></th>
						<th ><fmt:message bundle='${pageScope.bundle}'  key='Cost.name' /></th>
						<th ><fmt:message bundle='${pageScope.bundle}'  key='Actual.amount.(yuan)' /></th>
					</tr>
				</thead>
			</table>
    	</div>
    </div>
</div>
	</div>
<div>
    <input id="${map.cycleIdM0}" type="hidden" value="${map.cycleIdYM0 }"/>
   	<input id="${map.cycleIdM1}" type="hidden" value="${map.cycleIdYM1 }"/>
   	<input id="${map.cycleIdM2}" type="hidden" value="${map.cycleIdYM2 }"/>
   	<input id="${map.cycleIdM3}" type="hidden" value="${map.cycleIdYM3 }"/>
   	<input id="${map.cycleIdM4}" type="hidden" value="${map.cycleIdYM4 }"/>
   	<input id="${map.cycleIdM5}" type="hidden" value="${map.cycleIdYM5 }"/>
</div>
</body>
</html>