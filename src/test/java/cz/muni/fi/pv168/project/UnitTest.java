package cz.muni.fi.pv168.project;


import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchXmlExporter;
import cz.muni.fi.pv168.project.business.service.export.format.Format;
import cz.muni.fi.pv168.project.business.service.export.importer.BatchXmlImporter;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class UnitTest {
    private BatchXmlExporter batchXmlExporter;
    private TestDataGenerator testDataGenerator;

    @Before
    public void setUp() {
        batchXmlExporter = new BatchXmlExporter();
        testDataGenerator = new TestDataGenerator();
    }

    @Test
    public void exportBatch_ValidBatch_Success() throws IOException {
        Batch batch = createTestBatch();
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/src/test/resources/test_export.xml";
        batchXmlExporter.exportBatch(batch, filePath);
        assertTrue(Files.exists(Paths.get(filePath)));
    }

    @Test
    public void exportBatch_IOExceptionThrown_ExceptionHandled() {
        Batch batch = createTestBatch();
        String filePath = "non_existing_folder/test_export.xml";
        assertThrows(DataManipulationException.class, () -> batchXmlExporter.exportBatch(batch, filePath));
    }

    private Batch createTestBatch() {
        Collection<Recipe> recipes = List.of();
        Collection<Ingredient> ingredients = testDataGenerator.createTestIngredients(10);
        Collection<CustomUnit> units = testDataGenerator.createTestCustomUnits(10);
        Collection<Category> categories = testDataGenerator.createTestCategories();

        return new Batch(recipes, ingredients, units, categories);
    }

    @Test
    public void importBatch_ValidXmlFile_Success() {
        String currentDirectory = System.getProperty("user.dir");
        String filePath = currentDirectory + "/src/test/resources/test_export.xml";
        BatchXmlImporter batchXmlImporter = new BatchXmlImporter();
        Batch batch = batchXmlImporter.importBatch(filePath);
        assertNotNull(batch);
    }

    @Test
    public void importBatch_InvalidXmlFile_ExceptionThrown() {
        String filePath = "non_existing_folder/test_batch.xml";
        BatchXmlImporter batchXmlImporter = new BatchXmlImporter();

        assertThrows(RuntimeException.class, () -> batchXmlImporter.importBatch(filePath));
    }

    @Test
    public void getFormat_ReturnsCorrectFormat() {
        BatchXmlImporter batchXmlImporter = new BatchXmlImporter();
        Format format = batchXmlImporter.getFormat();
        assertEquals("XML", format.name());
        assertTrue(format.extensions().contains("xml"));
    }
}
