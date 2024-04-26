package cz.muni.fi.pv168.project.ui.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterDialog(Frame owner) {
        super(owner, "Register", true);
        setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                // TODO: Process the registration credentials
                dispose();
            }
        });
        add(registerButton);

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
}
