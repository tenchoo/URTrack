<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_CN" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'  key='Report.forms.query ' /></title>
<base href="<%=basePath%>" />
<!-- css -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/stylereport.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/component.css" />
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript" src="static/js/H-ui.js"></script>
<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.ba-throttle-debounce.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ui3/js/jquery.stickyheader.js"></script>
<style type="text/css">
.btnBlueO {
	background: url(/static/ui3/images/searchBtn.png) no-repeat;
}

.exportBtn {
	background: url(/static/ui3/images/exportBtn.png) no-repeat;
}

.bianxian {
	border-right: 1px solid #ddd;
}
</style>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : "reportController/showCodeAndName",
			data : {},
			success : function(result) {
				var select = $("#reptId").select2({
					width : 200,
					placeholder : '<fmt:message bundle="${pageScope.bundle}"  key="Report.name" />',
					tags : "true",
					allowClear : true,
					data : result
				});
				/*  $("#reptId").change(function(){
				for(var i=1;i<result.length;i++){
					if(result[i].id==$("#reptId").val()){
						
					}
				}
				})   */
			}
		})
	})

	function sreach() {
		var reptId = $("#reptId").val();
		if (reptId == -1 || reptId == "" || reptId == null) {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.report' />!");
			return false;
		}
		/* var chioceTime = $("#chioceTime").val();
		var nowtime = $("#chioceTime").val().split("-");
		var indate = nowtime[0]+nowtime[1];
		 if(chioceTime ==""&&reptId==9){
			alert("请选择月份");
			return false;
		}
		if(reptId==6||reptId==8){
			var indate = 0;
		} */
		//TODO
		$.ajax({
			type : "post",
			url : "${ctx}/reportController/queryReportOne",
			data: {"typeCode":$("#reptId").val()
				},
			success:function(result){
				$("#idData").html();
				var disInfo = result.displayInfo;
				var colIds = result.colIds;
				var colArr = result.colArr;
				var condCols = result.condCols;
				var list = result.list;
				var thList=[];
				thList.push("<thead> <tr>");
				for(var i = 0;i<disInfo.length;i++){
					thList.push("<th>");
					thList.push(disInfo[i]);
					thList.push("</th>");
				}
				thList.push("</tr> </thead>");
				thList.push("<tbody>");
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
				thList.push("</tbody>");
				$("#idData").html(thList.join(" "));
				//goPage(1,10,list.length);
				$("td").addClass("bianxian");
			}
		})
	}
	function doImport() {
		var typeCode = $("#reptId").val();
		/* var chioceTime = $("#chioceTime").val();
		var nowtime = $("#chioceTime").val().split("-");
		var indate = nowtime[0]+nowtime[1]; */
		if (typeCode == -1 || typeCode == "" || typeCode == null) {
			alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.report' />!");
			return false;
		}
		/*  if(chioceTime ==""&&typeCode==9){
			alert("请选择月份");
			return false;
		}
		if(typeCode==6||typeCode==8){
			var indate = 0;
		} */
		var url = "${ctx}/reportController/exportExcel?typeCode=" + typeCode
		window.location.href = url;
	}
</script>
</head>
<body>
 <div class="container">
	<input type="hidden" id="codeType">
	   <div class="tableBox">
		<div class="topBox clearfix">
			<div class="searchBox">
				<label><fmt:message bundle='${pageScope.bundle}'  key='Please.select.a.report' /></label> 
				<select id="reptId" class="input-text" style="width: 15%; height: 33px"></select>
				<button class="btn04 width03 mgT03" onclick="sreach();"><fmt:message bundle='${pageScope.bundle}'  key='Query' /></button>
			</div>
			<div class="exportTable">	
				<button class="btn04 float-right mgT03" onclick="doImport();"><fmt:message bundle='${pageScope.bundle}'  key='Export.report' /></button>
			</div>
		</div>
		<table class="overflow-y" id="idData">
		</table>
	</div>
	</div>
</body>
</html>