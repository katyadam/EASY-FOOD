package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;

public class UserValidator implements  Validator<RegisteredUser>{

    @Override
    public ValidationResult validate(RegisteredUser model) {
        // future validator
        if(model.getName().isEmpty()) {
            return ValidationResult.failed("Empty username");
        }
        if (model.getPassword().isEmpty()) {
            return ValidationResult.failed("Empty password");
        }
        /*if (!isStrongPassword(model.getPassword())) {
            return ValidationResult.failed("Password is not strong enough");
        }*/
        return ValidationResult.success();
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[0-9].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            return false;
        }
        return true;
    }
}
