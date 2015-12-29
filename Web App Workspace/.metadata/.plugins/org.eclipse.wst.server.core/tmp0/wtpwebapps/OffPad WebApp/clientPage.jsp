<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>OffPAD Prototype By Sijan</title>
<link rel='stylesheet' type='text/css' href='styles\style.css' />
<!-- Bootstrap Core CSS -->
<link href="styles/bootstrap.min.css" rel="stylesheet">
<!-- FontAwesome CSS -->
<link href="css/font-awesome.min.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="styles/simple-sidebar.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>
	<nav class="navbar navbar-default no-margin"> <!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header fixed-brand">
		<a class="navbar-brand" href="#"><i class="fa fa-university fa-4"></i>
			 OFFPad Prototype </a>
	</div>
	<!-- navbar-header-->
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav">
			<li class="active"><button class="navbar-toggle collapse in"
					data-toggle="collapse" id="menu-toggle-2">
					<span class="glyphicon glyphicon-th-large" aria-hidden="true"></span>
				</button></li>
		</ul>
	</div>
	<!-- bs-example-navbar-collapse-1 --> </nav>
	<div id="wrapper">
		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav nav-pills nav-stacked" id="menu">
				<li><a href="index.html"><span
						class="fa-stack fa-lg pull-left"><i
							class="fa fa-group fa-stack-1x "></i></span>Overview</a></li>
				<li class="active"><a href="ClientServlet"> <span
						class="fa-stack fa-lg pull-left"><i
							class="fa fa-money fa-stack-1x "></i></span>Transfer Amount
				</a></li>
				<li><a href="#"><span class="fa-stack fa-lg pull-left"><i
							class="fa fa-history fa-stack-1x "></i></span>History</a></li>
				<li><a href="#"><span class="fa-stack fa-lg pull-left"><i
							class="fa fa-code fa-stack-1x "></i></span>About</a></li>
			</ul>
		</div>
		<!-- /#sidebar-wrapper -->

		<!-- Page Content -->
		<div id="page-content-wrapper">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<h1>Transfer Amount</h1>
						
						<div class='login'>

							<form action='ServerServlet' method='GET'>
								    Destination Account Number: <input type='text' name='destination' value='${rDest}'> 
									Amount: <input type='text' name='amount' value='${rAmt}' /> 
									KID: <input type='text' name='amount' value='' /> 
									Remarks:<input type='text' name='amount' value='' /> <br /> 
									<input type='hidden' name='hash' value='${hashReceived}' />
									<br> <input type='submit' class="btn btn-large btn-primary" value='Transfer Amount' />
							</form>


						</div>

					</div>
				</div>
			</div>

		</div>
		<!-- /#page-content-wrapper -->

	</div>
	<!-- /#wrapper -->

	<!-- jQuery -->
	<script src="js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap.min.js"></script>

</body>
<script>
	$("#menu-toggle").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled");
	});
	$("#menu-toggle-2").click(function(e) {
		e.preventDefault();
		$("#wrapper").toggleClass("toggled-2");
		$('#menu ul').hide();
	});

	function initMenu() {
		$('#menu ul').hide();
		$('#menu ul').children('.current').parent().show();
		//$('#menu ul:first').show();
		$('#menu li a').click(function() {
			var checkElement = $(this).next();
			if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
				return false;
			}
			if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
				$('#menu ul:visible').slideUp('normal');
				checkElement.slideDown('normal');
				return false;
			}
		});
	}
	$(document).ready(function() {
		initMenu();
	});
</script>
</html>
