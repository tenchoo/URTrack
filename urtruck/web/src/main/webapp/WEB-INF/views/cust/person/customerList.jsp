<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
<title>个人客户管理</title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/ui/css/bootstrap-theme.css" media="all" />
<script type="text/javascript"
    src="${ctx}/static/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
    src="${ctx}/static/lib/layer/1.9.3/layer.js"></script>
<script type="text/javascript"
    src="${ctx}/static/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
    src="${ctx}/static/lib/laypage/1.2/laypage.js"></script>
<%-- <script type="text/javascript" src="${ctx}/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/static/js/H-ui.js"></script>
<script type="text/javascript" src="${ctx}/static/js/H-ui.admin.js"></script>
<%-- <script src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.min.js"></script> --%>
<script type="text/javascript"
    src="<%=basePath%>static/bootstrap/3.3.6/js/bootstrap.js"></script>
<script type="text/javascript"
    src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script >
<script type="text/javascript" src="${ctx}/static/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/js/messages_zh.js"></script>
<script type="text/javascript"
    src="${ctx}/static/js/additional-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery-methods.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${ctx}/static/js/dateformat.js"></script>
<!--客户列表js  -->
<script type="text/javascript">
 
        function add_cust(title,url,w,h){
            layer_show(title,url,w,h);
        }
       
        function openConcatList(id){
            debugger;
            window.location.href='${ctx}/person/toConcatList/'+id;
        }
       /*  function openAccountList(id){
            debugger;
            window.location.href='${ctx}/person/toAccountList/'+id;
        } */
        //更新
        function toUpdate(id){
            if(id!=""){
                url='${ctx}/person/toUpdate/'+id;
                layer_show('客户编辑',url,'800','550');
            }

        }

        function toDetail(id){
            if(id!=""){
                url='${ctx}/person/toDetail/'+id;
                layer_show('客户查看',url,'800','550');
                layerIndexs++;
            }

        }
        function closeLayer(){
          debugger;
          layer.closeAll();
         }
    var dataTableObj = {
        "bProcessing": true,
        "sPaginationType" : "bootstrap",
        "sServerMethod":"post",
        "bServerSide": true,
        "sAjaxSource" : "<%=basePath%>person/queryList", /* 跳转url */
        "iDisplayLength" : 10, /* 展示条数 */
        "columnDefs" : [
                {
                    "targets" : [ 0 ],
                    "data" : "custName"
                },
                {
                    "targets" : [ 1 ],
                    "data" : null,
                    "mRender" : function(data, type, full) {
                        var status = full.custState;
                         if (status == "0") {
                            return "潜在客户";
                        } else if (status == "1") {
                            return "在用客户";
                        } else if (status == "2") {
                            return "注销客户";
                        } 
                        
                        return "在用客户";
                    }
                },
                {
                    "targets" : [ 2 ],
                    "data" : null,
                    "mRender" : function(data, type, full) {
                        var code = full.psptTypeCode;
                        if (code == "1") {
                            return "营业执照";
                        } else if (code == "2") {
                            return "法人证书";
                        } else if (code == "3") {
                            return "组织机构代码证";
                        } else if (code == "4") {
                            return "介绍信";
                        } else if (code == "5") {
                            return "照会";
                        }else{
                        	 return "身份证";
                        }
                    }
                },
                {
                    "targets" : [ 3 ],
                    "data" : "psptId"
                },
                {
                    "targets" : [ 4 ],
                    "data" : null,
                    "mRender" : function(data, type, full) {
                        return '<a title=\"编辑\" href="javaScript:toUpdate('
                                + data.custId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">编辑</a> '
                                + '<a title=\"查看\" href="javaScript:toDetail('
                                + data.custId
                                + ');" class=\"ml-5\" style=\"text-decoration:none\">查看</a> '
                                + '<a title=\"联系人\"  style=\"text-decoration:none\" onclick=\"openConcatList('
                                + data.custId
                                + ');\">联系人</a>'
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

    
    
    $(document).ready(function() {
        $('#example').dataTable(dataTableObj);
    });

    function sreach() {
    	$('#example').dataTable().fnClearTable(false);
    	 
            var oSettings = $('#example').dataTable().fnSettings();
            oSettings.aoServerParams.push({
                "fn" : function(aoData) {
                    aoData.push({
                        "name" : "custName",
                        "value" : $("#custName").val()
                    },{
                        "name" : "psptId",
                        "value" : $("#psptId").val()
                    }

                    );
                }
            });        
    	 $('#example').dataTable().fnDraw();
        
    }
</script>
</head>
<body>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>${message}</div>
    </c:if>
    <div class="pd-20">
        <form role="form">
            <div class="oh row">             
                <div class="col-12 clearfix" style="margin-bottom: 50px;">
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;padding-left: 40px;">
						<label for="name">客户名称: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;">
						<input type="text" class="input-text" id="custName" name="custName">
					</div>
					
					<div class="col-md-3 col-lg-3 mt20" style="width: 10%;padding-left: 40px;">
						<label for="name">证件号码: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20" style="width: 25%;">
						<input type="text" class="input-text" id="psptId" name="psptId">
					</div>							
				</div>							
            </div>
            <div class="mt20 clr" style="text-align: center;">
                <button id="search" type="button" class="btn btn-primary radius"
                    onclick="sreach();">
                    <i class="Hui-iconfont">&#xe665;</i>搜索
                </button>
                <input class="btn btn-default radius" type="reset"
                    value="&nbsp;&nbsp;清空&nbsp;&nbsp;">
            </div>
        </form>

        <div class="fr mtb20">
            <a href="javascript:;" onclick="add_cust('添加客户','${ctx}/person/person/createCustomer','800','550')" class="btn btn-primary radius">
            <span class="human"></span>添加客户</a>
        </div>
        <div class="mt-20">
            <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
                <thead>
                    <tr class="zpTable">
                        <th>客户名称</th>
                        <th>客户状态</th>
                        <th>证件类别</th>
                        <th>证件号码</th>
                        <th>操作</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</body>
</html>
