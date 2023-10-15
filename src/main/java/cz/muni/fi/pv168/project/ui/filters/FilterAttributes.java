package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;

public record FilterAttributes(
        Ingredient ingredient,
        Category category,
        Integer calMin,
        Integer calMax,
        Integer portionsMin,
        Integer portionsMax
) {
}
