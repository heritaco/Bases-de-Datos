
import java.sql.*;
import java.io.*;

public class Ponchito {

	Connection conn = null;
	Statement stmt = null;
	BufferedReader in = null;

	static final String URL = "jdbc:mysql://localhost/";
	static final String BD = "ponchito"; // especificar: el nombre de la BD,
	static final String USER = "root"; // el nombre de usuario
	static final String PASSWD = "1234";// el password del usuario

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

	private void dumpResultSet(ResultSet rset) throws SQLException {

		ResultSetMetaData rsetmd = rset.getMetaData();
		int i = rsetmd.getColumnCount();

		while (rset.next()) {

			for (int j = 1; j <= i; j++) {
				System.out.print(rset.getString(j) + "\t");
			}
			System.out.println();
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

	private boolean menu() throws SQLException, IOException {

		String statement;

		System.out.println("\nNivel de aislamiento = " + conn.getTransactionIsolation() + "\n");
		System.out.println("(1) Lista completa lugares a visitar\n");
		System.out.println("(2) Lista restringida\n");
		System.out.println("(3) Insertar un empleado\n");
		System.out.println("(4) Borrar un empleado\n");
		System.out.println("(5) Modificar el salario de un empleado\n");
		System.out.println("(6) Validar todas operaciones\n");
		System.out.println("(7) Abortar todas las operaciones\n");
		System.out.println("(8) Cambiar nivel de aislamiento\n");
		System.out.println("(9) Salir\n\n");
		System.out.print("Option: ");

		switch (Integer.parseInt("0" + in.readLine())) {

			case 1:
				query("select * from lugaravisitar");
				break;

			case 2:
				System.out.println("\nPredicado?");
				query("select * from lugaravisitar where " + in.readLine());
				break;

			case 3:
				statement = "insert into lugaravisitar values ( ";

				System.out.println("\nNumero?");
				statement += in.readLine();

				System.out.println("Nombre?");
				statement += ", '" + in.readLine() + "', 'SALESMAN', ";

				System.out.println("Jefe?");
				statement += in.readLine() + ", '12-NOV-05', 1250.00,  500.00, 40 )";

				stmt.executeUpdate(statement);
				break;

			case 4:
				System.out.println("\nNombre?");
				stmt.executeUpdate("delete from emp where ename = '" + in.readLine() + "'");
				break;

			case 5:
				statement = "update lugaravisitar set ";

				System.out.println("\n<Atributo> = <Valor>?");
				statement += in.readLine();

				System.out.println("Predicado?");
				statement += " where " + in.readLine();

				stmt.executeUpdate(statement);
				break;

			case 6:
				conn.commit(); // fin de la transacción e inicio de la siguiente
				break;

			case 7:
				conn.rollback(); // fin de la transacción e inicio de la siguiente
				break;

			case 8:
				System.out.println();

				System.out.println(conn.TRANSACTION_NONE + " = TRANSACTION_NONE");
				System.out.println(conn.TRANSACTION_READ_UNCOMMITTED + " = TRANSACTION_READ_UNCOMMITTED");
				System.out.println(conn.TRANSACTION_READ_COMMITTED + " = TRANSACTION_READ_COMMITTED");
				System.out.println(conn.TRANSACTION_REPEATABLE_READ + " = TRANSACTION_REPEATABLE_READ");
				System.out.println(conn.TRANSACTION_SERIALIZABLE + " = TRANSACTION_SERIALIZABLE\n\n");

				System.out.println("Nivel?");
				conn.setTransactionIsolation(Integer.parseInt(in.readLine()));
				break;

			case 9:
				return false;
		}

		return true;
	}

	public static void main(String arg[]) throws SQLException, Exception {

		if (arg.length != 0) {

			System.err.println("Use: java Ponchito");
			System.exit(1);
		}

		Ponchito transaction = new Ponchito();

		while (true)

			try {
				if (!transaction.menu())
					break;

			} catch (Exception e) {

				System.err.println("failed");
				e.printStackTrace(System.err);
			}

		transaction.close();
	}

	public boolean addClient(String nombre, String apellido, String circuito, String salida) {
		String sql = "INSERT INTO Clientes (nombre, apellido, identificador_circuito, fecha_salida) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellido);
			pstmt.setString(3, circuito);
			pstmt.setString(4, salida);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}