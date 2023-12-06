package cz.muni.fi.pv168.project.business.service.validation;


import cz.muni.fi.pv168.project.business.model.Ingredient;

public class IngredientValidator implements Validator<Ingredient> {
    @Override
    public ValidationResult validate(Ingredient model) {
        if (!StringValidator.validateAlphaNumWhiteSapce(model.getName()).isValid()) {
            return ValidationResult.failed();
        }
//        if (!StringValidator.validateAlphaNum(model.getUnitType().getAbbreviation()).isValid()) {
//            return ValidationResult.failed();
//        }
//        has to be validated before making ingredient
//        if (!StringValidator.validateNum(model.getNutritionalValue()).isValid()) {
//            return ValidationResult.failed();
//        }

        return ValidationResult.success();
    }
}
