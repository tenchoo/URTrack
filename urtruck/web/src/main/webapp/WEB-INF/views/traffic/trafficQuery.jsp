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
<title><fmt:message bundle='${pageScope.bundle}'  key='User.data.query' /></title>
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
	function nowQuery() {
		var iccd = $("#iccid").val();
		var imsi = $("#imsiId").val();
		if (iccd == ""&&imsi=="") {
			layer.alert("<fmt:message bundle="${pageScope.bundle}"  key="Please.Input.Iccid.Or.Imsi" />!");
			return;
		}
		$.ajax({
			url:"${ctx}/traffic/doNowTrafficQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,
				"imsi":imsi,
			},
			success:function(data){
				var retmsg = data.expMsg;
				if (retmsg != null) {
					layer.alert(retmsg);
				}else{
					$("#iccid").val(data.iccid);
					$("#imsiId").val(data.imsi);
					$("#iccidId").html(data.iccid);
					$("#operatorId").html(data.trafficDto.operatorName);
					$("#msisdnId").html(data.trafficDto.msisdn);
					$("#deviceId").html(data.trafficDto.type);
					$("#remaindataId").html(data.trafficDto.dataRemaining);
					$("#userNameId").html(data.trafficDto.userName);
					$("#typeId").html(data.trafficDto.version);
					$("#custNameId").html(data.trafficDto.channelCustName);
					$("#cardStateId").html(data.trafficDto.state);
					$("#isOnline").html(data.trafficDto.isOnline);
					if(data.trafficDto.monthSign == "sign"){
						$("#sign").html("<fmt:message bundle='${pageScope.bundle}'  key='Already.Used.Data(Mb)' />:" + data.trafficDto.dataRemaining);
					} else {
						$("#sign").html("<fmt:message bundle='${pageScope.bundle}'  key='Data.Not.Used(Mb)' />:" + data.trafficDto.dataRemaining);
					}
					
				}
			}
		});
	}
	function monthQuery(){
		var iccd = $("#iccid").val();
		var imsi = $("#imsiId").val();
		if (iccd == ""&&imsi=="") {
			layer.alert("<fmt:message bundle="${pageScope.bundle}"  key="Please.Input.Iccid.Or.Imsi" />!");
			return;
		}
		var monthDate = $("#monthDate").val();
		if (monthDate == "") {
			alert("<fmt:message bundle="${pageScope.bundle}"  key="Please.Select.Date" />!");
			return;
		}
		$.ajax({
			url:"${ctx}/traffic/doTrafficQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,
				"selectType":"monthQuery",
				"monthDate":monthDate,
				"imsi":imsi,
			},
			success:function(resultMap){
				var data=resultMap.list;
				// 每次查询前，清空表格
				$("#example tr:not(:first)").remove();
				if(data[0].sessionStartTime==""&&data[0].dataVolume=="" ){
					alert(data[0].expMsg);
				}else{
					$("#iccid").val(resultMap.iccid);
					$("#imsiId").val(resultMap.imsi);
					for(i = 0;i<data.length;i++){
						$("#example").append('<tr><td class='+'font12'+'>'+data[i].sessionStartTime+'</td><td class='+'font12'+'>'+data[i].dataVolume+'</td></tr>');
					}
				}
				
			}
		});
	}
	function dayQuery(){
		var iccd = $("#iccid").val();
		var imsi = $("#imsiId").val();
		if (iccd == ""&&imsi=="") {
			layer.alert("<fmt:message bundle="${pageScope.bundle}"  key="Please.Input.Iccid.Or.Imsi" />!");
			return;
		}
		var dayDate = $("#dayDate").val();
		if (dayDate == "") {
			alert("<fmt:message bundle="${pageScope.bundle}"  key="Please.Select.Date" />!");
			return;
		}
		$.ajax({
			url:"${ctx}/traffic/doTrafficQuery",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,
				"selectType":"dayQuery",
				"dayDate":dayDate,
				"imsi":imsi,
			},
			success:function(data){
				var list=data.list;
				$("#example tr:not(:first)").remove();
				if(list[0].sessionStartTime==""&&list[0].dataVolume=="" ){
					alert(list[0].expMsg);
				}else{
					$("#iccid").val(data.iccid);
					$("#imsiId").val(data.imsi);
					for(i = 0;i<list.length;i++){
						$("#example").append('<tr><td>'+list[i].sessionStartTime+'</td><td>'+list[i].dataVolume+'</td></tr>');
					}
				}
			}
		});
	}
	function clearInput(){
		$("input[name='inputName']").val("");
	}
	</script>
