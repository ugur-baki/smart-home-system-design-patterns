package Simulation.Weather;

public interface WeatherSource {
    // Saat ve dakikayı ver, bana o anki hava durumunu söyle
    WeatherData getWeather(int hour, int minute);
}