<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Buscar Orador</title>
</head>
<form action="buscarOrador.html" method="post">
	<label>Nombre:</label><input name="name" type="text" size="50" maxlength="50"><br>
	<label>Email:</label> <input name="email" type="text" size="50" maxlength="150"><br>
	<label>Teléfono:</label> <input name="phone" type="text" size="50" maxlength="50"><br>
	<label>¿En España?</label> <input name="ownCountry" type="checkbox" value="" checked><br>
	<label>Dado de alta entre el</label><input name="fromDate" type="text" size="15" maxlength="15"><label> y el </label><input name="toDate" type="text" size="15" maxlength="15"><br>
	<label>Notas del orador:</label> <br><textarea name="notes" cols="60" rows="10" readonly>[Texto de la nota qquí]</textarea><br>
	<label>Oculto:</label> <input name="hidden" type="checkbox" disabled value=""><br>
	<label>Seudónimo:</label> <input name="pseudonym" type="text" size="50" maxlength="50"><br>
    <input name="submit" type="submit" value="Enviar">
</form>
<table width="200" border="1">
  <tr>
    <th scope="col"><label>UID</label></th>
    <th scope="col"><label>Nombre</label></th>
    <th scope="col"><label>Email</label></th>
    <th scope="col"><label>Teléfono</label></th>
    <th scope="col"></th>
  </tr>
		<c:forEach items="${prayers}" var="nextPrayer">
	  		<tr>
	    		<td>${nextPrayer.uid}</td>
	    		<td>${nextPrayer.name}</td>
	    		<td>${nextPrayer.email}</td>
	    		<td>${nextPrayer.phone}</td>
	    		<td><a href="buscarTurnos.html?Prayer_ID=${nextPrayer.uid}"><label>Mostrar Turnos</label></a></td>
		  	</tr>
	</c:forEach>
</table>
<body>
</body>
</html>