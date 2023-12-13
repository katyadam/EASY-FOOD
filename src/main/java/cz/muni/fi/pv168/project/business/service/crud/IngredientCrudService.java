package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.DuplicateValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.DataStorageException;

import java.util.List;

public class IngredientCrudService implements CrudService<Ingredient> {

    private final Repository<Ingredient> ingredientRepository;
    private final Validator<Ingredient> ingredientValidator;
    private final GuidProvider guidProvider;
    private final Validator<Ingredient> ingredientUsageValidator;

    public IngredientCrudService(
            Repository<Ingredient> ingredientRepository,
            Validator<Ingredient> ingredientValidator,
            GuidProvider guidProvider,
            Validator<Ingredient> ingredientUsageValidator) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientValidator = ingredientValidator
                .and(new DuplicateValidator<>(ingredientRepository))
                .and(ingredientUsageValidator);
        this.guidProvider = guidProvider;
        this.ingredientUsageValidator = ingredientUsageValidator;
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
    public ValidationResult deleteByGuid(String guid, boolean userAgreed) {
        Ingredient toDelete = ingredientRepository.findByGuid(guid)
                .orElseThrow(
                        () -> new DataStorageException("Ingredient with guid: " + guid + "not found!")
                );
        ValidationResult validationResult = ingredientUsageValidator.validate(toDelete);
        if (validationResult.isValid() || userAgreed) {
            ingredientRepository.deleteByGuid(guid);
        }
        return validationResult;
    }

    @Override
    public void deleteAll() {
        ingredientRepository.deleteAll();
    }
}
