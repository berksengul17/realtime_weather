import exception.WeatherStatsNotValidException;
import java.util.EnumMap;
import java.util.Map;
import enums.WeatherStatKey;

/**
 * Represents a collection of weather-related statistics for various cities,
 * indexed by {@link WeatherStatKey}. Statistics are stored as string values,
 * typically representing city names corresponding to each metric.
 */
public class WeatherStats {
    private final Map<WeatherStatKey, String> stats;
    private boolean isValid;

    /**
     * Constructs an empty and invalid {@code WeatherStats} object.
     * The stats map is initialized but must be filled and marked valid before use.
     */
    public WeatherStats() {
        this.stats = new EnumMap<>(WeatherStatKey.class);
        this.isValid = false;
    }

    /**
     * Constructs a valid {@code WeatherStats} object with the provided statistics map.
     *
     * @param stats a map from {@link WeatherStatKey} to their corresponding statistic values (usually city names)
     */
    public WeatherStats(Map<WeatherStatKey, String> stats) {
        this.stats = new EnumMap<>(stats);
        this.isValid = true;
    }

    /**
     * Returns the value of a specific weather statistic.
     *
     * @param key the statistic key to retrieve
     * @return the string value associated with the given key, or "N/A" if not present
     * @throws WeatherStatsNotValidException if the object is not marked as valid
     */
    public String getStat(WeatherStatKey key) throws WeatherStatsNotValidException {
        checkValidity();
        return stats.getOrDefault(key, "N/A");
    }

    /**
     * Sets or updates a weather statistic entry.
     *
     * @param key   the key representing the statistic type
     * @param value the corresponding value (typically a city name)
     */
    public void setStat(WeatherStatKey key, String value) {
        stats.put(key, value);
    }

    /**
     * Returns a copy of the full map of all statistics.
     *
     * @return a new {@code EnumMap} containing all current stats
     * @throws WeatherStatsNotValidException if the object is not marked as valid
     */
    public Map<WeatherStatKey, String> getAllStats() throws WeatherStatsNotValidException {
        checkValidity();
        return new EnumMap<>(stats);
    }

    /**
     * Checks whether the object is valid. Throws an exception if it is not.
     *
     * @throws WeatherStatsNotValidException if {@code isValid} is false
     */
    private void checkValidity() throws WeatherStatsNotValidException {
        if (!isValid) {
            throw new WeatherStatsNotValidException();
        }
    }
}
