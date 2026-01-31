package GUI;

import Common.SmartDevice;
import Simulation.SmartRoom;
import Structural.Composite.DeviceGroup;

import javax.swing.*;
import java.awt.*;

public class RoomControlPanel extends JPanel {

    private SmartRoom room;
    private DeviceGroup roomGroup;
    private Runnable onRefreshRequest; 

    public RoomControlPanel(SmartRoom room, Runnable onRefreshRequest) {
        this.room = room;
        this.roomGroup = room.getGroup();
        this.onRefreshRequest = onRefreshRequest;

        ThemeManager theme = ThemeManager.getInstance();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 60), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        
       //Rengi ThemeManager'dan al
        setBackground(theme.getCardBackgroundColor()); 

        // 1. ÜST KISIM 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(room.getName() + " [" + String.format("%.1f", room.getTemperature()) + "°C]");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Odayı Aç/Kapat Butonu
        JButton btnToggleRoom = new JButton("Güç " + (roomGroup.isOn() ? "Kapat" : "Aç"));
        
        //Rengi ThemeManager'dan al
        btnToggleRoom.setBackground(theme.getPrimaryButtonColor());
        btnToggleRoom.setForeground(Color.WHITE);
        btnToggleRoom.setFont(new Font("Arial", Font.BOLD, 10));
        btnToggleRoom.setPreferredSize(new Dimension(100, 25));
        
        btnToggleRoom.addActionListener(e -> {
            if (roomGroup.isOn()) roomGroup.turnOff();
            else roomGroup.turnOn();
            
            onRefreshRequest.run();
        });
        
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnToggleRoom, BorderLayout.EAST);

        // 2. ORTA KISIM 
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        tempPanel.setOpaque(false);
        
        JButton btnMinus = new JButton("-");
        JButton btnPlus = new JButton("+");
        JLabel lblTarget = new JLabel("Hedef: " + room.getTargetTemperature() + "°C");
        
        btnMinus.addActionListener(e -> {
            room.setTargetTemperature(room.getTargetTemperature() - 1);
            onRefreshRequest.run();
        });
        
        btnPlus.addActionListener(e -> {
            room.setTargetTemperature(room.getTargetTemperature() + 1);
            onRefreshRequest.run();
        });

        tempPanel.add(btnMinus);
        tempPanel.add(lblTarget);
        tempPanel.add(btnPlus);

        // 3. ALT KISIM 
        JPanel devicesGrid = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        devicesGrid.setOpaque(false);
        
        for (SmartDevice device : roomGroup.getDevices()) {
            JButton btnDev = DeviceUIFactory.createDeviceButton(device, () -> {
                if (device.isOn()) device.turnOff(); else device.turnOn();
                onRefreshRequest.run();
            });
            devicesGrid.add(btnDev);
        }

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setOpaque(false);
        topContainer.add(headerPanel, BorderLayout.NORTH);
        topContainer.add(tempPanel, BorderLayout.CENTER);

        add(topContainer, BorderLayout.NORTH);
        add(devicesGrid, BorderLayout.CENTER);
    }
}