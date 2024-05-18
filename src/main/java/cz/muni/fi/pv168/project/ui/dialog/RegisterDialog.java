package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.*;


public class RegisterDialog extends EntityDialog<RegisteredUser> {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JPasswordField confirmPasswordField;

    private CommonDependencyProvider commonDependencyProvider;

    private RegisteredUser entityUser;

    public RegisterDialog(Frame owner, CommonDependencyProvider commonDependencyProvider) {
        super(new RegisteredUser("", "", "", null), commonDependencyProvider.getUserRepository().findAll());
        this.commonDependencyProvider = commonDependencyProvider;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm password:");
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordLabel);
        add(confirmPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> registerUser());
        add(registerButton);
    }

    private void registerUser() {
        if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0 || confirmPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null, "Username and password and Confirm Password must not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!isStrongPassword(password)) {
            JOptionPane.showMessageDialog(null, "Password is not strong enough", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (commonDependencyProvider.getUserRepository().existsByName(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    @Override
    RegisteredUser getEntity() {

        RegisteredUser entityUser = new RegisteredUser(
                "",
                usernameField.getText(),
                passwordField.getPassword().toString(),
                0L
        );
        return entityUser;
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            return false;
        }
        return true;
    }
}
