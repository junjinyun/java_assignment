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

            // 아군 캐릭터 패널 생성
            JPanel allyPanel = CharacterPanelMaker.create("A" + i, "아군", gamePlayer);
            add(allyPanel);
        }

        for (int i = 1; i <= 4; i++) {
            JButton enemyButton = new JButton("E" + i + " - 적군");
            enemyButton.setOpaque(false);
            enemyButton.setForeground(Color.RED);
            enemyButton.addActionListener(e -> gamePlayer.selectEnemyByMid(i));
            add(enemyButton);

            // 적군 캐릭터 패널 생성
            JPanel enemyPanel = CharacterPanelMaker.create("E" + i, "적군", gamePlayer);
            add(enemyPanel);
        }
    }
}
