package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class ShowDialog {

    private final Recipe recipe;

    public ShowDialog(Recipe recipe) {
        this.recipe = recipe;
    }

    public void getRecipeInstruction() {
        StringBuilder recipeString = new StringBuilder();
        /*
        Font boldfont = new Font(Font.DIALOG,Font.BOLD, 12);

        AttributedString a = new AttributedString("Recipe Name:");
        AttributedString b = new AttributedString("Category Name: ");
        AttributedString c = new AttributedString("Nutritional Value: ");
        AttributedString d = new AttributedString("Portions: ");
        AttributedString e = new AttributedString("Preparation Time: ");
        AttributedString f = new AttributedString("Ingredients:\n");

        a.addAttribute(TextAttribute.FONT,boldfont);
        b.addAttribute(TextAttribute.FONT,boldfont);
        c.addAttribute(TextAttribute.FONT,boldfont);
        d.addAttribute(TextAttribute.FONT,boldfont);
        e.addAttribute(TextAttribute.FONT,boldfont);
        f.addAttribute(TextAttribute.FONT,boldfont);
        String test = a.toString();
        System.out.println(test);
*/
        recipeString.append("<p>");
        recipeString.append("<b>Recipe Name: </b>").append(recipe.getRecipeName()).append(HTMLEditorKit.endLineAction);
        recipeString.append("<b>Category Name:</b> ").append(recipe.getCategory().getName()).append(System.lineSeparator());
        recipeString.append("<b>Nutritional Value:v</b> ").append(recipe.getNutritionalValue()).append(System.lineSeparator());
        recipeString.append("<b>Portions:</b> ").append(recipe.getPortions()).append(System.lineSeparator());
        recipeString.append("<b>Preparation Time:</b> ").append(recipe.getPreparationTime().hours()).append(" hours ")
                .append(recipe.getPreparationTime().minutes()).append(" minutes").append(System.lineSeparator()).append(System.lineSeparator());
        recipeString.append("<b>Ingredients:</b>\n");
        for (int i = 0; i < recipe.getUsedIngredients().getRowCount(); i++) {
            Triplet ingredient = recipe.getUsedIngredients().getEntity(i);
            recipeString.append("-> ").append(ingredient.ingredient().getName())
                    .append("  ").append(ingredient.value()).append(" ").append(ingredient.unit()).append(System.lineSeparator());
        }
        recipeString.append(System.lineSeparator());

        recipeString.append("<b>Description: </b>").append(System.lineSeparator()).append(recipe.getDesription()).append(System.lineSeparator());
        recipeString.append("</p>");

        JEditorPane textArea = new JEditorPane("text/html",recipeString.toString());
        System.out.println(textArea.getEditorKit() + textArea.getText());
        textArea.setEditable(false);
        textArea.setSize(100, 200);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(null, scrollPane, "Recipe Details", JOptionPane.PLAIN_MESSAGE);
    }
}

