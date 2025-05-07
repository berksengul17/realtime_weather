package main.java.model;

import java.util.List;

public interface IWeatherDataLoader {
    List<City> load(String filePath) throws Exception;
} 