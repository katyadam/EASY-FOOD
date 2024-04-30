package cz.muni.fi.pv168.project.ui.action.accountActions;

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


public class LogoutAction extends AbstractAction {
    private CommonDependencyProvider commonDependencyProvider;

    public LogoutAction(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
    }

    public void logout(){

        commonDependencyProvider.getSession().Logout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logout();
        // TODO mby dialog with DO YOU WANN LOGOUT?
    }
}
