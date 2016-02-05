<%@include file="./TagLibraryHeader.jsp"%>

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

		<%@include file="./header.jsp"%>

		<div class="jumbotron">
			<h1><fmt:message key="form.modify"/> <fmt:message key="turn.turn"/></h1>
		</div>

		<form:form commandName="turn2Change">
			<input type="hidden" name="ButtonPushed" value="true" />
			<div class="table-responsive">
				<table class="table table-striped"  border="1"
					cellspacing="5" cellpadding="5">
					<tr>
						<th><label><fmt:message key="form.field" /></label></th>
						<th><label><fmt:message key="form.value" /></label></th>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.uid" /></label></td>
						<td><form:input class="form-control" path="uid" readonly='true'/>
						<p class="text-danger bg-danger"><form:errors path="prayer_id" /></p></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.prayer_id" /></label></td>
						<td><form:input class="form-control" path="prayer_id" readonly='true'/>
						<p class="text-danger bg-danger"><form:errors path="prayer_id" /></p></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.dow" /></label></td>
						<td><form:select path="dow" items="${dayList}" />
						<p class="text-danger bg-danger"><form:errors path="dow" /></p></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.turn" /></label></td>
						<td><form:select path="turn" items="${turnList}" />
						<p class="text-danger bg-danger"><form:errors path="turn" /></p></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.status" /></label></td>
						<td><form:select path="status" items="${statusList}" />
						<p class="text-danger bg-danger"><form:errors path="status" /></p></td>
					</tr>
					<tr>
						<td><label><fmt:message key="turn.notes" /></label></td>
						<td><form:textarea rows="5" cols="30" class="form-control" path="notes" />
						<p class="text-danger bg-danger"><form:errors path="notes" /></p></td>
					</tr>
					<tr>
						<td colspan="2"><input class="btn btn-primary btn-lg" type="submit"
							value="<fmt:message key='form.submmit'/>"></td>
					</tr>
					<tr>
						<td colspan="2"><p class="text-danger bg-danger"><form:errors></form:errors></p></td>
					</tr>
				</table>
			</div>
		</form:form>
		
		
		<%@include file="./footer.jsp"%>

	</div>
	<!-- /container -->

</body>
</html>