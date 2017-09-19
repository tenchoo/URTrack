function keyup(iccid){
	$("#errorMsg").html("");
	if (iccid != '' && iccid != null && iccid.length > 18) {
		$.ajax({
   			url:"laouser/showH5GoodsRealease",
   			data:{
   				"iccid2":iccid,			
   			},
   			success:function(data){
   				var list = data;
   				
   				if(list == '' || list == null || list.indexOf("<htm") > -1){
   					   layer.alert("设备没有提前绑定产品")
   					   return;
   				}else{
   					$("#flows_content").html("");
   				}
   				
   				var index = (list.length)%2;
   				var line = Math.ceil((list.length)/2)
   				var j = 0;
   				var content = "";
   				for ( var i = 1; i <= line; i++) {
   					if(index == 1 && line ==1){
   						if(list[j].discount == false){
   							if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p></div></li>");
   							}else{
   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	 </div></li>");
   							}
   						}else{
   							if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div></li>");
   							}else{
   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div></li>");
   							}
   						}
   					}else{
   						if(index == 1 && i == line){
   							if(list[j].discount == false){
   								if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
	   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> </div></li>");
	   							}else{
	   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left  '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	 </div></li>");
	   							}
	   						}else{
	   	   						if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
	   								$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div></li>");
	   							}else{
	   	   							$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div></li>");
	   							}
	   						}
   						}else{
   							if(list[j].discount == false){
   								if(list[j+1].discount == false){
   									if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   										content = "<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> </div>";
   									}else{
   										content = "<li class='row'><div class='col-xs-6_d pull-left  '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	 </div>";
   									}
   									if(list[j+1].goodsId == 3111129400000351 || list[j+1].goodsId == 3111130210000352){
   		   								content = content + "<div class='col-xs-6_d pull-right z_k'> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName.substring(0,list[j+1].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j+1].goodsName.substring(list[j+1].goodsName.indexOf("送"),list[j+1].length)+"</h5>		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p></div></li>";
   		   							}else {
   		   								content = content + "<div class='col-xs-6_d pull-right '> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName+"</h3> 		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p></div></li>";
   		   							}
   									$("#flows_content").append(content);
   									content="";
   								}else{
   									if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   										content = "<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> </div>";
   									}else{
   										content = "<li class='row'><div class='col-xs-6_d pull-left  '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	 </div>";
   									}
   									if(list[j+1].goodsId == 3111129400000351 || list[j+1].goodsId == 3111130210000352){
   		   								content = content + "<div class='col-xs-6_d pull-right z_k'> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName.substring(0,list[j+1].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j+1].goodsName.substring(list[j+1].goodsName.indexOf("送"),list[j+1].length)+"</h5>		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p> <span class='zk'>惠</span></div></li>";
   		   							}else {
   		   								content = content + "<div class='col-xs-6_d pull-right '> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName+"</h3> 		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p> 	<span class='zk'>惠</span></div></li>";
   		   							}
   									$("#flows_content").append(content);
   									content="";
   								}
   							}else{
   								if(list[j+1].discount == false){
   									if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   										content = "<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div>";
   									}else{
   										content = "<li class='row'><div class='col-xs-6_d pull-left  '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p><span class='zk'>惠</span> </div>";
   									}
   									if(list[j+1].goodsId == 3111129400000351 || list[j+1].goodsId == 3111130210000352){
   		   								content = content + "<div class='col-xs-6_d pull-right z_k'> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName.substring(0,list[j+1].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j+1].goodsName.substring(list[j+1].goodsName.indexOf("送"),list[j+1].length)+"</h5>		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p></div></li>";
   		   							}else {
   		   								content = content + "<div class='col-xs-6_d pull-right '> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName+"</h3> 		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p></div></li>";
   		   							}
   									$("#flows_content").append(content);
   									content="";
   								}else{
   									if(list[j].goodsId == 3111129400000351 || list[j].goodsId == 3111130210000352){
   										content = "<li class='row'><div class='col-xs-6_d pull-left z_k'> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName.substring(0,list[j].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j].goodsName.substring(list[j].goodsName.indexOf("送"),list[j].length)+"</h5> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div>";
   									}else{
   										content = "<li class='row'><div class='col-xs-6_d pull-left  '> <input type='text'  hidden  value='"+list[j].goodsId+"'> <input type='text' hidden  value='"+list[j].goodsReleaseId+"'>	<h3>"+list[j].goodsName+"</h3> 	<p><span>¥<b>"+list[j].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>有效期"+list[j].releaseCycle+list[j].unit+"</p> 	<span class='zk'>惠</span> </div>";
   									}
   									if(list[j+1].goodsId == 3111129400000351 || list[j+1].goodsId == 3111130210000352){
   		   								content = content + "<div class='col-xs-6_d pull-right z_k'> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName.substring(0,list[j+1].goodsName.indexOf("送"))+"</h3>	<h5>"+list[j+1].goodsName.substring(list[j+1].goodsName.indexOf("送"),list[j+1].length)+"</h5>		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p> <span class='zk'>惠</span></div></li>";
   		   							}else {
   		   								content = content + "<div class='col-xs-6_d pull-right '> <input type='text'  hidden  value='"+list[j+1].goodsId+"'> <input type='text' hidden  value='"+list[j+1].goodsReleaseId+"'> 	<h3>"+list[j+1].goodsName+"</h3> 		<p><span>¥<b>"+list[j+1].goodsPrices+"</b> 元</span></p><p class='inow_data'>有效期"+list[j+1].releaseCycle+list[j+1].unit+"</p> 	<span class='zk'>惠</span></div></li>";
   		   							}
   									$("#flows_content").append(content);
   									content="";
   								}
   							}
   						}
   					}
					j = j+2;
   				}
   				/*for ( var i = 0; i < list.length; i=i+2) {
   					//$("#flows_content").append("<div class='col-xs-6 '><a href=\"javascript:selectOne("+list[i].goodsPrices+", '"+list[i].goodsName+"', "+list[i].goodsId+", "+list[i].goodsReleaseId+");\" class='thumbnail' goodDesc='"+list[i].goodsDesc+"' payAmount='"+list[i].goodsPrices+"' > <img src='"+list[i].goodsPic+"'></a></div>");
					$("#flows_content").append("<li class='row'><div class='col-xs-6_d pull-left  z_k'> <input type='text'  hidden name='goodsId' value='"+list[i].goodsId+"'> <input type='text' hidden name='goodsReleaseId' value='"+list[i].goodsReleaseId+"'>	<h3>"+list[i].goodsDesc+"</h3> 	<p><del>¥<b>45</b> 元 </del><span>¥<b>"+list[i].goodsPrices+"</b> 元</span></p> 	<p class='inow_data'>当月有效</p> 	<span class='zk zk_5'><strong>5</strong><sup>折</sup></span> </div>");
   					if(i+1 < list.length){
   						$("#flows_content").append("<div class='col-xs-6_d pull-right z_k'> <input type='text'  hidden name='goodsId' value='"+list[i].goodsId+"'> <input type='text' hidden name='goodsReleaseId' value='"+list[i].goodsReleaseId+"'> 	<h3>"+list[i+1].goodsDesc+"</h3> 		<p><del>¥<b>45</b> 元 </del><span>¥<b>"+list[i+1].goodsPrices+"</b> 元</span></p> 		<p class='inow_data'>180天有效</p> 	z_k	<span class='zk'>直降<br/>10元</span> 	</div></li>");
   					}
   				}*/
   			},
		   error:function(){
			   $("#iccid").val(iccid);
			   layer.alert("查询产品失败！");
			}
   		});
	}else{
		$("#flows_content").html("");
		layer.alert("ICCID 输入错误！");
	}
}
								