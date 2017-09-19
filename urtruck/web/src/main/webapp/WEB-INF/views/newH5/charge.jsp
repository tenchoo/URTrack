<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.recharge' /></title>
	<base href="<%=basePath%>" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="static/newH5/css/style.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	<script type="text/javascript" src="static/newH5/js/jquery.js"></script>
	<script type="text/javascript" src="static/newH5/js/bootstrap.js"></script>
	<script type="text/javascript" src="static/newH5/js/main.js"></script>
	<script type="text/javascript" src="static/newH5/js/charge.js"></script>
	<script type="text/javascript" src="static/newH5/js/search_data.js"></script>
	<script type="text/javascript" src="static/h5/js/layer/layer.js"></script>
</head>
<body>
<div class="wrapper">
	<div class="container-fluid">
		<jsp:include page="header.jsp" flush="true"/>
		
		<form action="glaH5AppPay/toComfirm" method="post" id="cform">
		<div class="search row">
			<%-- <label class="pull-left">用户设备</label>
			<input type="tel" class="col-xs-9" placeholder="请输入设备号" id="iccid" value="${ssiccid}">
			<span><img src="static/newH5/images/search.png" class="img-responsive" onclick="search()"></span> --%>
			
			<label class="col-xs-3"><fmt:message bundle='${pageScope.bundle}'  key='User.equipment' /></label>
			<input type="tel" class="col-xs-9" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.device.number' />" id="iccid" value="${ssiccid}">
			<button class="searchBtn" type="button" onclick="search()"><fmt:message bundle='${pageScope.bundle}'  key='Determine' /></button>
			
			<input type="text" name="iccid" hidden value="">
			<input type="text" name="goodsId" hidden value="">
			<input type="text" name="goodsReleaseId" hidden value="">
			<div class="data_list" id="append">
				<ul>
					<c:if test="${not empty glaIccidList}">
						<c:forEach items="${glaIccidList}" var="item">
							<li data-value="${item}">•••${fn:substring(item,11,19)}</li>
						</c:forEach>
					</c:if>
				</ul>
			</div>
		</div>
				
		<div class="cz">
			<ul id="flows_content">
				<c:if test="${not empty goodsList}">
					<c:forEach items="${goodsList}"  begin="0" step="2" var="good"  varStatus="status">
						<li class="row">
							<c:if test="${good.discount == false}">
								<div class="col-xs-6_d pull-left ">
									<input type='text'  hidden  value='${good.goodsId }'> 
									<input type='text' hidden  value='${good.goodsReleaseId}'>	
									<h3>${good.goodsName}</h3>
									<p><span>¥<b>${good.goodsPrices }</b><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span></p><!-- <del>¥<b>45</b> 元 </del> -->
									<p class="inow_data"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${good.releaseCycle}${good.unit}</p>
								</div>
							</c:if>
							<c:if test="${good.discount == true}">
								<div class="col-xs-6_d pull-left z_k">
									<input type='text'  hidden  value='${good.goodsId }'> 
									<input type='text' hidden  value='${good.goodsReleaseId}'>	
									<h3>${good.goodsName}</h3>
									<p><span>¥<b>${good.goodsPrices }</b><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span></p><!-- <del>¥<b>45</b> 元 </del> -->
									<p class="inow_data"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${good.releaseCycle}${good.unit}</p>
									<span class="zk">惠</span>
								</div>
							</c:if>
							<c:if test="${!empty goodsList[status.index+1]}">
								<c:if test="${goodsList[status.index+1].discount == false}">
									<div class="col-xs-6_d pull-right">
										<input type='text'  hidden  value='${goodsList[status.index+1].goodsId }'> 
										<input type='text' hidden  value='${goodsList[status.index+1].goodsReleaseId}'>	
										<h3>${goodsList[status.index+1].goodsName}</h3>
										<p><span>¥<b>${goodsList[status.index+1].goodsPrices }</b><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span></p>
										<p class="inow_data"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${goodsList[status.index+1].releaseCycle}${goodsList[status.index+1].unit}</p>
									</div>
								</c:if>
								<c:if test="${goodsList[status.index+1].discount == true}">
									<div class="col-xs-6_d pull-right z_k">
										<input type='text'  hidden  value='${goodsList[status.index+1].goodsId }'> 
										<input type='text' hidden  value='${goodsList[status.index+1].goodsReleaseId}'>	
										<h3>${goodsList[status.index+1].goodsName}</h3>
										<p><span>¥<b>${goodsList[status.index+1].goodsPrices }</b><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span></p>
										<p class="inow_data"><fmt:message bundle='${pageScope.bundle}'  key='expiry.date' />${goodsList[status.index+1].releaseCycle}${goodsList[status.index+1].unit}</p>
										<span class="zk">惠</span>
									</div>
								</c:if>
							</c:if>
						</li>
					</c:forEach>
				</c:if>
			</ul>
			<div class="infoMessage" style="margin-top: 25px;">
				<p><fmt:message bundle='${pageScope.bundle}'  key='Reminder' />：</p>
				<p>（1）<fmt:message bundle='${pageScope.bundle}'  key='After.signed.in,you.can.inquiry.data.and.order.customized.products' /></p>
				<p>（2）<fmt:message bundle='${pageScope.bundle}'  key='Check.the.ICCID.please.The.wrong.device.will.not.be.refunded' /></p>
			</div>
			
		</div>
		</form>
		
		<div class="nav navbar-fixed-bottom" style="border-bottom:none;">
			<div class="wrap_btn">
				<button class="login_btn" disabled="disabled" onclick="sumbimt()"><fmt:message bundle='${pageScope.bundle}'  key='Sure.Recharge' /></button>
			</div>
		</div>
	</div>
