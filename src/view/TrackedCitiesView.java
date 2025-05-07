

// File: src/view/TrackedCitiesView.java
package view;

import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

import model.WeatherDataManager;
import model.WeatherRecord;
import model.WeatherSubject;

/**
 * Panel showing the current weather for all tracked cities.
 * Implements WeatherObserver to refresh automatically.
 */
public class TrackedCitiesView extends JPanel implements WeatherObserver {
    private DefaultListModel<String> listModel;
    private JList<String> cityList;
    private TrackedCitiesListener listener;

    public TrackedCitiesView() {
        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Tracked Cities"));
        setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        cityList = new JList<>(listModel);
        cityList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(cityList), BorderLayout.CENTER);
    }

    public void addRefreshListener(TrackedCitiesListener l) {
        this.listener = l;
    }

    public void showTrackedCities(Map<String, WeatherRecord> data, TempUnit unit) {
        listModel.clear();
        data.forEach((city, rec) -> {
            double t = rec.getTemperature();
            String line = String.format("%-12s %6.1f°%s",
                city, t, unit == TempUnit.CELSIUS ? "C" : "F");
            listModel.addElement(line);
        });
    }

    @Override
    public void update(WeatherSubject subject) {
        WeatherDataManager dataManager = (WeatherDataManager) subject;
        if (listener != null)
            try {
                listener.onRefreshRequested(dataManager.getDate());
            } catch (WeatherDataManagerNotValidException e) {
                e.printStackTrace();
            }
    }
}