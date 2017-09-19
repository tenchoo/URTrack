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
<title><fmt:message bundle='${pageScope.bundle}'  key='Account.introduction.of.cluster.network' /></title>
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
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>

<script type="text/javascript">
	$(function (){
		$("#msgdaoru").hide();
		var retmsg = $("#msgId").val();
		if(retmsg != ""){
			alert(retmsg);
		}
	});

	function BeforeFrom() {
		var cycleId = $('#cycleId').val();
		if(cycleId == ""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />！");
			return false;
		}
		$("#msgdaoru").show();
		return true;
	}
	
	
	function send(){  
		var fileVal = $('#inputfile').val();
		var cycleId = $('#cycleId').val();
		var tradeTypeCode = $('#tradeTypeCode').val();
		if(cycleId == ""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />！");
			return false;
		}
		if(fileVal == ""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.document' />!");
			return false;
		}
		var option = {
			url : "${ctx}/batchFtpImport/sendMsg",
			type : "post",
			dataType: "json",
		 	data:{
				"cycleId":cycleId,
				"tradeTypeCode":tradeTypeCode,
			}, 
			success : function(data) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Import.processing,the.details.of.progress.refer.to' />！");
				window.location.reload();
			},
			error : function() {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />！");
			}
		};
		$("#sform").ajaxSubmit(option);
	}
</script>

</head>
<body>
	<div class="pd-20">
	<input id="msgId" type="hidden" value="${retmsg}"/>
	<form id="form1"  method="post" action="${ctx}/batchFtpImport/doBatch" onsubmit="return BeforeFrom();" class="form form-horizontal" >
	    <div class="oh row">
	        <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Import.file.type' />：</label>
			<select name="tradeTypeCode" class="input-text" style="width:250px">
             <option id="tradeTypeCode" name="tradeTypeCode" value="1000"><fmt:message bundle='${pageScope.bundle}'  key='Cluster.billing.statement' /></option>
            </select>
         </div>   
         <div class="oh row">
             <label class="font12 col-2" style="width: 15%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />：</label>
             <input class="input-text" id="cycleId" style="width:250px" type="text" name="cycleId" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M-1}'})" readonly="readonly">
        </div>    
        <div class="oh row">
	        <div class="col-9 col-offset-3">
	            <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Import.file' />&nbsp;&nbsp;">
				<div id="msgdaoru"  style="display:none">
                	<h4><fmt:message bundle='${pageScope.bundle}'  key='Importing, please.do.not.submit.again' />......</h4>
                </div>
            </div>
        </div>
	</form>
	<div>
		<form method="post" enctype="multipart/form-data" name="sform" id="sform">
			<div class="col-12 mt20">
					<label class="fl labelWidth control-label text-left" for="mobileId"> <span class="colorRed smallStar"></span>
						<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Manual.import' />:</span>
					</label>
				<div class="fformControls langMl zpInput">
					<input type="file" class="input-text"
							style="width: 250px; padding-bottom: 35px;" id="inputfile"
							name="file">
				</div>
				<br>
			</div>
			<div class="col-9 col-offset-3">
				<input style="margin-left: -12px;" class="btn btn-primary radius"  type="button" onclick="send()"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Import.file' />&nbsp;&nbsp;" />
			</div>
		</form>
	</div>
	</div>
</body>

</html>