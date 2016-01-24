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

	<div class="container">

		<%@include file="./header.jsp"%>

		<div class="jumbotron">
			<h1><fmt:message key="prayer.showPrayers.title" /></h1>
			<p><fmt:message key="prayer.description"/></p>
		</div>

		<!-- Search form -->
		<h1>
			<label><fmt:message key="prayer.searchBy" /></label>
		</h1>
		<form:form commandName="prayer">

			<div class="table-responsive">
				<table class="table table-striped" width="200" border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<th><label><fmt:message key="form.field" /></label></th>
						<th><label><fmt:message key="form.value" /></label></th>
					</tr>
					<tr>
						<td><label><fmt:message key="prayer.name" /></label></td>
						<td><form:input class="form-control" path="name" /><br>
						<form:errors path="name" /></td>
					</tr>
					<tr>
						<td><label><fmt:message key="prayer.email" /></label></td>
						<td><form:input class="form-control" path="email" /><br>
						<form:errors path="email" /></td>
					</tr>
					<tr>
						<td><label><fmt:message key="prayer.phone" /></label></td>
						<td><form:input class="form-control" path="phone" /><br>
						<form:errors path="phone" /></td>
					</tr>
					<tr>
						<td><label><fmt:message key="prayer.notes" /></label></td>
						<td><form:input class="form-control" path="notes" /><br>
						<form:errors path="notes" /></td>
					<tr>
						<td><label><fmt:message key="prayer.ownCountry" /></label></td>
						<td><form:checkbox class="checkbox" path="ownCountry" /><br>
						<form:errors path="ownCountry" /></td>
					</tr>
					<tr>
						<td><label><fmt:message key="prayer.hidden" /></label></td>
						<td><form:checkbox path="hidden" /><br>
						<form:errors path="hidden" /></td>
					</tr>
					<tr>
						<td colspan="2"><input class="btn btn-default" type="submit"
							value="<fmt:message key='form.submmit'/>"></td>
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
				<fmt:message key="prayer.orphanPrayers" />
				(${response.errorsSize})
			</h2>

			<div class="table-responsive">
				<table class="table table-striped" width="200" border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<th scope="col"><label>UID</label></th>
						<th scope="col"><label><fmt:message key="prayer.name" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.email" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.phone" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.ownCountry" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.optinDate" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.notes" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.hidden" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.pseudonym" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.actions" /></label></th>
					</tr>
					<c:forEach items="${response.orphanPrayers}" var="nextPrayer">
						<tr>
							<td>${nextPrayer.uid}</td>
							<td>${nextPrayer.name}</td>
							<td>${nextPrayer.email}</td>
							<td>${nextPrayer.phone}</td>
							<td>${nextPrayer.ownCountry}</td>
							<td>${nextPrayer.optinDate}</td>
							<td>${nextPrayer.notes}</td>
							<td>${nextPrayer.hidden}</td>
							<td>${nextPrayer.pseudonym}</td>
							<td>
								<form action="./deletePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.delete'/>">
								</form>
							</td>
							<td>
								<form action="./changePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.modify'/>">
								</form>
							</td>
							<td>
								<form action="./showTurns.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='turns.turns'/>">
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</c:if>

		<!-- Results -->
		<c:if test="${response.prayersSize > 0}">

			<h2>
				<fmt:message key="prayer.searchResults.prefix" />
				${response.prayersSize}
				<fmt:message key="prayer.searchResults.postfix" />
				:
			</h2>

			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<tr>
						<th scope="col"><label>UID</label></th>
						<th scope="col"><label><fmt:message key="prayer.name" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.email" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.phone" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.ownCountry" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.optinDate" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.notes" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.hidden" /></label></th>
						<th scope="col"><label><fmt:message
									key="prayer.pseudonym" /></label></th>
						<th scope="col" colspan=3><label><fmt:message
									key="prayer.actions" /></label></th>
					</tr>
					<c:forEach items="${response.prayers}" var="nextPrayer">
						<tr>
							<td>${nextPrayer.uid}</td>
							<td>${nextPrayer.name}</td>
							<td>${nextPrayer.email}</td>
							<td>${nextPrayer.phone}</td>
							<td>${nextPrayer.ownCountry}</td>
							<td>${nextPrayer.optinDate}</td>
							<td>${nextPrayer.notes}</td>
							<td>${nextPrayer.hidden}</td>
							<td>${nextPrayer.pseudonym}</td>
							<td>
								<form action="./deletePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.delete'/>">
								</form>
							</td>
							<td>
								<form action="./changePrayer.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='form.modify'/>">
								</form>
							</td>
							<td>
								<form action="./showTurns.html" method="POST">
									<input type="hidden" name="prayer_id" value="${nextPrayer.uid}" />
									<input class="btn btn-default" type="submit"
										value="<fmt:message key='turns.turns'/>">
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