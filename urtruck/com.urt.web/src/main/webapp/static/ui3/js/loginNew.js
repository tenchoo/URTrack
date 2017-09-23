$(document).ready(function(){
	//页面加载完或者窗口改变大小的时候调整高度
	setScreenHeight();
	$(window).resize(function(event) {
		setScreenHeight();
	});
     //错误提示显示
     if($(".popup").children().text()!=null && $(".popup").children().text()!=''){
 		$(".popup").show();
 	 } 
	//登录密码验证
   /* $("#pwd").blur(function(){
        var regTL=/^[a-zA-Z\d_]{8,}$/;
        regTest(regTL,"#pwd","密码为数字字母组合，不能含有文字");
    });*/
	
    //登录注册切换
    loginChange();

    //二维码
    qrLogin();

    //欢迎登录弹窗
    ejectModal();

    //登录提交为空判断
    checkTrim('#btnLogin','.required','请填写账号信息');
    
  //点击提示信息后返回

    $('.popup').on('click',function(){
        $('.popup').hide();//提示信息隐藏
        $('.popup').children().text('');
    })
    
    $("#btnLogin").on('click',function(){
    	$("#user_from").submit();
    });
    
    

});
//报错信息
function popup(){
	if($(".popup").children().text()!=null && $(".popup").children().text()!=''){
		$(".popup").show();
	} 
}

//规则校验
function regTest(regEx,intID,msgInfo){
    var reg=regEx;
    var intVal=$(intID).val();
    if(!reg.test(intVal)){
        $('.popup span').html(msgInfo);
        $('.popup').show();//提示信息显示
         return false;
    }
}

//动态设置显示为全屏显示
function setScreenHeight(){
	var w_h = $(window).height();
	var w_w = $(window).width();
	var f_h = $('.footer').outerHeight();
	$('.content').height(w_h-f_h);
	$('.wrapper').width(w_w);
	$('.wrapper').height(w_h);
	$('.wrapper .bg').css({'width':w_w,'height':w_h});
}

//登录注册为空判断
function checkTrim(btnId,intClass,msgInfo){
//btnId=登录按钮,intClass=登录表单,msgInfo=表单为空时的提示信息
    $(btnId).on('click',function(){
        $(intClass).each(function(){
            if($(this).val().length==0){
                $('.popup span').html(msgInfo);
                $('.popup').show();//提示信息显示
            }
        }) 
       
    })
    
}

//登录注册切换状态
function loginChange(){
    $('.loginMenu').on('click',function(){
        $('.loginMenu').removeClass('active');
        $(this).addClass('active');
        $('.tabForm').hide();
        $('.tabForm').eq($(this).index()).show();
    })
}

/**
    * 二维码切换登录
**/
function qrLogin(){
    $('#iconQrCode').on('click',function(){
    	$.get("/glaH5App/getQrCodeImg", function(data, status) {
			var obj = eval("(" + data + ")");
			//存储UUID
			uuid = obj.uuid;
			//显示二维码
			$("#QrCodeImg").attr("src", obj.qrCodeImg);
			//开始验证登录
			t1 = window.setInterval("validateLogin()",3000); 
			
			//结束验证
			window.setTimeout("stopVali()",1000000)
		});
        $('.loginMenu').hide();//登录注册菜单隐藏
        $('.qrCode').show();//二维码菜单显示
        $('.qrCode').addClass('active');//二维码登录菜单加当前状态
        $('.tab_main').hide();//登录注册项隐藏
        $('.qrBox').show();//二维码图片
        $('#iconQrCode').hide();//右上角二维码登录图标消失
        $('#iconComp').show();//右上角电脑登录显示
    });
    $('#iconComp').on('click',function(){
        $('#iconQrCode').show();//右上角二维码登录图标消失
        $('#iconComp').hide();//右上角电脑登录隐藏
        $('.loginMenu').show();//登录注册菜单显示
        $('.qrCode').hide();//二维码菜单隐藏
        $('.qrBox').hide();//二维码图片
        $('.tab_main').show();//登录注册项显示

    });
}
/**
 * 欢迎登录弹窗
 */
function ejectModal(){
//    $('.qrImgBox').on('click',function(){
//        $('.modalBox').show();
//        $('.form_info').hide();
//    });
    $('.closeBtn').on('click',function(){
        $('.modalBox').hide();
        $('.form_info').show();
    })
}
/**
 * tian
 * 检查是否登陆
 */
function validateLogin(){
	$.get("/glaH5App/loginCheck?uuid="+uuid , function(data, status) {
		var obj = eval("(" + data + ")");
		if(obj.redirect == "banding"){
			window.clearInterval(t1);
			$('.modalBox').show();
			$('.form_info').hide();
			$('#nickname').html(obj.nickname);
		}else if(obj.redirect == "error"){
			
		}else{
			window.clearInterval(t1);
			location.href = obj.redirect;
		}
	});
}
function stopVali(){
	window.clearInterval(t1);
	$('.modalBox').hide();
	$('.form_info').show();
}