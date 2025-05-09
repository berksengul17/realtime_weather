package enums;

import java.util.function.Function;

/**
 * Enumeration of temperature units supported for conversion from Celsius.
 * Each unit defines a conversion function from Celsius.
 */
public enum TempUnit {
    CELSIUS("C", temp -> temp),
    FAHRENHEIT("F", temp -> temp * 9 / 5 + 32);

    private final String symbol;
    private final Function<Double, Double> fromCelsius;
    
    /**
     * Constructs a temperature unit with a symbol and conversion function from Celsius.
     *
     * @param symbol        the unit symbol (e.g., "C" or "F")
     * @param fromCelsius   the function to convert from Celsius to this unit
     */
    TempUnit(String symbol, Function<Double, Double> fromCelsius) {
        this.symbol = symbol;
        this.fromCelsius = fromCelsius;
    }

    /**
     * Converts a temperature value from Celsius to this unit.
     *
     * @param celsiusTemp the temperature in Celsius
     * @return the converted temperature in this unit
     */
    public double convertFromCelsius(double celsiusTemp) {
        return fromCelsius.apply(celsiusTemp);
    }

    /**
     * Returns the symbol associated with this unit (e.g., "C" or "F").
     *
     * @return the unit symbol
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}
