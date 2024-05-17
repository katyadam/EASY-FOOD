package cz.muni.fi.pv168.project.wiring;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.AddedIngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.ExportService;
import cz.muni.fi.pv168.project.business.service.export.ImportService;
import cz.muni.fi.pv168.project.business.service.validation.Validator;
import cz.muni.fi.pv168.project.storage.sql.db.DatabaseManager;
import cz.muni.fi.pv168.project.storage.sql.db.TransactionExecutor;
import cz.muni.fi.pv168.project.ui.action.ActionFactory;

/**
 * Interface for instance wirings
 */
public interface DependencyProvider {

    DatabaseManager getDatabaseManager();

    Repository<Recipe> getRecipeRepository();

    Repository<Category> getCategoryRepository();

    Repository<CustomUnit> getCustomUnitRepository();

    Repository<Ingredient> getIngredientRepository();

    Repository<RegisteredUser> getUserRepository();

    TransactionExecutor getTransactionExecutor();

    CrudService<Recipe> getRecipeCrudService();

    CrudService<Category> getCategoryCrudService();

    CrudService<CustomUnit> getCustomUnitCrudService();

    CrudService<Ingredient> getIngredientCrudService();
    AddedIngredientCrudService getAddedIngredientCrudService();

    CrudService<RegisteredUser> getUserCrudService();


    ImportService getImportService();

    ExportService getExportService();

    Validator<Recipe> getRecipeValidator();

    Validator<Category> getCategoryValidator();

    Validator<CustomUnit> getCustomUnitValidator();

    Validator<Ingredient> getIngredientValidator();
    Validator<AddedIngredient> getAddedIngredientValidator();

    Validator<RegisteredUser> getUserValidator();

    Session getSession();

    ActionFactory getActionFactory();

    void setActionFactory(ActionFactory actionFactory);
}

