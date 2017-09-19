$(document).ready(function(){
	//是否同意条款
	var inow = 0;
	var checked = false;
	$('.xy label').bind('touchstart', function(){
			inow ++ ;
			console.log(inow)
		if(inow % 2 == 0){
			checked = false;
			$(this).find('img').attr('src','static/newH5/images/gray_checked.png');
		}else{
			$(this).find('img').attr('src','static/newH5/images/red_checked.png');
			checked = true;
		}
		isCheckDo()
	});
	
/**
	验证用户注册表单
**/	
	var falg1 = false;
	var falg2 = false;
	var falg3 = false;
	var falg4 = false;
	var yzm = 'ssss'; //自定义验证码
	//验证注册号是否是手机号
	function isMobilePhone(){
		checkLName();
		var userZc = $.trim( $('#userZc').val() );
		var message = $('#userZc').parent().next().find('span');
		var reg = /^1[34578]\d{9}$/ //验证用户名(手机号)正则
		var regEmail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/  //验证邮箱
		if(userZc == ''){
			message.text('账号不能为空').parent().show();
			falg1 = false;
		}else if( !(reg.test(userZc) || regEmail.test(userZc))){
			$('#userZc').css('color','#c72c1f');
			message.text('账号输入有误 ( 账号为11位手机号 或者邮箱) ').parent().show();
			falg1 = false;
		}else{
			$('#userZc').parent().next().hide();
			$('#userZc').css('color','#333');
			falg1 = true;
			console.log(falg1)
		}

		isCheckDo()
	}
	
	function checkLName(){
		var loginName=$.trim( $('#userZc').val() );
		$.ajax({
			url:"/register/checkLoginName",
			type:"post",
			data:{'loginName':loginName},
			success:function(data){
				var message = $('#userZc').parent().next().find('span');
				if(data == 'false'){
					$('#userZc').css('color','#c72c1f');
					message.text('手机号或邮箱已注册').parent().show();
					falg1 = false;
				}
			}
		});
		
	}

	//验证密码是否合规
	function isPassword(){
		var pwdZc = $.trim( $('#pwdZc').val() );
		var message = $('#pwdZc').parent().next().find('span');
		var reg=/(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/;  //验证密码
		if(pwdZc == ''){
			message.text('密码不能为空').parent().show();
			falg2 = false;
		}else if( !reg.test(pwdZc)){
			$('#pwdZc').css('color','#c72c1f');
			message.text('6～16位字符，至少包含数字、字母（区分大小写），符号中的2种').parent().show();
			falg2 = false;
		}else{
			$('#pwdZc').parent().next().hide();
			$('#pwdZc').css('color','#333');
			falg2 = true;
			console.log(falg1)
		}

		isCheckDo()
	}

	//验证确认密码是否和第一次输入的密码一致
	function isPassword2(){
		var pwdZc = $.trim( $('#pwdZc').val() );
		var pwdZc2 = $.trim( $('#pwdZc2').val() );
		var message = $('#pwdZc2').parent().next().find('span');
		if(pwdZc2 == ''){
			message.text('确认密码不能为空').parent().show();
			falg3 = false;
		}else if( pwdZc2 != pwdZc){
			$('#pwdZc2').css('color','#c72c1f');
			message.text('二次密码输入错误').parent().show();
			falg3 = false;
		}else{
			$('#pwdZc2').parent().next().hide();
			$('#pwdZc2').css('color','#333');
			falg3 = true;
			console.log(falg3)
		}

		isCheckDo()
	}

	//验证手机验证码是否输入正确
	function isPhoneCode(obj){
		var yzmZc = $.trim( $('#yzmZc').val() );
		var message = $('#yzmZc').parent().next().find('span');
		if(yzmZc == ''){
			message.text('验证码不能为空').parent().show();
			falg4 = false;
		}else{
			$('#yzmZc').css('color','#333');
			$('#yzmZc').parent().next().hide();
			falg4 = true;
			console.log(falg4);
		}

		isCheckDo()
	}

	//验证是否都通过验证了
	function isCheckDo(){
		if(falg1 && falg2 && falg3 && falg4 &&checked){
			$('.login_btn').addClass('active_login');
			$('.login_btn').bind('touchstart',function(){
				onRegister();
			});

		}else{
			$('.login_btn').removeClass('active_login');
			$('.login_btn').unbind('touchstart')
		}
		
		if(falg1){
			$('.yzm').removeAttr("disabled");
		}else{
			$('.yzm').attr("disabled","disabled");
		}
	}

	//提交表单
	function onRegister(){
		$('#form_register').submit();  //15633892032
	}

	$('#userZc').bind('blur' , function(){
		isMobilePhone(); //验证注册号是否是手机号
	});

	$('#pwdZc').bind('blur' , function(){
		isPassword();  //验证密码是否合规
	});

	$('#pwdZc2').bind('blur' , function(){
		isPassword2(); //验证确认密码是否和第一次输入的密码一致
	});

	$('#yzmZc').bind('blur' , function(){
		isPhoneCode(yzm);  //验证手机验证码是否输入正确
	});

	$('.login_btn').bind('touchstart' , function(){
		isMobilePhone();
		isPassword();
		isPassword2();
		isPhoneCode(yzm);
	})

	/**控制密码输入框值显示为星号**/
	
	inintVal();
	function inintVal(){
		//密码
		$('#pwdZc').bind('focus',function(){
			$(this).attr('type','password');
		})
		
		//确认密码
		$('#pwdZc2').bind('focus',function(){
			$(this).attr('type','password');
		})
		
	}


})	