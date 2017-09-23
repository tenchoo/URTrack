<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Batch.addresses' /></title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<style type="text/css">
		body, html,#l-map{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
</style>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>

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
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>

<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}

	$(document).ready(function(){
		init()
		$('#example').dataTable(dataTableObj);
	});
	function init(){
		var defaultStart=new Date();
		var defaultDate=defaultStart.Format("yyyy/MM/dd");
		$("#startTime").val(defaultDate);
		$("#endTime").val(defaultDate);
	};

	function valate(){
        var startTime=$("#startTime").val();
        var endTime=$("#endTime").val();
        var middleTime=new Date(endTime).getTime()-new Date(startTime).getTime();
        var middleDay=middleTime/(24*3600*1000);
        if(startTime==""&&endTime==""){
        	layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Start.time.and.end.time.cannot.be.empty' />");
        }else if(startTime==""){
			layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='start.time.can.t.be.empty' />");
		}else if(endTime==""){
			layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='end.time.can.t.be.empty' />");
		}else if(middleTime<0){
			layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='The.final.time.can.t.beyond.the.beginning.time' />");
		}else{
		    sreach();
		}
	};
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
		"ordering": true, 
		"order": [[ 0, "desc" ]] ,
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>lbs/queryResultList",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
			"columnDefs": [ 
			         {"targets": [0],"data": "batchId"} ,
			         {"targets": [1],"data": null,
			        	 "mRender": function ( data, type, full ) {
					        var rate=Math.round((data.succNum+data.failNum) / data.sumNum*100);
					        if(rate==0){
			        	    	dealTag="未处理<fmt:message bundle='${pageScope.bundle}'  key='' />";
			        	    }else if(rate==100){
			        	    	dealTag="处理完成<fmt:message bundle='${pageScope.bundle}'  key='' />";
			        	    }else{
			        	    	dealTag="处理中<fmt:message bundle='${pageScope.bundle}'  key='' />";
			        	    }
		                	return dealTag;
		        	 }} ,
			         {"targets": [2],"data": null,"mRender": function ( data, type, full ) {
		                	return new Date(data.recvTime).Format("yyyy/MM/dd HH:mm:ss");
		        	 }} ,
			         {"targets": [3],"data": "sumNum"} ,
			       
			         {"targets": [4],"data": null,
			        	 "mRender": function ( data, type, full ) {
	//			        		    var rate=(Math.round((data.succNum+data.failNum) / data.sumNum * 10000) / 100.00 + "%")
	                            var rate=Math.round((data.succNum+data.failNum) / data.sumNum*100);
			                	return '<div style=""><progress value="'+rate+'" max="100"></progress></div>'+'<div  style=""><span>'+rate+'%</span></div></div>';
	
			        	 }
			         } ,
			         {"targets": [5],"data": null,
			        	 "mRender": function ( data, type, full ) {
			        	var rate=Math.round((data.succNum+data.failNum) / data.sumNum*100);
		                return '<input class=\"btn btn-primary radius\" type=\"reset\" value=\"更新进度<fmt:message bundle='${pageScope.bundle}'  key='' />&nbsp;\" onclick=\"update('+data.batchId+')\"/>'+'&nbsp;&nbsp;'+'<input class=\"btn btn-primary radius\" type=\"reset\" value=\"查看定位图<fmt:message bundle='${pageScope.bundle}'  key='' />&nbsp;\" onclick=\"showBatch('+data.batchId+','+rate+')\"/>'+'&nbsp;&nbsp;'+'<input class=\"btn btn-primary radius\" type=\"reset\" value=\"导出明细<fmt:message bundle='${pageScope.bundle}'  key='' />&nbsp;\" onclick=\"exportBatch('+data.batchId+','+rate+')\"/>';
	
		        	 }} ,
		           ],
			           
		  "sScrollX": "100%",
		  "sScrollXInner": "100%",
		  "bScrollCollapse": true,
		  "bPaginate": true,
		  "bLengthChange": false,
		  "bFilter": false,
		  "bSort": false,
		  "bInfo": true,
		  "bAutoWidth": true,
		  "aaSorting": [[1, "asc"]],
		  "bStateSave": false, 
		  "sPaginationType": "full_numbers",
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

	function sreach(){
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            	 	{
				            "name" :  "startTime",
				            "value": $("#startTime").val()
				        },
				        {
					        "name" :  "endTime",
					        "value": $("#endTime").val()
					    },
				        {
				            "name" :  "batchId",
				            "value": $("#batchId").val()
				        }
	 	            	 );}
	    });
		$('#example').dataTable().fnDraw();
	}
	function update(batchId){
		if(batchId!=""){
			$.ajax({
	            type: "post",
	            url: "<%=basePath %>lbs/updateByBatchId",
	            data: 'batchId='+batchId,
	            dataType: "json",
	            success: function (data) {
	                    if(data.data=="succ"){
	                    	sreach();
	                    	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.successfully' />");
	                    }else{
	                    	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.failed' />");
	                    }
	            },
	    });
		}
	}
	
	function queryOne(){
		$.ajax({
			url:"${ctx}/lbs/queryOne",
			data:{"iccid":$("#iccid").val()},
			success:function(result){
				showOneMap(result)
			}
		});
	}
	function exportBatch(batchId,rate){
		if(rate==100){
			$('#batchId').val(batchId);
			$('#cform').submit();
		} else {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='The.task.is.not.finished.and.cannot.be.exported' />");
		}
	}
	function showOneMap(addrStr){
		layer.open({
			  type: 2,
			  area: ['900px', '600px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: '/lbs/showOne?addrStr='+addrStr
		});
	};

	function showBatch(batchId,rate){
		if(rate==100){
			showBatchMap(batchId);
		} else {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='The.task.is.not.finished.and.cannot.be.located' />");
		}
	}
	function showBatchMap(batchId){
		layer.open({
			  type: 2,
			  area: ['900px', '600px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: '/lbs/showBatch?batchId='+batchId
		});
	};

	function upload(){
		var option = {
			url : "<%=basePath%>/lbs/batchImport",
			type : "post",
			success : function(data) {
				if(data.msg != null && data.msg != ""){
					alert(data.msg);
					sreach();
				}
			}
		};
		if(validateFile()){
			if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
				$("#sform").ajaxSubmit(option);
			}
		}
	}
	//验证上传文件
	function validateFile(){
		var obj = document.getElementById('inputfile'); 
		if (obj.value=='') { 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />！");
			return false; 
		} 
		var stuff = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; 
		if (stuff!='xls' && stuff!='xlsx' ) { 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
			return false; 
		} 
		return true;
	}
	function templet(){
		window.location.href = "${ctx}/download/queryLocation.xlsx";
	}

</script>
</head>
<body>
<div class="pd-20" align="center">
	<div class="seconSec ">
		<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='Single.card.location.query' /></h1>
	</div>
	<div class="cl pd-5 mt-10">
 		<label class="font12" style="float:left;width: 5%;">ICCID：</label>
 		<input type="text" id="iccid"  style="float:left;width: 20%;height: 32px;">
 		<label class="font12" style="float:left;width: 1%;"></label>
 		<button id="search"  style="float:left;" type="button" class="btn btn-primary radius" 
			onclick="queryOne();">
			<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Single.card.location' />
		</button>
		<br>
			<form id="cform" action="${ctx}/lbs/doExport" method="post" hidden>
				 <input id="batchId" name="batchId" hidden>
			</form>
		<br>
  	</div>
	<br>
	<div class="seconSec ">
		<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='Batch.location.query' /></h1>
	</div>
	<div>
		<form role="form" action="/ "
			method="post" enctype="multipart/form-data" id="sform">
			<div class="cl pd-5 mt-10">
				<label class="font12" style="float:left"><fmt:message bundle='${pageScope.bundle}'  key='Import.files' />：</label>
				<input type="file" class="font12"  style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
 				<label class="font12" style="float:left;width: 1%;"></label>
				<button id="search" type="button" class="btn btn-primary radius" style="float:left"
					onclick="upload();">
					<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='Import.file' />
				</button>
			</div>
		</form>
	</div>
	<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
			onclick="templet();">
		<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Download.templates' />
	</button>
	<br>
	<br>
	<div class="mt-20">
		<div class="row cl">
		    <div style="width:20%;float:left;">
	        	<label class="font12 fl" style="width:100%;"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.query.time' />：</label>
	        </div>
	        <div style="width:60%;float:left;">
	       		<input id="startTime" class="input-text" style="width: 30%;" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.start.date' /> "  type="text" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})" readonly="readonly">
	        	</span>--</span>
	        	<input id="endTime" class="input-text" style="width: 30%;" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.the.end.time' />"  type="text" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})" readonly="readonly">
		    </div>
		    <div style="width:18%;float:left;">
	        	<button id="search" type="button" style="width: 50%;float:left;" class="btn btn-primary radius" onclick="valate();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
	        </div>
		</div>
	    <br>
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Batch.number' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Processing.state' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='The.quantity.of.cards' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Processing.schedule' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
				</tr>
			</thead>
		</table>	
	</div>
</div>
</body>
</html>