import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.image.BufferedImage;

public class PonchitoGUI extends JFrame {
    private JPanel panel1;
    private JTextArea textArea;
    private Ponchito ponchito;
    private JLabel welcomeLabel;
    private JDialog newUserDialog;
    private JTextField numField, nombreField, apellidopField, apellidomField, añoRegistro, contraseñaField,
            clienteField, circuitoField;
    private JButton adminButton, reservarButton;
    private String adminPassword = "admin123";
    private Image backgroundImage;
    private int idcliente;

    // Constructor
    public PonchitoGUI() {
        try {
            backgroundImage = ImageIO.read(new File("landscape-photography-of-snowy-mountain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, null);
            }
        };

        textArea = new JTextArea(30, 30);
        panel1.add(textArea);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel1.add(scrollPane);

        welcomeLabel = new JLabel("Bienvenido a Ponchito Viajes!!");
        panel1.add(welcomeLabel, BorderLayout.NORTH);

        try {
            ponchito = new Ponchito();
        } catch (

        Exception e) {
            e.printStackTrace();
        }

        panel1.add(createButton("Ciudades", "select * from ciudad"));
        panel1.add(createButton("Lugares a visitar", "select * from lugaravisitar"));
        panel1.add(createButton("Circuito", "select * from circuito"));
        panel1.add(createButton("Fecha circuito", "select * from fechacircuito"));
        panel1.add(createButton("Etapa", "select * from Etapa"));
        panel1.add(createButton("Hotel", "select * from hotel"));

        adminButton = new JButton("Admin");
        reservarButton = new JButton("Ingresar");

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

        // Define idcliente as an instance variable at the top of your class

        // Then, inside your actionPerformed method, you can assign a value to it
        reservarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    idcliente = Integer.parseInt(JOptionPane.showInputDialog("IDCLIENTE:"));
                    String password = JOptionPane.showInputDialog("Contraseña:");
                    if (ponchito.checkClientCredentials(idcliente, password)) {
                        new ClienteGUI().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "ID o contraseña incorrecta.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido.");
                }
            }
        });

        panel1.add(adminButton);
        panel1.add(reservarButton);

        try

        {
            BufferedImage myPicture = ImageIO.read(new File("Ponchito-Isotipo.png"));
            Image scaledImage = myPicture.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
            panel1.add(picLabel);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // Reserva GUI esto es lo que falta
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class ClienteGUI extends JFrame {
        public ClienteGUI() {
            setSize(300, 200);
            setLayout(new FlowLayout());

            JButton ReserButton = new JButton("Hacer simulación");
            ReserButton.addActionListener(e -> CreateSim());
            add(ReserButton);

            JButton VereservasButton = new JButton("Ver Reservas");
            VereservasButton.addActionListener(e -> VerReservas());
            add(VereservasButton);

            JButton Versim = new JButton("Ver Simulaciones");
            Versim.addActionListener(e -> VerSimulacion());
            add(Versim);
        }

        // Ya esta
        private void CreateSim() {
            JDialog reservaDialog = new JDialog();
            reservaDialog.setTitle("Nueva simulación");
            reservaDialog.setLayout(new GridLayout(5, 2));

            JTextField idfechacircuito = addField(reservaDialog, "ID Fecha Circuito:");
            JTextField numpersonas = addField(reservaDialog, "Num Personas:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    int numeroSimulacion = ponchito.addSimulation(
                            idcliente,
                            Integer.parseInt(idfechacircuito.getText()),
                            Integer.parseInt(numpersonas.getText()));

                    JOptionPane.showMessageDialog(null,
                            "Reserva añadida correctamente! Numero de simulacion: " + numeroSimulacion);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            reservaDialog.add(saveButton);
            reservaDialog.pack();
            reservaDialog.setVisible(true);
        }

        private void VerReservas() {
            try {
                PreparedStatement statement = ponchito
                        .prepareStatement("SELECT * FROM Reservacion WHERE idcliente = ?");
                statement.setInt(1, idcliente);
                ResultSet resultSet = statement.executeQuery();

                StringBuilder results = new StringBuilder();
                while (resultSet.next()) {
                    results.append("numeroReservacion: ").append(resultSet.getInt("numeroReservacion"))
                            .append(", idcliente: ").append(resultSet.getInt("idcliente"))
                            .append(", idfechacircuito: ").append(resultSet.getInt("idfechacircuito"))
                            .append(", numpersonas: ").append(resultSet.getInt("numpersonas"))
                            .append("\n");
                }

                System.out.println("Reservations: " + results.toString()); // Print to console for debugging
                textArea.setText(results.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void VerSimulacion() {
            try {
                PreparedStatement statement = ponchito.prepareStatement("SELECT * FROM Simulacion WHERE idCliente = ?");
                statement.setInt(1, idcliente);
                ResultSet resultSet = statement.executeQuery();

                StringBuilder results = new StringBuilder();
                while (resultSet.next()) {
                    results.append("numeroSimulacion: ").append(resultSet.getInt("numeroSimulacion"))
                            .append(", idCliente: ").append(resultSet.getInt("idCliente"))
                            .append(", idfechacircuito: ").append(resultSet.getInt("idfechacircuito"))
                            .append(", numpersonas: ").append(resultSet.getInt("numpersonas"))
                            .append("\n");
                }

                textArea.setText(results.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Admin GUI
    class AdminGUI extends JFrame {
        public AdminGUI() {
            setSize(300, 400);
            setLayout(new FlowLayout());
            // ya esta
            JButton createUserButton = new JButton("Crear Cliente");
            createUserButton.addActionListener(e -> CreateUser());
            add(createUserButton);
            // falta
            JButton ReserButton = new JButton("Reservar");
            ReserButton.addActionListener(e -> CreateReserva());
            add(ReserButton);
            // ya esta
            JButton SeeClientsButton = new JButton("Ver Clientes");
            SeeClientsButton.addActionListener(e -> VerClientes());
            add(SeeClientsButton);
            // ya esta
            JButton VereservasButton = new JButton("Ver Reservas");
            VereservasButton.addActionListener(e -> VerReservas());
            add(VereservasButton);
            // ya esta
            JButton EditClientButton = new JButton("Editar Clientes");
            EditClientButton.addActionListener(e -> EditClient());
            add(EditClientButton);
            // ya esta
            JButton EditResButton = new JButton("Editar Reservas");
            EditResButton.addActionListener(e -> EditRes());
            add(EditResButton);
            // ya esta
            JButton versim = new JButton("Ver simulaciones");
            versim.addActionListener(e -> VerSim());
            add(versim);
        }

        // Ya esta
        private void VerSim() {
            try {
                String results = ponchito.query("select * from simulacion");
                textArea.setText(results);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Ya esta
        private void EditRes() {
            JDialog editResDialog = new JDialog();
            editResDialog.setTitle("Editar Reserva");
            editResDialog.setLayout(new GridLayout(8, 2));

            JTextField idField = addField(editResDialog, "ID de la reserva:");
            JTextField clienteField = addField(editResDialog, "ID Cliente:");
            JTextField circuitoField = addField(editResDialog, "ID Circuito:");
            JTextField numField = addField(editResDialog, "Num personas:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    boolean resUpdated = ponchito.updateReserva(
                            Integer.parseInt(idField.getText()),
                            Integer.parseInt(clienteField.getText()),
                            Integer.parseInt(circuitoField.getText()),
                            Integer.parseInt(numField.getText()));
                    if (resUpdated) {
                        JOptionPane.showMessageDialog(editResDialog, "Reserva editada!");
                    } else {
                        JOptionPane.showMessageDialog(editResDialog, "Hay algo mal.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(editResDialog, "Hay algo mal.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
            editResDialog.add(saveButton);
            editResDialog.pack();
            editResDialog.setVisible(true);
        }

        // Ya esta
        private void CreateUser() {
            newUserDialog = new JDialog();
            newUserDialog.setTitle("Nuevo Cliente");
            newUserDialog.setLayout(new GridLayout(8, 2));

            nombreField = addField(newUserDialog, "Nombre:");
            apellidopField = addField(newUserDialog, "Apellido Paterno:");
            apellidomField = addField(newUserDialog, "Apellido Materno:");

            String[] tipos = { "grupo", "individual" };
            JComboBox<String> tipo = new JComboBox<>(tipos);
            newUserDialog.add(new JLabel("Tipo:"));
            newUserDialog.add(tipo);

            String[] booleanOptions = { "true", "false" };
            JComboBox<String> agenciaEmpleado = new JComboBox<>(booleanOptions);
            newUserDialog.add(new JLabel("Agencia Empleado:"));
            newUserDialog.add(agenciaEmpleado);

            añoRegistro = addField(newUserDialog, "Año de registro:");

            contraseñaField = addField(newUserDialog, "Contraseña:");

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
                            Integer.parseInt(añoRegistro.getText()),
                            contraseñaField.getText());

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
            reservaDialog.setLayout(new GridLayout(4, 2));

            clienteField = addField(reservaDialog, "ID Cliente:");
            circuitoField = addField(reservaDialog, "ID Fecha Circuito:");
            numField = addField(reservaDialog, "Número de personas:");

            JButton saveButton = new JButton("Guardar");
            saveButton.addActionListener(e -> {
                try {
                    boolean reservaAdded = ponchito.addReserva(
                            Integer.parseInt(clienteField.getText()),
                            Integer.parseInt(circuitoField.getText()),
                            Integer.parseInt(numField.getText()));
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
                String results = ponchito.query("select * from reservacion");
                textArea.setText(results);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        private void EditClient() {
            JDialog editClientDialog = new JDialog();
            editClientDialog.setTitle("Editar Cliente");
            editClientDialog.setLayout(new GridLayout(9, 2));

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
            JTextField contraseñaField = addField(editClientDialog, "Contraseña:");

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
                            Integer.parseInt(añoRegistroField.getText()),
                            contraseñaField.getText());
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