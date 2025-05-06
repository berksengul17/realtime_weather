// File: src/view/StatsView.java
package view;

import controller.WeatherController;
import model.UnitType;
import model.WeatherStats;
import model.WeatherSubject;
import model.WeatherObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Panel displaying all required historical statistics:
 * • Highest avg temp city
 * • Lowest avg temp city
 * • Lowest Jan temp city
 * • Highest May humidity city
 * • Highest Apr wind city
 *
 * Observes model so it refreshes if unit or data changes.
 */
public class StatsView extends JPanel implements WeatherObserver {
    private final WeatherController controller;
    private JLabel highestAvgTempLabel;
    private JLabel lowestAvgTempLabel;
    private JLabel lowestJanTempLabel;
    private JLabel highestMayHumLabel;
    private JLabel highestAprWindLabel;

    public StatsView(WeatherController controller) {
        this.controller = controller;
        initComponents();
        controller.getModel().registerObserver(this);
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Statistics"));
        setLayout(new GridLayout(5, 1, 4, 4));

        highestAvgTempLabel = new JLabel();
        lowestAvgTempLabel  = new JLabel();
        lowestJanTempLabel  = new JLabel();
        highestMayHumLabel  = new JLabel();
        highestAprWindLabel = new JLabel();

        add(highestAvgTempLabel);
        add(lowestAvgTempLabel);
        add(lowestJanTempLabel);
        add(highestMayHumLabel);
        add(highestAprWindLabel);

        update();  // initial fill
    }

    /** Called by model; fetch fresh stats and display them. */
    @Override
    public void update() {
        WeatherStats stats = controller.onRequestStats();
        UnitType unit = controller.getModel().getUnit();

        highestAvgTempLabel.setText(
            "Highest Avg Temp: " + stats.getHighestAvgTempCity() );
        lowestAvgTempLabel.setText(
            "Lowest  Avg Temp: " + stats.getLowestAvgTempCity() );
        lowestJanTempLabel.setText(
            "Lowest Temp in Jan: " + stats.getLowestTempJanCity() );
        highestMayHumLabel.setText(
            "Highest Avg Hum in May: " + stats.getHighestAvgHumidityMayCity() );
        highestAprWindLabel.setText(
            "Highest Avg Wind in Apr: " + stats.getHighestAvgWindAprCity() );
    }
}
