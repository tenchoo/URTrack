<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
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
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <base href="<%=basePath%>" /> 
    <title><fmt:message bundle='${pageScope.bundle}'  key='Device.activation' /></title>
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
    <link href="static/h5/css/style.css" rel="stylesheet" type="text/css" media="all" />
    <script src="static/h5/js/wow.min.js"></script>
    <link href="static/h5/css/animate.css" rel='stylesheet' type='text/css' />
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <style>h5{font-size: 3.8vw}
    button{font-size: 4vw}</style>
    <link rel="stylesheet" href="static/h5/css/base.css">
    <link rel="stylesheet" type="text/css" href="static/h5/css/internet.css">
    <link rel="stylesheet" type="text/css" href="static/h5/css/error.css">
    <script type="text/javascript" src="static/h5/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="static/h5/js/md5.js"></script>
    <script type="text/javascript" src="static/h5/js/url.js"></script>
    <script type="text/javascript" src="static/h5/js/validation.js"></script>
    <script type="text/javascript" src="static/h5/js/layer.js"></script>
    <link rel="stylesheet" href="static/h5/css/layer.css" id="layui_layer_skinlayercss">
    <script type="text/javascript" src="static/h5/js/idcard.js"></script>
    <script type="text/javascript">
        new WOW().init();
        $(function(){
        	var iccidInput=$('[name="iccid"]');
             var error_box=$(".error-box");
             var usernameInput=$("#username input");
             var usernameError=$(".userameError");
             var idcardInput=$("#idcard input");
             var idcardError=$(".idcardError");
            var reg=/[\u4e00-\u9fa5]/g;
            var errorFlag=false;
            $("#activeBtn").click(function(){
                var ss=$(".checkbox input").attr("checked");
                if(!ss){
                    alert("<fmt:message bundle='${pageScope.bundle}'  key='please.agree.access.agreement' />");
                    return;
                }
                //iccid验证
                var iccid=iccidInput.charValidation();
                var mache=/^[0-9]{19}$/;
                var mache2=/^[0-9]{20}$/;
                if(!mache.test(iccid) && !mache2.test(iccid)){
                    error_box.css({
                        "display":"inline"
                    })
                    error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='please.enter.correct.device.number' />");
                    errorFlag=true;
                }
                //姓名验证
                var username=usernameInput.charValidation();
                if(!(/^[\u4e00-\u9fa5]{2,5}$/).test(username)){
                    usernameError.html("<fmt:message bundle='${pageScope.bundle}'  key='The.name.must.be.Chinese.and.between.in.2-5.numbers' />");
                    usernameError.css({
                        "display":"inline"
                    })
                    errorFlag=true;
                }
                //身份证验证
                var idcardValue=idcardInput.charValidation();
                if (idcardValue==''){
                    idcardError.html("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.correct.identification.number' />");
                    idcardError.css({
                        "display":"inline"
                    })
                    errorFlag=true;
                }
                if(!idCardNoUtil.checkIdCardNo(idcardValue)){
                    idcardError.html("<fmt:message bundle='${pageScope.bundle}'  key='Please.check.your.ID.number' />");
                    idcardError.css({
                        "display":"inline"
                    })
                    errorFlag=true;
                }
                $(this).attr({
                    disabled:'disabled'

                })
                //按钮变成灰色 不可点击
                $(this).css({
                    'background-color':'#8D8D8D'
                })
                if(errorFlag){
                    $(this).removeAttr("disabled");
                    $(this).css({
                        'background-color':'#e2231a'
                    })
                    return;
                }

                var data={'iccid':iccid,'realname':username,'idnum':idcardValue};
                var msg=layer.msg('<fmt:message bundle="${pageScope.bundle}"  key="Activated.If.long.time.no.response,please.reactivate" />', {
                    icon: 4,
                    time: -1 //2秒关闭（如果不配置，默认是3秒）
                }, function(){

                });
                
                $.ajax({
                    type:"post",
                    url:"<%=basePath%>glaH5AppActive/activeService",
                    data:data,
                    cache: false,
                    success:function(data){
                        /**
                         {"iccid":"8986061501000889175","nacid":"apn1","retcode":"2"}
                         激活状态：1、激活成功；
                         2、已激活；
                         0、激活失败,
                         -1(参数不全)，
                         -2(st校验失败),
                         -4(系统异常),
                         -5(签名错误)，
                         -6(iccid已经被其他账户绑定)
                         -7(非法iccid)
                         -8(密钥与iccid不匹配)
                         */
                        var jsonObj=eval("("+data+")");
                        var statusCode=parseInt(jsonObj.retcode);
                        switch (statusCode) {
                            case 0:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
                                break;
                            case 1:
                            	window.location.href="<%=basePath%>glaH5AppActive/activeSuccess?iccid="+iccid;
                            	break;
                            case 2:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='The.device.has.been.activated' />");
                                break;
                            case -1:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='Parameter.incomplete' />");
                                break;
                            case -2:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='Service.verification.failed.Please.login.again' />");
                                break;
                            case -4:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                //error_box.html("非法iccid");
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='The.device.number.does.not.exist.or.is.incorrect' />");
                                break;
                            case -5:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='Signature.error' />");
                                break;
                            case -6:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='iccid.has.been.bound.by.another.account' />");
                                break;
                            case -7:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                //error_box.html("非法iccid");
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='The.device.number.does.not.exist.or.is.incorrect' />");
                                break;
                            case -8:
                                privateKeyError.css({
                                    "display":"inline"
                                })
                                /* privatekeyInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                }) */
                                privateKeyError.html("<fmt:message bundle='${pageScope.bundle}'  key='The.activation.code.does.not.match.the.device.number' />");
                                break;
                            case -9:
                                error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='iccid.Equipment.is.not.belong.to.mifi.equipment' />");
                                break;
                            case -10:
                            	 error_box.css({
                                     "display":"inline"
                                 })
                                 iccidInput.css({
                                     "background-color":"#e2231a",
                                     "color":"#ffffff"
                                 })
                                 error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='The.card.has.been.activated' />");
                                 break;
                            case -11:
                           	 error_box.css({
                                    "display":"inline"
                                })
                                iccidInput.css({
                                    "background-color":"#e2231a",
                                    "color":"#ffffff"
                                })
                                error_box.html("iccid <fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
                                break;
                        }
                        $("#activeBtn").removeAttr("disabled");
                        layer.close(msg);
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown){
                        $("#activeBtn").removeAttr("disabled");
                        layer.close(msg);
                        alert("<fmt:message bundle='${pageScope.bundle}'  key='Activation.timeout,please.log.in.again' />!!");
                    }
                })
            })

            iccidInput.focus(function(){
            	error_box.show();
            	error_box.hide();
            });
           
            usernameInput.focus(function(){
            	usernameError.show();
            	usernameError.hide();
            });
            idcardInput.focus(function(){
            	idcardError.show();
            	idcardError.hide();
            });
            //input焦点离开事件
            iccidInput.blur(function(){
            	 errorFlag=false;
            	 var mache=/^[0-9]{19}$/;
            	 var mache2=/^[0-9]{20}$/;
                if(!mache.test($(this).charValidation()) && !mache2.test($(this).charValidation())){
                error_box.css({
                "display":"inline"
                })
                error_box.html("<fmt:message bundle='${pageScope.bundle}'  key='Please.input.a.valid.iccid' />");
                }
            })
           
            usernameInput.blur(function(){
                errorFlag=false;
                var mache=/^[\u4e00-\u9fa5]{2,5}$/;
                if(!mache.test($(this).charValidation())){
                usernameError.css({
                "display":"inline"
                })
                usernameError.html("<fmt:message bundle='${pageScope.bundle}'  key='The.name.must.be.Chinese.and.between.in.2-5.numbers' />");
                }
            })
            idcardInput.blur(function(){
                errorFlag=false;
                if(!idCardNoUtil.checkIdCardNo($(this).charValidation())){
                idcardError.html("<fmt:message bundle='${pageScope.bundle}'  key='Please.check.your.ID.number' />");
                idcardError.css({
                "display":"inline"
                })
                }
            })
        })
    </script>
    <style>
        select {
            -webkit-appearance: menulist;
            align-items: center;
            white-space: pre;
            -webkit-rtl-ordering: logical;
            background-color: white;
            cursor: default;
            border: none;
        }
    </style>
</head>
<body>
<div style=" font-size:3.8vw; ">
	<jsp:include page="header.jsp" flush="true"/> 
    <div  style="width: 100%;" class="row">

        <div style="font-size: 4vw; padding-top:2rem;padding-bottom:2rem;text-align: center;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 "><fmt:message bundle='${pageScope.bundle}'  key='Activate.new.device' /></div>
    </div>
    <div  style="width: 100%;" class="row">

             <form>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                   <div class="row">
                       <div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='device.number' />  </div>
                       <div style="" class="col-xs-8 ">
						   	<input style="width: 100%;border: hidden;background: none;" type="text" name="iccid" value="${iccid}">
                       </div>
                   </div>
                    <div class="row">
                        <div style="padding-top: 5px; font-size: 3.5vw;color:  #e2231a;" class="error-box col-xs-8 col-xs-offset-2"><fmt:message bundle='${pageScope.bundle}'  key='The.device.has.been.activated' /> </div>
                    </div>

                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1; display:none" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='activation.code' />  </div>
                        <div style="" id="privatekey" class="col-xs-8 "><input style="width: 100%;border: hidden;background: none;" type="text" name="privatekey">  </div>
                    </div>
                    <div class="row">
                        <div style="padding-top: 5px; font-size: 3.5vw;color:  #e2231a;" class="privatekeyError col-xs-8 col-xs-offset-2"><fmt:message bundle='${pageScope.bundle}'  key='please.enter.activation.code' /></div>
                    </div>
                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='name' /></div>
                        <div style=""id="username" class="col-xs-8 "><input style="width: 100%;border: hidden;background: none;" type="text" id="username">  </div>
                    </div>
                    <div class="row">
                        <div style="padding-top: 5px; font-size: 3.5vw;color:  #e2231a;" class="userameError col-xs-8 col-xs-offset-2"><fmt:message bundle='${pageScope.bundle}'  key='The.name.must.be.Chinese.and.between.in.2-5.numbers' /> </div>
                    </div>
                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #5e5e5e; padding-right: 0" class="col-xs-3 "><fmt:message bundle='${pageScope.bundle}'  key='ID.card' />  </div>
                        <div style="" class="col-xs-8 "id="idcard"><input style="width: 100%;border: hidden;background: none;" type="text"  name="idcard">  </div>
                    </div>
                    <div class="row">
                        <div style="padding-top: 5px; font-size: 3.5vw;color:  #e2231a;" class="idcardError col-xs-8 col-xs-offset-2"> <fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.correct.identification.number' /></div>
                    </div>
                </div>
                <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1; display:none" class="col-xs-11 col-xs-offset-1 ">
                    <div class="row">
                        <div style="color: #5e5e5e; padding:3px 0 0 15px;" class="col-xs-3 ">&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Type' /> </div>
                        <div style="" class="col-xs-8 ">
                            <div class="form-text">
                                <select style="border: none; width: 100%;background: none;" id="deviceType">
                                    <option style="border: none"value="">-<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.activation.device.types' />-</option>
                                    <option style="border: none"value="mifi" selected=""><fmt:message bundle='${pageScope.bundle}'  key='pc.partners' /></option>
                                    <option style="border: none" value="pad"><fmt:message bundle='${pageScope.bundle}'  key='tablet.computer' /></option>
                                </select>
                                <div class="deviceTypeError"></div>
                            </div>
                        </div>
                    </div>
                </div> 

            </form >

        
        <div style="padding-top:18px;padding-bottom:16px;border-bottom:1px  solid #dfe4e1" class="col-xs-11 col-xs-offset-1 ">
            <div class="row">
                <div style="color: #5e5e5e; padding-right: 0" class="col-xs-8 ">
                    <div class="checkbox">
                        <label>
                            <input  type="checkbox" value="" checked>
                          <p style="font-size: 3.3vw;padding-top: 1vw"> <fmt:message bundle='${pageScope.bundle}'  key='I.have.read.and.agreed.to.the.access.agreement' /></p>
                        </label>
                    </div>
                </div>
                <div style="padding-left: 0.5rem;margin-left:0rem;padding-right: 0;margin-right: 0" class="col-xs-2 col-xs-offset-1" >
                <a href="<%=basePath%>galH5AppActive/protocol">
                    <img style="margin:10px 0 0 0;width:18px;" src="static/h5/images/deal.png" class="img-responsive" alt="Responsive image">
                </a>
                </div>
            </div>


        </div>
       <div class="row">
            <div style="margin-bottom: 5rem; color: #9d9d9d" class="col-xs-11 col-xs-offset-1">
                <p style="margin: 6vw;margin-top: 8vw" ><fmt:message bundle='${pageScope.bundle}'  key='explain' />：</p>


                <p style="margin: 5vw;font-size: 3.4vw;line-height: 8vw;">
                    1.设备号及激活码位于设备后盖内，请正确填写；</br>
                    2.上网卡一旦激活将不支持退卡操作 ；</br>
                    3.如设备终端存在问题请直接到销售点进行检测处理。</br>
                    4.根据国家通信管理局相关政策规定，同时也保障用户自身的权益，懂的通信上网卡严格按照国家规定执行入网实名制。请务必正确填写使用人真实的身份证信息。</br>
                    5.对于上网卡无法激活或使用过程中存在异常,可直接致电懂的通信客服热线4006410041。</p>



            </div>
        </div>
    </div>

    <div style="margin-top: 30vh" class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div style="border-bottom: none" class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div style="text-align: left" class="col-xs-2 col-xs-offset-4"> <img src="static/h5/images/g.png" style="height: 5rem"></div>

                </div>
                <div   class="modal-body">
                    <div class="row"><div class="col-xs-10 "></div> </div>
                    <div style="margin-top: 3rem; text-align: center" class="row">
                        <div class="col-xs-10 col-xs-offset-1">
                            <p style="color: #353433; "><fmt:message bundle='${pageScope.bundle}'  key='Congratulations.on.your.successful.activation' />！</p>
                        </div>
                    </div>

                </div>
                <div style="border-top: none"class="modal-footer">
                    <div class="row">
                        <div class="col-xs-5">
                            <button style="color:#ffffff;border-radius: 10px;text-align: center;background-color: #fa9148; " type="button" class="btn " onclick="window.location.href='<%=basePath%>glaH5AppQuery/index'" ><fmt:message bundle='${pageScope.bundle}'  key='View.traffic' /></button>
                        </div>
                        <div class="col-xs-6">
                            <button style="color:#ffffff;border-radius: 10px;text-align: center;background-color: #fa9148; " type="button" class="btn " onclick="window.location.href='<%=basePath%>glaH5App/index'" ><fmt:message bundle='${pageScope.bundle}'  key='Home' /></button>
                        </div>
                    </div>


                </div>
            </div>
        </div>
    </div>

    <nav class="navbar navbar-default navbar-fixed-bottom">
        <div style="text-align: center;padding: ;width: 100%" class="container">
            <button style="font-size:4vw;width: 70vw;margin: 0.8rem auto; color:#ffffff;border-radius: 10px;text-align: center;background-color: #fa9148; " type="button" class="btn " id="activeBtn">
                <fmt:message bundle='${pageScope.bundle}'  key='Activation' /></button><!-- data-toggle="modal" data-target="#myModal" -->
        </div>
    </nav>
</div>
<!-- <script type="text/javascript">
		$(document).ready(function(){
			var Ww = $(window).width();
			var Hh = $(window).height();

			$(".close").click(function(){
				$(".mark").hide();
				$(".agreementBox").hide();
			})
			$("#agreement").click(function(){
				$(".mark").css("height",Hh);
				var lft= (Ww - $(".agreementBox").width())/2;
				$(".agreementBox").css("left",lft);
				$(".mark").show();
				$(".agreementBox").show();
			})

			$(".checkbox").click(function(){
				if ($("#checkbox1").attr("checked")) {
					$(this).removeClass("checked");
					$("#checkbox1").attr("checked",false);
				}else{
					$(this).addClass("checked");
					$("#checkbox1").attr("checked",true);
				}
			})
		})
	</script> -->
</body>
</html>