package cz.muni.fi.pv168.project.ui.action;

import javax.swing.*;

public class ActionFactory {
    private final AddAction addAction;
    private final DeleteAction deleteAction;
    private final EditAction editAction;
    private final QuitAction quitAction;
    private final ShowAction showAction;

    public ActionFactory(JTable recipeTable, JTable ingredientsTable, JTable unitsTable, JTable categoryTable) {
        this.addAction = new AddAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.deleteAction = new DeleteAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.editAction = new EditAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
        this.showAction = new ShowAction(recipeTable, ingredientsTable, unitsTable, categoryTable);
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

    public ShowAction getShowAction() {
        return showAction;
    }
}
