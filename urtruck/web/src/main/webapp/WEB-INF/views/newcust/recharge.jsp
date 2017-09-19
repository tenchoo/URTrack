<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_cn" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>财务总览</title>
<%-- <link href="${ctx}/static/ui/css/base.css" rel="stylesheet"> --%>

<link rel="stylesheet"
	href="${ctx}/static/newcust/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/newcust/css/bootstrap.min.css" />
<link rel="stylesheet" href="${ctx}/static/newcust/css/index.css" />
<link rel="stylesheet" href="${ctx}/static/newcust/css/pagination.css" />
<link rel="stylesheet" href="${ctx}/static/newcust/css/reset.css" />
<script type="text/javascript"
	src="${ctx}/static/newcust/js/jquery.pagination.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/newcust/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/newcust/js/echarts.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/newcust/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/newcust/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
	src="${ctx}/static/newcust/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
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

</head>
<body>
	<div class="container">
		<div class="row head">
			<h2>财务总览</h2>
		</div>
		<div class="row title">
			<div class="cl">
				<div class="blue_div fl"></div>
				<h2 class="fl">账户信息</h2>
			</div>

			<p>
				账本余额：￥<span>${surplusMoney}</span> <a class="ml10 mr10" href="javascript:;"
					onclick="recharge('充值','${ctx}/customer/recharge','600','200')">充值</a>

				<a href="">提现</a>
			</p>
		</div>
		<div class="row consumer_information">
			<div class="cl">
				<div class="blue_div fl"></div>
				<h2 class="fl">消费信息</h2>
			</div>
			<ul class="cl">
				<li class="fl mr10"><img src="${ctx}/static/newcust/img/tu.png"
					alt="" />
					<div class="fl box">
						<p>
							￥<span>${currentMonthSubmitMoney}</span>
						</p>
						<p>
							￥<span>本月充值</span>
						</p>
					</div></li>
				<li class="fl"><img src="${ctx}/static/newcust/img/tu.png"
					alt="" />
					<div class="fl box">
						<p>
							￥<span>${currentMonthReturnMoney}</span>
						</p>
						<p>
							￥<span>本月返现</span>
						</p>
					</div></li>
			</ul>
		</div>
	</div>

</body>
<script type="text/javascript">
	//充值函数
	function recharge(title, url, w, h) {
		layer_show(title, url, w, h);
	}
</script>

</html>