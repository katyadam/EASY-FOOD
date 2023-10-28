package cz.muni.fi.pv168.project;


import cz.muni.fi.pv168.project.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        initLookAndFeel();
        EventQueue.invokeLater(() -> new MainWindow().show());
    }

    private static void initLookAndFeel() {

        try {
            // UITHEME
            UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
    }
}
