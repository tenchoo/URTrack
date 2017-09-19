<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='send.text.message' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common. css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />


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
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript">
function upload(){
	window.location.href = "${ctx}/download/sendSMSCMCC.xlsx";
}
function sigeSend(){
	var ble=iccidChange();
	var lang=$("#lang").val();
	if (!ble) {
		return ;
	}
   var smsContent=$('#smsCon').val();
   var iccid=$('#iccid').val();
	if (""==smsContent) {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Content.Cannot.Be.Empty' />")
			return false;
	}
	if(lang=='EngLish'&/.*[\u4e00-\u9fa5]+.*$/.test(smsContent)) { 
	/* alert("<fmt:message bundle='${pageScope.bundle}'  key='Cannot.Contain.Chinese.Characters' />！"); */ 
	alert("Cannot.Contain.Chinese.Characters");
	return false; 
	}  
	$.ajax({
		type : "post",
		url:"${ctx}/cmpp/sigeSendSms",
		data:{"iccid":iccid,"content":smsContent,"lang":lang},
		success:function(result){
			//alert(result)
			if ("1"==result) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Send.Successfully' />!");
			}else{
				alert("<fmt:message bundle='${pageScope.bundle}'  key='Send.In.Failure' />!");
			}
		}
	});
}
function iccidChange(){
	 var iccid = $('#iccid').val();
	 if (iccid=="") {
		alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Iccid' />")
		return false;
	 }
	 var chencked=false;
	 $.ajax({
			type : "post",
			url:"${ctx}/cmpp/checkedICCID",
			data: {"iccid":iccid}, 
			async: false,
			success:function(result){
				debugger;
				if ("1"==result) {
				   $("#span").hide()
				   chencked=true;
				}else{
				   $("#span").html(result)
				   $("#span").show()
				} 
			}
		});
	return chencked;
}
function iccidQuery(){
	 var iccid = $('#iccidQuery').val();
	 var chencked=false;
	 $.ajax({
			type : "post",
			url:"${ctx}/cmpp/checkedICCID",
			data: {"iccid":iccid}, 
			async: false,
			success:function(result){
				
				if ("1"==result || "iccid非法"==result) {
				   chencked=true;
				}else{
				   alert(result)
				}
			}
		});
	return chencked;
}
function UrlDecode(str){      
	  var ret="";      
	  for(var i=0;i<str.length;i++){      
	   var chr = str.charAt(i);      
	    if(chr == "+"){      
	      ret+=" ";      
	    }else if(chr=="%"){      
	     var asc = str.substring(i+1,i+3);      
	     if(parseInt("0x"+asc)>0x7f){      
	      ret+=asc2str(parseInt("0x"+asc+str.substring(i+4,i+6)));      
	      i+=5;      
	     }else{      
	      ret+=asc2str(parseInt("0x"+asc));      
	      i+=2;      
	     }      
	    }else{      
	      ret+= chr;      
	    }      
	  } 
	  alert(ret)
	  return ret;      
	}      
