package Structural.Decorator;
import Common.SmartDevice;

public abstract class BaseDecorator implements SmartDevice{
protected SmartDevice wrappedDevice;

    public BaseDecorator(SmartDevice device) {
        this.wrappedDevice = device;
    }

    @Override
    public void turnOn()
    {
        wrappedDevice.turnOn(); 
    }

    @Override
    public void turnOff() 
    {
        wrappedDevice.turnOff(); 
    }

    @Override
    public String getStatus() 
    {
        return wrappedDevice.getStatus(); 
    }

    @Override
    public boolean isOn() {
        return wrappedDevice.isOn();
    }
}


