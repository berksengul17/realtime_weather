package model;

import java.util.List;

import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;
import exception.WeatherStatsNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeatherDataManager implements WeatherSubject {

    private List<City> cities;
    private List<WeatherObserver> observers;
    private TempUnit tempUnit;
    private boolean isValid;

    public WeatherDataManager() {
        this.cities = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.tempUnit = TempUnit.CELSIUS;
        this.isValid = false;
    }

    public WeatherDataManager(List<City> cities, TempUnit tempUnit) {
        this.cities = cities;
        this.tempUnit = tempUnit;
        this.observers = new ArrayList<>();
        this.isValid = true;
    }

    public WeatherRecord getWeather(String cityName, LocalDate date) throws WeatherDataManagerNotValidException {
        checkValidity();

        if (cityName == null || date == null) {
            throw new IllegalArgumentException("City name and date must not be null.");
        }
    
        return cities.stream()
            .filter(city -> city.getName().equals(cityName))
            .findFirst()
            .map(city -> city.getRecordByDate(date))
            .orElseThrow(() -> new IllegalArgumentException("City not found: " + cityName));
    }

    public TempUnit getTempUnit() throws WeatherDataManagerNotValidException {
        checkValidity();
        return tempUnit;
    }

    public void setTempUnit(TempUnit tempUnit) {
        this.tempUnit = tempUnit;
    }

    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        
    }

    private void checkValidity() throws WeatherDataManagerNotValidException {
        if (!isValid) {
            throw new WeatherDataManagerNotValidException();
        }
    }
}
