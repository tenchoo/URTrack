<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Enterprise.customer.monthly.data.usage.query' /></title>
<base href="<%=basePath%>" />
<%-- <link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css"
	rel="stylesheet" type="text/css" /> --%>
	
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
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/static/js/echarts-all.js"></script>
<script type="text/javascript" src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script >
<style>
 .goodsSelect{
 display: none;
}
</style>
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
	init();
	$('#example').dataTable(dataTableObj);
});
function init(){
	var defaultDate=new Date().Format("yyyyMM");
	$("#monthId").val(defaultDate);
}

	var htmlList=[];
	$.ajax({
		url:"/trafficRecord/getChannelCust",
		data:{},
		success:function(result){
			    for(var i=0;i<result.length;i++){
			    	var str='<option value="'+result[i].id+'">'+result[i].text+'</option>';
			    	htmlList.push(str);
			    }
			    $("#custId").html(htmlList.join(" "));
			   
		}
	});
	
    var dataTableObj = {
            "bProcessing": true,
            "sPaginationType" : "bootstrap",
            "sServerMethod":"post",
            "bServerSide": true,
            "sAjaxSource" : "<%=basePath%>trafficRecord/getRecord", /* 跳转url */
            "iDisplayLength" : 5, /* 展示条数 */
            "columnDefs" : [
                    {
                        "targets" : [ 0 ],
                        "data" : "CUSTNAME",
                    },        
                    {
                        "targets" : [ 1 ],
                        "data" : "ICCID",
                    },        
                    {
                        "targets" : [ 2 ],
                        "data" : "GOODSNAME",
                    },        
                    {
                        "targets" : [3 ],
                        "data" : "OPERATORSNAME",
                    },
                    {
                        "targets" : [4 ],
                        "data" : "COUNT",
                    },
                    {
                        "targets" : [5 ],
                        "data" : "FEE",
                    },
                    {
                        "targets" : [6 ],
                        "data" : "ORDERDATE",
                    },
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
                aoData.push(
        		{
	                    "name" : "custId",
	                    "value" : $("#custId").val()
	                },
                {
                    "name" : "monthId",
                    "value" : $("#monthId").val()
                }
                );
            }
        });
        $('#example').dataTable().fnDraw();
    	}
    
    function exportExcel(){
    	/* 	$.ajax({
        		type:"POST",
        		data:{monthId:$("#monthId").val(),custId:$("#custId").val()},
        		url : "${ctx}/trafficRecord/exportExcel",
        		datatype: "json",
        		success:function(data){
        		}
        	}) */
    	var url = "${ctx}/trafficRecord/exportExcel?custId="+$("#custId").val() + "&&monthId=" + $("#monthId").val()
    	window.location.href = url;
    }
</script>
</head>
<body>
	<div class="seconSec ">
			<h1 align="left"><fmt:message bundle='${pageScope.bundle}'  key='Ordering.records.query' /></h1>
			<br>
	</div>
	
	<div class="pd-20" >
		<div class="oh row">
			  <label class="font12" style="float: left;width: 12%;"><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />：&nbsp;&nbsp;</label>
			  <select id="custId" name="custId" class="input-text" style="float: left;width: 20%;"></select>
			  <label class="font12 fl"><fmt:message bundle='${pageScope.bundle}'  key='please.select.query.month' />：&nbsp;&nbsp;</label>
			  <input id="monthId" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='please.select.query.month' /> " style="width:250px" type="text" 
				  onClick="WdatePicker({onpicked:function(dq){selectDatediff(dq.cal.getNewDateStr());},
			      dateFmt:'yyyyMM',minDate:'1970-01',maxDate:'%y-{%M+1}',skin:'whyGreen'})" readonly="readonly">
			      <button id="search" type="button" class="btn btn-primary radius" onclick="sreach();">
					<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Query' />
				</button>
	    </div>
	    <br>
	  <div class="fr mtb20">
							<button id="search" type="button" class="btn btn-primary radius" style="float:left;margin-left:10px;"
								onclick="exportExcel();">
								<i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Detail.information.of.export' />
							</button>
							</div>
		<div class=" pd-20">
			<table  cellspacing="0" id="example" 
				class="table table-border table-bordered table-hover table-bg table-sort">
				<thead>
					<tr class="zpTable">
						<th><fmt:message bundle='${pageScope.bundle}'  key='Customer' /><label id="labelt1"></label></th>
						<th>ICCID<label id="labelt1"></label></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Ordering.products' /><label id="labelt1"></label></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Operator' /><label id="labelt1"></label></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='quantity' /><label id="labelt1"></label></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Payment.amount' /><label id="labelt1"></label></th>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Ordering.date' /><label id="labelt1"></label></th>
				</thead>
			</table>
		</div>
		<div class="cl pd-5">
			<div id="histogram1"
				style="height: 500px; width: 90%; border: 0px solid #ccc; padding: 10px;"></div>
		</div>
	</div>	
</body>
</html>