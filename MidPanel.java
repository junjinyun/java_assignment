package gameplay.UI.Bottom.Right;

import gameplay.GamePlayer;
import gameplay.UI.UIObserver;

import javax.swing.*;
import java.awt.*;

public class MidPanel extends JPanel implements UIObserver {

    private final CardLayout cardLayout;
    private final GamePlayer gamePlayer;
    private final EnemyStatsPanel enemyStatsPanel;
    private final JPanel mapWrapperPanel; // 감싸는 패널
    private final MapPanel mapPanel;
    private JPanel cardPanel = this;

    public MidPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(800, 800));
        setBorder(BorderFactory.createTitledBorder("멀티"));

        // MapPanel 초기화
        this.mapPanel = new MapPanel(gamePlayer);
        this.mapWrapperPanel = new JPanel(new GridBagLayout()); // 중앙 정렬용
        mapWrapperPanel.add(mapPanel); // 중앙에 배치

        // 기타 패널
        this.enemyStatsPanel = new EnemyStatsPanel(gamePlayer);
        InventoryUI inventoryUI = new InventoryUI(cardLayout, cardPanel);
        add(inventoryUI, "인벤토리");

        add(mapWrapperPanel, "지도");
        add(enemyStatsPanel, "적군 스탯");

        cardLayout.show(this, "지도");
    }

    public void showCard(String name) {
        cardLayout.show(this, name);
    }
    
    @Override
    public void update(GamePlayer gamePlayer) {
        enemyStatsPanel.updateStatsFromGamePlayer();
        cardLayout.show(this, "적군 스탯");
    }
}
