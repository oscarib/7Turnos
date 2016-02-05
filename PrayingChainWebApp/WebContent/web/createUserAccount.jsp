<%@include file="./TagLibraryHeader.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><fmt:message key="actions.create" /> <fmt:message key="other.new" /> <fmt:message key="other.user" /></title>

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
			<h1><fmt:message key="actions.create" /> <fmt:message key="other.new" /> <fmt:message key="other.user" /></h1>
		</div>

			<form:form commandName="newUser">

				<div class="table-responsive">
					<table class="table table-striped" width="100%" border="1"
						cellspacing="5" cellpadding="5">
						<tr>
							<th width="10%"><label>Campo</label></th>
							<th width="90%"><label>Valor</label></th>
						</tr>
						<tr>
							<td><label>Usuario</label></td>
							<td><form:input class="form-control" path="userAccount" />
							<p class="text-danger bg-danger"><form:errors path="userAccount" /></p></td>
						</tr>
						<tr>
							<td><label>Password</label></td>
							<td><form:password class="form-control" path="password" />
							<p class="text-danger bg-danger"><form:errors path="password" /></p></td>
						</tr>
						<tr>
							<td colspan="2"><input class="btn btn-default" type="submit"
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