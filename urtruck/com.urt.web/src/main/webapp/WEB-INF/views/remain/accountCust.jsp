<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page"/>
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><fmt:message bundle='${pageScope.bundle}'  key='Customer.account.page' /></title>
<link href="${ctx}/static/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.min.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />

<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />

	
<script type="text/javascript" src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/layer/1.9.3/layer.js"></script> 
<script type="text/javascript" src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>  
<script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script> 
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
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

<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<style type="text/css">
</style>
	<script type="text/javascript">
	function add_rule(){	 
		  var custId = $("#custId").val();
		  url='${ctx}/remain/createRule/'+custId;
			layer_show('<fmt:message bundle="${pageScope.bundle}"  key="add.the.balance.warning.rule" />',url,'800','550');
	}
	function picked(){
		var cycId = $("#cycId").val();
		var accColumn = [{"targets": [0],"data": "accessId"} , 
		                 {"targets": [1],"data": "chargeId"} ,
		                 {"targets": [2],"data": "acctBalanceId"} ,
		                 {"targets": [3],"data": "oldBalance",
		                	 "render": function (data, type, full, meta) {
					            	if(full.oldBalance != null){
					                return full.oldBalance/100;
					            	}else{
					            	return "";
					            	}
					            }} ,
		                 {"targets": [4],"data": "money",
				                	 "render": function (data, type, full, meta) {
							            	if(full.money != null){
							                return (full.money/100).toFixed(2);
							            	}else{
							            	return "";
							            	}
							            }} ,
		                 {"targets": [5],"data": "newBalance",
						                	 "render": function (data, type, full, meta) {
									            	if(full.newBalance != null){
									                return (full.newBalance/100).toFixed(2);
									            	}else{
									            	return "";
									            	}
									            }} ,
		                 {"targets": [6],"data": "recvOperId"},
		                 {"targets": [7],"data": "updateTime",
		                	 "render": function (data, type, full, meta) {
					            	if(full.updateTime != null){
					            		var updateTime = new Date(full.updateTime).format("yyyy-MM-dd hh:mm:ss"); 
					                return updateTime;
					            	}else{
					            	return "";
					            	}
					            }}]
		var payLogColumn = [{"targets": [0],"data": "chargeId"} , 
			                 {"targets": [1],"data": "channelCustId"} ,
			                 {"targets": [2],"data": null,"mRender": function(data,type,full){
									var status = full.payFeeModeCode;
		  							if(status=="1"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='cash' />";
		  							}
		  						}	
			                 } ,
			                 {"targets": [3],"data": null,"mRender": function(data,type,full){
									var status = full.paymentOp;
		  							if(status=="10000"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='Payment' />";
		  							}else if(status=="10001"){
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='return.premium' />";
		  							}else if(status=="10002") {
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='settlement.of.cash.refunds' />";
		  							}else if(status=="10003") {
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='buyback' />";
		  							}else if(status=="10004") {
		  								return "<fmt:message bundle='${pageScope.bundle}'  key='fee.deduction' />";
		  							}
		  						}	
			                 } ,
// 			                 {"targets": [4],"data": "tradeId"} ,
// 			                 {"targets": [4],"data": "payTag"} ,
			                 {"targets": [4],"data": "recvFee",
			                	 "render": function (data, type, full, meta) {
						            	if(full.recvFee != null){
						                return (full.recvFee/100).toFixed(2);
						            	}else{
						            	return "";
						            	}
						            }},
						     {"targets": [5],"data": "recvOperId"},
			                 {"targets": [6],"data": "recvTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.recvTime != null){
						            		var recvTime = new Date(full.recvTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return recvTime;
						            	}else{
						            	return "";
						            	}
						            }},
						      {"targets": [7],"data": "goodsId"},
						      {"targets": [8],"data": "goodsName"}
			                 ]
		var laoBillColumn = [{"targets": [0],"data": "balanceId"} , 
			                 {"targets": [1],"data": "channelCustId"} ,
			                 {"targets": [2],"data": "cycleId"} ,
			                 {"targets": [3],"data": "backFee",
			                	 "render": function (data, type, full, meta) {
						            	if(full.backFee != null){
						                return (full.backFee/100).toFixed(2);
						            	}else{
						            	return "";
						            	}
						            }} ,
			                 {"targets": [4],"data": "balanceTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.balanceTime != null){
						            		var balanceTime = new Date(full.balanceTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return balanceTime;
						            	}else{
						            	return "";
						            	}
						            }} ,
