import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfazCliente {

    static final String URL = "jdbc:mysql://localhost/";
    static final String BD = "ponchito";
    static final String USER = "root";
    static final String PASSWD = "1234";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Consultar Reservaciones");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Ingrese su nombre:");
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Consultar Reservaciones");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = textField.getText();
                consultarReservaciones(nombreUsuario);
            }
        });

        panel.add(label);
        panel.add(textField);
        panel.add(button);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private static void consultarReservaciones(String nombreUsuario) {
        try (Connection conn = DriverManager.getConnection(URL + BD, USER, PASSWD)) {
            String sql = "SELECT * FROM Reservacion WHERE usuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    System.out.println("Sus reservaciones:");
                    while (resultSet.next()) {
                        System.out.println("ID: " + resultSet.getInt("id") + ", Fecha: " + resultSet.getDate("fecha"));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
