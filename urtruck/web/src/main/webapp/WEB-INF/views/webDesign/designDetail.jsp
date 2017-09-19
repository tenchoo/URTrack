<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.io.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>企业网站定制详情页</title>
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/base.css" />
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/colpick.css" type="text/css"/>
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/common.css" />
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/com_detail_page.css" />
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/iconfont.css" />
		<script src="${ctx}/static/webDesign/js/jquery-1.11.3.min.js"></script>
		<script src="${ctx}/static/webDesign/js/colpick.js" type="text/javascript"></script>
		<script id="s1" src="${ctx}/static/webDesign/js/com_detail_page.js"></script>
	</head>
	<body>
		<div class="container">
			<div class="com_custom_header clearfloat">
				<div class="innerLeft fl">
					<img src="${ctx}/static/webDesign/img/网站定制图标.png" alt="" /><span>企业客户：</span>
					<input id="client_search1"  placeholder="请输入客户名称" type="tel" readOnly="readOnly"/>
					<button id="submit1">搜索</button>
					<div class="submit_menu2" id="append">
                        <ul>
                            <c:if test="${not empty custIds}">
                                <c:forEach items="${custIds}" var="custId">
                                    <li value="${custId.custId}">${custId.custName}</li>
                                </c:forEach>
                            </c:if>
                        </ul>
                    </div>
				</div>
				<a class="fr back" href="/webDesign/index">返回列表页</a>
				<a id="show" class="fr" href="#" onclick="document.getElementById('save').click()">预览</a>
				<a id="cancel" class="fr" href="/webDesign/index">取消</a>
				<a id="save" class="fr" href="/webDesign/index">保存</a>					
			</div>						
			<div class="com_custom_body clearfloat">
				<!--手机图区域-->
				<div id="content" class="fl">
					<div class="left_phone fl">
						<!--小懂充值-->
						<div class="phone_screen">
							<div class="close">
								<span class="back0"><</span><em>小懂充值</em><i class="iconfont">&#xe635;</i>
							</div>
							<div class="logo clearfloat">
								<h1 class="fl" ></h1>
								<i class="fr iconfont icon-menu">
									<ul>
										<li>充值</li>
										<li>查询</li>
										<li>登陆</li>
									</ul>
								</i>	
							</div>
							<div class="user_equip">
								<img src="${ctx}/static/webDesign/img/用户设备.jpg" alt="" />
							</div>
							<div class="pay clearfloat">
								<div class="fl">
									<h2>冲1G送1G</h2>
									<span class="small-font">￥40</span>
									<p class="small-font">有效期1个月</p>
								</div>
								<div class="fl">
									<h2>10G</h2>
									<span class="small-font">￥360</span>
									<p class="small-font">有效期12个月</p>
								</div>
							</div>
							<div class="sure_pay">
									<button>确认充值</button>
							</div>
						</div>
						<!--查询页面-->
						<div class="phone_screen unShow">
							<div class="close">
								<span class="back1"><</span><em>余额查询</em><i class="iconfont">&#xe61d;</i>
							</div>
							<div class="logo clearfloat">
								<h1 class="fl" ></h1>
								<i class="fr iconfont icon-menu">
									<ul>
										<li>充值</li>
										<li>查询</li>
										<li>登陆</li>
									</ul>
								</i>
							</div>
							<div>
								<img width="238" height="275" src="${ctx}/static/webDesign/img/查询页.jpg" alt="" />
							</div>
							<div class="sure_pay" style="margin-top: 0; background: #fff;">
								<button>立即充值</button>
							</div>
						</div>
						<!--用户登录页-->
						<div class="phone_screen unShow" style="background: #fff;">
							<i class="fr iconfont icon-menu"></i>
							<div class="close">
								<span class="back2"><</span><em>用户登录</em><i class="iconfont">&#xe635;</i>
							</div>
							<div class="logo clearfloat bigLogo">
								<h1 class="fl" ></h1>
							</div>
							<div>
								<img width="238" src="${ctx}/static/webDesign/img/登录图.fw.png" alt="" />
							</div>
							<div class="sure_pay" style="margin-top: 20px;">
								<button>登录</button>
							</div>
						</div>
					</div>
				</div>
				<input type="text" id="designId9" hidden value="">
				<input type="text" id="custId9" hidden value="">
				<input type="text" id="color9" hidden value="">
				<input type="text" id="image9" hidden value="">
				<input type="text" id="webUrl9" hidden value="">
                <input type="text" id="webContent9" hidden value="">
                <input type="text" id="value1" hidden value="">
                <input type="text" id="value2" hidden value="">
				<!--定制和部署区域-->
				<div class="right_feature fl">
					<div class="buttons clearfloat">
						<span class="active" href="">定制</span>
						<span class="ml10" href="">部署</span>
					</div>
					<!--定制-->
					<div class="custom clearfloat box">
						<p>
							<span class="cur">企业logo</span>
							<span>网站风格</span>
						</p>
						<!--企业logo-->
						<div class="upload inner_box">
							<img src="${ctx}/static/webDesign/img/logo.png" alt="" id="img0" style="width:255px;height:115px;"/>
						    <input type="file" name="upload" id="upload" onchange="uploadFile(this)" value="" style="display: none;" accept="image/gif,image/jpeg,image/jpg,image/x-ms-bmp,image/png,"/>
							<button onclick="document.getElementById('upload').click()">上传logo</button>
						</div>
						<!--网站风格-->
						<div class="web_style inner_box">
							<span class="color_text">颜色</span>
							<ul class="normal_use_color">
								<li style="margin-left: 15px;"></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
								<li></li>
							</ul>
							 <div id="picker01"></div>
						</div>
					</div>
					<!--部署-->
					<div class="deploy ml20 box">
						<p>
							<span class="cur">公众号配置</span>
							<span>二维码</span>
						</p>
						<div class="public_configuration inner_box">
							<div class="link">
								<form>
									<label for="">链接：</label>
									<input id="address" type="text" readonly="true" value="http://gla.lenovo.com" />
									<input type="button"  id="copy" class="copy_link" value="复制链接"/>
										<label style='vertical-align: top;' for="">说明：</label>
										<textarea id="txt" id="content0" readonly="true" ></textarea>
									<!-- <div class="number"><span id="word">100</span>/100</div>	--> 	
								</form>
							<!-- <button class="save">保存</button> -->    
							</div>
						</div>
						
						<div class="qr_code inner_box">
							<img src="${ctx}/static/webDesign/img/${imgPath}" alt="" id="QRcodeImg"/>
							<div><button class="add_icon" id="addImgToQR" onclick="document.getElementById('imgForQR').click()" >二维码添加图标</button>
							<input type="file" id="imgForQR" onchange="addImgForQrCode(this)" value="" style="display: none;" accept="image/gif,image/jpeg,image/jpg,image/x-ms-bmp,image/png,"/>
							</div>
							<div class="qr">
								<button class="qr_download" onclick="downLoadQR()">二维码下载</button>
			<!-- 				<ul>
									<li class="min"><i class="iconfont icon-xiazai"></i>&nbsp;258*258</li>
									<li class="big"><i class="iconfont icon-xiazai"></i>&nbsp;430*430</li> 
								</ul> -->   
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--遮罩层-->
			<div id="mask_layer" class="clearfloat">
				<div class="layer_left">
				</div>
				<div class="saoyisao">
					<img src="${ctx}/static/webDesign/img/${imgPath}" alt="" />
					<p>手机扫描二维码可在手机上预览</p>
					<span class="iconfont icon-guanbi"></span>
				</div>	
			</div>
		</div>
		
		<script>
			$('#picker01').colpick({
			    flat:true,
			    layout:'hex',
			    layout: 'full',
			    submitText: '确定',
			    height:156,
			    color: '007eeb',
			    onChange:function(hsb,hex,rgb,el,bySetColor) {
					$('.pay div.current').css({'border-color':'#'+hex,'color':'#'+hex});
					$('.sure_pay button').css({'background-color':'#'+hex});
					$('.left_phone .logo').css({"border-color":'#'+hex});//手机logo框的下边框
					$('.left_phone .logo .icon-menu ul,.icon-menu ul li').css({"border-color":'#'+hex});
				}
			});
		</script>
		<script>
			$(function(){
				var hex = "ff0000";
				$('.pay div.current').css({'border-color':'#'+hex,'color':'#'+hex});
                $('.sure_pay button').css({'background-color':'#'+hex});
                $('.left_phone .logo').css({"border-color":'#'+hex});//手机logo框的下边框
                $('.left_phone .logo .icon-menu ul,.icon-menu ul li').css({"border-color":'#'+hex});
				$('#color9').val(hex);
                $('#image9').val("logo.png");
                $('.logo h1').css({'background-image':'url(/static/webDesign/img/logo.png)'});
                $('#webUrl9').val("http://gla.lenovo.com");
                $('#webContent9').val("http://gla.lenovo.comhttp://gla.lenovo.comhttp://gla.lenovo.com");
                $('#address').val("http://gla.lenovo.com");
                $('#content0').text("http://gla.lenovo.comhttp://gla.lenovo.comhttp://gla.lenovo.com");
				<c:if test="${not empty webDesign}">
					var designId0 = "${webDesign.designId}";
					var custId0 = "${webDesign.custId}";
					var custName0 = "${webDesign.custName}";
					var color0 = "${webDesign.color}";
					var image0 = "${webDesign.image}";
					var webUrl0 = "${webDesign.webUrl}";
					var webContent0 = "${webDesign.webContent}";
					var value1 = "${webDesign.value1}";
					var value2 = "${webDesign.value2}";
	                $('#custId9').val(custId0);
					$('#client_search1').val(custName0);
					$('#client_search1').attr('value',custId0);
					$('#submit1').hide();
					if(designId0 != null && designId0 != ""){
						$('#submit1').hide();
						$('.pay div.current').css({'border-color':'#' + color0,'color':'#' +color0});
		                $('.sure_pay button').css({'background-color':'#' + color0});
		                $('.left_phone .logo').css({"border-color":'#' + color0});//手机logo框的下边框
		                $('.left_phone .logo .icon-menu ul,.icon-menu ul li').css({"border-color":'#' + color0});
		                $('.colpick_hex_field').find('input').val(color0);
		                $('.colpick_new_color').css({'background':'#' + color0});
		                $('.logo h1').css({'background-image':'url(/static/webDesign/img/'+image0+')'});
		                $('.upload').find('img').attr('src','/static/webDesign/img/' + image0);
		                $('#designId9').val(designId0);
		                $('#color9').val(color0);
		                $('#image9').val(image0);
		                $('#webUrl9').val(webUrl0);
		                $('#webContent9').val(webContent0);
		                $('#address').val(webUrl0);
		                
		                if(value1 != null && value1 != ""){
			                $('#QRcodeImg').attr('src','/static/webDesign/img/' + value1);
		                }
		                $('#value1').val(value1);
		                $('#value2').val(value2);
					}
                </c:if>; 
                //点击保存  $('#content0').val(webContent0);
                $('#save').on('click',function(){
                	var designId8 = $('#designId9').val();
                	var oneCustId = $('#client_search1').attr('value');
                    var custId8 = $('#custId9').val();
                    if(oneCustId != null && oneCustId != ""){
                    	custId8 = oneCustId;
                    }
                    var color8 = $('.colpick_hex_field').find('input').val();
                    var image8 = $('#image9').val();
                    var webUrl8 = $('#webUrl9').val();
                    var webContent8 = $('#webContent9').val();
                    var value1 = $('#value1').val();
                    var value2 = $('#value2').val();
                    if(custId8 == null || custId8 == ""){
                    	alert("请选择用户！");
                    	$('#client_search1').focus();
                    	return;
                    };
                    var formData = new FormData();
                    formData.append("designId",designId8);
                    formData.append("custId",custId8);
                    formData.append("color",color8);
                    formData.append("image",image8);
                    formData.append("webUrl",webUrl8);
                    formData.append("webContent",webContent8);
                    formData.append("value1",value1);
                    formData.append("value2",value2);
                    $.ajax({
                        url : "/webDesign/saveDesign",
                        type : "POST",
                        data : formData,
                        cache: false,
                        processData: false,
                        contentType: false,
                        success : function(data){
                            var path = data
                            if(path == "saved"){
			                    alert('保存成功，点击确定返回到列表');
                            }else{
                                alert("定制信息提交失败！");
                            }           
                        },
                        error : function(){
                            alert("定制信息提交错误！！！");
                        }
                    });
                });
                
                //复制链接
                $('#copy').on('click',function(){
                	var Url2 = document.getElementById('address');
                	Url2.select();
                	document.execCommand('copy'); // 执行浏览器复制命令
                	alert("已复制完成，可贴粘。");
                });
                
			});  
			
            //在二维码中间加图片
			function addImgForQrCode(logoFile){
				var custId = $('#custId9').val();
            	var file = logoFile.files[0];
            	var nameTmp = file.name.split(".");
                var fileType = nameTmp[nameTmp.length-1];
                if(file.size > 3*1024*1024 || (fileType!="png" && fileType!="jpg" && fileType!="gif" && fileType!="jpeg" && fileType!="bmp")){
                    alert("文件过大或文件格式错误，请重新选择小于3M,且为png,jpg,gif,jpeg,bmp类型的图片，谢谢！");
                    return;
                }
                var formData = new FormData();
                formData.append("logoFile",file);
                formData.append("custId",custId);
                $.ajax({
                    url : "/webDesign/buildQRWithLogo?v=" + new Date().valueOf(),
                    type : "POST",
                    data : formData,
                    cache: false,
                    processData: false,
                    contentType: false,
                    success : function(data){
                        var path = data
                        if(path != "failed"){
                            $('#QRcodeImg').attr("src", "/static/webDesign/img/" + path);
                            $('#value1').val(path);
                        }else{
                            alert("文件上传失败，请重新选择图片！");
                        }           
                    },
                    error : function(){
                        alert("文件上传失败，请重新选择正确图片！");
                    }
                });
            }
			
			function uploadFile(imgFile){
				var file = imgFile.files[0];
			    var designTmp = $('#designId9').val();
			    var formData = new FormData();
			    var nameTmp = file.name.split(".");
			    var fileType = nameTmp[nameTmp.length-1];
			    if(file.size > 3*1024*1024 || (fileType!="png" && fileType!="jpg" && fileType!="gif" && fileType!="jpeg" && fileType!="bmp")){
			    	alert("文件过大或文件格式错误，请重新选择小于3M,且为png,jpg,gif,jpeg,bmp类型的图片，谢谢！");
			    	return;
			    }
			    formData.append("upload", file);
			    formData.append("designId", designTmp);
				$.ajax({
			        url : "/webDesign/uploadImg?v=" + new Date().valueOf(),
			        type : "POST",
			        data : formData,
			        cache: false,
			        processData: false,
			        contentType: false,
			        success : function(data){
			        	var path = data
			            if(path != "false"){
			            	$('#img0').attr("src", "/static/webDesign/img/" + path);
			            	$('.logo h1').css({'background-image':'url(/static/webDesign/img/'+path+')'});
			            	$('#image9').val(path);
			            }else{
			            	alert("文件上传失败，请重新选择图片！");
			            }           
			        },
			        error : function(){
			        	alert("文件上传失败，请重新选择正确图片！");
			        }
			    });
			};
			
			function downLoadQR(){
				var fileName = $('#QRcodeImg').attr('src');
				var strs = fileName.split("/");
				fileName = strs[strs.length-1];
				alert(fileName);
				if(fileName == ""){
					alert("下载失败，请刷新页面后重试");
				}
				var url = '/webDesign/downLoadQR?fileName=' + fileName;
				window.location.href = url;
			}
		</script>
	</body>
</html>
