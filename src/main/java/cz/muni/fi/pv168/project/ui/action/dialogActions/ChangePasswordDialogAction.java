package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.ui.dialog.ChangePasswordDialog;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ChangePasswordDialogAction extends JDialog {

    private final CommonDependencyProvider commonDependencyProvider;
    public ChangePasswordDialogAction(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
    }

    public void actionPerformed(ActionEvent e) {
        ChangePasswordDialog dialog = new ChangePasswordDialog(commonDependencyProvider);
        dialog.setVisible(true);
    }

}
