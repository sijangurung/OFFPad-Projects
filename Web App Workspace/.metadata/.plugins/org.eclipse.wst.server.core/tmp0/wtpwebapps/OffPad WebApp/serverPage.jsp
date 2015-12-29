<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome to OFFPad Demo Prototype</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel='stylesheet' type='text/css' href='styles\style.css' />
</head>
<body class="server">

	<h1 align="center">Secure Banking Services</h1>

	<center>
		<img width='200px' src='images\\logo_offpad.jpg' />
	</center>

		<center>
			<h2>
				<u>Transfer Request Received</u>
			</h2>
	<div class='leftSided'>
		<div class=' centerDiv'>
			<p>Destination Account Number: ${destination}</p>
			<p>Amount: ${amount}</p>
			<br /> <h5>Generated Hash: </h5> ${generatedHash}<br/>
			<h5>Expected Hash: </h5> ${received_hash}<br /> <br />
		</div>
	</div>
			<c:choose>
				<c:when test="${isAuthentic == true}">
					<font size="3" color="green">Hash Match <br>
						Transaction Authentication is verified !
					</font>
					<br />
				</c:when>
				<c:otherwise>
					<font size="3" color="red">Hash MisMatch <br>
						Transaction Authentication falied !
					</font>
					<br />
				</c:otherwise>
			</c:choose>
		</center>

</body>
</html>
