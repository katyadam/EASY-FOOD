package cz.muni.fi.pv168.project;

import com.jthemedetecor.OsThemeDetector;
import cz.muni.fi.pv168.project.ui.MainWindow;

import javax.swing.*;
import java.awt.EventQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        initLookAndFeel();
        EventQueue.invokeLater(() -> new MainWindow().show());
    }

    private static void initLookAndFeel() {

        try {
            final OsThemeDetector detector = OsThemeDetector.getDetector();
            final boolean isDarkThemeUsed = detector.isDark();
            if (isDarkThemeUsed) {
                UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.FlatGradiantoMidnightBlueIJTheme");
            } else {
                UIManager.setLookAndFeel("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatLightOwlIJTheme");
            }

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Nimbus layout initialization failed", ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);


    }
}
