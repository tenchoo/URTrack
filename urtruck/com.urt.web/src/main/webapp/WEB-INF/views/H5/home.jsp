<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>"/>
 <title><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect.official.home.page' /></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="static/h5/js/jquery-1.12.4.min.js"></script>
    
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
    <script src="static/h5/js/bootstrap.min.js"></script>
    
    <!--使用swiper.js 插件  -->
    <link rel="stylesheet" href="static/h5/css/swiper.min.css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <script src="static/h5/js/swiper.min.js"></script>

    <style>
        *{
            font-family: "冬青黑体", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
        }
        input{background-color:transparent;}

        select {
            -webkit-appearance: menulist;
            align-items: center;
            white-space: pre;
            -webkit-rtl-ordering: logical;
            background-color: transparent;
            cursor: default;
            border: none;
            font-size: 3.8vw;
        }
        .alter {
         display: block;
   		 max-width: 100%;
  		  height: auto;
        }
        
    </style>

    <script src="static/h5/js/main.js"></script>
    <script src="static/h5/js/wow.min.js"></script>
    <link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
    <script>
        new WOW().init();
    </script>
    <style>button{font-size: :4vw}</style>

</head>
<body>
<div style="background-color: #f4f3f9; font-size: 3.8vw;" >
    <jsp:include page="header.jsp" flush="true"/> 

    <div style="margin:0 auto;" id="carousel-example-generic" class="swiper-container" data-ride="carousel">
        <!-- Indicators -->
       <!--  <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol> -->

        <!-- Wrapper for slides  -->
        <div  class="swiper-wrapper"  role="listbox">
            <div class="item swiper-slide">
                <a href="http://m.lenovo.com.cn/product/53486.html">
                	<img src="static/h5/images/s1.png" alt="..." class="alter">
                </a>
            </div>
            <div class="swiper-slide">
               <a href="http://weidian.com/item.html?itemID=1904419752&p=-1">
                <img src="static/h5/images/s5.png" alt="..." class="alter">
               </a>
            </div>
        </div>
        
        <!-- Controls -->
        <!-- <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a> -->
        
         <!-- Add Pagination -->
        <div class="swiper-pagination"></div>
        <!-- Add Arrows -->
        <!-- <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div> -->
    </div>
    <div style="background-color: #ffffff; width: 100vw;padding-bottom:0;">
        <div style="margin: auto 0rem;" class="row">
                <div style="text-align: center;padding-left:2vw;padding-right:2vw;padding-top: 1rem;padding-bottom:1rem;border-right: 1px  solid #dfe4e1" class="col-xs-3">
				<a href="<%=basePath%>h5/productIntroduce">
                    <img class="img-circle" src="static/h5/images/product.png" style="width: 10vw">
	            </a>
                    <p style="margin-top: 0.5rem; font-size: 3.3vw;"><fmt:message bundle='${pageScope.bundle}'  key='Product.introduction' /></p>
                </div>
				<div style="text-align: center; padding-left: 2vw; padding-right: 2vw; padding-top: 1rem;padding-bottom:1rem; border-right: 1px solid #dfe4e1"
					class="col-xs-3">
					<a href="<%=basePath%>deviceActivation/index"> <img
						class="img-circle" src="static/h5/images/fire1.png"
						style="width: 10vw">
					</a>
					<p style="margin-top: 0.5rem; font-size: 3.3vw;"><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></p>
				</div>
           
                <div style="text-align: center;padding-left:2vw;padding-right:2vw;padding-top: 1rem;padding-bottom:1rem;border-right: 1px  solid #dfe4e1" class="col-xs-3">
                   <a href="<%=basePath%>paymentService/topay">
                    <img class="img-circle" src="static/h5/images/charge1.PNG" style="width: 10vw">
           		   </a>
                    <p style="margin-top: 0.5rem; font-size: 3.3vw;"><fmt:message bundle='${pageScope.bundle}'  key='data.recharge' /></p>
                </div>
				<div
					style="text-align: center; padding-left: 2vw; padding-right: 2vw; padding-top: 1rem;padding-bottom:1rem;"
					class="col-xs-3">
					<a href="<%=basePath%>queryService/index"> <img
						class="img-circle" src="static/h5/images/query.png"
						style="width: 10vw">
					</a>
					<p style="margin-top: 0.5rem; font-size: 3.3vw;"><fmt:message bundle='${pageScope.bundle}'  key='data.inquery' /></p>
				</div>
			</div>
    </div>
    <div style="padding-left: 4vw;padding-top: 2vw;padding-bottom: 2vw;overflow:hidden;">
        <div class="col-xs-10"> <div style="line-height: 1.5rem"><fmt:message bundle='${pageScope.bundle}'  key='data.plan' /></div></div>
        <div class="col-xs-2">
            <a style="text-align: right" href="<%=basePath%>paymentService/topay">  <img src="static/h5/images/right.png" style="height: 1.5rem">
            </a>
        </div>
    </div>


    <div style="background-color: #ffffff; width: 100vw;padding-bottom:0;">
        <div style="margin: auto 0;" class="row">
                <div style="text-align: center;padding-top:0.5rem;padding-bottom:1vw;border-right: 1px  solid #dfe4e1" class="col-xs-4">
            <a href="<%=basePath%>paymentService/topay">
                    <img class="img-responsive" src="static/h5/images/bag444G.png" >
            </a>
                </div>
                <div style="text-align: center;padding-top: 0.5rem;padding-bottom:1vw;border-right: 1px  solid #dfe4e1" class="col-xs-4">
            <a href="<%=basePath%>paymentService/topay">
                    <img  class="img-responsive"src="static/h5/images/bag10G.PNG" >
            </a>
                </div>
                <div style="text-align: center;padding-top: 0.5rem;padding-bottom:1vw;" class="col-xs-4">
            <a href="<%=basePath%>paymentService/topay">
                    <img  class="img-responsive"src="static/h5/images/bag1G.png" >
            </a>
                </div>
        </div>
    </div>
    <div style="padding-left: 4vw;padding-top: 2vw;padding-bottom: 2vw;overflow:hidden;">
        <div class="col-xs-10"> <div style="line-height: 1.5rem">LTE<fmt:message bundle='${pageScope.bundle}'  key='device' /></div></div>
        <div class="col-xs-2">
            <!--<a style="text-align: right" href="">-->
                <!--<img src="images/right.png" style="height: 1.5rem">-->

            <!--</a>-->
        </div>
    </div>
    <div style="background-color: #ffffff; width: 100vw;padding-bottom:0;">
        <div style="margin: auto 0;" class="row">
            
            <div style="text-align: center;padding-top:3vw;padding-bottom:3vw;border-right: 1px  solid #dfe4e1;" class="col-xs-6">
                <a href="http://m.lenovo.com.cn/product/53486.html">
	                 <img class="img-responsive" src="static/h5/images/xiaoxin.jpg" >
	                 <p style="text-align: center;padding-top: 2vw;"><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Ideapad' /> Air12 LTE</p>
	                 <p style="text-align: center;padding-top: 0.6vw;">￥3999</p>
                </a>
            </div>
            
            
            <div style="text-align: center;padding-top: 3vw;padding-bottom:3vw;border-right: 1px  solid #dfe4e1;" class="col-xs-6">
                <!-- <a href="javascript:return false;"> -->
	                 <img  class="img-responsive"src="static/h5/images/MIIX.jpg" >
	                 <p style="text-align: center;padding-top: 2vw;">MIIX4 LTE</p>
	                 <p style="text-align: center;padding-top:0.6vw;"><fmt:message bundle='${pageScope.bundle}'  key='Coming.soon' /></p>
				<!-- </a> -->
            </div>
			
        </div>
    </div>
    <div style="padding-left: 4vw;padding-top: 2vw;padding-bottom: 2vw;overflow:hidden;">
        <div class="col-xs-10"> <div style="line-height: 1.5rem"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /></div></div>
        <div class="col-xs-2">
            <a style="text-align: right" href="<%=basePath%>h5/productIntroduce">
                <img src="static/h5/images/right.png" style="height: 1.5rem">

            </a>
        </div>
    </div>

    <div style="background-color: #ffffff; width: 100vw;padding-bottom:0;margin-top: 0.5vw">
        <div style="margin: auto 0;" class="row">
            <a href="<%=basePath%>h5/productIntroduce">
                <div style="text-align: center;padding-top:3vw;padding-bottom:1vw;" class="col-xs-7">
                    <img class="img-responsive" src="static/h5/images/mifi3.png" >

                </div>
                <div style="padding-left: 7vw;padding-top: 3vw;padding-bottom:1vw;" class="col-xs-5">

                    <p style="text-align: left;padding-top: 7vw;"><fmt:message bundle='${pageScope.bundle}'  key='Portable.MIFI' /></p>
                    <p style="text-align: left;padding-top:0.6vw;">￥599</p>

                </div>
            </a>

        </div>
    </div>
    <div style="text-align:center; background-color: #ffffff; width: 100vw;padding-bottom:1rem;padding-top:0rem;margin-top: 0.5vw">
        <p style="color: #8c8c8c;padding-top: 3rem;"><fmt:message bundle='${pageScope.bundle}'  key='contact.us' /></p>
        <div style="margin: auto 0;" class="row">
            <div class="col-lg-3" style="float: inherit;">
            	<a href="http://mp.weixin.qq.com/s?__biz=MzI2OTA3MTY3Mw==&mid=504141852&idx=1&sn=c53846c7f3d29e315af180d14ea3423a&chksm=711ddc4a466a555cae8d08d69907ce9f6718b551bcf71d25ca5eb7c420cc6246b938af7c46a0&scene=0#wechat_redirect" style="display: inherit;">
	                <div style="text-align: center;padding-top:3vw;padding-bottom:1vw;" class="col-xs-2 col-xs-offset-3">
	                    <img class="img-responsive" src="static/h5/images/wechat.PNG" >
	                </div>
                </a>
            </div>
            <div class="col-lg-3" style="float: inherit;">
            	<a href="http://m.weibo.cn/u/5747118758?from=1069095010&wm=5311_4002&sourceType=qq&uid=1775049384" style="display: inherit;">
               		<div style="text-align: center;padding-top:3vw;padding-bottom:1vw;" class="col-xs-2">
                  		<img class="img-responsive" src="static/h5/images/l.png" >
                  	</div>
                </a>
            </div>
            <div class="col-lg-3" style="float: inherit;">
            	<a href="tel:4006410041" style="display: inherit;">
                    <div style="text-align: center;padding-top:3vw;padding-bottom:1vw;" class="col-xs-2">
                        <img class="img-responsive" src="static/h5/images/photo.PNG" >
                    </div>
                </a>
            </div>

        </div>

    </div>
    <div  style="margin-top: 0.5vw;text-align: center;background-color: #ffffff;">
        <p style="color: #8c8c8c;padding-top: 2rem;padding-bottom:1rem;font-size: 3.3vw">@2016联想懂的通信版权所有<fmt:message bundle='${pageScope.bundle}'  key='' /></p></div>

    </div>
    <script>        
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        paginationClickable: true,
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: 2500,
        autoplayDisableOnInteraction: false
    });
	  </script>
</body>
</html>