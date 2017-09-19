<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_CN" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/reset.css" />
    <link href="${ctx}/static/glaNew/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/iconfont.css" />
    <link href="${ctx}/static/glaNew/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/common.css"/>
    <link rel="stylesheet" href="${ctx}/static/glaNew/css/dataTables.bootstrap.css" />
<title><fmt:message bundle='${pageScope.bundle}' key='Enterprise.customer.monthly.data.usage.query' /></title>
<base href="<%=basePath%>" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="static/js/dateformat.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
<style>
.goodsSelect {
	display: none;
}
</style>
<script type="text/javascript">
//点击全选
$().ready(function(){		
	$("#selectall").click(function(){
		$(".box").prop("checked",$(this).prop("checked"));	
	});
	
	$(document).on('click',".a-tag",function(){
		url='${ctx}/month/trafficDetails';
		 window.location.href=url + '?iccid=' +$(this).attr("aval");
		alert($(this).attr("aval"));
	});
	
	
});
//销卡
function pinCard(){
	var iccidList = new Array();
	if($("input[name='box']:checked").size() != 0){
		$("input[name='box']:checked").each(function(){
			var iccid = $(this).val();
			iccidList.push(iccid.toString());
		});
		alert(jQuery.type(iccidList));
		$.ajax({
			url : "${ctx}/month/piniccid",
			type : "post",
			data : {"iccidList":iccidList},
			success:function(data){
				if(data.msg){
				alert("销卡成功");
				}else{
					alert("失败");
				}
			}
		})
	}else{
		alert("请选择要销的卡")
	}
}
var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath%>month/queryList", /* 跳转url */
		"iDisplayLength" : 5, /* 展示条数 */
		"columnDefs" : [
							{ "targets": [0],
							  "data": null,
 							  "mRender": function ( data, type, full ) {
 							                	return "<input type='checkbox' value='"+data.ICCID+"' class='box' name='box' />" ;
 							                 }
 							         } ,
		                    {
		    					"targets" : [ 1 ],
		    					"data" : null,
		    					"mRender" : function(data, type, full) {
		 						   return  '<select name=\"selectId\" class=\"select2\" '+
		 						   'style=\"width: 100px;\">'+
		 						   "<option value=\"1\">更改状态</option>"+
		 						   "<option value=\"2\">库存</option>"+
		 						   "<option value=\"3\">可激活</option>"+
		 						   "<option value=\"4\">已激活</option>"+
		 						   "<option value=\"5\">已停用</option>"+
		 						   "<option value=\"6\">已失效</option>"+
		 						   "</select>"; 
		 					}
		    				},
				  {
				"targets" : [ 2 ],
				"data" : null,
				"mRender" : function(data, type, full) {
					var iccid = data.ICCID;
					//return '<a title=\"" href="javaScript:toOneQuery('+ iccid+ ');" class=\"ml-5 a-tag\" style=\"text-decoration:none\">'+iccid+'</a>';
					return '<a title=\"" aval="'+iccid+'" href="javaScript:void(0);" class=\"ml-5 a-tag\" style=\"text-decoration:none\">'+iccid+'</a>';
				}

			},
				{
					"targets" : [ 3 ],
					"data" : "MSISDN"
				},
				{
					"targets" : [ 4 ],
					"data" : "DATAADDED"
				},
				{
					"targets" : [ 5 ],
					"data" : "STATICNAMEA"
				},
				{
					"targets" : [ 6 ],
					"data" : "CUSTNAME"
				},
				{
					"targets" : [ 7 ],
					"data" : "STATICNAMEB"
				},
				{
					"targets" : [ 8 ],
					"data" : "CARDSTATUS"
				},
		],

		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : true,
		"bPaginate" : true,
		"bLengthChange" : false,
		"bFilter" : false,
		"bSort" : false,
		"bInfo" : true,
		"bAutoWidth" : true,
		"aaSorting" : [ [ 1, "asc" ] ],
		"bStateSave" : false,
		"sPaginationType" : "full_numbers",
		"oLanguage" : {
			"sLengthMenu" : "每页显示 _MENU_ 条记录",
			"sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='we.can.not.find.any.relevant.data' />",
			"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
			"sInfoEmtpy" : "显示0到0条记录",
			"sInfoFiltered" : "",
			"sProcessing" : "正在加载中...",
			"sSearch" : "搜索",
			"oPaginate" : {
				"sFirst":    " <fmt:message bundle="${pageScope.bundle}"  key="First.Page" />",
		          "sPrevious": " <fmt:message bundle="${pageScope.bundle}"  key="Previous.Page" />",
		          "sNext":     " <fmt:message bundle="${pageScope.bundle}"  key="Next.Page" /> ",
		          "sLast":     " <fmt:message bundle="${pageScope.bundle}"  key="Last.Page" />"
			}
		},
		"aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
				[ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
	//设置每页显示记录的下拉菜单
	}
	
