import java.time.LocalDate;

public class WeatherRecord {
    private LocalDate date;
    private Double temperature;
    private Double humidity;
    private Double windSpeed;
    private WeatherCondition weatherCondition;
    private Boolean isValidWeatherRecord = false;

    // Default Constructor
    public WeatherRecord() 
    {
        this.date = null;
        this.temperature = 0.0;
        this.humidity = 0.0;
        this.windSpeed = 0.0;
        this.weatherCondition = WeatherCondition.NONE;
    }

    // Full Constructor
    public WeatherRecord(LocalDate date, double temperature, double humidity, double windSpeed, WeatherCondition weatherCondition) 
    {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.isValidWeatherRecord = true;
    }

    // Copy Constructor
    public WeatherRecord(WeatherRecord weatherRecord) 
    {
        this.date = weatherRecord.getDate();
        this.temperature = weatherRecord.getTemperature();
        this.humidity = weatherRecord.getHumidity();
        this.windSpeed = weatherRecord.getWindSpeed();
        this.weatherCondition = weatherRecord.getWeatherCondition();
        this.isValidWeatherRecord = weatherRecord.isValidWeatherRecord();
    }

    public LocalDate getDate() 
    {
        return date;
    }

    public double getTemperature() 
    {
        return temperature;
    }

    public double getHumidity() 
    {
        return humidity;
    }

    public double getWindSpeed() 
    {
        return windSpeed;
    }

    public WeatherCondition getWeatherCondition() 
    {
        return weatherCondition;
    }

    public Boolean isValidWeatherRecord() 
    {
        return isValidWeatherRecord;
    }

    public void setDate(LocalDate date) 
    {
        this.date = date;
    }
    
    public void setTemperature(double temperature) 
    {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) 
    {
        this.humidity = humidity;
    }

    public void setWindSpeed(double windSpeed) 
    {
        this.windSpeed = windSpeed;
    }

    public void setWeatherCondition(WeatherCondition weatherCondition) 
    {
        this.weatherCondition = weatherCondition;
    }
}
