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
<title>Enterprise customer monthly traffic query</title>
<base href="<%=basePath%>" />
<%-- <link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" /> --%>
	
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
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>

<script type="text/javascript">
	var htmlList=[];
	var taskState="";
	$.ajax({
		url:"/monthQueryByEnglish/getChannelCust",
		data:{},
		success:function(result){
			    for(var i=0;i<result.length;i++){
			    	var str='<option value="'+result[i].id+'">'+result[i].text+'</option>';
			    	htmlList.push(str);
			    }
			    $("#custId").html(htmlList.join(" "));
			    htmlList=[];
				$("#custId").change(function() {
					$('#type').empty();
					$('#version').empty();
// 					$.ajax({
// 						type : "post",
// 						url : "${ctx}/ss/getAttrs",
// 						data: {"custId":$("#custId").val()},
// 						success:function(result){
// 							console.log(result);
// 							$("#attribute1").val(result.attrV1);
// 							$("#attribute2").val(result.attrV2);
// 							$("#lable1").text(result.attr1+":");
// 							$("#lable2").text(result.attr2+":");
// 						}
// 					});
					$.ajax({
						url:"${ctx}/ss/getTypeList",
						data:{"custId":$("#custId").val()},
						success:function(result){
						 htmlList.push('<option value="-1">Select</option>');
						 for(var i=1;i<result.length;i++){
						    	var str='<option value="'+result[i].id+'">'+result[i].text+'</option>';
						    	htmlList.push(str);
						    }
						    $("#type").html(htmlList.join(" "));
						    htmlList=[];
							$("#type").change(function() {
							    if($("#type").val()=="-1"){
							    	$('#version').empty();
							    	return;
							    }
								$('#version').empty();
								var pid="";
								$.ajax({
									url:"${ctx}/ss/getPidByValue",
									data:{"id":$("#type").val(),"custId":$("#custId").val()},
									async:false,
									success:function(result){
										pid=result;
									}
								});
								$.ajax({
									url:"${ctx}/ss/getVersionList",
									data:{"pid":pid,"custId":$("#custId").val()},
									success:function(result){
									 htmlList.push('<option value="-1">Select</option>');
									 for(var i=1;i<result.length;i++){
									    	var str='<option value="'+result[i].id+'">'+result[i].text+'</option>';
									    	htmlList.push(str);
									    }
									    $("#version").html(htmlList.join(" "));
									    htmlList=[];
									}
								});
							});
						}
					});
				});
		}
	});
	function checkCustId(){
		var custId = $("#custId").val();
		if (custId == "-1") {
			alert("Please select the channel customer name!");
			return false;
		}
		return true;
	}
	function checkUseCount(){
// 		alert($('input:radio:checked').val());
		var value1 = $("#flow1").val();
		var value2 = $("#flow2").val();
		
		if(parseFloat(value1) < parseFloat(value2)){
			if(value1=="-1"){
				alert("Please select the minimum amount of usage!");
				return false;
			}
		}else if(parseFloat(value1) > parseFloat(value2)){
			if(value2=="-1"){
				alert("Please select the minimum amount of usage!");
				return false;
			}else{
				alert("Please enter the correct usage range!");
				return false;
			}
		}
		return true;
	}
	function queryBy() {
		if (!checkCustId()) {
			return false;
		};
		if (!checkUseCount()) {
			return false;
		};
		$.ajax({
			url : "${ctx}/traffic/doQuery",
			type : "post",
			dataType : "json",
			data : {
				"channelCustId" : $("#custId").val(),
				"type"  :$('input:radio:checked').val(),
				"useCount1" : $("#flow1").val(),
				"useCount2" : $("#flow2").val(),
				"value1":$("#type").val(),
				"value2":$("#version").val(),
			},
			success : function(data) {
				taskState=data.state;
// 				alert(taskState);
				$("#labelt1").text(data.count);
				$("#labelt2").text(data.score);
				doHistogram1(data.xStr, data.yStr1);
			}
		});
	}
	function doHistogram1(xStr,yStr1) {
		var histogram1 = echarts.init(document.getElementById('histogram1'));
		histogram1.setOption({
			    title : {
			        text: 'The specified number of internal flow range distribution',
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['The specified range card number']
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
			                formatter: '{value}MB'
			            },
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            axisLabel : {
			                formatter: '{value} card'
			            },
			        }
			    ],
			    series : [
			        {
			            name:'The specified range card number',
			            type:'bar',
			            data:yStr1,
			        },
			    ]
			});
	}
	function queryOrExport() {
		if (!checkCustId()) {
			return false;
		};
		if (!checkUseCount()) {
			return false;
		};
		$.ajax({
			url : "${ctx}/month/selectBydynamic",
			type : "post",
			dataType : "json",
			data : {
				"channelCustId" : $("#custId").val(),
				"type"  :$('input:radio:checked').val(),
				"month" : $("monthId").val(),
				"useCount1" : $("#flow1").val(),
				"useCount2" : $("#flow2").val(),
			},
			success : function(data) {
				if(data=="0"){
					//数据量较大，第一次导出，先存入数据库，提示用户等待两分钟
					layer.msg('Download, please try again in two minutes', {icon: 1});
				}else if(data=="1"){
					layer.msg('Download, please try again in two minutes', {icon: 1});
				}else if(data=="2"){
					layer.msg('loading', {icon: 1});
					exportExcel(data)
				}else if(data=="3"){
					layer.msg('loading', {icon: 1});
					exportExcel(data)
				}else if(data=="5"){
					layer.msg('Large amount of data, please select the query conditions', {icon: 1});
				}
			}
		});
	}
	function exportExcel(data) {
		if (!checkCustId()) {
			return false;
		};
		if (!checkUseCount()) {
			return false;
		};
		var url="<%=basePath%>month";
		if(data=="2"){
			url=url+"/exportFileFromDB?";
		}else if(data=="3"){
			url=url+"/exportFile?";
		}
		var url = url+"channelCustId="
				+ $("#custId").val() + "&&useCount1=" + $("#flow1").val()
				+ "&&useCount2=" + $("#flow2").val()
				+ "&&type="+$('input:radio:checked').val()
		window.location.href = url;
	}
