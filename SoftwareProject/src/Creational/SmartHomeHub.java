package Creational;

import Simulation.SmartRoom;
import java.util.HashMap;
import java.util.Map;

public class SmartHomeHub {
    private static SmartHomeHub instance;
    
    // Odaları ismine göre saklayan harita
    private Map<String, SmartRoom> rooms;

    private SmartHomeHub() {
        rooms = new HashMap<>();
        System.out.println("HomeHub Başlatılıyor... Veritabanı bağlantısı kuruldu.");
    }

    public static synchronized SmartHomeHub getInstance() {
        if (instance == null) {
            instance = new SmartHomeHub();
        }
        return instance;
    }

    public void logSystem(String message) {
        System.out.println("[HUB LOG]: " + message);
    }
    
    // Yeni oda kurulduğunda buraya kaydederiz
    public void registerRoom(String name, SmartRoom room) {
        rooms.put(name, room);
    }

    // Odayı ismine göre geri çağırırız
    public SmartRoom getRoom(String name) {
        return rooms.get(name);
    }

    public Map<String, SmartRoom> getRooms() {
        return rooms;
    }
}