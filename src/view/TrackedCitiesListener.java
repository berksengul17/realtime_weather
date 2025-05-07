package view;

@FunctionalInterface
public interface TrackedCitiesListener {
    /**
     * Called when the TrackedCitiesView requests a refresh of tracked cities' data.
     */
    void onRefreshRequested();
}