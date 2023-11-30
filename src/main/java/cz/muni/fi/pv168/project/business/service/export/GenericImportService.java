package cz.muni.fi.pv168.project.business.service.export;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchOperationException;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.format.FormatMapping;
import cz.muni.fi.pv168.project.business.service.export.importer.BatchImporter;

import java.util.Collection;

/**
 * Generic synchronous implementation of the {@link ImportService}.
 */
public class GenericImportService implements ImportService {

    private final CrudService<Recipe> recipeCrudService;
    private final CrudService<Ingredient> ingredientCrudService;
    private final CrudService<Unit> customUnitsCrudService;
    private final CrudService<Category> categoryCrudService;
    private final FormatMapping<BatchImporter> importers;

    public GenericImportService(
            CrudService<Recipe> recipeCrudService,
            CrudService<Ingredient> ingredientCrudService,
            CrudService<Unit> customUnitsCrudService,
            CrudService<Category> categoryCrudService,
            Collection<BatchImporter> importers
    ) {
        this.recipeCrudService = recipeCrudService;
        this.ingredientCrudService = ingredientCrudService;
        this.customUnitsCrudService = customUnitsCrudService;
        this.categoryCrudService = categoryCrudService;

        this.importers = new FormatMapping<>(importers);
    }

    @Override
    public void importData(String filePath) {
        recipeCrudService.deleteAll();
        ingredientCrudService.deleteAll();
        customUnitsCrudService.deleteAll();
        categoryCrudService.deleteAll();


        var batch = getImporter(filePath).importBatch(filePath);

        batch.recipes().forEach(this::createRecipe);
        batch.ingredients().forEach(this::createIngredient);
        batch.units().forEach(this::createCustomUnit);
        batch.categories().forEach(this::createCategory);
    }

    private void createRecipe(Recipe recipe) {
        recipeCrudService.create(recipe)
                .intoException();
    }

    private void createIngredient(Ingredient ingredient) {
        ingredientCrudService.create(ingredient)
                .intoException();
    }

    private void createCustomUnit(Unit unit) {
        customUnitsCrudService.create(unit)
                .intoException();
    }

    private void createCategory(Category category) {
        categoryCrudService.create(category)
                .intoException();
    }


    @Override
    public Collection<Format> getFormats() {
        return importers.getFormats();
    }

    private BatchImporter getImporter(String filePath) {
        var extension = filePath.substring(filePath.lastIndexOf('.') + 1);
        var importer = importers.findByExtension(extension);
        if (importer == null) {
            throw new BatchOperationException("Extension %s has no registered formatter".formatted(extension));
        }

        return importer;
    }
}
