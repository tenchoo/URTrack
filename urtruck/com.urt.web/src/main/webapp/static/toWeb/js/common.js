
//模拟checkbox
function checkbox(a,b){
	var elem = a&&typeof(a)=='string' ? a : '.checkBox';
	$(elem).click(function(){
		var _this = $(this);
		var _input = _this.is('input:checkbox') ? _this : _this.children('input');
		if(_input.attr('checked') == 'checked'){
			_input.removeAttr('checked');
			_this.removeClass('checked');
		}else{
			_input.attr('checked','checked');
			_this.addClass('checked');
		}
		return b&&typeof(b)=='function' ? b.call() : false;
	});
}

//模拟radio
function radio(a){
	var elem = a&&typeof(a)=='string' ? a : '.radio';
	$(elem).click(function(){
		var _this = $(this);
		var _input = _this.is('input:radio') ? _this : _this.children('input');
		var name = _input.attr('name');
		if(_input.attr('checked') == 'checked'){
			//_input.removeAttr('checked');
			//_this.removeClass('checked');
		}else{
			$('.radio').each(function(){
				$(this).find('input').removeAttr('checked');
				$(this).removeClass('checked');
			});
			_input.attr('checked','checked');
			_this.addClass('checked');
		}
	});
}
	
//全选
function checkall(a,b,c,d){
	checkbox(c);
	var $a=$(a), $b=$(b), j=$b.length, n=$b.filter(function(){return $(this).hasClass('checked');}).length;
	d = d ? d : function(){return false};
	$a.click(function(){
		if(!$a.hasClass('checked')){
			$b.removeClass('checked').children('input').removeAttr('checked');
			n=0;
			d.call();
		}else{
			var i=0;
			for(i;i<j;i++){
				if(!$b.eq(i).hasClass('checked')) $b.eq(i).addClass('checked').children('input').attr('checked','checked');
			}
			n=j;
			d.call();
		}
	});
	$b.click(function(){
		n = $(this).hasClass('checked') ? n+1 : n-1;
		if(n < j){
			$a.removeClass('checked').children('input').removeAttr('checked');
			d.call();
		}else{
			$a.addClass('checked').children('input').attr('checked','checked');
			d.call();
		}
	});
}

$(function(){
	
	//输入框提示文字显示隐藏
	/*$(document).on('keyup', '.input', function(){
		var _this = $(this);
		var _label = _this.siblings('.label');
		if(_label.length){
			if(_this.val() != ''){
				_label.hide();
				_this.parent(".inputBox").removeClass("error");
			}else{
				_label.show();
			}
		}
	});*/
	$('.input').each(function(){
		if($(this).val() != ''){
		$(this).siblings('.label').hide();
		}
		});
	$(document).on('focus', '.input', function(){
		var _this = $(this);
		var _label = _this.siblings('.label');
		if(_label.length){
			_label.hide();
		}
	}).on('blur', '.input', function(){
		var _this = $(this);
		var _label = _this.siblings('.label');
		if(_label.length){
			if(_this.val() != ''){
				_label.hide();
				_this.parent(".inputBox").removeClass("error");
			}else{
				_label.show();
			}
		}
	});
	$(document).on('click', '.label', function(){
		var _input = $(this).siblings('.input');
		if(_input.length){
			_input.focus();
		} 
	});

	//导航菜单
	$('.nav dt').mouseenter(function(){
		var self = $(this);
		if(self.hasClass('navPro')){
			if(!self.hasClass('selected')){
				self.addClass('selected').siblings('dt').removeClass('selected');
				self.siblings('.pro').removeClass('hide');
				self.siblings('.flow').addClass('hide');
				$('.header').addClass('bg01');
			}else{
				if(!self.siblings('.pro').hasClass('hide')){
					//self.siblings('.pro').addClass('hide');
					//$('.header').removeClass('bg01');
				}else{
					self.siblings('.pro').removeClass('hide');
					$('.header').addClass('bg01');
				}
			}
		}else if(self.hasClass('navFlow')){
			if(!self.hasClass('selected')){
				self.addClass('selected').siblings('dt').removeClass('selected');
				self.siblings('.flow').removeClass('hide');
				self.siblings('.pro').addClass('hide');
				$('.header').addClass('bg01');
			}else{
				if(!self.siblings('.flow').hasClass('hide')){
					//self.siblings('.flow').addClass('hide');
					//$('.header').removeClass('bg01');
				}else{
					self.siblings('.flow').removeClass('hide');
					$('.header').addClass('bg01');
				}
			}
		}else if(self.hasClass('account') || self.hasClass('account_1')){
			
		}else{
			if(!self.hasClass('selected')){
				self.addClass('selected').siblings('dt').removeClass('selected');
				self.siblings('.pro').addClass('hide');
				self.siblings('.flow').addClass('hide');
				$('.header').removeClass('bg01');
			}
		}
	});
	$('.nav').mouseleave(function(){
		$('.nav').find('.pro').addClass('hide');
		$('.nav').find('.flow').addClass('hide');
		$('.nav .selected').removeClass('selected');
		$('.header').removeClass('bg01');
	});
	$('.flow a').click(function(){
		$(this).addClass('selected').siblings().removeClass('selected');
	});
	
	checkbox('.checkBox');
	radio('.radio');

	$('.proSlide').cycle({ 
		fx:'scrollHorz', 
		speed:1000, 
		timeout:0,
		prev:'.pro .arrowL',
		next:'.pro .arrowR'
	});
	
});

function changeInput(tar){
	$(tar).each(function(){
	if($(this).val() != ''){
	$(this).siblings('.label').hide();
	}
	});
	}


;(function($){
	$.fn.resizeImg = function(conf){
		var opt = this.data('resizeImg');
		if(opt){
			return this;
		}else{
			opt = $.extend({}, resizeImgConf, opt, conf);
			return this.each(function(){
				var $this = $(this);
				var $img = $this.find('img');
				$this.data('resizeImg', opt);
				$img[0].onload = function(){
					var width = $this.width();
					var height = $this.height();
					var imgwidth = $img[0].width;
					var imgheight = $img[0].height;
					/*if((width * imgheight) > (imgwidth * height)){
						$img.css({'height':'100%', 'width':'auto'});
					}else{
						$img.css({'width':'100%', 'height':'auto'});
					}*/
					if(imgwidth > width){
						$img.css({'height':'auto', 'width':'100%'});
					}
				}
			});
		}
	}
	var resizeImgConf = {}
})(jQuery);


