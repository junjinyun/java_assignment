package gameplay.UI;

import gameplay.GamePlayer;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

public class StatsPanel extends JPanel {
    private final Map<String, JLabel> statLabels = new LinkedHashMap<>();

    public StatsPanel(GamePlayer gamePlayer) {
        setLayout(new GridLayout(0, 2)); // 2열로 구성된 능력치 그리드
        setBorder(BorderFactory.createTitledBorder("캐릭터 스탯"));

        AllyStatusManager statusManager = gamePlayer.getAllyParty().getAllyByMappingID(gamePlayer.getMappingId()); // 현재 선택된 아군 데이터 로드
        gamePlayer.addObserver(this); // 옵저버 등록하여 gameplay에서 선택된 아군(MappingId) 변경 시 페인팅을 다시 함

        // 기본 스탯 항목 초기화 (원하는 항목 추가 가능)
        addStat("이름");
        addStat("체력");
        addStat("최대체력");
        addStat("공격력");
        addStat("방어력");
        addStat("최소 속도");
        addStat("최대 속도");
        addStat("속도");
        addStat("위치");

        showDefaultStats(); // 기본값 세팅

        List<String> IdList = Arrays.asList("A1", "A2", "A3", "A4");
        if (IdList.contains(gamePlayer.getMappingId())) { // 조건문 괄호 닫기
            BaseStats baseStats = statusManager.getBaseStats(); // 한 번만 호출하여 변수에 저장
            updateStat("이름", baseStats.getName());
            updateStat("체력", baseStats.getHealth());
            updateStat("최대체력", baseStats.getMaxHealth());
            updateStat("공격력", baseStats.getAttack());
            updateStat("방어력", baseStats.getDefense());
            updateStat("최소 속도", baseStats.getMaxSpeed());
            updateStat("최대 속도", baseStats.getMinSpeed());
            updateStat("속도", statusManager.getSpeed());
            updateStat("위치", statusManager.getPosition());
        }
    }

    private void addStat(String name) {
        JLabel label = new JLabel(name + ": ");
        JLabel value = new JLabel("0"); // 초기값
        statLabels.put(name, value);
        add(label);
        add(value);
    }

    public void updateStat(String name, int value) {
        JLabel label = statLabels.get(name);
        if (label != null) {
            label.setText(String.valueOf(value));
        }
    }

    public void showDefaultStats() {
        for (String key : statLabels.keySet()) {
            JLabel label = statLabels.get(key);
            if (label != null) {
                label.setText("로드 되지 않음");
            }
        }
    }
}