// 				            {"targets": [5],"data": null,"mRender": function(data,type,full){
// 								var status = full.cashbackTag;
// 	  							if(status=="1"){
// 	  								return "已返现";
// 	  							}else if(status=="0"){
// 	  								return "未返现";
// 	  							}
// 		  						}	
// 			                 } ,
			                 {"targets": [5],"data": "cashTime",
			                	 "render": function (data, type, full, meta) {
						            	if(full.cashTime != null){
						            		var cashTime = new Date(full.cashTime).format("yyyy-MM-dd hh:mm:ss"); 
						                return cashTime;
						            	}else{
						            	return "";
						            	}
						            }},
			                 {"targets": [6],"data": "cashChargeId"},
			                 {"targets": [7],"data": "recvOperId"},
			                 {"targets": [8],"data": "ruleId"}]
		var custId = $("#custId").val();
		listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=0",'#accessLogGrid',accColumn);
		listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=1",'#payLogGrid',payLogColumn);
		listGrid("<%=basePath %>remain/queryGrid?cycId="+cycId+"&type=2",'#laoBillResultGrid',laoBillColumn);
	}
	function listGrid(url,tableId,columns){
		$(tableId).dataTable().fnDestroy();
		var dataTableObj = {
				"bAutoWidth":true,
				"bProcessing": true,
				"sPaginationType" : "bootstrap",
				"sServerMethod":"post",
			    "bServerSide": true,
				"sAjaxSource" : url,    /* 跳转url */
				"iDisplayLength": 10,  /* 展示条数 */
		 		"columnDefs": columns,
				  "bScrollCollapse": true,
				  "bPaginate": true,
				  "bLengthChange": false,
				  "bFilter": false,
				  "bSort": false,
				  "bInfo": true,
				  "bStateSave": false, 
				  "sPaginationType": "full_numbers",
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
			    "aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单
			}
		$(tableId).dataTable(dataTableObj);
	}
	function del(custId){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Are.you.sure.you.want.to.delete.this.rule' />?")){
			$.ajax({
		        type:"POST",
		        url:"${ctx}/remain/delBalRules/"+custId,
				cache : false,
				dataType:"json",
				success : function(data) {
					if(data !=-1){
						alert("<fmt:message bundle='${pageScope.bundle}'  key='balance.warning.rule.delete.successfully' />！");
					}else{
						alert("<fmt:message bundle='${pageScope.bundle}'  key='balance.warning.rule.delete.failed' />！");
					}
				}
			});
		}
		window.location.reload();
		return;
	}
	
	//更新
	function toUpdate(custId){
		url='${ctx}/remain/updBalRules/'+custId;
		layer_show('<fmt:message bundle="${pageScope.bundle}"  key="edit.warning.rules" />',url,'800','550');
		//window.location.reload();		  
		return;
	}
	
	function balRuleInfo(){
		var custId= $("#custId").val();
		$.ajax({
	        type:"POST",
	        url:"${ctx}/remain/getBalRules/"+custId,
			cache : false,
			async: false, 
			dataType:"json",
			success : function(data) {
				if(data.ruleName !=null){					
			        var row = $("#template").clone();
			        row.find("#ruleName").text(data.ruleName);
			        row.find("#balanceInfo").text(data.balFee);
			        row.find("#emailInfo").text(data.emaileId);
			        row.find("#telInfo").text(data.telphoneId);
			        row.find("#proInfo").text(data.cal);
			        row.find("#operInfo").html('<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Delete" />\" href="javaScript:del('+custId+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle="${pageScope.bundle}"  key="Delete" /></i></a>'
							+ '<a title=\"<fmt:message bundle="${pageScope.bundle}"  key="Edit" />\" href="javaScript:toUpdate('+custId+');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle="${pageScope.bundle}"  key="Edit" /></a> ');
			        row.appendTo("#balRuleGrid");//添加到模板的容器中
				}								
			}
		});
		
	}
	$(document).ready(function(){
			 var custId = $("#custId").val();
		     var date=new Date;
			 var year=date.getFullYear(); 
			 var month=date.getMonth()+1;
			 month =(month<10 ? "0"+month:month); 
			 var mydate = (year.toString()+"/"+month.toString());
			 	$("#curDate").html(mydate);
			 	$.ajax({
					url:"/remain/getGroupInfo",
					data:{"custId":custId},
					success:function(result){
						//客户
						$("#custName").html(result[0].custName);
						$("#custState").html(result[0].custState);
						$("#psptTypeCode").html(result[0].psptTypeCode);
						$("#psptId").html(result[0].psptId);
						//账户
						$("#depositMoney").html(((result[0].depositMoney==null?0:result[0].depositMoney)/100).toFixed(2));
						//结算
						$("#userPaid").html(((result[0].userPaid==null?0:result[0].userPaid)/100).toFixed(2));
						$("#comNoPay").html(((result[0].comNoPay==null?0:result[0].comNoPay)/100).toFixed(2));
						$("#comPaid").html(((result[0].comPaid==null?0:result[0].comPaid)/100).toFixed(2));
						$("#count").html(result[0].count==null?0:result[0].count);
						$("#needCount").html(((result[0].needCount==null?0:result[0].needCount)/100).toFixed(2));
						$("#returned").html(((result[0].returned==null?0:result[0].returned)/100).toFixed(2));
				}
			});
			balRuleInfo();
		  });
	 </script>
