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

	<div class="container">

		<%@include file="./header.jsp"%>

		<div class="jumbotron">
			<h1><fmt:message key="turns.turns"/></h1>
			<p><fmt:message key="turn.showTurns.title"/></p>
		</div>

		<!-- Search form -->
		<h1>
			<label><fmt:message key="turn.searchBy" /></label>
		</h1>
		<form:form commandName="simpleTurn">

			<div class="table-responsive">
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
						<td><form:select path="dow">
								<form:option value="monday">
									<fmt:message key="day.monday" />
								</form:option>
								<form:option value="tuesday">
									<fmt:message key="day.tuesday" />
								</form:option>
								<form:option value="wednesday">
									<fmt:message key="day.wednesday" />
								</form:option>
								<form:option value="thursday">
									<fmt:message key="day.thursday" />
								</form:option>
								<form:option value="friday">
									<fmt:message key="day.friday" />
								</form:option>
								<form:option value="saturday">
									<fmt:message key="day.saturday" />
								</form:option>
								<form:option value="sunday">
									<fmt:message key="day.sunday" />
								</form:option>
							</form:select> <form:errors path="dow" /></td>
					</tr>

					<!-- TODO: Include filters on start and finishing turns
				<tr>
					<td><label><fmt:message key="turn.hour" /></label></td>
					<td>
						<form:select path="turn">
							<form:option value="0">00:00am</form:option>
							<form:option value="1">00:30am</form:option>
							<form:option value="2">01:00am</form:option>
							<form:option value="3">01:30am</form:option>
							<form:option value="4">02:00am</form:option>
							<form:option value="5">02:30am</form:option>
							<form:option value="6">03:00am</form:option>
							<form:option value="7">03:30am</form:option>
							<form:option value="8">04:00am</form:option>
							<form:option value="9">04:30am</form:option>
							<form:option value="10">05:00am</form:option>
							<form:option value="11">05:30am</form:option>
							<form:option value="12">06:00am</form:option>
							<form:option value="13">06:30am</form:option>
							<form:option value="14">07:00am</form:option>
							<form:option value="15">07:30am</form:option>
							<form:option value="16">08:00am</form:option>
							<form:option value="17">08:30am</form:option>
							<form:option value="18">09:00am</form:option>
							<form:option value="19">09:30am</form:option>
							<form:option value="20">10:00am</form:option>
							<form:option value="21">10:30am</form:option>
							<form:option value="22">11:00am</form:option>
							<form:option value="23">11:30am</form:option>
							<form:option value="24">12:00pm</form:option>
							<form:option value="25">12:30pm</form:option>
							<form:option value="26">13:00pm</form:option>
							<form:option value="27">13:30pm</form:option>
							<form:option value="28">14:00pm</form:option>
							<form:option value="29">14:30pm</form:option>
							<form:option value="30">15:00pm</form:option>
							<form:option value="31">15:30pm</form:option>
							<form:option value="32">16:00pm</form:option>
							<form:option value="33">16:30pm</form:option>
							<form:option value="34">17:00pm</form:option>
							<form:option value="35">17:30pm</form:option>
							<form:option value="36">18:00pm</form:option>
							<form:option value="37">18:30pm</form:option>
							<form:option value="38">19:00pm</form:option>
							<form:option value="39">19:30pm</form:option>
							<form:option value="40">20:00pm</form:option>
							<form:option value="41">20:30pm</form:option>
							<form:option value="42">21:00pm</form:option>
							<form:option value="43">21:30pm</form:option>
							<form:option value="44">22:00pm</form:option>
							<form:option value="45">22:30pm</form:option>
							<form:option value="46">23:00pm</form:option>
							<form:option value="47">23:30pm</form:option>
						</form:select>
					<form:errors path="turn" /></td>
				</tr>
 -->

					<tr>
						<td><label><fmt:message key="turn.status" /></label></td>
						<td><form:select path="status">
								<form:option value="accepted">
									<fmt:message key="status.accepted" />
								</form:option>
								<form:option value="cancelled">
									<fmt:message key="status.cancelled" />
								</form:option>
								<form:option value="NotCommitted">
									<fmt:message key="status.NotCommitted" />
								</form:option>
							</form:select> <form:errors path="status" /></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.notes" /></label></td>
						<td><form:input path="notes" /><br>
						<form:errors path="notes" /></td>
					</tr>
					<tr>
						<td colspan="2"><input class="btn btn-default" type="submit"
							value="<fmt:message key='form.search'/>"></td>
					</tr>
					<tr>
						<td colspan="2"><form:errors></form:errors></td>
					</tr>
				</table>
			</div>
		</form:form>

		<!-- Errors -->
		<c:if test="${response.errorsSize > 0}">
			<h2>
				<fmt:message key="other.warning" />
				${response.errorsSize}
				<fmt:message key="prayer.orphanTurns" />
			</h2>

			<div class="table-responsive">
				<table class="table table-striped" width="200" border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<th scope="col"><label><fmt:message key="turn.uid" /></label></th>
						<th scope="col"><label><fmt:message
									key="turn.prayer_id" /></label></th>
						<th scope="col"><label><fmt:message key="turn.dow" /></label></th>
						<th scope="col"><label><fmt:message key="turn.hour" /></label></th>
						<th scope="col"><label><fmt:message key="turn.status" /></label></th>
						<th scope="col"><label><fmt:message key="turn.notes" /></label></th>
						<th scope="col"><label><fmt:message key="turn.pax" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.actions" /></label></th>
					</tr>
					<c:forEach items="${response.orphanTurns}" var="nextTurn">
						<tr>
							<td>${nextTurn.uid}</td>
							<td>${nextTurn.prayer_id}</td>
							<td>${nextTurn.dow}</td>
							<td>${nextTurn.turn}</td>
							<td>${nextTurn.status}</td>
							<td>${nextTurn.notes}</td>
							<td>${nextTurn.pax}</td>
							<td>
								<form action="./deletePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextTurn.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.delete'/>">
								</form>
								<form action="./changePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextTurn.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.modify'/>">
								</form>
								<form action="./showPrayers.html" method="POST">
									<input type="hidden" name="prayer_id"
										value="${nextTurn.prayer_id}" /> <input
										class="btn btn-default" type="submit"
										value="<fmt:message key='prayer.prayer'/>">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>

		<!-- Results -->
		<c:if test="${response.turnsSize > 0}">

			<h2>
				<fmt:message key="turn.searchResults.prefix" />
				${response.turnsSize}
				<fmt:message key="turn.searchResults.postfix" />
				:
			</h2>

			<div class="table-responsive">
				<table class="table table-striped" width="200" border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<th scope="col"><label><fmt:message key="turn.uid" /></label></th>
						<th scope="col"><label><fmt:message
									key="turn.prayer_id" /></label></th>
						<th scope="col"><label><fmt:message key="turn.dow" /></label></th>
						<th scope="col"><label><fmt:message key="turn.hour" /></label></th>
						<th scope="col"><label><fmt:message key="turn.status" /></label></th>
						<th scope="col"><label><fmt:message key="turn.notes" /></label></th>
						<th scope="col"><label><fmt:message key="turn.pax" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.actions" /></label></th>
					</tr>
					<c:forEach items="${response.turns}" var="nextTurn">
						<tr>
							<td>${nextTurn.uid}</td>
							<td>${nextTurn.prayer_id}</td>
							<td>${nextTurn.dow}</td>
							<td>${nextTurn.turn}</td>
							<td>${nextTurn.status}</td>
							<td>${nextTurn.notes}</td>
							<td>${nextTurn.pax}</td>
							<td>
								<form action="./deletePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextTurn.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.delete'/>">
								</form>
								<form action="./changePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextTurn.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.modify'/>">
								</form>
								<form action="./showPrayers.html" method="POST">
									<input type="hidden" name="prayer_id"
										value="${nextTurn.prayer_id}" /> <input
										class="btn btn-default" type="submit"
										value="<fmt:message key='prayer.prayer'/>">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>

		<%@include file="./footer.jsp"%>

	</div>
	<!-- /container -->

</body>
</html>