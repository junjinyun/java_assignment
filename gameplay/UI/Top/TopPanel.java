package gameplay.UI.Top;

import gameplay.GamePlayer;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    public TopPanel(GamePlayer gamePlayer) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // 상단 정보 (고정 높이 80px)
        TopInfoPanel infoPanel = new TopInfoPanel();
        infoPanel.setPreferredSize(new Dimension(0, 80));
        add(infoPanel, BorderLayout.NORTH);

        // 캐릭터 그리드 (나머지 공간 채움)
        CharacterGridPanel gridPanel = new CharacterGridPanel(gamePlayer);
        add(gridPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon icon = new ImageIcon("src/image/Interface/cave.jpg");
        Image img = icon.getImage();

        // 배경 크기에 맞춰 다시 그리기
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }
}
