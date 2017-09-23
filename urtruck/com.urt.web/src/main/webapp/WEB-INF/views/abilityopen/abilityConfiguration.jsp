<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title><fmt:message bundle='${pageScope.bundle}'  key='capability.opening' /></title>
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
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
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





<script type="text/javascript">
    
    function del(id){
    	if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.you.want.to.delete' />")){
    		$.ajax({
    			type:"POST",
		        url:"${ctx}/abilityOpen/delIP?id="+id,
				cache : false,
				dataType:"json",
				success : function(data) {
					debugger
					if(data.success){
						/* layer.msg(data.msg,{icon:1,time:1000});
						sreach(); */
						sreach();
					}else{
						sreach();
					}
				}
    			
    		})
    		
    	}
    }
    function blockUp(id){
    	   
    		$.ajax({
    			type:"POST",
		        url:"${ctx}/abilityOpen/stopIP?id="+id,
				cache : false,
				dataType:"json",
				success : function(data) {
					debugger
					layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='disable.successfully' />");
					sreach();
				}
    			
    		})
    }
    
    function startUsing(id){
    	$.ajax({
			type:"POST",
	        url:"${ctx}/abilityOpen/openIP?id="+id,
			cache : false,
			dataType:"json",
			success : function(data) {
				layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='enable.successfully' />");
				sreach();
			}
			
		})
    }
    
    
    function add_ipaddress(title,url,w,h){
		layer_show(title,url,w,h);
	}
    
    
	function upload(){
		window.location.href = "${ctx}/download/GlobalLenovoAPI.doc";
	}
	var token="";
	function queryAppKey(){
		//发送ajax
	    $.ajax({
			type : "post",
			url : "${ctx}/abilityOpen/queryAppKey",
			data: {"custId":"${sessionUser.custId}"}, 
			success:function(result){
				if (result=="0") {
					token=result;
					$("#div1").show()
					
				}else{
				   $("#ruleName").val(result);
				}
			}
		});
	}
	
	function createAppKey(){
		//发送ajax
	    $.ajax({
			type : "post",
			url : "${ctx}/abilityOpen/createAppKey",
			data: {"custId":"${sessionUser.custId}"}, 
			success:function(result){
				$("#ruleName").val(result);
				//$("#div1").hide()
				$("#search").attr("disabled", true); 
			}
		});
	}
	
	$(document).ready(function() {
		$('#example').dataTable(dataTableObj);
		queryAppKey();
		document.getElementById('div1').style.display='none';
	});
	var dataTableObj = {
			"bProcessing": true,
			"sPaginationType" : "bootstrap",
			"sServerMethod":"post",
			"shorRowNumber":true,
		    "bServerSide": true,
			"sAjaxSource" : "<%=basePath%>abilityOpen/ipAddRessList?custId=${sessionUser.custId}", /* 跳转url */
			"iDisplayLength" : 10, /* 展示条数 */
			"columnDefs" : [
					/* {
						"targets" : [ 0 ],
						"data" : "RULE_NAME"
					}, */
					{
						"targets" : [ 0 ],
						"data" : "IP_ADDRESS"
					},
					{
						"targets" : [ 1 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var date = DateFormat.tolongdata(data.CREAT_DATE);
							return date;
						}
					},
					{
						"targets" : [ 2 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var date = DateFormat.tolongdata(data.UPDAT_DATE);
							return date;
						}
					},
					{
						"targets" : [ 3 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							debugger;
							var str = "";
							if (data.ISDISABLED == 0) {
								str = "<fmt:message bundle='${pageScope.bundle}'  key='enabled' />"
							} else {
								str = "<fmt:message bundle='${pageScope.bundle}'  key='disabled' />"
							}
							return str;
						}
					},
					{
						"targets" : [ 4 ],
						"data" : null,
						"mRender" : function(data, type, full) {
							var status='';
							
							if(data.ISDISABLED==0){
								
								status='<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="disabled" />\" href="javaScript:blockUp('+data.ID+
								');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="disabled" /></i></a>';
							}else if(data.ISDISABLED==1){
								
								status='<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="enable" />\" href="javaScript:startUsing('
									+ data.ID
									+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="enable" /></i></a>';
							}else{
								layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='unknown.state' />");
							}
							return   '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('
									+ data.ID
							         + ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></i></a>'
									+status;  	
						}
					} 

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
					"name" : "ruleName",
					"value" : $("#ruleName").val()
				}

				);
			}
		});
		$('#example').dataTable().fnDraw();
	}
	
</script>
</head>
<body >
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<div class="oh row">
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl">APPKEY：</label>
				<div class="tBox">
					<input type="text" readonly="true" class="input-text" value="" id="ruleName"
						name="ruleName">
				</div>
			</div>
			<div class="col-md-4 col-lg-4 mt20">
				<label for="name" class="font12 fl">CUSTID：</label>
				<div class="tBox">
					<input type="text" readonly="true" class="input-text" value="${sessionUser.custId}" id="custId"
						name="custId">
				</div>
			</div>
		</div>
		<div class="cl pd-5  bk-gray mt-20" id="div1">
					<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;" onclick="createAppKey();">
						<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='generate' /> AppKey
			       </button>
		</div> 
		<div class="cl pd-5  bk-gray mt-20">
				<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;" onclick="upload();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Interface.document.download' />
		       </button>
		</div>
		<div class="fr mtb20">
			<a href="javascript:;"
				onclick="add_ipaddress('添加IP地址','${ctx}/abilityOpen/addipaddress','600','200')"
				class="btn btn-primary radius"> <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='Added.IP.addresses' />
			</a>
		</div>
		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='IP.addresses' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='create.time ' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='update.time' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='status' /></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>
