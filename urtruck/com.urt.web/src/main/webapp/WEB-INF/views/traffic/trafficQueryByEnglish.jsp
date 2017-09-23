<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Card Data Query</title>
<base href="<%=basePath%>" />
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
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<script type="text/javascript"> 
	$(function() {
		var retmsg = $("#expMsg").val();
		if (retmsg != "") {
			alert(retmsg);
		}
	});

	function nowQuery() {
		var iccd = $("#iccid").val();
		if (iccd != "") {
			return true;
		}
		alert("Provide ICCID!");
		return false;
	}
	function monthQuery(){
		var iccd = $("#iccid").val();
		if (iccd == "") {
			alert("Provide ICCID!");
			return false;
		}
		var monthDate = $("#monthDate").val();
		if (monthDate == "") {
			alert("Select Date!");
			return false;
		}
		$.ajax({
			url:"${ctx}/trafficByEnglish/doTrafficQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,
				"selectType":"monthQuery",
				"monthDate":monthDate,
				
			},
			success:function(data){
				// 每次查询前，清空表格
				$("#example tr:not(:first)").remove();
				if(data[0].sessionStartTime==""&&data[0].dataVolume=="" ){
					alert(data[0].expMsg);
				}else{
					for(i = 0;i<data.length;i++){
						$("#example").append('<tr><td>'+data[i].sessionStartTime+'</td><td>'+data[i].dataVolume+'</td></tr>');
					}
				}
			}
		});
	}
	function dayQuery(){
		var iccd = $("#iccid").val();
		if (iccd == "") {
			alert("Provide ICCID!");
			return false;
		}
		var dayDate = $("#dayDate").val();
		if (dayDate == "") {
			alert("Select Date!");
			return false;
		}
		$.ajax({
			url:"${ctx}/trafficByEnglish/doTrafficQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,
				"selectType":"dayQuery",
				"dayDate":dayDate,
				
			},
			success:function(data){
				$("#example tr:not(:first)").remove();
				if(data[0].sessionStartTime==""&&data[0].dataVolume=="" ){
					alert(data[0].expMsg);
				}else{
					for(i = 0;i<data.length;i++){
						$("#example").append('<tr><td>'+data[i].sessionStartTime+'</td><td>'+data[i].dataVolume+'</td></tr>');
					}
				}
			}
		});
	}
	

	</script>
</head>
<body>
	<div class="pd-20" align="center">
		<form name="form" method="post" action="${ctx}/trafficByEnglish/doNowTrafficQuery">
		<div class="oh row">
			<div class="col-md-4 col-lg-4 mt20" >
				<label class="font12 fl langWidth" style="padding-top:0px;">ICCID:</label>
					<div class="tBox langMl" >
						<input id="iccid" class='input-text' placeholder="Provide ICCID" type="text" name="iccid" value="${iccid}">
					</div>
			</div>
		</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="submit" class="btn btn-primary radius" onclick="return nowQuery();"><i class="Hui-iconfont">&#xe665;</i> Data Balance Query</button>
				<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;Clear&nbsp;&nbsp;"> 
			</div>
		<br>
		<input id="expMsg" type="hidden" value="${expMsg}" />
			<div class="mt-20">
				<table id="table1" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
					<tr>
						<td width="25%" class="font12">ICCID: &nbsp;${trafficDto.iccid}</td>
						<td width="15%" class="font12">Operator: &nbsp;${trafficDto.operatorName}</td>
						<td width="20%" class="font12">Msisdn Number: &nbsp;${trafficDto.msisdn}</td>
						<td width="20%" class="font12">Terminal Type: &nbsp;${trafficDto.type}</td>
						<td rowspan="2" width="20%" class="font12" style="text-align: center;" >Data Balance(MB): &nbsp;${trafficDto.dataRemaining}</td>
					</tr>
					<tr>
						<td width="25%" class="font12">Customer Name: &nbsp;${trafficDto.userName}</td>
						<td width="15%" class="font12">Model: &nbsp;${trafficDto.version}</td>
						<td width="20%" class="font12">Channel Name: &nbsp;${trafficDto.channelCustName}</td>
						<td width="20%" class="font12">Card Status: &nbsp;${trafficDto.state}</td>
					</tr>
				</table>
			</div>

				
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label class="fl langWidth font12" style="padding-top:0px;">Daily  query  date:</label>
					<div class="tBox langMl">
						<input id="dayDate" class="input-text"
							type="text" name="dayDate" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM-dd',minDate:'1970-01-01',maxDate:'#now'})"
							readonly="readonly">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
					<button id="search" type="button" class="btn btn-primary radius" 
						onclick="dayQuery();"><i class="Hui-iconfont">&#xe665;</i> Daily data query
					</button>
			</div>
			
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label  class="fl langWidth font12"  style="padding-top:0px;">Monthly query date:</label>
					<div class="tBox langMl">
						<input id="monthDate" class="input-text"
							type="text" name="monthDate" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM',minDate:'1970-01',maxDate:'#now'})"
							readonly="readonly">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="monthQuery();">
					<i class="Hui-iconfont">&#xe665;</i> Monthly Data Query
				</button>
			</div>
			
			<p align="left" class="font12">Note: The end point of time for China Unicom\'s monthly billing period is the 26th day of each month;</p>
			<br>
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th>Date (Month - Day - Year)</th>
						<th>Data Usage(MB)</th>
					</tr>
				</thead>
			</table>
			</div>
		</form>
	</div>
</body>
</html>