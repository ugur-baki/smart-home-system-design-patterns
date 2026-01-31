package GUI;

import java.awt.*;

public class ThemeManager {
    private static ThemeManager instance;

    // --- MEVCUT RENKLER ---
    private final Color colorOn = new Color(144, 238, 144); // Açık Yeşil
    private final Color colorOff = new Color(255, 182, 193); // Açık Kırmızı
    
    // Termostat Renkleri
    private final Color colorTurbo = new Color(255, 69, 0);   // Koyu Turuncu
    private final Color colorNormal = new Color(255, 165, 0); // Turuncu
    
    private final Color colorCinemaActive = new Color(255, 140, 0);   
    private final Color colorCinemaPassive = new Color(200, 200, 200); 
    private final Color colorMasterError = new Color(220, 50, 50);    
    private final Color colorPrimaryBtn = new Color(70, 130, 180);    
    private final Color colorCardBg = new Color(245, 245, 245);       

    // Fontlar
    private final Font fontButton = new Font("Arial", Font.BOLD, 12);
    private final Font fontHeader = new Font("Arial", Font.BOLD, 14);

    private ThemeManager() {}

    public static synchronized ThemeManager getInstance() {
        if (instance == null) instance = new ThemeManager();
        return instance;
    }

    public Color getOnColor() { return colorOn; }
    public Color getOffColor() { return colorOff; }
    public Font getButtonFont() { return fontButton; }
    public Font getHeaderFont() { return fontHeader; }

    
    public Color getCinemaActiveColor() { return colorCinemaActive; }
    public Color getCinemaPassiveColor() { return colorCinemaPassive; }
    public Color getMasterErrorColor() { return colorMasterError; }
    public Color getPrimaryButtonColor() { return colorPrimaryBtn; }
    public Color getCardBackgroundColor() { return colorCardBg; }

    public Color getThermostatColor(String status) {
        if (status.contains("Turbo")) return colorTurbo;
        if (status.contains("Normal")) return colorNormal;
        return colorOn; 
    }

    // Dimmer için dinamik renk
    public Color getDimmerColor(int brightness) {
        int colorVal = 100 + (int)(brightness * 1.5);
        if (colorVal > 255) colorVal = 255;
        return new Color(colorVal, 255, 0);
    }
}