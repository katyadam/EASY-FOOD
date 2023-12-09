package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;


import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSpace;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateColor;


public class CategoryValidator implements Validator<Category> {
    @Override
    public ValidationResult validate(Category model) {

        if (!validateAlphaNumWhiteSpace(model.getName()).isValid()) {
            return ValidationResult.failed("category validation failed");
        }
        if (!validateColor(model.getColor().toString()).isValid()) {
            return ValidationResult.failed("category validation failed");
        }
        return ValidationResult.success();
    }



}
