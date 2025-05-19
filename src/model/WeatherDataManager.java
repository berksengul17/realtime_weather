package model;

import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;
import view.WeatherObserver;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherDataManager implements WeatherSubject {

    private List<City> cities;
    private List<City> trackedCities;
    private TempUnit tempUnit;
    private LocalDate date;
    private List<WeatherObserver> observers;
    private boolean isValid;

    public WeatherDataManager() {
        this.cities = new ArrayList<>();
        this.trackedCities = new ArrayList<>();
        this.tempUnit = TempUnit.CELSIUS;
        this.date = LocalDate.now();
        this.observers = new ArrayList<>();
        this.isValid = false;
    }

    public WeatherDataManager(List<City> cities, List<City> trackedCities, TempUnit tempUnit, LocalDate date) {
        this.cities = cities;
        this.trackedCities = trackedCities;
        this.tempUnit = tempUnit;
        this.date = date;
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
        notifyObservers();
    }

    public List<City> getTrackedCities() throws WeatherDataManagerNotValidException {
        checkValidity();
        return trackedCities;
    }

    public List<City> getCities() throws WeatherDataManagerNotValidException {
        checkValidity();
        return cities;
    }

    public Map<String, WeatherRecord> getTrackedCitiesWeather(LocalDate date) throws WeatherDataManagerNotValidException {
        checkValidity();
        Map<String, WeatherRecord> records = new HashMap<>();
    
        for (City city : trackedCities) {
            WeatherRecord record = city.getRecordByDate(date);
            records.put(city.getName(), record);
        }
    
        return records;
    }
    
    public void setTrackedCities(List<City> trackedCities) {
        this.trackedCities = trackedCities;
    }

    public LocalDate getDate() throws WeatherDataManagerNotValidException {
        checkValidity();
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(this);
        }
    }

    private void checkValidity() throws WeatherDataManagerNotValidException {
        if (!isValid) {
            throw new WeatherDataManagerNotValidException();
        }
    }
}
