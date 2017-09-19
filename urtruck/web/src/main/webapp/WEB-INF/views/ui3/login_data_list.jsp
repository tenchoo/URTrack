<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.Data.View' /></title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/data_list.js"></script>
<script type="text/javascript" src="js/main.js"></script>
</head>
<body style="background: transparent;">
	<div class="output">
		<p>
			<fmt:message bundle='${pageScope.bundle}'  key='Connections.number' />：<span class="tosize"><img src="images/num_1.png"><img
				src="images/num_2.png"><img src="images/num_3.png"><img
				src="images/num_4.png"><img src="images/num_5.png"><img
				src="images/num_6.png"><img src="images/num_7.png"><img
				src="images/num_8.png"><img src="images/num_9.png"><img
				src="images/num_0.png"></span><fmt:message bundle='${pageScope.bundle}'  key='Place' />
		</p>
	</div>
	<div class="region_data">
		<p class="title">
			<span class="scope"><fmt:message bundle='${pageScope.bundle}'  key='around.the.country' /></span><span class="back"><img
				src="images/back_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='Back.Nationwide' /></span>
		</p>
		<div class="data">
			<!-- <h3>运营商</h3>
			<div class="data_s">
				<p>移动<span>39230</span></p>
				<p>联通<span>39230</span></p>
				<p>电信<span>39230</span></p>
			</div>
			<h3>卡状态</h3>
			<div class="data_s">
				<p>待激活<span>39230</span></p>
				<p>在网<span>39230</span></p>
				<p>停机<span>39230</span></p>
			</div> -->
		</div>
		<p class="sign">
			<span>
			<i></i><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.exhibition.industry' /></span><span>
			<i></i><fmt:message bundle='${pageScope.bundle}'  key='smart.car.connected' /></span><span>
			<i></i><fmt:message bundle='${pageScope.bundle}'  key='smart.connected' /></span><span>
			<i></i><fmt:message bundle='${pageScope.bundle}'  key='Carrier.IOT' /></span>
		</p>
	</div>
	<div class="region_view" id="main">
		<img src="images/china.png" usemap="#Map" class="china_t" border="0">
		<map name="Map">
			<area id="map_t" shape="poly"
				coords="148,227,148,239,148,252,156,261,166,265,172,271,184,273,198,274,204,282,211,288,221,286,225,283,227,274,229,269,228,262,235,261,241,273,251,278,263,278,268,273,268,266,276,269,281,263,287,257,292,266,301,278,314,278,323,279,333,283,349,286,358,290,360,283,360,276,360,271,369,271,366,255,369,249,384,244,394,240,400,232,400,227,411,228,418,233,416,241,417,245,424,247,432,243,441,248,450,246,454,240,458,235,463,231,465,224,468,218,477,215,481,211,485,206,473,205,466,203,461,209,453,213,447,211,447,206,440,206,436,198,434,196,437,190,445,186,453,177,456,171,465,163,473,163,474,170,474,177,473,186,483,175,491,169,502,157,515,145,518,140,527,143,526,136,532,130,541,125,544,119,548,125,552,115,548,103,544,92,549,86,560,87,560,48,557,41,543,53,532,58,526,49,518,46,496,43,494,37,488,26,483,22,476,17,470,13,470,8,459,7,443,7,426,11,432,22,430,31,427,40,425,51,414,55,406,56,406,63,400,76,411,81,420,76,430,81,434,85,431,91,420,99,408,105,394,112,380,117,373,117,370,123,373,136,363,148,347,152,323,159,303,167,283,156,256,148,238,149,228,145,220,141,216,129,210,120,204,112,189,112,180,110,178,99,182,87,180,80,175,76,167,69,164,63,159,56,151,57,147,63,138,67,138,77,118,70,110,78,105,86,105,96,94,93,82,92,84,101,79,114,77,122,69,126,63,131,54,132,36,138,28,136,24,133,8,139,3,146,6,153,8,163,8,171,7,176,12,183,14,188,13,194,23,198,28,201,29,210,37,221,53,219,57,214,70,218,79,217,88,221,109,221,121,213,139,216,150,228"
				href="#">
			<area id="map_l" shape="poly"
				coords="235,348,235,355,234,362,229,370,223,381,222,388,230,389,234,399,239,407,240,416,255,421,258,421,263,426,269,427,268,419,267,413,274,409,283,409,291,409,305,409,313,400,320,406,329,409,330,422,339,422,351,424,361,424,365,434,371,442,357,452,353,458,358,460,369,460,376,456,378,450,378,446,372,437,372,432,374,425,389,420,405,415,408,408,416,406,423,406,435,402,447,396,454,387,464,383,468,375,475,361,477,351,479,346,485,340,477,340,470,337,463,330,458,322,457,317,450,312,443,307,433,310,418,314,410,323,408,334,408,344,411,350,412,362,410,372,403,375,394,378,384,378,379,370,379,366,372,364,369,364,363,359,359,352,354,350,358,343,356,328,353,318,349,310,356,307,366,300,361,293,358,289,342,284,322,280,308,277,303,276,298,271,294,266,290,261,280,264,276,266,269,268,268,276,251,277,245,271,240,267,237,260,230,262,228,277,221,289,213,289,208,287,202,279,199,277,191,273,174,271,157,262,154,253,149,240,149,228,143,219,133,215,121,215,114,218,112,223,97,223,90,221,82,215,76,214,69,217,60,215,53,212,46,219,39,222,35,225,33,231,35,243,31,249,26,246,28,253,25,256,23,263,34,268,39,274,41,282,52,283,57,277,59,286,64,293,69,300,78,309,89,315,95,320,106,322,122,327,126,338,135,329,149,332,154,338,160,346,171,347,184,342,195,333,202,332,206,332,219,338,223,334,232,340,234,349"
				href="#">
			<area id="map_r" shape="poly"
				coords="492,285,488,281,482,277,488,276,485,271,479,268,477,260,472,255,467,246,458,241,447,247,445,247,436,246,433,243,426,246,417,247,413,239,415,231,408,229,401,228,400,233,394,240,388,243,377,248,370,250,367,258,371,265,371,271,364,270,362,272,362,281,359,290,367,294,364,298,355,307,349,310,353,316,357,328,358,337,356,346,359,352,368,362,375,366,380,371,389,378,401,377,410,371,412,359,410,338,409,322,426,312,444,309,453,314,457,323,464,333,470,339,478,341,487,341,485,330,492,325,492,315,497,306,490,299,484,298,490,294,493,285"
				href="#">
		</map>
		<img src="images/china_t.png" class="china china_t" id="china_t"
			data-ok="false"> <img src="images/china_l.png"
			class="china china_l" id="china_l" data-ok="false"> <img
			src="images/china_r.png" class="china china_r" id="china_r"
			data-ok="false">
		<!-- <img src="images/t_map.png"  class="china" id="map_t"> -->
		<span id="t_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='North.District' /></span> 
		<span id="l_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='southern district' /></span> 
		<span id="r_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='Eastern.District' /></span>
		<div class="column column_t">
			<div class="col_w">
				<span class="col col_linear1"></span> <em class="zn_z">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear2"></span> <em class="zn_c">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear3"></span> <em class="zn_h">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear4"></span> <em class="yy_w">93823</em>
			</div>
		</div>
		<div class="column column_l">
			<div class="col_w">
				<span class="col col_linear1"></span> <em class="zn_z">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear2"></span> <em class="zn_c">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear3"></span> <em class="zn_h">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear4"></span> <em class="yy_w">93823</em>
			</div>
		</div>
		<div class="column column_r">
			<div class="col_w">
				<span class="col col_linear1"></span> <em class="zn_z">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear2"></span> <em class="zn_c">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear3"></span> <em class="zn_h">93823</em>
			</div>
			<div class="col_w">
				<span class="col col_linear4"></span> <em class="yy_w">93823</em>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="search_data">
		<div class="data_title">
			<ul>
				<li><span class="time"><em><fmt:message bundle='${pageScope.bundle}'  key='Time' /></em><img
						src="images/tb_icon.png" /></span>
					<div class="data_core">
						<div class="list">
							<ul>
								<li><a href=""><fmt:message bundle='${pageScope.bundle}'  key='This.month' /></a></li>
								<li><a href=""><fmt:message bundle='${pageScope.bundle}'  key='Q1' /></a></li>
								<li><a href="">2017年4月-2018年3月<fmt:message bundle='${pageScope.bundle}'  key='' /></a></li>
							</ul>
						</div>
					</div></li>
				<li><fmt:message bundle='${pageScope.bundle}'  key='Connection.number' /></li>
				<li><fmt:message bundle='${pageScope.bundle}'  key='income' /></li>
				<li><span><fmt:message bundle='${pageScope.bundle}'  key='data' /></span>
					<span><fmt:message bundle='${pageScope.bundle}'  key='Activatied.data' /><br />
					<fmt:message bundle='${pageScope.bundle}'  key='Recharge.data' />
				</span></li>
				<li><fmt:message bundle='${pageScope.bundle}'  key='Completion.rate' /></li>
			</ul>
		</div>
		<div class="data_main">
			<ul>
				<li><fmt:message bundle='${pageScope.bundle}'  key='Q1' /></li>
				<li>90000</li>
				<li>90000000元</li>
				<li>90000000G</li>
				<li><div id="indicatorContainer" style="margin-top: 22px"></div></li>
			</ul>
		</div>
		<script type="text/javascript" src="js/radialIndicator.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
		    //圆形流量使用————比例图 
		    $('#indicatorContainer').radialIndicator({
		        barColor: '#0194ff', //数值颜色
		        barWidth: 5,  //圆型线的宽度
		        initValue: 20, //所占比例值
		        radius:40, //半径宽度
		        roundCorner : true, //如果设置为true则圆形指示器的刻度bar有圆角
		        percentage: true  //设置为true显示圆形指示器的百分比数值
		    });

		   var radialObj = $('#indicatorContainer').data('radialIndicator');
				//获取圆形图对象
			   radialObj.animate(60); 
				//设置圆形对象的比例值
		})
	</script>
	</div>
</body>
</html>