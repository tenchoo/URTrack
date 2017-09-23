<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
	<form action="${pcwebPayRequestUrl}" name='pay' method="post">

		<c:forEach items="${params}" var="entry">
			<input type="hidden" name="<c:out value="${entry.key}" />" value="<c:out value="${entry.value}"/>">
		</c:forEach>
	</form>
	<script type="text/javascript">
	function onBridgeReady(){ 
		   WeixinJSBridge.invoke( 
	        'getBrandWCPayRequest', { 
	          "appId" : '${appId}',   //公众号名称，由商户传入    
	          "timeStamp": '${timeStamp}',     //时间戳，自1970年以来的秒数    
	          "nonceStr" : '${nonceStr}', //随机串    
	          "package" : '${prepay_id}',    
	          "signType" : '${signType}',     //微信签名方式:    
	          "paySign" : '${paySign}', //微信签名  
	        }, 
	        
	        function(res){ 
	        	//alert(res.err_code+"|||"+res.err_desc+"|||"+res.err_msg); 
	            if(res.err_msg == "get_brand_wcpay_request:ok" ){ 
	           		window.location.href="<%=basePath%>glaH5AppPay/payShow?payAmount="+'${payAmount}'+"&iccid="+'${iccid}'+"&goodsName="+'${goodsName}'+"&tradeId="+'${tradeId}'+"&goodReleaseId="+'${goodReleaseId}';
	          	}else if(res.err_msg = "get_brand_wcpay_request：cancel"){
	          		alert("<fmt:message bundle='${pageScope.bundle}'  key='User.cancelled.during.payment.process' />");
	          	}else if(res.err_msg = "get_brand_wcpay_request：fail"){
	          		alert("<fmt:message bundle='${pageScope.bundle}'  key='Payment.failed' />");
	          	}
	        } 
	      );  
	    } 
	    function pay(){ 
	    	if (typeof WeixinJSBridge == "undefined"){
	    		   if( document.addEventListener ){
	    		       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	    		   }else if (document.attachEvent){
	    		       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	    		       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	    		   }
	    		}else{
	    		   onBridgeReady();
	    		}
	    }
	    if('${payWay}' == "1"){ 
	    	//alert("appId:"+'${appId}'+"nonceStr"+'${nonceStr}'+"prepay_id"+'${prepay_id}'+"paySign"+'${paySign}')
	    	pay();
	    }else{ 
	    	document.pay.submit();
	    }
	    
 		
	</script>
</body>
</html>