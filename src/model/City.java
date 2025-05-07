package model;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class City {
    private String name;
    private ArrayList<WeatherRecord> weatherRecords;
    private boolean isValidCity = false;

    // Default Constructor
    public City() 
    {
        this.name = "";
        this.weatherRecords = new ArrayList<>();
    }
    // Full Constructor
    public City(String name) 
    {
        this.name = name;
        this.weatherRecords = new ArrayList<>();
        this.isValidCity = true;
    }
    // Copy Constructor
    public City(City city) 
    {
        this.name = city.getName();
        this.weatherRecords = new ArrayList<>(city.getWeatherRecords());
        this.isValidCity = city.isValidCity;
    }

    public String getName() 
    {
        return name;
    }

    public ArrayList<WeatherRecord> getWeatherRecords() 
    {
        return weatherRecords;
    }

    public boolean isValidCity() 
    {
        return isValidCity;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setWeatherRecords(ArrayList<WeatherRecord> weatherRecords) 
    {
        this.weatherRecords = weatherRecords;
    }

    public void addWeatherRecord(WeatherRecord record) 
    {
        if (!record.isValidWeatherRecord()) {
            throw new IllegalArgumentException(
                "WeatherRecord must not be null when adding to city: " + name);
        }
        weatherRecords.add(record);

    }

    public WeatherRecord getRecordByDate(LocalDate date) {
        return validRecords()
                .filter(r -> r.getDate() != null && r.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "No valid weather record found for " + date + " in city: " + name));
    }    

    /**
     * Helper: all valid records as a Stream.
     */
    private Stream<WeatherRecord> validRecords() {
        return weatherRecords.stream()
                             .filter(WeatherRecord::isValidWeatherRecord);
    }

    /**
     * Helper: all valid records in a given month.
     */
    private Stream<WeatherRecord> validRecordsInMonth(Month month) {
        Objects.requireNonNull(month, "Month must not be null");
        return validRecords()
               .filter(r -> {
                   LocalDate d = r.getDate();
                   return d != null && d.getMonth() == month;
               });
    }

    /**
     * Calculate the average temperature across ALL valid records.
     *
     * @return the average temperature
     * @throws IllegalStateException if there are no valid records
     */
    public double calculateAverageTemperature() {
        return validRecords()
            .mapToDouble(WeatherRecord::getTemperature)
            .average()
            .orElseThrow(() ->
                new IllegalStateException("No valid weather records to calculate average temperature for city: " + name));
    }

    /**
     * Find the lowest temperature in a specific month across valid records.
     *
     * @param month the Month to search (JANUARY … DECEMBER)
     * @return the lowest temperature observed in that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double getLowestTemperatureInMonth(Month month) {
        return validRecordsInMonth(month)
            .mapToDouble(WeatherRecord::getTemperature)
            .min()
            .orElseThrow(() ->
                new IllegalStateException("No valid weather records found for " + month + " in city: " + name));
    }

    /**
     * Calculate the average humidity in a given month across valid records.
     *
     * @param month the Month to search
     * @return the average humidity for that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double calculateAverageHumidityInMonth(Month month) {
        return validRecordsInMonth(month)
            .mapToDouble(WeatherRecord::getHumidity)
            .average()
            .orElseThrow(() ->
                new IllegalStateException("No valid weather records found for humidity in " + month + " for city: " + name));
    }

    /**
     * Calculate the average wind speed in a given month across valid records.
     *
     * @param month the Month to search
     * @return the average wind speed for that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double calculateAverageWindSpeedInMonth(Month month) {
        return validRecordsInMonth(month)
            .mapToDouble(WeatherRecord::getWindSpeed)
            .average()
            .orElseThrow(() ->
                new IllegalStateException("No valid weather records found for wind speed in " + month + " for city: " + name));
    }
}