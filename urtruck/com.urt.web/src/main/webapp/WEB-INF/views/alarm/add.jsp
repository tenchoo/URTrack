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
	$.ajax({

		url : "${ctx}/alarm/getLevel1",
		data : {},
		success : function(result) {
			var select = $("#level1").select2({
				width : 200,
				placeholder : '<fmt:message bundle='${pageScope.bundle}'  key='Primary.classification' />',
				tags : "true",
				allowClear : true,
				data : result
			});
			////
			$("#level1").change(function() {
				$('#level2').empty();
				$.ajax({

					url : "${ctx}/alarm/getLevel2",
					data : {
						"pid" : $("#level1").val()
					},
					success : function(result) {
						var select = $("#level2").select2({
							width : 200,
							placeholder : '<fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />',
							tags : "true",
							allowClear : true,
							data : result
						});
						$("#level2").change(function() {
							level2Changed();
						});
					}
				});
			})
		}
	});
	function level2Changed(){
		$.ajax({
			url : "${ctx}/alarm/getElementsByRuleType",
			data : {
				"ruleId" : $("#level2").val()
			},
			success : function(result) {
				var condition=eval(result.condition);
				var execute=eval(result.execute);
				/* var dataObj=eval("("+data+")"); */
				if(condition!=null && condition!="undefinde"){
					var condition_div="";
					$("#condition").empty();
					for(var i=0; i<condition.length; i++){
						
						if(i==0 || condition[i].ELEM_GROUP!=condition[i-1].ELEM_GROUP){
							condition_div="condition_div_"+condition[i].ELEM_GROUP;
							$("#condition").append("<div id="+condition_div+" class='col-10 from-control'>");
						}
						if(condition[i].DISPLAY_TYPE==1){
							$("#"+condition_div).append("<label class='form-label col-3' style='margin-top:10px;'>"+condition[i].ELEM_DISPLAY+"</label>");
							/* $("#"+condition_div).append("<input type='hidden' name='elementIds' value='"+condition[i].ELEMENT_ID+"'>"); */
						}
						if(condition[i].DISPLAY_TYPE==2){
							$("#"+condition_div).append("<div class='formControls col-7' style='margin-top:10px;'><input type='text' class='input-text' value='' name='"+condition[i].ELEMENT_ID+"' required />"
							+"<input type='hidden' name='elementIds' value='"+condition[i].ELEMENT_ID+"'></div>");
						}
						if(condition[i].DISPLAY_TYPE==3){ 
							$("#"+condition_div).append("<div class='formControls col-10'>");
							$("#"+condition_div).append("<select multiple='multiple' id='"+condition[i].ELEMENT_ID+"' name='"+condition[i].ELEMENT_ID+"' placeholder='<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />..' class='form-control select2'></select>"
									+"<input type='hidden' name='elementIds' value='"+condition[i].ELEMENT_ID+"'></div>");
							$("#"+condition[i].ELEMENT_ID).select2({
								  data: condition[i].ELEMENT_SOURCE,
								  placeholder:'<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />',
								  tags: 'true',
								  multiple: true,
								  allowClear:true
								})
						}
						/* if(i==condition.length || condition[i].ELEM_GROUP!=condition[i-1].ELEM_GROUP){
							$("#condition").append("</div>");
						} */
						if(i==condition.length){
							$("#condition").append("</div>");
						}else if(i!=0 && condition[i].ELEM_GROUP!=condition[i-1].ELEM_GROUP){
							$("#condition").append("</div>");
						}
						
					}  
				}
				if(execute!=null && execute!="undefinde"){
					var execute_div="";
					$("#execute").empty();
					for(var i=0; i<execute.length; i++){
						if(i==0 || execute[i].ELEM_GROUP!=execute[i-1].ELEM_GROUP){
							execute_div="execute_div_"+execute[i].ELEM_GROUP;
							$("#execute").append("<div id="+execute_div+" class='col-10 from-control'>");
							
						}
						if(execute[i].DISPLAY_TYPE==1){
							$("#"+execute_div).append("<label class='form-label col-3' style='margin-top:10px;'>"+execute[i].ELEM_DISPLAY+"</label>");
							/* $("#condition").append("<div id="+condition_div+" class='col-10 from-control'>"); */
						}
						if(execute[i].DISPLAY_TYPE==2){
							$("#"+execute_div).append("<div class='formControls col-7' style='margin-top:10px;'><input type='text' class='input-text' value='' name='"+execute[i].ELEMENT_ID+"' required placeholder='多个请用逗号隔开' />"+
									"<input type='hidden' name='elementIds' value='"+execute[i].ELEMENT_ID+"'></div>");
						}
						if(execute[i].DISPLAY_TYPE==3){
							$("#"+execute_div).append("<div class='formControls col-10'>"+
							"<select id='"+execute[i].ELEMENT_ID+"' name='"+execute[i].ELEMENT_ID+"' placeholder='<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />..' class='form-control select2'></select>"+
							"<input type='hidden' name='elementIds' value='"+execute[i].ELEMENT_ID+"'></div>");
							
							$("#"+execute[i].ELEMENT_ID).select2({
								  data: eval(execute[i].ELEMENT_SOURCE),
								  placeholder:'<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />',
								  tags: 'true',
								  multiple: true,
								  allowClear:true
								})
						}
						if(i==execute.length){
							$("#execute").append("</div>");
						}else if(i!=0 && execute[i].ELEM_GROUP!=execute[i-1].ELEM_GROUP){
							$("#execute").append("</div>");
						}
					}  
					
				}
				
			}
		});
		
	}
	$(document).ready(function() {
		$("#form_rule").validate();
	});

	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	function saveRule(){
		if($("#ruleName").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.rule.name.cannot.be.empty' />");
			return;
		}
		if($("#level1").val()=="-1" || $("#level1").val()=="" || $("#level1").val()==null){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Primary.classification.can.t.be.empty' />");
			return;
		}
		if($("#level2").val()=="-1" || $("#level2").val()=="" || $("#level2").val()==null){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='second.level.classification.can.t.be.empty' />");
			return;
		}
		var index = parent.layer.getFrameIndex(window.name);
		$.ajax({
	        type:"POST",
	        url:"${ctx}/alarm/save",
	        data:$("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='successfully.added' />");
				window.parent.location.reload();
				parent.layer.close(index);
			},
			error : function(error) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='failed.added' />");
			}
		});
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_rule"
				name="form-member-add">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='rule.name' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="text" class="input-text" id="ruleName" name="ruleName" required>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='select.classification' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Primary.classification' />：</label>
						<div class="formControls col-3">
							<select id="level1" name="level1" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.rule.classes' />"
								class="form-control select2">
							</select>
						</div>
						<label class="form-label col-3"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />：</label>
						<div class="formControls col-3">
							<select id="level2" name="ruleTypeId" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.rule.type' />"
								class="form-control select2">
							</select>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Rule.constraint' />：</h1>
				</div>
				<div class="row cl" id="condition">
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='perform.action' />：</h1>
				</div>
				<div class="row cl" id="execute">
				</div>
				<div class="row cl" style="margin-bottom: 20px;">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveRule();">
						<input class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> <input
							class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