function sreach() {
	$('#example').dataTable().fnClearTable(false);
	var oSettings = $('#example').dataTable().fnSettings();
	oSettings.aoServerParams.push({
		"fn" : function(aoData) {
			aoData.push({
				"name" : "paramNum",
				"value" : $("#paramNum").val()
			}

			);
		}
	});
	$('#example').dataTable().fnDraw();
}


$(document).ready(function(){
	$('#example').dataTable(dataTableObj);
	
});

	function queryOrExport() {
	/* 	if (!checkCustId()) {
			return false;
		};
		if (!checkUseCount()) {
			return false;
		}; */
		var expLayer=layer.msg('<fmt:message bundle='${pageScope.bundle}'  key='Downloading' />', {icon: 16,shade: [0.5, '#f5f5f5']});
		$.ajax({
			url : "${ctx}/month/selectBydynamic",
			type : "post",
			dataType : "json",
			data : {
				"paramNum" : $("#paramNum").val(),
			},
			success : function(data) {
				alert(data);
				if(data=="0"){
					//数据量较大，第一次导出，先存入数据库，提示用户等待两分钟
					layer.close(expLayer);
					layer.msg('<fmt:message bundle='${pageScope.bundle}'  key='Downloading,Please.Try.Again.2.Mins.Later' />', {icon: 1});
				}else if(data=="1"){
					layer.close(expLayer);
					layer.msg('<fmt:message bundle='${pageScope.bundle}'  key='Downloading,Please.Try.Again.2.Mins.Later' />', {icon: 1});
				}else if(data=="2"){
					layer.close(expLayer);
					exportExcel(data)
				}else if(data=="3"){
					layer.close(expLayer);
					exportExcel(data)
				}else if(data=="5"){
					layer.close(expLayer);
					layer.msg('<fmt:message bundle='${pageScope.bundle}'  key='The.Data.Amount.Is.Large..Please.Select.The.Inquiry.Condition' />', {icon: 1});
				}
			}
		});
	}
	function exportExcel(data) {
		/* if (!checkCustId()) {
			return false;
		};
		if (!checkUseCount()) {
			return false;
		}; */
		var url="<%=basePath%>month";
		if (data == "2") {
			layer
					.msg(
							'<fmt:message bundle='${pageScope.bundle}'  key='Downloading,Please.Wait.For.A.While' />',
							{
								icon : 16,
								shade : [ 0.5, '#f5f5f5' ],
								time : 5000
							});
			url = url + "/exportFileFromDB?";
		} else if (data == "3") {
			layer
					.msg(
							'<fmt:message bundle='${pageScope.bundle}'  key='Downloading,Please.Wait.For.A.While' />',
							{
								icon : 16,
								shade : [ 0.5, '#f5f5f5' ],
								time : 8000
							});
			url = url + "/exportFile?";
		}
		var url = url + "paramNum=" + $("#paramNum").val();
		window.location.href = url;
	}
