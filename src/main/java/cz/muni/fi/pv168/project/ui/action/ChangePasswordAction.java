
package cz.muni.fi.pv168.project.ui.action;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.UserCrudService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitValidator;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.CustomUnitDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Optional;


public class ChangePasswordAction extends AbstractAction{


    private final JPasswordField oldPasswordField;

    private final JPasswordField newPasswordField;

    private final JPasswordField confirmNewPasswordField;
    private CommonDependencyProvider commonDependencyProvider;
    private final CrudService<RegisteredUser> userCrudService;

    public ChangePasswordAction
            (CommonDependencyProvider commonDependencyProvider,
             JPasswordField oldPasswordField,
             JPasswordField newPasswordField,
             JPasswordField confirmNewPasswordField
            ) {
        super("Register");
        this.oldPasswordField = oldPasswordField;
        this.newPasswordField = newPasswordField;
        this.confirmNewPasswordField = confirmNewPasswordField;
        this.commonDependencyProvider = commonDependencyProvider;
        this.userCrudService = commonDependencyProvider.getUserCrudService();
        putValue(SHORT_DESCRIPTION, "Tries to register a user");
    }


    public boolean changePassword(char[] oldPassword, char[] newPassword, char[] confirmPassword) {
        var session = commonDependencyProvider.getSession();
        if (!session.isLoggedIn()) {
            System.out.println("Noone is logged in, this shouldn't happen!");
            return false;
        }

        var user = session.getLoggedUser();

        var foundUser = commonDependencyProvider.getUserRepository().findById(user.getID());
        if (!foundUser.isPresent()) {
            System.out.println("User in session not found, this shouldn't happen!");
            return false;
        }

        var oldHashPass = foundUser.get().getPassword();

        var fieldOldHashPass = UserCrudService.hashPassword(oldPassword.toString());

        if (!oldHashPass.equals(fieldOldHashPass)) {
            System.out.println("Wrong password!");
            return false;
        }

        if (!isStrongPassword(newPassword)) {
            System.out.println("Password is not strong enough.");
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            return false;
        }

        var newHash = UserCrudService.hashPassword(newPassword.toString());
        // bcs session should not hold hashed password
        var updateUser = new RegisteredUser(user.getGuid(), user.getName(), newHash, user.getID());

        try {
            userCrudService.update(updateUser);
        }
        catch (Exception e) {
            System.out.println("Account GUUID already exists.");
            return false;
        }
        System.out.println("Account created successfully.");
        return true;
        // TODO return strings maybe
    }

    private boolean isStrongPassword(char[] password) {
        if (password.length <= 8) {
            return false;
        }
        //TODO regex check for some upper case letter and numbers
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var res = changePassword(oldPasswordField.getPassword(), newPasswordField.getPassword(), confirmNewPasswordField.getPassword());
        if (res) {
            System.out.println("Updated password");
            // TODO throw some panel at user saying his password has changed...
        }
        System.out.println("Not updated");
        // TODO throw some panel at user that will have some text with error...
    }
}


