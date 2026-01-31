package Simulation.Weather;

public class WeatherData {
    public double temperature;
    public double sunlight; // 0.0 ile 1.0 arası

    public WeatherData(double temp, double sunlightPercent) {
        this.temperature = temp;
        this.sunlight = sunlightPercent / 100.0; // Yüzdeyi orana çevir (50 -> 0.5)
    }
}