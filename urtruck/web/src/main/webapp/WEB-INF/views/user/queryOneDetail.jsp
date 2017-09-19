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
	<title></title>
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
	
	
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
	<script type="text/javascript">
		function generateUserInfor(){
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.create.user.information' />？")){
				$.ajax({
					url:"${ctx}/userNewService/doUserInfor",
					data:{
						"batchId":$("#batchId").val()
						},
					success:function(result){
						if(result.msg == "OK"){
							alert("<fmt:message bundle='${pageScope.bundle}'  key='The.action.that.generate.user.information.have.been.implemented' />！");
						} else {
							alert("<fmt:message bundle='${pageScope.bundle}'  key='Failed.to.create.user.information' />！");
						}
					}
				});
			}
		}
	</script> 
</head>
<body>
	<div class="pd-20">
	<h4 align="center"><fmt:message bundle='${pageScope.bundle}'  key='Input.all.information.in.batch' /></h4>
	<input id="batchId" type="hidden" value="${batchId}"/>
	<div class="mt-20">
	<table id="example" style= "font: 20px" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer' />：<span >${map.CUST_NAME }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' />：<span >${map.VAL1 }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />：<span >${map.VAL2 }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Operator' />：<span >${map.OPERATORS_NAME }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Card.status' />：<span >${map.SERVICE_NAME }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='The.1.logo' />：<span >${dto.rsrvInfo1 }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='The.2.logo' />：<span >${dto.rsrvInfo2 }</span></td>
			<%-- <td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Whether.to.pay.cost' />：
				<span >
					<c:choose>
					    <c:when test="${dto.payment == 0}">
					      	 <fmt:message bundle='${pageScope.bundle}'  key='YES' />
					    </c:when>
					    <c:when test="${dto.payment == 1}">
					    	<fmt:message bundle='${pageScope.bundle}'  key='NO' />
					    </c:when>
					</c:choose>
				</span>
			</td> --%>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />：<span >${map.GOODS_NAME }</span></td>
		</tr>
		<tr>
			<%-- <td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' />：
				<span >
					<c:choose>
					    <c:when test="${dto.generateinfor == 0}">
					      	 <fmt:message bundle='${pageScope.bundle}'  key='YES' />
					    </c:when>
					    <c:when test="${dto.generateinfor == 1}">
					    	<fmt:message bundle='${pageScope.bundle}'  key='NO' />
					    </c:when>
					</c:choose>
				</span>
			</td> --%>
			
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.cycle' />：<span >${map.RELEASE_CYCLE }</span><span>
			<c:choose>
				    <c:when test="${map.UNIT == 0}">
				      	 <fmt:message bundle='${pageScope.bundle}'  key='month' />
				    </c:when>
			</c:choose>
			</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.price' />：<span >${map.RELEASE_PRICE }</span><span>元</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Order.numbers' />：<span >${dto.sumNum }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='order.amount' />：<span >${dto.orderFee }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.consignee' />：<span >${dto.enterpriseConsignee }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Reciever.Phonenumber' />：<span >${dto.consigneePhone }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Reciever.ID.card' />：<span >${dto.consigneeIdcard }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Shipping.Address' />：<span >${dto.deliveryAddress }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='order.date' />：<span >${dto.orderDate }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Other.note.message' />：<span >${dto.remark }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Card.type' />：<span >${dto.simType }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Card.size' />：<span >${dto.simSize }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='card.cost' />：<span>${dto.simFee }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='start' />ICCID：<span>${dto.iccidStart }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='end' />ICCID：<span>${dto.iccidEnd }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Assign.starting.number' />：<span >${dto.numberStart }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Assign.end.number' />：<span >${dto.numberEnd }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Delivery.date' />：<span ><fmt:formatDate value='${dto.deliveryDate}' pattern='yyyy-MM-dd'/></span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='consignor' />：<span >${dto.consignor }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='logistics.company' />：<span >${dto.logisticsCompany }</span></td>
		</tr>
		<tr>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='logistics.number' />：<span >${dto.logisticsNumber }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='receiver' />：<span >${dto.pullPerson }</span></td>
			<td class="font12"><fmt:message bundle='${pageScope.bundle}'  key='receiver.Phone.number' />：<span>${dto.pullNumber }</span></td>
		
		</tr>
	</table>
	<br>
	<input type="hidden" id="batchId" name="batchId" value="${batchId }">
		<div align="center">
			<button id="search" type="button" class="btn btn-primary radius" onclick="generateUserInfor();">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Generate.user.information' />
			</button>
		</div>
	</div>
</div>
</body>
</html>
 