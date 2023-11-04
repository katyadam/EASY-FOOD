package cz.muni.fi.pv168.project.service.export.batch;



import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;

import java.util.Collection;

public record Batch(Collection<Recipe> recipes, Collection<Ingredient> ingredients,
                    Collection<CustomUnit> customUnits, Collection<Category> categories) {
}
