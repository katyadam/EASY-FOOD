package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.GUILayout;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow {

    private final JFrame frame;
    private final GUILayout layout;
    private boolean isCleared = false;

//    private final Action quitAction = new QuitAction();
//    private final Action addAction;
//    private final Action deleteAction;
//    private final Action editAction;

    public MainWindow() {

        layout = new GUILayout();
        frame = new JFrame("EasyFood");
        frame.setSize(1920,1080);
        frame.setContentPane(layout.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layout.getAddButton().setCursor(new Cursor(Cursor.HAND_CURSOR));

        // removes text from Search Bar after typing
        layout.getSearchRecipesTextField().addKeyListener(new ClearTextFieldKeyListener());
    }

    private class ClearTextFieldKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            if (!isCleared) {
                layout.getSearchRecipesTextField().setText("");
                isCleared = true;
            }
        }
    }

    public void show() {
        frame.setVisible(true);
    }
//
//    private JFrame createFrame() {
//        var frame = new JFrame("Employee records");
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        return frame;
//    }

//    private JTable createEmployeeTable(List<Employee> employees) {
//        var model = new EmployeeTableModel(employees);
//        var table = new JTable(model);
//        table.setAutoCreateRowSorter(true);
//        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
//        var genderComboBox = new JComboBox<>(Gender.values());
//        table.setDefaultEditor(Gender.class, new DefaultCellEditor(genderComboBox));
//        return table;
//    }

//    private JPopupMenu createEmployeeTablePopupMenu() {
//        var menu = new JPopupMenu();
//        menu.add(deleteAction);
//        menu.add(editAction);
//        return menu;
//    }

//    private JMenuBar createMenuBar() {
//        var menuBar = new JMenuBar();
//        var editMenu = new JMenu("Edit");
//        editMenu.setMnemonic('e');
//        editMenu.add(addAction);
//        editMenu.addSeparator();
//        editMenu.add(quitAction);
//        menuBar.add(editMenu);
//        return menuBar;
//    }

//    private JToolBar createToolbar() {
//        var toolbar = new JToolBar();
//        toolbar.add(quitAction);
//        toolbar.addSeparator();
//        return toolbar;
//    }

//    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
//        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
//        // here you can put the code for handling selection change
//    }
}
