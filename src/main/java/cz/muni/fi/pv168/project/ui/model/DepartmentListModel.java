package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Department;

import javax.swing.AbstractListModel;
import java.util.List;

public class DepartmentListModel extends AbstractListModel<Department> {

    private final List<Department> departments;

    public DepartmentListModel(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public int getSize() {
        return departments.size();
    }

    @Override
    public Department getElementAt(int index) {
        return departments.get(index);
    }
}
