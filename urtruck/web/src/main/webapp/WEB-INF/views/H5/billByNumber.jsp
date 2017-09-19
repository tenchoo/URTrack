<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <title><fmt:message bundle='${pageScope.bundle}'  key='Consumer.inquiries' /></title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="/static/h5/css/bootstrap.min.css" type="text/css">
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!--<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">-->
	<link rel="stylesheet" href="/static/h5/css/bootstrap-theme.min.css" type="text/css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!--<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>-->
    <script src="/static/h5/js/jquery-1.12.4.min.js"></script>
    
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <!--<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
	<script src="/static/h5/js/bootstrap.min.js"></script>

    <link href="/static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />

    <style>
        *{
            font-family: "微软雅黑", "Microsoft Yahei", Arial, Helvetica, sans-serif, "宋体";
        }
        h5{font-size: 3.3vw}
        button{font-size: 4vw}
    </style>

    <script src="/static/h5/js/main.js"></script>

    <style>button{font-size: 4vw}</style>
  <script type="text/javascript">
  	var map = {};
    var mapYearMonth;
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	function change2(month){
		$("#yearMonthId").empty();
		$("#yearMonthId").append($("#"+month).val());
		month = month.split("月")[0];
		var list = map[month];
		$("#divxy").empty();
		for (var i = 0; i < list.length; i++) {
			$("#divxy").append("<div style='border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;' class='col-xs-12' ><div style='color: #8c8c8c;margin:  1.5rem auto' class='row'><div style='padding-right: 0; margin-right:0;text-align: center' class='col-xs-3'><h5>"
			+list[i].itemName+"</h5></div><div style='padding-right: 0; margin-right:0;text-align: center' class='col-xs-5'><h5>"
			+list[i].feeName+"</h5></div><div style='padding-right: 0;text-align: center' class='col-xs-4'><h5 style='padding-right: 0;'>"
			+list[i].discnt+"</h5></div></div></div>");
		}
	}
	
    $(function(){
		var defaultStart=new Date();
		var defaultDate=defaultStart.Format("yyyy/MM/dd");
		$("#nowDate").html(defaultDate);
    	$.ajax({
			url:"${ctx}/batchFtpImport/billByAjaxNumber",
			type:"post",
			dataType: "json",
			data:{
			},
			success : function(data) {
				map = data;
				var defaultStart = new Date();
				var month = defaultStart.getMonth();
				month = (month<10 ? "0"+month:month)+"月"; 
				if("00月" == month){
					month = "12月";
				}
				change2(month);
			}
		});
  });
  </script>

</head>
<body>
<div style="font-size: 3.3vw;" >
    <div style="width: 100vw">
        <div style="background-color: #c12e2a; margin:0 0 3vw 0;width: 100vw" class="row">

<div style="width: 100vw" class="row">
            <div style="font-size:3.4vw;color: #ffffff;margin: 1.5rem 3vw 4vw 3vw" class="col-xs-6 ">${number}， <fmt:message bundle='${pageScope.bundle}'  key='Hello' /></div>
    <div style="padding-left: ;" class="col-xs-5"><img src="${ctx}/static/h5/images/logo5.png"style="margin-top: 2.5vw;margin-left:1vw;height: 8vw"></div>
