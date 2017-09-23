<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='import.operator.bills' /></title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="static/lib/laypage/1.2/laypage.js"></script>  
<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="static/js/H-ui.js"></script> 
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="static/js/jquery.form.js"></script> 
<script type="text/javascript" src="static/js/jquery.validate.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>

<script type="text/javascript">
	function charge(){
		var option = {
				url : "chargeOff/doneCharge",
				type : "post",
				success : function(data) {
					layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Expenditure.in.process' />");
				},
				error : function() {
					layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Execution.failure' />！");
				}
			};
		var operators = $("#operators").find("option:selected").val();
		if(operators =='' || operators == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />");
			 return false;
		}
		
		var cycleId = $('#cycleId').val();
		if(cycleId == ""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />！");
			return false;
		}
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.charge.off' />？")){
			$("#chargeOff").attr("disabled","disabled");
			$("#sform").ajaxSubmit(option);
		}
	}
	function send(){ 
		var operators = $("#operators").find("option:selected").val();
		if(operators =='' || operators == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />");
			 return false;
		}
		
		var cycleId = $('#cycleId').val();
		if(cycleId == ""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />！");
			return false;
		}
		
		var obj=document.getElementById('inputfile'); 
		if(obj.value=='') 
		{ 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />！");
			return false; 
		} 
		var stuff=obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; 
		if(stuff!='xls' && stuff!='xlsx' ) 
		{ 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
			return false; 
		} 
		
		var option = {
			url : "chargeOff/billingImport",
			type : "post",
			success : function(data) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='In.the.import.processing,Please.see.the.batch.processing.results' />");
				$("#example").empty();
				var Data = data;
				for ( var key in Data) {
					if (key.indexOf('sucMsg') > -1) {
						$("#example").append(
								"<tr class='text-c'><td><fmt:message bundle='${pageScope.bundle}'  key='successful.number.in.batch.processing' /></td><td>"
										+ Data[key] + "</td></tr>");
					} else if (key.indexOf('errorMsg') > -1) {
						$("#example").append(
								"<tr class='text-c'><td><fmt:message bundle='${pageScope.bundle}'  key='incorrect.information' /></td><td>"
										+ Data[key] + "</td></tr>");
					}
				}
				$("#showResult").hide();
				$("#showResult").show();
				$("#chargeOff").show();
			},
			error : function() {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />！");
			}
		};
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
			$("#button").attr("disabled","disabled");
			$("#sform").ajaxSubmit(option);
		}
	}
</script>

</head>
<body>
	<div class="pd-20">
		<form method="post" enctype="multipart/form-data" name="sform" id="sform">
			<div class="oh row">
		        <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />：</label>
				<select id="operators" name="operators" class="input-text" style="width:250px">
	             	<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
					<c:forEach var="operator" items="${operators}">
						<option value="${operator.operatorsId}">${operator.operatorsName}</option>
				    </c:forEach>
	            </select>
	         </div>   
	         <div class="oh row">
	             <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />：</label>
	             <input class="input-text" id="cycleId" style="width:250px" type="text" name="cycleId" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M}'})" readonly="readonly">
	        </div> 
	        <div class="oh row">
	             <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />:</label>
	             <input type="file" class="input-text" style="width: 250px; padding-bottom: 35px;" id="inputfile" name="file">
	        </div>
			<div class="col-8 col-offset-3">
				<input style="margin-right: -25px;" class="btn btn-primary radius"  type="button" onclick="send()" id="button"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Import.file' />&nbsp;&nbsp;" />
				<input style="margin-left: 25px;" class="btn btn-primary radius"  type="button" onclick="charge()" id="chargeOff"  value="<fmt:message bundle='${pageScope.bundle}'  key='charge.off' />"  />
			</div>
		</form>
		<div class="mt-20" id="showResult" style="display: none">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			</table>
		</div>
	</div>
</body>

</html>