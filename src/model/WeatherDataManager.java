package model;

import java.util.List;
import java.util.ArrayList;

public class WeatherDataManager implements WeatherSubject {

    private List<WeatherObserver> observers;
    private boolean isValid;

    public WeatherDataManager() {
        this.observers = new ArrayList<>();
        this.isValid = false;
    }

    public WeatherRecord getWeather(City city, LocalDate date) {
        
    }

    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }
}
