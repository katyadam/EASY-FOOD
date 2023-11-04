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
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BatchXmlExporter implements BatchExporter{
    @Override
    public void exportBatch(Batch batch, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8))){
            writeStart("EasyFood", bw, 0);
            exportCategories(batch, bw, 1);
            exportCustomUnits(batch, bw, 1);
            exportIngredients(batch, bw, 1);
            exportRecipes(batch, bw, 1);
            writeEnd("EasyFood", bw, 0);
        } catch (IOException e) {
            throw new DataManipulationException("Cannot write into file");
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
        writeAtributeString(recipe.getRecipeName(), "FullName", bufferedWriter, tabs + 1);
        writeAtributeInt(recipe.getRecipeNutritionalValue(), "NutritionalValue", bufferedWriter, tabs + 1);
        writeAtributeString(recipe.getCategoryName(), "Category", bufferedWriter, tabs + 1);
        writeAtributeInt(recipe.getPortions(), "Portions", bufferedWriter, tabs + 1);
        writeAtributeString(recipe.getPreparationTime().toString(), "PreparationTime", bufferedWriter, tabs + 1);
        writeAtributeString(recipe.getDescription(), "Description", bufferedWriter, tabs + 1);

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
        writeAtributeString(added.getIngredient().getName(), "IngredientName", bufferedWriter, tabs + 1);
        writeAtributeString(added.getQuantity().toString(), "Quantity", bufferedWriter, tabs + 1);
        writeAtributeString(added.getUnit().getAbbreviation(), "Unit", bufferedWriter, tabs + 1);
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
        writeAtributeString(ingredient.getName(), "FullName", bufferedWriter, tabs + 1);
        writeAtributeString(ingredient.getUnitAbbreviation(), "FullName", bufferedWriter, tabs + 1);
        writeAtributeInt(ingredient.getNutritionalValue(), "NutritionalValue", bufferedWriter, tabs + 1);
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
        writeAtributeString(customUnit.getFullName(), "FullName", bufferedWriter, tabs + 1);
        writeAtributeString(customUnit.getAbbreviation(), "Abbreviation", bufferedWriter, tabs + 1);
        writeAtributeString(customUnit.getBaseAmount(), "BaseAmount", bufferedWriter, tabs + 1);
        writeAtributeString(customUnit.getBaseAmountNumber(), "BaseAmountNumber", bufferedWriter, tabs + 1);
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
        writeAtributeString(category.getName(), "Name", bufferedWriter, tabs + 1);
        writeAtributeString(category.getColorCode(), "Color", bufferedWriter, tabs + 1);
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

    private void writeAtributeString(String value, String name, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeTabs(tabs, bufferedWriter);
        bufferedWriter.write("<" + name + ">");
        bufferedWriter.write(value);
        writeEnd(name, bufferedWriter, 0);
    }
    private void writeAtributeInt(int value, String name, BufferedWriter bufferedWriter, int tabs) throws IOException {
        writeTabs(tabs, bufferedWriter);
        bufferedWriter.write("<" + name + ">");
        bufferedWriter.write(String.valueOf(value));
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
