package GUI;

import Behavioral.Observer.SimulationObserver;
import Simulation.SimulationContext;
import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel implements SimulationObserver {

    private JLabel timeLabel;
    private JLabel tempLabel;
    private JLabel sunLabel;
    private JProgressBar sunlightBar;

    public SimulationPanel() {
        setLayout(new GridLayout(1, 4));
        setBackground(new Color(50, 50, 50)); // Koyu Tema
        setBorder(BorderFactory.createTitledBorder(null, "ERZURUM - 6 OCAK SALI", 0, 0, null, Color.WHITE));

        // Bileşenler
        timeLabel = createLabel("🕒 00:00");
        tempLabel = createLabel("🌡️ 20.0 °C");
        sunLabel = createLabel("☀️ Güneş: Var");
        
        sunlightBar = new JProgressBar(0, 100);
        sunlightBar.setValue(100);
        sunlightBar.setStringPainted(true);
        sunlightBar.setForeground(Color.ORANGE);

        add(timeLabel);
        add(tempLabel);
        add(sunLabel);
        add(sunlightBar);

        // Simülasyona Abone Ol 
        SimulationContext.getInstance().attach(this);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setForeground(Color.CYAN);
        lbl.setFont(new Font("Monospaced", Font.BOLD, 16));
        return lbl;
    }

    // ZAMAN MOTORUNDAN GELEN SİNYAL
    @Override
    public void update(int hour, int minute, double outsideTemp, double sunlight) {
        // Swing arayüzünü güncelle
        SwingUtilities.invokeLater(() -> {
            timeLabel.setText(String.format("🕒 %02d:%02d", hour, minute));
            tempLabel.setText(String.format("🌡️ %.1f °C", outsideTemp));
            
            if (sunlight > 0.5) {
                sunLabel.setText("☀️ GÜNDÜZ");
                sunLabel.setForeground(Color.YELLOW);
            } else {
                sunLabel.setText("🌙 GECE");
                sunLabel.setForeground(Color.GRAY);
            }
            
            sunlightBar.setValue((int)(sunlight * 100));
        });
    }
}