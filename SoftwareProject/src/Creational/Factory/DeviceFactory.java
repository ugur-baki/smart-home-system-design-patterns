package Creational.Factory;

import Common.SmartDevice;
import Common.SmartLight;
import Common.SmartThermostat;
import Structural.Adapter.LegacyAdapter;
import Structural.Adapter.LegacyLight;
import Common.DeviceType; 

public abstract class DeviceFactory {

    public abstract SmartDevice createDevice();


    public static SmartDevice createDevice(DeviceType type) {
        switch (type) {
            case LIGHT:
                return new SmartLight();
            case THERMOSTAT:
                return new SmartThermostat();
            case LEGACY_LIGHT:
                LegacyLight oldBulb = new LegacyLight();
                return new LegacyAdapter(oldBulb);
            default:
                throw new IllegalArgumentException("Bilinmeyen cihaz türü!");
        }
    }
}