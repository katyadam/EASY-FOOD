package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.AddedIngredientCrudService;
import cz.muni.fi.pv168.project.ui.MainWindow;

import java.util.List;


public class AddedIngredientsTableModel extends AbstractEntityTableModel<AddedIngredient> {

    public AddedIngredientsTableModel(
            List<AddedIngredient> addedIngredients,
            AddedIngredientCrudService addedIngredientCrudService
    ) {
        super(List.of(
                Column.readonly("Ingredient", Ingredient.class, AddedIngredient::getIngredient),
                Column.readonly("amount", double.class, AddedIngredient::getQuantity),
                Column.readonly("Unit", Unit.class, AddedIngredient::getUnit)
        ), addedIngredients, addedIngredientCrudService);
    }

    @Override
    public void addRow(AddedIngredient entity) {
        super.getEntities().add(entity);
        fireTableRowsInserted(super.getEntities().size() - 1, super.getEntities().size() - 1);
    }


}
