import java.sql.*;
import java.io.*;

public class Ponchito {

	Connection conn = null;
	Statement stmt = null;
	BufferedReader in = null;

	static final String URL = "jdbc:mysql://localhost/";
	static final String BD = "ponchito";
	static final String USER = "root";
	static final String PASSWD = "1234";

	public Ponchito() throws SQLException, Exception {
		// this will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		System.out.print("Connecting to the database... ");

		// setup the connection with the DB
		conn = DriverManager.getConnection(URL + BD, USER, PASSWD);
		System.out.println("connected\n\n");

		conn.setAutoCommit(false); // inicio de la 1a transacción
		stmt = conn.createStatement();
		in = new BufferedReader(new InputStreamReader(System.in));
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

	private void close() throws SQLException {
		stmt.close();
		conn.close();
	}

	private boolean menu() throws SQLException, IOException {
		System.out.println("\nNivel de aislamiento = " + conn.getTransactionIsolation() + "\n");
		System.out.println("(1) Cambiar nivel de aislamiento\n");
		System.out.println("(2) Salir");
		System.out.print("Option:");

		switch (Integer.parseInt("0" + in.readLine())) {

			case 1:
				System.out.println();

				System.out.println(conn.TRANSACTION_NONE + " = TRANSACTION_NONE");
				System.out.println(conn.TRANSACTION_READ_UNCOMMITTED + " = TRANSACTION_READ_UNCOMMITTED");
				System.out.println(conn.TRANSACTION_READ_COMMITTED + " = TRANSACTION_READ_COMMITTED");
				System.out.println(conn.TRANSACTION_REPEATABLE_READ + " = TRANSACTION_REPEATABLE_READ");
				System.out.println(conn.TRANSACTION_SERIALIZABLE + " = TRANSACTION_SERIALIZABLE\n\n");

				System.out.println("Nivel de aislamiento actual = " + conn.getTransactionIsolation() + "\n");
				System.out.print("Nuevo nivel de aislamiento: ");
				conn.setTransactionIsolation(Integer.parseInt(in.readLine()));
				break;

			case 2:
				return false;
		}

		return true;
	}

	public static void main(String arg[]) throws SQLException, Exception {

		if (arg.length != 0) {

			System.err.println("Use: java Ponchito");
			System.exit(1);
		}

		Ponchito ponchito = new Ponchito();

		while (true)

			try {
				if (!ponchito.menu())
					break;

			} catch (Exception e) {

				System.err.println("failed");
				e.printStackTrace(System.err);
			}

		ponchito.close();
	}

	public boolean checkClientCredentials(int idcliente, String password) {
		try {
			PreparedStatement statement = conn
					.prepareStatement("SELECT * FROM Cliente WHERE idCliente = ? AND Contrasena = ?");
			statement.setInt(1, idcliente);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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

	public boolean addReserva(Integer idcliente, Integer idfechacircuito, Integer numpersonas) {
		String sql = "INSERT INTO Reservacion (idcliente, idfechacircuito, numpersonas) VALUES (?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idcliente);
			pstmt.setInt(2, idfechacircuito);
			pstmt.setInt(3, numpersonas);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int addSimulation(int idcliente, int idfechacircuito, int numpersonas) throws SQLException {
		String query = "INSERT INTO Simulacion (idCliente, idfechacircuito, numpersonas) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, idcliente);
		statement.setInt(2, idfechacircuito);
		statement.setInt(3, numpersonas);
		statement.executeUpdate();

		ResultSet generatedKeys = statement.getGeneratedKeys();
		if (generatedKeys.next()) {
			return generatedKeys.getInt(1);
		} else {
			throw new SQLException("No se pudo hacer la simulación.");
		}
	}

	public boolean updateClient(int idCliente, String nombre, String apellidoPaterno, String apellidoMaterno,
			String tipo, boolean agenciaEmpleado, int añoRegistro, String contrasena) {
		String sql = "UPDATE Cliente SET nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, tipo = ?, agenciaEmpleado = ?, añoRegistro = ?, contrasena = ? WHERE idCliente = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellidoPaterno);
			pstmt.setString(3, apellidoMaterno);
			pstmt.setString(4, tipo);
			pstmt.setBoolean(5, agenciaEmpleado);
			pstmt.setInt(6, añoRegistro);
			pstmt.setInt(8, idCliente);
			pstmt.setString(7, contrasena);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateReserva(int idReservacion, Integer idcliente, Integer idfechacircuito, Integer numpersonas) {
		String sql = "UPDATE Reservacion SET idcliente = ?, idfechacircuito = ?, numpersonas = ? WHERE idReservacion = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idcliente);
			pstmt.setInt(2, idfechacircuito);
			pstmt.setInt(3, numpersonas);
			pstmt.setInt(4, idReservacion);
			int rowsAffected = pstmt.executeUpdate();
			conn.commit(); // Commit the transaction
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}

}