package cz.muni.fi.pv168.project.ui.listeners;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class StatisticsUpdater implements TableModelListener {
    private JTable table;
    private String text;
    private JLabel statistics;

    public StatisticsUpdater(JTable table, String text, JLabel statistics) {
        this.table = table;
        this.text = text;
        this.statistics = statistics;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        StringBuilder builder = new StringBuilder()
                .append(text)
                .append(table.getRowCount())
                .append(" out of ")
                .append(table.getModel().getRowCount());
        statistics.setText(builder.toString());
    }
}
