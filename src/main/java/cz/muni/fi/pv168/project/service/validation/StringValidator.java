package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Category;

public class StringValidator {
    public static ValidationResult validateLength(String string, int min, int max) {
        var result = new ValidationResult();
        var length = string.length();

        if ( min > length ||  length > max ) {
            result.add("'%s' length is not between %d (inclusive) and %d (inclusive)"
                    .formatted(string, min, max)
            );
        }

        return result;
    }

    public static ValidationResult validateAlphaNum (String string) {
        if (string.matches("^[a-zA-Z0-9]*$")) {
            return ValidationResult.success();
        }
        return ValidationResult.failed();
    }

    public static ValidationResult validateDouble(String string) {
        // 1.23e-4, 3.14159, 42.0
        if (string.matches("[-+]?(\\d*\\.\\d+|\\d+\\.\\d*|\\d+)([eE][-+]?)?")) {
            return ValidationResult.success();
        }
        return ValidationResult.failed();
    }
}
