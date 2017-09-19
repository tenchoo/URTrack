<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_cn" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'
		key='Product.add.page' /></title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />

<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	
	

<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery.js"></script>


<script type="text/javascript">

function picked(){
	var cycId = $("#cycId").val();
	
	var payLogColumn = [{"targets": [0],"data": "chargeId"} , 
		                 {"targets": [1],"data": "channelCustId"} ,
		                 {"targets": [2],"data": null,"mRender": function(data,type,full){
								var status = full.payFeeModeCode;
	  							if(status=="1"){
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='cash' />";
	  							}
	  						}	
		                 } ,
		                 {"targets": [3],"data": null,"mRender": function(data,type,full){
								var status = full.paymentOp;
	  							if(status=="10000"){
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='Payment' />";
	  							}else if(status=="10001"){
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='return.premium' />";
	  							}else if(status=="10002") {
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='settlement.of.cash.refunds' />";
	  							}else if(status=="10003") {
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='buyback' />";
	  							}else if(status=="10004") {
	  								return "<fmt:message bundle='${pageScope.bundle}'  key='fee.deduction' />";
	  							}
	  						}	
		                 } ,
		                 {"targets": [4],"data": "recvFee",
		                	 "render": function (data, type, full, meta) {
					            	if(full.recvFee != null){
					                return (full.recvFee/100).toFixed(2);
					            	}else{
					            	return "";
					            	}
					            }},
					     {"targets": [5],"data": "recvOperId"},
		                 {"targets": [6],"data": "recvTime",
		                	 "render": function (data, type, full, meta) {
					            	if(full.recvTime != null){
					            		var recvTime = new Date(full.recvTime).format("yyyy-MM-dd hh:mm:ss"); 
					                return recvTime;
					            	}else{
					            	return "";
					            	}
					            }},
					      {"targets": [7],"data": "goodsId"},
					      {"targets": [8],"data": "goodsName"}
		                 ]
	var laoBillColumn = [{"targets": [0],"data": "balanceId"} , 
		                 {"targets": [1],"data": "channelCustId"} ,
		                 {"targets": [2],"data": "cycleId"} ,
		                 {"targets": [3],"data": "backFee",
		                	 "render": function (data, type, full, meta) {
					            	if(full.backFee != null){
					                return (full.backFee/100).toFixed(2);
					            	}else{
					            	return "";
					            	}
					            }} ,
		                 {"targets": [4],"data": "balanceTime",
		                	 "render": function (data, type, full, meta) {
					            	if(full.balanceTime != null){
					            		var balanceTime = new Date(full.balanceTime).format("yyyy-MM-dd hh:mm:ss"); 
					                return balanceTime;
					            	}else{
					            	return "";
					            	}
					            }} ,
		                 {"targets": [5],"data": "cashTime",
		                	 "render": function (data, type, full, meta) {
					            	if(full.cashTime != null){
					            		var cashTime = new Date(full.cashTime).format("yyyy-MM-dd hh:mm:ss"); 
					                return cashTime;
					            	}else{
					            	return "";
					            	}
					            }},
		                 {"targets": [6],"data": "cashChargeId"},
		                 {"targets": [7],"data": "recvOperId"},
		                 {"targets": [8],"data": "ruleId"}]
	var custId = $("#custId").val();
	listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=0",'#accessLogGrid',accColumn);
	listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=1",'#payLogGrid',payLogColumn);
	listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=2",'#laoBillResultGrid',laoBillColumn);
}

</script>



