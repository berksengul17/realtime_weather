package listener;

import java.time.LocalDate;

@FunctionalInterface
public interface CitySelectionListener {
    /**
     * Called when the user selects a city and date in the CitySelectionView.
     *
     * @param city the name of the selected city
     * @param date the chosen LocalDate
     */
    void onCitySelected(String city, LocalDate date);
}