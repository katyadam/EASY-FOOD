package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.data.TestDataGenerator;

import javax.swing.*;

public class ActionFactory {
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final QuitAction quitAction;

    public ActionFactory(JTable recipeTable, JTable ingredientsTable,JTable unitsTable) {
        this.addAction = new AddAction(recipeTable,ingredientsTable,unitsTable);
        this.deleteAction = new DeleteAction(recipeTable,ingredientsTable,unitsTable);
        this.editAction = new EditAction(recipeTable,ingredientsTable,unitsTable);
        this.quitAction = new QuitAction();
    }

    public AddAction getAddAction() {
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
