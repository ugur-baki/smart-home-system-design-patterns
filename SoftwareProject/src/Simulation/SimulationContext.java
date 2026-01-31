package Simulation;

import java.util.ArrayList;
import java.util.List;
import Simulation.Weather.*;
import Simulation.Weather.Data.*;
import Behavioral.Observer.SimulationObserver;

public class SimulationContext {
    
    private static SimulationContext instance;
    
    private int currentHour = 0;   // Başlangıç saati (00:00)
    private int currentMinute = 0;
    
    // Değerler artık veriden okunacak
    private double outsideTemp;
    private double sunlightRatio; 
    
    private List<SimulationObserver> observers = new ArrayList<>();
   
    private WeatherSource weatherSource;

    private SimulationContext() {
        // Varsayılan olarak Erzurum verisini yüklüyoruz
        this.weatherSource = new ErzurumJan6Data();
        
        // İlk değerleri set et
        updateEnvironment(); 
        System.out.println("Evren Simülasyonu Başlatıldı: Kaynak -> Erzurum 6 Ocak");
    }

    public static synchronized SimulationContext getInstance() {
        if (instance == null) instance = new SimulationContext();
        return instance;
    }
    
    public void setWeatherSource(WeatherSource source) {
        this.weatherSource = source;
    }

    public void tick() {
        // Zamanı İlerlet
        currentMinute++;
        if (currentMinute >= 60) {
            currentMinute = 0;
            currentHour++;
            if (currentHour >= 24) currentHour = 0;
        }
        
        //Veri kaynağından oku
        updateEnvironment();
        
        notifyObservers();
    }
    
    private void updateEnvironment() {
        // O eski if-else blokları çöp oldu.
        // Artık profesyonelce veriden okuyoruz:
        WeatherData data = weatherSource.getWeather(currentHour, currentMinute);
        
        this.outsideTemp = data.temperature;
        this.sunlightRatio = data.sunlight;
    }

    public void attach(SimulationObserver o) { observers.add(o); }
    public void detach(SimulationObserver o) { observers.remove(o); }
    private void notifyObservers() {
        for (SimulationObserver o : observers) {
            o.update(currentHour, currentMinute, outsideTemp, sunlightRatio);
        }
    }
    
    public String getTimeString() { return String.format("%02d:%02d", currentHour, currentMinute); }
    public double getOutsideTemp() { return outsideTemp; }
    public double getSunlight() { return sunlightRatio; }
}