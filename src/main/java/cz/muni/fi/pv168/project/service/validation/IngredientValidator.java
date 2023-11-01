package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Ingredient;

public class IngredientValidator implements Validator<Ingredient> {
    @Override
    public ValidationResult validate(Ingredient model) {
        return new ValidationResult(); // TODO: validate this Category object
    }
}
