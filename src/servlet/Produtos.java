package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProdutosJsp;
import dao.DaoProdutos;

@WebServlet("/Produtos")
public class Produtos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoProdutos daoProdutos = new DaoProdutos();

	public Produtos() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String acao = request.getParameter("acao");
			String produto = request.getParameter("produto");

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");

			if (acao.equalsIgnoreCase("delete")) {

				daoProdutos.delete(produto);
				request.setAttribute("produtos", daoProdutos.listar());
			}

			else if (acao.equalsIgnoreCase("editar")) {
				BeanProdutosJsp beanProdutosJsp = daoProdutos.consultar(produto);

				request.setAttribute("produto", beanProdutosJsp);
			}

			else if (acao.equalsIgnoreCase("listartodos")) {

				request.setAttribute("produtos", daoProdutos.listar());

			}
			request.setAttribute("categorias", daoProdutos.listarCategorias());
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				// recarrega pagina e limpa campos
				RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");
				request.setAttribute("produtos", daoProdutos.listar());
				view.forward(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		else {

			// doPost onde recebe os dados
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			String categoria = request.getParameter("categoria_id");

			BeanProdutosJsp produto = new BeanProdutosJsp();

			produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);

			produto.setNome(nome);
			produto.setCategoria_id(Long.parseLong(categoria));

			if (quantidade != null && !quantidade.isEmpty()) {

				produto.setQuantidade(Double.parseDouble(quantidade));

			}

			if (valor != null && !valor.isEmpty()) {

				String valorParse = valor.replaceAll("\\.", "");
				valorParse = valorParse.replaceAll("\\,", ".");

				produto.setValor(Double.parseDouble(valorParse));

			}

			try {
				if (id == null || id.isEmpty() && !daoProdutos.validarNome(nome)) {
					request.setAttribute("msg", "Produto já existe com o mesmo nome!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (nome == null || nome.isEmpty()) {
				request.setAttribute("msg", "Obrigatório o preenchimento do campo nome.");
			}

			if (id == null || id.isEmpty() && !nome.isEmpty() && nome != null) {
				daoProdutos.salvar(produto);
			}

			else if (id != null && !id.isEmpty()) {
				daoProdutos.atualizar(produto);
			}

			// mantendo dados no formulario apos validacoes
			try {
				if (id == null || id.isEmpty() || !daoProdutos.validarNome(nome)) {
					request.setAttribute("produto", produto);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			RequestDispatcher view = request.getRequestDispatcher("/cadastroProdutos.jsp");

			try {

				request.setAttribute("produtos", daoProdutos.listar());

				request.setAttribute("categorias", daoProdutos.listarCategorias());

			} catch (Exception e) {
				e.printStackTrace();
			}
			view.forward(request, response);

		}

		doGet(request, response);
	}

}
