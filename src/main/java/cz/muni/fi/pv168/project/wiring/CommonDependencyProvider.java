package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.CustomUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.CategorySqlRepository;
import cz.muni.fi.pv168.project.storage.sql.IngredientSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.RecipeSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.dao.CategoryDao;
import cz.muni.fi.pv168.project.storage.sql.dao.UnitDao;
import cz.muni.fi.pv168.project.storage.sql.dao.IngredientDao;
import cz.muni.fi.pv168.project.storage.sql.dao.RecipeDao;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionConnectionSupplier;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutorImpl;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionManagerImpl;
import cz.muni.fi.pv168.project.storage.sql.UnitSqlRepository;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.CategoryMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.UnitMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.IngredientMapper;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.RecipeMapper;

/**
 * Common dependency provider for both production and test environment.
 */
public class CommonDependencyProvider implements DependencyProvider {

    private final Repository<Recipe> recipes;
    private final Repository<Category> categories;
    private final Repository<Unit> customUnits;
    private final Repository<Ingredient> ingredients;
    private final DatabaseManager databaseManager;
    private final TransactionExecutor transactionExecutor;
    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Unit> customUnitCrudService;
    private final CrudService<Category> categoryCrudService;
    //private final ImportService importService; //TODO
    //private final ExportService exportService; //TODO
    private final RecipeValidator recipeValidator;
    private final CategoryValidator categoryValidator;
    private final IngredientValidator ingredientValidator;
    private final CustomUnitValidator customUnitValidator;

    public CommonDependencyProvider(DatabaseManager databaseManager) {
        recipeValidator = new RecipeValidator();
        categoryValidator = new CategoryValidator();
        ingredientValidator = new IngredientValidator();
        customUnitValidator = new CustomUnitValidator();

        var guidProvider = new UuidGuidProvider();

        this.databaseManager = databaseManager;
        var transactionManager = new TransactionManagerImpl(databaseManager);
        this.transactionExecutor = new TransactionExecutorImpl(transactionManager::beginTransaction);
        var transactionConnectionSupplier = new TransactionConnectionSupplier(transactionManager, databaseManager);

        var unitMapper = new UnitMapper();
        var categoryMapper = new CategoryMapper();
        var categoryDao = new CategoryDao(transactionConnectionSupplier);
        var unitDao = new UnitDao(transactionConnectionSupplier);
        var ingredientMapper = new IngredientMapper(unitDao, unitMapper);
        var ingredientDao = new IngredientDao(transactionConnectionSupplier);

        var recipeMapper = new RecipeMapper(categoryDao, categoryMapper);
        var recipeDao = new RecipeDao(transactionConnectionSupplier);

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

        recipeCrudService = new RecipeCrudService(recipes, recipeValidator, guidProvider);
        categoryCrudService = new CategoryCrudService(categories, categoryValidator, guidProvider);
        ingredientCrudService = new IngredientCrudService(ingredients, ingredientValidator, guidProvider);
        customUnitCrudService = new UnitService(customUnits, customUnitValidator, guidProvider);


        exportService = new GenericExportService(employeeCrudService, departmentCrudService,
                List.of(new BatchCsvExporter()));
        importService = new GenericImportService(employeeCrudService, departmentCrudService,
                List.of(new BatchCsvImporter()), transactionExecutor);
        //TODO

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
    public Repository<Unit> getCustomUnitRepository() {
        return customUnits;
    }

    @Override
    public Repository<Ingredient> getIngredientRepository() {
        return ingredients;
    }


    @Override
    public TransactionExecutor getTransactionExecutor() {
        return transactionExecutor;
    }

    @Override
    public CrudService<Recipe> getRecipeCrudService() {
        return null;
    }

    @Override
    public CrudService<Category> getCategoryCrudService() {
        return null;
    }

    @Override
    public CrudService<Unit> getCustomUnitCrudService() {
        return null;
    }

    @Override
    public CrudService<Ingredient> getIngredientCrudService() {
        return null;
    }


    @Override
    public ImportService getImportService() {
        return importService;
    }

    @Override
    public ExportService getExportService() {
        return exportService;
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
    public Validator<Unit> getCustomUnitValidator() {
        return customUnitValidator;
    }

    @Override
    public Validator<Ingredient> getIngredientValidator() {
        return ingredientValidator;
    }
}
