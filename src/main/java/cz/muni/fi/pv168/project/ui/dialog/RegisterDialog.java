package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.ui.action.accountActions.RegisterAction;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.*;


public class RegisterDialog extends JDialog {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JPasswordField confirmPasswordField;

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

        JLabel confirmPasswordLabel = new JLabel("Confirm your Password:");
        confirmPasswordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(new RegisterAction(
                    commonDependencyProvider,
                    usernameField,
                    passwordField,
                    confirmPasswordField
                )
        );
        add(registerButton);

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }
}