</script>
</head>
<body>
    <div class="seconSec ">
				 <c:forEach items="${info}" var="map">
				    <c:out value="${map.key}"></c:out>update on<c:out value="${map.value}"></c:out>&nbsp&nbsp&nbsp&nbsp&nbsp 
			     </c:forEach>
	</div>   
	<div class="seconSec ">
			<h1 align="left">Data Usage This Month</h1>
			<br>
	</div>
	
	<div class="pd-20" >
		<div class="oh row">
			  <label class="font12" style="float: left;width: 12%;">Channel Customer Name：&nbsp;&nbsp;</label>
			  <select id="custId" name="custId" class="input-text" style="float: left;width: 20%;"></select>
			  <lable class="font12" id="lable1">First classification:</lable><select id="type" name="type" class="input-text" style="width:200px;"></select>
			  <lable class="font12" id="lable2">Then classification:</lable><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>
			  <input type="text" id="attribute1" name="attribute1" style="display:none">
			  <input type="text" id="attribute2" name="attribute2" style="display:none">
	    </div>
	    <br>
	    <div class="oh row">
	        <form action="" method="get"> 
			    <label class="font12" style="float: left;width: 12%;"><input name="Fruit" type="radio" value="1"  checked="checked"/>Data Used This Month </label> 
			    <label class="font12" style="float: left;width: 12%;"><input name="Fruit" type="radio" value="2" />Data Balance This Month </label> 
		    </form> 
	    </div>
	    <br>
		<div class="oh row">
				<label style="width: 11.6%;" class="font12" for="useCount">The Data Range：&nbsp;</label>
<!-- 				<input type="text" class='input-text' id="useCount1" name="useCount1"  style="width: 20%;" /> -->
                <select class='input-text' id="flow1" name="flow1"  style="width: 20%;">
				<option value="-1">Select</option>
				<option value="0">0M</option>
				<option value="100">100MB</option>
				<option value="200">200MB</option>
				<option value="500">500MB</option>
				<option value="1024">1G</option>
				<option value="2048">2G</option>
				<option value="3072">3G</option>
				<option value="4096">4G</option>
				<option value="5120">5G</option>
				<option value="-2">Greater on 5G</option>
				</select>
				——&nbsp;
				<select class='input-text' id="flow2" name="flow2"  style="width: 20%;">
				<option value="-1">Select</option>
				<option value="0">0M</option>
				<option value="100">100MB</option>
				<option value="200">200MB</option>
				<option value="500">500MB</option>
				<option value="1024">1G</option>
				<option value="2048">2G</option>
				<option value="3072">3G</option>
				<option value="4096">4G</option>
				<option value="5120">5G</option>
				<option value="-2">Greater on 5G</option>
				</select>
				&nbsp;&nbsp;
				<button id="search" type="button" class="btn btn-primary radius" onclick="queryBy();">
					<i class="Hui-iconfont">&#xe665;</i>Query
				</button>
		</div>
		<div class=" pd-20">
			<table  cellspacing="0" id="table1" 
				class="table table-border table-bordered table-hover table-bg table-sort">
				<thead>
					<tr class="zpTable">
						<th>Number of Cards:<label id="labelt1"></label></th>
						<th>Percentage of Total Cards:<label id="labelt2"></label>&nbsp;%</th>
						<th style="text-align: center;"><input
							class="btn btn-primary radius" type="button" onclick="queryOrExport();"
							value="&nbsp;&nbsp;Export detailed information&nbsp;&nbsp;"></th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="cl pd-5">
			<div id="histogram1"
				style="height: 500px; width: 90%; border: 0px solid #ccc; padding: 10px;"></div>
		</div>
	</div>	
</body>
</html>