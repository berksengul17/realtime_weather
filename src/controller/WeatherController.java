package controller;

import model.City;
import model.StatCalculator;
import model.WeatherDataManager;
import model.WeatherStats;
import model.WeatherRecord;
import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;
import io.CSVWeatherDataLoader;
import io.IWeatherDataLoader;
import view.CitySelectionView;
import view.MainWindow;
import view.StatsView;
import view.TrackedCitiesView;
import view.UnitSelectionView;

import javax.swing.SwingUtilities;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class WeatherController {
    private final WeatherDataManager model;
    private final List<City> cities;
    private final MainWindow mainWindow;
    private final CitySelectionView citySelectionView;
    private final TrackedCitiesView trackedCitiesView;
    private final StatsView statsView;
    private final UnitSelectionView unitSelectionView;

    public WeatherController() {
        this.cities = loadCities();
        List<City> trackedCities = Arrays.asList(this.cities.get(0), this.cities.get(1), this.cities.get(2), this.cities.get(3), this.cities.get(4));
        this.model = new WeatherDataManager(cities, trackedCities, TempUnit.CELSIUS, LocalDate.now());

        // 1) Instantiate views
        this.citySelectionView    = new CitySelectionView();
        this.trackedCitiesView    = new TrackedCitiesView();
        this.statsView            = new StatsView();
        this.unitSelectionView    = new UnitSelectionView();

        // 2) Create and wire up the main window
        this.mainWindow = new MainWindow(
            citySelectionView,
            unitSelectionView,
            trackedCitiesView,
            statsView
        );

        // 3) Register view-components as observers of the model
        model.addObserver(citySelectionView);
        model.addObserver(trackedCitiesView);
        model.addObserver(statsView);
        model.addObserver(unitSelectionView);

        // 4) Wire view events → controller logic

        // City / Date selection
        citySelectionView.addCitySelectionListener((String city, LocalDate date) -> {
            try {
                WeatherRecord rec = model.getWeather(city, date);
                citySelectionView.showWeather(rec, model.getTempUnit());
            } catch (NoSuchElementException ex) {
                citySelectionView.showError("No data for " + city + " on " + date);
            } catch (WeatherDataManagerNotValidException ex) {
            }
        });

        // Temperature unit toggle
        unitSelectionView.addUnitChangeListener((TempUnit unit) -> {
            model.setTempUnit(unit);
        });

        // Tracked-cities refresh (e.g. on startup or manual "refresh" button)
        trackedCitiesView.addRefreshListener((LocalDate date) -> {
            Map<String, WeatherRecord> current = null;
            try {
                current = model.getTrackedCitiesWeather(date);
            } catch (WeatherDataManagerNotValidException ex) {
            }
            try {
                trackedCitiesView.showTrackedCities(current, model.getTempUnit());
            } catch (WeatherDataManagerNotValidException e) {
                e.printStackTrace();
            }
        });

        // Stats panel request
        statsView.addStatsListener(() -> {
            WeatherStats stats = StatCalculator.calculate(cities);
            try {
                statsView.displayStats(stats, model.getTempUnit());
            } catch (WeatherDataManagerNotValidException e) {
                e.printStackTrace();
            }
        });
    }

    /** 
     * Kick off the app: load data & prefs, show UI, and trigger initial update.
     */
    public void initApp() {
        SwingUtilities.invokeLater(() -> mainWindow.setVisible(true));
        model.notifyObservers();  // update all observer-views
    }

    private List<City> loadCities() {
        IWeatherDataLoader loader = new CSVWeatherDataLoader();
        try {
            return loader.load("./weather_data.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
