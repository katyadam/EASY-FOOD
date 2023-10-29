package cz.muni.fi.pv168.project.ui.dialog;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

import static javax.swing.JOptionPane.*;

abstract class EntityDialog<E> {

    private final JPanel panel = new JPanel();

    private JPanel leftPanel = null;

    private JPanel rightPanel = null;

    EntityDialog() {
        panel.setLayout(new MigLayout("wrap 4"));
    }

    EntityDialog(boolean twoPanels) {
        if (twoPanels) {
            leftPanel = new JPanel();
            rightPanel = new JPanel();
            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(rightPanel, BorderLayout.EAST);
            leftPanel.setLayout(new MigLayout("wrap 4"));
            rightPanel.setLayout(new MigLayout("fill "));
        }
    }

    void add(String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(component, "wmin 250lp, grow, span 3");
    }

    void addLeft(String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        leftPanel.add(label);
        leftPanel.add(component, "wmin 250lp, grow, span 3");
    }

    void addLeft(JComponent component, String constraints) {
        leftPanel.add(component, constraints);
    }

    void addRight(String labelText, JComponent component, String constraints) {
        JLabel label = new JLabel(labelText);
        rightPanel.add(label, "wrap");
        if (Objects.equals(constraints, "")) {
            constraints = "wmin 250lp, grow, span 3";
        }
        rightPanel.add(component, constraints);
    }


    void addLeft(JComponent component1, JComponent component2, JComponent component3, JComponent component4, JComponent component5) {
        leftPanel.add(component1, "grow, split 5, span 5");
        leftPanel.add(component2);
        leftPanel.add(component3);
        leftPanel.add(component4);
        leftPanel.add(component5);
    }

    void add(String ingredient) {
        JLabel label = new JLabel(ingredient);
        panel.add(label, "span 3, grow, wmin 250lp");

    }

    void add(JComponent scrollPane) {
        panel.add(scrollPane, "span 20, grow");
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
