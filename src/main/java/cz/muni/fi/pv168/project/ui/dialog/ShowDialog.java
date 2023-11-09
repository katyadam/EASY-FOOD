package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.AddedIngredient;
import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.*;

public class ShowDialog {

    private final Recipe recipe;

    public ShowDialog(Recipe recipe) {
        this.recipe = recipe;
    }

    public void getRecipeInstruction() {
        StringBuilder recipeString = new StringBuilder();
        recipeString.append("<p>");
        recipeString.append("<b>Recipe Name: </b>").append(recipe.getName()).append("<br>");
        recipeString.append("<b>Category Name:</b> ").append(recipe.getCategory().getName()).append("<br>");
        recipeString.append("<b>Nutritional Value [KCAL]:</b> ").append(recipe.getRecipeNutritionalValue()).append("<br>");
        recipeString.append("<b>Portions:</b> ").append(recipe.getPortions()).append("<br>");
        recipeString.append("<b>Preparation Time:</b> ").append(recipe.getPreparationTime().hours()).append(" hours ")
                .append(recipe.getPreparationTime().minutes()).append(" minutes").append("<br>").append("<br>");
        recipeString.append("<b>Ingredients:</b>\n");
        for (int i = 0; i < recipe.getUsedIngredients().getRowCount(); i++) {
            AddedIngredient ingredient = recipe.getUsedIngredients().getEntity(i);
            recipeString.append("-> ").append(ingredient.getIngredient().getName())
                    .append("  ").append(ingredient.getQuantity()).append(" ").append(ingredient.getUnit()).append("<br>");
        }
        recipeString.append("<br>");

        recipeString.append("<b>Description: </b>").append("<br>");
        for (String descriptionLine : recipe.getDescription().split("\n")) {
            recipeString.append(descriptionLine).append("<br>");
        }
        recipeString.append("</p>");

        JEditorPane textArea = new JEditorPane("text/html", recipeString.toString());
        System.out.println(textArea.getEditorKit() + textArea.getText());
        textArea.setEditable(false);
        textArea.setSize(100, 200);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Recipe Details", JOptionPane.PLAIN_MESSAGE);
    }
}

