<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create a new Prayer</title>
</head>
<form:form commandName="prayer">
    <table>
        <tr>
            <td><fmt:message key="prayer.name"/>:</td>
            <td><form:input path="name"/>
            	<form:errors path="name" cssClass="style.css" />
            </td>
        </tr>
        <tr>
            <td><fmt:message key="prayer.email"/>:</td>
            <td><form:input path="email"/></td>
        </tr>
        <tr>
            <td><fmt:message key="prayer.phone"/>:</td>
            <td><form:input path="phone"/></td>
        </tr>
        <tr>
            <td><fmt:message key="prayer.notes"/>:</td>
            <td><form:input path="notes"/></td>
        </tr>
        <tr>
            <td><fmt:message key="prayer.pseudonym"/>:</td>
            <td><form:input path="pseudonym"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="<fmt:message key="form.createPrayer.submmit"/>"/>
            </td>
        </tr>
    </table>
</form:form>
<body>
</body>
</html>