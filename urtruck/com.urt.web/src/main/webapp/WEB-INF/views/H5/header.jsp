<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<nav style="text-shadow: none; background:rgba(255,00,255,0); border:none;margin-bottom: 0vw" class="navbar navbar-default">
            <div style="text-shadow: none;background:rgba(255,00,255,0);  padding: 0em;border:none;" class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div style="background-color: #ffffff ;border: hidden;margin: 0em;" class="navbar-header ">
                <!-- <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button> -->
                <a class="navbar-brand" style="padding-top: 0px" <%-- href="<%=basePath%>h5/index" --%>><img src="static/h5/images/logo61.png" style="height: 2.2rem;margin-top:0.5rem;"></a>

            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div  style="border-bottom: none;background-color: #FFFFFF; font-family:微软雅黑;margin:0px;" class="collapse navbar-collapse " id="bs-example-navbar-collapse-1">
                <ul style="margin: 1.5rem 0rem;text-align: left" class="nav navbar-nav  ">
                    <li style="text-align: left" >
                        <a  style=" padding-top: 1.2rem;padding-bottom:1.3rem;animation-delay: 0.21s; animation-name: fadeInDownBig;border-bottom: 1px  solid #dfe4e1; text-align: left;
                    padding-left: 4rem" class="wow fadeInDownBig animated " href="<%=basePath%>h5/index"><fmt:message bundle='${pageScope.bundle}'  key='home.page' />
                        </a> </li>

                    <li class="dropdown">
                        <a style="padding-top: 1.2rem;padding-bottom:1.3rem;margin: auto 0;animation-delay: 0.16s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem;border-bottom: 1px  solid #dfe4e1;" href="#" class="dropdown-toggle wow fadeInDownBig animated" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">设备<fmt:message bundle='${pageScope.bundle}'  key='' /> <span class="caret"></span></a>
                        <ul style="margin: auto 1rem;" class="dropdown-menu">
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>deviceActivation/index"><fmt:message bundle='${pageScope.bundle}'  key='Activation' /></a></li>
                            <li role="separator" class="divider"></li>

                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>h5/productIntroduce"><fmt:message bundle='${pageScope.bundle}'  key='introduction' /></a></li>
                            <li role="separator" class="divider"></li>


                        </ul>
                    </li>
                    <li class="dropdown">
                        <a  style="padding-top: 1.2rem;padding-bottom:1.3rem;margin: auto 0;border-bottom: 1px  solid #dfe4e1;animation-delay: 0.11s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="#" class="wow fadeInDownBig animated dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message bundle='${pageScope.bundle}'  key='data' /> <span class="caret"></span></a>
                        <ul style="margin: auto 1rem;" class="dropdown-menu ">
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem" href="<%=basePath%>paymentService/topay"><fmt:message bundle='${pageScope.bundle}'  key='recharge' /></a></li>
                            <li role="separator" class="divider"></li>
                            <li><a class="wow fadeIn animated" style="font-size: 3.8vw;text-align: left;padding-left: 3rem"  href="<%=basePath%>queryService/index"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></a></li>
                            <li role="separator" class="divider"></li>

                        </ul>
                    </li>


                </ul>

                <ul style="margin: 0;" class="nav navbar-nav navbar-right ">
					<%
							if (session.getAttribute("lenovoid") != null) {
							%>
                            <li class="dropdown">
		                        <a style="margin: 0.5rem 0;padding-top:0;padding-bottom:2rem;animation-delay:0s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="<%=basePath%>h5/loginout" class="wow fadeInDownBig animated dropdown-toggle" >
		                       		<fmt:message bundle='${pageScope.bundle}'  key='quit' />
		                        </a>
		                    </li>
							<%
								}else {
							%>
							<li class="dropdown">
		                        <a style="margin: 0.5rem 0;padding-top:0;padding-bottom:2rem;animation-delay:0s; animation-name: fadeInDownBig;text-align: left;padding-left: 4rem" href="<%=basePath%>deviceActivation/index" class="wow fadeInDownBig animated dropdown-toggle" >
		                       		<fmt:message bundle='${pageScope.bundle}'  key='sign.in' />
		                        </a>
		                    </li>
							<%
								}
					%>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>