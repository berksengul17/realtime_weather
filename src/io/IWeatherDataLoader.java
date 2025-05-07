package io;
import java.util.List;

import model.City;

public interface IWeatherDataLoader {
    List<City> load(String filePath) throws Exception;
} 