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
<title>><fmt:message bundle='${pageScope.bundle}'  key='Batch.addresses' /></title>
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
	<input type="hidden" id="batchId" value="${batchId}"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yFEDv8FUDXLYM2lwQVGlLRvSu6Rti1i5"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript">
var adds=[];
var pointStr;//116.399@39.910
var pointArr=[];//[116.399, 39.910]
$.ajax({
    type: "post",
    url: "<%=basePath %>lbs/getBatchMap",
    data: 'batchId='+$("#batchId").val(),
    dataType: "json",
    success: function (data) {
   		for(var i=0;i<data.length;i++){
			pointStr=data[i];
			pointArr=pointStr.split(",")
			point=new BMap.Point(pointArr[0], pointArr[1]);
			adds.push(point);
   		}
   		createBatchMap(adds);
    },
});

function createBatchMap(adds){
	// 百度地图API功能
	var map = new BMap.Map("l-map");
	map.centerAndZoom(adds[0], 10);
	for(var i = 0; i<adds.length; i++){
		var marker = new BMap.Marker(adds[i]);
		map.addOverlay(marker);
	}
	map.enableScrollWheelZoom(true);
	
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