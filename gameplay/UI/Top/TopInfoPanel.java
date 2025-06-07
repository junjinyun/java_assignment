package gameplay.UI.Top;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import gameplay.GamePlayer;
import gameplay.Party.AllyStatusManager;
import gameplay.Party.EnemyStatusManager;
import gameplay.Party.AllyParty;
import gameplay.Party.EnemyParty;
import gameplay.UI.UIObserver;

public class TopInfoPanel extends JPanel implements UIObserver {

    private JLabel missionLabel;
    private JLabel turnLabel;
    private JLabel actionOrderLabel;
    private JLabel isAliveLabel;

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

        // 임무 목표 (항상 고정: "모든 지점 탐험")
        missionLabel = createLabel("모든 지점 탐험", SwingConstants.LEFT);
        row3.add(missionLabel, BorderLayout.WEST);

        // 턴 정보
        turnLabel = createLabel("턴 정보", SwingConstants.CENTER);
        row3.add(turnLabel, BorderLayout.CENTER);

        // 행동 순서
        actionOrderLabel = createLabel("행동 순서", SwingConstants.RIGHT);
        row3.add(actionOrderLabel, BorderLayout.EAST);

        add(row3);
        
        JPanel row4 = new JPanel(new BorderLayout());
        row4.setOpaque(false);
        isAliveLabel = createLabel("생사 여부", SwingConstants.RIGHT);
        row4.add(isAliveLabel, BorderLayout.EAST);
        add(row4);
    }

    // UIObserver 인터페이스 구현 메서드
    @Override
    public void update(GamePlayer player) {
        // 턴 정보 업데이트 (예: "3턴")
        String turnText = player.getTurn() + "턴";
        updateTurn(turnText);

        // 현재 행동 중인 순서
        int currentAct = player.getActcount();

        // 행동 순서 문자열 만들기
        List<String> orderList = new ArrayList<>();

        // 아군 행동 순서
        AllyParty allyParty = player.getAllyParty();
        if (allyParty != null) {
            for (AllyStatusManager ally : allyParty.getParty()) {
                if (ally.getBaseStats().getHealth() > 0) {
                    String text = ally.getMappingId() + "(" + ally.getActionOrder() + ")";
                    if (ally.getActionOrder() == currentAct) {
                        text = "<b><font color='red'>" + text + "</font></b>";
                    }
                    orderList.add(text);
                }
            }
        }

        // 적군 행동 순서
        EnemyParty enemyParty = player.getEnemyParty();
        if (enemyParty != null) {
            for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
                if (enemy.getBaseStats().getHealth() > 0) {
                    String text = enemy.getMappingId() + "(" + enemy.getActionOrder() + ")";
                    if (enemy.getActionOrder() == currentAct) {
                        text = "<b><font color='red'>" + text + "</font></b>";
                    }
                    orderList.add(text);
                }
            }
        }

        // 행동 순서 정렬 (행동순서 숫자 기준 오름차순)
        orderList.sort((s1, s2) -> {
            int n1 = extractNumber(s1);
            int n2 = extractNumber(s2);
            return Integer.compare(n1, n2);
        });

        // 행동 순서 텍스트 (HTML 형식)
        String actionOrderText = "<html>" + String.join(" ", orderList) + "</html>";
        updateActionOrder(actionOrderText);

        // 생사 여부 표시 (한 줄로: A1:생존 A2:사망 ...)
        StringBuilder aliveStatus = new StringBuilder();

        if (allyParty != null) {
            for (AllyStatusManager ally : allyParty.getParty()) {
                aliveStatus.append(ally.getMappingId())
                           .append(":")
                           .append(ally.getBaseStats().isAlive() ? "생존" : "사망")
                           .append(" ");
            }
        }

        if (enemyParty != null) {
            for (EnemyStatusManager enemy : enemyParty.getEnemyParty()) {
                aliveStatus.append(enemy.getMappingId())
                           .append(":")
                           .append(enemy.isAlive() ? "생존" : "사망")
                           .append(" ");
            }
        }

        isAliveLabel.setText(aliveStatus.toString().trim());
    }


    // 생사 여부 업데이트 메서드 추가
    public void updateIsAlive(String isAliveText) {
        isAliveLabel.setText(isAliveText);
    }


    // 턴 정보 업데이트
    public void updateTurn(String turn) {
        turnLabel.setText(turn);
    }

    // 행동 순서 업데이트
    public void updateActionOrder(String actionOrder) {
        actionOrderLabel.setText(actionOrder);
    }

    // 활성화 및 비활성화 메서드
    public void activateTurn() {
        turnLabel.setEnabled(true);
        turnLabel.setForeground(Color.YELLOW);
    }

    public void deactivateTurn() {
        turnLabel.setEnabled(false);
        turnLabel.setForeground(new Color(0, 0, 0, 0));
    }

    public void activateActionOrder() {
        actionOrderLabel.setEnabled(true);
        actionOrderLabel.setForeground(Color.YELLOW);
    }

    public void deactivateActionOrder() {
        actionOrderLabel.setEnabled(false);
        actionOrderLabel.setForeground(new Color(0, 0, 0, 0));
    }

    // JLabel 생성 메서드
    private JLabel createLabel(String text, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(Color.YELLOW);
        label.setOpaque(false);
        return label;
    }

    // 문자열에서 숫자 추출 (예: "A1(3)" -> 3)
    private int extractNumber(String s) {
        try {
            int start = s.indexOf('(');
            int end = s.indexOf(')');
            if (start >= 0 && end > start) {
                return Integer.parseInt(s.substring(start + 1, end));
            }
        } catch (NumberFormatException e) {
            // 무시
        }
        return Integer.MAX_VALUE; // 실패 시 가장 뒤로 정렬
    }
}
