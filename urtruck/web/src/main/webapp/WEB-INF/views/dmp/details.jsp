<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Customer.details.page' /></title>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" />
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
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
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
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>

<style>
.error {
	color: red;
}

.verticalSpacing {
	margin-top: 10px;
}
</style>
<script type="text/javascript">
			var imei = '${map.cardDto.imei}';
			var dataTableObj = {
			        "bProcessing": true,
			        "sPaginationType" : "bootstrap",
			        "sServerMethod":"post",
			        "bServerSide": true,
			        "sAjaxSource" : "<%=basePath%>deviceLog/logList?imei="+imei, /* 跳转url */
			        "iDisplayLength" : 5, /* 展示条数 */
			        "columnDefs" : [
			                {
			                    "targets" : [ 0 ],
			                    "data" : "CUSTNAME",
			                },
			                {
			                    "targets" : [ 1 ],
			                    "data" : "GROUPNAME",
			                },
			                {
			                    "targets" : [ 2 ],
			                    "data" : "STRATEGYNAME",
			                   
			                },
			                {
			                    "targets" : [ 3 ],
			                    "data" : "STATEGYCOMMENT",
			                },
			                {
			                    "targets" : [ 4 ],
			                    "data" : "TRIGGERCAUSE",
			                },
			                {
			                    "targets" : [ 5 ],
			                    "data" : "ISAGAINST",
			                },
			                {
			                    "targets" : [ 6 ],
			                    "data" : "OPERATIONNAME",
			                    
			                },
			                {
			                    "targets" : [ 7 ],
			                    "data" : "OPERATIONCOMMENT",
			                    
			                },
			                {
			                    "targets" : [ 8 ],
			                    "data" : "ISSUCCESS",
			                },
			                {
			                    "targets" : [ 9 ],
			                    "data" : "OPERATETIME",
			                },
			                {
			                    "targets" : [ 10 ],
			                    "data" : "OPERATETYPE",
			                },
			                {
			                    "targets" : [ 11 ],
			                    "data" : "OPERATEUSER",
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
			$(document).ready(function() {
			    $('#example').dataTable(dataTableObj);
			});
			
			 function sreach() {
			        $('#example').dataTable().fnClearTable(false);
			        var oSettings = $('#example').dataTable().fnSettings();
			        $('#example').dataTable().fnDraw();
			    }
			
			function stopWifi(){
				var imei = $("#imei").val();
				$.ajax({
					type:"post",
					data:{"imei":imei,"custid":$("#custid").val()},
					url:"<%=basePath%>operate/stopWifi",
					success:function(data){
						if(data){
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Success' />");
						}else{
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='unknown' />");
						}
						sreach()
					}
				})
			}
			function confirmStopWifi(){
				layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.turn.off.WiFi' />?", {
					  btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"] //按钮
					}, function(){
						stopWifi();
					}, function(){
					  
					});
			}
			function openWifi(){
				var imei = $("#imei").val();
				$.ajax({
					type:"post",
					data:{"imei":imei,"custid":$("#custid").val()},
					url:"<%=basePath%>operate/openWifi",
					success:function(data){
						if(data){
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Success' />");
						}else{
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='unknown' />");
						}
						sreach()
					}
				})
			}
			function confirmOpenWifi(){
				layer.confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.turn.on.WiFi' />?", {
					  btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"]
					}, function(){
						openWifi();
					}, function(){
					  
					});
			}
			function stopCard(){
				var imei = $("#imei").val();
				$.ajax({
					type:"post",
					data:{"imei":imei,"custid":$("#custid").val(),"iccid":$("#iccid").val(),"commend":"0"},
					url:"<%=basePath%>operate/stopOnCard",
					success:function(data){
						if(data){
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Success' />");
						}else{
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='unknown' />");
						}
						sreach()
					}
				})
			}
			function confirmStopCard(){
				layer.confirm('你确定停卡吗？', {
					btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"] //按钮
					}, function(){
						stopCard();
					}, function(){
					  
					});
			}
			function openCard(){
				var imei = $("#imei").val();
				$.ajax({
					type:"post",
					data:{"imei":imei,"custid":$("#custid").val(),"iccid":$("#iccid").val(),"commend":"1"},
					url:"<%=basePath%>operate/stopOnCard",
					success:function(data){
						if(data){
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='Success' />");
						}else{
							layer.alert("<fmt:message bundle='${pageScope.bundle}'  key='unknown' />");
						}
						sreach()
					}
				})
			}
			function confirmOpenCard(){
				layer.confirm('你确定开卡吗？', {
					btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"] //按钮
					}, function(){
						openCard();
					}, function(){
					  
					});
			}
   </script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_cust"
				name="form-member-add">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='customer.information' />：</h1>
				</div>
				<div class="row cl">
				<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="custName" name="custName" value="${map.resultmap.CUSTNAME}" readonly="readonly">
						</div>
						<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Equipment.type' />：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="model" name="model" value="${map.resultmap.MODEL}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='network' />：</label>
						<div class="formControls col-3 font12">
							<c:choose>
						       <c:when test="${map.resultmap.NETWORKTYPE=='WIFI'}">
						             <input type="text" class="input-text" value="wifi" readonly="readonly">
						       </c:when>
						        <c:when test="${map.resultmap.NETWORKTYPE=='MobileData'}">
						             <input type="text" class="input-text" value="<fmt:message bundle='${pageScope.bundle}'  key='Data.traffic' />" readonly="readonly">
						       </c:when>
						       <c:otherwise>
						             <input type="text" class="input-text" value="" readonly="readonly">
						       </c:otherwise>
						</c:choose>
						</div>
						<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Battery.level' />：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="batteryLevel" name="batteryLevel" value="${map.resultmap.BATTERYLEVEL}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control">
						<label class="form-label col-3 font12">IMEI：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text" id="imei" name="imei" value="${map.cardDto.imei}" readonly="readonly">
						</div>
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='operating.system' />：</label>
						<div class="formControls col-3 font12">
						<c:choose>
						       <c:when test="${map.cardDto.devicetype==0}">
						             <input type="text" class="input-text" value="Android" readonly="readonly">
						       </c:when>
						       <c:otherwise>
						             <input type="text" class="input-text" value="Windows" readonly="readonly">
						       </c:otherwise>
						</c:choose>
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Device.grouping' />：</label>
						<div class="formControls col-3 font12">
						 <input type="text" class="input-text" id="groupId" name="groupId" value="${map.ishavegroup}" readonly="readonly">
	
						</div>
						<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Card.status' />：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="simstate" name="simstate" value="${map.snmStateZh}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12">ICCID：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="iccid" name="iccid" value="${map.cardDto.iccid}" readonly="readonly">
						</div>
						<label class="form-label col-3">IMSI：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="imsi" name="imsi" value="${map.cardDto.imsi}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Phone.number' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="phonenumber" name="phonenumber" value="${map.cardDto.phonenumber}" readonly="readonly">
						</div>
						<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='Operator' />：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="networkoperatorname" name="networkoperatorname" value="${map.cardDto.networkoperatorname}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Registration.time' />：</label>
						<div class="formControls col-3 font12">
							<input type="text" class="input-text" id="creattime" name="creattime" value="${map.imputTime}" readonly="readonly">
						</div>
						<label class="form-label col-3"><fmt:message bundle='${pageScope.bundle}'  key='update.time' />：</label>
						<div class="formControls col-3">
						<input type="text" class="input-text" id="updatetime" name="updatetime" value="${map.updateStr}" readonly="readonly">
						</div>
					</div>
					<div class="col-10 from-control verticalSpacing">
						<label class="form-label col-3 font12"><fmt:message bundle='${pageScope.bundle}'  key='Is.the.device.online' />：</label>
						<div class="formControls col-3 font12">
							<c:choose>
							       <c:when test="${map.isonline}">
							             <input type="text" class="input-text" id="isonline" name="isonline" value="<fmt:message bundle='${pageScope.bundle}'  key='YES' />" readonly="readonly">
							       </c:when>
							       <c:otherwise>
							             <input type="text" class="input-text" id="isonline" name="isonline" value="<fmt:message bundle='${pageScope.bundle}'  key='NO' />" readonly="readonly">
							       </c:otherwise>
							</c:choose>
						</div>
						<label class="form-label col-3">CUSTID：</label>
						<div class="formControls col-3">
							<input type="text" class="input-text"
									id="custid" name="custid" value="${map.cardDto.custid}" readonly="readonly">
						</div>
					</div>
				</div>
				<div class="row cl" style="">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='user.operation' />：</h1>
				</div>
				<div class="mt-20">
					<button id="stopwifi" type="button" class="btn btn-primary radius" style="float:left;" onclick="confirmStopWifi();"><fmt:message bundle='${pageScope.bundle}'  key='Turn.off.WIFI' /></button>
				</div>
				<div class="mt-20">
					<button id="openwifi" type="button" class="btn btn-primary radius" style="float:left;margin-left:3em;" onclick="confirmOpenWifi();"><fmt:message bundle='${pageScope.bundle}'  key='Turn.on.WIFI' /></button>
				</div>
				<div class="mt-20">
					<button id="stopcard" type="button" class="btn btn-primary radius" style="float:left;margin-left:3em;" onclick="confirmStopCard();"><fmt:message bundle='${pageScope.bundle}'  key='Stop' /></button>
				</div>
				<div class="mt-20">
					<button id="opencard" type="button" class="btn btn-primary radius" style="float:left;margin-left:3em;" onclick="confirmOpenCard();"><fmt:message bundle='${pageScope.bundle}'  key='Open' /></button>
				</div>
				<div class="row cl" style="">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Operation.log' />：</h1>
				</div>
						<div class="mt-20">
            <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
                <thead>
                    <tr class="zpTable">
                        <th>用户名</th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Group.name' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Policy.name' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Policy.description' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Operation.trigger.reason' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Is.it.illegal' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Operation.name' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='operation.description' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='whether.successed.or.not' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Operation.type' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Person' /></th>
                    </tr>
                </thead>
            </table>
        </div>	
			</form>
		</div>
	</div>
</body>
</html>
