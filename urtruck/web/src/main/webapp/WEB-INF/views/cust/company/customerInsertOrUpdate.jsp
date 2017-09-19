<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.lenovo.LAOAPI.dto.LaoSsAccountDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	LaoSsAccountDto user = (LaoSsAccountDto) session.getAttribute("sessionUser");
	Long currentCustId = user.getCustId();
	// 客户Id为-1表示为懂的客户
	boolean isSuper = currentCustId.longValue() == -1L ? true : false;
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Add.enterprise.customers' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css" />
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
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
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
	var cust_staff={};
	var custId = '';
	function admin_add(title, url, w, h) {
		layer_show(title, url, w, h);
	}
	/*
	*保存客户：
	*hasNext:是否进入下一步创建账号
	*/
	function saveCust(hasNext){
		if($("#custName").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='the.user.name.is.empty' />");
			return;
		};
		if($("#busiLicenceNo").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Registration.Mark.of.Business.License.is.empty' />");
			return;
		}
		if($("#busiLicenceValidDateStr").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='expire.date.of.Business.License.is.empty' />");
			return;
		}
		if($("#sellType").val()=="00"){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='sales.type.is.empty' />");
			return;
		}
		if($("#dev").val()=="00"||$("#dev").val()==null){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='unchoose.developer.or.channels' />");
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/cust/save",
			async : "false",
			data : $("form").serialize(),
			dataType : "json",
			cache : false,
			success : function(datas) {
				if(hasNext){
					custId = datas.objData;
					url = '${ctx}/ssUser/custToAdd' + custId;
					layer.open({
						shift : 5,
						id : 3,
						type : 2,
						title : '<fmt:message bundle="${pageScope.bundle}"  key="New.enterprise.account"/>',
						shadeClose : true,
						shade : 0.8,
						area : [ '720px', '525px' ],
						content : url
					});
				}else{
					var index = parent.layer.getFrameIndex(window.name); 
					window.parent.location.reload();
					parent.layer.close(index);
					return datas.objData;
				}
				
			},
			error : function(error) {
			}
		});
	}
