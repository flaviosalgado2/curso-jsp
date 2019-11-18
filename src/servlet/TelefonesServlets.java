package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import beans.Telefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class TelefonesServlets extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();
	private DaoTelefones daoTelefones = new DaoTelefones();

	public TelefonesServlets() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String user = request.getParameter("user");

		if (user != null) {

			// apenas lista telefones
			if (acao.endsWith("addFone")) {
				// orientacao a objetos pura
				try {

					BeanCursoJsp usuario = daoUsuario.consultar(user);

					// guardando na sessao o atributo
					request.getSession().setAttribute("userEscolhido", usuario);
					// request.getSession().setAttribute("nomeUser", usuario.getNome());

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");

					request.setAttribute("telefones", daoTelefones.listar(usuario.getId()));

					view.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (acao.endsWith("deleteFone")) {

				String foneId = request.getParameter("foneId");

				daoTelefones.delete(foneId);

				BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");

				try {
					request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				request.setAttribute("msg", "Removido com Sucesso!");

				view.forward(request, response);
			}
		} else {

			
			try {
				
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

			String numero = request.getParameter("numero");
			String tipo = request.getParameter("tipo");

			String acao = request.getParameter("acao");

			if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

				if (numero == null || (numero != null && numero.isEmpty())) {
					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");

					request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));

					request.setAttribute("msg", "Informe o número do telefone!");

					view.forward(request, response);

				} else {

					Telefones telefones = new Telefones();

					telefones.setNumero(numero);
					telefones.setTipo(tipo);

					// pegando id armazenado na sessao
					telefones.setUsuario(beanCursoJsp.getId());

					// salvando telefone no submit
					daoTelefones.salvar(telefones);

					request.getSession().setAttribute("userEscolhido", beanCursoJsp);
					request.setAttribute("userEscolhido", beanCursoJsp);

					RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");

					request.setAttribute("telefones", daoTelefones.listar(beanCursoJsp.getId()));

					request.setAttribute("msg", "Telefone gravado com sucesso!");

					view.forward(request, response);

				}
			} else {

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");

				request.setAttribute("usuarios", daoUsuario.listar());

				view.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		doGet(request, response);
	}

}
