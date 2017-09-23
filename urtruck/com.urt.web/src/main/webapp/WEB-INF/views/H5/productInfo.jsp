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
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><fmt:message bundle='${pageScope.bundle}'  key='equipment.introduction' /></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
    <link rel="stylesheet" href="static/h5/css/bootstrap-theme.min.css" type="text/css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="static/h5/js/jquery-1.12.4.min.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
    <script src="static/h5/js/bootstrap.min.js"></script>

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
    </style>

    <script src="static/h5/js/main.js"></script>

    <script src="static/h5/js/wow.min.js"></script>
    <link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
    <script>
        new WOW().init();
    </script>
    <script>
        new WOW().init();
    </script>
    <style>button{font-size: :4vw}</style>

</head>
<body><div style="background-color: #f4f3f9; font-size: 3.8vw;" >
    <jsp:include page="header.jsp" flush="true"/> 
    <div class="row"style="width: 100vw;margin-right: 0;margin-left: 0;">
    <a href="http://weidian.com/item.html?itemID=1904419752&p=-1">
    <img    class="img-responsive wow fadeInUp animated " alt="Responsive image"
            style=" width: 100%;animation-delay:0.25s; animation-name: fadeInUp;background-color: #ffffff;margin-top: 0.2vw;margin-bottom: 1vw;" src="static/h5/images/mifi3.png">
	</a>
    </div>
    <div class="tabbable" id="tabs-634545">

        <ul class="nav nav-tabs">

            <li class="active">
                <a class="wow fadeInRight animated " style="animation-delay:0.1s; animation-name: fadeInRight;padding: 1vw 2vw" href="#panel-58792" data-toggle="tab">

                    <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Commodity.introduction' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
                </a>
            </li>
            <li>
                <a class="wow fadeInRight animated " style="animation-delay:0.15s; animation-name: fadeInRight;padding: 1vw 2vw"href="#panel-58793" data-toggle="tab">
                    <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Detailed.parameters' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p></a>
            </li>
            <li>
                <a class="wow fadeInRight animated " style="animation-delay:0.15s; animation-name: fadeInRight;padding: 1vw 2vw"href="#panel-485241" data-toggle="tab">
                    <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Activation.method' />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p></a>
            </li>

        </ul>
        <div style="background-color: #ffffff;" class="tab-content">
            <div class="tab-pane active" id="panel-58792">

                <div  style="width: 100%;" class="img-responsive wow fadeInUp animated row ">

                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Product.name' /> </div>
                            <div style="" class="col-xs-8 ">Lenovo Connect <fmt:message bundle='${pageScope.bundle}'  key='pc.partners' />  </div>
                        </div>


                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='processor' /> </div>
                            <div style="" id="privatekey" class="col-xs-8 ">  <fmt:message bundle='${pageScope.bundle}'  key='Qualcomm' />28nm MDM9X15</div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Network.type' />  </div>
                            <div style=""id="userame" class="col-xs-8 "><fmt:message bundle='${pageScope.bundle}'  key='Triple.play.five.model' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Wireless.transmission.rate' /> </div>
                            <div style="" class="col-xs-8 "id="idcard">150Mbps  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Battery.capacity' /> </div>
                            <div style="" class="col-xs-8 "id="idcard">2100<fmt:message bundle='${pageScope.bundle}'  key='Ma' />  </div>
                        </div>

                    </div>

                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Standby.time' /></div>
                            <div style="" class="col-xs-8 "id="idcard">50<fmt:message bundle='${pageScope.bundle}'  key='hour' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4"> <fmt:message bundle='${pageScope.bundle}'  key='Length.of.usage' /></div>
                            <div style="" class="col-xs-8 "id="idcard">6<fmt:message bundle='${pageScope.bundle}'  key='hour' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='feature' /> </div>
                            <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='With.intelligent.color.display.function' />  </div>
                        </div>

                    </div>
                    </div>

            </div>
            <div class="tab-pane" id="panel-58793">

                <div class="img-responsive wow fadeInUp animated row " style="width: 100%;" ">

                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color:; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Hardware' />  </div>
                            <div style="" class="col-xs-8 ">  </div>
                        </div>


                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='processor' />  </div>
                            <div style="" id="privatekey" class="col-xs-8 "><fmt:message bundle='${pageScope.bundle}'  key='Qualcomm' /> 28nm MDM9X15</div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Network.type' />  </div>
                            <div style=""id="userame" class="col-xs-8 "><fmt:message bundle='${pageScope.bundle}'  key='China.Unicom' />/<fmt:message bundle='${pageScope.bundle}'  key='China.Mobile' /> 3G/4G <fmt:message bundle='${pageScope.bundle}'  key='China.Telecom' />4G <fmt:message bundle='${pageScope.bundle}'  key='network' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='display.screen' /> </div>
                            <div style="" class="col-xs-8 "id="idcard">1.44<fmt:message bundle='${pageScope.bundle}'  key='Foot' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Key' /></div>
                            <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Power.button' /> / <fmt:message bundle='${pageScope.bundle}'  key='WPS.switch' />/ <fmt:message bundle='${pageScope.bundle}'  key='reset.button' /> </div>
                        </div>

                    </div>

                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Wireless.protocol' /> </div>
                            <div style="" class="col-xs-8 "id="idcard">802.11b/g/n  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                        <div class="row">
                            <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4"><fmt:message bundle='${pageScope.bundle}'  key='Battery' /> </div>
                            <div style="" class="col-xs-8 "id="idcard">2100<fmt:message bundle='${pageScope.bundle}'  key='Ma' />  </div>
                        </div>

                    </div>
                    <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                <div class="row">
                    <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='port' /></div>
                    <div style="" class="col-xs-8 "id="idcard">USB  </div>
                </div>

            </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='antenna' /></div>
                        <div style="" class="col-xs-8 "id="idcard"> <fmt:message bundle='${pageScope.bundle}'  key='built-in' /> </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Power.Supply' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='input' />：5V DC+/-10%<br>
                        Output:1.0A</br><fmt:message bundle='${pageScope.bundle}'  key='input' />100V-240V 50HZ-60HZ</div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='working.temperature' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">-10℃-60℃  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Operating.humidity' /></div>
                        <div style="" class="col-xs-8 "id="idcard">5%-65%  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Storage.temperature' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">-30℃-80℃  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Storage.humidity' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">5%-95%  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color:; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Software.information' /></div>
                        <div style="" class="col-xs-8 "id="idcard">  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Support.operating.system' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">Win7,XP ,Vista, Mac os  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Support.language' /> </div>
                        <div style="" class="col-xs-8 "id="idcard"> <fmt:message bundle='${pageScope.bundle}'  key='Chinese/English' /> </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Intervening.quantity' /></div>
                        <div style="" class="col-xs-8 "id="idcard">10个</div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Personalization' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='message' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Telephone.directory' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 ">APN <fmt:message bundle='${pageScope.bundle}'  key='setting' /> </div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /> </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 ">WIFI <fmt:message bundle='${pageScope.bundle}'  key='setting' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                <div class="row">
                    <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='devices.setting.up' /></div>
                    <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /> </div>
                </div>

            </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='route.configuration' /> </div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' /> </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Firewall.settings' /></div>
                        <div style="" class="col-xs-8 "id="idcard"><fmt:message bundle='${pageScope.bundle}'  key='Support' />  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: ; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Other' /></div>
                        <div style="" class="col-xs-8 "id="idcard"></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Size' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">98mm*59mm*14.8mm  </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Weight' /> </div>
                        <div style="" class="col-xs-8 "id="idcard">90g </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "><fmt:message bundle='${pageScope.bundle}'  key='Standby.time' /></div>
                        <div style="" class="col-xs-8 "id="idcard">50<fmt:message bundle='${pageScope.bundle}'  key='hour' /></div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #8e8d8e; padding-right: 0" class="col-xs-4 "> <fmt:message bundle='${pageScope.bundle}'  key='Length.of.usage' /></div>
                        <div style="" class="col-xs-8 "id="idcard">60<fmt:message bundle='${pageScope.bundle}'  key='hour' /> </div>
                    </div>

                </div>


                </div>
            </div>
            <div class="tab-pane" id="panel-485241">
                <div class="img-responsive wow fadeInUp animated row " style="width: 100%;padding: 3rem 1rem 3rem  3rem;color: #8e8d8e;" ">
                    <p><fmt:message bundle='${pageScope.bundle}'  key='Step.one.access.the.service.to.activate.the.URL' /><a href="<%=basePath%>deviceActivation/index" style="color:blue">  <fmt:message bundle='${pageScope.bundle}'  key='Activation' /></a>
                        </p>
                    <p><fmt:message bundle='${pageScope.bundle}'  key='Step.two.log.in.using.Lenovo.ID' /></p>
                <img    class="img-responsive wow fadeInUp animated " alt="Responsive image"
                        style="padding: 1rem 5rem; width: 100%;animation-delay:0.25s; animation-name: fadeInUp;background-color: #ffffff;margin-top: 0.2vw;margin-bottom: 1vw;" src="static/h5/images/log.png">
                <p><fmt:message bundle='${pageScope.bundle}'  key='Step.three.accessing.the.service.activation.URL' /><br>
                    &nbsp;&nbsp;&nbsp; 1. <fmt:message bundle='${pageScope.bundle}'  key='Enter.device.number,activation.code,real.name,valid.ID.card' /><br>
                    &nbsp;&nbsp;&nbsp; 2. <fmt:message bundle='${pageScope.bundle}'  key='Read.the.web.service.agreement' /><br>
                    &nbsp;&nbsp;&nbsp; 3. <fmt:message bundle='${pageScope.bundle}'  key='Click.activation.to.prompt.activation.to.return.to.login.home.page' /><br>
                </p>
                <img    class="img-responsive wow fadeInUp animated " alt="Responsive image"
                        style="padding: 1rem 5rem; width: 100%;animation-delay:0.25s; animation-name: fadeInUp;background-color: #ffffff;margin-top: 0.2vw;margin-bottom: 1vw;" src="static/h5/images/jihuo.png">
                <p style="font-size: 3.3vw"><br><fmt:message bundle='${pageScope.bundle}'  key='considerations' /><br><br>
                    &nbsp;&nbsp;&nbsp; 1.<fmt:message bundle='${pageScope.bundle}'  key='Registration.Lenovo.ID.must.use.mobile.phone.number.that.real.name.registered' /><br>
                    &nbsp;&nbsp;&nbsp;  2.<fmt:message bundle='${pageScope.bundle}'  key='For.the.first.time.login.after.successful' /><br>
                    &nbsp;&nbsp;&nbsp;  3.<fmt:message bundle='${pageScope.bundle}'  key='recharge' />：<a href="<%=basePath%>paymentService/topay" style="color:blue">  <fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' /></a>
                </p>
            </div>
            </div>

        </div>
    </div>
</div>
</html>