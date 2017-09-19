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
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link href="${ctx}/static/ui3/css/style.css" rel="stylesheet">

<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="static/js/jquery.form.js"></script>
	<script type="text/javascript">
	
	$(function() {
		//日期显示当月
		var date=new Date;
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		month =(month<10 ? "0"+month:month);
		var mydate = (year.toString()+'-'+month.toString());
		$('#seaTime').val(mydate);
		
	 	$.ajax({
			url : "${ctx}/homePage/doAjaxAdminReport",
			type : "post",
			dataType : "json",
			data : {"date" : $("#seaTime").val()},
			success : function(data) {
				doTable(data);
			},
			error:function(){
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Query.failed' />");
			}
		});  
	});
	
	function doTable(date){
		var classtd = $(".classtd");
		for(var j = 0;j < classtd.length; j++){
			classtd[j].innerText = "";
		}
		for(var i=0;i<date.length;i++){
			var staticName = date[i].STATICNAME;
			var suma = date[i].SUMUSERA;
			var sumb = date[i].SUMINCOMEB;
			var sumc = date[i].SUMUSERC;
			var sumd = date[i].SUMINCOMED;
			var sume = date[i].SUMUSERE;
			if(suma == null || suma == ""){
				suma = 0;
			}
			if(sumb == null || sumb == ""){
				sumb = 0;
			}
			if(sumc == null || sumc == ""){
				sumc = 0;
			}
			if(sumd == null || sumd == ""){
				sumd = 0;
			}
			if(sume == null || sume == ""){
				sume = 0;
			}
			if (staticName == "智能展业"){
				$("#tb11").html(suma);
				$("#tb12").html(sumb);
				$("#tb13").html(sumc);
				$("#tb14").html(sumd);
				$("#tb15").html(sume);
			} else if (staticName == "智能车联"){
				$("#tb21").html(suma);
				$("#tb22").html(sumb);
				$("#tb23").html(sumc);
				$("#tb24").html(sumd);
				$("#tb25").html(sume);
			} else if (staticName == "智能互联"){
				$("#tb31").html(suma);
				$("#tb32").html(sumb);
				$("#tb33").html(sumc);
				$("#tb34").html(sumd);
				$("#tb35").html(sume);
			} else if (staticName == "运营商物联"){
				$("#tb41").html(suma);
				$("#tb42").html(sumb);
				$("#tb43").html(sumc);
				$("#tb44").html(sumd);
				$("#tb45").html(sume);
			} else if (staticName == "合计"){
				$("#tb51").html(suma);
				$("#tb52").html(sumb);
				$("#tb53").html(sumc);
				$("#tb54").html(sumd);
				$("#tb55").html(sume);
			} else if (staticName == "智能互联TOC"){
				$("#tb61").html(suma);
				$("#tb62").html(sumb);
				$("#tb63").html(sumc);
				$("#tb64").html(sumd);
				$("#tb65").html(sume);
			} else if (staticName == "总计"){
				$("#tb71").html(suma);
				$("#tb72").html(sumb);
				$("#tb73").html(sumc);
				$("#tb74").html(sumd);
				$("#tb75").html(sume);
			} 
		}
		var classtd = $(".classtd");
		for(var j = 0;j < classtd.length; j++){
			if(!classtd[j].innerText){
				classtd[j].innerText = 0;
			}
		}
	}
	function toQuery(){
		$.ajax({
			url : "${ctx}/homePage/doAjaxAdminReport",
			type : "post",
			dataType : "json",
			data : {"date" : $("#seaTime").val()},
			success : function(data) {
				doTable(data);
			},
			error:function(){
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Query.failed' />");
			}
		});
	}
	
	function toExport(){
		/* $.ajax({
			url : "${ctx}/homePage/doExportAdminReport",
			type : "post",
			dataType : "json",
			data : {"date" : $("#seaTime").val()},
			success : function(date) {
				if(date != null && date != ""){
					//alert(date);
				}
			}
		}) */
		$('#formDate').val($("#seaTime").val());
		$('#cform').submit();
	}
	</script>
