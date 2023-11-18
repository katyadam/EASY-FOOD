package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;


import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSapce;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateColor;


public class CategoryValidator implements Validator<Category> {
    @Override
    public ValidationResult validate(Category model) {

        if (!validateAlphaNumWhiteSapce(model.getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateColor(model.getColor().toString()).isValid()) {
            return ValidationResult.failed();
        }
        return ValidationResult.success();
    }



}
