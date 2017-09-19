		//查询到的数据
	 	var recordData = [];

	 	var falg1 = false;
		var falg2 = false;

		var calendar = new datePicker();
		calendar.init({
			'trigger': '#startTime', /*按钮选择器，用于触发弹出插件*/
			'type': 'date',/*模式：date日期；datetime日期时间；time时间；ym年月；*/
			'minDate':'1900-1-1',/*最小日期*/
			'maxDate':'2100-12-31',/*最大日期*/
			'onSubmit':function(){/*确认时触发事件*/
				var theSelectData=calendar.value;
				if(!theSelectData == ''){
					falg1 = true;
				}
				cheackDo()
				console.log(falg1)
			},
			'onClose':function(){/*取消时触发事件*/
				
			}
		});
		
		var calendar2 = new datePicker();
		calendar2.init({
			'trigger': '#overTime', /*按钮选择器，用于触发弹出插件*/
			'type': 'date',/*模式：date日期；datetime日期时间；time时间；ym年月；*/
			'minDate':'1900-1-1',/*最小日期*/
			'maxDate':'2900-12-31',/*最大日期*/
			'onSubmit':function(){/*确认时触发事件*/
				var theSelectData=calendar2.value;
				if(!theSelectData == ''){
					falg2 = true;
				}
				console.log(falg2)
				cheackDo()
			},
			'onClose':function(){/*取消时触发事件*/
				
			}
		});

		//如果选择了查询时间范围就可以查询数据,并且把查询的数据以列表的形式显示出来
		function cheackDo(){
			if(falg1 && falg2){
				$('.login_btn').addClass('active_login');
				$('.login_btn').bind('touchstart' , function(){
					var msg = layer.msg('数据加载中', {
						icon : 16,
						time : 20000, //20秒关闭（如果不配置，默认是3秒）
						area : [ '182px', '66px' ],
						offset : '215px'
					});
					
					var iccid = $("#iccid").val();
					var startDate = $("#startTime").val();
					var endDate = $("#overTime").val();
					
					if(iccid.indexOf('898602') > -1 || iccid.indexOf('898603') > -1 ){
	   					layer.alert("暂不支持移动和电信卡号查询");
	   				}else{
	   					$.ajax({
	   						url:"glaH5AppQuery/queryPurchaseHistory",
	   						type:"post",
	   						data:{'iccid':iccid, 'startDate':startDate, 'endDate':endDate},
	   						success:function(result){
	   							recordData = (new Function("return " + result))(); 
	   							$('.search_data_list').empty();
	   							var html = '<ul>';
	   							for(var i=0; i<recordData.length; i++){
	   								html+='<li><span class="pull-left">使用'+ recordData[i].employ +'M<br/><em>持续'+ recordData[i].continued +'</em></span><span class="pull-right">'+ recordData[i].dateTime +'<br/><em></em></span></li>'
	   							};
	   							html+='</ul>';
	   							if(recordData.length == 0){
	   								html="<p class='text-center'><img src='static/newH5/images/history.png' width='32%'><span>消费记录暂无</span></p>";
	   							}
	   							$('.search_data_list').append(html);
	   							layer.close(msg);
	   						},
	   						error:function(){
	   							layer.alert("查询数据失败，请联系客服！");
	   							layer.close(msg);
	   						}
	   					});
	   				}
					
				})
				
			}
		}