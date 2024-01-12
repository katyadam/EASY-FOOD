package cz.muni.fi.pv168.project.business.service.validation;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.repository.Repository;

public class CategoryUsageValidator implements Validator<Category> {

    private final Repository<Recipe> recipeRepository;

    public CategoryUsageValidator(
            Repository<Recipe> recipeRepository
    ) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public ValidationResult validate(Category model) {
        int recipesWithThisModel = recipeRepository.findAll().stream()
                .filter(recipe -> recipe.getCategory().getName().equals(model.getName()))
                .toList().size();

        if (recipesWithThisModel > 0) {
            return ValidationResult.failed(
                    String.format("Category: %s is used in %d recipes!", model.getName(), recipesWithThisModel)
            );
        }
        return ValidationResult.success();
    }
}
