
// File: src/view/MainWindow.java
package view;

import java.awt.*;
import javax.swing.*;

/**
 * Main frame assembling all sub-views.
 */
public class MainWindow extends JFrame {
    public MainWindow(
        WeatherObserver citySelectionView,
        WeatherObserver unitSelectionView,
        WeatherObserver trackedCitiesView,
        WeatherObserver statsView
    ) {
        super("Realtime Weather");
        initUI(citySelectionView, unitSelectionView, trackedCitiesView, statsView);
    }

    private void initUI(
        WeatherObserver citySelectionView,
                WeatherObserver unitSelectionView,
                WeatherObserver trackedCitiesView,
                WeatherObserver statsView
            ) {
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new BorderLayout(8,8));
        
                JPanel top = new JPanel(new BorderLayout(8,8));
                top.add((Component) citySelectionView, BorderLayout.CENTER);
                top.add((Component) unitSelectionView, BorderLayout.EAST);
                add(top, BorderLayout.NORTH);
        
                add((Component) trackedCitiesView, BorderLayout.CENTER);
                add((Component) statsView, BorderLayout.SOUTH);

        setSize(700,600);
        setLocationRelativeTo(null);
    }
}