</script>
</head>
<body style="color: #444; background-color: #edf0ff;" >
    <div class="wrapper wrapper-content animated fadeInRight">
      <div class="pad row white-bg borderradius">
      	<!--顶部按钮区-->
      	<div class="row">
      		<h5 class="pb10" style="border-bottom:1px solid #ababab;"><a class="J_menuItem" href="SIM卡查询.html">SIM卡查询</a></h5>
      	</div>
        <div class="row white-bg mb30 mt10"> 
        	<form class="form-inline fl" id="sim_top_left" >
        		<div class="form-group">
			    	<input type="text" class="form-control no_right_borderradius" id="paramNum" placeholder="请输入 ICCID,MSISDN,IMSI"><i class="fa box_icon no_left_borderradius icon-chazhao" onclick="sreach()"></i>
			    	<button type="button" data-toggle="modal" data-target="#exampleModal" class="btn btn_blue ml10">高级搜索</button>
			  	</div>		  
        	</form>
        			  
			 <div class=" right_btns cl fr">
				  <button data-toggle="modal" data-target=".modal_delete_card" type="button" class="btn white_btn  ml10"  onclick="pinCard()">销卡</button>
				  <button data-toggle="modal" data-target=".batch_pin_card" type="button" class="btn  white_btn ml10">批量销卡</button>
				  <button type="button" class="btn  btn_blue  ml10">导出</button>
			 </div>
        </div>
        <!--表格区域-->
        <div class="row white-bg">  
			<table id="example" class="table table-striped table-bordered table-hover display "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th>全选 <input type="checkbox" id="selectall" />
						</th>
						<th>操作</th>
						<th>iccid</th>
						<th>服务号码</th>
						<th>运营商</th>
						<th>终端类型</th>
						<th>客户名称</th>
						<th>型号</th>
						<th>卡状态</th>
					</tr>
				</thead>
			</table>
        </div>
        </div>
    </div>
   <!--模态框-->
   
   <!--高级搜索-->
   <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header blue_bg">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h5 class="modal-title" id=""> <i class="fa icon-chazhao"></i>高级搜索</h5>
	      </div>
	      <div class="modal-body">
	    		<form class="form-inline">
				  <div class="form-group">
				    <label for="username">账户名：</label>
				    <input type="text" class="form-control" id="username" placeholder="请输入账户名">
				  </div>
				  <div class="form-group">
				    <label for="deviceId">设备ID:</label>
				    <input type="email" class="form-control" id="deviceId" placeholder="请输入设备ID">
				  </div>	
				  <div class="form-group">
				    <label for="username">已激活：</label>
				    <select class="form-control">
					  <option>请选择是否激活</option>
					  <option>是</option>
					  <option>否</option>
					
					</select>
				  </div>
				  <div class="form-group">
				    <label for="deviceId">ICCID :</label>
				    <input type="text" class="form-control" id="deviceId" placeholder="请输入设备ICCID">
				  </div>		
				</form>	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn white_btn" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn_blue">确定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!--换卡-->
	
	<div class="modal small_modal fade modal_change_card" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header blue_bg">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h5 class="modal-title" id=""> <i class="fa icon-bangdingyinxingqiashoujihao"></i>换卡</h5>
	      </div>
	      <div class="modal-body">
	      	<p class="ml10">您确定要换卡吗？</p>
	      	</div>
	      <div class="modal-footer">
	        <button type="button" class="btn white_btn" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn_blue">确定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!--消卡-->
	
	<div class="modal small_modal fade modal_delete_card" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header blue_bg">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h5 class="modal-title" id=""> <i class="fa icon-lajitong"></i>销卡</h5>
	      </div>
	      <div class="modal-body">
	      	<p class="ml10">您确定要销卡吗？</p>
	      	</div>
	      <div class="modal-footer">
	        <button type="button" class="btn white_btn" data-dismiss="modal">取消</button>
	        <button type="button" class="btn btn_blue">确定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!--批量销卡-->
	
	<div class="modal fade batch_pin_card" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header blue_bg">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h5 class="modal-title" id=""> <i class="fa icon-lajitong"></i>批量销卡</h5>
	      </div>
	      <div class="modal-body">
      		<form class="form-inline">
			  <div class="form-group">
			    <label for="username">上传文件：</label>
			    <input id="upload" onclick="document.getElementById('file').click();" type="text" readonly="true" class="form-control" id="username" placeholder="请选择文件">
			    <button id="upload_btn" type="button" class="btn btn_blue">上传</button>
			    <i class="fa icon-wenjianshangchuan" onclick="document.getElementById('file').click();"></i>
			    <input id="file" type="file" style="display: none;" name="" id="" value="" />
			  </div>
			  </form>
			  <p class=" mt20 text-center">批注：销卡模板,一个sheet页,一个iccid字段</p>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>