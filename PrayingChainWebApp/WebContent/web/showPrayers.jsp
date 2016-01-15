<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="prayer.showPrayers.title"/></title>
</head>
<body>

<div id="searchFields">

</div>

<div id="searchResults">

	<table width="200" border="1" cellspacing="5" cellpadding="5">
  		<tr>
    		<th scope="col"><label>UID</label></th>
    		<th scope="col"><label><fmt:message key="prayer.name"/></label></th>
   		 	<th scope="col"><label><fmt:message key="prayer.email"/></label></th>
   			<th scope="col"><label><fmt:message key="prayer.phone"/></label></th>
   			<th scope="col"><label><fmt:message key="prayer.own_country"/></label></th>
   			<th scope="col"><label><fmt:message key="prayer.optin_date"/></label></th>
    		<th scope="col"><label><fmt:message key="prayer.notes"/></label></th>
   			<th scope="col"><label><fmt:message key="prayer.hidden"/></label></th>
			<th scope="col"><label><fmt:message key="prayer.pseudonym"/></label></th>
			<th scope="col"><label><fmt:message key="prayer.actions"/></label></th>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>

</body>
</html>