<jsp:useBean id="calcula" class="beans.BeanCursoJsp"
	type="beans.BeanCursoJsp" scope="page" />


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Página Principal</title>

<style type="text/css">
#div1 {
	position: relative;
	/* border: solid 1px; */
	float: left;
	width: 150px;
	margin: 30px;
}

#div2 {
	/* border: solid 1px; */
	position: relative;
	width: 150px;
	float: left;
	margin: 30px;
}
</style>

</head>

<body bgcolor="#7FB3D5">
	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

	<h1 align="center">Acesso liberado!</h1>

	<jsp:setProperty property="*" name="calcula" />

	<h3 align="center">Seja bem vindo ao sistema em jsp</h3>

	<div id="div1">
		<h4>Cadastro de Usuários</h4>
		<a href="salvarUsuario?acao=listartodos"><img
			src="resources/img/cadastro_usuarios.png"
			alt="Cadastrar novo usuário" title="Cadastrar novo usuário"
			width="130px" height="130px"></a>
	</div>

	<div id="div2">
		<h4>Cadastro de Produtos</h4>
		<a href="Produtos?acao=listartodos"><img
			src="resources/img/produtos.png" alt="Cadastros de Produtos"
			title="Cadastro de Produtos" width="130px" height="130px"></a>
	</div>

</body>

</html>