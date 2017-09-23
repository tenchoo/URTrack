<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Equipment.recharge' /></title>
<link href="${ctx}/static/toWeb/css/dateRange.css" rel="stylesheet" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>

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
	
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/jquery.cycle.all.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/dateRange.js"></script>
<script type="text/javascript" src="${ctx}/static/toWeb/js/common.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
</head>
<body>
   <div class="header bg02">
        <div class="centerBox">
            <a href="javascript:;" class="logo"></a>
            <dl class="nav">
                <dt class=""><a href="${ctx}/customerQuery/loginSuccessIndex"><fmt:message bundle='${pageScope.bundle}'  key='home page' /></a></dt>
                <dt class="navPro"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></a></dt>
                <dt class="navFlow current"><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='phone.data.service' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.connection.platform' /></a></dt>
                <dt><a href="${ctx}/customerQuery/toBrandIntroduction"><fmt:message bundle='${pageScope.bundle}'  key='brand.Introduction' /></a></dt>
                <dt><a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></a></dt>
                <dt class="account_1">
                    <span class="photo"><img src="${pageContext.request.contextPath}/static/toWeb/images/photo.png" width="42" height="42" /></span>
                    <span class="acc">${loginName}</span>
                    &nbsp;|&nbsp;
                    <a href="${ctx}/customerQuery/toExit" class="topLink"><fmt:message bundle='${pageScope.bundle}'  key='quit' /></a>
                </dt>
                <dd class="pro hide">
                    <div class="proInner">
                        <ul class="proSlide">
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                            <li>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_1.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> / PRO6S</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_2.png" />
                                    <span class="title">Miix / MX6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_3.png" />
                                    <span class="title">YagoPad / PRO6</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_4.png" />
                                    <span class="title">ablet3 / PRO5</span>
                                </a>
                                <a class="elem" href="javascript:;">
                                    <img src="${pageContext.request.contextPath}/static/toWeb/images/pro_1_5.png" />
                                    <span class="title"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /> / MX5</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <a href="javascript:;" class="arrowL"></a><a href="javascript:;" 
                    class="arrowR"></a>
                </dd>
                <dd class="flow hide">
                	<a class="" href=""><fmt:message bundle='${pageScope.bundle}'  key='Device activation' /></a>
                	<a href="${ctx}/customerQuery/toRateSearch2"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></a>
                	<a href="javascript:;"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a>
                </dd>
            </dl>
        </div>
    </div>
    
    <div class="centerBox">
        
	 <form class="alipay_content" action="${ctx}/customerQuery/toComfirm" method="post" id="chargeForm">
        <div class="chongzhi">
			<div class="title01"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></div>
			<div class="text01"><fmt:message bundle='${pageScope.bundle}'  key='recharge.account' />：<span>${acconutId }</span></div>
            <div class="text02">
            	<div>
					<span class="tit"><fmt:message bundle='${pageScope.bundle}'  key='Device.number' /></span> 
					<select id="iccid" name="iccid" class="input-text" style="width:200px;">
					</select>
				</div>
				<span class="tips">（<fmt:message bundle='${pageScope.bundle}'  key='Tip: please.select.correct.device.number.to.avoid.false.recharge' />）</span>
				<input type="hidden" id="custId" value="${custId }">
				<input type="hidden" id="chargeMsg" value="${chargeMsg }">
				<%-- <input type="hidden" id="payTag" value="${payTag }"> --%>
            </div>
            <p> 
            	<span id="errorMsg" style="padding-left: 3.3rem; padding-top: 0.5rem; margin-bottom: 1rem; font-size: 3.3vw; color: red;"></span>
			</p>
			<div  class="row" id="flows_content"  >
			</div>
			<br>
				<span class="tit"><fmt:message bundle='${pageScope.bundle}'  key='subtotal' />：<font id="showPrice" size="8">0</font>￥</span>
				<div>
					<span class="text01"><fmt:message bundle='${pageScope.bundle}'  key='Select.payment.methods' />：</span>
					<input class="text01" type="radio" name="payType" value="1" checked="checked"/> <fmt:message bundle='${pageScope.bundle}'  key='paying.through.Wechat' />
					<input class="text01" type="radio" name="payType" value="2" /> <fmt:message bundle='${pageScope.bundle}'  key='paying.through.Alipay' />
				</div>
			</div>
				<input type="hidden" name="payName" value="alipay_directPay">
	            <input id="alipay_payAmount" type="hidden" name="payAmount" >
	            <input id="alipay_flowSize" type="hidden" name="flowSize" >
	            <input id="alipay_goodsName" type="hidden" name="goodsName">
				<input id="alipay_goodsId" type="hidden" name="goodsId">
				<input id="alipay_goodsReleaseId" type="hidden" name="goodsReleaseId">
				
    </form>
            <div class="btnBox">
            	<button style="margin-left: 1000px" id="doRecharge" type="button" class="btn btn-primary radius" >立即充值</button>
            </div>
            <br>
            <div class="tipsPanel" style="margin:0;">
            	<div class="tipsBox">
                	说明：<br />
                    1. <fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.recharge.ICCID.carefully.to.avoid.false.recharge'/>；<br />
                    2. <fmt:message bundle='${pageScope.bundle}'  key='if.you.order.a.1G.in.the.use.of.30.days.data.plan' />；
                </div>
            </div>
            
        </div>
    </div>

	<div class="footer">
    	<div class="logoBox">
        	<div class="footLogo">
        	</div>
            <div class="share">
            	<a href="javascript:;" class="sina"><fmt:message bundle='${pageScope.bundle}'  key='click.for.attention' /></a>
                <span class="qrcode01"><fmt:message bundle='${pageScope.bundle}'  key='WeChat.Official.Accounts' /></span>
                <!--<span class="qrcode02">APP下载</span>-->
                <br>
            </div>
        </div>
        <div class="copyright">©2017 Lenovo Connect all right reserved</div>
    </div>
		<script type="text/javascript">
			function selectOne(payAmount, goodsDesc, goodsId, goodsReleaseId) {
				$("#alipay_payAmount").val(payAmount);
				$("#alipay_goodsName").val(goodsDesc);
				$("#alipay_goodsId").val(goodsId);
				$("#alipay_goodsReleaseId").val(goodsReleaseId);
				$("#showPrice").text(payAmount);
				//清空h5数据
				$("#alipay_flowSize").val("");
			};
			
			$("#doRecharge").click(function() {
				var iccid = $("#iccid").val();
			    if(iccid == -1 || iccid == null || iccid==""){
				   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.device.numbers' />")
				   return false;
			    }
			    var checked = $("#alipay_payAmount").val();
				   if(checked == ''){
					  $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='please.select.data.plan' />")
					  return false;
				   }
			
				if (window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Please.confirm.whether.to.recharge.or.not' />？")) {
				
					var payType = $('input:radio[name="payType"]:checked').val();
					if (payType== 2){
						$('#chargeForm').attr('action', "${ctx}/customerQuery/goodsorder");
						$("#chargeForm").submit();
						/* $("#chargeForm").ajaxSubmit(option); */
					} else {
					    $('#chargeForm').attr('action', "${ctx}/customerQuery/goodsorder");
						/* $('#chargeForm').attr('action', "${ctx}/customerQuery/toComfirm"); */
						$("#chargeForm").submit();
					}
					
				}
			});
		</script>
		<script type="text/javascript">
					if ($("#chargeMsg").val()!="" && $("#chargeMsg").val()!=null) {
						alert($("#chargeMsg").val());
					} 
					$.ajax({
						url:"${ctx}/customerQuery/lookRecharge",
						data: {"custId":$("#custId").val()},
						success:function(result){
							var select=$("#iccid").select2({
								width : 250,  
								placeholder: '<fmt:message bundle='${pageScope.bundle}'  key='Device.number' />iccid',
								tags: "true",
								allowClear: true,
								data:result
							});
							$("#iccid").change(function() {
								$("#errorMsg").html("");
								var iccid = $("#iccid").val();
								if (iccid != '' && iccid != null && iccid.length > 18) {
									$.ajax({
										url : "${ctx}/laouser/showGoodsRealease",
										data : {"iccid2" : iccid},
										success : function(data) {
											var list = data;
							   				$("#flows_content").html("");
							   				if(list == '' || list == null || list.indexOf("<htm") > -1){
							   					   $("#errorMsg").html("<fmt:message bundle='${pageScope.bundle}'  key='The.device.did.not.bind.the.product.in.advance' />")
							   					   return;
							   				}
							   				for ( var i = 0; i < list.length; i++) {
												$("#flows_content").append(
													"<div class='col-xs-6 '><a href=\"javascript:selectOne("
													+ list[i].goodsPrices
													+ ", '"
													+ list[i].goodsName
													+ "', "
													+ list[i].goodsId
													+ ", "
													+ list[i].goodsReleaseId
													+ ");\" class='' goodDesc='"
													+ list[i].goodsDesc
													+ "' payAmount='"
													+ list[i].goodsPrices
													+ "' > <img src='"+list[i].goodsPic+"'></a><p>"
													+ list[i].goodsName
													+ " "
													+ list[i].operatorsName
													+ " "
													+ list[i].goodsPrices
													+ "</p></div>");
											}
										},
										error : function() {
											alert("<fmt:message bundle='${pageScope.bundle}'  key='Query.product.failed' />!");
										}
									});
								}
							})
						}
					});
		</script>
	</body>
</html>