package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LogoutDialogAction extends DialogAction  {
    public LogoutDialogAction(CommonDependencyProvider commonDependencyProvider) {
        super(commonDependencyProvider, "Log out");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?.", "Confirm",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            commonDependencyProvider.getSession().Logout();
        }
    }
}
