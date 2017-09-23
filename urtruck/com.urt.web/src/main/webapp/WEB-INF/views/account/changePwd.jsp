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
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='change.password' /></title>
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

<style>
.error{
	color:red;
}
.verticalSpacing{
	margin-top: 10px;
}
</style>
<script type="text/javascript">
function closeLayer(){
	debugger;
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
var falg=true;
function checkOldPwd(){
	var pwd=$("#oldPwd").val();
	$.ajax({
		url:"${ctx}/ssUser/checkPwd",
		data:{"pwd":pwd},
		type:"POST",
		dataType:"json",
		success:function(result){
			if(result.success==false){
				
				$("#oldpwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Password.error' />！");
				$("#oldpwMsg").css("color","red");
				$("#oldpwMsg").removeClass("success");
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Password.error' />！");
				flag=false;
			}else{
				$("#oldpwMsg").text("");
				$("#oldpwMsg").removeAttr("style");
				$("#oldpwMsg").addClass("success");
			}
		},
		error:function(){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Password.error' />！");
			flag=false;
		}
	});
}
function checkPW(){
	var pw=$("#plainPassword").val();
	/* var patrn=/^([a-zA-Z0-9]){6,19}$/;*/ 
	var patrn=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
	if (!patrn.exec(pw)){
		flag=false;
		$("#pwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />");
		$("#pwMsg").css("color","red");
		$("#pwMsg").removeClass("checkSuccess");
		return;
	}else{
		flag=true;
		$("#pwMsg").text("");
		$("#pwMsg").addClass("checkSuccess");
		$("#pwMsg").removeAttr("style");
	}
	if($("#confirmPassword").val()!=''){
		checkEqPw();
	}
	
}
function checkEqPw(){
	if($("#plainPassword").val()!=$("#confirmPassword").val()){
		$("#secondPwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Entered.passwords.differ' />");
		$("#secondPwMsg").css("color","red");
		$("#secondPwMsg").removeClass("checkSuccess");
		flag=false;
	}else{
		$("#secondPwMsg").text("");
		$("#secondPwMsg").addClass("checkSuccess"); 
		$("#secondPwMsg").removeAttr("style");
		flag=true;
	}
}
function submitForm(){
	checkOldPwd();
	if(flag==false){
		return;
	}
	checkPW();
	if(flag==false){
		return;
	}
	checkEqPw();
	if(flag==false){
		return;
	}
	$.ajax({
		url:"${ctx}/ssUser/changePwd",
		data:$("#form").serialize(),
		type:"POST",
		dataType:"json",
		success:function(result){
			if(result.success){
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='password.change.successful' />");
				closeLayer();
				
			}else{
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='password.change.unsuccessful' />");
			}
		},
		error:function(){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='password.change.unsuccessful' />");
		}
		
	});
}
</script>    
</head>
<body>
<div class="">
	<div class="seconSec font12">
<form method="post" class="form form-horizontal font12 fl" id="form" name="form-member-add">
	    <div class="row cl">
	    	<h1><fmt:message bundle='${pageScope.bundle}'  key='change.password' />:</h1>
	    </div>
	    <div class="row cl">
	    	<div class="col-10 col-offset-1">
				<div class="col-12 from-control verticalSpacing">
	    			<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='please.enter.old.password' />:</label>
			      	<div class="formControls col-6">
			        	<input type="password"  class="input-text" id="oldPwd" name="oldpw" onchange="checkOldPwd();" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.old.password' />" />
			      	</div>
			      	 <lable id="oldpwMsg" class="font12 hint"></lable>
			    </div>
				<div class="col-12 from-control verticalSpacing">
	    			<label class="form-label col-3" id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='new.password' />:</label>
			      	<div class="formControls col-6">
			        	<input type="password" class="input-text" id="plainPassword" name="plainPassword" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.password' />" onchange="checkPW();" />
			      	</div>
		            <lable id="pwMsg" class="font12 hint"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
			    </div>
				<div class="col-12 from-control verticalSpacing">
	    			<label class="form-label col-3" id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='new.password.confirm' />:</label>
			      	<div class="formControls col-6">
			        	<input type="password" class="input-text" id="confirmPassword" name="confirmPassword" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='retype.password' />" onchange="checkEqPw();" />
			      	</div>
			      	<lable id="secondPwMsg" class="font12 hint"></lable>
			    </div>
				
			   
			</div>
		</div>	
    	<div class="row cl">
	      	<div class="col-5 col-offset-4">
		        <input class="btn btn-primary radius" type="button" onclick="submitForm();"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;">
		        <input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
		        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
	      	</div>
    	</div>
</form>
</div>
</div>
</body>

</html>
