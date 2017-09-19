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
<form action="${ctx}/memberMethod/infoQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1 width="250px" align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Through.group.number.to.get.API.of.customer.basic.information' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>
<form action="${ctx}/memberMethod/dataplanleftQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real.time.query.of.residual.data.information.of.members' /> API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/dailydatausageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Member.daily.traffic.information' />API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/monthlydatausageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Member.monthly.traffic.information' />API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> startDateStr：<input type="text" id="startDateStr" name="startDateStr"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> endDateStr：<input type="text" id="endDateStr" name="endDateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/monthlybilldetailQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Member.monthly.cost.detailed.statement' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/monthlybillQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Member.monthly.cost.detailed.statement' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> startDateStr：<input type="text" id="startDateStr" name="startDateStr"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> endDateStr：<input type="text" id="endDateStr" name="endDateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/alarmQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Member.alarm.information' /> API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/datausageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real.time.data.information.of.Member.number' />API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/balanceQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real.time.query.for.member.balance' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/packageQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real-time.of.member.plan.information.have.been.ordered' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> dateStr：<input type="text" id="dateStr" name="dateStr"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/statusQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real-time.status.query.of.member.business' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/terminalstatusQuery" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Real.time.state.inquiry.of.terminal.was.at.power-on.or.out.of.service' /></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/simstateChangeOpen" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td>simstateChangeOpen</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/simstateChangeClose" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td>simstateChangeClose</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<form action="${ctx}/memberMethod/simstateChange" method="post" class="form form-horizontal" id="form-member-add" name="form-member-add">
<table style="BORDER-COLLAPSE: collapse;width:850px" borderColor=#000000 height=40 cellPadding=1  align=center border=1>
		<tr>
			<td><fmt:message bundle='${pageScope.bundle}'  key='Number.keep.alive.or.out.of.service' /> API</td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> msisdn：<input type="text" id="msisdn" name="msisdn"></td>
			<td><fmt:message bundle='${pageScope.bundle}'  key='enter' /> ncode：<input type="text" id="ncode" name="ncode"></td>
			<td><input type="submit" value="<fmt:message bundle='${pageScope.bundle}'  key='Submit' />"></td>
		</tr>
</table>
</form>

<br>
<span><fmt:message bundle='${pageScope.bundle}'  key='Retrieval.results' />：<input value="${result}"></span>
</body>
</html>