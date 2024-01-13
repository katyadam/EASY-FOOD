package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.CategoryUsageValidator;
import cz.muni.fi.pv168.project.business.service.validation.DuplicateValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.ui.action.TabbedPanelContext;

import javax.swing.*;
import java.util.List;

public class CategoryCrudService implements CrudService<Category> {


    private final Repository<Category> categoryRepository;
    private final Validator<Category> categoryValidator;
    private final Validator<Category> usageValidator;
    private final Validator<Category> duplicityValidator;
    private final GuidProvider guidProvider;

    public CategoryCrudService(
            Repository<Category> categoryRepository,
            Validator<Category> categoryValidator,
            CategoryUsageValidator usageValidator
    ) {
        this.categoryRepository = categoryRepository;
        this.categoryValidator = categoryValidator;
        this.guidProvider = new UuidGuidProvider();
        this.usageValidator = usageValidator;
        this.duplicityValidator = new DuplicateValidator<>(this.categoryRepository);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ValidationResult create(Category newEntity) {
        ValidationResult validationResult = categoryValidator
                .and(duplicityValidator)
                .validate(newEntity);
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
    public ValidationResult deleteByGuid(String guid, boolean userAgreed) {
        Category toDelete = categoryRepository.findByGuid(guid)
                .orElseThrow(
                        () -> new DataStorageException("Category with guid: " + guid + "not found!")
                );
        ValidationResult validationResult = usageValidator.validate(toDelete);
        if (validationResult.isValid()) {
            int confirm = JOptionPane.showOptionDialog(TabbedPanelContext.getActiveTable(),
                    "Confirm",
                    "Delete confirmation",
                    JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,
                    null,null,null);
            if ( confirm != JOptionPane.OK_OPTION) {
                return ValidationResult.failed("denied");
            }
            categoryRepository.deleteByGuid(guid);
        } else {
            int option = JOptionPane.showConfirmDialog(
                    new JPanel(),
                    validationResult + "All recipes with this category will be deleted.",
                    "Are you sure you want to delete?",
                    JOptionPane.YES_NO_OPTION
            );
            if (option == JOptionPane.YES_OPTION) {
                categoryRepository.deleteByGuid(guid);
                return ValidationResult.success();
            }
        }

        return validationResult;
    }

    @Override
    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
