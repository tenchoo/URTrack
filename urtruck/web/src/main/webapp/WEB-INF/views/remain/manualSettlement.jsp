<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Manual.settlement.of.cash.refunds' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css">
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	<script type="text/javascript"> 
	Date.prototype.Format = function(fmt)   
	{   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	}  
	
	function cashBack(){
		  var cashBackArr = [];
		  $(".checkchild").each(function(){
			  	var rows = $("#settlementGrid").rows; 
				if ($(this).is(":checked")) 
				{
					var row = $(this).parent().parent();
					var childrens = row.children();
					cashBackArr.push(childrens[1].innerHTML);
				}
			});
		  
		  $.ajax({
				url:"/remain/cashBack",
				data:{"balanceIds":cashBackArr.join(",")},
				beforeSend:function(XMLHttpRequest){ 
		              //alert('远程调用开始...'); 
		              $("#loading").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.wait.a.moment' />..."); 
		         }, 
				success:function(result){
				      $("#loading").empty();
					window.location.reload();
				}
		});
		  
	}
	function checkAllBox(){
		var state = 0;
	      $(".checkchild").each(function(){
				if ($(this).is(":checked")) 
				{
					$("#cashBackBtn").show();
					state = 1;
				}
			});
	      if(state == 0){
	    	  $("#cashBackBtn").hide();
	      }
	}
	
	$(function(){
		srh();
		$(".checkall").on('click',function () {
		      var check = $(this).prop("checked");
		      $(".checkchild").prop("checked", check);
		      checkAllBox();
		});
		
		$("#customer").select2({  
	        placeholder: "<label for='name' class='font12 fl'><fmt:message bundle='${pageScope.bundle}'  key='please.select.customer' /></label>",  
	        ajax: {  
	            url: "/remain/getCustomList",
	            type: "post",
	            dataType: 'json',  
	            delay: 500,  
	            data: function (params) {  
	                return {  
	                    q: params.term,   
	                    page: params.page  
	                };  
	            },  
	            processResults: function (data, page) {  
	                return {  
	                    results: data
	                };  
	            },  
	            cache: true  
	        },  
	        escapeMarkup: function (markup) {   
	            return markup;   
	        }, 
	        minimumInputLength: 1,  //至少输入多少个字符后才会去调用ajax  
	        minimumResultsForSearch: 1,  
	        width: "200px"  
	    });  
	})
    
	
	function srh(){
		var custId = $("#customer").val();
		var cashbackTag = $("#cashbackTag").val();
		var url = "<%=basePath %>remain/searchCom?custId="+custId+"&cashbackTag="+cashbackTag;
		var laoBillColumn = [{"targets": [0],
// 					            "sClass": "text-center",
					            "data": "ID",
					            "render": function (data, type, full, meta) {
					            	if(full.cashbackTag == "0"){
					                return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
					            	}else{
					            	return "";
					            	}
					            },
// 					            "bSortable": false
					         },
		                     {"targets": [1],"data": "balanceId"} , 
			                 {"targets": [2],"data": "channelCustId"} ,
			                 {"targets": [3],"data": "backFee",
			                	 "render": function (data, type, full, meta) {
					            	if(full.backFee != null){
					                return (full.backFee/100).toFixed(2);
					            	}else{
					            	return "";
					            	}
					            }} ,
			                 {"targets": [4],"data": "balanceTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.balanceTime != null){
						            		var balanceTime = new Date(full.balanceTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return balanceTime;
						            	}else{
						            	return "";
						            	}
						            }},
			                 {"targets": [5],"data": null,"mRender": function(data,type,full){
									var status = full.cashbackTag;
		  							if(status=="1"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Already.offered.cash.refunds' />";
		  							}else if(status=="0"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Haven.t.offered.cash.refunds' />";
		  							}
			  						}	
				                 } ,
			                 {"targets": [6],"data": "cashTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.cashTime != null){
						            		var cashTime = new Date(full.cashTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return cashTime;
						            	}else{
						            	return "";
						            	}
						            }},
			                 {"targets": [7],"data": "cashChargeId"},
			                 {"targets": [8],"data": "ruleId"}
			                ]
							listGrid(url,laoBillColumn);
	}
	
	function clears(){
		$("#cashbackTag").val("-1");
		$('#customer').empty();
		window.location.reload();
	}
	
	function listGrid(url,columns){
		$("#settlementGrid").dataTable().fnDestroy();
		var dataTableObj = {
				"ordering" : false,
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"sAjaxSource" : "<%=basePath%>role/list", /* 跳转url */
				"iDisplayLength" : 10, /* 展示条数 */
		 		"columnDefs": columns,
		 		"sAjaxSource" : url,
	  			 "drawCallback": function( settings ) {
	  				 $("#cb").removeClass('sorting_asc');
	  				$("#cashBackBtn").hide();
	  				$(".checkchild").on('click',function(){
						checkAllBox();
					});
	  		     },
	  		    "aaSorting": [[ 5, "asc" ]],
				"sScrollX" : "100%",
	  			"sScrollXInner" : "100%",
	  			"bScrollCollapse" : true,
	  			"bPaginate" : true,
	  			"bLengthChange" : false,
	  			"bFilter" : false,
	  			"bSort" : false,
	  			"bInfo" : true,
	  			"bAutoWidth" : true,
	  			"bStateSave" : false,
	  			"sPaginationType" : "full_numbers",
	  			"oLanguage" : {
	  				"sLengthMenu" : "每页显示 _MENU_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
	  	            "sZeroRecords" : "<fmt:message bundle='${pageScope.bundle}'  key='sorry,no.records' />",
	  	            "sInfo" : "<fmt:message bundle='${pageScope.bundle}'  key='Current.view' /> _START_ <fmt:message bundle='${pageScope.bundle}'  key='To' />"+
	  	            			"_END_ <fmt:message bundle='${pageScope.bundle}'  key='Article' />,<fmt:message bundle='${pageScope.bundle}'  key='Total' />"+
	  	            			" _TOTAL_ <fmt:message bundle='${pageScope.bundle}'  key='Records' />",
	  	            "sInfoEmtpy" : "显示0到0条记录",
	  	            "sInfoFiltered" : "",
	  	            "sProcessing" : "<fmt:message bundle='${pageScope.bundle}'  key='Loading' />...",
	  	            "sSearch" : "<fmt:message bundle='${pageScope.bundle}'  key='Search' />",
	  	            "oPaginate" : {
	  	                "sFirst" : "<fmt:message bundle='${pageScope.bundle}'  key='The.first.page' />",
	  	                "sPrevious" : " <fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /> ",
	  	                "sNext" : " <fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /> ",
	  	                "sLast" : " <fmt:message bundle='${pageScope.bundle}'  key='The.last.page' /> "
	  	            }
	  			},
			    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
			}
		$("#settlementGrid").dataTable(dataTableObj);
	}
	 </script>
