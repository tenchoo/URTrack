
$(document).ready(function(){
	//定制和部署的切换
	$('.right_feature .buttons span').on('click',function(){
		$(this).addClass('active').siblings().removeClass('active');
		var index = $(this).index();
		$('.right_feature .box').eq(index).css({'display':'block'}).siblings('.box').css({'display':'none'});
	});
	
	//定制选项的企业logo和网站风格的切换
	$('.right_feature .custom p span').on('click',function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		var index = $(this).index();
		$('.right_feature .custom .inner_box').eq(index).css({'display':'block'}).siblings('.inner_box').css({'display':'none'});
	});
	
	//部署选项的公众号配置和二维码的切换
	$('.right_feature .deploy p span').on('click',function(){
		$(this).addClass('cur').siblings().removeClass('cur');
		var index = $(this).index();
		$('.right_feature .deploy .inner_box').eq(index).css({'display':'block'}).siblings('.inner_box').css({'display':'none'});
	});
	//流量充值边框颜色切换
	$('.phone_screen .pay div').on('click',function(){
		$(this).addClass('current').siblings().removeClass('current');
		$(this).css({"color":$('.colpick_new_color').css('backgroundColor'),"border-color":$('.colpick_new_color').css('backgroundColor')})
		.siblings().css({'color':'black','border-color':'transparent'});	
	});
	//默认第一个流量框选中
	$('.phone_screen .pay div').eq(0).trigger('click');
	//二维码下载尺寸菜单显示与隐藏
//	$('.deploy .qr_code .qr_download').on('click',function(){
//		$('.deploy .qr_code ul').stop(true,true).slideToggle(300);
//	});
//	$('.deploy .qr').on('mouseleave',function(){
//		$('.deploy .qr_code ul').stop(true).slideUp(300);
//	});
	//文本框字数
	 $("#txt").on('input',function(){
	 	var len = $(this).val().length;
	 	 $('#word').text(100-len);
	 });
	 //加工li的颜色
	 var colors=['#000000','#fff','#ff0006','#fe00ba','#1e00ff','#02f6ff','#1eff01','#c6ff00','#ffd800','#ff8400','#f85700','#b400ff','#00ff95'];
	 $('.normal_use_color li').each(function(index){
	 	$(this).css('backgroundColor',colors[index]);
	 });
	 //rgb转#
	 function rgb2hex(rgb) {
		rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
		function hex(x) {
		return ("0" + parseInt(x).toString(16)).slice(-2);
		}
		return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
		}
	$('.colpick_current_color').html('重置').css('color','#fff');//重置按钮的默认文字颜色
	 
	 //点击color-piker需要设置的选项 
	 $('.normal_use_color li').on('click',function(){
	 	var change_color=$(this).css('backgroundColor');//颜色
	 	$(this).css('border','1px solid #000').siblings('li').css('border','1px solid #e6e6e6');
	 	$('.colpick_new_color').css({"background-color":change_color});
	 	$('.pay div.current').css({"color":change_color,"border-color":change_color});//流量充值框
	 	$('.left_phone .logo').css({"border-color":change_color});//手机logo框的下边框
	 	$('.colpick_hex_field input').val(rgb2hex(change_color).substring(1));//动态的色值变化
	 	$('.sure_pay button').css({"background-color":change_color});//确认充值按钮
	 	$('.left_phone .logo .icon-menu ul,.icon-menu ul li').css({'border-color':$('.colpick_new_color').css('backgroundColor')})
	 	.siblings().css({'background-color':'transparent'});
	 	/*$('.colpick_submit').on('click',function(){//点击确认按钮
	 		$('.colpick_current_color').css({"background-color":$('.colpick_new_color').css('backgroundColor')})
	 	})*/
	 });
	 //点击重置按钮时需要恢复默认的选项
	 $('.colpick_current_color').on('click',function(){
	 	$('.pay div').css({'color':'black','border-color':'black'});//流量充值框恢复默认
	 	$('.sure_pay button').css({'background-color':'#007eeb'});//确认充值按钮恢复默认
	 	$('.left_phone .logo').css({"border-color":'#007eeb'});//手机logo框的下边框恢复默认
	 	$('.left_phone .logo .icon-menu ul,.icon-menu ul li').css({'color':'#000','border-color':'#007eeb'})//菜单
	 	$('.phone_screen .pay div').eq(0).trigger('click');
	 });
	 //点击菜单按钮，显示菜单
	 $('.left_phone .logo .icon-menu').on('click',function(){
	 	$(this).children('ul').toggle();
	 });
	 //鼠标离开导航菜单隐藏导航菜单
	 $('.left_phone .logo .icon-menu').on('mouseleave',function(){
	 	$(this).children('ul').hide();
	 });
	 //导航菜单每一项的hover效果
	 $('.left_phone .logo .icon-menu ul li').on('mouseenter',function(){
	 	$(this).css({'border-color':$('.colpick_new_color').css('backgroundColor'),'background':'#eeeeee'})
	 	.siblings().css({'color':'#000','background':'transparent'});
	 });
	 //点击菜单切换页面
	 $('.left_phone .logo .icon-menu ul li').on('click',function(){
	 	var idx= $(this).index();
	 	$('.phone_screen').eq(idx).fadeIn(500).siblings('.phone_screen').hide();
	 });
	 //返回按钮
	 $('.back0').on('click',function(){
	 	setTimeout(function(){
	 		$('.phone_screen').eq(2).fadeIn(200).siblings('.phone_screen').fadeOut(0);
	 	},220);
	 	
	 	/*removeClass('unShow').siblings('.phone_screen').addClass('unShow');*/
	 });
	 $('.back1').on('click',function(){
	 	setTimeout(function(){
	 		$('.phone_screen').eq(0).fadeIn(200).siblings('.phone_screen').fadeOut(0);
	 	},220);
	 	
	 	/*.removeClass('unShow').siblings('.phone_screen').addClass('unShow');*/
	 });
	 $('.back2').on('click',function(){
	 	setTimeout(function(){
	 		$('.phone_screen').eq(1).fadeIn(200).siblings('.phone_screen').fadeOut(0);
	 	},220);
	 	
	 	/*.removeClass('unShow').siblings('.phone_screen').addClass('unShow');*/
	 });
	 //遮罩层的内容
	var winHeight=document.body.clientHeight;
	var winWidth=document.body.clientWidth;
	//设置遮罩层的宽高为屏幕的宽高
	$('#mask_layer').width(winWidth);
	$('#mask_layer').height(winHeight);
	//设置遮罩层始终占据整个屏幕
	var scrollTop=$(window).scrollTop();
	$('#mask_layer').css('top',scrollTop+'px');
	//遮罩层显示
	$('#show').on('click',function(){
		//克隆手机的内容并插入到遮罩层
		$('#mask_layer').fadeIn(600);
		$('#mask_layer .layer_left').append($('#content').clone(false));		
		$('body').css({'overflow':'hidden'});
		$('.container').css({'margin-right':'91px'});
	});
	//遮罩层关闭
	$('#mask_layer .icon-guanbi').on('click',function(){
		$('#mask_layer').fadeOut(300);
		$('body').css({'overflow':'auto'});
		$('.container').css({'margin':'0 auto'});
		
	});	

	$('#client_search1').focus();
	$('#client_search1').on('input',function(){
		if($(this).val().length==0){
			$('.submit_menu1').hide();		
		}else{
			$('.submit_menu1').show();	
		}		
	});	
		
	$('.submit_menu1 li').on('click',function(){
		var content = $(this).html();
		$('#client_search1').val(content);
		$('.submit_menu1').hide();
	});
	
	//取消按钮
	$('#cancel').on('click',function(){
		 $('.colpick_current_color').click();
	});
	
	

	
});