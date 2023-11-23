package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.service.crud.AddedIngredientCrudService;
import cz.muni.fi.pv168.project.business.service.validation.AddedIngredientValidator;
import cz.muni.fi.pv168.project.storage.memory.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;

import java.awt.*;
import java.util.ArrayList;

public class Recipe extends Entity {
    private PreparationTime preparationTime;
    private int portions;
    private Category category;
    private String description = "No recipe description";
    private int nutritionalValue;


    private final AddedIngredientsTableModel usedIngredients = new AddedIngredientsTableModel(
            new AddedIngredientCrudService(
                    new InMemoryRepository<>(new ArrayList<>()),
                    new AddedIngredientValidator(),
                    new UuidGuidProvider()
            ));

    public Recipe() {
    }

    public Recipe(
            String guid,
            String recipeName,
            Category category,
            PreparationTime preparationTime,
            int nutritionalValue,
            int portions,
            String description
    ) {
        super(guid);
        this.name = recipeName;
        this.preparationTime = preparationTime;
        this.nutritionalValue = nutritionalValue;
        this.portions = portions;
        this.category = category;
        this.description = description;
    }

    public String getRecipeName() {
        return super.name;
    }

    public void setRecipeName(String recipeName) {
        super.name = recipeName;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public void setPreparationTime(PreparationTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public PreparationTime getPreparationTime() {
        int hours = preparationTime.hours();
        int minutes = preparationTime.minutes();
        return new PreparationTime(hours, minutes);
    }

    public void destroy() {
        if (category != null) {
            category.removeRecipe(this);
        }
        for (AddedIngredient addedIngredient : usedIngredients.getEntities()) {
            addedIngredient.getIngredient().removeRecipe(this);
            if (addedIngredient.getUnit() instanceof Entity) {
                ((Entity) addedIngredient.getUnit()).removeRecipe(this);
            }
        }
    }

    public int getPortions() {
        return portions;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }

    public Color getCategoryColor() {
        return category != null ? category.getColor() : Color.black;
    }

    public int getRecipeNutritionalValue() {
        return usedIngredients.getTotalNutritionalValue();
    }

    public AddedIngredientsTableModel getUsedIngredients() {
        return usedIngredients;
    }

    @Override
    public String toString() {
        return name;
    }
}
