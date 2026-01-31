package Creational.Factory;

import Common.SmartDevice;
import Common.SmartThermostat;

public class ThermostatFactory extends DeviceFactory{
    
    @Override
    public SmartDevice createDevice() {
        return new SmartThermostat();
    }
    
}
