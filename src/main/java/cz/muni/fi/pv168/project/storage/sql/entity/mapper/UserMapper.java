package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.entity.RegisteredUserEntity;

public class UserMapper implements EntityMapper<RegisteredUserEntity, RegisteredUser>{
    @Override
    public RegisteredUser mapToBusiness(RegisteredUserEntity entity) {
        return new RegisteredUser(
                entity.guid(),
                entity.name(),
                entity.password(),
                entity.id()
                );
    }

    @Override
    public RegisteredUserEntity mapNewEntityToDatabase(RegisteredUser entity) {
        return getUserEntity(entity,null);
    }

    @Override
    public RegisteredUserEntity mapExistingEntityToDatabase(RegisteredUser entity, Long dbId) {
        return getUserEntity(entity,dbId);
    }

    private RegisteredUserEntity getUserEntity(RegisteredUser entity, Long dbid) {
        return new RegisteredUserEntity(
                dbid,
                entity.getGuid(),
                entity.getName(),
                entity.getPassword()
        );
    }
}
