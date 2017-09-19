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
<title><fmt:message bundle='${pageScope.bundle}'  key='Sales.statement.query' /></title>
<base href="<%=basePath%>" />
<!-- css -->
  <link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/stylereport.css">
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/component.css" />
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.ba-throttle-debounce.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.stickyheader.js"></script>
<style type="text/css">
.btnBlueO{
    background: url(/static/ui3/images/searchBtn.png) no-repeat;
}
.exportBtn{
    background: url(/static/ui3/images/exportBtn.png) no-repeat;
}
.bianxian {
    border-right: 1px solid #ddd;
</style>
<script type="text/javascript">
$(function(){           
	//日期显示当月
	var date=new Date;
	var year=date.getFullYear();
	var month=date.getMonth()+1;
	month =(month<10 ? "0"+month:month);
	var mydate = (year.toString()+'-'+month.toString());
	$('#chioceTime').val(mydate);
});

	function sreach(){
		var codeType = 5;
	    var chioceTime = $("#chioceTime").val();
		var nowtime = $("#chioceTime").val().split("-");
		var indate = nowtime[0]+nowtime[1];
		if(chioceTime ==""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose.the.month' />");
			return false;
		} 
		//TODO
		$.ajax({
			type : "post",
			url : "${ctx}/reportController/queryReportOne",
			data: {"typeCode":codeType,
				 "indate":indate
				},
			success:function(result){
				$("#tatbody").html();
				var disInfo = result.displayInfo;
				var colIds = result.colIds;
				var colArr = result.colArr;
				var condCols = result.condCols;
				var list = result.list;
				var thList=[];
				/*  thList.push("<thead> <tr>");
				for(var i = 0;i<disInfo.length;i++){
					thList.push("<th>");
					thList.push(disInfo[i]);
					thList.push("</th>");
				}
				thList.push("</tr> </thead>");
				thList.push("<tbody>");  */
				for(var i = 0;i<list.length;i++){
					thList.push("<tr>");
					for(var j = 0;j<colIds.length;j++){
						thList.push("<td>");
						if(colArr[j] == 1){
							/* '<a title=\"录入信息\" href="javaScript:toOneQuery('
							+ full.batchId
							+ ');" class=\"ml-5\" style=\"text-decoration:none\">录入信息</a>' */
/* 		            		"<a href='javaScript:detail("+list[i].reptId+");' class='btn_info' ></a>"+
 */
							thList.push("<a href=javaScript:toDetail("
									+'\''+list[i][condCols]+'\''
									+");>"
									+list[i][colIds[j]]
									+"</a>"
									);
						} else {
							thList.push(list[i][colIds[j]]);
						}
						thList.push("</td>");
					}
					thList.push("</tr>");
				}
				//thList.push("</tbody>");
				$("#tatbody").html(thList.join(" "));
				//goPage(1,10,list.length);
				$("td").addClass("bianxian");
			}
		})
	}
	function doImport(){
		var typeCode = 5;
		var chioceTime = $("#chioceTime").val();
		var nowtime = $("#chioceTime").val().split("-");
		var indate = nowtime[0]+nowtime[1];
		if(chioceTime==""){
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.choose.the.month' />!");
			return false;
		}
		var url = "${ctx}/reportController/exportExcel?indate="+indate+"&&typeCode="+typeCode
		window.location.href = url;
	}
</script>
</head>
<body style="overflow-y:hidden";>
 <div class="container">
<input type="hidden" id="reptId">
<!-- 加载内容放这里 -->
            <div class="tableBox">
                <div class="topBox clearfix">
                    <div class="searchBox">
                         <label><fmt:message bundle='${pageScope.bundle}'  key='choose.time' />：</label>
                        <input id="chioceTime" class="Wdate" name="chioceTime" onfocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,isShowToday:false,isShowOK:false,readOnly:true})" />
                         <button class="btn04 width03 " onclick="sreach();"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
                    </div>
                    <div class="exportTable"><button class="btn04 float-right " onclick="doImport();"><fmt:message bundle='${pageScope.bundle}'  key='Export.report' /></button></div>
                </div>
               <div class="tableTit">
                     	<fmt:message bundle='${pageScope.bundle}'  key='sales.statement' />
                </div>
             <table class="overflow-y" id="idData">
                  <thead>
             		<tr>
						<th><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='Operator' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='First.class.Category' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='secondary.classification' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='purchased.data.plan' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.who.are.in.the.system' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.normal.users' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.whose.telephone.is.out.of.service' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.who.wait.for.activated.device' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.who.cancel.account' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.who.are.in.the.silence.period' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.active.users' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.for.renewal.plan.(Accumulated.number)' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='Amount.of.renewal.plan.(Accumulated.number)' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='The.number.of.users.for.renewal.plan.(this.month)' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='Amount.of.renewal.plan.(this.month)' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='plan.will.expire.in.this.month' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='plan.will.expire.in.the.next.month' /></th>
	             		<th><fmt:message bundle='${pageScope.bundle}'  key='than.the.5%.of.total.data.plan.(excluding.Unicom)' /></th>
	             	</tr>
             	</thead>
             	<tbody id="tatbody">
             	</tbody>
             </table>
		</div>
	</div>
</body>
</html>