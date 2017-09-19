<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='System.user.management' /></title>

<!-- css -->
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

<!-- <style>
.mt20 {
margin-top: 0px !important;
 }
.pd-10 {
     padding: 0px !important; 
}
     
</style> -->
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
	src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript">
		//更新
		function toUpdateSsUser(accountId){
			if(accountId!=""){
				var url = '${ctx}/ssUser/update/'+accountId;
				layer_show('<fmt:message bundle="${pageScope.bundle}"  key="Edit.system.user" />',url,'800','550');
			}
		}
		//重置密码
		function resetPassword(accountId){
			layer.confirm('<fmt:message bundle="${pageScope.bundle}"  key="Are.you.sure.you.want.to.reset.this.user.password" />',function(index){
				$.ajax({
			        type:"POST",
			        url:"${ctx}/ssUser/resetPassword?accountId="+accountId,
					cache : false,
					dataType:"json",
					success : function(data) {
						if(data.flag){
							layer.msg(data.msg,{icon:1,time:1000});
						}else{
							layer.msg(data.msg,{icon:1,time:1000});
						}
					}
				});
			});
		}
		
		//冻结账号，账号状态为0表示处于正常状态，账号状态为1表示处于冻结状态
		function freezingAccount(accountId) {
			$.ajax({
				url:"${ctx}/ssUser/freezingAccount",
				data:{"accountId":accountId},
				type:"POST",
				dataType:"json",
				success:function(result) {
					if (result.success) {
						// 重置查询条件
						$("#loginName").val('');
						$("#nickname").val('');
						// 重新加载
						searchAccount();
						layer.msg("用户已冻结。",{icon:1,time:1000});
					}
				}
			});
		}
		
		//激活账号
		function activeAccount(accountId){
			$.ajax({
				url:"${ctx}/ssUser/activeAccount",
				data:{"accountId":accountId},
				type:"POST",
				dataType:"json",
				success:function(result) {
					if (result.success) {
						// 重置查询条件
						$("#loginName").val('');
						$("#nickname").val('');
						// 重新加载
						searchAccount();
						layer.msg("用户已激活。",{icon:1,time:1000});
					}
				}
			});
		}
		
		/*管理员-增加*/
		function admin_add(title,url,w,h){
			layer_show(title,url,w,h);
		}
	</script>
<script type="text/javascript">
	var dataTableObj = {
		"bProcessing": true,
		"sPaginationType" : "bootstrap",
		"sServerMethod":"post",
	    "bServerSide": true,
		"sAjaxSource" : "<%=basePath %>ssUser/toSsUserList",    /* 跳转url */
		"iDisplayLength": 10,  /* 展示条数 */
 					 "columnDefs": [ 
			         {"targets": [0],"data": "loginName","sDefaultContent" : ""} ,
			         {"targets": [1],"data": "nickname","sDefaultContent" : ""} ,
			         {"targets": [2],"data": null,
	  						"mRender": function(data, type, full){
	  							var status = full.status;
	  							if(status=="0") return "<fmt:message bundle='${pageScope.bundle}'  key='normal' />";
	  							else if(status=="1") return "<fmt:message bundle='${pageScope.bundle}'  key='freezing' />";
	  						}	 
			         } ,
			         {"targets": [3],"data":  null,
			        	 "mRender": function ( data, type, full ) {
				        		var date= DateFormat.tolongdata(data.createDate);
				                	return date;
				                 }
			         } ,
			         {"targets": [4],"data": 
			        	 null
			        	 ,
			        	 "mRender": function ( data, type, full ) {
			        		       var ids = data.roles;
			        		       var idStr = "";
			        		       if(ids != null){
				        		       for(var i=0;i<ids.length;i++) //遍历用户角色
				        		   	   { 
				        		    	   if(i>0){
				        		    		 idStr+=",";
				        		   		   }
				        		    	   if(ids[i] != null){
					        		    	   idStr+=ids[i].roleName;
				        		    	   }
				        		   	   }
			        		       }
				                	return idStr;
				                 }
			         } ,
			         {"targets": [5],"data": null,
			        	 "mRender": function ( data, type, full ) {
			        		    var status = full.status;
			        		    var link = '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Edit" />\" href="javaScript:toUpdateSsUser('+data.acconutId
			        		    		+ ');" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe60c;</i></a>'
			                			+'&nbsp;&nbsp;&nbsp;&nbsp;<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="reset.password" />\" href="javascript:resetPassword('+data.acconutId
			                			+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe63f;</i></a>'
			             		var userOper = '&nbsp;&nbsp;&nbsp;&nbsp;';
			             		if (status=="0"){
			             			userOper = userOper + '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="freezing" />\" href="javascript:freezingAccount('+ data.acconutId
		                			+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe60e;</i></a>';
			             		}
			             		else if (status=="1") {
			             			userOper = userOper + '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="active" />\" href="javascript:activeAccount('+ data.acconutId
		                			+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe605;</i></a>';
			             		}
			             		
			                	return (link + userOper);
			        	 }
			         } ,
			         
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
	
	$(document).ready(function(){
		$('#example').dataTable(dataTableObj);
	});
	
	function searchAccount(){
		$('#example').dataTable().fnClearTable(false);
		var oSettings = $('#example').dataTable().fnSettings();
	    oSettings.aoServerParams.push({
	        "fn": function (aoData) {
	            aoData.push(
	            		{
			            "name" :  "loginName",
			            "value": $("#loginName").val()},
			            {"name" : "nickname",
		            	 "value": $("#nickname").val()}
	 	            	 );}
	    });
	    $('#example').dataTable().fnDraw();
	}
	
	</script>
	
</head>
<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">
		<form role="form">
			<div class="oh row">
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' />：</label> 
					<div class="tBox">
						<input type="text" class="input-text" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.login.name' />" id="loginName" name="loginName"> 
					</div>
				</div>
				<div class="col-md-4 col-lg-4 mt20">
					<label for="name" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Nickname' />：</label> 
					<div class="tBox">
						<input type="text" class="input-text" value=""	placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.enter.nickname' />" id="nickname" name="nickname">
					</div>
				</div>
			</div>
			<div class="mt20 clr" style="text-align: center;">
				<button id="search" type="button" class="btn btn-primary radius"
					onclick="searchAccount();">
					<i class="Hui-iconfont">&#xe665;</i> <fmt:message bundle='${pageScope.bundle}'  key='search.user.in.system' />
				</button>
				<input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
			</div>
		</form>
		<div class="fr mtb20">
			<a href="javascript:;" onclick="admin_add('<fmt:message bundle='${pageScope.bundle}'  key='Add.system.user' />','${ctx}/ssUser/toAdd','800','550')" class="btn btn-primary radius">
			<!-- <i class="Hui-iconfont">&#xe600;</i> --><span class="human"></span>
					<fmt:message bundle='${pageScope.bundle}'  key='Add.system.user' /></a>
		</div>
		<div class="mt-20">
			<table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Login.name' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='Nickname' /></th>
						<!-- <th width="130"><fmt:message bundle='${pageScope.bundle}'  key='Email' /></th>
				<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='telephone.number' /></th> -->
						<th width="80"><fmt:message bundle='${pageScope.bundle}'  key='User.state' /></th>
						<th width="70"><fmt:message bundle='${pageScope.bundle}'  key='Creation.date' /></th>
						<th width="90"><fmt:message bundle='${pageScope.bundle}'  key='User.roles' /></th>
						<th width="100"><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
	function setIframeHeight(iframe) {
		if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
		iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
		}
		}
		};

		window.onload = function () {
		setIframeHeight(document.getElementById('ifm'));
		};
	</script>


</body>

</html>
