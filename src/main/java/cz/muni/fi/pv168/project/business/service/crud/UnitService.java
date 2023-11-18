package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;

import java.util.List;

public class UnitService implements CrudService<Unit> {

    private final Repository<Unit> customUnitRepository;
    private final Validator<Unit> customUnitValidator;
    private final GuidProvider guidProvider;

    public UnitService(
            Repository<Unit> customUnitRepository,
            Validator<Unit> customUnitValidator,
            GuidProvider guidProvider
    ) {
        this.customUnitRepository = customUnitRepository;
        this.customUnitValidator = customUnitValidator;
        this.guidProvider = guidProvider;
    }


    @Override
    public List<Unit> findAll() {
        return customUnitRepository.findAll();
    }

    @Override
    public ValidationResult create(Unit newEntity) {
        ValidationResult validationResult = customUnitValidator.validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (customUnitRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "CustomUnit with given guid already exists: \" + newEntity.getGuid()"
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            customUnitRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(Unit entity) {
        ValidationResult validationResult = customUnitValidator.validate(entity);
        if (validationResult.isValid()) {
            customUnitRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public void deleteByGuid(String guid) {
        customUnitRepository.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        customUnitRepository.deleteAll();
    }
}
