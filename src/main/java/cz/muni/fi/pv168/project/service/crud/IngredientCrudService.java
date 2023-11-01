package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.GuidProvider;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;

import java.util.List;

public class IngredientCrudService implements CrudService<Ingredient> {

    private final Repository<Ingredient> ingredientRepository;
    private final Validator<Ingredient> ingredientValidator;
    private final GuidProvider guidProvider;

    public IngredientCrudService(
            Repository<Ingredient> ingredientRepository,
            Validator<Ingredient> ingredientValidator,
            GuidProvider guidProvider
    ) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientValidator = ingredientValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public ValidationResult create(Ingredient newEntity) {
        ValidationResult validationResult = ingredientValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (ingredientRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "Ingredient with given guid already exists: \" + newEntity.getGuid()"
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            ingredientRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Ingredient entity) {
        ValidationResult validationResult = ingredientValidator.validate(entity);
        if (validationResult.isValid()) {
            ingredientRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        ingredientRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        ingredientRepository.deleteAll();
    }
}
