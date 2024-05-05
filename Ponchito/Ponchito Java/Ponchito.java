import java.sql.*;

public class Ponchito {

	Connection conn = null;
	Statement stmt = null;

	static final String URL = "jdbc:mysql://localhost/";
	static final String BD = "ponchito";
	static final String USER = "root";
	static final String PASSWD = "1234";

	public Ponchito() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(URL + BD, USER, PASSWD);
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkCredentials(String id, String password) {
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM clientes WHERE id = ? AND password = ?");
			statement.setString(1, id);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String query(String statement) throws SQLException {
		ResultSet rset = stmt.executeQuery(statement);
		StringBuilder results = new StringBuilder();
		results.append("Results:\n");
		while (rset.next()) {
			ResultSetMetaData rsetmd = rset.getMetaData();
			int i = rsetmd.getColumnCount();
			for (int j = 1; j <= i; j++) {
				results.append(rset.getString(j)).append("\t");
			}
			results.append("\n");
		}
		rset.close();
		return results.toString();
	}

	public void close() throws SQLException {
		stmt.close();
		conn.close();
	}

	public boolean addClient(String nombre, String apellidoPaterno, String apellidoMaterno,
			String tipo, boolean agenciaEmpleado, int añoRegistro, String contraseña) {
		String sql = "INSERT INTO Cliente (nombre, apellidoPaterno, apellidoMaterno, tipo, agenciaEmpleado, añoRegistro, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidoPaterno);
			pstmt.setString(3, apellidoMaterno);
			pstmt.setString(4, tipo);
			pstmt.setBoolean(5, agenciaEmpleado);
			pstmt.setInt(6, añoRegistro);
			pstmt.setString(7, contraseña);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addReserva(Integer idcliente, String circuito) {
		String sql = "INSERT INTO Cliente (idcliente, circuito) VALUES (?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idcliente);
			pstmt.setString(2, circuito);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateClient(int idCliente, String nombre, String apellidoPaterno, String apellidoMaterno,
			String tipo, boolean agenciaEmpleado, int añoRegistro) {
		String sql = "UPDATE Cliente SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, tipo = ?, agenciaEmpleado = ?, añoRegistro = ? WHERE idCliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidoPaterno);
			pstmt.setString(3, apellidoMaterno);
			pstmt.setString(4, tipo);
			pstmt.setBoolean(5, agenciaEmpleado);
			pstmt.setInt(6, añoRegistro);
			pstmt.setInt(7, idCliente);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}