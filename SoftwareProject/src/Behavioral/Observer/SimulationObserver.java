package Behavioral.Observer;

public interface SimulationObserver {
    // Evren her "tick" ettiğinde bu metod çağrılır
    void update(int hour, int minute, double outsideTemp, double sunlight);
}