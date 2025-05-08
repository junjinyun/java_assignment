package UI;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {

    public RightPanel(BattleAreaPanel battlePanel) {
        setPreferredSize(new Dimension(150, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("보기 선택"));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        JButton btnMap = new JButton("지도");
        JButton btnInv = new JButton("인벤토리");
        JButton btnEnemy = new JButton("적 정보");

        btnMap.addActionListener(e -> battlePanel.showCard("지도"));
        btnInv.addActionListener(e -> battlePanel.showCard("인벤토리"));
        btnEnemy.addActionListener(e -> battlePanel.showCard("적 정보"));

        buttonPanel.add(btnMap);
        buttonPanel.add(btnInv);
        buttonPanel.add(btnEnemy);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
