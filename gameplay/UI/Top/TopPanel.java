package gameplay.UI.Top;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;
import java.awt.image.BufferedImage;

public class TopPanel extends JPanel {

    public TopPanel(GamePlayer gamePlayer) {
        setLayout(new BorderLayout());
        setOpaque(false);

        // 텍스트 정보 (1 비율)
        TopInfoPanel infoPanel = new TopInfoPanel();
        infoPanel.setPreferredSize(new Dimension(0, 80));
        add(infoPanel, BorderLayout.NORTH);

        // 아군/적군 캐릭터 정보 (7 비율)
        CharacterGridPanel gridPanel = new CharacterGridPanel(gamePlayer);
        add(gridPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 배경 이미지 로드
        ImageIcon icon = new ImageIcon("src/image/Interface/cave.jpg");  // 기존 배경 이미지 경로
        Image img = icon.getImage();
        
        // 이미지 크기 조정
        BufferedImage resizedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2d.dispose();

        // 배경 그리기
        g.drawImage(resizedImage, 0, 0, this);
    }
}
