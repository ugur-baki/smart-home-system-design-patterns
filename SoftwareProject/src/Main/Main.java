package Main;


import GUI.SmartHomeUI;
import Structural.Proxy.SmartHomeProxy;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            // 1. Sistemi Arka Planda Kur
            SmartHomeProxy system = new SmartHomeProxy("ADMIN");
            
            // Örnek Veriler Ekle
            system.addDevice("LIGHT", "Salon", true);
            system.addDevice("THERMOSTAT", "Salon", false);
            system.addDevice("LIGHT", "Mutfak", false);

            system.setUserRole("ADMIN"); //USER yazarsak admin yetkilerini kaldırırız
            
            // 2. Arayüzü Başlat
            SmartHomeUI gui = new SmartHomeUI(system);
            gui.setVisible(true);
            
            System.out.println("✅ v6.0 Arayüzü Başlatıldı.");
        });
    }
}