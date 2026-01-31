package GUI;

import Common.SmartDevice;
import Common.SmartThermostat;
import Structural.Decorator.AutoDimmerDecorator;

import javax.swing.*;
import java.awt.*;

public class DeviceUIFactory {

    public static JButton createDeviceButton(SmartDevice device, Runnable onClickAction) {
        // Singleton örneğini al
        ThemeManager theme = ThemeManager.getInstance();

        String statusText = device.isOn() ? "AÇIK" : "KAPALI";
        
        // 1. Varsayılan Renk 
        Color bg = device.isOn() ? theme.getOnColor() : theme.getOffColor();

        // 2. Termostat Kontrolü
        if (device instanceof SmartThermostat && device.isOn()) {
            statusText = ((SmartThermostat) device).getStatus();
            bg = theme.getThermostatColor(statusText);
        }

        // 3. Dimmer Kontrolü
        if (device instanceof AutoDimmerDecorator && device.isOn()) {
            int lvl = ((AutoDimmerDecorator) device).getBrightnessLevel();
            statusText = "PARLAKLIK: %" + lvl;
            bg = theme.getDimmerColor(lvl);
        }

        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(140, 60));
        
        // Fontu da Singleton'dan çekiyoruz
        btn.setFont(theme.getButtonFont());
        
        btn.setText("<html><center>" + device.toString() + "<br/><b>" + statusText + "</b></center></html>");
        btn.setBackground(bg);
        btn.addActionListener(e -> onClickAction.run());
        
        return btn;
    }
}