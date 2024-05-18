package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LogoutDialogAction extends DialogAction  {
    private final MainWindow mainWindow;
    public LogoutDialogAction(CommonDependencyProvider commonDependencyProvider, MainWindow mainWindow) {
        super(commonDependencyProvider, "Log out");
        this.mainWindow = mainWindow;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?.", "Confirm",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            commonDependencyProvider.getSession().Logout();
        }
        mainWindow.refresh();
    }
}
