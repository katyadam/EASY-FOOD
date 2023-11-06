package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.AddedIngredient;
import cz.muni.fi.pv168.project.model.Category;

public class AddedIngredientValidator implements Validator<AddedIngredient> {
    @Override
    public ValidationResult validate(AddedIngredient model) {
        if (!StringValidator.validateAlphaNum(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!StringValidator.validateAlphaNum(model.getUnit().getAbbreviation()).isValid()) {
            return ValidationResult.failed();
        }
        if (!StringValidator.validateDouble(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed();
        }

        return ValidationResult.success();
    }

}
