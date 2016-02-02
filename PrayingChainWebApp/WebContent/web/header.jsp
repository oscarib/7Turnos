
<!-- Common header for main pages -->
<div class="header">

	<!-- Static navbar -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><fmt:message key="other.webappname" /></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="./index.html">Home</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><fmt:message key="other.menu" /><span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="./showStatistics.html"><fmt:message key="statistics.title" /></a></li>
							<li><a href="./uploadCalendar.html"><fmt:message key="other.uploadCalendar" /></a></li>
							<li role="separator" class="divider"></li>
							<li class="dropdown-header"><fmt:message key="other.ddbb" /></li>
							<li><a href="./showPrayers.html"><fmt:message key="prayer.searchResults.postfix" /></a></li>
							<li><a href="./showTurns.html"><fmt:message key="turns.turns" /></a></li>
							<li role="separator" class="divider"></li>
							<li><a href='logout'><fmt:message key="other.logout" /></a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</nav>

</div>