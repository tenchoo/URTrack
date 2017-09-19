$(document).ready(function(){

/**
	登录表单验证
**/	
	var ok1 = false;
	var ok2 = false;
	var ok3 = false;
	var yzm = 'ssss'; //自定义验证码

	$('#userName').bind('blur' , function(){
		isCheckName(); //验证用户名
	});

	$('#pwd').bind('blur' , function(){
		isCheckPwd(); //验证密码
	});

	$('#yzm').bind('blur' , function(){
		isCheckYzm(yzm); //验证验证码
	});

	$('.login_btn').bind('touchstart', function(ev){
		var ev = ev || window.event;
		ev.stopPropagation();
		ev.preventDefault();

		isCheckName();
		isCheckPwd();
		isCheckYzm(yzm);

	})

	//验证用户名函数
	function isCheckName(){
		var userName = $.trim($('#userName').val());
		var message = $('#userName').parent().next().find('span');
		var reg = /^1[34578]\d{9}$/ //验证用户名(手机号)正则
		var regEmail = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/  //验证邮箱
		if(userName == ''){
			message.text('账号不能为空').parent().show();
			ok1 = false;
//		}else if( !(reg.test(userName) || regEmail.test(userName))){
//			$('#userName').css('color','#c72c1f');
//			message.text('账号输入有误 ( 账号为11位手机号或者邮箱)').parent().show();
//			ok1 = false;
		}else{
			$('#userName').parent().next().hide();
			$('#userName').css('color','#333');
			ok1 = true;
			console.log(ok1)
		}

		isCheckDo()
	}
	
	//验证密码函数
	function isCheckPwd(){
		var pwd = $.trim($('#pwd').val());
		var message = $('#pwd').parent().next().find('span');
		var reg=/(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/;  //验证密码
		if(pwd == ''){
			message.text('密码不能为空').parent().show();
			ok2 = false;
//		}else if( !reg.test(pwd)){
//			$('#pwd').css('color','#c72c1f');
//			message.text('6～16位字符，至少包含数字、字母（区分大小写），符号中的2种').parent().show();
//			ok2 = false;
		}else{
			$('#pwd').css('color','#333');
			$('#pwd').parent().next().hide();
			ok2 = true;
			console.log(ok2)
		}

		isCheckDo()
	}

	//验证验证码函数
	function isCheckYzm(obj){
		var yzmVal = $.trim($('#yzm').val());
		obj = $.trim($('#yzm').val());
		var message = $('#yzm').parent().next().find('span');
		if(yzmVal == ''){
			message.text('验证码不能为空').parent().show();
			ok3 = false;
		}else if( yzmVal.toUpperCase() != obj.toUpperCase() ){
			$('#yzm').css('color','#c72c1f');
			message.text('验证码输入有误').parent().show();
			ok3 = false;
		}else{
			$('#yzm').css('color','#333');
			$('#yzm').parent().next().hide();
			ok3 = true;
			console.log(ok3);
		}

		isCheckDo()
	}

	//验证是否都通过验证了
	function isCheckDo(){
		if(ok1 && ok2 && ok3){
			$('.login_btn').bind('touchstart',function(){
				onLogin();
			});

		}else{
			$('.login_btn').unbind('touchstart');
		}
	}

	//提交表单
	function onLogin(){
		var option = {
			    url : "glaH5App/index",
			    type: "post",
			    success : function(data){
			    	var json = (new Function("return " + data))();
			    	if(("error" in json)) {
			    	 if(json.error.indexOf("验证") > -1){
			    		 var message = $('#yzm').parent().next().find('span');
			    		 $('#yzm').css('color','#c72c1f');
			 			 message.text('验证码输入有误').parent().show();
			    	 }else if(json.error.indexOf("密码") > -1){
			    		 var message = $('#pwd').parent().next().find('span');
			    		 $('#pwd').css('color','#c72c1f');
			 			 message.text(json.error).parent().show();
			    	 }else{
			    		 var message = $('#userName').parent().next().find('span');
			    		 $('#userName').css('color','#c72c1f');
			 			 message.text(json.error).parent().show();
			    	 }
					 $('.login_btn').unbind('touchstart');
			    	 refreshCaptcha();
			    	}
			    	if(("success" in json)){
			    		location.href=json.success;
			    	 }
			    	 
			   },
			   error:function(){
					alert('登陆失败');
				}
		 };
		$('#login_form').ajaxSubmit(option);  //15633892032
	}


	/**控制密码输入框值显示为星号**/	
	inintVal();
	function inintVal(){
		//密码
		$('#pwd').bind('focus',function(){
			
			$(this).attr('type','password');
			
		})
	}
})

function refreshCaptcha() {
	$("#captcha").attr("src", "servlet/captchaCode?t=" + Math.random());
}