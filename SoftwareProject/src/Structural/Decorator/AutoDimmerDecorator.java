package Structural.Decorator;

import Common.SmartDevice;

public class AutoDimmerDecorator extends BaseDecorator {

    private int brightnessLevel = 100;
    //Otomatik mod bayrağı. True ise güneşe bakar, False ise manuel kalır.
    private boolean autoMode = true; 

    public AutoDimmerDecorator(SmartDevice device) {
        super(device);
    }
    
    // Simülasyon bu metodu çağırır
    public void adjustBrightness(double sunlightRatio) {
        // Eğer manuel moddaysak (Sinema vb.), güneşi yoksay
        if (!autoMode) return;

        if (isOn()) {
            this.brightnessLevel = (int) ((1.0 - sunlightRatio) * 100);
            if (this.brightnessLevel < 0) this.brightnessLevel = 0;
        } else {
            this.brightnessLevel = 0;
        }
    }

    //Manuel olarak parlaklık ayarla 
    public void setBrightness(int level) {
        this.brightnessLevel = level;
    }

    // Modu değiştir
    public void setAutoMode(boolean auto) {
        this.autoMode = auto;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    public int getBrightnessLevel() {
        return brightnessLevel;
    }

    @Override
    public String getStatus() {
        if (!isOn()) return "KAPALI";
        String modeStr = autoMode ? "OTO" : "MANUEL";
        return String.format("%s-PARLAKLIK: %%%d", modeStr, brightnessLevel);
    }
    
    @Override
    public String toString() {
        return "Akıllı Lamba";
    }
}