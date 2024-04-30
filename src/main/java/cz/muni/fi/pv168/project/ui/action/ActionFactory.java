package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.service.crud.UserCrudService;
import cz.muni.fi.pv168.project.ui.dialog.LoginDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class ActionFactory {
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final QuitAction quitAction;
    private final ShowAction showAction;

    private final LoginAction loginAction;


    private final LogoutAction logoutAction;
    private final RegisterAction registerAction;
    private final ChangePasswordAction changePasswordAction;

    private final CommonDependencyProvider commonDependencyProvider;

    public ActionFactory(
            JTable recipeTable,
            JTable ingredientsTable,
            JTable unitsTable,
            JTable categoryTable,
            CommonDependencyProvider commonDependencyProvider
    ) {
        this.commonDependencyProvider = commonDependencyProvider;
        this.addAction = new AddAction(recipeTable, ingredientsTable, unitsTable, categoryTable, commonDependencyProvider);
        this.deleteAction = new DeleteAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.editAction = new EditAction(recipeTable, ingredientsTable, unitsTable, categoryTable, commonDependencyProvider);
        this.showAction = new ShowAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.quitAction = new QuitAction();
        this.loginAction =  new LoginAction(commonDependencyProvider);
        this.logoutAction = new LogoutAction(commonDependencyProvider);
        this.registerAction = new RegisterAction(commonDependencyProvider);
        this.changePasswordAction = new ChangePasswordAction(commonDependencyProvider)
        // TODO

    }

    public AddAction getAddAction() {
        return addAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public EditAction getEditAction() {
        return editAction;
    }

    public QuitAction getQuitAction() {
        return quitAction;
    }

    public ShowAction getShowAction() {
        return showAction;
    }

    public LoginAction getLoginAction() {
        return loginAction;
    }

    public LogoutAction getLogoutAction() {
        return logoutAction;
    }

    public void setLogged(boolean logged) {
        addAction.setEnabled(logged);
        editAction.setEnabled(logged);
        deleteAction.setEnabled(logged);
        if (logged)  {
            loginAction.setEnabled(false);
            logoutAction.setEnabled(true);
        }
        else {
            loginAction.setEnabled(true);
            logoutAction.setEnabled(false);
        }
    }

    public RegisterAction getRegisterAction() {
        return null;
    }

    public ChangePasswordAction getChangePasswordAction() {
        return null;
    }
}
