import java.sql.*;

public class App {
    static final String DB_URL = "jdbc:mariadb://localhost:3306/mktest";
    static final String USER = "root";
    static final String PASS = "1234";
    static final String QUERY = "SELECT * FROM stud";

    public static void main(String[] args) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(QUERY);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                System.out.print("Roll: " + rs.getString("roll"));
                System.out.print(", Name: " + rs.getString("name"));
                System.out.println(", CGPA: " + rs.getFloat("cgpa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
