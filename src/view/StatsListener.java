package view;

@FunctionalInterface
public interface StatsListener {
    /**
     * Called when the StatsView requests updated statistics.
     */
    void onStatsRequested();
}