</div>	
	<script type="text/javascript">
		function search(){
			var $dataList = $('.data_list');
			$dataList.hide();
			var $search = $('.search input[type="tel"]');
			$search = $search.val().replace("•••","");
			var parent = /\d{8}$/ //
			if($search != null && $search != ''){
				change();
			}else{
				layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.device.number' />！");
			}
		}
		
		//onchange 事件
		function change(){
			var iccid = $("#iccid").val();
			if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
			console.log("iccid:",iccid);
			if (iccid != '' && iccid != null) {
				$.ajax({
		   			url:"glaH5AppPay/queryIccid",
		   			data:{
		   				"iccid":iccid,			
		   			},
		   			success:function(result){
						$("#flows_content").html("");
		   				if(result != null && result != ''){
		   					console.log("iccid:",result);
		   					$("input[name='iccid']").val(result);
		   					$('#iccid').val(result);
							keyup(result)
						}else{
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='The.device.number.is.not.activated' />！");
						}
		   			},
				   error:function(){
					   layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Query.failed' />！");
					}
		   		});
			}else{
				$("#flows_content").html("");
				layer.alert("ICCID <fmt:message bundle='${pageScope.bundle}'  key='Input.error' />！");
			}
		};
		
<%--		$(function() {
			<c:if test="${not empty ssiccid}">
				search();
			</c:if>
		});		--%>

		$(function() {
			<c:if test="${not empty ssiccid}">
				$("input[name='iccid']").val($("#iccid").val());
			</c:if>
		});	
		
		window.onload = function(){
			$('.cz').click(function(ev){
			   var $finDiv = $('.cz ul')
			    $finDiv.find('div').bind('touchstart', function(){
					$finDiv.find('div').removeClass('active_k');
					$(this).toggleClass('active_k').siblings();
					if(color != null){
						$finDiv.find('div').css({'color' : '#666'});
						$finDiv.find('div').css({'border' : '2px solid #666'});
					}
					if($("#iccid").val() != ''){
						$(".login_btn").addClass('active_login');
						$(".login_btn").removeAttr('disabled');
						if(color != null){
	                        $('.active_login').css({'background' : '#' + color});
						}
					}
					if(color != null){
						$(this).css({'color' : '#' + color});
						$(this).css({'border' : '2px solid #' + color});
                    }
					
					$("input[name='goodsId']").val($(this).find('input:first').val());
					$("input[name='goodsReleaseId']").val($(this).find('input:last').val());
					
					console.log("goodsReleaseId:",$("input[name='goodsReleaseId']").val());
				});
			});
		}
		
		function sumbimt(){
			var ss =$("input[name='iccid']").val();
			if(ss.indexOf('898602') > -1 || ss.indexOf('898603') > -1 ){
					layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='China.Mobile.and.China.telecom.card.are.not.supported.for.the.order' />");
			}else{
				var iccid = $("#iccid").val();
				if(iccid.indexOf("•••") > -1) iccid = iccid.replace("•••","");
				if(iccid != '' && $("input[name='iccid']").val()!= ''){
					if(iccid== ss.substring (12) || iccid== ss.substring (11,19) || iccid == ss || iccid == ss.substring (0,19)){
						$('#cform').submit();
					}else{
						layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.confirm.the.input.device.number' />");
					}
				}else{
					layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.input.device.number.and.order.after.product' />");
				}
			}
		}
		
		
	</script>
</body>
</html>