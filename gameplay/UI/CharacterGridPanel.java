package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;

public class CharacterGridPanel extends JPanel {

    public CharacterGridPanel(GamePlayer gamePlayer) {
        setLayout(new GridLayout(1, 9));
        setOpaque(false);

        for (int i = 4; i >= 1; i--) {
            add(CharacterPanelMaker.create("A" + i, "아군"));
        }

        JLabel turnLabel = new JLabel("턴 표시", SwingConstants.CENTER);
        turnLabel.setForeground(Color.YELLOW);
        turnLabel.setOpaque(false);
        add(turnLabel);

        for (int i = 1; i <= 4; i++) {
            add(CharacterPanelMaker.create("E" + i, "적군"));
        }
    }
}
