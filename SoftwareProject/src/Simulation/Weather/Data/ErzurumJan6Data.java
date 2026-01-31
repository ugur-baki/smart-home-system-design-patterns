package Simulation.Weather.Data;

import java.util.TreeMap;
import Simulation.Weather.*;

public class ErzurumJan6Data implements WeatherSource {
    
    // Dakika bazlı veri haritası (Dakika -> Hava Durumu)
    // Örn: Saat 01:00 -> 60. dakika
    private TreeMap<Integer, WeatherData> dataMap = new TreeMap<>();

    public ErzurumJan6Data() {
        initData();
    }

    private void initData() {
        // Tablodaki verileri (Saat * 60 + Dakika) formülüyle ekliyoruz
        add(0, 0,  -8, 0);   // 00:00
        add(1, 0,  -8, 0);
        add(2, 0,  -9, 0);
        add(3, 0,  -9, 0);
        add(4, 0,  -10, 0);
        add(5, 0,  -10, 0);  // En soğuk
        add(6, 0,  -10, 2);  // Tan vakti
        add(7, 0,  -9, 25);  // Alacakaranlık
        add(7, 33, -9, 50);  // GÜNEŞ DOĞUŞU (Dakika: 33)
        add(8, 0,  -8, 75);
        add(9, 0,  -7, 90);
        add(10, 0, -6, 100); // Tam aydınlık
        add(11, 0, -5, 100);
        add(12, 0, -4, 100);
        add(13, 0, -3, 100);
        add(14, 0, -2, 100); // En sıcak
        add(15, 0, -3, 90);
        add(16, 0, -4, 70);
        add(16, 30, -5, 40); // Hava kararmaya başlıyor (Dakika: 30)
        add(17, 0,  -6, 20); // Güneş batışı
        add(17, 30, -7, 5);  // Alacakaranlık sonu
        add(18, 0,  -8, 0);  // Gece
        add(19, 0,  -8, 0);
        add(20, 0,  -9, 0);
        add(21, 0,  -9, 0);
        add(22, 0,  -10, 0);
        add(23, 0,  -10, 0);
    }

    private void add(int h, int m, double temp, double light) {
        dataMap.put(h * 60 + m, new WeatherData(temp, light));
    }

    @Override
    public WeatherData getWeather(int hour, int minute) {
        int currentMinuteOfDay = hour * 60 + minute;
        
        // Şu anki dakikaya eşit veya daha küçük olan en son anahtarı bul (floorEntry)
        // Yani saat 07:45 ise, 07:33 verisini getirecek.
        var entry = dataMap.floorEntry(currentMinuteOfDay);
        
        if (entry != null) {
            return entry.getValue();
        }
        // Eğer veri yoksa varsayılan (Gece yarısından öncesi için)
        return new WeatherData(-8, 0);
    }
}