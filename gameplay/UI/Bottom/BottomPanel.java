package gameplay.UI.Bottom;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;
import gameplay.UI.Bottom.Left.LeftPanel;
import gameplay.UI.Bottom.Right.MidPanel;
import gameplay.UI.Bottom.Right.RightPanel;

public class BottomPanel extends JPanel {

    private GamePlayer gamePlayer;
    private MidPanel midPanel;

    public BottomPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;  // GamePlayer 객체 전달
        
        setLayout(new BorderLayout());

        // GamePlayer를 MidPanel 생성자에 전달
        this.midPanel = new MidPanel(gamePlayer);  // 수정된 부분
        gamePlayer.addObserver(midPanel);
        add(new LeftPanel(gamePlayer), BorderLayout.WEST);
        add(midPanel, BorderLayout.CENTER);
        add(new RightPanel(midPanel,gamePlayer), BorderLayout.EAST);
    }
    public MidPanel getMidPanel() {
        return midPanel;
    }
}
