<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c :set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>

<title><fmt:message bundle='${pageScope.bundle}'  key='Define.warning.rules' /></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<script type="text/javascript"
	src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>

<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
	src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>

<style>
.error {
	color: red;
}
.verticalSpacing {
	margin-top: 10px;
}
 .selectGroup{
 display: none;
}
</style>
<script type="text/javascript">
var operationsofstrategy={}
$(function(){
   var type=$("#typeId").val();
   var select = document.getElementById("schemeType");  
   if(type=="1"){
      select.options[1].selected = true;
   }else if(type=="2"){
      select.options[2].selected = true;
      getGroups(2);
   }
    $.ajax({
		url:"${ctx}/tactical/getStrategyOperations",
		data:{id:$("#schemeId").val()},
		success:function(result){
		$.each(result, function(index, value) {
		     var checkbox=document.getElementById(""+value.id);
			 checkbox.checked = true;
			}); 
		}
	  });
})
 function selectOperaton(strategyId){
       if($("#"+strategyId).is(':checked')){
         layer.open({
			  type: 2,
			  area: ['300px', '400px'],
			  title:"<fmt:message bundle='${pageScope.bundle}'  key='select.action' />",	
			  fixed: false, //不固定
			  maxmin: true,
			  btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"],
			  content: '/tactical/selectOperatons?',
			   yes:function(index,layero){
			     var operationIds = $(layero).find("iframe")[0].contentWindow.getOperation();
			     operationsofstrategy[strategyId]=operationIds
			     layer.close(index);
			  },
			  btn2:function(index){
			  }
			});
       }else{
       }
    }
    function editGroup(){
         var type=$("#typeId").val();
         if(type=="2"){
            getGroups();
         }
    }
	function initGroup(){
		var schemeType=$("#schemeType").val();
		if(schemeType=="1"){
			$("#selectGroup").addClass("selectGroup");
			$("#groups").empty();
		}else if(schemeType=="2"){
			getGroups();
		}
	}
	function getGroups(type){
	    $("#selectGroup").removeClass("selectGroup");
			$.ajax({
				url:"${ctx}/tactical/getGroups",
				data:{},
				success:function(result){
				 var htmlList=[];
				 for(var i=0;i<result.length;i++){
				    	var str=' <label class="font12"><input type="checkbox" id=\"'+result[i].id+'\" name="group" value=\"'+result[i].id+'\"/>'+result[i].groupName+'&nbsp&nbsp&nbsp&nbsp</label>';
				    	htmlList.push(str);
				    }
				    $("#groups").html(htmlList.join(" "));
				    if(type==2){
				          $.ajax({
								url:"${ctx}/tactical/getGroupsOfScheme",
								data:{id:$("#schemeId").val()},
								success:function(result){
								 for(var i=0;i<result.length;i++){
								        var groupId=result[i].id;
								        var checkbox=document.getElementById(""+groupId);
								        checkbox.checked = true;
								    }
								}
							  });
				           }
				}
			});
	}
	$(document).ready(function() {
		$("#form_rule").validate();
	});
   function editOperations(strategyId) {
       var schemeId=$("#schemeId").val();
           layer.open({
			  type: 2,
			  area: ['300px', '400px'],
			  title:"<fmt:message bundle='${pageScope.bundle}'  key='select.action' />",	
			  fixed: false, //不固定
			  maxmin: true,
			  btn: ["<fmt:message bundle='${pageScope.bundle}'  key='Determine' />","<fmt:message bundle='${pageScope.bundle}'  key='Cancel' />"],
			  content: '/tactical/editOperations?schemeId='+schemeId+"&strategyId="+strategyId,
			   yes:function(index,layero){
			     var operationIds = $(layero).find("iframe")[0].contentWindow.getOperation();
			     operationsofstrategy[strategyId]=operationIds
			     layer.close(index);
			  },
			  btn2:function(index){
			  }
			});
	}
	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	function saveRule(){
	debugger
		if($("#schemeName").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='policy.name.cannot.be.empty' />");
			return;
		}
		if($("#schemeComment").val()==""){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Policy.description.cannot.be.empty' />");
			return;
		}
		if($("#schemeType").val()=="" || $("#schemeType").val()==null){
			layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='type.selection.cannot.be.empty' />");
			return;
		}
		var index = parent.layer.getFrameIndex(window.name);
		var schemeName=$("#schemeName").val();
		var schemeComment=$("#schemeComment").val();
		var schemeType=$("#schemeType").val();
		var groupIds=[];
	    var strategyIds=[];
		if(schemeType=="2"){
			$('input[name="group"]:checked').each(function(){
	            groupIds.push($(this).val());
	        }); 
		}
		$('input[name="strategy"]:checked').each(function(){
	            strategyIds.push($(this).val());
	        });
	        debugger
	    if(strategyIds==null||strategyIds.length<=0){
		    layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Constraints.cannot.be.empty' />");
				return;
	    }
	    var strategyJson=JSON.stringify(operationsofstrategy);
	     debugger 
		$.ajax({
	        type:"POST",
	        url:"${ctx}/tactical/saveScheme",
	        data:{"id":$("#schemeId").val(),"schemeName":schemeName,"schemeComment":schemeComment,"targittype":schemeType,"delflag":"1","groupIds":groupIds.join(","),strategyJson},
			dataType : "json",
			cache : false,
			success : function(data) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='successfully.added' />");
				window.parent.location.reload();
				parent.layer.close(index);
			},
			error : function(error) {
				alert("<fmt:message bundle='${pageScope.bundle}'  key='failed.added' />");
			}
		});
	}
