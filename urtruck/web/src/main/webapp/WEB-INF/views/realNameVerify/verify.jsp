<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta name="author" content="">
<meta name="robots" content="all">
<meta name="Copyright" content="lenovo">
<title><fmt:message bundle='${pageScope.bundle}'  key='Lenovo.Connect' /></title>
<link href="${ctx}/static/css/global-new.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/skin/default/skin.css" rel="stylesheet" type="text/css" id="skin" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script> 

<style>
.error{
	color:red;
}
.verticalSpacing{
	margin-top: 10px;
}
</style>
</head>

<body>
<div class="con cf">
    <div class="wrapper oh">
		 <div class="content">
             <!--实名认证             -->
             <div class="real wrap">
                 <h2><fmt:message bundle='${pageScope.bundle}'  key='pealse.enter.real.name' /></h2>
               <h4 style="color:red;font-size:14px;">${message }</h4>
               <form action="${ctx}/realnameVerify/verify" id="form" class="real_form" method="post" enctype="multipart/form-data" >
                     <input type="text" name="realname" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='name' />" id="realname" value="${realname}">
                     
                     <select name="idtype">
						  <option value ="1"><fmt:message bundle='${pageScope.bundle}'  key='ID.card' /></option>
						  <option value ="2"><fmt:message bundle='${pageScope.bundle}'  key='Household.Register' /></option>
						  <option value="3"><fmt:message bundle='${pageScope.bundle}'  key='Military.id.card' /></option>
						  <option value="4"><fmt:message bundle='${pageScope.bundle}'  key='the.resident.identity cards of people.s.armed.policemen' /></option>
						  <option value="5"><fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.HongKong.and.Macao.to.Chinese.mainland' /></option>
						  <option value="6"><fmt:message bundle='${pageScope.bundle}'  key='travel.permit.for.residents.of.Taiwan.to.Chinese.mainland' /></option>
						  <option value="7"><fmt:message bundle='${pageScope.bundle}'  key='passport' /></option>
					 </select>
					<!--  <input type="hidden" name="idtype" value="1"> -->
	<!--                      <div class="select_div">
                         <div class="caret"></div>
                        
                         <input type="text" name="parperType" placeholder="证件类型" id="select_val" >
                        
                         <input type="hidden" id="input_val" name="idtype">
                         <select id="select_type">
                         	 <option></option>
                             <option value="1">身份证</option>
                             <option value="2">驾驶证</option>
                         </select>
                     </div> -->
                    
                     <input type="text" name="idnum" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Document.number' />" id="idnum" value="${idnum}">
                     <input type="text" name="tel" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='telephone.number' />" id="tel" value="${tel}">
                     <label><fmt:message bundle='${pageScope.bundle}'  key='upload.handheld.id.photos' /></label>
                     <span style="margin-left: 0">
	                     <div class="img_wrapper" style="margin-top: 10px;"><img src="${ctx}/static/images/sfz01.jpg" class="preview"  id="img1" /></div><p>1、<fmt:message bundle='${pageScope.bundle}'  key='clear.faces' /></p><p>2、<fmt:message bundle='${pageScope.bundle}'  key='All.the.information.is.clear.and.clear' /></p><p>3、<fmt:message bundle='${pageScope.bundle}'  key='bear.your.arms' /></p>
	                     <input id="handpic_path" type="hidden" name="handpicurl"/>
	                     <input id="handpic" type="hidden" value="handpic"/>
                     	 <a><input type="file" name="handpic" class="doc" onchange="uploadPic('img1','handpic_path');" /><fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.front.side.of.handheld.id.photos' /></a>
                     </span>
                     <span>
	                     <div class="img_wrapper"><img src="${ctx}/static/images/sfz02.jpg" class="preview" id="img2" /></div>
	                     <input id="frontpic_path" type="hidden" name="frontpicurl"/>
	                     <input id="frontpic" type="hidden" value="frontpic"/>
	                     <a><input type="file" name="frontpic"  class="doc"  onchange="uploadPic('img2','frontpic_path');" /><fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.front.side.of.ID.card.photos' /></a>
	                 </span>
                     <span>
	                     <div class="img_wrapper"><img src="${ctx}/static/images/sfz03.jpg" class="preview" id="img3" /></div>
	                     <input id="backpic_path" type="hidden" name="backpicurl"/>
	                     <input id="backpic" type="hidden" value="backpic"/>
	                     <a><input type="file" name="backpic" class="doc"  onchange="uploadPic('img3','backpic_path')"/><fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.back.side.of.ID.card.photos' /></a>
	                 </span>
                     <button type="button" onclick="check();"><fmt:message bundle='${pageScope.bundle}'  key='Submit' /><b>(<fmt:message bundle='${pageScope.bundle}'  key='Once.submitted,manual.will.review.your.data' />)</b></button>
                     <p style="text-indent: 0;"><fmt:message bundle='${pageScope.bundle}'  key='We.will.verify.your.uploaded.photos.within.5.working.days' /></p>
                 </form>
             </div>
		</div>
	</div>
