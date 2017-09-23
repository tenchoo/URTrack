<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Batch.addresses' /></title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<style type="text/css">
		body, html,#l-map{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
</head>
<body>
	<div id="l-map"></div>
	<input type="hidden" id="addrStr" value="${addrStr}"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yFEDv8FUDXLYM2lwQVGlLRvSu6Rti1i5"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript">

	// 百度地图API功能
	var pointStr=$("#positionId").val();////116.399,39.910
	var addrStr = $("#addrStr").val();
	if ("operatorsError" == addrStr) {
		alert("ICCID<fmt:message bundle='${pageScope.bundle}'  key='user.s.Operator.is.wrong' />！");
	} else if ("lbsError" == addrStr) {
		alert("调用接口异常<fmt:message bundle='${pageScope.bundle}'  key='Call.interface.exception' />！");
	} else if ("iccidError" == addrStr) {
		alert("ICCID不存在<fmt:message bundle='${pageScope.bundle}'  key='Not.existed' />！");
	} else if ("otherError" == addrStr) {
		alert("其他错误<fmt:message bundle='${pageScope.bundle}'  key='Other.errors' />！");
	} else if ("shutError" == addrStr) {
		alert("用户已关机无法定位<fmt:message bundle='${pageScope.bundle}'  key='Cannot.locate.since.customer.shutdown' />！");
	} else {
		var pointArr = addrStr.split(",")
		var map = new BMap.Map("l-map");
		var point=new BMap.Point(pointArr[0], pointArr[1]);
		map.centerAndZoom(point,12);
		var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",  
		            new BMap.Size(23, 25), {  
		                offset: new BMap.Size(10, 25),  
		                imageOffset: new BMap.Size(0, 0 -  12 * 25)  
		            });  
		var marker=new BMap.Marker(point,{ icon: myIcon });
		map.addOverlay(marker);
	// 	marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		//逆地址
		var geoc = new BMap.Geocoder();
		map.addEventListener("click", function(e){        
			var pt = e.point;
			geoc.getLocation(pt, function(rs){
				var addComp = rs.addressComponents;
				layer.alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
			});        
		});
	}
	
	
</script>

</body>
</html>