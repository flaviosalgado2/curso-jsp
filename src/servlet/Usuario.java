package servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
//pacote da Base64()
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// response.getWriter().append("Served at: ").append(request.getContextPath());

		try {

			String acao = request.getParameter("acao");
			String user = request.getParameter("user");

			// so deleta se acao recebida for igual a delete
			if (acao != null && acao.equalsIgnoreCase("delete") && user != null) {

				daoUsuario.delete(user);

				// deletou, recarrega todos os usuarios, e retorna pra mesma pagina
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);
			} else if (acao != null && acao.equalsIgnoreCase("listartodos")) {

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);

			} else if (acao != null && acao.equalsIgnoreCase("download")) {

				BeanCursoJsp usuario = daoUsuario.consultar(user);

				if (usuario != null) {

					String contentType = "";
					byte[] fileBytes = null;

					String tipo = request.getParameter("tipo");

					if (tipo.equalsIgnoreCase("imagem")) {
						contentType = usuario.getContentType();
						new Base64();
						// convert a imagem do banco para bytes
						fileBytes = Base64.decodeBase64(usuario.getFotoBase64());
					} else if (tipo.equalsIgnoreCase("curriculo")) {
						contentType = usuario.getContentTypeCurriculo();
						new Base64();
						// convert a pdf do banco para bytes
						fileBytes = Base64.decodeBase64(usuario.getCurriculoBase64());
					}

					// quebrando por regex, pegando vetor posicao 1
					response.setHeader("Content-Disposition",
							"attachment;filename=arquivo." + contentType.split("\\/")[1]);

					InputStream is = new ByteArrayInputStream(fileBytes);

					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();

					while ((read = is.read(bytes)) != -1) {
						os.write(bytes, 0, read);
					}
					os.flush();
					os.close();
				}
			} else {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				// recarrega pagina limpa
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

			// doPost onde recebe os dados
			String id = request.getParameter("id");

			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String telefone = request.getParameter("telefone");
			// dados parte 2
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String uf = request.getParameter("uf");
			String ibge = request.getParameter("ibge");
			String sexo = request.getParameter("sexo");
			String perfil = request.getParameter("perfil");

			BeanCursoJsp usuario = new BeanCursoJsp();

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);

			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			// dados parte 2
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setUf(uf);
			usuario.setIbge(ibge);
			usuario.setSexo(sexo);
			usuario.setPerfil(perfil);

			if (request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {

				usuario.setAtivo(true);

			} else {

				usuario.setAtivo(false);

			}

			try {

				// inicio - upload de imagens
				if (ServletFileUpload.isMultipartContent(request)) {

					Part imagemFoto = request.getPart("foto");

					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

						new Base64();
						String fotoBase64 = Base64
								.encodeBase64String(converteStremParaByte(imagemFoto.getInputStream()));

						usuario.setFotoBase64(fotoBase64);
						usuario.setContentType(imagemFoto.getContentType());

						/* Inicio miniatura */

						new Base64();
						// tranforma a imagem em um buffer image
						byte[] imageByteDecode = Base64.decodeBase64(fotoBase64);
						BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));

						// pega o tipo da imagem
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

						// cria imagem em miniatura
						BufferedImage resizedImage = new BufferedImage(100, 100, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, 100, 100, null);
						g.dispose();

						// escrever imagem novamente
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(resizedImage, "png", baos);

						String miniaturaBase64 = "data:image/png;base64,"
								+ DatatypeConverter.printBase64Binary(baos.toByteArray());

						usuario.setFotoBase64Miniatura(miniaturaBase64);
						/* Fim miniatura */

					} else {
						usuario.setAtualizarImage(false);
						// usuario.setFotoBase64(request.getParameter("fotoTemp"));
						// usuario.setContentType(request.getParameter("contentTypeTemp"));

					}

					Part curriculoPdf = request.getPart("curriculo");

					// processa PDF
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {

						String curriculoBase64 = new Base64()
								.encodeBase64String(converteStremParaByte(curriculoPdf.getInputStream()));
						usuario.setCurriculoBase64(curriculoBase64);
						usuario.setContentTypeCurriculo(curriculoPdf.getContentType());

					} else {
						usuario.setAtualizarPdf(false);
						// usuario.setCurriculoBase64(request.getParameter("TempPDF"));
						// usuario.setContentTypeCurriculo(request.getParameter("contentTypeTempPDF"));
					}

				}
				// fim - upload de imagens
				if (login == null || login.isEmpty()) {
					request.setAttribute("msg", "Campo de Login é obrigatório o preenchimento!");
					// mantendo dados apos validacao
					request.setAttribute("user", usuario);
				}

				else if (senha == null || senha.isEmpty()) {
					request.setAttribute("msg", "Campo de Senha é obrigatório o preenchimento!");
					// mantendo dados apos validacao
					request.setAttribute("user", usuario);
				}

				else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
					request.setAttribute("msg", "Usuário já existe com o mesmo login!");
					// mantendo dados apos validacao
					request.setAttribute("user", usuario);
				}

				else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
					request.setAttribute("msg", "Já existe usuário com esta senha!");
					// mantendo dados apos validacao
					request.setAttribute("user", usuario);
				}

				else if (id == null
						|| id.isEmpty() && !daoUsuario.validarSenha(senha) && !daoUsuario.validarLogin(login)) {
					request.setAttribute("msg", "Já existe este usuário e esta mesma senha!");
					// mantendo dados apos validacao
					request.setAttribute("user", usuario);
				}

				else if (id == null
						|| id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha)) {
					daoUsuario.salvar(usuario);

				}

				else if (id != null && !id.isEmpty()) {
					daoUsuario.atualizar(usuario);
				}

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");

				// carregamento da tabela
				request.setAttribute("usuarios", daoUsuario.listar());
				// request.setAttribute("msg", "Gravado com Sucesso!");
				view.forward(request, response);

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		doGet(request, response);
	}

	// converte a entrada de fluxo de dados da imagem para um array de bytes byte[]
	private byte[] converteStremParaByte(InputStream imagem) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();
	}

}
