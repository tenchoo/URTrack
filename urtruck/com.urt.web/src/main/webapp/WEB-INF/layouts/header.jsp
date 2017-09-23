<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<script type="text/javascript">
window.STARTTIME = new Date().getTime();
</script>
<%-- <LINK rel="Bookmark" href="${ctx}/static/images/favicon.ico" >
<LINK rel="Shortcut Icon" href="${ctx}/static/images/favicon.ico" /> --%>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<script type="text/javascript" src="lib/PIE_IE678.js"></script>
<![endif]-->
<%-- <link href="${ctx}/static/css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<!--bootstrap-->
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet" />
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet" /> --%>

<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/h5/bootstrap.css">
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/data_list.js"></script>-->


<script type="text/javascript">
	    var scriptTag = document.createElement('script');
	    scriptTag.type = 'text/javascript';
	    scriptTag.async = true; 
	    scriptTag.src = "${ctx}/static/js/avatar.js";
	    var head= document.getElementsByTagName('head')[0] || document.documentElement;
	    head.insertBefore(scriptTag, head.firstChild);

		var avatar = window.Avatar = window.Avatar || [];
//		window.STARTTIME = new Date().getTime();
		//获取pageId
		var pageTmp = window.location.pathname;
		var pageId = pageTmp.substr(1,pageTmp.lengh);
		
		//获取渠道
		var channelTmp = document.referrer;
		var channel = null;
		if(channelTmp.indexOf("gla") > -1){
			channelTmp = "";
		}
		if(channelTmp == null || channelTmp == ""){
			channel = "lenovo";
		}else{
			channel = channelTmp;
		}
		
		//设备(浏览器或手机)型号 , 版本号 
		var deviceModel = "";
		var versionCode = "";
		var sys = {};
		if (/(iPhone|iPad|iPod|iOS|Android)/i.test(navigator.userAgent)) {		//iPhone,iPad,iPod,iOS
		    //获取当前的苹果手机信息 
		    sys = getIPhoneInfo(); 
		    //sys.browser得到手机的类型，sys.ver得到手机的版本 
		    deviceModel = sys.browser;
		    versionCode = sys.ver;
		} else if (/(Android)/i.test(navigator.userAgent)) {			//Android
			//获取当前的安卓手机信息 
		    sys = getAndroidInfo(); 
		    //sys.browser得到手机的类型，sys.ver得到手机的版本 
		    deviceModel = sys.browser;
		    versionCode = sys.ver;
		} else {														//PC端浏览器 
		    //获取当前的浏览器信息 
		    sys = getBrowserInfo(); 
		    //sys.browser得到浏览器的类型，sys.ver得到浏览器的版本 
		    deviceModel = sys.browser;
		    versionCode = sys.ver;
		};
		
		//获取安卓手机信息  
		function getAndroidInfo(){ 
		      var Sys = {}; 
		      var ua = navigator.userAgent.toLowerCase(); 
		      var re = /(android).*?([\d.]+)/; 
		      var m = ua.match(re);
		      if(m == null){
		    	  Sys.browser = "other Android OS";
		    	  Sys.ver = "0.0";
		      }else{
			      Sys.browser = m[0]; 
			      Sys.ver = m[2]; 
		      }
		      return Sys; 
		    } 
		//获取苹果手机信息  
		function getIPhoneInfo(){ 
		      var Sys = {}; 
		      var ua = navigator.userAgent.toLowerCase(); 
		      var re = /(cpu iphone os|cpu os).*?([\d_]+)/; 
		      var m = ua.match(re);
		      if(m == null){
		    	  Sys.browser = "other iPhone OS";
		    	  Sys.ver = "0_0";
		      }else{
			      Sys.browser = m[0]; 
			      Sys.ver = m[2]; 
		      }
		      return Sys; 
		    } 
		//获取浏览器信息 
	    function getBrowserInfo(){ 
	      var Sys = {}; 
	      var ua = navigator.userAgent.toLowerCase(); 
	      var re = /(maxthon|chrome|msie|firefox|opera|qqbrowser|version).*?([\d.]+)/; 
	      var m = ua.match(re); 
	      if(m == null){
	    	  Sys.browser = "other browsers";
	    	  Sys.ver = "0.0";
	      }else{
		      Sys.browser = m[1].replace(/version/, "safari"); 
		      Sys.ver = m[2]; 
	      }
	      return Sys; 
	    } 
		
		var curDomain = document.domain;
		avatar.push({
		 	"appKey" : "MTRMPZ990473",
		 	"pageId" : pageId,
		 	"throttle" : 0,
		 	"channel" : channel,
//		 	"deviceId" : deviceId,
		 	"deviceModel" : deviceModel,
		 	"startTime" : STARTTIME,
		 	"trackStayTime" : true,
//		 	"versionName" : "",
		 	"delay" : 100,
//		 	"versionCode" : versionCode,
//			"PVInfo" : "",
		 	"cookieDomain": curDomain,
		 	"init": function(){
		 		console.log('初始化完成');
	 		}
	    });
</script>
<!-- 
		//TestDemo
		var uaTmp = "Mozilla/5.0 (iPhone 6s; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 MQQBrowser/7.6.0 Mobile/14E304 Safari/8536.25 MttCustomUA/2 QBWebViewType/1 WKType/1";
		var uaa = uaTmp.toLowerCase();
		var rea = /(cpu iphone os|cpu os).*?([\d_]+)/; 
		var r1 = "";
		var r2 = "";
	    var ma = uaa.match(rea);
	    if(ma != null && ma != ""){
	    	r1 = ma[1];
	    	r2 = ma[2];
	    }
 -->
 </head>
</html>
