package cryptoTrader.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cryptoTrader.utils.DataUtils.DataExecutor;
import cryptoTrader.utils.LoginServices.Authenticate;
import cryptoTrader.utils.LoginServices.Register;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame implements ActionListener, DocumentListener {
    private static LoginUI instance;
    private static JTextField nameIn;
    private static JPasswordField passIn;
    private static JButton enter;
    private static JButton register;
    private static JButton mask;

    public static LoginUI newLogin() {
        if (instance == null) {
            synchronized (LoginUI.class) {
                if (instance == null) {
                    instance = new LoginUI();
                }
            }
        }
        return instance;
    }

    private LoginUI() {
        super("Authentication");

        JPanel panel = new JPanel();
        super.add(panel);

        panel.setLayout(null);

        JLabel label = new JLabel("Username");
        label.setBounds(20, 20, 80, 25);
        panel.add(label);

        nameIn = new JTextField();
        nameIn.setBounds(100, 20, 165, 25);
        nameIn.getDocument().addDocumentListener(this);
        panel.add(nameIn);

        JLabel pass = new JLabel("Password");
        pass.setBounds(20, 60, 80, 25);
        panel.add(pass);

        passIn = new JPasswordField();
        passIn.setBounds(100, 60, 165, 25);
        passIn.getDocument().addDocumentListener(this);
        panel.add(passIn);

        mask = new JButton("Show");
        mask.setBounds(100, 100, 80, 25);
        mask.addActionListener(this);
        mask.setEnabled(false);
        panel.add(mask);

        enter = new JButton("Login");
        enter.setBounds(100, 140, 80, 25);
        enter.addActionListener(this);
        enter.setEnabled(false);
        panel.add(enter);

        register = new JButton("Register");
        register.setBounds(95, 180, 90, 25);
        register.addActionListener(this);
        register.setEnabled(false);
        panel.add(register);
        
        super.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        JFrame frame = LoginUI.newLogin();
        frame.setSize(300, 270);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DataExecutor executor = new DataExecutor();
        String name = nameIn.getText();
        String pass = new String(passIn.getPassword());
        boolean result;
        int input;

        if (e.getActionCommand().equals("Register")) {
            result = executor.execute(new Register(name, pass));
            if (result) {
                input = JOptionPane.showConfirmDialog(null,
                        "Successfully registered " + name + ".", "Welcome", JOptionPane.DEFAULT_OPTION);
            } else {
                input = JOptionPane.showConfirmDialog(null,
                        name + " already exists.", "Error", JOptionPane.DEFAULT_OPTION);
            }
        } else if (e.getActionCommand().equals("Login")) {
            result = executor.execute(new Authenticate(nameIn.getText(), pass));
            if (result) {
                // update.setText("Success");
            } else {
                input = JOptionPane.showConfirmDialog(null,
                        "Incorrect credentials. The app will now terminate.", "Error", JOptionPane.DEFAULT_OPTION);
                if (input == 0) {
                    dispose();
                }
            }
        } else if (e.getActionCommand().equals("Show")) {
            passIn.setEchoChar((char) 0);
            mask.setText("Hide");
        } else {
            passIn.setEchoChar('*');
            mask.setText("Show");
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changed();

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changed();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changed();
    }

    private void changed() {
        if (passIn.getPassword().length == 0 || nameIn.getText().equals("")) {
            enter.setEnabled(false);
            register.setEnabled(false);
        } else {
            enter.setEnabled(true);
            register.setEnabled(true);
        }

        if (passIn.getPassword().length == 0) {
            mask.setEnabled(false);
        } else {
            mask.setEnabled(true);
        }
    }
}