</script>
</head>
<body>
	<div class="">
		<div class="seconSec font12">
			<form method="post" class="form form-horizontal" id="form_rule" name="form-member-add">
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Policy.name' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
							<input type="text" class="input-text" id="schemeName" name="schemeName" value="${schemeDto.schemeName}" required>
							<input type="hidden" class="input-text" id="schemeId" name="schemeId" value="${schemeDto.id}">
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1><fmt:message bundle='${pageScope.bundle}'  key='Policy.description' />：</h1>
				</div>
				<div class="row cl">
					<div class="col-10 from-control">
						<div class="formControls col-8 col-offset-2">
						<textarea rows="4px" cols="62px" id="schemeComment" name="schemeComment"  required>${schemeDto.schemeComment}</textarea>
						</div>
					</div>
				</div>
				<div class="row cl">
					<h1>类别选择：</h1>
				</div>
			    <div class="oh row"  style="margin-left:86px;">
			           <input type="hidden" class="input-text" id="typeId" name="typeId" value="${schemeDto.targittype}">
					   <select class='input-text' id="schemeType" name="schemeType" onchange="initGroup();"  style="width: 40%;">
							<option value=""><fmt:message bundle='${pageScope.bundle}'  key='Please.select.policy.type' /></option>
							<option value="1"><fmt:message bundle='${pageScope.bundle}'  key='Common.policy' /></option>
							<option value="2"><fmt:message bundle='${pageScope.bundle}'  key='privacy.policy' /></option>
						</select>
			    </div>
			    <div class="selectGroup" id="selectGroup" >
				    <div class="row cl">
						<h1><fmt:message bundle='${pageScope.bundle}'  key='Device.group.selection' />：</h1>
					</div>
					<div class="row cl" id="groups" style="margin-left:86px;">
					</div>
			    </div>
				<div class="row cl">
					<h1>规则约束：</h1>
				</div>
				<div class="row cl" id="condition" style="margin-left:86px;">
					 <c:forEach items="${strategyDtos}" var="strategy">
					    <label class="font12">
					        <input type="checkbox" name="strategy" id="${strategy.id}" value="${strategy.id}" onclick="selectOperaton(${strategy.id})"/>
					    </label>
					    <a onclick="editOperations(${strategy.id})"><c:out value="${strategy.strategyName}"></c:out>&nbsp&nbsp&nbsp&nbsp&nbsp</a>
				     </c:forEach>
				</div>
				
				<div class="row cl" style="margin-bottom: 20px;">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Submit' />&nbsp;&nbsp;" onclick="saveRule();">
						<input class="btn btn-default radius" type="reset"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Emptied' />&nbsp;&nbsp;"> <input
							class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
