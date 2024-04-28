package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.action.RegisterAction;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private CommonDependencyProvider commonDependencyProvider;

    public RegisterDialog(Frame owner, CommonDependencyProvider commonDependencyProvider) {
        super(owner, "Register", true);
        this.commonDependencyProvider = commonDependencyProvider;
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
        registerButton.addActionListener(new RegisterAction(
                    commonDependencyProvider,
                    usernameField,
                    passwordField
                )
        );
        add(registerButton);

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
}
