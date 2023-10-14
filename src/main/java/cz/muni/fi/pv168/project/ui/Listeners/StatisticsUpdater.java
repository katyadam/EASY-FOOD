package cz.muni.fi.pv168.project.ui.Listeners;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * @author Filip Skvara
 */
public class StatisticsUpdater implements TableModelListener {
    private JTable table;
    private int componentIndex;
    private String text;
    private JToolBar statistics;
    public StatisticsUpdater(JTable table, int componentIndex, String text,JToolBar statistics) {
        this.table = table;
        this.componentIndex = componentIndex;
        this.text = text;
        this.statistics = statistics;
    }
    @Override
    public void tableChanged(TableModelEvent e) {
        ((JLabel) statistics.getComponent(componentIndex))
                .setText(text + table.getModel().getRowCount());
    }
}
