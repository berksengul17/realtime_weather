package view;

import model.WeatherSubject;

/**
 * Listener interface for views or other components that need to be notified
 * when the weather model's data or state changes (e.g., unit switch,
 * new data load, tracked-city updates).
 */

public interface WeatherObserver {
    /**
     * Called by the WeatherSubject when its state has changed and observers
     * should update their displays or data accordingly.
     */
    void update(WeatherSubject subject);

}