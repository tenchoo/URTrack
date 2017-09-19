$(document).ready(function(){

	//登录注册切换状态
	LoginRegistration.tabSwitch();

	//页面加载完或者窗口改变大小的时候调整高度
	LoginRegistration.setScreenHeight();
	$(window).resize(function(event) {
		LoginRegistration.setScreenHeight();
	});

	//修改默认设置
	LoginRegistration.defaultSettingOptional();

	//表单验证————登录验证
	formValidation.appendNode();
	formValidation.LoginValidation();
	//表单验证————注册验证
	formValidation.registerValidation();

	//二维码
	qrLogin();

	//欢迎登录弹窗
	ejectModal();


})
/**
 	* 二维码切换登录
**/
function qrLogin(){
	$('#iconQrCode').on('click',function(){
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
	$('.qrImgBox').on('click',function(){
		$('.modalBox').show();
		$('.form_info').hide();
	});
	$('.closeBtn').on('click',function(){
		$('.modalBox').hide();
		$('.form_info').show();
	})
}

/**
	*登录注册
**/
var LoginRegistration = (function(){

	//登录注册切换状态
	var tabSwitch = function (){
		var oLi = $('.tab_hd').find('li');
		var tab_con = $('.tab_main').find('.tab_con');
		oLi.each(function(){
			oLi.bind('click' , function(){
				var index = $(this).index();
				oLi.removeClass('active');
				tab_con.hide();
				$(this).addClass('active');
				$('.tab_main').find('.tab_con').eq(index).show();
				return false;
			})

		})
		jumpZc();
	};

	//还没注册
	var jumpZc = function(){
		$('.zc').bind('click' , function(){
			$('.tab_hd').find('li').removeClass('active').eq(1).addClass('active');
			$('.tab_main').find('.tab_con').hide().eq(1).show();
		})
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

	//设置是否同意默认设置
	function defaultSettingOptional(){
		$('.xy').bind('click' , function(){
			var imgObj = $(this).find('img');
			if($(imgObj).attr('src') == 'images/ok_icon.png'){
				$(imgObj).attr('src','images/ok_icon2.png');
				imgObj.attr('data-ok',false);
			}else{
				$(imgObj).attr('src','images/ok_icon.png');
				imgObj.attr('data-ok',true);
			}
		})
	}

	return {
		tabSwitch:tabSwitch,
		setScreenHeight:setScreenHeight,
		defaultSettingOptional:defaultSettingOptional
	}
}());



/**
	*表单验证
**/
var formValidation = (function(){

	//验证登录和注册公用的提示信息
	var appendNode = function(){
		$('body').append('<div class="popup" style="display:none"><span>错误提示信息</span></div>');
	}

	//登录验证
	var LoginValidation = function(){
		var falg1 = false;
		var falg2 = false;
		var falg3 = false;
		var message = $('.popup');
		var text_val = message.find('span');
		//特定时间隐藏错误提示信息
		var specifiedTimeHide = function(){
			setTimeout(function(){
				message.hide();
			},800)
		};

		//失去焦点验证账号
		$('#username').bind('blur' , function(){
			isCheckName($(this));
		});

		//验证密码
		$('#pwd').bind('blur' , function(){
			isCheckPwd($(this));
		});

		//验证验证码
		$('#yzm').bind('blur' , function(){
			isCheckYzm($(this));
		});

		//验证账号函数
		var isCheckName = function(obj){
			var objVal = $.trim(obj.val());
			var reg = /^1[34578]\d{9}$/ //验证用户名(手机号)正则
			if(objVal == ''){
				text_val.text( '账号不能为空' );
				message.show();
				specifiedTimeHide();
				falg1 = false;
			}else if( !reg.test(objVal) ){
				text_val.text( '账号输入有误 ( 账号为11位手机号 ) ' );
				message.show();
				specifiedTimeHide();
				falg1 = false;
			}else{
				falg1 = true;
			}
			checkDo();
		};

		//验证密码
		var isCheckPwd = function(obj){
			var objVal = $.trim(obj.val());
			var reg=/(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/;  //验证密码
			if(objVal == ''){
				text_val.text( '密码不能为空' );
				message.show();
				specifiedTimeHide();
				falg2 = false;
			}else if( !reg.test(objVal) ){
				text_val.text( '6～16位字符，至少包含数字、字母（区分大小写），符号中的2种' );
				message.show();
				specifiedTimeHide();
				falg2 = false;
			}else{
				falg2 = true;
			}
			checkDo();
		};

		//验证验证码
		var isCheckYzm = function(obj){
			var objVal = $.trim(obj.val());
			var yzmVal = $.trim($('.yzm').text());
			if(objVal == ''){
				text_val.text('验证码不能为空');
				message.show();
				specifiedTimeHide();
				falg3 = false;
			}else if(objVal.toLowerCase() != yzmVal.toLowerCase()){
				text_val.text('验证码输入有误');
				message.show();
				specifiedTimeHide();
				falg3 = false;
			}else{
				falg3 = true;
			}
			checkDo();
		};

		//如果没有填写表单提示
		$('.submit_btn').bind('click' , function(){

			if(!falg1 || !falg2|| !falg3){
				text_val.text('请填写表单信息');
				message.show();
				specifiedTimeHide();
			}
		});

		//验证是否所有信息有正确，正确可提交表单
		var checkDo = function(){
			if( falg1 && falg2 && falg3 ){
				$('.submit_btn').bind('click' , function(){
					subMitForm();
					/*
					var check = $('.agreement .xy img').attr('data-ok');
					console.log(check)
					if(check != 'false'){
						subMitForm();
					}else{
						text_val.text('请同意使我保持登录状态');
						message.show();
						specifiedTimeHide();
					};*/
				});
			}else{
				$('.submit_btn').unbind('click');
			};

		};

		//提交表单
		function subMitForm(){
			$('#user_from').submit();
		}
	};

	//注册验证
	var registerValidation = function(){
		var ok1 = false;
		var ok2 = false;
		var ok3 = false;
		var ok4 = false;

		var message = $('.popup');
		var text_val = message.find('span');
		//特定时间隐藏错误提示信息
		var specifiedTimeHide = function(){
			setTimeout(function(){
				message.hide();
			},800)
		};

		//失去焦点验证账号
		$('#phone').bind('blur' , function(){
			isMobilePhone($(this));
		});

		//验证密码
		$('#zc_pwd').bind('blur' , function(){
			isPassword($(this));
		});

		//确认密码
		$('#zc_pwd2').bind('blur' , function(){
			isPassword2($(this));
		});

		//验证验证码
		$('#zc_yzm').bind('blur' , function(){
			isCheckYzm($(this));
		});


		var phone = '18000000000'  //默认已注册的手机号
		//验证账号函数
		var isMobilePhone = function(obj){
			var objVal = $.trim(obj.val());
			var reg = /^1[34578]\d{9}$/ //验证用户名(手机号)正则
			if(objVal == ''){
				text_val.text( '账号不能为空' );
				message.show();
				specifiedTimeHide();
				ok1 = false;
			}else if( !reg.test(objVal) ){
				text_val.text( '账号输入有误 ( 账号为11位手机号 ) ' );
				message.show();
				specifiedTimeHide();
				ok1 = false;
			}else if( objVal == phone){
				text_val.text('手机号已注册');
				message.show();
				specifiedTimeHide();
				ok1 = false;
			}else{
				ok1 = true;
			}
			checkDo();
		};

		//验证密码
		var isPassword = function(obj){
			var objVal = $.trim(obj.val());
			var reg=/(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/;  //验证密码
			if(objVal == ''){
				text_val.text( '密码不能为空' );
				message.show();
				specifiedTimeHide();
				ok2 = false;
			}else if( !reg.test(objVal) ){
				text_val.text( '6～16位字符，至少包含数字、字母（区分大小写），符号中的2种' );
				message.show();
				specifiedTimeHide();
				ok2 = false;
			}else{
				ok2 = true;
			}
			checkDo();
		};

		//验证确认密码是否和第一次输入的密码一致
		var isPassword2 = function(obj){
			var pwdVal = $.trim($('#zc_pwd').val())
			var objVal = $.trim(obj.val());
			if(objVal == ''){
				text_val.text( '确认密码不能为空' );
				message.show();
				specifiedTimeHide();
				ok3 = false;
			}else if( objVal != pwdVal ){
				text_val.text( '二次密码输入错误' );
				message.show();
				specifiedTimeHide();
				ok3 = false;
			}else{
				ok3 = true;
			}
			checkDo();
		};

		//验证验证码
		var yzm = 'ssss'; //自定义验证码
		var isCheckYzm = function(obj){
			var objVal = $.trim(obj.val());
			if(objVal == ''){
				text_val.text('验证码不能为空');
				message.show();
				specifiedTimeHide();
				ok4 = false;
			}else if(objVal.toLowerCase() != yzm.toLowerCase()){
				text_val.text('验证码输入有误');
				message.show();
				specifiedTimeHide();
				ok4 = false;
			}else{
				ok4 = true;
			}
			checkDo();
		};

		//如果没有填写表单提示信息
		$('.submit_btn').bind('click' , function(){
			if(!ok1 && !ok2 && !ok3 && !ok4){
				text_val.text('请填写表单信息');
				message.show();
				specifiedTimeHide();
			}
		});

		//验证是否所有信息有正确，正确可提交表单
		var checkDo = function(){
			if( ok1 && ok2 && ok3 && ok4 ){
				$('.submit_btn').bind('click' , function(){
					var check = $('.agreement2 .xy img').attr('data-ok');
					console.log(check)
					if(check != 'false'){
						console.log('可以提交了')
						subMitForm();
					}else{
						text_val.text('请同意使用条款和隐私协议');
						message.show();
						specifiedTimeHide();
					};
				});
			}else{
				$('.submit_btn').unbind('click');
			};;
		};

		//提交表单
		function subMitForm(){
			$('#zc_from').submit();
		}


		/**	发送验证码**/
		$('.yzm2').bind('click' ,function(){
			getPhoneCode($(this))
		});

		function getPhoneCode(obj){
			var s = 60;
			var timer = null;
			obj.text(s + ' 已发送')
			obj.removeClass('linear').addClass('bg_c')
			timer = setInterval(function(){
				s--;
				obj.text(s + ' 已发送')
				if(s < 0){
					clearInterval(timer);
					obj.text('发送手机验证码');
					obj.removeClass('bg_c').addClass('linear');
				}

			},1000);
			return false;
		}
	}

	return {
		appendNode:appendNode,
		LoginValidation:LoginValidation,
		registerValidation:registerValidation
	}
}())