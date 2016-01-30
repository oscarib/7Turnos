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
<title><fmt:message key="prayer.searchResults.postfix" /></title>

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

	<!-- starts container -->
	<div class="container">

		<!-- Bootstrap section template	
		<div class="row">
			<div class="col-xs-12">
			</div>
		</div>
		 -->

		<!-- Header af all pages -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./header.jsp"%>
			</div>
		</div>

		<!-- Jumbotron: Page general title -->
		<div class="row">
			<div class="col-xs-12">
				<div class="jumbotron">
					<h1>
						<fmt:message key="prayer.showPrayers.title" />
					</h1>
					<p>
						<fmt:message key="prayer.description" />
					</p>
				</div>
			</div>
		</div>

		<!-- Create new Prayer Button -->
		<div class="row">
			<div class="col-xs-12">
				<a href="./createPrayer.html" class="btn btn-primary active"
					role="button"> <fmt:message key="actions.create" /> <fmt:message
						key="other.new" /> <fmt:message key="prayer.prayer" />
				</a>
			</div>
		</div>

		<!-- Search Form -->
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
				<h1>
					<label><fmt:message key="prayer.searchBy" /></label>
				</h1>
				<form:form commandName="prayer">

					<table class="table table-striped" cellspacing="5"
						cellpadding="5">
						<tr>
							<th><label><fmt:message key="form.field" /></label></th>
							<th><label><fmt:message key="form.value" /></label></th>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.uid" /></label></td>
							<td><form:input class="form-control" path="uid" />
								<p class="text-danger bg-danger">
									<form:errors path="uid" />
								</p></td>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.name" /></label></td>
							<td><form:input class="form-control" path="name" />
								<p class="text-danger bg-danger">
									<form:errors path="name" />
								</p></td>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.email" /></label></td>
							<td><form:input class="form-control" path="email" />
								<p class="text-danger bg-danger">
									<form:errors path="email" />
								</p></td>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.phone" /></label></td>
							<td><form:input class="form-control" path="phone" />
								<p class="text-danger bg-danger">
									<form:errors path="phone" />
								</p></td>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.notes" /></label></td>
							<td><form:input class="form-control" path="notes" />
								<p class="text-danger bg-danger">
									<form:errors path="notes" />
								</p></td>
						<tr>
							<td><label><fmt:message key="prayer.hidden" /></label></td>
							<td><form:select path="hidden" items="${hiddenList}" />
								<p class="text-danger bg-danger">
									<form:errors path="hidden" />
								</p></td>
						</tr>
						<tr>
							<td><label><fmt:message key="prayer.ownCountry" /></label></td>
							<td><form:select path="ownCountry" items="${ownCountryList}" />
								<p class="text-danger bg-danger">
									<form:errors path="hidden" />
								</p></td>
						</tr>
						<tr>
							<td colspan="2"><input class="btn btn-primary" type="submit"
								value="<fmt:message key='form.submmit'/>"></td>
						</tr>
						<tr>
							<td colspan="2"><p class="text-danger bg-danger">
									<form:errors></form:errors>
								</p></td>
						</tr>
					</table>
				</form:form>
			</div>
			<!-- End of Search form -->

			<!-- Errors Section -->
			<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
				<!-- Errors -->
				<c:if test="${errorsSize > 0}">
					<h2>
						<fmt:message key="prayer.orphanPrayers" />
						(${errorsSize})
					</h2>

					<table class="table table-striped" width="200" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th scope="col"><label><fmt:message key="other.data" /></label></th>
							<th scope="col"><label><fmt:message
										key="prayer.actions" /></label></th>
						</tr>
						<c:forEach items="${orphanPrayers}" var="nextPrayer">
							<tr>
								<td><label><fmt:message key="other.ID" /></label>:
									${nextPrayer.uid}<br> ${nextPrayer.name}
										-${nextPrayer.email}- 
								<br>
								<label><fmt:message key="prayer.phone" /></label>:
									${nextPrayer.phone}<br>
								<label><fmt:message key="prayer.ownCountry" /></label>:
									${nextPrayer.ownCountry}<br>
								<label><fmt:message key="prayer.optinDate" /></label>:
									${nextPrayer.optinDate}</br> <label><fmt:message
											key="prayer.notes" /></label>:<br> ${nextPrayer.notes}</br> <label><fmt:message
											key="prayer.hidden" /></label>: ${nextPrayer.hidden}<br>
								<label><fmt:message key="prayer.pseudonym" /></label>:
									${nextPrayer.pseudonym}</td>
								<td>
									<p>
									<form action="./deletePrayer.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input class="btn btn-danger"
											type="submit" value="<fmt:message key='form.delete'/>">
									</form>
									</p>
									<p>
									<form action="./changePrayer.html" method="POST">
										<input type="hidden" name="firstCall" value="true" /> <input
											type="hidden" name="uid" value="${nextPrayer.uid}" /> <input
											type="hidden" name="name" value="${nextPrayer.name}" /> <input
											type="hidden" name="phone" value="${nextPrayer.phone}" /> <input
											type="hidden" name="email" value="${nextPrayer.email}" /> <input
											type="hidden" name="ownCountry"
											value="${nextPrayer.ownCountry}" /> <input type="hidden"
											name="notes" value="${nextPrayer.notes}" /> <input
											type="hidden" name="hidden" value="${nextPrayer.hidden}" />
										<input type="hidden" name="pseudonym"
											value="${nextPrayer.pseudonym}" /> <input
											class="btn btn-warning" type="submit"
											value="<fmt:message key='form.modify'/>">
									</form>
									</p>
									<p>
									<form action="./showTurns.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input class="btn btn-default"
											type="submit"
											value="<fmt:message key='other.see'/> <fmt:message key='turns.turns'/>">
									</form>
									</p>
									<p>
									<form action="./createTurn.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input type="hidden"
											name="firstCall" value="true" /> <input
											class="btn btn-success" type="submit"
											value="<fmt:message key='actions.create'/> <fmt:message key='turn.turn'/>">
									</form>
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<!-- End of Errors Section -->

			<!-- Data Section -->
			<div class="col-xs-12 col-sm-12 col-md-7 col-lg-7">
				<c:if test="${prayersSize > 0}">

					<h2>
						<fmt:message key="prayer.searchResults.prefix" />
						${prayersSize}
						<fmt:message key="prayer.searchResults.postfix" />
						:
					</h2>

					<table class="table table-striped table-hover">
						<tr>
							<th scope="col"><label><fmt:message key="other.data" /></label></th>
							<th scope="col" colspan=3><label><fmt:message
										key="prayer.actions" /></label></th>
						</tr>
						<c:forEach items="${prayers}" var="nextPrayer">
							<tr>
								<td><label><fmt:message key="other.ID" /></label>:
									${nextPrayer.uid}<br> ${nextPrayer.name}
										-${nextPrayer.email}- 
								<br>
								<label><fmt:message key="prayer.phone" /></label>:
									${nextPrayer.phone}<br>
								<label><fmt:message key="prayer.ownCountry" /></label>:
									${nextPrayer.ownCountry}<br>
								<label><fmt:message key="prayer.optinDate" /></label>:
									${nextPrayer.optinDate}</br> <label><fmt:message
											key="prayer.notes" /></label>:<br> ${nextPrayer.notes}</br> <label><fmt:message
											key="prayer.hidden" /></label>: ${nextPrayer.hidden}<br>
								<label><fmt:message key="prayer.pseudonym" /></label>:
									${nextPrayer.pseudonym}</td>
								<td>
									<p>
									<form action="./deletePrayer.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input class="btn btn-danger"
											type="submit" value="<fmt:message key='form.delete'/>">
									</form>
									</p>
									<p>
									<form action="./changePrayer.html" method="POST">
										<input type="hidden" name="firstCall" value="true" /> <input
											type="hidden" name="uid" value="${nextPrayer.uid}" /> <input
											type="hidden" name="name" value="${nextPrayer.name}" /> <input
											type="hidden" name="phone" value="${nextPrayer.phone}" /> <input
											type="hidden" name="email" value="${nextPrayer.email}" /> <input
											type="hidden" name="ownCountry"
											value="${nextPrayer.ownCountry}" /> <input type="hidden"
											name="notes" value="${nextPrayer.notes}" /> <input
											type="hidden" name="hidden" value="${nextPrayer.hidden}" />
										<input type="hidden" name="pseudonym"
											value="${nextPrayer.pseudonym}" /> <input
											class="btn btn-warning" type="submit"
											value="<fmt:message key='form.modify'/>">
									</form>
									</p>
									<p>
									<form action="./showTurns.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input class="btn btn-default"
											type="submit"
											value="<fmt:message key='other.see'/> <fmt:message key='turns.turns'/>">
									</form>
									</p>
									<p>
									<form action="./createTurn.html" method="POST">
										<input type="hidden" name="prayer_id"
											value="${nextPrayer.uid}" /> <input type="hidden"
											name="firstCall" value="true" /> <input
											class="btn btn-success" type="submit"
											value="<fmt:message key='actions.create'/> <fmt:message key='turn.turn'/>">
									</form>
									</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div>
			<!-- End of Data Section -->
		</div>


		<!-- Footer Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./footer.jsp"%>
			</div>
		</div>
		<!-- End of Footer Section -->

		</div>
		<!-- End of container -->
</body>
</html>