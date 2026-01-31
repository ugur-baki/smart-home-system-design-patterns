package GUI;

import Structural.Proxy.SmartHomeProxy;
import Simulation.SmartRoom; 
import Structural.Composite.DeviceGroup;
import Creational.SmartHomeHub; 

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UserDashboardPanel extends JPanel {
    
    private SmartHomeProxy systemProxy;
    private JPanel contentPanel;

    public UserDashboardPanel(SmartHomeProxy proxy) {
        this.systemProxy = proxy;
        this.setLayout(new BorderLayout());
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        contentPanel.removeAll();
        
        Map<String, DeviceGroup> groups = systemProxy.getRooms();

        for (String roomName : groups.keySet()) {
            SmartRoom room = SmartHomeHub.getInstance().getRoom(roomName);
            
            if (room != null) {
                RoomControlPanel panel = new RoomControlPanel(room, this::refresh);
                contentPanel.add(panel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 15))); 
            }
        }
        
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}