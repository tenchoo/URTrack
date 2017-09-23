<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='create.account' /></title>
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
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript">
		/* $(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					loginName: {
						remote: "${ctx}/register/checkLoginName"
					}
				},
				messages: {
					loginName: {
						remote: "用户登录名已存在"
					}
				}
			});
		}); */
		var flag=true;
		function checkLoginName(){
			debugger;
			var loginName=$("#loginName").val();
			var msg='';
			var patrn=/^([a-zA-Z0-9]){6,20}$/;   
			if (!patrn.exec(loginName)){
				flag=false;
				$("#loginNameMsg").css("color","red");
				return;
			}else{
				$("#loginNameMsg").removeAttr("style");
			}
			debugger;
			$.ajax({
				url:"${ctx}/register/checkLoginName",
				type:"post",
				data:{'loginName':loginName},
				success:function(result){
					debugger;
					console.log("result:"+result);
					if(result=='false'){
						msg="<fmt:message bundle='${pageScope.bundle}'  key='login.name.already.exists' />"; 
						flag=false;
						$("#loginNameMsg").text(msg);
						$("#loginNameMsg").css("color","red");
					}else{
						msg="<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />";
						$("#loginNameMsg").text(msg);
						$("#loginNameMsg").removeAttr("style");
						flag=true;
					}
					
				}
			});
			
		}
		function checkEqPw(){
			if($("#plainPassword").val()!=$("#confirmPassword").val()){
				$("#secondPwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Entered.passwords.differ' />");
				$("#secondPwMsg").css("color","red");
				flag=false;
			}else{
				$("#secondPwMsg").text("");
				$("#secondPwMsg").removeAttr("style");
				flag=true;
			}
		}
		function checkPW(){
			var pw=$("#plainPassword").val();
			var patrn=/^([a-zA-Z0-9]){6,19}$/;   
			if (!patrn.exec(pw)){
				flag=false;
				$("#pwMsg").css("color","red");
				return;
			}else{
				flag=true;
				$("#pwMsg").removeAttr("style");
			}
			debugger;
			if($("#confirmPassword").val()!=''){
				checkEqPw();
			}
			
		}
		function check(){
			checkLoginName();
			if(flag==false){
				return false;
			}
			checkEqPw();
			if(flag==false){
				return false;
			}
			checkPW();
			debugger;
			if(flag==false){
				return false;
			}else{
				return true;
			}
		}
</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/register" method="post" class="form-horizontal" onsubmit="return check();">
		<fieldset>
			<legend><small><fmt:message bundle='${pageScope.bundle}'  key='create.account' /></small></legend>
			<div class="control-group">
				<label for="loginName" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />:</label>
				<div class="controls">
					<c:if test="${!empty name}">
						<input type="text" id="loginName" name="loginName" class="input-large required" minlength="3" value="${name}" readonly="readonly"/>
					</c:if>
					<c:if test="${empty name}">
						<input type="text" id="loginName" name="loginName" class="input-large required" minlength="3" value="${name}" onchange="checkLoginName();"/><lable id="loginNameMsg"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
					</c:if>
					<input type="hidden" id="relatedId" name="relatedId" class="input-large required" value="${relatedId}"/>
				</div>
			</div>
			<div class="control-group">
				<label for="name" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='Nickname' />:</label>
				<div class="controls">
					<input type="text" id="nickname" name="nickname" class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='Password' />:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword" class="input-large required" onchange="checkPW();"/><lable id="pwMsg"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label"><fmt:message bundle='${pageScope.bundle}'  key='confirm.password' />:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword" class="input-large required" equalTo="#plainPassword" onchange="checkEqPw();"/><lable id="secondPwMsg"></lable>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="<fmt:message bundle='${pageScope.bundle}'  key='return' />" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
