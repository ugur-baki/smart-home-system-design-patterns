package Behavioral.Strategy;

public class EcoMode implements HeatingStrategy{
    
    @Override
    public void heat() {
        System.out.println("ISITMA: Ekonomik mod aktif. Yavaş ve tasarruflu ısıtılıyor.");
    }
}
