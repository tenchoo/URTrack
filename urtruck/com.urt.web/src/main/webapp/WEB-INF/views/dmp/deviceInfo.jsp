<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title><fmt:message bundle='${pageScope.bundle}'  key='Batch.operation' /></title>
<base href="<%=basePath%>" />
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

<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script >
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>

<script type="text/javascript">
var htmlList=[];
$.ajax({
	url:"/traffic/getChannelCust",
	data:{},
	success:function(result){
		    for(var i=0;i<result.length;i++){
		    	var str='<option value="'+result[i].id+'">'+result[i].text+'</option>';
		    	htmlList.push(str);
		    }
		    $("#custId").html(htmlList.join(" "));
	}
});
	function showMap(id){
		layer.open({
			  type: 2,
			  area: ['900px', '600px'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: '/deviceInfo/showMap?imei='+id
			});
		};
function sendTestInfo(){
	$.ajax({
		url:"/deviceInfo/sendDeviceInfo",
		data:{},
		success:function(result){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Message.sent.successfully' />")
		}
	});
}		
		
		function showTypeMap(){
			layer.open({
				  type: 2,
				  area: ['900px', '600px'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: '/deviceInfo/showDeviceTypeMap?devicetype='+ $("#deviceType").val()+'&custid='+$("#custId").val()+'&iccid='+$("#cardIccId").val()+'&phonenumber='+$("#phonenumberid").val()+'&imei='+$("#imeiId").val()
				});
			}
		
		function details(id){
			layer.open({
				  type: 2,
				  area: ['1200px', '600px'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: '/deviceInfo/details?imei='+id
				});
			};
			
		function showBatchMap(){
			debugger
			 var leng = $("#example tr").length; 
			  var imeis = new Array(); 
			  for(var i=1; i<leng; i++) 
			  { 
			    imeiStr = $("#example tr").eq(i).find("td:eq(1)").html(); 
			    imeis.push(imeiStr); 
			  } 
			layer.open({
				  type: 2,
				  area: ['900px', '600px'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: '/deviceInfo/showBatchMap?imeis='+ imeis.join(',')
				});
			};
		function trailMap(id){
			layer.open({
				  type: 2,
				  area: ['900px', '600px'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: '/deviceInfo/trailMap?imei='+id
				});
			};
		    var dataTableObj = {
		            "bProcessing": true,
		            "sPaginationType" : "bootstrap",
		            "sServerMethod":"post",
		            "bServerSide": true,
		            "sAjaxSource" : "<%=basePath%>deviceInfo/deviceList", /* 跳转url */
		            "iDisplayLength" : 5, /* 展示条数 */
		            "columnDefs" : [
                            {
		                        "targets" : [ 0 ],
		                        "data" : "CUSTNAME",
		                    },        
                            {
		                        "targets" : [ 1 ],
		                        "data" : "IMEI",
		                    },        
                            {
		                        "targets" : [ 2 ],
		                        "data" : "MODEL",
		                    },        
		                    {
		                        "targets" : [3 ],
		                        "data" : "GROUPNAME",
		                    },
		                    {
		                        "targets" : [4 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	var imei=data.IMEI;
		                        	$.ajax({
		                        		type:"post",
		                        		data:{"imei":data.IMEI},
		                        		url:"<%=basePath%>deviceInfo/isOnline",
		                        		success:function(data){
		                        			$("#"+imei).html(data.msg);
		                        		}
		                        	})
		                            return '<font id=\"'+data.IMEI+'\"></font>';
		                        }
		                    },
		                    {
		                        "targets" : [5 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	var networkType = data.NETWORKTYPE;
		                        	var workType;
		                        	if(networkType=="WIFI"){
		                        		workType="wifi";
		                        	}else if(networkType=="MobileData"){
		                        		workType="<fmt:message bundle='${pageScope.bundle}'  key='Data.traffic' />";
		                        	}else{
		                        		workType="";
		                        	}
		                        	return workType;
		                        }
		                    },
		                    {
		                        "targets" : [ 6 ],
		                        "data" : "PHONENUMBER",
		                        "visible": false,
		                    },
		                    {
		                        "targets" : [ 7 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	var devicetype=data.DEVICETYPE;
		                        	var type;
		                        	switch(devicetype){
		                        	case 0:
		                        		type="Android";
		                        		break;
		                        	case 1:
		                        		type="Windows";
		                        		break;
		                        	default:
		                        		type="未知";
		                        	}
		                           return type;
		                        }
		                    },
		                    {
		                        "targets" : [ 8 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	var simstate=data.SIMSTATE;
		                        	var deviceType=data.DEVICETYPE;
		                        	var snmStateZh;
		                        	if(deviceType==0){//安卓
		                        		switch(simstate){
			                        	case "0":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='unknown' />";
			                        		break;		
			                        	case "1":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Detachment.equipment' />";
			                        		break;
			                        	case "2":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Locked.(require.user.s.SIM.PIN.code.to.unlock)' />";
			                        		break;
			                        	case "3":
			                        		snmStateZh="被锁(需要用户的SIM PUK码解锁)";
			                        		break;
			                        	case "4":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Locked.(require.network.PIN.code.to.unlock)' />";
			                        		break;
			                        	case "5":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Be.ready' />";
			                        		break;
			                        	case "6":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Not.ready' />";
			                        		break;
			                        	case "7":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Permanently.abandoned' />";
			                        		break;
			                        	case "8":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='card.exception' />";
			                        		break;
			                        	case "9":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Restricted.card' />";
			                        		break;
			                        	default:
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='unknown' />";
			                        	}
		                        	}else{
			                        	switch(simstate){//windows
			                        	case "0":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='No.registration,.haven't.searched.for.new.operators.to.register' />";
			                        		break;
			                        	case "1":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Registered.domestic.network' />";
			                        		break;
			                        	case "2":
			                        		snmStateZh="未注册，正在寻找新的运营商去注册";
			                        		break;
			                        	case "3":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Registration.rejection' />";
			                        		break;
			                        	case "4":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='unknown' />";
			                        		break;
			                        	case "5":
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Registered,.roaming.status' />";
			                        		break;
			                        	default:
			                        		snmStateZh="<fmt:message bundle='${pageScope.bundle}'  key='Unavailable' />";
			                        	}
		                        	}
		                           return snmStateZh;
		                        }
		                    },
		                    {
		                        "targets" : [ 9 ],
		                        "data" : "NETWORKOPERATORNAME",
		                    },
		                    {
		                        "targets" : [ 10 ],
		                        "data" : null,
		                        "mRender" : function(data, type, full) {
		                        	var imei=data.IMEI;
		                            return '<button id=\"map\" type=\"button\" class=\"btn btn-primary radius\" onclick=\"showMap('+'\''+imei+'\''+');\"><fmt:message bundle=\"${pageScope.bundle}\"  key=\"Location.map\" /></button>'
		                                    + '<button id=\"batchMap\" type=\"button\" class=\"btn btn-primary radius\" onclick=\"trailMap('+'\''+imei+'\''+');\"> <fmt:message bundle=\"${pageScope.bundle}\"  key=\"Trajectory.diagram\" /></button>'
		                                    +'<button id=\"details\" type=\"button\" class=\"btn btn-primary radius\" onclick=\"details('+'\''+imei+'\''+');\"> <fmt:message bundle=\"${pageScope.bundle}\"  key=\"Details\" /></button>'
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
		    $(document).ready(function() {
		        $('#example').dataTable(dataTableObj);
		    });
		    function sreach() {
		        $('#example').dataTable().fnClearTable(false);
		        var oSettings = $('#example').dataTable().fnSettings();
		        oSettings.aoServerParams.push({
		            "fn" : function(aoData) {
		                aoData.push(
                		{
 		                    "name" : "custid",
 		                    "value" : $("#custId").val()
 		                },
		                {
		                    "name" : "iccid",
		                    "value" : $("#cardIccId").val()
		                },
		                {
				            "name" :  "phonenumber",
				            "value": $("#phonenumberid").val()
				        },
		                {
				            "name" :  "imei",
				            "value": $("#imeiId").val()
				        },
		                {
				            "name" :  "devicetype",
				            "value": $("#deviceType").val()
				        }
		                );
		            }
		        });
		        $('#example').dataTable().fnDraw();
		    }
</script>
</head>
<body>
	<div class="pd-20 font12">
		
		 <form role="form">
		    <div class="oh row">
			  <div class="col-md-4 col-lg-4 mt20">
                    <label for="custId" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</label>
                    <div class="tBox">
                        <select id="custId" name="custid" class="input-text"></select>
                    </div>
                </div>
                			  <div class="col-md-4 col-lg-4 mt20">
                    <label class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='System.type' />:</label>
                    <div class="tBox">
                        <select id="deviceType" name="deviceType" class="input-text">
                        	<option value=" "><fmt:message bundle='${pageScope.bundle}'  key='Please.choose' /></option>
							<option value="0">Android</option>
							<option value="1">Windows</option>
                        </select>
                    </div>		
                </div>
		    </div>	
		    
            <div class="oh row">
                <div class="col-md-4 col-lg-4 mt20">
                    <label for="cardIccId" class="font12 fl">ICCID:</label>
                    <div class="tBox">
                        <input type="text" class="input-text" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.input.a.valid.iccid' />" id="cardIccId" name="iccid">
                    </div>
                </div>
                <div class="col-md-4 col-lg-4 mt20">
                    <label for="phonenumberid" class="font12 fl">IMEI:</label>
                    <div class="tBox">
                        <input type="text" class="input-text" value="" placeholder="请输入IMEI号" id="imeiId" name="imei">
                    </div>
                </div>
                 <div class="col-md-4 col-lg-4 mt20">
                    <label for="phonenumberid" class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='Phone.number' />:</label>
                    <div class="tBox">
                        <input type="text" class="input-text" value="" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.phone.number' />" id="phonenumberid" name="phonenumber">
                    </div>
                </div>

            </div>
            <div class="mt20 clr" style="text-align: center;">
                <button id="search" type="button" class="btn btn-primary radius"
                    onclick="sreach();">
                    <i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Search' />
                </button>
                <input class="btn btn-default radius" type="reset"
                    value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
            </div>
        </form>
<!--         <button id="typeMap" type="button" class="btn btn-primary radius" style="float:right" onclick="sendTestInfo();">发送测试消息</button> -->
		<button id="typeMap" type="button" class="btn btn-primary radius" style="float:right" onclick="showTypeMap();">所有设备定位图</button>
		<button id="batchMap" type="button" class="btn btn-primary radius" style="float:right" onclick="showBatchMap();">本页设备定位图</button>
		
		<div class="mt-20">
            <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
                <thead>
                    <tr class="zpTable">
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
                        <th>IMEI</th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Model' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Location.group' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Is.it.online.or.not' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='network' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Phone.number' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='operating.system' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Card.status' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
                        <th><fmt:message bundle='${pageScope.bundle}'  key='map' /></th>
                    </tr>
                </thead>
            </table>
        </div>
	</div>
</body>
</html>
