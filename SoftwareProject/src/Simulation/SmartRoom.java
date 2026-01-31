package Simulation;

import Common.SmartDevice;
import Common.SmartThermostat;
import Structural.Decorator.AutoDimmerDecorator; // YENİ
import Structural.Composite.DeviceGroup;

import java.util.Random;

import Behavioral.Template.SimulatedEntity;

public class SmartRoom extends SimulatedEntity {
    
    private DeviceGroup physicalGroup; 
    private double currentInsideTemp = 20.0;
    private int targetTemp = 24; 
    private Random rand = new Random();

    public SmartRoom(String name, DeviceGroup group) {
        super(name);
        this.physicalGroup = group;
    }

    @Override
    protected void processPhysics(double outsideTemp, double sunlight) {
        // 1. Isı Hesaplamaları
        if (currentInsideTemp > outsideTemp) {
            currentInsideTemp -= rand.nextDouble(0.1, 0.35);}


        else if (currentInsideTemp < outsideTemp) {
            currentInsideTemp += rand.nextDouble(0.1, 0.35);}

        for (SmartDevice d : physicalGroup.getDevices()) {
            // Termostat Etkisi
            if (d instanceof SmartThermostat) {
                SmartThermostat thermostat = (SmartThermostat) d;
                double power = thermostat.manageTemperature(currentInsideTemp, this.targetTemp);
                currentInsideTemp += power;
            }
            
            // Işık Hesaplamaları (Otomatik Dimmer)
            // Eğer cihaz bir Dimmer Decorator ise, güneş bilgisini gönder
            if (d instanceof AutoDimmerDecorator) {
                ((AutoDimmerDecorator) d).adjustBrightness(sunlight);
            }
        }
    }

    @Override
    protected void checkSensors() {
        // Sensör yok
    }

    @Override
    protected void executeLogic() {
        if (currentInsideTemp > 35.0) physicalGroup.turnOff();
    }
    
    public double getTemperature() { return currentInsideTemp; }
    public void setTargetTemperature(int temp) { this.targetTemp = temp; }
    public int getTargetTemperature() { return targetTemp; }

    public DeviceGroup getGroup() {
        return physicalGroup;
    }

    public String getName() {
        return super.entityName;
    }
    
}