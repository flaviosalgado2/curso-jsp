<%@page import="beans.BeanCursoJsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de Usuário</title>

<link rel="stylesheet" type="text/css" href="resources/css/util.css">
<link rel="stylesheet" type="text/css" href="resources/css/main.css">

<link rel="stylesheet" type="text/css" href="resources/css/util2.css">
<link rel="stylesheet" type="text/css" href="resources/css/main2.css">

<link rel="stylesheet" type="text/css"
	href="resources/css/centralizando.css">

<!-- Adicionando JQuery -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"
	integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
	crossorigin="anonymous"></script>
<style>
#addUsuario {
	width: 40%;
}
</style>

</head>
<body>

	<a href="acessoliberado.jsp">Início</a>
	<a href="index.jsp">Sair</a>

	<div class="container-contact100">
		<div class="wrap-contact100" id="addUsuario">
			<center>
				<h2>Cadastro de Usuário</h2>
				<h3 style="color: orange;">${msg}</h3>
			</center>
			<form class="contact100-form validate-form" action="salvarUsuario"
				method="post" name="formUser" id="formUser"
				enctype="multipart/form-data">
				<center>
					<table>
						<tr>
							<td>Código:</td>
							<td><input class="input100" type="text" readonly="readonly"
								id="id" name="id" value="${user.id}"></td>
							<td>Login:</td>
							<td><input class="input100" type="text" id="login"
								name="login" value="${user.login}"
								placeholder="Informe seu login" autofocus></td>
						</tr>

						<tr>
							<td>CEP:</td>
							<td><input class="input100" type="text" id="cep" name="cep"
								onblur="consultaCep();" value="${user.cep}"></td>
							<td>Rua:</td>
							<td><input class="input100" type="text" id="rua" name="rua"
								value="${user.rua}"></td>
						</tr>

						<tr>
							<td>Senha:</td>
							<td><input class="input100" type="password" id="senha"
								name="senha" value="${user.senha}"></td>
							<td>Bairro:</td>
							<td><input class="input100" type="text" id="bairro"
								name="bairro" value="${user.bairro}"></td>
						</tr>

						<tr>
							<td>Nome:</td>
							<td><input class="input100" type="text" id="nome"
								name="nome" value="${user.nome}"></td>
							<td>Cidade:</td>
							<td><input class="input100" type="text" id="cidade"
								name="cidade" value="${user.cidade}"></td>
						</tr>
						<tr>
							<td>Estado:</td>
							<td><input class="input100" type="text" id="uf" name="uf"
								value="${user.uf}"></td>
							<td>IBGE:</td>
							<td><input class="input100" type="text" id="ibge"
								name="ibge" value="${user.ibge}"></td>
						</tr>
						<tr>
							<td>Ativo:</td>
							<td><input name="ativo" type="checkbox"
								<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.isAtivo()) {
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%> /></td>
							<td>Sexo:</td>
							<td><input type="radio" name="sexo" value="masculino"
								<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("masculino")) {
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>>Masculino</input>
								<input type="radio" name="sexo" value="feminino"
								<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getSexo().equalsIgnoreCase("feminino")) {
					out.print("checked=\"checked\"");
					out.print(" ");
				}
			}%>>Feminino</input>
							</td>
						</tr>
						<tr>
							<td>Foto:</td>
							<td><input class="input100" type="file" id="foto"
								name="foto" value="foto"><input class="input100"
								type="text" id="fotoTemp" name="fotoTemp" style="display: none;"
								readonly="readonly" value="${user.fotoBase64}" /> <input
								class="input100" type="text" id="contentTypeTemp"
								name="contentTypeTemp" style="display: none;"
								readonly="readonly" value="${user.contentType}" /></td>

							<td>Curriculo:</td>
							<td><input class="input100" type="file" id="curriculo"
								name="curriculo" value="curriculo"><input
								class="input100" type="text" id="tempPDF" name="tempPDF"
								style="display: none;" readonly="readonly"
								value="${user.curriculoBase64}" /> <input class="input100"
								type="text" id="contentTypeTempPDF" name="contentTypeTempPDF"
								style="display: none;" readonly="readonly"
								value="${user.contentTypeCurriculo}" /></td>
						</tr>

						<tr>
							<td>Perfil:</td>
							<td><select id="perfil" name="perfil" style="width: 220px;">
									<option value="nao_informado">[---Selecione---]</option>
									<option value="administrador"
										<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("administrador")) {
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>administrador</option>
									<option value="secretario"
										<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("secretario")) {
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>secretário</option>
									<option value="gerente"
										<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("gerente")) {
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>gerente</option>
									<option value="funcionario"
										<%if (request.getAttribute("user") != null) {

				BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
				if (user.getPerfil().equalsIgnoreCase("funcionario")) {
					out.print("selected=\"selected\"");
					out.print(" ");
				}
			}%>>funcionário</option>
							</select></td>
						</tr>

						<tr>
							<td colspan="2"><input class="contact100-form-btn"
								type="submit" value="Salvar"> <input
								class="contact100-form-btn" type="submit" value="Cancelar"
								onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'">

								<input class="contact100-form-btn" type="submit" value="Limpar"
								onclick="document.getElementById('formUser').action='salvarUsuario?acao=reset'">

							</td>
						</tr>
					</table>
				</center>
			</form>
		</div>

			<div>
				<form method="post" action="servletPesquisa" style="width: 65%;">
					<ul class="contact100-form validate-form">
						<li><table>
								<tr>
									<td>Descrição:</td>
									<td><input type="text" id="descricaoconsulta"
										name="descricaoconsulta"></td>
									<td><input class="contact100-form-btn" type="submit"
										value="Pesquisar"></td>
								</tr>
							</table></li>
					</ul>
				</form>
			</div>		

		<div class="wrap-table100" id="resultados">
			<div class="table100">
				<center>
					<h2>Usuários Cadastrados</h2>
				</center>
				<br>

				<table class="responsive-table">
					<tr>
						<th>Id</th>
						<th>Login</th>
						<th>Foto</th>
						<th>Curriculo</th>
						<th>Nome</th>
						<th>Deletar</th>
						<th>Editar</th>
						<th>Telefones</th>
					</tr>
					<c:forEach items="${usuarios}" var="user">
						<tr class="table100-head">
							<td style="width: 150px"><c:out value="${user.id}"></c:out></td>
							<td style="width: 150px"><c:out value="${user.login}"></c:out></td>

							<c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
								<td><a
									href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
										src='<c:out value="${user.fotoBase64Miniatura}"></c:out>'
										alt="Imagem do Usuário" title="Imagem do Usuário" width="32px"
										height="32px" /></a></td>
							</c:if>

							<c:if test="${user.fotoBase64Miniatura.isEmpty() == null}">
								<td><img src="resources/img/user_falta.png"
									alt="Imagem do Usuário" title="Imagem do Usuário" width="32px"
									height="32px"></td>
							</c:if>

							<c:if test="${user.curriculoBase64.isEmpty() == false}">
								<td><a
									href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
										src="resources/img/pdf-png.png" alt="Curriculo do Usuário"
										title="Curriculo do Usuário" width="32px" height="32px" /></a></td>
							</c:if>

							<c:if test="${user.curriculoBase64 == null}">
								<td><img src="resources/img/block.png"
									alt="Curriculo do Usuário faltando"
									title="Curriculo do Usuário faltando" width="32px"
									height="32px"></td>
							</c:if>

							<td><c:out value="${user.nome}"></c:out></td>

							<td><a href="salvarUsuario?acao=delete&user=${user.id}"
								onclick="return confirm('Confirma a exclusão?');"><img
									src="resources/img/excluir.png" alt="Excluir" title="Excluir"
									width="20px" height="20px"></a></td>

							<td><a href="salvarUsuario?acao=editar&user=${user.id}"><img
									src="resources/img/editar.png" alt="Editar" title="Editar"
									width="20px" height="20px"></a></td>

							<td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img
									src="resources/img/fone.png" alt="Telefones" title="Telefones"
									width="20px" height="20px"></a></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function consultaCep() {

			var cep = $("#cep").val();

			//Consulta o webservice viacep.com.br/
			$.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?",
					function(dados) {

						if (!("erro" in dados)) {
							//Atualiza os campos com os valores da consulta.
							$("#rua").val(dados.logradouro);
							$("#bairro").val(dados.bairro);
							$("#cidade").val(dados.localidade);
							$("#uf").val(dados.uf);
							$("#ibge").val(dados.ibge);
						} //end if.
						else {
							//CEP pesquisado não foi encontrado.
							$("#rua").val();
							$("#bairro").val();
							$("#cidade").val();
							$("#uf").val();
							$("#ibge").val();
							alert("CEP não encontrado.");
						}
					});

		}
	</script>

</body>
</html>