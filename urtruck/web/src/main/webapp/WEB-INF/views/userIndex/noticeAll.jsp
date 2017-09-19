<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tagEx" uri="http://www.tag.com/mytag" %>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>   
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<html lang="en" class="overHid">
<head>
    <meta charset="UTF-8">
    <title><fmt:message bundle='${pageScope.bundle}'  key='Notice Page' /></title>
    <script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/ui3/css/newStyle.css">
    <link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
    <script type="text/javascript" src="${ctx}/static/ui3/js/jquery.js"></script>
    <%-- <script type="text/javascript" src="${ctx}/static/ui3/js/data_list.js"></script> --%>
    	
</head>
<body class="add_bg">
<input id="hidePageNo" type="hidden">
<input id="hideTotalPage" type="hidden">
<input id="hidePageSize" type="hidden">
    <div class="main clearfix">
        <div class="clear"></div>
        <div class="container">
            <!-- 加载内容放这里 -->
            <div class="box clearfix">
               <h3><span><fmt:message bundle='${pageScope.bundle}'  key='Important.notice' /></span></h3>
                <div class="boxBody height01">
                    <ul class="msgList clearfix" id="noticeAll">
                    </ul>
                      <p class="textLeft lineHeight01" id="showInfo"></p>
                </div>
                 <div class="boxFooter">
                    <div class="pages">
                        <span id="firstBtn"><fmt:message bundle='${pageScope.bundle}'  key='home.page' /></span>
                        <span id="preBtn"><fmt:message bundle='${pageScope.bundle}'  key='The.previous.page' /></span>
                       <!--  <a class="on">1</a>
                        <a href="#?page=2">2</a>
                        <a href="#?page=3">3</a>
                        <a href="#?page=4">4</a>
                        <a href="#?page=5">5</a>
                        <a href="#?page=6">6</a> -->
                        <span id="nextBtn"><fmt:message bundle='${pageScope.bundle}'  key='The.next.page' /></span>
                        <span id="lastBtn"><fmt:message bundle='${pageScope.bundle}'  key='The.last.page' /></span>
                        <span><font id="pageNo">0</font>/<font name="totalPage"></font></span>
                        <i><fmt:message bundle='${pageScope.bundle}'  key='Total' /><font name="totalPage">0</font><fmt:message bundle='${pageScope.bundle}'  key='page' />，<fmt:message bundle='${pageScope.bundle}'  key='To' /><input type="text" id="entryPageSize"><fmt:message bundle='${pageScope.bundle}'  key='page' /><button id="GoBtn">GO</button></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>
$(function(){
	notice();
	BtnClick();
})
	function BtnClick(){
		$("#firstBtn").on("click",function(){
			notice(1);
		});
		$("#preBtn").on("click",function(){
			var pageNo = $("#hidePageNo").val();
			var pageSize = $("#hidePageSize").val();
			var nowStart = (pageNo-1)*pageSize+1;
			var pageStart= nowStart - pageSize;
			if(pageStart<1){
				notice(1);
			}else{
				notice(pageStart);
			}
		
		});
		$("#nextBtn").on("click",function(){
			var pageNo = $("#hidePageNo").val();
			var pageSize = $("#hidePageSize").val();
			var pageStart = (pageNo*pageSize)+1;
			var totalPage = $("#hideTotalPage").val();
			if(pageNo == totalPage){
				return;
			}else{
				notice(pageStart);
			}
		});
		$("#lastBtn").on("click",function(){
			var totalPage = $("#hideTotalPage").val();
			var pageSize = $("#hidePageSize").val();
			var pageStart = (totalPage-1)*pageSize;
			if(pageStart<1){
				notice(1);
			}else{
				notice(pageStart);
			}
		});
		$("#GoBtn").on("click",function(){
			var entryPageSize = $("#entryPageSize").val();
			var totalPage = $("#hideTotalPage").val();
			var pageSize = $("#hidePageSize").val();
			if(entryPageSize<1){
				notice(1)
			}else if(entryPageSize>totalPage){
				notice((totalPage-1)*pageSize);
			}else{
				notice((entryPageSize-1)*pageSize);
			}
			$("#entryPageSize").val("");
		});
	}
 //所有重要通知
     function notice(pageStart){
	$("#noticeAll").empty();
	$("#showInfo").empty();
    	 $.ajax({
    		 	type:"post",
    			url:"${ctx}/custNotice/custNoticeList",
    			data:{pageStart:pageStart},
    			success:function(result){
    				var res = result.pageDate.list;
    				var html = "";
    				if(res.length>0){
    				for (var i = 0; i< res.length; i++) {
    					html += '<li><a onclick="noticeContent('+res[i].NOTICEID+')">'+res[i].NOTICETITLE+'</a><span>'+res[i].STARTTIME+'</span></li>'
					}
    				var pageNo = result.pageDate.pageNo;
    				var pageSize = result.pageDate.pageSize;
    				var totalRecord = result.pageDate.totalRecord;
    				var startPa = (pageNo-1)*pageSize+1
    				var endPa = startPa + pageSize -1;
    				if(endPa>totalRecord){
    					endPa = totalRecord ;
    				}else{
    					endPa = startPa + pageSize -1;
    				}
    				$("#hideTotalPage").val(result.pageDate.totalPage);
    				$("#hidePageNo").val(result.pageDate.pageNo);
    				$("#hidePageSize").val(result.pageDate.pageSize);
    				$("#noticeAll").html(html);
    				$("#showInfo").html("<fmt:message bundle='${pageScope.bundle}'  key='Current.view' />"+startPa+"<fmt:message bundle='${pageScope.bundle}'  key='To' />"+endPa+"<fmt:message bundle='${pageScope.bundle}'  key='Article' />,<fmt:message bundle='${pageScope.bundle}'  key='Total' />"+result.pageDate.totalRecord+"<fmt:message bundle='${pageScope.bundle}'  key='Records' />");
    				$("font[name='totalPage']").html(result.pageDate.totalPage);
    				$("#pageNo").html(result.pageDate.pageNo);
    				$("#pageall").html(result.pageDate.totalPage);
    				}else{
    					$('button').unbind('click');
						$('span').unbind('click');
    				}
    			}
    		  });
    	}
    	function noticeContent(id){
    		url='${ctx}/custNotice/noticeContent/?noticeId='+id;
			window.location.href=url
    	}
</script>
</body>
</html>