package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.service.crud.UserCrudService;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.ui.dialog.LoginDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.*;

import java.util.List;
import java.util.Optional;


public class LoginAction extends AbstractAction {
    private UserCrudService userCrudService;
    private CommonDependencyProvider commonDependencyProvider;

    public LoginAction(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
        this.userCrudService = (UserCrudService) commonDependencyProvider.getUserCrudService();
    }

    public boolean login(String username, String password) {
        if (!userCrudService.usernameExists(username)) {
            // TODO
            return false;
        }

        var res =  userCrudService.login(username, password);

        if (res.isPresent()) {
            commonDependencyProvider.getSession().setLoggedUser(res.get());
            return true;
        }
        //TODO do info to user here
        return false; // Incorrect username or password
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new LoginDialog(commonDependencyProvider);
    }
}