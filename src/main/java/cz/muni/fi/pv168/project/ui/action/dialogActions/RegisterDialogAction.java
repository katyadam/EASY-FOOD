package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.dialog.LoginDialog;
import cz.muni.fi.pv168.project.ui.dialog.RegisterDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import java.awt.event.ActionEvent;

public class RegisterDialogAction extends DialogAction {
    public RegisterDialogAction(CommonDependencyProvider commonDependencyProvider) {
        super(commonDependencyProvider,"Register");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var dialog = new RegisterDialog(null, commonDependencyProvider);
        dialog.show(null, "Register Dialog", commonDependencyProvider.getUserValidator())
                .ifPresent(user -> commonDependencyProvider.getUserCrudService().create(user));
    }
}