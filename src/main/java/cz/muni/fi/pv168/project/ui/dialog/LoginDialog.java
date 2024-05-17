package cz.muni.fi.pv168.project.ui.dialog;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;


public class LoginDialog extends EntityDialog<RegisteredUser> {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private CommonDependencyProvider commonDependencyProvider;

    private RegisteredUser entityUser;

    public LoginDialog( CommonDependencyProvider commonDependencyProvider) {
        super(new RegisteredUser("", "", "", null), Collections.emptyList());
        this.commonDependencyProvider = commonDependencyProvider;

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        JButton loginButton = getLoginButton();
        add(loginButton);

        JButton registerButton = new JButton("Register"); 
        registerButton.addActionListener(commonDependencyProvider.getActionFactory().getRegisterDialogAction()::actionPerformed);
        add(registerButton);

    }

    private JButton getLoginButton() {
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var username = usernameField.getText();
                var password = new String(passwordField.getPassword());
                // TODO: hash password
                var user = commonDependencyProvider.getUserRepository().existByLogin(username, password);

                if (!user.isPresent()) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                commonDependencyProvider.getSession().setLoggedUser(user.get());
                entityUser = user.get();
            }
        });
        return loginButton;
    }

    @Override
    RegisteredUser getEntity() {
        return entityUser;
    }
}
