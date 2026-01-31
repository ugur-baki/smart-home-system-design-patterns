package GUI;

import Structural.Proxy.SmartHomeProxy;
import Structural.Composite.DeviceGroup;
import Structural.Decorator.AutoDimmerDecorator;
import Simulation.SimulationContext;
import Simulation.SmartRoom;
import Behavioral.Observer.SimulationObserver;
import Behavioral.Memento.SceneMemento;
import Common.SmartDevice;
import Common.SmartThermostat;
import Creational.SmartHomeHub;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SmartHomeUI extends JFrame implements SimulationObserver {

    private SimulationPanel simulationPanel;
    private UserDashboardPanel userDashboard;
    private AdminControlPanel adminPanel;
    private TimeControlPanel timeControlPanel;

    private Timer simulationTimer;
    private SmartHomeProxy proxy;
    
    private SceneMemento sceneMemento = new SceneMemento();
    private boolean isCinemaMode = false;

    public SmartHomeUI(SmartHomeProxy proxy) {
        this.proxy = proxy;
        setTitle("HomeGuard v8.0 - Scenario Edition");
        setSize(1100, 750); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Timer
        simulationTimer = new Timer(1000, e -> SimulationContext.getInstance().tick());
        simulationTimer.start();

        // 1. ÜST: Durum Paneli
        simulationPanel = new SimulationPanel();
        add(simulationPanel, BorderLayout.NORTH);

        // 2. ORTA: Sekmeler
        JTabbedPane tabbedPane = new JTabbedPane();
        userDashboard = new UserDashboardPanel(proxy);
        tabbedPane.addTab("🏠 EV KONTROLÜ", userDashboard);
        adminPanel = new AdminControlPanel(proxy);
        tabbedPane.addTab("🛠️ YÖNETİCİ PANELİ", adminPanel);
        add(tabbedPane, BorderLayout.CENTER);

        // 3. ALT: Senaryo + Zaman
        JPanel bottomContainer = new JPanel(new BorderLayout());
        
        // A) Senaryo Barı 
        bottomContainer.add(createScenarioBar(), BorderLayout.NORTH);
        
        // B) Zaman Kontrolü
        timeControlPanel = new TimeControlPanel(simulationTimer);
        bottomContainer.add(timeControlPanel, BorderLayout.CENTER);

        add(bottomContainer, BorderLayout.SOUTH);

        SimulationContext.getInstance().attach(this);
        refreshAll();
    }

   private JPanel createScenarioBar() {
        // ThemeManager örneğini al
        ThemeManager theme = ThemeManager.getInstance();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(getWidth(), 60));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        panel.setBackground(new Color(220, 220, 225));

        // SOL: Butonlar
        JPanel leftBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftBox.setOpaque(false);

        // 1. Master Switch 
        JButton btnMasterOff = new JButton("🛑 TÜM EVİ KAPAT");
        btnMasterOff.setBackground(theme.getMasterErrorColor()); // GÜNCELLENDİ
        btnMasterOff.setForeground(Color.WHITE);
        btnMasterOff.addActionListener(e -> {
            for (DeviceGroup group : proxy.getRooms().values()) {
                group.turnOff();
            }
            refreshAll();
            JOptionPane.showMessageDialog(this, "Evdeki tüm cihazlar kapatıldı.");
        });

        // 2. Sinema Modu 
        JToggleButton btnCinema = new JToggleButton("🎬 SİNEMA MODU: KAPALI");
        btnCinema.setBackground(theme.getCinemaPassiveColor());
        btnCinema.setForeground(Color.BLACK);
        
        btnCinema.addActionListener(e -> {
            boolean active = btnCinema.isSelected();
            toggleCinemaMode(active);
            
            if (active) {
                // Aktif Renk ThemeManager'dan
                btnCinema.setBackground(theme.getCinemaActiveColor());
                btnCinema.setForeground(Color.WHITE);
                btnCinema.setText("🎬 SİNEMA MODU: AÇIK");
            } else {
                // Pasif Renk ThemeManager'dan
                btnCinema.setBackground(theme.getCinemaPassiveColor());
                btnCinema.setForeground(Color.BLACK);
                btnCinema.setText("🎬 SİNEMA MODU: KAPALI");
            }
        });

        // 3. Diğer Butonlar
        JButton btnHoliday = new JButton("✈️ Tatil");
        JButton btnCustom1 = new JButton("Mod 1");
        JButton btnCustom2 = new JButton("Mod 2");

        leftBox.add(btnMasterOff);
        leftBox.add(btnCinema);
        leftBox.add(btnHoliday);
        leftBox.add(btnCustom1);
        leftBox.add(btnCustom2);

        // SAĞ: Genel Sıcaklık
        JPanel rightBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightBox.setOpaque(false);
        
        JLabel lblGlobalTemp = new JLabel("Ev Geneli Hedef Sıcaklık:");
        JSpinner spinnerTemp = new JSpinner(new SpinnerNumberModel(22, 15, 30, 1));
        
        spinnerTemp.addChangeListener(e -> {
            int val = (int) spinnerTemp.getValue();
            for(String roomName : proxy.getRooms().keySet()) {
                proxy.setRoomTargetTemp(roomName, val);
            }
            refreshAll();
        });

        rightBox.add(lblGlobalTemp);
        rightBox.add(spinnerTemp);

        panel.add(leftBox, BorderLayout.WEST);
        panel.add(rightBox, BorderLayout.EAST);
        return panel;
    }

    private void toggleCinemaMode(boolean enable) {
        if (enable) {

            // 1. Mevcut Durumu Kaydet (Memento)
            List<SmartDevice> allDevices = new ArrayList<>();
            for(DeviceGroup grp : proxy.getRooms().values()) {
                allDevices.add(grp);
            }
            sceneMemento.saveState(allDevices);

            // 2. Cihazları Ayarla (Işıklar kısılsın, Termostat açılsın)
            applyCinemaLogic(allDevices);

            // 3.Odaların Hedef Sıcaklığını Sabitle (Örn: 20 Derece)
            for (String roomName : proxy.getRooms().keySet()) {
                // Proxy üzerinden odanın hedefini değiştiriyoruz
                proxy.setRoomTargetTemp(roomName, 20);
            }

            System.out.println(">>> SİNEMA MODU AKTİF (Işıklar %15, Isı 20°C) <<<");

        } else {
           

            // 4. Eski Hali Geri Yükle 
            sceneMemento.restoreState();

    

            System.out.println("<<< SİNEMA MODU KAPANDI (ESKİ HALİNE DÖNDÜ) >>>");
        }
        refreshAll();
    }

    private void applyCinemaLogic(List<SmartDevice> devices) {
        for (SmartDevice d : devices) {
            // 1. Eğer bir Grup ise (Recursive çağırma)
            if (d instanceof DeviceGroup) {
                applyCinemaLogic(((DeviceGroup) d).getDevices());
                continue;
            }

            // 2. Eğer Akıllı Lamba ise (Dimmer)
            if (d instanceof AutoDimmerDecorator) {
                AutoDimmerDecorator dimmer = (AutoDimmerDecorator) d;
                dimmer.turnOn();
                dimmer.setAutoMode(false); // Otomatiği kapat
                dimmer.setBrightness(15);  // Loş ışık
            }

            // 3. Eğer Termostat ise
            else if (d instanceof SmartThermostat) {
                // Termostatı mutlaka açıyoruz
                d.turnOn(); 
                System.out.println("🔥 SİNEMA MODU: Termostat Aktif Edildi.");
            }

            // 4. Normal lamba ise kapat
            else if (d.toString().contains("Lamba") || d.toString().contains("Light")) {
                d.turnOff();
            }
        }
    }

    private void refreshAll() {
        userDashboard.refresh();
        adminPanel.refresh();
    }

    @Override
    public void update(int hour, int minute, double outsideTemp, double sunlight) {
        SwingUtilities.invokeLater(this::refreshAll);
    }
}