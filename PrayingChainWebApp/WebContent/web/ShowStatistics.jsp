<%@include file="./TagLibraryHeader.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="statistics.title" /></title>

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

		<!-- Header Section -->
		<div class="row">
			<div class="col-xs-12">
		<%@include file="./header.jsp"%>
			</div>
		</div>
		<!-- End of Header Section -->

		<!-- Jumbotron (Page Title) -->
		<div class="row">
			<div class="col-xs-12">
				<div class="jumbotron">
					<h1>Estadísticas</h1>
					<p>Estadísticas de la cadena de oración en el momento presente</p>
				</div>
			</div>
		</div>
		<!-- End of Jumbotron (Page Title) -->

		<!-- Data Section -->
		<div class="row">
			<div class="col-xs-12">
				<table class="table table-striped" width="545" border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<td width="261"><label><fmt:message
									key="statistics.turns.total" /></label></td>
						<td width="243">${response.TotalTurns}</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.turns.covered" /></label></td>
						<td>${response.TurnsCovered}
							(${response.TurnsUsedPercentage}%)</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.turns.available" /></label></td>
						<td>${response.AvailableTurns}
							(${response.FreeTurnsPercentage}%)</td>
					</tr>
					<tr class="danger">
						<td><label><fmt:message key="statistics.turns.empty" /></label></td>
						<td>${response.EmptyTurns} (${response.EmptyTurnsPercentage}%)</td>
					</tr>
					<tr class="success">
						<td><label><fmt:message
									key="statistics.prayers.committed" /></label></td>
						<td>${response.CommittedPrayers}</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.prayers.noncommitted" /></label></td>
						<td>${response.NonCommittedPrayers}</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.prayers.total" /></label></td>
						<td>${response.TotalPrayers}</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.redundancy.committed" /></label></td>
						<td>${response.CommittedRedundancy}%</td>
					</tr>
					<tr>
						<td><label><fmt:message
									key="statistics.redundancy.total" /></label></td>
						<td>${response.TotalRedundancy}%</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- End of Data Section -->

		<!-- Footer Section -->
		<div class="row">
			<div class="col-xs-12">
				<%@include file="./footer.jsp"%>
			</div>
		</div>
		<!-- End of Footer Section -->

	</div>
	<!-- Ends container -->

</body>
</html>