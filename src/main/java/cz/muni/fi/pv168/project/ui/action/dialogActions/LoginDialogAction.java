package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.dialog.LoginDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import java.awt.event.ActionEvent;

public class LoginDialogAction extends DialogAction {
    private final MainWindow mainWindow;
    public LoginDialogAction(CommonDependencyProvider commonDependencyProvider, MainWindow mainWindow) {
        super(commonDependencyProvider,"Log in");
        this.mainWindow = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         var dialog = new LoginDialog(commonDependencyProvider);
         dialog.show(null, "Login Dialog", commonDependencyProvider.getUserValidator())
                 .ifPresent(user -> commonDependencyProvider.getSession().setLoggedUser(user));
         mainWindow.refresh();
    }
}
