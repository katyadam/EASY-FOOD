package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Recipe;

import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSpace;

public class RecipeValidator implements Validator<Recipe> {
    @Override
    public ValidationResult validate(Recipe model) {
        if (model.getCategory() == null) {
            return ValidationResult.failed("category is null");
        }
        if (model.getName().isEmpty()) {
            return ValidationResult.failed("name is empty");
        }
        if (!validateAlphaNumWhiteSpace(model.getName()).isValid()) {
            return ValidationResult.failed("name is not valid");
        }
        if (!validateAlphaNumWhiteSpace(model.getCategoryName()).isValid()) {
            return ValidationResult.failed("category name is not valid");
        }
        if (model.getDescription().contains("<") || model.getDescription().contains(">")) {
            return ValidationResult.failed("description is not valid");
        }
        return ValidationResult.success();
    }
}
