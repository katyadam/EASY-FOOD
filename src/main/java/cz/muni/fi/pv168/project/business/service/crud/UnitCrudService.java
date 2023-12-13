package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.DuplicateValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.DataStorageException;

import java.util.List;

public class UnitCrudService implements CrudService<Unit> {

    private final Repository<Unit> unitRepository;
    private final Validator<Unit> customUnitValidator;
    private final GuidProvider guidProvider;
    private final Validator<Unit> unitUsageValidator;

    public UnitCrudService(
            Repository<Unit> unitRepository,
            Validator<Unit> customUnitValidator,
            GuidProvider guidProvider,
            Validator<Unit> unitUsageValidator
    ) {
        this.unitRepository = unitRepository;
        this.customUnitValidator = customUnitValidator
                .and(new DuplicateValidator<>(unitRepository));
        this.guidProvider = guidProvider;
        this.unitUsageValidator = unitUsageValidator;
    }


    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public ValidationResult create(Unit newEntity) {
        ValidationResult validationResult = customUnitValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (unitRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "CustomUnit with given guid already exists: \" + newEntity.getGuid()"
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            unitRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Unit entity) {
        ValidationResult validationResult = customUnitValidator.validate(entity);
        if (validationResult.isValid()) {
            unitRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult deleteByGuid(String guid, boolean userAgreed) {
        Unit toDelete = unitRepository.findByGuid(guid)
                .orElseThrow(
                        () -> new DataStorageException("Unit with guid: " + guid + "not found!")
                );
        ValidationResult validationResult = unitUsageValidator.validate(toDelete);
        if (validationResult.isValid() || userAgreed) {
            unitRepository.deleteByGuid(guid);
        }
        return validationResult;
    }

    @Override
    public void deleteAll() {
        unitRepository.deleteAll();
    }
}
