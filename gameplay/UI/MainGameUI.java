package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import gameplay.GamePlayer;

public class MainGameUI extends JFrame {

    private GamePlayer gamePlayer;

    public MainGameUI() {
        this.gamePlayer = new GamePlayer();  // GamePlayer 객체 생성
        
        setTitle("메인 UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // 테두리 제거

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new TopPanel(gamePlayer));  // TopPanel에 GamePlayer 전달
        mainPanel.add(new BottomPanel(gamePlayer));  // BottomPanel에 GamePlayer 전달

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameUI frame = new MainGameUI();

            // 전체화면 설정
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(frame); // 진짜 전체화면 모드 진입
            } else {
                // Fallback: 최대화 + undecorated (거의 전체화면)
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });
    }
}
