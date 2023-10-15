package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractEntityTableModel<Triplet> {

    public AddedIngredientsTableModel() {
        super(List.of(
                Column.readonly("Ingredient", Ingredient.class, Triplet::getA),
                Column.readonly("amount", double.class, Triplet::getB),
                Column.readonly("Unit", Unit.class, Triplet::getC)
        ), new ArrayList<>());
    }
    
    public boolean contains(Ingredient ingredient) {
        for (Triplet triplet: data ) {
            if ( triplet.getA() == ingredient) {
                return true;
            }
        }
        return false;
    }
}
