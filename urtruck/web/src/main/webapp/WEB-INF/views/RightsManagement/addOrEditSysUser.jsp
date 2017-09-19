<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Add.system.user' /></title>
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
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<style>
.error {
	color: red;
}
</style>

<script type="text/javascript">
        function getCookie(c_name)
        {
        if (document.cookie.length>0)
          {
          c_start=document.cookie.indexOf(c_name + "=")
          if (c_start!=-1)
            {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
            }
          }
        return ""
        }

	$(document).ready(function() {
		var type = "${submitType}";
		console.log(type);
		//跳转更新页面时，隐藏密码
		if (type == "update") {
			$("#pasDivId").attr("style", "display:none");
			$("#pasDivId2").attr("style", "display:none");
			$("#password").attr("disabled", true);
		}else{
		}
	});
</script>
<script type="text/javascript">
	function Trim(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
	function saveSysUser() {
		// var custId = parent.$("#custId").val();
		// $("#custId").val(custId);
		debugger;
    var custId = parent.$("#custId").val();
        if (custId != undefined) {
            $("#custId").val(custId);
        }

        /* var custId2 = getCookie("postCustId");
        if (custId2 != undefined) {
            $("#custId").val(custId2);
        } */
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    console.log('index'+index);
		$.ajax({
			type : "POST",
			url : "${ctx}/ssUser/${submitType}",
			data : $("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
				window.parent.location.reload();
				parent.layer.close(index);
			},
			error : function(error) {
			}
		});
	}
	var flag = true;
	var oldLoginName = "";
	function checkLoginName() {
		debugger;
		var loginName = Trim($("#loginName").val());
		oldLoginName = Trim($("#oldLoginName").val());
		var msg = '';
		var patrn = /^([a-zA-Z0-9]){6,20}$/;
		if (!patrn.exec(loginName)) {
			flag = false;
			$("#loginNameMsg").css("color", "red");
			return;
		} else {
			$("#loginNameMsg").removeAttr("style");
			flag = true;
		}
		if (oldLoginName == loginName) {
			msg = "<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />";
			$("#loginNameMsg").text(msg);
			$("#loginNameMsg").removeAttr("style");
			flag = true;

		} else {
			$.ajax({
				url : "${ctx}/register/checkLoginName",
				type : "post",
				data : {
					'loginName' : loginName
				},
				success : function(result) {
					debugger;
					console.log("result:" + result);
					if (result == 'false') {
						msg = "<fmt:message bundle='${pageScope.bundle}'  key='login.name.already.exists' />";
						flag = false;
						$("#loginNameMsg").text(msg);
						$("#loginNameMsg").css("color", "red");
					} else {
						msg = "<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />";
						$("#loginNameMsg").text(msg);
						$("#loginNameMsg").removeAttr("style");
						flag = true;
					}

				}
			});
		}

	}
	function checkEqPw() {
		if ($("#password").val() != $("#confirmPassword").val()) {
			$("#secondPwMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='Entered.passwords.differ' />");
			$("#secondPwMsg").css("color", "red");
			flag = false;
		} else {
			$("#secondPwMsg").text("");
			$("#secondPwMsg").removeAttr("style");
			flag = true;
		}
	}
	function checkPW() {
		var pw = $("#password").val();
		var patrn=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;
		/* var patrn = /^([a-zA-Z0-9]){6,20}$/; */
		if (!patrn.exec(pw)) {
			flag = false;
			$("#pwMsg").css("color", "red");
			return;
		} else {
			flag = true;
			$("#pwMsg").removeAttr("style");
		}
		if ($("#confirmPassword").val() != '') {
			checkEqPw();
		}
	}
	function check() {
		checkLoginName();
		if (flag == false) {
			return;
		}
		if (oldLoginName != "") {
			saveSysUser();
		} else {
			checkEqPw();
			if (flag == false) {
				return;
			}
			checkPW();
			debugger;
			if (flag == false) {
				return "";
			} else {
				saveSysUser();
			}
		}

	}
	function clearErrMsg() {
		$("#loginNameMsg").text("<fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' />");
		$("#loginNameMsg").removeAttr("style");
		$("#secondPwMsg").text("");
		$("#secondPwMsg").removeAttr("style");
		$("#pwMsg").removeAttr("style");
	}
	function closeLayer() {
		debugger;
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='Basic.information' /></h1>
			<form role="form" class="form form-horizontal" id="majorForm">
				<div class="col-6 mt20 zpHeight">
					<input type="hidden" class="input-text"
						value="${ssUserDto.acconutId}" placeholder="" id="acconutId"
						name="acconutId"> <label
						class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />:</span>
					</label>
					<div class="formControls zpInput">
						<input type="text" class="input-text"
							value="${ssUserDto.loginName}" placeholder="" id="loginName"
							name="loginName" onchange="checkLoginName();"> <input
							type="hidden" class="input-text" value="${ssUserDto.loginName}"
							placeholder="" id="oldLoginName" name="oldLoginName">
						<lable id="loginNameMsg" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
						<input type="hidden" id="custId" name="custId" />
					</div>
				</div>
				<div class="col-6 mt20 zpHeight">
					<label class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Nickname' />:</span>
					</label>
					<div class="formControls zpInput">
						<input type="text" class="input-text"
							value="${ssUserDto.nickname}" placeholder="" id="nickname"
							name="nickname">
					</div>
				</div>
				<div class="col-6 mt20 zpHeight" id="pasDivId">
					<label class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Password' />：</span>
					</label>
					<div class="formControls zpInput">
						<input type="password" class="input-text" id="password"
							name="password" value="${ssUserDto.plainPassword}"
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Password' />" onchange="checkPW();"></br>
						<lable id="pwMsg" class="font12"><fmt:message bundle='${pageScope.bundle}'  key='login.name.must.be.6-20.digits.and.combinations.of.letters.and.cannot.be.Chinese' /></lable>
					</div>
				</div>
				<div class="col-6 mt20 zpHeight" id="pasDivId2">
					<label class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='confirm.password' />：</span>
					</label>
					<div class="formControls zpInput">
						<input type="password" class="input-text" id="confirmPassword"
							placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Re-enter.password' />" value="${ssUserDto.password}"
							onchange="checkEqPw();">
						<lable id="secondPwMsg" class="font12"></lable>
					</div>
				</div>
                <!-- 一级组织部门-->
                <div class="col-6 mt20 zpHeight" id="pasDivId">
                    <label class="fl labelWidth control-label text-left">
                    <span
                        class="colorRed smallStar"></span><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='primary.sector' />：</span>
                    </label>
                    <!-- <div class="formControls zpInput">
                        <input type="password" class="input-text" id="password"
                            name="password" value="${ssUserDto.fstStruct}"
                            placeholder="一级组织部门" ></br>
                    </div> -->
                     <div class="formControls zpInput">
                    <input type="text" class="input-text"
                            value="${ssUserDto.fstStruct}" id="fstStruct"
                            name="fstStruct" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Level.I.organization' />"></br>
                         </div>
                    </div>
                <!-- 二级组织部门 -->
                <div class="col-6 mt20 zpHeight" id="pasDivId2">
                    <label class="fl labelWidth control-label text-left">
                    <span
                        class="colorRed smallStar"></span>
                    <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='subordinate.units' />：</span>
                    </label>
                    <div class="formControls zpInput">
                         <input type="text" class="input-text"
                            value="${ssUserDto.secStruct}" id="secStruct"
                            name="secStruct" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Secondary.organization' />">
                    </div>
                    </div>
                </div>
				<%-- <div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>手机:</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${ssUserDto.phone}" placeholder="" id="phone" name="phone" validate="{required:true}">
      </div>
      <div class="col-4"> </div>
	</div>
	<div class="row cl">
      <label class="form-label col-3"><span class="c-red">*</span>电子邮箱:</label>
      <div class="formControls col-5">
        <input type="text" class="input-text" value="${ssUserDto.email}" placeholder="" id="email" name="email" validate="{required:true}">
      </div>
      <div class="col-4"> </div>
	</div> --%>
				<div class="col-12 mt20 zpHeight2">
					<label class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='role' />:</span>
					</label>
					<div class="formControls zpInput">
						<c:forEach var="item" items="${roleList}" varStatus="status">
							<div class="col-6">
								<label class="font12"> <input type="radio"
									value="${item.roleId }" name="roleIds" id="userRoleId_1"
									<c:forEach items="${ssUserDto.roles}" var="role">
			                 <c:if test="${role.roleId == item.roleId}">
			                     checked="checked"
			                 </c:if>
             			 </c:forEach> />${item.roleName}
								</label>
							</div>
							<c:if test="${status.index%2==2}">
								<br>
							</c:if>

						</c:forEach>
					</div>
				</div>
				<div class="col-12 mt20 zpHeight2">
					<label class="fl labelWidth control-label text-left"> <span
						class="colorRed smallStar"></span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Remark' />:</span>
					</label>
					<div class="formControls zpInput">
						<textarea id="remark" name="remark" cols="" rows=""
							class="textarea" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='DEFAULT.USER.REMARK' />" dragonfly="true"
							onKeyUp="textarealength(this,100)">${ssUserDto.remark}</textarea>
						<p class="textarea-numberbar">
							<em class="textarea-length">0</em>/100
						</p>
					</div>
					<div class="col-2"></div>
				</div>
				<div class="col-12" style="margin-bottom: 20px;">
					<div class="zpButton">
						<input class="btn btn-primary radius" type="button"
							onclick="check();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;"> <input
							class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Reduction' />&nbsp;&nbsp;" onclick="clearErrMsg();">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
