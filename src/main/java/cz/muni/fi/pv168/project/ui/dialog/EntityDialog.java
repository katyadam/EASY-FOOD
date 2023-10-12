package cz.muni.fi.pv168.project.ui.dialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 4"));
    }

    void add(String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow, span 3");
    }

    void add(JComponent component1,JComponent component2,JComponent component3, JComponent component4) {
        panel.add(component1);
        panel.add(component2);
        panel.add(component3);
        panel.add(component4);
    }
    void add(String ingredient) {
        JLabel label = new JLabel(ingredient);
        panel.add(label, "span 3, grow, wmin 250lp");

    }
    void add(JComponent scrollPane) {
        panel.add(scrollPane,"span 40");
    }

    abstract E getEntity();

    public Optional<E> show(JComponent parentComponent, String title) {
        int result = JOptionPane.showOptionDialog(parentComponent, panel, title,
                OK_CANCEL_OPTION, PLAIN_MESSAGE, null, null, null);
        if (result == OK_OPTION) {
            return Optional.of(getEntity());
        } else {
            return Optional.empty();
        }
    }
}