<body>
<input id="custId" type="hidden" value="${custId}" />
<div id="customer"  class="seconSec">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='customer.information' /></h1>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<label for="name" ><fmt:message bundle='${pageScope.bundle}'  key='Customer.name' />:</label>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div  id="custName"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='Customer.state' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="custState"></div>
				</div>
				</div>
			</div>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='Document.type' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="psptTypeCode"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='Document.number' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="psptId"></div>
				</div>
				</div>
			</div>
			</div>
			
			<div id="account"  class="seconSec">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='account.information' /></h1>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='current.balance' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="depositMoney"></div>
				</div>
				</div>
			</div>
			</div>
			<div id="balRule"  class="seconSec">
      		<h1><fmt:message bundle='${pageScope.bundle}'  key='the.balance.warning.rule' /></h1>
      		<div class="mt-20">
      		<table id="balRuleGrid" class="table table-border table-bordered table-hover table-bg table-sort">
        	<thead  class="zpTable">
          	<tr>
           	<th><fmt:message bundle='${pageScope.bundle}'  key='rule.name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Balance.threshold' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Mail.recipient' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='telephone.receiver' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='priority' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Operation' /></th>
          	</tr>
        	</thead>
        	<tbody>
             <tr id="template">
             <td id="ruleName"></td>
             <td id="balanceInfo"></td>
             <td id="emailInfo"></td>
             <td id="telInfo"></td>
             <td id="proInfo"></td>
             <td id="operInfo"></td>
             </tr>
            </tbody>      	
      		</table>    		
     	 	</div>
      		</div>
      		<div class="seconSec">
      		<a href="javascript:;"
				onclick="add_rule()"
				class="btn btn-primary radius" style="margin:15px 10px;"> <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='add.the.balance.warning.rule' />
			</a>
			</div>
			<div id="settle" class="seconSec">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='billing.information' /></h1>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='the.current.payment.days' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="curDate"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='Number.of.users' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="count"></div>
				</div>
				</div>
			</div>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='The.user.has.paid.the.fee' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="userPaid"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='The.enterprise.has.paid.for.the.use' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="comPaid"></div>
				</div>
				</div>
			</div>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='The.enterprise.does.not.expend.the.use' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="comNoPay"></div>
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='The.settlement.shall.be.returned.to.the.total.amount' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="needCount"></div>
				</div>
				</div>
			</div>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='cash.back' />:
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<div id="returned"></div>
				</div>
				</div>
			</div>
			</div>
			
			<div  class="seconSec" >
			<h1><fmt:message bundle='${pageScope.bundle}'  key='payment.days.running.water.query' /></h1>
			<div class="oh" >
				<div class="col-12">
				<div class="col-md-3 col-lg-3 mt20">
					<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />：
				</div>
				<div class="col-md-3 col-lg-3 mt20">
					<input id="cycId" class="input-text" placeholder="<fmt:message bundle='${pageScope.bundle}'  key='Please.select.the.Account.Period' />" style="width:80px" type="text" name="cycleId" onClick="WdatePicker({onpicked:function(dq){picked();},dateFmt:'yyyy/MM',minDate:'1970-01',maxDate:'#now'})"  readonly="readonly"/>  
				</div>
				</div>
			</div>
			</div>
			
			<div id="ac" class="seconSec" >
			<h1><fmt:message bundle='${pageScope.bundle}'  key='deposit.and.withdrawal.log' /></h1>
			<div class="mt-20" >
			<table id="accessLogGrid"  class="table table-border table-bordered table-hover table-bg table-sort">
			<thead class="zpTable">
				<tr>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Transaction.details.history.list' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Log.details.history.list' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Book.mark' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Original.balance.(yuan)' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='amount.of.money' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Current.balance.(yuan)' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Operating.time' /></th>
				</tr>
			</thead>
		</table>
			</div>
			</div>
			
			<div id="pl" class="seconSec" style="margin-top:50px">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='business.expense.log' /></h1>
			<div class="mt-20">
			<table id="payLogGrid"  class="table table-border table-bordered table-hover table-bg table-sort">
			<thead class="zpTable">
				<tr>
					<th><fmt:message bundle='${pageScope.bundle}'  key='line-of-business' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Channel.customer' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Payment.Method' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Stored.value.operation' /></th>					
