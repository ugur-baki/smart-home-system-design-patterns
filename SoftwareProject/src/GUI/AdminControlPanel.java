package GUI;

import Common.DeviceType;
import Common.SmartDevice;
import Common.SmartThermostat;
import Structural.Composite.DeviceGroup;
import Structural.Decorator.AutoDimmerDecorator;
import Structural.Proxy.SmartHomeProxy;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminControlPanel extends JPanel {

    private SmartHomeProxy systemProxy;
    private JPanel listPanel;
    
    // Form Elemanları
    private JComboBox<DeviceType> comboType;
    private JComboBox<String> cmbRoom; 
    private JCheckBox chkDimmer;

    // ThemeManager
    private ThemeManager theme = ThemeManager.getInstance();

    public AdminControlPanel(SmartHomeProxy proxy) {
        this.systemProxy = proxy;
        this.setLayout(new BorderLayout());

        // Üst Form
        JPanel formPanel = createAddDeviceForm();
        this.add(formPanel, BorderLayout.NORTH);

        // Liste
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(BorderFactory.createTitledBorder("Cihaz Envanteri"));
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scroll, BorderLayout.CENTER);
        
        updateRoomCombo();
    }

    private JPanel createAddDeviceForm() {
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.setBorder(BorderFactory.createTitledBorder("Yeni Cihaz Ekle"));
        
        formPanel.setBackground(theme.getCardBackgroundColor());

        comboType = new JComboBox<>(DeviceType.values());
        
        cmbRoom = new JComboBox<>();
        cmbRoom.setEditable(true); 
        cmbRoom.setPreferredSize(new Dimension(140, 25));
        
        chkDimmer = new JCheckBox("Dimmer (Oto-Parlaklık)");
        chkDimmer.setOpaque(false);

        comboType.addActionListener(e -> {
            DeviceType selected = (DeviceType) comboType.getSelectedItem();
            chkDimmer.setEnabled(selected == DeviceType.LIGHT);
            if (selected != DeviceType.LIGHT) chkDimmer.setSelected(false);
        });

        JButton btnAdd = new JButton("Cihaz Ekle ➕");
        
    
        btnAdd.setBackground(theme.getPrimaryButtonColor());
        btnAdd.setForeground(Color.WHITE);
        
        btnAdd.addActionListener(e -> {
            String room = (String) cmbRoom.getSelectedItem();
            if (room != null && !room.trim().isEmpty()) {
                systemProxy.addDevice(
                    ((DeviceType)comboType.getSelectedItem()).name(), 
                    room.trim(), 
                    chkDimmer.isSelected()
                );
                updateRoomCombo(); 
                refresh();         
            }
        });

        formPanel.add(new JLabel("Tür:"));
        formPanel.add(comboType);
        formPanel.add(new JLabel("Oda:"));
        formPanel.add(cmbRoom);
        formPanel.add(chkDimmer);
        formPanel.add(btnAdd);
        
        return formPanel;
    }

    private void updateRoomCombo() {
        Object currentText = cmbRoom.getSelectedItem(); 
        cmbRoom.removeAllItems();
        Map<String, DeviceGroup> rooms = systemProxy.getRooms();
        for (String rName : rooms.keySet()) {
            cmbRoom.addItem(rName);
        }
        if (currentText != null) {
            cmbRoom.setSelectedItem(currentText);
        }
    }

    public void refresh() {
        listPanel.removeAll();
        Map<String, DeviceGroup> rooms = systemProxy.getRooms();

        for (String rName : rooms.keySet()) {
            DeviceGroup group = rooms.get(rName);
            
            JLabel roomTitle = new JLabel("📂 " + rName);
            roomTitle.setFont(theme.getHeaderFont());
            roomTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
            listPanel.add(roomTitle);

            for (SmartDevice device : group.getDevices()) {
                JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                
                JLabel lblInfo = new JLabel("   • " + device.getClass().getSimpleName());
                lblInfo.setPreferredSize(new Dimension(300, 30));

                JButton btnDel = new JButton("Sil ❌");
                btnDel.setBackground(theme.getMasterErrorColor()); 
                btnDel.setForeground(Color.WHITE);
                btnDel.addActionListener(e -> {
                    systemProxy.removeDevice(rName, device);
                    updateRoomCombo(); 
                    refresh();
                });

                itemPanel.add(lblInfo);
                itemPanel.add(btnDel);

                if (!(device instanceof AutoDimmerDecorator) && !(device instanceof SmartThermostat)) {
                    JButton btnUp = new JButton("Dimmer Ekle ⬆️");
                    btnUp.setBackground(Color.ORANGE); 
                    btnUp.addActionListener(e -> {
                        systemProxy.addDimmerFeature(rName, device);
                        refresh();
                    });
                    itemPanel.add(btnUp);
                }
                listPanel.add(itemPanel);
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
}