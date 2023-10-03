package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Department;
import cz.muni.fi.pv168.project.ui.dialog.EmployeeDialog;
import cz.muni.fi.pv168.project.ui.model.EmployeeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class AddAction extends AbstractAction {

    private final JTable employeeTable;
    private final TestDataGenerator testDataGenerator;
    private final ListModel<Department> departmentListModel;

    public AddAction(JTable employeeTable, TestDataGenerator testDataGenerator, ListModel<Department> departmentListModel) {
        super("Add", Icons.ADD_ICON);
        this.employeeTable = employeeTable;
        this.testDataGenerator = testDataGenerator;
        this.departmentListModel = departmentListModel;
        putValue(SHORT_DESCRIPTION, "Adds new employee");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EmployeeTableModel employeeTableModel = (EmployeeTableModel) employeeTable.getModel();
        EmployeeDialog dialog = new EmployeeDialog(testDataGenerator.createTestEmployee(), departmentListModel);
        dialog.show(employeeTable, "Add Employee")
                .ifPresent(employeeTableModel::addRow);
    }
}
