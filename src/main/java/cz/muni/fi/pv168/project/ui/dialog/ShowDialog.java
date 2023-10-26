package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import javax.swing.*;

public class ShowDialog {

    private final Recipe recipe;

    public ShowDialog(Recipe recipe) {
        this.recipe = recipe;
    }

    public void getRecipeInstruction() {
        StringBuilder recipeString = new StringBuilder();
        recipeString.append("Recipe Name: ").append(recipe.getRecipeName()).append("\n");
        recipeString.append("Category Name: ").append(recipe.getCategory().getName()).append("\n");
        recipeString.append("Nutritional Value: ").append(recipe.getNutritionalValue()).append("\n");
        recipeString.append("Portions: ").append(recipe.getPortions()).append("\n");
        recipeString.append("Preparation Time: ").append(recipe.getPreparationTime().getHour()).append(" hodin ")
                .append(recipe.getPreparationTime().getMinute()).append(" minut").append("\n").append("\n");
        recipeString.append("Ingredients:\n");
        for (int i = 0; i < recipe.getUsedIngredients().getRowCount(); i++) {
            Triplet ingredient = recipe.getUsedIngredients().getEntity(i);
            recipeString.append("-> ").append(ingredient.ingredient().getName())
                    .append("  ").append(ingredient.value()).append(" ").append(ingredient.unit()).append("\n");
        }
        recipeString.append("\n");

        recipeString.append("Description:").append("\n").append(recipe.getDesription()).append("\n");

        JTextArea textArea = new JTextArea(recipeString.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Recipe Details", JOptionPane.PLAIN_MESSAGE);
    }
}