</head>
<body>
	<div class="seconSec">

		<h1>账户管理</h1>
		<form method="post" class="form form-horizontal" id="form"
			name="form-member-add" enctype="multipart/form-data">
			
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl"><span
					class="colorRed smallStar">*</span>企业名称:</label>
				<div class="tBox">
					<input type="text" readonly="true" class="input-text" value="${custInfo.custName}" id="custName"
						name="custName">
				</div>
			</div>
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl"><span
					class="colorRed smallStar">*</span>银行账号:</label>
				<div class="tBox">
					<input type="text" class="input-text" value=""
							placeholder="请输入银行账号" id="bankaccount" name="bankaccount">
				</div>
			</div>
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl"><span
					class="colorRed smallStar">*</span>开户银行:</label>
				<div class="tBox">
					<input type="text" class="input-text" value=""
							placeholder="请输入开户银行" id="bank" name="bank">
				</div>
			</div>
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl"><span
					class="colorRed smallStar">*</span>企业法人:</label>
				<div class="tBox">
					<input type="text" class="input-text" value=""
							placeholder="请输入企业法人" id="legalperson" name="legalperson">
				</div>
			</div>
			

		    <div class="row cl">
				<div class="zpButton">
					<input class="btn btn-primary radius" type="button"
						onclick="save();" value="&nbsp;&nbsp;保存&nbsp;&nbsp;">
						
						<input class="btn btn-primary radius" type="button"
						onclick="save();" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
						
						 <input
						class="btn btn-default radius" type="reset"
						value="&nbsp;&nbsp;清空&nbsp;&nbsp;" onclick="clear();">
				</div>
			</div>
		</form>
		<input id="custId" type="hidden" value="${custInfo.custId}" />
		<div id="balRule"  class="seconSec">
      		<h1><fmt:message bundle='${pageScope.bundle}'  key='the.balance.warning.rule' /></h1>
      		<div class="mt-20">
      		<table id="balRuleGrid" class="table table-border table-bordered table-hover table-bg table-sort">
        	<thead  class="zpTable">
          	<tr>
           	<th><fmt:message bundle='${pageScope.bundle}'  key='rule.name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Balance.threshold' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Mail.recipient' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='telephone.receiver' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='priority' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
          	</tr>
        	</thead>
        	<tbody>
             <tr id="template">
             <td id="ruleName"></td>
             <td id="balanceInfo"></td>
             <td id="emailInfo"></td>
             <td id="telInfo"></td>
             <td id="proInfo"></td>
             <td id="operInfo"></td>
             </tr>
            </tbody>      	
      		</table>    		
     	 	</div>
      		</div>
      		<div class="seconSec">
      		<a href="javascript:;"
				onclick="add_rule()"
				class="btn btn-primary radius" style="margin:15px 10px;"> <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='add.the.balance.warning.rule' />
			</a>
			</div>
	</div>
	<script type="text/javascript">
	
	function add_rule(){	 
		  var custId = $("#custId").val();
		  url='${ctx}/remain/createRule/'+custId;
			layer_show('<fmt:message bundle="${pageScope.bundle}"  key="add.the.balance.warning.rule" />',url,'800','550');
	}
	function balRuleInfo(){
		
		$.ajax({
	        type:"POST",
	        url:"${ctx}/remain/getBalRules/"+custId,
			cache : false,
			async: false, 
			dataType:"json",
			success : function(data) {
				if(data.ruleName !=null){					
			        var row = $("#template").clone();
			        row.find("#ruleName").text(data.ruleName);
			        row.find("#balanceInfo").text(data.balFee);
			        row.find("#emailInfo").text(data.emaileId);
			        row.find("#telInfo").text(data.telphoneId);
			        row.find("#proInfo").text(data.cal);
			        row.find("#operInfo").html('<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('+custId+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></i></a>'
							+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Edit" />\" href="javaScript:toUpdate('+custId+');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Edit" /></a> ');
			        row.appendTo("#balRuleGrid");//添加到模板的容器中
				}								
			}
		});
		
	}
	 
	    $(document).ready(function(){
		
	    	balRuleInfo();
	    	 
		});
	     
	     
		function check() {
			var goodsNameValue = window.document.getElementById("welcomeLanguage").value;
			var activeWayValue = window.document.getElementById("lang").value;
			var picValue = window.document.getElementById("pic").value;
			
		}
		//清空
		function clear() {
			$("#welcomeLanguage").text("");
			$("#pic").text("");
			$("#lang").text("");
		}

		//保存
		function save() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			var flag = check();
			if (flag == false) {
				return;
			}

			$.ajax({
				type : "POST",
				url : "${ctx}/customer/addcuststyle",
				data : $("form").serialize(),
				dataType : "json",
				cache : false,
				success : function(data) {
					window.parent.location.reload();
					parent.layer.close(index);
				},
				error : function(error) {
				}
			})
		}
	</script>
</body>
</html>