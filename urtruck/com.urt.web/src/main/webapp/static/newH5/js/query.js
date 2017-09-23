function request(jsonData) {
			var msg = layer.msg('数据加载中', {
				icon : 16,
				time : 20000, //20秒关闭（如果不配置，默认是3秒）
				area : [ '182px', '66px' ],
				offset : '215px'
			});
			$.ajax({
				type : "post",
				url : "/glaH5AppQuery/queryFlow",
				data : jsonData,
				cache : false,
				success : function(data) {
					var jsonObj = eval("(" + data + ")");
					  var statusCode=parseInt(jsonObj.retcode);
                      switch (statusCode) {
                          case 1:
                        	  showResult(data);
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
                          case -3:
                        	  layer.alert("获取当前资费计划失败");
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
                        	  layer.alert("本用户没有操作该iccid权限");
                              break;
                          case -11:
                        	  layer.alert("系统中未查询到该iccid");
                              break;
                      }
					layer.close(msg);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					layer.close(msg);
					layer.alert("数据加载超时！！");
				}

			});
		}
function showResult(data){
	data = (new Function("return " + data))();
	$("#updateTime").html("最后更新时间<br/>"+data.timestamp);
	if(data.packageRemaining == 0 && data.packageCount == 0){
		dataFlag = false;
	}else{
		dataFlag = true;
	}
	var init = {
			packageRemaining:data.packageRemaining,  //设置当前包剩余流量
			dataRemaining:data.dataRemaining,						   //设置剩余总流量
   			packageExpirationDate:data.packageExpirationDate.split("T")[0],//设置当前包有效时间
   			packageNum:data.packageNum,								//设置包个数
   			packageCount:data.packageCount, //设置当前包总流量
   			x:145,     //圆心得坐标
   			y:145,
   			c:120     //圆的半径
   		}
   		//流量监控图
   		yp(init);
}


$(document).ready(function(){
	//流量监控图
	box = document.getElementById('box');
	cvs = document.getElementById('cvs');
	oP = box.getElementsByTagName('p')[0];
	context = cvs.getContext('2d');
	dataFlag = true;
});
function yp(init){
	
	context.clearRect(0,0,cvs.width,cvs.height);//清除画布
	var total = parseInt(init.packageCount)+parseInt(init.packageRemaining)
	var _inow = Math.floor(init.packageRemaining/total*360); //剩余流量与圆盘的比例
	$('#box').find('h2').remove();
	var h2 = document.createElement('h2');
	h2.innerHTML = "本月使用" +init.packageCount+ "M";
	$("#packet").html("流量包"+init.packageNum+"个<br/>总流量"+init.dataRemaining+"M");
	if(dataFlag){

		h2.className = 'text-center l_m l_m2';
		oP.innerHTML = "当前流量包剩余<span class='total'>"+init.packageRemaining+"<em>M</em></span><span>"+(init.packageExpirationDate)+"到期</span>"
	}else{

		h2.className = 'text-center l_m';
		oP.innerHTML = "<span class='total' style='position:absolute;top:27px;width:100px;font-size: 16px;color:#666;'>暂无数据</span>"
	}
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
	if(dataFlag){
		context.fillStyle = '#d60a07';
	}else{
		context.fillStyle = '#f2f2f2';
	}
	context.moveTo(init.x,init.y);
	context.arc(init.x,init.y,init.c*(12/20),0,360*Math.PI/180,false);
	context.fill();
	context.closePath();
	
}