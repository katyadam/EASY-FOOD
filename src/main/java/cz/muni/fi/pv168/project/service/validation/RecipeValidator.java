package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Recipe;

import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateAlphaNum;
import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateAlphaNumWhiteSapce;
import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateDouble;
import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateNum;

public class RecipeValidator implements Validator<Recipe> {
    @Override
    public ValidationResult validate(Recipe model) {
        if (!validateAlphaNumWhiteSapce(model.getRecipeName()).isValid()) {
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
