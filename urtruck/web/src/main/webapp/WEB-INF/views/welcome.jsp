<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<style type="text/css">
	html,body{
		height:auto;
	}
</style>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title><fmt:message bundle='${pageScope.bundle}'  key='Welcome' />！</title>
</head>
<body class="greyBg">
<div class="part2">
     <!--inCnt start-->
     <div class="contenFive">
        <!--contenFive start-->
        <div class="clearfloat maindl">
           <dl class="one" onClick="location.href='${ctx}/goods/goodsList';">
              <dt><img src="${ctx}/static/ui/images/sevenone.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Goods.Center' /></dd>
           </dl>
           <dl class="two" onClick="location.href='${ctx}/cust/conpanyList';">
              <dt><img src="${ctx}/static/ui/images/seventwo.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Customer.Center' /></dd>
           </dl>
           <dl class="three" onClick="location.href='${ctx}/laouser/queryLaoUser';">
              <dt><img src="${ctx}/static/ui/images/fivethree.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Order.Center' /></dd>
           </dl>
           <dl class="four" onClick="location.href='${ctx}/ssUser/toList';">
              <dt><img src="${ctx}/static/ui/images/fivefour.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Manage.Center' /></dd>
           </dl>
           <dl class="five" onClick="location.href='${ctx}/userService/uploadExcel';">
              <dt><img src="${ctx}/static/ui/images/fivefive.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Sim.Card.Center' /></dd>
           </dl>
           <dl class="six" onClick="location.href='#';">
              <dt><img src="${ctx}/static/ui/images/fivesix.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Terminal.Center' /></dd>
           </dl>
           <dl class="seven" onClick="location.href='#';">
              <dt><img src="${ctx}/static/ui/images/fiveseven.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='IDS.Center' /></dd>
           </dl>
           <dl class="eight" onClick="location.href='${ctx}/batchFtpImport/batchview';">
              <dt><img src="${ctx}/static/ui/images/fiveeight.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Accounts.Center' /></dd>
           </dl>
           <dl class="nine" onClick="location.href='#';">
              <dt><img src="${ctx}/static/ui/images/fivenine.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Billing.Center' /></dd>
           </dl>
           <dl class="ten" onClick="location.href='#';">
              <dt><img src="${ctx}/static/ui/images/fiveten.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Close.Account.Center' /></dd>
           </dl>
           <dl class="elaven" onClick="location.href='${ctx}/remain/accountManage';">
              <dt><img src="${ctx}/static/ui/images/fiveelaven.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Remaining.Sum.Center' /></dd>
           </dl>
           <dl class="twelve" onClick="location.href='#';">
              <dt><img src="${ctx}/static/ui/images/fivetwelve.png" class="img-responsive"></dt>
              <dd><fmt:message bundle='${pageScope.bundle}'  key='Location.Center' /></dd>
           </dl>

        </div>
        <!--contenFive end-->
     </div>
        
     <!--inCnt end-->
     <div class="footerBottom" id="footer">
		<h1 class="text-center">Copyright <fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' /> by GLA v1.0</h1>
	</div>
  </div>
<%-- <div class="pd-20" style="padding-top:20px;">
  <img src="${ctx}/static/images/welcome.png" height="550" width="1267"/>
</div>
<footer class="footer">
  <p>感谢LAOAPI项目<br>Copyright &copy;All Rights Reserved.<br>
    本后台系统由<a href="http://www.h-ui.net/" target="_blank" title=""></a>提供技术支持</p>
</footer> --%>
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F080836300300be57b7f34f4b3e97d911' type='text/javascript'%3E%3C/script%3E"));
</script>
</body>
</html>
