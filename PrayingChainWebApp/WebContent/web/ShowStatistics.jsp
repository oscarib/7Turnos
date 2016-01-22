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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Navbar Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="./web/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
<link href="./web/css/navbar.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<%@include file="./header.jsp"%>

		<div class="jumbotron">
			<h1>Estadísticas</h1>
			<p>Estadísticas de la cadena de oración en el momento presente</p>
		</div>

		<!-- Here add the content of the page -->
		<div class="table-responsive">
			<table class="table table-striped" width="545" border="1"
				cellspacing="5" cellpadding="5">
				<tr>
					<td width="261"><label><fmt:message
								key="statistics.turns.total" /></label></td>
					<td width="243">${response.TotalTurns}</td>
				</tr>
				<tr>
					<td><label><fmt:message key="statistics.turns.covered" /></label></td>
					<td>${response.TurnsCovered}
						(${response.TurnsUsedPercentage}%)</td>
				</tr>
				<tr>
					<td><label><fmt:message
								key="statistics.turns.available" /></label></td>
					<td>${response.AvailableTurns}
						(${response.FreeTurnsPercentage}%)</td>
				</tr>
				<tr>
					<td><label><fmt:message key="statistics.turns.empty" /></label></td>
					<td>${response.EmptyTurns}(${response.EmptyTurnsPercentage}%)</td>
				</tr>
				<tr>
					<td><label><fmt:message
								key="statistics.prayers.committed" /></label></td>
					<td>${response.CommittedPrayers}</td>
				</tr>
				<tr>
					<td><label><fmt:message
								key="statistics.prayers.noncommitted" /></label></td>
					<td>${response.NonCommittedPrayers}</td>
				</tr>
				<tr>
					<td><label><fmt:message key="statistics.prayers.total" /></label></td>
					<td>${response.TotalPrayers}</td>
				</tr>
				<tr>
					<td><label><fmt:message
								key="statistics.redundancy.committed" /></label></td>
					<td>${response.CommittedRedundancy}%</td>
				</tr>
				<tr>
					<td><label><fmt:message
								key="statistics.redundancy.total" /></label></td>
					<td>${response.TotalRedundancy}%</td>
				</tr>
			</table>
		</div>

		<%@include file="./footer.jsp"%>

	</div>
	<!-- /container -->

</body>
</html>