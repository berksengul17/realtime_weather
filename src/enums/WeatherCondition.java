package enums;

public enum WeatherCondition {
    SUNNY("Sunny"),
    CLOUDY("Cloudy"),
    RAINY("Rainy"),
    SNOWY("Snowy"),
    PARTLY_CLOUDY("Partly Cloudy"),
    HEAVY_SNOW("Heavy Snow"),
    NONE("");

    private final String displayName;

    WeatherCondition(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static WeatherCondition fromString(String condition) {
        return switch (condition.toUpperCase().replace(" ", "_")) {
            case "SUNNY" -> SUNNY;
            case "CLOUDY" -> CLOUDY;
            case "RAINY" -> RAINY;
            case "SNOWY" -> SNOWY;
            case "PARTLY_CLOUDY" -> PARTLY_CLOUDY;
            case "HEAVY_SNOW" -> HEAVY_SNOW;
            default -> throw new IllegalArgumentException("Unknown weather condition: " + condition);
        };
    }
} 