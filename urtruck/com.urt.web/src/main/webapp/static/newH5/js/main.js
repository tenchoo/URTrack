$(document).ready(function(){
/**
	发送验证码
**/	
	$('.yzm').bind('click' , function(){
		var s = 59;
		var timer = null;
		var that = $(this);
		var loginName = $('#userZc').val();
		if(typeof(loginName) == 'undefined') loginName = $('#phone').val();
		if(loginName != '')
		$.ajax({
			url:"glaH5App/sendCheckMsg",
			type:"post",
			data:{'phone':loginName},
			success:function(result){
				if(result){
					$(this).text(s + ' 已发送')
					$(this).css('background','#999')
					timer = setInterval(function(){
						s--;
						that.text(s + ' 已发送')
						that.attr("disabled","disabled");
						if(s < 0){
							clearInterval(timer);
							that.text('获取验证码');
							that.css('background','#c72c1f');
							that.removeAttr("disabled");
						}
						
					},1000);
				}else{
					layer.alert("发送短信失败！ 请稍等再次发送");
				}
			},
			error:function(){
				layer.alert("发送短信失败！ 请稍等再次发送");
			}
		});
		return false;
	});
	//document.addEventListener('touchmove', function(e){e.preventDefault()}, false);

/**
	菜单操作
**/
	$('.menu_btn').bind('touchstart',function(ev){
		if($('.menu_wrap').is(":hidden")){
			$('.menu_wrap').show();
		}else{
			$('.menu_wrap').hide();
		}
		var ev = ev || window.event;
		ev.stopPropagation()
	});
	$('.menu').bind('touchstart' , function(ev){
		var ev = ev || window.event;
		ev.stopPropagation()
	});
	$(document).bind('touchstart', function(){
		$('.menu_wrap').hide();
	});

	/*overscrollFn.navigatorAgent();*/
})
/**
	*解决出界问题
**/

var overscrollFn = (function(){

	
	//判断移动设备类型
	var navigatorAgent = function(){
		var u = navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		//alert('是否是Android：'+isAndroid);
		//alert('是否是iOS：'+isiOS);

		if(isiOS){
			//alert($('body').children().has('wrapper'))
			if($('body').children().hasClass('wrapper')){
				$('.wrapper').addClass('scroll');
			}else{
				$('.container-fluid').addClass('scroll');
			}
			overscroll(document.querySelector('.scroll'));
			document.body.addEventListener('touchmove', function(evt) {
				if(!evt._isScroller) {
				  evt.preventDefault();
				}
			});
		};
	};

	//ios终端 需要做的事情
	var overscroll = function(el) {
	  el.addEventListener('touchstart', function() {
	    var top = el.scrollTop
	      , totalScroll = el.scrollHeight
	      , currentScroll = top + el.offsetHeight;
	    
	    if(top === 0) {
	      el.scrollTop = 1;
	    } else if(currentScroll === totalScroll) {
	      el.scrollTop = top - 1;
	    }
	  });
	  el.addEventListener('touchmove', function(evt) {
	    
	    if(el.offsetHeight < el.scrollHeight)
	      evt._isScroller = true;
	  });
	};
	
	return {
		overscroll:overscroll,
		navigatorAgent:navigatorAgent
	}
}());	