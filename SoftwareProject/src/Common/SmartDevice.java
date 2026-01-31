package Common;

public interface SmartDevice {
    void turnOn();
    void turnOff();
    boolean isOn();
    String getStatus();

    String toString();
}