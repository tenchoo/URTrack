<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>   
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>   
<html>
<head>
<meta charset="UTF-8">
	<title><fmt:message bundle='${pageScope.bundle}'  key='Administrator.Data.View' /></title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
	<script type="text/javascript">
	var total={};	
	function getNum(){
			/* $('.loadingBox').fadeIn(1000); */
			$.ajax({
				url:'${ctx}/userTotalService/getNumber',
				type:'POST',
				data: {"startDate":$("#sdate").val(),"endDate":$("#edate").val()},
				dataType:'json',
				async:false,
				success:function(data){
					/* $('.loadingBox').fadeOut(1000); */
					console.log(data);
					total=data;
				}
			});
		}
		
	</script>
	<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
	<script type="text/javascript" src="${ctx}/static/ui3/js/data_list.js"></script>
	<script type="text/javascript" src="${ctx}/static/ui3/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="overHid-X" id="logindDtaList">
	
	<!-- <div class="main"> -->
		<hidden id="count" class="hide">${count}</hidden>
		<!-- <div class="container backNone"> -->
		<!-- 加载内容放这里 -->
		<div class="width1200">
			
			<div class="output">
				<p><fmt:message bundle='${pageScope.bundle}'  key='Connection.number' />：
					<span class="tosize">
					</span><fmt:message bundle='${pageScope.bundle}'  key='Place' />
				</p>
			</div>
			<div class="region_data">
				<p class="title">
					<span class="scope clearfix"><i><fmt:message bundle='${pageScope.bundle}'  key='around.the.country' /></i></span>
					<span class="back"><img src="${ctx}/static/ui3/images/back_icon.png"><fmt:message bundle='${pageScope.bundle}'  key='Back.To.Nationwide' /></span>
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
				<span><i></i><fmt:message bundle='${pageScope.bundle}'  key='Intelligent.exhibition.industry' /></span>
				<span><i></i><fmt:message bundle='${pageScope.bundle}'  key='smart.car.connected' /></span>
				<span><i></i><fmt:message bundle='${pageScope.bundle}'  key='smart.connected' /></span>
				<span><i></i><fmt:message bundle='${pageScope.bundle}'  key='Carrier.IOT' /></span>
				</p>
			</div>
			<div class="region_view" id="main">
				<img src="${ctx}/static/ui3/images/china.png" usemap="#Map" border="0" >
              <map name="Map">
                <area shape="poly" id="map_t" coords="381,206,412,170,379,169,370,162,401,133,406,143,408,151,428,137,444,117,468,103,463,74,476,67,475,34,451,48,418,33,397,1,366,8,356,42,343,47,340,65,365,61,373,74,346,92,321,93,302,123,261,138,231,125,193,117,172,93,151,85,157,78,139,46,119,49,117,63,101,60,86,76,71,76,64,102,48,110,34,113,13,112,4,118,7,132,5,148,16,160,25,178,37,188,44,179,74,188,111,187,125,190,125,204,129,216,143,228,161,231,176,239,188,243,193,220,209,232,227,231,229,224,243,220,257,235,281,236,298,242,306,240,308,228,314,229,311,208" href="#">
                <area shape="poly" id="map_r" coords="302,244,308,254,302,260,293,260,293,268,301,279,303,292,299,299,306,309,315,307,320,312,323,323,331,330,339,318,351,313,345,289,347,278,347,274,371,261,376,264,389,264,389,272,398,288,404,287,416,278,422,257,409,250,418,246,412,234,395,211,387,204,373,209,329,209,314,209,303,244" href="#">
                <area shape="poly" id="map_l" coords="123,196,42,182,31,191,23,199,24,210,18,218,33,237,49,243,70,268,82,276,110,276,131,283,139,297,177,285,193,286,201,301,194,316,192,330,207,352,229,363,241,347,267,346,279,356,312,370,351,348,391,325,408,288,386,268,369,263,350,281,350,301,349,311,331,323,299,295,295,270,294,261,305,256,302,243,259,235,242,220,223,236,195,223,189,243,169,237,145,233,126,216,123,194,106,189,93,189" href="#">
              </map>


				<img src="${ctx}/static/ui3/images/china_t.png"  class="china china_t" id="china_t" data-ok="false">
	            <img src="${ctx}/static/ui3/images/china_l.png"  class="china china_l"  id="china_l" data-ok="false">
	            <img src="${ctx}/static/ui3/images/china_r.png"  class="china china_r"  id="china_r" data-ok="false">
	            <!-- <img src="images/t_map.png"  class="china" id="map_t"> -->
	            <span id="t_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='North.District' /></span>
	            <span id="l_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='South.District' /></span>
	            <span id="r_area" class="area"><fmt:message bundle='${pageScope.bundle}'  key='East.District' /></span>
	        	<div class="column column_t">
	        		<div class="col_w left01">
	        			<span class="col colT col_linear1"></span>
	        			<em class="zn_z hide">93823</em>
	        		</div>
	        		<div class="col_w left02">
	        			<span class="col colT col_linear2"></span>
	        			<em class="zn_c hide">93823</em>
	        		</div>
	        		<div class="col_w left03">
	        			<span class="col colT col_linear3"></span>
	        			<em class="zn_h hide">93823</em>
	        		</div>
	        		<div class="col_w left04">
	        			<span class="col colT col_linear4"></span>
	        			<em class="yy_w hide">93823</em>
	        		</div>
        	  </div>
	        	<div class="column column_l">
	        		<div class="col_w left01">
	        			<span class="col colL col_linear1"></span>
	        			<em class="zn_z hide">93823</em>
	        		</div>
	        		<div class="col_w left02">
	        			<span class="col colL col_linear2"></span>
	        			<em class="zn_c hide">93823</em>
	        		</div>
	        		<div class="col_w left03">
	        			<span class="col colL col_linear3"></span>
	        			<em class="zn_h hide">93823</em>
	        		</div>
	        		<div class="col_w left04">
	        			<span class="col colL col_linear4"></span>
	        			<em class="yy_w hide">93823</em>
	        		</div>
	        	</div>
	        	<div class="column column_r">
	        		<div class="col_w left01">
	        			<span class="col colR col_linear1"></span>
	        			<em class="zn_z hide">93823</em>
	        		</div>
	        		<div class="col_w left02">
	        			<span class="col colR col_linear2"></span>
	        			<em class="zn_c hide">93823</em>
	        		</div>
	        		<div class="col_w left03">
	        			<span class="col colR col_linear3"></span>
	        			<em class="zn_h hide">93823</em>
	        		</div>
	        		<div class="col_w left04">
	        			<span class="col colR col_linear4"></span>
	        			<em class="yy_w hide">93823</em>
	        		</div>
	        	</div>
	      </div>
		  <div class="clear"></div>
		  <div class="search_data">
		  		<div class="data_title">
		  			<ul class="clearfix">
		  				<li>
		  					<label><fmt:message bundle='${pageScope.bundle}'  key='Time' /></label>
		  					<input id="sdate" class="Wdate startTime mgL02" name="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true,maxDate:'#F{$dp.$D(\'edate\')}',onpicked:pickeTimeFn})" />
    						<b class="timeLine"><fmt:message bundle='${pageScope.bundle}'  key='To' /></b>
    						<input id="edate" class="Wdate endTime" name="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true,minDate:'#F{$dp.$D(\'sdate\')}',startDate:'#F{$dp.$D(\'sdate\',{d:+1})}',onpicked:pickeTimeFn})" />
    						<button class="btn02" id="confirmBtn"><fmt:message bundle='${pageScope.bundle}'  key='Sure' /></button>
    						<i></i>

		  				</li>
		  				<li><fmt:message bundle='${pageScope.bundle}'  key='Connection.number' /><i></i></li>
		  				<li><fmt:message bundle='${pageScope.bundle}'  key='Income' /><i></i></li>
		  				<li><span><fmt:message bundle='${pageScope.bundle}'  key='Flow' /></span><i></i></li>
		  				<li><fmt:message bundle='${pageScope.bundle}'  key='Income.Chain.Relative.Ratio' /><i></i></li>
		  				<li><fmt:message bundle='${pageScope.bundle}'  key='Completion.Rate' /></li>
		  			</ul>
		  		</div>
		  		<div class="data_main">
		  			<ul>
		  				<li class="data_time">
		  					<span class="sTime"></span>--<span class="eTime"></span>
		  				</li>
		  				<li class="data_conn"></li>
		  				<li class="data_earn"></li>
		  				<li class="data_flow"></li>
		  				<li class="data_earnMom"></li>
		  				<li class="data_comp">
		  					<div id="indicatorContainer" style="margin-top:22px"></div>
		  				</li>
		  			</ul>
		  		</div>
		  	<%-- <script type="text/javascript" src="${ctx}/static/ui3/js/radialIndicator.js"></script>
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
					radialObj.animate(crNum);
						//设置圆形对象的比例值
				})
			</script> --%>
		  </div>
	  </div>
<!-- 	</div>
	</div> -->
	<div class="loadingBox hide clearfix">
		<div class="load-1">
			<div class="k-line k-line1-1"></div>
			<div class="k-line k-line1-2"></div>
			<div class="k-line k-line1-3"></div>
		</div>
	</div>
	<script type="text/javascript">
    //获取选中的值
      function pickeTimeFn(){
          var _val = this.value,
              _name = this.name;
          if(_name =='startTime'){
              $('.sTime').text(_val);
          }else if(_name =='endTime'){
              $('.eTime').text(_val);
          }
        //执行统一搜索

      };
      numCount($("#count").text());
     
  </script>
</body>
</html>
 