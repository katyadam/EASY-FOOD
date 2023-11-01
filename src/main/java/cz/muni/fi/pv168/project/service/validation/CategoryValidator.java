package cz.muni.fi.pv168.project.service.validation;

import cz.muni.fi.pv168.project.model.Category;

public class CategoryValidator implements Validator<Category> {
    @Override
    public ValidationResult validate(Category model) {
        return new ValidationResult(); // TODO: validate this Category object
    }
}
