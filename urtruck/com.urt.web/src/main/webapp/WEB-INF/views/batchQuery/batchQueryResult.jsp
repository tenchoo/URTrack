
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Corporate.customer.management' /></title>
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

	
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
	<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
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
	}
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>batchQuery/queryResultList",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
 		"columnDefs": [ 
			         {"targets": [0],"data": "batchId"} ,
			         {"targets": [1],"data": "tradeTypeCode"} ,
			         {"targets": [2],"data": null,"mRender": function ( data, type, full ) {
			        	    var dealTag=data.dealTag;
			        	    if(dealTag=="0"){
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='Unhandled' />";
			        	    }else if(dealTag=="2"){
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='processing.is.completed' />";
			        	    }else{
			        	    	dealTag="<fmt:message bundle='${pageScope.bundle}'  key='processing' />";
			        	    }
		                	return dealTag;
		        	 }} ,
			         {"targets": [3],"data": null,"mRender": function ( data, type, full ) {
		                	return new Date(data.recvTime).Format("yyyy/MM/dd HH:mm:ss");
		        	 }} ,
			         {"targets": [4],"data": "operId","mRender": function ( data, type, full ) {
			        	    var operId;
			        	    if(data!=null&&data.operId!=null){
			        	    	operId=data.operId;
			        	    }else{
			        	    	operId="admin"
			        	    }
		                	return operId;
		        	 }} ,
			         {"targets": [5],"data": "sumNum"} ,
			         {"targets": [6],"data": "succNum"} ,
			         {"targets": [7],"data": null,"mRender": function ( data, type, full ) {
			        	    if(null==data.failNum){
			        	    	data.failNum = 0
			        	    }
                            return '<a href="###" onclick="openNew('+data.batchId+')" class="text-decoration:underline;"><big>'+data.failNum+'</big></a>';
		        	 }} ,
			         {"targets": [8],"data": null,
			        	 "mRender": function ( data, type, full ) {
// 			        		    var rate=(Math.round((data.succNum+data.failNum) / data.sumNum * 10000) / 100.00 + "%")
                                var rate=Math.round((data.succNum+data.failNum) / data.sumNum*100);
			                	return '<div style=""><progress value="'+rate+'" max="100"></progress></div>'+'<div  style=""><span>'+rate+'%</span></div></div>';
    
			        	 }
			         } ,
			         {"targets": [9],"data": null,"mRender": function ( data, type, full ) {
		                	return '<input class=\"btn btn-primary radius\" type=\"reset\" value=\"&nbsp;&nbsp;<fmt:message bundle="${pageScope.bundle}"  key="Update" />&nbsp;&nbsp;\" onclick=\"update('+data.batchId+')\"/>';

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
	function openNew(batchId){
		if(batchId!=""){
			window.location.href='<%=basePath %>batchQuery/queryFailData?batchId='+batchId;
		}
	}
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
		    $('#example').dataTable().fnDraw();
		}
	};
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
				        },
				        {
				            "name" :  "tradeTypeCode",
				            "value": $("#tradeTypeCode").val()
				        }
	 	            	 );}
	    });
// 	    $('#example').dataTable().fnDraw();
	}
	function update(batchId){
		if(batchId!=""){
			$.ajax({
                type: "post",
                url: "<%=basePath %>batchQuery/updateByBatchId",
                data: 'batchId='+batchId,
                dataType: "json",
                success: function (data) {
                        if(data.data=="succ"){
                        	sreach();
                        	$('#example').dataTable().fnDraw(false);
                        	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.successfully' />");
                        }else{
                        	layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='update.failed' />");
                        }
                },
        });
		}
	}

</script>
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<form role="form" >
	         <div class="row cl">
	             <label class="font12 fl" style="width: 20%"><span class="c-red">*</span><fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.query.date' />：</label>
	             <input id="startTime" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.start.date' /> " style="width:250px" type="text" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})" readonly="readonly">
	             </span>--</span>
	             <input id="endTime" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.the.end.time' />" style="width:250px" type="text" onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})" readonly="readonly">
	        </div>
	        <br>
	        <div class="row cl">
	            <div>
	             <label for="batchId" class="font12 fl" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Batch.serial.number' />：</label>
	             <input id="batchId" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Batch.number.(optional)' />" style="width:40%" type="text">
	            </div>
	            <br>
	            <div>
	             <label for="tradeTypeCode" class="font12 fl" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Business.type' />：</label>
	             <input id="tradeTypeCode" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Business.type.(optional)' />" style="width:40%" type="text">
	            </div>
	        </div>
	      	<div class="pd-10" style="text-align:center;">
			   	<button id="search" type="button" class="btn btn-primary radius" onclick="valate();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
			   	<input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
		   	</div>
		</form>
		<div class="mt-20">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
			<thead>
				<tr class="zpTable">
					<th><fmt:message bundle='${pageScope.bundle}'  key='Batch.serial.number' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Business.type' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Processing.state' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Total.quantity' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Successful.quantity' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Failure.quantity' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Processing.schedule' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
			</thead>
		</table>
		</div>
</div>

</body>
</html>

 