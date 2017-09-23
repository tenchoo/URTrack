$(document).ready(function(){

/**
	*输入设备号搜索
**/
	var $search = $('.search input[type="tel"]');
	var $dataList = $('.data_list ul li');
	var cans = $('input[name="iccid"]');

	$search.bind('focus',function(){
		$('.data_list').show();
		/*var curValue = $search.val();
		if(curValue == this.defaultValue){
			$(this).val('').css('color','#333');
		}*/
	});

	/*$search.bind('blur',function(){
		var curValue = $search.val();
		if($.trim(curValue) == ''){
			$(this).val(this.defaultValue).css('color','#ccc');
		}
	});*/
	$dataList.bind('touchstart', function(ev){
		var ev = ev || window.event;
		ev.stopPropagation();
		ev.preventDefault();
	});
	
	if(!$search.is(':checked')){
		$dataList.each(function(index){
			$(this).bind('click touchstart', function(){
				$('.data_list').hide();
				$search.val(this.innerHTML);
				cans.val($(this).attr('data-value'));
			})
		});
	};

	$(document).bind('touchstart', function(){
		$('.data_list').hide();
	});

/**
	搜索
**/
	$('#iccid').bind('keyup' , function(){
		getContent()
	})
	var getContent = function(){
		var iccid = $.trim($('#iccid').val()); //输入的搜索值
	    var data = $('.data_list').find('li');
	    
	    data.each(function(){
	    	var val = $.trim($(this).text())
	    	if(val.indexOf(iccid) != -1){
	    		$(this).show();  //把搜索到的显示出来
	    	}else{
	    		$(this).hide();  //不符合搜索要求的隐藏
	    	}
	    })
	    	
	    
	}

	//等查询完数据后获取自定义属性值
	var getDataValue = function(){
		var arrValue = []; //自定义属性值存放数组
		var oLi = $('#append ul li');
		oLi.each(function(){
			arrValue.push($(this).attr('data-value'))
		})
		return arrValue;
	}
	var a = getDataValue();
	console.log(a)

})
