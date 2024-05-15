package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.dialog.LoginDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import java.awt.event.ActionEvent;

public class LoginDialogAction extends DialogAction {
    public LoginDialogAction(CommonDependencyProvider commonDependencyProvider) {
        super(commonDependencyProvider,"Log in");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         var dialog = new LoginDialog(commonDependencyProvider);
         dialog.show(null, "Login Dialog", commonDependencyProvider.getUserValidator());
    }
}
