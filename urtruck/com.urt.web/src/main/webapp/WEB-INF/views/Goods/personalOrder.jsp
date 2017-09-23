<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/lib/pager-taglib.jar"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Individual.order' /></title>
<base href="<%=basePath%>" />

<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript"
	src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript">
$(function(){
	$.post("<%=request.getContextPath()%>/laouser/queryIccids",function(data){
		var iccids = eval(data);
		for ( var i = 0; i < iccids.length; i++) {
			$("#iccid").append("<option value='"+iccids[i].iccid+"'>"+iccids[i].iccid+"</option>");
		}
	},"json");
	
	
	$("#iccid").change(function(){
		debugger;
		var iccd = $("#iccid").val();
		$.ajax({
			url:"<%=request.getContextPath()%>/laouser/showUser",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,			
			},
			success:function(data){
				$("#lable1").text(data.objData.dataRemaining);
				$("#lable2").text(data.objData.cardState);
				$("#lable3").text(data.objData.value1);
				$("#lable4").text(data.objData.value2);
				$("#lable5").text(data.objData.channelCustName);
				$("#lable6").text(data.objData.operatorName);
			}
		});
	});

	$("#iccid").change(function(){
		debugger;
		$('#table1,#div,#priceDiv').hide();
		var iccd = $("#iccid").val();
		$.ajax({
			url:"<%=request.getContextPath()%>/laouser/showGoodsRealease",
			type:"post",
			dataType: "json",
			data:{
				"iccid":iccd,			
			},
			success:function(data){
				$('#table1,#div').show();
				console:{"data:"+data};
				$("#div").text("");
 				var list = eval(data);
				for ( var i = 0; i < list.length; i++) {
					$("#div").append(
							
/* 							"<div class='container-fluid'>"+
							"<div class='row'>"+
								"<div class='col-md-4 col-lg-4 plrNone' style='color: red;' onclick='priceChange("+list[i].goodsId+")'>"+
								"<table id='table1' border='1'class='table table-border table-bordered' style='margin-top:10px;'>"+
									"<tr>"+
										"<td rowspan='3' align='center'><img alt='路径错误' src='"+list[i].goodsPic+"'></td>"+
										"<td align='center'><input type='hidden' name='goodsId' value="+list[i].goodsId+">"+list[i].goodsName+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center'>"+list[i].goodsPrices+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center'>"+list[i].operatorsName+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td colspan='2'>备注:</td>"+
									"</tr>"+
								"</table>"+
								"</div>"+
							"</div>"+
						"</div>" */
							
							
						"<div class='col-sm-3 col-md-3 col-lg-3' style='color: red;padding-left:0px;' onclick='priceChange("+list[i].goodsId+","+list[i].goodsReleaseId+")'>"+
						"<table id='table1' border='1' class='table table-border table-bordered' style='margin-top:10px;'>"+
							"<tr>"+
								"<td class='prdList' style='text-align:center;border:none;border-bottom: 1px solid #ddd;'><img alt='路径错误' src='"+list[i].goodsPic+"' width='159' height='105'>"+
								"<p class='text-center font12'><input type='hidden' name='goodsId' value="+list[i].goodsId+">"+list[i].goodsName+"&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].operatorsName+"&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].goodsPrices+"</p>"+																	
								"</td>"+
							"</tr>"+
						"</table>"+
						"</div>"
					
						
					);
				}	 		
			}
		});
	});
	

	$("#button1").click(function(){
		debugger;		
		var iccd2 = $("#iccid2").val();
		$('#table1,#div,#priceDiv').hide();
		$.ajax({
			url:"<%=request.getContextPath()%>/laouser/showUser",
			type:"post",
			dataType: "json",
			data:{
				"iccid2":iccd2,			
			},
			success:function(data){			
				if(data.success){
					$("#lable1").text(data.objData.dataRemaining);
					$("#lable2").text(data.objData.cardState);
					$("#lable3").text(data.objData.value1);
					$("#lable4").text(data.objData.value2);
					$("#lable5").text(data.objData.channelCustName);
					$("#lable6").text(data.objData.operatorName);					
				}else{
					alert(data.msg);					
				}																
			}
		});
		
		
		
		var iccd2 = $("#iccid2").val();
		$.ajax({
			url:"<%=request.getContextPath()%>/laouser/showGoodsRealease",
			type:"post",
			dataType: "json",
			data:{
				"iccid2":iccd2,			
			},
			success:function(data){
				$('#div,#table1').show();
	         	console:{"data:"+data};
				$("#div").text("");
					var list = eval(data);
				for ( var i = 0; i < list.length; i++) {
					$("#div").append(
/* 							"<div class='container-fluid'>"+
							"<div class='row'>"+
								"<div class='col-md-4 col-lg-4 plrNone' style='color: red;' onclick='priceChange("+list[i].goodsId+")'>"+
								"<table id='table1' border='1' class='table table-border table-bordered' style='margin-top:10px;'>"+
									"<tr>"+
										"<td rowspan='3' align='center'><img alt='路径错误' src='"+list[i].goodsPic+"'></td>"+
										"<td align='center'><input type='hidden' name='goodsId' value="+list[i].goodsId+">"+list[i].goodsName+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center'>"+list[i].goodsPrices+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td align='center'>"+list[i].operatorsName+"</td>"+
									"</tr>"+
									"<tr>"+
										"<td colspan='2'>备注:</td>"+
									"</tr>"+
								"</table>"+
								"</div>"+
							"</div>"+
						"</div>" */
							"<div class='col-sm-3 col-md-3 col-lg-3' style='color: red;padding-left:0px;' onclick='priceChange("+list[i].goodsId+","+list[i].goodsReleaseId+")'>"+
							"<table id='table1' border='1' class='table table-border table-bordered' style='margin-top:10px;'>"+
								"<tr>"+
									"<td class='prdList' style='text-align:center;border:none;border-bottom: 1px solid #ddd;'><img alt='路径错误' src='"+list[i].goodsPic+"' width='159' height='105'>"+
									"<p class='text-center font12'><input type='hidden' name='goodsId' value="+list[i].goodsId+">"+list[i].goodsName+"&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].operatorsName+"&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].goodsPrices+"元&nbsp;&nbsp;&nbsp;&nbsp;"+list[i].releaseCycle+"</p>"+																	
									"</td>"+
								"</tr>"+
							"</table>"+
							"</div>"
			
					
					
					);
				}	 		
			}
		});
	});	
	
	
	
	
	
	
	
});
var goodsIdd;
function priceChange(goodsId,goodsReleaseId) {
	debugger;
	$.ajax({
		url:"<%=request.getContextPath()%>/laouser/showPrice",
		type:"post",
		dataType: "json",
		data:{
			"goodsId":goodsId,			
			"goodsReleaseId":goodsReleaseId		
		},
		success:function(data){
			$("#priceDiv").text("");
			var newPrice = eval(data);
			$('#priceDiv').show();
			$("#priceDiv").append("<div style='margin:20px 0 20px 15px;font-weight:600;font-size:14px;'><span class='font12'><fmt:message bundle='${pageScope.bundle}'  key='Payment' />:</span>&nbsp;&nbsp;&nbsp;<span id='newPrice' style='color:red;font-weight:700;'>"+newPrice+"</span ><span class='font12'><fmt:message bundle='${pageScope.bundle}'  key='yuan' /></span></div><input type='submit' class='btn btn-primary radius' margin-left:15px; value='<fmt:message bundle='${pageScope.bundle}'  key='Instant.recharge' />'/>");
			goodsIdd=goodsId;
		}
	});
}

