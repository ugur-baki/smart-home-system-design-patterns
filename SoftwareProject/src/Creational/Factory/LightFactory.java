package Creational.Factory;

import Common.SmartDevice;
import Common.SmartLight;

public class LightFactory extends DeviceFactory{
    
    @Override
    public SmartDevice createDevice() {
        return new SmartLight();
    }
    
}
