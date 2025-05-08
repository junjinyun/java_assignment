package UI;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel {

    public LeftPanel(BattleAreaPanel battlePanel) {
        setPreferredSize(new Dimension(920, 0));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("스킬 및 아군 정보"));

        JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 0));
        ButtonGroup skillGroup = new ButtonGroup();

        ImageIcon activeIcon = new ImageIcon("src/image/Interface/green.png");
        ImageIcon inactiveIcon = new ImageIcon("src/image/Interface/red.png");

        for (int i = 0; i < 6; i++) {
            JRadioButton skillButton = new JRadioButton("S" + (i + 1));
            skillButton.setPreferredSize(new Dimension(100, 100));
            skillButton.setIcon(inactiveIcon);

            skillButton.addActionListener(e -> {
                if (skillButton.isSelected()) {
                    skillButton.setIcon(activeIcon);
                } else {
                    skillButton.setIcon(inactiveIcon);
                }

                for (Component comp : skillPanel.getComponents()) {
                    if (comp instanceof JRadioButton btn && btn != skillButton) {
                        btn.setIcon(inactiveIcon);
                        btn.setSelected(false);
                    }
                }
            });

            skillGroup.add(skillButton);
            skillPanel.add(skillButton);
        }

        JPanel skillGroupPanel = new JPanel(new BorderLayout());
        skillGroupPanel.setBorder(BorderFactory.createTitledBorder("스킬 그룹"));
        skillGroupPanel.add(skillPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.add(new JLabel("장비 정보", SwingConstants.CENTER));
        infoPanel.add(new JLabel("아군 스탯 표시창", SwingConstants.CENTER));

        add(skillGroupPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
}
