package enums;


/**
 * Enumeration of keys used to identify specific weather statistics.
 * Each enum constant is associated with a human-readable description of the metric.
*/
public enum WeatherStatKey {
    HIGHEST_AVG_TEMP("The city with the highest average temperature over the 5 months"),
    LOWEST_AVG_TEMP("The city with the lowest average temperature over the 5 months"),
    LOWEST_TEMP_IN_JANUARY("The city with the lowest temperature in January 2025"),
    HIGHEST_AVG_HUMIDITY_IN_MAY("The city with the highest average humidity in May 2025"),
    HIGHEST_AVG_WIND_SPEED_IN_APRIL("The city with the highest average wind speed in April 2025");

    private final String description;

    /**
     * Constructs a WeatherStatKey with a human-readable description.
     *
     * @param description the description of the weather statistic
     */
    WeatherStatKey(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the statistic key.
     *
     * @return the human-readable description
     */
    @Override
    public String toString() {
        return description;
    }
}
