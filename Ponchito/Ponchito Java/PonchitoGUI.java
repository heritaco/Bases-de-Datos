import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class PonchitoGUI {
    private JButton button0, button1, button2, button3, button4, button5;
    private JPanel panel1;
    private JTextArea textArea;
    private Ponchito ponchito;
    private JLabel welcomeLabel;

    private JButton adminButton;
    private JDialog newUserDialog;
    private JTextField nombreField, apellidoField, circuitoField, salidaField;
    private String adminPassword = "admin123"; // You should store passwords securely, not like this

    public PonchitoGUI() {
        panel1 = new JPanel();
        textArea = new JTextArea(15, 30);
        panel1.add(textArea);

        welcomeLabel = new JLabel("Welcome to Ponchito Viajes!!");
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
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog("Enter password");
                if (adminPassword.equals(password)) {
                    openNewUserDialog();
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password");
                }
            }
        });
        panel1.add(adminButton);
    }

    private JTextField addField(JDialog dialog, String labelText) {
        dialog.add(new JLabel(labelText));
        JTextField field = new JTextField();
        dialog.add(field);
        return field;
    }

    private void openNewUserDialog() {
        newUserDialog = new JDialog();
        newUserDialog.setTitle("New User");
        newUserDialog.setLayout(new GridLayout(5, 2));

        nombreField = addField(newUserDialog, "Nombre:");
        apellidoField = addField(newUserDialog, "Apellido:");
        circuitoField = addField(newUserDialog, "Circuito:");
        salidaField = addField(newUserDialog, "Salida:");

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Ponchito db = new Ponchito();
                db.addClient(nombreField.getText(), apellidoField.getText(), circuitoField.getText(),
                        salidaField.getText());
                newUserDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        newUserDialog.add(saveButton);

        newUserDialog.pack();
        newUserDialog.setVisible(true);
    }

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("PonchitoGUI");
        PonchitoGUI ponchitoGUI = new PonchitoGUI();
        frame.setContentPane(ponchitoGUI.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}