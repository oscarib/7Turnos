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
<link href="./css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
<link href="./css/navbar.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body onload='document.f.edmUser.focus();'>

	<div class="container">

		<!-- Header Section -->
		<div class="row">
			<div class="col-xs-12">
			<p> </p>
			</div>
		</div>
		<!-- End Header Section -->

		<!-- Jumbotron: Page general title -->
		<div class="row">
			<div class="col-xs-12">
				<div class="jumbotron">
					<h1>Acceder</h1>
					<p>Introduce tu usuario y contraseña para acceder a esta página, por favor</p>
				</div>
			</div>
		</div>
		<!-- End of Jumbotron: Page general title -->

		<!-- Login Form -->
		<div class="row">
			<div class="col-xs-1 col-sm-2 col-md-3 col-lg-3"></div>
			<div class="col-xs-10 col-sm-8 col-md-6 col-lg-6">

				<!-- Login form -->
				<c:url value="/performLogin" var="loginUrl"/>
				<form name='f' action='${loginUrl}' method='POST'>
					<table class="table table-striped" cellspacing="5" border="1" 
						cellpadding="5">
						<tr>
							<td>Usuario:</td>
							<td><input type='text' name='edmUser' value=''></td>
						</tr>
						<tr>
							<td>Contraseña:</td>
							<td><input type='password' name='edmPwd' /></td>
						</tr>
						<tr>
							<td colspan='2'><input name="submit" type="submit" value="Login" /></td>
						</tr>
					</table>
				</form>
				<!-- End of Login form -->

			</div>
		</div>
		<!-- End of Login Form -->

		<!-- Footer Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./footer.jsp"%>
			</div>
		</div>
		<!-- End Footer Section -->

	</div>
	<!-- End of container -->

</body>
</html>