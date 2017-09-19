<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>
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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Single.card.operation' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />


<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript">
$(function(){
	
})
//触发事件，查询卡状态
function operation(){

	var pars = {iccid:$("#iccid").val(),custid:$("#custid").val()}
			$("input[type='radio']").removeAttr('checked');
	 $.ajax({
		type:"POST",
		data:pars,
		dataType:"json",
		cache : false,
		url:"${ctx}/cardOperation/selectState",
		success:function(data){
			if(data.retMsg=="1"){
			$("#cardStatus").html("<fmt:message bundle='${pageScope.bundle}'  key='this.card.is.in.the.state.of.power-on' />");
			$("#open").prop("disabled",true);
			$("#stop").prop("checked",true);
			}else if(data.retMsg=="2"){
			$("#cardStatus").html("<fmt:message bundle='${pageScope.bundle}'  key='this.card.is.in.the.state.of.power-off' />");
			$("#open").prop("checked",true);
			$("#stop").prop("disabled",true);
			}else{
				$("#cardStatus").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.input.a.valid.iccid' />");
				$("#open").prop("disabled",true);
				$("#stop").prop("disabled",true); 
			}
		}
	}) 
}
//停开机功能
function stopOnCard(){
	var param = {commend:$("input[name='Fruit']:checked").val(),iccid:$("input[name='iccid']").val(),custid:$("#custid").val()};
		 $.ajax({
			type:"POST",
			data:param,
			dataType:"json",
			cache : false,
			url:"${ctx}/cardOperation/stopOnCard",
			success:function(data){
				if(data.success){
					layer.alert(data.msg,function(){
					layer.closeAll();
					location.reload();
					})
				}else{
					  layer.alert(data.msg,function(){
                          layer.closeAll();
                          location.reload();
                      })
				}
			}
		}) 
}
function save(){
	var str = $("#cardStatus").html();
	var commend = $("input[name='Fruit']:checked").val();
	var iccid = $("input[name='iccid']").val();
	if(str==""&&iccid!=""){
		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Wait.a.moment,query.in.process' />");
	}else if(commend==0){
		layer.confirm('<fmt:message bundle='${pageScope.bundle}'  key="are.you.sure.to.cancel.card" />？', {
			  btn: ['<fmt:message bundle="${pageScope.bundle}"  key="YES" />','<fmt:message bundle="${pageScope.bundle}"  key="NO" />'] //按钮
			}, function(){
				stopOnCard();
			}, function(){
			  
			});
	}else if(commend==1){
		layer.confirm('<fmt:message bundle="${pageScope.bundle}"  key="are.you.sure.to.active.card" />？', {
			  btn: ['<fmt:message bundle="${pageScope.bundle}"  key="YES" />','<fmt:message bundle="${pageScope.bundle}"  key="NO" />'] //按钮
			}, function(){
				stopOnCard();
			}, function(){
			  
			});
	}else if(commend==null){
		layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='nonexcutable.operation' />");
	}else if(str=="请输入正确的iccid"){
		layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='nonexcutable.operation' />");
	}
	
	
}
</script> 
</head>
<body>
<input type="hidden" id="custid" value="${custId}">
	<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='user.operation' /></h1>
	</div>
<div class="pd-20 font12">
		<form role="form" id="submitForm">
		<div class="oh">
			<div class="col-12">
				<lable class="kehu">ICCID: </lable>
				<input type="text" name="iccid" id="iccid" maxlength=30" 请输入Iccid"  style="width:250px" class="input-text" onmouseout="operation(this.id)" on>
				<div id="cardStatus" style="color: red;margin-left: 95px;font-size: 150%"></div>
			</div>
		</div>
		
		<div class="oh row">
			    <label class="font12" style="float: left;width: 10%;">
			    <input name="Fruit" id="stop" type="radio" value="0"  /><fmt:message bundle='${pageScope.bundle}'  key='deactivate' /></label> 
			    <label class="font12" style="width: 10%;">
			    <input name="Fruit" id="open" type="radio" value="1" /><fmt:message bundle='${pageScope.bundle}'  key='starting.up' /></label> 
	    </div>
		<div>
			<font style="margin-left: 30px;color: red">※&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='When.the.phone.plan.of.card.is.not.used.up' />。</font>
		</div>
		<div class="text-center mt20">
			<button id="search" type="button" class="btn btn-primary" style="margin-left:50px;float:left;width: 5%;"onclick="save()"><fmt:message bundle='${pageScope.bundle}'  key='Submit' /></button>
		</div>	
		</form>
</div>
</body>
</html>
