package Behavioral.Strategy;

public class TurboMode implements HeatingStrategy {
    @Override
    public void heat() {
        System.out.println("ISITMA: Turbo mod aktif! Hızlı ısıtma başladı.");
    }
}
