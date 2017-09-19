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

<title><fmt:message bundle='${pageScope.bundle}'  key='Activate.the.card ' /></title>
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
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript" src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>

<style>
.error{
	color:red;
}
.verticalSpacing{
	margin-top: 10px;
}
.form-control{
	border:0 none !important;
}
.ml45{
    margin-left:45px;
}
</style>
<script type="text/javascript">
function activate(){
	$.ajax({
		url:"${ctx}/card/activate",
		data:$("#form_card").serialize(),
		success:function(data){
			if(data.indexOf("InvalidCard") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='Invalid.ID.card' />");
	    	}else if(data.indexOf("ok") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='activation.success' />");
	    	}else if(data.indexOf("activefailed") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
	    	}else if(data.indexOf("orderfailed") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='fail.to.order' />");
	    	}else if(data.indexOf("maintenance") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='there.exits.cards' />");
	    	}else if(data.indexOf("operatorServiceFailed") >-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='no.operator.operation' />");
	    	}else if(data.indexOf("activated")>-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='The.card.has.been.activated' />");
	    	}else if(data.indexOf("noAccess")>-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='permission.denied.for.the.card' />");
	    	}else if(data.indexOf("notsufficientfunds")>-1){
	    		alert("<fmt:message bundle='${pageScope.bundle}'  key='not.sufficient.funds' />");
	    	}
		},
		error:function(){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='activation.failed' />");
		}
	});
}
function iccidChange(){
	debugger;
	$.ajax({
		url:"${ctx}/card/getIccidInfoById",
		data:{"id":$("#ICCID").val()},
		type:"post",
		success:function(result){
			debugger;
			console.log(result);
			$("#lable1").text(result.ATT1+":");
			$("#lable2").text(result.ATT2+":");
			$("#type").val(result.VAL1);
			$("#version").val(result.VAL2);
			$("#custName").val(result.CUST_NAME);
			$("#custId").val(result.CUST_ID);
		}
	});
}
</script>    
</head>
<body>
<div class="">
<div class="seconSec font12">
<form method="post" class="form form-horizontal font12 " id="form_card" name="form-member-add">
	    <div class="row cl">
	    	<!-- <div class="col-12 form-control verticalSpacing ">
		    	<label class="form-label col-5"><span class="c-red"></span>客户:</label>
		      	<div class="formControls col-3">
		        	<input type="text" class="input-text" id="custName" />
		        	<input type="hidden" id="custId" name="custId" />
		      	</div>
		    </div>   -->
	    </div>
	     <div class="row cl">
	   		 <h1 class="formControls col-offset-4" ><fmt:message bundle='${pageScope.bundle}'  key='card.information' />:</h1>
	    </div>
	    <div class="row cl">
	    	<div class="col-12">
				<div class="col-12 form-control ">
	    			<label class="form-label col-5 "><span class="c-red">*</span>ICCID:</label>
			      	<div class="formControls col-3 ">
			        	<input type="text"  class="input-text" name="iccid" id="ICCID" onchange="iccidChange();" />
			      	</div>
			      	<!-- <label class="form-label col-2"><span class="c-red">*</span>激活码:</label>
			      	<div class="formControls col-4">
			      			<input type="text"  class="input-text" value=""  name="code" />
			      	</div> -->
			      	
			    </div>
				<div class="col-12 form-control verticalSpacing" ">
            <!-- <label class="form-label col-2" id="lable1"></label>
			      	<div class="formControls col-6">
			        	<input type="text" readonly="readonly" class="input-text" id="type" />
			      	</div> -->
			      	<!-- <label class="form-label col-2" id="lable2"></label> -->
			      	<!-- <div class="formControls col-4">
			        	<input type="text" readonly="readonly" class="input-text" id="version" />
			      	</div> -->
			    </div>
				<!-- <div class="col-12 form-control verticalSpacing"> -->
	    			<!-- <label class="form-label col-3"><span class="c-red">*</span>企业客户:</label>
			      	<div class="formControls col-3">
			        	<select id="cardAgent" name="cardAgent" class="form-control select2">
			        	</select>
			      	</div> -->
			      	<!-- <label class="form-label col-3"><span class="c-red">*</span>商品类型:</label> -->
			      	<%--<div class="formControls col-6">
			        	 <tagEx:exp custId="${custId}" typeId="col" value="attribute1"></tagEx:exp>
			        	<tagEx:productTypeSelect id="test1" name="test1" valueKey="STATIC_CODE" textKey="STATIC_NAME" value="" custId="${custId}" bind="test2"></tagEx:productTypeSelect>
			        	<tagEx:exp custId="${custId}" typeId="col" value="attribute2"></tagEx:exp>
			        	<select id="version">
			        	</select>
			        	<script type="text/javascript">
							if($("select[name=test1]")[0].selectedIndex>0){
								change();
							}
						</script> 
			        	<tagEx:productVersionSelect id="test2" name="test2" valueKey="STATIC_CODE" textKey="STATIC_NAME" pid="0"></tagEx:productVersionSelect> 
			      	</div>--%>
			    </div>
			   
			</div>
		</div>	
    	<div class="row cl">
	      	<div class="col-3 col-offset-5">
		        <input class="btn btn-primary radius ml45 " type="button" onclick="activate();"  value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Activation' />&nbsp;&nbsp;">
		        <input class="btn btn-default radius" type="reset"
					value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;">
		        <!-- <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="javascript:history.go(-1);"> -->
	      	</div>
    	</div>
</form>
</div>
</div>
</body>

</html>
