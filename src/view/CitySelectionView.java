// File: src/view/CitySelectionView.java
package view;

import enums.TempUnit;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.*;
import model.WeatherRecord;

/**
 * Panel for choosing City & Date and displaying that day's weather.
 * Implements WeatherObserver to refresh display when unit changes.
 */
public class CitySelectionView extends JPanel implements WeatherObserver {
    private JComboBox<String> cityCombo;
    private JSpinner dateSpinner;
    private JButton showButton;
    private JLabel tempLabel, humidityLabel, windLabel;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private CitySelectionListener listener;

    public CitySelectionView() {
        initComponents();
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
        cityCombo = new JComboBox<>();
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
        gbc.gridheight = 1;

        // Weather labels
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

        // Show button action → listener
        showButton.addActionListener(e -> {
            Date d = (Date) dateSpinner.getValue();
            LocalDate ld = LocalDate.parse(dateFormat.format(d));
            String city = (String) cityCombo.getSelectedItem();
            if (listener != null) {
                listener.onCitySelected(city, ld);
            }
        });
    }

    public void addCitySelectionListener(CitySelectionListener l) {
        this.listener = l;
    }

    public void populateCities(String[] cities) {
        cityCombo.setModel(new DefaultComboBoxModel<>(cities));
    }

    public void showWeather(WeatherRecord rec, TempUnit unit) {
        double t = rec.getTemperature();
        tempLabel.setText(String.format("%.1f °%s", t, unit == TempUnit.CELSIUS ? "C" : "F"));
        humidityLabel.setText(String.format("%.0f %%", rec.getHumidity()));
        windLabel.setText(String.format("%.1f km/h", rec.getWindSpeed()));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void update() {
        // Refresh current selection
        showButton.doClick();
    }
}