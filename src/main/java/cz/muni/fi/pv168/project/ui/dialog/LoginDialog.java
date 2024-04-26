package cz.muni.fi.pv168.project.ui.dialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class LoginDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        setLayout(new GridLayout(4, 2)); // Increase the grid layout to accommodate the new button

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // TODO: Process the login credentials
                dispose();
            }
        });
        add(loginButton);

        JButton registerButton = new JButton("Register"); 
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Handle the Register button action
            }
        });
        add(registerButton);

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
}
