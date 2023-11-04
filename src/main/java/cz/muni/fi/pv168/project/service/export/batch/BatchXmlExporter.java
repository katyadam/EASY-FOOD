package cz.muni.fi.pv168.project.service.export.batch;

import cz.muni.fi.pv168.project.model.AddedIngredient;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.service.export.DataManipulationException;
import cz.muni.fi.pv168.project.service.export.format.Format;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BatchXmlExporter implements BatchExporter{
    @Override
    public void exportBatch(Batch batch, String filePath) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
            writeStart("EasyFood", bufferedWriter, 0);
            exportCategories(batch, bufferedWriter, 1);
            exportCustomUnits(batch, bufferedWriter, 1);
            exportIngredients(batch, bufferedWriter, 1);
            exportRecipes(batch, bufferedWriter, 1);
            writeEnd("EasyFood", bufferedWriter, 0);
            bufferedWriter.close();
        } catch (IOException e) {
            throw new DataManipulationException("Bad file");
        }
    }

    private void exportRecipes(Batch batch, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Recipes", bufferedWriter, tabs);
        for ( Recipe recipe: batch.recipes()) {
            exportRecipe(recipe, bufferedWriter, tabs + 1);
        };
        writeEnd("Recipes", bufferedWriter, tabs);
    }

    private void exportRecipe(Recipe recipe, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Recipe", bufferedWriter, tabs);
        writeAtribute(recipe.getRecipeName(), "FullName", bufferedWriter, tabs + 1);
        writeAtribute(recipe.getRecipeNutritionalValue(), "NutritionalValue", bufferedWriter, tabs + 1);
        writeAtribute(recipe.getCategoryName(), "Category", bufferedWriter, tabs + 1);
        writeAtribute(recipe.getPortions(), "Portions", bufferedWriter, tabs + 1);
        writeAtribute(recipe.getPreparationTime().toString(), "PreparationTime", bufferedWriter, tabs + 1);
        writeAtribute(recipe.getDescription(), "Description", bufferedWriter, tabs + 1);

        exportAddedIngredients(recipe.getUsedIngredients().getEntities(), bufferedWriter, tabs + 1);
        writeEnd("Recipe", bufferedWriter, tabs);
    }

    private void exportAddedIngredients(List<AddedIngredient> addedIngredients, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("UsedIngredients", bufferedWriter, tabs);
        for (AddedIngredient added : addedIngredients) {
            exportAddedIngredient(added, bufferedWriter, tabs + 1);
        }
        writeEnd("UsedIngredients", bufferedWriter, tabs);
    }

    private void exportAddedIngredient(AddedIngredient added, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Ingredient", bufferedWriter, tabs);
        writeAtribute(added.getIngredient().getName(), "IngredientName", bufferedWriter, tabs + 1);
        writeAtribute(added.getQuantity().toString(), "Quantity", bufferedWriter, tabs + 1);
        writeAtribute(added.getUnit().getAbbreviation(), "Unit", bufferedWriter, tabs + 1);
        writeEnd("Ingredient", bufferedWriter, tabs);
    }

    private void exportIngredients(Batch batch, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Ingredients", bufferedWriter, tabs);
        for ( Ingredient ingredient: batch.ingredients()) {
            exportIngredient(ingredient, bufferedWriter, tabs + 1);
        };
        writeEnd("Ingredients", bufferedWriter, tabs);
    }

    private void exportIngredient(Ingredient ingredient, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Ingredient", bufferedWriter, tabs);
        writeAtribute(ingredient.getName(), "FullName", bufferedWriter, tabs + 1);
        writeAtribute(ingredient.getUnitAbbreviation(), "FullName", bufferedWriter, tabs + 1);
        writeAtribute(ingredient.getNutritionalValue(), "NutritionalValue", bufferedWriter, tabs + 1);
        writeEnd("Ingredient", bufferedWriter, tabs);
    }

    private void exportCustomUnits(Batch batch, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("CustomUnits", bufferedWriter, tabs);
        for ( CustomUnit customUnit: batch.customUnits()) {
            exportCustomUnit(customUnit, bufferedWriter, tabs + 1);
        };
        writeEnd("CustomUnits", bufferedWriter, tabs);
    }

    private void exportCustomUnit(CustomUnit customUnit, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("CustomUnit", bufferedWriter, tabs);
        writeAtribute(customUnit.getFullName(), "FullName", bufferedWriter, tabs + 1);
        writeAtribute(customUnit.getAbbreviation(), "Abbreviation", bufferedWriter, tabs + 1);
        writeAtribute(customUnit.getBaseAmount(), "BaseAmount", bufferedWriter, tabs + 1);
        writeAtribute(customUnit.getBaseAmountNumber(), "BaseAmountNumber", bufferedWriter, tabs + 1);
        writeEnd("CustomUnit", bufferedWriter, tabs);
    }

    private void exportCategories(Batch batch, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Categories", bufferedWriter, tabs);
        for ( Category category: batch.categories()) {
            exportCategory(category, bufferedWriter, tabs + 1);
        };
        writeEnd("Categories", bufferedWriter, tabs);
    }

    private void exportCategory(Category category, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeStart("Category", bufferedWriter, tabs);
        writeAtribute(category.getName(), "Name", bufferedWriter, tabs + 1);
        writeAtribute(category.getColorCode(), "Color", bufferedWriter, tabs + 1);
        writeEnd("Category", bufferedWriter, tabs);
    }

    private void writeStart(String value, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeTabs(tabs, bufferedWriter);
        bufferedWriter.write("<" + value + ">\n");
    }

    private void writeEnd(String value, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeTabs(tabs, bufferedWriter);
        bufferedWriter.write("</" + value + ">\n");
    }

    private void writeAtribute(String value, String name, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeTabs(tabs, bufferedWriter);
        bufferedWriter.write("<" + name + ">");
        bufferedWriter.write(value);
        writeEnd(name, bufferedWriter, 0);
    }
    private void writeAtribute(int value, String name, BufferedWriter bufferedWriter, int tabs) throws IOException {
        bufferedWriter.write("<" + name + ">");
        bufferedWriter.write(value);
        writeEnd(name, bufferedWriter, 0);
    }
    private void writeTabs(int tabs, BufferedWriter bufferedWriter) throws IOException {
        for (int i = 0; i < tabs; i++) {
            bufferedWriter.write("\t");
        }
    }



    @Override
    public Format getFormat() {
        return new Format("XML", List.of("xml"));
    }
}
