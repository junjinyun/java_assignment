package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;
import java.awt.image.BufferedImage;

public class TopPanel extends JPanel {

    private GamePlayer gamePlayer;

    public TopPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;  // GamePlayer 객체 전달
        
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel titleLabel = new JLabel("해당 턴의 캐릭터 행동순서", SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setOpaque(false);
        add(titleLabel, BorderLayout.NORTH);

        JPanel charactersPanel = new JPanel(new GridLayout(1, 9));
        charactersPanel.setOpaque(false);

        for (int i = 4; i >= 1; i--)
            charactersPanel.add(createCharacterPanel("A" + i));

        JLabel turnLabel = new JLabel("턴 표시", SwingConstants.CENTER);
        turnLabel.setForeground(Color.YELLOW);
        turnLabel.setOpaque(false);
        charactersPanel.add(turnLabel);

        for (int i = 1; i <= 4; i++)
            charactersPanel.add(createCharacterPanel("E" + i));

        add(charactersPanel, BorderLayout.CENTER);
    }

    private JPanel createCharacterPanel(String name) {
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

        JLabel factionLabel = new JLabel("아군", SwingConstants.CENTER);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon("src/image/Interface/cave.jpg");
        Image img = icon.getImage();
        BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2d.dispose();
        g.drawImage(resizedImage, 0, 0, this);
    }
}
