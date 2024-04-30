
package cz.muni.fi.pv168.project.ui.action.accountActions;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.dialog.RegisterDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class RegisterAction extends AbstractAction{
    private CommonDependencyProvider commonDependencyProvider;
    private final CrudService<RegisteredUser> userCrudService;

    private final JTextField usernameField;

    private final JPasswordField passwordField;
    private final JPasswordField confirmPasswordField;

    public RegisterAction
            (CommonDependencyProvider commonDependencyProvider,
             JTextField usernameField,
             JPasswordField passwordField,
             JPasswordField confirmPasswordField

             ) {
        super("Register");

        this.commonDependencyProvider = commonDependencyProvider;
        this.userCrudService = commonDependencyProvider.getUserCrudService();

        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
        putValue(SHORT_DESCRIPTION, "Tries to register a user");
    }


    public void register() {
        if(!checkFields()) {
            return;
        }
        if(!userExists()) {
            clearAllFields();
            return;
        }
        if(!isValidPassword()) {
            clearPassFields();
            return;
        }
        if(!confirmPassword()) {
            clearPassFields();
            return;
        }
        if(!createUser()) {
            clearPassFields();
            return;
        }

        // throw window created succesfully
        System.out.println("Account created successfully.");
        clearAllFields();
    }

    private boolean createUser() {
        var username = usernameField.getText();
        var password = passwordField.getPassword().toString();
        try {
            userCrudService.create(new RegisteredUser( null, username, password,null));
            return true;
        }
        catch (Exception e) {
            // throw window system error
            return false;
        }
    }

    private boolean confirmPassword() {
        var password = passwordField.getPassword().toString();
        var confirm = confirmPasswordField.getPassword().toString();
        if (!password.equals(confirm)) {

            //throw
            return false;
        }
        return true;
    }

    private boolean isValidPassword() {
        var password = passwordField.getPassword().toString();
        if (password.length() <= 8) {
            // throw window
            return false;
        }
        return true;
    }

    private boolean userExists() {
        var username = usernameField.getText();
        if (commonDependencyProvider.getUserRepository().existsByName(username)) {
            //throw
            System.out.println("Username already exists in the database.");
            return false;
        }
        return true;
    }

    private boolean checkFields() {
        if (usernameField.getText().isEmpty()) {
            // throw window
            return false;
        }
        if (passwordField.getPassword().length == 0) {
            // throw window
            return false;
        }
        if (confirmPasswordField.getPassword().length == 0) {
            // throw window
            return false;
        }
        return true;
    }

    private void clearAllFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void clearPassFields() {
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        register();
    }
}


