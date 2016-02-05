<%@include file="./TagLibraryHeader.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="actions.create" /> <fmt:message key="other.new" /> <fmt:message key="prayer.prayer" /></title>

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
			<h1><fmt:message key="actions.create" /> <fmt:message key="other.new" /> <fmt:message key="prayer.prayer" /></h1>
		</div>

			<form:form commandName="newPrayer">

				<div class="table-responsive">
					<table class="table table-striped" width="100%" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th width="10%"><label>Campo</label></th>
							<th width="90%"><label>Valor</label></th>
						</tr>
						<tr>
							<td><label>Nombre</label></td>
							<td><form:input class="form-control" path="name" />
							<p class="text-danger bg-danger"><form:errors path="name" /></p></td>
						</tr>
						<tr>
							<td><label>Email</label></td>
							<td><form:input class="form-control" path="email" />
							<p class="text-danger bg-danger"><form:errors path="email" /></p></td>
						</tr>
						<tr>
							<td><label>Teléfono</label></td>
							<td><form:input class="form-control" path="phone" />
							<p class="text-danger bg-danger"><form:errors path="phone" /></p></td>
						</tr>
						<tr>
							<td><label>País</label></td>
							<td><form:select path="ownCountry" items="${ownCountryList}" />
							<p class="text-danger bg-danger"><form:errors path="ownCountry" /></p></td>
						</tr>
						<tr>
							<td><label>Notes</label></td>
							<td><form:input class="form-control" path="notes" />
							<p class="text-danger bg-danger"><form:errors path="notes" /></p></td>
						<tr>
							<td><label>Oculto:</label></td>
							<td><form:select path="hidden" items="${hiddenList}" />
							<p class="text-danger bg-danger"><form:errors path="hidden" /></p></td>
						</tr>
						<tr>
							<td><label>Seudónimo:</label></td>
							<td><form:input class="form-control" path="pseudonym" />
							<p class="text-danger bg-danger"><form:errors path="pseudonym" /></p></td>
						</tr>
						<tr>
							<td colspan="2"><input class="btn btn-default" type="submit"
								value="<fmt:message key='form.submmit'/>"></td>
						</tr>
						<tr>
							<p class="text-danger bg-danger"><td colspan="2"><form:errors></form:errors></</td>
						</tr>
					</table>
				</div>
			</form:form>

		<%@include file="./footer.jsp"%>
	</div>
	<!-- /container -->

</body>
</html>