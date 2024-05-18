package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.ui.dialog.ChangePasswordDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangePasswordDialogAction extends DialogAction {
    public ChangePasswordDialogAction(CommonDependencyProvider commonDependencyProvider) {
        super(commonDependencyProvider, "Change Password");
    }


    public void actionPerformed(ActionEvent e) {
        ChangePasswordDialog dialog = new ChangePasswordDialog(commonDependencyProvider);
        dialog.setVisible(true);
    }

}
