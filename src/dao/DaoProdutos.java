package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCategoria;
import beans.BeanProdutosJsp;
import connection.SingleConnection;

public class DaoProdutos {
	private Connection connection;

	public DaoProdutos() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanProdutosJsp produto) {

		try {

			// nosso sql
			String sql = "insert into produtos(nome, quantidade, valor, categoria_id) values (?, ?, ?, ?)";

			// prepara sql pra insercao
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, produto.getNome());
			insert.setDouble(2, produto.getQuantidade());
			insert.setDouble(3, produto.getValor());
			insert.setLong(4, produto.getCategoria_id());

			insert.execute();

			connection.commit();

		} catch (Exception e) {

			try {

				connection.rollback();

			} catch (Exception e2) {

				e2.printStackTrace();

			}
		}

	}

	public List<BeanProdutosJsp> listar() throws Exception {

		List<BeanProdutosJsp> listar = new ArrayList<BeanProdutosJsp>();

		String sql = "select * from produtos";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			BeanProdutosJsp beanProdutosJsp = new BeanProdutosJsp();

			beanProdutosJsp.setId(resultSet.getLong("id"));
			beanProdutosJsp.setNome(resultSet.getString("nome"));
			beanProdutosJsp.setQuantidade(resultSet.getDouble("quantidade"));
			beanProdutosJsp.setValor(resultSet.getDouble("valor"));
			beanProdutosJsp.setCategoria_id(resultSet.getLong("categoria_id"));

			listar.add(beanProdutosJsp);

		}

		return listar;

	}
	
	public List<BeanCategoria> listarCategorias() throws Exception{
		List<BeanCategoria> retorno = new ArrayList<BeanCategoria>();
		String sql = "select * from categoria";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next()) {
			BeanCategoria categoria = new BeanCategoria();
			categoria.setId(resultSet.getLong("id"));
			categoria.setNome(resultSet.getString("nome"));
			
			retorno.add(categoria);
			
		}
		
		return retorno;
	}

	public void delete(String id) {

		try {
			String sql = "delete from produtos where id = '" + id + "'";

			PreparedStatement preparedStatement;

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.execute();

		} catch (Exception e) {

			e.printStackTrace();

			try {

				connection.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();

			}
		}

	}

	// sql para editar o produto
	public BeanProdutosJsp consultar(String id) throws Exception {
		String sql = "select * from produtos where id = '" + id + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			BeanProdutosJsp beanProdutosJsp = new BeanProdutosJsp();

			beanProdutosJsp.setId(resultSet.getLong("id"));
			beanProdutosJsp.setNome(resultSet.getString("nome"));
			beanProdutosJsp.setQuantidade(resultSet.getDouble("quantidade"));
			beanProdutosJsp.setValor(resultSet.getDouble("valor"));
			beanProdutosJsp.setCategoria_id(resultSet.getLong("categoria_id"));

			return beanProdutosJsp;
		}
		return null;
	}

	// verifica se já existe um produto com o mesmo nome
	public boolean validarNome(String nome) throws Exception {
		String sql = "select count(1) as qtd from produtos where nome = '" + nome + "'";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {

			return resultSet.getInt("qtd") <= 0;// return true
		}
		return false;
	}

	// sql para atualizar os dados do produto
	public void atualizar(BeanProdutosJsp produto) {

		try {
			String sql = "update produtos set nome = ?, quantidade = ?, valor = ?, categoria_id = ? where id = " + produto.getId();
			
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, produto.getNome());
			preparedStatement.setDouble(2, produto.getQuantidade());
			preparedStatement.setDouble(3, produto.getValor());
			preparedStatement.setLong(4, produto.getCategoria_id());

			preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {

			e.printStackTrace();

			try {

				connection.rollback();

			} catch (SQLException e1) {

				e1.printStackTrace();

			}
		}
	}

}
