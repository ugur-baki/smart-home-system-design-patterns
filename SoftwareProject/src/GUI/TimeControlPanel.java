package GUI;

import javax.swing.*;
import java.awt.*;

public class TimeControlPanel extends JPanel {
    
    private Timer simulationTimer;
    
    public TimeControlPanel(Timer timer) {
        this.simulationTimer = timer;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Zaman Akış Kontrolü"));
        this.setBackground(new Color(50, 50, 50));

        JLabel lblSpeed = new JLabel("Hız: 1x");
        lblSpeed.setForeground(Color.WHITE);
        lblSpeed.setHorizontalAlignment(SwingConstants.CENTER);

        JSlider speedSlider = new JSlider(0, 100, 1);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setBackground(new Color(50, 50, 50));

        speedSlider.addChangeListener(e -> {
            int val = speedSlider.getValue();
            if (val == 0) {
                simulationTimer.stop();
                lblSpeed.setText("Simülasyon Durduruldu ⏸️");
            } else {
                int newDelay = 1000 / (val == 0 ? 1 : val);
                simulationTimer.setDelay(newDelay);
                if (!simulationTimer.isRunning()) simulationTimer.start();
                lblSpeed.setText("Hız: " + val + "x ⏩");
            }
        });

        this.add(lblSpeed, BorderLayout.NORTH);
        this.add(speedSlider, BorderLayout.CENTER);
    }
}