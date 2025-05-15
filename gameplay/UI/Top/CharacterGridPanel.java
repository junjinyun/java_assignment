package gameplay.UI.Top;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;

public class CharacterGridPanel extends JPanel {

    private GamePlayer gamePlayer;

    public CharacterGridPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        setLayout(new GridLayout(1, 9));
        setOpaque(false);

        for (int i = 4; i >= 1; i--) {
            JButton allyButton = new JButton("A" + i + " - 아군");
            allyButton.setOpaque(false);
            allyButton.setForeground(Color.WHITE);
            allyButton.addActionListener(e -> gamePlayer.selectAllyByMid(i));
            add(allyButton);
        }

        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);  // 텍스트 없이 빈 공간만 차지하도록 설정
        add(emptyPanel);

        for (int i = 1; i <= 4; i++) {
            JButton enemyButton = new JButton("E" + i + " - 적군");
            enemyButton.setOpaque(false);
            enemyButton.setForeground(Color.RED);
            enemyButton.addActionListener(e -> gamePlayer.selectEnemyByMid(i));
            add(enemyButton);
        }
    }
}
