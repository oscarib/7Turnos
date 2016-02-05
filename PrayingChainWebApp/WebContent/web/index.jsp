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

		<!-- Main component for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Panel de Control</h1>
			<p>Panel de control para la aplicación de gestión de "Cadena de Oración". Desde aquí podrán
			gestionarse parámetros de configuración, actualizaciones del calendario, y todo lo relativo
			a los oradores y turnos que después se verán en el sitio principal de la cadena</p>
		</div>

		<%@include file="./footer.jsp"%>

	</div>
	<!-- /container -->

</body>
</html>