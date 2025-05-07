package exception;

/**
 * Thrown to indicate that an operation was attempted on an invalid
 * {@link WeatherStats} object.
 *
 * A {@code WeatherStats} object is considered invalid when its internal validity
 * flag is {@code false}, meaning its statistics are incomplete or unreliable.
 */
public class WeatherStatsNotValidException extends Exception {

    /**
     * Constructs a new exception with a default error message.
     * The message indicates that the {@code WeatherStats} object is not valid.
     */
    public WeatherStatsNotValidException() {
        super("WeatherStats object is not valid.");
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param msg the detail message providing context about the invalid stats
     */
    public WeatherStatsNotValidException(String msg) {
        super(msg);
    }
}
