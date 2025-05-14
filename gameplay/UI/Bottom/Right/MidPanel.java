package gameplay.UI.Bottom.Right;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;
import gameplay.UI.UIObserver;

public class MidPanel extends JPanel implements UIObserver {

    private final CardLayout cardLayout;
    private final GamePlayer gamePlayer;
    private final EnemyStatsPanel enemyStatsPanel;

    public MidPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(800, 800));
        setBorder(BorderFactory.createTitledBorder("멀티"));

        // 아군 및 적군 스탯 패널
        enemyStatsPanel = new EnemyStatsPanel(gamePlayer);  // 적군용 EnemyStatsPanel

        JPanel mapPanel = new JPanel();
        mapPanel.add(new JLabel("지도"));

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.add(new JLabel("인벤토리"));

        add(mapPanel, "지도");
        add(inventoryPanel, "인벤토리");
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