</head>
<body>
			<div class="bg01 mgT02 pdB03">
				<div class="tableTop clearfix">
				<div class="float-left">
					<label><fmt:message bundle='${pageScope.bundle}'  key='Time' />：</label>
					<input class="Wdate intA mgT03" id="seaTime" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true})" >
					<button class="btn04 width03 mgT03" onclick="toQuery();"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
				</div>
				<button class="btn04 float-right mgT03" onclick="toExport();"><fmt:message bundle='${pageScope.bundle}'  key='Export.table' /></button>
			</div>
			<form id="cform" action="${ctx}/homePage/doExportAdminReport" method="post" hidden>
				 <input id="formDate" name="date" hidden>
			</form>
			<table class="tableA">
				  <tr class="tit">
				    <th width="6%">&nbsp;<i></i></th>
				    <th width="16%"><fmt:message bundle='${pageScope.bundle}'  key='Primary.classification' /><i></i></th>
				    <th width="13%"><fmt:message bundle='${pageScope.bundle}'  key='New.users.of.this.month' /><i></i></th>
				    <th width="17%"><fmt:message bundle='${pageScope.bundle}'  key='New.income.for.the.month' /><i></i></th>
				    <th width="19%"><fmt:message bundle='${pageScope.bundle}'  key='cumulative.users.of.financial.year' /><i></i></th>
				    <th width="19%"><fmt:message bundle='${pageScope.bundle}'  key='total.income.for.financial.year.(yuan)' /><i></i></th>
				    <th width="10%"><fmt:message bundle='${pageScope.bundle}'  key='Total.number.of.users' /><i></i></th>
				  </tr>
				  <tr class="thBottom">
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				    <td>&nbsp;</td>
				  </tr>
				  <tr class="bg01">
				    <td rowspan="5" class="borderRa">TO B</td>
				    <td><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.exhibition.industry' /><i></i></td>
				    <td><span id="tb11" class="classtd"></span><i></i></td>
				    <td><span id="tb12" class="classtd"></span><i></i></td>
				    <td><span id="tb13" class="classtd"></span><i></i></td>
				    <td><span id="tb14" class="classtd"></span><i></i></td>
				    <td><span id="tb15" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td><fmt:message bundle='${pageScope.bundle}'  key='smart.car.connected' /><i></i></td>
				    <td><span id="tb21" class="classtd"></span><i></i></td>
				    <td><span id="tb22" class="classtd"></span><i></i></td>
				    <td><span id="tb23" class="classtd"></span><i></i></td>
				    <td><span id="tb24" class="classtd"></span><i></i></td>
				    <td><span id="tb25" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td><fmt:message bundle='${pageScope.bundle}'  key='smart.connected' /><i></i></td>
				 	<td><span id="tb31" class="classtd"></span><i></i></td>
				    <td><span id="tb32" class="classtd"></span><i></i></td>
				    <td><span id="tb33" class="classtd"></span><i></i></td>
				    <td><span id="tb34" class="classtd"></span><i></i></td>
				    <td><span id="tb35" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td><fmt:message bundle='${pageScope.bundle}'  key='Carrier.IOT' /><i></i></td>
 					<td><span id="tb41" class="classtd"></span><i></i></td>
				    <td><span id="tb42" class="classtd"></span><i></i></td>
				    <td><span id="tb43" class="classtd"></span><i></i></td>
				    <td><span id="tb44" class="classtd"></span><i></i></td>
				    <td><span id="tb45" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td><fmt:message bundle='${pageScope.bundle}'  key='sum' /><i></i></td>
				    <td><span id="tb51" class="classtd"></span><i></i></td>
				    <td><span id="tb52" class="classtd"></span><i></i></td>
				    <td><span id="tb53" class="classtd"></span><i></i></td>
				    <td><span id="tb54" class="classtd"></span><i></i></td>
				    <td><span id="tb55" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td rowspan="2" class="borderRa">TO C</td>
				    <td><fmt:message bundle='${pageScope.bundle}'  key='smart.connected' /><i></i></td>
				 	<td><span id="tb61" class="classtd"></span><i></i></td>
				    <td><span id="tb62" class="classtd"></span><i></i></td>
				    <td><span id="tb63" class="classtd"></span><i></i></td>
				    <td><span id="tb64" class="classtd"></span><i></i></td>
				    <td><span id="tb65" class="classtd"></span><i></i></td>
				  </tr>
				  <tr class="bg01">
				    <td><fmt:message bundle='${pageScope.bundle}'  key='Total' /><i></i></td>
					<td><span id="tb71" class="classtd"></span><i></i></td>
				    <td><span id="tb72" class="classtd"></span><i></i></td>
				    <td><span id="tb73" class="classtd"></span><i></i></td>
				    <td><span id="tb74" class="classtd"></span><i></i></td>
				    <td><span id="tb75" class="classtd"></span><i></i></td>
				  </tr>
				</table>
			</div>
	</body>
</html>