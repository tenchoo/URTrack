$(document).ready(function(){
	Verification.findPassword();
	inintForm.initValue();
})

/**
	*找回密码
	* code 模拟验证码
	* pwdOld = 123456 //模拟原密码
**/
var Verification = (function(){

	var code = 'ssss';
	var pwdOld = 123456; //旧密码
	var falg = false;
	var falg1 = false;
	var falg2 = false;
	var falg3 = false;

	//验证手机号
	var isCheckedPhone = function(){
		checkLName();
		var phoneVal = $.trim( $('#phone').val() );
		var message = $('#phone').parent().next().find('span');
		var reg = /^1[34578]\d{9}$/ //验证用户名(手机号)正则
		if(phoneVal == ''){
			$('#phone').css('color','#c72c1f');
			message.text('账号不能为空').parent().show();
			falg = false;
		}else if( !reg.test(phoneVal)){
			$('#phone').css('color','#c72c1f');
			message.text('账号输入有误 ( 账号为11位手机号 ) ').parent().show();
			falg = false;
		}else{
			$('#phone').css('color','#333');
			$('#phone').parent().next().hide();
			falg = true;
		}

		isCheckDo();
	}
	
	function checkLName(){
		var loginName=$.trim( $('#phone').val() );
		$.ajax({
			url:"/register/checkLoginName",
			type:"post",
			data:{'loginName':loginName},
			success:function(data){
				var message = $('#phone').parent().next().find('span');
				if(data != 'false'){
					$('#phone').css('color','#c72c1f');
					message.text('手机号未注册').parent().show();
					falg = false;
				}
			}
		});
		
	}

	//验证验证码
	var isCheckedCode = function(){
		var codeVal = $.trim($('#codeid').val());
		var message = $('#codeid').parent().next().find('span');
		if(codeVal == ''){
			$('#codeid').css('color','#c72c1f');
			message.text('验证码不能为空').parent().show();
			falg1 = false;
		}else{
			$('#codeid').parent().next().hide();
			$('#codeid').css('color','#333');
			falg1 = true;
		}
		isCheckDo();
	};

	//验证新密码
	var isCheckedPassword = function(){
		//checkOldPwd();
		var pwdVal = $.trim($('#pwdid').val());
		var message = $('#pwdid').parent().next().find('span');
		var reg=/(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$/;  //验证密码
		if(pwdVal == ''){
			$('#pwdid').css('color','#c72c1f');
			message.text('新密码不能为空').parent().show();
			falg2 = false;
		}else if( !reg.test(pwdVal) ){
			$('#pwdid').css('color','#c72c1f');
			message.text('6～16位字符，至少包含数字、字母（区分大小写），符号中的2种').parent().show();
			falg2 = false;
		}else{
			$('#pwdid').css('color','#333');
			$('#pwdid').parent().next().hide();
			falg2 = true;
		}
		isCheckDo();
	};
	
	function checkOldPwd(){
		var pwd=$.trim($('#pwdid').val());
		$.ajax({
			url:"ssUser/checkPwd",
			data:{"pwd":pwd},
			type:"POST",
			dataType:"json",
			success:function(result){
				if(result.success==false){
					var message = $('#pwdid').parent().next().find('span');
					$('#pwdid').css('color','#c72c1f');
					message.text('新密码不能和原密码一样').parent().show();
					falg2 = false;
				}else{
					falg2 = true;
				}
			},
			error:function(){
				layer.msg("原密码错误！");
				falg2 = false;
			}
		});
	}

	//确认新密码
	var isCheckedNewPassword = function(){
		var pwdVal = $.trim($('#pwdid').val());
		var pwdVal2 = $.trim($('#pwdid2').val());
		var message = $('#pwdid2').parent().next().find('span');
		if(pwdVal2 == ''){
			$('#pwdid2').css('color','#c72c1f');
			message.text('确认密码不能为空').parent().show();
			falg3 = false;
		}else if(pwdVal2 != pwdVal){
			$('#pwdid2').css('color','#c72c1f');
			message.text('二次密码输入错误').parent().show();
			falg3 = false;
		}else{
			$('#pwdid2').css('color','#333');
			$('#pwdid2').parent().next().hide();
			falg3 = true;
		}
		isCheckDo();
	};

	//验证通过后表单提交调用
	var submitForm = function(){
		var option = {
			url : "glaH5App/resetPwd",
			type : "post",
			success : function(result) {
				if(result.success){
					$('.popup_mask').show(300);
					$('.popup').show(300);
				}else{
					layer.alert(result.msg);
				}
			},
			error : function() {
				layer.alert("修改密码失败");
			}
		}
		$('#findpassword').ajaxSubmit(option); 
	}
	//验证所有是否都通过
	var isCheckDo = function (){
		if(falg && falg1 && falg2 && falg3){
			$('.login_btn').addClass('active_login');
			$('.login_btn').bind('touchstart' , function(){
				submitForm();
			});

		}else{
			$('.login_btn').unbind('touchstart');
			$('.login_btn').removeClass('active_login');
		}
		
		if(falg){
			$('.yzm').removeAttr("disabled");
		}else{
			$('.yzm').attr("disabled","disabled");
		}
	};

	//返回给外部使用
	var findPassword = function(){
		$('#phone').bind('blur keyup' , function(){
			isCheckedPhone()
		});

		$('#codeid').bind('blur' , function(){
			isCheckedCode();
		});

		$('#pwdid').bind('blur' , function(){
			isCheckedPassword();
		});

		$('#pwdid2').bind('blur' , function(){
			isCheckedNewPassword();
		});

		$('.login_btn').bind('touchstart', function(ev){
			var ev = ev || window.event;
			ev.stopPropagation();
			ev.preventDefault();

			isCheckedPhone()
			isCheckedCode();
			isCheckedPassword();
			isCheckedNewPassword();

		})

	}

	return {
		code:code,
		pwdOld:pwdOld,
		findPassword:findPassword
	};

}());

/**
	控制密码输入框值显示为星号
**/

var inintForm = (function(){
	
	var initValue = function(){
		$('#pwdid').bind('focus' , function(){
			$(this).attr('type','password');
		});

		$('#pwdid2').bind('focus' , function(){
			$(this).attr('type','password');
		});

	};

	return{initValue:initValue}
}());