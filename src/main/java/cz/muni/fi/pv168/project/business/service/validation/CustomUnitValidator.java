package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Unit;

import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNum;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSpace;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateDouble;

public class CustomUnitValidator implements Validator<Unit> {
    @Override
    public ValidationResult validate(Unit model) {


        if (!validateAlphaNumWhiteSpace(model.getName()).isValid()) {
            return ValidationResult.failed("unit validation failed");
        }
        if (!validateAlphaNum(model.getAbbreviation()).isValid()) {
            return ValidationResult.failed("unit validation failed");
        }
        if (!validateDouble(model.getBaseAmountNumber()).isValid()) {
            return ValidationResult.failed("unit validation failed");
        }
        if (!validateAlphaNum(model.getBaseUnit().getAbbreviation()).isValid()) {
            return ValidationResult.failed("unit validation failed");
        }

        return ValidationResult.success();
    }

}