</head>
<body>
	<div class="pd-20" align="center">
		<div class="oh row">
			<div class="col-md-4 col-lg-4 mt20" >
				<label class="font12 fl langWidth" style="padding-top:0px;">ICCID:</label>
					<div class="tBox langMl" >
						<input id="iccid" class='input-text' placeholder="<fmt:message bundle="${pageScope.bundle}"  key="Please.Input.Iccid" />" type="text" name="inputName" value="">
					</div>
			</div>
			<div class="col-md-4 col-lg-4 mt20" >
				<label class="font12 fl langWidth" style="padding-top:0px;">IMSI:</label>
					<div class="tBox langMl" >
						<input id="imsiId" class='input-text' placeholder="<fmt:message bundle="${pageScope.bundle}"  key="Please.Input.Imsi" />" type="text" name="inputName" value="">
					</div>
			</div>
		</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" class="btn btn-primary radius" onclick="nowQuery();"><i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle="${pageScope.bundle}"  key="Data.Traffic.Inquiry" /></button>
				<input class="btn btn-default radius" type="reset" onclick="clearInput();" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Clear.Out" />&nbsp;&nbsp;"> 
			</div>
		<br>
		<div class="mt-20">
			<table id="table1" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<tr>
					<td width="25%" class="font12">ICCID: &nbsp;<label class="font12" id="iccidId"></label></td>
					<td width="15%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Operator" />: &nbsp;<label class="font12" id="operatorId"></label></td>
					<td width="20%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Msisdn.Number" />: &nbsp;<label class="font12" id="msisdnId"></label></td>
					<td width="20%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Device.type" />: &nbsp;<label class="font12" id="deviceId"></label></td>
					<td width="20%" rowspan="2" class="font12" style="text-align: center;" ><label id="sign" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Data.Not.Used(Mb)" />: &nbsp;<label class="font12" id="remaindataId"></label></label></td>
<!-- 						<td width="20%" class="font12" style="text-align: center;" >是否在网: &nbsp;<label class="font12" id="isOnline"></label></td>
 -->				</tr>
				<tr>
					<td width="25%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Customer.Name" />: &nbsp;<label class="font12" id="userNameId"></label></td>
					<td width="15%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Type" />: &nbsp;<label class="font12" id="typeId"></label></td>
					<td width="20%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Channel.Customer.Name" />: &nbsp;<label class="font12" id="custNameId"></label></td>
					<td width="20%" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="Sim.Card.Contidion" />: &nbsp;<label class="font12" id="cardStateId"></label><label class="font12" id="isOnline"></label></td>
				</tr>
			</table>
			<br>
		</div>

				
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label class="fl langWidth font12" style="padding-top:0px;"><fmt:message bundle="${pageScope.bundle}"  key="Daily.Flow.Date.Inquiry" />:</label>
					<div class="tBox langMl">
						<input id="dayDate" class="input-text"
							type="text" name="inputName" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM-dd',minDate:'1970-01-01',maxDate:'#now'})"
							readonly="readonly">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
					<button id="search" type="button" class="btn btn-primary radius" 
						onclick="dayQuery();"><i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle="${pageScope.bundle}"  key="Dayly.Date.Flow.Checking" />
					</button>
			</div>
			
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label  class="fl langWidth font12"  style="padding-top:0px;"><fmt:message bundle="${pageScope.bundle}"  key="Check.Monthly.Data.Flow.Account.Period" />:</label>
					<div class="tBox langMl">
						<input id="monthDate" class="input-text"
							type="text" name="inputName" 
							onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy-MM',minDate:'1970-01',maxDate:'%y-{%M+1}'})"
							readonly="readonly">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="monthQuery();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle="${pageScope.bundle}"  key="Monthly.Date.Flow.Checking" />
				</button>
			</div>
			<p align="left" class="font12"><fmt:message bundle="${pageScope.bundle}"  key="notes.the.account.period.day.is.26th.every.month.in.Chinaunicom" /><fmt:message bundle="${pageScope.bundle}"  key="update" />;</p>
			<br>
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle="${pageScope.bundle}"  key="Date(Y-M-D)" /></th>
						<th><fmt:message bundle="${pageScope.bundle}"  key="Used.Data(Mb)" /></th>
					</tr>
				</thead>
			</table>
			</div>
	</div>
</body>
</html>