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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="prayer.showPrayers.title"/></title>
</head>
<body>

	<!-- DIV for presenting ddbb fields for searching -->
	<div id="searchFields">
		<h1>
			<label><fmt:message key="form.searchBy"/></label>:
		</h1>
		<form:form commandName="prayer">
			<table width="200" border="1" cellspacing="5" cellpadding="5">
				<tr>
					<td><label><fmt:message key="form.searchBy"/></label></td>
					<td><label><fmt:message key="form.enterValue"/></label></td>
				</tr>
				<tr>
					<td><label><fmt:message key="prayer.name" /></label></td>
					<td>
						<form:input path="name"/><br>
						<form:errors path="name"/>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="prayer.email" /></label></td>
					<td>
						<form:input path="email"/><br>
						<form:errors path="email"/>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="prayer.phone" /></label></td>
					<td>
						<form:input path="phone"/><br>
						<form:errors path="phone"/>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="prayer.notes" /></label></td>
					<td>
						<form:input path="notes"/><br>
						<form:errors path="notes"/>
					</td>
				<tr>
					<td><label><fmt:message key="prayer.ownCountry" /></label></td>
					<td>
						<form:checkbox path="ownCountry"/><br>
						<form:errors path="ownCountry"/>
					</td>
				</tr>
				<tr>
					<td><label><fmt:message key="prayer.hidden" /></label></td>
					<td>
						<form:checkbox path="hidden"/><br>
						<form:errors path="hidden"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="<fmt:message key='form.submmit'/>">
						<input type="submit" value="<fmt:message key='form.reset'/>" />
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<form:errors></form:errors>
					</td>
				</tr>
			</table>
		</form:form>
	</div>

	<div id="searchResults">
		<fmt:message key="list.prayers.size" />: ${response.size}
		<table width="200" border="1" cellspacing="5" cellpadding="5">
			<tr>
				<th scope="col"><label>UID</label></th>
				<th scope="col"><label><fmt:message key="prayer.name" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.email" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.phone" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.ownCountry" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.optinDate" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.notes" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.hidden" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.pseudonym" /></label></th>
				<th scope="col"><label><fmt:message key="prayer.actions" /></label></th>
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
						<form action="/deletePrayer.html" method="POST">
							<input type="hidden" name="prayer_id" value="${nextPrayer.uid}"/>
							<input type="submit" value="<fmt:message key='form.delete'/>">
						</form>
						<form action="/changePrayer.html" method="POST">
							<input type="hidden" name="prayer_id" value="${nextPrayer.uid}"/>
							<input type="submit" value="<fmt:message key='form.modify'/>">
						</form>
						<form action="/showTurns4Prayer.html" method="POST">
							<input type="hidden" name="prayer_id" value="${nextPrayer.uid}"/>
							<input type="submit" value="<fmt:message key='turns.turns'/>">
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>
</html>