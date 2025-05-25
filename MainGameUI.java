package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;
import gameplay.UI.Bottom.BottomPanel;
import gameplay.UI.Top.TopPanel;

public class MainGameUI extends JFrame {

    private GamePlayer gamePlayer;

    public MainGameUI() {
        this.gamePlayer = new GamePlayer();

        setTitle("메인 UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setUndecorated(true); // 테두리 제거

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new TopPanel(gamePlayer));
        mainPanel.add(new BottomPanel(gamePlayer));

        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameUI frame = new MainGameUI();

            // 전체화면 설정
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(frame);
                frame.setVisible(true);
            } else {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
            }
        });
    }
}
