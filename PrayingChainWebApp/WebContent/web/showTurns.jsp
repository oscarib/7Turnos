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
<title><fmt:message key="turns.turns" /></title>

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

	<!--  Main Container -->
	<div class="container">
	
		<!-- Header Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./header.jsp"%>
			</div>
		</div>
		<!-- End of Header Section -->
	
		<!-- Jumbotron Section (Page Title) -->
		<div class="row">
			<div class="col-xs-12">
				<div class="jumbotron">
					<h1><fmt:message key="form.search"/> <fmt:message key="turns.turns"/></h1>
				</div>
			</div>
		</div>
		<!-- End of Jumbotron Section (Page Title) -->

		<!-- Data Section -->
		<div class="row">
		
			<!-- Search Form -->
			<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
				<h1>
					<label><fmt:message key="turn.searchBy" /></label>
				</h1>
				<form:form commandName="simpleTurn">

					<table class="table table-striped" width="200" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th><label><fmt:message key="form.field" /></label></th>
							<th><label><fmt:message key="form.value" /></label></th>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.uid" /></label></td>
							<td><form:input path="uid" /><br>
							<form:errors path="uid" /></td>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.prayer_id" /></label></td>
							<td><form:input path="prayer_id" /><br>
							<form:errors path="prayer_id" /></td>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.dow" /></label></td>
							<td><form:select path="dow" items="${dowList}" /><br>
							<form:errors path="dow" /></td>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.turn" /></label></td>
							<td><form:select path="turn" items="${turnList}" /><br>
							<form:errors path="turn" /></td>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.status" /></label></td>
							<td><form:select path="status" items="${statusList}" /><br>
							<form:errors path="status" /></td>
						</tr>
						<tr>
							<td><label><fmt:message key="turn.notes" /></label></td>
							<td><form:input path="notes" /><br>
							<form:errors path="notes" /></td>
						</tr>
						<tr>
							<td colspan="2"><input class="btn btn-primary" type="submit"
								value="<fmt:message key='form.search'/>"></td>
						</tr>
						<tr>
							<td colspan="2" class="text-danger bg-danger"><form:errors /></td>
						</tr>
					</table>
				</form:form>
			</div>
			<!-- End of Search Form -->

			<!-- Error Section -->
			<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
				<c:if test="${errorsSize > 0}">
					<h2>
						<fmt:message key="other.warning" />
						${errorsSize}
						<fmt:message key="prayer.orphanTurns" />
					</h2>

					<table class="table table-striped" width="200" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th scope="col"><label><fmt:message key="other.data" /></label></th>
							<th scope="col"><label><fmt:message
										key="prayer.actions" /></label></th>
						</tr>
						<c:forEach items="${orphanTurns}" var="nextTurn">
							<tr>
								<td><label><fmt:message key="other.ID" /></label>:
									${nextTurn.uid}.${nextTurn.prayer_id}<br>
									${nextTurn.dow}
									(${nextTurn.turn}). ${nextTurn.status}<br>
									<label><fmt:message key="turn.notes" /></label>:<br>
									${nextTurn.notes}<br>
									<label><fmt:message key="prayer.prayer" />:</label><br>
									${nextTurn.prayerName}<br>
								</td>

								<td>
									<p>
									<form action="./deleteTurn.html" method="POST">
										<input type="hidden" name="uid" value="${nextTurn.uid}" /> <input
											class="btn btn-danger" type="submit"
											value="<fmt:message key='form.delete'/>">
									</form>
									</p>
									<p>
									<form action="./changeTurn.html" method="POST">
										<input type="hidden" name="firstCall" value="true" /> <input
											type="hidden" name="uid" value="${nextTurn.uid}" /> <input
											type="hidden" name="prayer_id" value="${nextTurn.prayer_id}" />
										<input type="hidden" name="dow" value="${nextTurn.dow}" /> <input
											type="hidden" name="turn" value="${nextTurn.turnInt}" /> <input
											type="hidden" name="status" value="${nextTurn.status}" /> <input
											type="hidden" name="notes" value="${nextTurn.notes}" /> <input
											class="btn btn-warning" type="submit"
											value="<fmt:message key='form.modify'/>">
									</form>
									</p>
									<p>
									<form action="./showPrayers.html" method="POST">
										<input type="hidden" name="uid" value="${nextTurn.prayer_id}" />
										<input class="btn btn-default" type="submit"
											value="<fmt:message key='other.see'/> <fmt:message key='prayer.prayer'/>">
									</form>
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<!-- End of Error Section -->

			<!-- Results Section -->
			<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
				<c:if test="${turnsSize > 0}">
					<h1>
						${turnsSize}
						<fmt:message key="turn.searchResults.postfix" />
						:
					</h1>
					<table class="table table-striped" width="200" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th scope="col"><label><fmt:message key="other.data" /></label></th>
							<th scope="col"><label><fmt:message
										key="prayer.actions" /></label></th>
						</tr>
						<c:forEach items="${turns}" var="nextTurn">
							<tr>
								<td><label><fmt:message key="other.ID" /></label>:
									${nextTurn.uid}.${nextTurn.prayer_id}<br>
									${nextTurn.dow}
									(${nextTurn.turn}). ${nextTurn.status}<br>
									<label><fmt:message key="turn.notes" /></label>:<br>
									${nextTurn.notes}<br>
									<label><fmt:message key="prayer.prayer" />:</label><br>
									${nextTurn.prayerName}<br>
								</td>
								<td>
									<p>
									<form action="./deleteTurn.html" method="POST">
										<input type="hidden" name="uid" value="${nextTurn.uid}" /> <input
											class="btn btn-danger" type="submit"
											value="<fmt:message key='form.delete'/>">
									</form>
									</p>
									<p>
									<form action="./changeTurn.html" method="POST">
										<input type="hidden" name="firstCall" value="true" /> <input
											type="hidden" name="uid" value="${nextTurn.uid}" /> <input
											type="hidden" name="prayer_id" value="${nextTurn.prayer_id}" />
										<input type="hidden" name="dow" value="${nextTurn.dow}" /> <input
											type="hidden" name="turn" value="${nextTurn.turnInt}" /> <input
											type="hidden" name="status" value="${nextTurn.status}" /> <input
											type="hidden" name="notes" value="${nextTurn.notes}" /> <input
											class="btn btn-warning" type="submit"
											value="<fmt:message key='form.modify'/>">
									</form>
									</p>
									<p>
									<form action="./showPrayers.html" method="POST">
										<input type="hidden" name="uid" value="${nextTurn.prayer_id}" />
										<input class="btn btn-default" type="submit"
											value="<fmt:message key='other.see'/> <fmt:message key='prayer.prayer'/>">
									</form>
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>

			</div>
			<!-- End of Results Section -->

		</div>
		<!-- End Data Section -->

		<!-- Footer Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./footer.jsp"%>
			</div>
		</div>
		<!-- End of Footer Section -->

	</div>
	<!--  End of Main Container -->

</body>
</html>