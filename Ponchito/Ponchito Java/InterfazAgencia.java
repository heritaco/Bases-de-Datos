import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InterfazAgencia {

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
        JFrame frame = new JFrame("Interfaz de Agencia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton consultarButton = new JButton("Consultar Folleto y Reservaciones");
        JButton crearClienteButton = new JButton("Crear Cliente");
        JButton reservarButton = new JButton("Reservar Viaje");
        JButton modificarReservaButton = new JButton("Modificar Reserva");

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para consultar el folleto y las reservaciones
                consultarFolletoYReservaciones();
            }
        });

        crearClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para crear un cliente
                crearCliente();
            }
        });

        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para reservar un viaje
                reservarViaje();
            }
        });

        modificarReservaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para modificar una reserva
                modificarReserva();
            }
        });

        panel.add(consultarButton);
        panel.add(crearClienteButton);
        panel.add(reservarButton);
        panel.add(modificarReservaButton);
        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private static void consultarFolletoYReservaciones() {
        // Implementación de la lógica para consultar el folleto y las reservaciones
        JOptionPane.showMessageDialog(null, "Consulta de Folleto y Reservaciones");
    }

    private static void crearCliente() {
        // Implementación de la lógica para crear un cliente
        JOptionPane.showMessageDialog(null, "Creación de Cliente");
    }

    private static void reservarViaje() {
        // Implementación de la lógica para reservar un viaje
        JOptionPane.showMessageDialog(null, "Reserva de Viaje");
    }

    private static void modificarReserva() {
        // Implementación de la lógica para modificar una reserva
        JOptionPane.showMessageDialog(null, "Modificación de Reserva");
    }
}