</script>

</head>
<body>
	<div class="seconSec">
		<h1><fmt:message bundle='${pageScope.bundle}'  key='Individual.order' /></h1>

		<form  role="form"  method="post" action="<%=request.getContextPath()%>/laouser/toConfirm">
		<div class="cl pd-5 mt-10">
			<label><span style="font-size: 12px;"><fmt:message bundle='${pageScope.bundle}'  key='Select.activation.mode' />：</span></label>                               
			<input name="Fruit" type="radio" value=""  onclick="$('#handle').hide(),$('#selectCard').show();"/><span style="font-size: 12px;"><fmt:message bundle='${pageScope.bundle}'  key='the.number.of.Bound.card' /></span>
			<input name="Fruit" type="radio" value=""  onclick="$('#selectCard').hide(),$('#div').hide(),$('#priceDiv').hide(),$('#table1').hide(),$('#handle').show();" /><span style="font-size: 12px;"><fmt:message bundle='${pageScope.bundle}'  key='Manually.enter' /></span>
		</div>
		
		<div class="oh" style="display:none" id="selectCard">		
			<div class="col-7 mt20">
				<label class="fl langWidth">
					<span class="colorRed smallStar">*</span>
					<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.binding.card' />:</span>
				</label>
		      	<div class="formControls zpInput langMl">
		        	<select name="iccid" id="iccid"  class="input-text form-control " >
	 					<option >===<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.binding.card' />===</option>
					</select>
		      	</div>		      
		     </div> 
		</div>
		
		<div id="handle" style="display:none" class="oh">
		   	<div class="col-7 mt20 mt20" >
				<label class="fl langWidth">
					<span class="colorRed smallStar">*</span>
					<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.card.number' />:</span>
				</label>
		      	<div class="formControls zpInput langMl">
					<input type="text" name="iccid2" id="iccid2" class="input-text"/>
		      	</div>
		     </div>	

	     <div class="clr mtb20 langMl">
	     	<input type="button" id="button1" value="<fmt:message bundle='${pageScope.bundle}'  key='Determine' />" class="btn btn-primary radius" />
	     </div>
	    </div>
	    
	    
		<div class="mt-20">
			<table id="table1" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0" style="display:none;">
					<tr class="zpTable">
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Residual.trafffic' />:</span>  &nbsp;<span id="lable1" class="font12"></span></td>
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Current.status.card' />:</span>  &nbsp;<span id="lable2" class="font12"></span></td>
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Device.type' />:</span>  &nbsp;<span id="lable3" class="font12"></span></td>
					</tr>
					<tr class="zpTable">
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Types.of.segmentation' />:</span>  &nbsp;<span id="lable4" class="font12"></span></td>
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.channels' />:</span>  &nbsp;<span id="lable5" class="font12"></span></td>
						<td><span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Operator' />:</span> &nbsp; <span id="lable6" class="font12"></span></td>
					</tr>
				</table>
			</div>
			
			<div id="div" style="color: red;width:100%;overflow:hidden;">
<%-- 				<div class='container-fluid'>
					<div class='row'>
						<div class='col-sm-3 col-md-3 col-lg-3 plrNone' style='color: red;' onclick='priceChange("+list[i].goodsId+")'>
							<table id='table1' border='1' class='table table-border table-bordered' style="margin-top:10px;">
								<tr>
									<td class="prdList" style="text-align:center;border:none;">
										<img alt='路径错误' src="${ctx}/static/ui/images/pic.png" width="159" height="105">
										<p class="text-center font12">产品产品</p>
										<p class="text-center font12">产品价格产品</p>
										<p class="text-center font12">产品价格品价格</p>
									</td>
								</tr>
								<tr>
									<td colspan='3' class="font12" style="border-left:none;border-right:none;">备注:</td>
								</tr>
							</table>
						</div>
					</div>
				</div> --%>		
			</div>
			
			<div id="priceDiv">
			
			</div>			
		</form>
	</div>
</body>
</html>