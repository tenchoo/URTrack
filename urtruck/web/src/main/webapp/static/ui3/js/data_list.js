$(document).ready(function(){
	//设置默认显示
	Admin.setDefaultShow();
	// Admin.getOutputNum();
	
	 //新导航
	$('.nav li').on('mouseover',function(){
		var index=$(this).index();
		var childBox=$(this).find($('.nav .childMenu'));
		var childBtn=childBox.children();
		$(this).children().eq(0).addClass('on');
		if(childBtn.length){
			$(this).find(childBox).show();
		}else{
			$(this).find(childBox).hide();
		}
		if(0<childBtn.length&&childBtn.length<=10){
			childBox.css('width','150px');
		}else if(10<childBtn.length&&childBtn.length<=20){
			childBox.css('width','300px');
		}
	})
	$('.nav li').on('mouseout',function(){
		$(this).children().eq(0).removeClass('on');
		$(this).find($(this).find($('.nav .childMenu'))).hide()
	})
	
	
	getNum();
	ChinaMap.getMap();
	ChinaMap.dataPartition();

	
	$(".msgList li:odd").css("background-color","#f7f7f7");
	//柱状图划过显示数据
	$('.col').on('mouseover',function(){
		$(this).next().show();
	}).on('mouseout',function(){
		$(this).next().hide();
	})
	//统计数字每三位加逗号
	/*numCount('300');*///注意后台返回一定要是字符串格式，因为js数字只能解晰16位以下

	//日期显示当月
	/*(function(){
		var date=new Date;
		var year=date.getFullYear();
		var month=date.getMonth()+1;
		month =(month<10 ? "0"+month:month);
		var mydate = (year.toString()+'-'+month.toString());
		$('.startTime').val(mydate);
		$('.endTime').val(mydate);
		$('.sTime').text(mydate);
		$('.eTime').text(mydate);
	})();*/

		
	//表格
	var crNum=0;//完成率变量

	//月份处的确定按钮
	$('#confirmBtn').on('click',function(){
		
		//清空
		/*var total = {
			//North District
			"northArea":
			[
				//  y--China Mobile   l--China Unicom   d--China Telecom   w--Activation   z--on service   t--out of service
				//  table表格数据----cn--连接数,收入--en,fn--流量,em--收入环比,cr--完成率
				//Intelligent exhibition industry
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart car connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//Carrier IOT
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//智能B
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0}

			],
			//southern district
			"southArea":[
				//Intelligent exhibition industry
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart car connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//Carrier IOT
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//智能B
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0}
			],
			//Eastern District
			"eastArea":[
				//Intelligent exhibition industry
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart car connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//Carrier IOT
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//智能B
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0}
			]
		};*/
//		$('.loadingBox').fadeIn(2000);
		getNum();
		ChinaMap.getMap();
		ChinaMap.dataPartition();
	})

})

/*统计数字每三位加逗号*/
function numCount(count){
    var strNum=count+'';
    var arrNum=strNum.split('').reverse().join('').replace(/(\d{3})/g,'$1a').replace(/\,$/,'').split('').reverse();
    var arrLength=arrNum.length;
    if(arrLength%4==0){
    	var str=arrNum.join('').substring(1);
    	arrNum=str.split('');
    }
    var html="";
    $.each(arrNum,function(index,value){
        html+='<img class="imgTab" src="../static/ui3/images/num_'+value+'.png">';
        $('.tosize').html(html);
    });
}

/**
	*管理员
**/
var Admin = (function(){
	var setDefaultShow = function(){
		//常见问题显示隐藏
		// switchTab($('.question') , $('.user_info .answer_wrap') , 'click');

		//时间显示隐藏列表
		switchTab($('.time') , $('.data_core') , 'click');

		//控制显示隐藏
		function switchTab(obj,list,oEvent){
			obj.bind(oEvent , function(){
				if(oEvent == 'click'){
					if(list.is(':hidden')){
						list.show();
					}else{
						list.hide();
					}
				}else if(oEvent == 'mouseover'){
					list.show();
				}else if(oEvent == 'mouseout'){
					list.hide();
				}

				return false;
			});

			$(document).bind(oEvent , function(){
				list.hide();
			})
		}

/*
		//导航显示影藏
		navList($('.nav li') , $('.nav .answer_wrap'));
		// navList($('.user_info li') , $('.user_info .answer_wrap'));

		function navList(objBtn,objList){
			objBtn.each(function(){
				$(this).bind('mouseover' , function(){
					var aa=$(this).find(objList).children().find('li');
					if(aa.length){
						$(this).find(objList).show();
					}else{
						$(this).find(objList).hide();
					}
				});
				$(this).bind('mouseout' , function(){
					$(this).find(objList).hide()
				});
			})
		}*/
		
		

		//按钮提示图标样式控制
		controlStyle()
		function controlStyle(){
			var check = true;
			$('.question').on('mouseover',function(){
				if( check ){
					$(this).find('em').removeClass().addClass('xiaoguo');
					check = false;
				}else{
					$(this).find('em').removeClass().addClass('xiaoguo2');
					check = true;
				}
			})
		}
	}

	//获取连接数
	// var getOutputNum = function(){
	// 	var num = '6927542587435777'; //自定义获取到的连接数
	// 	var arr = num.split('');
	// 	var objSpan = $('.output p span').empty();
	// 	var len = arr.length;
	// 	for(var i=0; i<len; i++){
	// 		objSpan.append('<img src="../static/ui3/images/num_'+arr[i]+'.png">')
	// 	}
	// };
	return {
		setDefaultShow:setDefaultShow,
		// getOutputNum:getOutputNum
	}

}());

