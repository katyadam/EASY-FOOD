package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.business.model.GuidProvider;

import java.util.List;

public class AddedIngredientCrudService implements CrudService<AddedIngredient> {

    private final Repository<AddedIngredient> addedIngredientRepository;
    private final Validator<AddedIngredient> addedIngredientValidator;
    private final GuidProvider guidProvider;

    public AddedIngredientCrudService(
            Repository<AddedIngredient> addedIngredientRepository,
            Validator<AddedIngredient> addedIngredientValidator,
            GuidProvider guidProvider
    ) {
        this.addedIngredientRepository = addedIngredientRepository;
        this.addedIngredientValidator = addedIngredientValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<AddedIngredient> findAll() {
        return addedIngredientRepository.findAll();
    }

    @Override
    public ValidationResult create(AddedIngredient newEntity) {
        ValidationResult validationResult = addedIngredientValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (addedIngredientRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "Ingredient with given guid already exists: \" + newEntity.getGuid()"
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            addedIngredientRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(AddedIngredient entity) {
        ValidationResult validationResult = addedIngredientValidator.validate(entity);
        if (validationResult.isValid()) {
            addedIngredientRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        addedIngredientRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        addedIngredientRepository.deleteAll();
    }
}