<!-- 					<th>关联流水<fmt:message bundle='${pageScope.bundle}'  key='' /></th> -->
<!-- 					<th>缴费标识<fmt:message bundle='${pageScope.bundle}'  key='' /></th> -->
					<th><fmt:message bundle='${pageScope.bundle}'  key='Payment.amount.(yuan)' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Transaction.time' /></th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='commodity' />ID</th>
					<th><fmt:message bundle='${pageScope.bundle}'  key='Commodity.name' /></th>
				</tr>
			</thead>
			</table>
			</div>
			</div>
			
			<div id="settle" class="seconSec" style="margin-top:50px">
			<h1><fmt:message bundle='${pageScope.bundle}'  key='Settlement.statements' /></h1>
			<div class="mt-20">
			<table id="laoBillResultGrid"  class="table table-border table-bordered table-hover table-bg table-sort">
			<thead class="zpTable">
				<tr>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Settlement.statements' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Channel.customer' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='account.period' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Amount.of.cash.refunds(RMB)' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Settlement.time' /></th>
<!-- 					<th ><fmt:message bundle='${pageScope.bundle}'  key='mark.of.cash.refunds' /></th> -->
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Date.of.cash.refunds' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Statements.of.cash.refunds' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Agent' /></th>
					<th ><fmt:message bundle='${pageScope.bundle}'  key='Rule' /> ID</th>
				</tr>
			</thead>
			</table>
			</div>
			</div>
</body>
</html>