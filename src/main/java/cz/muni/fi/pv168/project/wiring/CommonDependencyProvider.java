package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.AddedIngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UserCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitCrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericExportService;
import cz.muni.fi.pv168.project.business.service.export.GenericImportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchXmlExporter;
import cz.muni.fi.pv168.project.business.service.export.importer.BatchXmlImporter;
import cz.muni.fi.pv168.project.business.service.validation.AddedIngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.CategoryUsageValidator;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientUsageValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.UserValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitUsageValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.AddedIngredientSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.CategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.IngredientSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.UnitSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.UserSqlRepositary;
import cz.muni.fi.pv168.project.storage.sql.dao.*;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseConnection;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseInitializer;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionalImportService;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.AddedIngredientMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.CategoryMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.IngredientMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.UnitMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.UserMapper;

import java.sql.Connection;
import java.util.List;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {
    public static final String INIT_SQL = "init.sql";
    private final Repository<Recipe> recipes;
    private final Repository<Category> categories;
    private final Repository<CustomUnit> customUnits;
    private final Repository<Ingredient> ingredients;
    private final Repository<RegisteredUser> users;
    private final AddedIngredientSqlRepository addedIngredients;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Ingredient> ingredientCrudService;
    private final AddedIngredientCrudService addedIngredientCrudService;
    private final CrudService<CustomUnit> customUnitCrudService;
    private final CrudService<Category> categoryCrudService;
    private final CrudService<RegisteredUser> userCrudService;

    private final RecipeValidator recipeValidator;
    private final CategoryValidator categoryValidator;
    private final IngredientValidator ingredientValidator;
    private final UnitValidator customUnitValidator;
    private final AddedIngredientValidator addedIngredientValidator;
    private final CategoryUsageValidator categoryUsageValidator;

    private final UserValidator userValidator;
    private static final DatabaseConnection CONNECTION = new DatabaseConnection();
    private final DatabaseManager databaseManager;
    private final TransactionExecutorImpl transactionExecutor;
    private final TransactionalImportService transactionalImportService;
    private Session session;

    public CommonDependencyProvider() {

        Connection connection = CONNECTION.useConnection();
        DatabaseInitializer.init(connection, INIT_SQL);
        recipeValidator = new RecipeValidator();
        categoryValidator = new CategoryValidator();
        ingredientValidator = new IngredientValidator();
        customUnitValidator = new UnitValidator();
        addedIngredientValidator = new AddedIngredientValidator();
        userValidator = new UserValidator();

        var guidProvider = new UuidGuidProvider();

        var userDao = new UserDao(connection);
        var unitDao = new UnitDao(connection);
        var categoryDao = new CategoryDao(connection);
        var ingredientDao = new IngredientDao(connection);
        var addedIngredientDao = new AddedIngredientDao(connection);
        var recipeDao = new RecipeDao(connection);

        var userMapper = new UserMapper();
        var unitMapper = new UnitMapper(userDao, userMapper);
        var categoryMapper = new CategoryMapper(userDao,userMapper);
        var ingredientMapper = new IngredientMapper(unitDao, unitMapper, userDao, userMapper );
        var recipeMapper = new RecipeMapper(categoryDao, categoryMapper,userDao, userMapper );
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

        this.users = new UserSqlRepositary(
                userDao,
                userMapper
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

        userCrudService = new UserCrudService((UserSqlRepositary) users, userValidator, guidProvider);

        BatchXmlImporter xmlImporter = new BatchXmlImporter();
        GenericImportService genericImportService = new GenericImportService(
                getRecipeCrudService(),
                getIngredientCrudService(),
                getCustomUnitCrudService(),
                getCategoryCrudService(),
                List.of(xmlImporter),
                new GenericExportService(
                        getRecipeCrudService(),
                        getIngredientCrudService(),
                        getCustomUnitCrudService(),
                        getCategoryCrudService(),
                        List.of(new BatchXmlExporter())
                )
        );
        this.databaseManager = new DatabaseManager(CONNECTION.getConnectionString());
        var transactionManager = new TransactionManagerImpl(getDatabaseManager());
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        this.transactionalImportService = new TransactionalImportService(genericImportService, transactionExecutor);

        this.session = new Session();

    }

    @Override
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
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
    public Repository<RegisteredUser> getUserRepository() {
        return users;
    }
    @Override
    public CrudService<RegisteredUser> getUserCrudService() {
        return userCrudService;
    }
    @Override
    public Validator<RegisteredUser> getUserValidator() {
        return userValidator;
    }

    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
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

    public TransactionalImportService getTransactionalImportService() {
        return transactionalImportService;
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

    @Override
    public Session getSession() { return session; }
}
