package gameplay.UI;

import gameplay.Event.EventLauncher;
import gameplay.Event.EventListener;
import gameplay.GamePlayer;
import gameplay.UI.Bottom.BottomPanel;
import gameplay.UI.Shop.ShopUI;
import gameplay.UI.Top.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGameUI extends JFrame implements EventListener {

    private GamePlayer gamePlayer;
    private EventLauncher eventLauncher;

    public MainGameUI() {
        this.gamePlayer = new GamePlayer();
        eventLauncher = new EventLauncher(gamePlayer);
        this.gamePlayer.setEventLauncher(this.eventLauncher);
        this.eventLauncher.addEventListener(this);

        setTitle("메인 UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(new TopPanel(gamePlayer));
        mainPanel.add(new BottomPanel(gamePlayer));

        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void onEvent(String eventType, Object data) {
        if ("merchant".equals(eventType) || "blackMarket".equals(eventType)) {
            SwingUtilities.invokeLater(() -> {
                showShopWindowNonModal("상점", new ShopUI(null, gamePlayer, data));
            });
        }
    }

    /**
     * 비모달 상점 창 띄우기 - 전체 화면 해제 후 창 복구
     */
    private void showShopWindowNonModal(String title, JPanel shopUIContent) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        // 전체화면 해제 및 창 복구 (해상도 작업 표시줄 영역 고려)
        if (gd != null && gd.getFullScreenWindow() == this) {
            gd.setFullScreenWindow(null);

            // 전체화면 해제 후 창 복구
            setExtendedState(JFrame.NORMAL);

            // 작업 표시줄 제외한 사용 가능한 화면 크기 구하기
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Rectangle screenBounds = ge.getMaximumWindowBounds();

            setSize(screenBounds.width, screenBounds.height);
            setLocation(screenBounds.x, screenBounds.y);

            revalidate();
            repaint();

            setVisible(true);
        }

        // 부모창(MainGameUI)을 소유자로 하는 비모달 JDialog 생성
        JDialog shopDialog = new JDialog(this, title, Dialog.ModalityType.MODELESS);

        // 상점 UI 패널 세팅
        shopDialog.setContentPane(shopUIContent);
        shopDialog.pack();

        // 부모창 기준 중앙 위치
        shopDialog.setLocationRelativeTo(this);

        // 항상 최상위 옵션은 끄기
        shopDialog.setAlwaysOnTop(false);

        // 최소화/최대화 버튼 비활성화
        shopDialog.setResizable(true);

        // 창 닫을 때 이벤트 처리 및 전체화면 복구
        shopDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        shopDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (gd != null) {
                    gd.setFullScreenWindow(MainGameUI.this);
                }
            }
        });

        // 창 띄우기
        shopDialog.setVisible(true);
    }

    public EventLauncher getEventLauncher() {
        return eventLauncher;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGameUI frame = new MainGameUI();

            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(frame);
            } else {
                // 작업 표시줄 영역 제외 크기로 초기화
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Rectangle screenBounds = ge.getMaximumWindowBounds();

                frame.setSize(screenBounds.width, screenBounds.height);
                frame.setLocation(screenBounds.x, screenBounds.y);
                frame.setVisible(true);
            }
        });
    }
}
