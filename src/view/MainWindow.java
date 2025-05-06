// File: src/view/MainWindow.java
package view;

import controller.WeatherController;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window. Holds all sub‐views and wires them to the controller.
 */
public class MainWindow extends JFrame {
    private final WeatherController controller;

    public MainWindow(WeatherController controller) {
        super("Realtime Weather");
        this.controller = controller;
        initUI();
        controller.initApp();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8,8));

        // Top: city/date selector + unit chooser
        JPanel top = new JPanel(new BorderLayout(8,8));
        top.add(new CitySelectionView(controller), BorderLayout.CENTER);
        top.add(new UnitSelectionView(controller), BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Center: tracked cities
        add(new TrackedCitiesView(controller), BorderLayout.CENTER);

        // Bottom: stats
        add(new StatsView(controller), BorderLayout.SOUTH);

        setSize(700, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
