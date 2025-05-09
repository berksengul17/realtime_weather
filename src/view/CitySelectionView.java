// File: src/view/CitySelectionView.java
package view;

import enums.TempUnit;
import listener.CitySelectionListener;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import model.WeatherRecord;
import model.WeatherSubject;
import model.WeatherDataManager;
import model.City;

/**
 * Panel for choosing City & Date and displaying that day's weather.
 * Implements WeatherObserver to refresh display when unit changes.
 */
public class CitySelectionView extends JPanel implements WeatherObserver {
    private JComboBox<String> cityCombo;
    private JSpinner dateSpinner;
    private JButton showButton;
    private JLabel tempLabel, humidityLabel, windLabel, conditionLabel;

    private CitySelectionListener listener;

    public CitySelectionView() {
        initComponents();
    }

    private void initComponents() {
        // Minimal padding for a more compact layout
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Select City & Date"),
            BorderFactory.createEmptyBorder(3, 5, 5, 5) // Reduced padding
        ));
        
        // Use a BorderLayout with controls on the left and weather info on the right
        setLayout(new BorderLayout(15, 5)); // Reduced spacing between components
        
        // Left panel - Selection controls
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3); // Reduced insets for more compact layout
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // City dropdown - with smaller font
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font(cityLabel.getFont().getName(), Font.BOLD, 12));
        leftPanel.add(cityLabel, gbc);
        
        gbc.gridx = 1;
        cityCombo = new JComboBox<>();
        cityCombo.setFont(new Font(cityCombo.getFont().getName(), Font.PLAIN, 12));
        cityCombo.setPreferredSize(new Dimension(180, 25)); // Smaller size
        gbc.weightx = 1.0;
        leftPanel.add(cityCombo, gbc);
        gbc.weightx = 0.0;

        // Date spinner with restricted range: 2025-01-01 to 2025-05-31
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font(dateLabel.getFont().getName(), Font.BOLD, 12));
        leftPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        
        // Create date range: 2025-01-01 to 2025-05-31
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        Date startDate = startCalendar.getTime();
        
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(2025, Calendar.MAY, 31, 23, 59, 59);
        Date endDate = endCalendar.getTime();
        
        // Create a date model with the restricted range
        SpinnerDateModel dateModel = new SpinnerDateModel(startDate, startDate, endDate, Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        editor.getTextField().setFont(new Font(editor.getTextField().getFont().getName(), Font.PLAIN, 12));
        dateSpinner.setEditor(editor);
        dateSpinner.setPreferredSize(new Dimension(180, 25)); // Smaller size
        gbc.weightx = 1.0;
        leftPanel.add(dateSpinner, gbc);
        gbc.weightx = 0.0;

        // Show button - under the date field
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1; // Changed to span a single column
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 3, 3, 3); // Less space above the button
        showButton = new JButton("Show");
        showButton.setFont(new Font(showButton.getFont().getName(), Font.BOLD, 12));
        showButton.setPreferredSize(new Dimension(90, 25)); // Smaller button
        leftPanel.add(showButton, gbc);
        
        gbc.gridx = 1; 
        
        // Reset constraints
        gbc.gridwidth = 1;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Right panel - Weather information
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Weather Information"),
            BorderFactory.createEmptyBorder(3, 5, 3, 5) // Reduced padding
        ));
        
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(3, 3, 3, 3); // Reduced spacing between items
        gbcRight.anchor = GridBagConstraints.WEST;
        gbcRight.fill = GridBagConstraints.HORIZONTAL;

        // Weather labels with smaller font
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 12);
        Font valueFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        
        // Weather Condition
        gbcRight.gridx = 0; gbcRight.gridy = 0;
        JLabel conditionTitleLabel = new JLabel("Condition:");
        conditionTitleLabel.setFont(labelFont);
        rightPanel.add(conditionTitleLabel, gbcRight);
        
        gbcRight.gridx = 1;
        conditionLabel = new JLabel("--");
        conditionLabel.setFont(valueFont);
        gbcRight.weightx = 1.0;
        rightPanel.add(conditionLabel, gbcRight);
        gbcRight.weightx = 0.0;
        
        // Temperature label
        gbcRight.gridx = 0; gbcRight.gridy = 1;
        JLabel tempTitleLabel = new JLabel("Temperature:");
        tempTitleLabel.setFont(labelFont);
        rightPanel.add(tempTitleLabel, gbcRight);
        
        gbcRight.gridx = 1;
        tempLabel = new JLabel("--");
        tempLabel.setFont(valueFont);
        gbcRight.weightx = 1.0;
        rightPanel.add(tempLabel, gbcRight);
        gbcRight.weightx = 0.0;

        // Humidity label
        gbcRight.gridx = 0; gbcRight.gridy = 2;
        JLabel humidityTitleLabel = new JLabel("Humidity:");
        humidityTitleLabel.setFont(labelFont);
        rightPanel.add(humidityTitleLabel, gbcRight);
        
        gbcRight.gridx = 1;
        humidityLabel = new JLabel("--");
        humidityLabel.setFont(valueFont);
        gbcRight.weightx = 1.0;
        rightPanel.add(humidityLabel, gbcRight);
        gbcRight.weightx = 0.0;

        // Wind Speed label
        gbcRight.gridx = 0; gbcRight.gridy = 3;
        JLabel windTitleLabel = new JLabel("Wind Speed:");
        windTitleLabel.setFont(labelFont);
        rightPanel.add(windTitleLabel, gbcRight);
        
        gbcRight.gridx = 1;
        windLabel = new JLabel("--");
        windLabel.setFont(valueFont);
        gbcRight.weightx = 1.0;
        rightPanel.add(windLabel, gbcRight);

        // Add the panels to the main layout
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        // Show button action → listener
        showButton.addActionListener(e -> {
            Date d = (Date) dateSpinner.getValue();
            LocalDate ld = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        // Get temperature in Celsius from the record
        double celsiusTemp = rec.getTemperature();
        
        // Convert to the correct unit
        double displayTemp = unit.convertFromCelsius(celsiusTemp);
        
        tempLabel.setText(String.format("%.1f °%s", displayTemp, unit.getSymbol()));
        humidityLabel.setText(String.format("%.0f %%", rec.getHumidity()));
        windLabel.setText(String.format("%.1f km/h", rec.getWindSpeed()));
        conditionLabel.setText(rec.getWeatherCondition().toString());
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void update(WeatherSubject subject) {
        try {
            WeatherDataManager dataManager = (WeatherDataManager) subject;
            
            // Populate cities dropdown if it's empty
            if (cityCombo.getItemCount() == 0) {
                // Extract city names from the model
                String[] cityNames = dataManager.getCities().stream()
                    .map(City::getName)
                    .distinct()
                    .toArray(String[]::new);
                
                populateCities(cityNames);
                
                // Select first city if available
                if (cityNames.length > 0) {
                    cityCombo.setSelectedIndex(0);
                }
            }
            
            // Refresh current selection if there's a city selected
            if (cityCombo.getSelectedItem() != null) {
                showButton.doClick();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}