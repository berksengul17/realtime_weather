// File: src/view/StatsView.java
package view;

import enums.TempUnit;
import enums.WeatherStatKey;
import exception.WeatherStatsNotValidException;
import java.awt.*;
import javax.swing.*;
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
        setBorder(BorderFactory.createTitledBorder("Statistics"));
        setLayout(new GridLayout(5, 1, 4, 4));
        highestAvgTempLabel = new JLabel(); add(highestAvgTempLabel);
        lowestAvgTempLabel  = new JLabel(); add(lowestAvgTempLabel);
        lowestJanTempLabel  = new JLabel(); add(lowestJanTempLabel);
        highestMayHumLabel  = new JLabel(); add(highestMayHumLabel);
        highestAprWindLabel = new JLabel(); add(highestAprWindLabel);
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
        lowestAvgTempLabel .setText(
            "Lowest Avg Temp: "  + stats.getStat(WeatherStatKey.LOWEST_AVG_TEMP));
        lowestJanTempLabel .setText(
            "Lowest Temp in Jan: " + stats.getStat(WeatherStatKey.LOWEST_TEMP_IN_JANUARY));
        highestMayHumLabel .setText(
            "Highest Avg Hum in May: " + stats.getStat(WeatherStatKey.HIGHEST_AVG_HUMIDITY_IN_MAY));
        highestAprWindLabel.setText(
            "Highest Avg Wind in Apr: "  + stats.getStat(WeatherStatKey.HIGHEST_AVG_WIND_SPEED_IN_APRIL));
    } catch (WeatherStatsNotValidException e) {
        // If stats isn’t marked valid yet, show "N/A"
        highestAvgTempLabel.setText("Highest Avg Temp: N/A");
        lowestAvgTempLabel .setText("Lowest Avg Temp: N/A");
        lowestJanTempLabel .setText("Lowest Temp in Jan: N/A");
        highestMayHumLabel .setText("Highest Avg Hum in May: N/A");
        highestAprWindLabel.setText("Highest Avg Wind in Apr: N/A");
    }
}


    @Override
    public void update(WeatherSubject subject) {
        if (listener != null) listener.onStatsRequested();
    }
}