// File: src/view/TrackedCitiesView.java
package view;

import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;
import listener.TrackedCitiesListener;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;

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
    private final int ROW_HEIGHT = 25; // Reverted to original row height
    
    public TrackedCitiesView() {
        initComponents();
    }

    private void initComponents() {
        // Create a titled border with more compact padding
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Tracked Cities");
        titledBorder.setTitleFont(new Font(titledBorder.getTitleFont().getName(), Font.BOLD, 12)); // Smaller font
        
        // Add minimal padding around the content
        setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            BorderFactory.createEmptyBorder(5, 5, 5, 5) // Reduced padding
        ));
        
        setLayout(new BorderLayout());
        
        // Initialize list model and list
        listModel = new DefaultListModel<>();
        cityList = new JList<>(listModel);
        cityList.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Smaller font
        
        // Fix the row height to ensure accurate size calculations
        cityList.setFixedCellHeight(ROW_HEIGHT);
        
        // Set cell renderer for better spacing
        cityList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                // Add minimal padding around list items
                label.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
                return label;
            }
        });
        
        // We don't want scrollbars - panel should exactly match content size
        add(cityList, BorderLayout.CENTER);
    }

    public void addRefreshListener(TrackedCitiesListener l) {
        this.listener = l;
    }

    public void showTrackedCities(Map<String, WeatherRecord> data, TempUnit unit) {
        listModel.clear();
        data.forEach((city, rec) -> {
            // Get temperature in Celsius from the record
            double celsiusTemp = rec.getTemperature();
            
            // Convert to the correct unit
            double displayTemp = unit.convertFromCelsius(celsiusTemp);
            
            String line = String.format("%-12s %6.1f°%s",
                city, displayTemp, unit.getSymbol());
            listModel.addElement(line);
        });
        
        // Set the exact preferred size based on content
        int rowCount = Math.max(1, listModel.getSize());
        int titleHeight = 25; // Height for the title border
        int paddingHeight = 10; // Reduced padding height
        int listHeight = rowCount * ROW_HEIGHT;
        int totalHeight = titleHeight + listHeight + paddingHeight;
        
        setPreferredSize(new Dimension(getWidth(), totalHeight));
        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        
        // Calculate height based on the number of items, with reduced padding
        int rowCount = Math.max(1, listModel.getSize());
        int titleHeight = 25; // Height for the title border
        int paddingHeight = 10; // Reduced padding height
        int listHeight = rowCount * ROW_HEIGHT;
        int totalHeight = titleHeight + listHeight + paddingHeight;
        
        return new Dimension(super.getPreferredSize().width, totalHeight);
    }

    @Override
    public Dimension getMaximumSize() {
        // Ensure maximum height matches preferred height
        Dimension pref = getPreferredSize();
        return new Dimension(super.getMaximumSize().width, pref.height);
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