</div>
            <div style="font-size:4.4vw;color: yellow;margin: 3vw 3vw 3vw 1vw;border-radius: 10px;
            padding-top: 2px;padding-bottom:2px;margin-bottom: 0;margin-left:9px;
            margin-right:5px;text-align: center;padding-right: 2px;padding-left:2px "
                 class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='This.month.situation.of.usage' /> </div>
            <div style="font-size:4.4vw;color: #ffffff;margin: 3vw 3vw 3vw 3vw;text-align: center;padding-top: 0;
            margin-bottom: 5px;" class="col-xs-10 "><fmt:message bundle='${pageScope.bundle}'  key='Conversation' />${map.soundsDiscnt},<fmt:message bundle='${pageScope.bundle}'  key='message' />${map.messageDiscnt},<fmt:message bundle='${pageScope.bundle}'  key='data' />${map.flowDiscnt}</div>
            <div style="font-size:2.8vw;color: #ffffff;margin: 3vw 3vw 3vw 1vw;text-align: center;
            padding-top: 0;margin-top: 0;margin-bottom: 2rem;" class="col-xs-5 ">
            &nbsp;&nbsp;[<fmt:message bundle='${pageScope.bundle}'  key='deadline' /><span id="nowDate"></span>]
            </div>
        </div>
        <div class="tabbable" id="tabs-634545">
            <ul class="nav nav-pills">
                <li class="active">
                    <a  onclick="change2('${map.cycleIdM0}');" style="padding: 1vw 2vw" href="#panel-58792" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;" >&nbsp;&nbsp;<span id = "spanId1">${map.cycleIdM0}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM1}');" style="padding: 1vw 2vw"href="#panel-58793" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;" >&nbsp;&nbsp;<span id = "spanId2">${map.cycleIdM1}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM2}');" style="padding: 1vw 2vw"href="#panel-58794" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;<span id = "spanId3">${map.cycleIdM2}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li >
                    <a  onclick="change2('${map.cycleIdM3}');" style="padding: 1vw 2vw" href="#panel-58795" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;<span id = "spanId4">${map.cycleIdM3}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM4}');" style="padding: 1vw 2vw"href="#panel-58796" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;<span id = "spanId5">${map.cycleIdM4}</span>&nbsp;&nbsp;</p></a>
                </li>
                <li>
                    <a  onclick="change2('${map.cycleIdM5}');" style="padding: 1vw 2vw"href="#panel-58797" data-toggle="tab">
                        <p style="text-align: center;padding-top: 1vw;">&nbsp;&nbsp;<span id = "spanId6">${map.cycleIdM5}</span>&nbsp;&nbsp;</p></a>
                </li>
            </ul>
            <div style="background-color: #ffffff;" class="tab-content" id = "xiaofeidiv">
                <div class="tab-pane active" id="panel-58792">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                    <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem"><span id="yearMonthId">${map.cycleIdYM0 }</span><fmt:message bundle='${pageScope.bundle}'  key='The.consumer.record.is.as.follows' />:</p>
                        </div>
                </div>
                <div>
                	<input id="${map.cycleIdM0}" type="hidden" value="${map.cycleIdYM0 }"/>
                	<input id="${map.cycleIdM1}" type="hidden" value="${map.cycleIdYM1 }"/>
                	<input id="${map.cycleIdM2}" type="hidden" value="${map.cycleIdYM2 }"/>
                	<input id="${map.cycleIdM3}" type="hidden" value="${map.cycleIdYM3 }"/>
                	<input id="${map.cycleIdM4}" type="hidden" value="${map.cycleIdYM4 }"/>
                	<input id="${map.cycleIdM5}" type="hidden" value="${map.cycleIdYM5 }"/>
                </div>
         <%--         <div class="tab-pane active" id="panel-58793">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                        <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem">${map.cycleIdYM1 }消费记录如下:</p>
                    </div>
                </div>
                <div class="tab-pane active" id="panel-58794">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                        <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem">${map.cycleIdYM2 }消费记录如下:</p>
                    </div>
                </div>
                <div class="tab-pane active" id="panel-58795">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                        <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem">${map.cycleIdYM3 }消费记录如下:</p>
                    </div>
                </div>
                <div class="tab-pane active" id="panel-58796">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                        <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem">${map.cycleIdYM4 }消费记录如下:</p>
                    </div>
                </div>
                <div class="tab-pane active" id="panel-58797">
                    <div style="width: 96vw; background-color:#ffffff;margin: 1rem auto;padding-top: 1rem"  class="row" >
                        <p style="font-size: 3.8vw;color:#8c8c8c;margin-bottom: 1rem; margin-left: 1rem">${map.cycleIdYM5 }消费记录如下:</p>
                    </div>
                </div>  --%>

            </div>
        <div style="width: 96vw; background-color:#ffffff;margin: 0rem auto;padding-top: 0rem"  class="row" >
            <div id="page1">
                <div style="text-align: center" >
                
                    <div style="border-bottom:1px  solid #dfe4e1;margin:1rem 1rem 0rem 0rem; padding:1rem 0 1rem auto;" class="col-xs-12" >
                            <div style="margin:  1.5rem auto" class="row">
                                <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5><fmt:message bundle='${pageScope.bundle}'  key='Product.name' /></h5></div>
                                <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5><fmt:message bundle='${pageScope.bundle}'  key='Cost.name' /></h5></div>
                                <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;"><fmt:message bundle='${pageScope.bundle}'  key='Actual.amount.(yuan)' /></h5></div>
                            </div>
                        </div>
                    <div id = "divxy"></div>
                    <!-- <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >
                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>语音</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>本地语音主叫</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>
                        </div>
                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >
                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>语音</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>国内长途</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>
                        </div>
                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>语音</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>国内漫游</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>语音</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>虚拟网内本地</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>语音</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>短信</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>流量</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>全国流量</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>国际</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>国际漫游（语音）</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>国际</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>国际漫游（其他）</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >


                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>国际</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>国际长途</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>

                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >

                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>国际</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>呼叫转移（语音）</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>

                        </div>
                    </div>
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 0rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >

                        <div style="color: #8c8c8c;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>其他</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>其他</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>
                        </div>
                    </div> 
                    <div style="border-bottom:1px  solid #dfe4e1;margin:0rem 1rem 4rem 0rem; padding:auto 0 auto auto;" class="col-xs-12" >

                        <div style="color: #c12e2a;margin:  1.5rem auto" class="row">
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-3"><h5>合计</h5></div>
                            <div style="padding-right: 0; margin-right:0;text-align: center" class="col-xs-5"><h5>合计</h5></div>
                            <div style="padding-right: 0;text-align: center" class="col-xs-4"><h5 style="padding-right: 0;">11</h5></div>
                        </div>
                    </div> -->
            </div>
        </div>
    </div>
    </div>
</div>
</body>
</html>