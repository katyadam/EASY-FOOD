package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.CustomUnit;

public class CustomUnitValidator implements Validator<CustomUnit> {
    @Override
    public ValidationResult validate(CustomUnit model) {
        return new ValidationResult();
    }
}
