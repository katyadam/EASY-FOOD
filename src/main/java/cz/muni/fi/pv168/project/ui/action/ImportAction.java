package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.service.crud.CustomUnitService;
import cz.muni.fi.pv168.project.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.service.crud.RecipeCrudService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ImportAction extends AbstractAction {

    protected final CategoryCrudService categoryCrudService;
    protected final CustomUnitService customUnitService;
    protected final IngredientCrudService ingredientCrudService;
    protected final RecipeCrudService recipeCrudService;
    public ImportAction(String name, CategoryCrudService categoryCrudService, CustomUnitService customUnitService, IngredientCrudService ingredientCrudService, RecipeCrudService recipeCrudService) {
        super(name);
        this.categoryCrudService = categoryCrudService;
        this.customUnitService = customUnitService;
        this.ingredientCrudService = ingredientCrudService;
        this.recipeCrudService = recipeCrudService;
        putValue(SHORT_DESCRIPTION, "Imports from an XML file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        return;
    }
}
