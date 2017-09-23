<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<style type="text/css">
body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
</style>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
</head>
<body>
	<div id="allmap"></div>
	<input type="hidden" id="imei" value="${imei}"/>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=yFEDv8FUDXLYM2lwQVGlLRvSu6Rti1i5"></script>
<script type="text/javascript">
var points=[];
var pointStr;//116.399@39.910
var pointArr=[];//[116.399, 39.910]
var startPoint,endPoint;
$.ajax({
    type: "post",
    url: "<%=basePath %>deviceInfo/getPointStrList",
    data: 'imei='+$("#imei").val(),
    dataType: "json",
    success: function (data) {
    	if(data.length==1){
    		pointStr=data[0];
    		pointArr=pointStr.split("@");
    		startPoint=new BMap.Point(pointArr[0], pointArr[1]);
    		endPoint=new BMap.Point(pointArr[0], pointArr[1]);
    		points.push(startPoint);
    		points.push(endPoint);
    		createTrailMap(points);
    	}
    	if(data.length>1){
    		for(var i=0;i<data.length;i++){
    			if(i==0){
    				pointStr=data[i];
    				pointArr=pointStr.split("@");
    				startPoint=new BMap.Point(pointArr[0], pointArr[1]);
    				points.push(startPoint);
    			}else if(i==data.length-1){
    				pointStr=data[i];
    				pointArr=pointStr.split("@");
    				endPoint=new BMap.Point(pointArr[0], pointArr[1]);
    				points.push(endPoint);
    			}else{
    				pointStr=data[i];
    				pointArr=pointStr.split("@")
    				mpoint=new BMap.Point(pointArr[0], pointArr[1]);
    				points.push(mpoint);
    			}
    		}
    		createTrailMap(points);
    	}
    },
});
function createTrailMap(points){
	// var startPoint=new BMap.Point(116.399, 39.910);
	// var endPoint=new BMap.Point(116.404, 39.915);
	// var mPoint1=new BMap.Point(116.405, 39.920);
	// var mPoint2=new BMap.Point(116.425, 39.900);
	var map = new BMap.Map("allmap");
	map.centerAndZoom(points[0], 20);
	map.enableScrollWheelZoom(true);
	var polyline = new BMap.Polyline(points, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});map.addOverlay(polyline);

	//对起点、终点、途经点做一个简单的处理，泡泡跟文字提示
	var m1 = new BMap.Marker(startPoint);
	var m2 = new BMap.Marker(endPoint);
	map.addOverlay(m1);
	map.addOverlay(m2);
	// var lab1 = new BMap.Label("起点", {offset:new BMap.Size(20,-10)});
	var lab1 = new BMap.Label("<fmt:message bundle='${pageScope.bundle}'  key='start' />", { position: startPoint, offset: new BMap.Size(-10, -25) });
	// var lab2 = new BMap.Label("终点", {offset:new BMap.Size(20,-10)});
	var lab2 = new BMap.Label("<fmt:message bundle='${pageScope.bundle}'  key='end' />", { position: endPoint, offset: new BMap.Size(-10, -25) });
	lab1.setStyle({
	    color: "#fff",
	    fontSize: "16px",
	    backgroundColor: "0.05",
	    border: "0",
	    fontWeight: "bold"
	});
	lab2.setStyle({
	    color: "#fff",
	    fontSize: "16px",
	    backgroundColor: "0.05",
	    border: "0",
	    fontWeight: "bold"
	});
	// m1.setLabel(lab1);
	// m2.setLabel(lab2);
	map.addOverlay(lab1);
	map.addOverlay(lab2);
	
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