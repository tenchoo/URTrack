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
<title>单卡查询</title>
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
    var dataTableObj = {
        "bProcessing": true,
        "sPaginationType" : "bootstrap",
        "sServerMethod":"post",
        "bServerSide": true,
        "sAjaxSource" : "<%=basePath%>card/cardQuery", /* 跳转url */
        "iDisplayLength" : 10, /* 展示条数 */
        "columnDefs" : [
                {
                    "targets" : [ 0 ],
                    "data" : "iccid"
                },
                {
                    "targets" : [ 1 ],
                    "data" : "msisdn"
                },
                {
                    "targets" : [ 2 ],
                    
                    "data" : "removeTag",
                    "mRender" : function(data, type, full) {
							return "测试期";
						}
                },
                {
                    "targets" : [ 3 ],
                    "data" : "msisdn",
                    "mRender" : function(data, type, full) {
							if (full.openDate != null) {
								var updateTime = new Date(full.openDate)
										.format("yyyy-MM-dd");
								return "测试期产品";
							} else {
								return "测试期产品";
							}
						}
                },
                {
                    "targets" : [ 4 ],
                    "data" : "openDate",
                    "mRender" : function(data, type, full) {
							if (full.openDate != null) {
								var updateTime = new Date(full.openDate)
										.format("yyyy-MM-dd");
								return updateTime;
							} else {
								return "";
							}
						}
                },
                {
                	"targets" : [ 5 ],
                    "data" : "openDate",
                    "mRender" : function(data, type, full) {
						if (full.openDate != null) {
							var updateTime = new Date(full.openDate)
									.format("yyyy-MM-dd");
							return updateTime;
						} else {
							return "";
						}
					}
                 
                },
               
                {
					 "targets" : [ 6 ],
                     "data" : "destroyTime",
                     "mRender" : function(data, type, full) {
     				if (full.destroyTime != null) {
     					var updateTime = new Date(full.destroyTime).format("yyyy-MM-dd");
     					return updateTime;
     				} else {
     					return "";
     				}
     				}
                },
                {
                    "targets" : [ 7 ],
                    "data" : "msisdn",
                    "mRender" : function(data, type, full) {
								return "在网";
							}
                },
                {
                    "targets" : [8 ],
                    "data" : "msisdn",
                    "mRender" : function(data, type, full) {
								return "4G";
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
                    "name" : "msisdn",
                    "value" : $("#msisdn").val()
                },{
                    "name" : "iccid",
                    "value" : $("#iccid").val()
                }

                );
            }
        });
        $('#example').dataTable().fnDraw();
    }
</script>
</head>
<body>
    <div id='msg' style="display: none;"><div class="pd-20 font12">
			<form role="form" action="/cust/import_cust" method="post"
				enctype="multipart/form-data" id="sform">
				<div class="cl pd-5 mt-10">
					<label style="float: left">导入文件:</label> <input type="file"
						class="" style="width: 200px; padding: 7px; float: left;"
						id="inputfile" name="file">
					<button id="search" type="button" class="btn btn-primary radius"
						style="float: left" onclick="upload();">导入</button>
				</div>
			</form>
		</div></div>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>${message}</div>
    </c:if>
    <div class="pd-20">
        <form role="form">
            <div class="oh row">             
                <div class="col-md-3 col-lg-3 mt20" style="width: 10%;">
						<label for="name">ICCID: </label>
					</div>
					<div class="col-md-3 col-lg-3 mt20" style="width: 25%;margin-left: 0px;">
						<input type="text" class="input-text" placeholder="请输入iccid" id="iccid"
							name="iccid" >
					</div>				
					<div class="col-md-3 col-lg-3 mt20"
						style="width: 25%;padding-left: 150px;">
						<label for="name">MSISDN: </label>
					</div>
					<div class="col-md-3 col-lg-3  mt20"
						style="width: 25%; margin-left: 0px;">
						<input type="text" class="input-text" placeholder="请输入misidn" id="msisdn" name="msisdn" >
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
        <div class="mt-20">
            <table id="example" class="table table-border table-bordered table-hover table-bg table-sort " cellpadding="0">
                <thead>
                    <tr class="zpTable">
                        <th>ICCID</th>
                        <th>MSISDN</th>
                        <th>生命周期状态</th>
                        <th>套餐名称</th>
                        <th>激活日期</th>
                        <th>测试期开始时间</th>
                        <th>测试期结束时间</th>
                        <th>在网状态</th>
                        <th>网络制式</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
</body>
</html>
