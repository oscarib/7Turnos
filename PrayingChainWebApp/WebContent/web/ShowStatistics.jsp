<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="statistics.title"/></title>
</head>
<body>
	<div id="Statistics">
		<table width="545" border="1" cellspacing="5" cellpadding="5">
			<tr>
				<td width="261"><label><fmt:message key="statistics.turns.total"/></label></td>
				<td width="243">${response.TotalTurns}</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.turns.covered"/></label></td>
				<td>${response.TurnsCovered} (${response.TurnsUsedPercentage}%)</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.turns.available"/></label></td>
				<td>${response.AvailableTurns} (${response.FreeTurnsPercentage}%)</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.turns.empty"/></label></td>
				<td>${response.EmptyTurns} (${response.EmptyTurnsPercentage}%)</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.prayers.committed"/></label></td>
				<td>${response.CommittedPrayers}</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.prayers.noncommitted"/></label></td>
				<td>${response.NonCommittedPrayers}</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.prayers.total"/></label></td>
				<td>${response.TotalPrayers}</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.redundancy.committed"/></label></td>
				<td>${response.CommittedRedundancy}%</td>
			</tr>
			<tr>
				<td><label><fmt:message key="statistics.redundancy.total"/></label></td>
				<td>${response.TotalRedundancy}%</td>
			</tr>
		</table>
	</div>
</body>
</html>