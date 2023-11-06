package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Category;

public class CategoryValidator implements Validator<Category> {
    @Override
    public ValidationResult validate(Category model) {

        if (!StringValidator.validateAlphaNum(model.getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateColor(model).isValid()) {
            return ValidationResult.failed();
        }
        return ValidationResult.success();
    }

    private ValidationResult validateColor(Category model) {
        if (model.getColor().toString().matches("^\\d{1,3},\\d{1,3},\\d{1,3}$")) {
            return ValidationResult.success();
        }
        return ValidationResult.failed();
    }

}
