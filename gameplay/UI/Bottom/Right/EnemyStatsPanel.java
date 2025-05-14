package gameplay.UI.Bottom.Right;

import gameplay.GamePlayer;
import gameplay.Party.EnemyStatusManager;
import loaddata.Enemy;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnemyStatsPanel extends JPanel {

    private final Map<String, JLabel> statLabels = new LinkedHashMap<>();
    private GamePlayer gamePlayer;
    private JPanel statPanel;  // 기본 스탯 정보를 표시할 패널
    private JPanel skillPanel; // 스킬 정보를 표시할 패널

    public EnemyStatsPanel(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        setLayout(new BorderLayout());  // 부모 패널은 BorderLayout을 사용하여 상하로 분리
        
        // 기본 스탯과 소지스킬을 각각 다르게 처리할 패널을 준비
        statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(0, 2));  // 2열로 구성된 능력치 그리드
        statPanel.setBorder(BorderFactory.createTitledBorder("적군 스탯"));
        
        skillPanel = new JPanel();
        skillPanel.setLayout(new GridLayout(1, 1)); // 스킬 영역을 하나의 큰 영역으로 설정
        skillPanel.setBorder(BorderFactory.createTitledBorder("소지스킬"));

        // 기본 스탯 항목 초기화
        addStat("이름", statPanel);
        addStat("체력", statPanel);
        addStat("최대체력", statPanel);
        addStat("공격력", statPanel);
        addStat("방어력", statPanel);
        addStat("최소 속도", statPanel);
        addStat("최대 속도", statPanel);
        addStat("속도", statPanel);
        addStat("위치", statPanel);

        // 소지스킬은 처음에는 빈 상태로 추가
        addStat("소지스킬", skillPanel);

        // 스탯 패널과 스킬 패널을 상하로 분리하는 SplitPanel 사용
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, statPanel, skillPanel);
        splitPane.setDividerLocation(0.7);  // 3:7 비율로 분리
        splitPane.setResizeWeight(0.7); // 크기 비율 유지
        
        add(splitPane, BorderLayout.CENTER);

        showDefaultStats(); // 초기값 세팅
    }

    private void addStat(String name, JPanel panel) {
        JLabel label = new JLabel(name + ": ");
        JLabel value = new JLabel("0"); // 초기값
        statLabels.put(name, value);
        panel.add(label);
        panel.add(value);
    }

    public void updateStat(String name, String value) {
        JLabel label = statLabels.get(name);
        if (label != null) {
            label.setText(value);
        }
    }

    public void updateStat(String name, int value) {
        updateStat(name, String.valueOf(value));
    }

    public void showDefaultStats() {
        for (String key : statLabels.keySet()) {
            JLabel label = statLabels.get(key);
            if (label != null) {
                label.setText("로드 되지 않음");
            }
        }
    }

    // 적군 스탯을 갱신하는 메서드
    public void updateStatsFromGamePlayer() {
        if (gamePlayer == null || gamePlayer.getEnemyParty() == null)
            return;

        EnemyStatusManager statusManager = gamePlayer.getEnemyParty().getEnemyByMappingID(gamePlayer.getEMappingId());
        if (statusManager == null) return;

        Enemy baseStats = statusManager.getBaseStats();
        if (baseStats == null) return;

        updateStat("이름", baseStats.getName());
        updateStat("체력", baseStats.getHealth());
        updateStat("최대체력", baseStats.getMaxHealth());
        updateStat("공격력", baseStats.getAttack());
        updateStat("방어력", baseStats.getDefense());
        updateStat("최소 속도", baseStats.getMinSpeed());
        updateStat("최대 속도", baseStats.getMaxSpeed());
        updateStat("속도", statusManager.getCurrentSpeed());
        updateStat("위치", statusManager.getPosition());

        // 변환 함수: default -> 없음
        java.util.function.Function<Object, String> displayValue = val -> {
            if (val == null || "default".equals(val.toString())) return "없음";
            return val.toString();
        };

        // 스킬 이름, 피해 배율, 효과까지만 출력
        StringBuilder skillsInfo = new StringBuilder("<html>");
        statusManager.getSkillList().forEach(skill -> {
            skillsInfo.append("이름: ").append(displayValue.apply(skill.getName())).append(" / ")
                      .append("피해 배율: ").append(displayValue.apply(skill.getDamageMultiplier())).append(" / ")
                      .append("효과: ").append(displayValue.apply(skill.getAEffect())).append("<br>")
            		  .append("제약조건: ").append(displayValue.apply(skill.getConstraint())).append(" / ")
            		  .append("사용위치: ").append(displayValue.apply(skill.getSkillActivationZone())).append(" / ")
            		  .append("대상위치: ").append(displayValue.apply(skill.getTargetLocation())).append("<br> <br>");
        });
        skillsInfo.append("</html>");

        // 소지스킬 라벨에 업데이트된 정보 표시
        JLabel skillLabel = statLabels.get("소지스킬");
        if (skillLabel != null) {
            skillLabel.setText(skillsInfo.toString());
        }
    }
}
