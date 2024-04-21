package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

public class UserMapper implements EntityMapper<UserEntity, RegisteredUser>{
    @Override
    public RegisteredUser mapToBusiness(UserEntity entity) {
        return new RegisteredUser(
                entity.guid(),
                entity.name(),
                entity.password()
        );
    }

    @Override
    public UserEntity mapNewEntityToDatabase(RegisteredUser entity) {
        return getUserEntity(entity,null);
    }

    @Override
    public UserEntity mapExistingEntityToDatabase(RegisteredUser entity, Long dbId) {
        return getUserEntity(entity,dbId);
    }

    private UserEntity getUserEntity(RegisteredUser entity, Long dbid) {
        return new UserEntity(
                dbid,
                entity.getGuid(),
                entity.getName(),
                entity.getPassword()
        );
    }
}
