package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.BaseUnit;
import cz.muni.fi.pv168.project.storage.sql.entity.BaseUnitEntity;

public class BaseUnitMapper implements EntityMapper<BaseUnitEntity, BaseUnit> {
    @Override
    public BaseUnit mapToBusiness(BaseUnitEntity entity) {
        return new BaseUnit(
                entity.guid(),
                entity.baseUnitName(),
                entity.abbreviation()
        );
    }

    @Override
    public BaseUnitEntity mapNewEntityToDatabase(BaseUnit entity) {
        return new BaseUnitEntity(
                entity.getGuid(),
                entity.getBaseUnitName(),
                entity.getAbbreviation()
        );
    }

    @Override
    public BaseUnitEntity mapExistingEntityToDatabase(BaseUnit entity, Long dbId) {
        return new BaseUnitEntity(
                dbId,
                entity.getGuid(),
                entity.getBaseUnitName(),
                entity.getAbbreviation()
        );
    }
}
