package Creational;

public class AutomationScenario {
// Üretilecek nesnenin özellikleri
    private String name;
    private int temperature;
    private boolean lightsOn;
    public boolean blindsOpen;

    // Private constructor: Sadece Builder erişebilir
    AutomationScenario(ScenarioBuilder builder) {
        this.name = builder.name;
        this.temperature = builder.temperature;
        this.lightsOn = builder.lightsOn;
        this.blindsOpen = builder.blindsOpen;
    }

    public void runScenario() {
        System.out.println("Senaryo Çalıştı: " + name + 
                           " | Hedef Isı: " + temperature + 
                           " | Işıklar: " + lightsOn);
    }

   //(Builder)
    public static class ScenarioBuilder {
        private String name;
        // Varsayılan değerler
        private int temperature = 20;
        private boolean lightsOn = false;
        private boolean blindsOpen = false;

        public ScenarioBuilder(String name) {
            this.name = name;
        }

        public ScenarioBuilder setTemperature(int temp) {
            this.temperature = temp;
            return this;
        }

        public ScenarioBuilder setLightsOn(boolean status) {
            this.lightsOn = status;
            return this;
        }

        public AutomationScenario build() {
            return new AutomationScenario(this);
        }
            
    }
}
