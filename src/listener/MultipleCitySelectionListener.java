package listener;

import java.util.List;

public interface MultipleCitySelectionListener {
  void onSelectedCitiesChange(List<String> selectedCities);
}