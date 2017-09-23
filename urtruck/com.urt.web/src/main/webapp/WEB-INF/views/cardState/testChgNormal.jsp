<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title>上传卡信息</title>
	<base href="<%=basePath%>" />
	<link href="${ctx}/static/select2-4.0.3/dist/css/select2.css" rel="stylesheet" type="text/css"/>
	
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
	
	
	<!-- css -->
	<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
	<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
	<script type="text/javascript" src="static/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="static/lib/layer/1.9.3/layer.js"></script> 
	<script type="text/javascript" src="static/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.js"></script> 
	<script type="text/javascript" src="static/js/H-ui.admin.js"></script>
	<script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="static/js/dateformat.js"></script> 
	<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
	<script type="text/javascript" src="${ctx}/static/select2-4.0.3/dist/js/select2.full.js"></script>
	 
	<script type="text/javascript">
	var chCustId='';
	var oldCustId='';
	var value1='';
	var value2='';
	$.ajax({
		url:"person/getChannelCust",
		data:{},
		success:function(result){
			var select=$("#custId").select2({
				width : 200,  
				placeholder: '客户',
				tags: "true",
				allowClear: true,
				data:result
			});
 		  $("#custId").change(function() {
				chCustId=$("#custId").val();
				$('#type').empty();
				$('#version').empty();
				$('#initproduct').empty();
			/* 	$.ajax({
					type : "post",
					url : "${ctx}/ss/getAttrs",
					data: {"custId":$("#custId").val()},
					success:function(result){
						console.log(result);
						$("#attribute1").val(result.attrV1);
						$("#attribute2").val(result.attrV2);
						alert("attribute1:"+result.attrV1+" attribute2:"+result.attrV2);
					}
				}) */;  
				/* if(){
					alert("请选择二级分类!");
				} */
				$.ajax({
					url:"${ctx}/ss/getTypeList",
					data:{"custId":$("#custId").val()},
					success:function(result){
						var select=$("#type").select2({
							width : 200,  
							placeholder: '类型',
							tags: "true",
							allowClear: true,
							data:result
						});
						 $("#type").change(function() {
							$('#version').empty();
							var pid="";
							$.ajax({
								url:"${ctx}/ss/getPidByValue",
								data:{"id":$("#type").val(),"custId":$("#custId").val()},
								async:false,
								success:function(result){
									pid=result;
								}
							});
							$.ajax({
								url:"${ctx}/ss/getVersionList",
								data:{"pid":pid,"custId":$("#custId").val()},
								success:function(result){
									var select=$("#version").select2({
										width : 200,  
										placeholder: '版本',
										tags: "true",
										allowClear: true,
										data:result
									});									
								}
							});
						});
					}
				});
			});
		}
	});
	function cmcc(){
		 window.location.href = "${ctx}/download/cmcc.xlsx";
	}
	//验证上传文件
	function validateFile(){
		var obj=document.getElementById('inputfile'); 
		if(obj.value=='') 
		{ 
			layer.msg("请上传Excel文件！");
			return false; 
		} 
		var stuff=obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; 
		if(stuff!='xls' && stuff!='xlsx' ) 
		{ 
			layer.msg("请选择正确Excel文件格式");
			return false; 
		} 
		return true;
	}
	 function add_cust(id){
		 layer_show('添加客户','${ctx}/person/person/createCustomer','800','550');
     }
    
	//状态变更的方法
	 function singleStateChg(){
		if(chCustId==""){
			alert("请选择客户！");
			return ;
		}
		
		if($("#version").val()==""){
			alert("二级分类不能为空！");
			return ;
		}
		if($("#iccId").val()=="" && $("#msisdn").val()==""){
			alert("单卡变更iccid与msisdn不能同时为空！");
		}else{
			  $.ajax({
					type : "POST",
					url : "${ctx}/cardState/singleStateChg",
					data : {
						"iccId":$("#iccId").val(),"msisdn":$("#msisdn").val(),"psptId":"123","psptTypeCode":"1","chCustId":chCustId
					},
					dataType : "json",
					cache : false,
					success : function(data) {						
							 if(data==0){ 
								alert("激活成功!");		
							}else if(data == -2){
								alert("该卡号已激活或未进行卡号信息同步！");
							}else{
								alert("激活失败！");
							}	 							
					},
					 error : function(error) {
						alert("激活失败！");
					} 
				}); 
		}	
	}
		//状态变更的方法
	 function multiStateChg(){
   		 var option = {
		    url : "<%=basePath %>cardState/multiStateChg",
		    type: "post",
		    success : function(data){
		    	if(data==0){
					alert("激活成功!");		
				}else{
					alert("激活失败！");
				}		
		   }, 
		   error:function(){
			   alert("激活失败！");
			}
	 	};
		$("#cform").attr("enctype","multipart/form-data");
		if(validateFile())
		if(window.confirm("您确定要导入？")){
			$("#cform").ajaxSubmit(option);
			}
		}
		
		//查询测试期卡
	    function sreach() {
	      if(window.document.getElementById("type").value=="" || window.document.getElementById("version").value==""){
				alert("请选择分类！");
				return ;
			} 
	      if(window.document.getElementById("type").value=="" || window.document.getElementById("version").value==""){
				alert("请选择分类！");
				return ;
			} 
/* 	      if(oldCustId==chCustId && value1==window.document.getElementById("type").value && 
	    		  value2 == window.document.getElementById("version").value ){
	    	    alert("查询的条件与上一次一致，直接返回！");
				return ;
	      }
	      value1 = window.document.getElementById("type").value;
	      value2 = window.document.getElementById("version").value;
	      oldCustId = window.document.getElementById("custId").value */
			var url ="<%=basePath%>person/cardList?chCustId="+chCustId+"";
			 var dataTableObj = {
				        "bProcessing": true,
				        "sPaginationType" : "bootstrap",
				        "sServerMethod":"post",
				        "bServerSide": true,
				        "sAjaxSource" : url, /* 跳转url */
				        "iDisplayLength" : 10, /* 展示条数 */
				        "columnDefs" : [
				                {
				                  "targets" : [ 0 ],
				                  "data" : null,
				                  "mRender" : function(data, type, full) {
				                      	return full.channelCustId;
				                    }
				                  
				                },
				                {
				                    "targets" : [ 1 ],
				                    "data" : null,
				                    "mRender" : function(data, type, full) {	                        
				                        return full.iccid;
				                    }
				                },
				                {
				                    "targets" : [ 2 ],
				                    "data" : null,
				                    "mRender" : function(data, type, full) {				             
				                        	 return full.msisdn;
				                    }
				                },
				                {
				                    "targets" : [ 3 ],
				                    "data" : null,
				                    "mRender" : function(data, type, full) {
				                    	return '<a title=\"添加个人客户\"  style=\"text-decoration:none\" onclick=\"add_cust('
		                                + data.custId
		                                + ');\">添加个人客户</a>' 
				                    }
				                }

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
				            "sLengthMenu" : "每页显示 _MENU_ 条记录",
				            "sZeroRecords" : "对不起，查询不到任何相关数据",
				            "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				            "sInfoEmpty": "当前显示 0 到 0 条，共 0  条记录",
				            "sInfoFiltered" : "",
				            "sProcessing" : "正在加载中...",
				            "sSearch" : "搜索",
				            "oPaginate" : {
				                "sFirst" : "第一页",
				                "sPrevious" : " 上一页 ",
				                "sNext" : " 下一页 ",
				                "sLast" : " 最后一页 "
				            }
				        },
				        "aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
				                [ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
				    //设置每页显示记录的下拉菜单
				    }
			 $('#example').dataTable().fnDestroy();
		     $('#example').dataTable(dataTableObj); 			  				
	    }
	    
	</script>
</head>
<body>
	<div class="pd-20 font12">
	<!-- <form role="form" action="/cardState/batchImport2" method="post"  id="cform"> -->
	<form role="form" method="post"  id="cform" enctype="multipart/form-data"> 
	   <div class="seconSec ">
			<h1 align="left">测试期查询</h1>
			<br>
			<br>
		</div>
	   <div class="oh">
	       <div class="col-12">
				<label class="kehu"><span class="colorRed smallStar">*</span>部门: </label><select id="custId" name="custid" class="input-text" style="width:200px;"></select>
				<label id="lable1"><span class="colorRed smallStar">*</span>一级分类：</label><select id="type" name="type" class="input-text" style="width:200px;"></select>
				<label id="lable2">二级分类：</label><select id="version" name="version" class="input-text" style="width:200px;" onkeyup=""></select>			
				<button id="search" type="button" class="btn btn-primary radius"
                    onclick="sreach();">
                    <i class="Hui-iconfont">&#xe665;</i>搜索
                </button>
			</div>
		</div>	
		<div class="mt-20">
            <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
                <thead>
                    <tr class="zpTable">
                        <th>部门编码</th>
                        <th>ICCID</th>
                        <th>MSISDN</th>
                        <th>操作</th>
                    </tr>
                </thead>
            </table>
            <br>
            <br>
        </div>
	    <div class="seconSec clearfix">
        <h1>单卡激活</h1>       
		<div class="oh">
			<div class="col-12">												
				<div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name">ICCID: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">
						<input type="text" class="input-text" id="iccId"
							name="iccId" >
					</div>				
					<div class="col-md-3 col-lg-3 mt20"
						style="width: 25%;padding-left: 150px;">
						<label for="name">MSISDN: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin-left: 0px;">
						<input type="text" class="input-text" id="msisdn" name="msisdn" >
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 15%; margin-left: 0px;">
					<!-- <button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;" onclick="singleStateChg();"><i class="Hui-iconfont">&#xe665;</i>单卡激活</button> -->
					<input class="btn btn-primary radius" type="button" style="float:right;margin-left:10px;"
								value="&nbsp;&nbsp;单卡激活&nbsp;&nbsp;" onclick="singleStateChg();">
					</div>
			</div>
			
			<div class="col-12">												
				<div class="col-md-3 col-lg-3 mt20" style="width: 10%;display: none">
						<label for="name">证件类别： </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;display: none">
						<select id="psptTypeCode" name="psptTypeCode" class="input-text" style="width:200px;">
						<option value="1">身份证</option>	
						<option value="2">户口簿</option>
						<option value="3">军人身份证</option>
						<option value="4">武装警察身份证</option>
						<option value="5">港澳居民往来内地通行证</option>
						<option value="6">台湾居民来往大陆通行证</option>
						<option value="7">护照</option>							
					</select>
					</div>				
					<div class="col-md-3 col-lg-3 mt20"
						style="width: 25%;padding-left: 150px;display: none">
						<label class="form-label col-3">ID：</label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin-left: 0px;display: none">
						<input type="text" class="input-text" id="psptId" name="psptId">
					</div>
			</div>								
		</div>
		</div>
		<div class="seconSec clearfix">
        <h1>批量激活</h1>      
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<label>上传方式：</label> 
			<input name="Fruit" type="radio" value=""  onclick="$('#selectHander').hide(),$('#selectExcel').show();"/>Excel文件上传 
		</div>
		
		<div class="cl pd-5 mt-10" id="selectExcel" style="display:none">		  
			<label for="name" style="float:left;width:100px;">导入Excel文件：</label>
	      	<input type="file" class="" style="width:200px;padding-top: 7px;float:left;" id="inputfile" name="file">	     
	<!-- 		<button id="search" type="button" class="btn btn-primary radius" 
					onclick="cmcc();">
					<i class="Hui-iconfont">&#xe665;</i>模板 下载
			</button> -->	
			 <input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;模板下载&nbsp;&nbsp;" onclick="cmcc();">			
		<!-- 	<button id="search" type="button" class="btn btn-primary radius" style="float:right;margin-left:10px;" onclick="multiStateChg();"><i class="Hui-iconfont">&#xe665;</i>批量激活</button>	 -->
      	    <input class="btn btn-primary radius" type="button" style="float:right;margin-left:10px;"
								value="&nbsp;&nbsp;批量激活&nbsp;&nbsp;" onclick="multiStateChg();">
      	</div>  
      	</div>   
	</form>	
</div>
</body>
</html>
