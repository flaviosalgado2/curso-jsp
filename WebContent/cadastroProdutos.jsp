<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Produtos</title>

<link rel="stylesheet" type="text/css" href="resources/css/util.css">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">

<link rel="stylesheet" type="text/css" href="resources/css/util2.css">
<link rel="stylesheet" type="text/css" href="resources/css/main2.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/centralizando.css">

<script src="resources/js/jquery.min.js" type="text/javascript"></script>
<script src="resources/js/jquery.maskMoney.min.js"
	type="text/javascript"></script>

</head>
<body>

	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

	<div class="container-contact100">
		<div class="wrap-contact100" id="formulario">
			<center>
				<h2>Cadastro de Produtos</h2>
				<h3 style="color: orange;">${msg}</h3>
			</center>
			<form class="contact100-form validate-form" action="Produtos"
				method="post" id="formProduto">
				<center>
					<table>
						<tr>
							<td>Código:</td>
							<td><input class="input100" type="text" readonly="readonly"
								id="id" name="id" value="${produto.id}"></td>
						</tr>
						<tr>
							<td>Nome:</td>
							<td><input class="input100" type="text" id="nome"
								name="nome" value="${produto.nome}"></td>
						</tr>
						<tr>
							<td>Quantidade:</td>
							<td><input class="input100" type="number" id="telefone"
								name="quantidade" value="${produto.quantidade}" maxlength="7"></td>
						</tr>
						<tr>
							<td>Valor:</td>
							<td><input class="input100" type="text" id="valor"
								name="valor" value="${produto.valorEmTexto}" data-thousands="."
								data-decimal=","></td>
						</tr>
						<tr>
							<td>Categoria:</td>
							<td><select id="categorias" name="categoria_id">
									<c:forEach items="${categorias}" var="cat">
										<option value="${cat.id}" id="cat.id"
											<c:if test="${cat.id == produto.categoria_id}"><c:out value="selected=selected"/>
											</c:if>>${cat.nome}
										</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td colspan="2"><input class="contact100-form-btn"
								type="submit" value="Salvar"><input
								class="contact100-form-btn" type="submit" value="Cancelar"
								onclick="document.getElementById('formProduto').action='Produtos?acao=reset'">
								<input class="contact100-form-btn" type="submit" value="Limpar"
								onclick="document.getElementById('formProduto').action='Produtos?acao=reset'"></td>
						</tr>
					</table>
				</center>
			</form>
		</div>

		<div class="wrap-table100" id="resultados">
			<div class="table100">
				<center>
					<h2>Produtos Cadastrados</h2>
				</center>
				<br>

				<table class="responsive-table">
					<tr>
						<th>Id</th>

						<th>Nome</th>
						<th>Quantidade</th>
						<th>Valor</th>


					</tr>
					<c:forEach items="${produtos}" var="produto">
						<tr class="table100-head">
							<td style="width: 150px"><c:out value="${produto.id}"></c:out></td>
							<td style="width: 150px"><c:out value="${produto.nome}"></c:out></td>
							<td><c:out value="${produto.quantidade}"></c:out></td>
							<td><fmt:formatNumber type="number" maxFractionDigits="3"
									value="${produto.valor}"></fmt:formatNumber></td>

							<td><a href="Produtos?acao=delete&produto=${produto.id}"><img
									src="resources/img/excluir.png" alt="Excluir" title="Excluir"
									width="20px" height="20px"
									onclick="return confirm('Confirma a exclusão?');"></a></td>
							<td><a href="Produtos?acao=editar&produto=${produto.id}"><img
									src="resources/img/editar.png" alt="Editar" title="Editar"
									width="20px" height="20px"></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>


</body>

<script type="text/javascript">
	$(function() {
		$('#valor').maskMoney();
	})
</script>

</html>