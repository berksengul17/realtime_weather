import java.time.Month;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

import enums.WeatherStatKey;
import model.City;

public class StatCalculator {

    /**
     * Computes weather statistics for a list of valid cities.
     *
     * @param cities the list of City objects
     * @return a populated and valid WeatherStats object
     */
    public static WeatherStats calculate(List<City> cities) {
        Map<WeatherStatKey, String> statMap = new EnumMap<>(WeatherStatKey.class);

        // Filter only valid cities
        List<City> validCities = cities.stream()
            .filter(City::isValidCity)
            .toList();

        // Highest average temperature
        statMap.put(WeatherStatKey.HIGHEST_AVG_TEMP,
            cityWithExtreme(validCities, Comparator.comparingDouble(City::calculateAverageTemperature)));

        // Lowest average temperature
        statMap.put(WeatherStatKey.LOWEST_AVG_TEMP,
            cityWithExtreme(validCities, Comparator.comparingDouble(City::calculateAverageTemperature).reversed()));

        // Lowest temperature in January
        statMap.put(WeatherStatKey.LOWEST_TEMP_IN_JANUARY,
            cityWithExtreme(validCities, c -> c.getLowestTemperatureInMonth(Month.JANUARY), Comparator.naturalOrder()));

        // Highest average humidity in May
        statMap.put(WeatherStatKey.HIGHEST_AVG_HUMIDITY_IN_MAY,
            cityWithExtreme(validCities, c -> c.calculateAverageHumidityInMonth(Month.MAY), Comparator.reverseOrder()));

        // Highest average wind speed in April
        statMap.put(WeatherStatKey.HIGHEST_AVG_WIND_SPEED_IN_APRIL,
            cityWithExtreme(validCities, c -> c.calculateAverageWindSpeedInMonth(Month.APRIL), Comparator.reverseOrder()));

        WeatherStats stats = new WeatherStats(statMap);
        return stats;
    }

    /**
     * Returns the name of the city with the extreme (max/min) value based on the provided comparator.
     * This overload uses a method reference (like average temperature).
     */
    private static String cityWithExtreme(List<City> cities, Comparator<City> comparator) {
        return cities.stream()
            .min(comparator)
            .map(City::getName)
            .orElse("N/A");
    }

    /**
     * Returns the name of the city with the extreme (max/min) value based on a double-producing function.
     * This overload allows comparison by extracted values (e.g., temperature in a specific month).
     */
    private static String cityWithExtreme(List<City> cities,
                                          java.util.function.Function<City, Double> valueExtractor,
                                          Comparator<Double> comparator) {
        return cities.stream()
            .map(city -> Map.entry(city.getName(), valueExtractor.apply(city)))
            .filter(entry -> !entry.getValue().isNaN())
            .min(Map.Entry.comparingByValue(comparator))
            .map(Map.Entry::getKey)
            .orElse("N/A");
    }
}
