package cz.muni.fi.pv168.project.ui.action.dialogActions;

import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;


import javax.swing.*;


public abstract class DialogAction extends AbstractAction {
    protected final CommonDependencyProvider commonDependencyProvider;

    public DialogAction(CommonDependencyProvider commonDependencyProvider) {
        this.commonDependencyProvider = commonDependencyProvider;
    }

}
