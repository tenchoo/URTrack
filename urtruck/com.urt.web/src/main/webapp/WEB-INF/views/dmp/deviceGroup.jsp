<%@ page contentType="text/html;charset=UTF-8"%>
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
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='List.of.project.groups' /></title>

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
			//删除设备组
		 	function del(id){
		 		layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Confirm.to.delete.this.group' />?",{
		 			btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"] //按钮
		 		},function(){
		 			$.ajax({
				        type:"POST",
				        url:"${ctx}/deviceManage/del?id="+id,
						cache : false,
						dataType:"json",
						success : function(data) {
							if(data.success){
								layer.msg(data.msg,{icon:1,time:1000});
							}else{
								layer.msg(data.msg,{icon:2,time:1000});
							}
								sreach();
						}
					});
		 		},function(){
		 			
		 		})
		 		}
			function add_deviceManage(title,url,w,h){
				layer_show(title,url,w,h);
			}
			//更新
		 	function toUpdate(id){
				if(id!=""){
					url='${ctx}/deviceManage/toUpdate/'+id;
					layer_show("<fmt:message bundle='${pageScope.bundle}'  key='Edit.project.group' />",url,'800','550');
				}
				
			}
		    function import_cust(id){
		    	$("#groupId").val(id);
		        var index = layer.open({
		        type: 1, 
		        area: ['400px', '150px'],
		        scrolling:'no',
		        name:'12',
		        content: $('#msg') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响,
		      });
		  }
			var dataTableObj = {
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"sAjaxSource" : "<%=basePath%>deviceManage/deviceManageList", /* 跳转url */
				"iDisplayLength" : 10, /* 展示条数 */
				"columnDefs" : [
						{
							"targets" : [ 0 ],
							"visible": false, 
							"data" : "ID"
						},
						{
							"targets" : [ 1 ],
							"data" : "GROUP_NAME"
						},
						{
							"targets" : [ 2 ],
							"data" : "GROUPCOMMENT"
						},
						 {
							"targets" : [ 3 ],
							"data" : null,
							"mRender" : function(data, type, full) {
								var id = data.ID
		                    	$.ajax({
		                    		type:"post",
		                    		data:{"id":data.ID},
		                    		url:"<%=basePath%>deviceManage/deviceNum",	
		                    		success:function(data){
		                    			$("#"+id).html(data.deviceNum);
		                    		}
		                    	})
		                    	 return '<font id=\"'+data.ID+'\"></font>';
							}
						}, 
						{
							"targets" : [ 4 ],
							"data" : null,
							"mRender" : function(data, type, full) {
								return '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Delete\" />\" href="javaScript:del('
										+ data.ID
										+ ');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"Delete\" /></i></a>'
										+ '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Edit\" />\" href="javaScript:toUpdate('
										+ data.ID
										+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"Edit\" /></a> '
										+ '<a title=\"<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Import.equipment\" />\" href="javaScript:import_cust('
										+ data.ID
										+ ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"Import.equipment\" /></a> '
										
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
		        $('#example').dataTable().fnDraw();
		    }
			//点击导入
			function upload(){
				//var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				var id = $("#groupId").val();
				var option = {
					url : "<%=basePath%>/deviceManage/importCardGroup?id="+id,
					type : "post",
					success : function(data) {
						if(data){
							layer.alert(data.retMsg,function(){
								sreach();
							layer.closeAll();
							$("#inputfile").val("");
							})
						}else{
							  layer.alert(data.retMsg,function(){
		                          open.layer.closeAll();
		                      })
						}
					}
				};
				if(validateFile())
					layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='are.you.sure.to.import' />?", {
						btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"] //按钮
						}, function(){
							$("#sform").ajaxSubmit(option);
						}, function(){
						  
						});
			}
			//通知模板下载
			function notify(){
				 window.location.href = "${ctx}/download/device.xlsx";
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
					if(stuff!='xls' && stuff!='xlsx' ){ 
						layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.correct.Excel.file.format' />");
						return false; 
					} 
					return true;
				}
			$(document).ready(function() {
				$('#example').dataTable(dataTableObj);
			});
</script>
</head>
<body>
		<input type="hidden" id="groupId">
			 <div id='msg' style="display: none;"><div class="pd-20 font12"><form  role="form" action="/cust/import_cust"
			      method="post" enctype="multipart/form-data" id="sform">
			      <div class="cl pd-5 mt-10">
			        <label style="float:left"><fmt:message bundle='${pageScope.bundle}'  key='Import.files' />:</label>
			        <input type="file" class=""  style="width:200px; padding: 7px;float:left;" id="inputfile" name="file">
			        <button id="search" type="button" class="btn btn-primary radius" style="float:left"
			          onclick="upload(id);"> <fmt:message bundle='${pageScope.bundle}'  key='Import.file' /></button>
			      </div>
			    </form></div></div>
				<c:if test="${not empty message}">
					<div id="message" class="alert alert-success">
						<button data-dismiss="alert" class="close">×</button>${message}</div>
				</c:if>
				<div class="pd-20">
			
					<div class="fr mtb20">
						<a href="javascript:;"
							onclick="add_deviceManage('<fmt:message bundle=\"${pageScope.bundle}\"  key=\"Add.device.group\" />','${ctx}/deviceManage/addDeviceManage','800','550')"
							class="btn btn-primary radius"> <span class="human"></span><fmt:message bundle='${pageScope.bundle}'  key='Add.device.group' />
						</a>
					</div>
							<div class="fr mtb20">
							<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
								onclick="notify();">
								<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Device.information.import.template.download' />
							</button>
							</div>
					<div class="mt-20">
						<table id="example"
							class="table table-border table-bordered table-hover table-bg table-sort "
							cellpadding="0">
							<thead>
								<tr class="zpTable">
									<th>id</th>
									<th><fmt:message bundle='${pageScope.bundle}'  key='Device.group.name' /></th>
									<th><fmt:message bundle='${pageScope.bundle}'  key='Device.group.description' /></th>
								 	<th><fmt:message bundle='${pageScope.bundle}'  key='Equipment.quantity' /></th>	
									<th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
</body>
</html>
