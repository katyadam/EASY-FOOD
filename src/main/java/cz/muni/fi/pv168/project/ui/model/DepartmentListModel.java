package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.AbstractListModel;
import java.util.List;

public class DepartmentListModel extends AbstractListModel<Recipe> {
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Recipe getElementAt(int index) {
        return null;
    }

//    private final List<Department> departments;
//
//    public DepartmentListModel(List<Department> departments) {
//        this.departments = departments;
//    }
//
//    @Override
//    public int getSize() {
//        return departments.size();
//    }
//
//    @Override
//    public Department getElementAt(int index) {
//        return departments.get(index);
//    }
}