//地图
var ChinaMap = (function(){
	/**
		地图操作
	**/
	var getMap = function(){
		//大区数据
		

		/*var total = {
			//North District

			"northArea":
			[
				//  y--China Mobile   l--China Unicom   d--China Telecom   w--Activation   z--on service   t--out of service
				//  table表格数据----cn--连接数,收入--en,fn--流量,em--收入环比,cr--完成率
				//Intelligent exhibition industry
				{"y":1,"l":1,"d":1,"w":4242,"z":1,"t":1,"cn":con_N_z,"en":1,"fn":1,"em":1,"cr":1},
				//smart car connected
				{"y":11,"l":265461,"d":11331,"w":41,"z":51,"t":66651,"cn":con_N_c,"en":111,"fn":54,"em":548,"cr":111},
				//smart connected
				{"y":14562,"l":22,"d":31532,"w":42546,"z":52,"t":64652,"cn":con_N_h,"en":111,"fn":54,"em":548,"cr":111},
				//Carrier IOT
				{"y":145463,"l":23,"d":3453,"w":45463,"z":53,"t":63,"cn":con_N_w,"en":111,"fn":54,"em":548,"cr":111},
				//智能B
				{"y":145463,"l":23,"d":3453,"w":45463,"z":53,"t":63,"cn":con_N_B,"en":111,"fn":54,"em":548,"cr":111}

			],
			//southern district
			"southArea":[
				//Intelligent exhibition industry
				{"y":13,"l":23,"d":33,"w":43,"z":53,"t":63,"cn":con_S_z,"en":111,"fn":54,"em":548,"cr":111},
				//smart car connected
				{"y":12,"l":22,"d":32,"w":42,"z":52,"t":62,"cn":con_S_c,"en":111,"fn":54,"em":548,"cr":111},
				//smart connected
				{"y":11,"l":21,"d":31,"w":41,"z":51,"t":61,"cn":con_S_h,"en":111,"fn":54,"em":548,"cr":111},
				//Carrier IOT
				{"y":11,"l":12,"d":13,"w":14,"z":15,"t":16,"cn":con_S_w,"en":111,"fn":54,"em":548,"cr":111},
				//智能B
				{"y":145463,"l":23,"d":3453,"w":45463,"z":53,"t":63,"cn":con_S_B,"en":111,"fn":54,"em":548,"cr":111}

			],
			//Eastern District
			"eastArea":[
				//Intelligent exhibition industry
				{"y":20,"l":2,"d":3,"w":4,"z":5,"t":1,"cn":con_E_z,"en":1,"fn":1,"em":1,"cr":1},
				//smart car connected
				{"y":1,"l":23222,"d":23,"w":24,"z":25,"t":1,"cn":con_E_c,"en":1,"fn":54,"em":1,"cr":111},
				//smart connected
				{"y":51,"l":32,"d":33,"w":34,"z":35,"t":1,"cn":con_E_h,"en":1,"fn":54,"em":1,"cr":111},
				//Carrier IOT
				{"y":51,"l":42,"d":43,"w":44,"z":45,"t":1,"cn":con_E_w,"en":1,"fn":54,"em":1,"cr":111},
				//智能B
				{"y":1,"l":23,"d":1,"w":1,"z":53,"t":63,"cn":con_E_B,"en":1,"fn":54,"em":1,"cr":111}

			]
		};*/
	//异常数据处理
	var fillAreaData=
			[
				//  y--China Mobile   l--China Unicom   d--China Telecom   w--Activation   z--on service   t--out of service
				//  table表格数据----cn--连接数,收入--en,fn--流量,em--收入环比,cr--完成率
				//Intelligent exhibition industry
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart car connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//smart connected
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//Carrier IOT
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0},
				//智能B
				{"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0}

			];
	var fillItemData={"y":0,"l":0,"d":0,"w":0,"z":0,"t":0,"cn":0,"en":0,"fn":0,"em":0,"cr":0};

	//json中如果有的项没有赋值为0
	judgeNull("northArea");
	judgeNull("southArea");
	judgeNull("eastArea");
	function judgeNull(itemArea){
		var item=total[itemArea];
		if(item==''||item==null||item==undefined){
			total[itemArea]=fillAreaData;
		}else{
            for(var i=0; i<total[itemArea].length; i++){
                if(total[itemArea][i]==''||total[itemArea][i]==null||total[itemArea][i]==undefined){//如果大区里面的项目为空
                    total[itemArea][i]=fillItemData;
                }
            }
        }
	}


	//North District-------China MobileChina Unicom电脑联接数
	var con_N_z=0;var con_N_c=0;var con_N_h=0;var con_N_w=0;var con_N_B=0;
	//展业-
	con_N_z=total["northArea"][0].y+total["northArea"][0].l+total["northArea"][0].d;
	total["northArea"][0].cn=con_N_z;
	//车联
	con_N_c=total["northArea"][1].y+total["northArea"][1].l+total["northArea"][1].d;
	total["northArea"][1].cn=con_N_c;
	//互联-
	con_N_h=total["northArea"][2].y+total["northArea"][2].l+total["northArea"][2].d;
	total["northArea"][2].cn=con_N_h;
	//物联
	con_N_w=total["northArea"][3].y+total["northArea"][3].l+total["northArea"][3].d;
	total["northArea"][3].cn=con_N_w;
	//智能B
	con_N_B=total["northArea"][4].y+total["northArea"][4].l+total["northArea"][4].d;
	total["northArea"][4].cn=con_N_B;

	//southern district-------China MobileChina Unicom电脑联接数
	var con_S_z=0;var con_S_c=0;var con_S_h=0;var con_S_w=0;var con_S_B=0;
	//southern district---展业
	con_S_z=total["southArea"][0].y+total["southArea"][0].l+total["southArea"][0].d;
	total["southArea"][0].cn=con_S_z;
	//southern district---车联-
	con_S_c=total["southArea"][1].y+total["southArea"][1].l+total["southArea"][1].d;
	total["southArea"][1].cn=con_S_c;
	//southern district---互联
	con_S_h=total["southArea"][2].y+total["southArea"][2].l+total["southArea"][2].d;
	total["southArea"][2].cn=con_S_h;
	//southern district---物联
	con_S_w=total["southArea"][3].y+total["southArea"][3].l+total["southArea"][3].d;
	total["southArea"][3].cn=con_S_w;
	//智能B
	con_S_B=total["southArea"][4].y+total["southArea"][4].l+total["southArea"][4].d;
	total["southArea"][4].cn=con_S_B;

	//Eastern District-----------China MobileChina Unicom电脑联接数
	var con_E_z=0;var con_E_c=0;var con_E_h=0;var con_E_w=0;var con_E_B=0;
	// Eastern District---展业
	con_E_z=total["eastArea"][0].y+total["eastArea"][0].l+total["eastArea"][0].d;
	total["eastArea"][0].cn=con_E_z;
	//Eastern District---车联
	con_E_c=total["eastArea"][1].y+total["eastArea"][1].l+total["eastArea"][1].d;
	total["eastArea"][1].cn=con_E_c;
	//Eastern District---互联
	con_E_h=total["eastArea"][2].y+total["eastArea"][2].l+total["eastArea"][2].d;
	total["eastArea"][2].cn=con_E_h;
	//Eastern District---物联
	con_E_w=total["eastArea"][3].y+total["eastArea"][3].l+total["eastArea"][3].d;
	total["eastArea"][3].cn=con_E_w;
	//智能B
	con_E_B=total["eastArea"][4].y+total["eastArea"][4].l+total["eastArea"][4].d;
	total["eastArea"][4].cn=con_E_B;


		//默认大区数据显示
		chinadata(total);
		function chinadata(data){
			//  China Mobile   China Unicom   China Telecom   Activation on service   out of service
			var y = 0, l = 0, d = 0, w = 0, z = 0, t = 0;
			var html = '';
			var dataHtml = $('.data');//左侧列表数据容器
			for(var item in data){
				// console.log(item)
				for(var i=0; i<data[item].length; i++){
					y += data[item][i].y;//每一个大区里各个分类里的 China Mobile相加
					l += data[item][i].l;
					d += data[item][i].d;
					w += data[item][i].w;
					z += data[item][i].z;
					t += data[item][i].t;

					html = '<h3>Operator</h3><div class="data_s"><p>China Mobile<span>'+y+'</span></p><p>China Unicom<span>'+l+'</span></p><p>China Telecom<span>'+d+'</span></p></div><h3>Card status</h3><div class="data_s"><p>Activation<span>'+w+'</span></p><p>on service<span>'+z+'</span></p><p>out of service<span>'+t+'</span></p></div>'
				}
				dataHtml.html(html);
			}
			return falg = false;
		}

		//柱状图各类数据默认显示
		(function(data){
			//Intelligent exhibition industry  smart car connected  smart connected  Carrier IOT  智能B
			var zn_z_t = 0, zn_c_t = 0, zn_h_t = 0, yy_w_t = 0, zn_b_t = 0;
			var zn_z_l = 0, zn_c_l = 0, zn_h_l = 0, yy_w_l = 0; zn_b_l = 0
			var zn_z_r = 0, zn_c_r = 0, zn_h_r = 0, yy_w_r = 0; zn_b_r = 0
			//North District柱状图
			zn_z_t += data['northArea'][0].y + data['northArea'][0].l + data['northArea'][0].d;
			zn_c_t += data['northArea'][1].y + data['northArea'][1].l + data['northArea'][1].d;
			zn_h_t += data['northArea'][2].y + data['northArea'][2].l + data['northArea'][2].d;
			yy_w_t += data['northArea'][3].y + data['northArea'][3].l + data['northArea'][3].d;
			zn_b_t += data['northArea'][4].y + data['northArea'][4].l + data['northArea'][4].d;

			//southern district柱状图
			zn_z_l += data['southArea'][0].y + data['southArea'][0].l + data['southArea'][0].d;
			zn_c_l += data['southArea'][1].y + data['southArea'][1].l + data['southArea'][1].d;
			zn_h_l += data['southArea'][2].y + data['southArea'][2].l + data['southArea'][2].d;
			yy_w_l += data['southArea'][3].y + data['southArea'][3].l + data['southArea'][3].d;
			zn_b_l += data['southArea'][4].y + data['southArea'][4].l + data['southArea'][4].d;

			//Eastern District柱状图
			zn_z_r += data['eastArea'][0].y + data['eastArea'][0].l + data['eastArea'][0].d;
			zn_c_r += data['eastArea'][1].y + data['eastArea'][1].l + data['eastArea'][1].d;
			zn_h_r += data['eastArea'][2].y + data['eastArea'][2].l + data['eastArea'][2].d;
			yy_w_r += data['eastArea'][3].y + data['eastArea'][3].l + data['eastArea'][3].d;
			zn_b_r += data['eastArea'][4].y + data['eastArea'][4].l + data['eastArea'][4].d;

			$('.column_t').find('.zn_z').text(zn_z_t)
			$('.column_t').find('.zn_c').text(zn_c_t)
			$('.column_t').find('.zn_h').text(zn_h_t)
			$('.column_t').find('.yy_w').text(yy_w_t)
			$('.column_t').find('.zn_b').text(zn_b_t)

			$('.column_l').find('.zn_z').text(zn_z_l)
			$('.column_l').find('.zn_c').text(zn_c_l)
			$('.column_l').find('.zn_h').text(zn_h_l)
			$('.column_l').find('.yy_w').text(yy_w_l)
			$('.column_l').find('.zn_b').text(zn_b_l)

			$('.column_r').find('.zn_z').text(zn_z_r)
			$('.column_r').find('.zn_c').text(zn_c_r)
			$('.column_r').find('.zn_h').text(zn_h_r)
			$('.column_r').find('.yy_w').text(yy_w_r)
			$('.column_r').find('.zn_b').text(zn_b_r)

			//柱状图变化公式
			function countBar(count){
				var barHeight=parseInt((count/1000000)*60);//以百万为基数，柱状图最高显示为60px
				if(count==0||count<10000)
				{
					return barHeight=5+'px';
				}else if(count>=1000000){//大于基数时为60px;
					return barHeight=60+'px';
				}else if(count<100000||count>10000){
					return barHeight=barHeight+'px';
				}
			}

			// North District柱状图-----随数据变化--zyj
			$('.colT').eq(0).css('height',countBar(zn_z_t));
			$('.colT').eq(1).css('height',countBar(zn_c_t));
			$('.colT').eq(2).css('height',countBar(zn_h_t));
			$('.colT').eq(3).css('height',countBar(yy_w_t));
			$('.colT').eq(4).css('height',countBar(zn_b_t));

			// southern district柱状图-----随数据变化--zyj
			$('.colL').eq(0).css('height',countBar(zn_z_l));
			$('.colL').eq(1).css('height',countBar(zn_c_l));
			$('.colL').eq(2).css('height',countBar(zn_h_l));
			$('.colL').eq(3).css('height',countBar(yy_w_l));
			$('.colL').eq(4).css('height',countBar(zn_b_l));

			// Eastern District柱状图-----随数据变化--zyj
			$('.colR').eq(0).css('height',countBar(zn_z_r));
			$('.colR').eq(1).css('height',countBar(zn_c_r));
			$('.colR').eq(2).css('height',countBar(zn_h_r));
			$('.colR').eq(3).css('height',countBar(yy_w_r));
			$('.colR').eq(4).css('height',countBar(zn_b_r));
		})(total);


		//获取各区的所有分类数据
		var falg = false;
		function getEachAreaData(data ,area){
			if(area=="northArea"){
				northFlag = true;
			}
			if(area=="eastArea"){
				eastFlag = true;
			}
			if(area=="southArea"){
				southFlag = true;
			}
			//  China Mobile   China Unicom   China Telecom   Activation on service   out of service
			var y = 0, l = 0, d = 0, w = 0, z = 0, t = 0;
			var html = '';
			var dataHtml = $('.data');//左侧列表数据容器
			var items = data[area]
			for(var i=0; i<items.length; i++){
				y += items[i].y;
				l += items[i].l;
				d += items[i].d;
				w += items[i].w;
				z += items[i].z;
				t += items[i].t;
				html = '<h3>Operator</h3><div class="data_s"><p>China Mobile<span>'+y+'</span></p><p>China Unicom<span>'+l+'</span></p><p>China Telecom<span>'+d+'</span></p></div><h3>Card status</h3><div class="data_s"><p>Activation<span>'+w+'</span></p><p>on service<span>'+z+'</span></p><p>out of service<span>'+t+'</span></p></div>'

			}
			dataHtml.html(html);
			return falg = true;
		};

		var china_t = $('#china_t');
		var china_l = $('#china_l');
		var china_r = $('#china_r');

		var map_t = $('#map_t');
		var map_l = $('#map_l');
		var map_r = $('#map_r');

		// var map_t = $('#map_t');


		//上部地图区域
		map_t.bind('mouseover' , function(ev){
			china_t.show()
		});

		china_t.bind('mouseout' , function(){
			china_t.hide()
		});
		china_t.on('click' , function(){
			$('#main').append('<img src="../static/ui3/images/t_map.png"  class="china map" id="t_map">');
			//点击当前大区高亮
			$('#china_t').hide();
			$('#china_l').hide();
			$('#china_r').hide();
			$('.scope').text('North District');
			$('.back').show();

			$('#l_map').remove();
			$('#r_map').remove();
			$('#china_t').attr('data-ok',true);
			$('#china_l').attr('data-ok',false);
			$('#china_r').attr('data-ok',false);
			//获取North District数据
			getEachAreaData(total , "northArea");
			//表格数据变化
			areaTable("northArea",25);
			//
			$(".column").find(".col").removeClass('show_bor');
			$('#t_map').on('click',function(){
				if(!northFlag){
					clickArea({
				 		"area":"northArea",
				 		"areaName":'North District'
				 	})
				}else{
					backAll();
					chinaTable(total);//表格返回around the country数据
				}
			});
		});

		//左侧地图区域
		map_l.bind('mouseover' , function(){
			china_l.show()
		});

		china_l.bind('mouseout' , function(){
			china_l.hide()
		});

		china_l.on('click' , function(){
			$('#main').append('<img src="../static/ui3/images/l_map.png"  class="china map china_l" id="l_map">');
			//点击当前大区高亮
			$('#china_t').hide();
			$('#china_l').hide();
			$('#china_r').hide();
			$('.scope').text('southern district');
			$('.back').show();
			$('#t_map').remove();
			$('#r_map').remove();
			$('#china_t').attr('data-ok',false);
			$('#china_l').attr('data-ok',true);
			$('#china_r').attr('data-ok',false);
			//获取southern district数据
			getEachAreaData(total , "southArea");
			//表格数据变化
			areaTable("southArea",26);
			//
			$(".column").find(".col").removeClass('show_bor');
			$("#l_map").on("click",function(){
				if(!southFlag){
					clickArea({
				 		"area":"southArea",
				 		"areaName":'southern district'
				 	})
				}else{
					backAll();
					chinaTable(total);//表格返回around the country数据
				}
			});

		});

		// //右侧地图区域
		map_r.bind('mouseover' , function(){
			china_r.show()
		});

		china_r.bind('mouseout' , function(){
			china_r.hide()
		});

		china_r.on('click' , function(){
			$('#main').append('<img src="../static/ui3/images/r_map.png"  class="china map china_r" id="r_map">');
			//点击当前大区高亮
			$('#china_t').hide();
			$('#china_l').hide();
			$('#china_r').hide();
			$('.scope').text('Eastern District');
			$('.back').show();
			$('#t_map').remove();
			$('#l_map').remove();
			$('#china_t').attr('data-ok',false);
			$('#china_l').attr('data-ok',false);
			$('#china_r').attr('data-ok',true);
			//获取Eastern District数据
			getEachAreaData(total , "eastArea");
			//表格数据变化
			areaTable("eastArea",27);
			//
			$(".column").find(".col").removeClass('show_bor');
			$("#r_map").on("click",function(){
				if(!eastFlag){
					clickArea({
				 		"area":"eastArea",
				 		"areaName":'Eastern District'
				 	})
				}else{
					backAll();
					chinaTable(total);//表格返回around the country数据
				}
			});
		});

		function titleShow(index,area){
			if(index == 0){
				$('.scope').html('<i class="allItem">around the country</i><i class="areaTit on">'+area+'</i><b class="backArea"><img src="../static/ui3/images/back_icon.png">Return all</b><b>Intelligent exhibition industry</b>');
			}else if(index == 1){
				$('.scope').html('<i class="allItem">around the country</i><i class="areaTit on">'+area+'</i><b class="backArea"><img src="../static/ui3/images/back_icon.png">Return all</b><b>smart car connected</b>');
			}else if(index == 2){
				$('.scope').html('<i class="allItem">around the country</i><i class="areaTit on">'+area+'</i><b class="backArea"><img src="../static/ui3/images/back_icon.png">Return all</b><b>smart connected</b>');
			}else if(index==3){
				$('.scope').html('<i class="allItem">around the country</i><i class="areaTit on">'+area+'</i><b class="backArea"><img src="../static/ui3/images/back_icon.png">Return all</b><b>Carrier IOT</b>');
			}else{
				$('.scope').html('<i class="allItem">around the country</i><i class="areaTit on">'+area+'</i><b class="backArea"><img src="../static/ui3/images/back_icon.png">Return all</b><b>智能BBB</b>');
			}
		}

		//返回around the country
		$('.back').bind('click' , function(){
			$('.column').find('.col').removeClass('show_bor');
			$('.china_t').attr('data-ok',false);
			$('.china_l').attr('data-ok',false);
			$('.china_r').attr('data-ok',false);
			$(this).hide();
			$('.scope').text('around the country');
			$('#t_map').remove();
			$('#l_map').remove();
			$('#r_map').remove();
			chinadata(total);
			chinaTable(total);//表格数据返回around the country
		})
		//表格数据--table---around the country数据
		chinaTable(total);

		function chinaTable(data){
			// cn--连接数,earning--en,fn--流量,em--收入环比,crNum--完成率
			var cn = 0, en = 0, fn = 0, em = 0;
			for(var item in data){
				// console.log(item)
				for(var i=0; i<data[item].length; i++){
					cn += data[item][i].cn;//每一个大区里各个分类里的 China Mobile相加
					console.log(cn);
					en += data[item][i].en;
					fn += data[item][i].fn;
					em += data[item][i].em;
				}
				$('.data_conn').text(cn);//连接数
//				$('.data_earn').text(en);//收入
				$('.data_earn').text('--');//收入
				$('.data_flow').text(fn);//流量
//				$('.data_earnMom').text(em);//收入环比
				$('.data_earnMom').text('--');//收入环比
				$('.data_comp').text('--');
				crNum =65;//完成率--(实际完成数/计划完成数)*100%

			}
		}

		//表格数据--table---各区、各项(柱状图)数据
		function barTable(area,borThis,crNumData){
			$('.data_conn').text(total[area][borThis].cn);//连接数
//			$('.data_earn').text(total[area][borThis].en);//收入
			$('.data_flow').text(total[area][borThis].fn);//流量
//			$('.data_earnMom').text(total[area][borThis].em);//收入环比
//			crNum =crNumData;//完成率--(实际完成数/计划完成数)*100%
			
			$('.data_earn').text('--');//收入
			$('.data_earnMom').text('--');//收入环比
			$('.data_comp').text('--');
		}

		//表格数据--table---各区数据
		function areaTable(area){
			// cn--连接数,收入--en,fn--流量,em--收入环比,cr--完成率
			var cn = 0, en = 0, fn = 0, em = 0, cr=0;
			var itemA=total[area];
			cn = itemA[0].cn+itemA[1].cn+itemA[2].cn+itemA[3].cn;
			en = itemA[0].en+itemA[1].en+itemA[2].en+itemA[3].en;
			fn = itemA[0].fn+itemA[1].fn+itemA[2].fn+itemA[3].fn;
			em = itemA[0].em+itemA[1].em+itemA[2].em+itemA[3].em;
			// cr = itemA.cr+itemA.cr+itemA.cr+itemA[2].er+itemA[3].er;
			$('.data_conn').text(cn);//连接数
//			$('.data_earn').text(en);//收入
			$('.data_flow').text(fn);//流量
//			$('.data_earnMom').text(em);//收入环比
			//完成率
//			crNum =60;//完成率--(实际完成数/计划完成数)*100%
			
			$('.data_earn').text('--');//收入
			$('.data_earnMom').text('--');//收入环比
			$('.data_comp').text('--');

		}

		//表格数据--table---around the country各项数据和显示
		function itemAllTable(borThis){
			// cn--连接数,earning--en,fn--流量,em--收入环比,cr--完成率
			var cn = 0, en = 0, fn = 0, em = 0, cr=0;
			cn = total['northArea'][borThis].cn+total['southArea'][borThis].cn+total['eastArea'][borThis].cn;
			en = total['northArea'][borThis].en+total['southArea'][borThis].en+total['eastArea'][borThis].en;
			fn = total['northArea'][borThis].fn+total['southArea'][borThis].fn+total['eastArea'][borThis].fn;
			em = total['northArea'][borThis].em+total['southArea'][borThis].em+total['eastArea'][borThis].em;
			// cr = total['northArea'][borThis].cr+total['southArea'][borThis].cr+total['eastArea'][borThis].cr;
			$('.data_conn').text(cn );//连接数
//			$('.data_earn').text(en);//收入
			$('.data_flow').text(fn);//流量
//			$('.data_earnMom').text(em);//收入环比
			//完成率
//			crNum =50;//完成率--(实际完成数/计划完成数)*100%
			
			$('.data_earn').text('--');//收入
			$('.data_earnMom').text('--');//收入环比
			$('.data_comp').text('--');
		}

		//点击柱状图左侧获取对应的数据--zyj
		function getDataArea(cls,thisEq,area,areaName){
			//显示效果
			$('.col').removeClass('show_bor');
			$(cls).eq(thisEq).addClass('show_bor');
			//数据获取
			var y = 0, l = 0, d = 0, w = 0, z = 0, t = 0;
			var html = '';
			var dataHtml = $('.data');//左侧列表数据容器
			titleShow(thisEq,areaName);
			y = total[area][thisEq].y;
			l = total[area][thisEq].l;
			d = total[area][thisEq].d;
			w = total[area][thisEq].w;
			z = total[area][thisEq].z;
			t = total[area][thisEq].t;
			html = '<h3>Operator</h3><div class="data_s"><p>China Mobile<span>'+y+'</span></p><p>China Unicom<span>'+l+'</span></p><p>China Telecom<span>'+d+'</span></p></div><h3>Card status</h3><div class="data_s"><p>Activation<span>'+w+'</span></p><p>on service<span>'+z+'</span></p><p>out of service<span>'+t+'</span></p></div>'
					dataHtml.html(html);
		}
		//点击大区
		var northFlag=false; //North District
		var southFlag=false;//southern district
		var eastFlag=false;//东西

		function clickArea(opt){
			getEachAreaData(total , opt.area);
		 	$('.scope').text(opt.areaName);
			$('.back').show();
		 	$(".column").find(".col").removeClass('show_bor');
		}
		//大区选中状态下，再次点击--《返回around the country》
		function backAll(){
			$('.scope').text('around the country');
			$('#t_map').remove();
			$('#l_map').remove();
			$('#r_map').remove();
			$('.col').removeClass('show_bor');
			chinadata(total);
			northFlag=false;
			southFlag=false;
			eastFlag=false;
			$('.back').hide();

			//地图鼠标划过状态隐藏
			$('#china_t').hide();
			$('#china_l').hide();
			$('#china_r').hide();
		}
		//四大项around the country数据
		function itemAll(borThis){
			var y = 0, l = 0, d = 0, w = 0, z = 0, t = 0;
			var html = '';
			var dataHtml = $('.data');//左侧列表数据容器
			y = total['northArea'][borThis].y+total['southArea'][borThis].y+total['eastArea'][borThis].y;
			l = total['northArea'][borThis].l+total['southArea'][borThis].l+total['eastArea'][borThis].l;
			d = total['northArea'][borThis].d+total['southArea'][borThis].d+total['eastArea'][borThis].d;
			w = total['northArea'][borThis].w+total['southArea'][borThis].w+total['eastArea'][borThis].w;
			z = total['northArea'][borThis].z+total['southArea'][borThis].z+total['eastArea'][borThis].z;
			t = total['northArea'][borThis].t+total['southArea'][borThis].t+total['eastArea'][borThis].t;
			html = '<h3>Operator</h3><div class="data_s"><p>China Mobile<span>'+y+'</span></p><p>China Unicom<span>'+l+'</span></p><p>China Telecom<span>'+d+'</span></p></div><h3>Card status</h3><div class="data_s"><p>Activation<span>'+w+'</span></p><p>on service<span>'+z+'</span></p><p>out of service<span>'+t+'</span></p></div>'
			dataHtml.html(html);
		}

		//点击左侧around the country和当前区域该项切换，点击返回某区，回该区全部数据
		function chinaAndArea(borThis,area){
			var saveAreaData=$('.data').html();//存储左侧数据
			// var saveTableData=$('.data_main').html();//存储表格数据
			var _data_time=$('.data_time').html();
			var _data_earn=$('.data_earn').text();
			var _data_flow=$('.data_flow').text();
			var _data_earnMom=$('.data_earnMom').text();

			//点击around the country，是显示around the country该项数据
			$('.allItem').on('click',function(){
				itemAll(borThis);//左侧显示around the country数据
				itemAllTable(borThis)//表格显示around the country该项数据
				$('.areaTit').removeClass('on');
				$('.allItem').addClass('on');
				//地图选中状态隐藏
				$('.'+area).hide();
				//Return all各项数据
				$('.colT').eq(borThis).addClass('show_bor');
				$('.colL').eq(borThis).addClass('show_bor');
				$('.colR').eq(borThis).addClass('show_bor');
			});

			//点击区域名称，显示该区域数据
			$('.areaTit').on('click',function(){
				$('.allItem').removeClass('on');
				$('.areaTit').addClass('on')
				$('.data').html(saveAreaData);//左侧显示around the country数据
				// $('.data_main').html(saveTableData);//表格显示around the country数据
				$('.data_time').html(_data_time);
				$('.data_earn').text(_data_earn);
				$('.data_flow').text(_data_flow);
				$('.data_earnMom').text(_data_earnMom);

				//地图选中状态显示
				$('.'+area).show();

				//重消掉之前加的show_bor
				if(area=='china_t'){
					$('.colL').eq(borThis).removeClass('show_bor');
					$('.colR').eq(borThis).removeClass('show_bor');
				}else if(area=='china_l'){
					$('.colT').eq(borThis).removeClass('show_bor');
					$('.colR').eq(borThis).removeClass('show_bor');
				}else{
					$('.colT').eq(borThis).removeClass('show_bor');
					$('.colL').eq(borThis).removeClass('show_bor');
				}
			});
		}
		//左侧：点击---《Return all》，返回某区全部数据
		function backArea(areaNameStr,data,area){
			$('.backArea').on('click',function(){
				$('.scope').text(areaNameStr);
				$('.back').show();
				$('.col').removeClass('show_bor');
				if(area=='northArea'){
					$('#t_map').show();
				}else if(area=='southArea'){
					$('#l_map').show();
				}else{
					$('#r_map').show();
				}
				getEachAreaData(data,area);//显示大区数据
				areaTable(area);//表格--大区数据
			});
		}
		//North District-t-各柱状图数据
		$('.colT').on('click',function(){
			var borThis=$('.colT').index(this);
			if($(this).hasClass('show_bor')){//取消柱状图选中状态后，左侧数据恢复大区数据
				$(this).removeClass('show_bor');
				getEachAreaData(total , "northArea");//左侧显示区数据
				areaTable("northArea")//表格--大区数据
				$('.scope').text('North District');
				$('.back').hide();

			}else{
				// var thisEq=$('.colT').index(this);//指定当前this
				getDataArea('.colT',borThis,'northArea','North District');//左侧显示当前柱所对数据
				barTable("northArea",borThis)//表格---当前区,当前项的当前数据
				$('.back').hide();

				//显示效果
				$('#l_map').remove();
				$('#r_map').remove();
				$('#china_l').css('display','none');
				$('#china_r').css('display','none');

				//添加选中--上--North District-创建图片
				if(!$("#t_map")[0]){
					$('#main').append('<img src="../static/ui3/images/t_map.png"  class="china map china_t" id="t_map">');
					$('#t_map').on('click',function(){
						if(!northFlag){
							clickArea({
						 		"area":"northArea",
						 		"areaName":'North District'
						 	})
						}else{
							backAll();
						}
					});
				}
				chinaAndArea(borThis,'china_t');//点击左侧around the country和当前区域切换
				backArea('North District',total,"northArea");//点击返回North District，列表与表格返回North District总数据
			}

        });

        //southern district-l-各柱状图数据
		$('.colL').on('click',function(){
			var borThis=$('.colL').index(this);
			if($(this).hasClass('show_bor')){//取消柱状图选中状态后，左侧数据恢复大区数据
				$(this).removeClass('show_bor');
				getEachAreaData(total , "southArea");//左侧显示区数据
				areaTable("southArea")//表格--大区数据
				$('.scope').text('southern district');
				$('.back').hide();

			}else{
				var thisEq=$('.colL').index(this);
				$(this).addClass('show_bor');
				getDataArea('.colL',thisEq,'southArea','southern district');
				barTable("southArea",thisEq)//当前区,当前项的当前数据
				$('.back').hide();

				//显示效果
				$('#r_map').remove();
				$('#t_map').remove();
				$('#china_r').css('display','none');
				$('#china_t').css('display','none');

				//添加选中左-southern district
				if(!$("#l_map")[0]){
					$('#main').append('<img src="../static/ui3/images/l_map.png"  class="china map china_l" id="l_map">');
					$('#l_map').on('click',function(){
					 	if(!southFlag){
							clickArea({
						 		"area":"southArea",
						 		"areaName":'southern district'
						 	})
						}else{
							backAll();
						}
					});
				}
				chinaAndArea(borThis,'china_l');//点击左侧around the country和当前区域切换
				backArea('southern district',total,"southArea");//点击返回southern district，列表与表格返回southern district总数据
			}

        });

        //Eastern District-r-各柱状图数据
		$('.colR').on('click',function(){
			var borThis=$('.colR').index(this);
			if($(this).hasClass('show_bor')){//取消柱状图选中状态后，左侧数据恢复大区数据
				$(this).removeClass('show_bor');
				// chinadata(total);//显示大区数据
				getEachAreaData(total , "eastArea");//左侧显示区数据
				areaTable("eastArea")//表格--大区数据
				$('.scope').text('Eastern District');
				$('.back').hide();

			}else{
				var thisEq=$('.colR').index(this);
				getDataArea('.colR',thisEq,'eastArea','Eastern District');
				barTable("eastArea",thisEq)//当前区,当前项的当前数据
				$('.back').hide();

				//显示效果
				$('#l_map').remove();
				$('#t_map').remove();
				$('#china_l').css('display','none');
				$('#china_t').css('display','none');
				//添加选中--右--Eastern District
				if(!$("#r_map")[0]){
					$('#main').append('<img src="../static/ui3/images/r_map.png"  class="china map china_r" id="r_map">');
					$("#r_map").on("click",function(){
						if(!eastFlag){
							clickArea({
						 		"area":"eastArea",
						 		"areaName":'Eastern District'
						 	})
						}else{
							backAll();
						}
					});
				}
				chinaAndArea(borThis,'china_r');//点击左侧around the country和当前区域切换
				backArea('Eastern District',total,"eastArea");//点击返回Eastern District，列表与表格返回Eastern District总数据
			}

        });

	};

	/**
		数据划分时间段
	**/
	var dataPartition = function(){
		$('.list').find('a').bind('click' , function(ev){
			var timeVal = $(this).text();
			$('.time').find('em').text(timeVal);
			ev.preventDefault()
		})
	}

	return {
		getMap:getMap,
		dataPartition:dataPartition
	}
}())