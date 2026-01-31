package Behavioral.Template;

import Behavioral.Observer.SimulationObserver;
import Simulation.SimulationContext;

// TEMPLATE METHOD PATTERN
public abstract class SimulatedEntity implements SimulationObserver {
    
    protected String entityName;
    
    public SimulatedEntity(String name) {
        this.entityName = name;
        // Otomatik abone ol
        SimulationContext.getInstance().attach(this);
    }

    @Override
    public void update(int hour, int minute, double outsideTemp, double sunlight) {
        
        processPhysics(outsideTemp, sunlight); // 1. Fiziksel Etki
        checkSensors();                        // 2. Sensör Kontrolü
        executeLogic();                        // 3. Karar Verme
    }

    // Alt sınıfların doldurması gereken boşluklar
    protected abstract void processPhysics(double outsideTemp, double sunlight);
    protected abstract void checkSensors();
    protected abstract void executeLogic();
}