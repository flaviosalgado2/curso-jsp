<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="myprefix" uri="WEB-INF/testetag.tld"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>P�gina Principal</title>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById('idDoInput').focus();
	}
</script>
<link rel="stylesheet" href="resources/css/estilo.css">
</head>
<body bgcolor="#92C9ED">
	<div class="login">
		<h1>Tela de Login</h1>
		<form action="LoginServlet" method="post">
			<br /> <input type="text" id="idDoInput" name="login"
				placeholder="Login"> <br /> <br /> <input type="password"
				id="senha" name="senha" placeholder="Senha"> <br />
			<button type="submit" value="logar"
				class="btn btn-primary btn-block btn-large">Logar</button>
		</form>
	</div>
</body>
</html>