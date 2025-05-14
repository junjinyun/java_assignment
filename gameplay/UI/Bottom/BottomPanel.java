package gameplay.UI;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;

public class BottomPanel extends JPanel {

    private GamePlayer gamePlayer;

    public BottomPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;  // GamePlayer 객체 전달
        
        setLayout(new BorderLayout());

        // GamePlayer를 MidPanel 생성자에 전달
        MidPanel midPanel = new MidPanel(gamePlayer);  // 수정된 부분
        add(new LeftPanel(midPanel, gamePlayer), BorderLayout.WEST);
        add(midPanel, BorderLayout.CENTER);
        add(new RightPanel(midPanel), BorderLayout.EAST);
    }
}
