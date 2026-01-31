package Common;

public enum DeviceType {
    LIGHT("Akıllı Aydınlatma"),
    THERMOSTAT("Akıllı Termostat"),
    
    LEGACY_LIGHT("Eski Tip Lamba (Adapter)");

    private String displayName;

    DeviceType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}


