<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>首页</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/reset.css" />
    <link href="${ctx}/static/glaNew/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/static/glaNew/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/iconfont.css" />
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/subPage/index.css" />
     <!-- 全局js -->
    <script src="${ctx}/static/glaNew/js/jquery-1.11.3.min.js"></script>
    <script src="${ctx}/static/glaNew/js/bootstrap.min.js"></script>
    <!-- 自定义js -->
    <script src="${ctx}/static/glaNew/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${ctx}/static/glaNew/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctx}/static/glaNew/js/subPage/index.js"></script>
</head>

<body class="gray-bg" style="color: #444444; background-color: #edf0ff;">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row"> 
        	<div class="totle-connection  col-md-3 col-xs-12">
        		<h5>总连接数</h5>
        		<div>
        			<strong>3400000</strong>位
        		</div>
        		<ul id="area" class="cl">
        			<li class="active">全国</li>
        			<li>北区</li>
        			<li>东区</li>
        			<li>南区</li>
        		</ul>
        		<div class="sub-content">
        			<dl>
        				<dt>运营商</dt>
        				<dd><span>移动</span><p>111</p></dd>
        				<dd><span>联通</span><p>111</p></dd>
        				<dd><span>电信</span><p>111</p></dd>
        			</dl>
        			<dl>
        				<dt>卡状态</dt>
        				<dd><span>待激活</span><p>111</p></dd>
        				<dd><span>在网</span><p>111</p></dd>
        				<dd><span>停机</span><p>111</p></dd>
        			</dl>
        		</div>
        	</div>
        	<div class="col-md-8 col-sm-12 col-xs-12 map_area">
        	<div class="region_view" id="main">
			<img src="${ctx}/static/glaNew/img/china.png" usemap="#Map" border="0" >
              <map name="Map">
                <area shape="poly" id="map_t" coords="381,206,412,170,379,169,370,162,401,133,406,143,408,151,428,137,444,117,468,103,463,74,476,67,475,34,451,48,418,33,397,1,366,8,356,42,343,47,340,65,365,61,373,74,346,92,321,93,302,123,261,138,231,125,193,117,172,93,151,85,157,78,139,46,119,49,117,63,101,60,86,76,71,76,64,102,48,110,34,113,13,112,4,118,7,132,5,148,16,160,25,178,37,188,44,179,74,188,111,187,125,190,125,204,129,216,143,228,161,231,176,239,188,243,193,220,209,232,227,231,229,224,243,220,257,235,281,236,298,242,306,240,308,228,314,229,311,208" href="#">
                <area shape="poly" id="map_r" coords="302,244,308,254,302,260,293,260,293,268,301,279,303,292,299,299,306,309,315,307,320,312,323,323,331,330,339,318,351,313,345,289,347,278,347,274,371,261,376,264,389,264,389,272,398,288,404,287,416,278,422,257,409,250,418,246,412,234,395,211,387,204,373,209,329,209,314,209,303,244" href="#">
                <area shape="poly" id="map_l" coords="123,196,42,182,31,191,23,199,24,210,18,218,33,237,49,243,70,268,82,276,110,276,131,283,139,297,177,285,193,286,201,301,194,316,192,330,207,352,229,363,241,347,267,346,279,356,312,370,351,348,391,325,408,288,386,268,369,263,350,281,350,301,349,311,331,323,299,295,295,270,294,261,305,256,302,243,259,235,242,220,223,236,195,223,189,243,169,237,145,233,126,216,123,194,106,189,93,189" href="#">
              </map>
				<img src="${ctx}/static/glaNew/img/china_t.png"  class="china china_t"  id="china_t" data-ok="false">
	            <img src="${ctx}/static/glaNew/img/china_l.png"  class="china china_l"  id="china_l" data-ok="false">
	            <img src="${ctx}/static/glaNew/img/china_r.png"  class="china china_r"  id="china_r" data-ok="false">
	            <!-- <img src="images/t_map.png"  class="china" id="map_t"> -->
	            <span id="t_area" class="area cursor">北区</span>
	            <span id="l_area" class="area">南区</span>
	            <span id="r_area" class="area">东区</span>
	     	 </div>
	     	 <div class="progresses">
				<div class="progressbar" data-perc="100">
					<h4>智能展业</h4>
					<div class="toolip">
						智能展业<br><strong></strong>	
					</div>
					<div class="bar"></div>
				
					<div class="label"></div>
				
				</div>


				
				<div class="progressbar" data-perc="75">
					<h4>智能车联</h4>
					<div class="toolip">
						智能车联<br><strong></strong>	
					</div>
					<div class="bar color2"></div>
				
					<div class="label"></div>
				
				</div>


				
				<div class="progressbar" data-perc="50">
					<h4>智能互联</h4>
					<div class="toolip">
						智能互联<br><strong></strong>	
					</div>
					<div class="bar color3"></div>
				
					<div class="label"></div>
				
				</div>


				
				<div class="progressbar" data-perc="25">
					<h4>运营商物联</h4>
					<div class="toolip">
						运营商物联<br><strong></strong>	
					</div>
					<div class="bar color4">
						
					</div>
					
				
					<div class="label"></div>
					
				
				</div>
	     	 </div>
        	</div>
  
        </div>
        
        <div class="row time_search">
        	<div class="query clearfix">
				<label for="" class="fl lab">时间:</label>
				<div class="monthQuery fl">
					<div class="input-append date form_datetime cl">
					    <input style="background-color: #FFF" size="11" type="text" value="2017 . 09 . 05" readonly class="form-control fl input-sm"></i>
					    <span class="add-on fl"><i class="icon-th fa date_icon icon-rili "></i></span>
					</div>
				</div>
				<label for="" class="fl lab">...</label>
				<div class="monthQuery fl">
					<div class="input-append date form_datetime cl">
					    <input style="background-color: #fff;" size="11" type="text"  readonly class="form-control fl input-sm" placeholder="请选择结束日期">
					    <span class="add-on fl"><i class="icon-th date_icon fa icon-rili "></i></span>
					</div>
				</div>
				<button id="sure" class="btn-default btn">确定</button>
			</div>
			
			<div class="row" id="foot_data">	
				<ul>
					<li>
						<p class="title">连接数</p>
						<p>111</p>
					</li>
					<li>
						<p class="title">收入(元)</p>
						<p>000</p>
					</li>
					<li>
						<p class="title">流量</p>
						<p>500000</p>
					</li>
					<li>
						<p class="title">收入环比</p>
						<p>--</p>
					</li>
					<li>
						<p class="title">完成率</p>
						<p>--</p>
					</li>
				</ul>
			</div>
        </div>
    </div>
   
  
	<script type="text/javascript">

	$(function() {	
		//进度条
		$('.progressbar').each(function(){	
			var t = $(this),	
			dataperc = t.attr('data-perc'),	//后台算出来的百分比就是这个data-perc
			barperc = Math.round(dataperc*1.98);
			t.find('.bar').animate({width:barperc}, dataperc*25);	
			t.find('.label').append('<div class="perc"></div>');		
		function perc(){
			var length = t.find('.bar').css('width'),
			perc = Math.round(parseInt(length)/1.98),
			labelpos = (parseInt(length)-2);
			t.find('.perc').text(perc+'%');
			t.find('.toolip strong').text(perc+'%');
		}
		perc();
		setInterval(perc, 0); 
		});	
		//进度条上数字的显示和隐藏
		$('.bar').on('mouseenter',function(){
			$(this).siblings('.toolip').show();
		});
		$('.bar').on('mouseleave',function(){
			$(this).siblings('.toolip').hide();
		});
		
		//日期插件
		var newDate1 = new Date(19930102);
		var newDate2 = new Date();
    	var t1       = newDate1.toJSON(); 
    	var t2       = newDate2.toJSON(); 
		$(".form_datetime").datetimepicker({
	        format: "yyyy . mm . dd ",
	        autoclose: true,
	        //weekStart: 1,
			minView:2,//有0，1，2，3 表示年月日时分秒
	        todayBtn: true,//今天按钮
	        startDate:new Date(t1),//起始日期
	        endDate:new Date(t2),//结束日期
	        pickerPosition: "bottom-right",//位置
	        language:"zh-CN" //语言设置
	  	});
		
		
	});

</script>


</body>

</html>
