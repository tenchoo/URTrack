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
	<title><fmt:message bundle='${pageScope.bundle}'  key='Upload.card.information' /></title>
	<base href="<%=basePath%>" />
	<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
	
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
	
	
	<!-- css -->
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
	<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
	<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	<script type="text/javascript">
	$.ajax({
		//url:"${ctx}/cust/getAgentList",
		url:"traffic/getChannelCust",
		data:{},
		success:function(result){
			var select=$("#custId").select2({
				width : 200,  
				placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Customer" />',
				tags: "true",
				allowClear: true,
				data:result
			});
			$("#custId").change(function() {
				$('#type').empty();
				$('#version').empty();
				$('#initproduct').empty();
				$.ajax({
					type : "post",
					url : "${ctx}/ss/getAttrs",
					data: {"custId":$("#custId").val()},
					success:function(result){
						console.log(result);
						$("#attribute1").val(result.attrV1);
						$("#attribute2").val(result.attrV2);
				/* 		$("#lable1").text(result.attr1+":");
						$("#lable2").text(result.attr2+":"); */
					}
				});
				$.ajax({
					url:"${ctx}/ss/getTypeList",
					data:{"custId":$("#custId").val()},
					success:function(result){
						var select=$("#type").select2({
							width : 200,  
							placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Type" />',
							tags: "true",
							allowClear: true,
							data:result
						});
						$("#type").change(function() {
							$('#version').empty();
							var pid="";
							$.ajax({
								url:"${ctx}/ss/getPidByValue",
								data:{"id":$("#type").val(),"custId":$("#custId").val()},
								async:false,
								success:function(result){
									pid=result;
								}
							});
							$.ajax({
								url:"${ctx}/ss/getVersionList",
								data:{"pid":pid,"custId":$("#custId").val()},
								success:function(result){
									var select=$("#version").select2({
										width : 200,  
										placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
										tags: "true",
										allowClear: true,
										data:result
									});
								}
							});
						});
					}
				});
			});
		}
	});
	$(function(){
		var table = $('#example').dataTable(dataTableObj);
	    $("#operators").change(function() {
			$('#cardstatus').empty();
			$.ajax({
				url:"<%=basePath%>userService/getCardstatusList",
				data:{"operators":$("#operators").val()},
				success:function(result){
					var select=$("#cardstatus").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Card.type" />',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
		});
	});
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": false,
		"sAjaxSource" : "<%=basePath %>userService/getIccidInfo",    /* 跳转url */
		"iDisplayLength": 5,  /* 展示条数 */
 					 "columnDefs": [ 
			         {"targets": [0],"data": "iccid"} ,
			         {"targets": [1],"data": "custid"} ,
			         {"targets": [2],"data": "devicetype"} ,
			         {"targets": [3],"data": "privatekey"} ,
			         {"targets": [4],"data": "cardtype"} ,
			         {"targets": [5],"data": "initproduct"} ,
			         {"targets": [6],"data": "cardstatus"} ,
			         {"targets": [7],"data": "operators"} ,
			         {"targets": [8],"data": "ctype"} ,
			         {"targets": [9],"data": "attribute1"} ,
			         {"targets": [10],"data": "value1"} ,
			         {"targets": [11],"data": "attribute2"} ,
			         {"targets": [12],"data": "value2"} ,
			         {"targets": [13],"data": "ifMaintenance"} ,
			         {"targets": [14],"data": "msisdn"} ,
			         {"targets": [15],"data": "inDate"} ,
		           ],
			           
		  "sScrollX": "100%",
		  "sScrollXInner": "100%",
		  "bScrollCollapse": true,
		  "bPaginate": true,
		  "bLengthChange": false,
		  "bFilter": false,
		  "bDestroy":true,
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
	//验证信息
	function myValidate(){
		var custId = $("#custId").find("option:selected").val();
		if(custId =='' || custId == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.customer' />");
			 return false;
		}
		var type = $("#type").find("option:selected").val();
		if(type =='' || type == -1){
			 var lable1 = $("#lable1").text();
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />"+lable1);
			 return false;
		}
		var version = $("#version").find("option:selected").val();
		if(version =='' || version == -1){
			 var lable2 = $("#lable2").text();
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose' />"+lable2);
			 return false;
		}
		var operators = $("#operators").find("option:selected").val();
		if(operators =='' || operators == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.operator' />");
			 return false;
		}
		var cardstatus = $("#cardstatus").find("option:selected").val();
		if(cardstatus =='' || cardstatus == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.card.status' />");
			 return false;
		}
		var initproduct = $("#initproduct").find("option:selected").val();
		if(initproduct =='' || initproduct == -1){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.select.product' />");
			 return false;
		}
		return true;
	};
	
	//下载模板
	function downlaod(){
		window.location.href = "${ctx}/download/upload.xlsx";
	}
	
	function cmcc(){
		 window.location.href = "${ctx}/download/cmcc.xlsx";
	}
	//验证上传文件
	function validateFile(){
		var obj=document.getElementById('inputfile'); 
		if(obj.value=='') 
		{ 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.excel.file' />！");
			return false; 
		} 
		var stuff=obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; 
		if(stuff!='xls' && stuff!='xlsx' ) 
		{ 
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
			return false; 
		} 
		return true;
	}
	//验证input
	function validateInput(){
		var iccidHeader = $("#iccidHeader").val();
		if(iccidHeader =='' || isNaN(iccidHeader)){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.iccid.correctly' />");
			 return false;
		}
		var startIccid = $("#startIccid").val();
		if(startIccid =='' || isNaN(startIccid)){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.start.number.of.iccid.correctly' />");
			 return false;
		}
	    var endIccid = $("#endIccid").val();
		if(endIccid =='' || isNaN(endIccid)){
			 layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.write.end.number.of.iccid.correctly' />");
			 return false;
		}
		return true;
	}
	function upload(){
		var option = {
			    url : "<%=basePath %>userService/batchImport",
			    type: "post",
			    success : function(data){
			    	 //重新构建table  
                     $('#example').dataTable().fnClearTable();   //将数据清除 
			    	 $('#example').dataTable(dataTableObj);
			   }, 
			   error:function(){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
				}
		 };
		$("#cform").attr("enctype","multipart/form-data");
		if(myValidate()&& validateFile())
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
			$("#cform").ajaxSubmit(option);
		}
	}
	
	function importIccid(){
		var option = {
			    url : "<%=basePath %>userService/batchImport2",
			    type: "post",
			    success : function(data){
			    	 //重新构建table  
                     $('#example').dataTable().fnClearTable();   //将数据清除 
			    	 $('#example').dataTable(dataTableObj);
			   },
			   error:function(){
					alert("<fmt:message bundle='${pageScope.bundle}'  key='import.failed' />");
				}
		 };
		$("#cform").removeAttr("enctype");
		if(myValidate() && validateInput())
		if(window.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />？")){
			$("#cform").ajaxSubmit(option);
		}
	}
	//查询产品的方法
	 function queryGoodsRealease(){
	    	$('#initproduct').empty();
			$.ajax({
				url:"${ctx}/userService/getGoodRealses",
				data:{"value2":$("#version").val(),"value1":$("#type").val(),"custId":$("#custId").val(),"operatorsId":$("#operators").val()},
				success:function(result){
					var select=$("#initproduct").select2({
						width : 200,  
						placeholder: '<fmt:message bundle="${pageScope.bundle}"  key="Version" />',
						tags: "true",
						allowClear: true,
						data:result
					});
				}
			});
	    }
	</script>
</head>
<body>
	<div class="pd-20 font12">
	<form role="form" action="/userService/batchImport2" method="post"  id="cform">
		<div class="oh">
			<div class="col-12">
				<lable class="kehu"><fmt:message bundle='${pageScope.bundle}'  key='Customer' />: </lable><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
				<lable id="lable1"><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' />:</lable><select id="type" name="type" class="input-text" style="width:200px;"></select>
				<lable id="lable2"><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' />:</lable><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>
				<input type="text" id="attribute1" name="attribute1" style="display:none">
				<input type="text" id="attribute2" name="attribute2" style="display:none">
			</div>
		</div>
		<div class="oh">	
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Operator' />: </label>
				<div class="tBox">
					<select id="operators" name="operators" class="selectbox">
						<option value="-1"><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
						<c:forEach var="operator" items="${operatorList}">
						<option value="${operator.operatorsId}">${operator.operatorsName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Card.status' />：</label>
				<div class="tBox">
					<select id="cardstatus" name="cardstatus" class="input-text"></select>
				</div>
			</div>
			<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' />：</label>
				<div class="tBox">
				 	<select id="ifMaintenance" name="ifMaintenance" class="input-text">
						<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='YES' /></option>
						<option value="0"><fmt:message bundle='${pageScope.bundle}'  key='NO' /></option>
				   	</select>	
				</div>
			</div>
		</div>
		<div class="text-center mt20">
			<div class="col-3 ">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='Product' />：</label>
				<div class="tBox">
					<select id="initproduct" name="initproduct" class="input-text"></select>
				</div>
			</div>
			<button id="search" type="button" class="btn btn-primary radius" onclick="queryGoodsRealease();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query.products' /></button>
		</div>

		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<label><fmt:message bundle='${pageScope.bundle}'  key='Upload.mode' />：</label> 
			<input name="Fruit" type="radio" value=""  onclick="$('#selectHander').hide(),$('#selectExcel').show();"/>Excel<fmt:message bundle='${pageScope.bundle}'  key='File.upload' /> 
			<input name="Fruit" type="radio" value=""  onclick="$('#selectExcel').hide(),$('#selectHander').show();" /><fmt:message bundle='${pageScope.bundle}'  key='Manual.upload' />
		</div>
		
		<div class="cl pd-5 mt-10" id="selectExcel" style="display:none">
			<label for="name" style="float:left;width:100px;"><fmt:message bundle='${pageScope.bundle}'  key='Import.excel.file' />：</label>
	      	<input type="file" class="" style="width:200px;padding-top: 7px;float:left;" id="inputfile" name="file">
	        <button id="search" type="button" class="btn btn-primary radius" onclick="upload();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' /></button>
	        <button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;"
					onclick="downlaod();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Other.Excel.template.download' />
			</button>
			<button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;"
					onclick="cmcc();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Mobile.upload.Excel.template.download' />
				</button>
      	</div>
		<div class="cl pd-5 mt-10" id="selectHander" style="display:none">
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl">Iccid<fmt:message bundle='${pageScope.bundle}'  key='Head.of.number' />:</label>
				<div class="tBox">
					<input type="text" name="iccidHeader" id="iccidHeader" placeholder=" Iccid<fmt:message bundle='${pageScope.bundle}'  key='Head.of.number' />"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='origin' />Iccid:</label>
				<div class="tBox">
					<input type="text" name="startIccid" id="startIccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='origin' />Iccid"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl"><fmt:message bundle='${pageScope.bundle}'  key='deadline' />Iccid:</label>
				<div class="tBox">
					<input type="text" name="endIccid" id="endIccid" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='deadline' />Iccid"  style="width:200px" class="input-text">
				</div>
		</div>
		<div class="col-3 mt20">
				<label for="name" class="font12 labelWidth fl">Iccid<fmt:message bundle='${pageScope.bundle}'  key='Tail.of.number' />:</label>
				<div class="tBox">
					<input type="text" name="iccidEnd" id="iccidEnd" placeholder=" Iccid<fmt:message bundle='${pageScope.bundle}'  key='Tail.of.number' />"  style="width:200px" class="input-text">
				</div>
		</div>	
		<div class="text-center mt20">
			<button id="search" type="button" style="margin-top: 20px" class="btn btn-primary radius" onclick="importIccid();"><i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Import.file' /></button>
		</div>
		</div>
	</form>
	<div class="mt-20">
		<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
		<thead>
			<tr class="zpTable">
				<th >ICCID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Customer' />ID</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Device.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Password' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.subdivision.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Initial.product' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Card.type' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.1' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.1' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Attribute.2' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Value.2' /></th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Is.it.in.maintenance' /></th>
				<th >MSISDN</th>
				<th ><fmt:message bundle='${pageScope.bundle}'  key='Entry.time' /></th>
			</tr>
		</thead>
	</table>
	</div>
</div>
</body>
</html>
