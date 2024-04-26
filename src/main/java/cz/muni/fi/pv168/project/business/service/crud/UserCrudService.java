package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.DuplicateValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.ui.dialog.DeletePopupDialog;

import java.util.Collection;
import java.util.List;

public class UserCrudService implements CrudService<RegisteredUser> {

    private final Repository<RegisteredUser> userRepository;
    private final Validator<RegisteredUser> userValidator;
    private final GuidProvider guidProvider;

    private final DuplicateValidator<RegisteredUser> duplicityValidator;

    public UserCrudService(
            Repository<RegisteredUser> userRepository,
            Validator<RegisteredUser> userValidator,
            GuidProvider guidProvider
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.guidProvider = guidProvider;
        this.duplicityValidator = new DuplicateValidator<>(userRepository);
    }


    @Override
    public List<RegisteredUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public ValidationResult create(RegisteredUser newEntity) {
        ValidationResult validationResult = userValidator
                .and(duplicityValidator)
                .validate(newEntity);
        if (newEntity.getGuid() == null || newEntity.getGuid().isBlank()) {
            newEntity.setGuid(guidProvider.newGuid());
        } else if (userRepository.existsByGuid(newEntity.getGuid())) {
            throw new EntityAlreadyExistsException(
                    "CustomUnit with given guid already exists: " + newEntity.getGuid()
            );
        }

        if (validationResult.isValid()) {
            newEntity.setGuid(guidProvider.newGuid());
            userRepository.create(newEntity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult update(RegisteredUser entity) {
        ValidationResult validationResult = userValidator.validate(entity);
        if (validationResult.isValid()) {
            userRepository.update(entity);
        }

        return validationResult;
    }

    @Override
    public ValidationResult deleteByGuid(String guid, boolean userAgreed) {
        RegisteredUser toDelete = userRepository.findByGuid(guid)
                .orElseThrow(
                        () -> new DataStorageException("Custom unit with guid: " + guid + "not found!")
                );
//        ValidationResult validationResult = unitUsageValidator.validate(toDelete);

        DeletePopupDialog deletePopupDialog = new DeletePopupDialog(List.of(userValidator.toString())
        );

        if (deletePopupDialog.show().isValid()) {
            userRepository.deleteByGuid(guid);
            return ValidationResult.success();
        }
        return ValidationResult.failed("denied");
    }

    @Override
    public ValidationResult deleteMultipleByGuids(Collection<String> guids) {
        List<String> invalidValResults = guids.stream()
                .map(guid -> userRepository.findByGuid(guid)
                        .orElseThrow(
                                () -> new DataStorageException("Registered User with guid: " + guid + "not found!"))
                )
                .map(RegisteredUser::toString)
                .toList();

        DeletePopupDialog deletePopupDialog = new DeletePopupDialog(invalidValResults);

        if (deletePopupDialog.show().isValid()) {
            guids.forEach(userRepository::deleteByGuid);
            return ValidationResult.success();
        }
        return ValidationResult.failed("denied");
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
