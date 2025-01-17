package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefones;
import connection.SingleConnection;

public class DaoTelefones {
	private Connection connection;

	public DaoTelefones() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefones telefone) {

		try {

			// nosso sql
			String sql = "insert into telefone(numero, tipo, usuario) values (?, ?, ?)";

			// prepara sql pra insercao
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setDouble(3, telefone.getUsuario());

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

	public List<Telefones> listar(Long user) throws Exception {

		List<Telefones> listar = new ArrayList<Telefones>();

		String sql = "select * from telefone where usuario = " + user;

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			Telefones telefone = new Telefones();

			telefone.setId(resultSet.getLong("id"));
			telefone.setNumero(resultSet.getString("numero"));
			telefone.setTipo(resultSet.getString("tipo"));
			telefone.setUsuario(resultSet.getLong("usuario"));

			listar.add(telefone);

		}

		return listar;

	}

	public void delete(String id) {

		try {
			String sql = "delete from telefone where id = '" + id + "'";

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
}
