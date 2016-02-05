<%@include file="./TagLibraryHeader.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="other.success" /> <fmt:message key="info.PrayerCreated" /></title>

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
			<h1>
				<fmt:message key="other.success" />
			</h1>
			<h2>
				<fmt:message key="info.PrayerCreated" />
			</h2>
		</div>
		
		<div>
			<form action="./showPrayers.html" method="POST">
				<input type="hidden" name="uid" value="${newPrayer.uid}" />
				<input type="hidden" name="email" value="${newPrayer.email}" />
				<input type="hidden" name="phone" value="${newPrayer.phone}" />
				<input type="hidden" name="name" value="${newPrayer.name}" />
				<input class="btn btn-primary btn-lg active" type="submit" value="<fmt:message key='other.see'/> <fmt:message key='prayer.prayer'/>">
				<sec:csrfInput/>
			</form>
		</div>

		<%@include file="./footer.jsp"%>

	</div>
	<!-- /container -->

</body>
</html>