<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/lib/pager-taglib.jar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Individual.order' /></title>
<base href="<%=basePath%>" />
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
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

</head>
<body>
		<div class="seconSec">
		<h1><fmt:message bundle='${pageScope.bundle}'  key='Individual.product.order.confirmation' /></h1>
	<div class="pd-20 "  >
			<div class="container">
					<div class="row">
						<label class="fl labelWidth">
								<span class="colorRed smallStar">*</span>
								<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Order.information' />:</span>
						</label>
						<div class="formControls zpInput font12" style="line-height:34px;">
							<span>iccid:</span><span>${iccid}</span>
				      	</div>
					</div>
			</div>
			
	      	
			<p></p>
<%-- 			<div class='container' style="margin:20px 0;">
				<div class='row'>
					<div class='col-sm-8' style='color: red;'>
						<table id='table1' border='1'class="table table-border table-bordered">
						<tr>
							<td rowspan='2' align='center' style="text-align:center;"><img alt='路径错误' src="${goodsPic }"></td>
							<td align='center'><input type='hidden' name='goodsId' value="${goodsId }">${goodsName}</td>
						</tr>
						<tr>
							<td align='center'>${fee}元</td>
						</tr>
						<tr>
							<td colspan='2'>备注:</td></tr></table>
					</div>
				</div>
			</div> --%>
				
			<div class='container-fluid'>
					<div class='row'>
						<div class='col-sm-3 col-md-3 col-lg-3 plrNone' style='color: red;' onclick='priceChange("+list[i].goodsId+")'>
							<table id='table1' border='1' class='table table-border table-bordered' style="margin-top:10px;">
								<tr>
									<td class="prdList" style="text-align:center;border:none;border-bottom: 1px solid #ddd;">
										<img alt='<fmt:message bundle='${pageScope.bundle}'  key='Path.error' />' src="${goodsPic }" width="159" height="105">
										<p class="text-center font12"><input type='hidden' name='goodsId' value="${goodsId }">${goodsName}&nbsp;&nbsp;&nbsp;&nbsp;${fee}<fmt:message bundle='${pageScope.bundle}'  key='yuan' /></p>
										<p class="text-center font12"></p>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div> 
			
			
			
			<div>
			
			<div class="oh mt-10">
				<div class="fl font12" style="margin-top:13px;"><fmt:message bundle='${pageScope.bundle}'  key='Payment.method' /></div>
				<div class="fl">
					<label style="width:auto;margin-left:10px;"> 
						<input type="radio" name="optionsRadios" id="optionsRadios3" value="option3" checked="checked" style="margin-top:13.5px;">
					</label>
				</div>
				<div style="height:40px;line-height:40px;" class="col-3">
					<span class="fl" style="display:inline-block;height:20px;margin:10px 5px 0 5px;">
						<img src="static/h5/images/pay.png" style="height:100%" alt="<fmt:message bundle='${pageScope.bundle}'  key='Path.error' />">
					</span>
					<span class="font12 fl" style="display:inline-block;line-height:40px;"><fmt:message bundle='${pageScope.bundle}'  key='paying.through.Alipay' /></span>
				</div>
				
			</div>
			
			
			<div>
				<div class="row">
					<div style='margin:20px 0 20px 15px;font-weight:600;font-size:12px;'>小计<fmt:message bundle='${pageScope.bundle}'  key='' />：<span style="color:#f00;font-size:14px;font-weight:bold;">${fee}</span><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></div>
					<form action="laouser/torealPay" method="post" id="chargeForm">
						<input type="hidden" name="payName" value="alipay_directPay">
			            <input  type="hidden" name="fee" value="${fee}">
			            <input  type="hidden" name="iccid" value="${iccid}">
			            <input  type="hidden" name="tradeId" value="${tradeId}">
			            <input  type="hidden" name="goodsId" value="${goodsId}"/>
					</form>	
					<div class="col-sm-9">
						<a href="javascript:void(0);"  onclick="$('#chargeForm').submit()" ><span class="btn btn-primary radius"><fmt:message bundle='${pageScope.bundle}'  key='Immediate.payment' /></span></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>