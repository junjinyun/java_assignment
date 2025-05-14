package gameplay.UI.Bottom.Right;

import javax.swing.*;
import java.awt.*;
import gameplay.GamePlayer;

public class RightPanel extends JPanel {

    public RightPanel(MidPanel midPanel, GamePlayer gamePlayer) {
        setPreferredSize(new Dimension(150, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("보기 선택"));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JButton btnMap = new JButton("지도");
        JButton btnInv = new JButton("인벤토리");
        JButton btnEnemy = new JButton("적 정보");

        btnMap.addActionListener(e -> midPanel.showCard("지도"));
        btnInv.addActionListener(e -> midPanel.showCard("인벤토리"));
        btnEnemy.addActionListener(e -> midPanel.showCard("적군 스탯"));  // 선택된 적의 정보 갱신

        buttonPanel.add(btnMap);
        buttonPanel.add(btnInv);
        buttonPanel.add(btnEnemy);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
