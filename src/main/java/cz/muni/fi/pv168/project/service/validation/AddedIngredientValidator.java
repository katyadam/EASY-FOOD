package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.AddedIngredient;

import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateAlphaNum;
import static cz.muni.fi.pv168.project.service.validation.StringValidator.validateAlphaNumWhiteSapce;


public class AddedIngredientValidator implements Validator<AddedIngredient> {
    @Override
    public ValidationResult validate(AddedIngredient model) {
        if (!validateAlphaNumWhiteSapce(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateAlphaNum(model.getUnit().getAbbreviation()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateAlphaNum(model.getIngredient().getName()).isValid()) {
            return ValidationResult.failed();
        }

        return ValidationResult.success();
    }

}
