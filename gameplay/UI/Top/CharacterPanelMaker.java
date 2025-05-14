package gameplay.UI.Top;

import javax.swing.*;
import java.awt.*;

public class CharacterPanelMaker {

    public static JPanel create(String name, String faction) {
        JPanel charPanel = new JPanel(new BorderLayout());
        charPanel.setBorder(BorderFactory.createTitledBorder(name));
        charPanel.setOpaque(false);

        JProgressBar healthBar = new JProgressBar(0, 100);
        healthBar.setValue(80);
        healthBar.setStringPainted(true);
        healthBar.setForeground(Color.RED);
        healthBar.setOpaque(false);
        healthBar.setBackground(new Color(0, 0, 0, 0));
        healthBar.setUI(new javax.swing.plaf.basic.BasicProgressBarUI());
        charPanel.add(healthBar, BorderLayout.NORTH);

        JLabel factionLabel = new JLabel(faction, SwingConstants.CENTER);
        factionLabel.setOpaque(false);
        factionLabel.setForeground(Color.YELLOW);
        charPanel.add(factionLabel, BorderLayout.CENTER);

        JPanel effectsPanel = new JPanel(new GridLayout(4, 1));
        effectsPanel.setOpaque(false);
        String[] labels = {"버프: 없음", "상태이상: 없음", "디버프: 없음", "특수효과: 없음"};

        for (String text : labels) {
            JLabel lbl = new JLabel(text, SwingConstants.CENTER);
            lbl.setOpaque(false);
            lbl.setForeground(Color.YELLOW);
            effectsPanel.add(lbl);
        }

        charPanel.add(effectsPanel, BorderLayout.SOUTH);
        return charPanel;
    }
}
