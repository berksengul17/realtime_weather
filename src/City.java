import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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
    public City(String name, ArrayList<WeatherRecord> weatherRecords) 
    {
        this.name = name;
        this.weatherRecords = weatherRecords;
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

    /**
     * Retrieve a valid WeatherRecord for the given date.
     *
     * @param date the LocalDate to search
     * @return the matching WeatherRecord
     * @throws IllegalArgumentException if date is null
     * @throws NoSuchElementException if no valid record exists for that date
     */
    public WeatherRecord getRecord(LocalDate date) 
    {
        if (date == null) {
            throw new IllegalArgumentException(
                "Date must not be null when fetching a weather record for city: " + name);
        }
        for (WeatherRecord record : weatherRecords) 
        {
            if (Boolean.TRUE.equals(record.isValidWeatherRecord())
                && date.equals(record.getDate())) {
                return record;
            }
        }
        throw new NoSuchElementException(
            "No valid weather record found for date " + date + " in city: " + name);
    }

    /**
     * Calculate the average temperature across ALL valid records.
     *
     * @return the average temperature
     * @throws IllegalStateException if there are no valid records
     */
    public double calculateAverageTemperature() 
    {
        if (weatherRecords == null || weatherRecords.isEmpty()) 
        {
            throw new IllegalStateException(
                "No weather records available for city: " + name);
        }

        double sum = 0.0;
        int count = 0;
        for (WeatherRecord r : weatherRecords) {
            if (Boolean.TRUE.equals(r.isValidWeatherRecord())) 
            {
                sum += r.getTemperature();
                count++;
            }
        }
        if (count == 0) 
        {
            throw new IllegalStateException(
                "No valid weather records to calculate average temperature for city: " + name);
        }
        return sum / count;
    }

    /**
     * Find the lowest temperature in a specific month across valid records.
     *
     * @param month the Month to search (JANUARY … DECEMBER)
     * @return the lowest temperature observed in that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double getLowestTemperatureInMonth(Month month) 
    {
        if (month == null) 
        {
            throw new IllegalArgumentException("Month must not be null");
        }

        double lowest = Double.MAX_VALUE;
        boolean found = false;
        for (WeatherRecord r : weatherRecords) 
        {
            if (Boolean.TRUE.equals(r.isValidWeatherRecord())
                    && r.getDate() != null
                    && r.getDate().getMonth() == month) 
                    {

                double t = r.getTemperature();
                if (t < lowest) 
                {
                    lowest = t;
                }
                found = true;
            }
        }
        if (!found) 
        {
            throw new IllegalStateException(
                "No valid weather records found for " + month +
                " in city: " + name);
        }
        return lowest;
    }

    /**
     * Calculate the average humidity in a given month across valid records.
     *
     * @param month the Month to search
     * @return the average humidity for that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double calculateAverageHumidityInMonth(Month month) 
    {
        if (month == null) 
        {
            throw new IllegalArgumentException("Month must not be null");
        }

        double sum = 0.0;
        int count = 0;
        for (WeatherRecord r : weatherRecords) 
        {
            if (Boolean.TRUE.equals(r.isValidWeatherRecord())
                    && r.getDate() != null
                    && r.getDate().getMonth() == month) 
                    {

                sum += r.getHumidity();
                count++;
            }
        }
        if (count == 0) 
        {
            throw new IllegalStateException(
                "No valid weather records found for humidity in " +
                month + " for city: " + name);
        }
        return sum / count;
    }

    /**
     * Calculate the average wind speed in a given month across valid records.
     *
     * @param month the Month to search
     * @return the average wind speed for that month
     * @throws IllegalArgumentException if month is null
     * @throws IllegalStateException    if no valid records exist for that month
     */
    public double calculateAverageWindSpeedInMonth(Month month) 
    {
        if (month == null) 
        {
            throw new IllegalArgumentException("Month must not be null");
        }

        double sum = 0.0;
        int count = 0;
        for (WeatherRecord r : weatherRecords) 
        {
            if (Boolean.TRUE.equals(r.isValidWeatherRecord())
                    && r.getDate() != null
                    && r.getDate().getMonth() == month) {

                sum += r.getWindSpeed();
                count++;
            }
        }
        if (count == 0) 
        {
            throw new IllegalStateException(
                "No valid weather records found for wind speed in " +
                month + " for city: " + name);
        }
        return sum / count;
    }
}
