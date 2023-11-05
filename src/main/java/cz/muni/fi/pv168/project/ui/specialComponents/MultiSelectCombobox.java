package cz.muni.fi.pv168.project.ui.specialComponents;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Skvara
 */
public class MultiSelectCombobox<T> extends JButton {
    private List<T> items;
    private List<T> selectedItems = new ArrayList<>();
    private JPopupMenu menu;

    public MultiSelectCombobox(List<T> items, String name) {
        super(name);
        this.items = items;
        createMenu();
        addActionListener(e -> {
            if (!menu.isVisible()) {
                Point p = getLocationOnScreen();
                menu.setInvoker(this);
                menu.setLocation((int) p.getX(),
                        (int) p.getY() + this.getHeight());
                menu.setVisible(true);
            } else {
                menu.setVisible(false);
            }

        });
    }
    private JPopupMenu createMenu() {
        menu = new JPopupMenu();
        for (T item : items) {
            menu.add(new SelectedAction<>(selectedItems, item, menu, this));
        }
        return menu;
    }
    public void reload(List<T> items) {
        this.items = items;
        this.selectedItems = new ArrayList<>();
        createMenu();
    }
    public List<T> reap() {
        return items;
    }
}
class SelectedAction<T> extends AbstractAction {

    public List<T> selectedItems;
    private final T item;
    private final JPopupMenu menu;
    private final JButton button;

    private boolean selected = false;
    public SelectedAction(List<T> selectedItems, T item, JPopupMenu menu, MultiSelectCombobox<T> button) {
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
