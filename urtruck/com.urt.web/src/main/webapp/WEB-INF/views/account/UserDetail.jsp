<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Personal.details' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

	
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
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
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>

<style>
.error {
	color: red;
}

.verticalSpacing {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
function addLenovo(){
	layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Developing" />...");
}
function changePwd(title,url,w,h){
	layer_show(title,url,w,h);
}
function saveAccount(){
	$.ajax({
		url:"${ctx}/ssUser/updateAccount",
		data:$("#form_account").serialize(),
		dataType : "json",
		type:"POST",
		cache : false,
		success : function(data) {
			debugger;
			layer.msg(data.msg);
		},
		error:function(){
			layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Failed.To.Update.Account" />");
		}
	});
}
function saveCust(){
	debugger;
	$.ajax({
        type:"POST",
        url:"${ctx}/cust/saveCustPerson",
        data:$("#form_cust").serialize(),
		dataType : "json",
		cache : false,
		success : function(data) {
			debugger;
			if(data.success){
				layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Successful.Update.The.Personal.Data" />");
			}else{
				layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Failed.To.Update.The.Personal.Data" />");
			}
		},
		error : function(error) {
			layer.msg("<fmt:message bundle="${pageScope.bundle}"  key="Failed.To.Update.The.Personal.Data" />");
		}
	});
}
</script>
</head>
<body>
<div class="">
	<div class="seconSec font12">
		<form method="post"
			class="form form-horizontal" id="form_account" name="form-account">
			<div class="row cl">
				<h1 class="padding-lr10"><fmt:message bundle="${pageScope.bundle}"  key="Account.Information" />：</h1>
			</div>
			<div class="row cl">
				<div class="col-10 from-control">
					<label class="form-label col-3 font12"><span class="c-red" required>*</span><fmt:message bundle="${pageScope.bundle}"  key="Login.Name" />：</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" id="loginName" name="loginName" required value="${account.loginName}">
						<input type="hidden" class="input-text" id="acconutId" name="acconutId" value="${account.acconutId}">
					</div>
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Nickname" />：</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" id="nickname" name="nickname" value="${account.nickname}">
					</div>
	
				</div>
				<%-- <div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12">密码：</label>
					<div class="formControls col-3 font12">
						<input type="password" class="input-text" id="pwd" name="pwd" readonly="readonly" value="${account.password}">
					</div>
					<button class="btn btn-primary radius">修改密码</button>
					
				</div> --%>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Remark" />：</label>
					<div class="formControls col-3 font12">
						<textarea rows="4" cols="50" name="remark" id="remark">${account.remark}</textarea>
					</div>
					<c:set var="zhang" scope="session" value="${account.nickname}"></c:set>
					<c:if test="${zhang=='yifan'}">
						<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Getting.St" />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="nickname" name="nickname" value="${lpsust}">
						</div>
					</c:if>
				</div>
			</div>
			<div class="row cl" style="margin-bottom: 20px;">
				<div class="col-6 col-offset-5">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Submit" />&nbsp;&nbsp;" onclick="saveAccount();">
					<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Reduce" />&nbsp;&nbsp;"> 
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Modify.Password" />&nbsp;&nbsp;" onclick="changePwd('<fmt:message bundle="${pageScope.bundle}"  key="Modify.Password" />','${ctx}/ssUser/toChangePwd','800','400')"> 
				</div>
			</div>
		</form>
		<c:if test="${custPerson}!=null">
		<form method="post" class="form form-horizontal" id="form_cust" name="form_cust">
			<div class="row cl">
				<h1><fmt:message bundle="${pageScope.bundle}"  key="Customer.Information" />：</h1>
				<input type="hidden" name="custId" id="custId" value="${custPerson.custId}">
			</div>
			<div class="row cl">
				<div class="col-10 from-control">
					<label class="form-label col-3 font12"><span class="c-red" required>*</span><fmt:message bundle="${pageScope.bundle}"  key="Customer.Name" />：</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" id="custName" name="custName" value="${customer.custName}" required>
					</div>
					<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle="${pageScope.bundle}"  key="Contact.Number" />：</label>
					<div class="formControls col-3">
						<input type="text" class="input-text" id="phone" name="phone" required value="${custPerson.phone}">
					</div>
	
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Certificate.Type" />：</label>
					<input type="hidden" id="psptTypeValue" value="${custPerson.psptTypeCode}" />
					<div class="formControls col-3">
						<select id="psptTypeCode" name="psptTypeCode">
							<option value="1"><fmt:message bundle="${pageScope.bundle}"  key="Business.License" /></option>
							<option value="2"><fmt:message bundle="${pageScope.bundle}"  key="Legal.Person.Certificate" /></option>
							<option value="3"><fmt:message bundle="${pageScope.bundle}"  key="Organization.Code.Certificate" /></option>
							<option value="4"><fmt:message bundle="${pageScope.bundle}"  key="Introduction.Letter" /></option>
							<option value="5"><fmt:message bundle="${pageScope.bundle}"  key="Note" /></option>
						</select>
					</div>
					<script type="text/javascript">
						$("#psptTypeCode").val($("#psptTypeValue").val());
					</script>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Certificate.Number" /></lable>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="psptId" name="psptId" value="${custPerson.psptId}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Certificate.Validity" />：</label>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="psptEndDateStr" name="psptEndDateStr" onclick="WdatePicker()" readonly="readonly" value="${psptEndDateStr}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Certificate.Address" />：</label>
					<div class="formControls col-6 font12">
						<input type="text" class="input-text" id="psptAddr" name="psptAddr" value="${custPerson.psptAddr}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Contacting.Address" />：</label>
					<div class="formControls col-6 font12">
						<input type="text" class="input-text" id="postAddress" name="postAddress" value="${custPerson.postAddress}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Postal.Code" />：</label>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="postCode" name="postCode" value="${custPerson.postCode}">
					</div>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Addressee" />：</lable>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="postPerson" name="postPerson" value="${custPerson.postPerson}">
					</div>
				</div>
				
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Gender" />：</label>
					<input type="hidden" class="input-text" id="sexVal" value="${custPerson.sex}" >
					<div class="formControls col-3 font12">
						<select id="sex" name="sex">
							<option value="1"><fmt:message bundle="${pageScope.bundle}"  key="Male" /></option>
							<option value="2"><fmt:message bundle="${pageScope.bundle}"  key="Female" /></option>
						</select>
						<script type="text/javascript">
						$("#sex").val($("#sexVal").val());
					</script>
					</div>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Date.Of.Birth" />：</lable>
					<input type="hidden" class="input-text" id="birthdayFlagVal" value="${custPerson.birthdayFlag}" >
					<input type="hidden" class="input-text" id="sexVal" value="${custPerson.sex}" >
					<div class="formControls col-3 font12" style="display: inline-block;">
						<select id="birthdayFlag" name="birthdayFlag">
							<option value="1"><fmt:message bundle="${pageScope.bundle}"  key="Solar.Canlendar" /></option>
							<option value="2"><fmt:message bundle="${pageScope.bundle}"  key="Lunar.Canlendar" /></option>
						</select>
						<script type="text/javascript">
						$("#birthdayFlag").val($("#birthdayFlagVal").val());
					</script>
						<input type="text" class="input-text" style="width: 70%;" id="birthdayStr" name="birthdayStr" onclick="WdatePicker()" value="${birthdayStr}" readonly="readonly">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Fax.Number" />：</label>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="faxNbr" name="faxNbr" value="${custPerson.faxNbr}">
					</div>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Email" />：</lable>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="email" name="email" value="${custPerson.email}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Nationality" />：</label>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="nationalityCode" name="nationalityCode" value="${custPerson.nationalityCode}">
					</div>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Place.Of.Origin" />：</lable>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="localNativeCode" name="localNativeCode" value="${custPerson.localNativeCode}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="Graduation.University" />：</label>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="graduateSchool" name="graduateSchool" value="${custPerson.graduateSchool}">
					</div>
					<lable class="form-label col-3"><fmt:message bundle="${pageScope.bundle}"  key="Major" />：</lable>
					<div class="formControls col-3 font12">
						<input type="text" class="input-text" id="speciality" name="speciality" value="${custPerson.speciality}">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<label class="form-label col-3 font12"><fmt:message bundle="${pageScope.bundle}"  key="remark" />：</label>
					<div class="formControls col-3 font12">
						<textarea rows="4" cols="50" name="remark" id="remark">${custPerson.remark}</textarea>
					</div>
				</div>
				
			</div>
			<div class="row cl" style="margin-bottom: 20px;">
				<div class="col-6 col-offset-5">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Submit" />&nbsp;&nbsp;" onclick="saveCust();">
					<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Reduce" />&nbsp;&nbsp;"> 
				</div>
			</div>
		</form>
		</c:if>
		<form method="post" class="form form-horizontal" id="form_rel" name="form-bind">
			<div class="row cl">
				<h1><fmt:message bundle="${pageScope.bundle}"  key="Binding.Information" />：</h1>
			</div>
			<div class="row cl">
				<div class="col-10 from-control">
					<div class="form-label col-3 font12">
						<img  src="${ctx}/static/ui/images/bindIcon/len.png" style="background-color: #33485d;width:40xp;height:40px;" >
					</div>
					<div class="formControls col-7" >
						<img  src="${ctx}/static/ui/images/bindIcon/add.jpg" style="background-color: #33485d;width:40xp;height:40px;" onclick="addLenovo();" >
					</div>
	
				</div>
				<div class="col-10 from-control verticalSpacing">
					<div class="form-label col-3 font12">
						<img  src="${ctx}/static/ui/images/bindIcon/wenxin.png" style="background-color: #33485d;width:40xp;height:40px;">
					</div>
					<div class="formControls col-7" >
						<img  src="${ctx}/static/ui/images/bindIcon/add.jpg" style="background-color: #33485d;width:40xp;height:40px;" onclick="addLenovo();">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<div class="form-label col-3 font12">
						<img  src="${ctx}/static/ui/images/bindIcon/zhifubao.png" style="background-color: #33485d;width:40xp;height:40px;">
					</div>
					<div class="formControls col-7" >
						<img  src="${ctx}/static/ui/images/bindIcon/add.jpg" style="background-color: #33485d;width:40xp;height:40px;" onclick="addLenovo();">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<div class="form-label col-3 font12">
						<img  src="${ctx}/static/ui/images/bindIcon/qq.png" style="background-color: #33485d;width:40xp;height:40px;">
					</div>
					<div class="formControls col-7" >
						<img  src="${ctx}/static/ui/images/bindIcon/add.jpg" style="background-color: #33485d;width:40xp;height:40px;" onclick="addLenovo();">
					</div>
				</div>
				<div class="col-10 from-control verticalSpacing">
					<div class="form-label col-3 font12">
						<img  src="${ctx}/static/ui/images/bindIcon/weibo.png" style="background-color: #33485d;width:40xp;height:40px;">
					</div>
					<div class="formControls col-7" >
						<img  src="${ctx}/static/ui/images/bindIcon/add.jpg" style="background-color: #33485d;width:40xp;height:40px;" onclick="addLenovo();">
					</div>
				</div>
			</div>
			<!-- <div class="row cl" style="margin-bottom: 20px;">
				<div class="col-6 col-offset-5">
					<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="saveAcoountRel();">
					<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;还原&nbsp;&nbsp;"> 
				</div>
			</div> -->
		</form>
	</div>
</div>
</body>
</html>
