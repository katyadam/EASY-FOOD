package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractEntityTableModel<AddedIngredient> {

    private Integer totalNutritionalValue = 0;
    private final CrudService<AddedIngredient> addedIngredientCrudService;

    public AddedIngredientsTableModel(CrudService<AddedIngredient> addedIngredientCrudService) {
        super(List.of(
                Column.readonly("Ingredient", Ingredient.class, AddedIngredient::getIngredient),
                Column.readonly("amount", double.class, AddedIngredient::getQuantity),
                Column.readonly("Unit", Unit.class, AddedIngredient::getUnit)
        ), new ArrayList<>(), addedIngredientCrudService);
        this.addedIngredientCrudService = addedIngredientCrudService;
    }

    public int getTotalNutritionalValue() {
        return totalNutritionalValue;
    }

    @Override
    public void addRow(AddedIngredient entity) {
        totalNutritionalValue += entity.getIngredient().getNutritionalValue();
        super.addRow(entity);
    }

    public boolean contains(Ingredient ingredient) {
        return addedIngredientCrudService.findAll().stream()
                .map(AddedIngredient::getIngredient)
                .anyMatch(i -> i == ingredient);
    }
}
