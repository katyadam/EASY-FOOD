--
-- Recipe table definition
--
CREATE TABLE IF NOT EXISTS "Recipe"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `recipeName`    VARCHAR(150) NOT NULL,
    `preparationTime`    INT NOT NULL,
    `portions`    INT NOT NULL,
    `categoryId` BIGINT REFERENCES "Category"(`id`),
    `nutritionalValue`    INT NOT NULL,
    `description VARCHAR(255) DEFAULT 'No recipe description',
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `categoryName`    VARCHAR(150) NOT NULL,
    `color`    VARCHAR(20) NOT NULL,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- CustomUnit table definition
--
CREATE TABLE IF NOT EXISTS "CustomUnit"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `unitName`    VARCHAR(150) NOT NULL,
    `abbreviation`    VARCHAR(150) NOT NULL,
    `amount`    DOUBLE NOT NULL,
    `baseUnit`    VARCHAR(150) NOT NULL,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Ingredient table definition
--
CREATE TABLE IF NOT EXISTS "Ingredient"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`      VARCHAR     NOT NULL UNIQUE,
    `ingredientName`    VARCHAR(150) NOT NULL,
    `nutritionalValue`    INT NOT NULL,
    `unitType`    VARCHAR(150) NOT NULL,
    `createdAt` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);



package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.DepartmentEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class RecipeSqlRepository implements Repository<Recipe> {

    private final DataAccessObject<RecipeEntity> recipeDao;
    private final EntityMapper<RecipeEntity, Recipe> recipeMapper;

    public RecipeSqlRepository(
            DataAccessObject<DepartmentEntity> recipeDao,
            EntityMapper<DepartmentEntity, Department> recipeMapper) {
        this.recipeDao = recipeDao;
        this.recipeMapper = recipeMapper;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeDao
                .findAll()
                .stream()
                .map(recipeMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Recipe newEntity) {
        recipeDao.create(recipeMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Recipe entity) {
        var existingDepartment = recipeDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Department not found, guid: " + entity.getGuid()));
        var updatedRecipe = recipeMapper.mapExistingEntityToDatabase(entity, existingDepartment.id());

        recipeDao.update(updatedRecipe);
    }

    @Override
    public void deleteByGuid(String guid) {
        recipeDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        recipeDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return recipeDao.existsByGuid(guid);
    }

    @Override
    public Optional<Recipe> findByGuid(String guid) {
        return recipeDao
                .findByGuid(guid)
                .map(recipeMapper::mapToBusiness);
    }
}
