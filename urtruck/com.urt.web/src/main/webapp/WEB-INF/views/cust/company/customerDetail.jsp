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
	// 返回主页
	function closeLayer() {
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
			     <input type="hidden" id="parentCustName" value="${parentCustDto.custName}" />
			    
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='customer.information' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
						<div class="formControls col-3 font12">
							<label>${customerDto.custName}</label>	
						</div>
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Customer.state' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${customerDto.custState == "0"}'>
								<label><fmt:message bundle='${pageScope.bundle}' key='potential.customer' /></label>
							</c:if>
							<c:if test='${customerDto.custState == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}' key='customer.in.use.service' /></label>
							</c:if>
							<c:if test='${customerDto.custState == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}' key='account.closed' /></label>
							</c:if>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Document.type' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${customerDto.psptTypeCode == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='business.licence' /></label>
							</c:if>
							<c:if test='${customerDto.psptTypeCode == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='legal.representative.and.registration.certificate' /></label>
							</c:if>
							<c:if test='${customerDto.psptTypeCode == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Organization.code.certificate' /></label>
							</c:if>
							<c:if test='${customerDto.psptTypeCode == "4"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='letter.of.introduction' /></label>
							</c:if>
							<c:if test='${customerDto.psptTypeCode == "5"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='diplomatic.note' /></label>
							</c:if>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Document.number' />：</label>
						<div class="formControls col-3 font12">
							<label>${customerDto.psptId}</label>
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
							<label>${custGroupDto.busiLicenceNo}</label>
						</div>
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='business.license.types' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${custGroupDto.busiLicenceType == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Co.Ltd' /></label>
							</c:if>
							<c:if test='${custGroupDto.busiLicenceType == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Company.limited.by.shares' /></label>
							</c:if>
							<c:if test='${custGroupDto.busiLicenceType == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='individual.proprietorship.enterprise' /></label>
							</c:if>
							<c:if test='${custGroupDto.busiLicenceType == "4"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='partnership.business' /></label>
							</c:if>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='The.validity.of.the.business.license' />：</label>
						<div class="formControls col-3 font12">
							<label>${busiLicenceValidDateStr}</label>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='type.of.enterprise' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${custGroupDto.groupType == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='domestic.enterprise' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='state-owned.enterprise' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='collectively-owned.enterprise' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "4"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='joint-equity.cooperative.enterprises' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "5"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='joint.venture' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "6"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Co.Ltd' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "7"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Company.limited.by.shares' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "8"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='investing.enterprise.Company.Ltd' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "9"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Joint.venture.enterprises' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "10"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='cooperative.enterprise' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "11"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='sole.proprietorship.from.Taiwan,Hong.Kong.and.Macao' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "12"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Stock.corporation.from.Taiwan,Hong.Kong.and.Macao' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "13"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='foreign.investment.enterprises' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "14"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Chinese-foreign.Joint.Ventures' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "15"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Chinese-foreign.contractual.joint.ventures' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "16"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Companies.limited.by.shares.with.foreign.investment' /></label>
							</c:if>
							<c:if test='${custGroupDto.groupType == "17"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='other.commercial.services' /></label>
							</c:if>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='level.rating.of.corporate.customer' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.custClassType}</label>
						</div>
						
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Type.of.sales' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${custGroupDto.sellType == "0"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='self-support' /></label>
							</c:if>
							<c:if test='${custGroupDto.sellType == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='channel' /></label>
							</c:if>
							<c:if test='${custGroupDto.sellType == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Channel.developing' /></label>
							</c:if>
							<c:if test='${custGroupDto.sellType == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='USER.GROUP.CUSTOMER' /></label>
							</c:if>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing" id="attrInfo">
						<c:if test='${custGroupDto.sellType == "0" || custGroupDto.sellType == "1" || custGroupDto.sellType == "2" }'>
							<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='developing.channel' />：</label>
						</c:if>
						<c:if test='${custGroupDto.sellType == "3"}'>
							<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_CUST' />：</label>
						</c:if>
						<div class="formControls col-3 font12">
							<label id="dev" name="dev">${parentCustDto.custName}</label>
						</div>
						<c:if test='${custGroupDto.sellType == "0"}'>
							<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='self-operated.developer' />：</label>
						</c:if>
						<c:if test='${custGroupDto.sellType == "1"}'>
							<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Channel.developer' />：</label>
						</c:if>
						<c:if test='${custGroupDto.sellType == "2" || custGroupDto.sellType== "3"}'>
							<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='developing.person' />：</label>
						</c:if>
						<div class="formControls col-3 font12" id="attr2Div">
							<label id="devRV" name="devRV">${customerDto.rsrvStr1}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${custGroupDto.regionCode == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.North' /></label>
							</c:if>
							<c:if test='${custGroupDto.regionCode == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.South' /></label>
							</c:if>
							<c:if test='${custGroupDto.regionCode == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='CUST.OWN_GROUP.East' /></label>
							</c:if>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Nationaltax.No' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.busiTaxId}</label>
						</div>
					</div>

					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='legal.entity.certification.type' />：</label>
						<div class="formControls col-3 font12">
							<c:if test='${custGroupDto.juristicPsptType == "1"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='business.licence' /></label>
							</c:if>
							<c:if test='${custGroupDto.juristicPsptType == "2"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='legal.representative.and.registration.certificate' /></label>
							</c:if>
							<c:if test='${custGroupDto.juristicPsptType == "3"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='Organization.code.certificate' /></label>
							</c:if>
							<c:if test='${custGroupDto.juristicPsptType == "4"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='letter.of.introduction' /></label>
							</c:if>
							<c:if test='${custGroupDto.juristicPsptType == "5"}'>
								<label><fmt:message bundle='${pageScope.bundle}'  key='diplomatic.note' /></label>
							</c:if>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='legal.entity.certification.number' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.juristicPsptId}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Organisation.Code.Certificate' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.orgStructCode}</label>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Unified.pay.code' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.unifyPayCode}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='attribute.of.internation' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.international}</label>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='location.of.an.enterprise' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.provinceCode}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.EPARCHYCODE' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.eparchyCode}</label>
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='CUST.COUNTRY_SEAT' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.countrySeat}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='enterprise.address' />：</label>
						<div class="formControls col-3 font12">
							<label>${custGroupDto.groupAddr}</label>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Company.profile' />：</label>
						<div class="formControls col-4 font12">
							<label>${custGroupDto.groupMemo}</label>
						</div>
					</div>
				  </div>
					<div class="row cl" style="margin-bottom: 20px;">
						<div class="col-8 col-offset-6">
						    <input class="btn btn-primary radius" type="button"
							    value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					   </div>
					</div>
			</form>
		</div>
	</div>
</body>
</html>