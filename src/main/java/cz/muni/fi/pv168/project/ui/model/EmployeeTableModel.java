package cz.muni.fi.pv168.project.ui.model;


import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

//    private final List<Employee> employees;
//
//    private final List<Column<Employee, ?>> columns = List.of(
//            Column.editable("First name", String.class, Employee::getFirstName, Employee::setFirstName),
//            Column.editable("Last name", String.class, Employee::getLastName, Employee::setLastName),
//            Column.editable("Department", Department.class, Employee::getDepartment, Employee::setDepartment)
//    );
//
//    public EmployeeTableModel(List<Employee> employees) {
//        this.employees = new ArrayList<>(employees);
//    }
//
//    @Override
//    public int getRowCount() {
//        return employees.size();
//    }
//
//    @Override
//    public int getColumnCount() {
//        return columns.size();
//    }
//
//    @Override
//    public Object getValueAt(int rowIndex, int columnIndex) {
//        Employee employee = getEntity(rowIndex);
//        return columns.get(columnIndex).getValue(employee);
//    }
//
//    @Override
//    public String getColumnName(int columnIndex) {
//        return columns.get(columnIndex).getName();
//    }
//
//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return columns.get(columnIndex).getColumnType();
//    }
//
//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columns.get(columnIndex).isEditable();
//    }
//
//    @Override
//    public void setValueAt(Object value, int rowIndex, int columnIndex) {
//        Employee employee = getEntity(rowIndex);
//        columns.get(columnIndex).setValue(value, employee);
//    }
//
//    public void deleteRow(int rowIndex) {
//        employees.remove(rowIndex);
//        fireTableRowsDeleted(rowIndex, rowIndex);
//    }
//
//    public void addRow(Employee employee) {
//        int newRowIndex = employees.size();
//        employees.add(employee);
//        fireTableRowsInserted(newRowIndex, newRowIndex);
//    }
//
//    public void updateRow(Employee employee) {
//        int rowIndex = employees.indexOf(employee);
//        fireTableRowsUpdated(rowIndex, rowIndex);
//    }
//
//    public Employee getEntity(int rowIndex) {
//        return employees.get(rowIndex);
//    }
}