function sndMsg(){
	var lang =$("#langs").val();
	var option = {
		url : "${ctx}/cmpp/sendMsg?lang="+lang,
		type : "post",
		success : function(data) {
			if(data.indexOf("success")>-1){
			//alert("<fmt:message bundle='${pageScope.bundle}'  key='Send.Successfully' />!");	
			}else if(data.indexOf("null")>-1){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='File.contents.cannot.be.empty' />");	
			}else if(data.indexOf("error")>-1){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='The.file.format.is.incorrect' />");	
			}else{
				alert(data);
			}
			window.location.reload(true);
			
		},
		error : function() {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Send.In.Failure' />");
		}
	};
	$("#sform").ajaxSubmit(option);
	
}

	function srh(){
		var ble=iccidQuery();
		if (!ble) {
			return ;
		}
		var iccid = $('#iccidQuery').val();
		var start = $('#start').val();
		var end = $('#end').val();
		var sms = $("input[name='rd']:checked").val();
		var condition = $("[name='condition']").val();
		
		if((start == "" && end != "") || (start != "" && end == "")){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.fill.in.the.time' />");
			return;
		}
		var url = "<%=basePath%>cmpp/queryList?start="+start+"&end="+end+"&send="+sms+"&iccid="+iccid+"&condition="+condition;
		var cmppColumn = [{"targets": [0], "data": "smsId"},
		                     {"targets": [1],"data": "channelCustId"} , 
			                 {"targets": [2],"data": "iccid"} ,
			                 {"targets": [3],"data": "msisdn"},			                	
			                 {"targets": [4],"data": "smsContext" },			                	
			                 {"targets": [5],"data": "sendTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.sendTime != null){
						            		var sendTime = new Date(full.sendTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return sendTime;
						            	}else{
						            	return "";
						            	}
						            }},	
			                /*  {"targets": [6],"data": "operId"},	 */
			                 {"targets": [6],"data": null,"mRender": function(data,type,full){
									var status = full.dealTag;
		  							if(status=="2"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Send.Successfully' />";
		  							}else if(status=="3") {
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Send.In.Failure' />";
		  							}else{
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Please.Deal.With.It' />";
		  							}
		  						}	
			                 } ,	
			                 {"targets": [7],"data": "resultInfo"}
			                /*  {"targets": [9],"data": "updateTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.updateTime != null){
						            		var updateTime = new Date(full.updateTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return updateTime;
						            	}else{
						            	return "";
						            	}
						            }} */
			                ]
							listGrid(url,cmppColumn);
	}
	
	function listGrid(url,columns){
		$("#cmppGrid").dataTable().fnDestroy();
		var dataTableObj = {
				"ordering" : false,
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"iDisplayLength" : 10, /* 展示条数 */
		 		"columnDefs": columns,
		 		"sAjaxSource" : url,
// 	  			 "drawCallback": function( settings ) {
// 	  		     },
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
		$("#cmppGrid").dataTable(dataTableObj);
	}
	 
	
	function send()  
	{  
		var fileVal = $('#inputfile').val();
		if(fileVal == ""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.Deal.With.It' />");
			return;
		}
		sndMsg();
// 	       //定义表单要提交的URL
<%-- 	       var url="<%=request.getContextPath() %>/cmpp/sendMsg";   --%>
// 	       //将表单的action的URL更改为我们希望提交的URL
// 	       document.form1.action=url;
// 	       //利用Javascript提交表单  
// 	       document.form1.submit();
	       
// 	       window.location.reload();
// 	       //列表展示
// 	       $.ajax({
// 		        type:"POST",
<%-- 		        url:"<%=request.getContextPath()%>/cmpp/sendMsg", --%>
// 		   		dataType : "json",
// 		   		data:$('#form1').serialize(),
// 		   		success : function(data) {
// 		   		  	alert(data);
// 		   		},
// 		   		error : function(error) {
// 		   		}
// 	   		});
	 }
	//限制上传文件的类型和大小
	  function validate_file(){
	      var file = $("#inputfile");       
	      if(!/.(xlsx)$/.test(file.val())){    
	        alert("文件类型必须是xlsx");
	        file.after(file.clone().val(""));   
	        file.remove();
	        return false;
	       }
	  }
	 
		  
	$(document).ready(function(){
		var smsId = $('#smsId').val();
		 
		  if ("1"==smsId) {
			  $("#sigeSms").show();
			  $("#batchSms").hide();
		  }
		  if ("2"==smsId) {
			  $("#batchSms").show();
			  $("#sigeSms").hide();
		  }
		  
		  $('#smsId').change(function(){
				 var smsId = $('#smsId').val();
				 
				  if ("1"==smsId) {
					  $("#sigeSms").show();
					  $("#batchSms").hide();
				  }
				  if ("2"==smsId) {
					  $("#batchSms").show();
					  $("#sigeSms").hide();
				  }
		 });
		// UrlDecode("%3Coihojoojo%3E");
		});
	$(function(){
		$("#send").click(function(){
				$("[name='condition']").show();			
				$("#condition").show();			
		});
		$("#accpent").click(function(){
				$("[name='condition']").hide();			
				$("#condition").hide();			
		});
	});
	
</script>
</head>
<body>
	<div class="pd-20">
		<div class="seconSec">
			<div class="col-12 mt20">
				<label> <span
					class="" style="color: red;"><fmt:message bundle='${pageScope.bundle}'  key='Sending.Way' />:</span>
				</label>
				<div class="">
					<select id="smsId" name="smsId" class="select2"
						style="width: 200px;">
						<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Single.Card.Sending' /></option>
						<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='Sending.In.Quantities' /></option>
					</select>
				</div>
			</div>

			<form method="post" enctype="multipart/form-data" name="sform"
				id="sform">
				<div id="sigeSms">
					<label for="name">ICCID:</label>
					<div id="ceshi">
						<input type="text" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Iccid' />" id="iccid"
							name="iccid" style="width: 200px; height: 30px"
							onchange="iccidChange()" /> <span id="span" style="color: red;"></span>
					</div>
					
					<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Text.Message.Language' />:</label>
					<div>
						<select id="lang">
							<option value="EngLish">English</option>
							<option value="Chinese">简体中文</option>
						</select>
					</div>
					<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Sms.Content' />:</label>
					<div id="smsContent">
						<textarea value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Sms.Content' />" rows="4" cols="50"
							name="smsCon" id="smsCon"></textarea>
						<%-- <span style="color: red;"><fmt:message bundle='${pageScope.bundle}'  key='Chinese.Not.Supported' /></span> --%>
					</div>
					<input type="button" onclick="sigeSend()"
						class='btn btn-primary radius' value="<fmt:message bundle='${pageScope.bundle}'  key='Send' />" />

				</div>
				<!--------------批量  -->
				<div class="col-12 mt20" id="batchSms">
					<label for="name"><fmt:message bundle='${pageScope.bundle}'  key='Text.Message.Language' />:</label>
					<div>
						<select id="langs">
							<option value="EngLish">English</option>
							<option value="Chinese">简体中文</option>
						</select>
					</div>
					<label class=""  for="mobileId">
						 <span ><fmt:message bundle='${pageScope.bundle}'  key='Import.Number' />:</span>
					</label>
					<div class="">
						<input type="file" 
							style="width: 250px; padding-bottom: 35px;" id="inputfile"
							name="file" onchange="validate_file()">
					</div>
						<input type="button" onclick="send()"
							class='btn btn-primary radius' value="<fmt:message bundle='${pageScope.bundle}'  key='Send' />" />
					
					<div class="cl pd-5  bk-gray mt-20">
						<button id="search" type="button" class="btn btn-primary radius"
							style="float: left; margin-left: 10px;" onclick="upload();">
							<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Sms.Template.Download' />
						</button>
					</div>
				</div>

			</form>
		</div>


		<div class="mt20 clr" style="text-align: center;">
			<!--   <div class="tBox" style="text-align: left;">
		    iccid查询<input type="text" class="input-text" value="" style="width: 230px"
							placeholder="请输入iccid" id="iccidQuery" name="iccidQuery"
							onchange="iccidQuery()"/>
			</div>
 -->
			<fmt:message bundle='${pageScope.bundle}'  key='Send.Sms.To.Inquiry' />
			<input id="send" type="radio" name="rd" value="1" checked="checked" /> 
			<fmt:message bundle='${pageScope.bundle}'  key='Receiver.Sms.Inquiry' /> 
			<input id="accpent" type="radio" name="rd" value="0" />
			<span id="condition"><fmt:message bundle='${pageScope.bundle}'  key='Sms.Condition.Inquiry' /></span> 
			<select name="condition">
				<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='Sms.success' /></option>
				<option value="失败"><fmt:message bundle='${pageScope.bundle}'  key='Sms.failure' /></option>
				<option value=""><fmt:message bundle='${pageScope.bundle}'  key='Sms.all' /></option>
			</select>
			ICCID<fmt:message bundle='${pageScope.bundle}'  key='Inquiry' />
			
			<input type="text" class="input-text" value="" style="width: 160px; height: 30px" 
			placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Iccid' />"
				id="iccidQuery" name="iccidQuery" onchange="iccidQuery()" /> 
				
				<fmt:message bundle='${pageScope.bundle}'  key='Starting.Time' />: 
				<input id="start" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.start.time' />"
				style="width: 80px" type="text" name="start"
				onClick="WdatePicker({onpicked:function(dq){},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})"
				readonly="readonly" /> 
				<fmt:message bundle='${pageScope.bundle}'  key='Ending.Time' />: 
				<input id="end" class="input-text"
				placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.end.time' />" style="width: 80px" type="text" name="end"
				onClick="WdatePicker({onpicked:function(dq){},dateFmt:'yyyy/MM/dd',minDate:'1970-01',maxDate:'#now'})"
				readonly="readonly" />
			<button id="search" type="button" class="btn btn-primary radius" onclick="srh();">
				<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Inquiry' />
			</button>
		</div>

		<div class="mt-20">
			<table id="cmppGrid"
				class="table table-border table-bordered table-hover table-bg "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='Sms.Id' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Channel.Id' /></th>
						<th>iccid</th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Phone.Number' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Sms.Content' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Sending.Time' /></th>
						<!-- <th>操作人员ID</th> -->
						<th><fmt:message bundle='${pageScope.bundle}'  key='Sms.Condition' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Error.Message' /></th>
						<!-- <th>更新时间</th> -->
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>
