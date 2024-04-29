package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;

public class UserValidator implements  Validator<RegisteredUser>{

    @Override
    public ValidationResult validate(RegisteredUser model) {
        // TODO better validation
        if(model.getName().isEmpty()) {
            return ValidationResult.failed("Empty username");
        }
        if (model.getPassword().isEmpty()) {
            return ValidationResult.failed("Empty password");
        }
        return ValidationResult.success();
    }
}
