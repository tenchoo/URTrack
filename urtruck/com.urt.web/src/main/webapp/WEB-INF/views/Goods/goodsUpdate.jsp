<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Product.change.page' /></title>

<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<!-- css -->
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript"
	src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
</head>
<body>
	<div class="seconSec">
		<h1><fmt:message bundle='${pageScope.bundle}'  key='Product.information' /></h1>
		<form method="post" class="form form-horizontal" id="form1"
			name="form-member-add" enctype="multipart/form-data">



			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.name' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" value="${goods.goodsName }"
						name="goodsName" id="goodsName"> <input type="hidden"
						class="input-text" value="${goods.goodsId }" name="goodsId">
				</div>
			</div>
			<div class="col-12 mt20 zpHeight">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Upload.pictures.(please.upload.pictures.again)' /></span>
				</label>
				<div class="formControls langMl zpInput">
					<div style="float: left;">
						<img width="60" height="60" id="allImgUrl"
							src="${goods.goodsPic }" />
					</div>
					<input type="hidden" name="goodsPic" id="path"
						src="${goods.goodsPic }" />
					<div style="float: left; margin-left: 20px; margin-top: 15px;">
						<input type="file" onchange="uploadPic()" name="pic" id="pic" />
					</div>
				</div>
			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message 
                    bundle='${pageScope.bundle}'  key='Product.provider' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<select name="operatorsId" id="operators" class="input-text"></select>
				</div>
			</div>
			</div>
			<div class="col-12 mt20 zpHeight">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Price.(yuan)' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="goodsPrices"
						value="${goods.goodsPrices }" id="goodsPrices" />
				</div>
			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.type' />:</span>
				</label> <input type="hidden" id="goodsTypeId" value="${goods.goodsType}">
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="goodsType"
						id="goodsType" />
				</div>
			</div>
			<div class="col-12 mt20">
				<label class="fl langWidth"> <span
					class="colorRed smallStar">*</span> <span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='Product.effective.mode' />:</span>
				</label> <input type="hidden" id="activeWayId" value="${goods.activeWay}">
				<div class="formControls langMl zpInput">
					<input type="text" class="input-text" name="activeWay"
						id="activeWay" />
				</div>
			</div>
			<div class="col-12">
				<!-- 	      <div class="col-9 col-offset-3">
	        <input class="btn btn-primary radius" type="submit"  value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
	        <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="javascript:history.go(-1);">
	      </div> -->

				<div class="zpButton">
					<input class="btn btn-primary radius" type="button"
						onclick="update();" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;">
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	var innerValue =null;
	$('#goodsPrices').on('input',function(){
		innerValue = $(this).val();
	}).on('blur',function(){
		$(this).val(formatCurrency(innerValue));
	});
	//将页面上输入的金额保留小数点两位
	function formatCurrency(num) {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    cents = num%100;
	    num = Math.floor(num/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num + '.' + cents);
	}
//上传图片
function uploadPic(){
	//定义参数
	var options = {
		url : "<%=request.getContextPath()%>/goods/uploadPic",
		dataType : "json",
		type :  "post",
		success : function(data){
			//回调 二个路径  
			//url
			//path
			$("#allImgUrl").attr("src",data.url);
			$("#path").val(data.url);			
		}
	};	
	//jquery.form使用方式
	$("#form1").ajaxSubmit(options);
	
}

	$(function(){
		$.post("<%=request.getContextPath()%>/operators/findOperators",
			function(data) {
				var operatorsList = eval(data);
				for (var i = 0; i < operatorsList.length; i++) {
					if ('${goods.operatorsId}' == operatorsList[i].operatorsId) {
						$("#operators")
								.append(
										"<option value='"+operatorsList[i].operatorsId+"'selected='selected'>"
												+ operatorsList[i].operatorsName
												+ "</option>");
					} else {
						$("#operators")
								.append(
										"<option value='"+operatorsList[i].operatorsId+"'>"
												+ operatorsList[i].operatorsName
												+ "</option>");
		
					}
				}
			}, "json");
			var goodsTypes = [ {
				text : "预付单卡",
				id : "0"
			}, {
				text : "预付共享灵活",
				id : "1"
			}, {
				text : "预付共享固定",
				id : "2"
			}, {
				text : "月付单卡",
				id : "3"
			}, {
				text : "月付共享灵活",
				id : "4"
			} , {
				text : "月付共享固定",
				id : "5"
			} , {
				text : "单卡叠加包",
				id : "6"
			} , {
				text : "流量池叠加包",
				id : "7"
			} ];
			var goodsTypeSelect = $("#goodsType").select2({
				data : goodsTypes

			});
			goodsTypeSelect.val($("#goodsTypeId").val()).trigger("change");
			var activeWays = [ {
				text : "<fmt:message bundle='${pageScope.bundle}'  key='Effective.immediately' />",
				id : "0"
			}, {
				text : "次月生效",
				id : "1"
			},
			{
				text : "到期生效",
				id : "2"
			},
			];
			var activeWaySelect = $("#activeWay").select2({
				data : activeWays
			});
			activeWaySelect.val($("#activeWayId").val()).trigger("change");
		});

		function check() {
			debugger;
			var goodsNameValue = window.document.getElementById("goodsName").value;
			var goodsTypeValue = window.document.getElementById("goodsType").value;
			var activeWayValue = window.document.getElementById("activeWay").value;
			var picValue = window.document.getElementById("pic").value;
			var operatorsValue = window.document.getElementById("operators").value;
			var goodsPricesValue = window.document
					.getElementById("goodsPrices").value;
			if (goodsNameValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.name.of.the.commodity.is.not.empty' />!");
				return false;
			}

			if (picValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.upload.pictures' />!");
				return false;
			}
			if (operatorsValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.supplier' />");
				return false;
			}
			if (goodsTypeValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.type.of.commodity.cannot.be.empty' />!");
				return false;
			}
			if (activeWayValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='The.goods.effect.manner.cannot.be.null' />!");
				return false;
			}
			if (goodsPricesValue == "") {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.price' />");
				return false;
			}

			return true;
		}

		function update() {
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			var flag = check();
			if (flag == false) {
				return;
			}

			$.ajax({
				type : "POST",
				url : "${ctx}/goods/updateGoods",
				data : $("form").serialize(),
				dataType : "json",
				cache : false,
				success : function(data) {
					window.parent.location.reload();
					parent.layer.close(index);
				},
				error : function(error) {
				}
			});
		}
	</script>
</body>
</html>







