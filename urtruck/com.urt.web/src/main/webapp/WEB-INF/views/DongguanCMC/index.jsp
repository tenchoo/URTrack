<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/select2-4.0.3/dist/css/select2.css" media="all" />
</head>
<body>
<form action="${ctx}/groupMethod/infoQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1 width="250px" align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.information.inquiry' /> API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><input type="submit" value=""></td>
		</tr>
</table>
</form>
<form action="${ctx}/groupMethod/dailyalarmQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.in.batch.outage.alarm.list' /> API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/dailydatausagealarmQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.in.batch.outage.alarm.list' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/dailydatausageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.in.batch.Daily.traffic ' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/monthlydatausageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.in.batch.monthly.traffic' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />month：<input type="text" id="month" name="month"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/memberbillQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.in.batch.cost' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />month：<input type="text" id="month" name="month"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/billQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.total.cost.(History)' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />month：<input type="text" id="month" name="month"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/memberInfoQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.members.information' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />pageNo：<input type="text" id="pageNo" name="pageNo"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/membercountQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Through.the.group.serial.number.and.month' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />month：<input type="text" id="month" name="month"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/productInfoQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.product.information' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/balanceQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Group.balance.information.real-time.inquiry' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />productCode：<input type="text" id="productCode" name="productCode"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/groupMethod/test" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Test.case' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' />ecCode：<input type="text" id="ecCode" name="ecCode"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<br>
<span><fmt:message bundle='${pageScope.bundle}'  key='Retrieval.results' />：<input value="${result}"></span>
</body>
</html>