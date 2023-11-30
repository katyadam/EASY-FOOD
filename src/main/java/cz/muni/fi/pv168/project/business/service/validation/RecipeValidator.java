package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Recipe;

import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSapce;

public class RecipeValidator implements Validator<Recipe> {
    @Override
    public ValidationResult validate(Recipe model) {
        if (!validateAlphaNumWhiteSapce(model.getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateAlphaNumWhiteSapce(model.getCategoryName()).isValid()) {
            return ValidationResult.failed();
        }
        if (model.getDescription().contains("<") || model.getDescription().contains(">")) {
            return ValidationResult.failed();
        }
        return ValidationResult.success();
    }
}
