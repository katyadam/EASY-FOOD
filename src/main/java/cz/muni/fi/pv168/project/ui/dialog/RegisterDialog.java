package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.ui.action.accountActions.RegisterAction;
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
        this.show(registerButton, "Registration", commonDependencyProvider.getUserValidator());
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (commonDependencyProvider.getUserRepository().existsByName(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // TODO hash password and assign uuid? idk how ID works when we have UUID but sure

        commonDependencyProvider.getUserCrudService().create(new RegisteredUser("",username, password, null));

        JOptionPane.showMessageDialog(null, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    @Override
    RegisteredUser getEntity() {
        return entityUser;
    }
}
