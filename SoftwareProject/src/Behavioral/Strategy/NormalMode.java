package Behavioral.Strategy;

public class NormalMode implements HeatingStrategy {

    @Override
    public void heat() {
        System.out.println("ISITMA: Normal mod aktif! Normal ısıtma başladı.");
    }    
}