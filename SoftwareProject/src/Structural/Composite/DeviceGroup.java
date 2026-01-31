package Structural.Composite;

import java.util.ArrayList;
import java.util.List;
import Common.SmartDevice;

public class DeviceGroup implements SmartDevice{
   
    private String groupName;
   
    private List<SmartDevice> devices = new ArrayList<>();

    public DeviceGroup(String groupName) {
        this.groupName = groupName;
    }

    public void add(SmartDevice device) {
        devices.add(device);
    }

    public void remove(SmartDevice device) {
        devices.remove(device);
    }

    @Override
    public void turnOn() {
        System.out.println("--- " + groupName + " Açılıyor ---");
        for (SmartDevice device : devices) {
            device.turnOn(); 
        }
    }

    @Override
    public void turnOff() {
        System.out.println("--- " + groupName + " Kapanıyor ---");
        for (SmartDevice device : devices) {
            device.turnOff();
        }
    }

    @Override
    public String getStatus() {
        return groupName + " (İçindeki cihaz sayısı: " + devices.size() + ")";
    }

    public List<SmartDevice> getDevices() {
        return devices;
    }

    @Override
    public boolean isOn() {
        for (SmartDevice device : devices) {
            if (device.isOn()) return true;
        }
        return false;
    }

    public void replaceDevice(Common.SmartDevice oldDevice, Common.SmartDevice newDevice) {
        int index = devices.indexOf(oldDevice);
        
        if (index != -1) {
            devices.set(index, newDevice);
            System.out.println(groupName + ": Cihaz güncellendi -> " + newDevice.getClass().getSimpleName());
        } else {
            System.out.println("Hata: Değiştirilecek cihaz grupta bulunamadı.");
        }
    }
   
}
