// File: src/view/CitySelectionView.java
package view;

import controller.WeatherController;
import model.UnitType;
import model.WeatherRecord;
import model.WeatherDataManager;
import model.WeatherSubject;
import model.WeatherObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.SpinnerDateModel;
import java.text.SimpleDateFormat;

/**
 * Panel for choosing City + Date and displaying that day's weather.
 * Implements WeatherObserver so it can refresh its display when the global
 * unit changes (Celsius ↔ Fahrenheit).
 */
public class CitySelectionView extends JPanel implements WeatherObserver {
    private final WeatherController controller;

    private JComboBox<String> cityCombo;
    private JSpinner dateSpinner;
    private JButton showButton;
    private JLabel tempLabel, humidityLabel, windLabel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CitySelectionView(WeatherController controller) {
        this.controller = controller;
        initComponents();
        controller.getModel().registerObserver(this);
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Select City & Date"));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.anchor = GridBagConstraints.WEST;

        // City dropdown
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityCombo = new JComboBox<>(controller.getModel().getAllCityNames().toArray(new String[0]));
        add(cityCombo, gbc);

        // Date spinner
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        add(dateSpinner, gbc);

        // Show button
        gbc.gridx = 2; gbc.gridy = 0; gbc.gridheight = 2;
        showButton = new JButton("Show");
        add(showButton, gbc);

        // Weather labels
        gbc.gridheight = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Temperature:"), gbc);
        gbc.gridx = 1;
        tempLabel = new JLabel("--");
        add(tempLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Humidity:"), gbc);
        gbc.gridx = 1;
        humidityLabel = new JLabel("--");
        add(humidityLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Wind Speed:"), gbc);
        gbc.gridx = 1;
        windLabel = new JLabel("--");
        add(windLabel, gbc);

        // Button action
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = (Date) dateSpinner.getValue();
                LocalDate ld = LocalDate.parse(dateFormat.format(d));
                String city = (String) cityCombo.getSelectedItem();
                WeatherRecord rec = controller.onCitySelected(city, ld);
                if (rec != null) {
                    updateDisplay(rec);
                } else {
                    JOptionPane.showMessageDialog(
                        CitySelectionView.this,
                        "No data for " + city + " on " + ld,
                        "Data Missing",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /** Populate the labels from a WeatherRecord. */
    private void updateDisplay(WeatherRecord rec) {
        double t = rec.getTemperature(controller.getModel().getUnit());
        tempLabel.setText(String.format("%.1f °%s",
            t,
            controller.getModel().getUnit() == UnitType.CELSIUS ? "C" : "F"));
        humidityLabel.setText(String.format("%.0f %%", rec.getHumidity()));
        windLabel.setText(String.format("%.1f km/h", rec.getWindSpeed()));
    }

    /** Called by model when unit or data changes. Refreshes the current view. */
    @Override
    public void update() {
        // Simply re-simulate the current selection to refresh units
        showButton.doClick();
    }
}
