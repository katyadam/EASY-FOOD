package cz.muni.fi.pv168.project.service.crud;

import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.model.GuidProvider;
import cz.muni.fi.pv168.project.repository.Repository;
import cz.muni.fi.pv168.project.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.service.validation.Validator;

import java.util.List;

public class CustomUnitService implements CrudService<CustomUnit> {

    private final Repository<CustomUnit> customUnitRepository;
    private final Validator<CustomUnit> customUnitValidator;
    private final GuidProvider guidProvider;

    public CustomUnitService(
            Repository<CustomUnit> customUnitRepository,
            Validator<CustomUnit> customUnitValidator,
            GuidProvider guidProvider
    ) {
        this.customUnitRepository = customUnitRepository;
        this.customUnitValidator = customUnitValidator;
        this.guidProvider = guidProvider;
    }


    @Override
    public List<CustomUnit> findAll() {
        return customUnitRepository.findAll();
    }

    @Override
    public ValidationResult create(CustomUnit newEntity) {
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
    public ValidationResult update(CustomUnit entity) {
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
