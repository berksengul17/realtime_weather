// File: src/view/StatsView.java
package view;

import enums.TempUnit;
import enums.WeatherStatKey;
import exception.WeatherStatsNotValidException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import model.WeatherStats;
import model.WeatherSubject;

/**
 * Panel displaying historical statistics.
 * Implements WeatherObserver to refresh automatically.
 */
public class StatsView extends JPanel implements WeatherObserver {
    private JLabel highestAvgTempLabel;
    private JLabel lowestAvgTempLabel;
    private JLabel lowestJanTempLabel;
    private JLabel highestMayHumLabel;
    private JLabel highestAprWindLabel;
    private StatsListener listener;

    public StatsView() {
        initComponents();
    }

    private void initComponents() {
        // Create the titled border with smaller font
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Statistics");
        titledBorder.setTitleFont(new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 12));
        
        // Use a titled border with minimal padding
        setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            new EmptyBorder(5, 5, 5, 5) // Minimal padding
        ));
        
        // Use a simpler FlowLayout oriented vertically to ensure all stats are visible
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        // Create font for labels
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        
        // Create and add all stats with minimal spacing
        highestAvgTempLabel = createCompactLabel(labelFont, "Highest Avg Temp: N/A");
        lowestAvgTempLabel = createCompactLabel(labelFont, "Lowest Avg Temp: N/A");
        lowestJanTempLabel = createCompactLabel(labelFont, "Lowest Temp in Jan: N/A");
        highestMayHumLabel = createCompactLabel(labelFont, "Highest Avg Humidity in May: N/A");
        highestAprWindLabel = createCompactLabel(labelFont, "Highest Avg Wind in Apr: N/A");
        
        // Add all labels directly to the panel
        add(highestAvgTempLabel);
        add(lowestAvgTempLabel);
        add(lowestJanTempLabel);
        add(highestMayHumLabel);
        add(highestAprWindLabel);
        
        // Align all labels to the left
        highestAvgTempLabel.setAlignmentX(LEFT_ALIGNMENT);
        lowestAvgTempLabel.setAlignmentX(LEFT_ALIGNMENT);
        lowestJanTempLabel.setAlignmentX(LEFT_ALIGNMENT);
        highestMayHumLabel.setAlignmentX(LEFT_ALIGNMENT);
        highestAprWindLabel.setAlignmentX(LEFT_ALIGNMENT);
    }
    
    private JLabel createCompactLabel(Font font, String text) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        // Minimal padding
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        return label;
    }

    public void addStatsListener(StatsListener l) {
        this.listener = l;
    }

    /**
     * Populate all statistic labels from the given WeatherStats.
     * If the stats object is invalid, shows "N/A" for each entry.
     */
    public void displayStats(WeatherStats stats, TempUnit unit) {
        try {
            highestAvgTempLabel.setText(
                "Highest Avg Temp: " + stats.getStat(WeatherStatKey.HIGHEST_AVG_TEMP));
            lowestAvgTempLabel.setText(
                "Lowest Avg Temp: " + stats.getStat(WeatherStatKey.LOWEST_AVG_TEMP));
            lowestJanTempLabel.setText(
                "Lowest Temp in Jan: " + stats.getStat(WeatherStatKey.LOWEST_TEMP_IN_JANUARY));
            highestMayHumLabel.setText(
                "Highest Avg Humidity in May: " + stats.getStat(WeatherStatKey.HIGHEST_AVG_HUMIDITY_IN_MAY));
            highestAprWindLabel.setText(
                "Highest Avg Wind in Apr: " + stats.getStat(WeatherStatKey.HIGHEST_AVG_WIND_SPEED_IN_APRIL));
        } catch (WeatherStatsNotValidException e) {
            // If stats isn't marked valid yet, show "N/A"
            highestAvgTempLabel.setText("Highest Avg Temp: N/A");
            lowestAvgTempLabel.setText("Lowest Avg Temp: N/A");
            lowestJanTempLabel.setText("Lowest Temp in Jan: N/A");
            highestMayHumLabel.setText("Highest Avg Humidity in May: N/A");
            highestAprWindLabel.setText("Highest Avg Wind in Apr: N/A");
        }
    }

    @Override
    public void update(WeatherSubject subject) {
        if (listener != null) listener.onStatsRequested();
    }
}