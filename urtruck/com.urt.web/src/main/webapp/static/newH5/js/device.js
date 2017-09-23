$(document).ready(function(){

/**
	是否同意条款
**/
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

	//验证设备号是否是19位数字
	function isDeviceNumber(){    //1739203829485728292
		var equipment = $.trim( $('#equipment').val() );
		var message = $('#equipment').parent().next().find('em');
		var parent = /^\d{19}$/g //验证设备号正则
		if(equipment == ''){
			message.text('设备号不能为空').parent().parent().show();
		}else if( !parent.test(equipment)){
			$('#equipment').css('color','#c72c1f');
			message.text('设备号输入有误').parent().parent().show();
		}else{
			$('#equipment').parent().next().hide();
			$('#equipment').css('color','#333');
			falg1 = true;
			console.log(falg1)
		}

		isCheckDo()
	}

	//验证激活码
	function isActivationCode(){
		var activation = $.trim( $('#activation').val() );
		var message = $('#activation').parent().next().find('em');
		var patrn=/^[a-z\d]{6}$/i; //激活码四位数字和字母混合
		if(activation == ''){
			message.text('激活码不能为空').parent().parent().show();
		}else if( !patrn.test(activation)){
			$('#activation').css('color','#c72c1f');
			message.text('激活码输入有误').parent().parent().show();
		}else{
			$('#activation').parent().next().hide();
			$('#activation').css('color','#333');
			falg2 = true;
			console.log(falg1)
		}

		isCheckDo()
	}

	//验证用户姓名
	function isCheckedUserName(){
		var nameId = $.trim( $('#nameId').val() );
		var nameZw = /^[\u4e00-\u9fa5 ]{2,20}$/;//验证中文名
		//var nameYw = /^[a-zA-Z\/ ]{2,20}$/;     //验证英文名
		var message = $('#nameId').parent().next().find('em');
		if(nameId == ''){
			message.text('姓名不能为空').parent().parent().show();
		}else if( !nameZw.test(nameId)){
			$('#nameId').css('color','#c72c1f');
			message.text('姓名输入错误').parent().parent().show();
		}else{
			$('#nameId').parent().next().hide();
			$('#nameId').css('color','#333');
			falg3 = true;
			console.log(falg3)
		}

		isCheckDo()
	}

	//验证身份证号码
	function isCodeId(){
		var userNameId = $.trim( $('#userNameId').val() );
		var message = $('#userNameId').parent().next().find('em');
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
		//150778139702062535
		if(userNameId == ''){
			message.text('身份证号码不能为空').parent().parent().show();
		}else if( !reg.test(userNameId) || !idCardNoUtil.checkIdCardNo(userNameId)){
			$('#userNameId').css('color','#c72c1f');
			message.text('身份证号码输入有误').parent().parent().show();
		}else{
			$('#userNameId').css('color','#333');
			$('#userNameId').parent().next().hide();
			falg4 = true;
			console.log(falg4);
		}

		isCheckDo()
	}

	//验证是否都通过验证了
	function isCheckDo(){
		if(falg1 && falg2 && falg3 && falg4 && checked){
			$('.login_btn').addClass('active_login');
			$('.login_btn').bind('touchstart',function(){
				onDevice();
			});

		}else{
			$('.login_btn').removeClass('active_login');
			$('.login_btn').unbind('touchstart');

		}
	}

	//提交表单
	function onDevice(){
		var iccid=$.trim( $('#equipment').val() );
        //姓名验证
        var username=$.trim( $('#nameId').val() );
        //身份证验证
        var idcardValue=$.trim( $('#userNameId').val() );
		var data={'iccid':iccid,'realname':username,'idnum':idcardValue};
		
		$.ajax({
            type:"post",
            url:"glaH5AppActive/activeService",
            data:data,
            cache: false,
            success:function(data){
                var jsonObj=eval("("+data+")");
                var statusCode=parseInt(jsonObj.retcode);
                switch (statusCode) {
                    case 0:
                    	layer.alert("激活失败");
                        break;
                    case 1:
                    	layer.alert("激活成功！");
                    	location.reload(true); 
                    	break;
                    case 2:
                        layer.alert("该设备已激活");
                        break;
                    case -1:
                    	layer.alert("参数不全");
                        break;
                    case -2:
                    	layer.alert("服务校验失败请重新登录");
                        break;
                    case -4:
                    	layer.alert("该设备号不存在或者错误设备号");
                        break;
                    case -5:
                    	layer.alert("签名错误");
                        break;
                    case -6:
                    	layer.alert("iccid已经被其他账户绑定");
                        break;
                    case -7:
                    	layer.alert("该设备号不存在或者错误设备号");
                        break;
                    case -8:
                    	layer.alert("激活码与设备号不匹配");
                        break;
                    case -9:
                    	layer.alert("iccid所属设备非mifi设备");
                        break;
                    case -10:
                    	layer.alert("iccid已经激活");
                        break;
                    case -11:
                    	layer.alert("iccid激活失败");
                        break;
                    case -12:
                    	layer.alert("激活码错误！");
                        break;
                }
            },
            error:function(XMLHttpRequest, textStatus, errorThrown){
                layer.alert("激活超时请重新登录!!");
            }
        })//end ajax
	}

	$('#equipment').bind('blur' , function(){
		isDeviceNumber(); //验证19位设备号
	});

	$('#activation').bind('blur' , function(){
		isActivationCode();  //验证激活码
	});

	$('#nameId').bind('blur' , function(){
		isCheckedUserName(); //验证姓名
	});

	$('#userNameId').bind('blur' , function(){
		isCodeId();  //验证身份证号码
	});

	$('.login_btn').bind('touchstart' , function(){
		isDeviceNumber();
		isActivationCode();
		isCheckedUserName();
		isCodeId();
	})

})		