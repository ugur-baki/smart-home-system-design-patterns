package Common;

public class SmartLight implements SmartDevice {
    
    private boolean isOn = false; // State pattern yerine boolean bayrak

    @Override
    public void turnOn() {
        if (!isOn) {
            System.out.println("Lamba: Açılıyor...");
            isOn = true;
        } else {
            System.out.println("Lamba: Zaten açık.");
        }
    }

    @Override
    public void turnOff() {
        if (isOn) {
            System.out.println("Lamba: Kapanıyor...");
            isOn = false;
        }
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public String getStatus() {
        return isOn ? "Açık" : "Kapalı";
    }
    
    @Override
    public String toString() {
        return "Lamba";
    }

    
}