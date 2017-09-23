$(document).ready(function(){

/**
	*输入设备号搜索
**/
	
	var $search = $('.search input[type="text"]');
	var $dataList = $('.data_list ul li');

	$search.bind('focus',function(){
		$('.data_list').show();
		var curValue = $search.val();
		if(curValue == this.defaultValue){
			$(this).val('').css('color','#000');
		}
	});

	$search.bind('blur',function(){
		var curValue = $search.val();
		if($.trim(curValue) == ''){
			$(this).val(this.defaultValue).css('color','#ccc');
		}
	});
	$dataList.bind('touchstart', function(ev){
		var ev = ev || window.event;
		ev.stopPropagation();
	});
	
	if(!$search.is(':checked')){
		$dataList.each(function(index){
			$(this).bind('click touchstart', function(){
				$('.data_list').hide();
				$search.val(this.innerHTML);
			})
		});
	};

	$(document).bind('touchstart', function(){
		//$('.data_list').hide();
	});

/**
	*流量监控图
**/	
	
	var box = document.getElementById('box');
	var cvs = document.getElementById('cvs');
	var oP = box.getElementsByTagName('p')[0];
	var context = cvs.getContext('2d');

	//var x = 160; X 轴位置（圆形中心点）
	//var y = 160; Y 轴位置 （圆形中心点）
	//var c = 120; 圆形半径

	var init = {
		inow:700,  //设置剩余流量
		x:145,     //圆心得坐标
		y:145,
		c:120     //圆的半径
	}

	yp(init);

	function yp(init){
		
		context.clearRect(0,0,cvs.width,cvs.height);//清除画布
		var _inow = Math.floor(init.inow/1000*360); //剩余流量与圆盘的比例
		var h2 = document.createElement('h2');
		h2.className = 'text-center l_m';
		h2.innerHTML = "本月使用流量为" + (1000 - init.inow)+ "M";
		oP.innerHTML = "当前流量包剩余<span class='total'>"+init.inow+"M</span><span>"+"2017-05-27到期</span>"
		box.appendChild(h2)

		//灰色圆盘 第一层
		context.beginPath();
		context.fillStyle = '#f2f2f2';
		context.strokeStyle = '#e4e4e4';
		context.moveTo(init.x,init.y);
		context.arc(init.x,init.y,init.c,0,360*Math.PI/180,false);
		context.stroke();
		context.fill();
		context.closePath();
	
		//红色圆盘 第二层
		
		//context.lineCap = 'round';
		//context.fillStyle = '#c72c1f'; //填充颜色
		context.beginPath();
		context.lineWidth = 12.5;
		
		context.strokeStyle = '#d60a07';
		context.moveTo(init.x,init.y);
	//context.arc(init.x,init.y,init.c*(18/20),180*Math.PI/180,-90*Math.PI/180,true);
		context.arc(init.x,init.y,init.c*(18/20),-90*Math.PI/180,(-90+_inow)*Math.PI/180,false);
		context.stroke();
		context.closePath();
		context.restore();

		//context.fill(); //填充
		

		//灰色圆盘 第三层遮盖红色边框的起始线
		context.lineWidth = 1;
		context.beginPath();
		context.fillStyle = '#f2f2f2';
		context.moveTo(init.x,init.y);
		context.arc(init.x,init.y,init.c*(17/20),0,360*Math.PI/180,false);
		context.fill();
		context.closePath();

		//白色圆盘 第四层
		context.lineWidth = 1;
		context.beginPath();
		context.fillStyle = '#fff';
		context.strokeStyle = '#e4e4e4';
		context.moveTo(init.x,init.y);
		context.arc(init.x,init.y,init.c*(16/20),0,360*Math.PI/180,false);
		context.stroke();
		context.fill();
		context.closePath();
/**/
		//红色圆盘 第五层红色圆盘
		context.beginPath();
		context.fillStyle = '#d60a07';
		context.moveTo(init.x,init.y);
		context.arc(init.x,init.y,init.c*(12/20),0,360*Math.PI/180,false);
		context.fill();
		context.closePath();
		 
	}
})