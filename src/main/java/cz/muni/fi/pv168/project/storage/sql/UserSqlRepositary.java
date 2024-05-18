package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.dao.RegisteredUserDao;
import cz.muni.fi.pv168.project.storage.sql.entity.RegisteredUserEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class UserSqlRepositary implements Repository<RegisteredUser> {

    private final RegisteredUserDao userDao;
    private final EntityMapper<RegisteredUserEntity, RegisteredUser> userMapper;

    public UserSqlRepositary(
            RegisteredUserDao userDao,
            EntityMapper<RegisteredUserEntity, RegisteredUser> userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public List<RegisteredUser> findAll() {
        return userDao.
                findAll().
                stream().
                map(userMapper::mapToBusiness).
                toList();
    }
    @Override
    public List<RegisteredUser> findAll(Long userId) {
        return List.of();
    }

    @Override
    public Optional<RegisteredUser> findByGuid(String guid) {
        return userDao.findByGuid(guid).map(userMapper::mapToBusiness);
    }

    @Override
    public Optional<RegisteredUser> findById(Long id) {
        return userDao.findById(id).map(userMapper::mapToBusiness);
    }

    @Override
    public void create(RegisteredUser newEntity) {
        userDao.create(userMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(RegisteredUser entity) {
        var existingUser = userDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("User not found, guid: " + entity.getGuid()));
        var updatedRecipe = userMapper.mapExistingEntityToDatabase(entity, existingUser.id());
        userDao.update(updatedRecipe);
    }

    @Override
    public void deleteByGuid(String guid) {
        userDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return userDao.existsByGuid(guid);
    }

    @Override
    public boolean existsByName(String name) {return userDao.existsByUsername(name);}

    public Optional<RegisteredUser> existByLogin(String name, String password) {
        return userDao.existsByLogin(name, password).map(userMapper::mapToBusiness);
    }
}
