package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.*;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.*;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.*;
import cz.muni.fi.pv168.project.storage.sql.*;
import cz.muni.fi.pv168.project.storage.sql.dao.*;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseConnection;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseInitializer;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.*;

import java.sql.Connection;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {
    public static final String INIT_SQL = "init.sql";
    private final Repository<Recipe> recipes;
    private final Repository<Category> categories;
    private final Repository<CustomUnit> customUnits;
    private final Repository<Ingredient> ingredients;
    private final AddedIngredientSqlRepository addedIngredients;
    //    private final DatabaseManager databaseManager;
//    private final TransactionExecutor transactionExecutor;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Ingredient> ingredientCrudService;
    private final AddedIngredientCrudService addedIngredientCrudService;
    private final CrudService<CustomUnit> customUnitCrudService;
    private final CrudService<Category> categoryCrudService;
    //private final ImportService importService; //TODO
    //private final ExportService exportService; //TODO
    private final RecipeValidator recipeValidator;
    private final CategoryValidator categoryValidator;
    private final IngredientValidator ingredientValidator;
    private final UnitValidator customUnitValidator;
    private final AddedIngredientValidator addedIngredientValidator;
    private final CategoryUsageValidator categoryUsageValidator;

    private static final DatabaseConnection CONNECTION = new DatabaseConnection();

    public CommonDependencyProvider() {

        Connection connection = CONNECTION.useConnection();
        DatabaseInitializer.init(connection, INIT_SQL);
        recipeValidator = new RecipeValidator();
        categoryValidator = new CategoryValidator();
        ingredientValidator = new IngredientValidator();
        customUnitValidator = new UnitValidator();
        addedIngredientValidator = new AddedIngredientValidator();

        var guidProvider = new UuidGuidProvider();

//        var transactionManager = new TransactionManagerImpl(databaseManager);
//        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
//        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        //var baseUnitMapper = new BaseUnitMapper();
        //var baseUnitDao = new BaseUnitDao(connection);

        var unitMapper = new UnitMapper();
        var unitDao = new UnitDao(connection);

        var categoryMapper = new CategoryMapper();
        var categoryDao = new CategoryDao(connection);

        var ingredientMapper = new IngredientMapper(unitDao, unitMapper);
        var ingredientDao = new IngredientDao(connection);

        var addedIngredientDao = new AddedIngredientDao(connection);

        var recipeMapper = new RecipeMapper(categoryDao, categoryMapper);
        var recipeDao = new RecipeDao(connection);

        var addedIngredientMapper = new AddedIngredientMapper(recipeDao, recipeMapper, ingredientDao, ingredientMapper, unitDao, unitMapper);

        this.addedIngredients = new AddedIngredientSqlRepository(
                addedIngredientDao,
                addedIngredientMapper,
                recipeDao
        );
        this.recipes = new RecipeSqlRepository(
                recipeDao,
                recipeMapper
        );
        this.categories = new CategorySqlRepository(
                categoryDao,
                categoryMapper
        );
        this.customUnits = new UnitSqlRepository(
                unitDao,
                unitMapper
        );
        this.ingredients = new IngredientSqlRepository(
                ingredientDao,
                ingredientMapper
        );

        categoryUsageValidator = new CategoryUsageValidator(recipes);

        categoryCrudService = new CategoryCrudService(categories, categoryValidator, categoryUsageValidator);
        ingredientCrudService = new IngredientCrudService(
                ingredients,
                ingredientValidator,
                guidProvider,
                new IngredientUsageValidator(addedIngredients)
        );
        customUnitCrudService = new UnitCrudService(
                customUnits,
                customUnitValidator,
                guidProvider,
                new UnitUsageValidator(addedIngredients)
        );
        addedIngredientCrudService = new AddedIngredientCrudService(addedIngredients, addedIngredientValidator, guidProvider);
        recipeCrudService = new RecipeCrudService(recipes, recipeValidator, guidProvider, addedIngredientCrudService);

        for (Ingredient ingredient: ingredients.findAll()) {
            ingredient.calculateUsage();
        }

//        exportService = new GenericExportService(employeeCrudService, departmentCrudService,
//                List.of(new BatchCsvExporter()));
//        importService = new GenericImportService(employeeCrudService, departmentCrudService,
//                List.of(new BatchCsvImporter()), transactionExecutor);
        //TODO import export

    }

    @Override
    public DatabaseManager getDatabaseManager() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Repository<Recipe> getRecipeRepository() {
        return recipes;
    }

    @Override
    public Repository<Category> getCategoryRepository() {
        return categories;
    }

    @Override
    public Repository<CustomUnit> getCustomUnitRepository() {
        return customUnits;
    }

    @Override
    public Repository<Ingredient> getIngredientRepository() {
        return ingredients;
    }


    @Override
    public TransactionExecutor getTransactionExecutor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CrudService<Recipe> getRecipeCrudService() {
        return recipeCrudService;
    }

    @Override
    public CrudService<Category> getCategoryCrudService() {
        return categoryCrudService;
    }

    @Override
    public CrudService<CustomUnit> getCustomUnitCrudService() {
        return customUnitCrudService;
    }

    @Override
    public CrudService<Ingredient> getIngredientCrudService() {
        return ingredientCrudService;
    }

    @Override
    public AddedIngredientCrudService getAddedIngredientCrudService() {
        return addedIngredientCrudService;
    }


    @Override
    public ImportService getImportService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExportService getExportService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Validator<Recipe> getRecipeValidator() {
        return recipeValidator;
    }

    @Override
    public Validator<Category> getCategoryValidator() {
        return categoryValidator;
    }

    @Override
    public Validator<CustomUnit> getCustomUnitValidator() {
        return customUnitValidator;
    }

    @Override
    public Validator<Ingredient> getIngredientValidator() {
        return ingredientValidator;
    }

    @Override
    public Validator<AddedIngredient> getAddedIngredientValidator() {
        return addedIngredientValidator;
    }

    public CategoryUsageValidator getCategoryUsageValidator() {
        return categoryUsageValidator;
    }
}