<body>
<div class="pd-20">
<form role="form">
			<div class="oh">
			<div class="col-12">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</label>
					<div class="tBox">
						<select id="customer" class="input-text" style="width:200px"></select>
					</div>
				</div>
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Status.of.cash.refunds' />:</label>
					<div class="tBox">
						<select class="input-text"  id="cashbackTag" style="width:200px"><option value ="-1"><fmt:message bundle='${pageScope.bundle}'  key='Nothing' /></option><option value ="0"><fmt:message bundle='${pageScope.bundle}'  key='Unhandled' /></option><option value ="1"><fmt:message bundle='${pageScope.bundle}'  key='Already.processed' /></option></select>
					</div>
				</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="cashBackBtn" type="button" class="btn btn-primary radius" 
					onclick="cashBack();" style="display:none"><fmt:message bundle='${pageScope.bundle}'  key='cash.refunds' />
				</button>
				<button id="search" type="button" class="btn btn-primary radius" 
					onclick="srh();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' />
				</button>
				<input class="btn btn-default radius" onclick="clears()" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
</form>
<div class="mt-20">
		<table id="settlementGrid"  class="table table-border table-bordered table-hover table-bg " cellpadding="0">
			<thead>
				<tr class="zpTable">
				 	<th id="cb">
                	<input type="checkbox" id="boss"  class="checkall" />
            		</th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Settlement.statements' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Channel.customer' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Amount.of.cash.refunds(RMB)' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Settlement.time' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='mark.of.cash.refunds' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Date.of.cash.refunds' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Statements.of.cash.refunds' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Rule' /> ID</th>
				</tr>
			</thead>
		</table>
</div>
</div>
</body>
</html>