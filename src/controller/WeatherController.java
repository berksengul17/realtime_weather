package controller;

import model.WeatherDataManager;
import model.WeatherStats;
import model.WeatherRecord;
import enums.TempUnit;
import exception.WeatherDataManagerNotValidException;
import view.CitySelectionListener;
import view.CitySelectionView;
import view.MainWindow;
import view.StatsListener;
import view.StatsView;
import view.TrackedCitiesListener;
import view.TrackedCitiesView;
import view.UnitSelectionListener;
import view.UnitSelectionView;

import javax.swing.SwingUtilities;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;
import view.WeatherObserver;

public class WeatherController {
    private final WeatherDataManager model;
    private final MainWindow mainWindow;
    private final CitySelectionView citySelectionView;
    private final TrackedCitiesView trackedCitiesView;
    private final StatsView statsView;
    private final UnitSelectionView unitSelectionView;

    public WeatherController(WeatherDataManager model) {
        this.model = model;

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

        // Tracked-cities refresh (e.g. on startup or manual “refresh” button)
        trackedCitiesView.addRefreshListener((LocalDate date) -> {
            Map<String, WeatherRecord> current;
            try {
                current = model.getTrackedCitiesWeather(date);
            } catch (WeatherDataManagerNotValidException ex) {
            }
            trackedCitiesView.showTrackedCities(current, model.getTempUnit());
        });

        // Stats panel request
        statsView.addStatsListener(() -> {
            WeatherStats stats = model.calculateStats();
            statsView.displayStats(stats, model.getTempUnit());
        });
    }

    /** 
     * Kick off the app: load data & prefs, show UI, and trigger initial update.
     */
    public void initApp() {
        model.loadData();
        model.loadPreferences();
        SwingUtilities.invokeLater(() -> mainWindow.setVisible(true));
        model.notifyObservers();  // update all observer-views
    }
}
