package cz.muni.fi.pv168.project.ui.dialog;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;


public class LoginDialog extends EntityDialog<RegisteredUser> {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private CommonDependencyProvider commonDependencyProvider;

    public LoginDialog( CommonDependencyProvider commonDependencyProvider) {
        super(new RegisteredUser("", "", ""), Collections.emptyList());
        this.commonDependencyProvider = commonDependencyProvider;

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
                entity.setName(usernameField.getText());
                entity.setPassword(passwordField.getPassword().toString());
            }
        });
        add(loginButton);

        JButton registerButton = new JButton("Register"); 
        registerButton.addActionListener(e -> {
                RegisterDialog registerDialog = new RegisterDialog(new Frame(), commonDependencyProvider);
                registerDialog.setVisible(true);
        });
        add(registerButton);

    }

    @Override
    RegisteredUser getEntity() {
        return null;
    }
}
