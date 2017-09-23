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
<html>
<head>
	<title><fmt:message bundle='${pageScope.bundle}'  key='Role.management' /></title>
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
	
	<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script> 
	<script type="text/javascript">

	  function sendSM(){
       $.ajax({
    	   type:"post",
    	   url:"<%=basePath %>batchQuery/toSendSM",
    	   dataType: "json",
    	   data:{"iccid":$("#mobileId").val(),"context":$("#contextId").val()},
    	   success:function(data){
    	      layer.msg(data.succ);
           },
       });
    }
	</script>
</head>
<body>
 
	 <div class="pd-20">
	     <div class="seconSec">
	     	<h1><fmt:message bundle='${pageScope.bundle}'  key='send.text.message' /></h1>
	     <!-- <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">发送短信</span> </div> -->
	     <div style="">
		     <div class="col-12 mt20">
			     <!-- <label for="name" class="fl langWidth">iccid ：</label> -->
			     <label class="fl labelWidth control-label text-left" for="mobileId">
					<span class="colorRed smallStar"></span>
					<span class="font12">iccid:</span>
				</label>
			     <div class="fformControls langMl zpInput">
			        <input id="mobileId" type="text" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.phone.number' />"></input>&nbsp;&nbsp;&nbsp;&nbsp;
			     </div>
		     </div>
		    <div class="col-12 mt20">
		        <!-- <label for="name" class="fl langWidth">发送内容 ：</label> -->
		        <label class="fl labelWidth control-label text-left" for="groupMemo">
					<span class="colorRed smallStar"></span>
					<span class="font12"><fmt:message bundle='${pageScope.bundle}'  key='send.content' />:</span>
				</label>
				<div class="formControls langMl zpInput">
					<textarea rows="4" cols="130px" name="groupMemo" id="groupMemo" class="textarea" style="width:100%;"></textarea>
				</div>
			</div>
		     
	         <div class="langMl text-left">
		          <button type="button" onclick="sendSM()" class="btn btn-primary radius" style="margin-top: 20px"> <fmt:message bundle='${pageScope.bundle}'  key='send.text.message' /></button>
	         </div>
			 
	     </div>
     	</div>
     </div>
</body>
</html>
 