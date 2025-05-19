package view;

import listener.MultipleCitySelectionListener;
import model.WeatherSubject;
import util.CheckListItem;
import model.City;
import model.WeatherDataManager;
import model.WeatherRecord;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultipleCitySelectionView extends JPanel implements WeatherObserver {
    private DefaultListModel<CheckListItem> cityListModel;
    private JList<CheckListItem> cityList;
    private JButton showButton;
    private JTextArea outputArea;
    private MultipleCitySelectionListener listener;

    public MultipleCitySelectionView() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8, 8));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Today's Weather for Multiple Cities");
        titledBorder.setTitleFont(new Font("SansSerif", Font.BOLD, 12));
        setBorder(BorderFactory.createCompoundBorder(
                titledBorder,
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        cityListModel = new DefaultListModel<>();
        cityList = new JList<>(cityListModel);
        cityList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
          JCheckBox checkBox = new JCheckBox(value.getLabel(), value.isSelected());
          checkBox.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
          checkBox.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
          return checkBox;
        });
      
        cityList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(cityList);

        showButton = new JButton("Show Selected Cities");

        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        JPanel listAndButton = new JPanel(new BorderLayout(5, 5));
        listAndButton.add(listScrollPane, BorderLayout.CENTER);
        listAndButton.add(showButton, BorderLayout.SOUTH);

        add(listAndButton, BorderLayout.WEST);
        add(outputScrollPane, BorderLayout.CENTER);

        cityList.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
              int index = cityList.locationToIndex(e.getPoint());
              if (index >= 0) {
                  CheckListItem item = cityListModel.getElementAt(index);
                  item.setSelected(!item.isSelected());
                  cityList.repaint();
              }
          }
      });      

        showButton.addActionListener(e -> {
          List<String> selectedCities = new ArrayList<>();
          for (int i = 0; i < cityListModel.size(); i++) {
              CheckListItem item = cityListModel.get(i);
              if (item.isSelected()) {
                  selectedCities.add(item.getLabel());
              }
          }

          if (listener != null) {
              listener.onSelectedCitiesChange(selectedCities);
          }
      });
    }

    public void addMultipleCitySelectionListener(MultipleCitySelectionListener listener) {
        this.listener = listener;
    }

    public void updateCityList(String[] cities) {
        cityListModel.clear();
        for (String city : cities) {
            cityListModel.addElement(new CheckListItem(city));
        }
    }

    public void showComparedCities(Map<String, WeatherRecord> records) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, WeatherRecord> entry : records.entrySet()) {
            String city = entry.getKey();
            WeatherRecord record = entry.getValue();
            sb.append(String.format("%-12s Temp: %.1f°C, Humidity: %.1f%%, Wind: %.1f km/h%n",
                    city,
                    record.getTemperature(),
                    record.getHumidity(),
                    record.getWindSpeed()));
        }

        if (records.isEmpty()) {
            sb.append("No data available for selected cities.\n");
        }

        outputArea.setText(sb.toString());
    }

    @Override
    public void update(WeatherSubject subject) {
      try {
        WeatherDataManager dataManager = (WeatherDataManager) subject;
        String[] cities = dataManager.getCities().stream()
        .map(City::getName)
        .distinct()
        .toArray(String[]::new);

        updateCityList(cities);

      } catch (Exception e) {

      }
    }
}
