package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import java.awt.event.ActionEvent;

public class LogoutDialogAction extends DialogAction  {
    public LogoutDialogAction(CommonDependencyProvider commonDependencyProvider) {
        super(commonDependencyProvider);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO ask user if he really wants to log out
    }
}
