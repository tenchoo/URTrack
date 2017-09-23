<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="zh_cn" scope="page" />
<fmt:setBundle basename="i18n.i18n" scope="page" var="bundle" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
<title><fmt:message bundle='${pageScope.bundle}'
		key='Enterprise.customer.management' /></title>
<link href="${ctx}/static/ui/css/base.css" rel="stylesheet">
<link href="${ctx}/static/ui/css/common.css" rel="stylesheet">
<link href="${ctx}/static/css/H-ui.css" rel="stylesheet">
<link href="${ctx}/static/lib/Hui-iconfont/1.0.1/iconfont.css"
	rel="stylesheet">
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
	src="<%=basePath%>static/bootstrap/3.3.6/js/jquery.dataTables.js"></script>

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
            //导入更新
            function toUpdateImport(data){
                if(data!=""){
                    url='${ctx}/cust/toUpdateImport/'+data;
                    //layer_show('客户编辑',url,'800','550');
                    layer.open({
                    id:1,
                    type: 2,
                    scrollbar:false,
                    title: 'layer mobile页',
                    shadeClose: true,
                    shade: 0.8,
                    area: ['800px','550px'],
                    content: url //iframe的url
                    });
                }
            }
          function upload(){
              var option = {
                url : "<%=basePath%>cust/importCustomer",
                type : "post",
                success : function(data) {
                   $('#inputfile').val('');
                   toUpdateImport(data);
                   layer.closeAll('page');
                },
                error : function() {
                }
              };
             $("#sform").ajaxSubmit(option);
            }
        function add_cust(title,url,w,h){
            layer_show(title,url,w,h);
        }
        function import_cust(){
              var index = layer.open({
              type: 1,
              area: ['400px', '150px'],
              scrolling:'no',
              name:'12',
              content: $('#msg') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响,
            });
            $('#msg').show();
        }
        function openConcatList(id){
            debugger;
            window.location.href='${ctx}/cust/toConcatList/'+id;
        }
        function openAccountList(id){
            debugger;
            window.location.href='${ctx}/cust/toAccountList/'+id;
        }
        //更新
        function toUpdate(id){
            if(id!=""){
                url='${ctx}/cust/toUpdate/'+id;
                layer_show('<fmt:message bundle='${pageScope.bundle}'  key='customer.edit' />',url,'800','550');
            }

        }


        function toDetail(id){
            if(id!=""){
                url='${ctx}/cust/toDetail/'+id;
                layer_show('企业信息查看',url,'800','550');
                layerIndexs++;
            }

        }
        
        function closeLayer() {
    		debugger;
    		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    		parent.layer.close(index);
    	}
       
        	var dataTableObj = {
        	        "bProcessing": true,
        	        "sPaginationType" : "bootstrap",
        	        "sServerMethod":"post",
        	        "bServerSide": true,
        	        "sAjaxSource" : "<%=basePath%>feeSplit/toAccountList?custId="+${custId}+"", /* 跳转url */
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
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='potential.customer' />";
        	                        } else if (status == "1") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='customer.in.use.service' />";
        	                        } else if (status == "2") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='account.closed' />";
        	                        }
        	                    }
        	                },
        	                {
        	                    "targets" : [ 2 ],
        	                    "data" : null,
        	                    "mRender" : function(data, type, full) {
        	                        var code = full.psptTypeCode;
        	                        if (code == "1") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='business.licence' />";
        	                        } else if (code == "2") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='legal.representative.and.registration.certificate' />";
        	                        } else if (code == "3") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='Organization.code.certificate' />";
        	                        } else if (code == "4") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='letter.of.introduction' />";
        	                        } else if (code == "5") {
        	                            return "<fmt:message bundle='${pageScope.bundle}'  key='diplomatic.note' />";
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
        	                    "mRender" : function(data, type, full,row) {
        	                        return '<a title=\"<fmt:message bundle='${pageScope.bundle}'  key='View' />\" href="javaScript:toDetail('
        	                                + data.custId
        	                                + ');" class=\"ml-5\" style=\"text-decoration:none\"><fmt:message bundle='${pageScope.bundle}'  key='View' /></a> ';
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
        	        "aLengthMenu" : [ [ 10, 25, 50, -1, 0 ],
        	                [ "每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据" ] ]
        	    //设置每页显示记录的下拉菜单
        }

    $(document).ready(function() {
         $('#example').dataTable(dataTableObj);
    });


</script>
</head>
<body>
    <input type="hidden"  value="${custId}" 
							id="custId" name="custId">
	<div id='msg' style="display: none;">
		<div class="pd-20 font12">
			<form role="form" action="/cust/import_cust" method="post"
				enctype="multipart/form-data" id="sform">
				<div class="oh row">
					<div class="col-md-4 col-lg-4 mt20">
						<label style="float: left"><fmt:message
								bundle='${pageScope.bundle}' key='Import.files' />:</label> <input
							type="file" class=""
							style="width: 200px; padding: 7px; float: left;" id="inputfile"
							name="file">
					</div>
					<div class="col-md-4 col-lg-4 mt20">
						<button id="search" type="button" class="btn btn-primary radius"
							style="float: left" onclick="upload();">
							<fmt:message bundle='${pageScope.bundle}' key='Import.file' />
						</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="pd-20">

		<div class="mt-20">
			<table id="example"
				class="table table-border table-bordered table-hover table-bg table-sort "
				cellpadding="0">
				<thead>
					<tr class="zpTable">
						<th>企业名称</th>
						<th>客户状态</th>
						<th>证件类型</th>
						<th>证件号码</th>
						<th>操作</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<form action="${ctx}/cust/save" method="post"
				class="form form-horizontal" id="form_cust" name="form-member-add">
				<div class="row cl">
					<div class="col-6 col-offset-5">
						<input class="btn btn-primary radius" type="button"
							value="&nbsp;&nbsp;<fmt:message bundle='${pageScope.bundle}'  key='return' />&nbsp;&nbsp;" onclick="closeLayer();">
					</div>
				</div>
		</form>
		
	</div>
</body>
</html>
