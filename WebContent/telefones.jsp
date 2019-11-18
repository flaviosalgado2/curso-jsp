<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Telefones</title>

<link rel="stylesheet" type="text/css" href="resources/css/util.css">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">

<link rel="stylesheet" type="text/css" href="resources/css/util2.css">
<link rel="stylesheet" type="text/css" href="resources/css/main2.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/centralizando.css">
</head>
<body>

	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

	<div class="container-contact100">
		<div class="wrap-contact100" id="formulario">
			<center>
				<h2>Cadastro de Telefones</h2>
				<h3 style="color: orange;">${msg}</h3>
			</center>
			<form class="contact100-form validate-form" action="salvarTelefones"
				method="post" id="formTelefones">
				<center>
					<table>
						<tr>
							<td>User:</td>
							<td><input class="input100" type="text" readonly="readonly"
								id="id" name="id" value="${userEscolhido.id}"></td>
							<td>Nome:</td>
							<td><input class="input100" type="text" readonly="readonly"
								id="nome" name="nome" value="${userEscolhido.nome}"></td>
						</tr>

						<tr>
							<td>Número:</td>
							<td><input class="input100" type="text" id="numero"
								name="numero"></td>
							<td>Tipo:</td>
							<td><select id="tipo" name="tipo">
									<option>Casa</option>
									<option>Contato</option>
									<option>Celular</option>
									<option>Casa</option>
							</select></td>
						</tr>

						<tr>
							<td colspan="2"><input class="contact100-form-btn"
								type="submit" value="Salvar"></td>
							<td colspan="2"><input class="contact100-form-btn"
								type="submit" value="Voltar" onclick="document.getElementById('formTelefones').action = 'salvarTelefones?acao=voltar'"></td>
						</tr>
					</table>
				</center>
			</form>
		</div>

		<div class="wrap-table100" id="resultados">
			<div class="table100">
				<center>
					<h2>Telefones Cadastrados</h2>
				</center>
				<br>

				<table class="responsive-table">
					<tr>
						<th>Id</th>
						<th>Número</th>
						<th>Tipo</th>
						<th>Excluir Tel</th>
					</tr>
					<c:forEach items="${telefones}" var="fone">
						<tr class="table100-head">
							<td style="width: 150px"><c:out value="${fone.id}"></c:out></td>
							<td style="width: 150px"><c:out value="${fone.numero}"></c:out></td>
							<td><c:out value="${fone.tipo}"></c:out></td>

							<td><a
								href="salvarTelefones?acao=deleteFone&foneId=${fone.id}"><img
									src="resources/img/excluir.png" alt="Excluir" title="Excluir"
									width="20px" height="20px" onclick="return confirm('Confirma a exclusão?');"></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>