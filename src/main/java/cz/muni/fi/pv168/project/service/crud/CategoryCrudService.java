package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.GuidProvider;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;

import java.util.List;

public class CategoryCrudService implements CrudService<Category> {

    private final Repository<Category> categoryRepository;
    private final Validator<Category> categoryValidator;
    private final GuidProvider guidProvider;

    public CategoryCrudService(
            Repository<Category> categoryRepository,
            Validator<Category> categoryValidator,
            GuidProvider guidProvider
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
        this.guidProvider = guidProvider;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ValidationResult create(Category newEntity) {
        ValidationResult validationResult = categoryValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (categoryRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "Category with given guid already exists: \" + newEntity.getGuid()"
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            categoryRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Category entity) {
        ValidationResult validationResult = categoryValidator.validate(entity);
        if (validationResult.isValid()) {
            categoryRepository.update(entity);
        }
        
        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        categoryRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
