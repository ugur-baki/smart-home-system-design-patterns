package Structural.Adapter;

public class LegacyLight {
    public void switchOn() {
        System.out.println("Eski Model Işık: Yanıyor (Legacy API)");
    }
    
    public void switchOff() {
        System.out.println("Eski Model Işık: Söndü (Legacy API)");
    }

    public String toString() {
        return "Eski Tip Lamba";
    }
}
