package exception;

public class WeatherDataManagerNotValidException extends Exception {
    public WeatherDataManagerNotValidException() {
        super("WeatherDataManager object is not valid.");
    }

    public WeatherDataManagerNotValidException(String msg) {
        super(msg);
    }
}
