package cz.muni.fi.pv168.project.ui.filters;


import cz.muni.fi.pv168.project.ui.model.AbstractEntityTableModel;

import javax.swing.*;

/**
 * General entity matcher which can be extended by implementing the {@link EntityMatcher#evaluate(Object)}
 * method.
 *
 * @param <T> entity type
 */
public abstract class EntityMatcher<T> extends RowFilter<AbstractEntityTableModel<T>, Integer> {

    @Override
    public boolean include(Entry<? extends AbstractEntityTableModel<T>, ? extends Integer> entry) {
        AbstractEntityTableModel<T> tableModel = entry.getModel();
        int rowIndex = entry.getIdentifier();
        T entity = tableModel.getEntity(rowIndex);

        return evaluate(entity);
    }

    public abstract boolean evaluate(T entity);
}
