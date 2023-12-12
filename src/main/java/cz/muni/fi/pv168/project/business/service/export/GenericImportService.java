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


import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.ui.action.mport.ImportType;

import javax.swing.*;
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
    public void importData(String filePath, ImportType importType) {
        if (importType == ImportType.OVERWRITE) {
            recipeCrudService.deleteAll();
            ingredientCrudService.deleteAll();
            customUnitsCrudService.deleteAll();
            categoryCrudService.deleteAll();
        }

        var batch = getImporter(filePath).importBatch(filePath);

        batch.categories().forEach(this::createCategory);
        batch.ingredients().forEach(this::createIngredient);
        batch.units().forEach(this::createCustomUnit);
        batch.recipes().forEach(this::createRecipe);

    }

    private void createRecipe(Recipe recipe) {
        ValidationResult validationResult = recipeCrudService.create(recipe);
        if (!validationResult.isValid()) {
            JOptionPane.showMessageDialog(new JPanel(), validationResult);
            throw new DataManipulationException("Create recipe failed!");
        }
    }

    private void createIngredient(Ingredient ingredient) {
        ValidationResult validationResult = ingredientCrudService.create(ingredient);
        if (!validationResult.isValid()) {
            JOptionPane.showMessageDialog(new JPanel(), validationResult);
            throw new DataManipulationException("Create ingredient failed!");
        }
    }

    private void createCustomUnit(Unit unit) {
        ValidationResult validationResult = customUnitsCrudService.create(unit);
        if (!validationResult.isValid()) {
            JOptionPane.showMessageDialog(new JPanel(), validationResult);
            throw new DataManipulationException("Create unit failed!");
        }
    }

    private void createCategory(Category category) {
        ValidationResult validationResult = categoryCrudService.create(category);
        if (!validationResult.isValid()) {
            JOptionPane.showMessageDialog(new JPanel(), validationResult);
            throw new DataManipulationException("Create category failed!");
        }
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
