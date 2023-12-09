package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import java.util.List;

public class RecipeCrudService implements CrudService<Recipe> {

    private final Repository<Recipe> recipeRepository;
    private final RecipeValidator recipeValidator;
    private final GuidProvider guidProvider;
    private final CrudService<AddedIngredient> addedIngredientCrudService;

    public RecipeCrudService(
            Repository<Recipe> recipeRepository,
            RecipeValidator recipeValidator,
            GuidProvider guidProvider,
            CrudService<AddedIngredient> addedIngredientCrudService) {
        this.recipeRepository = recipeRepository;
        this.recipeValidator = recipeValidator;
        this.guidProvider = guidProvider;
        this.addedIngredientCrudService = addedIngredientCrudService;
    }


    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public ValidationResult create(Recipe newEntity) {
        ValidationResult validationResult = recipeValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (recipeRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException("Recipe with given guid already exists: " + newEntity.getGuid());
        }
        if (validationResult.isValid()) {
            recipeRepository.create(newEntity);
//            Setting this new recipe to its added ingredients
            newEntity.getAddedIngredients().forEach(
                    addedIngredient -> addedIngredient.setRecipe(newEntity)
            );
//            Creating added ingredient via their service
            newEntity.getAddedIngredients().forEach(addedIngredientCrudService::create);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Recipe entity) {
        ValidationResult validationResult = recipeValidator.validate(entity);
        if (validationResult.isValid()) {
            recipeRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeRepository.deleteAll();
    }
}
