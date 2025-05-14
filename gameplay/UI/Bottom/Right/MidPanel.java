package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;

public class MidPanel extends JPanel {

    private final CardLayout cardLayout;
    private final GamePlayer gamePlayer;  // GamePlayer 객체 선언

    // GamePlayer 객체를 받는 생성자 추가
    public MidPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;  // 전달된 GamePlayer 객체 저장
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(800, 800));
        setBorder(BorderFactory.createTitledBorder("멀티"));

        JPanel mapPanel = new JPanel();
        mapPanel.add(new JLabel("A - 지도"));

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.add(new JLabel("A - 인벤토리"));

        JPanel enemyPanel = new JPanel();
        enemyPanel.add(new JLabel("A - 적 정보"));

        add(mapPanel, "지도");
        add(inventoryPanel, "인벤토리");
        add(enemyPanel, "적 정보");

        cardLayout.show(this, "지도");
    }

    public void showCard(String name) {
        cardLayout.show(this, name);
    }
}
