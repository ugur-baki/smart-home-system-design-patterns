package Behavioral.Memento;

import Common.SmartDevice;
import Structural.Composite.DeviceGroup;
import Structural.Decorator.AutoDimmerDecorator;
import Creational.SmartHomeHub; 
import Simulation.SmartRoom;    

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneMemento {
    private Map<SmartDevice, Boolean> powerStates = new HashMap<>();
    private Map<SmartDevice, Integer> brightnessStates = new HashMap<>();
    private Map<SmartDevice, Boolean> autoModeStates = new HashMap<>();
    
    //Oda isimleri ve hedef sıcaklıklarını tutacak Map
    private Map<String, Integer> roomTempStates = new HashMap<>();

    public void saveState(List<SmartDevice> devices) {
        powerStates.clear();
        brightnessStates.clear();
        autoModeStates.clear();
        roomTempStates.clear();
        
        // Cihaz durumlarını kaydet
        for (SmartDevice device : devices) {
            captureDevice(device);
        }

        // Oda sıcaklıklarını kaydet
        Map<String, SmartRoom> rooms = SmartHomeHub.getInstance().getRooms();
        for (Map.Entry<String, SmartRoom> entry : rooms.entrySet()) {
            roomTempStates.put(entry.getKey(), entry.getValue().getTargetTemperature());
        }
        System.out.println("💾 MEMENTO: Kayıt Alındı (Sıcaklıklar Dahil).");
    }

    private void captureDevice(SmartDevice device) {
        powerStates.put(device, device.isOn());

        if (device instanceof AutoDimmerDecorator) {
            AutoDimmerDecorator dimmer = (AutoDimmerDecorator) device;
            brightnessStates.put(device, dimmer.getBrightnessLevel());
            autoModeStates.put(device, dimmer.isAutoMode());
        }

        if (device instanceof DeviceGroup) {
            for (SmartDevice child : ((DeviceGroup) device).getDevices()) {
                captureDevice(child);
            }
        }
    }

    public void restoreState() {
        if (powerStates.isEmpty()) return;

        // 1. Güç durumlarını yükle
        for (Map.Entry<SmartDevice, Boolean> entry : powerStates.entrySet()) {
            SmartDevice device = entry.getKey();
            if (entry.getValue() && !device.isOn()) device.turnOn();
            else if (!entry.getValue() && device.isOn()) device.turnOff();
        }

        // 2. Parlaklık ve Mod durumlarını yükle
        for (Map.Entry<SmartDevice, Integer> entry : brightnessStates.entrySet()) {
            SmartDevice device = entry.getKey();
            if (device instanceof AutoDimmerDecorator) {
                AutoDimmerDecorator dimmer = (AutoDimmerDecorator) device;
                if (autoModeStates.containsKey(device)) {
                    dimmer.setAutoMode(autoModeStates.get(device));
                }
                dimmer.setBrightness(entry.getValue());
            }
        }

        // 3.Sıcaklıkları Geri Yükle
        SmartHomeHub hub = SmartHomeHub.getInstance();
        for (Map.Entry<String, Integer> entry : roomTempStates.entrySet()) {
            SmartRoom room = hub.getRoom(entry.getKey());
            if (room != null) {
                room.setTargetTemperature(entry.getValue());
            }
        }

        System.out.println("💾 MEMENTO: Sahne Geri Yüklendi (Sıcaklıklar Eski Haline Döndü).");
    }
}