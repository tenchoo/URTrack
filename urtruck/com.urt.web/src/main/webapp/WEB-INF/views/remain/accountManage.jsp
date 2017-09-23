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
<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">

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
<style type="text/css">
</style>
  <script type="text/javascript">
  function clears(){
    $("#custId").empty();
    window.location.reload();
  }

  function add_rule(){	 
	  var custId = $("#custId").val();
	  url='${ctx}/remain/createRule/'+custId;
		layer_show('<fmt:message bundle='${pageScope.bundle}'  key='Add.Balance.Alarm' />',url,'800','550');
	}

  function srh(){
      var custIdVal = $("#custId").val();
      if(custIdVal == null){
        $("#loading").html("<font color='red'><fmt:message bundle='${pageScope.bundle}'  key='Please.Select.A.Customer' />...</font>");
        return;
      }else{
        $("#loading").empty();
      }
      console.log(custIdVal);
      $.ajax({
        url:"/remain/getGroupInfo",
        data:{"custId":custIdVal},
        async: false, 
        beforeSend:function(XMLHttpRequest){
                  $("#loading").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.Wait.For.A.While' />...");
             },
        success:function(result){
          $("#loading").empty();
          $("#svArea").hide();
          clearAcc();
          clearCust();
          //客户
          $("#custName").html(result[0].custName);
          $("#custState").html(result[0].custState);
          $("#psptTypeCode").html(result[0].psptTypeCode);
          $("#psptId").html(result[0].psptId);
          $("#customer").show();
          //账户
         // $("#cashTag").html(result[0].cashTag);
          $("#cashTag").html(result[0].cashTag+"<button style='margin-left:10px;margin-bottom:1px' onclick='editAcct()' type='button' class='btn btn-primary radius' ><fmt:message bundle='${pageScope.bundle}'  key='Edit' /></button>");
          $("#depositMoney").html((result[0].depositMoney/100).toFixed(2)+"<button style='margin-left:10px;margin-bottom:1px' onclick='pay()' type='button' class='btn btn-primary radius' ><fmt:message bundle='${pageScope.bundle}'  key='Recharge' /></button>");
          $("#startCycId").html(result[0].startCycId);
          $("#endCycId").html(result[0].endCycId);
          //结算
          $("#userPaid").html((result[0].userPaid/100).toFixed(2));
          $("#comNoPay").html((result[0].comNoPay/100).toFixed(2));
          $("#comPaid").html((result[0].comPaid/100).toFixed(2));
          $("#count").html(result[0].count);
          $("#needCount").html((result[0].needCount/100).toFixed(2));
          $("#returned").html((result[0].returned/100).toFixed(2));
          if(result[0].account == true){
            listRule("<%=basePath %>remain/queryList?custId="+custIdVal,'#remainGrid');
            $("#addAccount").hide();
            $("#account").show();
            $("#ruleDetail").show();
            $("#settle").show();
            $("#case2").hide();
          }else if(result[0].account == false){
            $("#addAccount").show();
            $("#account").hide();
            $("#ruleDetail").hide();
            $("#settle").hide();
          }
      }
    });
      balRuleInfo(); 
  }

  function clearCust(){
    //客户
    $("#custName").html("");
    $("#custState").html("");
    $("#psptTypeCode").html("");
    $("#psptId").html("");
  }
  function clearAcc(){
    //账户
    $("#cashTag").val("");
    $("#depositMoney").html("");
    $("#startCycId").html("");
    $("#endCycId").html("");
    //结算
    $("#userPaid").html("");
    $("#comNoPay").html("");
    $("#comPaid").html("");
    $("#count").html("");
    $("#needCount").html("");
    $("#returned").html("");
  }
  function pay(){
	$("#edit2").hide();	  
	$("#editRule").hide();
	$("#payment").show(); 
  }

  function subPay(){
    // 注意：故意限制了 0321 这种格式，如不需要，直接reg=/^\d+$/;
    var reg = /^\d+(\.\d+)?$/; //可以输出小数点e
    

    var custId = $("#custId").val();
    var payment = $("#paymentText").val();
    if (reg.test(payment) == false) {
      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.Numbers.In.The.Correct.Format' />！");
      return false;
    }
    payment = payment * 100;//转换成分为单位
    var cashTag = $("#cashTag").text();
    if(cashTag == "<fmt:message bundle='${pageScope.bundle}'  key='Not.Use.Settlement.Cash.Returning' />"){
      cashTag = 0;
    }else if(cashTag == "<fmt:message bundle='${pageScope.bundle}'  key='Use.Settlement.Cash.Returning.In.Real.Time' />"){
      cashTag = 1;
    }else{
      cashTag = 2;
    }
    var dataArr = [];
    dataArr.push(custId);
    dataArr.push(payment);
    dataArr.push(cashTag);
    var str = dataArr.join(",");

    $.ajax({
      url:"/remain/subPay",
      data:{"payData":str},
      beforeSend:function(XMLHttpRequest){
                //alert('远程调用开始...');
                $("#loading2").html("<fmt:message bundle='${pageScope.bundle}'  key='Please.Wait.For.A.While' />...");
           },
      success:function(result){
        $("#loading2").empty();
        if(result.result == "success"){
          $("#payment").hide();
          $("#paymentText").val();
          alert("<fmt:message bundle='${pageScope.bundle}'  key='Recharge.Successfully' />");
          window.location.reload();
        }else{
          alert("<fmt:message bundle='${pageScope.bundle}'  key='sys.error' />");
        }
      }
    });

  }

  function sv(){
    var reg = /^\d+(\.\d+)?$/; //可以输出小数点
    var case2depositMoney = $("#case2depositMoney").val();
    if (reg.test(case2depositMoney) == false) {
      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.The.Correct.Format.Of.Figures.In.Account.Amount' />");
      return false;
    }
    var case2invoinceFee = $("#case2invoinceFee").val();
    if (reg.test(case2invoinceFee) == false) {
      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.The.Correct.Format.Of.Figures.In.Invoice.Amount' />");
      return false;
    }
    var case2printFee = $("#case2printFee").val();
    if (reg.test(case2printFee) == false) {
      layer.msg("<fmt:message bundle='${pageScope.bundle}'  key='Please.Input.The.Correct.Format.Of.Figures.In.Invoice.Printed' />");
      return false;
    }
    var custId = $("#custId").val();
//    var pay = $("#case2pay").val();
    var returnType = $("#case2returnType").val();
    var returnRule = $("#case2returnRule").val();
/*     if(returnRule == -1){
      returnRule = 10000001;
    } */
    var depositMoney = $("#case2depositMoney").val()*100;
    var invoinceFee = $("#case2invoinceFee").val()*100;
    var printFee = $("#case2printFee").val()*100;
    var dataArr = [];
    dataArr.push(custId);
//    dataArr.push(pay);
    dataArr.push(returnType);
    dataArr.push(returnRule);
    dataArr.push(depositMoney);
    dataArr.push(invoinceFee);
    dataArr.push(printFee);
    var str = dataArr.join(",");
    $.ajax({
      url:"/remain/saveRules",
      data:{"accData":str},
      success:function(result){
        alert("<fmt:message bundle='${pageScope.bundle}'  key='Add.Successfully' />");
        if(result.result == "success"){
           $("#case2pay").val("");
           $("#case2returnType").val("");
           $("#case2returnRule").val("");
           $("#case2depositMoney").val("");
           $("#case2invoinceFee").val("");
           $("#case2printFee").val("");
           window.location.reload();
        }else{
        }
      }
    });
  }
  
  function comEditRule(){
	  var custId = $("#custId").val();
	  var returnType = $("#case3edit").val()-1;
	  var returnRule = $("#editCaseSelect").val();
	  var dataArr = [];
	    dataArr.push(custId);
	    dataArr.push(returnType);
	    dataArr.push(returnRule);	   
	    var str = dataArr.join(",");
	    $.ajax({
	      url:"/remain/editRules",
	      data:{"accData":str},
	      success:function(result){
	        if(result.result == "success"){
	           $("#case2pay").val("");
	           $("#case2returnType").val("");
	           $("#case2returnRule").val("");	
	           alert("<fmt:message bundle='${pageScope.bundle}'  key='Edit.Settlement.Successfully' />！");
	        }else{
	        	 alert("<fmt:message bundle='${pageScope.bundle}'  key='Faied.To.Add' />");
	        } 
	      }
	    });  
	  window.location.reload();
  }
  function editAcct(){  
	    $("#paymentType").hide();
	    $("#cashpay").hide();	    
	    $("#editAccount").hide();
	    $("#editCaseSelect").html("");
	    $("#editCaseSelect").unbind("change");
	    $("#case2rule").hide();
        $('#editRuleList').hide();
        $('#editSelList').hide();
	    $.ajax({
	      url:"/remain/getRules",
	      data:{},
	      success:function(result){
	        var content = "";
	        for(var i=0;i<result.length;i++){
	          content += "<option value='"+result[i].id+"'>"+result[i].text+"</option>";
	        }
	        $("#editCaseSelect").append(content);
	      }
	    });
	  
	    $("#case2").hide();
	    $("#editRule").show();	    
	    $("#balanceRule").hide();
	    $("#svArea").hide();
	    $("#edit2").show();	    
	    $("#editCaseSelect").change(function(){
	      listRule("<%=basePath %>remain/addQueryList?custGroupId="+$("#editCaseSelect").val(),'#case2grid');
	      $("#case2rule").show();
	    });
	    $("#case3edit").change(function(){
	      var ruleVal = $('#case3edit').val();
	       if( ruleVal == 2 || ruleVal == 3){
	        console.log(ruleVal);
	        $('#editRuleList').show();
	        $('#editSelList').show();
	        
	      }else{
	        console.log(ruleVal);
	        $('#editRuleList').hide();
	        $('#editSelList').hide();
	        $('#editCaseSelect').val(-1);
	      } 
	    });
	  }
  function addAcct(){
    $("#addAccount").hide();
    $("#case2returnRule").html("");
    $("#case2returnRule").unbind("change");
    $("#case2rule").hide();
    $('#editRuleList').hide();
    $('#editSelList').hide();
    $.ajax({
      url:"/remain/getRules",
      data:{},
      success:function(result){
        var content = "";
        for(var i=0;i<result.length;i++){
          content += "<option value='"+result[i].id+"'>"+result[i].text+"</option>";
        }
        $("#case2returnRule").append(content);
      }
    });
    $("#case2").show();
    $("#balanceRule").hide();
    $("#svArea").show();
    $("#case2returnRule").change(function(){
      listRule("<%=basePath %>remain/addQueryList?custGroupId="+$("#case2returnRule").val(),'#case2grid');
      $("#case2rule").show();
    });
    //1548
    $("#case2returnType").change(function(){
      var ruleVal = $('#case2returnType').val();
      if( ruleVal == 1 || ruleVal == 2){
        console.log(ruleVal);
        $('#balanceRule').show();
      }else{
        console.log(ruleVal);
        $('#balanceRule').hide();
        $('#case2rule').hide();
        $('#case2returnRule').val(-1);
      }
    });
  }
  
  function del(custId){
		if(confirm("<fmt:message bundle='${pageScope.bundle}'  key='Sure.To.Delete.This.Rule?' />")){
			$.ajax({
		        type:"POST",
		        url:"${ctx}/remain/delBalRules/"+custId,
				cache : false,
				dataType:"json",
				success : function(data) {
					if(data !=-1){
						alert("<fmt:message bundle='${pageScope.bundle}'  key='Delete.Balance.Alarm.Rule.Successfully' />");
					}else{
						alert("<fmt:message bundle='${pageScope.bundle}'  key='Fail.To.Delete.Balance.Alarm.Rule' />");
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
		layer_show('<fmt:message bundle='${pageScope.bundle}'  key='Edit.Alarm.Rule' />',url,'800','550');		  
		return;
	}
	
	function balRuleInfo(){
		var custId= $("#custId").val();
		$("#balRule").show();	
		$.ajax({
	        type:"POST",
	        url:"${ctx}/remain/getBalRules/"+custId,
			cache : false,
			//async: false, 
			dataType:"json",
			success : function(data) {
				if(data.ruleName !=null){					
			        var row = $("#template").clone();
			        row.find("#ruleName").text(data.ruleName);
			        row.find("#balanceInfo").text(data.balFee);
			        row.find("#emailInfo").text(data.emaileId);
			        row.find("#telInfo").text(data.telphoneId);
			        row.find("#proInfo").text(data.cal);
			        row.find("#operInfo").html('<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Delete' />\" href="javaScript:del('+custId+');" style=\"text-decoration:none\"><i class=\"Hui-iconfont\"><fmt:message bundle='${pageScope.bundle}'  key='Delete' /></i></a>'
							+ '<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='Edit' />\" href="javaScript:toUpdate('+custId+');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle='${pageScope.bundle}'  key='Edit' /></a> ');
			        row.appendTo("#balRuleGrid");//添加到模板的容器中
				}
			}
		});
	}
  function listRule(url,tableId){
    $(tableId).dataTable().fnDestroy();
    var dataTableObj = {
        "bProcessing": true,
        "sPaginationType" : "bootstrap",
        "sServerMethod":"post",
          "bServerSide": true,
        "sAjaxSource" : url,    /* 跳转url */
        "iDisplayLength": 10,  /* 展示条数 */
             "columnDefs": [
                 {"targets": [0],"data": "rulegroupId"} ,
                 {"targets": [1],"data": "rulegroupName"} ,
                 {"targets": [2],"data": "ruleId"} ,
                 {"targets": [3],"data": "ruleName"} ,
                 {"targets": [4],"data": "condStat"} ,
                 {"targets": [5],"data": "calFormula"} ,
                 {"targets": [6],"data": "priority"}
                 ],

          "sScrollX": "100%",
          "sScrollXInner": "100%",
          "bScrollCollapse": true,
          "bPaginate": true,
          "bLengthChange": false,
          "bFilter": false,
          "bSort": false,
          "bInfo": true,
          "bAutoWidth": true,
          "aaSorting": [[1, "asc"]],
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
  $(document).ready(function(){
     $("#case2").hide();
     $("#svArea").hide();
     $("#balRule").hide();
       var date=new Date;
     var year=date.getFullYear();
     var month;
     if(date.getMonth() == 12){
       month=1;
     }else{
       month = date.getMonth()+1;
     }
     month =(month<10 ? "0"+month:month);
     var mydate = (year.toString()+"/"+month.toString());
     $("#curDate").html(mydate);
     $("#custId").select2({
          placeholder: "<label for='name' class='font12 fl'><fmt:message bundle='${pageScope.bundle}'  key='Please.select.customer' /></label>",
          language: "zh-CN",
          ajax: {
              url: "/remain/getCustomList",
              type: "post",
              dataType: 'json',
              delay: 500,
              data: function (params) {
                  return {
                      q: params.term,
                      page: params.page
                  };
              },
              processResults: function (data, page) {
                  return {
                      results: data
                  };
              },
              cache: true
          },
          escapeMarkup: function (markup) {
              return markup;
          },
          minimumInputLength: 1,  //至少输入多少个字符后才会去调用ajax
          minimumResultsForSearch: 1,
          width: "260px"
      });
  });




   </script>
<body>
<div class="pd-20">
      <div class="oh clearfix">
      <div class="col-12 clearfix">
        <div class="col-md-4 col-lg-4 mt20 clearfix" style="padding-left:40px;">
          <label for="name" class="font12 fl" style="float:left;"><fmt:message bundle='${pageScope.bundle}'  key='Customer.Name' />:</label>
          <div class="tBox">
            <select id="custId" name="custId" class="input-text" style="width:200px;"></select>
          </div>
        </div>
        <div class="col-md-4 col-lg-4 mt20">
          <div style="margin-left:5px" id="loading"></div>
        </div>
        </div>
      </div>
      <div class="mt20 clr" style="text-align: center;">
        <button id="addAccount" style="display:none;"  class="btn btn-primary radius"
          onclick="addAcct();">
          <fmt:message bundle='${pageScope.bundle}'  key='Add.Account.Information' />
        </button>
        <button id="search" type="button" class="btn btn-primary radius"
          onclick="srh();">
          <i class="Hui-iconfont">&#xe665;</i><fmt:message bundle='${pageScope.bundle}'  key='Inquiry' />
        </button>
        <input class="btn btn-default radius" onclick="clears()" type="reset"
          value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='Clear.Out' />&nbsp;&nbsp;">
      </div>
      <div id="customer" style="display:none" class="seconSec clearfix">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Customer.Information' /></h1>
      <div class="oh" >
        <div class="col-12 clearfix">
        <div class="col-md-3 col-lg-3 mt20">
          <label for="name" ><fmt:message bundle='${pageScope.bundle}'  key='Customer.Name' />:</label>
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div  id="custName"></div>
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Customer.Condition' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="custState"></div>
        </div>
        </div>
      </div>
      <div class="oh" >
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Certificate.Type' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="psptTypeCode"></div>
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Certificate.Number' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="psptId"></div>
        </div>
        </div>
      </div>
      </div>

      <div id="account" style="display:none"  class="seconSec clearfix">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='account.information' /></h1>
      <div class="oh" >
        <div class="col-12">
        <div class="col-md-2 col-lg-2 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Settlement.Cash.Returning.Method' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="cashTag"></div>
        </div>
        <div class="col-md-2 col-lg-2 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Balance(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="depositMoney"></div>
        </div>
        </div>
      </div>
      </div>
      <div id="balRule" style="display:none"  class="seconSec">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Balance.Alarm.Rule' /></h1>
      <div class="mt-20">
      <table id="balRuleGrid" class="table table-border table-bordered table-hover table-bg table-sort">
        <thead  class="zpTable">
          <tr>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Balance.Threshold' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Recipient.Mail.Information' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Recipient.Phone.Information' /></th>
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
      <a href="javascript:;"
				onclick="add_rule()"
				class="btn btn-primary radius" style="margin:15px 10px;" > <span class="human"></span>
				<fmt:message bundle='${pageScope.bundle}'  key='Add.Balance.Alarm' />
	  </a>
      </div>
      </div>
      <div id="ruleDetail" style="display:none"  class="seconSec">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Rule.List' /></h1>
      <div class="mt-20">
      <table id="remainGrid" class="table table-border table-bordered table-hover table-bg table-sort">
        <thead  class="zpTable">
          <tr>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Set.Id' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Set.Name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Id' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='rule.name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Condition' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Calculation.Method' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='priority' /></th>
          </tr>
        </thead>
      </table>
      </div>
      </div>

      <div id="settle" style="display:none;margin-top:50px"  class="seconSec clearfix">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Settlement.Information' /></h1>
      <div class="oh clearfix" >
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Current.Account.Period' />:
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
      <div class="oh clearfix" >
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Already.Paided.Fee.By.Customer(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="userPaid"></div>
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Already.Paided.Fee.By.Company(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="comPaid"></div>
        </div>
        </div>
      </div>
      <div class="oh clearfix" >
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Not.Paided.Fee.By.Company(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="comNoPay"></div>
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Settlement.Cash.Returning.Number(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="needCount"></div>
        </div>
        </div>
      </div>
      <div class="oh clearfix" >
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Already.Cash.Returning.Number(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <div id="returned"></div>
        </div>
        </div>
      </div>
      </div>
       </div>
<!-- 	 <div id="paymentType" style="display:none"  class="seconSec clearfix">
      		  <h1>充值信息</h1>
		      <div class="oh" >
			       <div class="col-12">
				        <div class="col-md-3 col-lg-4 mt20">
				                     充值方式:
				        </div>
				         <div class="col-md-3 col-lg-4 mt20">
					       <select id="chargType" name="chargType" readonly="readonly">
					      	  <option value="-1">请选择</option>
					      	  <option value="0">现金充值</option>
							  <option value="1">微信充值</option>
							  <option value="2">支付宝充值</option>
							</select>	
						 </div>
			     	 </div>
		      </div>
      	</div>
       <div id="cashpay" style="display:none;padding-left: 37px;"  class="seconSec clearfix" >  
       		<h1>现金充值</h1>
	      <div class="oh" >
		       <div class="col-12">  
			        <div class="col-md-3 col-lg-4 mt20">
			          充值金额(元):
			        </div>
			        <div class="col-md-3 col-lg-4 mt20">
			          <input type="text" id="paymentText" class="input-text" />
			        </div>
			        <div class="col-md-3 col-lg-4 mt20">
			          <input type="button"  class="btn btn-primary radius" value="提交" onclick="subPay()"/>
			        </div>
		        </div>
	      </div>
      </div> -->
            <div id="payment" style="display:none;padding-left: 37px;"  class="seconSec clearfix">
      			<h1><fmt:message bundle='${pageScope.bundle}'  key='Recharge' /></h1>
			      <div class="oh" >
				        <div class="col-12">
					        <div class="col-md-2  mt20">
					          <fmt:message bundle='${pageScope.bundle}'  key='Recharge.Fee(Yuan)' />:
					        </div>
					        <div class="col-md-1  mt20" style="margin-right:20px;">
					          <input type="text" id="paymentText" class="input-text" />
					        </div>
					        <div class="col-md-1  mt20">
					          <input type="button"  class="btn btn-primary radius" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />" onclick="subPay()"/>
					        </div>
				        </div>
			      </div>
      		</div>
      
      <div id="editRule" style="display:none;padding-left: 37px;"  class="seconSec clearfix">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Edit.Settlement.Rule' /></h1>
      <div class="oh" >
        <div class="col-12">  
        <div class="col-md-2 col-lg-2 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Settlement.Cash.Returning.Method' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <select id="case3edit" class="input-text"><option value ="0"><fmt:message bundle='${pageScope.bundle}'  key='Please.Select' /></option><option value ="1"><fmt:message bundle='${pageScope.bundle}'  key='Not.Use.Settlement.Cash.Returning' /></option><option value ="2"><fmt:message bundle='${pageScope.bundle}'  key='Use.Settlement.Cash.Returning.In.Real.Time' /></option><option value ="3"><fmt:message bundle='${pageScope.bundle}'  key='Use.Settlement.Cash.Returning.By.Manual.Work' /></option></select>
        </div>
        <div class="col-md-2 col-lg-2 mt20" style="display:none;margin-left:76px;" id="editRuleList">
                       <fmt:message bundle='${pageScope.bundle}'  key='Settlement.Rule' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20" style="display:none;" id="editSelList">
          <select id="editCaseSelect" name="editCaseSelect"  class="input-text"></select>
        </div>
        </div>
      </div>      
      </div>  

      <div id="case2" style="display:none;width:88%"  class="seconSec pd-20 clearfix">
      <h1 style="margin-bottom:10px;"><fmt:message bundle='${pageScope.bundle}'  key='Add.Account.Information' /></h1>
      <div class="oh" >
        <div class="col-12" style="height:40px;">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Account.Amount(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <input type="text" id=case2depositMoney class="input-text" value="0"></input>
        </div>
        <div class="col-md-3 col-lg-3 mt20" style="padding-left:20px;">
          <fmt:message bundle='${pageScope.bundle}'  key='Invoice.Account.Amount(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <input type="text" id="case2invoinceFee" class="input-text" value="0"></input>
        </div>
        </div>
      </div>
      <div class="oh" >
         <div class="col-12" style="height:40px;">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Printed.Invoice.Amount(Yuan)' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <input type="text" id="case2printFee" class="input-text" value="0"></input>
        </div>
        <div class="col-md-3 col-lg-3 mt20" style="padding-left:20px;">
          <fmt:message bundle='${pageScope.bundle}'  key='Settlement.Cash.Returning.Method' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <select id="case2returnType" class="input-text"><option value ="0"><fmt:message bundle='${pageScope.bundle}'  key='Not.Use.Settlement.Cash.Returning' /></option><option value ="1"><fmt:message bundle='${pageScope.bundle}'  key='Use.Settlement.Cash.Returning.In.Real.Time' /></option><option value ="2"><fmt:message bundle='${pageScope.bundle}'  key='Use.Settlement.Cash.Returning.By.Manual.Work' /></option></select>
        </div>
        </div>
      </div>
      <div class="oh"  id="balanceRule">
        <div class="col-12">
        <div class="col-md-3 col-lg-3 mt20">
          <fmt:message bundle='${pageScope.bundle}'  key='Operation' />:
        </div>
        <div class="col-md-3 col-lg-3 mt20">
          <select id="case2returnRule" name="case2returnRule"  class="input-text"></select>
        </div>
        </div>
      </div>
      </div>

      <div id="case2rule" style="display:none;padding-left: 37px;"  class="seconSec clearfix">
      <h1><fmt:message bundle='${pageScope.bundle}'  key='Settlement.Rule' /></h1>
      <div class="mt-20">
      <table id="case2grid" class="table table-border table-bordered table-hover table-bg table-sort">
        <thead  class="zpTable">
          <tr>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Set.Id' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Set.Name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Id' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Name' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Rule.Condition' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='Calculation.Method' /></th>
            <th><fmt:message bundle='${pageScope.bundle}'  key='priority' /></th>
          </tr>
        </thead>
      </table>
      </div>
      </div>

      <div class="mt20 clr" style="text-align: center;display:none" id="svArea">
        <input type="button" id="saveBtn"   class="btn btn-primary radius" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"
          onclick="sv();">
      </div>
      
       <div class="mt20 clr" style="text-align: center;display:none" id="edit2">
        <input type="button" id="edit2Btn"   class="btn btn-primary radius" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"
          onclick="comEditRule();">
        </div>
</body>
</html>
