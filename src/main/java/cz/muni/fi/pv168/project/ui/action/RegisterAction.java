
package cz.muni.fi.pv168.project.ui.action;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitValidator;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.CustomUnitDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.RegisterDialog;
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


public class RegisterAction extends AbstractAction{
    private CommonDependencyProvider commonDependencyProvider;
    private final CrudService<RegisteredUser> userCrudService;

    public RegisterAction
            (CommonDependencyProvider commonDependencyProvider

             ) {
        super("Register");

        this.commonDependencyProvider = commonDependencyProvider;
        this.userCrudService = commonDependencyProvider.getUserCrudService();
        putValue(SHORT_DESCRIPTION, "Tries to register a user");
    }


    public boolean register(String name, String password) {
        if (commonDependencyProvider.getUserRepository().existsByName(name)) {
            System.out.println("Username already exists in the database.");
            return false;
        }

        if (!isStrongPassword(password)) {
            System.out.println("Password is not strong enough.");
            return false;
        }

        // Create a new account in the database
        // TODO need to hash pw
        try {
            userCrudService.create(new RegisteredUser( null, name, password,null));
        }
        catch (Exception e) {
            System.out.println("Account GUUID already exists.");
            return false;
        }
        System.out.println("Account created successfully.");
        return true;
    }

    private boolean isStrongPassword(String password) {
        if (password.length() <= 8) {
            return false;
        }
        //TODO regex check for some upper case letter and numbers
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //var openRegisterDialog();
        var regDialog = new RegisterDialog(null, commonDependencyProvider);
        regDialog.show();
        var username = "";
        var password = "";
        // TODO get register info from dialog
        var res = register(username, password);

    }
}


