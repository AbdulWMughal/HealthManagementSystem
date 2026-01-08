package com.healthcare;

import com.healthcare.controller.MainController;
import com.healthcare.utils.CSVDatabaseLoader;
import com.healthcare.view.MainViewGUI;

import javax.swing.*;

public class HealthcareApp {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            
            CSVDatabaseLoader db = new CSVDatabaseLoader();
            db.loadData();

            
            MainViewGUI view = new MainViewGUI();

            
            new MainController(view, db);

            // 4. Show View
            view.setVisible(true);
        });
    }
}