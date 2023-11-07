package cz.muni.fi.pv168.project.ui.specialComponents;

import cz.muni.fi.pv168.project.ui.resources.Icons;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Skvara
 */
public class MultiSelectCombobox<T> extends JButton {
    private List<T> items;
    private List<T> selectedItems = new ArrayList<>();
    private JScrollPopupMenu menu;
    private JComboBox<T> coloring = new JComboBox<>();

    private boolean showing = false;

    public MultiSelectCombobox(List<T> items, String name) {
        super(name);
        super.setBorder(coloring.getBorder());
        super.setBackground(coloring.getBackground());
        this.items = items;
        createMenu();
        addActionListener(e -> {
            if (!showing) {
                menu.show(this,0,this.getHeight());
                showing = true;
                return;
            }
            showing = false;

        });
    }
    private JPopupMenu createMenu() {
        menu = new JScrollPopupMenu();
        menu.setMaximumVisibleRows(6);
        for (T item : items) {
            menu.add( new SelectedAction<T>(selectedItems, item, menu, this));
        }
        return menu;
    }
    public void reload(List<T> items) {
        this.items = items;
        this.selectedItems = new ArrayList<>();
        createMenu();
    }
    public List<T> reap() {
        return selectedItems;
    }
}
class SelectedAction<T> extends AbstractAction {

    public List<T> selectedItems;
    private final T item;
    private final JPopupMenu menu;
    private final JButton button;

    private boolean selected = false;
    public SelectedAction(List<T> selectedItems, T item, JScrollPopupMenu menu, MultiSelectCombobox<T> button) {
        super(item.toString());
        this.item = item;
        this.menu = menu;
        this.button = button;
        this.selectedItems = selectedItems;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        menu.show(button, 0, button.getHeight());
        if ( selected ) {
            selectedItems.remove(item);
            putValue(Action.SMALL_ICON, null);
            selected = false;
        } else {
            selectedItems.add(item);
            putValue(Action.SMALL_ICON, Icons.SELECTED_ICON);
            selected = true;
        }
    }
}
