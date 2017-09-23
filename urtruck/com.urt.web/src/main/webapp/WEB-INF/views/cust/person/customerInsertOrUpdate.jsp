<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title>添加个人客户</title>
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
	/*管理员-增加*/
	var custId = '';
	function admin_add(title, url, w, h) {
		layer_show(title, url, w, h);
	}

	$(document).ready();
	 $(function() {
		debugger
		var custId=$("#custId").val();
		if(custId.length>0){
		   initSell();
		}
	    $("#form_cust").validate();
		$("#sellType").change(function() {
			var htmlList=[];
			htmlList.push("<option value='00'>请选择</option>");
			$("#dev").show();
			$("#devRV").show();
			var label=document.getElementById("attributeId");
			var label2=document.getElementById("attribute2Id");
			if($("#sellType").val() == 2){
				label.innerHTML="选择渠道：";
				label2.innerHTML="发展人：";
				$("#devRV").val("");
				$.ajax({
					url : "${ctx}/person/getChannelCust",									
					data : { "sellType":"1"},
					success : function(result) {
						for (var i = 0; i < result.length; i++) {
							htmlList.push("<option value='"+result[i].custId+"'>"+ result[i].custName + "</option>");
							cust_staff[result[i].custId]=result[i].rsrvStr1;
						}	
						$("#dev").html(htmlList.join(" "));
						$("#dev").change(function(){
							debugger
							$("#devRV").val(cust_staff[$("#dev").val()]);
						})
					}
				});
			}else{
				if($("#sellType").val() == 0){
					label.innerHTML="自营发展人：";   
				}else{
					label.innerHTML="渠道发展人：";
				}
				label2.innerHTML="发展渠道：";
				$.ajax({
					url : "${ctx}/cust/getStaffs",									
					data : {},
					success : function(result) {
						debugger
						for (var i = 0; i < result.length; i++) {
							htmlList.push("<option value='"+result[i].staffId+"'>"+ result[i].staffId + "</option>");
						}	
						$("#dev").html(htmlList.join(" "));
						$("#devRV").val("联想懂得");
					}
				});
			}

		});
	}); 
	 
	 function initSell(){
			var sellType=$("#sellTypeValue").val();
			$("#sellType").val(sellType);
			var htmlList=[];
			var label=document.getElementById("attributeId");
			var label2=document.getElementById("attribute2Id");
			$("#dev").show();
			$("#devRV").show();
			if(sellType=="2"){
				label.innerHTML="选择渠道：";
				label2.innerHTML="发展人：";
				var custId=$("#devCustValue").val();
				$.ajax({
					url : "${ctx}/cust/getChannelCust",									
					data : { "sellType":"1"},
					success : function(result) {
						for (var i = 0; i < result.length; i++) {
							htmlList.push("<option value='"+result[i].custId+"'>"+ result[i].custName + "</option>");
							cust_staff[result[i].custId]=result[i].rsrvStr1;
						}	
						$("#dev").html(htmlList.join(" "));	
						$("#dev").val(custId);
						$("#devRV").val(cust_staff[custId]);
					}
				});
			}else if(sellType=="1"){
				label.innerHTML="渠道发展人：";
				label2.innerHTML="发展渠道：";
				var staffId=$("#devRsRvStr1").val();
				$.ajax({
					url : "${ctx}/cust/getStaffs",									
					data : {},
					success : function(result) {
						for (var i = 0; i < result.length; i++) {
							htmlList.push("<option value='"+result[i].staffId+"'>"+ result[i].staffId + "</option>");
						}	
						debugger
						$("#dev").html(htmlList.join(" "));		
						$("#dev").val(staffId);
						$("#devRV").val("联想懂得");
					}
				});
			}else{
				label.innerHTML="自营发展人：";
				label2.innerHTML="发展渠道：";
				var staffId=$("#devRsRvStr1").val();
				$.ajax({
					url : "${ctx}/cust/getStaffs",									
					data : {},
					success : function(result) {
						for (var i = 0; i < result.length; i++) {
							htmlList.push("<option value='"+result[i].staffId+"'>"+ result[i].staffId + "</option>");
						}	
						debugger
						$("#dev").html(htmlList.join(" "));		
						$("#dev").val(staffId);
						$("#devRV").val("联想懂得");
					}
				});
			}
		}
	function saveCustomer() {
		var dataArr = [];
		dataArr.push($("#custId").val());
		dataArr.push($("#custName").val());
		dataArr.push($("#psptId").val());
		if($("#sex").val()=="" ||$("#sex").val()==0){
			dataArr.push("0");			
		}else{
			dataArr.push("1");				
		}
		dataArr.push($("#birthday").val());
		dataArr.push($("#psptAddr").val());
		dataArr.push($("#postAddress").val());	
		
		dataArr.push($("#custState").val());
		dataArr.push($("#psptTypeCode").val()==""?0:$("#psptTypeCode").val());	
		
		var str = dataArr.join(";");
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$.ajax({
			type : "POST",
			url : "${ctx}/person/save",
			data : {
				"accData" : str
			},
			dataType : "json",
		    cache : false,
			success : function(data) {
				window.parent.location.reload();
				parent.layer.close(index);
				alert("操作成功！");
				return data.objData;
				},
			error : function(error) {
				 alert("操作失败");
				}
			});
		/* $.ajax({
			type : "POST",
			url : "${ctx}/person/save",
			data : $("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(data) {
				window.parent.location.reload();
				parent.layer.close(index);
				alert("操作成功！");
				return data.objData;
			},
			error : function(error) {
				alert("操作成功！");
			}
		}); */
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
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_cust"
				name="form-member-add">
				<input type="hidden" id="custId" name="custId" value="${customerDto.custId}"> 
				<input type="hidden" id="custStateHidden" value="${customerDto.custState}" /> 
			<%-- 	<input type="hidden" id="psptTypeValue" value="${customerDto.psptTypeCode}" />  --%>
				
			    <input type="hidden" id="sex" value="${custPersonDto.sex}" />
			    
				<input type="hidden" id="birthday" value="${custPersonDto.birthday}" /> 
				<input type="hidden" id="psptAddr" value="${custPersonDto.psptAddr}" /> 
				<input type="hidden" id="postAddress" value="${custPersonDto.postAddress}" /> 			
				<input type="hidden" id="parentIdValue" value="${customerDto.parentId}" /> 
				<input type="hidden" id="devCustValue" value="${customerDto.devCust}" />
			    <input type="hidden" id="devActValue" value="${customerDto.devAct}" />
				<div class="row cl">
					<h1>客户信息：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3"><span class="c-red"
							required>*</span>客户名称：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${customerDto.custName}" id="custName" name="custName"
								required>
						</div>
						<label class="form-label col-3"><span class="c-red">*</span>客户类型：</label>
						<div class="formControls col-3 font12">
							<select id="custState" name="custState">
								<option value="0">潜在客户</option>
								<option value="1">在用客户</option>
								<option value="2">注销客户</option>
							</select>
							<script type="text/javascript">
								$("#custState")
										.val($("#custStateHidden").val());
							</script>
						</div>

					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3"><span class="c-red"
							required>*</span>证件号码：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${customerDto.psptId}" id="psptId" name="psptId">
						</div>
						<label class="form-label col-3">证件类别：</label>
						<div class="formControls col-3">
							<select id="psptTypeCode" name="psptTypeCode">
							   <option value="0">身份证</option>								
							</select>
							<script type="text/javascript">
								$("#psptTypeCode").val(
										$("#psptTypeValue").val());
							</script>
						</div>
					</div>
				</div>
				<div class="row cl" style="">
					<h1>个人资料：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">性别：</label>
						<div class="formControls col-3">
							<%-- <input type="text" class="input-text"
								value="${custPersonDto.sex}" id="sex"
								placeholder="请输入男或者女" name="sex">  --%>
								
						 <select id="sex" name="sex">
								<option value="0">男</option>
								<option value="1">女</option>
							</select>
							<script type="text/javascript">
								$("#sex").val($("#sex").val());
							</script>
						</div>
						<label class="form-label col-3">生日：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.birthday}" id="birthday"
								placeholder="如：20170801" name="birthday">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">						
						<div style="display: inline;">
							<label class="form-label col-3">销售类型：</label>
							<div class="formControls col-3">
								<select id="sellType" name="sellType">
									<option value="0">自营</option>
									<option value="1">渠道客户</option>
								    <option value="2">渠道发展</option>
								</select>
								<script type="text/javascript">
									$("#sellType").val(
											$("#sellTypeValue").val());
								</script>
							</div>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
							<label class="form-label col-3" id="attributeId"></label>
							<div class="formControls col-3">
								<select id="dev" name="dev" class="input-text" style="display:none;"></select>
							</div>
							    <label class="form-label col-3" id="attribute2Id"></label>
							<div class="formControls col-3">
								<input type="text" class="input-text" value="" id="devRV" name="devRV" style="display:none;" readonly>
							</div>
						</div>
					
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">身份证所在地：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.psptAddr}" id="psptAddr"
								name="psptAddr">
						</div>						
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3">居住地址：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
								value="${custPersonDto.postAddress}" id="postAddress"
								name="postAddress">
						</div>
					</div>
				</div>	
					<div class="row cl" style="margin-bottom: 20px;">
						<div class="col-8 col-offset-4">
							<input class="btn btn-primary radius" type="button"
								value="&nbsp;&nbsp;提交&nbsp;&nbsp;" onclick="saveCustomer();">
							<input class="btn btn-default radius" type="reset"
								value="&nbsp;&nbsp;清空&nbsp;&nbsp;"> <input
								class="btn btn-primary radius" type="button"
								value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="closeLayer();">
						</div>
					</div>
			</form>
		</div>
	</div>
</body>
</html>
