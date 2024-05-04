import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.image.BufferedImage;

public class PonchitoGUI {
    private JButton button0, button1, button2, button3, button4, button5;
    private JPanel panel1;
    private JTextArea textArea;
    private Ponchito ponchito;
    private JLabel welcomeLabel;
    private JDialog newUserDialog;
    private JTextField nombreField, apellidopField, apellidomField, añoRegistro;
    private JButton adminButton, reservarButton;
    private String adminPassword = "admin123";

    // Constructor
    public PonchitoGUI() {

        panel1 = new JPanel();
        textArea = new JTextArea(15, 30);
        panel1.add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel1.add(scrollPane);

        welcomeLabel = new JLabel("Bienvenido a Ponchito Viajes!!");
        panel1.add(welcomeLabel, BorderLayout.NORTH);

        try {
            ponchito = new Ponchito();
        } catch (Exception e) {
            e.printStackTrace();
        }

        button0 = createButton("Ciudades", "select * from ciudad");
        button1 = createButton("Lugares a visitar", "select * from lugaravisitar");
        button2 = createButton("Circuito", "select * from circuito");
        button3 = createButton("Fecha circuito", "select * from fechacircuito");
        button4 = createButton("Etapa", "select * from Etapa");
        button5 = createButton("Hotel", "select * from hotel");

        panel1.add(button0);
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);

