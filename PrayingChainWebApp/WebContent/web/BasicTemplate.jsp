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

		<!-- Header Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./header.jsp"%>
			</div>
		</div>
		<!-- End Header Section -->

		<!-- Jumbotron: Page general title -->
		<div class="row">
			<div class="col-xs-12">
				<div class="jumbotron">
					<h1>
						<!-- Localized Title -->
						<fmt:message key="prayer.showPrayers.title" />
					</h1>
					<p>
						<!-- Localized Description of the page -->
						<fmt:message key="prayer.description" />
					</p>
				</div>
			</div>
		</div>
		<!-- End of Jumbotron: Page general title -->

<!-- Place your content here -->
		<div class="row">
			<div class="col-xs-12">
			</div>
		</div>
<!-- Place your content here -->

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