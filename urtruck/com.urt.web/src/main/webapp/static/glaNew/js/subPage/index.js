$(document).ready(function(){
	//首页tab选项切换
	$('#area li').on('click',function(){
		$(this).addClass('active').siblings('li').removeClass('active');
	});
	
		var china_t = $('#china_t');
		var china_l = $('#china_l');
		var china_r = $('#china_r');
		var map_t = $('#map_t');
		var map_l = $('#map_l');
		var map_r = $('#map_r');
		var areas =$('.area');
		//上部地图区域
		china_t.show();
		map_t.on('mouseenter' , function(ev){
			china_t.show();
			areas.eq(0).addClass('cursor');
		});
		china_t.on('mouseleave' , function(){
			china_t.hide();
			areas.eq(0).removeClass('cursor');
			
		});
		china_t.on('click' , function(){
		
		});
		//左侧地图区域
		map_l.on('mouseenter' , function(){
			china_l.show();
			areas.eq(1).addClass('cursor');
			
		});

		china_l.on('mouseleave' , function(){
			china_l.hide();
			areas.eq(1).removeClass('cursor');
			
		});
		china_l.on('click' , function(){

		});
		//右侧地图区域
		map_r.on('mouseenter' , function(){
			
			china_r.show();
			areas.eq(2).addClass('cursor');
			
		});

		china_r.on('mouseleave' , function(){
			china_r.hide()
			areas.eq(2).removeClass('cursor');
			
		});
		china_r.on('click' , function(){				

		});

});
