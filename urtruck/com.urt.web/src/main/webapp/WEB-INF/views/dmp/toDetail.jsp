<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Define.warning.rules' /></title>
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<style>
.error {
	color: red;
}
.verticalSpacing {
	margin-top: 10px;
}

</style>
<script type="text/javascript">
  
	function initGroup(){
		var schemeType=$("#schemeType").val();
		if(schemeType=="1"){
			$("#selectGroup").addClass("selectGroup");
			$("#groups").empty();
		}else if(schemeType=="2"){
			$("#selectGroup").removeClass("selectGroup");
			$.ajax({
				url:"${ctx}/tactical/getGroups",
				data:{},
				success:function(result){
				 var htmlList=[];
				 for(var i=0;i<result.length;i++){
				    	var str=' <label class="font12"><input type="checkbox" name="group" value=\"'+result[i].id+'\"/>'+result[i].groupName+'&nbsp&nbsp&nbsp&nbsp</label>';
				    	htmlList.push(str);
				    }
				    $("#groups").html(htmlList.join(" "));
				}
			});
		}
	}
	$(document).ready(function() {
		$("#form_rule").validate();
	});
   
	
	
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_rule" name="form-member-add">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Policy.name' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="text" class="input-text" id="schemeName" name="schemeName" value="${schemeDto.schemeName}" readOnly required>
							<input type="hidden" class="input-text" id="schemeId" name="schemeId" value="${schemeDto.id}" required>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Policy.description' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
						<textarea rows="4px" cols="62px" id="schemeComment" name="schemeComment" readOnly  required>${schemeDto.schemeComment}</textarea>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Policy.type' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							 
						<c:if test="${schemeDto.targittype == '1'}">
						   <input type="text" class="input-text" value="<fmt:message bundle='${pageScope.bundle}'  key='Common.policy' />" readOnly>
						</c:if>
						<c:if test="${schemeDto.targittype == '2'}">
						   <input type="text" class="input-text" value="<fmt:message bundle='${pageScope.bundle}'  key='privacy.policy' />" readOnly>
						</c:if>
						</div>
					</div>
				</div>
				<c:if test="${schemeDto.targittype == '2'}">
					<div>
					    <div class="row cl">
							<h1><fmt:message bundle='${pageScope.bundle}'  key='Device.group.selection' />：</h1>
						</div>
						<div class="row cl" id="groups" style="margin-left:86px;">
						    <c:forEach items="${groupDtos}" var="groupDto" varStatus="vs">  
						            <tr>  
						                 <td align = "center">${groupDto.groupName}</td>  
						            </tr>  
						    </c:forEach>  
				       </div>
				    </div>	   
				</c:if>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Constraints.and.operations' />：</h1>
				</div>
				<div class="row cl" id="condition" style="margin-left:86px;">
					 <c:forEach items="${operationMap}" var="map">
					       <p>${map.key}：</p>
					       <div style="float:left;">
							    <ol>
							    <c:forEach items="${map.value}" var="operationDto">
	                                <li>--${operationDto.operationName}</li>
								</c:forEach>
	                            </ol>
                           <div>
				     </c:forEach>
				</div>
				
			</form>
		</div>
	</div>
</body>
</html>
