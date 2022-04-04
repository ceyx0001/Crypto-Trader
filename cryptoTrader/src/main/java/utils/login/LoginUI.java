package utils.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.MVC.TradeController;

/**
 * This class implements the login functionality of the system.
 * Namely the security, user registration, and event handling of the user login
 * use case.
 * 
 * @author Jun Shao
 * @date 2022-03-30
 */
public class LoginUI extends JFrame implements ActionListener, DocumentListener {
    private JTextField nameIn;
    private JPasswordField passIn;
    private JButton enter;
    private JButton register;
    private JButton mask;

    /**
     * Constructor method for the GUI
     */
    public LoginUI() {
        super("Authentication");
        setSize(300, 270);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        super.add(panel);

        panel.setLayout(null);

        // creating and adding components
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

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * actionPerformed handles the logic for action events
     * 
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameIn.getText();
        String pass = new String(passIn.getPassword());
        boolean result;
        int input;
        UserOperation action = new UserOperation(name, pass);

        if (e.getActionCommand().equals("Register")) { // register a new user
            result = action.saveUser();
            if (result) {
                input = JOptionPane.showConfirmDialog(null,
                        "Successfully registered " + name + ".", "Welcome", JOptionPane.DEFAULT_OPTION);
            } else {
                input = JOptionPane.showConfirmDialog(null,
                        name + " already exists.", "Error", JOptionPane.DEFAULT_OPTION);
            }
        } else if (e.getActionCommand().equals("Login")) { // start auth process
            result = action.authenticate();
            if (result) {
                new TradeController();
                dispose();
            } else {
                input = JOptionPane.showConfirmDialog(null,
                        "Incorrect credentials. The app will now terminate.", "Error", JOptionPane.DEFAULT_OPTION);
                if (input == 0 || input == -1) {
                    dispose();
                }
            }
        } else if (e.getActionCommand().equals("Show")) { // show password field
            passIn.setEchoChar((char) 0);
            mask.setText("Hide");
        } else {
            passIn.setEchoChar('*');
            mask.setText("Show");
        }
    }

    /**
     * Gives notification that there was an insert into the document
     * 
     * @param e the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        update();

    }

    /**
     * Gives notification that a portion of the document has been removed
     * 
     * @param e the document event
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    /**
     * Gives notification that an attribute or set of attributes changed
     * 
     * @param e the document event
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }

    /**
     * Handles the greying out the GUI buttons
     */
    private void update() {
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