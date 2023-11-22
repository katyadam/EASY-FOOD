package cz.muni.fi.pv168.project.business.service.export.importer;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.BaseUnits;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.PreparationTime;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ImporterHandler extends DefaultHandler {
    private final List<Category> categoryList;
    private final List<Unit> unitList;
    private final List<Ingredient> ingredientList;
    private final List<Recipe> recipeList;

    public static final String CATEGORY = "Category";
    public static final String CUSTOM_UNIT = "CustomUnit";
    public static final String INGREDIENT = "Ingredient";
    public static final String RECIPE = "Recipe";
    public static final String ADDED_INGREDIENTS = "AddedIngredients";
    public static final String ADDED_INGREDIENT = "AddedIngredient";
    private StringBuilder elementValue;
    private Category activeCategory;
    private Unit activeUnit;
    private Ingredient activeIngredient;
    private Recipe activeRecipe;
    private AddedIngredient activeAddedIngredient;

    private List<AddedIngredient> addedIngredients;

    public ImporterHandler() {
        this.categoryList = new ArrayList<>();
        this.unitList = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
        this.recipeList = new ArrayList<>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case CATEGORY -> activeCategory = new Category();
            case CUSTOM_UNIT -> activeUnit = new Unit();
            case INGREDIENT -> activeIngredient = new Ingredient();
            case RECIPE -> activeRecipe = new Recipe();
            case ADDED_INGREDIENTS -> addedIngredients = new ArrayList<>();
            case ADDED_INGREDIENT -> activeAddedIngredient = new AddedIngredient();
            default -> elementValue = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case CATEGORY -> categoryList.add(activeCategory);
            case CUSTOM_UNIT -> unitList.add(activeUnit);
            case INGREDIENT -> ingredientList.add(activeIngredient);
            case RECIPE -> recipeList.add(activeRecipe);
            case "CategoryName" -> activeCategory.setName(elementValue.toString());
            case "Color" -> activeCategory.setColor(parseColor(elementValue.toString()));
            case "CustomUnitName" -> activeUnit.setName(elementValue.toString());
            case "Abbreviation" -> activeUnit.setAbbreviation(elementValue.toString());
            case "BaseAmountNumber" -> activeUnit.setAmount(Double.parseDouble(elementValue.toString()));
//            case "BaseUnitAbbr" -> activeUnit.setBaseUnit(parseUnit(elementValue.toString()));
            case "IngredientName" -> activeIngredient.setName(elementValue.toString());
            case "IngredientUnit" -> activeIngredient.setUnitType(parseUnit(elementValue.toString()));
            case "NutritionalValue" -> activeIngredient.setNutritionalValue(Integer.parseInt(elementValue.toString()));
            case "RecipeName" -> activeRecipe.setName(elementValue.toString());
            case "RecipeCategory" -> activeRecipe.setCategory(parseCategory(elementValue.toString()));
            case "Portions" -> activeRecipe.setPortions(Integer.parseInt(elementValue.toString()));
            case "PreparationTime" -> activeRecipe.setPreparationTime(parsePreparationTime(elementValue.toString()));
            case "Description" -> activeRecipe.setDescription(elementValue.toString());
            case "AddedIngredientName" -> activeAddedIngredient.setIngredient(parseIngredient(elementValue.toString()));
            case "Quantity" -> activeAddedIngredient.setQuantity(Double.parseDouble(elementValue.toString()));
            case "AddedUnit" -> activeAddedIngredient.setUnit(parseUnit(elementValue.toString()));
            case ADDED_INGREDIENTS -> saveUsedIngredients();
            case ADDED_INGREDIENT -> addedIngredients.add(activeAddedIngredient);

        }
    }

    private Color parseColor(String line) {
        String[] split = line.split(",");
        return new Color(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
        );
    }

    private Category parseCategory(String categoryName) {
        return categoryList.stream()
                .filter(category -> category.getName().equals(categoryName))
                .findFirst()
                .orElseThrow(
                        () -> new DataManipulationException("Category with name: " + categoryName + " not found!")
                );
    }

    private Ingredient parseIngredient(String ingredientName) {
        return ingredientList.stream()
                .filter(ingredient -> ingredient.getName().equals(ingredientName))
                .findFirst()
                .orElseThrow(
                        () -> new DataManipulationException("Ingredient with name: " + ingredientName + " not found!")
                );
    }

    private PreparationTime parsePreparationTime(String prepTime) {
        String[] split = prepTime.split(" ");
        return new PreparationTime(Integer.parseInt(split[0]), Integer.parseInt(split[2]));
    }

    private void saveUsedIngredients() {
        AddedIngredientsTableModel tableModel = activeRecipe.getUsedIngredients();
        addedIngredients.forEach(tableModel::addRow);
    }

    private Unit parseUnit(String unitAbbr) {
//        for (var baseUnit : BaseUnits.getBaseUnitList()) {
//            if (baseUnit.getAbbreviation().equals(unitAbbr)) {
//                return baseUnit;
//            }
//        }
        for (var customUnit : unitList) {
            if (customUnit.getAbbreviation().equals(unitAbbr)) {
                return customUnit;
            }
        }
        throw new DataManipulationException("Unit: " + unitAbbr + " was not found!");
    }

    public Batch getBatch() {
        return new Batch(recipeList, ingredientList, unitList, categoryList);
    }

}
