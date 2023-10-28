package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractEntityTableModel<Triplet> {

    public AddedIngredientsTableModel() {
        super(List.of(
                Column.readonly("Ingredient", Ingredient.class, Triplet::ingredient),
                Column.readonly("amount", double.class, Triplet::value),
                Column.readonly("Unit", Unit.class, Triplet::unit)
        ), new ArrayList<>());
    }

    public boolean contains(Ingredient ingredient) {
        for (Triplet triplet : data) {
            if (triplet.ingredient() == ingredient) {
                return true;
            }
        }
        return false;
    }
}
