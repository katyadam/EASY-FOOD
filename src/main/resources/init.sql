// PRESERVE ORDER

--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`         VARCHAR      NOT NULL UNIQUE,
    `categoryName` VARCHAR(150) NOT NULL,
    `color`        INT          NOT NULL,
    `createdAt`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- BaseUnit table definition
--
CREATE TABLE IF NOT EXISTS "BaseUnit"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`         VARCHAR      NOT NULL UNIQUE,
    `baseUnitName` VARCHAR(150) NOT NULL,
    `abbreviation` VARCHAR(150) NOT NULL
);

--
-- CustomUnit table definition
--
CREATE TABLE IF NOT EXISTS "Unit"
(
    `id`           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`         VARCHAR      NOT NULL UNIQUE,
    `unitName`     VARCHAR(150) NOT NULL,
    `abbreviation` VARCHAR(150) NOT NULL,
    `amount`       DOUBLE       NOT NULL,
    `baseUnitId`   BIGINT REFERENCES "BaseUnit" (`id`),
    `createdAt`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Ingredient table definition
--
CREATE TABLE IF NOT EXISTS "Ingredient"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR      NOT NULL UNIQUE,
    `ingredientName`   VARCHAR(150) NOT NULL,
    `nutritionalValue` INT          NOT NULL,
    `unitId`           BIGINT REFERENCES "Unit" (`id`),
    `createdAt`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Recipe table definition
--
CREATE TABLE IF NOT EXISTS "Recipe"
(
    `id`               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `guid`             VARCHAR      NOT NULL UNIQUE,
    `recipeName`       VARCHAR(150) NOT NULL,
    `prepHours`        INT          NOT NULL,
    `prepMinutes`      INT          NOT NULL,
    `portions`         INT          NOT NULL,
    `categoryId`       BIGINT REFERENCES "Category" (`id`),
    `nutritionalValue` INT          NOT NULL,
    `description`      VARCHAR(255)          DEFAULT 'No recipe description',
    `createdAt`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--
-- Initialization of base units
--
MERGE INTO "BaseUnit" ("guid", "baseUnitName", "abbreviation")
    VALUES ('bu-pc', 'piece', 'pc'),
           ('bu-ml', 'milliliter', 'ml'),
           ('bu-l', 'liter', 'l'),
           ('bu-g', 'gram', 'g'),
           ('bu-kg', 'kilogram', 'kg')
