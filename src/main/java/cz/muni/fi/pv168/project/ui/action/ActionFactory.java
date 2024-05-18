package cz.muni.fi.pv168.project.ui.action;


import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.action.dialogActions.ChangePasswordDialogAction;
import cz.muni.fi.pv168.project.ui.action.dialogActions.LoginDialogAction;
import cz.muni.fi.pv168.project.ui.action.dialogActions.LogoutDialogAction;
import cz.muni.fi.pv168.project.ui.action.dialogActions.RegisterDialogAction;
import cz.muni.fi.pv168.project.ui.action.utilityAction.AddAction;
import cz.muni.fi.pv168.project.ui.action.utilityAction.DeleteAction;
import cz.muni.fi.pv168.project.ui.action.utilityAction.EditAction;
import cz.muni.fi.pv168.project.ui.action.utilityAction.ShowAction;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;

public class ActionFactory {
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final QuitAction quitAction;
    private final ShowAction showAction;

    private final RegisterDialogAction registerDialogAction;
    private final ChangePasswordDialogAction changePasswordDialogAction;
    private final LogoutDialogAction logoutDialogAction;
    private final LoginDialogAction loginDialogAction;


    public ActionFactory(
            JTable recipeTable,
            JTable ingredientsTable,
            JTable unitsTable,
            JTable categoryTable,
            CommonDependencyProvider commonDependencyProvider,
            MainWindow mainWindow
    ) {
        this.addAction = new AddAction(recipeTable, ingredientsTable, unitsTable, categoryTable, commonDependencyProvider);
        this.deleteAction = new DeleteAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.editAction = new EditAction(recipeTable, ingredientsTable, unitsTable, categoryTable, commonDependencyProvider);
        this.showAction = new ShowAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.quitAction = new QuitAction();
        this.logoutDialogAction = new LogoutDialogAction(commonDependencyProvider,mainWindow);
        this.registerDialogAction = new RegisterDialogAction(commonDependencyProvider);
        this.changePasswordDialogAction = new ChangePasswordDialogAction(commonDependencyProvider);
        this.loginDialogAction = new LoginDialogAction(commonDependencyProvider,mainWindow);

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

    public LoginDialogAction getLoginDialogAction() {
        return loginDialogAction;
    }

    public LogoutDialogAction getLogoutDialogAction() {
        return logoutDialogAction;
    }
    public ChangePasswordDialogAction getChangePasswordDialogAction() {
        return changePasswordDialogAction;
    }
    public RegisterDialogAction getRegisterDialogAction() {
        return registerDialogAction;
    }


    public void setLogged(boolean logged) {
        addAction.setEnabled(logged);
        editAction.setEnabled(logged);
        deleteAction.setEnabled(logged);
        if (logged)  {
            loginDialogAction.setEnabled(false);
            logoutDialogAction.setEnabled(true);
            changePasswordDialogAction.setEnabled(true);
            registerDialogAction.setEnabled(false);
        }
        else {
            loginDialogAction.setEnabled(true);
            logoutDialogAction.setEnabled(false);
            changePasswordDialogAction.setEnabled(false);
            registerDialogAction.setEnabled(true);
        }
    }
}
