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
        setLayout(new BorderLayout(8, 8));
        
        // Create top panel with city selection on left and unit selection on right
        JPanel top = new JPanel(new BorderLayout(8, 8));
        top.add((Component) citySelectionView, BorderLayout.CENTER);
        top.add((Component) unitSelectionView, BorderLayout.EAST);
        // Add more padding to the top panel
        top.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(top, BorderLayout.NORTH);
        
        // Create main content panel with both tracked cities and stats
        JPanel contentPanel = new JPanel(new BorderLayout(0, 8));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        
        // Add tracked cities with exact content height
        JComponent trackedCitiesComponent = (JComponent) trackedCitiesView;
        contentPanel.add(trackedCitiesComponent, BorderLayout.NORTH);
        
        // Add stats view to fill the rest of the space
        JComponent statsComponent = (JComponent) statsView;
        contentPanel.add(statsComponent, BorderLayout.CENTER);
        
        // Add the content panel to the main layout
        add(contentPanel, BorderLayout.CENTER);

        // Use pack() instead of hardcoded size to ensure optimal component sizes
        pack();
        
        // Set a minimum size for the window
        setMinimumSize(new Dimension(700, 550));
        
        // Set initial size that better fits the content
        setSize(750, 600);
        setLocationRelativeTo(null);
    }
}
