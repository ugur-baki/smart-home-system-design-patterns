package Common;

import Behavioral.Strategy.HeatingStrategy;
import Behavioral.Strategy.EcoMode;
import Behavioral.Strategy.NormalMode; 
import Behavioral.Strategy.TurboMode;

public class SmartThermostat implements SmartDevice {
    
    private boolean isOn = false; 
    private HeatingStrategy heatingStrategy;

    public SmartThermostat() {
        this.heatingStrategy = new EcoMode();
    }

    public double manageTemperature(double currentRoomTemp, int targetRoomTemp) {
        if (!isOn) return 0.0;

        double gap = targetRoomTemp - currentRoomTemp;

        if (gap > 3.0) {
            if (!(heatingStrategy instanceof TurboMode)) heatingStrategy = new TurboMode();
            return 1.0; 
        } else if (gap > 0.5) {
            if (!(heatingStrategy instanceof NormalMode)) heatingStrategy = new NormalMode();
            return 0.5; 
        } else {
            if (!(heatingStrategy instanceof EcoMode)) heatingStrategy = new EcoMode();
            return 0.2; 
        }
    }

    @Override public void turnOn() { this.isOn = true; }
    @Override public void turnOff() { this.isOn = false; }
    @Override public boolean isOn() { return isOn; }

    @Override 
    public String getStatus() { 
        if (!isOn) return "Kapalı";
        return heatingStrategy.getClass().getSimpleName(); 
    }


    @Override public String toString() { return "Termostat"; }
}