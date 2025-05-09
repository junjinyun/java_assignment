package gameplay.UI;

import javax.swing.*;
import java.awt.*;

public class TopInfoPanel extends JPanel {

    private JLabel missionLabel;
    private JLabel turnLabel;
    private JLabel actionOrderLabel;

    public TopInfoPanel() {
        setLayout(new GridLayout(5, 1));
        setOpaque(false);

        // 1줄: 공백
        JLabel blank = new JLabel(" ");
        blank.setOpaque(false);
        add(blank);

        // 3줄: 임무 목표 / 턴 / 행동순서
        JPanel row3 = new JPanel(new BorderLayout());
        row3.setOpaque(false);

        // 임무 목표
        missionLabel = createLabel("임무 목표", SwingConstants.LEFT);
        row3.add(missionLabel, BorderLayout.WEST);

        // 턴 정보
        turnLabel = createLabel("턴 정보", SwingConstants.CENTER);
        row3.add(turnLabel, BorderLayout.CENTER);

        // 행동 순서
        actionOrderLabel = createLabel("행동 순서", SwingConstants.RIGHT);
        row3.add(actionOrderLabel, BorderLayout.EAST);

        add(row3);
    }

    // 텍스트를 변경할 수 있도록 update 메서드 제공
    public void updateMission(String mission) {
        missionLabel.setText(mission);
    }

    public void updateTurn(String turn) {
        turnLabel.setText(turn);
    }

    public void updateActionOrder(String actionOrder) {
        actionOrderLabel.setText(actionOrder);
    }

    // 활성화 및 비활성화 메서드
    public void activateTurn() {
        turnLabel.setEnabled(true);
        turnLabel.setForeground(Color.YELLOW);  // 활성화된 텍스트 색상
    }

    public void deactivateTurn() {
        turnLabel.setEnabled(false);
        turnLabel.setForeground(new Color(0, 0, 0, 0));  // 비활성화된 텍스트를 완전히 투명하게 설정
    }

    public void activateActionOrder() {
        actionOrderLabel.setEnabled(true);
        actionOrderLabel.setForeground(Color.YELLOW);  // 활성화된 텍스트 색상
    }

    public void deactivateActionOrder() {
        actionOrderLabel.setEnabled(false);
        actionOrderLabel.setForeground(new Color(0, 0, 0, 0));  // 비활성화된 텍스트를 완전히 투명하게 설정
    }

    // JLabel을 생성하는 메서드
    private JLabel createLabel(String text, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(Color.YELLOW);
        label.setOpaque(false);
        return label;
    }
}
