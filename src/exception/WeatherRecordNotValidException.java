package exception;

/**
 * Thrown to indicate that an operation was attempted on an invalid
 * {@link WeatherRecord} object.
 *
 * A {@code WeatherRecord} is considered invalid when its internal validity
 * flag is {@code false}, meaning its data should not be used for analysis
 * or display.
 */
public class WeatherRecordNotValidException extends Exception {

    /**
     * Constructs a new exception with a default error message.
     * The message indicates that the {@code WeatherRecord} object is not valid.
     */
    public WeatherRecordNotValidException() {
        super("WeatherRecord object is not valid.");
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param msg the detail message providing context about the invalid record
     */
    public WeatherRecordNotValidException(String msg) {
        super(msg);
    }
}
