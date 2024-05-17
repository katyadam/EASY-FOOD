package cz.muni.fi.pv168.project.business.service.crud;

import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.validation.DuplicateValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.UserSqlRepositary;
import cz.muni.fi.pv168.project.ui.dialog.DeletePopupDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserCrudService implements CrudService<RegisteredUser> {

    private final UserSqlRepositary userRepository;
    private final Validator<RegisteredUser> userValidator;
    private final GuidProvider guidProvider;

    private final DuplicateValidator<RegisteredUser> duplicityValidator;

    public UserCrudService(
            UserSqlRepositary userRepository,
            Validator<RegisteredUser> userValidator,
            GuidProvider guidProvider
    ) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.guidProvider = guidProvider;
        this.duplicityValidator = new DuplicateValidator<>(userRepository);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByName(username);
    }

    public Optional<RegisteredUser> login(String username, String password) {
        String hashedPassword = UserCrudService.hashPassword(password);
        return userRepository.existByLogin(username, hashedPassword);

    }

    @Override
    public List<RegisteredUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<RegisteredUser> findAll(Long userID) {
        return List.of();
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
            var pass = newEntity.getPassword();
            newEntity.setPassword(hashPassword(pass));
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

    @Override
    @Deprecated
    public Collection<RegisteredUser> getAllOfUser(RegisteredUser user) {
        return userRepository.findAll(user.getID());
    }

    public static String hashPassword(String password)  {
        String generatedPassword = null;
        try
        {
            // NO SALT HASHING = not that secure
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPassword;
    }
}
