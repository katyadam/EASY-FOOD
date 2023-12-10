package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Recipe;

import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSpace;

public class RecipeValidator implements Validator<Recipe> {
    @Override
    public ValidationResult validate(Recipe model) {
        if (model.getCategory() == null) {
            return ValidationResult.failed("Category is empty!");
        }
        if (model.getName().isEmpty()) {
            return ValidationResult.failed("Name is empty!");
        }
        if (!validateAlphaNumWhiteSpace(model.getName()).isValid()) {
            return ValidationResult.failed("Name format is invalid!");
        }
        if (!validateAlphaNumWhiteSpace(model.getCategoryName()).isValid()) {
            return ValidationResult.failed("Category name format is invalid!");
        }
        if (model.getDescription().contains("<") || model.getDescription().contains(">")) {
            return ValidationResult.failed("Description format is invalid!");
        }
        return ValidationResult.success();
    }
}
