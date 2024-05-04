import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfazGlobal {

    static final String URL = "jdbc:mysql://localhost/";
    static final String BD = "ponchito";
    static final String USER = "root";
    static final String PASSWD = "1234";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mostrarInterfazPublica();
            }
        });
    }

    private static void mostrarInterfazPublica() {
        JFrame frame = new JFrame("Interfaz Pública");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton clienteButton = new JButton("Acceder como Cliente");
        JButton agenciaButton = new JButton("Acceder como Trabajador");

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para acceder como cliente
                String palabraClave = JOptionPane.showInputDialog(null, "Ingrese su palabra clave:");
                if (verificarCliente(palabraClave)) {
                    mostrarInterfazCliente();
                } else {
                    JOptionPane.showMessageDialog(null, "Palabra clave incorrecta. Acceso denegado.");
                }
            }
        });

        agenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para acceder como trabajador
                String palabraClave = JOptionPane.showInputDialog(null, "Ingrese su palabra clave:");
                if (verificarTrabajador(palabraClave)) {
                    mostrarInterfazAgencia();
                } else {
                    JOptionPane.showMessageDialog(null, "Palabra clave incorrecta. Acceso denegado.");
                }
            }
        });

        panel.add(clienteButton);
        panel.add(agenciaButton);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private static boolean verificarCliente(String palabraClave) {
        // Implementación de la lógica para verificar la palabra clave de un cliente en la base de datos
        // Aquí se puede realizar una consulta a la base de datos para verificar si la palabra clave es válida
        return "clavecliente".equals(palabraClave); // Solo para propósitos de demostración
    }

    private static boolean verificarTrabajador(String palabraClave) {
        // Implementación de la lógica para verificar la palabra clave de un trabajador en la base de datos
        // Aquí se puede realizar una consulta a la base de datos para verificar si la palabra clave es válida
        return "clavetrabajador".equals(palabraClave); // Solo para propósitos de demostración
    }

    private static void mostrarInterfazCliente() {
        // Implementación de la lógica para mostrar la interfaz de cliente
        JOptionPane.showMessageDialog(null, "Acceso a la Interfaz de Cliente");
    }

    private static void mostrarInterfazAgencia() {
        // Implementación de la lógica para mostrar la interfaz de agencia
        JOptionPane.showMessageDialog(null, "Acceso a la Interfaz de Agencia");
    }
}