</div>	
<script type="text/javascript">
function check(){
	debugger;
  	var nameValue=window.document.getElementById("realname").value;  
  	var idTypeValue=$("#idtype option:selected").val();/* window.document.getElementById("idtype").value; */  
  	var idnumValue=window.document.getElementById("idnum").value; 
  	var telValue=window.document.getElementById("tel").value;  
  	var handpicValue=window.document.getElementById("handpic_path").value;
  	var frontpicValue=window.document.getElementById("frontpic_path").value; 
  	var backpicValue=window.document.getElementById("backpic_path").value; 
  	if(nameValue == ""){
  		 $("#name_span").text("<fmt:message bundle='${pageScope.bundle}'  key='User.name.cannot.be.empty' />!");
         return false; 
  	}
    if(!/^[\u4e00-\u9fa5]{2,4}$/.test(nameValue)){
       	alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.a.valid.name' />");
       	return false;
    }
    if (idTypeValue == "") {     	
         alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.select.certificate.type' />!");  
         return false;  
    }
    if(idnumValue==''){
         alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.identification.number' />");
         return false;
    }
    if(!/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/.test(idnumValue)){
       	alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.the.correct.identification.number' />");
       	return false;
    }
    if(telValue==''){
        alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.your.contact.number' />");
        return false;
    }
    if(!/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(telValue)){
     	alert("<fmt:message bundle='${pageScope.bundle}'  key='Please.enter.a.valid.contact.number' />");
     	return false;
    }
    if(handpicValue==''){
        alert("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.front.side.of.handheld.id.photos' />");
        return false;
    }
    if(frontpicValue==''){
         alert("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.front.side.of.ID.card.photos' />");
         return false;
    }
    if(backpicValue==''){
         alert("<fmt:message bundle='${pageScope.bundle}'  key='please.upload.the.back.side.of.ID.card.photos' />");
         return false;
    }
    $.ajax({
    	url:'${ctx}/realnameVerify/verify',
    	data:$("#form").serialize(),
    	success:function(result){
    		if(result.success==true){
    			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Real.name.authentication.successful,please.wait.for.review' />");
    		}else{
    			layer.msg(result.msg)
    		}
    	},
    	error:function(){
    		layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Real.name.authentication.failed' />");
    	}
    });
    return true;  
}   
       
if (!document.getElementsByClassName) {
    document.getElementsByClassName = function (className, element) {
        var children = (element || document).getElementsByTagName('*');
        var elements = new Array();
        for (var i = 0; i < children.length; i++) {
            var child = children[i];
            var classNames = child.className.split(' ');
            for (var j = 0; j < classNames.length; j++) {
                if (classNames[j] == className) {
                    elements.push(child);
                    break;
                }
            }
        }
        return elements;
    };
}
function uploadPic(img,id){
	debugger;
	//定义参数
	var options = {
		url : "<%=request.getContextPath() %>/fileUpload/uploadPic",
		dataType : "json",
		type :  "post",
		success : function(data){
			debugger;
			//回调 二个路径  
			//url
			//path
			$("#"+img).attr("src",data.url);
			$("#"+id).val(data.url);			
		}
	};	
	//jquery.form使用方式
	$("#form").ajaxSubmit(options);
	
	
	
	
}

       
</script>  
</body>
</html> 