package cz.muni.fi.pv168.project.ui.action;

import javax.swing.*;

public class ActionFactory {
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final QuitAction quitAction;

    public ActionFactory(JTable recipeTable, JTable ingredientsTable, JTable unitsTable) {
        this.addAction = new AddAction(recipeTable, ingredientsTable, unitsTable);
        this.deleteAction = new DeleteAction(recipeTable, ingredientsTable, unitsTable);
        this.editAction = new EditAction(recipeTable, ingredientsTable, unitsTable);
        this.quitAction = new QuitAction();
    }

    public AddAction getAddAction() {
        return addAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public EditAction getEditAction() {
        return editAction;
    }

    public QuitAction getQuitAction() {
        return quitAction;
    }
}
