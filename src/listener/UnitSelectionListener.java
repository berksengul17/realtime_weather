package listener;

import enums.TempUnit;

@FunctionalInterface
public interface UnitSelectionListener {
    /**
     * Called when the user changes the temperature unit in UnitSelectionView.
     *
     * @param unit the newly selected TempUnit (CELSIUS or FAHRENHEIT)
     */
    void onUnitChanged(TempUnit unit);
}