        panel1.add(button5);
        adminButton = new JButton("Admin");
        reservarButton = new JButton("Reservar");

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog("Contraseña de administrador:");
                if (adminPassword.equals(password)) {
                    new AdminGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
                }
            }
        });

        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservaGUI().setVisible(true);
            }
        });

        panel1.add(adminButton);
        panel1.add(reservarButton);

        try

        {
            BufferedImage myPicture = ImageIO.read(new File("Ponchito.png"));
            Image scaledImage = myPicture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            panel1.add(picLabel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Reserva GUI
    class ReservaGUI extends JFrame {
        public ReservaGUI() {
            setSize(300, 200);
            setLayout(new FlowLayout());

            JButton ReserButton = new JButton("Reservar");
            // ReserButton.addActionListener(e -> CreateReserva());
            add(ReserButton);

            JButton VereservasButton = new JButton("Ver Reservas");
            // VereservasButton.addActionListener(e -> VerReservas());
            add(VereservasButton);
        }
    }

    // Admin GUI
    class AdminGUI extends JFrame {
        public AdminGUI() {
            setSize(300, 200);
            setLayout(new FlowLayout());

            JButton createUserButton = new JButton("Crear Cliente");
            createUserButton.addActionListener(e -> CreateUser());
            add(createUserButton);

            JButton ReserButton = new JButton("Reservar");
            ReserButton.addActionListener(e -> CreateReserva());
            add(ReserButton);

            JButton SeeClientsButton = new JButton("Ver Clientes");
            SeeClientsButton.addActionListener(e -> VerClientes());
            add(SeeClientsButton);

            JButton VereservasButton = new JButton("Ver Reservas");
            VereservasButton.addActionListener(e -> VerReservas());
            add(VereservasButton);

            JButton EditClientButton = new JButton("Editar Clientes");
            EditClientButton.addActionListener(e -> EditClient());
            add(EditClientButton);
        }

        private void CreateUser() {
            newUserDialog = new JDialog();
            newUserDialog.setTitle("Nuevo Cliente");
            newUserDialog.setLayout(new GridLayout(7, 2));

            nombreField = addField(newUserDialog, "Nombre:");
            apellidopField = addField(newUserDialog, "Apellido Paterno:");
            apellidomField = addField(newUserDialog, "Apellido Materno:");

            String[] tipos = { "compañía", "grupo", "individual" };
            JComboBox<String> tipo = new JComboBox<>(tipos);
            newUserDialog.add(new JLabel("Tipo:"));
            newUserDialog.add(tipo);

            String[] booleanOptions = { "true", "false" };
            JComboBox<String> agenciaEmpleado = new JComboBox<>(booleanOptions);
            newUserDialog.add(new JLabel("Agencia Empleado:"));
            newUserDialog.add(agenciaEmpleado);

            añoRegistro = addField(newUserDialog, "Año de registro:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    boolean agenciaEmpleadoBool = Boolean.parseBoolean((String) agenciaEmpleado.getSelectedItem());

                    String tipoSelected = (String) tipo.getSelectedItem();

                    boolean clientAdded = ponchito.addClient(
                            nombreField.getText(),
                            apellidopField.getText(),
                            apellidomField.getText(),
                            tipoSelected,
                            agenciaEmpleadoBool,
                            Integer.parseInt(añoRegistro.getText()));

                    if (clientAdded) {
                        JOptionPane.showMessageDialog(null, "Cliente añadido correctamente!");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Ha ocurrido un error al añadir el cliente. Por favor, inténtelo de nuevo.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, introduzca un año de registro válido.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            newUserDialog.add(saveButton);
            newUserDialog.pack();
            newUserDialog.setVisible(true);
        }

        private void CreateReserva() {
            JDialog reservaDialog = new JDialog();
            reservaDialog.setTitle("Nueva Reserva");
            reservaDialog.setLayout(new GridLayout(5, 2));

            JTextField clienteField = addField(reservaDialog, "Cliente:");
            JTextField circuitoField = addField(reservaDialog, "Circuito:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    boolean reservaAdded = ponchito.addReserva(
                            Integer.parseInt(clienteField.getText()), //
                            circuitoField.getText());

                    if (reservaAdded) {
                        JOptionPane.showMessageDialog(null, "Reserva añadida correctamente!");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Ha ocurrido un error al añadir la reserva. Por favor, inténtelo de nuevo.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            reservaDialog.add(saveButton);
            reservaDialog.pack();
            reservaDialog.setVisible(true);
        }

        private void VerClientes() {
            try {
                String results = ponchito.query("select * from cliente");
                textArea.setText(results);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void VerReservas() {
            try {
                String results = ponchito.query("select * from reserva");
                textArea.setText(results);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void EditClient() {
            JDialog editClientDialog = new JDialog();
            editClientDialog.setTitle("Editar Cliente");
            editClientDialog.setLayout(new GridLayout(8, 2));

            JTextField idField = addField(editClientDialog, "ID del cliente:");
            JTextField nombreField = addField(editClientDialog, "Nombre:");
            JTextField apellidoPaternoField = addField(editClientDialog, "Apellido Paterno:");
            JTextField apellidoMaternoField = addField(editClientDialog, "Apellido Materno:");
            String[] tipos = { "compañía", "grupo", "individual" };
            JComboBox<String> tipoField = new JComboBox<>(tipos);
            editClientDialog.add(new JLabel("Tipo:"));
            editClientDialog.add(tipoField);
            JCheckBox agenciaEmpleadoField = new JCheckBox("Agencia Empleado");
            editClientDialog.add(agenciaEmpleadoField);
            JTextField añoRegistroField = addField(editClientDialog, "Año de registro:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    boolean clientUpdated = ponchito.updateClient(
                            Integer.parseInt(idField.getText()),
                            nombreField.getText(),
                            apellidoPaternoField.getText(),
                            apellidoMaternoField.getText(),
                            (String) tipoField.getSelectedItem(),
                            agenciaEmpleadoField.isSelected(),
                            Integer.parseInt(añoRegistroField.getText()));
                    if (clientUpdated) {
                        JOptionPane.showMessageDialog(editClientDialog, "Cliente editado!");
                    } else {
                        JOptionPane.showMessageDialog(editClientDialog, "Hay algo mal.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editClientDialog, "Hay algo mal.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            editClientDialog.add(saveButton);
            editClientDialog.pack();
            editClientDialog.setVisible(true);
        }

    }

    // Add a field to the dialog
    private JTextField addField(JDialog dialog, String labelText) {
        dialog.add(new JLabel(labelText));
        JTextField field = new JTextField();
        dialog.add(field);
        return field;
    }

    // Create a button
    private JButton createButton(String text, String query) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font
        button.setBackground(Color.WHITE); // Set the background color
        button.setForeground(Color.BLACK); // Set the text color
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String results = ponchito.query(query);
                    textArea.setText(results);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return button;
    }

    //
    public static void main(String[] args) {
        JFrame frame = new JFrame("PonchitoGUI");
        PonchitoGUI ponchitoGUI = new PonchitoGUI();
        frame.setContentPane(ponchitoGUI.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}