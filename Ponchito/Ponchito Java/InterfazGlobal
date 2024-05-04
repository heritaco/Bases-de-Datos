import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InterfazGlobal extends JFrame {
    private JButton publicButton, clienteButton, agenciaButton;
    private JPanel panel;
    private Ponchito ponchito;

    public InterfazGlobal() {
        super("Interfaz Global - Ponchito Viajes");

        // Inicialización de la conexión a la base de datos
        try {
            ponchito = new Ponchito();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Creación de los botones para cada tipo de usuario
        publicButton = new JButton("Interfaz Pública");
        clienteButton = new JButton("Interfaz Cliente");
        agenciaButton = new JButton("Interfaz Agencia");

        // Agregar oyentes de eventos a los botones
        publicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInterfazPublica();
            }
        });

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInterfazCliente();
            }
        });

        agenciaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInterfazAgencia();
            }
        });

        // Crear panel y agregar botones
        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(publicButton);
        panel.add(clienteButton);
        panel.add(agenciaButton);

        // Configuración de la ventana
        add(panel);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para mostrar la interfaz pública
    private void mostrarInterfazPublica() {
        // Aquí debes implementar la lógica para mostrar la interfaz pública
        JOptionPane.showMessageDialog(this, "Interfaz Pública");
    }

    // Método para mostrar la interfaz de cliente
    private void mostrarInterfazCliente() {
        // Aquí debes implementar la lógica de autenticación de cliente
        // Por ahora, simplemente abrimos la interfaz de cliente
        String nombreCliente = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            new ClienteGUI(nombreCliente);
        } else {
            JOptionPane.showMessageDialog(this, "Nombre de cliente no válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para mostrar la interfaz de agencia
    private void mostrarInterfazAgencia() {
        // Aquí debes implementar la lógica de autenticación de la agencia
        // Por ahora, simplemente abrimos la interfaz de agencia
        String nombreAgencia = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        String clave = JOptionPane.showInputDialog("Ingrese su contraseña:");
        if (nombreAgencia != null && !nombreAgencia.isEmpty() && clave != null && !clave.isEmpty()) {
            if (autenticarAgencia(nombreAgencia, clave)) {
                // Si la autenticación es exitosa, mostramos la interfaz de agencia
                new AgenciaGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Nombre de agencia o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nombre de agencia o contraseña no válidos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para autenticar a la agencia
    private boolean autenticarAgencia(String nombreAgencia, String clave) {
        // Aquí debes implementar la lógica de autenticación de la agencia
        // Por ahora, simplemente verificamos si el nombre y la clave coinciden con algún usuario de la base de datos
        return nombreAgencia.equals("nombreDeUsuario") && clave.equals("contraseña");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazGlobal::new);
    }
}
