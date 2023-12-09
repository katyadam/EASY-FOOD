package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;


public class AddedIngredientValidator implements Validator<AddedIngredient> {
    @Override
    public ValidationResult validate(AddedIngredient model) {
        if (!StringValidator.validateAlphaNumWhiteSpace(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed("added ingredient validation failed");
        }
        if (!StringValidator.validateAlphaNum(model.getUnit().getAbbreviation()).isValid()) {
            return ValidationResult.failed("added ingredient validation failed");
        }
        if (!StringValidator.validateAlphaNum(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed("added ingredient validation failed");
        }

        return ValidationResult.success();
    }

}
