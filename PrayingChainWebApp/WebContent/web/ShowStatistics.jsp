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
				<td width="261">Total Turns</td>
				<td width="243">${response.TotalTurns}</td>
			</tr>
			<tr>
				<td>Turns Covered</td>
				<td>${response.TurnsCovered} (${response.TurnsUsedPercentage}%)</td>
			</tr>
			<tr>
				<td>Available Turns</td>
				<td>${response.AvailableTurns} (${response.FreeTurnsPercentage}%)</td>
			</tr>
			<tr>
				<td>Empty Turns</td>
				<td>${response.EmptyTurns} (${response.EmptyTurnsPercentage}%)</td>
			</tr>
			<tr>
				<td>Committed Prayers</td>
				<td>${response.CommittedPrayers}</td>
			</tr>
			<tr>
				<td>Non Committed Prayers</td>
				<td>${response.NonCommittedPrayers}</td>
			</tr>
			<tr>
				<td>Total Prayers</td>
				<td>${response.TotalPrayers}</td>
			</tr>
			<tr>
				<td>Redundancy (Committed / Used Turns)</td>
				<td>${response.CommittedRedundancy}%</td>
			</tr>
			<tr>
				<td>Redundancy (Total Prayers / Used Turns)</td>
				<td>${response.TotalRedundancy}%</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
</body>
</html>