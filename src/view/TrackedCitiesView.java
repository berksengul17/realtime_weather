// File: src/view/TrackedCitiesView.java
package view;

import controller.WeatherController;
import model.UnitType;
import model.WeatherRecord;
import model.WeatherSubject;
import model.WeatherObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Panel showing the current weather for all tracked cities in a simple list.
 * Observes the model so that it can update whenever the unit or the data changes.
 */
public class TrackedCitiesView extends JPanel implements WeatherObserver {
    private final WeatherController controller;
    private DefaultListModel<String> listModel;
    private JList<String> cityList;

    public TrackedCitiesView(WeatherController controller) {
        this.controller = controller;
        initComponents();
        controller.getModel().registerObserver(this);
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Tracked Cities"));
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        cityList = new JList<>(listModel);
        cityList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(cityList), BorderLayout.CENTER);
        update();  // initial fill
    }

    /** Fetches current tracked cities + weather from controller and repaints list. */
    @Override
    public void update() {
        listModel.clear();
        UnitType unit = controller.getModel().getUnit();
        Map<String, WeatherRecord> map = controller.onRequestTrackedWeather();
        for (Map.Entry<String, WeatherRecord> e : map.entrySet()) {
            double t = e.getValue().getTemperature(unit);
            String line = String.format("%-12s %6.1f°%s",
                e.getKey(),
                t,
                unit == UnitType.CELSIUS ? "C" : "F");
            listModel.addElement(line);
        }
    }
}
