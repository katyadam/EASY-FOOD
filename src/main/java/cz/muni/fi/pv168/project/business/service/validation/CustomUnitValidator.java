package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Unit;

import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNum;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateAlphaNumWhiteSapce;
import static cz.muni.fi.pv168.project.business.service.validation.StringValidator.validateDouble;

public class CustomUnitValidator implements Validator<Unit> {
    @Override
    public ValidationResult validate(Unit model) {


        if (!validateAlphaNumWhiteSapce(model.getName()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateAlphaNum(model.getAbbreviation()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateDouble(model.getBaseAmountNumber()).isValid()) {
            return ValidationResult.failed();
        }
        if (!validateAlphaNum(model.getBaseUnit().getAbbreviation()).isValid()) {
            return ValidationResult.failed();
        }

        return ValidationResult.success();
    }

}
