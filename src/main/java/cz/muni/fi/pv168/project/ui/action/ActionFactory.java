package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.data.TestDataGenerator;

import javax.swing.*;

public class ActionFactory {
    private final AbstractAction addAction;
    private final AbstractAction deleteAction;
    private final AbstractAction editAction;
    private final AbstractAction quitAction;

    public ActionFactory(JTable recipeTable, TestDataGenerator testDataGenerator) {
        this.addAction = new AddAction(recipeTable, testDataGenerator);
        this.deleteAction = new DeleteAction(recipeTable);
        this.editAction = new EditAction(recipeTable);
        this.quitAction = new QuitAction();
    }

    public AbstractAction getAddAction() {
        return addAction;
    }

    public AbstractAction getDeleteAction() {
        return deleteAction;
    }

    public AbstractAction getEditAction() {
        return editAction;
    }

    public AbstractAction getQuitAction() {
        return quitAction;
    }
}
