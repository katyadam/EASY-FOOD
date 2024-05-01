package cz.muni.fi.pv168.project;


import cz.muni.fi.pv168.project.business.model.BaseUnit;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.business.service.validation.UnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
    private CategoryValidator categoryValidator;
    private RecipeValidator recipeValidator;
    private IngredientValidator ingredientValidator;
    private UnitValidator unitValidator;
    private TestDataGenerator testDataGenerator;

    @Before
    public void setUp() {
        categoryValidator = new CategoryValidator();
        recipeValidator = new RecipeValidator();
        ingredientValidator = new IngredientValidator();
        unitValidator = new UnitValidator();
        testDataGenerator = new TestDataGenerator();
    }

    @Test
    public void testValidCategories() {
        List<Category> validCategories = testDataGenerator.createTestCategories();
        for (Category category : validCategories) {
            ValidationResult result = categoryValidator.validate(category);
            assertTrue(result.isValid());
        }
    }

    @Test
    public void testEmptyCategoryName() {
        Category emptyNameCategory = new Category("", null, null);
        ValidationResult result = categoryValidator.validate(emptyNameCategory);
        assertFalse(result.isValid());
    }

    @Test
    public void testInvalidCategoryName() {
        Category invalidNameCategory = new Category("Invalid@Category", null, null);
        ValidationResult result = categoryValidator.validate(invalidNameCategory);
        assertFalse(result.isValid());
    }

    @Test
    public void testValidRecipe() {
        Recipe validRecipe = testDataGenerator.createTestRecipe();
        ValidationResult result = recipeValidator.validate(validRecipe);
        assertTrue(result.isValid());
    }

    @Test
    public void testEmptyRecipeName() {
        Recipe emptyNameRecipe = new Recipe("", "", testDataGenerator.createTestCategories().get(0), 12, 12, "aaa", null);
        ValidationResult result = recipeValidator.validate(emptyNameRecipe);
        assertFalse(result.isValid());
    }

    @Test
    public void testValidIngredients() {
        List<Ingredient> validIngredients = testDataGenerator.createTestIngredients(10);
        for (Ingredient ingredient : validIngredients) {
            ValidationResult result = ingredientValidator.validate(ingredient);
            assertTrue(result.isValid());
        }
    }

    @Test
    public void testEmptyIngredientsName() {
        Ingredient emptyNameIngredients = new Ingredient("", 12, null);
        ValidationResult result = ingredientValidator.validate(emptyNameIngredients);
        assertFalse(result.isValid());
    }

    @Test
    public void testValidUnits() {
        List<CustomUnit> validUnits = testDataGenerator.createTestCustomUnits(10);
        for (CustomUnit customUnit : validUnits) {
            ValidationResult result = unitValidator.validate(customUnit);
            assertTrue(result.isValid());
        }
    }

    @Test
    public void testEmptyUnitName() {
        CustomUnit emptyNameUnit = new CustomUnit("", "aa", 12, BaseUnit.LITER, null);
        ValidationResult result = unitValidator.validate(emptyNameUnit);
        assertFalse(result.isValid());
    }
}
