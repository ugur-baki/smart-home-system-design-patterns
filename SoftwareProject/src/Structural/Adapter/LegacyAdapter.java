package Structural.Adapter;
import Common.SmartDevice;

public class LegacyAdapter implements SmartDevice{
    
    private LegacyLight legacyLight;
    private boolean status = false;

    public LegacyAdapter(LegacyLight legacyLight) {
        this.legacyLight = legacyLight;
    }

    @Override
    public void turnOn() {
        legacyLight.switchOn();
        this.status = true;
    }

    @Override
    public void turnOff() {
        legacyLight.switchOff();
        this.status = false;
    }

    @Override
    public boolean isOn() {
        return status;
    }

    @Override
    public String getStatus() {
        return "Eski Cihaz (Adapter ile bağlı)";
    }
    public String toString() {
        return "Eski Tip Lamba";
    }
}
