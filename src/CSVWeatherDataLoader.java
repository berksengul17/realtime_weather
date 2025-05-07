package main.java.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVWeatherDataLoader implements IWeatherDataLoader {
    @Override
    public List<City> load(String filePath) throws Exception {
        Map<String, City> cityMap = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip header line
            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new Exception("CSV file is empty");
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length != 6) {
                    throw new Exception("Invalid CSV format: each line should have 6 fields");
                }
                
                try {
                    // Parse data
                    String cityName = data[0];
                    LocalDate date = LocalDate.parse(data[1]);
                    double temperature = Double.parseDouble(data[2]);
                    int humidity = Integer.parseInt(data[3]);
                    double windSpeed = Double.parseDouble(data[4]);
                    WeatherCondition condition = WeatherCondition.fromString(data[5]);
                    
                    // Create WeatherRecord
                    WeatherRecord record = new WeatherRecord(date, temperature, humidity, windSpeed, condition);
                    
                    // Get or create City
                    City city = cityMap.computeIfAbsent(cityName, City::new);
                    city.addWeatherRecord(record);
                } catch (IllegalArgumentException e) {
                    throw new Exception("Error parsing line: " + line + ". " + e.getMessage());
                }
            }
        }
        
        return new ArrayList<>(cityMap.values());
    }
} 