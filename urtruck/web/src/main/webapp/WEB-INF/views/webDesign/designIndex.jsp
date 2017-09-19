<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8" />
		<title>企业网站定制</title>
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/base.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/static/webDesign/css/common.css"/>
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/com_custom_index.css" />
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/iconfont.css" />
		<link rel="stylesheet" href="${ctx}/static/webDesign/css/pagination.css" />
        <script type="text/javascript" src="${ctx}/static/webDesign/js/jquery-1.11.3.min.js"></script>
        <script type="text/javascript" src="${ctx}/static/webDesign/js/jquery.pagination.min.js"></script>
	</head>
	<body>
		<div class="container">
			<div class="com_custom_header clearfloat">
				<div class="innerLeft fl">
					<img src="${ctx}/static/webDesign/img/网站定制图标.png" alt="" /><span>企业网站定制</span>
					<input id="client_search2"  placeholder="请输入客户名称" type="tel"/>
					<button id="submit2">搜索</button>
					<c:if test="${not empty custIds}">
					   <div class="submit_menu2" id="append">
					       <ul>
		                      <c:forEach items="${custIds}" var="custId">
		                          <li value="${custId.custId}">${custId.custName}</li>
		                      </c:forEach>
		                  </ul>
	                   </div>
		            </c:if>
				</div>
				
				<a class="fr" href="#" onclick="designNew()">立即定制</a>
			</div>
			
			<input type="text" id="countToT" hidden value="${count}">
			
			<div class="clearfloat">
			   <div class="com_custom_body clearfloat">
		<!--		 <c:if test="${empty designs}">-->
					<div class="com_custom_box fl">
						<img src="${ctx}/static/webDesign/img/网站定制图.png" alt="" />
						<div class="com_custom_box_footer">
							<a class="right_now" href="#" onclick="designNew()">立即定制</a>
						</div>
					</div>
	<!--			 </c:if>-->
				
					<!--定制完成的-->
	<!-- 			 <c:if test="${not empty designs}">
					<c:forEach items="${designs}" var="design">
						<div class="com_custom_box fl">
							<div class="logoArea" style="background-image:url(${ctx}/static/webDesign/img/${design.image})">
							</div>
							<div class="com_custom_box_footer">
								<div>
									<h1>${design.custName}</h1>
									<div class="date">
										<p>${design.designDate}</p>
										<p class="color">颜色 <span style="background-color:#${design.color}"></span></p>
									</div>
								</div>
								<div class="clearfloat">
									<button class="fr button" onclick="window.location.href='/webDesign/detail?designId='+${design.designId}+'&v=' + new Date().valueOf()"><i class="iconfont icon-bianji"></i>查看/编辑</button>
								</div>
							</div>
						</div>	
					</c:forEach>
				  </c:if>	 -->
				</div>
				<div class="clearfloat">
					<div id="pagination" class="page fr" style="margin-bottom:20px;"></div>
				</div>
			</div>
		</div>	
		<script>
			//搜索框
	         $(function(){
                $('#client_search2').on('focus',function(){
                    $('.submit_menu2').show();  
                }); 
                $('.submit_menu2 li').on('click',function(e){
                    var content = $(this).html();
                    var custId = $(this).attr('value');
                    $('#client_search2').val(content);
                    $('#client_search2').attr('value',custId);
                    $('.submit_menu2').hide();
                    return e.stopPropagation();
                }); 
                
                $('body').on('click',function(e){
                	if($(e.target).is('input') || $(e.target).is('li')){
                		return;
                	}else{
		                $('.submit_menu2').hide();
                	}
                });
                
                $.ajax({
                    url:"/webDesign/queryPage?v=" + new Date().valueOf(),
                    data:{
                        "begin":1,
                        "end":8,
                    },
                    success:function(result){
                        if(result != null && result != ''){
	                        $(".com_custom_body").html("");
                            var list = result;
                            var listLen = Math.ceil(list.length);
                            var content = "";
                            for(var i=0;i<listLen;i++){
                                content = "<div class='com_custom_box fl'>";
                                content = content + "<div class='logoArea' style='background-image:url(/static/webDesign/img/" + list[i].image + ")'></div>";
                                content = content + "<div class='com_custom_box_footer'><div><h1>" + list[i].custName + "</h1><div class='date'><p>" + list[i].designDate + "</p>" 
                                          + "<p class='color'>颜色 <span style='background:#" + list[i].color + "'></span></p></div></div> <div class='clearfloat'>"
                                          + "<button class='fr button' onclick='window.location.href=\"/webDesign/detail?custId="
                                          + list[i].custId + "&designId=" + list[i].designId +"&v=" + new Date().valueOf() + "\"'><i class='iconfont icon-bianji'></i>查看/编辑</button></div></div></div>";
                                $(".com_custom_body").append(content);
                            }
                        }
                    },
                });

                var countToT = Math.ceil($('#countToT').val());
                $("#pagination").pagination({
                    currentPage: 1,
                    totalPage: countToT,
                    isShow: true,
                    count: 4,
                    homePageText: "首页",
                    endPageText: "尾页",
                    prevPageText: "上一页",
                    nextPageText: "下一页",
                    callback: function(current) {
                    	$.ajax({
                            url:"/webDesign/queryPage?v=" + new Date().valueOf(),
                            data:{
                                "begin":current * 8 - 7,
                                "end":current * 8,
                            },
                            success:function(result){
                                $(".com_custom_body").html("");
                                if(result != null && result != ''){
                                	var list = result;
                                	var listLen = Math.ceil(list.length);
                                	var content = "";
                                	for(var i=0;i<listLen;i++){
	                                	content = "<div class='com_custom_box fl'>";
	                                	content = content + "<div class='logoArea' style='background-image:url(/static/webDesign/img/" + list[i].image + ")'></div>";
	                                	content = content + "<div class='com_custom_box_footer'><div><h1>" + list[i].custName + "</h1><div class='date'><p>" + list[i].designDate + "</p>" 
	                                              + "<p class='color'>颜色 <span style='background:" + list[i].color + "'></span></p></div></div> <div class='clearfloat'>"
	                                              + "<button class='fr button' onclick='window.location.href=\"/webDesign/detail?designId="
	                                              + list[i].designId + "&v=" + new Date().valueOf() + "\"'><i class='iconfont icon-bianji'></i>查看/编辑</button></div></div></div>";
	                                    $(".com_custom_body").append(content);
                                	}
                                }else{
                                    alert("查询定制信息失败！");
                                }
                            },
                           error:function(){
                               alert("查询定制信息失败2！");
                            }
                        });
                    }
                });
                
                <c:if test="${not empty oneCustName}">
                    var oneCustId = "${oneCustId}";
                    var oneCustName = "${oneCustName}";
                    $('#client_search2').attr("value",oneCustId);
                    $('#client_search2').val(oneCustName);
                    $('#client_search2').attr("readOnly","true");
                    $('#submit2').hide();
                </c:if>;
                
                <c:if test="${empty onCustName}">
					$('#client_search2').on('keyup' , function(){
				        getContent()
					});
					var getContent = function(){
				        var iccid = $.trim($('#client_search2').val()); //输入的搜索值
				        var data = $('.submit_menu2').find('li');
				        
				        data.each(function(){
				            var val = $.trim($(this).text())
				            if(val.indexOf(iccid) != -1){
				                $(this).show();  //把搜索到的显示出来
				            }else{
				                $(this).hide();  //不符合搜索要求的隐藏
				            }
				        });
					};
                </c:if>
				
            });
			
	       //定制新样式
            function designNew(){
                 var custId = $('#client_search2').attr('value');
                 alert(custId);
                 if(custId == null || custId == ""){
                     alert("请选择企业！");
                     $('#client_search2').focus();
                     return;
                 }
                 window.location.href = "/webDesign/detail?custId=" + custId + "&designId=";
             };
		</script>
	</body>	
</html>