$(function() {
	var custId=$("#custId").val();
	if(custId.length>0){
	   initSell();
	}
    $("#form_cust").validate();
	$("#sellType").change(function() {
		var htmlList=[];
		htmlList.push("<option value='00'><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>");
		$("#dev").show();
		$("#devRV").show();
		var label=document.getElementById("attributeId");
		var label2=document.getElementById("attribute2Id");
		if($("#sellType").val() == 2){
			label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='choose.channel' />：";
			label2.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='developing.person' />：";
			$("#devRV").val("");
			$.ajax({
				url : "${ctx}/cust/getChannelCust",									
				data : { "sellType":"1"},
				success : function(result) {
					for (var i = 0; i < result.length; i++) {
						htmlList.push("<option value='"+result[i].custId+"'>"+ result[i].custName + "</option>");
						cust_staff[result[i].custId]=result[i].rsrvStr1;
					}	
					$("#dev").html(htmlList.join(" "));
					$("#dev").change(function(){
						$("#devRV").val(cust_staff[$("#dev").val()]);
					})
				}
			});
		} else {
			if($("#sellType").val() == 0){
				label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='self-operated.developer' />：";   
			}else if ($("#sellType").val() == 1) {
				label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='Channel.developer' />：";
			}
			label2.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='developing.channel' />：";
			$.ajax({
				url : "${ctx}/cust/getStaffs",									
				data : {},
				success : function(result) {
					debugger
					for (var i = 0; i < result.length; i++) {
						htmlList.push("<option value='"+result[i].staffId+"'>"+ result[i].staffId + "</option>");
					}	
					$("#dev").html(htmlList.join(" "));
					$("#devRV").val("<fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' />");
				}
			});
		}

	});
});



	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);

	}
	function initSell(){
		var sellType=$("#sellTypeValue").val();
		$("#sellType").val(sellType);
		var htmlList=[];
		var label=document.getElementById("attributeId");
		var label2=document.getElementById("attribute2Id");
		$("#dev").show();
		$("#devRV").show();
		if(sellType=="2"){
			label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='choose.channel' />：";
			label2.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='developing.person' />：";
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
			label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='Channel.developer' />：";
			label2.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='developing.channel' />：";
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
					$("#devRV").val("<fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' />");
				}
			});
		}else{
			label.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='self-operated.developer' />：";
			label2.innerHTML="<fmt:message bundle='${pageScope.bundle}'  key='developing.channel' />：";
			var staffId=$("#devRsRvStr1").val();
			$.ajax({
				url : "${ctx}/cust/getStaffs",						
				data : {},
				success : function(result) {
					for (var i = 0; i < result.length; i++) {
						htmlList.push("<option value='"+result[i].staffId+"'>"+ result[i].staffId + "</option>");
					}	
					$("#dev").html(htmlList.join(" "));		
					$("#dev").val(staffId);
					$("#devRV").val("<fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' />");
				}
			});
		}
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
				<input type="hidden" id="psptTypeValue" value="${customerDto.psptTypeCode}" /> 
				<input type="hidden" id="groupTypeValue" value="${custGroupDto.groupType}" /> 
				<input type="hidden" id="busiLicenceTypeValue" value="${custGroupDto.busiLicenceType}" /> 
				<input type="hidden" id="juristicPsptTypeValue" value="${custGroupDto.juristicPsptType}" />
				<input type="hidden" id="ids" value="${ids}" /> 
				<input type="hidden" id="sellTypeValue" value="${custGroupDto.sellType}" />
				<input type="hidden" id="parentIdValue" value="${customerDto.parentId}" /> 
				<input type="hidden" id="devRsRvStr1" value="${customerDto.rsrvStr1}" />
				<input type="hidden" id="devCustValue" value="${customerDto.devCust}" />
			    <input type="hidden" id="devActValue" value="${customerDto.devAct}" />
			    <input type="hidden" id="parentId" value="${customerDto.parentId}" />
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='customer.information' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" value="${customerDto.custName}" id="custName" name="custName" required>
						</div>
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer.state' />：</label>
						<div class="formControls col-3 font12">
							<select id="custState" name="custState" class="input-text">
								<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='potential.customer' /></option>
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='customer.in.use.service' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='account.closed' /></option>
							</select>
							<script type="text/javascript">
								$("#custState").val($("#custStateHidden").val());
							</script>
						</div>

					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Document.type' />：</label>
						<div class="formControls col-3 font12">
							<select id="psptTypeCode" name="psptTypeCode" class="input-text">
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='business.licence' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='legal.representative.and.registration.certificate' /></option>
								<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Organization.code.certificate' /></option>
								<option value="4"><fmt:message bundle='${pageScope.bundle}'  key='letter.of.introduction' /></option>
								<option value="5"><fmt:message bundle='${pageScope.bundle}'  key='diplomatic.note' /></option>
							</select>
							<script type="text/javascript">
								$("#psptTypeCode").val($("#psptTypeValue").val());
							</script>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Document.number' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${customerDto.psptId}" id="psptId" name="psptId">
						</div>
					</div>
				</div>
				<div class="row cl" style="">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='enterprise.data' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Registration.Mark.of.Business.License' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.busiLicenceNo}" id="busiLicenceNo"
								name="busiLicenceNo" required>
						</div>
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='business.license.types' />：</label>
						<div class="formControls col-3 font12">
							<select id="busiLicenceType" name="busiLicenceType" class="input-text" required>
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Co.Ltd' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Company.limited.by.shares' /></option>
								<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='individual.proprietorship.enterprise' /></option>
								<option value="4"><fmt:message bundle='${pageScope.bundle}'  key='partnership.business' /></option>
							</select>
							<script type="text/javascript">
								$("#busiLicenceType").val(
										$("#busiLicenceTypeValue").val());
							</script>
						</div>

					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.validity.of.the.business.license' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="busiLicenceValidDateStr"
								value="${busiLicenceValidDateStr}"
								name="busiLicenceValidDateStr" onclick="WdatePicker()"
								readonly="readonly">
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='type.of.enterprise' />：</label>
						<div class="formControls col-3 font12">
							<select id="groupType" name="groupType" class="input-text">
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='domestic.enterprise' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='state-owned.enterprise' /></option>
								<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='collectively-owned.enterprise' /></option>
								<option value="4"><fmt:message bundle='${pageScope.bundle}'  key='joint-equity.cooperative.enterprises' /></option>
								<option value="5"><fmt:message bundle='${pageScope.bundle}'  key='joint.venture' /></option>
								<option value="6"><fmt:message bundle='${pageScope.bundle}'  key='Co.Ltd' /></option>
								<option value="7"><fmt:message bundle='${pageScope.bundle}'  key='Company.limited.by.shares' /></option>
								<option value="8"><fmt:message bundle='${pageScope.bundle}'  key='investing.enterprise.Company.Ltd' /></option>
								<option value="9"><fmt:message bundle='${pageScope.bundle}'  key='Joint.venture.enterprises' /></option>
								<option value="10"><fmt:message bundle='${pageScope.bundle}'  key='cooperative.enterprise' /></option>
								<option value="11"><fmt:message bundle='${pageScope.bundle}'  key='sole.proprietorship.from.Taiwan,Hong.Kong.and.Macao' /></option>
								<option value="12"><fmt:message bundle='${pageScope.bundle}'  key='Stock.corporation.from.Taiwan,Hong.Kong.and.Macao' /></option>
								<option value="13"><fmt:message bundle='${pageScope.bundle}'  key='foreign.investment.enterprises' /></option>
								<option value="14"><fmt:message bundle='${pageScope.bundle}'  key='Chinese-foreign.Joint.Ventures' /></option>
								<option value="15"><fmt:message bundle='${pageScope.bundle}'  key='Chinese-foreign.contractual.joint.ventures' /></option>
								<option value="16"><fmt:message bundle='${pageScope.bundle}'  key='Companies.limited.by.shares.with.foreign.investment' /></option>
								<option value="17"><fmt:message bundle='${pageScope.bundle}'  key='other.commercial.services' /></option>
							</select>
							<script type="text/javascript">
								$("#groupType").val($("#groupTypeValue").val());
							</script>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='level.rating.of.corporate.customer' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.custClassType}" id="custClassType"
								name="custClassType">
						</div>
						<c:choose>
							<c:when test="<%=isSuper%>">
								<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Type.of.sales' />：</label>
								<div class="formControls col-3 font12">
									<select id="sellType" name="sellType" class="input-text">
									    <option value="00"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
									    	<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='self-support' /></option>
											<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='channel' /></option>
											<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Channel.developing' /></option>
									</select>
								</div>
							</c:when>
							<c:otherwise>
								<input type="hidden" id="sellType" name="sellType" value="3"/>
							</c:otherwise>
						</c:choose>
					</div>
					<c:choose>
						<c:when test="<%=isSuper%>">
							<div class="col-10 from-control verticalSpacing" id="attrInfo">
								<label class="form-label col-3 font12" id="attributeId"></label>
								<div class="formControls col-3 font12">
									<select id="dev" name="dev" class="input-text" style="display:none;"></select>
								</div>
								<label class="form-label col-3 font12" id="attribute2Id"></label>
								<div class="formControls col-3 font12" id="attr2Div">
									<input type="text" class="input-text" value="" id="devRV" name="devRV" style="display:none;" readonly>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<input type="hidden" id="dev" name="dev" value="<%=currentCustId %>"/>
						</c:otherwise>
					</c:choose>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP' />：</label>
						<div class="formControls col-3 font12">
							<select id="regionCode" name="regionCode" class="input-text">
								<option value="00"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.North' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.South' /></option>
								<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.East' /></option>
							</select>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Nationaltax.No' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" value="${custGroupDto.busiTaxId}" id="busiTaxId" name="busiTaxId" />
						</div>
					</div>

					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='legal.entity.certification.type' />：</label>
						<div class="formControls col-3 font12">
							<%--  <input type="text"  class="input-text" value="${juristicPsptType}"  id="juristicPsptType" name="juristicPsptType" > --%>
							<select id="juristicPsptType" name="juristicPsptType" class="input-text">
								<!-- 营业执照、法人证书、组织机构代码证、介绍信、照会 -->
								<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='business.licence' /></option>
								<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='legal.representative.and.registration.certificate' /></option>
								<option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Organization.code.certificate' /></option>
								<option value="4"><fmt:message bundle='${pageScope.bundle}'  key='letter.of.introduction' /></option>
								<option value="5"><fmt:message bundle='${pageScope.bundle}'  key='diplomatic.note' /></option>
							</select>
							<script type="text/javascript">
								$("#juristicPsptType").val($("#juristicPsptTypeValue").val());
							</script>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='legal.entity.certification.number' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" value="${custGroupDto.juristicPsptId}" id="juristicPsptId" name="juristicPsptId">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Organisation.Code.Certificate' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.orgStructCode}" id="orgStructCode"
								name="orgStructCode">
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Unified.pay.code' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.unifyPayCode}" id="unifyPayCode"
								name="unifyPayCode">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='attribute.of.internation' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.international}" id="international"
								name="international">
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='location.of.an.enterprise' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.provinceCode}" id="provinceCode"
								name="provinceCode">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.EPARCHYCODE' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.eparchyCode}" id="eparchyCode"
								name="eparchyCode">
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.COUNTRY_SEAT' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text"
								value="${custGroupDto.countrySeat}" id="countrySeat"
								name="countrySeat">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='enterprise.address' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" value="${custGroupDto.groupAddr}" id="groupAddr" name="groupAddr">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Company.profile' />：</label>
						<div class="formControls col-4 font12">
							<textarea rows="4" cols="50" name="groupMemo" id="groupMemo">${custGroupDto.groupMemo}</textarea>
						</div>
					</div>
				  </div>
					<div class="row cl" style="margin-bottom: 20px;">
						<c:if test='${viewMode=="add" || viewMode =="edit"}'>
						   <div class="col-8 col-offset-4">
							   <input class="btn btn-primary radius" type="button"
									value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveCust(false);">
								<input class="btn btn-default radius" type="reset"
									value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> 
								<input class="btn btn-primary radius" type="button"
									value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Next.step' />&nbsp;&nbsp;"
									onclick="saveCust(true);">
						  
							    <input class="btn btn-primary radius" type="button"
								    value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
						   </div>
						</c:if>
					</div>
			</form>
		</div>
	</div>
</body>
</html>
